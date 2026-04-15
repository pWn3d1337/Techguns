package techguns.items.armors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import techguns.TGArmorBonus;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.items.TGSlotType;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ItemGasMask extends ItemTGSpecialSlot {
	
	public ItemGasMask(String unlocalizedName, String textureName, int camoCount,int dur) {
		super(unlocalizedName, textureName, camoCount,dur);
	}

	@Override
	public TGSlotType getSlot(ItemStack item) {
		return TGSlotType.FACESLOT;
	}

	@Override
	public void onPlayerTick(ItemStack item, PlayerTickEvent event, TechgunsExtendedPlayerProperties extendedPlayer) {
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
	public float getBonus(TGArmorBonus type, ItemStack stack, boolean consume, EntityPlayer player){
		return 0.0f;
	}

}
