package techguns.plugins.minetweaker;

import java.util.ArrayList;
import java.util.Iterator;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import techguns.inventory.ChargingStationRecipe;
import techguns.inventory.ItemStackOreDict;

@ZenClass("mods.techguns.ChargingStation")
public class CharginStationTweaker {
	
	private CharginStationTweaker(){};
	
	@ZenMethod
	public static void addRecipe(IItemStack input, IItemStack output, int RFAmount){
		MineTweakerAPI.apply(new addInputAction(new ItemStackOreDict(MineTweakerMC.getItemStack(input)), MineTweakerMC.getItemStack(output), RFAmount));
	}

	@ZenMethod
	public static void addRecipe(String input, IItemStack output, int RFAmount){
		MineTweakerAPI.apply(new addInputAction(new ItemStackOreDict(input), MineTweakerMC.getItemStack(output), RFAmount));
	}
	
	@ZenMethod
	public static void removeRecipe(IItemStack input){
		MineTweakerAPI.apply(new removeInputAction(new ItemStackOreDict(MineTweakerMC.getItemStack(input))));
	}

	@ZenMethod
	public static void removeRecipe(String input){
		MineTweakerAPI.apply(new removeInputAction(new ItemStackOreDict(input)));
	}
	
	
	private static class addInputAction implements IUndoableAction
	{

		ChargingStationRecipe added_recipe=null;
		ItemStackOreDict input;
		ItemStack output;
		int rf;
		
		public addInputAction(ItemStackOreDict input, ItemStack output, int RFAmount) {
			super();
			this.input = input;
			this.output=output;
			this.rf = RFAmount;
		}

		@Override
		public void apply() {
			this.added_recipe=ChargingStationRecipe.addRecipe(input, output, rf);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public String describe() {
			return "Add "+(input)+" to ChargingStation";
		}

		@Override
		public String describeUndo() {
			return "Remove "+(input)+" from CharingStation";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public void undo() {
			ChargingStationRecipe.getRecipes().remove(this.added_recipe);
		}
		
	}
	
	private static class removeInputAction implements IUndoableAction
	{

		ItemStackOreDict input;
		
		ArrayList<ChargingStationRecipe> removed_recipes=new ArrayList<ChargingStationRecipe>();
		
		public removeInputAction(ItemStackOreDict input) {
			this.input=input;
		}

		@Override
		public void apply() {
			ArrayList<ChargingStationRecipe> recipes = ChargingStationRecipe.getRecipes();
			
			Iterator<ChargingStationRecipe> iter = recipes.iterator();
			
			while (iter.hasNext()){
				
				ChargingStationRecipe rec = iter.next();
				
				if (rec.input.matches(this.input)){
					removed_recipes.add(rec);
					iter.remove();					
				}
			}
			
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public String describe() {
			return "Remove "+(input)+" from ChargingStation."; //: "+removed_recipes.size()+ " removed recipe(s)";
		}

		@Override
		public String describeUndo() {
			return "Add "+(input)+" to ChargingStation"; //: "+removed_recipes.size()+ " added recipe(s)";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public void undo() {
			ArrayList<ChargingStationRecipe> recipes = ChargingStationRecipe.getRecipes();
			
			for (ChargingStationRecipe rec: removed_recipes){
				recipes.add(rec);
			}
		}
		
	}
	
}
