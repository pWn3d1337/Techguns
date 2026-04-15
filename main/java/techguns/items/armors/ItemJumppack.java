package techguns.items.armors;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import techguns.TGArmorBonus;
import techguns.TGItems;
import techguns.TGPackets;
import techguns.client.audio.TGSoundCategory;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.items.TGSlotType;
import techguns.packets.PacketPlaySound;
import techguns.packets.PacketSpawnParticle;
import techguns.packets.PacketSpawnParticleOnEntity;
import techguns.util.TextUtil;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class ItemJumppack extends ItemTGSpecialSlotAmmo{

	private static int durPerUse=1;
	
	private static final float JUMPBOOST=0.8f;
	private static final float FREEHEIGHT=8.0f;
	private static final float FALLDMG = 0.2f;
	
	public ItemJumppack(String unlocalizedName, String textureName, int camoCount, int dur) {
		super(unlocalizedName, textureName, camoCount, dur, TGItems.compressedAirTank, TGItems.compressedAirTankEmpty);
	}

	@Override
	public TGSlotType getSlot(ItemStack item) {
		return TGSlotType.BACKSLOT;
	}

	@Override
	public void onPlayerTick(ItemStack item, PlayerTickEvent player, TechgunsExtendedPlayerProperties extendedPlayer) {
		if ((item.getItemDamage()+this.durPerUse>item.getMaxDamage())){
			this.tryReloadAndRepair(item, player.player);
		}
	}

	@Override
	public float getBonus(TGArmorBonus type, ItemStack stack, boolean consume, EntityPlayer player) {	
		if (type==TGArmorBonus.JUMP){
						
			if (consume){
				TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(player);
					
				//System.out.println("PropsJP:"+props.enableJetpack+ " StackDMG:"+stack.getItemDamage());
				
				
				if (props.enableJetpack && (stack.getItemDamage()+this.durPerUse) <= stack.getMaxDamage()){
					//System.out.println("check1");
					stack.setItemDamage(stack.getItemDamage()+this.durPerUse);
					if ( !player.worldObj.isRemote){
					//	System.out.println("check2");
						this.doJumpEffect(player);
					}
					return JUMPBOOST;
				}
			} else {
				if ((stack.getItemDamage()+this.durPerUse) <= stack.getMaxDamage()){
					return JUMPBOOST;
				}
			}
		} else if (type==TGArmorBonus.FREEHEIGHT){
			if (stack.getItemDamage()<stack.getMaxDamage()){
				return FREEHEIGHT;
			}
		} else if (type==TGArmorBonus.FALLDMG){
			if (consume){
				
				TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(player);
					
				
				if (props.enableJetpack && (stack.getItemDamage()+this.durPerUse) <= stack.getMaxDamage()){
					stack.setItemDamage(stack.getItemDamage()+this.durPerUse);
					if ( !player.worldObj.isRemote){
						this.doFallEffect(player);
					}
					return FALLDMG;
				}
				
			} else {
				if (stack.getItemDamage()<stack.getMaxDamage()){
					return FALLDMG;
				}
			}
		}
		return 0;
	}
	
	public void doJumpEffect(EntityPlayer ply){
		//send out the effects
		if (!ply.worldObj.isRemote){
			TargetPoint targetPoint = new TargetPoint(ply.worldObj.provider.dimensionId, ply.posX, ply.posY, ply.posZ, 50.0f);
			
			TGPackets.network.sendToAllAround(new PacketSpawnParticleOnEntity("JumpPackBoost",ply), targetPoint);
			TGPackets.network.sendToAllAround(new PacketPlaySound("techguns:effects.steamarmorJump2", ply, 1.0f, 1.0f, false, true, false, true, TGSoundCategory.PLAYER_EFFECT), targetPoint);
		}
	}

	public void doFallEffect(EntityPlayer ply){
		if (!ply.worldObj.isRemote){
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("JumpPackFall",ply.posX, ply.posY, ply.posZ), new TargetPoint(ply.worldObj.provider.dimensionId, ply.posX, ply.posY, ply.posZ, 50.0f));
		}
	}
	
	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean b) {
		super.addInformation(item, player, list, b);
		list.add(TextUtil.trans("techguns.armorTooltip.jumpheight")+": +"+JUMPBOOST);
		list.add(TextUtil.trans("techguns.armorTooltip.falldamage")+": -"+FALLDMG*100.0f+"%");
		list.add(TextUtil.trans("techguns.armorTooltip.fallheight")+": -"+FREEHEIGHT);
	}

	
}
