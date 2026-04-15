package techguns.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import techguns.TGArmorBonus;
import techguns.TGConfig;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.client.ClientProxy;
import techguns.client.audio.TGSoundCategory;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.gui.playerinventory.TGPlayerInventory;
import techguns.items.armors.GenericArmor;
import techguns.items.armors.ITGSpecialSlot;
import techguns.items.armors.PoweredArmor;
import techguns.items.guns.GenericGun;
import techguns.packets.PacketPlaySound;
import techguns.packets.PacketShootGun;
import techguns.packets.PacketTGExtendedPlayerSync;
import techguns.util.TextUtil;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TechgunsTickHandler {
	private static final UUID UUID_SPEED = UUID.fromString("5D8E53EB-DCFA-4121-B4DB-99BCAFA6B70B");
	private static final UUID UUID_HEALTH = UUID.fromString("4CFA49EB-D215-498B-9CC9-4BD0D1350B1F");
	private static final UUID UUID_KNOCKBACK_RESISTANCE = UUID.fromString("3441FC5D-F0B6-47F4-AFBB-DC5005670254");
	
	private static Method ITEMFOOD_onFoodEaten;
	static {
		ITEMFOOD_onFoodEaten = ReflectionHelper.findMethod(ItemFood.class, null, new String[]{"onFoodEaten","func_77849_c"}, ItemStack.class, World.class, EntityPlayer.class);
	}
	
	/*@SubscribeEvent
	public void PlayerTick(PlayerTickEvent event){
		

		
			ItemStack current_item = event.player.getEquipmentInSlot(0);//event.player.getItemInUse();
			//System.out.println("Player:"+event.player.getGameProfile().getName());
			
			if (current_item != null) {
				System.out.println("item:"+current_item.getDisplayName());
				if(current_item.getItem() instanceof GenericGun){
	
				} else {
					Techguns.player_zoom=1.0f;
				}
			} else {
				Techguns.player_zoom=1.0f;
			}

	}*/
	
	//Called whenever the player is updated or ticked. 
	 @SubscribeEvent
	 public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		 
		 TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(event.player);
		 
		 //System.out.println(event.phase);
		 if (event.phase == Phase.START){
			 
			 /*if(event.side==Side.SERVER){
				 ItemStack stack=event.player.getHeldItem();
				 if ( stack!=null &&stack.getItem() instanceof GenericGun){
					 ((EntityPlayerMP) event.player).theItemInWorldManager.setBlockReachDistance(0.05f);
					 
				 } else {
					 //((EntityPlayerMP) event.player).theItemInWorldManager.setBlockReachDistance(((EntityPlayerMP) event.player).theItemInWorldManager.getBlockReachDistance());
				 }
			 
			 }*/
			 
			 //reduce fire delays on both sides
			 if(props.fireDelay>0){
				 props.fireDelay--;
			 }
			 if(props.loopSoundDelay>0){
				 props.loopSoundDelay--;
			 }
			 
			 
			 if(event.side==Side.CLIENT){
				 ClientProxy cp = ClientProxy.get();
				 
				 
				 //ONLY DO FOR OWN PLAYER!
				 if (event.player==cp.getPlayerClient()){
					 
					 //System.out.println("Clientside Playertick for"+event.player.getCommandSenderName());
					 if(Minecraft.getMinecraft().inGameHasFocus){
						 ItemStack stack=event.player.getCurrentEquippedItem();
						 if ( stack!=null && stack.getItem() instanceof GenericGun && ((GenericGun)stack.getItem()).isShootWithLeftClick()){
							 if(cp.keyFirePressed){
								 //event.player.swingItem();
								 GenericGun gun = (GenericGun) stack.getItem();
								 if (props.fireDelay <=0){
									 
									 TGPackets.network.sendToServer(new PacketShootGun(gun.isZooming()));
									 gun.shootGunLeftClick(stack, event.player.worldObj, event.player, gun.isZooming());
								 }
								 if(gun.isSemiAuto()){
									 cp.keyFirePressed=false;
								 }
							 }
							 /*if(((GenericGun)stack.getItem()).isSemiAuto()){
								 cp.keyFirePressed=false;
							 }*/
						 } else {
							 cp.keyFirePressed=false;
						 }
					 } else {
						 cp.keyFirePressed=false;
					 }
				 }
			 
			 } else {
				 //SERVER ONLY CODE
				 if (props.swingSoundDelay>0){
					 props.swingSoundDelay--;
				 }
			 }
		 } else {
		 
		 
		 
		 if(!event.player.worldObj.isRemote){
			event.player.getDataWatcher().updateObject(TechgunsExtendedPlayerProperties.DATA_WATCHER_ID_FACESLOT, props.TG_inventory.inventory[TGPlayerInventory.SLOT_FACE]);
			event.player.getDataWatcher().updateObject(TechgunsExtendedPlayerProperties.DATA_WATCHER_ID_BACKSLOT, props.TG_inventory.inventory[TGPlayerInventory.SLOT_BACK]);
		 }
		 
		 boolean wearingTechgunsArmor = false;
		 for (int i =0;i<4;i++){
			 ItemStack istack = event.player.inventory.armorItemInSlot(i);
			 if (GenericArmor.isTechgunArmor(istack)){
				 wearingTechgunsArmor = true;
				 break;
			 }
		 }
		 
         IAttributeInstance iattributeinstance = event.player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
         if (iattributeinstance!=null){
        	 AttributeModifier mod_speed = iattributeinstance.getModifier(this.UUID_SPEED);
        	 if (mod_speed!=null){
        		 iattributeinstance.removeModifier(mod_speed);
        	 }
         }
         
         //AttributeModifier speedboost = new AttributeModifier(this.UUID_SPEED, "TechgunsSpeedboost", 0.1f, 2);
         
         IAttributeInstance iattributeinstance2 = event.player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
         if (iattributeinstance2!=null){
        	 AttributeModifier mod_health = iattributeinstance2.getModifier(UUID_HEALTH);
        	 if (mod_health !=null){
        		 iattributeinstance2.removeModifier(mod_health);
        	 }
         }
         
         IAttributeInstance iattributeinstance3 = event.player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.knockbackResistance);
         if (iattributeinstance3!=null){
        	 AttributeModifier mod_knockback_resistance = iattributeinstance3.getModifier(UUID_KNOCKBACK_RESISTANCE);
        	 if (mod_knockback_resistance  !=null){
        		 iattributeinstance3.removeModifier(mod_knockback_resistance);
        	 }
         }
         
         
         //AttributeModifier healthboost = new AttributeModifier(this.UUID_HEALTH, "TechgunsHealthbonus", 0.1f, 0);
         
         /*if (iattributeinstance != null)
         {
             iattributeinstance.removeModifier(speedboost);
         }
         if (iattributeinstance2 != null)
         {
        	 iattributeinstance2.removeModifier(healthboost);
         }*/
		 
         float speed=0.0f;
		 if (wearingTechgunsArmor){
			/* if(event.player.onGround){
				 event.player.motionX*=1.2f;
				 event.player.motionZ*=1.2f;
			 } else {
				 event.player.motionY*=1.1f;
			 }*/
			 
			 PoweredArmor.calculateConsumptionTick(event.player);
			 
			 
			 if (event.player.isBurning()){
				 float cooling = GenericArmor.getArmorBonusForPlayer(event.player, TGArmorBonus.COOLING_SYSTEM, false);
				 if (cooling >=1.0f){
					 event.player.extinguish();
				 }
			 }
			 
			 //Check for stats
			 speed = GenericArmor.getArmorBonusForPlayer(event.player, TGArmorBonus.SPEED,false);
			 
			 if (event.player.isInsideOfMaterial(Material.water) || event.player.isInsideOfMaterial(Material.lava)){
				 speed += GenericArmor.getArmorBonusForPlayer(event.player, TGArmorBonus.SPEED_WATER,false);
			 }
			 //System.out.println("SPEED:"+speed);
			 
			 if ((speed) >0.0f){
				 //event.player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id,2,0,true));
				 if(event.player.isSprinting()){
					 speed=speed*2.0f;
				 }
				 
				 if(event.player.isUsingItem() && event.player.getHeldItem()!=null){
					 ItemStack item = event.player.getHeldItem();
					 if (item.getItem() instanceof GenericGun){
						 speed+=5.0f;
					 }
				 }
				 
				 //speedboost = ;
	             iattributeinstance.applyModifier(new AttributeModifier(this.UUID_SPEED, "TechgunsSpeedboost", speed, 2));
			 }
			 float healthBonus = GenericArmor.getArmorBonusForPlayer(event.player, TGArmorBonus.EXTRA_HEART,false);
			 if (healthBonus >0.0f){
				 
				 //healthboost = ;
	             iattributeinstance2.applyModifier(new AttributeModifier(this.UUID_HEALTH, "TechgunsHealthbonus", healthBonus, 0));
				 
			 }
			 
			 float knockbackresistance = GenericArmor.getArmorBonusForPlayer(event.player, TGArmorBonus.KNOCKBACK_RESISTANCE,false);
			 if (knockbackresistance >0.0f){
				 
				 //healthboost = ;
	             iattributeinstance3.applyModifier(new AttributeModifier(this.UUID_KNOCKBACK_RESISTANCE, "TechgunsKnockbackresistbonus", knockbackresistance, 0));
				 
			 }
			 
		 } else {
			 //event.player.capabilities.setPlayerWalkSpeed(0.1f);
			/* if(event.player.isUsingItem() && event.player.getHeldItem()!=null){
				 ItemStack item = event.player.getHeldItem();
				 if (item.getItem() instanceof GenericGun){
					 speed+=3.5f;
				 }
			 }*/
			 
			 //speedboost = ;
			 if (speed>0.0f){
				 iattributeinstance.applyModifier(new AttributeModifier(this.UUID_SPEED, "TechgunsSpeedboost", speed, 2));
			 }
		 }
		 
		 //Step height is client only
		 if(event.player.worldObj.isRemote){
			//ONLY DO FOR OWN PLAYER!
			 if (event.player==ClientProxy.get().getPlayerClient()){
				 float stepassist = GenericArmor.getArmorBonusForPlayer(event.player, TGArmorBonus.STEPASSIST,false);
				 
				 Techguns.proxy.setHasStepassist(stepassist>0.0f);
				 
				 if(props.enableStepAssist && (stepassist>0)){
					 
					 if(event.player.stepHeight<1.0f){
						 event.player.stepHeight=1.0f;
					 } 
					 
				 } else {
					 
					 if(event.player.stepHeight>0.5f){
						 event.player.stepHeight=0.5f;
					 }
					 
				 }
			 }
		 }
		 //System.out.println("Player step heighT:"+event.player.stepHeight);
		 
		 //System.out.println("Player fly speed:"+event.player.capabilities.getFlySpeed());
		 //event.player.capabilities.setFlySpeed(0.2f);
		 
		 //nightvision
		 boolean enabled=false;
		 if(props!=null){
			 enabled= props.enableNightVision;
		 }
		 float nightvision=GenericArmor.getArmorBonusForPlayer(event.player, TGArmorBonus.NIGHTVISION, enabled);
		 Techguns.proxy.setHasNightvision(nightvision>0.0f);
		 if(nightvision>0.0f && enabled){
			 if(!event.player.worldObj.isRemote){
				 event.player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 220, -1));
			 }
		 }
		 		 
		 
		 //flying
		 float flightbonus = GenericArmor.getArmorBonusForPlayer(event.player, TGArmorBonus.CREATIVE_FLIGHT, false);
		 if (flightbonus >0){
			 event.player.capabilities.allowFlying = true;
			 props.gotCreativeFlightLastTick=true;
			 
			 if (event.player.capabilities.isFlying){
				 if (!props.wasFlying){
					 
					 if (event.player.worldObj.isRemote) {
						 Techguns.proxy.createFXOnEntity("AntiGravRing", event.player);
					// System.out.println("EFFECT!");
					 } else {
						 TGPackets.network.sendToAllAround(new PacketPlaySound("techguns:items.antiGravStart", event.player, 1.0f, 1.0f, false, true, false, true, TGSoundCategory.PLAYER_EFFECT), new TargetPoint(event.player.worldObj.provider.dimensionId, event.player.posX, event.player.posY, event.player.posZ, 50.0f));
					 }
					 
				 } 
				 props.wasFlying=true;
				 
			 } else {
				 props.wasFlying=false;
			 }
			 
			 
		 } else {
			 if (props.gotCreativeFlightLastTick && !event.player.capabilities.isCreativeMode){
				 event.player.capabilities.allowFlying=false;
				 event.player.capabilities.isFlying=false;
			 }
			 props.gotCreativeFlightLastTick=false;
			 props.wasFlying=false;
		 }
		 
		 //Fly speed is client only
		 if(event.player.worldObj.isRemote){
			 final float DEFAULT_FLYSPEED=0.05f;
			//ONLY DO FOR OWN PLAYER!
			 if (event.player==ClientProxy.get().getPlayerClient()){
				 float flyspeedBonus = GenericArmor.getArmorBonusForPlayer(event.player, TGArmorBonus.FLYSPEED,false);
				 if (flyspeedBonus>0){
					 if (props.enableJetpack){
						 Techguns.proxy.setFlySpeed((1.0f+flyspeedBonus)*DEFAULT_FLYSPEED);
					 } else {
						 Techguns.proxy.setFlySpeed(DEFAULT_FLYSPEED);
					 }
				 } else {
					 if (props.gotCreativeFlightLastTick){
						 Techguns.proxy.setFlySpeed(DEFAULT_FLYSPEED);
					 }
				 }
			 }
		 }
		 
		 
		 //Auto food consumption
		
		 if (!TGConfig.disableAutofeeder && event.player.getFoodStats().getFoodLevel()<=19){
			/*if( Techguns.consumeAmmo(event.player.inventory.mainInventory, TGItems.bioTank, 0, event.player.inventory.mainInventory.length)){
				event.player.getFoodStats().addStats(4, 1.0f);
				if (!event.player.worldObj.isRemote){
					event.player.worldObj.playSoundAtEntity(event.player, "random.eat", 1.0f, 1.0f);
				}
				int x = Techguns.addItemToInventory(event.player.inventory.mainInventory, TGItems.newStack(TGItems.bioTankEmpty,1), 0, event.player.inventory.mainInventory.length);
				if(x>0){
					if (!event.player.worldObj.isRemote){
						event.player.worldObj.spawnEntityInWorld(new EntityItem(event.player.worldObj, event.player.posX, event.player.posY, event.player.posZ, TGItems.newStack(TGItems.bioTankEmpty, x)));
					}
				}
			}*/
			 
			 if (props!=null){
				 int needed = 20-event.player.getFoodStats().getFoodLevel();
				 if (props.foodleft>0){
					 if (props.foodleft<=needed){
						 event.player.getFoodStats().addStats(props.foodleft,props.lastSaturation);
						 props.foodleft=0;
						 props.lastSaturation=0.0f;
					 } else {
						 event.player.getFoodStats().addStats(needed,props.lastSaturation);
						 props.foodleft-=needed;
					 }
					 if (!event.player.worldObj.isRemote){
						 TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(event.player,props,true), (EntityPlayerMP) event.player);
					 }
				 } else {
					 ItemStack stack = Techguns.consumeFood(props.TG_inventory.inventory, props.TG_inventory.SLOTS_AUTOFOOD_START, props.TG_inventory.SLOTS_AUTOFOOD_END+1);
					 if (stack !=null){
						 ItemFood food = (ItemFood) stack.getItem();
						 
						 //check potion effect.
						 //food.
						 try {
							ITEMFOOD_onFoodEaten.invoke(food, stack, event.player.worldObj,event.player);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
						 
						 if (!event.player.worldObj.isRemote){
							 event.player.worldObj.playSoundAtEntity(event.player, "random.eat", 1.0f, 1.0f);
						 }
						 
						 int left = food.func_150905_g(stack)-needed;
						 if (left >0){
							 event.player.getFoodStats().addStats(needed, food.func_150906_h(stack));
							 props.foodleft=left;
							 props.lastSaturation=food.func_150906_h(stack);
						 } else {
							 event.player.getFoodStats().addStats(food.func_150905_g(stack), food.func_150906_h(stack));
							 props.foodleft=0;
							 props.lastSaturation=0.0f;
						 }
						 if (!event.player.worldObj.isRemote){
							 TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(event.player,props,true), (EntityPlayerMP) event.player);
						 }
					 }
				 }
			 }
			 
		 }			 
		 
		 //Additional slot handling:
		 if (props !=null){
			 ItemStack faceslot = props.TG_inventory.inventory[TGPlayerInventory.SLOT_FACE];
			 if (faceslot !=null){
				 ITGSpecialSlot	face = (ITGSpecialSlot) faceslot.getItem();			 
				 face.onPlayerTick(faceslot, event, props);
			 }
			 
			 props.isGliding=false;
			 ItemStack backslot = props.TG_inventory.inventory[TGPlayerInventory.SLOT_BACK];
			 if (backslot !=null){
				 ITGSpecialSlot	back = (ITGSpecialSlot) backslot.getItem();			 
				 back.onPlayerTick(backslot, event, props);
			 }
			 
			 /*if(event.player.worldObj.isRemote){
				 
			 }*/
			 //System.out.println("Player Gliding:"+props.isGliding);
			 Techguns.proxy.handlePlayerGliding(props, event.player);
		 }
		 
		 }
		 
		 //FOOD exhaustion manipulation
		/* try{
			 FoodStats stats= event.player.getFoodStats();
			 Field exhaustion = FoodStats.class.getDeclaredField("foodExhaustionLevel");
			 exhaustion.setAccessible(true);
			 if(exhaustion.getFloat(stats)>20.0f){
				 exhaustion.setFloat(stats, 20.0f);
			 }
		 } catch(Exception e){
			 e.printStackTrace();
		 }*/
		 
	}

	 @SideOnly(Side.CLIENT)
	 @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	 public void updateCheckMessage(PlayerTickEvent event){
		 if (event.side.isClient() && !ClientProxy.get().versionChecker.warned && ClientProxy.get().versionChecker.warn!=0){
			 
			ClickEvent chatClickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, "https://minecraft.curseforge.com/projects/techguns/files");
			
			ChatComponentText downloadMsg = new ChatComponentText(TextUtil.trans("techguns.updateCheck.download"));
			
	        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, downloadMsg);
			
			ChatStyle chatStyle = new ChatStyle().setChatClickEvent(chatClickEvent);
			chatStyle.setChatHoverEvent(hoverEvent);
	    
	        String msg = EnumChatFormatting.GREEN+"[Techguns]("+Techguns.VERSION+"): "+EnumChatFormatting.WHITE;
	        
	        byte warn = ClientProxy.get().versionChecker.warn;
	        if (warn==1){
	        	msg += TextUtil.trans("techguns.updateCheck.newVersion.stable") + ": "+ClientProxy.get().versionChecker.recommended;
	        } else if (warn==2){
	        	msg += TextUtil.trans("techguns.updateCheck.newVersion.beta")+": "+ClientProxy.get().versionChecker.latest;
	        };
	        
	        String msg2 = EnumChatFormatting.GRAY+">"+TextUtil.trans("techguns.updateCheck.click");
	        
	        ChatComponentText chatComponentMsg = new ChatComponentText(msg);
	        ChatComponentText chatComponentDownload = new ChatComponentText(msg2);

	        chatComponentDownload.setChatStyle(chatStyle);

	        event.player.addChatMessage(chatComponentMsg);
	        event.player.addChatMessage(chatComponentDownload);
		 
	        ClientProxy.get().versionChecker.warned=true;
		 }
	 }

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRenderTick(RenderTickEvent event) {
		//if (Techguns.player_zoom!=1.0f) {
		
		//EntityPlayer player = Techguns.proxy.getPlayerClient();
		ClientProxy cp = ClientProxy.get();
		EntityPlayer player = cp.getPlayerClient();
		if (player != null){
			ItemStack current_item = player.getEquipmentInSlot(0);
			
			if (current_item != null) {
	
				if(current_item.getItem() instanceof GenericGun){
					
					
					if ((((GenericGun) current_item.getItem())).isHoldZoom()){
						
						if (player.isSneaking()) {
	
							cp.player_zoom = ((GenericGun) current_item.getItem()).getZoomMult();
						} else {
							cp.player_zoom=1.0f;
						}
						
					} else {
						if(!((GenericGun)current_item.getItem()).isZooming()) {
							cp.player_zoom = 1.0f;
						}
					}			
					
				} else {
					cp.player_zoom=1.0f;
				}
			} else {
				cp.player_zoom=1.0f;
			}
		}
		//}
	}
	
	
	/**
	 * It's fired on the same bus as tick events, so it's in this class
	 * @param event
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL,receiveCanceled=false)
	public void onCraftingEvent(ItemCraftedEvent event){
		ItemStack stack = event.crafting;

		if (stack!=null && stack.getItem() instanceof GenericGun){
		
		//	GenericGun gun = (GenericGun) stack.getItem();
				
				ItemStack source = null;
				for (int i=0; i<event.craftMatrix.getSizeInventory(); i++){
					if(event.craftMatrix.getStackInSlot(i)!=null){
						source = event.craftMatrix.getStackInSlot(i);
					//	System.out.println("found sourceitem!");
						break;
					}
					
					
				}
				if ( source!=null && source.getItem() instanceof GenericGun){

					GenericGun akimboGun = (GenericGun) source.getItem();
					if (akimboGun.isAkimbo()){
			
						stack.stackSize=1;
						ItemStack gun2 = stack.copy();
						
						int ammoAkimbo = akimboGun.getCurrentAmmo(source);
						int ammoGun1 = ((GenericGun)stack.getItem()).getCurrentAmmo(stack);
						
						gun2.stackTagCompound.setShort("ammo", (short) (ammoAkimbo-ammoGun1));
						
						if (event.player!=null){
							int ret = Techguns.addItemToInventory(event.player.inventory.mainInventory, gun2, 0, event.player.inventory.mainInventory.length);
							if (ret>0 && !event.player.worldObj.isRemote){
								event.player.worldObj.spawnEntityInWorld(new EntityItem(event.player.worldObj, event.player.posX, event.player.posY,event.player.posZ, gun2));
							}
						}
						
						
					}					
					
				}
			}
		}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public void onEvent(PlayerLoggedOutEvent event){
		if(event.player.worldObj.isRemote){
			ClientProxy cp = ClientProxy.get();
			cp.removeEntityFromMaps(event.player);
			//System.out.println("Removed Player from maps because of Logout");
		}
	}
	
}
