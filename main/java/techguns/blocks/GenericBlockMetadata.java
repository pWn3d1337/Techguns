package techguns.blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class GenericBlockMetadata extends GenericBlock {

	protected String[] blocktextures;
	protected IIcon[] icons;
	protected int amountUsed;
	protected byte[] lightlevel;
	
	public GenericBlockMetadata(Material mat, int amountUsed) {
		super(mat);
		icons = new IIcon[amountUsed];
		blocktextures = new String[amountUsed];
		this.amountUsed = amountUsed;
		this.lightlevel=new byte[amountUsed];
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if(meta<amountUsed){
			return icons[meta];
		}
		return super.getIcon(side, meta);
	}

	@Override
	public void registerBlockIcons(IIconRegister r) {
		super.registerBlockIcons(r);
		for (int i=0;i<amountUsed;i++){
			icons[i] = r.registerIcon(blocktextures[i]);
		}
	}

	public void setBlockTypeForMeta(int meta, String tex){
		this.blocktextures[meta]=tex;
		this.lightlevel[meta]=0;
	}
	
	public void setLightLevelForMeta(int meta, int lightlevel){
		this.lightlevel[meta]=(byte) lightlevel;
	}
	
    @Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i=0;i<amountUsed;++i){
				list.add(new ItemStack(item,1,i));
		}
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if (meta<this.amountUsed){
			return this.lightlevel[meta];
		}
		return super.getLightValue(world, x, y, z);
	}
	
    
	
}
