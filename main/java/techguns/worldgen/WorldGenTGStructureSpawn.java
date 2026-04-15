package techguns.worldgen;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import techguns.TGConfig;
import techguns.worldgen.structures.Structure;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenTGStructureSpawn implements IWorldGenerator {
	//Structure building = new FactoryHouseSmall(9, 6, 10, 16, 6, 16);
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		
		/*switch (world.provider.dimensionId)
		{
			case -1: generateNether(world, random, chunkX, chunkZ, biome);
			case 0: generateSurface(world, random, chunkX, chunkZ, biome);
			case 1: generateEnd(world, random, chunkX, chunkZ, biome);
		}*/
		if (world.provider.dimensionId==0){
			BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(chunkX*16, chunkZ*16);
			generateSurface(world, random, chunkX, chunkZ, biome);
		}
	}

	private void generateEnd(World world, Random random, int ChunkX, int ChunkZ, BiomeGenBase biome) {
	}

	private void generateNether(World world, Random random, int ChunkX, int ChunkZ, BiomeGenBase biome) {

	}


	private void generateSurface(World world, Random random, int cx, int cz, BiomeGenBase biome) {
				
		int SPAWNWEIGHT_SMALL = TGConfig.spawnWeightTGStructureSmall;
		int SPAWNWEIGHT_BIG = TGConfig.spawnWeightTGStructureBig;
		int SPAWNWEIGHT_MEDIUM = TGConfig.spawnWeightTGStructureMedium;
		
		StructureSize size = null;
		int sizeX = 0;
		int sizeZ = 0;
		
		if((cx % SPAWNWEIGHT_BIG == 0) && (cz % SPAWNWEIGHT_BIG ==0)){
			size=StructureSize.BIG;
			//sizeX = 32+random.nextInt(48);
		    //sizeZ = 32+random.nextInt(48);
		} else if ((cx % SPAWNWEIGHT_MEDIUM == 0) && (cz % SPAWNWEIGHT_MEDIUM ==0)){
			
			size = StructureSize.MEDIUM;
		} else if ((cx % SPAWNWEIGHT_SMALL == 0) && (cz % SPAWNWEIGHT_SMALL ==0)){
			size=StructureSize.SMALL;
			
			//sizeX=16;
			//sizeZ=16;
			
		}
		
		
		if(size!=null){
		
			StructureType type = StructureType.LAND;
			
			if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.OCEAN)){
				type = StructureType.WATER;
				//System.out.println("Get WATER TYPE STRUCTURE!!!");
			}
				
			Structure s = TGStructureSpawnRegister.choseStructure(random, biome, size, type);

			if (s!=null){
				
				sizeX=s.getSizeX(random);
				sizeZ=s.getSizeZ(random);
				
				
				/*int y=-1;
				if (type==StructureType.LAND){
					 y =BlockUtils.getValidSpawnY(world, cx*16, cz*16, sizeX, sizeZ,4);
				} else {
					y =BlockUtils.getValidSpawnYWater(world, cx*16, cz*16, sizeX, sizeZ,2);
				}*/
				
				/*for(int i=0; i<20; i++){
		 			world.setBlock(cx*16, 5+i, cz*16, Blocks.emerald_block);
		 		}*/
				
				
					//BlockUtils.removeJunkInArea(world, cx*16-1, cz*16-1, sizeX+2, sizeZ+2);
				s.spawnStructureWorldgen(world, cx, cz, sizeX, 8, sizeZ, random, biome);
					//s.setBlocks(world, cx*16, y-1, cz*16, sizeX, 8, sizeZ, random.nextInt(4), BiomeColorType.WOODLAND);
				
				
				
			}
			
		}
		
	}

	
	/*private void generateSurfaceOLD(World world, Random random, int x, int z, int sizeX, int sizeZ, boolean rndcheck) {
		int SPAWNWEIGHT_SMALL = TGConfig.spawnWeightTGStructureSmall;
		int SPAWNWEIGHT_BIG = TGConfig.spawnWeightTGStructureBig;
		
		int cx = x/16;
		int cz = z/16;
		
		if((cx % SPAWNWEIGHT_BIG == 0) && (cz % SPAWNWEIGHT_BIG ==0)){
			
			if(rndcheck || random.nextFloat()<0.5){
				//int sizeX = building.minX;
				//int sizeZ = building.minZ;
				//int sizeY = building.minY;
				if(sizeX<=0){
					sizeX = 32+random.nextInt(48);
				} 
				if (sizeZ <=0){
				   sizeZ = 32+random.nextInt(48);
				}
				
				
				
				
				int y = BlockUtils.getValidSpawnY(world, x, z, sizeX, sizeZ,5);
				if(  y>0){
				
					BlockUtils.removeJunkInArea(world, x-1, z-1, sizeX+2, sizeZ+2);
					//BlockUtils.flattenArea(world, x-1, z-1, sizeX+2, sizeZ+2, 0);
					//BlockUtils.apply2DHeightmapFilter(world, x-1, z-1, sizeX+2, sizeZ+2, BlockUtils.FILTER_GAUSSIAN_5x5);
					
					MilitaryCamp camp = new MilitaryCamp(4,random);
				    camp.init(x, y, z, sizeX, sizeZ);
				    camp.setBlocks(world);
					
					
					//building.setBlocks(world, x,BlockUtils.getHeightValueNoTrees(world, x, z)-1, z, sizeX, sizeY, sizeZ, random.nextInt(4), BiomeColorType.WOODLAND);
				
				} else {
					if(sizeX>=32 && sizeZ>=32){
						generateSurface(world, random, x,z, sizeX-16, sizeZ-16, true);
						
					}
					
					
				}
			}		
			
		} else if ((cx % SPAWNWEIGHT_SMALL == 0) && (cz % SPAWNWEIGHT_SMALL ==0)){
					
			if(rndcheck || random.nextFloat()<0.5){
				//int sizeX = building.minX;
				//int sizeZ = building.minZ;
				//int sizeY = building.minY;
				if(sizeX<=0){
					sizeX = 12; //32+random.nextInt(48);
				} 
				if (sizeZ <=0){
				   sizeZ = 12; //32+random.nextInt(48);
				}
				
				
				
				
				int y = BlockUtils.getValidSpawnY(world, x, z, sizeX, sizeZ,4);
				if(  y>0){
				
					BlockUtils.removeJunkInArea(world, x-1, z-1, sizeX+2, sizeZ+2);
					//BlockUtils.flattenArea(world, x-1, z-1, sizeX+2, sizeZ+2, 0);
					//BlockUtils.apply2DHeightmapFilter(world, x-1, z-1, sizeX+2, sizeZ+2, BlockUtils.FILTER_GAUSSIAN_5x5);
								
					//Random r = new Random();
					boolean spawned=false;
					
					BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(x, z);
					if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SANDY)){
						if (random.nextDouble()>0.5f){
							AlienBugNest s = new AlienBugNest(x, y, z, 16+random.nextInt(16), 0, 16+random.nextInt(16), random);
							s.setBlocks(world);
							spawned=true;
						}
					}
					
					
					if (!spawned){
						int type = random.nextInt(6);
						switch (type){
							case 0:
							case 1:
								Structure s = new FactoryHouseSmall(8,0,7,9,5,10);
								s.setBlocks(world, x, y-1, z, sizeX, 8, sizeZ, random.nextInt(4), BiomeColorType.WOODLAND);	
								break;
							case 2:
								Structure h = new HouseMedium(16, 12, 16, 16, 12, 16);
								h.setBlocks(world, x, y-1, z, 16, 12, 16, random.nextInt(4), BiomeColorType.WOODLAND);
								break;
							case 3:
							case 4:
								Structure t = new SmallTrainstation(0, 0, 0, 0, 0, 0);
								t.setBlocks(world, x, y-1, z, sizeX, 7, sizeZ, random.nextInt(4), BiomeColorType.WOODLAND);
								break;
							case 5:
							default:
								break;
						}
						//building.setBlocks(world, x,BlockUtils.getHeightValueNoTrees(world, x, z)-1, z, sizeX, sizeY, sizeZ, random.nextInt(4), BiomeColorType.WOODLAND);
					}
				} else {
				
					
				}
			}		

		}
	}*/

}
