package techguns.nei;

import java.awt.Rectangle;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import techguns.gui.GuiAmmoPress;
import techguns.gui.GuiFabricator;
import techguns.inventory.FabricatorRecipe;
import techguns.tileentities.FabricatorTileEntMaster;
import techguns.util.ItemUtil;
import techguns.util.TextUtil;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.Optional;

public class TechgunsFabricatorNEIRecipeHandler extends TemplateRecipeHandler {

	private static final String ID="Techguns:Fabricator";
	public static final int ICON_OFFSET_X=-5;
	public static final int ICON_OFFSET_Y=-11;
	private ResourceLocation texture_gui_elemets= GuiAmmoPress.texture;
	
	public static String getIDString(){
		return ID;
	}
	
	@Optional.Method(modid = "NotEnoughItems")
	@Override
	public String getRecipeName() {
		return TextUtil.trans("techguns.container.fabricator");
	}

	@Optional.Method(modid = "NotEnoughItems")
	@Override
	public String getGuiTexture() {
		return GuiFabricator.texture.toString();
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiFabricator.class;
	}
	
	public boolean isInRect(int mouseX, int mouseY, int x, int y, int width, int height){
		return mouseX>=x && mouseX<=(x+width) && mouseY >=y && mouseY<= (y+height);
	}
	
	/**
	 * Progress bar and stuff
	 */
    @Override
    public void drawExtras(int recipe)
    {
    	int color = 0xFFFFFFFF;

    	Minecraft.getMinecraft().getTextureManager().bindTexture(GuiFabricator.texture);
    	drawProgressBar(21+ICON_OFFSET_X,53+ICON_OFFSET_Y,0,167,91,10,5*20,0);
    	int power = FabricatorTileEntMaster.RF_TICK*100;
    	//int power = FabricatorRecipe.getRecipes().get(recipe).chargeAmount;
    	//int time = 5*20;
    	
    	Minecraft.getMinecraft().getTextureManager().bindTexture(texture_gui_elemets);
    	drawProgressBar(8+ICON_OFFSET_X, 17+ICON_OFFSET_Y, 251, 1, 4, 48, 5*20, 7);
    	
    	GuiDraw.gui.drawCenteredString(GuiDraw.fontRenderer, power+" RF", 57+ICON_OFFSET_X, 40+ICON_OFFSET_Y, color);
    	//GuiDraw.gui.drawCenteredString(GuiDraw.fontRenderer, time+" ticks", 57+ICON_OFFSET_X, 35+ICON_OFFSET_Y, color);
    }
	
	@Override
	public void loadTransferRects() {
		this.transferRects.add(new RecipeTransferRect(new Rectangle(20+ICON_OFFSET_X, 55+ICON_OFFSET_Y, 92, 12), getIDString()));
	}
	
	@Override
    public void loadCraftingRecipes(ItemStack result) {
		ArrayList<FabricatorRecipe> recipes = FabricatorRecipe.recipes;
		
		for(int i=0;i<recipes.size();i++){
			if (ItemUtil.isItemEqual(recipes.get(i).outputItem, result)){
				arecipes.add(new CachedFabricatorRecipe(recipes.get(i)));
			}
		}
    }
	
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		ArrayList<FabricatorRecipe> recipes = FabricatorRecipe.getRecipes();
		for(int i=0;i<recipes.size();i++){
			if (recipes.get(i).usesItem(ingredient)){
				arecipes.add(new CachedFabricatorRecipe(recipes.get(i)));
			}
		}
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals(this.ID)){
			this.loadAllRecipes();
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}
	
	public void loadAllRecipes(){
		ArrayList<FabricatorRecipe> recipes = FabricatorRecipe.getRecipes();
		for(int i=0;i<recipes.size();i++){
			arecipes.add(new CachedFabricatorRecipe(recipes.get(i)));
		}
	}
	
	
	public class CachedFabricatorRecipe extends CachedRecipe
    {
        public FabricatorRecipe recipe;

        public  CachedFabricatorRecipe(FabricatorRecipe recipe) {
            this.recipe = recipe;
        }

        @Override
        public PositionedStack getResult() {
        	PositionedStack stack = new PositionedStack(new ItemStack(recipe.outputItem.getItem(),recipe.amountOutput ,recipe.outputItem.getItemDamage()), 116+ICON_OFFSET_X, 50+ICON_OFFSET_Y);
            return stack;
        }

        @Override
        public ArrayList<PositionedStack> getIngredients() {
            ArrayList<PositionedStack> recipestacks = new ArrayList<PositionedStack>();
            recipestacks.add(new PositionedStack(recipe.inputItem.getItemStacks(recipe.amountInput), 19+ICON_OFFSET_X, 17+ICON_OFFSET_Y));
            if (!recipe.wireSlot.isNullStack()){
            	recipestacks.add(new PositionedStack(recipe.wireSlot.getItemStacks(recipe.amountWire), 47+ICON_OFFSET_X, 17+ICON_OFFSET_Y));
            }
            if (!recipe.powderSlot.isNullStack()){
            	recipestacks.add(new PositionedStack(recipe.powderSlot.getItemStacks(recipe.amountPowder), 68+ICON_OFFSET_X, 17+ICON_OFFSET_Y));
            }
            recipestacks.add(new PositionedStack(recipe.plateSlot.getItemStacks(recipe.amountPlates), 89+ICON_OFFSET_X, 17+ICON_OFFSET_Y));
            return (ArrayList<PositionedStack>) this.getCycledIngredients(cycleticks / 24, recipestacks);
        }
        
        
    }
	

}
