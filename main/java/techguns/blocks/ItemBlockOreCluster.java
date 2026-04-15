package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import techguns.util.TextUtil;

public class ItemBlockOreCluster extends GenericItemBlockMetadata {
	OreCluster cluster;
	
	public ItemBlockOreCluster(Block block) {
		super(block);
		this.cluster = (OreCluster) block;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getItemDamage();
		return TextUtil.trans("techguns.tile.oreCluster.name")+" ("+ TextUtil.trans(cluster.ores[meta].getUnlocalizedName()+".name")+")";
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		return cluster.getIcon(0, meta);
	}

	
	
}
