package techguns.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import techguns.gui.GuiAmmoPress;
import techguns.gui.GuiReactionChamber;
import techguns.inventory.ReactionBeamFocus;
import techguns.inventory.ReactionChamberRecipe;
import techguns.inventory.ReactionChamberRecipe.RiskType;
import techguns.tileentities.ReactionChamberOperation;
import techguns.util.TextUtil;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.Optional;

public class TechgunsReactionChamberNEIRecipeHandler extends TechgunsLiquidTemplateRecipeHandler {
	
	private static final String ID="Techguns:ReactionChamber";
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
		return TextUtil.trans("techguns.container.reactionChamber");
	}

	@Optional.Method(modid = "NotEnoughItems")
	@Override
	public String getGuiTexture() {
		return GuiReactionChamber.texture.toString();
	}
	
	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiReactionChamber.class;
	}
		
	@Override
    public void loadCraftingRecipes(ItemStack result) {
		ArrayList<ReactionChamberRecipe> recipes = ReactionChamberRecipe.getRecipes();
		
		for(int i=0;i<recipes.size();i++){
			if (recipes.get(i).hasResult(result)){
				arecipes.add(new CachedReactionChamberRecipe(recipes.get(i)));
			}
		}
    }
	
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		ArrayList<ReactionChamberRecipe> recipes = ReactionChamberRecipe.getRecipes();
		for(int i=0;i<recipes.size();i++){
			if (recipes.get(i).usesItem(ingredient)){
				arecipes.add(new CachedReactionChamberRecipe(recipes.get(i)));
			}
		}
	}
	
	/*@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals(this.ID)){
			this.loadAllRecipes();
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}*/
	
	@Override
	public void loadAllRecipes(){
		ArrayList<ReactionChamberRecipe> recipes = ReactionChamberRecipe.getRecipes();
		for(int i=0;i<recipes.size();i++){
			arecipes.add(new CachedReactionChamberRecipe(recipes.get(i)));
		}
	}
	
	
	@Override
	public void loadTransferRects() {
		this.transferRects.add(new RecipeTransferRect(new Rectangle(66+ICON_OFFSET_X, 60+ICON_OFFSET_Y, 102, 14), getIDString()));
	}
	
	
	private int getDangerType(int recipeID){
		ReactionChamberRecipe rec = ReactionChamberRecipe.getById((short) recipeID);
		
		if ((rec.risk == RiskType.NONE || rec.risk == RiskType.BREAK_ITEM)) {
				//if( rec.instability == 0f){
				if (rec.intensityMargin==0) {
					return 0;
				} else {
					return 1;
				}
		} else if (rec.risk == RiskType.EXPLOSION_LOW){
			return 2;
		} else {
			return 3;
		}
		
	}
	
	
	@Override
	public void loadAllRecipesUsingFluid(FluidStack fluidStack) {
		//No resulting fluids
	}

	@Override
	public void loadUsageRecipesFluid(FluidStack fluidStack) {
		ArrayList<ReactionChamberRecipe> recipes = ReactionChamberRecipe.getRecipes();
		for(int i=0;i<recipes.size();i++){
			if (recipes.get(i).liquidIn == fluidStack.getFluid()){
				arecipes.add(new CachedReactionChamberRecipe(recipes.get(i)));
			}
		}
	}

	@Override
	public boolean shouldTransferFluid(GuiRecipe gui, int recipe, boolean usage) {
		int mx = GuiDraw.getMousePosition().x - (gui.width-176)/2 - gui.getRecipePosition(recipe).x;
		int my= GuiDraw.getMousePosition().y - (gui.height-166)/2 - gui.getRecipePosition(recipe).y;
		
    	CachedReactionChamberRecipe rec = (CachedReactionChamberRecipe) this.arecipes.get(recipe);
    	ReactionChamberRecipe r = rec.recipe;
		
		if(isInRect(mx,my,this.ICON_OFFSET_X+18, this.ICON_OFFSET_Y+17,12,52)){
    		if(r.liquidIn!=null){
    			return this.transferFluid(new FluidStack(r.liquidIn, r.liquidLevel*1000), usage);    			
    		} else {
    			return false;
    		}
    	}
		return false;
	}

	/**
	 * Progress bar and stuff
	 */
    @Override
    public void drawExtras(int recipe)
    {
    
    	//ReactionChamberRecipe rec = ReactionChamberRecipe.getById((short) recipe);
    	CachedReactionChamberRecipe crec = (CachedReactionChamberRecipe) this.arecipes.get(recipe);
    	ReactionChamberRecipe rec = crec.recipe;
    	
    	
    	int color = 0xFFFFFFFF;

    	Minecraft.getMinecraft().getTextureManager().bindTexture(GuiReactionChamber.texture);
    	drawProgressBar(67+ICON_OFFSET_X,61+ICON_OFFSET_Y,0,167,105,6,rec.requiredCompletion*20,0);
    	
    	drawProgressBar(67+ICON_OFFSET_X,68+ICON_OFFSET_Y,0,174,105,6,rec.ticks*20,0);
    	
    	
    	//this.drawTexturedModalRect(k+114, l+16+px, 190, px, 5, 40-px);
    	int px = (10-rec.preferredIntensity)*4;
    	GuiDraw.gui.drawTexturedModalRect(ICON_OFFSET_X+114, ICON_OFFSET_Y+16+px, 190, px, 6, 40-px);
    	
    	
    	if(rec.liquidIn!=null){
    		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
    		IIcon tex = rec.liquidIn.getIcon();
    		px = (int) (rec.liquidLevel*5);
        	drawFluidWithTesselator(tex, ICON_OFFSET_X+19, ICON_OFFSET_Y+17, 10, 50, px);
        	//GuiDraw.gui.drawCenteredString(GuiDraw.fontRenderer, chemrecipe.fluidIn.amount+" mB", 35+ICON_OFFSET_X, 50+ICON_OFFSET_Y, color);
        	//GuiDraw.fontRenderer.drawStringWithShadow( chemrecipe.fluidIn.amount+" mB", 35+ICON_OFFSET_X, 60+ICON_OFFSET_Y, color);
    	}
    	
    	Minecraft.getMinecraft().getTextureManager().bindTexture(GuiReactionChamber.texture);
    	GuiDraw.gui.drawTexturedModalRect(ICON_OFFSET_X+18, ICON_OFFSET_Y+16, 176, 31, 12, 52);
    	
    	GuiDraw.gui.drawTexturedModalRect(ICON_OFFSET_X+18, ICON_OFFSET_Y+17+50-rec.liquidLevel*5, 178, 32, 8, 1);
    	
    	int danger = getDangerType(recipe);
    	GuiDraw.gui.drawTexturedModalRect(ICON_OFFSET_X+94, ICON_OFFSET_Y+37, 205, 12*danger, 12, 12);
    	    	
    	
    	int power = (int) (rec.RFTick/ReactionChamberOperation.RECIPE_TICKRATE);
    	//int power = FabricatorRecipe.getRecipes().get(recipe).chargeAmount;
    	//int time = 5*20;
    	
    	Minecraft.getMinecraft().getTextureManager().bindTexture(texture_gui_elemets);
    	drawProgressBar(8+ICON_OFFSET_X, 17+ICON_OFFSET_Y, 251, 1, 4, 48, 5*20, 7);
    	
    	GuiDraw.gui.drawCenteredString(GuiDraw.fontRenderer, ""+power, 57+ICON_OFFSET_X, 35+ICON_OFFSET_Y, color);
    	GuiDraw.gui.drawCenteredString(GuiDraw.fontRenderer, "RF/T", 57+ICON_OFFSET_X, 45+ICON_OFFSET_Y, color);
    	
    	GuiDraw.gui.drawString(GuiDraw.fontRenderer, rec.liquidLevel*10+"%", 34+ICON_OFFSET_X, 58+ICON_OFFSET_Y, color);
    	GuiDraw.gui.drawString(GuiDraw.fontRenderer, rec.preferredIntensity+"", 121+ICON_OFFSET_X, 16+ICON_OFFSET_Y, color);
    	
    }
    
	@Override
	public List<String> handleTooltip(GuiRecipe gui, List<String> currenttip, int recipe) {
		int mx = GuiDraw.getMousePosition().x - (gui.width-176)/2 - gui.getRecipePosition(recipe).x;
		int my= GuiDraw.getMousePosition().y - (gui.height-166)/2 - gui.getRecipePosition(recipe).y;
		
    	CachedReactionChamberRecipe rec = (CachedReactionChamberRecipe) this.arecipes.get(recipe);
    	ReactionChamberRecipe r = rec.recipe;
		
    	//System.out.println("M:"+mx+","+my);

    	//gui.firstGui.
    	//System.out.println("IOF_X:"+(this.ICON_OFFSET_X+18));
    	if(isInRect(mx,my,this.ICON_OFFSET_X+18, this.ICON_OFFSET_Y+17,12,52)){
    		if(r.liquidIn!=null){
    			currenttip.add(TextUtil.trans(r.liquidIn.getUnlocalizedName()));
    			currenttip.add(TextUtil.trans("techguns.reactionTooltip.consume")+" "+r.liquidConsumtion+ "mB");
    		} else {
    			currenttip.add(TextUtil.trans("techguns.chemlab.nofluidin"));
    		}
    	}
    	
    	if(isInRect(mx,my,this.ICON_OFFSET_X+94, this.ICON_OFFSET_Y+37,12,12)){

			currenttip.add(TextUtil.trans("techguns.reactionChamberNEI.Instability")+": "+ (r.intensityMargin>0?EnumChatFormatting.YELLOW+TextUtil.trans("techguns.yes"):EnumChatFormatting.GREEN+TextUtil.trans("techguns.no")));
			currenttip.add(TextUtil.trans("techguns.reactionChamberNEI.Danger")+": "+r.risk);
    	}
    	
		return super.handleTooltip(gui, currenttip, recipe);
	}
	
	public class CachedReactionChamberRecipe extends CachedRecipe {
		
		public ReactionChamberRecipe recipe;
		

		public CachedReactionChamberRecipe(ReactionChamberRecipe recipe) {
			super();
			this.recipe = recipe;
		}


        @Override
        public ArrayList<PositionedStack> getIngredients() {
            ArrayList<PositionedStack> recipestacks = new ArrayList<PositionedStack>();
            
            recipestacks.add(new PositionedStack(recipe.input.getItemStacks(), 35+ICON_OFFSET_X, 17+ICON_OFFSET_Y));
            recipestacks.add(new PositionedStack(ReactionBeamFocus.getItemForType(recipe.beamFocus), ICON_OFFSET_X+93, ICON_OFFSET_Y+17));
            
            ArrayList<ItemStack> outputs = recipe.outputs;
            if(outputs.size()>1){
				recipestacks.add(new PositionedStack(outputs.get(1), 152+ICON_OFFSET_X, 17+ICON_OFFSET_Y));
				
				if(outputs.size()>2){
					recipestacks.add(new PositionedStack(outputs.get(2), 134+ICON_OFFSET_X, 35+ICON_OFFSET_Y));
					
					if(outputs.size()>3){
						recipestacks.add(new PositionedStack(outputs.get(3), 152+ICON_OFFSET_X, 35+ICON_OFFSET_Y));
					}
				}
			}
            
            return (ArrayList<PositionedStack>) this.getCycledIngredients(cycleticks / 24, recipestacks);
        }
		
		@Override
        public PositionedStack getResult() {
			ArrayList<ItemStack> outputs = recipe.outputs;
			ItemStack output=null;
			PositionedStack stack=null;
			
			if(outputs.size()>0){
				output= outputs.get(0);
				stack = new PositionedStack(output, 134+ICON_OFFSET_X, 17+ICON_OFFSET_Y);
			}
        	return stack;
        }

	}
}
