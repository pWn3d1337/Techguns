package techguns.entities.spawn;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.TempCategory;
import net.minecraftforge.common.BiomeDictionary;
import techguns.TGConfig;
import techguns.entities.npc.GenericNPC;
import techguns.util.MathUtil.Vec2;

/**
 * Handles spawning of Techguns NPCs
 *
 */
public class TGSpawnManager {
	public static ArrayList<TGNpcSpawn> NPCs_lvl0 = new ArrayList<>();
	public static ArrayList<TGNpcSpawn> NPCs_lvl1 = new ArrayList<>();
	public static ArrayList<TGNpcSpawn> NPCs_lvl2 = new ArrayList<>();
	public static ArrayList<TGNpcSpawn> NPCs_lvl3 = new ArrayList<>();
	public static ArrayList<TGNpcSpawn> NPCs_lvl4 = new ArrayList<>();
	public static ArrayList<TGNpcSpawn> NPCs_lvl5 = new ArrayList<>();
	
	public static ArrayList<ArrayList<TGNpcSpawn>> spawntable = new ArrayList<ArrayList<TGNpcSpawn>>(6);
	
	private static Random rnd = new Random();
	
	static{
		spawntable.add(NPCs_lvl0);
		spawntable.add(NPCs_lvl1);
		spawntable.add(NPCs_lvl2);
		spawntable.add(NPCs_lvl3);
		spawntable.add(NPCs_lvl4);
		spawntable.add(NPCs_lvl5);
	}
	
/*	private static BiomeGenBase[] overworldLandBiomes = new BiomeGenBase[]{BiomeGenBase.beach, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills,BiomeGenBase.coldBeach,
			BiomeGenBase.coldTaiga, BiomeGenBase.coldTaigaHills, BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge,
			BiomeGenBase.extremeHillsPlus, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.iceMountains,
			BiomeGenBase.icePlains, BiomeGenBase.jungle, BiomeGenBase.jungleEdge, BiomeGenBase.jungleHills, BiomeGenBase.megaTaiga, BiomeGenBase.megaTaigaHills,
			BiomeGenBase.mesa, BiomeGenBase.mesaPlateau, BiomeGenBase.mesaPlateau_F, BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore,
			BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.roofedForest, BiomeGenBase.savanna, BiomeGenBase.stoneBeach, BiomeGenBase.swampland,
			BiomeGenBase.taiga, BiomeGenBase.taigaHills};
	
	
	private static BiomeGenBase[] desertBiomes = new BiomeGenBase[]{BiomeGenBase.desert, BiomeGenBase.desertHills,BiomeGenBase.mesa, BiomeGenBase.mesaPlateau, BiomeGenBase.mesaPlateau_F};
	
	private static BiomeGenBase[] iceBiomes = new BiomeGenBase[]{BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.iceMountains,BiomeGenBase.icePlains,BiomeGenBase.coldTaiga,
			BiomeGenBase.coldTaigaHills, BiomeGenBase.coldBeach};*/
	
	
	
	public static void registerSpawn(TGNpcSpawn spawn, int danger){
		ArrayList<TGNpcSpawn> list = spawntable.get(danger);
		
		if(list!=null){
			list.add(spawn);
		} else {
			System.out.println("Tried to register NPC spawn for dangerlevel outside of [0-5]: "+danger);
		}
		
	}
	
	//NPCS

	
	public static void handleSpawn(World w, Entity dummyEnt){
		
		GenericNPC entNew = null;
		
		BiomeGenBase biome = w.getBiomeGenForCoords((int)Math.round(dummyEnt.posX), (int)Math.round(dummyEnt.posZ));
		
		int dist_danger = getDistanceDanger(w, dummyEnt);
		int biome_danger = getBiomeDanger(w, dummyEnt,biome);
		
		int danger = dist_danger+biome_danger;
		
		TGNpcSpawn chosenSpawn=null;
		
		int totalweight=0;
		//calculate possible weights
		for (int d=0;d<=danger;d++){
			
			ArrayList<TGNpcSpawn> list = spawntable.get(d);
			for(int i=0;i<list.size();i++){
				TGNpcSpawn spawn = list.get(i);
				
				if(spawn.dimensionMatches(w)){
					totalweight+=spawn.getWeightForBiome(biome);
				}
			}
			
		}
		
		if (totalweight>0){
		
			int roll = rnd.nextInt(totalweight);
			
			//System.out.println("Danger:"+danger+" TotalWeight:"+totalweight+" Roll:"+roll);			
			totalweight=0;

			for (int d=0;d<=danger;d++){
				ArrayList<TGNpcSpawn> list = spawntable.get(d);
				for(int i=0;i<list.size();i++){
					TGNpcSpawn spawn = list.get(i);
					if(spawn.dimensionMatches(w)){
						totalweight+=spawn.getWeightForBiome(biome);
						if (totalweight >=roll){
							chosenSpawn = spawn;
							
							//System.out.println("Spawning: "+spawn.type.toString());
							
							break;
						}
					}
				}
				if (chosenSpawn !=null){
					break;
				}
			}
			
			if (chosenSpawn!=null){
			
				try {
					entNew = chosenSpawn.type.getDeclaredConstructor(World.class).newInstance(w);
					setPositionsAndReplace(w, entNew, dummyEnt, danger);
					
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} finally {
					dummyEnt.setDead();
				}
			}
		
		} else {
			System.out.println("TotalWeight 0, nothing to spawn???");
		}
		
		
	}
	
	private static int getBiomeDanger(World w, Entity ent, BiomeGenBase biome){
		
		/*if(isInArray(biome, iceBiomes)){
			return 1;
		} else if (isInArray(biome,desertBiomes)){
			return 2;
		} else if (isInArray(biome,overworldLandBiomes)){
			return 0;
		} else {*/
			
			//calculate from parameters
	//		if (biome.getTempCategory() == TempCategory.COLD){
	//			return 1;
	//		} else if (biome.getTempCategory() == TempCategory.WARM){
	//			return 2;
	//		}
			
		//}
			
			
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.NETHER)){
			return 0;
		}
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.END)){
			return 0;
		}
		
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.WASTELAND)){
			return 2;
		}
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SANDY)){
			return 2;
		}
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SNOWY)){
			return 2;
		}
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SWAMP)){
			return 2;
		}
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SPOOKY)){
			return 2;
		}
			
		
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.COLD)){
			return 1;
		}
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.DEAD)){
			return 1;
		}
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SPARSE)){
			return 1;
		}
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SAVANNA)){
			return 1;
		}
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.DRY)){
			return 1;
		}
		
		return 0;
	}
	
	private static int getDistanceDanger(World w, Entity ent){
		Vec2 spawn = new Vec2(w.getSpawnPoint().posX,w.getSpawnPoint().posZ);
		Vec2 pos = new Vec2(ent.posX, ent.posZ);
		
		double distance = spawn.getVecTo(pos).len();
		
		//System.out.println("Distance:"+distance);
		
		float factor=1;
		
		//if nether distance is halfed
		if(w.provider.dimensionId==-1){
			factor = 0.5f;
		}
		
		
		if (distance < TGConfig.distanceSpawnLevel0*factor){
			return 0;
		} else if (distance<TGConfig.distanceSpawnLevel1*factor){
			return 1;
		} else if (distance<TGConfig.distanceSpawnLevel2*factor){
			return 2;
		} else {
			return 3;
		}
		
	}
	
	public static boolean isInArray(BiomeGenBase biome, BiomeGenBase[] biomes){
		for (int i=0;i<biomes.length;i++){
			
			if (biome.equals(biomes[i])){
				return true;
			}
		}
		
		return false;
	}
	
	private static void setPositionsAndReplace(World w, GenericNPC newNpc, Entity entity, int difficulty){
		newNpc.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, 0.0f);
		//newNpc.setRotationYawHead(entity.rotationYaw);
		//newNpc.onSpawnWithEgg((IEntityLivingData)null);
		newNpc.onSpawnByManager(difficulty);
		w.spawnEntityInWorld(newNpc);
		
		entity.setDead();
	}
	
	
}
