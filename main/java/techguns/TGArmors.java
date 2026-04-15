package techguns;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import techguns.damagesystem.TGArmorMaterial;
import techguns.items.armors.ArmorPowerType;
import techguns.items.armors.GenericArmor;
import techguns.items.armors.GenericArmorMultiCamo;
import techguns.items.armors.PoweredArmor;
import cpw.mods.fml.common.registry.GameRegistry;

public class TGArmors {

	//Armor Types
	/*public static ArmorMaterial T1_COMBAT = EnumHelper.addArmorMaterial("T1_COMBAT", 30, new int[] {3, 4, 2, 1}, 15); //10 Armor total
	public static ArmorMaterial T1_SCOUT = EnumHelper.addArmorMaterial("T1_SCOUT", 30, new int[] {2, 3, 1, 1}, 15); //7 Armor total
	public static ArmorMaterial T1_MINER = EnumHelper.addArmorMaterial("T1_MINER", 30, new int[] {2, 3, 1, 1}, 15); //7 Armor total
	public static ArmorMaterial T1_STEAM = EnumHelper.addArmorMaterial("T1_STEAM", 48, new int[] {4, 6, 4, 2}, 15); //16 Armor total
	
	public static ArmorMaterial T2_COMBAT = EnumHelper.addArmorMaterial("T2_COMBAT", 36, new int[] {4, 6, 3, 2}, 15); //15 Armor total
	public static ArmorMaterial T2_COMMANDO = EnumHelper.addArmorMaterial("T2_COMMANDO", 36, new int[] {3, 5, 3, 1}, 15); //12 Armor total
	
	public static ArmorMaterial T3_COMBAT = EnumHelper.addArmorMaterial("T3_COMBAT", 64, new int[] {5, 7, 5, 3}, 15); //20 Armor total*/
	
	public static TGArmorMaterial T1_COMBAT = new TGArmorMaterial("T1_COMBAT", 60, 0, 15.0f);
	public static TGArmorMaterial T1_SCOUT = new TGArmorMaterial("T1_SCOUT", 60, 0, 13.0f);
	public static TGArmorMaterial T1_MINER = new TGArmorMaterial("T1_MINER", 60, 0, 13.0f);
	public static TGArmorMaterial T1_STEAM = new TGArmorMaterial("T1_STEAM", 200, 0, 20.0f).setArmorFire(18.0f).setArmorExplosion(18.0f).setArmorEnergy(16.0f).setArmorIce(16.0f).setArmorLightning(16.0f).setArmorPoison(10.0f).setArmorRadiation(6.0f).setPenetrationResistance(0.1f);
	
	public static TGArmorMaterial T2_COMBAT = new TGArmorMaterial("T2_COMBAT", 72, 0, 19.0f);
	public static TGArmorMaterial T2_COMMANDO = new TGArmorMaterial("T2_COMMANDO", 72, 0, 18.0f).setArmorFire(15.0f).setArmorExplosion(15.0f).setArmorEnergy(15.0f).setArmorIce(15.0f).setArmorLightning(15.0f).setArmorPoison(10.0f).setArmorRadiation(5.0f);
	
	public static TGArmorMaterial T2_RIOT = new TGArmorMaterial("T2_RIOT", 96, 0, 20.0f).setArmorFire(18.0f).setArmorExplosion(18.0f).setArmorEnergy(18.0f).setArmorIce(17.0f).setArmorLightning(17.0f).setArmorPoison(16.0f).setArmorRadiation(16.0f).setPenetrationResistance(0.1f);
	
	
	public static TGArmorMaterial T3_COMBAT = new TGArmorMaterial("T3_COMBAT", 240, 0, 22.0f).setArmorFire(18.0f).setArmorExplosion(18.0f).setArmorEnergy(18.0f).setArmorIce(18.0f).setArmorLightning(18.0f).setArmorPoison(10.0f).setArmorRadiation(6.0f).setPenetrationResistance(0.15f);
	
	public static TGArmorMaterial T3_POWER = new TGArmorMaterial("T3_POWER", 360, 0, 24.0f).setArmorFire(20.0f).setArmorExplosion(22.0f).setArmorEnergy(20.0f).setArmorIce(20.0f).setArmorLightning(20.0f).setArmorPoison(15.0f).setArmorRadiation(10.0f).setPenetrationResistance(0.25f);
	
	public static TGArmorMaterial T3_MINER = new TGArmorMaterial("T3_MINER",300,0,21.0f).setArmorFire(21.0f).setArmorExplosion(21.0f).setArmorEnergy(21.0f).setArmorIce(21.0f).setArmorLightning(21.0f).setArmorPoison(21.0f).setArmorRadiation(21.0f).setPenetrationResistance(0.15f);
	
	//Same as T3_Combat
	public static TGArmorMaterial T3_EXO = new TGArmorMaterial("T3_EXO", 300, 0, 22.0f).setArmorFire(18.0f).setArmorExplosion(18.0f).setArmorEnergy(18.0f).setArmorIce(18.0f).setArmorLightning(18.0f).setArmorPoison(10.0f).setArmorRadiation(6.0f).setPenetrationResistance(0.15f);
	
	public static TGArmorMaterial T2_BERET = new TGArmorMaterial("T2_BERET",60,0,8.0f);
	
	//Armor Items
	public static Item t1_combat_Helmet;
	public static Item t1_combat_Chestplate;
	public static Item t1_combat_Leggings;
	public static Item t1_combat_Boots;
	
	public static Item t1_scout_Helmet;
	public static Item t1_scout_Chestplate;
	public static Item t1_scout_Leggings;
	public static Item t1_scout_Boots;
	
	public static Item t1_miner_Helmet;
	public static Item t1_miner_Chestplate;
	public static Item t1_miner_Leggings;
	public static Item t1_miner_Boots;
	
	public static Item t2_combat_Helmet;
	public static Item t2_combat_Chestplate;
	public static Item t2_combat_Leggings;
	public static Item t2_combat_Boots;
	
	public static Item t2_commando_Helmet;
	public static Item t2_commando_Chestplate;
	public static Item t2_commando_Leggings;
	public static Item t2_commando_Boots;
	
	public static Item t3_combat_Helmet;
	public static Item t3_combat_Chestplate;
	public static Item t3_combat_Leggings;
	public static Item t3_combat_Boots;
	
	public static Item steam_Helmet;
	public static Item steam_Chestplate;
	public static Item steam_Leggings;
	public static Item steam_Boots;
	
	public static Item t3_power_Helmet;
	public static Item t3_power_Chestplate;
	public static Item t3_power_Leggings;
	public static Item t3_power_Boots;
	
	public static Item t3_miner_Helmet;
	public static Item t3_miner_Chestplate;
	public static Item t3_miner_Leggings;
	public static Item t3_miner_Boots;
	
	public static Item t3_exo_Helmet;
	public static Item t3_exo_Chestplate;
	public static Item t3_exo_Leggings;
	public static Item t3_exo_Boots;
	
	public static Item t2_beret;
	
	public static Item t2_riot_Helmet;
	public static Item t2_riot_Chestplate;
	public static Item t2_riot_Leggings;
	public static Item t2_riot_Boots;
	
	public static final void init() {
		//Add Items
		
		//Register Armors:
		//----------------
		//T1 Combat Armor
		GameRegistry.registerItem(t1_combat_Helmet = new GenericArmor("t1_combat_helmet", T1_COMBAT, "t1_combat", 0).setRepairMats(new ItemStack(Items.iron_ingot,1), TGItems.heavyCloth, 0.5f, 2).setKnockbackResistance(0.05f), "t1_combat_helmet"); //0 for helmet
		GameRegistry.registerItem(t1_combat_Chestplate = new GenericArmor("t1_combat_chestplate", T1_COMBAT, "t1_combat", 1).setRepairMats(new ItemStack(Items.iron_ingot,1), TGItems.heavyCloth, 0.5f, 4).setKnockbackResistance(0.20f), "t1_combat_chestplate"); // 1 for chestplate
		GameRegistry.registerItem(t1_combat_Leggings = new GenericArmor("t1_combat_leggings", T1_COMBAT, "t1_combat", 2).setRepairMats(new ItemStack(Items.iron_ingot,1), TGItems.heavyCloth, 1.0f/3.0f, 3).setKnockbackResistance(0.10f), "t1_combat_leggings"); // 2 for leggings
		GameRegistry.registerItem(t1_combat_Boots = new GenericArmor("t1_combat_boots", T1_COMBAT, "t1_combat", 3).setRepairMats(new ItemStack(Items.iron_ingot,1), TGItems.heavyCloth, 0.5f, 2).setKnockbackResistance(0.05f), "t1_combat_boots"); // 3 for boots
		//T1 Scout Armor
		String[] t1_scout_textures = {"t1_scout", "t1_scout_forest","t1_scout_snow","t1_scout_black"};
		GameRegistry.registerItem(t1_scout_Helmet = new GenericArmorMultiCamo("t1_scout_helmet", T1_SCOUT, t1_scout_textures, 0).setSpeedBoni(0.125f,0.02f).setRepairMats(null, TGItems.heavyCloth, 0f, 2), "t1_scout_helmet"); //0 for helmet
		GameRegistry.registerItem(t1_scout_Chestplate = new GenericArmorMultiCamo("t1_scout_chestplate", T1_SCOUT, t1_scout_textures, 1).setSpeedBoni(0.125f,0.02f).setRepairMats(null, TGItems.heavyCloth, 0f, 4), "t1_scout_chestplate"); // 1 for chestplate
		GameRegistry.registerItem(t1_scout_Leggings = new GenericArmorMultiCamo("t1_scout_leggings", T1_SCOUT, t1_scout_textures, 2).setSpeedBoni(0.125f,0.02f).setRepairMats(null, TGItems.heavyCloth, 0f, 3), "t1_scout_leggings"); // 2 for leggings
		GameRegistry.registerItem(t1_scout_Boots = new GenericArmorMultiCamo("t1_scout_boots", T1_SCOUT, t1_scout_textures, 3).setSpeedBoni(0.125f,0.1f).setFallProtection(0.2f, 1.0f).setRepairMats(null, TGItems.heavyCloth, 0f, 2), "t1_scout_boots"); // 3 for boots
		//T1 Bob der Baumeister
		String[] t1_miner_textures = {"t1_miner", "t1_miner_red","t1_miner_green","t1_miner_black"};
		GameRegistry.registerItem(t1_miner_Helmet = new GenericArmorMultiCamo("t1_miner_helmet", T1_MINER, t1_miner_textures, 0).setCamoNameSuffix("helmet").setSpeedBoni(0.08f,0.00f).setMiningBoni(0.05f).setRepairMats(new ItemStack(Items.iron_ingot,1), TGItems.heavyCloth, 0.5f, 2), "t1_miner_helmet"); //0 for helmet
		GameRegistry.registerItem(t1_miner_Chestplate = new GenericArmorMultiCamo("t1_miner_chestplate", T1_MINER, t1_miner_textures, 1).setSpeedBoni(0.08f,0.0f).setMiningBoni(0.05f).setRepairMats(new ItemStack(Items.iron_ingot,1), TGItems.heavyCloth, 0.5f, 4), "t1_miner_chestplate"); // 1 for chestplate
		GameRegistry.registerItem(t1_miner_Leggings = new GenericArmorMultiCamo("t1_miner_leggings", T1_MINER, t1_miner_textures, 2).setSpeedBoni(0.08f,0.0f).setMiningBoni(0.05f).setRepairMats(new ItemStack(Items.iron_ingot,1), TGItems.heavyCloth, 1.0f/3.0f, 2), "t1_miner_leggings"); // 2 for leggings
		GameRegistry.registerItem(t1_miner_Boots = new GenericArmorMultiCamo("t1_miner_boots", T1_MINER, t1_miner_textures, 3).setSpeedBoni(0.08f,0.1f).setMiningBoni(0.05f).setFallProtection(0.2f, 1.0f).setRepairMats(new ItemStack(Items.iron_ingot,1), TGItems.heavyCloth, 0.5f, 2), "t1_miner_boots"); // 3 for boots
		
		//Steam Power Armor
		//ModelBiped armorModel1 = new ModelSteamArmor(0);
		//ModelBiped armorModel2 = new ModelSteamArmor(1);
		String[] steam_armor_textures = {"steam_armor", "steam_armor_rusty", "steam_armor_iron", "steam_armor_hazard"};
		GameRegistry.registerItem(steam_Helmet = new PoweredArmor("steam_helmet", T1_STEAM, steam_armor_textures, 0,ArmorPowerType.STEAM,10).setMiningBoni(0.05f,0.0f).setHealthBonus(1,0).setSpeedBoni(0.05f,0.03f,0.0f,0.0f).setRepairMats(TGItems.steamArmorPlate, TGItems.steamArmorPlate, 1.0f, 2).setArmorModel(0).setHideFaceslot(true).setKnockbackResistance(0.15f), "steam,_helmet"); //0 for helmet
		GameRegistry.registerItem(steam_Chestplate = new PoweredArmor("steam_chestplate", T1_STEAM, steam_armor_textures, 1,ArmorPowerType.STEAM,3600).setSpeedBoni(0.05f,0.03f,0.0f,0.0f).setMiningBoni(0.05f,0.0f).setHealthBonus(2,0).setBattery(TGItems.compressedAirTank).setEmptyBattery(TGItems.compressedAirTankEmpty).setRepairMats(TGItems.steamArmorPlate, TGItems.steamArmorPlate, 1.0f, 4).setArmorModel(0).setHideBackslot(true).setKnockbackResistance(0.30f), "steam_chestplate"); // 1 for chestplate
		GameRegistry.registerItem(steam_Leggings = new PoweredArmor("steam_leggings", T1_STEAM, steam_armor_textures, 2,ArmorPowerType.STEAM,10).setSpeedBoni(0.05f,0.03f,0f,0f).setMiningBoni(0.05f,0f).setHealthBonus(1,0).setRepairMats(TGItems.steamArmorPlate, TGItems.steamArmorPlate, 1.0f, 3).setArmorModel(1).setKnockbackResistance(0.20f), "steam_leggings"); // 2 for leggings
		GameRegistry.registerItem(steam_Boots = new PoweredArmor("steam_boots", T1_STEAM, steam_armor_textures, 3,ArmorPowerType.STEAM,10).setSpeedBoni(0.05f,0.15f,0,0).setMiningBoni(0.05f,0).setFallProtection(0.2f, 1.0f,0,0).setHealthBonus(1,0).setStepAssist(1.0f,0).setRepairMats(TGItems.steamArmorPlate, TGItems.steamArmorPlate, 1.0f, 2).setArmorModel(0).setKnockbackResistance(0.15f), "steam_boots"); // 3 for boots
		
		//T2 Combat Armor
		String[] t2_combat_textures = {"t2_combat", "t2_combat_wood","t2_combat_desert","t2_combat_arctic","t2_combat_swat","t2_combat_security"};
		GameRegistry.registerItem(t2_combat_Helmet = new GenericArmorMultiCamo("t2_combat_helmet", T2_COMBAT, t2_combat_textures, 0).setSpeedBoni(0.1f,0.0f).setRepairMats(TGItems.ingotObsidianSteel, TGItems.heavyCloth, 0.5f, 2).setKnockbackResistance(0.10f), "t2_combat_helmet"); //0 for helmet
		GameRegistry.registerItem(t2_combat_Chestplate = new GenericArmorMultiCamo("t2_combat_chestplate", T2_COMBAT, t2_combat_textures, 1).setSpeedBoni(0.1f, 0.0f).setRepairMats(TGItems.ingotObsidianSteel, TGItems.heavyCloth, 0.5f, 4).setKnockbackResistance(0.25f), "t2_combat_chestplate"); // 1 for chestplate
		GameRegistry.registerItem(t2_combat_Leggings = new GenericArmorMultiCamo("t2_combat_leggings", T2_COMBAT, t2_combat_textures, 2).setSpeedBoni(0.1f, 0.0f).setRepairMats(TGItems.ingotObsidianSteel, TGItems.heavyCloth, 1.0f/3.0f, 3).setKnockbackResistance(0.15f), "t2_combat_leggings"); // 2 for leggings
		GameRegistry.registerItem(t2_combat_Boots = new GenericArmorMultiCamo("t2_combat_boots", T2_COMBAT, t2_combat_textures, 3).setSpeedBoni(0.1f, 0.1f).setRepairMats(TGItems.ingotObsidianSteel, TGItems.heavyCloth, 0.5f, 2).setKnockbackResistance(0.10f), "t2_combat_boots"); // 3 for boots
		
		//T2 Commando Armor
		GameRegistry.registerItem(t2_commando_Helmet = new GenericArmor("t2_commando_helmet", T2_COMMANDO, "t2_commando", 0).setSpeedBoni(0.10f,0.f).setMiningBoniWater(1.25f).setGunBonus(0.05f).setKnockbackResistance(0.05f).setOxygenGear(1.0f), "t2_commando_helmet"); //0 for helmet
		GameRegistry.registerItem(t2_commando_Chestplate = new GenericArmor("t2_commando_chestplate", T2_COMMANDO, "t2_commando", 1).setSpeedBoni(0.10f,0.f).setMiningBoniWater(1.25f).setGunBonus(0.05f).setKnockbackResistance(0.20f), "t2_commando_chestplate"); // 1 for chestplate
		GameRegistry.registerItem(t2_commando_Leggings = new GenericArmor("t2_commando_leggings", T2_COMMANDO, "t2_commando", 2).setSpeedBoni(0.10f,0.f).setMiningBoniWater(1.25f).setGunBonus(0.05f).setKnockbackResistance(0.10f), "t2_commando_leggings"); // 2 for leggings
		GameRegistry.registerItem(t2_commando_Boots = new GenericArmor("t2_commando_boots", T2_COMMANDO, "t2_commando", 3).setSpeedBoni(0.10f,0.1f).setMiningBoniWater(1.25f).setGunBonus(0.05f).setKnockbackResistance(0.05f).setFallProtection(0.2f, 1.0f), "t2_commando_boots"); // 3 for boots
		
		
		//T2.5 Riot Gear Armor
		String[] t2_riot_textures = {"t2_riotArmor"};
		GameRegistry.registerItem(t2_riot_Helmet = new GenericArmorMultiCamo("t2_riot_helmet", T2_RIOT, t2_riot_textures, 0).setSpeedBoni(0.12f,0.f).setRepairMats(TGItems.ingotObsidianSteel, TGItems.treatedLeather, 0.5f, 2).setKnockbackResistance(0.125f).setArmorModel(8), "t2_riot_helmet"); //0 for helmet
		GameRegistry.registerItem(t2_riot_Chestplate = new GenericArmorMultiCamo("t2_riot_chestplate", T2_RIOT, t2_riot_textures, 1).setSpeedBoni(0.12f, 0.f).setRepairMats(TGItems.ingotObsidianSteel, TGItems.treatedLeather, 0.5f, 4).setKnockbackResistance(0.275f).setArmorModel(9), "t2_riot_chestplate"); // 1 for chestplate
		GameRegistry.registerItem(t2_riot_Leggings = new GenericArmorMultiCamo("t2_riot_leggings", T2_RIOT, t2_riot_textures, 2).setSpeedBoni(0.13f, 0.f).setRepairMats(TGItems.ingotObsidianSteel, TGItems.treatedLeather, 1.0f/3.0f, 3).setKnockbackResistance(0.175f).setArmorModel(10), "t2_riot_leggings"); // 2 for leggings
		GameRegistry.registerItem(t2_riot_Boots = new GenericArmorMultiCamo("t2_riot_boots", T2_RIOT, t2_riot_textures, 3).setSpeedBoni(0.13f, 0.15f).setRepairMats(TGItems.ingotObsidianSteel, TGItems.treatedLeather, 0.5f, 2).setKnockbackResistance(0.125f).setArmorModel(11), "t2_riot_boots"); // 3 for boots
	
		
		//T3 Combat Armor
		String[] t3_combat_textures = {"t3_combat", "t3_combat_silver", "t3_combat_green"};
		GameRegistry.registerItem(t3_combat_Helmet = new GenericArmorMultiCamo("t3_combat_helmet", T3_COMBAT, t3_combat_textures, 0).setSpeedBoni(0.1f,0.0f).setRepairMats(TGItems.carbonPlate, TGItems.carbonFibers, 0.5f, 2).setKnockbackResistance(0.15f), "t3_combat_helmet"); //0 for helmet
		GameRegistry.registerItem(t3_combat_Chestplate = new GenericArmorMultiCamo("t3_combat_chestplate", T3_COMBAT, t3_combat_textures, 1).setSpeedBoni(0.1f, 0.0f).setRepairMats(TGItems.carbonPlate, TGItems.carbonFibers, 0.5f, 4).setKnockbackResistance(0.30f), "t3_combat_chestplate"); // 1 for chestplate
		GameRegistry.registerItem(t3_combat_Leggings = new GenericArmorMultiCamo("t3_combat_leggings", T3_COMBAT, t3_combat_textures, 2).setSpeedBoni(0.1f, 0.0f).setRepairMats(TGItems.carbonPlate, TGItems.carbonFibers, 1.0f/3.0f, 3).setKnockbackResistance(0.20f), "t3_combat_leggings"); // 2 for leggings
		GameRegistry.registerItem(t3_combat_Boots = new GenericArmorMultiCamo("t3_combat_boots", T3_COMBAT, t3_combat_textures, 3).setSpeedBoni(0.1f, 0.1f).setRepairMats(TGItems.carbonPlate, TGItems.carbonFibers, 0.5f, 2).setKnockbackResistance(0.15f), "t3_combat_boots"); // 3 for boots
		
		//T3 power armor
		String[] t3_power_armor_textures = {"powerarmor", "powerarmor_dark", "powerarmor_dark2"};
		GameRegistry.registerItem(t3_power_Helmet = new PoweredArmor("t3_power_helmet", T3_POWER, t3_power_armor_textures, 0,ArmorPowerType.RF,10).setMiningBoni(0.05f,0.0f).setHealthBonus(1,0).setSpeedBoni(0.05f,0.03f,0.0f,0.0f).setRepairMats(TGItems.powerPlating, TGItems.goldWire, 0.5f, 2).setArmorModel(2).setHideFaceslot(true).setKnockbackResistance(0.20f), "t3_power_helmet"); //0 for helmet
		GameRegistry.registerItem(t3_power_Chestplate = new PoweredArmor("t3_power_chestplate", T3_POWER, t3_power_armor_textures, 1,ArmorPowerType.RF,3600).setSpeedBoni(0.05f,0.03f,0.0f,0.0f).setMiningBoni(0.05f,0.0f).setHealthBonus(2,0).setBattery(TGItems.energyCell).setEmptyBattery(TGItems.energyCellEmpty).setRepairMats(TGItems.powerPlating, TGItems.goldWire, 0.5f, 4).setArmorModel(2).setHideBackslot(false).setKnockbackResistance(0.35f), "t3_power_chestplate"); // 1 for chestplate
		GameRegistry.registerItem(t3_power_Leggings = new PoweredArmor("t3_power_leggings", T3_POWER, t3_power_armor_textures, 2,ArmorPowerType.RF,10).setSpeedBoni(0.05f,0.03f,0f,0f).setMiningBoni(0.05f,0f).setHealthBonus(1,0).setRepairMats(TGItems.powerPlating, TGItems.goldWire, 1.0f/3.0f, 3).setArmorModel(3).setKnockbackResistance(0.25f), "t3_power_leggings"); // 2 for leggings
		GameRegistry.registerItem(t3_power_Boots = new PoweredArmor("t3_power_boots", T3_POWER, t3_power_armor_textures, 3,ArmorPowerType.RF,10).setSpeedBoni(0.05f,0.15f,0,0).setMiningBoni(0.05f,0).setFallProtection(0.2f, 1.0f,0,0).setHealthBonus(1,0).setStepAssist(1.0f,0).setRepairMats(TGItems.powerPlating, TGItems.goldWire, 0.5f, 2).setArmorModel(2).setKnockbackResistance(0.20f), "t3_power_boots"); // 3 for boots
		
		//T3 Mining Armor
		String[] t3_miner_textures = {"hevsuit", "hevsuit_silver", "hevsuit_black"};
		GameRegistry.registerItem(t3_miner_Helmet = new PoweredArmor("t3_miner_helmet", T3_MINER, t3_miner_textures, 0,ArmorPowerType.RF,10).setSpeedBoni(0.1f,0.3f,0,0).setWaterspeedBonus(1.25f, 0).setMiningBoni(0.1f,0).setMiningBoniWater(1.25f,0).setOxygenGear(1.0f,0.0f).setHideFaceslot(true).setRepairMats(TGItems.carbonPlate, TGItems.circuitBoardElite, 0.5f, 2).setKnockbackResistance(0.10f), "t3_miner_helmet"); //0 for helmet
		GameRegistry.registerItem(t3_miner_Chestplate = new PoweredArmor("t3_miner_chestplate", T3_MINER, t3_miner_textures, 1,ArmorPowerType.RF,10000).setBattery(TGItems.energyCell).setEmptyBattery(TGItems.energyCellEmpty).setSpeedBoni(0.1f, 0.03f,0,0).setWaterspeedBonus(1.25f, 0).setMiningBoni(0.1f,0).setMiningBoniWater(1.25f,0).setCoolingSystem(1.0f,0).setRepairMats(TGItems.carbonPlate, TGItems.circuitBoardElite, 0.5f, 4).setKnockbackResistance(0.25f), "t3_miner_chestplate"); // 1 for chestplate
		GameRegistry.registerItem(t3_miner_Leggings = new PoweredArmor("t3_miner_leggings", T3_MINER, t3_miner_textures, 2,ArmorPowerType.RF,10).setSpeedBoni(0.1f, 0.03f,0,0).setWaterspeedBonus(1.25f, 0).setMiningBoni(0.1f,0).setMiningBoniWater(1.25f,0).setRepairMats(TGItems.carbonPlate, TGItems.circuitBoardElite, 1.0f/3.0f, 3).setKnockbackResistance(0.15f), "t3_miner_leggings"); // 2 for leggings
		GameRegistry.registerItem(t3_miner_Boots = new PoweredArmor("t3_miner_boots", T3_MINER, t3_miner_textures, 3,ArmorPowerType.RF,10).setSpeedBoni(0.1f, 0.15f,0,0).setWaterspeedBonus(1.25f, 0).setMiningBoni(0.1f,0).setMiningBoniWater(1.25f,0).setStepAssist(1.0f,0).setFallProtection(0.5f, 3.0f,0,0).setRepairMats(TGItems.carbonPlate, TGItems.circuitBoardElite, 0.5f, 2).setKnockbackResistance(0.10f), "t3_miner_boots"); // 3 for boots
		
		String[] exo_armor_textures = {"t3_exo","t3_exo_silver","t3_exo_green"};
		GameRegistry.registerItem(t3_exo_Helmet = new PoweredArmor("t3_exo_helmet", T3_EXO, exo_armor_textures, 0,ArmorPowerType.RF,10).setSpeedBoni(0.2f,0.1f,0.0f,0.0f).setMiningBoni(0.075f,0.0f).setRepairMats(TGItems.carbonPlate, TGItems.carbonFibers, 0.5f, 2).setArmorModel(4).setHideFaceslot(false).setKnockbackResistance(0.15f), "t3_exo_helmet"); //0 for helmet
		GameRegistry.registerItem(t3_exo_Chestplate = new PoweredArmor("t3_exo_chestplate", T3_EXO, exo_armor_textures, 1,ArmorPowerType.RF,7200).setSpeedBoni(0.2f,0.1f,0.0f,0.0f).setMiningBoni(0.075f,0.0f).setBattery(TGItems.energyCell).setEmptyBattery(TGItems.energyCellEmpty).setRepairMats(TGItems.carbonPlate, TGItems.copperWire, 0.5f, 4).setArmorModel(6).setHideBackslot(false).setKnockbackResistance(0.30f), "t3_exo_chestplate"); // 1 for chestplate
		GameRegistry.registerItem(t3_exo_Leggings = new PoweredArmor("t3_exo_leggings", T3_EXO, exo_armor_textures, 2,ArmorPowerType.RF,10).setSpeedBoni(0.2f,0.1f,0f,0f).setMiningBoni(0.075f,0f).setRepairMats(TGItems.carbonPlate, TGItems.carbonFibers, 1.0f/3.0f, 3).setArmorModel(5).setKnockbackResistance(0.20f), "t3_exo_leggings"); // 2 for leggings
		GameRegistry.registerItem(t3_exo_Boots = new PoweredArmor("t3_exo_boots", T3_EXO, exo_armor_textures, 3,ArmorPowerType.RF,10).setSpeedBoni(0.2f,0.3f,0,0).setMiningBoni(0.075f,0).setFallProtection(0.5f, 5.0f,0,0).setStepAssist(1.0f,0).setRepairMats(TGItems.carbonPlate, TGItems.carbonFibers, 0.5f, 2).setArmorModel(6).setKnockbackResistance(0.15f), "t3_exo_boots"); // 3 for boots
		
		String[] t2_beret_textures = {"beret_texture", "beret_texture_black", "beret_texture_green"};
		GameRegistry.registerItem(t2_beret = new GenericArmorMultiCamo("t2_beret", T2_BERET, t2_beret_textures, 0).setSpeedBoni(0.1f,0.0f).setRepairMats(TGItems.heavyCloth, TGItems.heavyCloth, 1.0f, 2).setArmorModel(7), "t2_beret"); //0 for helmet
		
		//Recipes:
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t1_combat_Helmet,1), "iii","c c", 'i', "ingotIron", 'c', TGItems.heavyCloth));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t1_combat_Chestplate,1), "c c","iii","ccc", 'i', "ingotIron", 'c', TGItems.heavyCloth));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t1_combat_Leggings,1), "iii","c c","c c", 'i', "ingotIron", 'c', TGItems.heavyCloth));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t1_combat_Boots,1), "c c","i i", 'i', "ingotIron", 'c', TGItems.heavyCloth));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t1_miner_Helmet,1), "iyi","c c",'i', "ingotIron", 'c', TGItems.heavyCloth, 'y', "dyeYellow"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t1_miner_Chestplate,1), "c c","ccc","ici", 'i', "ingotIron", 'c', TGItems.heavyCloth));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t1_miner_Leggings,1), "ici","c c","c c", 'i', "ingotIron", 'c', TGItems.heavyCloth));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t1_miner_Boots,1), "c c","i i", 'i', "nuggetIron", 'c', TGItems.heavyCloth));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t1_scout_Helmet,1), "ccc","c c", 'c', TGItems.heavyCloth));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t1_scout_Chestplate,1), "c c","ccc","ccc", 'c', TGItems.heavyCloth));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t1_scout_Leggings,1), "ccc","c c","c c", 'c', TGItems.heavyCloth));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t1_scout_Boots,1), "c c","c c", 'c', TGItems.heavyCloth));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t2_combat_Helmet,1), "iii","h h", 'h', TGItems.heavyCloth,'i', "ingotObsidianSteel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t2_combat_Chestplate,1), "h h","iii","iii", 'h', TGItems.heavyCloth,'i', "ingotObsidianSteel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t2_combat_Leggings,1), "ihi","i i","h h", 'h', TGItems.heavyCloth,'i', "ingotObsidianSteel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t2_combat_Boots,1), "h h","i i", 'h',TGItems.heavyCloth,'i', "ingotObsidianSteel"));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t2_commando_Helmet,1), "ihi","hgh", 'h', "itemRubber",'i', "ingotObsidianSteel",'g',"paneGlass"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t2_commando_Chestplate,1), "h h","hih","iii", 'h', "itemRubber",'i', "ingotObsidianSteel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t2_commando_Leggings,1), "hih","i i","h h", 'h', "itemRubber",'i', "ingotObsidianSteel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t2_commando_Boots,1), "i i","h h", 'h',"itemRubber",'i', "ingotObsidianSteel"));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t3_combat_Helmet,1), "ppp","f f", 'f', "fiberCarbon",'p', "plateCarbon"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t3_combat_Chestplate,1), "p p","ppp","fpf", 'f', "fiberCarbon",'p', "plateCarbon"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t3_combat_Leggings,1), "pfp","p p","f f", 'f', "fiberCarbon",'p', "plateCarbon"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t3_combat_Boots,1), "f f","p p", 'f', "fiberCarbon",'p', "plateCarbon"));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steam_Helmet,1), "sss","s s", 's', TGItems.steamArmorPlate));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steam_Chestplate,1), "s s","sss","sss", 's', TGItems.steamArmorPlate));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steam_Leggings,1), "sss","s s","s s", 's', TGItems.steamArmorPlate));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steam_Boots,1), "s s","s s", 's', TGItems.steamArmorPlate));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t3_power_Helmet,1), "cpc","pgp", 'p', TGItems.powerPlating, 'g', "paneGlass", 'c', "circuitElite"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t3_power_Chestplate,1), "p p","ece","ppp", 'p', TGItems.powerPlating, 'c', "circuitElite", 'e', TGItems.electricEngine));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t3_power_Leggings,1), "pcp","e e","p p", 'p', TGItems.powerPlating, 'c', "circuitElite", 'e', TGItems.electricEngine));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t3_power_Boots,1), "e e","p p", 'p', TGItems.powerPlating, 'e',TGItems.electricEngine));
	
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t3_miner_Helmet,1), "pcp","lgl", 'p', "plateCarbon", 'g', "paneGlass", 'c', "circuitElite", 'l', "plateTitanium"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t3_miner_Chestplate,1), "p p","lcl","lpl", 'p',  "plateCarbon", 'c', "circuitElite", 'l', "plateTitanium"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t3_miner_Leggings,1), "ppp","c c","l l", 'p',  "plateCarbon", 'c', "circuitElite", 'l', "plateTitanium"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t3_miner_Boots,1), "l l","p p", 'p',  "plateCarbon", 'l',"plateTitanium"));
	
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(t3_exo_Helmet,1), new ItemStack(t3_combat_Helmet,1,OreDictionary.WILDCARD_VALUE),TGItems.mechanicalPartsT3, "circuitElite", TGItems.electricEngine, "ingotTitanium"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(t3_exo_Chestplate,1), new ItemStack(t3_combat_Chestplate,1,OreDictionary.WILDCARD_VALUE),TGItems.mechanicalPartsT3, "circuitElite", TGItems.electricEngine, "ingotTitanium","ingotTitanium"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(t3_exo_Leggings,1), new ItemStack(t3_combat_Leggings,1,OreDictionary.WILDCARD_VALUE),TGItems.mechanicalPartsT3, "circuitElite", TGItems.electricEngine, "ingotTitanium","ingotTitanium"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(t3_exo_Boots,1), new ItemStack(t3_combat_Boots,1,OreDictionary.WILDCARD_VALUE),TGItems.mechanicalPartsT3, "circuitElite", TGItems.electricEngine, "ingotTitanium"));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t2_riot_Helmet,1), "php","l l",'p', "plateLead", 'l', TGItems.newStack(TGItems.treatedLeather, 1), 'h', new ItemStack(t2_combat_Helmet,1,OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t2_riot_Chestplate,1), "p p","lcl","lpl",'p', "plateLead", 'l', TGItems.newStack(TGItems.treatedLeather, 1), 'c', new ItemStack(t2_combat_Chestplate,1,OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t2_riot_Leggings,1), "lhl","p p","l l",'p', "plateLead", 'l', TGItems.newStack(TGItems.treatedLeather, 1), 'h', new ItemStack(t2_combat_Leggings,1,OreDictionary.WILDCARD_VALUE)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t2_riot_Boots,1), "lhl","p p",'p', "plateLead", 'l', TGItems.newStack(TGItems.treatedLeather, 1), 'h', new ItemStack(t2_combat_Boots,1,OreDictionary.WILDCARD_VALUE)));
		
		
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(t2_beret,1), "cc ","l l", 'l', Items.leather,'c', TGItems.heavyCloth));
	}
	
}
