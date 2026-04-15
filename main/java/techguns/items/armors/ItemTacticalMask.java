package techguns.items.armors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import techguns.TGArmorBonus;
import techguns.TGItems;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.items.TGSlotType;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ItemTacticalMask extends ItemTGSpecialSlotAmmo {

	public ItemTacticalMask(String unlocalizedName, String textureName, int camoCount, int dur) {
		super(unlocalizedName, textureName, camoCount, dur,TGItems.energyCell, TGItems.energyCellEmpty);
	}

	@Override
	public TGSlotType getSlot(ItemStack item) {
		return TGSlotType.FACESLOT;
	}

	@Override
	public void onPlayerTick(ItemStack item, PlayerTickEvent event, TechgunsExtendedPlayerProperties extendedPlayer) {
		if(item.getItemDamage()<item.getMaxDamage()){
			if(extendedPlayer.enableNightVision){
				item.setItemDamage(item.getItemDamage()+1);
			}
		} else {
			
			/*if (Techguns.consumeAmmoPlayer(event.player, TGItems.newStack(TGItems.energyCell,1))){
				item.setItemDamage(0);
			}*/
			this.tryReloadAndRepair(item, event.player);
		}
		
		PotionEffect poison=event.player.getActivePotionEffect(Potion.poison);
		PotionEffect wither=event.player.getActivePotionEffect(Potion.wither);
		
		if (poison !=null && item.getItemDamage()<item.getMaxDamage()){
			event.player.removePotionEffect(Potion.poison.id);			
			item.setItemDamage(item.getItemDamage()+1);
		}
		if (wither !=null && item.getItemDamage()<item.getMaxDamage()){
			event.player.removePotionEffect(Potion.wither.id);			
			item.setItemDamage(item.getItemDamage()+1);
		}
	}
	
	@Override
	public float getBonus(TGArmorBonus type,ItemStack stack, boolean consume, EntityPlayer player){
		if(type==TGArmorBonus.NIGHTVISION || type == TGArmorBonus.OXYGEN_GEAR){
			if (stack.getItemDamage()< stack.getMaxDamage()){
				return 1.0f;
			}
		}
		return 0.0f;
	}
}
