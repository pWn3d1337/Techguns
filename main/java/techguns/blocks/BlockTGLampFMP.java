package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
/**
 * After Tutorial:
 * http://whov.altervista.org/sided-multiparts/
 *
 */
public class BlockTGLampFMP extends GenericBlock {

	public BlockTGLampFMP() {
		super(Material.glass);
    	setHardness(0.25f);
		setBlockName("Lamp");
		setStepSound(soundTypeGlass);
		setHarvestLevel("pickaxe", 1);
		setBlockTextureName("techguns:lamp01");
		setLightLevel(1.0f);
		setLightOpacity(0);
		//setBlockBounds(0.2f, 0f, 0.2f, 0.8f, 0.2f, 0.8f);
	}

	 /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata_of_the_item) {
     return side;//this sets the metadata of the block to side
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
     return world.isSideSolid(x - 1, y, z, ForgeDirection.EAST ) ||
      world.isSideSolid(x + 1, y, z, ForgeDirection.WEST ) ||
      world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH) ||
      world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH) ||
      world.isSideSolid(x, y - 1, z, ForgeDirection.UP   ) ||
      world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN );
    }
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
     //make it drop if support block is removed
     if (!this.canPlaceBlockAt(world, x, y, z)) {
      this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
      world.setBlockToAir(x, y, z);
      return;
     } else {
      int l = world.getBlockMetadata(x, y, z);
      if (!canPlaceBlockOnSide(world, x, y, z, l)) {
       this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
       world.setBlockToAir(x, y, z);
      }
     }
    }

    //and you probably want to set the block bounds based on the side it is attached to
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
     int l = world.getBlockMetadata(x, y, z);
     if         (l == 0) this.setBlockBounds(0.3F, 0.9F, 0.3F, 0.7F, 1F, 0.7F);
     else if (l == 1) this.setBlockBounds(0.3F, 0F, 0.3F, 0.7F, 0.1F, 0.7F);
     else if (l == 2) this.setBlockBounds(0.3F, 0.3F, 0.9F, 0.7F, 0.7F, 1F);
     else if (l == 3) this.setBlockBounds(0.3F, 0.3F, 0.0F, 0.7F, 0.7F, 0.1F);
     else if (l == 4) this.setBlockBounds(0.9F, 0.3F, 0.3F, 1.0F, 0.7F, 0.7F);
     else if (l == 5) this.setBlockBounds(0.0F, 0.3F, 0.3F, 0.1F, 0.7F, 0.7F);
    }
    
    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    /*public boolean renderAsNormalBlock()
    {
        return false;
    }*/

    /**
     * The type of render function that is called for this block
     */
    /*public int getRenderType()
    {
    	return this.renderType; //8
    }*/
}
