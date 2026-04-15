package techguns;

import java.util.ArrayList;

import com.google.gson.JsonObject;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import techguns.inventory.AmmoPressBuildPlans;
import techguns.inventory.ChargingStationRecipe;
import techguns.inventory.ChemLabRecipes;
import techguns.inventory.FabricatorRecipe;
import techguns.inventory.ItemStackOreDict;
import techguns.inventory.MetalPressRecipes;
import techguns.inventory.ReactionBeamFocus;
import techguns.inventory.ReactionChamberRecipe;
import techguns.inventory.ReactionChamberRecipe.RiskType;
import techguns.util.ItemUtil;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Class that contains recipes for machines
 *
 */
public class TGMachineRecipes {
		
	public static void addRecipes(){
		//AmmoPressRecipe.addRecipe(new ItemStack(Techguns.bullets9mm,10), new String[]{"ingotCopper","ingotIron","itemGunpowder"}, new int[]{2,1,1});
		//AMMO PRESS
		ArrayList<String> metal2 = new ArrayList<String>();
		ArrayList<String> metal1 = new ArrayList<String>();
		ArrayList<String> powders = new ArrayList<String>();
		metal2.add("ingotCopper");
		metal2.add("ingotTin");
		metal2.add("ingotIron");
		metal2.add("ingotBronze");
		metal1.add("ingotSteel");
		metal1.add("ingotLead");
		powders.add("itemGunpowder");
		AmmoPressBuildPlans.init(metal1, metal2, powders);
		
		//METAL PRESS
		MetalPressRecipes.addRecipe("ingotSteel", new ItemStack(Blocks.obsidian,1), TGItems.newStack(TGItems.ingotObsidianSteel,1), true);
		MetalPressRecipes.addRecipe("ingotTin","ingotTin",TGItems.newStack(TGItems.plateTin,2), true);
		MetalPressRecipes.addRecipe("ingotCopper","ingotCopper",TGItems.newStack(TGItems.plateCopper,2), true);
		MetalPressRecipes.addRecipe("ingotBronze","ingotBronze",TGItems.newStack(TGItems.plateBronze,2), true);
		MetalPressRecipes.addRecipe("ingotIron","ingotIron",TGItems.newStack(TGItems.plateIron,2), true);
		MetalPressRecipes.addRecipe("ingotSteel","ingotSteel",TGItems.newStack(TGItems.plateSteel,2), true);
		MetalPressRecipes.addRecipe("ingotLead","ingotLead",TGItems.newStack(TGItems.plateLead,2), true);
		MetalPressRecipes.addRecipe("plateIron", new ItemStack(Items.flint,1), TGItems.newStack(TGItems.mechanicalParts, 1), true);
		MetalPressRecipes.addRecipe("plateObsidianSteel", "gemQuartz", TGItems.newStack(TGItems.mechanicalPartsT2, 1), true);
		MetalPressRecipes.addRecipe("plateCarbon", new ItemStack(Items.blaze_rod), TGItems.newStack(TGItems.mechanicalPartsT3, 2), true);
		MetalPressRecipes.addRecipe("plateCopper", "plateCopper", TGItems.newStack(TGItems.copperWire, 8), true);
		MetalPressRecipes.addRecipe("fiberCarbon", "fiberCarbon", TGItems.newStack(TGItems.carbonPlate, 2), true);
		MetalPressRecipes.addRecipe("plateObsidianSteel", TGItems.newStack(TGItems.tgx, 1), TGItems.newStack(TGItems.advBullets, 16), true);
		MetalPressRecipes.addRecipe("ingotObsidianSteel", "ingotObsidianSteel", TGItems.newStack(TGItems.plateObsidianSteel, 2), true);
		MetalPressRecipes.addRecipe("plateSteel", "plateBronze", TGItems.newStack(TGItems.steamArmorPlate, 1), true);
		MetalPressRecipes.addRecipe("ingotGold", "ingotGold", TGItems.newStack(TGItems.goldWire, 2), true);
		
		MetalPressRecipes.addRecipe("plateIron", new ItemStack(Blocks.tnt,1), TGItems.newStack(TGItems._40mmGrenade, 16), true);
		
		MetalPressRecipes.addRecipe("ingotTitanium","ingotTitanium",TGItems.newStack(TGItems.plateTitanium,2), true);
		
		//Chem Lab
		if(ItemUtil.existsInOredict("dustCoal")){
			ChemLabRecipes.addRecipe("dustRedstone",1, "dustCoal",1, null,0, new FluidStack(FluidRegistry.WATER,250), null, new ItemStack(Items.gunpowder,1), true,5);
		} else {
			ChemLabRecipes.addRecipe("dustRedstone",1, new ItemStack(Items.coal),1, null,0, new FluidStack(FluidRegistry.WATER,250), null, new ItemStack(Items.gunpowder,1), true,5);
		}
		ChemLabRecipes.addRecipe(new ItemStack(Blocks.sand),1, new ItemStack(Blocks.gravel),1, null,0, new FluidStack(FluidRegistry.WATER,250), null, new ItemStack(TGBlocks.concrete,2), true,5);
		if(TGFluids.OIL!=null){
			ChemLabRecipes.addRecipe(new ItemStack(Items.slime_ball), 1, new ItemStack(Items.coal), 1, null, 0, new FluidStack(FluidRegistry.WATER,1000), new FluidStack(TGFluids.OIL,250), null, true, 20);
			ChemLabRecipes.addRecipe("itemRawRubber", 1, (ItemStack)null, 0, null, 0, new FluidStack(TGFluids.OIL,500), null, TGItems.newStack(TGItems.rawPlastic, 1), false, 25);
		} else {
			ChemLabRecipes.addRecipe("itemRawRubber", 1, new ItemStack(Items.coal,1), 1, null, 0, new FluidStack(TGFluids.WATER,1000), null, TGItems.newStack(TGItems.rawPlastic, 1), true, 25);
		}
		
		ChemLabRecipes.addRecipe(TGItems.bioMass, 1, new ItemStack(Items.gunpowder), 1, null, 0, new FluidStack(TGFluids.WATER,1000), new FluidStack(TGFluids.ACID,1000), null, true, 20);
		
		if (ItemUtil.existsInOredict("itemBioFuel")){
			ChemLabRecipes.addRecipe("itemBioFuel",4, TGItems.newStack(TGItems.bioTankEmpty,1), 1, null, 0, new FluidStack(TGFluids.WATER,500), null, TGItems.newStack(TGItems.bioTank, 1), false, 1);
			ChemLabRecipes.addRecipe("itemBioFuel",4, new ItemStack(Items.gunpowder), 1, null, 0, new FluidStack(TGFluids.WATER,1000), new FluidStack(TGFluids.ACID,1000), null, false, 20);
			
			//ChemLabRecipes.addRecipe("itemBioFuel",2, "itemBioFuel", 1, null, 0, new FluidStack(TGFluids.WATER,125), new FluidStack(TGFluids.BIOFUEL,125), null, false, 5);
			
		}
		
		
		ItemStack fuelTank = TGItems.newStack(TGItems.flamethrowerTank, 1);
		ItemStack fuelTankEmpty = TGItems.newStack(TGItems.flamethrowerTankEmpty, 1);
		if(TGFluids.FUEL!=null){
			ChemLabRecipes.addRecipe("itemGunpowder", 1, "gemLapis", 1, null, 0, new FluidStack(TGFluids.FUEL,250), null, TGItems.newStack(TGItems.tgx, 1), true, 20);
			ChemLabRecipes.addRecipe(fuelTankEmpty, 1, (ItemStack)null, 0, null, 0, new FluidStack(TGFluids.FUEL,250), null, fuelTank, false, 1);
			ChemLabRecipes.addRecipe(fuelTank, 1, (ItemStack)null, 0, null, 0, null, new FluidStack(TGFluids.FUEL,250), fuelTankEmpty, false, 1);
		} else {
			ChemLabRecipes.addRecipe("itemGunpowder", 1, "gemLapis", 1, null, 0, new FluidStack(FluidRegistry.LAVA,500), null, TGItems.newStack(TGItems.tgx, 1), true, 20);
			ChemLabRecipes.addRecipe(fuelTankEmpty, 1, (ItemStack)null, 0, null, 0, new FluidStack(TGFluids.LAVA,500), null, fuelTank, false, 1);
			ChemLabRecipes.addRecipe(fuelTank, 1, (ItemStack)null, 0, null, 0, null,new FluidStack(TGFluids.LAVA,500), fuelTankEmpty, false, 1);
			
		}
		
		ChemLabRecipes.addRecipe("gemDiamond", 1, new ItemStack(Items.blaze_powder,1), 1, null, 0, new FluidStack(TGFluids.LAVA,1000), null, TGItems.newStack(TGItems.carbonFibers, 2), true, 25);
		
		ItemStackOreDict stackLogWood = new ItemStackOreDict("logWood").setNoStrictMode();
		ItemStackOreDict nullStack = new ItemStackOreDict((ItemStack)null);
		ChemLabRecipes.addRecipe(stackLogWood, 1, nullStack, 0, null, 0, new FluidStack(TGFluids.WATER,1000), null, TGItems.newStack(TGItems.rawRubber, 1), false, 20);
		
		ChemLabRecipes.addRecipe(TGItems.newStack(TGItems.bioMass, 1),1, TGItems.newStack(TGItems.bioTankEmpty,1), 1, null, 0, new FluidStack(TGFluids.WATER,500), null, TGItems.newStack(TGItems.bioTank, 1), true, 1);
		
		ChemLabRecipes.addRecipe(new ItemStack(Items.coal,1), 1, TGItems.newStack(TGItems.compressedAirTankEmpty, 1), 1, null, 0, new FluidStack(TGFluids.WATER,250), null, TGItems.newStack(TGItems.compressedAirTank, 1), true, 5);
		
		ChemLabRecipes.addRecipe(new ItemStack(Blocks.netherrack), 1, new ItemStack(Blocks.soul_sand), 1, null, 0, new FluidStack(TGFluids.LAVA,1000), null, TGItems.newStack(TGItems.nethercharge, 4), true, 20);
		
		ItemStackOreDict uranium = new ItemStackOreDict("oreUranium").setNoStrictMode();
		/*System.out.println("STACKS!!!!!");
		for(ItemStack stack : uranium.getItemStacks()){
			System.out.println("Stack:"+stack);
		}*/
		ChemLabRecipes.addRecipe(uranium, 1, nullStack, 0, null, 0, new FluidStack(TGFluids.ACID,250), null, TGItems.newStack(TGItems.yellowcake, 1), false, 20);
		
		ChemLabRecipes.addRecipe(TGItems.nuclearPowerCellDepleted, 1, TGItems.enrichedUranium, 1, null, 0, new FluidStack(TGFluids.WATER,1000), null, TGItems.newStack(TGItems.nuclearPowerCell, 1), true, 40);
		
		if (TGFluids.MILK!=null){
			ChemLabRecipes.addRecipe(new ItemStack(Items.dye,1,2), 1, "itemRawRubber", 1, null, 0, new FluidStack(TGFluids.MILK,500), null, new ItemStack(Items.slime_ball), true, 25);
		}
		
		ChemLabRecipes.addRecipe(new ItemStack(Items.leather,2), 2, new ItemStack(Items.slime_ball,1), 1, null, 0, new FluidStack(TGFluids.ACID,500), null, TGItems.newStack(TGItems.treatedLeather,2), false, 20);
		
		//ChemLabRecipes.addRecipe("blockWool", 1, (ItemStack)null, 0, null, 0, new FluidStack(TGFluids.WATER,500), null, new ItemStack(TGItems.medkit,1,0), false, 5);
		
		GameRegistry.addSmelting(TGItems.newStack(TGItems.rawRubber, 1), TGItems.newStack(TGItems.rubberBar, 1),0);
		GameRegistry.addSmelting(TGItems.newStack(TGItems.rawPlastic,1),TGItems.newStack(TGItems.plasticSheet, 1) , 0);
		
		GameRegistry.addSmelting(TGItems.newStack(TGItems.oreTitanium, 1), TGItems.newStack(TGItems.ingotTitanium, 1), 0);
		
		
		//Smelt Plates back to ingots
		GameRegistry.addSmelting(TGItems.newStack(TGItems.plateObsidianSteel, 1), TGItems.ingotObsidianSteel, 0);
		GameRegistry.addSmelting(TGItems.newStack(TGItems.plateTitanium, 1), TGItems.ingotTitanium, 0);
			
		
		
		//vanilla recipes
		ChemLabRecipes.addRecipe(new ItemStack(Blocks.dirt),1, new ItemStack(Blocks.gravel),1, null,0, new FluidStack(FluidRegistry.WATER,250), null, new ItemStack(Blocks.clay), true,5);
		ChemLabRecipes.addRecipe(new ItemStack(Items.rotten_flesh),1, (ItemStack)null,0,null,0,new FluidStack(FluidRegistry.WATER,1000), null, new ItemStack(Items.leather), false,5);
		
		//Reaction Chamber
		//-----------------
		//Beam Focus Items
		ReactionBeamFocus.addBeamFocus(TGItems.focus_uvemitter, 100, "techguns:machines.chemlabWork");
		ReactionBeamFocus.addBeamFocus(TGItems.rcHeatRay, 100, "techguns:machines.rc_heatrayWork");
		
		
		//Recipes
		//																																												ticks, completition, intens, intens_margin, liquidlevel, consumed liquid, instability
		ReactionChamberRecipe.addRecipe(new ItemStackOreDict(new ItemStack(Items.wheat_seeds, 1)), TGItems.focus_uvemitter, FluidRegistry.WATER, new ItemStack[]{new ItemStack(Items.wheat,1), new ItemStack(Items.wheat_seeds,2)}, 10, 5, 3, 2, 1, 1000, 0, RiskType.BREAK_ITEM,50000);
		ReactionChamberRecipe.addRecipe(new ItemStackOreDict("gemDiamond",1), TGItems.rcHeatRay, TGFluids.LIQUID_REDSTONE, new ItemStack[]{TGItems.newStack(TGItems.laserFocus, 1)}, 10, 5, 3, 2, 4, 4000, 0, RiskType.BREAK_ITEM,100000);
		ReactionChamberRecipe.addRecipe(new ItemStackOreDict(new ItemStack(TGBlocks.oreTitanIron, 1)), TGItems.rcHeatRay, TGFluids.ACID, new ItemStack[]{TGItems.newStack(TGItems.oreTitanium, 2),new ItemStack(Blocks.iron_ore, 1)}, 2, 1, 5, 0, 3, 100,0, RiskType.BREAK_ITEM, 25000);
		
		ReactionChamberRecipe.addRecipe(new ItemStackOreDict(TGItems.newStack(TGItems.quartzRod, 1)), TGItems.rcHeatRay, TGFluids.LAVA, new ItemStack[]{new ItemStack(Items.blaze_rod, 1)}, 5, 3, 7, 2, 4, 1000,0, RiskType.BREAK_ITEM, 250000);
		
		ReactionChamberRecipe.addRecipe(new ItemStackOreDict("blockRedstone"), TGItems.rcHeatRay, TGFluids.LAVA, new ItemStack[]{new ItemStack(Blocks.glowstone, 1)}, 5, 3, 7, 2, 4, 1000,0, RiskType.EXPLOSION_MEDIUM, 250000);
		
		ReactionChamberRecipe.addRecipe(new ItemStackOreDict(new ItemStack(Items.nether_star)), TGItems.rcHeatRay, TGFluids.LIQUID_ENDER, new ItemStack[]{TGItems.newStack(TGItems.antiGravityCore, 1)}, 10, 7, 8, 1, 4, 4000, 1, RiskType.EXPLOSION_LOW, 500000);
		
		ReactionChamberRecipe.addRecipe(new ItemStackOreDict("dustUranium"), TGItems.rcHeatRay, TGFluids.WATER, new ItemStack[]{TGItems.newStack(TGItems.enrichedUranium, 1)}, 5, 4, 7, 0, 3, 1000, 1, RiskType.BREAK_ITEM, 250000);
		
		//FABRICATOR RECIPES
		FabricatorRecipe.addRecipe(new ItemStackOreDict("ingotGold"), 1, FabricatorRecipe.copperWires, 1, FabricatorRecipe.redstone, 3, FabricatorRecipe.plastic, 1, TGItems.energyCellEmpty, 1);
		FabricatorRecipe.addRecipe(new ItemStackOreDict(new ItemStack(Blocks.soul_sand,1)), 1, FabricatorRecipe.goldWires, 1, FabricatorRecipe.redstone, 1, FabricatorRecipe.plastic, 1, TGItems.cyberneticParts, 1);
		FabricatorRecipe.addRecipe(new ItemStackOreDict("ingotTitanium"), 2, FabricatorRecipe.circuit_basic, 4, FabricatorRecipe.mechanicalPartsT3, 1, FabricatorRecipe.carbonPlate, 4, TGItems.powerPlating, 2);
		FabricatorRecipe.addRecipe(new ItemStackOreDict(TGItems.newStack(TGItems.coil, 1)), 1, FabricatorRecipe.circuit_elite, 2, FabricatorRecipe.mechanicalPartsT3, 1, FabricatorRecipe.titaniumPlate, 1, TGItems.sonicEmitter, 1);		
		FabricatorRecipe.addRecipe(new ItemStackOreDict(TGItems.newStack(TGItems.enrichedUranium, 1)), 1, FabricatorRecipe.circuit_elite, 2, FabricatorRecipe.mechanicalPartsT3, 2, FabricatorRecipe.leadPlate, 2, TGItems.radEmitter, 1);		
		
		FabricatorRecipe.addRecipe(new ItemStackOreDict("ingotSteel", 2), 1, FabricatorRecipe.circuit_basic, 1, FabricatorRecipe.redstone, 4, FabricatorRecipe.leadPlate, 2, TGItems.nuclearPowerCellDepleted, 1);		
		
		
		//Charging Station
		ChargingStationRecipe.addRecipe(new ItemStackOreDict(TGItems.energyCellEmpty), TGItems.energyCell, 50000);
		
	}

}
