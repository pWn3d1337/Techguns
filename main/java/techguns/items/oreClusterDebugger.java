package techguns.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.util.TextUtil;

public class oreClusterDebugger extends GenericItem {
	
	public oreClusterDebugger() {
		super();
		setMaxStackSize(1);
		setUnlocalizedName("oreClusterDebugger");
		setTextureName("techguns:testItem");
	}
	
	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		int distance=16;
		if (!world.isRemote) {
			for(int iy=y;iy>0;--iy){
				for (int ix = x-distance;ix<x+distance;++ix){
					
					for (int iz = z-distance;iz<z+distance;++iz){
						
						Block b = world.getBlock(ix, iy, iz);
						if (b.getBlockHardness(world, ix, iy, iz)>=0){
							world.setBlockToAir(ix, iy, iz);
						}
					}
					
				}
			}
		}

		return true;	
    }	

	@Override
	public void addInformation(ItemStack item, EntityPlayer player,
			List list, boolean b) {
		super.addInformation(item, player, list, b);
		list.add(TextUtil.trans("techguns.oreClusterDebugger.Tooltip"));
	}
	
}
