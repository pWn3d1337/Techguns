package techguns.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;

public class GenericItemBucket extends ItemBucket {

	public GenericItemBucket(Block b) {
		super(b);
	}

	@Override
	public String getUnlocalizedName(ItemStack s) {
		return "techguns."+super.getUnlocalizedName(s);
	}

	
}
