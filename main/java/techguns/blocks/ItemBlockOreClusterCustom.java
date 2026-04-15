package techguns.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import techguns.util.TextUtil;

public class ItemBlockOreClusterCustom extends ItemBlockWithMetadata {

	OreClusterCustom oreCluster;
	
	public ItemBlockOreClusterCustom(Block block) {
		super(block,block);
		this.oreCluster = (OreClusterCustom) block;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.field_150939_a.getUnlocalizedName();
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean b) {
		super.addInformation(item, player, list, b);
		int meta = item.getItemDamage();
		
		if (oreCluster.fluids[meta]!=null){
			list.add(oreCluster.fluids[meta].getLocalizedName());
		} else {
			list.add(TextUtil.trans(oreCluster.ores[meta].getUnlocalizedName()+".name"));
		}
		
	}
	
	
	
}
