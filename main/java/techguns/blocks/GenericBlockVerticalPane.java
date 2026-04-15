package techguns.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GenericBlockVerticalPane extends GenericBlock implements IBlockCamoChangeable {
	
	private float height = 0.1875F;
    private float width = 0.5625F;
    private float width2 = 0.75F;
    
    String[] textures;
	IIcon[] icons;
	
	final int VARIATIONS;


	public GenericBlockVerticalPane(String[] textures, Material mat) {
		super(mat);
		this.textures = textures;
		this.VARIATIONS = textures.length;
	}
	
	public GenericBlockVerticalPane setDimensions(float height, float width, float width2) {
		this.height = height;
		this.width = width;
		this.width2 = width2;
		return this;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
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
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, height, 1.0F);
    }
	
	 /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess w, int x, int y, int z)
    {
        boolean west = this.canConnectFenceTo(w, x, y, z - 1);
        boolean east = this.canConnectFenceTo(w, x, y, z + 1);
        boolean south = this.canConnectFenceTo(w, x - 1, y, z);
        boolean north = this.canConnectFenceTo(w, x + 1, y, z);
        

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

    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return this.icons[meta];
    }
	

	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {		
		icons = new IIcon[VARIATIONS];
		 for (int i = 0; i < VARIATIONS; i++) {
			 icons[i] = reg.registerIcon(textures[i]);			
	     }       
    }
    
	@Override
	public int getVariationCount() {
		return this.VARIATIONS;
	}

	@Override
	public String getVariationName(int meta) {
		return this.getUnlocalizedName()+".camo."+meta;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab,
			List subItems) {
		for (int i = 0; i < VARIATIONS; i++) {
			subItems.add(new ItemStack(item, 1, i));
	     }
		
	}

	@Override
	public int getMetaOffset() {
		return 1;
	}

}
