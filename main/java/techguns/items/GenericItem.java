package techguns.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import techguns.Techguns;

public class GenericItem extends Item {
	
	public GenericItem() {
		// Constructor Configuration
		setMaxStackSize(64);
		setCreativeTab(Techguns.tabTechgun);
		setUnlocalizedName("testItem");
		setTextureName("techguns:testItem");
	}
	
	public GenericItem(String name) {
		// Constructor Configuration
		setMaxStackSize(64);
		setCreativeTab(Techguns.tabTechgun);
		setUnlocalizedName(name);
		setTextureName("techguns:"+name);
	}

	@Override
	public String getUnlocalizedName() {
		return "techguns."+super.getUnlocalizedName();
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "techguns."+super.getUnlocalizedName(stack);
	}
	
	
}