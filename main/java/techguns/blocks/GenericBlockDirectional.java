package techguns.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import techguns.Techguns;
import techguns.client.ClientProxy;
import techguns.util.BlockUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GenericBlockDirectional extends GenericBlock{

	public IIcon[] icons = new IIcon[6];
	public IIcon[] iconsOverlay; // = new IIcon[6];
	private String texture;
	
	private boolean overlay = false;
	
	public GenericBlockDirectional(Material mat, String texture, boolean overlay) {
		super(mat);
		this.textureName = Techguns.MODID+":"+texture;
		this.texture = texture;
		this.overlay = overlay;
	}
	
	
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		//Top/Bottom
		this.icons[1] = reg.registerIcon(this.textureName + "_top");
		if (BlockUtils.exists(this.texture, "_bottom")) this.icons[0] = reg.registerIcon(this.textureName + "_bottom");
		else this.icons[0] = icons[1];	//use top
		//Left/Right
		this.icons[4] = reg.registerIcon(this.textureName + "_side");
		this.icons[5] = icons[4];	
		//Front/Back
		if (BlockUtils.exists(this.texture, "_front")) this.icons[2] = reg.registerIcon(this.textureName + "_front");
		else this.icons[2] = icons[4];	//use side
		if (BlockUtils.exists(this.texture, "_back")) this.icons[3] = reg.registerIcon(this.textureName + "_back");
		else this.icons[3] = icons[4];	//use side	
		
		if (overlay) {
			this.iconsOverlay = new IIcon[6];
			if (BlockUtils.exists(this.texture, "_overlay_bottom")) this.iconsOverlay[0] = reg.registerIcon(this.textureName + "_overlay_bottom");
			if (BlockUtils.exists(this.texture, "_overlay_top")) this.iconsOverlay[1] = reg.registerIcon(this.textureName + "_overlay_top");
			if (BlockUtils.exists(this.texture, "_overlay_front")) this.iconsOverlay[2] = reg.registerIcon(this.textureName + "_overlay_front");
			if (BlockUtils.exists(this.texture, "_overlay_back")) this.iconsOverlay[3] = reg.registerIcon(this.textureName + "_overlay_back");
			if (BlockUtils.exists(this.texture, "_overlay_side")) this.iconsOverlay[4] = reg.registerIcon(this.textureName + "_overlay_side");
			if (BlockUtils.exists(this.texture, "_overlay_side")) this.iconsOverlay[5] = reg.registerIcon(this.textureName + "_overlay_side");
		}
	}
	
	
	 /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        //return side == 1 ? this.field_149935_N : (side == 0 ? this.field_149935_N : (side != meta ? this.blockIcon : this.field_149936_O));
    	if (side < 2 || meta < 2) {
    		return icons[side];
    	}else {
    		return icons[2+((side+(meta)) % 4)];
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getOverlayIcon(int side, int meta)
    {
        //return side == 1 ? this.field_149935_N : (side == 0 ? this.field_149935_N : (side != meta ? this.blockIcon : this.field_149936_O));
    	if (side < 2 || meta < 2) {
    		return iconsOverlay[side];
    	}else {
    		return iconsOverlay[2+((side+(meta)) % 4)];
    	}
    }
	
	
	 /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
    {
        int l = MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 2, 2);
        }

        if (l == 1)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 3, 2);
        }

        if (l == 2)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 5, 2);
        }

        if (l == 3)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 4, 2);
        }

    }



	public boolean hasOverlay() {
		return overlay;
	}


	@Override
	public boolean canRenderInPass(int pass)
    {
		ClientProxy.get().renderPass = pass;
        return true;
    }
	
	@Override
    public int getRenderBlockPass()
	{
	    return 1;
	}
	
	
}
