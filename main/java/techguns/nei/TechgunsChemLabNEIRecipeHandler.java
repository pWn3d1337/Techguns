package techguns.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import techguns.gui.GuiAmmoPress;
import techguns.gui.GuiChemLab;
import techguns.inventory.ChemLabRecipes;
import techguns.util.TextUtil;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "codechicken.nei.api.API", modid = "NotEnoughItems")
public class TechgunsChemLabNEIRecipeHandler extends TechgunsLiquidTemplateRecipeHandler {
	private static final String ID="Techguns:ChemLab";
	public static final int ICON_OFFSET_X=-5;
	public static final int ICON_OFFSET_Y=-11;
	private ResourceLocation texture_gui_elemets= GuiAmmoPress.texture;

	@Override
	public String getIDString(){
		return ID;
	}
	
	@Optional.Method(modid = "NotEnoughItems")
	@Override
	public String getRecipeName() {
		return TextUtil.trans("techguns.container.chemlab");
	}

	@Optional.Method(modid = "NotEnoughItems")
	@Override
	public String getGuiTexture() {
		return GuiChemLab.texture.toString();
	}
	
	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiChemLab.class;
	}
	
	
	@Override
	public void loadTransferRects() {
		this.transferRects.add(new RecipeTransferRect(new Rectangle(81+ICON_OFFSET_X-1, 19+ICON_OFFSET_Y-4, 53, 30), getIDString()));
	}

	@Override
	public void loadAllRecipesUsingFluid(FluidStack fluid){
		//System.out.println("Load all rec producing Fluid:"+fluid);
		for(ChemLabRecipes.ChemLabRecipe recipe : ChemLabRecipes.getRecipes()){
			if(recipe!=null && recipe.fluidOutput!=null && recipe.fluidOutput.getFluid()==fluid.getFluid()){
				
				ArrayList<ItemStack> inputs1 = recipe.slot1.getItemStacks();
				setStackSizes(inputs1, recipe.amounts[0]);
				ArrayList<ItemStack> inputs2 = recipe.slot2.getItemStacks();
				setStackSizes(inputs2, recipe.amounts[1]);
				ArrayList<ItemStack> inputs3 = new ArrayList<ItemStack>();
				if(recipe.slot3!=null){
					inputs3.add(recipe.slot3);
					setStackSizes(inputs3, recipe.amounts[2]);
				}
				arecipes.add(new CachedChemLabRecipe(new ChemLabRecipe(inputs1,inputs2,inputs3,recipe.output, recipe.fluidIn, recipe.fluidOutput,recipe.powerPerTick)));
			}
		}
		
	}
	
	@Override
	public void loadAllRecipes(){
		
		for(ChemLabRecipes.ChemLabRecipe recipe : ChemLabRecipes.getRecipes()){
			if(recipe!=null){
				ArrayList<ItemStack> inputs1 = recipe.slot1.getItemStacks();
				setStackSizes(inputs1, recipe.amounts[0]);
				ArrayList<ItemStack> inputs2 = recipe.slot2.getItemStacks();
				setStackSizes(inputs2, recipe.amounts[1]);
				ArrayList<ItemStack> inputs3 = new ArrayList<ItemStack>();
				if(recipe.slot3!=null){
					inputs3.add(recipe.slot3);
					setStackSizes(inputs3, recipe.amounts[2]);
				}
				arecipes.add(new CachedChemLabRecipe(new ChemLabRecipe(inputs1,inputs2,inputs3,recipe.output, recipe.fluidIn, recipe.fluidOutput,recipe.powerPerTick)));
			}
		}
	}
	
	@Override
	public List<String> handleTooltip(GuiRecipe gui, List<String> currenttip, int recipe) {
		int mx = GuiDraw.getMousePosition().x - (gui.width-176)/2 - gui.getRecipePosition(recipe).x;
		int my= GuiDraw.getMousePosition().y - (gui.height-166)/2 - gui.getRecipePosition(recipe).y;
		
    	CachedChemLabRecipe rec = (CachedChemLabRecipe) this.arecipes.get(recipe);
    	ChemLabRecipe chemrecipe = rec.recipe;
		
    	//System.out.println("M:"+mx+","+my);

    	//gui.firstGui.
    	//System.out.println("IOF_X:"+(this.ICON_OFFSET_X+18));
    	if(isInRect(mx,my,this.ICON_OFFSET_X+18, this.ICON_OFFSET_Y+17,12,52)){
    		if(chemrecipe.fluidIn!=null){
    			currenttip.add(TextUtil.trans(chemrecipe.fluidIn.getFluid().getUnlocalizedName()));
    		} else {
    			currenttip.add(TextUtil.trans("techguns.chemlab.nofluidin"));
    		}
    	}
    	
    	if(isInRect(mx,my,this.ICON_OFFSET_X+157, this.ICON_OFFSET_Y+17,12,52)){
    		if(chemrecipe.fluidOut!=null){
    			currenttip.add(TextUtil.trans(chemrecipe.fluidOut.getFluid().getUnlocalizedName()));
    		} else {
    			currenttip.add(TextUtil.trans("techguns.chemlab.nofluidout"));
    		}
    	}
		
		return super.handleTooltip(gui, currenttip, recipe);
	}

	
	
	
	@Override
	public boolean shouldTransferFluid(GuiRecipe gui, int recipe, boolean usage) {
		
		int mx = GuiDraw.getMousePosition().x - (gui.width-176)/2 - gui.getRecipePosition(recipe).x;
		int my= GuiDraw.getMousePosition().y - (gui.height-166)/2 - gui.getRecipePosition(recipe).y;
		
    	CachedChemLabRecipe rec = (CachedChemLabRecipe) this.arecipes.get(recipe);
    	ChemLabRecipe chemrecipe = rec.recipe;
		
    	//System.out.println("M:"+mx+","+my);

    	//gui.firstGui.
    	//System.out.println("IOF_X:"+(this.ICON_OFFSET_X+18));
    	if(isInRect(mx,my,this.ICON_OFFSET_X+18, this.ICON_OFFSET_Y+17,12,52)){
    		if (chemrecipe.fluidIn!=null){
    			
    			return this.transferFluid(chemrecipe.fluidIn, usage);
    			
    		}
    	}
    	
    	if(isInRect(mx,my,this.ICON_OFFSET_X+157, this.ICON_OFFSET_Y+17,12,52)){
    		if (chemrecipe.fluidOut!=null){
    			return this.transferFluid(chemrecipe.fluidOut, usage);
    		}
    	}
		
		
		return false;
	}

	@Override
    public void loadCraftingRecipes(ItemStack result) {
		ChemLabRecipes.ChemLabRecipe recipe = ChemLabRecipes.getRecipesFor(result);
		if(recipe!=null){
			ArrayList<ItemStack> inputs1 = recipe.slot1.getItemStacks();
			setStackSizes(inputs1, recipe.amounts[0]);
			ArrayList<ItemStack> inputs2 = recipe.slot2.getItemStacks();
			setStackSizes(inputs2, recipe.amounts[1]);
			ArrayList<ItemStack> inputs3 = new ArrayList<ItemStack>();
			if(recipe.slot3!=null){
				inputs3.add(recipe.slot3);
				setStackSizes(inputs3, recipe.amounts[2]);
			}
			arecipes.add(new CachedChemLabRecipe(new ChemLabRecipe(inputs1,inputs2,inputs3,recipe.output, recipe.fluidIn, recipe.fluidOutput,recipe.powerPerTick)));
		}
        
    }
	
	
	private static void setStackSizes(List<ItemStack> list, int amount){
		for (int i=0;i<list.size();i++){
			list.get(i).stackSize=amount;
		}
	}
	
	

	@Override
	public void loadUsageRecipesFluid(FluidStack fluid){
		for(ChemLabRecipes.ChemLabRecipe recipe : ChemLabRecipes.getRecipes()){
			if(recipe!=null && recipe.fluidIn!=null && recipe.fluidIn.getFluid()==fluid.getFluid()){
				ArrayList<ItemStack> inputs1 = recipe.slot1.getItemStacks();
				setStackSizes(inputs1, recipe.amounts[0]);
				ArrayList<ItemStack> inputs2 = recipe.slot2.getItemStacks();
				setStackSizes(inputs2, recipe.amounts[1]);
				ArrayList<ItemStack> inputs3 = new ArrayList<ItemStack>();
				if(recipe.slot3!=null){
					inputs3.add(recipe.slot3);
					setStackSizes(inputs3, recipe.amounts[2]);
				}
				arecipes.add(new CachedChemLabRecipe(new ChemLabRecipe(inputs1,inputs2,inputs3,recipe.output, recipe.fluidIn, recipe.fluidOutput,recipe.powerPerTick)));
			}
		}        
	}
	
	
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if (ChemLabRecipes.hasRecipeUsing(ingredient)){
			ArrayList<ChemLabRecipes.ChemLabRecipe> recipes = ChemLabRecipes.getRecipesUsing(ingredient);
			
			for (ChemLabRecipes.ChemLabRecipe r : recipes){
				ArrayList<ItemStack> slot1 = r.slot1.getItemStacks();
				setStackSizes(slot1, r.amounts[0]);
				ArrayList<ItemStack> slot2 = r.slot2.getItemStacks();
				setStackSizes(slot2, r.amounts[1]);
				ArrayList<ItemStack> slot3 = new ArrayList<ItemStack>();
				if(r.slot3!=null){
					slot3.add(r.slot3);
					setStackSizes(slot3, r.amounts[2]);
				}
				
				arecipes.add(new CachedChemLabRecipe(new ChemLabRecipe(slot1, slot2, slot3,r.output,r.fluidIn, r.fluidOutput,r.powerPerTick)));
			}
			
		}
	}
	
   	
	/**
	 * Progress bar and stuff
	 */
    @Override
    public void drawExtras(int recipe)
    {
    	int color = 0xFFFFFFFF;
    	//drawProgressBar(123+ICON_OFFSET_X,36+ICON_OFFSET_Y,180,0,10,21,5*20,1);
    	/*progress bar*/
    	int ticks = 100;
    	float prog = cycleticks % ticks / (float) ticks;
    	int k = ICON_OFFSET_X;
    	int l = ICON_OFFSET_Y;
    	
    	int i1 = (int) ((prog<0.2f?(prog*5):1.0f)*25);
    	this.drawTexturedModalRect(k+81, l+19, 178, 5, 8, i1);
    	
    	if (prog>=0.2f){
        	int i2 = (int) ((prog<0.4f?(prog-0.2f)*5:1.0f)*9);
        	this.drawTexturedModalRect(k+88, l+39, 186, 25, i2, 1);
    	}
    	
    	if (prog>=0.4f){
        	int i3 = (int) ((prog<0.6f?(prog-0.4f)*5:1.0f)*20);
        	this.drawTexturedModalRect(k+97, l+23+(20-i3), 194, 9+(20-i3), 14, i3);
    	}
    	
    	if (prog>=0.6f){
        	int i4 = (int) ((prog<0.8f?(prog-0.6f)*5:1.0f)*16);
        	this.drawTexturedModalRect(k+108, l+17, 205, 3, i4, 6);
    	}
    	if (prog>=0.8f){
        	int i5 = (int) (((prog-0.8f)*5)*24);
        	this.drawTexturedModalRect(k+117, l+20, 214, 6, 14, i5+1);
    	}
    	/*progress bar end*/
    	
    	CachedChemLabRecipe rec = (CachedChemLabRecipe) this.arecipes.get(recipe);
    	ChemLabRecipe chemrecipe = rec.recipe;
    	
    	int power = chemrecipe.rftick;
    	//int time = 100;
    	
    	//ChemLabRecipes.ChemLabRecipe chemrecipe= ChemLabRecipes.getRecipe(recipe);
    	
    	

    	
    	GuiDraw.gui.drawCenteredString(GuiDraw.fontRenderer, power+" RF/t", 105+ICON_OFFSET_X, 50+ICON_OFFSET_Y, color);
    	//GuiDraw.gui.drawCenteredString(GuiDraw.fontRenderer, time+" ticks", 97+ICON_OFFSET_X, 55+ICON_OFFSET_Y, color);
    	
    	if(chemrecipe.fluidIn!=null){
    		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
    		IIcon tex = chemrecipe.fluidIn.getFluid().getIcon();
    		int px = (int) (chemrecipe.fluidIn.amount*50.0f/2000);
        	this.drawFluidWithTesselator(tex, k+19, l+17, 10, 50, px);
        	//GuiDraw.gui.drawCenteredString(GuiDraw.fontRenderer, chemrecipe.fluidIn.amount+" mB", 35+ICON_OFFSET_X, 50+ICON_OFFSET_Y, color);
        	GuiDraw.fontRenderer.drawStringWithShadow( chemrecipe.fluidIn.amount+" mB", 35+ICON_OFFSET_X, 60+ICON_OFFSET_Y, color);
    	}
    	if(chemrecipe.fluidOut!=null){
    		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
    		IIcon tex = chemrecipe.fluidOut.getFluid().getIcon();
    		int px = (int) (chemrecipe.fluidOut.amount*50.0f/2000);
        	this.drawFluidWithTesselator(tex, k+158, l+17, 10, 50, px);
        	//GuiDraw.gui.drawCenteredString(GuiDraw.fontRenderer, chemrecipe.fluidOutput.amount+" mB", 120+ICON_OFFSET_X, 50+ICON_OFFSET_Y, color);
        	GuiDraw.fontRenderer.drawStringWithShadow(chemrecipe.fluidOut.amount+" mB", 115+ICON_OFFSET_X, 60+ICON_OFFSET_Y, color);
    	}
    	
    	 Minecraft.getMinecraft().getTextureManager().bindTexture(GuiChemLab.texture);
         this.drawTexturedModalRect(k+18, l+17, 176, 32, 12, 52);
         this.drawTexturedModalRect(k+157, l+17, 176, 32, 12, 52);
    	
    	Minecraft.getMinecraft().getTextureManager().bindTexture(texture_gui_elemets);
    	drawProgressBar(8+ICON_OFFSET_X, 17+ICON_OFFSET_Y, 251, 1, 4, 48, 5*20, 7);
    }
    
    public static class ChemLabRecipe
    {
        public PositionedStack input1;
        public PositionedStack input2;
        public PositionedStack input3;
        public PositionedStack output;
        public FluidStack fluidIn;
        public FluidStack fluidOut;
        public int rftick;

        public ChemLabRecipe(List<ItemStack> input1, List<ItemStack> input2, List<ItemStack> input3, ItemStack output, FluidStack fluidIn, FluidStack fluidOut,int rf) {

	        	if(input1.size()>0){
	        		this.input1= new PositionedStack(input1, 35+ICON_OFFSET_X, 17+ICON_OFFSET_Y);
	        	}else {
	        		this.input1=null;
	        	}

        		if(input2.size()>0){
        			this.input2=new PositionedStack(input2, 57+ICON_OFFSET_X, 17+ICON_OFFSET_Y);
        		} else {
        			this.input2=null;
        		}
        		
        		if(input3.size()>0){
        			this.input3=new PositionedStack(input3, 35+ICON_OFFSET_X, 40+ICON_OFFSET_Y);
        		} else {
        			this.input3=null;
        		}
        		
        		if(output!=null){
        		this.output = new PositionedStack(new ItemStack(output.getItem(), output.stackSize, output.getItemDamage()), 135+ICON_OFFSET_X, 17+ICON_OFFSET_Y);
        		} else {
        			this.output=null;
        		}
        		this.rftick=rf;
        		if(fluidIn!=null){
        			this.fluidIn = new FluidStack(fluidIn, fluidIn.amount);
        		} else {
        			this.fluidIn=null;
        		}
        		if(fluidOut!=null){
        			this.fluidOut = new FluidStack(fluidOut, fluidOut.amount);
        		} else {
        			this.fluidOut=null;
        		}
        }
    }
    public class CachedChemLabRecipe extends CachedRecipe
    {
        public ChemLabRecipe recipe;

        public  CachedChemLabRecipe(ChemLabRecipe recipe) {
            this.recipe = recipe;
        }

        @Override
        public PositionedStack getResult() {
            return recipe.output;
        }

        @Override
        public ArrayList<PositionedStack> getIngredients() {
            ArrayList<PositionedStack> recipestacks = new ArrayList<PositionedStack>();
            if(recipe.input1!=null){
            	recipestacks.add(recipe.input1);
            }
            if(recipe.input2!=null){
            	recipestacks.add(recipe.input2);
            }
            if(recipe.input3!=null){
            	recipestacks.add(recipe.input3);
            }
            return (ArrayList<PositionedStack>) this.getCycledIngredients(cycleticks / 24, recipestacks);
        }
        
        
    }

}
