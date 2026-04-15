package techguns.plugins.minetweaker;

import java.util.ArrayList;
import java.util.Iterator;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import techguns.inventory.ChemLabRecipes;
import techguns.inventory.ChemLabRecipes.ChemLabRecipe;
import techguns.inventory.ItemStackOreDict;
import techguns.util.ItemUtil;

@ZenClass("mods.techguns.ChemLab")
public class ChemLabTweaker {

	@ZenMethod
	public static void addRecipe(String input1,int amount1,String input2,int amount2, ILiquidStack fluidIn, boolean allowSwap, IItemStack output, ILiquidStack fluidOut, int rfTick){
		MineTweakerAPI.apply(new addInputAction(TGMineTweakerHelper.toItemStackOreDict(input1),amount1,TGMineTweakerHelper.toItemStackOreDict(input2),amount2,fluidIn,allowSwap,output, fluidOut,rfTick));
	}
	
	@ZenMethod
	public static void addRecipe(IItemStack input1,int amount1,String input2,int amount2, ILiquidStack fluidIn, boolean allowSwap, IItemStack output, ILiquidStack fluidOut, int rfTick){
		MineTweakerAPI.apply(new addInputAction(TGMineTweakerHelper.toItemStackOreDict(input1),amount1,TGMineTweakerHelper.toItemStackOreDict(input2),amount2,fluidIn,allowSwap,output, fluidOut,rfTick));
	}
	
	@ZenMethod
	public static void addRecipe(String input1,int amount1,IItemStack input2,int amount2, ILiquidStack fluidIn, boolean allowSwap, IItemStack output, ILiquidStack fluidOut, int rfTick){
		MineTweakerAPI.apply(new addInputAction(TGMineTweakerHelper.toItemStackOreDict(input1),amount1,TGMineTweakerHelper.toItemStackOreDict(input2),amount2,fluidIn,allowSwap,output, fluidOut,rfTick));
	}
	
	@ZenMethod
	public static void addRecipe(IItemStack input1,int amount1,IItemStack input2,int amount2, ILiquidStack fluidIn, boolean allowSwap, IItemStack output, ILiquidStack fluidOut, int rfTick){
		MineTweakerAPI.apply(new addInputAction(TGMineTweakerHelper.toItemStackOreDict(input1),amount1,TGMineTweakerHelper.toItemStackOreDict(input2),amount2,fluidIn,allowSwap,output, fluidOut,rfTick));
	}
	
	@ZenMethod
	public static void removeRecipe(IItemStack output, ILiquidStack fluidOut){
		MineTweakerAPI.apply(new removeInputAction(output, fluidOut));
	}
	
	
	private static class addInputAction implements IUndoableAction
	{

		ChemLabRecipe added_recipe=null;
		ItemStackOreDict input1;
		ItemStackOreDict input2;
		//ItemStackOreDict bottle=null;
		FluidStack fluidIn;
		FluidStack fluidOut;
		boolean swap;
		ItemStack output;
		int power;
		int amount1;
		int amount2;
		
		public addInputAction(ItemStackOreDict input1, int amount1, ItemStackOreDict input2, int amount2, ILiquidStack fluidIn, boolean swap, IItemStack output,ILiquidStack fluidOut, int power) {
			super();
			this.input1 = input1;
			this.amount1=amount1;
			this.input2 = input2;
			this.amount2=amount2;
			this.fluidIn = MineTweakerMC.getLiquidStack(fluidIn);
			this.fluidOut = MineTweakerMC.getLiquidStack(fluidOut);
			this.swap = swap;
			this.output = MineTweakerMC.getItemStack(output);
			this.power = power;
			
			if (amount2<=0){
				this.input2 = new ItemStackOreDict((ItemStack)null);
			}
			
			if (this.fluidIn.amount<=0){
				this.fluidIn=null;
			}
			
			if (this.fluidOut.amount<=0){
				this.fluidOut=null;
			}
			
			if (this.output!=null && this.output.stackSize<=0){
				this.output=null;
			}
			
		}

		@Override
		public void apply() {
			added_recipe = new ChemLabRecipe(input1,amount1,input2,amount2,null,0,fluidIn,swap,output,fluidOut,power);
			ChemLabRecipes.getRecipes().add(added_recipe);
		}
		@Override
		public boolean canUndo() {
			return true;
		}
		@Override
		public String describe() {
			return "Add Recipe for"+(this.output==null?"NO_ITEM":this.output)+"/"+(this.fluidOut==null?"NO_FLUID":this.fluidOut.getUnlocalizedName())+" to ChemLab";
		}
		@Override
		public String describeUndo() {
			return "Removed Recipe for"+(this.output==null?"NO_ITEM":this.output)+"/"+(this.fluidOut==null?"NO_FLUID":this.fluidOut.getUnlocalizedName())+" from ChemLab";
		}
		@Override
		public Object getOverrideKey() {
			return null;
		}
		@Override
		public void undo() {
			ChemLabRecipes.getRecipes().remove(added_recipe);
		}
		
	}
	
	private static class removeInputAction implements IUndoableAction
	{

		ArrayList<ChemLabRecipe> removed_recipes=new ArrayList<ChemLabRecipe>();
		ItemStackOreDict input1;
		ItemStackOreDict input2;
		//ItemStackOreDict bottle=null;
		FluidStack fluidIn;
		FluidStack fluidOut;
		ItemStack output;
		boolean checkInput;
		
		public removeInputAction(IItemStack output,ILiquidStack fluidOut) {
			super();
			this.fluidOut = MineTweakerMC.getLiquidStack(fluidOut);
			this.output = MineTweakerMC.getItemStack(output);
			checkInput=false;
		}
		
		
		public removeInputAction(IItemStack output,ILiquidStack fluidOut, ItemStackOreDict input1, ItemStackOreDict input2, ILiquidStack fluidIn) {
			super();
			this.fluidOut = MineTweakerMC.getLiquidStack(fluidOut);
			this.output = MineTweakerMC.getItemStack(output);
			checkInput=true;
			this.input1=input1;
			this.input2=input2;
			this.fluidIn = MineTweakerMC.getLiquidStack(fluidIn);
		}
		
		
		
		@Override
		public void apply() {
			
			Iterator<ChemLabRecipe> iter = ChemLabRecipes.getRecipes().iterator();
			
			while (iter.hasNext()){
				ChemLabRecipe rec = iter.next();
								
					if (ItemUtil.isItemEqual(rec.output, this.output) && ItemUtil.isFluidEqual(rec.fluidOutput, this.fluidOut)){
						
						if (checkInput){
						
							if (rec.slot1.matches(input1) && rec.slot2.matches(input2) && ItemUtil.isFluidEqual(rec.fluidIn, this.fluidIn)){
								removed_recipes.add(rec);
								iter.remove();
							}	
								
						} else {
							removed_recipes.add(rec);
							iter.remove();
						}
					}
			}
			
		}
		@Override
		public boolean canUndo() {
			return true;
		}
		@Override
		public String describe() {
			return "Removed Recipe(s) for "+(this.output==null?"NO_ITEM":this.output)+"/"+(this.fluidOut==null?"NO_FLUID":this.fluidOut.getUnlocalizedName())+" from ChemLab";
		}
		@Override
		public String describeUndo() {
			return "Added Recipe(s) for "+(this.output==null?"NO_ITEM":this.output)+"/"+(this.fluidOut==null?"NO_FLUID":this.fluidOut.getUnlocalizedName())+" back to ChemLab";
		}
		@Override
		public Object getOverrideKey() {
			return null;
		}
		
		@Override
		public void undo() {
			for (ChemLabRecipe rec : removed_recipes){
				ChemLabRecipes.getRecipes().add(rec);
			}
		}
		
	}	
	
}
