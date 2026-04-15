package techguns;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import techguns.blocks.Bioblob;
import techguns.blocks.BlockAirMarker;
import techguns.blocks.BlockBugSand;
import techguns.blocks.BlockBugSlime;
import techguns.blocks.BlockFluidAcid;
import techguns.blocks.BlockSandbags;
import techguns.blocks.BlockTGChest;
import techguns.blocks.BlockTGDoor;
import techguns.blocks.BlockTGLadder;
import techguns.blocks.BlockTGLamp;
import techguns.blocks.BlockTGLampFMP;
import techguns.blocks.BlockTGMonsterSpawner;
import techguns.blocks.GenericBlock;
import techguns.blocks.GenericBlockCTM;
import techguns.blocks.GenericBlockDirectional;
import techguns.blocks.GenericBlockMetadata;
import techguns.blocks.GenericBlockPane;
import techguns.blocks.GenericBlockVerticalPane;
import techguns.blocks.GenericItemBlockCamoChangeable;
import techguns.blocks.GenericItemBlockMetadata;
import techguns.blocks.ItemBlockMetaDataMachine;
import techguns.blocks.ItemBlockOreCluster;
import techguns.blocks.ItemBlockOreClusterCustom;
import techguns.blocks.ItemBlockOreDrill;
import techguns.blocks.ItemBlockTGChest;
import techguns.blocks.OreCluster;
import techguns.blocks.OreClusterCustom;
import techguns.blocks.ctm.CarvingRegistry;
import techguns.blocks.machines.BasicMachine;
import techguns.blocks.machines.MultiBlockMachineBlock;
import techguns.blocks.machines.OreDrillBlock;

import com.cricketcraft.chisel.api.carving.ICarvingRegistry;

import cpw.mods.fml.common.registry.GameRegistry;

public class TGBlocks {
	//chiselblocks
	public static ICarvingRegistry chiselRegistry;

	//Special blocks
	public static Block bioBlobSmall;
	//public static Block bioBlobMedium;
	//public static Block bioBlobLarge;
	
	//Ladders etc
	public static BlockTGLadder ladder01;
	public static BlockTGLamp lamp01;
	public static BlockTGLampFMP lamp01FMP;
	public static BlockTGDoor door01;
	
	//Other Blocks
	public static GenericBlockPane camoNetPane;
	public static Block camoNetRoof;
	public static Block container;
	public static Block screen;
	public static Block controlpanel;
	public static Block concrete;
	public static Block sandbags;
	public static Block metalpanel;
	public static Block metalpanel2;
	
	public static Block bugnest;
	public static Block bugnesteggs;
	public static Block bugnestslimy;
	
	public static Block monsterspawner;
	
	//ores
	public static GenericBlockMetadata oreTitanIron;
	
	
	//building blocks
	public static Block steelframe_glass;
	
	public static ArrayList<OreCluster> oreclusters = new ArrayList<OreCluster>();
	public static OreCluster oreCluster;
	public static OreCluster oreCluster2;
	public static OreClusterCustom oreClusterCustom;
	public static OreClusterCustom oreClusterCustom2;
	//public static OreDrill oreDrill;
	public static OreDrillBlock oreDrill;
	
	public static MultiBlockMachineBlock machineMultiBlock;
	
	//ore References
	//set in post init
	public static ItemStack oreCopper=null;
	public static ItemStack oreTin=null;
	public static ItemStack oreLead=null;
	public static ItemStack oreOsmium=null;
	public static ItemStack oreSilver=null;
	public static ItemStack oreNickel=null;
	public static ItemStack orePlatinum=null;
	public static ItemStack oreAluminium=null;
	public static ItemStack oreUranium=null;
	public static ItemStack oreCertusQuartz=null;
	
	//Machines
	public static Block basicMachine;
	
	//MultiPartTest
	public static Block smallCube;
	
	public static Block fluidBlockAcid;
	
	/**
	 * building spawn dummy block for AIR
	 */
	public static Block airMarker;
	
	public static Block tgchest;
	public static Block tgchest_weapon;

	
	public static void init() {
		 /*
		  * ********************************
		  * BLOCKS
		  * **********************************
		  */
			 
		 bioBlobSmall = new Bioblob().setBlockName("Bioblob");
		 //bioBlobMedium = new Bioblob(2).setBlockName("Bioblob2");
		 //bioBlobLarge = new Bioblob(3).setBlockName("Bioblob3");	 
		 String[] netTextures = new String[]{"techguns:camoNet", "techguns:camoNet_snow", "techguns:camoNet_desert"};
		 String[] poleTextures = new String[]{"planks_oak", "log_spruce", "planks_jungle"};
		 camoNetPane =  (GenericBlockPane) new GenericBlockPane(netTextures, poleTextures, Material.cloth).setHardness(1.5F).setResistance(2.0F).setBlockName("camoNetPane").setStepSound(Block.soundTypeCloth);
		 camoNetRoof = new GenericBlockVerticalPane(netTextures, Material.cloth).setBlockName("camoNetRoof").setBlockTextureName("techguns:camoNet").setStepSound(Block.soundTypeCloth);
		 screen = new GenericBlockDirectional(Material.iron, "screen", true).setBlockName("screen");
		 controlpanel = new GenericBlockDirectional(Material.iron, "controlpanel", true).setBlockName("controlpanel");
		 sandbags = new BlockSandbags(Material.cloth).setHardness(2.0f).setResistance(10.0f).setBlockName("sandbags").setBlockTextureName("techguns:sandbags");
		 bugnesteggs = new GenericBlock(Material.sand).setHardness(3.0f).setResistance(2.0f).setBlockName("bugnesteggs").setBlockTextureName("techguns:bugnesteggs").setLightLevel(5.0f);
		 bugnestslimy = new BlockBugSlime().setHardness(3.0f).setResistance(2.0f).setBlockName("slimetrail").setBlockTextureName("techguns:bugnestslimy");
		 
		 oreTitanIron = new GenericBlockMetadata(Material.rock, 2);
		 oreTitanIron.setHardness(4.0F).setResistance(7.0F).setStepSound(Block.soundTypeStone).setBlockName("oreTitanIron").setCreativeTab(Techguns.tabTechgun);
		 
		 oreTitanIron.setHarvestLevel("pickaxe", 3,0);
		 oreTitanIron.setBlockTypeForMeta(0, "techguns:oreTitanIron");
		 oreTitanIron.setLightLevelForMeta(0, 3);
		 
		 oreTitanIron.setHarvestLevel("pickaxe", 2,1);
		 oreTitanIron.setBlockTypeForMeta(1, "techguns:oreUranium");
		 oreTitanIron.setLightLevelForMeta(1, 5);
		 
		 //container = new GenericBlock(Material.iron).setBlockTextureName("techguns:container_red").setBlockName("container").setStepSound(Block.soundTypePiston);
		 basicMachine = new BasicMachine();
	 	//turretBase = new techguns.blocks.TurretBase(Material.rock).setBlockTextureName("techguns:test").setBlockName("TurretBase");

		 oreCluster = (OreCluster) new OreCluster("oreCluster",TGConfig.oreClusterMultipliers1, TGConfig.oreClusterMultipliersPower1).setBlockTextureName("iron_ore");
		 oreclusters.add(oreCluster);
		 //Add default clusters
		 oreCluster.addOre(new ItemStack(Blocks.iron_ore), 1, 0.8f, 0.6f, 0.5f,0,1,64);
		 oreCluster.addOre(new ItemStack(Blocks.gold_ore), 2, 1.0f, 1.0f, 0.6f,0,3,48);
		 oreCluster.addOre(new ItemStack(Blocks.redstone_ore), 2, 0.8f, 0.0f, 0.0f,0,2,32);
		 oreCluster.addOre(new ItemStack(Blocks.coal_ore), 1, 0.3f, 0.3f, 0.3f,1,1,64);
		 oreCluster.addOre(new ItemStack(Blocks.lapis_ore), 2, 0.5f, 0.5f, 1.0f,1,2,48);
		 oreCluster.addOre(new ItemStack(Blocks.diamond_ore), 3, 0.6f, 0.90f, 1.0f,2,4,32);
		 oreCluster.addOre(new ItemStack(Blocks.emerald_ore), 3, 0.2f, 1.0f, 0.45f,2,4,32);
		 
		 
		 
		 oreCluster2 = (OreCluster) new OreCluster("oreCluster2",TGConfig.oreClusterMultipliers2, TGConfig.oreClusterMultipliersPower2).setBlockTextureName("iron_ore");
		 oreclusters.add(oreCluster2);
		
		 
		 if (TGConfig.addOreClusterCustom){
			 oreClusterCustom = (OreClusterCustom) new OreClusterCustom("oreClusterCustom").setBlockTextureName("iron_ore");
			 oreclusters.add(oreClusterCustom);
		 }
		 if (TGConfig.addOreClusterCustom2){
			 oreClusterCustom2 = (OreClusterCustom) new OreClusterCustom("oreClusterCustom2").setBlockTextureName("iron_ore");
			 oreclusters.add(oreClusterCustom2);
		 }
		 
		 //OreCluster
		 
		 ladder01 = (BlockTGLadder) new BlockTGLadder().setHardness(10.0f).setResistance(1000.0f);
		 lamp01 = new BlockTGLamp();
		 door01 = (BlockTGDoor) new BlockTGDoor(Material.iron).setHardness(20.0f).setResistance(2000.0f);
		 lamp01FMP = new BlockTGLampFMP();
		 
		 smallCube = new GenericBlock(Material.iron).setBlockName("smallCube").setBlockTextureName("redstone_block");
		 smallCube.setBlockBounds(0.2f, 0.2f, 0.2f, 0.8f, 0.8f, 0.8f);
		 
		 steelframe_glass= new GenericBlock(Material.glass).setBlockName("steelframeGlass").setBlockTextureName("techguns:steelframe_glass").setHardness(4.0f).setResistance(20.0f);
		 
		 airMarker = new BlockAirMarker();
		 	 
	
		 FluidRegistry.registerFluid(TGFluids.ACID);
		 fluidBlockAcid = new BlockFluidAcid(TGFluids.ACID);
		 TGFluids.ACID.setBlock(fluidBlockAcid);
	
		 monsterspawner = new BlockTGMonsterSpawner(Material.rock,2).setBlockTypeForMeta(0, "techguns:bugnestblock", "techguns:bugnestblock_hole");
		 ((BlockTGMonsterSpawner)monsterspawner).setBlockTypeForMeta(1, "techguns:concrete01", "techguns:concrete_hole");
		 
		 tgchest = (new BlockTGChest(0)).setHardness(5.0F).setResistance(500.0F).setStepSound(Block.soundTypeWood).setBlockName("tgchest");
		 tgchest_weapon = (new BlockTGChest(1)).setHardness(5.0F).setResistance(500.0F).setStepSound(Block.soundTypeWood).setBlockName("tgchest_weapon");
		 /*
		  * ********************************
		  * REGISTER BLOCKS
		  * **********************************
		  */
		 
         GameRegistry.registerBlock(bioBlobSmall, "BioBlobSmall");
         //GameRegistry.registerBlock(bioBlobMedium, "BioBlobMedium");
         //GameRegistry.registerBlock(bioBlobLarge, "BioBlobLarge");
         
         GameRegistry.registerBlock(camoNetPane, GenericItemBlockCamoChangeable.class, "camoNetPane");
         GameRegistry.registerBlock(camoNetRoof, GenericItemBlockCamoChangeable.class, "camoNetRoof");
         //GameRegistry.registerBlock(container, "container");
         GameRegistry.registerBlock(screen, "screen");
         GameRegistry.registerBlock(controlpanel, "controlpanel");
         
         
         //GameRegistry.registerBlock(basicMachine, "ammoPress");
         GameRegistry.registerBlock(basicMachine, ItemBlockMetaDataMachine.class, "basicMachine");
         
         GameRegistry.registerBlock(oreCluster, GenericItemBlockMetadata.class,"oreCluster");
         GameRegistry.registerBlock(oreCluster2, GenericItemBlockMetadata.class,"oreCluster2");
         
         if (TGConfig.addOreClusterCustom){
        	 GameRegistry.registerBlock(oreClusterCustom, ItemBlockOreClusterCustom.class,"oreClusterCustom");
         }
         if (TGConfig.addOreClusterCustom2){
        	 GameRegistry.registerBlock(oreClusterCustom2, ItemBlockOreClusterCustom.class,"oreClusterCustom2");
         }
         //GameRegistry.registerBlock(oreDrill, ItemBlockOreDrill.class, "oreDrill");
         
         GameRegistry.registerBlock(ladder01, GenericItemBlockCamoChangeable.class,"ladder01");
         GameRegistry.registerBlock(lamp01, GenericItemBlockMetadata.class,  "lamp01");
         GameRegistry.registerBlock(door01, "door01");
         //GameRegistry.registerBlock(lamp01FMP, "lamp01FMP");
         GameRegistry.registerBlock(sandbags, "sandbags");
         
         GameRegistry.registerBlock(bugnesteggs, "bugnesteggs");
         GameRegistry.registerBlock(bugnestslimy, "bugnestslimy");
         
         GameRegistry.registerBlock(airMarker, "airMarker");
         //GameRegistry.registerBlock(smallCube, "smallCube");
         
         GameRegistry.registerBlock(oreTitanIron, GenericItemBlockMetadata.class, "oreTitanIron");
		 oreUranium = new ItemStack(oreTitanIron,1,1);
         
         
         GameRegistry.registerBlock(monsterspawner, GenericItemBlockMetadata.class, "TGMonsterSpawner");
         
         ItemStack oreTitanIron = new ItemStack(TGBlocks.oreTitanIron,1,0);
		 OreDictionary.registerOre("oreTitaniumIron", oreTitanIron);
		 OreDictionary.registerOre("oreIllmenite", oreTitanIron);
         OreDictionary.registerOre("oreUranium", new ItemStack(TGBlocks.oreTitanIron,1,1));
		 
         //GameRegistry.registerBlock(steelframe_glass, "steelframeGlass");
         GameRegistry.registerBlock(fluidBlockAcid, "acid");
         
         GameRegistry.registerBlock(tgchest, ItemBlockTGChest.class, "tgchest");
         GameRegistry.registerBlock(tgchest_weapon, ItemBlockTGChest.class, "tgchest_weapon");
         /*
		  * ********************************
		  * RECIPES
		  * **********************************
		  */
         
         /*
          * **************************
          * Chisel Variations 
          * **************************
          */
         chiselRegistry = new CarvingRegistry();
         //RendererCTM ctm = new RendererCTM(); //Workaround?
		 Techguns.proxy.initChisel();
         
         //-----
         //container = new GenericBlockCTM(Material.iron,"container_red",ctm.getRenderId()).setBlockName("container").setStepSound(Block.soundTypePiston);
         container = new GenericBlockCTM(Material.iron).setBlockName("container").setStepSound(Block.soundTypePiston).setHardness(1.5F).setResistance(3.0F); ; 
         ((GenericBlockCTM) container).carverHelper.addVariation("techguns.tile.container.0.desc", 0, "container_red", "techguns");
         ((GenericBlockCTM) container).carverHelper.addVariation("techguns.tile.container.1.desc", 1, "container_green", "techguns");
         ((GenericBlockCTM) container).carverHelper.addVariation("techguns.tile.container.2.desc", 2, "container_blue", "techguns");
         ((GenericBlockCTM) container).carverHelper.addVariation("techguns.tile.container.3.desc", 3, "container_orange", "techguns");
         ((GenericBlockCTM) container).carverHelper.addVariation("techguns.tile.container.4.desc", 4, "container_scifi1", "techguns");
         ((GenericBlockCTM) container).carverHelper.addVariation("techguns.tile.container.5.desc", 5, "container_scifi_rusty", "techguns");
         ((GenericBlockCTM) container).carverHelper.registerAll(container, "container");
         
         concrete = new GenericBlockCTM(Material.rock).setBlockName("concrete").setStepSound(Block.soundTypeStone).setHardness(3.0F).setResistance(10.0F); 
         ((GenericBlockCTM) concrete).carverHelper.addVariation("techguns.tile.concrete.0.desc", 0, "concrete01", "techguns");
         ((GenericBlockCTM) concrete).carverHelper.addVariation("techguns.tile.concrete.1.desc", 1, "concrete02", "techguns");
         ((GenericBlockCTM) concrete).carverHelper.addVariation("techguns.tile.concrete.2.desc", 2, "concrete_r", "techguns");
         ((GenericBlockCTM) concrete).carverHelper.addVariation("techguns.tile.concrete.3.desc", 3, "concrete_r02", "techguns");
         ((GenericBlockCTM) concrete).carverHelper.addVariation("techguns.tile.concrete.4.desc", 4, "concrete_old2", "techguns");
         ((GenericBlockCTM) concrete).carverHelper.addVariation("techguns.tile.concrete.5.desc", 5, "concrete03", "techguns");
         ((GenericBlockCTM) concrete).carverHelper.addVariation("techguns.tile.concrete.6.desc", 6, "concrete04", "techguns");
         ((GenericBlockCTM) concrete).carverHelper.registerAll(concrete, "concrete");
         
         metalpanel = new GenericBlockCTM(Material.iron).setBlockName("metalpanel").setStepSound(Block.soundTypePiston).setHardness(2.0F).setResistance(8.0F);
         ((GenericBlockCTM) metalpanel).carverHelper.addVariation("techguns.tile.metalpanel.0.desc", 0, "metalpanel_rusty", "techguns");
         ((GenericBlockCTM) metalpanel).carverHelper.addVariation("techguns.tile.metalpanel.1.desc", 1, "metalpanel_rusty2", "techguns");
         ((GenericBlockCTM) metalpanel).carverHelper.addVariation("techguns.tile.metalpanel.2.desc", 2, "metalpanel_hazard", "techguns");
         ((GenericBlockCTM) metalpanel).carverHelper.addVariation("techguns.tile.metalpanel.3.desc", 3, "metalpanel_dark", "techguns");
         ((GenericBlockCTM) metalpanel).carverHelper.addVariation("techguns.tile.metalpanel.4.desc", 4, "metalgrate_rusty", "techguns");
         ((GenericBlockCTM) metalpanel).carverHelper.addVariation("techguns.tile.metalpanel.5.desc", 5, "metalgrate_rusty2", "techguns");
         ((GenericBlockCTM) metalpanel).carverHelper.addVariation("techguns.tile.metalpanel.6.desc", 6, "metalgrate_hazard", "techguns");
         ((GenericBlockCTM) metalpanel).carverHelper.addVariation("techguns.tile.metalpanel.7.desc", 7, "metalgrate_dark", "techguns");
         ((GenericBlockCTM) metalpanel).carverHelper.registerAll(metalpanel, "metalpanel");
         
         metalpanel2 = new GenericBlockCTM(Material.iron).setBlockName("metalpanel2").setStepSound(Block.soundTypePiston).setHardness(2.0F).setResistance(8.0F);
         ((GenericBlockCTM) metalpanel2).carverHelper.addVariation("techguns.tile.metalpanel2.0.desc", 0, "metalpanel2_rusty", "techguns");
         ((GenericBlockCTM) metalpanel2).carverHelper.addVariation("techguns.tile.metalpanel2.1.desc", 1, "metalpanel2_hazard", "techguns");
         ((GenericBlockCTM) metalpanel2).carverHelper.addVariation("techguns.tile.metalpanel2.2.desc", 2, "metalpanel16_largeborder", "techguns");
         ((GenericBlockCTM) metalpanel2).carverHelper.addVariation("techguns.tile.metalpanel2.3.desc", 3, "steelframe_dark", "techguns");
         ((GenericBlockCTM) metalpanel2).carverHelper.addVariation("techguns.tile.metalpanel2.4.desc", 4, "steelframe_blue", "techguns");
         ((GenericBlockCTM) metalpanel2).carverHelper.addVariation("techguns.tile.metalpanel2.5.desc", 5, "steelframe_scaffold", "techguns");
         ((GenericBlockCTM) metalpanel2).carverHelper.addVariation("techguns.tile.metalpanel2.6.desc", 6, "metalpanel_techgrid", "techguns");

         ((GenericBlockCTM) metalpanel2).carverHelper.registerAll(metalpanel2, "metalpanel2");
         
         
         bugnest = new BlockBugSand(Material.sand).setBlockName("bugnest").setStepSound(Block.soundTypeSand).setHardness(2.0F).setResistance(2.0F);
         ((GenericBlockCTM) bugnest).carverHelper.addVariation("techguns.tile.bugnest.0.desc", 0, "bugnestblock", "techguns");
         //((GenericBlockCTM) bugnest).carverHelper.addVariation("techguns.tile.bugnestslimy.1.desc", 1, "bugnestslimy", "techguns");
      
         ((GenericBlockCTM) bugnest).carverHelper.registerAll(bugnest, "bugnest");
         
         
		 oreDrill = new OreDrillBlock();
		 oreDrill.carverHelper.addVariation("", 0, "drillFrame_outer", Techguns.MODID.toLowerCase());
		 oreDrill.carverHelper.addVariation("", 1, "drillFrame", Techguns.MODID.toLowerCase());
		 oreDrill.carverHelper.addVariation("", 2, "drillRod", Techguns.MODID.toLowerCase());
		 oreDrill.carverHelper.addVariation("", 3, "drillEngine", Techguns.MODID.toLowerCase());
		 oreDrill.carverHelper.addVariation("", 4, "drillEngineController", Techguns.MODID.toLowerCase());
		 //oreDrill.carverHelper.addVariation("", 5, "drillRodSide", Techguns.MODID.toLowerCase());
		 
		 oreDrill.carverHelper.addVariation("", 8, "drillFrame_outer_c", Techguns.MODID.toLowerCase());
		 oreDrill.carverHelper.addVariation("", 9, "drillFrame_c", Techguns.MODID.toLowerCase());
		 oreDrill.carverHelper.addVariation("", 10, "invisible", Techguns.MODID.toLowerCase());
		 oreDrill.carverHelper.addVariation("", 11, "drillEngine_c", Techguns.MODID.toLowerCase());
		 oreDrill.carverHelper.addVariation("", 12, "drillEngineTop_c", Techguns.MODID.toLowerCase());
		 
		 oreDrill.carverHelper.forbidChiseling = true;
		 oreDrill.setBlockName("oreDrill");
		 GameRegistry.registerBlock(oreDrill, ItemBlockOreDrill.class, "oreDrill");
		 
		 
		 
		 machineMultiBlock = new MultiBlockMachineBlock();
		 machineMultiBlock.carverHelper.addVariation("", 0, "rc_block",Techguns.MODID.toLowerCase());
		 machineMultiBlock.carverHelper.addVariation("", 1, "rc_controller",Techguns.MODID.toLowerCase());
		 machineMultiBlock.carverHelper.addVariation("", 2, "rc_glass",Techguns.MODID.toLowerCase());
		 machineMultiBlock.carverHelper.addVariation("", 3, "fabricatorHousing",Techguns.MODID.toLowerCase());
		 machineMultiBlock.carverHelper.addVariation("", 4, "fabricatorController",Techguns.MODID.toLowerCase());
		 machineMultiBlock.carverHelper.addVariation("", 5, "fabricatorGlass",Techguns.MODID.toLowerCase());
		 
		 //machineMultiBlock.carverHelper.addVariation("", 8, "invisible",Techguns.MODID.toLowerCase());
		 //machineMultiBlock.carverHelper.addVariation("", 9, "invisible",Techguns.MODID.toLowerCase());
		 //machineMultiBlock.carverHelper.addVariation("", 10, "invisible",Techguns.MODID.toLowerCase());
		 //machineMultiBlock.carverHelper.addVariation("", 11, "invisible",Techguns.MODID.toLowerCase());
		 machineMultiBlock.carverHelper.addVariation("", 15, "invisible",Techguns.MODID.toLowerCase());
		 
		 machineMultiBlock.carverHelper.forbidChiseling = true;
		 GameRegistry.registerBlock(machineMultiBlock, ItemBlockMetaDataMachine.class, "machineMultiBlock");
		 
		 //oreDrill.carverHelper.registerVariations("oreDrill");
		 //oreDrill.carverHelper.registerAll(oreDrill, "oreDrill", ItemBlockOreDrill.class);
		 
         
//         icons[0] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":" + "drillFrameCorner");
// 		icons[1] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":" + "drillFrame");
// 		icons[2] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":" + "drillRodTop");
// 		icons[3] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":" + "drillEngine");
// 		icons[4] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":" + "drillEngineController");
// 		icons[5] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":" + "drillRodSide");
// 		icons[6] = null;
// 		icons[7] = null;
// 		icons[8] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":" + "drillFrameCorner");
// 		icons[9] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":" + "drillFrame_c");
// 		icons[10] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":" + "invisible");
// 		icons[11] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":" + "drillEngine");
// 		icons[12] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":" + "drillEngineController");
         
         //ICarvingGroup cg_container = new CarvingGroup("container");
         //cg_container.addVariation(new CarvingVariation(TGBlocks.container, 0));
         //chiselRegistry.addGroup(cg_container);
         //----
         
         
	}
	
	
	private static ItemStack initOre(ItemStack ore, String name){
		if(ore==null){
			ArrayList<ItemStack> ores = OreDictionary.getOres(name);
			if(ores.size()>0){
				if(ores.get(0)!=null){
					return TGItems.newStack(ores.get(0), 1);
				}
			}
		}
		return ore;
	}
	
	public static void postInit(){
		TGBlocks.door01.setDoorItem(TGItems.door01);
		
		TGBlocks.oreCopper=initOre(TGBlocks.oreCopper, "oreCopper");
		TGBlocks.oreTin=initOre(TGBlocks.oreTin, "oreTin");
		TGBlocks.oreLead=initOre(TGBlocks.oreLead, "oreLead");
		TGBlocks.oreOsmium=initOre(TGBlocks.oreOsmium, "oreOsmium");
		TGBlocks.oreNickel=initOre(TGBlocks.oreNickel, "oreNickel");
		TGBlocks.oreSilver=initOre(TGBlocks.oreSilver, "oreSilver");
		TGBlocks.oreAluminium=initOre(TGBlocks.oreAluminium, "oreAluminium");
		TGBlocks.orePlatinum=initOre(TGBlocks.orePlatinum, "orePlatinum");
		TGBlocks.oreUranium=initOre(TGBlocks.oreUranium, "oreUranium");
		TGBlocks.oreCertusQuartz=initOre(TGBlocks.oreCertusQuartz, "oreCertusQuartz");
		
		ItemStack stone =new ItemStack(Blocks.stone);
		
		if(TGBlocks.oreCopper!=null){
			oreCluster.addOre(oreCopper, 1, 0.65f, 0.5f, 0.05f, 0,1,48);
		} else {
			oreCluster.addOre(stone, 0, 0,0,0,0,0,0);
		}
		if(TGBlocks.oreTin!=null){
			oreCluster.addOre(oreTin, 1, 0.65f, 0.95f, 1.0f, 0,1,48);
		}else {
			oreCluster.addOre(stone, 0, 0,0,0,0,0,0);
		}
		if(TGBlocks.oreLead!=null){
			oreCluster.addOre(oreLead, 2, 0.55f, 0.65f, 1.0f, 0,2,48);
		}else {
			oreCluster.addOre(stone, 0, 0,0,0,0,0,0);
		}
		if(TGBlocks.oreOsmium!=null){
			oreCluster.addOre(oreOsmium, 2, 0.75f, 0.75f, 1.0f, 1,2,48);
		}else {
			oreCluster.addOre(stone, 0, 0,0,0,0,0,0);
		}
		if(TGBlocks.oreSilver!=null){
			oreCluster.addOre(oreSilver, 2, 0.90f, 0.90f, 1.0f, 0,3,32);
		}else {
			oreCluster.addOre(stone, 0, 0,0,0,0,0,0);
		}
		if(TGBlocks.oreNickel!=null){
			oreCluster.addOre(oreNickel, 2, 1.0f, 0.8f, 0.6f, 0,2,32);
		}else {
			oreCluster.addOre(stone, 0, 0,0,0,0,0,0);
		}
		if(TGBlocks.oreAluminium!=null){
			oreCluster.addOre(oreAluminium, 2, 0.95f, 0.95f, 0.95f, 1,2,32);
		}else {
			oreCluster.addOre(stone, 0, 0,0,0,0,0,0);
		}
		if(TGBlocks.orePlatinum!=null){
			oreCluster.addOre(orePlatinum, 2, 0.9f, 0.9f, 1.0f, 1,4,32);
		}else {
			oreCluster.addOre(stone, 0, 0,0,0,0,0,0);
		}
		if(TGBlocks.oreUranium!=null){
			oreCluster.addOre(oreUranium, 3, 0.5f, 1.0f, 0.5f, 0,4,32);
		}else {
			oreCluster.addOre(stone, 0, 0,0,0,0,0,0);
		}
		
		//////////////////////////////////////////
		
		 oreCluster2.addOre(new ItemStack(Blocks.quartz_ore), 2, 1.0f, 1.0f, 1.0f,0,1,64);
		 oreCluster2.addOre(new ItemStack(TGBlocks.oreTitanIron,1,0) , 3, 0.85f, 0.85f, 0.95f, 1, 4, 32);
		
		if(TGBlocks.oreCertusQuartz!=null){
			oreCluster2.addOre(oreCertusQuartz, 3, 0.75f, 0.95f, 1.0f, 2,3,32);
		}else {
			oreCluster2.addOre(stone, 0, 0,0,0,0,0,0);
		}
		
		if(TGFluids.OIL!=null){
			//System.out.println("OIL IS NOT NULL");
			oreCluster2.addLiquid(new FluidStack(TGFluids.OIL,FluidContainerRegistry.BUCKET_VOLUME), 3, 0.1f, 0.1f, 0.1f, 3,3,5,true);
		} else {
			//System.out.println("OIL IS NULL");
			oreCluster2.addOre(stone, 0, 0,0,0,0,0,0);
		}
		
		oreCluster2.addOre(new ItemStack(Blocks.glowstone), 2, 0.9f, 0.9f, 0.2f, 2, 2, 96);
		
	}
	public static boolean isOrecluster(Block b){
		for (int i=0; i<oreclusters.size();i++){
			if (oreclusters.get(i)==b){
				return true;
			}
		}
		return false;
	}
	
}
