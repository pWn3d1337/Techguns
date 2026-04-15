package techguns.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import techguns.TGItems;
import techguns.gui.GuiAmmoPress;
import techguns.inventory.AmmoPressBuildPlans;
import techguns.util.TextUtil;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "codechicken.nei.api.API", modid = "NotEnoughItems")
public class TechgunsAmmoPressNEIRecipeHandler extends TemplateRecipeHandler {
	private static final String ID = "Techguns:AmmoPress";
	public static final int ICON_OFFSET_X=-5;
	public static final int ICON_OFFSET_Y=-11;

	@Optional.Method(modid = "NotEnoughItems")
	@Override
	public String getRecipeName() {
		return TextUtil.trans("techguns.container.ammopress");
	}

	@Optional.Method(modid = "NotEnoughItems")
	@Override
	public String getGuiTexture() {
		return GuiAmmoPress.texture.toString();
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiAmmoPress.class;
	}

	@Override
	public void loadTransferRects() {
		this.transferRects.add(new RecipeTransferRect(new Rectangle(this.ICON_OFFSET_X+123-1, this.ICON_OFFSET_Y+36-1, 12, 23), getIDString()));
	}
	
	/*@Override
	public void loadTransferRects() {
		this.transferRects.add(new RecipeTransferRect(new Rectangle(123, 36, 10, 21), getIDString()));
	}*/
	
	public static String getIDString(){
		return ID;
	}
	
	/**
	 * Progress bar and stuff
	 */
    @Override
    public void drawExtras(int recipe)
    {
    	int color = 0xFFFFFFFF;
    	drawProgressBar(8+ICON_OFFSET_X, 17+ICON_OFFSET_Y, 251, 1, 4, 48, 5*20, 7);
    	drawProgressBar(123+ICON_OFFSET_X,36+ICON_OFFSET_Y,180,0,10,21,5*20,1);
    	int power = AmmoPressBuildPlans.getTotalPower(recipe);
    	int time = 5*20;
    	
    	GuiDraw.gui.drawCenteredString(GuiDraw.fontRenderer, power+" RF", 57+ICON_OFFSET_X, 25+ICON_OFFSET_Y, color);
    	GuiDraw.gui.drawCenteredString(GuiDraw.fontRenderer, time+" ticks", 57+ICON_OFFSET_X, 35+ICON_OFFSET_Y, color);
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
		
		for(ItemStack result: AmmoPressBuildPlans.getPossibleResults()){
			arecipes.add(new CachedAmmoPressRecipe(new AmmoPressRecipe(AmmoPressBuildPlans.metal1,AmmoPressBuildPlans.metal2,AmmoPressBuildPlans.powder,result)));
		}
	}
	
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		ArrayList<ItemStack> ingr = new ArrayList<ItemStack>();
		
		if(AmmoPressBuildPlans.isInList((ingredient),AmmoPressBuildPlans.metal1)){

			for(ItemStack result: AmmoPressBuildPlans.getPossibleResults()){
				ingr.add(ingredient);
				arecipes.add(new CachedAmmoPressRecipe(new AmmoPressRecipe(ingr,AmmoPressBuildPlans.metal2,AmmoPressBuildPlans.powder,result)));
			}
			
		} else if (AmmoPressBuildPlans.isInList((ingredient),AmmoPressBuildPlans.metal2)){

			ItemStack ingr2 = ingredient.copy();
			ingr2.stackSize=2;
			for(ItemStack result: AmmoPressBuildPlans.getPossibleResults()){
				
				//ingr.add(TGItems.newStack(ingredient,2));
				ingr.add(ingr2);
				arecipes.add(new CachedAmmoPressRecipe(new AmmoPressRecipe(AmmoPressBuildPlans.metal1,ingr,AmmoPressBuildPlans.powder,result)));
			}
			
		} else if (AmmoPressBuildPlans.isInList((ingredient),AmmoPressBuildPlans.powder)){

			for(ItemStack result: AmmoPressBuildPlans.getPossibleResults()){
				ingr.add(ingredient);
				arecipes.add(new CachedAmmoPressRecipe(new AmmoPressRecipe(AmmoPressBuildPlans.metal1,AmmoPressBuildPlans.metal2,ingr,result)));
			}
		}
	}

	@Override
    public void loadCraftingRecipes(ItemStack result) {
        if (result.getItem() != TGItems.sharedItem) return;
        int damage = result.getItemDamage();

        if(damage == TGItems.bullets9mm.getItemDamage() || damage == TGItems.shotgunShell.getItemDamage() || damage ==TGItems.rifleBullets.getItemDamage()
        		|| damage == TGItems.highCalibreBullets.getItemDamage()){
        	
        	ItemStack ammo = TGItems.newStack(result, AmmoPressBuildPlans.getAmountForRecipe(result.getItemDamage()));
        	arecipes.add(new CachedAmmoPressRecipe(new AmmoPressRecipe(AmmoPressBuildPlans.metal1,AmmoPressBuildPlans.metal2,AmmoPressBuildPlans.powder,ammo)));
        	
        } else {
        	return;
        }
        
    }
	
    public static class AmmoPressRecipe
    {
        public PositionedStack metal1;
        public PositionedStack metal2;
        public PositionedStack gunpowder;
        public PositionedStack ammo;

        public AmmoPressRecipe(List<ItemStack> metal1, List<ItemStack> metal2,List<ItemStack> gunpowder, ItemStack ammo) {
            this.metal1 = new PositionedStack(metal1, 100+ICON_OFFSET_X, 17+ICON_OFFSET_Y);
            this.metal2 = new PositionedStack(metal2, 120+ICON_OFFSET_X, 17+ICON_OFFSET_Y);
            this.gunpowder = new PositionedStack(gunpowder, 140+ICON_OFFSET_X, 17+ICON_OFFSET_Y);
            this.ammo = new PositionedStack(ammo,120+ICON_OFFSET_X,60+ICON_OFFSET_Y);
        }
    }
	
	public class CachedAmmoPressRecipe extends CachedRecipe
    {
        public AmmoPressRecipe recipe;

        public  CachedAmmoPressRecipe(AmmoPressRecipe recipe) {
            this.recipe = recipe;
        }

        @Override
        public PositionedStack getResult() {
            return recipe.ammo;
        }

        @Override
        public ArrayList<PositionedStack> getIngredients() {
            ArrayList<PositionedStack> recipestacks = new ArrayList<PositionedStack>();
            recipestacks.add(recipe.metal1);
            recipestacks.add(recipe.metal2);
            recipestacks.add(recipe.gunpowder);
            return (ArrayList<PositionedStack>) this.getCycledIngredients(cycleticks / 24, recipestacks);
        }
        
        
    }
	
	
}
