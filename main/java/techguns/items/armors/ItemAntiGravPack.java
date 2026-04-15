package techguns.items.armors;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import techguns.TGArmorBonus;
import techguns.TGItems;
import techguns.Techguns;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.items.TGSlotType;
import techguns.util.TextUtil;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ItemAntiGravPack extends ItemTGSpecialSlotAmmo {
	
	private static final int CONSUMPTION_PER_TICK =1;
	
	private static final float FLYBONUS=0.25f;
	
	public ItemAntiGravPack(String unlocalizedName, String textureName, int camoCount,int dur) {
		super(unlocalizedName, textureName, camoCount,dur,TGItems.nuclearPowerCell,TGItems.nuclearPowerCellDepleted );
	}

	@Override
	public TGSlotType getSlot(ItemStack item) {
		return TGSlotType.BACKSLOT;
	}

	@Override
	public void onPlayerTick(ItemStack item, PlayerTickEvent player, TechgunsExtendedPlayerProperties extendedPlayer) {
		if (item.getItemDamage()+1>= item.getMaxDamage()){
			this.tryReloadAndRepair(item, player.player);
		}
		if (player.player.capabilities.isFlying && !player.player.capabilities.isCreativeMode){
			if (item.getItemDamage()<item.getMaxDamage()){
				item.setItemDamage(item.getItemDamage()+1);
				//this.doEffect(player.player);
			}
		}
	}

	@Override
	public float getBonus(TGArmorBonus type, ItemStack stack, boolean consume, EntityPlayer player) {
		if (type==TGArmorBonus.CREATIVE_FLIGHT){
			if (stack.getItemDamage()<stack.getMaxDamage()){
				return 1;
			}
		} else if (type==TGArmorBonus.FLYSPEED){
			if (stack.getItemDamage()<stack.getMaxDamage()){
				return FLYBONUS;
			}			
		}
		return 0;
	}

	protected void doEffect(EntityPlayer ply){
		if (ply.worldObj.isRemote){
			//TargetPoint targetPoint = new TargetPoint(ply.worldObj.provider.dimensionId, ply.posX, ply.posY, ply.posZ, 50.0f);
			
			//TGPackets.network.sendToAllAround(new PacketSpawnParticleOnEntity("AntiGravRingGreen",ply), targetPoint);
			Techguns.proxy.createFXOnEntity("AntiGravRingGreen",ply);
		}
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean b) {
		super.addInformation(item, player, list, b);
		list.add(TextUtil.trans("techguns.armorTooltip.creativeFlight"));
		list.add(TextUtil.trans("techguns.armorTooltip.flyspeed")+": +"+FLYBONUS*100.0f+"%");
	}

	
}
