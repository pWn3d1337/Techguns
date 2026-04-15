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
import techguns.inventory.ItemStackOreDict;
import techguns.inventory.MetalPressRecipes;
import techguns.inventory.MetalPressRecipes.MetalPressRecipe;
import techguns.util.ItemUtil;

@ZenClass("mods.techguns.MetalPress")
public class MetalPressTweaker {

	
	@ZenMethod
	public static void addRecipe(IItemStack input1,IItemStack input2, IItemStack output, boolean allowSwap){
		MineTweakerAPI.apply(new addInputAction(TGMineTweakerHelper.toItemStackOreDict(input1),TGMineTweakerHelper.toItemStackOreDict(input2),allowSwap,output));
	}
	@ZenMethod
	public static void addRecipe(String input1,IItemStack input2, IItemStack output, boolean allowSwap){
		MineTweakerAPI.apply(new addInputAction(TGMineTweakerHelper.toItemStackOreDict(input1),TGMineTweakerHelper.toItemStackOreDict(input2),allowSwap,output));
	}
	@ZenMethod
	public static void addRecipe(IItemStack input1,String input2, IItemStack output, boolean allowSwap){
		MineTweakerAPI.apply(new addInputAction(TGMineTweakerHelper.toItemStackOreDict(input1),TGMineTweakerHelper.toItemStackOreDict(input2),allowSwap,output));
	}
	@ZenMethod
	public static void addRecipe(String input1,String input2, IItemStack output, boolean allowSwap){
		MineTweakerAPI.apply(new addInputAction(TGMineTweakerHelper.toItemStackOreDict(input1),TGMineTweakerHelper.toItemStackOreDict(input2),allowSwap,output));
	}
	
	
	@ZenMethod
	public static void removeRecipe(IItemStack output){
		MineTweakerAPI.apply(new removeInputAction((ItemStackOreDict)null,(ItemStackOreDict)null,output));
	}
	
	@ZenMethod
	public static void removeRecipe(String input1,String input2, IItemStack output){
		MineTweakerAPI.apply(new removeInputAction(TGMineTweakerHelper.toItemStackOreDict(input1),TGMineTweakerHelper.toItemStackOreDict(input2),output));
	}
	@ZenMethod
	public static void removeRecipe(IItemStack input1,String input2, IItemStack output){
		MineTweakerAPI.apply(new removeInputAction(TGMineTweakerHelper.toItemStackOreDict(input1),TGMineTweakerHelper.toItemStackOreDict(input2),output));
	}
	@ZenMethod
	public static void removeRecipe(String input1,IItemStack input2, IItemStack output){
		MineTweakerAPI.apply(new removeInputAction(TGMineTweakerHelper.toItemStackOreDict(input1),TGMineTweakerHelper.toItemStackOreDict(input2),output));
	}
	@ZenMethod
	public static void removeRecipe(IItemStack input1,IItemStack input2, IItemStack output){
		MineTweakerAPI.apply(new removeInputAction(TGMineTweakerHelper.toItemStackOreDict(input1),TGMineTweakerHelper.toItemStackOreDict(input2),output));
	}
	
	
	private static class addInputAction implements IUndoableAction
	{

		MetalPressRecipe added_recipe=null;
		ItemStackOreDict input1;
		ItemStackOreDict input2;
		boolean swap;
		ItemStack output;
		
		public addInputAction(ItemStackOreDict input1, ItemStackOreDict input2, boolean swap, IItemStack output) {
			super();
			this.input1 = input1;
			this.input2 = input2;
			this.swap = swap;
			this.output = MineTweakerMC.getItemStack(output);
		}
		
		@Override
		public void apply() {
			added_recipe = new MetalPressRecipe(input1, input2, swap, output);
			MetalPressRecipes.getRecipes().add(added_recipe);
		}
		@Override
		public boolean canUndo() {
			return true;
		}
		@Override
		public String describe() {
			return "Add "+(input1)+"+"+(input2)+"-->"+output+" to MetalPress";
		}
		@Override
		public String describeUndo() {
			return "Removed "+(input1)+"+"+(input2)+"-->"+output+" from MetalPress";
		}
		@Override
		public Object getOverrideKey() {
			return null;
		}
		@Override
		public void undo() {
			MetalPressRecipes.getRecipes().remove(added_recipe);
		}
		
	}
	
	private static class removeInputAction implements IUndoableAction
	{

		ArrayList<MetalPressRecipe> removed_recipes = new ArrayList<MetalPressRecipe>();
		ItemStackOreDict input1;
		ItemStackOreDict input2;
		ItemStack output;
		
		public removeInputAction(ItemStackOreDict input1, ItemStackOreDict input2, IItemStack output) {
			super();
			this.input1 = input1;
			this.input2 = input2;
			this.output = MineTweakerMC.getItemStack(output);
		}
		
		@Override
		public void apply() {
			
			Iterator<MetalPressRecipe> iter = MetalPressRecipes.getRecipes().iterator();
			
			while (iter.hasNext()){
				MetalPressRecipe rec = iter.next();
								
					if (ItemUtil.isItemEqual(rec.output, this.output)){
						
						if (input1!=null){
						
							if (rec.slot1.matches(this.input1) && rec.slot2.matches(this.input2)){
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
			if (this.input1!=null){
				return "Removed Recipe "+input1+"+"+input2+"-->"+this.output+" from MetalPress";
			}
			return "Removed Recipe(s) for "+this.output+" from MetalPress";
		}
		@Override
		public String describeUndo() {
			if (this.input1!=null){
				return "Added Recipe "+input1+"+"+input2+"-->"+this.output+" to MetalPress";
			}
			return "Added Recipe(s) for "+this.output+" back to MetalPress";
		}
		@Override
		public Object getOverrideKey() {
			return null;
		}
		@Override
		public void undo() {
			for (MetalPressRecipe rec : removed_recipes){
				MetalPressRecipes.getRecipes().add(rec);
			}
		}
		
	}
	
}
