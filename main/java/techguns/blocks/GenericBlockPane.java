package techguns.blocks;

import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import techguns.Techguns;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GenericBlockPane extends BlockPane implements IBlockCamoChangeable{
	
	int renderType;
	//IIcon[] paneIconsTop;
	IIcon[] paneIconsSide;
	IIcon[] poleIcons;
	String[] sideTextures;
	//String[] topTextures;
	String[] poleTextures;
	IIcon paneIconTop;
	
	final int VARIATIONS;

	public GenericBlockPane(String[] sideTextures, String[] poleTextures,
			Material mat) {
		super("", "", mat, true);
		setCreativeTab(Techguns.tabTechgun);
		this.sideTextures = sideTextures;
		this.poleTextures = poleTextures;
		this.VARIATIONS = sideTextures.length;
	}
	

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        float f = 0.4375F;
        float f1 = 0.5625F;
        float f2 = 0.4375F;
        float f3 = 0.5625F;
        boolean north  = this.canPaneConnectTo(world, x, y, z - 1, NORTH);
        boolean south = this.canPaneConnectTo(world, x, y, z + 1, SOUTH);
        boolean west = this.canPaneConnectTo(world, x - 1, y, z, WEST );
        boolean east = this.canPaneConnectTo(world, x + 1, y, z, EAST );

        if (!(north||east||south||west)) {
        	this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
        	return;
        }
        
        if ((!west || !east) && (west || east || north || south))
        {
            if (west && !east)
            {
                f = 0.0F;
            }
            else if (!west && east)
            {
                f1 = 1.0F;
            }
        }
        else
        {
            f = 0.0F;
            f1 = 1.0F;
        }

        if ((!north || !south) && (west || east || north || south))
        {
            if (north && !south)
            {
                f2 = 0.0F;
            }
            else if (!north && south)
            {
                f3 = 1.0F;
            }
        }
        else
        {
            f2 = 0.0F;
            f3 = 1.0F;
        }

        this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
    }
	
	@Override
	public boolean canPaneConnectTo(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
    {
		Block b = world.getBlock(x, y, z);
		boolean canConnect = b == this ||
				b instanceof BlockPane;
		return canConnect;
    }
	
	public GenericBlockPane setRenderType(int renderType) {
		this.renderType = renderType;
		return this;
	}
	
	@Override
	public int getRenderType() {
		return renderType;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab,
			List subItems) {
		for (int i = 0; i < VARIATIONS; i++) {
			subItems.add(new ItemStack(item, 1, i));
	     }
		
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return this.paneIconsSide[meta];
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon func_150097_e()
    {
		return this.paneIconTop;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
		paneIconsSide = new IIcon[VARIATIONS];
		paneIconTop = reg.registerIcon("techguns:invisible");
		poleIcons = new IIcon[VARIATIONS];
		 for (int i = 0; i < VARIATIONS; i++) {
			 paneIconsSide[i] = reg.registerIcon(sideTextures[i]);
			 //paneIconsTop[i] = reg.registerIcon(topTextures[i]);
			 poleIcons[i] = reg.registerIcon(poleTextures[i]);
	     }
        
    }

    @SideOnly(Side.CLIENT)
	public IIcon getPoleIcon(int blockMetadata) {
		return this.poleIcons[blockMetadata];
	}


	@Override
	public int getVariationCount() {
		return this.VARIATIONS;
	}


	@Override
	public String getVariationName(int meta) {
		return this.getUnlocalizedName()+".camo."+meta;
	}

	

	@Override
	public String getUnlocalizedName() {
		return "techguns."+super.getUnlocalizedName();
	}


	@Override
	public int getMetaOffset() {
		return 1;
	}

	
//	@Override
//	public int getLightOpacity(IBlockAccess world, int x, int y, int z)
//    {
//		int meta = world.getBlockMetadata(x, y, z);
//		switch (meta) {
//		case 0: //Tent Wall
//			return 255; //fully opaque
//		default:
//			return getLightOpacity();
//		}
//        
//    }
}
