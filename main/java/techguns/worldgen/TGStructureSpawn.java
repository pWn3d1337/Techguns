package techguns.worldgen;

import java.util.ArrayList;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import techguns.worldgen.structures.Structure;

public class TGStructureSpawn {
	Structure structure;
	int spawnWeight;
	/**
	 * Restricted to these biomes, null = all biomes
	 */
	ArrayList<BiomeDictionary.Type> biomeWhitelist;
	
	ArrayList<Integer> dimensionIDs;
	
	StructureSize size;

	ArrayList<StructureType> allowedTypes;	
	
	public TGStructureSpawn(Structure structure, int spawnWeight, ArrayList<Type> biomeWhitelist, ArrayList<Integer> dimensionIDs, ArrayList<StructureType> allowedTypes, StructureSize size) {
		super();
		this.structure = structure;
		this.spawnWeight = spawnWeight;
		this.biomeWhitelist = biomeWhitelist;
		this.dimensionIDs = dimensionIDs;
		this.allowedTypes = allowedTypes;
		this.size = size;
	}

	public int getWeightForBiome(BiomeGenBase biome, StructureSize size, StructureType type){
		if (this.size != size){
			return 0;
		} else if (!this.allowedTypes.contains(type)){
			return 0;
		}
		
		if (this.biomeWhitelist == null) {
			return this.spawnWeight;
		} else {
			for(int i=0; i<this.biomeWhitelist.size(); i++){
				if (BiomeDictionary.isBiomeOfType(biome, this.biomeWhitelist.get(i))){
					return this.spawnWeight;
				}
			}
			
			return 0;
		}
	}
	
	public boolean dimensionMatches(World w){
		int id = w.provider.dimensionId;
		if(this.dimensionIDs.contains(id)){
			return true;
		} 
		return false;
	}
	
}
