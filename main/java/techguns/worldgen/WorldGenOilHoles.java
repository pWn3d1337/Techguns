package techguns.worldgen;

import java.util.Random;

import techguns.TGBlocks;
import techguns.TGConfig;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenOilHoles implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

		if (world.provider.dimensionId==0){
			this.generateOverworld(world, random, chunkX, chunkZ);
		}
	}

	private int countBedrock(World w, int x, int y, int z){
		int count=0;
		if(w.getBlock(x,y,z)==Blocks.bedrock){
			count++;
		}
		if(w.getBlock(x-1,y,z)==Blocks.bedrock){
			count++;
		}
		if(w.getBlock(x+1,y,z)==Blocks.bedrock){
			count++;
		}
		if(w.getBlock(x,y,z-1)==Blocks.bedrock){
			count++;
		}
		if(w.getBlock(x,y,z+1)==Blocks.bedrock){
			count++;
		}
		return count;
	}
	
	private void generateOverworld(World world, Random random, int chunkX, int chunkZ) {
		int x = chunkX*16;
		int z = chunkZ*16;
		
		if (random.nextFloat()<TGConfig.oilHoleFrequency){
			//System.out.println("SPAWNING OIL HOLE!");
			x+=random.nextInt(16);
			z+=random.nextInt(16);
			int y=0;
			//boolean bedrock=true;
			
			boolean isbedrock=countBedrock(world, x, y, z)>0;
			
			while(y<=10 && isbedrock){
			
				Block b = world.getBlock(x, y, z);
				
				world.setBlock(x, y, z, TGBlocks.oreCluster2, 3, 2);
				world.setBlock(x+1, y, z, Blocks.bedrock);
				world.setBlock(x-1, y, z, Blocks.bedrock);
				world.setBlock(x, y, z+1, Blocks.bedrock);
				world.setBlock(x, y, z-1, Blocks.bedrock);
				y++;
				
				isbedrock = countBedrock(world, x, y, z) > 0; 
			}
			
		}
		
		
	}
}
