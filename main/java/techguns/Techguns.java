package techguns;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import techguns.blocks.GenericBlock;
import techguns.client.particle.EntityParticleSystem;
import techguns.dispenser.BehaviorProjectileDispenseDamagevalue;
import techguns.entities.npc.TGNpcFactions;
import techguns.entities.projectiles.BeamProjectile;
import techguns.entities.projectiles.BiogunProjectile;
import techguns.entities.projectiles.BlasterRifleProjectile;
import techguns.entities.projectiles.CyberdemonBlasterProjectile;
import techguns.entities.projectiles.EnergyBlastProjectile;
import techguns.entities.projectiles.FlamethrowerProjectile;
import techguns.entities.projectiles.FlyingGibs;
import techguns.entities.projectiles.FragGrenadeProjectile;
import techguns.entities.projectiles.GenericProjectile;
import techguns.entities.projectiles.GrenadeLauncherProjectile;
import techguns.entities.projectiles.GrenadeProjectile;
import techguns.entities.projectiles.InvisibleProjectile;
import techguns.entities.projectiles.LaserBeam;
import techguns.entities.projectiles.LaserBeam2;
import techguns.entities.projectiles.LaserProjectile;
import techguns.entities.projectiles.MiningDummyProjectile;
import techguns.entities.projectiles.MovingBeam;
import techguns.entities.projectiles.NeutrinoBeam;
import techguns.entities.projectiles.NukeRocketProjectile;
import techguns.entities.projectiles.PowerHammerProjectile;
import techguns.entities.projectiles.RocketProjectile;
import techguns.entities.projectiles.SimpleProjectile;
import techguns.entities.projectiles.SonicShotgunProjectile;
import techguns.entities.projectiles.TeslaBeam;
import techguns.entities.projectiles.TeslaProjectile;
import techguns.events.TechgunsEventhandler;
import techguns.events.TechgunsTickHandler;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.gui.TechgunsGuiHandler;
import techguns.gui.playerinventory.TGPlayerInventory;
import techguns.items.BlockReader;
import techguns.items.BuildingScanTool;
import techguns.items.GenericItem;
import techguns.items.RecipeAkimboGun;
import techguns.items.RecipeAkimboSplit;
import techguns.items.ShapelessOreRecipeCopyNBT;
import techguns.items.StructureTestTool;
import techguns.items.oreClusterDebugger;
import techguns.items.guns.GenericGrenade;
import techguns.plugins.ftbutilities.FTBUtilitiesIntegrationActual;
import techguns.plugins.ftbutilities.FTBUtilitiesIntegrationDummy;
import techguns.plugins.minetweaker.TechgunsMineTweakerIntegration;
import techguns.plugins.tconstruct.ITechgunsTConstructIntegration;
import techguns.plugins.thermalexpansion.TechgunsThermalExpansionIntegration;
import techguns.tileentities.AmmoPressTileEnt;
import techguns.tileentities.BioblobTileEnt;
import techguns.tileentities.CamoBenchTileEnt;
import techguns.tileentities.CampFlagTileEnt;
import techguns.tileentities.ChargingStationTileEnt;
import techguns.tileentities.ChemLabTileEnt;
import techguns.tileentities.ExplosiveChargeAdvTileEnt;
import techguns.tileentities.ExplosiveChargeTileEnt;
import techguns.tileentities.FabricatorTileEntMaster;
import techguns.tileentities.FabricatorTileEntSlave;
import techguns.tileentities.MetalPressTileEnt;
import techguns.tileentities.OreDrillTileEnt;
import techguns.tileentities.OreDrillTileEntMaster;
import techguns.tileentities.OreDrillTileEntSlave;
import techguns.tileentities.ReactionChamberTileEntMaster;
import techguns.tileentities.ReactionChamberTileEntSlave;
import techguns.tileentities.RepairBenchTileEnt;
import techguns.tileentities.ScrapperTileEnt;
import techguns.tileentities.TGChestTileEnt;
import techguns.tileentities.TGSpawnerTileEnt;
import techguns.tileentities.TurretBaseEnt;
import techguns.worldgen.OreGenerator;
import techguns.worldgen.WorldGenOilHoles;
import techguns.worldgen.WorldGenTGStructureSpawn;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler; // used in 1.6.2
//import cpw.mods.fml.common.Mod.PreInit;    // used in 1.5.2
//import cpw.mods.fml.common.Mod.Init;       // used in 1.5.2
//import cpw.mods.fml.common.Mod.PostInit;   // used in 1.5.2
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
//import cpw.mods.fml.common.network.NetworkMod; // not used in 1.7
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid=Techguns.MODID, name=Techguns.MODNAME, version=Techguns.VERSION, dependencies="after:ForgeMultipart;after:CoFHCore;after:Chisel;after:TConstruct;after:FTBU")
//@NetworkMod(clientSideRequired=true) // not used in 1.7
public class Techguns {
	
		public static final String MODID = "Techguns";
	    public static final String MODNAME = "Techguns";
	    public static final String VERSION = "0.1.2_alphatest4.1";
	
	
		public static int modEntityID = 0;

		
		
		/**
		 * Config constants
		 * 
		 */
		
		/**
		 * if Tinkers construct is loaded
		 */
		public static boolean isTConstructLoaded=false;
		public static ITechgunsTConstructIntegration pluginTConstructIntegration;
		
		/**
		 * Config end
		 */
		
    	//Test Items
    	//public static Item testItem;
    	//public static Item testIngot;
    	//public static Item testGun;
    	//public static Item testMag;
    	
    	public static Item structureTestTool;
    	public static Item blockReaderTool;
    	public static Item buildingScanTool;
    	public static Item oreClusterDebugTool;
	
    	//Ingots
    	public static Item ingotSteel;
    	public static Item ingotLead;
    	public static Item ingotCopper;
    	public static Item ingotTin;
    	public static Item ingotBronze;
    	
    	public static Item nuggetIron;
    	public static Item nuggetCopper;
    	public static Item nuggetLead;
    	public static Item nuggetSteel;
    	
    	//Ores
    	public static Block oreLead;
    	public static Block oreCopper;
    	public static Block oreTin;


    	
    	//Turret stuff
    	public static Block turretBase;
    	
    	
    	public static CreativeTabs tabTechgun = new CreativeTabs("Techguns") {
    	    @Override
    	    @SideOnly(Side.CLIENT)
    	    public Item getTabIconItem() {
    	        return TGuns.handcannon;
    	    }

			@Override
			public String getTranslatedTabLabel() {
				return "techguns."+super.getTranslatedTabLabel();
			}
    	    
    	};
    	
        // The instance of your mod that Forge uses.
        @Instance(value = "Techguns")
        public static Techguns instance;
       
        // Says where the client and server 'proxy' code is loaded.
        @SidedProxy(clientSide="techguns.client.ClientProxy", serverSide="techguns.CommonProxy")
        public static CommonProxy proxy;
       
        @EventHandler // used in 1.6.2
        //@PreInit    // used in 1.5.2
        public void preInit(FMLPreInitializationEvent event) {
        	
        	/*//Load the config file
        	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        	config.load();
        	
        	this.doOreGenLead = config.getBoolean("doOreGenLead", config.CATEGORY_GENERAL, false, "Generate Lead Ore, disable if other mod does (like Thermal Expansion)");
        	this.doOreGenTinCopper = config.getBoolean("doOreGenCopperTin", config.CATEGORY_GENERAL, false, "Generate Copper and Tin Ore, disable if other mod does. (TE and Mekansim)");
        	
        	this.addSteelCrafting = config.getBoolean("addSteelCrafting", config.CATEGORY_GENERAL, false, "Add steel crafting by resmelting iron ingots. Disable if other mod adds steel crafting (Mekanism, Railcraft)");
        	
        	this.addBronzeCrafting = config.getBoolean("addBronzeCrafting", config.CATEGORY_GENERAL, false, "Craft bronze ingot with 3 copper and 1 tin. Disable if other mod adds bronze");
        	
        	this.addCopperTinBronzeNuggets = config.getBoolean("addCopperTinBronzeNuggets", config.CATEGORY_GENERAL, false, "Add nuggets and crafting for Copper (Tin and Bronze are not in use), needed with only Mekanism, Not needed with TE");
        	
        	this.addLead = config.getBoolean("addLeadIngot", config.CATEGORY_GENERAL, false, "Add Lead ingots.");
        	
        	this.addLeadNuggets = config.getBoolean("addLeadNuggets", config.CATEGORY_GENERAL, false, "Add Lead nuggets and crafting. Disable if other mod adds them. (Thermal Expansion");
        	
        	this.addSteel = config.getBoolean("addSteelIngot", config.CATEGORY_GENERAL, false, "Adds Steel ingots.");
        	this.addSteelNuggets = config.getBoolean("addSteelNuggets", config.CATEGORY_GENERAL, false, "Add Steel nuggets and crafting, if Techguns steel is not added, it tries to add them to the first Steel found in the OreDict");
        	
        	this.addIronNuggets = config.getBoolean("addIronNuggets", config.CATEGORY_GENERAL, false, "Add Iron nuggets and crafting");
        	
        	config.save();*/
        	TGConfig.init(event);
        	
        	/* testItem = new GenericItem();
        	 testIngot = new GenericItem().setMaxStackSize(16).setUnlocalizedName("testIngot").setTextureName("techguns:testIngot");
        	 testMag = new GenericItemShared();
        	 testGun = new GenericGun(new ItemStack(testMag,1));*/
        	 structureTestTool = new StructureTestTool().setUnlocalizedName("structureTestTool").setTextureName("techguns:structureTestTool");
        	 blockReaderTool = new BlockReader().setUnlocalizedName("blockReaderTool").setTextureName("techguns:blockReaderTool");
        	 buildingScanTool = new BuildingScanTool().setUnlocalizedName("buildingScanTool").setTextureName("techguns:structureScanTool");
        	 oreClusterDebugTool = new oreClusterDebugger();
        	 
        	/* stoneBarrel = new GenericItem().setMaxStackSize(64).setUnlocalizedName("stoneBarrel").setTextureName("techguns:stoneBarrel");
        	 woodStock = new GenericItem().setMaxStackSize(64).setUnlocalizedName("woodStock").setTextureName("techguns:woodStock");
        	 ironBarrel = new GenericItem().setMaxStackSize(64).setUnlocalizedName("ironBarrel").setTextureName("techguns:ironBarrel"); 
        	 ironGears = new GenericItem().setMaxStackSize(64).setUnlocalizedName("ironGears").setTextureName("techguns:ironGears"); 
        	 spring = new GenericItem().setMaxStackSize(64).setUnlocalizedName("spring").setTextureName("techguns:spring");
        	 fireMechanism = new GenericItem().setMaxStackSize(64).setUnlocalizedName("fireMechanism").setTextureName("techguns:fireMechanism");
        	 ironGunstock = new GenericItem().setMaxStackSize(64).setUnlocalizedName("ironGunstock").setTextureName("techguns:ironGunstock");  
        	 pumpMechanism = new GenericItem().setMaxStackSize(64).setUnlocalizedName("pumpMechanism").setTextureName("techguns:pumpMechanism");
        	 
        	 plastic = new GenericItem().setMaxStackSize(64).setUnlocalizedName("plastic").setTextureName("techguns:plastic");
        	 plasticForegrip = new GenericItem().setMaxStackSize(64).setUnlocalizedName("foregrip").setTextureName("");
        	 plasticGunstock = new GenericItem().setMaxStackSize(64).setUnlocalizedName("plasticGunstock").setTextureName("");
        	 
        	 fireMechanismT2 = new GenericItem().setMaxStackSize(64).setUnlocalizedName("fireMechanismT2").setTextureName("techguns:fireMechanismT2");
        	 obsidianSteelBarrel = new GenericItem().setMaxStackSize(64).setUnlocalizedName("obsidianSteelBarrel").setTextureName("techguns:obsidianSteelBarrel"); 
        	 
        	 laserBarrel = new GenericItem().setMaxStackSize(64).setUnlocalizedName("laserBarrel").setTextureName("techguns:laserBarrel");
        	 laserFocus = new GenericItem().setMaxStackSize(64).setUnlocalizedName("laserFocus").setTextureName("techguns:laserfocus");
        	 fireMechanismT3 = new GenericItem().setMaxStackSize(64).setUnlocalizedName("fireMechanismT3").setTextureName("techguns:fireMechanismT3");*/
        	 
        	 /*stonebullets = new GenericItem().setMaxStackSize(64).setUnlocalizedName("stonebullets").setTextureName("techguns:handgunBullet");
        	 bullets9mm = new GenericItem().setMaxStackSize(64).setUnlocalizedName("bullets9mm").setTextureName("techguns:9mmBullets");
        	 shotgunShell = new GenericItem().setMaxStackSize(64).setUnlocalizedName("shotgunShells").setTextureName("techguns:shotgunShell");
        	 rifleBullets = new GenericItem().setMaxStackSize(64).setUnlocalizedName("rifleBullets").setTextureName("techguns:rifleBullets");
        	 highCalibreBullets = new GenericItem().setMaxStackSize(64).setUnlocalizedName("highCalibreBullets").setTextureName("techguns:highCalibreBullets");
        	 advBullets = new GenericItem().setMaxStackSize(64).setUnlocalizedName("adbBullets").setTextureName("techguns:advBullets");
        	 
        	 pistolMag9mm = new GenericItem().setMaxStackSize(64).setUnlocalizedName("pistolMag9mm").setTextureName("techguns:pistolMag");
        	 pistolMag9mmEmtpy = new GenericItem().setMaxStackSize(64).setUnlocalizedName("pistolMag9mmEmpty").setTextureName("techguns:pistolMagEmpty");
        	 
        	 smgMag = new GenericItem().setMaxStackSize(64).setUnlocalizedName("smgMag").setTextureName("techguns:SMGMagazine");
        	 smgMagEmpty = new GenericItem().setMaxStackSize(64).setUnlocalizedName("smgMagEmpty").setTextureName("techguns:SMGMagazineEmpty");
        	 
        	 pdwMag = new GenericItem().setMaxStackSize(64).setUnlocalizedName("pdwMag").setTextureName("techguns:pdwMag");
        	 pdwMagEmpty = new GenericItem().setMaxStackSize(64).setUnlocalizedName("pdwMagEmpty").setTextureName("techguns:pdwMagEmpty");
        	 
        	 //No texture, 3d model
        	 m4Mag = new GenericItem().setMaxStackSize(64).setUnlocalizedName("m4Mag");
        	 m4MagEmpty = new GenericItem().setMaxStackSize(64).setUnlocalizedName("m4MagEmpty");
        	 
        	 //No texture, 3d model
        	 as50Mag = new GenericItem().setMaxStackSize(64).setUnlocalizedName("as50Mag");
        	 as50MagEmpty = new GenericItem().setMaxStackSize(64).setUnlocalizedName("as50MagEmpty");
        	 
        	//No texture, 3d model
        	 lmgMag = new GenericItem().setMaxStackSize(64).setUnlocalizedName("lmgMag");
        	 lmgMagEmpty = new GenericItem().setMaxStackSize(64).setUnlocalizedName("lmgMagEmpty");
        	 
        	 flamethrowerTank = new GenericItem().setMaxStackSize(64).setUnlocalizedName("flamethrowerTank").setTextureName("techguns:flamethrowerTank");
        	 flamethrowerTankEmpty = new GenericItem().setMaxStackSize(64).setUnlocalizedName("flamethrowerTank").setTextureName("techguns:flamethrowerTankEmpty");
        	 
        	 bioTank = new GenericItem().setMaxStackSize(64).setUnlocalizedName("bioTank").setTextureName("techguns:biotank");
        	 bioTankEmpty = new GenericItem().setMaxStackSize(64).setUnlocalizedName("bioTank").setTextureName("techguns:biotank_empty");*/
        	// bioMass = new GenericItem().setMaxStackSize(64).setUnlocalizedName("bioMass").setTextureName("techguns:biomass");
        	 
        	 /*rocketAmmo = new GenericItem().setMaxStackSize(64).setUnlocalizedName("rocketAmmo");
        	 
        	 energyCell = new GenericItem().setMaxStackSize(64).setUnlocalizedName("energyCell").setTextureName("techguns:energyCell");
        	 energyCellEmpty = new GenericItem().setMaxStackSize(64).setUnlocalizedName("energyCellEmpty").setTextureName("techguns:energyCellEmpty");*/
        	 
        	 TGItems.init();
        	 
        	 TGuns.init();
        	
        	 TGWeaponStats.init(event);
        	 
          	 //metals
        	 ingotSteel = new GenericItem().setMaxStackSize(64).setUnlocalizedName("ingotSteel").setTextureName("techguns:ingotSteel");
        	 ingotCopper = new GenericItem().setMaxStackSize(64).setUnlocalizedName("ingotCopper").setTextureName("techguns:ingotCopper");
        	 ingotTin = new GenericItem().setMaxStackSize(64).setUnlocalizedName("ingotTin").setTextureName("techguns:ingotTin");
        	 ingotBronze = new GenericItem().setMaxStackSize(64).setUnlocalizedName("ingotBronze").setTextureName("techguns:ingotBronze");
        	 ingotLead = new GenericItem().setMaxStackSize(64).setUnlocalizedName("ingotLead").setTextureName("techguns:ingotLead");
        	 //ingotObsidianSteel = new GenericItem().setMaxStackSize(64).setUnlocalizedName("ingotObsidianSteel").setTextureName("techguns:ingotGunSteel");
        	 
        	 nuggetIron = new GenericItem().setMaxStackSize(64).setUnlocalizedName("nuggetIron").setTextureName("techguns:nuggetIron");
        	 nuggetCopper = new GenericItem().setMaxStackSize(64).setUnlocalizedName("nuggetCopper").setTextureName("techguns:copperNugget");
        	 nuggetLead = new GenericItem().setMaxStackSize(64).setUnlocalizedName("nuggetLead").setTextureName("techguns:nuggetLead");
        	 nuggetSteel = new GenericItem().setMaxStackSize(64).setUnlocalizedName("nuggetSteel").setTextureName("techguns:nuggetSteel");
        	 
        	 //Armor materials
        	 //heavyCloth = new GenericItem().setMaxStackSize(64).setUnlocalizedName("heavyCloth").setTextureName("techguns:heavycloth");
        	 
        	 
        	 if (TGConfig.doOreGenLead) {
	        	 //ores
	        	 oreLead = new GenericBlock(Material.rock).setBlockTextureName("techguns:oreLeadBlock").setBlockName("oreLead");
        	 }
	         if (TGConfig.doOreGenTinCopper) {
	        	 oreTin = new GenericBlock(Material.rock).setBlockTextureName("techguns:oreTinBlock").setBlockName("oreTin");
	        	 oreCopper = new GenericBlock(Material.rock).setBlockTextureName("techguns:oreCopperBlock").setBlockName("oreCopper");
	         }
        	 
	        

             // The second parameter is an unique registry identifier (not the displayed name)
             // Please don't use item1.getUnlocalizedName(), or you will make Lex sad
            // GameRegistry.registerItem(testItem, "TestItem");
            // GameRegistry.registerItem(testIngot, "TestIngot");
            // GameRegistry.registerItem(testGun, "TestGun");
            // GameRegistry.registerItem(testMag, "TestMag");
        	 
        	 GameRegistry.registerItem(structureTestTool, "structureTestTool");
        	 GameRegistry.registerItem(blockReaderTool, "blockReaderTool");
        	 GameRegistry.registerItem(buildingScanTool, "buildingScanTool");
        	 GameRegistry.registerItem(oreClusterDebugTool, "oreClusterDebugger");
             
        	 if (TGConfig.addSteel){
        		 GameRegistry.registerItem(ingotSteel, "ingotSteel");
        	 }
             
             if (!this.dontAddCopperTinBronze()){
	             GameRegistry.registerItem(ingotCopper, "ingotCopper");
	             GameRegistry.registerItem(ingotTin, "ingotTin");
	             GameRegistry.registerItem(ingotBronze, "ingotBronze");
             }
             
             if (TGConfig.addLead){
            	 GameRegistry.registerItem(ingotLead, "ingotLead");
             }
             
             //GameRegistry.registerItem(ingotObsidianSteel, "ingotObsidianSteel");
             
             //nuggets
             if (TGConfig.addIronNuggets){
            	 GameRegistry.registerItem(nuggetIron, "nuggetIron");
             }
             if (TGConfig.addLeadNuggets) {
            	 GameRegistry.registerItem(nuggetLead, "nuggetLead");
             }
             if (TGConfig.addSteelNuggets){
            	 GameRegistry.registerItem(nuggetSteel, "nuggetSteel");
             }
             
             if (TGConfig.addCopperTinBronzeNuggets) {
            	 GameRegistry.registerItem(nuggetCopper, "nuggetCopper");
             }
             
             if (TGConfig.addIronNuggets){
            	 GameRegistry.registerItem(nuggetIron, "nuggetIron");
             }
             
             //parts
          /*   GameRegistry.registerItem(stoneBarrel, "stoneBarrel");
             GameRegistry.registerItem(woodStock, "woodStock");
             GameRegistry.registerItem(ironBarrel, "ironBarrel");
             GameRegistry.registerItem(ironGears, "ironGears");
             GameRegistry.registerItem(spring, "spring");
             GameRegistry.registerItem(fireMechanism, "fireMechanism");
             GameRegistry.registerItem(ironGunstock, "ironGunstock");
             GameRegistry.registerItem(pumpMechanism, "pumpMechanism");
             GameRegistry.registerItem(fireMechanismT2, "fireMechanismT2");
             GameRegistry.registerItem(obsidianSteelBarrel, "obsidianSteelBarrel");
             GameRegistry.registerItem(laserBarrel, "laserBarrel");
             GameRegistry.registerItem(laserFocus, "laserFocus");
             GameRegistry.registerItem(fireMechanismT3, "fireMechanismT3");
             
             GameRegistry.registerItem(heavyCloth,"heavyCloth");*/
             
             /*GameRegistry.registerItem(plastic, "plastic");
             GameRegistry.registerItem(plasticForegrip, "foregrip");
             GameRegistry.registerItem(plasticGunstock, "plasticGunstock");*/
             
 
             //tools
             //GameRegistry.registerItem(driller, "driller");
             
             //ammo
          /*   GameRegistry.registerItem(stonebullets, "stonebullets");
             GameRegistry.registerItem(bullets9mm, "bullets9mm");
             GameRegistry.registerItem(pistolMag9mm, "pistolMag9mm");
             GameRegistry.registerItem(pistolMag9mmEmtpy, "pistolMag9mmEmpty");
             GameRegistry.registerItem(shotgunShell, "shotgunShell");
             GameRegistry.registerItem(smgMag, "smgMag");
             GameRegistry.registerItem(smgMagEmpty, "smgMagEmpty");
             GameRegistry.registerItem(rifleBullets, "rifleBullets");
             GameRegistry.registerItem(flamethrowerTank, "flamethrowerTank");
             GameRegistry.registerItem(flamethrowerTankEmpty, "flamethrowerTankEmpty");
             GameRegistry.registerItem(bioTank, "bioTank");
             GameRegistry.registerItem(bioTankEmpty, "bioTankEmpty");*/
             
             //GameRegistry.registerItem(bioMass, "bioMass");
             
            /* GameRegistry.registerItem(m4Mag,"m4Mag");
             GameRegistry.registerItem(m4MagEmpty,"m4MagEmpty");
             GameRegistry.registerItem(rocketAmmo, "rocketAmmo");
             GameRegistry.registerItem(energyCell, "energyCell");
             GameRegistry.registerItem(energyCellEmpty, "energyCellEmpty");
             GameRegistry.registerItem(as50Mag,"as50Mag");
             GameRegistry.registerItem(as50MagEmpty,"as50MagEmpty");
             GameRegistry.registerItem(highCalibreBullets, "highCalibreBullets");
             GameRegistry.registerItem(lmgMag,"lmgMag");
             GameRegistry.registerItem(lmgMagEmpty,"lmgMagEmpty");
             GameRegistry.registerItem(advBullets, "advBullets");
             GameRegistry.registerItem(pdwMag,"pdwMag");
             GameRegistry.registerItem(pdwMagEmpty,"pdwMagEmpty");*/
             

             //GameRegistry.registerItem(flashbang,"flashbang");
             
             //ores
             if (TGConfig.doOreGenLead){
            	 GameRegistry.registerBlock(oreLead, "oreLead");
             }
             if (TGConfig.doOreGenTinCopper){
	             GameRegistry.registerBlock(oreTin, "oreTin");
	             GameRegistry.registerBlock(oreCopper, "oreCopper");
             }
                          
             //OreDict
             
             //oreDict for default items
             OreDictionary.registerOre("itemGunpowder", Items.gunpowder);
             
             if (TGConfig.doOreGenLead){
            	 OreDictionary.registerOre("oreLead", oreLead);
             }
             if (TGConfig.doOreGenTinCopper) {
	             OreDictionary.registerOre("oreTin", oreTin);
	             OreDictionary.registerOre("oreCopper", oreCopper);
             }
             
             if (TGConfig.addSteel){
            	 OreDictionary.registerOre("ingotSteel", ingotSteel);
             }
             
             if (!this.dontAddCopperTinBronze()){
	             OreDictionary.registerOre("ingotTin", ingotTin);
	             OreDictionary.registerOre("ingotCopper", ingotCopper);
	             OreDictionary.registerOre("ingotBronze", ingotBronze);
             }
             if (TGConfig.addLead){
            	 OreDictionary.registerOre("ingotLead", ingotLead);
             }
             
                                   
             
             if (TGConfig.addIronNuggets){
            	 OreDictionary.registerOre("nuggetIron", nuggetIron);
             }
             if (TGConfig.addCopperTinBronzeNuggets){
            	 OreDictionary.registerOre("nuggetCopper",nuggetCopper);
             }
             if (TGConfig.addLeadNuggets){
            	 OreDictionary.registerOre("nuggetLead", nuggetLead);
             }
             if (TGConfig.addSteelNuggets){
            	 OreDictionary.registerOre("nuggetSteel", nuggetSteel);
             }
             
             //OreDictionary.registerOre("gearIron", ironGears);
             
             EntityRegistry.registerModEntity(GenericProjectile.class, "GenericProjectile", ++modEntityID,  Techguns.instance, 80, 3, true);
             EntityRegistry.registerModEntity(SimpleProjectile.class, "SimpleProjectile", ++modEntityID,  Techguns.instance, 80, 3, true);
             EntityRegistry.registerModEntity(FlamethrowerProjectile.class, "FlamethrowerProjectile", ++modEntityID,  Techguns.instance, 80, 3, true);
             EntityRegistry.registerModEntity(LaserProjectile.class, "LaserProjectile", ++modEntityID,  Techguns.instance, 80, 3, true);
             EntityRegistry.registerModEntity(LaserBeam.class, "LaserBeam", ++modEntityID,  Techguns.instance, 80, 3, true);
             EntityRegistry.registerModEntity(RocketProjectile.class, "RocketProjectile", ++modEntityID,  Techguns.instance, 80, 3, true);
             EntityRegistry.registerModEntity(NukeRocketProjectile.class, "NukeRocketProjectile", ++modEntityID,  Techguns.instance, 80, 3, true);
             EntityRegistry.registerModEntity(BiogunProjectile.class, "BiogunProjectile", ++modEntityID,  Techguns.instance, 80, 3, true);
             //EntityRegistry.registerModEntity(BlasterProjectile.class, "BlasterProjectile", ++modEntityID,  Techguns.instance, 80, 3, true);
             EntityRegistry.registerModEntity(TeslaProjectile.class, "TeslaProjectile", ++modEntityID,  Techguns.instance, 80, 3, true);
             EntityRegistry.registerModEntity(TeslaBeam.class, "TeslaBeam", ++modEntityID,  Techguns.instance, 80, 3, true);
             EntityRegistry.registerModEntity(GrenadeProjectile.class, "GrenadeProjectile", ++modEntityID, Techguns.instance, 120, 1, true);
             EntityRegistry.registerModEntity(MiningDummyProjectile.class, "DummyMiningProjectile", ++modEntityID, Techguns.instance, 80, 3, true);
             EntityRegistry.registerModEntity(CyberdemonBlasterProjectile.class, "CyberdemonBlasterProjectile", ++modEntityID,  Techguns.instance, 80, 3, true);
             //EntityRegistry.registerModEntity(TurretEntityUNUSED.class, "Turret", ++modEntityID, Techguns.instance, 80, 1, true);
             EntityRegistry.registerModEntity(BlasterRifleProjectile.class, "BlasterRifleProjectile", ++modEntityID, Techguns.instance, 80, 3, true);
             EntityRegistry.registerModEntity(PowerHammerProjectile.class, "PowerHammerProjectile", ++modEntityID, Techguns.instance, 80, 3, true);
             EntityRegistry.registerModEntity(FlyingGibs.class, "FlyingGibs", ++modEntityID, Techguns.instance, 80, 3, true);
             EntityRegistry.registerModEntity(EntityParticleSystem.class, "ParticleSystem", ++modEntityID, Techguns.instance, 150, 3, true);
             EntityRegistry.registerModEntity(LaserBeam2.class, "LaserBeam2", ++modEntityID,  Techguns.instance, 80, 3, true);
             EntityRegistry.registerModEntity(GrenadeLauncherProjectile.class, "GrenadeLauncherProjectile", ++modEntityID, Techguns.instance, 120, 1, true);
             EntityRegistry.registerModEntity(SonicShotgunProjectile.class, "SonicShotgunProjectile", ++modEntityID, Techguns.instance, 80, 1, true);
             EntityRegistry.registerModEntity(InvisibleProjectile.class, "InvisibleProjectile", ++modEntityID,  Techguns.instance, 80, 3, true);
             EntityRegistry.registerModEntity(MovingBeam.class, "MovingBeam", ++modEntityID,  Techguns.instance, 80, 3, true);
             EntityRegistry.registerModEntity(BeamProjectile.class, "BeamProjectile", ++modEntityID,  Techguns.instance, 80, 3, true);
             EntityRegistry.registerModEntity(FragGrenadeProjectile.class, "FragGrenadeProjectile", ++modEntityID, Techguns.instance, 120, 1, true);
             EntityRegistry.registerModEntity(NeutrinoBeam.class, "NeutrinoBeam", ++modEntityID,  Techguns.instance, 80, 3, true);
             EntityRegistry.registerModEntity(EnergyBlastProjectile.class, "EnergyBlastProjectile", ++modEntityID, Techguns.instance, 80, 3, true);
              
             
             //Recipes
             
             //parts
         
              
             //Bronze
             if (TGConfig.addBronzeCrafting) {
            	 GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ingotBronze, 4), "ingotCopper","ingotCopper","ingotCopper","ingotTin"));
             }
             //Steel
             if (TGConfig.addSteelCrafting){
            	 GameRegistry.addSmelting(Items.iron_ingot, new ItemStack(ingotSteel,1), 0.35f);
             }
             
             //GameRegistry.addSmelting(Items.slime_ball, new ItemStack(plastic,2), 0.35f);
             
             
             
             //iron nuggets
             if (TGConfig.addIronNuggets){
	             GameRegistry.addShapelessRecipe(new ItemStack(nuggetIron,9), Items.iron_ingot);
	             GameRegistry.addShapedRecipe(new ItemStack(Items.iron_ingot,1), "nnn","nnn","nnn", 'n', nuggetIron);
             }
             
             //GameRegistry.addShapelessRecipe(new ItemStack(nuggetCopper,9), ingotCopper);
             if (TGConfig.addCopperTinBronzeNuggets) {
	             GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggetCopper,9), "ingotCopper"));
	             GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingotCopper,1), "nnn","nnn","nnn", 'n', "nuggetCopper"));
             }
             if (TGConfig.addLeadNuggets){
	             GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggetLead,9), "ingotLead"));
	             GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingotLead,1), "nnn","nnn","nnn", 'n', "nuggetLead"));
             }
             if (TGConfig.addSteelNuggets){
            	 if (TGConfig.addSteel){
		             GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggetSteel,9), ingotSteel));
		             GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingotSteel,1), "nnn","nnn","nnn", 'n', nuggetSteel));
            	 } else {
            		//Moved to post init
            	 }
             }
             
             //Ore Processing
             if (TGConfig.doOreGenTinCopper) {
	             GameRegistry.addSmelting(oreCopper, new ItemStack(ingotCopper,1), 0.7f);
	             GameRegistry.addSmelting(oreTin, new ItemStack(ingotTin,1), 0.7f);
             }
             if (TGConfig.doOreGenLead){
            	 GameRegistry.addSmelting(oreLead, new ItemStack(ingotLead,1), 0.7f);
             }
             
             
             //Turret
             //GameRegistry.registerBlock(turretBase, "TurretBase");
             
             GameRegistry.registerTileEntity(BioblobTileEnt.class, "BioblobTileEnt");
             
             GameRegistry.registerTileEntity(AmmoPressTileEnt.class, "AmmoPressTileEnt");
             
             GameRegistry.registerTileEntity(OreDrillTileEnt.class, "OreDrillTileEnt");
             GameRegistry.registerTileEntity(MetalPressTileEnt.class, "MetalPressTileEnt");
             
             GameRegistry.registerTileEntity(TurretBaseEnt.class, "TurretBaseEnt");
             
             GameRegistry.registerTileEntity(CamoBenchTileEnt.class, "CamoBenchTileEnt");
             GameRegistry.registerTileEntity(ChemLabTileEnt.class, "ChemLabTileEnt");
             
             GameRegistry.registerTileEntity(OreDrillTileEntMaster.class, "OreDrillTileEntMaster");
             GameRegistry.registerTileEntity(OreDrillTileEntSlave.class, "OreDrillTileEntSlave");
             
             GameRegistry.registerTileEntity(RepairBenchTileEnt.class, "RepairBenchTileEnt");
             
             GameRegistry.registerTileEntity(ExplosiveChargeTileEnt.class, "ExplosiveChargeTileEnt");
             
             GameRegistry.registerTileEntity(CampFlagTileEnt.class, "CampFlagTileEnt");
             
             GameRegistry.registerTileEntity(ExplosiveChargeAdvTileEnt.class, "ExplosiveChargeAdvTileEnt");
             
             GameRegistry.registerTileEntity(ReactionChamberTileEntMaster.class, "ReactionChamberTileEntMaster");
             GameRegistry.registerTileEntity(ReactionChamberTileEntSlave.class, "ReactionChamberTileEntSlave");
             
             GameRegistry.registerTileEntity(FabricatorTileEntMaster.class, "FabricatorTileEntMaster");
             GameRegistry.registerTileEntity(FabricatorTileEntSlave.class, "FabricatorTileEntSlave");
             
             GameRegistry.registerTileEntity(ChargingStationTileEnt.class, "ChargingStationTileEnt");
             
             GameRegistry.registerTileEntity(ScrapperTileEnt.class, "ScrapperTileEnt");
             
             GameRegistry.registerTileEntity(TGSpawnerTileEnt.class, "TGSpawnerTileEnt");
             
             GameRegistry.registerTileEntity(TGChestTileEnt.class, "TGChestTileEnt");
             
             
             
             //register recipe handlers
             RecipeSorter.register(Techguns.MODID+":akimboGun", RecipeAkimboGun.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
             RecipeSorter.register(Techguns.MODID+":akimboSplit", RecipeAkimboSplit.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
             RecipeSorter.register(Techguns.MODID+":shapelessNBTcopy", ShapelessOreRecipeCopyNBT.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
              
             
             
             /*
              * ------------------------------
              * ADD CATEGORY INIT CODES HERE
              * -----------------------------------
              */
             
             TGArmors.init();
             //Guns.init();
             //Items.init();
             TGFluids.init();
             TGBlocks.init();
             TGEntities.init();
             TGPackets.init();
            // new TGMultiPartRegister().init();
 
             
             proxy.loadFXList();
             /*
              * END INIT 
              */
             
             
             
             
             
             //Register Worldgen
             if (TGConfig.doOreGenTitanium||TGConfig.doOreGenLead||TGConfig.doOreGenTinCopper||TGConfig.doOreGenUranium){
            	 GameRegistry.registerWorldGenerator(new OreGenerator(TGConfig.doOreGenLead, TGConfig.doOreGenTinCopper, TGConfig.doOreGenTitanium, TGConfig.doOreGenUranium), 1);
             }
             //GameRegistry.registerWorldGenerator(new OreClusterWorldspawn(), 3);
             
                         
             
             //Register Dispenser Behaviour
             BlockDispenser.dispenseBehaviorRegistry.putObject(TGItems.sharedItem, new BehaviorProjectileDispenseDamagevalue());
               
             BlockDispenser.dispenseBehaviorRegistry.putObject(TGuns.stielGranate, new BehaviorProjectileDispense()
             {
                 protected IProjectile getProjectileEntity(World world, IPosition pos)
                 {
                	 GenericGrenade g = ((GenericGrenade)TGuns.stielGranate);
                    GrenadeProjectile grenade = new GrenadeProjectile(world, pos.getX(), pos.getY(), pos.getZ(), g.damage,g.radius,1.0f,200,0.05f,0.015f, false, 0.0f, g.maxBounces, pos.getX(), pos.getY(), pos.getZ());
                     return grenade;
                 }
             });
             
             BlockDispenser.dispenseBehaviorRegistry.putObject(TGuns.fragGrenade, new BehaviorProjectileDispense()
             {
                 protected IProjectile getProjectileEntity(World world, IPosition pos)
                 {
                	 GenericGrenade g = ((GenericGrenade)TGuns.fragGrenade);
                     GrenadeProjectile grenade = new FragGrenadeProjectile(world, pos.getX(), pos.getY(), pos.getZ(), g.damage,g.radius,1.0f,120,0.05f,0.015f,false,0.0f, g.maxBounces, pos.getX(), pos.getY(), pos.getZ());
                     return grenade;
                 }
             });
             
        }
       
        public boolean dontAddCopperTinBronze(){
        	return !TGConfig.addBronzeCrafting && !TGConfig.addCopperTinBronzeNuggets && !TGConfig.doOreGenTinCopper;
        }
        
        @EventHandler // used in 1.6.2
        //@Init       // used in 1.5.2
        public void load(FMLInitializationEvent event) {
                proxy.registerRenderers();
                
                MinecraftForge.EVENT_BUS.register(new TechgunsEventhandler());
                Techguns.proxy.RegisterClientHandlers();
                FMLCommonHandler.instance().bus().register(new TechgunsTickHandler());
                NetworkRegistry.INSTANCE.registerGuiHandler(instance, new TechgunsGuiHandler());
        }
       
        @EventHandler // used in 1.6.2
        //@PostInit   // used in 1.5.2
        public void postInit(FMLPostInitializationEvent event) {
                // Stub Method
        	TGFluids.postInit();
        	TGBlocks.postInit();
        	TGItems.postInit();
        	
            TGItems.registerRecipes();
            TGMachineRecipes.addRecipes();
            
            TGuns.postInit();
            
            TGEntities.postInit();
            
            if (TGConfig.addSteelNuggets && !TGConfig.addSteel){
            	 ArrayList<ItemStack> steels = OreDictionary.getOres("ingotSteel");
            	 if (steels.size()>0){
	        		 GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nuggetSteel,9), "ingotSteel"));
		             GameRegistry.addRecipe(new ShapedOreRecipe(steels.get(0), "nnn","nnn","nnn", 'n', "nuggetSteel"));
            	 }
            }
            
            if(Loader.isModLoaded("TConstruct")){
        		isTConstructLoaded=true;
            	try {
            		if(event.getSide()==Side.CLIENT){
            			pluginTConstructIntegration=(ITechgunsTConstructIntegration) Class.forName("techguns.plugins.tconstruct.TechgunsTConstructIntegrationClient").newInstance();
            		} else {
            			pluginTConstructIntegration=(ITechgunsTConstructIntegration) Class.forName("techguns.plugins.tconstruct.TechgunsTConstructIntegration").newInstance();
            		}
					pluginTConstructIntegration.init();
            	} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
            }
            
            if(Loader.isModLoaded("ThermalExpansion")){
            	//only sends recipes
        		new TechgunsThermalExpansionIntegration().init();
            }
            
            if(Loader.isModLoaded("FTBU")){
            	TGNpcFactions.friendsAPI = new FTBUtilitiesIntegrationActual();
            } else {
            	TGNpcFactions.friendsAPI = new FTBUtilitiesIntegrationDummy();
            }
            
            if (Loader.isModLoaded("MineTweaker3")){
            	new TechgunsMineTweakerIntegration().init();
            }
            
            if(TGConfig.doWorldspawn){
            	GameRegistry.registerWorldGenerator(new WorldGenTGStructureSpawn(), 4);
            }
            
            if (TGConfig.spawnOilHoles && TGFluids.OIL!=TGFluids.LAVA){
            	GameRegistry.registerWorldGenerator(new WorldGenOilHoles(), 3);
            }
            
          /*  MapGenStructureIO.func_143031_a(VillageBunker.class, "ViTGBunker");
            VillageHandlerTGBunker villageHandlerBunker = new VillageHandlerTGBunker();
            VillagerRegistry.instance().registerVillageCreationHandler(villageHandlerBunker);*/
            Techguns.proxy.postInit();
        }
          
        
        public static int addAmmoToPlayerInventory(EntityPlayer ply, ItemStack ammo){
        	TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(ply);
        	
        	if (props!=null){
        		int amount = addItemToInventory(props.TG_inventory.inventory, ammo, props.TG_inventory.SLOTS_AMMO_START, props.TG_inventory.SLOTS_AMMO_END+1);
        		if (amount>0){
        			return addItemToInventory(ply.inventory.mainInventory, ammo, 0, ply.inventory.mainInventory.length);
        		} else {
        			return 0;
        		}
        	} else {
        		return addItemToInventory(ply.inventory.mainInventory, ammo, 0, ply.inventory.mainInventory.length);
        	}
        }
        /**
         * Try merge itemstack with inventory, return amount of stacksize that could NOT be merged.
         * @param inv
         * @param item2
         * @param startIndex
         * @param endIndex
         * @return
         */
        public static int addItemToInventory(ItemStack[] inv, ItemStack item2, int startIndex, int endIndex){
        	ItemStack item = item2.copy();//TGItems.newStack(item2, item2.stackSize);
        	
        	for(int i=startIndex;i<endIndex;i++){
        		//1st try a merge
        		if (OreDictionary.itemMatches(inv[i], item, true) && (inv[i].stackSize<item.getMaxStackSize())){
        			int diff = (inv[i].stackSize + item.stackSize) -item.getMaxStackSize();
        			if (diff < 0){
        				inv[i].stackSize+=item.stackSize;
        				item.stackSize=0;
        				return 0;
        			} else {
        				inv[i].stackSize=item.getMaxStackSize();
        				item.stackSize=diff;
        			}
        		}

        	}
        	
        	/**
        	 * could not fully merge, try fill empty slot
        	 */
        	if(item.stackSize>0){
        		for(int i=startIndex;i<endIndex;i++){
            		if(inv[i]==null){
            			inv[i]=item.copy();//TGItems.newStack(item, item.stackSize);
            			item.stackSize=0;
            			return 0;
            		}

            	}
        	}
        	
        	//Return number of items that could not be put into inventory
        	return item.stackSize;
        }
        
        public static boolean consumeAmmoPlayer(EntityPlayer ply, ItemStack ammo){
        	TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(ply);
        	
        	if ( props!=null ){
	        	int amount = ammo.stackSize;
	        	if (amount ==1){        	
		        	if (consumeAmmo(props.TG_inventory.inventory, ammo,TGPlayerInventory.SLOTS_AMMO_START, TGPlayerInventory.SLOTS_AMMO_END+1)){
		        		return true;
		        	} else {
		        		return consumeAmmo(ply.inventory.mainInventory,ammo,0,ply.inventory.mainInventory.length);
		        	}
	        	} else {
	        		
	        		//Check first if amount can be consumed
	        		int needed = canConsumeItem(props.TG_inventory.inventory, ammo,TGPlayerInventory.SLOTS_AMMO_START, TGPlayerInventory.SLOTS_AMMO_END+1);
	        		//System.out.println("needed1:"+needed);
	        		int needed2 = canConsumeItem(ply.inventory.mainInventory,ammo,0,ply.inventory.mainInventory.length);
	        		//System.out.println("needed2:"+needed);
	        		if ( needed+needed2 <= amount){
	        			
	        			int missing = consumeItem(props.TG_inventory.inventory, ammo,TGPlayerInventory.SLOTS_AMMO_START, TGPlayerInventory.SLOTS_AMMO_END+1);
	        			
	        			if (missing >0){
	        				return consumeItem(ply.inventory.mainInventory,TGItems.newStack(ammo, missing),0,ply.inventory.mainInventory.length) <= 0;
	        			} else {
	        				return true;
	        			}
	        			
	        			
	        		} else {
	        			return false;
	        		}
	
	        	}
        	} else {
        		return false;
        	}
        	
        }
        
        public static boolean consumeAmmo(ItemStack[] inv,ItemStack ammo,int startIndex, int endIndex){
        	//ply.consumeInventoryItem(ammo.getItem());
        	
        	int i = searchItem(inv, ammo, startIndex, endIndex);

            if (i < 0)
            {
                return false;
            }
            else
            {
                if (--inv[i].stackSize <= 0)
                {
                    inv[i] = null;
                    //System.out.println("Set item to null");
                }

                return true;
            }
        }
        /**
         * Search for item in inventory with same damage value;
         * @param inv
         * @param stack
         * @return
         */
        private static int searchItem(ItemStack[] inv,ItemStack stack,int startIndex, int endIndex)
        {
            for (int i = startIndex; i < endIndex; ++i)
            {
                if (inv[i] != null && inv[i].getItem() == stack.getItem() && inv[i].getItemDamage()==stack.getItemDamage())
                {
                    return i;
                }
            }

            return -1;
        }
        
        public static int countMags(ItemStack[] inv, ItemStack stack ,int startIndex, int endIndex){
        	int count=0;
        	
            for (int i = startIndex; i < endIndex; ++i)
            {
                if (inv[i] != null && inv[i].getItem() == stack.getItem() && inv[i].getItemDamage()==stack.getItemDamage())
                {
                    count+=inv[i].stackSize;
                }
            }
            return count;
        }
        
        /**
         * Return missing items, that can not be consumed, 0 == ammount can be consumed
         * @param inv
         * @param item
         * @param startIndex
         * @param endIndex
         * @return
         */
        public static int canConsumeItem(ItemStack[] inv,ItemStack item,int startIndex, int endIndex){
        	//ply.consumeInventoryItem(ammo.getItem());
        	int needed = item.stackSize;
        	
        	for (int i = startIndex; i < endIndex; ++i)
            {
                if (inv[i] != null && inv[i].getItem() == item.getItem() && inv[i].getItemDamage()==item.getItemDamage())
                {
                    needed -= inv[i].stackSize;
                    if(needed<=0){
                    	return 0;
                    }
                }
            }
        	return needed;
        }
        /**
         * Consumes from multiple stacks, returns amount that could not be consumed, does not check if total ammount can be consumed,
         * use canConsumeItem() to check that before
         * @param inv
         * @param item
         * @param startIndex
         * @param endIndex
         * @return
         */
        public static int consumeItem(ItemStack[] inv,ItemStack item,int startIndex, int endIndex){
        	//ply.consumeInventoryItem(ammo.getItem());
        	int needed=item.stackSize;
        	
        	for (int i = startIndex; i < endIndex; ++i)
            {
                if (inv[i] != null && inv[i].getItem() == item.getItem() && inv[i].getItemDamage()==item.getItemDamage())
                {
                	if (inv[i].stackSize<=needed){
                        needed -= inv[i].stackSize;
                        inv[i]=null;
                	} else {
                		inv[i].stackSize-=needed;
                		//needed=0;
                		return 0;
                    }
                }
            }
        	return needed;
        }
        
        public static ItemStack consumeFood(ItemStack[] inv,int startIndex, int endIndex){
        	for (int i = startIndex; i < endIndex; ++i)
            {
                if (inv[i] != null && inv[i].getItem() instanceof ItemFood)
                {
                	ItemStack food =TGItems.newStack(inv[i], 1);
                	inv[i].stackSize--;
                	if (inv[i].stackSize<=0){
                		inv[i]=null;
                	}
                	return food;
                }
            }
        	return null;
        }
        
}