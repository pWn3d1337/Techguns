package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import techguns.Techguns;

public class GenericBlock extends Block {

	int renderType = 0; 
	
	public GenericBlock(Material mat) {
		super(mat);
		
		setCreativeTab(Techguns.tabTechgun);
		setHardness(2.0f);
		setBlockName("Generic Block");
		setStepSound(soundTypeStone);
		setHarvestLevel("pickaxe", 1);		
	}	

	public GenericBlock setRenderType(int renderType) {
		this.renderType = renderType;
		return this;
	}
	
	@Override
	public int getRenderType() {
		return renderType;
	}
	
	/**
	 * returns the metadata for this block type based on it's current meta and a rotation offset
	 * @param rotationOffset number of clockwise rotations
	 * @return
	 */
	public int getRotationMeta(int metadata, int rotationOffset) {
		return metadata;
	}

	@Override
	public String getUnlocalizedName() {
		return "techguns."+super.getUnlocalizedName();
	}

	/*@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, z, z);
        return new ItemStack(block, 1, this.damageDropped(meta));
	}*/
	
	
	
}

