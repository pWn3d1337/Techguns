package techguns.blocks;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTGLamp extends GenericBlock {

	public IIcon[] icons = new IIcon[6];
	
	public BlockTGLamp() {
		super(Material.iron);
    	setHardness(0.25f);
		setBlockName("Lamp");
		setStepSound(soundTypeGlass);
		//setHarvestLevel("pickaxe", 1);
		//setBlockTextureName("techguns:lamp01");
		setLightLevel(1.0f);
		setLightOpacity(0);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.icons[0] = reg.registerIcon("techguns:lamp01");
		this.icons[1] = reg.registerIcon("techguns:lamp02");
		this.icons[2] = reg.registerIcon("techguns:lantern01_side");
		this.icons[3] = reg.registerIcon("techguns:lantern02_side");
		this.icons[4] = reg.registerIcon("techguns:lantern01_top");
		this.icons[5] = reg.registerIcon("techguns:lantern02_top");
	}
	
	
	
	
	 @Override
	public int damageDropped(int meta) {
		if(meta<7){
			return 0;
		}else if (meta == 7) {
			return 7;
		} else if (meta < 15) {
			return 8;
		}else {
			return 15;
		}
	 }
	/**
     * Gets the block's texture. Args: side, meta
     * LAMP SOCKET
     */
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    { 	
    	if(meta<7){
			return icons[0];
		}else if (meta == 7) {
			return (side <= 1) ? icons[4] : icons[2];
		} else if (meta < 15) {
			return icons[1];
		}else {
			return (side <= 1) ? icons[5] : icons[3];
		}
    }
	
//	/**
//	 * LAMP light texture
//	 */
//    @SideOnly(Side.CLIENT)
//    public IIcon getLampIcon(int side, int meta)
//    {
//    	if (meta < 8) {
//    		return icons[1];
//    	}else {
//    		return icons[3];
//    	}
//    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab,
			List subItems) {
		subItems.add(new ItemStack(item, 1, 0));
		subItems.add(new ItemStack(item, 1, 8));
		subItems.add(new ItemStack(item, 1, 7));
		subItems.add(new ItemStack(item, 1, 15));
	}
	
	 /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        this.setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
        return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
        this.setBoundingBox(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));
    }

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
    {
        this.setBlockBoundsBasedOnState(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
        return super.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
    }

    public void setBoundingBox(int meta)
    {
    	final int width = 32; //width of block in pixels (double res)
		float px = 1.0f/(float)width; //0.03125D; //double res
		int w1 = 16; //lamp socket width in pixels (doubleres);
		int h1 = 6; //lamp socket height
		
		float minW1 = ((width-w1)/2.0f)*px;
		float maxW1 = (width - ((width-w1)/2.0f))*px;	
		float minH1 = (h1)*px;
		float maxH1 = (width-h1)*px;
    	

        if (meta == 1 || meta == 9){ //TOP (Y+)
        	this.setBlockBounds(minW1, maxH1, minW1, maxW1, 1.0f, maxW1);
			
		}else if (meta == 2 || meta == 10) { //BOTTOM (Y-)
			this.setBlockBounds(minW1, 0.0f, minW1, maxW1, minH1, maxW1);
			
		} else if(meta == 3 || meta == 11) { //SOUTH (Z+)
			this.setBlockBounds(minW1, minW1, maxH1, maxW1, maxW1, 1.0f);
		
		} else if (meta == 4 || meta == 12) { //NORTH (Z-)
			this.setBlockBounds(minW1, minW1, 0f,  maxW1, maxW1, minH1);
						
		} else if(meta == 5 || meta == 13) { //EAST (X+)
			this.setBlockBounds(maxH1, minW1, minW1, 1f, maxW1, maxW1);
		
		} else if (meta == 6 || meta == 14) { //WEST (X-)
			this.setBlockBounds(0f, minW1, minW1, minH1, maxW1, maxW1);				
		}else if (meta == 7 || meta == 15 ) { //LANTERN
			this.setBlockBounds(minW1, 0.125f, minW1, maxW1, 0.875f, maxW1);
		}
    }
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
//    @SideOnly(Side.CLIENT)
//	public boolean shouldSideBeRendered(IBlockAccess p1, int p2, int p3,
//			int p4, int p5) {
//			return true;
//	}
    

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return world.isSideSolid(x - 1, y, z, EAST,  true) ||
               world.isSideSolid(x + 1, y, z, WEST,  true) ||
               world.isSideSolid(x, y, z - 1, SOUTH, true) ||
               world.isSideSolid(x, y, z + 1, NORTH, true) ||
               world.isSideSolid(x, y - 1, z, UP,    true) ||
               world.isSideSolid(x, y + 1, z, DOWN,  true);             
    }

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {
    	if (metadata == 7 || metadata == 15) return metadata;	
    	
    	int meta = metadata;
        int offset = 0;
        if (metadata == 8) offset = 8;

        if (side == 0 && world.isSideSolid(x, y + 1, z, DOWN, true))
        {
            meta = 1 + offset;
        }
        
        if (side == 1 && world.isSideSolid(x, y - 1, z, UP, true))
        {
            meta = 2 + offset;
        }

        if (side == 2 && world.isSideSolid(x, y, z + 1, NORTH, true))
        {
            meta = 3 + offset;
        }

        if (side == 3 && world.isSideSolid(x, y, z - 1, SOUTH, true))
        {
            meta = 4 + offset;
        }

        if (side == 4 && world.isSideSolid(x + 1, y, z, WEST, true))
        {
            meta = 5 + offset;
        }

        if (side == 5 && world.isSideSolid(x - 1, y, z, EAST, true))
        {
            meta = 6 + offset;
        }

        return meta;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        super.updateTick(world, x, y, z, rand);

        int m = world.getBlockMetadata(x, y, z);
        if (m == 0 || m == 8)
        {
            this.onBlockAdded(world, x, y, z);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int x, int y, int z)
    {
    	int m = world.getBlockMetadata(x, y, z);
    	
        if (m == 0 || m == 8)
        {
        	if (world.isSideSolid(x, y + 1, z, DOWN, true)) {
        		world.setBlockMetadataWithNotify(x, y, z, m+1, 2);
        	}else if (world.isSideSolid(x, y - 1, z, UP, true)) {
        		world.setBlockMetadataWithNotify(x, y, z, m+2, 2);
        	} else if (world.isSideSolid(x, y, z + 1, NORTH, true)) {
                world.setBlockMetadataWithNotify(x, y, z, m+3, 2);
        	}else if (world.isSideSolid(x, y, z - 1, SOUTH, true)) {
                world.setBlockMetadataWithNotify(x, y, z, m+4, 2);
            }else if (world.isSideSolid(x + 1, y, z, WEST, true)) {
                world.setBlockMetadataWithNotify(x, y, z, m+5, 2);
            }else if (world.isSideSolid(x - 1, y, z, EAST, true)) {
                world.setBlockMetadataWithNotify(x, y, z, m+6, 2);
            }
            
        }

        this.checkIfValidBlock(world, x, y, z);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
    {
        this.validateNeighborChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
    }

    protected boolean validateNeighborChange(World world, int x, int y, int z, Block block)
    {
        if (this.checkIfValidBlock(world, x, y, z))
        {
            int meta = world.getBlockMetadata(x, y, z);
            boolean invalid = false;
            
            if (!world.isSideSolid(x, y + 1, z, DOWN, true) && (meta == 1 || meta == 9)) {
        		invalid = true;
        	}
            if (!world.isSideSolid(x, y - 1, z, UP, true) && (meta == 2 || meta == 10)) {
            	invalid = true;
        	}
            if (!world.isSideSolid(x, y, z + 1, NORTH, true) && (meta == 3 || meta == 11)) {
            	invalid = true;
        	}
            if (!world.isSideSolid(x, y, z - 1, SOUTH, true) && (meta == 4 || meta == 12)) {
            	invalid = true;
            }
            if (!world.isSideSolid(x + 1, y, z, WEST, true) && (meta == 5 || meta == 13)) {
            	invalid = true;
            }
            if (!world.isSideSolid(x - 1, y, z, EAST, true) && (meta == 6 || meta == 14)) {
            	invalid = true;
            }   

            if (invalid)
            {
                this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z));
                world.setBlockToAir(x, y, z);
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    protected boolean checkIfValidBlock(World world, int x, int y, int z)
    {
        if (!this.canPlaceBlockAt(world, x, y, z))
        {
            if (world.getBlock(x, y, z) == this)
            {
                this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z));
                world.setBlockToAir(x, y, z);
            }

            return false;
        }
        else
        {
            return true;
        }
    }
    
    private void dropBlockAsItem(World world, int x, int y, int z, int meta) {
    	if(meta<7){
			meta = 0;
		}else if (meta == 7) {
			meta = 7;
		} else if (meta < 15) {
			meta= 8;
		}else {
			meta= 15;
		}
    	this.dropBlockAsItem(world, x, y, z, meta, 0);
    }

    
    public boolean canConnectTo(IBlockAccess w, int x, int y, int z)
    {
        Block block = w.getBlock(x, y, z);
        return block != this &&
        	   block.getMaterial().isOpaque()
        	   && block.renderAsNormalBlock()
        	   && block.getMaterial() != Material.gourd;
    }
    
    @Override
	public int getRotationMeta(int metadata, int rotationOffset) {
    	if (metadata <=2 || (metadata > 6 && metadata < 11 ) || metadata == 15) return metadata;
		int m = metadata;
		int offset = metadata >= 8 ? 8 : 0;
		for (int i = 0; i < rotationOffset; i++) {
			switch(m % 8) {
				case 5:
					m=4;
					break;
				case 3:
					m=5;
					break;
				case 6:
					m=3;
					break;
				case 4:
					m=6;
					break;
			}
		}
		return offset+m;
	}

}
