package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class GenericItemBlockMetadata extends ItemBlockWithMetadata {

	public GenericItemBlockMetadata(Block block) {
		super(block,block);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
	    return super.getUnlocalizedName(stack) + "_" + stack.getItemDamage();
	}


	
	
}
