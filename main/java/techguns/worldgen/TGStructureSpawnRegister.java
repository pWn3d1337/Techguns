package techguns.worldgen;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import techguns.worldgen.structures.AircraftCarrierNew;
import techguns.worldgen.structures.AlienBugNestStructure;
import techguns.worldgen.structures.BigBunker;
import techguns.worldgen.structures.CityStructure;
import techguns.worldgen.structures.FactoryHouseSmall;
import techguns.worldgen.structures.HouseMedium;
import techguns.worldgen.structures.MilitaryBaseStructure;
import techguns.worldgen.structures.SmallTrainstation;
import techguns.worldgen.structures.Structure;
import techguns.worldgen.structures.Submarine;

public class TGStructureSpawnRegister {
	protected static ArrayList<TGStructureSpawn> spawns_small = new ArrayList<TGStructureSpawn>();
	protected static ArrayList<TGStructureSpawn> spawns_big = new ArrayList<TGStructureSpawn>();
	protected static ArrayList<TGStructureSpawn> spawns_medium = new ArrayList<TGStructureSpawn>();
	
	protected static ArrayList<Integer> OVERWORLD = new ArrayList<Integer>(1);
	protected static ArrayList<StructureType> LAND = new ArrayList<StructureType>(1);
	protected static ArrayList<StructureType> WATER = new ArrayList<StructureType>(1);
	
	protected static ArrayList<BiomeDictionary.Type> DESERTS_ONLY = new ArrayList<BiomeDictionary.Type>(2);
	
	static {
		OVERWORLD.add(0);
		LAND.add(StructureType.LAND);
		WATER.add(StructureType.WATER);
		
		DESERTS_ONLY.add(BiomeDictionary.Type.SANDY);
		DESERTS_ONLY.add(BiomeDictionary.Type.WASTELAND);
		
		spawns_small.add(new TGStructureSpawn(new FactoryHouseSmall(8,0,7,9,5,10).setXZSize(11, 10),1,null,OVERWORLD,LAND,StructureSize.SMALL));
		spawns_small.add(new TGStructureSpawn(new SmallTrainstation(0, 0, 0, 0, 0, 0).setXZSize(11, 12),1,null,OVERWORLD,LAND,StructureSize.SMALL));

		spawns_medium.add(new TGStructureSpawn(new HouseMedium(16, 12, 16, 16, 12, 16).setXZSize(16, 16),1,null,OVERWORLD,LAND,StructureSize.MEDIUM));	
		spawns_medium.add(new TGStructureSpawn(new AlienBugNestStructure().setXZSize(4, 4),1,DESERTS_ONLY,OVERWORLD,LAND,StructureSize.MEDIUM));
		spawns_medium.add(new TGStructureSpawn(new BigBunker(32,14,17,32,14,17).setXZSize(32, 17), 1, null, OVERWORLD, LAND, StructureSize.MEDIUM));
				
		spawns_big.add(new TGStructureSpawn(new MilitaryBaseStructure(0, 0, 0, 0, 0, 0),1,null,OVERWORLD,LAND,StructureSize.BIG));
		
		spawns_big.add(new TGStructureSpawn(new AircraftCarrierNew(54,24,21,54,24,21).setXZSize(54, 21),1,null, OVERWORLD, WATER, StructureSize.BIG));
		spawns_big.add(new TGStructureSpawn(new Submarine().setXZSize(33, 7),1,null, OVERWORLD, WATER, StructureSize.BIG));
		
		//spawns_big.add(new TGStructureSpawn(new CityStructure(),1,null,OVERWORLD,LAND,StructureSize.BIG));
	}
	
	
	
	
	public static Structure choseStructure(Random rnd, BiomeGenBase biome, StructureSize size, StructureType type){
		int totalweight=0;		
				
		ArrayList<TGStructureSpawn> spawns;
		if (size==StructureSize.BIG){
			spawns=spawns_big;
		} else if (size==StructureSize.MEDIUM) {
			spawns=spawns_medium;
		} else {
			spawns=spawns_small;
		}
		
		
		for(int i=0;i<spawns.size();i++){	
			totalweight+=spawns.get(i).getWeightForBiome(biome,size,type);
		}
		
		if (totalweight>0){
		
			int roll = rnd.nextInt(totalweight)+1;
			//System.out.println("Totalweight:"+totalweight+" , rolled:"+roll);
			
			int weight=0;
			for(int i=0; i<spawns.size();i++){
				weight += spawns.get(i).getWeightForBiome(biome,size,type);
				if (roll<=weight) {
				//	System.out.println("Chosen:"+weight);
					return spawns.get(i).structure;
				} else {	
					
				}
				
			}
		} else {
			//System.out.println("SPAWNWEIGHT IS NULL type:"+size);
		}
		return null;
	}
	
}
