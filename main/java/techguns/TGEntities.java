package techguns;

import java.util.ArrayList;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import techguns.entities.npc.AlienBug;
import techguns.entities.npc.ArmySoldier;
import techguns.entities.npc.AttackHelicopter;
import techguns.entities.npc.Bandit;
import techguns.entities.npc.Commando;
import techguns.entities.npc.CyberDemon;
import techguns.entities.npc.DictatorDave;
import techguns.entities.npc.NPCTurret;
import techguns.entities.npc.Outcast;
import techguns.entities.npc.PsychoSteve;
import techguns.entities.npc.SkeletonSoldier;
import techguns.entities.npc.StormTrooper;
import techguns.entities.npc.SuperMutantBasic;
import techguns.entities.npc.SuperMutantElite;
import techguns.entities.npc.SuperMutantHeavy;
import techguns.entities.npc.TGDummySpawn;
import techguns.entities.npc.ZombieFarmer;
import techguns.entities.npc.ZombieMiner;
import techguns.entities.npc.ZombiePigmanSoldier;
import techguns.entities.npc.ZombieSoldier;
import techguns.entities.spawn.TGNpcSpawn;
import techguns.entities.spawn.TGSpawnManager;
import techguns.items.TGMonsterPlacer;
import techguns.util.BlockUtils.BiomeColorType;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class TGEntities {

	public static final int MAX_NPC_DIFFICULTY = 3;
	
	public static void init() {
				
		/*BiomeGenBase[] easyBiomes = new BiomeGenBase[]{BiomeGenBase.beach, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.forest,
				BiomeGenBase.forestHills, BiomeGenBase.plains, BiomeGenBase.river};*/

	/*	BiomeGenBase[] overworldLandBiomes = new BiomeGenBase[]{BiomeGenBase.beach, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills,BiomeGenBase.coldBeach,
				BiomeGenBase.coldTaiga, BiomeGenBase.coldTaigaHills, BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge,
				BiomeGenBase.extremeHillsPlus, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.iceMountains,
				BiomeGenBase.icePlains, BiomeGenBase.jungle, BiomeGenBase.jungleEdge, BiomeGenBase.jungleHills, BiomeGenBase.megaTaiga, BiomeGenBase.megaTaigaHills,
				BiomeGenBase.mesa, BiomeGenBase.mesaPlateau, BiomeGenBase.mesaPlateau_F, BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore,
				BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.roofedForest, BiomeGenBase.savanna, BiomeGenBase.stoneBeach, BiomeGenBase.swampland,
				BiomeGenBase.taiga, BiomeGenBase.taigaHills};
		
		
		BiomeGenBase[] desertBiomes = new BiomeGenBase[]{BiomeGenBase.desert, BiomeGenBase.desertHills,BiomeGenBase.mesa, BiomeGenBase.mesaPlateau, BiomeGenBase.mesaPlateau_F};
		
		BiomeGenBase[] iceBiomes = new BiomeGenBase[]{BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.iceMountains,BiomeGenBase.icePlains,BiomeGenBase.coldTaiga,
				BiomeGenBase.coldTaigaHills, BiomeGenBase.coldBeach};*/
		
		//registerModEntityWithEgg(GenericNPC.class, "Skeleshooter", 0x3F5505, 0x4E6414);
		registerModEntityWithEgg(ZombieSoldier.class, "ZombieSoldier", 0x757468, 0x38B038);
		registerModEntityWithEgg(ZombieFarmer.class, "ZombieFarmer", 0x757468, 0x38B038);
		registerModEntityWithEgg(ZombieMiner.class, "ZombieMiner", 0x757468, 0x38B038);
		registerModEntityWithEgg(ZombiePigmanSoldier.class, "ZombiePigmanSoldier", 0xFF1111, 0x770000);
		registerModEntityWithEgg(AttackHelicopter.class, "AttackHelicopter", 0x373d23, 0x8ec0d7);
		registerModEntityWithEgg(AlienBug.class, "AlienBug", 0xc6a96b, 0x71552e);
		
		registerModEntityWithEgg(CyberDemon.class, "CyberDemon", 0xFF1111, 0x777777);
		
		registerModEntityWithEgg(ArmySoldier.class, "ArmySoldier", 0x74806e, 0x191512);
		
		registerModEntityWithEgg(Bandit.class, "Bandit", 0x8f9d59, 0x2c3117);
		
		registerModEntityWithEgg(Outcast.class, "Outcast", 0x101010,0xFF0000);
		
		registerModEntityWithEgg(SkeletonSoldier.class, "SkeletonSoldier", 0x404040,0xF0F0F0);
		
		EntityRegistry.registerModEntity(NPCTurret.class, "SimpleTurret", ++Techguns.modEntityID, Techguns.instance, 120, 3, false);
		
		EntityRegistry.registerModEntity(TGDummySpawn.class, "TGDummySpawn", ++Techguns.modEntityID, Techguns.instance, 80, 3, false);
		
		
		registerModEntityWithEgg(PsychoSteve.class, "PsychoSteve", 0x757468,0xF0F0F0);
		registerModEntityWithEgg(DictatorDave.class, "DictatorDave", 0x8f9d59, 0xA0A000);
		
		registerModEntityWithEgg(Commando.class, "Commando", 0x191512, 0x74806e);
		
		registerModEntityWithEgg(StormTrooper.class, "Stormtrooper", 0xF0F0F0, 0x404040);
		
		registerModEntityWithEgg(SuperMutantBasic.class, "SuperMutantBasic", 0xc6a96b, 0x71552e);
		registerModEntityWithEgg(SuperMutantHeavy.class, "SuperMutantHeavy", 0xc6a96b, 0x71552e);
		registerModEntityWithEgg(SuperMutantElite.class, "SuperMutantElite", 0xc6a96b, 0x71552e);
		
		//registerModEntityWithEgg(AlienBugDomesticated.class, "AlienBugDomesticated", 0xc6a96b, 0x71552e);
		
		/*if(TGConfig.spawnWeightZombieSoldier>0){
			EntityRegistry.addSpawn(ZombieSoldier.class, TGConfig.spawnWeightZombieSoldier, 1, 2, EnumCreatureType.monster, overworldLandBiomes);
		}
		if(TGConfig.spawnWeightZombieFarmer>0){
			EntityRegistry.addSpawn(ZombieFarmer.class, TGConfig.spawnWeightZombieFarmer, 1, 2, EnumCreatureType.monster, overworldLandBiomes);
		}
		if(TGConfig.spawnWeightZombieMiner>0){
			EntityRegistry.addSpawn(ZombieMiner.class, TGConfig.spawnWeightZombieMiner, 1, 2, EnumCreatureType.monster, overworldLandBiomes);
		}
		if(TGConfig.spawnWeightZombiePigmanSoldier>0){
			EntityRegistry.addSpawn(ZombiePigmanSoldier.class, TGConfig.spawnWeightZombiePigmanSoldier, 1, 4, EnumCreatureType.monster,BiomeGenBase.hell);
		}
		if(TGConfig.spawnWeightCyberDemon>0){
			EntityRegistry.addSpawn(CyberDemon.class, TGConfig.spawnWeightCyberDemon, 1, 1, EnumCreatureType.monster,BiomeGenBase.hell);
		}
		if (TGConfig.spawnWeightBandit>0){
			EntityRegistry.addSpawn(Bandit.class, TGConfig.spawnWeightBandit, 2, 5, EnumCreatureType.monster,desertBiomes);
		}
		if (TGConfig.spawnWeightSkeletonSoldier>0){
			EntityRegistry.addSpawn(SkeletonSoldier.class, TGConfig.spawnWeightSkeletonSoldier, 1, 2, EnumCreatureType.monster,overworldLandBiomes);
		}*/

	}
	
	
	public static void postInit(){
		
		BiomeGenBase[] allBiomes = BiomeGenBase.getBiomeGenArray();
		ArrayList<BiomeGenBase> allOverworldBiomes = new ArrayList<BiomeGenBase>();
		
		for (int i =0; i<allBiomes.length;i++){
			BiomeGenBase b = allBiomes[i];
			
			if (b!=null && !BiomeDictionary.isBiomeOfType(b, BiomeDictionary.Type.NETHER) && !BiomeDictionary.isBiomeOfType(b, BiomeDictionary.Type.END)){
				allOverworldBiomes.add(b);
			}
		}
			
		
		EntityRegistry.addSpawn(TGDummySpawn.class, TGConfig.spawnWeightTGOverworld, 1, 2, EnumCreatureType.monster, allOverworldBiomes.toArray(new BiomeGenBase[allOverworldBiomes.size()]));
		EntityRegistry.addSpawn(TGDummySpawn.class, TGConfig.spawnWeightTGNether, 1, 2, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(BiomeDictionary.Type.NETHER));
		
		
		ArrayList<Integer> nether =new ArrayList<Integer>(1);
		nether.add(-1);
		
		TGSpawnManager.registerSpawn(new TGNpcSpawn(ZombieSoldier.class, TGConfig.spawnWeightZombieSoldier), 1);
		TGSpawnManager.registerSpawn(new TGNpcSpawn(ZombieFarmer.class, TGConfig.spawnWeightZombieFarmer), 0);
		TGSpawnManager.registerSpawn(new TGNpcSpawn(ZombieMiner.class, TGConfig.spawnWeightZombieMiner), 0);
		TGSpawnManager.registerSpawn(new TGNpcSpawn(SkeletonSoldier.class, TGConfig.spawnWeightSkeletonSoldier), 1);
		
		TGSpawnManager.registerSpawn(new TGNpcSpawn(PsychoSteve.class, TGConfig.spawnWeightPsychoSteve), 1);
		
		TGSpawnManager.registerSpawn(new TGNpcSpawn(Bandit.class, TGConfig.spawnWeightBandit), 2);
		
		
		TGSpawnManager.registerSpawn(new TGNpcSpawn(ZombiePigmanSoldier.class, TGConfig.spawnWeightZombiePigmanSoldier,nether), 0);
		TGSpawnManager.registerSpawn(new TGNpcSpawn(CyberDemon.class, TGConfig.spawnWeightCyberDemon,nether), 0);
	}
	
	
	
	
	
	
	static void registerModEntityWithEgg(Class parEntityClass, String parEntityName, 
		      int parEggColor, int parEggSpotsColor)
	{
	    EntityRegistry.registerModEntity(parEntityClass, parEntityName, ++Techguns.modEntityID, Techguns.instance, 80, 3, false);
	    registerSpawnEgg(parEntityName, parEggColor, parEggSpotsColor);
	}


	private static void registerSpawnEgg(String parEntityName, int parEggColor, int parEggSpotsColor) {
			Item itemSpawnEgg = new TGMonsterPlacer(parEntityName, parEggColor, parEggSpotsColor)
				.setUnlocalizedName("spawn_egg_"+parEntityName.toLowerCase())
				.setTextureName("techguns:spawn_egg");
			GameRegistry.registerItem(itemSpawnEgg, "spawnEgg"+parEntityName);	
	}
}
