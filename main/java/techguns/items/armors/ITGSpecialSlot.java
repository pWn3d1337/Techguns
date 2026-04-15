package techguns.items.armors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import techguns.TGArmorBonus;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.items.TGSlotType;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public interface ITGSpecialSlot {
	public TGSlotType getSlot(ItemStack item);
	public void onPlayerTick(ItemStack item, PlayerTickEvent player, TechgunsExtendedPlayerProperties extendedPlayer);
	public float getBonus(TGArmorBonus type, ItemStack stack, boolean consume, EntityPlayer player);
}
