package techguns.nei;

import techguns.Techguns;
import techguns.gui.GuiAmmoPress;
import techguns.gui.GuiChemLab;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "codechicken.nei.api.API", modid = "NotEnoughItems")
public class NEITechgunsConfig implements IConfigureNEI{
	TemplateRecipeHandler ammoPressRecipes;
	TemplateRecipeHandler metalPressRecipes;
	TemplateRecipeHandler chemLabRecipes;
	TemplateRecipeHandler charchgingStationRecipes;
	TemplateRecipeHandler fabricatorRecipes;
	TemplateRecipeHandler reactionChamberRecipes;
	
	@Optional.Method(modid = "NotEnoughItems")
	@Override
	public void loadConfig() {
		ammoPressRecipes = new TechgunsAmmoPressNEIRecipeHandler();
		API.registerRecipeHandler(ammoPressRecipes);
		API.registerUsageHandler(ammoPressRecipes);
		
		metalPressRecipes = new TechgunsMetalPressNEIRecipeHandler();
		API.registerRecipeHandler(metalPressRecipes);
		API.registerUsageHandler(metalPressRecipes);
		
		chemLabRecipes = new TechgunsChemLabNEIRecipeHandler();
		API.registerRecipeHandler(chemLabRecipes);
		API.registerUsageHandler(chemLabRecipes);
		
		charchgingStationRecipes = new TechgunsChargingStationNEIRecipeHandler();
		API.registerRecipeHandler(charchgingStationRecipes);
		API.registerUsageHandler(charchgingStationRecipes);
		
		fabricatorRecipes = new TechgunsFabricatorNEIRecipeHandler();
		API.registerRecipeHandler(fabricatorRecipes);
		API.registerUsageHandler(fabricatorRecipes);
		
		reactionChamberRecipes = new TechgunsReactionChamberNEIRecipeHandler();
		API.registerRecipeHandler(reactionChamberRecipes);
		API.registerUsageHandler(reactionChamberRecipes);
		
		API.setGuiOffset(GuiAmmoPress.class,-TechgunsAmmoPressNEIRecipeHandler.ICON_OFFSET_X, -TechgunsAmmoPressNEIRecipeHandler.ICON_OFFSET_Y);
	//	API.setGuiOffset(GuiMetalPress.class, -TechgunsMetalPressNEIRecipeHandler.ICON_OFFSET_X, -TechgunsMetalPressNEIRecipeHandler.ICON_OFFSET_Y);
		//API.setGuiOffset(GuiMetalPress.class, 0,0);
		API.setGuiOffset(GuiChemLab.class, -TechgunsChemLabNEIRecipeHandler.ICON_OFFSET_X, -TechgunsChemLabNEIRecipeHandler.ICON_OFFSET_Y);
		
	}

	@Optional.Method(modid = "NotEnoughItems")
	@Override
	public String getName() {
		return Techguns.MODNAME;
	}

	@Optional.Method(modid = "NotEnoughItems")
	@Override
	public String getVersion() {
		return Techguns.VERSION;
	}

}
