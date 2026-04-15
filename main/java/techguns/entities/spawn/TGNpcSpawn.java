package techguns.entities.spawn;

import java.util.ArrayList;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import techguns.entities.npc.GenericNPC;

public class TGNpcSpawn {

	Class<? extends GenericNPC> type;	
	int spawnWeight;
	/**
	 * Restricted to these biomes, null = all biomes
	 */
	ArrayList<BiomeGenBase> biomeWhitelist;
	
	ArrayList<Integer> dimensionIDs;
	
	public TGNpcSpawn(Class<? extends GenericNPC> type, int spawnWeight){
		this.type=type;
		this.spawnWeight=spawnWeight;
		this.biomeWhitelist = null;
		this.dimensionIDs=new ArrayList<Integer>(1);
		this.dimensionIDs.add(0);
	}
	
	public TGNpcSpawn(Class<? extends GenericNPC> type, int spawnWeight, BiomeGenBase... biomes) {
		this(type,spawnWeight);
		this.biomeWhitelist = new ArrayList<BiomeGenBase>(biomes.length);
		for (int i =0; i<biomes.length; i++){
			this.biomeWhitelist.add(biomes[i]);
		}
	}
	
	public TGNpcSpawn(Class<? extends GenericNPC> type, int spawnWeight, ArrayList<Integer> dimensions, BiomeGenBase... biomes) {
		this(type,spawnWeight,dimensions);
		this.biomeWhitelist = new ArrayList<BiomeGenBase>(biomes.length);
		for (int i =0; i<biomes.length; i++){
			this.biomeWhitelist.add(biomes[i]);
		}
	}
	
	public TGNpcSpawn(Class<? extends GenericNPC> type, int spawnWeight, ArrayList<Integer> dimensions) {
		this(type,spawnWeight);
		this.dimensionIDs=dimensions;
	}
	
	public int getWeightForBiome(BiomeGenBase biome){
		if (this.biomeWhitelist == null) {
			return this.spawnWeight;
		} else {
			if(this.biomeWhitelist.contains(biome)){
				return this.spawnWeight;
			} else {
				return 0;
			}
		}
	}
	
	public boolean dimensionMatches(World w){
		int id = w.provider.dimensionId;
		//System.out.println("DimensionCheck:"+id);
		if(this.dimensionIDs.contains(id)){
			//System.out.println("true");
			return true;
		} 
		//System.out.println("false");
		return false;
	}
}
