package techguns.items.armors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import techguns.TGArmorBonus;
import techguns.TGItems;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.items.TGSlotType;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ItemScubaTanks extends ItemTGSpecialSlotAmmo {
	
	public ItemScubaTanks(String unlocalizedName, String textureName, int camoCount,int dur) {
		super(unlocalizedName, textureName, camoCount,dur,TGItems.compressedAirTank, TGItems.compressedAirTankEmpty);

	}	
	
	
	@Override
	public TGSlotType getSlot(ItemStack item) {
		return TGSlotType.BACKSLOT;
	}

	@Override
	public void onPlayerTick(ItemStack item, PlayerTickEvent event, TechgunsExtendedPlayerProperties extendedPlayer) {
		//System.out.println("Player Oxygen:"+event.player.getAir());
		
		if(item.getItemDamage()>=item.getMaxDamage()){
			tryReloadAndRepair(item,event.player);
		}
		
		
		if(item.getItemDamage()<item.getMaxDamage()){
			if(event.player.getAir()<=280){
				if(GenericArmor.getArmorBonusForPlayer(event.player, TGArmorBonus.OXYGEN_GEAR, false)>0){
					event.player.setAir(event.player.getAir()+20);
					item.setItemDamage(item.getItemDamage()+1);
				}		
			}
		}
	}

	@Override
	public float getBonus(TGArmorBonus type,ItemStack stack, boolean consume, EntityPlayer player) {
		return 0;
	}

}
