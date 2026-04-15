package techguns.blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import techguns.Techguns;

import com.cricketcraft.chisel.api.ICarvable;
import com.cricketcraft.chisel.api.carving.CarvableHelper;
import com.cricketcraft.chisel.api.carving.IVariationInfo;
import com.cricketcraft.chisel.api.carving.VariationInfoBase;
import com.cricketcraft.chisel.api.rendering.ClientUtils;

/**
 * Convenience implementation of ICMBlock. This does everything you should need for basic CTM.
 * <p>
 * This class does <i>not</i> handle metadata-dependent textures.
 */
public class GenericBlockCTM extends GenericBlock implements ICarvable{  //ICTMBlock<ISubmapManager>, 

	//@SideOnly(Side.CLIENT)
	//private SubmapManagerCTM manager;
	
	public CarvableHelper carverHelper;

	private final String modid = Techguns.MODID;
	//private final String texturePath;
	
	private String desc = "temp";
	
	//private final int renderId;
	
	private VariationInfoBase[] variations;

	public GenericBlockCTM(Material material) {
		super(material);
		this.carverHelper = new CarvableHelper(this);
	}
	
//	public GenericBlockCTM(Material material, String texturePath, int renderId) {
//		super(material);
//		this.carverHelper = new CarvableHelper(this);
//		this.texturePath = texturePath;
//		this.renderId = renderId;
//	}
	
//	public GenericBlockCTM(String modid, String texturePath, int renderId) {
//		super(Material.rock);
//		this.modid = modid;
//		this.texturePath = texturePath;
//		this.renderId = renderId;
//	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerBlockIcons(IIconRegister icon) {
//		manager = new SubmapManagerCTM(texturePath);
//		manager.registerIcons(modid, this, icon);
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
//		return manager.getIcon(world, x, y, z, side);
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIcon(int side, int meta) {
//		return manager.getIcon(side, meta);
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public int getRenderType() {
//		return renderId;
//	}
	
	@Override
	public IIcon getIcon(int side, int metadata) {
		return carverHelper.getIcon(side, metadata);
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return carverHelper.getIcon(world, x, y, z, side);
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
		carverHelper.registerBlockIcons(Techguns.MODID, this, register);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
		carverHelper.registerSubBlocks(this, tabs, list);
	}

	@Override
	public int damageDropped (int metadata) {
		return metadata;
	}

	@Override
	public int getRenderType() {
		return ClientUtils.renderCTMId;
	}

//	@Override
//	public ISubmapManager getManager(IBlockAccess world, int x, int y, int z, int meta) {
//		return manager;
//	}
//
//	@Override
//	public ISubmapManager getManager(int meta) {
//		return manager;
//	}

	public String getModid() {
		return modid;
	}

//	public String getTexturePath() {
//		return texturePath;
//	}
	

//	@Override
//	public IVariationInfo getVariation(IBlockAccess world, int x, int y, int z,
//			int metadata) {
//		return new VariationInfoBase(TGBlocks.chiselRegistry.getVariation(world.getBlock(x, y, z), metadata), this.desc, this.manager, TextureType.NORMAL); 
//	}
//
//	@Override
//	public IVariationInfo getVariation(ItemStack stack) {
//		return new VariationInfoBase(TGBlocks.chiselRegistry.getVariation(Block.getBlockFromItem(stack.getItem()), stack.getItemDamage()), this.desc, this.manager); 
//	}
	
	/*@Override
	public IVariationInfo getVariation(IBlockAccess world, int x, int y, int z, int metadata) {
		return carverHelper.getVariation(metadata);
	}

	@Override
	public IVariationInfo getVariation(ItemStack stack) {
		return carverHelper.getVariation(stack.getItemDamage());
	}*/

	@Override
	public IVariationInfo getManager(IBlockAccess world, int x, int y, int z, int metadata) {
		return carverHelper.getVariation(metadata);
	}

	@Override
	public IVariationInfo getManager(int meta) {
		return carverHelper.getVariation(meta);
	}
	
	
}