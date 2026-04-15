package techguns.events;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import techguns.TGArmorBonus;
import techguns.TGConfig;
import techguns.TGFluids;
import techguns.TGItems;
import techguns.TGPackets;
import techguns.TGuns;
import techguns.Techguns;
import techguns.client.ClientProxy;
import techguns.client.renderer.additionalslots.RenderAdditionalSlotItem;
import techguns.client.renderer.additionalslots.RenderAkimboGun;
import techguns.client.renderer.death.RendererDeadLivingEntity;
import techguns.damagesystem.DamageSystem;
import techguns.damagesystem.INpcTGDamageSystem;
import techguns.damagesystem.IToolTGDamageSystem;
import techguns.damagesystem.TGDamageSource;
import techguns.entities.npc.GenericNPC;
import techguns.entities.npc.INPCTechgunsShooter;
import techguns.entities.npc.NPCTurret;
import techguns.entities.npc.TGDummySpawn;
import techguns.entities.spawn.TGSpawnManager;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.gui.playerinventory.TGGuiTabButton;
import techguns.items.armors.GenericArmor;
import techguns.items.guns.GenericGun;
import techguns.packets.PacketEntityDeathType;
import techguns.packets.PacketRequestTGPlayerSync;
import techguns.packets.PacketTGExtendedPlayerSync;
import techguns.util.EntityDeathUtils;
import techguns.util.EntityDeathUtils.DeathType;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TechgunsEventhandler {
	//@SideOnly(Side.CLIENT)
	//private ResourceLocation player_inv_texture = new ResourceLocation("techguns:textures/gui/TGPlayerInventory.png");
	
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void handleFovEvent(FOVUpdateEvent event){
		//handle fov for movespeed bonus
		//float speedBonus = GenericArmor.getArmorBonusForPlayer(event.entity, TGArmorBonus.SPEED);
		//float speedFOV = 1.0f;//speedBonus >0 ? 1/speedBonus : 1.0f;
		
		//float speedFOV=1.0f;
		//if (speedBonus >0){

		float f = 1.0f;
		
		if ( TGConfig.cl_lockSpeedFov){
			IAttributeInstance iattributeinstance = event.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        	f = 1/ ((float)(((iattributeinstance.getAttributeValue() / (double)event.entity.capabilities.getWalkSpeed() + 1.0D) / 2.0D)));
        	
        	if(ClientProxy.get().getPlayerClient().isSprinting()){
        		f*=1.2f;
        	}
        	
		}
		//}
		//System.out.println("SpeedFOV:"+f);
		
		event.newfov = event.newfov*ClientProxy.get().player_zoom*f;//*speedFOV;
	}
	


	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public void onEvent(LivingJumpEvent event)
	{
	    if (event.entity instanceof EntityPlayer)
	    {
	        EntityPlayer ply = (EntityPlayer) event.entity;
	        float jumpbonus = GenericArmor.getArmorBonusForPlayer(ply, TGArmorBonus.JUMP,true);
	        
	        /*if (ply.onGround && ply.isSneaking()){
	        	jumpbonus*=5;
	        }*/
	        
	        ply.motionY+=jumpbonus;
	    }  
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public void onEvent(LivingFallEvent event)
	{
	    if (event.entity instanceof EntityPlayer)
	    {
	    	//System.out.println("HEIGHT:"+event.distance);
	    	
	    	boolean consume= event.distance>3.0f;
	    	
	        EntityPlayer ply = (EntityPlayer) event.entity;
	        float fallbonus = GenericArmor.getArmorBonusForPlayer(ply, TGArmorBonus.FALLDMG,consume);
	        float reduction = (fallbonus <1 ? 1-fallbonus : 0.0f);
	        float freeheight = GenericArmor.getArmorBonusForPlayer(ply, TGArmorBonus.FREEHEIGHT,false);
	        if(freeheight<event.distance){
	        	event.distance-=freeheight;
	        } else {
	        	event.distance=0.0f;
	        }
	        
	        event.distance*=reduction;
	    }  
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=false)
	public void stopGunHarvesting(BreakSpeed event){
		ItemStack item = event.entityPlayer.getCurrentEquippedItem();
		if (item!=null && item.getItem() instanceof GenericGun){
			GenericGun gun = (GenericGun) item.getItem();
			if (gun.isShootWithLeftClick()){
				event.newSpeed = -1.0f;
				event.setCanceled(true);
			}
		}
		
		//speed.newSpeed = speed.originalSpeed*bonus*waterbonus;
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public void onEvent(BreakSpeed speed){
		float bonus = 1.0f+GenericArmor.getArmorBonusForPlayer(speed.entityPlayer, TGArmorBonus.BREAKSPEED,true);
		float waterbonus=1.0f;
		if(speed.entityPlayer.isInsideOfMaterial(Material.water)  || speed.entityPlayer.isInsideOfMaterial(Material.lava)){
			waterbonus += GenericArmor.getArmorBonusForPlayer(speed.entityPlayer, TGArmorBonus.BREAKSPEED_WATER,true);
		}
		
		//new speed is initialized with original speed
		speed.newSpeed = speed.newSpeed*bonus*waterbonus;
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public void onEvent(ChunkEvent.Unload event){
		if (event.world.isRemote){
			ClientProxy cp = ClientProxy.get();
			int len =event.getChunk().entityLists.length;
			for (int i=0;i<len;i++){
				List list = event.getChunk().entityLists[i];
				if (list != null){
					
					for (int j=0;j<list.size();j++){
						Object o =list.get(j);
						if (o instanceof EntityLivingBase){
							cp.removeEntityFromMaps((EntityLivingBase) o);
							//System.out.println("Removed Entity from maps because of Unload:"+o.getClass().getName()+":"+o.toString());
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event){
		NBTTagCompound tags = new NBTTagCompound();
		TechgunsExtendedPlayerProperties.get(event.original).saveNBTData(tags);
		TechgunsExtendedPlayerProperties.get(event.entityPlayer).loadNBTData(tags);
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void handleEntityRenderEvent(RenderLivingEvent.Pre event) {
		ClientProxy cp = ClientProxy.get();
		if (cp.hasDeathType(event.entity) ) {
			DeathType dt = cp.getEntityDeathType(event.entity);
			switch (dt) {
			case GORE:
				event.setCanceled(true);
				break;
			case DISMEMBER:
			case BIO:
			case LASER:
				//System.out.println("BIODEATH!1");
				event.setCanceled(true);
				RendererDeadLivingEntity.doRender(event.renderer, event.entity, event.x, event.y, event.z, 0f, dt);
				break;
			case DEFAULT:
			default:
				break;
			}
			if (event.entity.deathTime == 0) {
				cp.clearEntityDeathType(event.entity);
			}
		}
		
		if(event.entity instanceof INPCTechgunsShooter && event.renderer instanceof RenderBiped){
			ItemStack stack=event.entity.getHeldItem();
			if  (stack!=null && stack.getItem() instanceof GenericGun){
				
				RenderBiped r = (RenderBiped) event.renderer;
				if(event.entity instanceof GenericNPC){
					r.modelBipedMain.aimedBow=((GenericNPC)event.entity).getHasAimedBowAnim();
				} else {
					r.modelBipedMain.aimedBow = true;
				}

			}
		}
		
		/*if(event.renderer instanceof RenderPlayer){
			
			ItemStack stack=event.entity.getHeldItem();
			if  (stack!=null && stack.getItem() instanceof GenericGun){
				GenericGun gun = (GenericGun) stack.getItem();
				if (gun.isAkimbo()) {
					RenderAkimboGun.renderSecondGun(event.entity, gun, stack, ((RenderPlayer)event.renderer).modelBipedMain);
				}
			}
		}*/
		
		//event.setCanceled(true);
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public void onPlayerDrops(PlayerDropsEvent event){
		EntityPlayer ply = event.entityPlayer;
		TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(ply);
		
		if(props!=null){
			
			ply.captureDrops=true;
			
			props.dropInventory();
			
			ply.captureDrops=false;
			
			
		}
		
		
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public void onLivingDeathEvent(LivingDeathEvent event){
		if(event.entityLiving.worldObj.isRemote){
			if (event.entityLiving instanceof GenericNPC || event.entityLiving instanceof NPCTurret){
				ClientProxy cp = ClientProxy.get();
				cp.removeEntityFromMaps(event.entityLiving);
			}
			//System.out.println("Removed Entity from maps because of Death");					
		} else {
			if(event.entityLiving instanceof EntityPlayer){
				TechgunsExtendedPlayerProperties tgplayer = TechgunsExtendedPlayerProperties.get((EntityPlayer) event.entityLiving);
				//tgplayer.dropInventory();
				tgplayer.foodleft=0;
				tgplayer.lastSaturation=0;
			}
			
			if (event.source instanceof TGDamageSource) {
				TGDamageSource tgs = (TGDamageSource)event.source;
				if (tgs.deathType != DeathType.DEFAULT) {
					if (EntityDeathUtils.hasSpecialDeathAnim(event.entityLiving,  tgs.deathType)) {
						//Entity entity = event.entityLiving;
						//System.out.printf("(server)EntityMotion: (%.1f/%.1f/%.1f)\n",entity.motionX,entity.motionY,entity.motionZ);
						//Notify clients
						TGPackets.network.sendToAllAround(new PacketEntityDeathType(event.entityLiving, tgs.deathType),
								new TargetPoint(event.entityLiving.dimension, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, 100.0f));
						
						//ServerSideDeathTypeCode
					/*	switch (tgs.deathType) {
						case BIO:
							event.entityLiving.worldObj.playSoundEffect(event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, "techguns:effects.biodeath", 1.0F, 1.0f);
							break;
						case DEFAULT:
							break;
						case GORE:
							event.entityLiving.worldObj.playSoundEffect(event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, "techguns:effects.gore", 1.0F, 1.0f);
							break;
						case LASER:
							event.entityLiving.worldObj.playSoundEffect(event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, "techguns:effects.disintegrate", 1.0F, 1.0f);
							break;
						default:
							break;
						
						}*/
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		if(event.entity instanceof EntityPlayer){
			TechgunsExtendedPlayerProperties properties = TechgunsExtendedPlayerProperties.get((EntityPlayer) event.entity);
			if (properties == null){
				properties = Techguns.proxy.registerExtendedProperties((EntityPlayer) event.entity);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public void onGuiPostInit(GuiScreenEvent.InitGuiEvent.Post event){
		if (!Techguns.isTConstructLoaded && event.gui instanceof GuiInventory){
		    int xSize = 176;
		    int ySize = 166;
			
		    int k = (event.gui.width - xSize) / 2;
	        int l = (event.gui.height - ySize) / 2;
			
	        //TODO: get this from config file
	        int index = 2;
	        event.buttonList.add(new TGGuiTabButton(index, k-26, l+5, false,new ItemStack(Blocks.crafting_table,1),0));
	        event.buttonList.add(new TGGuiTabButton(index, k-26, l+5+26, true,TGItems.newStack(TGItems.bullets9mm,1),TGConfig.GUI_ID_tgplayerInventory));
			
		}
	}
	
	/*@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public void onGuiPreDraw(GuiScreenEvent.DrawScreenEvent.Pre event){
		if (event.gui instanceof GuiInventory){
		    int xSize = 176;
		    int ySize = 166;
	        
	        int k = (event.gui.width - xSize) / 2;
	        int l = (event.gui.height - ySize) / 2;
			
	        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	        event.gui.mc.getTextureManager().bindTexture(player_inv_texture);
	        event.gui.drawTexturedModalRect(k-20-4, l+6, 177, 0, 24, 26);
	        event.gui.drawTexturedModalRect(k-20-4, l+6+26, 177, 26, 24, 26);
			
		}
	}*/
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public void renderAkimboSecondGun(RenderPlayerEvent.Specials.Pre event){
		//if (event.slot==1){
			
			ItemStack stack =event.entityPlayer.getHeldItem();
		 	if(stack!=null && stack.getItem() instanceof GenericGun){

		 		GenericGun gun = (GenericGun) stack.getItem();
		 		if (gun.isAkimbo()){
		 			
		 			RenderAdditionalSlotItem render = RenderAdditionalSlotItem.getRenderForItem(stack);
					if (render!=null){
						//System.out.println("Render2ndGun!");
						render.render(stack,event.entityPlayer,event.renderer,event.partialRenderTick, true);
					}
		 			
		 		}
		 		
		 		
		 	}
			
		//}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public void onRenderPlayerEventPre(RenderPlayerEvent.Pre event){
	 	ItemStack stack =event.entityPlayer.getHeldItem();
	 	if(stack!=null && stack.getItem() instanceof GenericGun){
	 		event.renderer.modelArmorChestplate.aimedBow = true;
	 		event.renderer.modelArmor.aimedBow =true;
	 		event.renderer.modelBipedMain.aimedBow = true;
	 		
	 		/*GenericGun gun = (GenericGun) stack.getItem();
	 		if (gun.isAkimbo()){
	 			
	 			RenderAdditionalSlotItem render = RenderAdditionalSlotItem.getRenderForItem(stack);
				if (render!=null){
					System.out.println("Render2ndGun!");
					render.render(stack,event.entityPlayer,event.renderer,event.partialRenderTick, true);
				}
	 			
	 			
	 		}*/
	 		
	 		
	 	}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public void onRenderArmorEvent(RenderPlayerEvent.SetArmorModel event){
		//System.out.println("RenderingPlayer:"+event.entityPlayer.getGameProfile().getName());
		//event.entityPlayer
		//event.
		//TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(event.entityPlayer);
		//if(props != null){
			//ItemStack facegear = props.TG_inventory.inventory[TGPlayerInventory.SLOT_FACE];
		
		if(event.slot==0){
			ItemStack facegear = event.entityPlayer.getDataWatcher().getWatchableObjectItemStack(TechgunsExtendedPlayerProperties.DATA_WATCHER_ID_FACESLOT);
			boolean showFaceslot=true;

			if(facegear!=null){
				
				//System.out.println("Player has a facegear");
				
				if(event.entityPlayer.inventory.armorInventory[3]!=null){
					if (event.entityPlayer.inventory.armorInventory[3].getItem() instanceof GenericArmor){
						GenericArmor armor=(GenericArmor) event.entityPlayer.inventory.armorInventory[3].getItem();
						showFaceslot = !armor.isHideFaceslot();
					}
				}
				
				RenderAdditionalSlotItem render = RenderAdditionalSlotItem.getRenderForItem(facegear);
				if (render!=null){
					render.render(facegear,event.entityPlayer,event.renderer,event.partialRenderTick, showFaceslot);
				}
			}
		} else if (event.slot==1){
			
			boolean showBackback=true;
			//ItemStack backgear = props.TG_inventory.inventory[TGPlayerInventory.SLOT_BACK];
			ItemStack backgear = event.entityPlayer.getDataWatcher().getWatchableObjectItemStack(TechgunsExtendedPlayerProperties.DATA_WATCHER_ID_BACKSLOT);
			
			if(backgear!=null){
				//System.out.println("Player has a backgear");
				
				if(event.entityPlayer.inventory.armorInventory[2]!=null){
					if (event.entityPlayer.inventory.armorInventory[2].getItem() instanceof GenericArmor){
						GenericArmor armor=(GenericArmor) event.entityPlayer.inventory.armorInventory[2].getItem();
						showBackback = !armor.isHideBackslot();
					}
				}
				
				RenderAdditionalSlotItem render = RenderAdditionalSlotItem.getRenderForItem(backgear);
				if (render!=null){
					render.render(backgear,event.entityPlayer,event.renderer, event.partialRenderTick, showBackback);
				}
			}
		}
		//}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public void onEntityJoinWorld(EntityJoinWorldEvent event){
		if (!event.entity.worldObj.isRemote){
			if(event.entity instanceof EntityPlayer){
				EntityPlayer ply = (EntityPlayer) event.entity;
				TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(ply);
				if (props!=null){
					//TGPackets.network.sendToDimension(new PacketTGExtendedPlayerSync(props, false), event.entity.dimension);
					TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(ply,props, true), (EntityPlayerMP) ply);
					//ply.getDataWatcher().updateObject(TechgunsExtendedPlayerProperties.DATA_WATCHER_ID_FACESLOT, props.TG_inventory.inventory[TGPlayerInventory.SLOT_FACE]);
					//ply.getDataWatcher().updateObject(TechgunsExtendedPlayerProperties.DATA_WATCHER_ID_BACKSLOT, props.TG_inventory.inventory[TGPlayerInventory.SLOT_BACK]);
				
				}
			} else if (event.entity instanceof TGDummySpawn){
				//
				
				TGSpawnManager.handleSpawn(event.world, event.entity);
				event.setCanceled(true);
				
			}
		}
	}
	
	/*@SubscribeEvent(priority=EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent event){
		ItemStack stack = event.entityPlayer.getCurrentEquippedItem();
		if(stack !=null && stack.getItem() instanceof GenericGun){
			if(event.action==Action.LEFT_CLICK_BLOCK){
				event.setCanceled(true);
			}
		}
	}*/
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.HIGH)
	public void onMouseEvent(MouseEvent event){
		EntityClientPlayerMP ply = Minecraft.getMinecraft().thePlayer;
		if(event.button==0 && Minecraft.getMinecraft().inGameHasFocus){
			//System.out.println("MOUSE EVENT LMB");
			
			ItemStack stack=ply.getCurrentEquippedItem();
			if(stack!=null && stack.getItem() instanceof GenericGun){
				ClientProxy cp = ClientProxy.get();
				
				if(((GenericGun)stack.getItem()).isShootWithLeftClick()){
					//System.out.println("KEY FIRE ="+event.buttonstate);
					cp.keyFirePressed=event.buttonstate;
					event.setCanceled(true);
					
					//can't mine/attack while reloading
				} else if(cp.getplayerReloadtime(ClientProxy.get().getPlayerClient()) >0) {
				  //System.out.println("IT'S A MINING TOOL");
					long diff = cp.getplayerReloadtime(ClientProxy.get().getPlayerClient()) - System.currentTimeMillis();
					if(diff>0){
						if (event.buttonstate==true){
							//System.out.println("CANCEL FIRE WHILE RELOADING MINING TOOL");
							event.setCanceled(true);
						}
					}
					
				}
				
			}
		}
		//System.out.println("Button:"+event.button);
		//System.out.println("State:"+event.buttonstate);
		
		
	}
	
	/*@SubscribeEvent
	public void onLavaBurnDamage(LivingHurtEvent event){
		EntityLivingBase elb = event.entityLiving;
		DamageSource src = event.source;
		if (elb instanceof EntityPlayer && (src.equals(DamageSource.lava)||src.equals(DamageSource.inFire)||src.equals(DamageSource.onFire))){
			EntityPlayer ply = (EntityPlayer) elb;	
			
			if (GenericArmor.getArmorBonusForPlayer(ply, TGArmorBonus.COOLING_SYSTEM, true)>=1.0f){
				event.setCanceled(true);
			}
			
		}
	}*/
	
	@SubscribeEvent(priority=EventPriority.HIGH, receiveCanceled=false)
	public void OnLivingAttack(LivingAttackEvent event){
		
		EntityLivingBase elb = event.entityLiving;
		
		DamageSource src = event.source;
		
		Entity attacker =src.getSourceOfDamage();
			if (attacker instanceof EntityLivingBase ){
			
			ItemStack currentItem = ((EntityLivingBase)attacker).getEquipmentInSlot(0);
			if(currentItem!=null && currentItem.getItem()instanceof IToolTGDamageSystem){
				src = ((IToolTGDamageSystem)currentItem.getItem()).getDamageSource(event.source);
			}
		}
		
		if (elb instanceof EntityPlayer && (src.equals(DamageSource.lava)||src.equals(DamageSource.inFire)||src.equals(DamageSource.onFire))){
			EntityPlayer ply = (EntityPlayer) elb;	
			
			if (GenericArmor.getArmorBonusForPlayer(ply, TGArmorBonus.COOLING_SYSTEM, true)>=1.0f){
				event.setCanceled(true);
			}
			
		} else if(src instanceof TGDamageSource || event.entityLiving instanceof INpcTGDamageSystem){
			
			event.setCanceled(true);
			if(event.entityLiving instanceof EntityPlayer){
				DamageSystem.onLivingAttackPlayer((EntityPlayer)event.entityLiving, src, event.ammount);
			} else {
				DamageSystem.onLivingAttackEntityLivingBase(event.entityLiving, src, event.ammount);
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL)
	public void onStartTracking(PlayerEvent.StartTracking event){
		if(event.entityPlayer.worldObj.isRemote){
			TGPackets.network.sendToServer(new PacketRequestTGPlayerSync(event.entityPlayer));			
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL)
	public void onStopTracking(PlayerEvent.StopTracking event){
		if(event.entityPlayer.worldObj.isRemote){
			ClientProxy cp = ClientProxy.get();
			cp.removeEntityFromMaps(event.entityPlayer);
			TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(event.entityPlayer);
			if(props!=null){
				props.setJumpkeyPressed(false);
				props.isGliding=false;
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL)
	@SideOnly(Side.CLIENT)
	public void onTextureStitch(TextureStitchEvent.Pre event) {
		if (event.map.getTextureType() == 0 && TGFluids.addedMilk) {

			IIcon flowingIcon=event.map.registerIcon("techguns:milk_flow");
			IIcon stillIcon=event.map.registerIcon("techguns:milk_still");
			
			TGFluids.MILK.setIcons(stillIcon, flowingIcon);
		}
	}

	
	/*@SubscribeEvent(priority=EventPriority.NORMAL)
	public void onBlockBreakTest(BlockEvent.PlaceEvent event) {
		//if (Math.random()<0.5f){
			event.setCanceled(true);
			System.out.println("BlockPlaceEvent:"+event.x+","+event.y+","+event.z+":"+event.player+" CANCELLED!");
		//}
	}*/
	
	/*@SubscribeEvent(priority=EventPriority.NORMAL)
	public void onBlockBreakTest(BlockEvent.BreakEvent event) {
		if (Math.random()<0.5f){
			event.setCanceled(true);
			System.out.println("BlockBreakEvent:"+event.x+","+event.y+","+event.z+":"+event.getPlayer()+" CANCELLED!");
		}
	}*/
	
	/*@SubscribeEvent(priority=EventPriority.NORMAL)
	public void onExplodeTest(ExplosionEvent.Start event) {
	//	event.setCanceled(true);
		System.out.println("ExplosionEvent:"+event.explosion.explosionX+","+event.explosion.explosionY+","+event.explosion.explosionZ+":"+event.explosion.getExplosivePlacedBy());
	}*/
	
	/*@SubscribeEvent(priority=EventPriority.NORMAL)
	public void onExplodeTest(ExplosionEvent.Detonate event) {
		System.out.println("ExplosionDetonateEvent:"+event.explosion.explosionX+","+event.explosion.explosionY+","+event.explosion.explosionZ+":"+event.explosion.getExplosivePlacedBy());
	}*/
	
	/*@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.LOWEST, receiveCanceled=false)
	public void onBlockBreak(BreakEvent event){
		System.out.println("Break Event"+event);

		if(event.block==TGBlocks.machineMultiBlock){
			event.world.markBlockForUpdate(event.x, event.y, event.z);
		}
	}*/
	
	
	/*@SubscribeEvent(priority=EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent event){
		//System.out.println("ACTION:"+event.action);
		if(event.action==Action.LEFT_CLICK_BLOCK){
			ItemStack item = event.entityPlayer.getCurrentEquippedItem();
			if (item !=null && item.getItem() instanceof GenericGun){
				GenericGun gun = (GenericGun) item.getItem();
				if (gun.isShootWithLeftClick()){
					event.useBlock = Result.DENY;
				}
			}
			
		}
	}*/
	
	/*@SubscribeEvent(priority=EventPriority.NORMAL)
	public void onFogColor(EntityViewRenderEvent.FogDensity event){
		if(event.entity instanceof EntityPlayer){
			EntityPlayer ply = (EntityPlayer) event.entity;
			//System.out.println("FOG: "+event.red+", "+event.green+", "+event.blue);
			
			if (ply.isInsideOfMaterial(Material.water)||ply.isInsideOfMaterial(Material.lava)){
				if (GenericArmor.getArmorBonusForPlayer(ply, TGArmorBonus.OXYGEN_GEAR,false)>=1.0f){
					System.out.println("UNDER WATER");
					
					if (ply.isInsideOfMaterial(Material.water)){
						event.density=0.05f;
					} else if (ply.isInsideOfMaterial(Material.lava)){
						event.density = 0.15f;
					}
					event.setCanceled(true);
				}
			}
			
		}
	}*/
	
	/*@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onLivingUpdateClient(LivingUpdateEvent event){
		if (event.entityLiving.worldObj.isRemote && event.entityLiving instanceof EntityPlayerSP){
			
			EntityPlayerSP player= (EntityPlayerSP) event.entityLiving;
			 //counter onItemUseSlowdown
			 if(player.isUsingItem() && player.getHeldItem()!=null){
				 ItemStack item = player.getHeldItem();
				 System.out.println("using item");
				 if(item.getItem() instanceof GenericGun){
					 player.movementInput.moveForward*=5.0f;
					 player.movementInput.moveStrafe*=5.0f;
					 System.out.println("speed him up");
				 }
				 
			 }
		 }
	}*/
	
	/*@SubscribeEvent(priority=EventPriority.NORMAL)
	public void onFogColor(EntityViewRenderEvent.FogColors event){
		if(event.entity instanceof EntityPlayer){
			EntityPlayer ply = (EntityPlayer) event.entity;
			TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(ply);
			if (props!=null){
				ItemStack face =props.TG_inventory.inventory[TGPlayerInventory.SLOT_FACE];
				if(face!=null && face.getItem() instanceof ItemGasMask){
					
					
					float f11 = 1.0f;
		            float f6 = 1.0F / event.red;

		            if (f6 > 1.0F / event.green)
		            {
		                f6 = 1.0F / event.green;
		            }

		            if (f6 > 1.0F / event.blue)
		            {
		                f6 = 1.0F / event.blue;
		            }

		            event.red = event.red * (1.0F - f11) + event.red * f6 * f11;
		            event.green = event.green * (1.0F - f11) + event.green * f6 * f11;
		            event.blue = event.blue * (1.0F - f11) + event.blue * f6 * f11;
					
					
				}
				
			}
		}
	}*/
}
