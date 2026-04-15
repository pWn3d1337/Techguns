package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class GenericItemBlockMetadataStringPrefix extends GenericItemBlockMetadata {

	public GenericItemBlockMetadataStringPrefix(Block block) {
		super(block);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "techguns."+super.getUnlocalizedName(stack);
	}	
	
}
