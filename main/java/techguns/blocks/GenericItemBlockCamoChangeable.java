package techguns.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import techguns.items.armors.ICamoChangeable;
import techguns.util.TextUtil;

public class GenericItemBlockCamoChangeable extends GenericItemBlockMetadata implements ICamoChangeable{

	public GenericItemBlockCamoChangeable(Block block) {
		super(block);
	}

	@Override
	public int getCamoCount() {
		return ((IBlockCamoChangeable) field_150939_a).getVariationCount();
	}
	
	private int getMetaOffset(){
		return ((IBlockCamoChangeable) field_150939_a).getMetaOffset();
	}

	@Override
	public int switchCamo(ItemStack item) {
		GenericItemBlockCamoChangeable camoblock = (GenericItemBlockCamoChangeable) item.getItem();
		int meta = ((camoblock.getCurrentCamoIndex(item)+1) % getCamoCount())* this.getMetaOffset();
		item.setItemDamage(meta);
		return meta;
	}

	@Override
	public int switchCamo(ItemStack item, boolean back) {
		GenericItemBlockCamoChangeable camoblock = (GenericItemBlockCamoChangeable) item.getItem();
		int meta = ((camoblock.getCurrentCamoIndex(item) + (back ? -1 : 1)) % getCamoCount()) * this.getMetaOffset();
		item.setItemDamage(meta);
		return meta;
	}

	@Override
	public int getCurrentCamoIndex(ItemStack item) {
		return item.getItemDamage()/this.getMetaOffset();
	}

	@Override
	public String getCurrentCamoName(ItemStack item) {
		return ((IBlockCamoChangeable) field_150939_a).getVariationName(item.getItemDamage());
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b) {
		super.addInformation(stack, player, list, b);
		list.add(TextUtil.trans("techguns.tile.camoNet.tooltip")+": "+ TextUtil.trans(this.getCurrentCamoName(stack)));
	}
	
	
}
