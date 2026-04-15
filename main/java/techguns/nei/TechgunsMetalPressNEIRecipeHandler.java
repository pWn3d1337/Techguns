package techguns.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import techguns.gui.GuiAmmoPress;
import techguns.gui.GuiMetalPress;
import techguns.inventory.MetalPressRecipes;
import techguns.util.TextUtil;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "codechicken.nei.api.API", modid = "NotEnoughItems")
public class TechgunsMetalPressNEIRecipeHandler extends TemplateRecipeHandler {
	private static final String ID = "Techguns:MetalPress";
	public static final int ICON_OFFSET_X=-5;
	public static final int ICON_OFFSET_Y=-11;
	private ResourceLocation texture_gui_elemets= GuiAmmoPress.texture;
	
	@Optional.Method(modid = "NotEnoughItems")
	@Override
	public String getRecipeName() {
		 return TextUtil.trans("techguns.container.metalpress");
	}

	@Optional.Method(modid = "NotEnoughItems")
	@Override
	public String getGuiTexture() {
		return GuiMetalPress.texture.toString();
	}
	
	@Override
	public void loadTransferRects() {
		this.transferRects.add(new RecipeTransferRect(new Rectangle(this.ICON_OFFSET_X+123-1, this.ICON_OFFSET_Y+36-1, 12, 23), getIDString()));
	}
	
	public static String getIDString(){
		return ID;
	}
	
	public void loadAllRecipes(){
		
		for(MetalPressRecipes.MetalPressRecipe recipe : MetalPressRecipes.getRecipes()){
			if(recipe!=null){
				ArrayList<ItemStack> inputs1 = recipe.getInputs(1);
				ArrayList<ItemStack> inputs2 = recipe.getInputs(2);

				arecipes.add(new CachedMetalPressRecipe(new MetalPressRecipe(inputs1,inputs2,recipe.output)));
			}
		}
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals(ID)){
			this.loadAllRecipes();
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	/**
	 * Progress bar and stuff
	 */
    @Override
    public void drawExtras(int recipe)
    {
    	int color = 0xFFFFFFFF;
    	Minecraft.getMinecraft().renderEngine.bindTexture(texture_gui_elemets);
    	drawProgressBar(8+ICON_OFFSET_X, 17+ICON_OFFSET_Y, 251, 1, 4, 48, 5*20, 7);
    	drawProgressBar(123+ICON_OFFSET_X,36+ICON_OFFSET_Y,180,0,10,21,5*20,1);
    	int power = MetalPressRecipes.getTotalPower(recipe);
    	int time = MetalPressRecipes.getTotaltime(recipe);
    	
    	GuiDraw.gui.drawCenteredString(GuiDraw.fontRenderer, power+" RF", 57+ICON_OFFSET_X, 25+ICON_OFFSET_Y, color);
    	GuiDraw.gui.drawCenteredString(GuiDraw.fontRenderer, time+" ticks", 57+ICON_OFFSET_X, 35+ICON_OFFSET_Y, color);
    }
	
	
    
	@Override
	public void loadCraftingRecipes(ItemStack result) {
		MetalPressRecipes.MetalPressRecipe recipe = MetalPressRecipes.getRecipesFor(result);
		if(recipe!=null){
			ArrayList<ItemStack> inputs1 = recipe.getInputs(1);
			ArrayList<ItemStack> inputs2 = recipe.getInputs(2);
			
			/*for (ItemStack stack1 : inputs1){
				for (ItemStack stack2 : inputs2){
					arecipes.add(new CachedMetalPressRecipe(new MetalPressRecipe(stack1,stack2,result)));
				}
			}*/
			ItemStack res2 = result.copy();
			res2.stackSize = recipe.output.stackSize;
			arecipes.add(new CachedMetalPressRecipe(new MetalPressRecipe(inputs1,inputs2,res2)));
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (MetalPressRecipes.hasRecipeUsing(ingredient)){
			ArrayList<MetalPressRecipes.MetalPressRecipe> recipes = MetalPressRecipes.getRecipesUsing(ingredient);
			
			for (MetalPressRecipes.MetalPressRecipe r : recipes){
				ArrayList<ItemStack> slot1 = r.slot1.getItemStacks();
				ArrayList<ItemStack> slot2 = r.slot2.getItemStacks();
				/*
				for (ItemStack stack1: slot1){
					for (ItemStack stack2: slot2){
						if (OreDictionary.itemMatches(stack1, ingredient, true) || OreDictionary.itemMatches(stack2, ingredient, true)){
							arecipes.add(new CachedMetalPressRecipe(new MetalPressRecipe(stack1, stack2, r.output)));
							if (r.allowSwap){
								arecipes.add(new CachedMetalPressRecipe(new MetalPressRecipe(stack2, stack1, r.output)));
							}
						}
					}
				}*/
				arecipes.add(new CachedMetalPressRecipe(new MetalPressRecipe(slot1, slot2, r.output)));
			}
			
		}
	}

	

	public static class MetalPressRecipe
    {
        public PositionedStack input1;
        public PositionedStack input2;
        public PositionedStack output;

       /*public MetalPressRecipe(ItemStack input1, ItemStack input2,ItemStack output) {
            this.input1 = new PositionedStack(new ItemStack(input1.getItem(), input1.stackSize, input1.getItemDamage()), 110+ICON_OFFSET_X, 17+ICON_OFFSET_Y);
            this.input2 = new PositionedStack(new ItemStack(input2.getItem(), input2.stackSize, input2.getItemDamage()), 130+ICON_OFFSET_X, 17+ICON_OFFSET_Y);
            this.output = new PositionedStack(new ItemStack(output.getItem(), output.stackSize, output.getItemDamage()), 120+ICON_OFFSET_X,60+ICON_OFFSET_Y);
        }*/
        public MetalPressRecipe(List<ItemStack> input1, List<ItemStack> input2,ItemStack output) {
        	//for (ItemStack input : input1){
        		this.input1= new PositionedStack(input1, 110+ICON_OFFSET_X, 17+ICON_OFFSET_Y);
        	//}
        	//for (ItemStack input : input2){
        		this.input2=new PositionedStack(input2, 130+ICON_OFFSET_X, 17+ICON_OFFSET_Y);
        	//}
            this.output = new PositionedStack(new ItemStack(output.getItem(), output.stackSize, output.getItemDamage()), 120+ICON_OFFSET_X,60+ICON_OFFSET_Y);
        }
    }
	
	public class CachedMetalPressRecipe extends CachedRecipe
    {
        public MetalPressRecipe recipe;

        public CachedMetalPressRecipe(MetalPressRecipe recipe) {
            this.recipe = recipe;
        }

        @Override
        public PositionedStack getResult() {
            return recipe.output;
        }

        
        
        @Override
		public List<PositionedStack> getCycledIngredients(int cycle, List<PositionedStack> ingredients) {
			return super.getCycledIngredients(cycle, ingredients);
		}

		@Override
        public ArrayList<PositionedStack> getIngredients() {			
            ArrayList<PositionedStack> recipestacks = new ArrayList<PositionedStack>();
            recipestacks.add(recipe.input1);
            recipestacks.add(recipe.input2);
			return (ArrayList<PositionedStack>) getCycledIngredients(cycleticks / 24, recipestacks);
            //return recipestacks;
        }
    }

}
