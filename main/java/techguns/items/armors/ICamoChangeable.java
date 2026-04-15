package techguns.items.armors;

import net.minecraft.item.ItemStack;

public interface ICamoChangeable {
	public int getCamoCount();
	public int switchCamo(ItemStack item);
	public int switchCamo(ItemStack item, boolean back);
	public int getCurrentCamoIndex(ItemStack item);
	public String getCurrentCamoName(ItemStack item);
}
