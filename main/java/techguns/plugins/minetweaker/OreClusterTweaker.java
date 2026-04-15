package techguns.plugins.minetweaker;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import techguns.TGBlocks;

@ZenClass("mods.techguns.OreCluster")
public class OreClusterTweaker {

	@ZenMethod
	public static void addOreCluster(int id, IItemStack ore, int miningLevel, float r, float g, float b, int textureType, float multPower, float multAmount){
		MineTweakerAPI.apply(new addOreClusterType(id, ore, miningLevel, r, g, b, textureType, multPower, multAmount,0));
	}
	
	@ZenMethod
	public static void addOreCluster2(int id, IItemStack ore, int miningLevel, float r, float g, float b, int textureType, float multPower, float multAmount){
		MineTweakerAPI.apply(new addOreClusterType(id, ore, miningLevel, r, g, b, textureType, multPower, multAmount,1));
	}
	
	@ZenMethod
	public static void addOreCluster(int id, ILiquidStack liquid, int miningLevel, float r, float g, float b, int textureType, float multPower, float multAmount){
		MineTweakerAPI.apply(new addOreClusterType(id, liquid, miningLevel, r, g, b, textureType, multPower, multAmount,0));
	}
	
	@ZenMethod
	public static void addOreCluster2(int id, ILiquidStack liquid, int miningLevel, float r, float g, float b, int textureType, float multPower, float multAmount){
		MineTweakerAPI.apply(new addOreClusterType(id, liquid, miningLevel, r, g, b, textureType, multPower, multAmount,1));
	}
	
	private static class addOreClusterType extends OneWayAction
	{
		int id;
		ItemStack ore;
		FluidStack fluid;
		int miningLevel;
		float r;
		float g;
		float b;
		byte textureType;
		float multPower;
		float multOres;
		int clusterID;
		
		public addOreClusterType(int id, IItemStack ore, int miningLevel, float r, float g, float b, int textureType, float multPower, float multOres, int clusterID) {
			super();
			this.id = id;
			this.ore = MineTweakerMC.getItemStack(ore);
			this.miningLevel = miningLevel;
			this.r = r;
			this.g = g;
			this.b = b;
			this.textureType = (byte) textureType;
			this.multPower = multPower;
			this.multOres = multOres;
			this.clusterID = clusterID;
			this.fluid=null;
		}

		public addOreClusterType(int id, ILiquidStack liquid, int miningLevel, float r, float g, float b, int textureType, float multPower, float multOres, int clusterID) {
			super();
			this.id = id;
			this.ore = null;//MineTweakerMC.getItemStack(ore);
			this.fluid = MineTweakerMC.getLiquidStack(liquid);
			this.miningLevel = miningLevel;
			this.r = r;
			this.g = g;
			this.b = b;
			this.textureType = (byte) textureType;
			this.multPower = multPower;
			this.multOres = multOres;
			this.clusterID = clusterID;
		}

		@Override
		public void apply() {
			switch(clusterID){
				case 0:
					if (this.fluid==null){
						TGBlocks.oreClusterCustom.setOre(id, ore, miningLevel, r, g, b, textureType, multPower, multOres);
					} else {
						TGBlocks.oreClusterCustom.setFluid(id, fluid, miningLevel, r, g, b, textureType, multPower, multOres);
					}
					break;
				case 1:
					if (this.fluid==null){
						TGBlocks.oreClusterCustom2.setOre(id, ore, miningLevel, r, g, b, textureType, multPower, multOres);
					} else {
						TGBlocks.oreClusterCustom2.setFluid(id, fluid, miningLevel, r, g, b, textureType, multPower, multOres);
					}
					break;
			}
		}

		@Override
		public String describe() {
			if (this.fluid==null){
				return "Add OreClusterType for "+ore+" as META:"+id+ " to CustomCluster"+(this.clusterID+1);
			}
			return "Add OreClusterType for fluid "+fluid.getFluid().getUnlocalizedName()+" as META:"+id+ " to CustomCluster"+(this.clusterID+1);
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
				
		
	}
	
}
