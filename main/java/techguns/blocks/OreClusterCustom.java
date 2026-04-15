package techguns.blocks;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class OreClusterCustom extends OreCluster {

	public OreClusterCustom(String blockname) {
		super(blockname);
	}

	public void setOre(int id, ItemStack ore, int level, float r, float g, float b, int tex, float multPower, float multAmount){
		this.ores[id]=ore;
		this.mininglevels[id]=level;
		this.colors[id][0]=r;
		this.colors[id][1]=g;
		this.colors[id][2]=b;
		this.textureType[id]=tex;
		this.rarity[id]=0;
		this.maxy[id]=0;
		this.multipliersPower[id]=multPower;
		this.multipliersAmount[id]=multAmount;
		this.fluids[id]=null;
	}	
	
	public void setFluid(int id, FluidStack fluid, int level, float r, float g, float b, int tex, float multPower, float multAmount){
		this.ores[id]=new ItemStack(this,1);
		this.mininglevels[id]=level;
		this.colors[id][0]=r;
		this.colors[id][1]=g;
		this.colors[id][2]=b;
		this.textureType[id]=tex;
		this.rarity[id]=0;
		this.maxy[id]=0;
		this.multipliersPower[id]=multPower;
		this.multipliersAmount[id]=multAmount;
		this.fluids[id]=fluid;
	}	
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i=0;i<16;++i){
			if(mininglevels[i]>0){
				list.add(new ItemStack(item,1,i));
			}
		}
	}
}
