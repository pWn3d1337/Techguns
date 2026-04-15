package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import techguns.tileentities.BioblobTileEnt;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Bioblob extends BlockContainer {

	static final Material MAT = Material.rock;
	//int size;

	public Bioblob() {
		super(MAT);
		setBlockTextureName("techguns:bio");
	}

	public Bioblob(Material mat) {
		super(mat);
	}

	@Override
	public boolean isOpaqueCube() {
		return false; //this.opaque;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false; //size == 3;
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess p1, int p2, int p3,
			int p4, int p5) {
//		if (size == 3) {
//			return super.shouldSideBeRendered(p1, p2, p3, p4, p5);
//		} else {
			return true;
		//}
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

    public void setBoundingBox(int meta, int size)
    {
    	final int width = 32; //width of block in pixels (double res)
		float px = 1.0f/(float)width; //0.03125D; //double res
		int w1 = 10 + 6*size; //lamp socket width in pixels (doubleres);
		int h1 = 2 + 4*size; //lamp socket height
		
		float minW1 = ((width-w1)/2.0f)*px;
		float maxW1 = (width - ((width-w1)/2.0f))*px;	
		float minH1 = (h1)*px;
		float maxH1 = (width-h1)*px;
    	

        if (meta == 0){ //TOP (Y+)
        	this.setBlockBounds(minW1, maxH1, minW1, maxW1, 1.0f, maxW1);
			
		}else if (meta == 1) { //BOTTOM (Y-)
			this.setBlockBounds(minW1, 0.0f, minW1, maxW1, minH1, maxW1);
			
		} else if(meta == 2) { //SOUTH (Z+)
			this.setBlockBounds(minW1, minW1, maxH1, maxW1, maxW1, 1.0f);
		
		} else if (meta == 3) { //NORTH (Z-)
			this.setBlockBounds(minW1, minW1, 0f,  maxW1, maxW1, minH1);
						
		} else if(meta == 4) { //EAST (X+)
			this.setBlockBounds(maxH1, minW1, minW1, 1f, maxW1, maxW1);
		
		} else if (meta == 5) { //WEST (X-)
			this.setBlockBounds(0f, minW1, minW1, minH1, maxW1, maxW1);				
		}
        
    }
	
	public void setBlockBoundsForItemRender() {
		
		/*switch (size) {
		case 1:
			this.setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 0.25F, 0.8F);
			break;
		case 2:
			this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.45F, 0.9F);
			break;
		case 3:
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1F, 1F, 1F);
			break;
		default:
			System.out.println("bioblob: invalid size.");
			break;
		}*/
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess w,
			int x, int y, int z) {
			/*switch (size) {
			case 1:
				this.setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 0.25F, 0.8F);
				break;
			case 2:
				this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.45F, 0.9F);
				break;
			case 3:
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 1F, 1F, 1F);
				break;*/
			int meta = w.getBlockMetadata(x, y, z);
			int size=1;
			
			TileEntity tile = w.getTileEntity(x,y,z);
			if(tile instanceof BioblobTileEnt){
				size=((BioblobTileEnt)tile).getBlobSize();
			}
			
			this.setBoundingBox(meta,size);
		
	}
	
	
	/**
    * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
    */
    public void onEntityCollidedWithBlock(World p1_world, int x, int y, int z, Entity p5_entity)
    {
        try {
        	((EntityLiving)p5_entity).addPotionEffect(new PotionEffect(Potion.poison.id,100,2,false));
        }catch (ClassCastException e) {
        	
        }
    }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new BioblobTileEnt();
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.block.BlockContainer#breakBlock(net.minecraft.world.World, int, int, int, net.minecraft.block.Block, int)
	 */
	@Override
	public void breakBlock(World world, int x, int y,
			int z, Block b, int p_149749_6_) {
		world.removeTileEntity(x, y, z);
	}

	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta) {
		return false;
	}
	
	
}
