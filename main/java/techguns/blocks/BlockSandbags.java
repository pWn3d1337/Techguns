package techguns.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSandbags extends GenericBlock{
	
	IIcon[] icons;
	
	public BlockSandbags(Material mat) {
		super(mat);
		//setLightOpacity(255);
	}

	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.icons = new IIcon[2];
		this.icons[0] = reg.registerIcon("techguns:sandbags-side");
		this.icons[1] = reg.registerIcon("techguns:sandbags-top");
	}
	
	
	 /**
     * Gets the block's texture. Args: side, meta
     */
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	if (side <= 1) {
    		return icons[1];
    	}else {
    		return icons[0];
    	}
    }
	

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess p1, int p2, int p3,
			int p4, int p5) {
		return true;
	}
	
	 /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
    	//float width = 0.75f;
        //float width2 = 0.75f;
        this.setBlockBounds(0.0F, 0.0F, 0.25f, 1.0F, 1.0F, 0.75f);
    }
	
    
    
	 @Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list,
			Entity entity) {
		 this.setBlockBoundsBasedOnState(world, x, y, z);
		super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
	}


	/*@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}*/


	/**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess w, int x, int y, int z)
    {
        boolean west = this.canConnectFenceTo(w, x, y, z - 1);
        boolean east = this.canConnectFenceTo(w, x, y, z + 1);
        boolean south = this.canConnectFenceTo(w, x - 1, y, z);
        boolean north = this.canConnectFenceTo(w, x + 1, y, z);
        
        float width = 0.75f;
        float width2 = 0.75f;
        float height = 1.0f;
        

        if (north && south && east && west) this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, height, 1.0f);
        else if (north && south && east && !west) this.setBlockBounds(0.0F, 0.0F, 1-width, 1.0F, height, 1.0f);
        else if (north && south && !east && west) this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, height, width);
        else if (north && south && !east && !west) this.setBlockBounds(0.0F, 0.0F, 1-width2, 1.0F, height, width2);
        else if (north && !south && east && west) this.setBlockBounds(1-width, 0.0F, 0.0F, 1.0F, height, 1.0f);
        else if (north && !south && east && !west) this.setBlockBounds(1-width, 0.0F, 1-width, 1.0F, height, 1.0f);
        else if (north && !south && !east && west) this.setBlockBounds(1-width, 0.0F, 0, 1.0F, height, width);
        else if (north && !south && !east && !west) this.setBlockBounds(1-width, 0.0F, 1-width2, 1.0F, height, width2);
        else if (!north && south && east && west) this.setBlockBounds(0.0F, 0.0F, 0.0F, width, height, 1.0f);
        
        else if (!north && south && east && !west) this.setBlockBounds(0.0F, 0.0F, 1-width, width, height, 1.0f);
        else if (!north && south && !east && west) this.setBlockBounds(0.0F, 0.0F, 0.0F, width, height, width);
        else if (!north && south && !east && !west) this.setBlockBounds(0.0F, 0.0F, 1-width2, width, height, width2);
        else if (!north && !south && east && west) this.setBlockBounds(1-width2, 0.0F, 0.0F, width2, height, 1.0f); //---
        else if (!north && !south && east && !west) this.setBlockBounds(1-width2, 0.0F, 1-width, width2, height, 1.0f);
        else if (!north && !south && !east && west) this.setBlockBounds(1-width2, 0.0F, 0, width2, height, width);
        
        
        else this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, height, 0.75f);
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
     * Returns true if the specified block can be connected by a tent roof
     */
    public boolean canConnectFenceTo(IBlockAccess w, int x, int y, int z)
    {
        Block block = w.getBlock(x, y, z);
        return block != this && block != Blocks.fence_gate ? (block.getMaterial().isOpaque() && block.renderAsNormalBlock() ? block.getMaterial() != Material.gourd : false) : true;
    }

}
