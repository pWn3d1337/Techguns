package techguns.nei;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import codechicken.nei.NEIClientConfig;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "codechicken.nei.api.API", modid = "NotEnoughItems")
public abstract class TechgunsLiquidTemplateRecipeHandler extends TemplateRecipeHandler {

	
	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId.equals("liquid") && ingredients[0] instanceof FluidStack && ((FluidStack)ingredients[0]).getFluid()!=null){
			this.loadUsageRecipesFluid((FluidStack) ingredients[0]);		
			
		} else if (inputId.equals(this.getIDString()) && ingredients[0] instanceof ItemStack){
			this.loadUsageRecipes((ItemStack) ingredients[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("liquid") && results[0] instanceof FluidStack && ((FluidStack)results[0]).getFluid()!=null){
			this.loadAllRecipesUsingFluid((FluidStack) results[0]);		
			
		} else if (outputId.equals(this.getIDString())){
			this.loadAllRecipes();
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}
	
	
	public abstract void loadAllRecipesUsingFluid(FluidStack fluidStack);

	public abstract void loadAllRecipes();

	public abstract String getIDString();

	public abstract void loadUsageRecipesFluid(FluidStack fluidStack);

	@Override
	public boolean keyTyped(GuiRecipe gui, char keyChar, int keyCode, int recipe) {
        if (keyCode == NEIClientConfig.getKeyBinding("gui.recipe")) {
            if (this.shouldTransferFluid(gui, recipe, false)) {
                return true;
            }
            
        } else if (keyCode == NEIClientConfig.getKeyBinding("gui.usage")) {
            if (this.shouldTransferFluid(gui, recipe, true)) {
                return true;
            }
        }
        return super.keyTyped(gui, keyChar, keyCode, recipe);
	}

	@Override
	public boolean mouseClicked(GuiRecipe gui, int button, int recipe) {
		if (button == 0){
		    if (this.shouldTransferFluid(gui, recipe, false)) {
                return true;
            }
            
        } else if (button==1) {
            if (this.shouldTransferFluid(gui, recipe, true)) {
                return true;
            }
		}
		return super.mouseClicked(gui, button, recipe);
	}
	
	
	public abstract boolean shouldTransferFluid(GuiRecipe gui, int recipe, boolean usage);
	
	public boolean transferFluid(FluidStack fluid, boolean usage){
		if (usage){
			
			if(!GuiUsageRecipe.openRecipeGui("liquid", new Object[]{fluid})){
				return false;
			}
			
		} else {
			
			if(!GuiCraftingRecipe.openRecipeGui("liquid", new Object[]{fluid})){
				return false;
			}
			
		}
		return true;
	}


	public boolean isInRect(int mouseX, int mouseY, int x, int y, int width, int height){
		return mouseX>=x && mouseX<=(x+width) && mouseY >=y && mouseY<= (y+height);
	}
	
	 /**
     * Copied form Gui.class
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    protected void drawTexturedModalRect(int p_73729_1_, int p_73729_2_, int p_73729_3_, int p_73729_4_, int p_73729_5_, int p_73729_6_)
    {
    	double zLevel=0D;
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(p_73729_1_ + 0), (double)(p_73729_2_ + p_73729_6_), (double)zLevel, (double)((float)(p_73729_3_ + 0) * f), (double)((float)(p_73729_4_ + p_73729_6_) * f1));
        tessellator.addVertexWithUV((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + p_73729_6_), (double)zLevel, (double)((float)(p_73729_3_ + p_73729_5_) * f), (double)((float)(p_73729_4_ + p_73729_6_) * f1));
        tessellator.addVertexWithUV((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + 0), (double)zLevel, (double)((float)(p_73729_3_ + p_73729_5_) * f), (double)((float)(p_73729_4_ + 0) * f1));
        tessellator.addVertexWithUV((double)(p_73729_1_ + 0), (double)(p_73729_2_ + 0), (double)zLevel, (double)((float)(p_73729_3_ + 0) * f), (double)((float)(p_73729_4_ + 0) * f1));
        tessellator.draw();
    }

    protected static void drawFluidWithTesselator(IIcon tex, int x, int y, int width, int height, int px){
		int offset =0;
    	int len=tex.getIconHeight();
    	while (offset < px){
    		if(offset+len>px){
    			len=px-offset;
    		}
    		drawTexturedModelRectFromIconFluidTank(x, y+height-offset-len, tex,width,len);
    		offset+=tex.getIconHeight();
    	}
	}
	
	protected static void drawTexturedModelRectFromIconFluidTank(int x, int y, IIcon icon, int w, int h)
    {
		double zLevel=0;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + h), (double)zLevel, (double)icon.getMinU(), (double)icon.getMaxV());
        tessellator.addVertexWithUV((double)(x + w), (double)(y + h), (double)zLevel, (double)icon.getMaxU(), (double)icon.getMaxV());
        tessellator.addVertexWithUV((double)(x + w), (double)(y + 0), (double)zLevel, (double)icon.getMaxU(), (double)icon.getInterpolatedV(icon.getIconHeight()-h));
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)zLevel, (double)icon.getMinU(), (double)icon.getInterpolatedV(icon.getIconHeight()-h));
        tessellator.draw();
    }
}
