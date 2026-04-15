package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import techguns.TGArmorBonus;
import techguns.TGConfig;
import techguns.TGPackets;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.items.armors.GenericArmor;
import techguns.items.guns.GenericGun;
import techguns.keybinds.TGKeybindsID;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketTGKeybindPress implements IMessage {
	public byte buttonID;

	
	public PacketTGKeybindPress(){
	};
	
	public PacketTGKeybindPress(byte buttonID) {
		super();
		this.buttonID = buttonID;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		buttonID=buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(buttonID);
	}

	public static class Handler implements  IMessageHandler<PacketTGKeybindPress, IMessage> {

		@Override
		public IMessage onMessage(PacketTGKeybindPress message, MessageContext ctx) {
			EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
			
			TechgunsExtendedPlayerProperties props= TechgunsExtendedPlayerProperties.get(ply);
			
			if (message.buttonID==TGKeybindsID.TOGGLE_NIGHTVISION){
				
				props.enableNightVision=!props.enableNightVision;
				if(!props.enableNightVision){
					if(ply.getActivePotionEffect(Potion.nightVision)!=null){
						ply.removePotionEffect(Potion.nightVision.id);
						ply.worldObj.playSoundAtEntity(ply, "techguns:nightvision.off", 1.0f, 1.0f);
					}
				} else if (GenericArmor.getArmorBonusForPlayer(ply, TGArmorBonus.NIGHTVISION, false)>0.0f){
					ply.worldObj.playSoundAtEntity(ply, "techguns:nightvision.on", 1.0f, 1.0f);
				}
				TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(ply,props, true), (EntityPlayerMP)ply);
				
			} 
			else if (message.buttonID==TGKeybindsID.TOGGLE_SAFEMODE){
				
				props.enableSafemode=!props.enableSafemode;
				
				if (!props.enableSafemode && !TGConfig.allowUnsafemode(ply)){
					props.enableSafemode=true;
				} 
				TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(ply,props, true), (EntityPlayerMP)ply);
			}	
			else if (message.buttonID==TGKeybindsID.TOGGLE_JETPACK){
								
				if (!props.isJumpkeyPressed()){
					props.enableJetpack=!props.enableJetpack;
					TGPackets.network.sendTo(new PacketTGPlayerFieldSync(ply,PacketTGPlayerFieldSync.FIELD_ENABLEJETPACK, props.enableJetpack), (EntityPlayerMP)ply);
					
					if (!props.enableJetpack && props.isJumpkeyPressed()){
						props.setJumpkeyPressed(false);
						TGPackets.network.sendToDimension(new PacketTGPlayerFieldSync(ply, PacketTGPlayerFieldSync.FIELD_JUMPBUTTONPRESSED, false), ply.worldObj.provider.dimensionId);
					}
				}
				
			}	
			else if (message.buttonID==TGKeybindsID.JETPACK_BOOST_START){
				
				props.setJumpkeyPressed(true);
				/*ItemStack stack = props.TG_inventory.inventory[TGPlayerInventory.SLOT_BACK];
				if(stack!=null && stack.getItem() instanceof ItemJetpack){
					((ItemJetpack)stack.getItem()).setBoosting(stack, true);					
				}*/
				
				//send change to other player
				TGPackets.network.sendToDimension(new PacketTGPlayerFieldSync(ply, PacketTGPlayerFieldSync.FIELD_JUMPBUTTONPRESSED, true), ply.worldObj.provider.dimensionId);
				
				
			}
			else if (message.buttonID==TGKeybindsID.JETPACK_BOOST_STOP){
				
				props.setJumpkeyPressed(false);
				/*ItemStack stack = props.TG_inventory.inventory[TGPlayerInventory.SLOT_BACK];
				if(stack!=null && stack.getItem() instanceof ItemJetpack){
					((ItemJetpack)stack.getItem()).setBoosting(stack, false);					
				}*/
				TGPackets.network.sendToDimension(new PacketTGPlayerFieldSync(ply, PacketTGPlayerFieldSync.FIELD_JUMPBUTTONPRESSED, false), ply.worldObj.provider.dimensionId);
				
			}
			else if (message.buttonID==TGKeybindsID.JETPACK_FORWARD_START){
				
				props.isForwardKeyPressed=true;
				
			}
			else if (message.buttonID==TGKeybindsID.JETPACK_FORWARD_STOP){
				
				props.isForwardKeyPressed=false;
				
			}
			else if (message.buttonID ==TGKeybindsID.FORCE_RELOAD){
				
				ItemStack item = ply.getCurrentEquippedItem();
				if (item!=null && item.getItem() instanceof GenericGun){
					
					GenericGun gun = (GenericGun) item.getItem();
					gun.tryForcedReload(item, ply.worldObj, ply);
					
				}
				
				
			} else if (message.buttonID == TGKeybindsID.TOGGLE_HUD){
				props.showTGHudElements=!props.showTGHudElements;
				
				TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(ply,props, true), (EntityPlayerMP)ply);
				
			} else if (message.buttonID == TGKeybindsID.TOGGLE_STEP_ASSIST){
				props.enableStepAssist=!props.enableStepAssist;
				
				TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(ply,props, true), (EntityPlayerMP)ply);
			}
			
			return null;
		}
		
	}
}
