package techguns.items.armors;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import techguns.TGArmorBonus;
import techguns.TGItems;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.items.GenericItemShared;
import techguns.items.TGSlotType;
import techguns.keybinds.TGKeybindsID;
import techguns.packets.PacketTGKeybindPress;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ItemJetpack extends ItemTGSpecialSlotAmmo {
	
	public ItemJetpack(String unlocalizedName, String textureName, int camoCount,int dur) {
		super(unlocalizedName, textureName, camoCount,dur,TGItems.flamethrowerTank,TGItems.flamethrowerTankEmpty );
	}

	@Override
	public TGSlotType getSlot(ItemStack item) {
		return TGSlotType.BACKSLOT;
	}

	@Override
	public void onPlayerTick(ItemStack item, PlayerTickEvent event, TechgunsExtendedPlayerProperties extendedPlayer) {
		//System.out.println("JMPMvmntFctr:"+event.player.jumpMovementFactor);
		
		if(item.getItemDamage()>=item.getMaxDamage()){
			tryReloadAndRepair(item,event.player);
		}
		
		
		
		
		if(event.player.worldObj.isRemote){
			
			EntityPlayer self = Techguns.proxy.getPlayerClient();//Minecraft.getMinecraft().thePlayer;
			if(event.player==self){
				
				
				if (extendedPlayer.enableJetpack){
					boolean last_state = extendedPlayer.isJumpkeyPressed();
					boolean jump_pressed = Minecraft.getMinecraft().gameSettings.keyBindJump.getIsKeyPressed();
					
					if(jump_pressed && !last_state){
						//ClientProxy.jumpKeyPressed=true;
						extendedPlayer.setJumpkeyPressed(true);
						TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.JETPACK_BOOST_START));
					} else if (!jump_pressed && last_state){
						extendedPlayer.setJumpkeyPressed(false);
						TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.JETPACK_BOOST_STOP));
					}
				}	
					/*
					boolean forward_pressed = Minecraft.getMinecraft().gameSettings.keyBindForward.getIsKeyPressed();
					if(forward_pressed && !ClientProxy.forwardKeyPressed){
						ClientProxy.forwardKeyPressed=true;
						TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybinds.JETPACK_FORWARD_START));
					} else if (!forward_pressed && ClientProxy.forwardKeyPressed){
						ClientProxy.forwardKeyPressed=false;
						TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybinds.JETPACK_FORWARD_STOP));
					}*/
					
					
					if(item.getItemDamage()<item.getMaxDamage()){
						
						//if(!extendedPlayer.enableHovermode){
						
							if(extendedPlayer.isJumpkeyPressed()){
								applyBoostToPlayer(event.player,item);
							} /*else if(event.player.isSneaking()){
								hoverPlayer(event.player,item, ClientProxy.forwardKeyPressed);
							}*/
							
							//also act like glider
							GenericItemShared.onPlayerTickGlider(item, event, extendedPlayer);
							
						/*} else {
							
							boolean ascend = ClientProxy.jumpKeyPressed;
							boolean descend = event.player.isSneaking();
							
							if(ascend && !descend){
								applyBoostToPlayer(event.player, item);
							} else if (!ascend && descend){
								descendPlayer(event.player, item);
							} else {
								hoverPlayer(event.player, item, ClientProxy.forwardKeyPressed);
							}
							
						}*/
					} else {
						if(extendedPlayer.isJumpkeyPressed()){
							extendedPlayer.setJumpkeyPressed(false);
							TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.JETPACK_BOOST_STOP));
						}	
						/*if(ClientProxy.forwardKeyPressed){
							ClientProxy.forwardKeyPressed=false;
							TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybinds.JETPACK_FORWARD_STOP));
						}*/	
					}
				
				
				
			}
		} else {
			
			//sever side
			if(item.getItemDamage()<item.getMaxDamage()){
				
				//if (!extendedPlayer.enableHovermode){
					if(extendedPlayer.isJumpkeyPressed()){
						applyBoostToPlayer(event.player, item);
					}/* else if (event.player.isSneaking()){
						hoverPlayer(event.player,item,extendedPlayer.isForwardKeyPressed);
					}*/
					//also act like glider
					GenericItemShared.onPlayerTickGlider(item, event, extendedPlayer);
					
				/*} else {
					boolean ascend = extendedPlayer.isJumpkeyPressed;
					boolean descend = event.player.isSneaking();
					
					if(ascend && !descend){
						applyBoostToPlayer(event.player, item);
					} else if (!ascend && descend){
						descendPlayer(event.player, item);
					} else {
						hoverPlayer(event.player, item, extendedPlayer.isForwardKeyPressed);
					}
				}*/
			} 
			
		}
		
		
	}

	/*public void setBoosting(ItemStack stack, boolean state){
		NBTTagCompound tags = stack.getTagCompound();
		if(tags==null){
			tags = new NBTTagCompound();
			stack.setTagCompound(tags);
		}
		tags.setBoolean("boost", state);
	}
	
	public boolean getBoosting(ItemStack stack){
		NBTTagCompound tags = stack.getTagCompound();
		if(tags==null){
			tags = new NBTTagCompound();
			tags.setBoolean("boost", false);
			stack.setTagCompound(tags);
		}
		return tags.getBoolean("boost");
	}*/
	
	protected void applyBoostToPlayer(EntityPlayer ply, ItemStack item){
		ply.motionY=Math.min(ply.motionY+0.15f, 0.4f);	
		ply.fallDistance=0;
		item.setItemDamage(item.getItemDamage()+1);
	
		
		
		/*if(!ply.worldObj.isRemote){
			//System.out.println("PARTICLE");
			//ply.worldObj.spawnParticle("lava", ply.posX, ply.posY, ply.posZ, ply.motionX, -ply.motionY, ply.motionZ);
			ply.worldObj.spawnParticle("lava", ply.posX, ply.posY, ply.posZ, 0,0,0);
			System.out.println("Plypos:"+ply.posX+","+ply.posY+","+ply.posZ);
		}*/
		
	}
	
	protected void descendPlayer(EntityPlayer ply, ItemStack item){
		ply.motionY=Math.max(ply.motionY-0.15f, -0.4f);	
		ply.fallDistance=0;
		item.setItemDamage(item.getItemDamage()+1);
	
	}
	
	protected void hoverPlayer(EntityPlayer ply, ItemStack item, boolean forwardPressed){
		if (ply.motionY>0){
			ply.motionY = Math.max(0, ply.motionY-0.2f);
		} else {
			ply.motionY = Math.min(0, ply.motionY+0.2f);
		}
		ply.fallDistance=0;
			
		
		//apply a speed bonus
		
		//System.out.println("Move fwd:"+ply.moveForward);
		
		
		/*	Vec3 lookVec = ply.getLookVec();
			MathUtil.Vec2 playerMoveVec =new MathUtil.Vec2(lookVec.xCoord, lookVec.zCoord);
			
			double speed = playerMoveVec.len();
			
			if (speed>0) {
				//normalize, don't call normalize because we already got the length
				playerMoveVec.x /= speed;
				playerMoveVec.y /= speed;
				
				if(forwardPressed){
					speed = Math.max(0.4f, speed+0.025f);
				} else {
					speed = Math.min(0.0f, speed-0.025f);
				}
				
				ply.motionX=playerMoveVec.x*speed;
				ply.motionZ=playerMoveVec.y*speed;
				ply.velocityChanged=true;
			}*/
		
		item.setItemDamage(item.getItemDamage()+1);
	}	
	
	@Override
	public float getBonus(TGArmorBonus type,ItemStack stack, boolean consume, EntityPlayer player) {
		return 0;
	}

}
