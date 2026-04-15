package techguns.blocks;

import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import techguns.util.TextUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Mostly copied from BlockLadder
 */
public class BlockTGLadder extends GenericBlock implements IBlockCamoChangeable{
	private IIcon[] textures;


    public BlockTGLadder()
    {
    	super(Material.iron);
    	setHardness(2.0f);
		setBlockName("LadderBlock");
		setStepSound(soundTypeMetal);
		setHarvestLevel("pickaxe", 1);
		//setBlockTextureName("techguns:ladder01");
		setBlockTextureName("techguns:metalLadder");
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
        this.func_149797_b(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));
    }

    
    
    @Override
	public int damageDropped(int meta) {
		return meta-(meta%4);
	}

	@Override
   	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
   		for(int i=0;i<4;++i){
			list.add(new ItemStack(item,1,4*i));
   		}
   	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.textures=new IIcon[4];
		textures[0]=reg.registerIcon("techguns:metalLadder");
		textures[1]=reg.registerIcon("techguns:metalLadderRusty");
		textures[2]=reg.registerIcon("techguns:metalLadderShiny");
		textures[3]=reg.registerIcon("techguns:metalLadderCarbon");
	}

	@Override
	public IIcon getIcon(int sise, int meta) {
		return textures[meta/4];
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

    public void func_149797_b(int meta)
    {
        float f = 0.125F;

        int p_149797_1_ = meta%this.getMetaOffset();
        
        if (p_149797_1_ == 0)
        {
            this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        }

        if (p_149797_1_ == 1)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        }

        if (p_149797_1_ == 2)
        {
            this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }

        if (p_149797_1_ == 3)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        }
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    
    
    
    @Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		// TODO Auto-generated method stub
		return super.getPickBlock(target, world, x, y, z, player);
	}

	/**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
    	return this.renderType; //8
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return world.isSideSolid(x - 1, y, z, EAST ) ||
               world.isSideSolid(x + 1, y, z, WEST ) ||
               world.isSideSolid(x, y, z - 1, SOUTH) ||
               world.isSideSolid(x, y, z + 1, NORTH);
    }

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {
        //int j1 = metadata%this.getMetaOffset();
    	int j1 = -1;
    	
        if (j1 == -1 || side == 2) 
        {
        	if ( world.isSideSolid(x, y, z + 1, NORTH))
            	j1 = 0;
        	else
        		j1 = -1;
        }

        if (j1 == -1 || side == 3){
        	if (world.isSideSolid(x, y, z - 1, SOUTH))       
        		j1 = 1;
        	else
        		j1 = -1;
        }

        if (j1 == -1 || side == 4) {
        	if (world.isSideSolid(x + 1, y, z, WEST))
        		j1 = 2;
        	else
        		j1 = -1;
        }

        if (j1 == -1 || side == 5) {
        	if (world.isSideSolid(x - 1, y, z, EAST))
        		j1 = 3;
        	else
        		j1 = -1;
        }
        //System.out.println("OnBlockPlaced: Metadata="+metadata+" - return="+(j1+metadata));
        return j1+(metadata); ///this.getMetaOffset());
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
    	int meta = world.getBlockMetadata(x, y, z);
        int l = meta%this.getMetaOffset();
        boolean flag = false;

        if (l == 0 && world.isSideSolid(x, y, z + 1, NORTH))
        {
            flag = true;
        }

        if (l == 1 && world.isSideSolid(x, y, z - 1, SOUTH))
        {
            flag = true;
        }

        if (l == 2 && world.isSideSolid(x + 1, y, z, WEST))
        {
            flag = true;
        }

        if (l == 3 && world.isSideSolid(x - 1, y, z, EAST))
        {
            flag = true;
        }

        if (!flag)
        {
            this.dropBlockAsItem(world, x, y, z, meta/this.getMetaOffset(), 0);
            world.setBlockToAir(x, y, z);
        }

        super.onNeighborBlockChange(world, x, y, z, block);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random p_149745_1_)
    {
        return 1;
    }

    @Override
    public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity)
    {
        return true;
    }

	@Override
	public int getVariationCount() {
		return 4;
	}

	@Override
	public String getVariationName(int meta) {
		return TextUtil.trans((this.getUnlocalizedName()+"."+meta));
	}

	@Override
	public int getMetaOffset() {
		return 4;
	}
		
	@Override
	public int getRotationMeta(int metadata, int rotationOffset) {
		int m = metadata;
		int var = metadata / 4;
		for (int i = 0; i < rotationOffset; i++) {
			switch(m % 4) {
				case 0:
					m=2;
					break;
				case 2:
					m=1;
					break;
				case 1:
					m=3;
					break;
				case 3:
					m=0;
					break;
			}
		}
		return (var*4)+m;
	}
	
}
