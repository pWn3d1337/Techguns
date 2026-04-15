package techguns;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import techguns.items.GenericItemBucket;
import techguns.items.GenericItemShared;
import techguns.items.ItemCamoNetPlacer;
import techguns.items.ItemMedkit;
import techguns.items.ItemTGDoor;
import techguns.items.OreClusterScanner;
import techguns.items.TGSlotType;
import techguns.items.armors.ItemAntiGravPack;
import techguns.items.armors.ItemGasMask;
import techguns.items.armors.ItemJetpack;
import techguns.items.armors.ItemJumppack;
import techguns.items.armors.ItemNightVisionGoggles;
import techguns.items.armors.ItemScubaTanks;
import techguns.items.armors.ItemTacticalMask;
import techguns.items.tools.TGCrowbar;
import techguns.items.tools.TGPickaxe;
import techguns.items.tools.TGSword;
import cpw.mods.fml.common.registry.GameRegistry;

public class TGItems {
	
	public static final String CHESTGEN_WEAPONCHEST_T1 = "techgunsWeaponchestT1";
	public static final String CHESTGEN_WEAPONCHEST_T2 = "techgunsWeaponchestT2";
	public static final String CHESTGEN_WEAPONCHEST_T3 = "techgunsWeaponchestT3";
	public static final String CHESTGEN_REINFORCEDCHEST_T1 = "techgunsReinforcedchestT1";
	public static final String CHESTGEN_REINFORCEDCHEST_T2 = "techgunsReinforcedchestT2";
	public static final String CHESTGEN_REINFORCEDCHEST_T3 = "techgunsReinforcedchestT3";
	
	public static final String CHESTGEN_MILBASEBARRACKS = "techgunsMilBaseBarracks";
	
	public static final String[] WEAPONCHEST_LOOT_TIER = {CHESTGEN_WEAPONCHEST_T1,CHESTGEN_WEAPONCHEST_T2,CHESTGEN_WEAPONCHEST_T3};
	public static final String[] REINFORCEDCHEST_LOOT_TIER = {CHESTGEN_REINFORCEDCHEST_T1,CHESTGEN_REINFORCEDCHEST_T2,CHESTGEN_REINFORCEDCHEST_T3};
	
	/**
	 * AMMO
	 */
	public static GenericItemShared sharedItem;
		
	public static ItemStack stonebullets;
	public static ItemStack bullets9mm;
	public static ItemStack pistolMag9mm;
	public static ItemStack pistolMag9mmEmtpy;
	
	public static ItemStack shotgunShell;
	public static ItemStack smgMag;
	public static ItemStack smgMagEmpty;
	public static ItemStack rifleBullets;
	
	public static ItemStack flamethrowerTank;
	public static ItemStack flamethrowerTankEmpty;
	public static ItemStack bioTank;
	public static ItemStack bioTankEmpty;
	
	public static ItemStack m4Mag;
	public static ItemStack m4MagEmpty;
	public static ItemStack rocketAmmo;
	public static ItemStack energyCell;
	public static ItemStack energyCellEmpty;
	
	public static ItemStack as50Mag;
	public static ItemStack as50MagEmpty;
	public static ItemStack highCalibreBullets;
	public static ItemStack lmgMag;
	public static ItemStack lmgMagEmpty;
	public static ItemStack minigunMag;
	public static ItemStack minigunMagEmpty;
	
	public static ItemStack advBullets;
	public static ItemStack pdwMag;
	public static ItemStack pdwMagEmpty;

	//parts
	public static ItemStack stoneBarrel;
	public static ItemStack woodStock;
	
	public static ItemStack ironBarrel;
	public static ItemStack compressedAirTank;
	public static ItemStack compressedAirTankEmpty;
	public static ItemStack mechanicalParts;
	
	public static ItemStack plasticGunstock;
	public static ItemStack pumpMechanism;
	public static ItemStack obsidianSteelBarrel;
	public static ItemStack mechanicalPartsT2;
	
	public static ItemStack laserBarrel;
	public static ItemStack laserFocus;
	public static ItemStack mechanicalPartsT3;
	public static ItemStack bioMass;
	public static ItemStack heavyCloth;
	
	public static ItemStack ingotObsidianSteel;
	
	public static ItemStack plateTin;
	public static ItemStack plateCopper;
	public static ItemStack plateIron;
	
	public static ItemStack plateBronze;
	public static ItemStack plateSteel;
	
	public static Item door01;
	public static ItemStack turretArmorIron;
	public static ItemStack turretArmorSteel;
	public static ItemStack turretArmorObsidianSteel;
	
	public static ItemStack machineStackUpgrade;
	
	public static ItemGasMask gasMask;
	//public static ItemGasMask gasMaskHeavy;
	
	public static ItemStack glider;
	
	public static ItemStack ironReceiver;
	public static ItemStack steelReceiver;
	public static ItemStack obsidianSteelReceiver;
	public static ItemStack carbonReceiver;
	
	public static ItemStack copperWire;

	public static ItemStack gliderWing;
	public static ItemStack gliderBackback;
	public static ItemStack tgx;
	public static ItemStack carbonFibers;
	public static ItemStack carbonPlate;
	public static ItemStack nethercharge;
	public static ItemStack electricEngine;
	
	public static ItemStack plateObsidianSteel;
	public static ItemStack turretArmorCarbon;
	
	public static ItemStack steamArmorPlate;
	public static ItemStack circuitBoard;
	
	//public static ItemStack rubberDrop;
	public static ItemStack coil;
	public static ItemStack rawRubber;
	public static ItemStack rawPlastic;
	public static ItemStack plasticSheet;
	public static ItemStack rubberBar;
	
	public static ItemStack gasMaskFilter;
	
	public static ItemStack oreDrillSmallSteel;
	public static ItemStack oreDrillSmallObsidianSteel;
	public static ItemStack oreDrillSmallCarbon;
	
	public static ItemStack oreDrillMediumSteel;
	public static ItemStack oreDrillMediumObsidianSteel;
	public static ItemStack oreDrillMediumCarbon;
	
	public static ItemStack oreDrillLargeSteel;
	public static ItemStack oreDrillLargeObsidianSteel;
	public static ItemStack oreDrillLargeCarbon;
	
	public static ItemStack carbonGunstock;
	public static ItemStack carbonBarrel;
	
	public static ItemStack circuitBoardElite;
	public static ItemStack goldWire;
	
	public static ItemNightVisionGoggles nightVisionGoggles;
	
	public static ItemStack rifleBulletsStack;
	
	public static ItemJetpack jetpack;
	
	public static ItemScubaTanks scubaTanks;
	
	public static ItemStack powerPlating;
	
	public static ItemStack oxygenMask;
	
	public static ItemTacticalMask tacticalMask;
	
	
	public static ItemStack cyberneticParts;
	
	//Reaction chamber foci
	public static ItemStack focus_uvemitter;
	
	public static ItemStack _40mmGrenade;
	
	
	public static ItemStack oreTitanium;
	public static ItemStack ingotTitanium;
	public static ItemStack plateTitanium;
	
	public static ItemStack sonicEmitter;
	public static ItemStack rcHeatRay;
	
	//autoConfigurating Recipes with fallback
	public static String hardenedGlassOrGlass;
	public static String electrumOrGold;
	
	
	public static Item combatKnife;
	
	public static Item oreClusterScanner;
	
	public static Item bucketAcid;
	
	public static ItemStack quartzRod;
	
	public static ItemJumppack jumppack;
	
	public static ItemAntiGravPack antiGravPack;
	
	public static ItemStack antiGravityCore;
	
	public static ItemStack yellowcake;
	
	public static ItemStack enrichedUranium;
	
	public static ItemStack nuclearWarheadSmall;
	
	public static ItemStack plateLead;
	
	public static ItemStack rocketAmmoNuclear;
	
	public static ItemStack nuclearPowerCell;
	public static ItemStack nuclearPowerCellDepleted;
	
	public static ItemStack radEmitter;
	
	public static ItemStack treatedLeather;
	
	public static Item camoNetPlacer;
	
	public static Item crowbar;
	
	//public static ItemMedkit medkit;
	
	//TOOLS
	static ToolMaterial TG_STEEL = EnumHelper.addToolMaterial("Steel", 2, 1000, 7.5f, 2.5f, 12);
	
	
	
	public static void init() {
		sharedItem = new GenericItemShared();
		GameRegistry.registerItem(sharedItem, "TechgunsAmmo");
		int meta =0;
		
		stonebullets = registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		bullets9mm= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		
		sharedItem.addMagazineTooltip(meta);
		pistolMag9mm= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		
		pistolMag9mmEmtpy= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		
		shotgunShell= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		
		sharedItem.addMagazineTooltip(meta);
		smgMag= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		smgMagEmpty= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		rifleBullets= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		
		flamethrowerTank= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		flamethrowerTankEmpty = registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		bioTank= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		bioTankEmpty= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		
		sharedItem.addMagazineTooltip(meta);
		m4Mag= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		m4MagEmpty= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		rocketAmmo= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		energyCell= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		energyCellEmpty= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		
		sharedItem.addMagazineTooltip(meta);
		as50Mag= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		
		as50MagEmpty= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		highCalibreBullets= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		
		sharedItem.addMagazineTooltip(meta);
		lmgMag= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		
		lmgMagEmpty= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		
		advBullets= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		
		sharedItem.addMagazineTooltip(meta);
		pdwMag= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		pdwMagEmpty= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		
		stoneBarrel=registerSharedItem(meta++);
		woodStock=registerSharedItem(meta++);

		ironBarrel=registerSharedItem(meta++);
		compressedAirTank=registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		compressedAirTankEmpty=registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		mechanicalParts=registerSharedItem(meta++);

		plasticGunstock=registerSharedItem(meta++);
		pumpMechanism=registerSharedItem(meta++);
		obsidianSteelBarrel=registerSharedItem(meta++);
		mechanicalPartsT2=registerSharedItem(meta++);

		laserBarrel=registerSharedItem(meta++);
		laserFocus=registerSharedItem(meta++);
		mechanicalPartsT3=registerSharedItem(meta++);
		bioMass=registerSharedItem(meta++);
		heavyCloth=registerSharedItem(meta++);
		
		ingotObsidianSteel=registerSharedItem(meta++);
		
		plateTin=registerSharedItem(meta++);
		plateCopper=registerSharedItem(meta++);
		plateIron=registerSharedItem(meta++);
		plateBronze=registerSharedItem(meta++);
		plateSteel=registerSharedItem(meta++);
		
		sharedItem.addMagazineTooltip(meta);
		minigunMag= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		
		minigunMagEmpty= registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		
		GameRegistry.registerItem((door01 = new ItemTGDoor().setTextureName("techguns:metalDoor")), "doorItem01");
		turretArmorIron=registerSharedItem(meta++,TGSlotType.TURRETARMOR,1);
		turretArmorSteel=registerSharedItem(meta++,TGSlotType.TURRETARMOR,1);
		turretArmorObsidianSteel=registerSharedItem(meta++,TGSlotType.TURRETARMOR,1);
		glider=registerSharedItem(meta++,TGSlotType.BACKSLOT,1);
		
		machineStackUpgrade = registerSharedItem(meta++, TGSlotType.NORMAL,7);
		
		ironReceiver=registerSharedItem(meta++);
		steelReceiver=registerSharedItem(meta++);
		obsidianSteelReceiver=registerSharedItem(meta++);
		carbonReceiver=registerSharedItem(meta++);
		copperWire=registerSharedItem(meta++);
		
		gliderWing = registerSharedItem(meta++);
		gliderBackback = registerSharedItem(meta++);
		tgx = registerSharedItem(meta++);
		carbonFibers = registerSharedItem(meta++);
		carbonPlate = registerSharedItem(meta++);
		nethercharge = registerSharedItem(meta++);
		electricEngine = registerSharedItem(meta++);
		
		plateObsidianSteel = registerSharedItem(meta++);
		turretArmorCarbon = registerSharedItem(meta++,TGSlotType.TURRETARMOR,1);
		
		steamArmorPlate = registerSharedItem(meta++);
		circuitBoard = registerSharedItem(meta++);
		coil=registerSharedItem(meta++);
		rawRubber=registerSharedItem(meta++);
		rawPlastic = registerSharedItem(meta++);
		plasticSheet = registerSharedItem(meta++);
		rubberBar = registerSharedItem(meta++);
		gasMaskFilter=registerSharedItem(meta++);
		
		oreDrillSmallSteel = registerSharedItem(meta++, TGSlotType.DRILL_SMALL,1);
		oreDrillSmallObsidianSteel = registerSharedItem(meta++, TGSlotType.DRILL_SMALL,1);
		oreDrillSmallCarbon = registerSharedItem(meta++, TGSlotType.DRILL_SMALL,1);

		oreDrillMediumSteel = registerSharedItem(meta++, TGSlotType.DRILL_MEDIUM,1);
		oreDrillMediumObsidianSteel = registerSharedItem(meta++, TGSlotType.DRILL_MEDIUM,1);
		oreDrillMediumCarbon = registerSharedItem(meta++, TGSlotType.DRILL_MEDIUM,1);

		oreDrillLargeSteel = registerSharedItem(meta++, TGSlotType.DRILL_LARGE,1);
		oreDrillLargeObsidianSteel = registerSharedItem(meta++, TGSlotType.DRILL_LARGE,1);
		oreDrillLargeCarbon = registerSharedItem(meta++, TGSlotType.DRILL_LARGE,1);
		
		carbonGunstock = registerSharedItem(meta++);
		carbonBarrel = registerSharedItem(meta++);
		
		goldWire = registerSharedItem(meta++);
		circuitBoardElite = registerSharedItem(meta++);
		
		rifleBulletsStack = registerSharedItem(meta++, TGSlotType.AMMOSLOT);
		powerPlating = registerSharedItem(meta++);
		
		oxygenMask = registerSharedItem(meta++,TGSlotType.FACESLOT,1);
		
		cyberneticParts = registerSharedItem(meta++);
		
		focus_uvemitter = registerSharedItem(meta++,TGSlotType.REACTION_CHAMBER_FOCUS,1);
		
		_40mmGrenade = registerSharedItem(meta++, TGSlotType.AMMOSLOT);
		
		oreTitanium = registerSharedItem(meta++);
		ingotTitanium = registerSharedItem(meta++);
		
		plateTitanium = registerSharedItem(meta++);
		
		sonicEmitter = registerSharedItem(meta++);
		
		rcHeatRay = registerSharedItem(meta++,TGSlotType.REACTION_CHAMBER_FOCUS,1);
		
		quartzRod = registerSharedItem(meta++);
		
		antiGravityCore = registerSharedItem(meta++);
		
		yellowcake = registerSharedItem(meta++);
		
		enrichedUranium = registerSharedItem(meta++);
		
		nuclearWarheadSmall = registerSharedItem(meta++);
		
		plateLead = registerSharedItem(meta++);
		
		rocketAmmoNuclear = registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		
		nuclearPowerCell = registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		nuclearPowerCellDepleted = registerSharedItem(meta++,TGSlotType.AMMOSLOT);
		
		radEmitter = registerSharedItem(meta++);
		
		treatedLeather = registerSharedItem(meta++);
		
		gasMask=new ItemGasMask("gasMask", "techguns:textures/armor/gasMask.png",1, 300);
		GameRegistry.registerItem(gasMask, "gasMask");
		//gasMaskHeavy=new ItemGasMask("gasMaskHeavy", "techguns:textures/armor/gasMask.png", 600);
		//GameRegistry.registerItem(gasMaskHeavy, "gasMaskHeavy");
		
		nightVisionGoggles = new ItemNightVisionGoggles("nightVisionGoggles", "nightVisionGoggles",1, 24000);
		GameRegistry.registerItem(nightVisionGoggles,"nightVisionGoggles");	
		
		jetpack = new ItemJetpack("jetpack", "jetPackIcon",4, 5000);
		GameRegistry.registerItem(jetpack, "jetpack");
		
		scubaTanks = new ItemScubaTanks("scubaTanks", "oxygenFlasksIcon",1, 600);
		GameRegistry.registerItem(scubaTanks, "scubaTanks");
		
		tacticalMask = new ItemTacticalMask("tacticalMask", "tacticalMaskItem",4, 24000);
		GameRegistry.registerItem(tacticalMask, "tacticalMask");
		
		oreClusterScanner = new OreClusterScanner();
		GameRegistry.registerItem(oreClusterScanner, "oreClusterScanner");
		
		//TOOLS
		combatKnife = new TGSword(TG_STEEL, "combatKnife");
		GameRegistry.registerItem(combatKnife, "combatKnife");
		
		jumppack = new ItemJumppack("jumppack", "jumpPackIcon",4, 1000);
		GameRegistry.registerItem(jumppack, "jumppack");
		
		antiGravPack = new ItemAntiGravPack("antiGravPack", "jetPackIcon",5, 12000);
		GameRegistry.registerItem(antiGravPack, "antiGravPack");
		
		camoNetPlacer = new ItemCamoNetPlacer("camoNetPlacer");
		GameRegistry.registerItem(camoNetPlacer, "camoNetPlacer");
		
		
		crowbar = new TGCrowbar(TG_STEEL, "crowbar");
		GameRegistry.registerItem(crowbar, "crowbar");
		
		//medkit = new ItemMedkit("medkit",2);
		//GameRegistry.registerItem(medkit, "medkit");
		
		 OreDictionary.registerOre("ingotObsidianSteel", ingotObsidianSteel); 
		 
		 OreDictionary.registerOre("plateIron", plateIron);
		 OreDictionary.registerOre("plateTin", plateTin);
		 OreDictionary.registerOre("plateCopper", plateCopper);
		 OreDictionary.registerOre("plateBronze", plateBronze);
		 OreDictionary.registerOre("plateSteel", plateSteel);
		 OreDictionary.registerOre("plateCarbon", carbonPlate);
		 OreDictionary.registerOre("fiberCarbon", carbonFibers);
		 OreDictionary.registerOre("plateObsidianSteel", plateObsidianSteel);
		 OreDictionary.registerOre("circuitBasic", circuitBoard);
		 OreDictionary.registerOre("wireCopper", copperWire);
		 OreDictionary.registerOre("itemRawRubber", rawRubber);
		 OreDictionary.registerOre("rawPlastic", rawPlastic);
		 OreDictionary.registerOre("itemRubber", rubberBar);
		 OreDictionary.registerOre("wireGold", goldWire);
		 OreDictionary.registerOre("circuitElite", circuitBoardElite);
		 OreDictionary.registerOre("ingotTitanium", ingotTitanium);
		 OreDictionary.registerOre("oreTitanium", oreTitanium);
		 OreDictionary.registerOre("plateTitanium", plateTitanium);
		 OreDictionary.registerOre("sheetPlastic", TGItems.plasticSheet);
		 OreDictionary.registerOre("dustUranium", TGItems.yellowcake);
		 OreDictionary.registerOre("ingotUraniumEnriched", TGItems.enrichedUranium);
		 OreDictionary.registerOre("plateLead", TGItems.plateLead);
		 
		//registerRecipes();
	}
	
	/**
	 * Copy stack with overriding stacksize
	 */
	public static ItemStack newStack(ItemStack type, int amount){
		ItemStack newStack = type.copy();
		newStack.stackSize=amount;
		return newStack;
		//return new ItemStack(type.getItem(), amount, type.getItemDamage());
	}
	
	
	private static ItemStack registerSharedItem(int meta, TGSlotType slot, int maxStackSize){
		sharedItem.maxStackSize[meta]=(short)maxStackSize;
		sharedItem.slotType[meta]=slot;
		return new ItemStack(sharedItem,1,meta);
	}
	
	private static ItemStack registerSharedItem(int meta, TGSlotType slot){
		return registerSharedItem(meta, slot, 64);
	}
	
	private static ItemStack registerSharedItem(int meta){
		return registerSharedItem(meta, TGSlotType.NORMAL);
	}
	
	public static void registerRecipes(){
		GameRegistry.addRecipe(new ShapedOreRecipe(newStack(stoneBarrel,1), "sss","   ","sss", 's', "stone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(woodStock,1), "ww", " w", 'w', "logWood"));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(ironBarrel,1), "iii","   ","iii", 'i', "ingotIron"));
        //GameRegistry.addRecipe(new ShapedOreRecipe(newStack(spring,1), "ii "," i "," ii", 'i', "nuggetIron"));
        //GameRegistry.addRecipe(new ShapedOreRecipe(newStack(ironGears,1), " i ","i i"," i ", 'i', "ingotIron"));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(mechanicalParts,1)," i ", "ifi", " i ", 'f', Items.flint, 'i', "ingotIron"));
        
        //GameRegistry.addRecipe(new ShapedOreRecipe(newStack(plasticGunstock,1),"  i","nni","  n", 'i', "ingotIron", 'n', "nuggetIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(plasticGunstock,1),"ppp","  p", 'p', "sheetPlastic"));
        
        // GameRegistry.addRecipe(new ShapedOreRecipe(newStack(mechanicalPartsT2,1), "sqg","oqo", 'q', "gemQuartz", 's', spring, 'g', "gearIron", 'o', Blocks.obsidian));
        
       //GameRegistry.addRecipe(new ShapedOreRecipe(newStack(mechanicalPartsT3,1), "sbg","dbd",'b', Items.blaze_rod, 'd', "gemDiamond", 's', spring, 'g', "gearIron"));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(obsidianSteelBarrel, 1), "ooo","   ","ooo", 'o', ingotObsidianSteel));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(pumpMechanism, 1), "nnn", "gpg", "nnn", 'n', "nuggetIron", 'g', "blockGlass", 'p', Blocks.piston));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(laserBarrel,1), "fff","ggl","fff", 'f', electrumOrGold, 'g', hardenedGlassOrGlass, 'l', TGItems.laserFocus));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(heavyCloth,3), " w ","wlw"," w ", 'w', Blocks.wool, 'l', Items.leather));
        
        
        GameRegistry.addRecipe(new ShapelessOreRecipe(newStack(stonebullets,16), "cobblestone", "cobblestone", "cobblestone", Items.gunpowder));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(bullets9mm,8), "clc","cgc","ccc", 'c', "nuggetCopper", 'l', "ingotLead", 'g', Items.gunpowder));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(shotgunShell,5), "lll","cgc","ccc", 'c', "nuggetCopper", 'l', "nuggetLead", 'g', Items.gunpowder));
        
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(pistolMag9mmEmtpy,4), "i i","imi","igi", 'i', "nuggetIron",'g', "ingotIron", 'm', TGItems.mechanicalParts));
        GameRegistry.addShapelessRecipe(newStack(pistolMag9mm, 1), pistolMag9mmEmtpy, bullets9mm,bullets9mm,bullets9mm);
        
        //GameRegistry.addRecipe(new ShapedOreRecipe(newStack(smgMag,1), "ibi","ibi","iii", 'i', "nuggetIron", 'b', bullets9mm));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(smgMagEmpty,4), "i i","i i","imi", 'i', "nuggetIron", 'm', TGItems.mechanicalParts));
        GameRegistry.addShapelessRecipe(newStack(smgMag, 1), smgMagEmpty, bullets9mm, bullets9mm);
        
        //GameRegistry.addRecipe(new ShapedOreRecipe(newStack(rifleBullets,16),"sls","sls","sgs", 's', "nuggetSteel", 'l', "ingotLead", 'g', Items.gunpowder));
        //GameRegistry.addRecipe(new ShapedOreRecipe(newStack(highCalibreBullets,6),"sls","sgs","sgs", 's', "nuggetSteel", 'l', "ingotLead", 'g', Items.gunpowder));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(m4MagEmpty,4),"s s","s s","sms", 's', "nuggetSteel",'m', TGItems.mechanicalParts));
        
        GameRegistry.addShapelessRecipe(newStack(m4Mag, 1), m4MagEmpty, rifleBullets, rifleBullets, rifleBullets);
        
        /*GameRegistry.addRecipe(new ShapedOreRecipe(newStack(flamethrowerTank,1),"pip","sbi","pip", 's', "blockGlassHardened", 'p', "sheetPlastic", 'i', "nuggetInvar", 'b', Items.lava_bucket));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(flamethrowerTankEmpty,1),"pip","s i","pip", 's', "blockGlassHardened", 'p', "sheetPlastic", 'i', "nuggetInvar"));
        GameRegistry.addShapelessRecipe(newStack(flamethrowerTank,1), flamethrowerTankEmpty, Items.lava_bucket);*/
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(bioTankEmpty,1),"sss","sgs","sgs", 's', "nuggetSteel", 'g', "blockGlass"));
        //GameRegistry.addRecipe(new ShapedOreRecipe(newStack(bioTankEmpty,1),"sgs","s s","sgs", 's', "nuggetSteel", 'g', "blockGlassHardened"));
        //GameRegistry.addShapelessRecipe(newStack(bioTank,1), bioTankEmpty, bioMass);
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(bioMass,4)," g ","gbg"," g ", 'g', "dyeGreen", 'b', "slimeball"));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(rocketAmmo,4), " s ","sts","sgs", 's', "nuggetIron", 't', Blocks.tnt, 'g', Items.gunpowder));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(lmgMagEmpty,4), " ii", "imi","iii", 'i', "ingotSteel", 'm', TGItems.mechanicalParts));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(lmgMag,1), "bbb", "bmb","bbb", 'b', rifleBullets, 'm', lmgMagEmpty));
        GameRegistry.addRecipe(new ShapelessOreRecipe(newStack(lmgMag,1), lmgMagEmpty, rifleBulletsStack, rifleBulletsStack));
        
        //GameRegistry.addRecipe(new ShapedOreRecipe(newStack(energyCell,1),"ggg","rrr","ggg", 'g', "nuggetGold", 'r', "dustRedstone"));
        //GameRegistry.addRecipe(new ShapelessOreRecipe(newStack(energyCell,1), energyCellEmpty,"dustRedstone", "dustRedstone", "dustRedstone"));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(as50MagEmpty,4),"s s","s s","sms", 's', "ingotSteel", 'm', TGItems.mechanicalParts));
        //GameRegistry.addRecipe(new ShapedOreRecipe(newStack(as50Mag,1),"shs","shs","sis", 's', "nuggetSteel", 'i', "ingotSteel", 'h', highCalibreBullets));
        GameRegistry.addRecipe(new ShapelessOreRecipe(newStack(as50Mag,1),as50MagEmpty, highCalibreBullets, highCalibreBullets));
        
       // GameRegistry.addRecipe(new ShapedOreRecipe(newStack(laserFocus,1),"hrh","rgr","hrh", 'g', "gemDiamond", 'r', "blockRedstone", 'h', hardenedGlassOrGlass));
        //GameRegistry.addRecipe(new ShapelessOreRecipe(newStack(ingotObsidianSteel,1), "ingotSteel", Blocks.obsidian));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(turretArmorIron,1),"ppp"," p "," p ", 'p', "plateIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(turretArmorSteel,1),"ppp"," p "," p ", 'p', "plateSteel"));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(turretArmorObsidianSteel,1),"ppp"," p "," p ", 'p', "plateObsidianSteel"));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(turretArmorCarbon,1),"ppp"," p "," p ", 'p', "plateCarbon"));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(ironReceiver,1), "iii"," mn","  n", 'i', "ingotIron", 'n', "nuggetIron",'m', TGItems.mechanicalParts));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(steelReceiver,1), "iii"," mn","  n", 'i', "ingotSteel", 'n', "nuggetSteel",'m', TGItems.mechanicalPartsT2));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(obsidianSteelReceiver,1), "ppp"," mi","  i", 'i', "ingotObsidianSteel", 'p', "plateObsidianSteel",'m', TGItems.mechanicalPartsT3));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(carbonReceiver,1), "ppp"," mi","  i", 'i', "ingotObsidianSteel", 'p', "plateCarbon",'m', TGItems.mechanicalPartsT3));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(machineStackUpgrade,1), "pgp","ncn","pnp", 'p', "plateIron", 'n', "ingotGold",'g', "dyeGreen", 'c', new ItemStack(Blocks.chest,1)));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(pdwMagEmpty,4),"i i","i i","imi",'i', "ingotObsidianSteel", 'm',TGItems.mechanicalPartsT2));
        GameRegistry.addRecipe(new ShapelessOreRecipe(newStack(pdwMag,1), TGItems.pdwMagEmpty, TGItems.advBullets, TGItems.advBullets, TGItems.advBullets));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(copperWire,1)," cc"," c ","cc ",'c', "nuggetCopper"));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(gliderBackback,1), "hhh","shs","hhh", 'h', TGItems.heavyCloth, 's', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(gliderWing,1), "sss","hhh","hhh", 'h', TGItems.heavyCloth, 's', "ingotIron"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(newStack(glider,1), TGItems.gliderBackback, TGItems.gliderWing, TGItems.gliderWing));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(electricEngine,1), "wrw","imi","wrw", 'w', TGItems.copperWire, 'i', "ingotIron", 'r', Items.redstone, 'm', TGItems.mechanicalParts));
	
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.basicMachine,1,0), "ili","cec","iri", 'i', "ingotIron", 'c', "ingotCopper", 'l', "ingotLead",'e', TGItems.electricEngine, 'r', Items.redstone));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.basicMachine,1,1), "ibi","iei","iri", 'i', "ingotIron", 'b', "blockIron",'e', TGItems.electricEngine, 'r', Items.redstone));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.basicMachine,1,1), "ibi","iei","iri", 'i', "ingotIron", 'b', "plateIron",'e', TGItems.electricEngine, 'r', Items.redstone));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.basicMachine,1,2), "pip","cec","prp", 'p', "plateIron", 'c', new ItemStack(TGBlocks.concrete, 1), 'b', "plateIron",'e', TGItems.electricEngine, 'r', Items.redstone, 'i', "circuitBasic"));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.basicMachine,1,3), "ddd","ici","iii", 'd', "dye", 'c', new ItemStack(Blocks.crafting_table,1), 'i', "nuggetIron"));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.basicMachine,1,4), "igi","geg","iri", 'i', "ingotIron", 'g', new ItemStack(Items.glass_bottle), 'e', TGItems.electricEngine, 'r', Items.redstone));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.basicMachine,1,5), "pmp","ici","iii", 'm', TGItems.mechanicalParts, 'c', new ItemStack(Blocks.crafting_table,1), 'i', "nuggetIron", 'p', "plateIron"));
        
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.ladder01,16),"iii"," i ","iii", 'i', "plateTin"));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(TGItems.circuitBoard,4),"cgc","rir","cgc", 'i', "plateIron", 'c', "wireCopper", 'g', "dyeGreen", 'r', Items.redstone));
	
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.lamp01,16,0),"iii","grg","ggg",'i',"nuggetIron",'r', "dustRedstone",'g', "paneGlass"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TGBlocks.lamp01,1,0), new ItemStack(TGBlocks.lamp01,1,8)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TGBlocks.lamp01,1,8), new ItemStack(TGBlocks.lamp01,1,0)));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.lamp01,16,7),"iii","grg","iii",'i',"nuggetIron",'r', "dustRedstone",'g', "paneGlass"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TGBlocks.lamp01,1,15), new ItemStack(TGBlocks.lamp01,1,7)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TGBlocks.lamp01,1,7), new ItemStack(TGBlocks.lamp01,1,15)));
        
       // GameRegistry.addRecipe(new ShapelessOreRecipe(newStack(TGItems.rawRubber,1), TGItems.rubberDrop,TGItems.rubberDrop,TGItems.rubberDrop,TGItems.rubberDrop,TGItems.rubberDrop,TGItems.rubberDrop,TGItems.rubberDrop,TGItems.rubberDrop,TGItems.rubberDrop));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(TGItems.gasMaskFilter,1), "iii","ici","iii", 'i', "nuggetIron", 'c', Items.coal));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGItems.gasMask,1), "grg","rrr","rfr", 'r', "itemRubber", 'f', TGItems.gasMaskFilter, 'g', "paneGlass"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TGItems.gasMask,1,0), new ItemStack(TGItems.gasMask,1,OreDictionary.WILDCARD_VALUE), TGItems.gasMaskFilter));
    	
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.oreDrill,8,0), "pbp","bpb","pbp", 'p', "plateIron", 'b', new ItemStack(Blocks.iron_bars,1)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.oreDrill,8,1), "bbb","bpb","bbb", 'p', "plateIron", 'b', new ItemStack(Blocks.iron_bars,1)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.oreDrill,4,2), "ipi","ipi","ipi", 'p', "plateIron", 'i', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.oreDrill,4,3), "bbb","beb","bbb", 'p', "plateIron", 'b', new ItemStack(Blocks.iron_bars,1),'e', TGItems.electricEngine));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.oreDrill,1,4), "pcp","cec","pcp", 'p', "plateIron", 'c', "circuitBasic",'e', TGItems.electricEngine));
	
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.container,8,0), "i i"," c ", "i i", 'i', "ingotIron", 'c', Blocks.chest));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(carbonBarrel,1), "iii","   ","iii", 'i', "plateCarbon"));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(carbonGunstock,1), "iii"," pp", 'i', "plateCarbon", 'p', "sheetPlastic"));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(oreDrillSmallSteel,1),"sps","sps"," s ", 's', "ingotSteel", 'p', "plateSteel"));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(oreDrillSmallObsidianSteel,1),"sps","sps"," s ", 's', "ingotObsidianSteel", 'p', "plateObsidianSteel"));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(oreDrillSmallCarbon,1),"sss","sss"," s ",  's', "plateCarbon"));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(oreDrillMediumSteel,1),"d d"," d ", 'd', TGItems.oreDrillSmallSteel));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(oreDrillMediumObsidianSteel,1),"d d"," d ", 'd', TGItems.oreDrillSmallObsidianSteel));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(oreDrillMediumCarbon,1),"d d"," d ", 'd', TGItems.oreDrillSmallCarbon));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(oreDrillLargeSteel,1),"d d"," d ", 'd', TGItems.oreDrillMediumSteel));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(oreDrillLargeObsidianSteel,1),"d d"," d ", 'd', TGItems.oreDrillMediumObsidianSteel));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(oreDrillLargeCarbon,1),"d d"," d ", 'd', TGItems.oreDrillMediumCarbon));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(nightVisionGoggles,1), "rhr","cec","gsg", 'r', "itemRubber", 'h', TGItems.heavyCloth, 'c',"circuitBasic",'e', TGItems.energyCell, 'g', "blockGlass", 's', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(nightVisionGoggles,1,nightVisionGoggles.getMaxDamage()), "rhr","cec","gsg", 'r', "itemRubber", 'h', TGItems.heavyCloth, 'c',"circuitBasic",'e', TGItems.energyCellEmpty, 'g', "blockGlass", 's', "ingotIron"));
    	
        GameRegistry.addRecipe(new ShapelessOreRecipe(newStack(circuitBoardElite,1), TGItems.circuitBoard, TGItems.goldWire, "gemLapis"));
        
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TGBlocks.sandbags,8), "itemRubber", Blocks.sand, Blocks.sand, Blocks.sand, Blocks.sand,Blocks.sand, Blocks.sand, Blocks.sand, Blocks.sand));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.camoNetPane,8), "sds", "sds", 's', Items.stick, 'd', Blocks.dirt));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.camoNetRoof,16), "wdw","dsd", "wdw", 's', Items.string, 'd', Blocks.dirt, 'w', Items.stick));
 
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(flamethrowerTankEmpty,4), "p","g","p", 'g', "blockGlass", 'p', "sheetPlastic"));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGItems.door01,2), "pp","pp","pp", 'p', "plateIron"));
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGItems.door01,2), "pp","pp","pp", 'p', "plateTin"));
        
        GameRegistry.addRecipe(new ShapelessOreRecipe(newStack(rifleBulletsStack,1), rifleBullets,rifleBullets,rifleBullets,rifleBullets));
        GameRegistry.addRecipe(new ShapelessOreRecipe(newStack(rifleBullets,4), rifleBulletsStack));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(minigunMagEmpty,4), "sss","pmp","sss",'s', "ingotSteel", 'p', "sheetPlastic", 'm', TGItems.mechanicalPartsT2));
        GameRegistry.addRecipe(new ShapelessOreRecipe(newStack(minigunMag,1),minigunMagEmpty, rifleBulletsStack,rifleBulletsStack,rifleBulletsStack,rifleBulletsStack));
	
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(compressedAirTankEmpty,7),"plp","p p","ppp", 'p', "plateIron", 'l', Blocks.lever));
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(compressedAirTankEmpty,7),"plp","p p","ppp", 'p', "plateTin", 'l', Blocks.lever));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.basicMachine,8,6), "rtr","tct","rtr", 'r', "itemRubber", 't', Blocks.tnt, 'c', "circuitBasic"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.basicMachine,4,8), "rtr","tct","rtr", 'r', "sheetPlastic", 't', TGItems.tgx, 'c', "circuitBasic"));
	
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGItems.jetpack,1,0), "f f","pgp","rmr",'f', TGItems.flamethrowerTank, 'p', "plateObsidianSteel", 'g', TGItems.glider, 'r', TGItems.rocketAmmo, 'm', TGItems.mechanicalPartsT2));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGItems.jetpack,1,0), "f f","pgp","rmr",'f', TGItems.flamethrowerTank, 'p', "plateObsidianSteel", 'g', new ItemStack(TGItems.jumppack,1,OreDictionary.WILDCARD_VALUE), 'r', TGItems.rocketAmmo, 'm', TGItems.mechanicalPartsT2));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGItems.antiGravPack,1,0), "tet","cac","mbm",'t', "plateTitanium", 'e', TGItems.nuclearPowerCell, 'b', TGItems.gliderBackback, 'c', "circuitElite", 'm', TGItems.mechanicalPartsT3, 'a', TGItems.antiGravityCore));
        
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGItems.scubaTanks,1,0), "p p","aia","p p", 'p', "sheetPlastic", 'a', TGItems.compressedAirTank, 'i', "plateIron"));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGItems.jumppack,1,0), "n n","pbp","c c", 'p', "plateIron", 'c', TGItems.compressedAirTank, 'n', "nuggetIron", 'b', TGItems.newStack(TGItems.gliderBackback, 1)));
        
        
        //GameRegistry.addRecipe(new ShapedOreRecipe(newStack(powerPlating,2), "pcp","cmc","pcp", 'p', "plateCarbon", 'c', "circuitBasic", 'm', TGItems.mechanicalPartsT3));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(oxygenMask,1), " p ","rpr", 'p', "sheetPlastic", 'r', "itemRubber"));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGItems.tacticalMask,1,0),"pnp","cgc","pop", 'p', "sheetPlastic", 'n', new ItemStack(TGItems.nightVisionGoggles,1,OreDictionary.WILDCARD_VALUE), 'c', "circuitBasic", 'g', new ItemStack(TGItems.gasMask,1,OreDictionary.WILDCARD_VALUE), 'o', TGItems.oxygenMask));
	
        GameRegistry.addRecipe(new ShapedOreRecipe(newStack(TGItems.coil,1), " wi","wiw","iw ", 'i', "ingotIron", 'w', "wireCopper"));
	
        ItemStack rc = new ItemStack(TGBlocks.machineMultiBlock,1, 0);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.machineMultiBlock,9,0), "sms","pcp","ses", 's', "plateSteel", 'm', TGItems.mechanicalPartsT3, 'p', TGItems.cyberneticParts, 'e', "circuitElite",'c', new ItemStack(TGBlocks.basicMachine,1, 4)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.machineMultiBlock,1,1), " g ","crc"," g ", 'g', hardenedGlassOrGlass, 'c', "circuitElite",  'r', rc));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.machineMultiBlock,6,2), "rgr","rgr","rgr", 'r', rc, 'g', hardenedGlassOrGlass));
        
        ItemStack fab = new ItemStack(TGBlocks.machineMultiBlock,1, 3);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.machineMultiBlock,4,3), "sms","pep","scs", 's', "plateSteel", 'm', TGItems.mechanicalPartsT3, 'p', TGItems.cyberneticParts, 'e', TGItems.electricEngine, 'c', "circuitElite"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.machineMultiBlock,1,4), " g ","cfc"," g ", 'g', hardenedGlassOrGlass, 'c', "circuitElite",  'f', fab));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.machineMultiBlock,4,5), "fgf","g g","fgf", 'f', fab, 'g', hardenedGlassOrGlass));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.basicMachine,1,9), "sgs","cbc","sgs", 's', "plateSteel", 'g', "wireGold", 'b', "circuitBasic", 'c', TGItems.coil));
	
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGItems.combatKnife,1), " s","p ", 's', "plateSteel", 'p', "sheetPlastic"));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(TGItems.newStack(TGItems.rcHeatRay,1), "cwc","plp","plp", 'c', "circuitElite", 'w', "wireGold", 'p', "plateSteel", 'l', Blocks.redstone_lamp));
	
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGItems.oreClusterScanner,1), "g g", "pcp","pep", 'g', "wireGold", 'p', "plateSteel", 'c', "circuitElite", 'e', TGItems.energyCell));
	
	
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.metalpanel,32,0), "sss","sps","sss", 's', "stone", 'p', "plateCopper"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.metalpanel2,64,0), "sss","psp","sss", 's', "stone", 'p', "plateCopper"));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(TGItems.newStack(TGItems.quartzRod,1), "q  ", " q ", "  q", 'q', "gemQuartz"));
        GameRegistry.addRecipe(new ShapedOreRecipe(TGItems.newStack(TGItems.quartzRod,1), "  q", " q ", "q  ", 'q', "gemQuartz"));
        
       GameRegistry.addRecipe(new ShapedOreRecipe(TGItems.newStack(TGItems.nuclearWarheadSmall, 2), "pcp","tut","pcp", 'p', "plateLead", 't', TGItems.tgx, 'c', "circuitBasic", 'u', "ingotUraniumEnriched"));
        
       GameRegistry.addShapelessRecipe(TGItems.newStack(TGItems.rocketAmmoNuclear, 1), TGItems.newStack(TGItems.rocketAmmo, 1), TGItems.newStack(TGItems.nuclearWarheadSmall, 1));
  
       GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGItems.camoNetPlacer,1,0),"sns",'s',"stickWood", 'n', new ItemStack(TGBlocks.camoNetPane,1,0)));
       GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGItems.camoNetPlacer,1,1),"sns",'s',"stickWood", 'n', new ItemStack(TGBlocks.camoNetPane,1,1)));
       GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGItems.camoNetPlacer,1,2),"sns",'s',"stickWood", 'n', new ItemStack(TGBlocks.camoNetPane,1,2)));
       

       GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGItems.crowbar,1), "  p", " p ", "p  ", 'p', "plateSteel"));
       
//       GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGItems.medkit,2,1), "hbh","bwb","hbh",'h', TGItems.heavyCloth, 'w', Items.potionitem, 'b', new ItemStack(TGItems.medkit,1,0)));
       
       GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.tgchest,1), "n n", " c ", "n n", 'c', Blocks.chest, 'n', "nuggetIron"));
       GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TGBlocks.tgchest_weapon,1), "pcp", 'c', Blocks.chest, 'p', "plateIron"));
       
       
       //GameRegistry.addRecipe(new ShapedOreRecipe(TGItems.newStack(TGItems.nuclearPowerCellDepleted, 2), "lcl","s s","lcl", 'l', "plateLead", 'c', "circuitBasic", 's', "plateSteel"));
	}
	
	public static void postInit() {
		((ItemTGDoor)TGItems.door01).setDoorBlock(TGBlocks.door01);
		

		hardenedGlassOrGlass=getOredictNameWithFallback("blockGlassHardened", "blockGlass");
		electrumOrGold=getOredictNameWithFallback("ingotElectrum", "ingotGold");
		
		bucketAcid = new GenericItemBucket(TGBlocks.fluidBlockAcid).setContainerItem(Items.bucket).setMaxStackSize(1).setUnlocalizedName("bucket_acid").setCreativeTab(Techguns.tabTechgun).setTextureName("techguns:bucket_acid");
		GameRegistry.registerItem(bucketAcid, "bucket_acid");
		
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("creeper_acid", FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(bucketAcid), new ItemStack(Items.bucket));
		
		if(TGFluids.FUEL!=null){
			FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("fuel", 250), TGItems.newStack(TGItems.flamethrowerTank, 1), TGItems.newStack(TGItems.flamethrowerTankEmpty, 1));
		} else {
			FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("lava", 500), TGItems.newStack(TGItems.flamethrowerTank, 1), TGItems.newStack(TGItems.flamethrowerTankEmpty, 1));
			
		}
		
		//add items to dungeon loot
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(TGuns.handcannon,0, 1, 1, 10));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(TGItems.stonebullets, 8, 16, 10));
		
		//Ammo to dungeon loot
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(TGItems.bullets9mm, 4, 8, 3));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(TGItems.shotgunShell, 4, 32, 3));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(TGItems.rifleBullets, 2, 8, 3));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(TGItems.highCalibreBullets, 2, 6, 1));
		
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(TGItems.m4Mag, 2, 4, 2));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(TGItems.smgMag, 3, 6, 2));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(TGItems.pistolMag9mm, 2, 6, 2));
		
		//weapons to dungeon loot
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(TGuns.revolver,0, 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(TGuns.sawedoff,0, 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(TGuns.thompson,0, 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(TGuns.ak47,0, 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(TGuns.m4,0, 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(TGuns.boltaction,0, 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(TGuns.pistol,0, 1, 1, 1));
		
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(TGItems.nightVisionGoggles,0, 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(TGItems.gasMask,0, 1, 1, 1));
		
		
		//TG chests
		ChestGenHooks.getInfo(CHESTGEN_WEAPONCHEST_T1).setMin(2);
		ChestGenHooks.getInfo(CHESTGEN_WEAPONCHEST_T1).setMax(8);
		
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T1, new WeightedRandomChestContent(TGuns.handcannon,0, 1, 1, 2));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T1, new WeightedRandomChestContent(TGItems.stonebullets, 8, 16, 3));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T1, new WeightedRandomChestContent(TGItems.bullets9mm, 4, 8, 3));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T1, new WeightedRandomChestContent(TGItems.shotgunShell, 4, 32, 3));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T1, new WeightedRandomChestContent(TGuns.revolver,0, 1, 1, 2));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T1, new WeightedRandomChestContent(TGuns.sawedoff,0, 1, 1, 2));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T1, new WeightedRandomChestContent(TGuns.thompson,0, 1, 1, 2));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T1, new WeightedRandomChestContent(TGItems.smgMag, 3, 6, 3));
		
		
		//T2 Weapon
		ChestGenHooks.getInfo(CHESTGEN_WEAPONCHEST_T2).setMin(4);
		ChestGenHooks.getInfo(CHESTGEN_WEAPONCHEST_T2).setMax(8);
		
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T2, new WeightedRandomChestContent(TGItems.bullets9mm, 4, 12, 4));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T2, new WeightedRandomChestContent(TGItems.shotgunShell, 4, 32, 4));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T2, new WeightedRandomChestContent(TGuns.revolver,0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T2, new WeightedRandomChestContent(TGuns.sawedoff,0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T2, new WeightedRandomChestContent(TGuns.thompson,0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T2, new WeightedRandomChestContent(TGItems.smgMag, 3, 6, 3));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T2, new WeightedRandomChestContent(TGItems.rifleBullets, 4, 12, 4));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T2, new WeightedRandomChestContent(TGItems.m4Mag, 2, 8, 3));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T2, new WeightedRandomChestContent(TGuns.boltaction,0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T2, new WeightedRandomChestContent(TGuns.mac10,0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T2, new WeightedRandomChestContent(TGuns.ak47,0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T2, new WeightedRandomChestContent(TGuns.m4,0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T2, new WeightedRandomChestContent(TGuns.pistol,0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T2, new WeightedRandomChestContent(TGItems.pistolMag9mm, 2, 5, 2));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T2, new WeightedRandomChestContent(TGuns.stielGranate,0, 3, 8, 2));
		
		//T3 Weapon
		ChestGenHooks.getInfo(CHESTGEN_WEAPONCHEST_T3).setMin(5);
		ChestGenHooks.getInfo(CHESTGEN_WEAPONCHEST_T3).setMax(10);
		
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T3, new WeightedRandomChestContent(TGItems.bullets9mm, 4, 12, 4));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T3, new WeightedRandomChestContent(TGItems.shotgunShell, 4, 32, 4));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T3, new WeightedRandomChestContent(TGItems.rifleBullets, 4, 12, 4));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T3, new WeightedRandomChestContent(TGItems.m4Mag, 4, 8, 4));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T3, new WeightedRandomChestContent(TGItems.smgMag, 4, 8, 4));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T3, new WeightedRandomChestContent(TGuns.combatShotgun,0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T3, new WeightedRandomChestContent(TGuns.boltaction,0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T3, new WeightedRandomChestContent(TGuns.mac10,0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T3, new WeightedRandomChestContent(TGuns.ak47,0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T3, new WeightedRandomChestContent(TGuns.m4,0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T3, new WeightedRandomChestContent(TGuns.pistol,0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T3, new WeightedRandomChestContent(TGItems.pistolMag9mm, 2, 5, 4));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T3, new WeightedRandomChestContent(TGItems.flamethrowerTank, 3, 8, 3));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T3, new WeightedRandomChestContent(TGuns.flamethrower,0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T3, new WeightedRandomChestContent(TGuns.rocketlauncher,0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T3, new WeightedRandomChestContent(TGItems.rocketAmmo, 4, 8, 2));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T3, new WeightedRandomChestContent(TGuns.fragGrenade,0, 3, 8, 2));
		ChestGenHooks.addItem(CHESTGEN_WEAPONCHEST_T3, new WeightedRandomChestContent(TGuns.stielGranate,0, 4, 12, 2));
		
		
		ChestGenHooks.getInfo(CHESTGEN_REINFORCEDCHEST_T1).setMin(3);
		ChestGenHooks.getInfo(CHESTGEN_REINFORCEDCHEST_T1).setMax(6);
		ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T1, new WeightedRandomChestContent(Items.coal,0, 4, 16, 3));
		ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T1, new WeightedRandomChestContent(Items.iron_ingot,0, 4, 16, 3));
		ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T1, new WeightedRandomChestContent(new ItemStack(Blocks.stone), 16, 32, 3));
		ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T1, new WeightedRandomChestContent(new ItemStack(Blocks.log,1,0), 8, 16, 3));
		ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T1, new WeightedRandomChestContent(Items.gunpowder,0, 4, 8, 3));

		ArrayList<ItemStack> tin = OreDictionary.getOres("ingotTin");
		if(tin.size()>0){
			ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T1, new WeightedRandomChestContent(tin.get(0), 8, 32, 3));
		}
		ArrayList<ItemStack> copper = OreDictionary.getOres("ingotCopper");
		if(copper.size()>0){
			ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T1, new WeightedRandomChestContent(copper.get(0), 8, 32, 3));
		}
		ArrayList<ItemStack> lead = OreDictionary.getOres("ingotLead");
		if(lead.size()>0){
			ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T1, new WeightedRandomChestContent(lead.get(0), 6, 24, 3));
		}
		
		
		ChestGenHooks.getInfo(CHESTGEN_REINFORCEDCHEST_T2).setMin(4);
		ChestGenHooks.getInfo(CHESTGEN_REINFORCEDCHEST_T2).setMax(8);
		ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T2, new WeightedRandomChestContent(Items.coal,0, 4, 16, 3));
		ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T2, new WeightedRandomChestContent(Items.iron_ingot,0, 3, 12, 3));
		ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T2, new WeightedRandomChestContent(new ItemStack(Blocks.stone), 16, 32, 3));
		ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T2, new WeightedRandomChestContent(new ItemStack(Blocks.log,1,0), 6, 12, 3));
		ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T2, new WeightedRandomChestContent(Items.gunpowder,0, 4, 8, 3));
		ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T2, new WeightedRandomChestContent(Items.gold_ingot,0, 2, 6, 3));
		ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T2, new WeightedRandomChestContent(TGItems.rubberBar, 2, 8, 3));
		
		if(tin.size()>0){
			ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T2, new WeightedRandomChestContent(tin.get(0), 4, 16, 3));
		}
		if(copper.size()>0){
			ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T2, new WeightedRandomChestContent(copper.get(0), 4, 16, 3));
		}
		if(lead.size()>0){
			ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T2, new WeightedRandomChestContent(lead.get(0), 3, 12, 3));
		}
		ArrayList<ItemStack> silver = OreDictionary.getOres("ingotSilver");
		if(silver.size()>0){
			ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T2, new WeightedRandomChestContent(silver.get(0), 2, 8, 3));
		}
		ArrayList<ItemStack> steel = OreDictionary.getOres("ingotSteel");
		if(steel.size()>0){
			ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T2, new WeightedRandomChestContent(steel.get(0), 2, 8, 3));
		}
		
		
		ChestGenHooks.getInfo(CHESTGEN_REINFORCEDCHEST_T3).setMin(5);
		ChestGenHooks.getInfo(CHESTGEN_REINFORCEDCHEST_T3).setMax(9);
		ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T3, new WeightedRandomChestContent(Items.coal,0, 8, 16, 3));
		ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T3, new WeightedRandomChestContent(Items.iron_ingot,0, 8, 16, 3));
		ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T3, new WeightedRandomChestContent(new ItemStack(Blocks.stone), 24, 32, 3));
		ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T3, new WeightedRandomChestContent(new ItemStack(Blocks.log,1,0), 8, 16, 3));
		ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T3, new WeightedRandomChestContent(Items.gunpowder,0, 6, 12, 3));
		ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T3, new WeightedRandomChestContent(Items.gold_ingot,0, 6, 18, 3));
		ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T3, new WeightedRandomChestContent(TGItems.rubberBar, 3, 10, 3));
		ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T3, new WeightedRandomChestContent(TGItems.plasticSheet, 2, 8, 3));
		
		if(tin.size()>0){
			ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T3, new WeightedRandomChestContent(tin.get(0), 8, 16, 3));
		}
		if(copper.size()>0){
			ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T3, new WeightedRandomChestContent(copper.get(0), 8, 16, 3));
		}
		if(lead.size()>0){
			ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T3, new WeightedRandomChestContent(lead.get(0), 8, 16, 3));
		}
		if(silver.size()>0){
			ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T3, new WeightedRandomChestContent(silver.get(0), 4, 16, 3));
		}
		if(steel.size()>0){
			ChestGenHooks.addItem(CHESTGEN_REINFORCEDCHEST_T3, new WeightedRandomChestContent(steel.get(0), 4, 16, 3));
		}
		
		
		
		ChestGenHooks.getInfo(CHESTGEN_MILBASEBARRACKS).setMin(1);
		ChestGenHooks.getInfo(CHESTGEN_MILBASEBARRACKS).setMax(4);
		ChestGenHooks.addItem(CHESTGEN_MILBASEBARRACKS, new WeightedRandomChestContent(TGArmors.t1_combat_Boots, 0, 1, 1, 2));
		ChestGenHooks.addItem(CHESTGEN_MILBASEBARRACKS, new WeightedRandomChestContent(TGArmors.t1_combat_Chestplate, 0, 1, 1, 2));
		ChestGenHooks.addItem(CHESTGEN_MILBASEBARRACKS, new WeightedRandomChestContent(TGArmors.t1_combat_Helmet, 0, 1, 1, 2));
		ChestGenHooks.addItem(CHESTGEN_MILBASEBARRACKS, new WeightedRandomChestContent(TGArmors.t1_combat_Leggings, 0, 1, 1, 2));
		ChestGenHooks.addItem(CHESTGEN_MILBASEBARRACKS, new WeightedRandomChestContent(TGArmors.t2_combat_Boots, 0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_MILBASEBARRACKS, new WeightedRandomChestContent(TGArmors.t2_combat_Chestplate, 0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_MILBASEBARRACKS, new WeightedRandomChestContent(TGArmors.t2_combat_Helmet, 0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_MILBASEBARRACKS, new WeightedRandomChestContent(TGArmors.t2_combat_Leggings, 0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_MILBASEBARRACKS, new WeightedRandomChestContent(TGItems.glider, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_MILBASEBARRACKS, new WeightedRandomChestContent(TGItems.gasMask,0, 1, 1, 2));
		ChestGenHooks.addItem(CHESTGEN_MILBASEBARRACKS, new WeightedRandomChestContent(TGItems.gasMaskFilter, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_MILBASEBARRACKS, new WeightedRandomChestContent(TGItems.nightVisionGoggles,0, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_MILBASEBARRACKS, new WeightedRandomChestContent(TGItems.oxygenMask, 1, 1, 1));
		ChestGenHooks.addItem(CHESTGEN_MILBASEBARRACKS, new WeightedRandomChestContent(TGItems.compressedAirTank, 1, 5, 2));
		ChestGenHooks.addItem(CHESTGEN_MILBASEBARRACKS, new WeightedRandomChestContent(TGItems.scubaTanks,0, 1, 1, 1));
		
	}
	
	private static String getOredictNameWithFallback(String modded, String fallback){
		if(OreDictionary.getOres(modded).isEmpty()){
			return fallback;
		} else {
			return modded;
		}
	}
}
