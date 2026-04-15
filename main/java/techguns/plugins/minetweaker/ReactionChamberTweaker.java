package techguns.plugins.minetweaker;

import java.util.Iterator;

import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import techguns.inventory.ItemStackOreDict;
import techguns.inventory.ReactionBeamFocus;
import techguns.inventory.ReactionChamberRecipe;
import techguns.inventory.ReactionChamberRecipe.RiskType;
import techguns.util.ItemUtil;
import techguns.util.MathUtil;

@ZenClass("mods.techguns.ReactionChamber")
public class ReactionChamberTweaker {

	@ZenMethod
	public static void addRecipe(IItemStack input,ILiquidStack fluid, IItemStack[] outputs, IItemStack beamFocus, int ticks, int requiredCompletion,
			int preferredIntensity, int intensityMargin, int liquidLevel, int liquidConsumption, float instability, String risk, int RFTick){
		MineTweakerAPI.apply(new addInputAction(TGMineTweakerHelper.toItemStackOreDict(input),fluid,outputs,
				beamFocus,ticks,requiredCompletion,preferredIntensity,intensityMargin,
				liquidLevel, liquidConsumption, instability, risk, RFTick));
	}
	
	@ZenMethod
	public static void addRecipe(String input,ILiquidStack fluid, IItemStack[] outputs, IItemStack beamFocus, int ticks, int requiredCompletion,
			int preferredIntensity, int intensityMargin, int liquidLevel, int liquidConsumption, float instability, String risk, int RFTick){
		MineTweakerAPI.apply(new addInputAction(TGMineTweakerHelper.toItemStackOreDict(input),fluid,outputs,
				beamFocus,ticks,requiredCompletion,preferredIntensity,intensityMargin,
				liquidLevel, liquidConsumption, instability, risk, RFTick));
	}
	
	@ZenMethod
	public static void addRecipe(IItemStack input,ILiquidStack fluid, IItemStack output, IItemStack beamFocus, int ticks, int requiredCompletion,
			int preferredIntensity, int intensityMargin, int liquidLevel, int liquidConsumption, float instability, String risk, int RFTick){
		MineTweakerAPI.apply(new addInputAction(TGMineTweakerHelper.toItemStackOreDict(input),fluid,new IItemStack[]{output},
				beamFocus,ticks,requiredCompletion,preferredIntensity,intensityMargin,
				liquidLevel, liquidConsumption, instability, risk, RFTick));
	}

	@ZenMethod
	public static void addRecipe(String input,ILiquidStack fluid, IItemStack output, IItemStack beamFocus, int ticks, int requiredCompletion,
			int preferredIntensity, int intensityMargin, int liquidLevel, int liquidConsumption, float instability, String risk, int RFTick){
		MineTweakerAPI.apply(new addInputAction(TGMineTweakerHelper.toItemStackOreDict(input),fluid,new IItemStack[]{output},
				beamFocus,ticks,requiredCompletion,preferredIntensity,intensityMargin,
				liquidLevel, liquidConsumption, instability, risk, RFTick));
	}

	@ZenMethod
	public static void removeRecipe(IItemStack input, ILiquidStack fluid){
		MineTweakerAPI.apply(new removeInputAction(TGMineTweakerHelper.toItemStackOreDict(input), fluid));
	}
	
	@ZenMethod
	public static void removeRecipe(String input, ILiquidStack fluid){
		MineTweakerAPI.apply(new removeInputAction(TGMineTweakerHelper.toItemStackOreDict(input), fluid));
	}
	
	private static class addInputAction extends OneWayAction
	{

		ReactionChamberRecipe added_recipe=null;
		
		ItemStackOreDict input;
		FluidStack fluid;
		ItemStack[] outputs;
		
		ItemStack beamFocus;
		
		byte ticks; //time of reaction in ReactionTicks
		
		byte requiredCompletion; //Required completion level for success
		byte preferredIntensity; //[0-10]
		byte intensityMargin; //[0-10]
		byte liquidLevel; //[0-10] in Buckets

		short liquidConsumtion; // < LiquidLevel; in milliBuckets
		float instability; //[0.0f-1.0f] chance to change preferredIntensity per ReactionTick
		
		String risk_str;
		RiskType risk;

		int RFTick;
		
		boolean valid=true;
		String msg;
			
		public addInputAction(ItemStackOreDict input, ILiquidStack fluid, IItemStack[] outputs, IItemStack beamFocus, int ticks, int requiredCompletion,
				int preferredIntensity, int intensityMargin, int liquidLevel, int liquidConsumtion, float instability, String risk, int RFTick) {
			super();
			this.input = input;
			this.fluid = MineTweakerMC.getLiquidStack(fluid);
			this.outputs = MineTweakerMC.getItemStacks(outputs);
			this.beamFocus = MineTweakerMC.getItemStack(beamFocus);
			this.ticks = (byte) ticks;
			this.requiredCompletion = (byte) requiredCompletion;
			this.preferredIntensity = (byte) preferredIntensity;
			this.intensityMargin = (byte) intensityMargin;
			this.liquidLevel = (byte) liquidLevel;
			this.liquidConsumtion = (short) liquidConsumtion;
			this.instability = instability;
			this.risk_str = risk;//RiskType.valueOf(risk);
			this.RFTick = RFTick;
			
			this.valid=checkValid();
		}
		
		@Override
		public void apply() {
			if (valid){
				added_recipe = ReactionChamberRecipe.addRecipe(input,beamFocus,fluid.getFluid(),
						outputs,ticks,requiredCompletion,preferredIntensity,intensityMargin,
						liquidLevel,liquidConsumtion,instability,risk,RFTick);
			}
		}

		@Override
		public String describe() {
			if (valid){
				return "Add Recipe for "+outputs[0]+" to ReactionChamber";
			} else {
				return "ERROR Adding ReactionChamber Recipe: "+msg;
			}
		}
		@Override
		public Object getOverrideKey() {
			return null;
		}
		
		private boolean checkValid(){
			
			if (ReactionBeamFocus.getBeamFocus(beamFocus)==null){
				msg="Item passed as Beam Focus is not valid!";
				return false;
			}
			
			if (!MathUtil.inRange(preferredIntensity, 0, 10)){
				msg="Preferred Intensity must be in [0-10]!";
				return false;
			}
			if (!MathUtil.inRange(intensityMargin, 0, 10)){
				msg="intensityMargin must be in [0-10]!";
				return false;
			}
			
			if (!MathUtil.inRange(liquidLevel, 0, 10)){
				msg="liquidLeve must be in [0-10]!";
				return false;
			}
			
			if (!MathUtil.inRange(liquidConsumtion, 0, liquidLevel*1000)){
				msg="liquidConsumption must not be negative and lower than 1000*liquidLevel!";
				return false;
			}
			
			if (instability<0f || instability >1f){
				msg="instability must be between 0.0 and 1.0";
				return false;
			}
			
			try {
				this.risk = RiskType.valueOf(risk_str);
			} catch (IllegalArgumentException ex){
				msg="Invalid risktype: "+ risk_str;
				return false;
			}
			
			return true;
		}
	}
	
	private static class removeInputAction extends OneWayAction
	{
		
		ItemStackOreDict input;
		FluidStack fluid;
		
		public removeInputAction(ItemStackOreDict input, ILiquidStack fluid) {
			super();
			this.input = input;
			this.fluid = MineTweakerMC.getLiquidStack(fluid);
		}

		@Override
		public void apply() {
			
			Iterator<ReactionChamberRecipe> iter = ReactionChamberRecipe.getRecipes().iterator();
			
			while (iter.hasNext()){
				ReactionChamberRecipe rec = iter.next();
								
				if (rec.input.matches(input) && ItemUtil.isFluidEqual(new FluidStack(rec.liquidIn,1000), fluid)){
				
					iter.remove();
				}
			}
		}
		
		@Override
		public String describe() {
			return "Removed Recipe(s) using "+input+"/"+fluid.getUnlocalizedName()+" from ReactionChamber";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
		

	}
}
