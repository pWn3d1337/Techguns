package techguns.nei;

import java.awt.Rectangle;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import techguns.gui.GuiAmmoPress;
import techguns.gui.GuiChargingStation;
import techguns.inventory.ChargingStationRecipe;
import techguns.util.ItemUtil;
import techguns.util.TextUtil;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "codechicken.nei.api.API", modid = "NotEnoughItems")
public class TechgunsChargingStationNEIRecipeHandler extends TemplateRecipeHandler {
	private static final String ID="Techguns:ChargingStation";
	public static final int ICON_OFFSET_X=-5;
	public static final int ICON_OFFSET_Y=-11;
	private ResourceLocation texture_gui_elemets= GuiAmmoPress.texture;
	
	public static String getIDString(){
		return ID;
	}
	
	@Optional.Method(modid = "NotEnoughItems")
	@Override
	public String getRecipeName() {
		return TextUtil.trans("techguns.container.chargingStation");
	}

	@Optional.Method(modid = "NotEnoughItems")
	@Override
	public String getGuiTexture() {
		return GuiChargingStation.texture.toString();
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiChargingStation.class;
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

    	Minecraft.getMinecraft().getTextureManager().bindTexture(GuiChargingStation.texture);
    	drawProgressBar(39+ICON_OFFSET_X,19+ICON_OFFSET_Y,0,167,30,10,5*20,0);
    	int power = ChargingStationRecipe.getRecipes().get(recipe).chargeAmount;
    	//int time = 5*20;
    	
    	Minecraft.getMinecraft().getTextureManager().bindTexture(texture_gui_elemets);
    	drawProgressBar(8+ICON_OFFSET_X, 17+ICON_OFFSET_Y, 251, 1, 4, 48, 5*20, 7);
    	
    	GuiDraw.gui.drawCenteredString(GuiDraw.fontRenderer, power+" RF", 57+ICON_OFFSET_X, 40+ICON_OFFSET_Y, color);
    	//GuiDraw.gui.drawCenteredString(GuiDraw.fontRenderer, time+" ticks", 57+ICON_OFFSET_X, 35+ICON_OFFSET_Y, color);
    }
	
	@Override
	public void loadTransferRects() {
		this.transferRects.add(new RecipeTransferRect(new Rectangle(38+ICON_OFFSET_X, 18+ICON_OFFSET_Y, 28, 12), getIDString()));
	}
	
	@Override
    public void loadCraftingRecipes(ItemStack result) {
		ArrayList<ChargingStationRecipe> recipes = ChargingStationRecipe.getRecipes();
		
		for(int i=0;i<recipes.size();i++){
			if (ItemUtil.isItemEqual(recipes.get(i).output, result)){
				arecipes.add(new CachedChargingStationRecipe(recipes.get(i)));
			}
		}
    }
	
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		ArrayList<ChargingStationRecipe> recipes = ChargingStationRecipe.getRecipes();
		for(int i=0;i<recipes.size();i++){
			if (recipes.get(i).input.isEqualWithOreDict(ingredient)){
				arecipes.add(new CachedChargingStationRecipe(recipes.get(i)));
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
		ArrayList<ChargingStationRecipe> recipes = ChargingStationRecipe.getRecipes();
		for(int i=0;i<recipes.size();i++){
			arecipes.add(new CachedChargingStationRecipe(recipes.get(i)));
		}
	}
	
	
	public class CachedChargingStationRecipe extends CachedRecipe
    {
        public ChargingStationRecipe recipe;

        public  CachedChargingStationRecipe(ChargingStationRecipe recipe) {
            this.recipe = recipe;
        }

        @Override
        public PositionedStack getResult() {
        	PositionedStack stack = new PositionedStack(recipe.output, 68+ICON_OFFSET_X, 17+ICON_OFFSET_Y);
            return stack;
        }

        @Override
        public ArrayList<PositionedStack> getIngredients() {
            ArrayList<PositionedStack> recipestacks = new ArrayList<PositionedStack>();
            recipestacks.add(new PositionedStack(recipe.input.getItemStacks(), 19+ICON_OFFSET_X, 17+ICON_OFFSET_Y));
            return (ArrayList<PositionedStack>) this.getCycledIngredients(cycleticks / 24, recipestacks);
        }
        
        
    }
	
}
