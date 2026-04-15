package techguns.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import techguns.TGBlocks;
import techguns.blocks.OreCluster;
import cpw.mods.fml.common.IWorldGenerator;



/**
 * From Wuppy's tutorial
 * http://www.wuppy29.com/minecraft/modding-tutorials/wuppys-minecraft-forge-modding-tutorials-for-1-7-updating-1-6-to-1-7-part-3-world-generation/
 *
 */
public class OreClusterWorldspawn implements IWorldGenerator {

	public OreClusterWorldspawn() {
		super();
	}

	private int getMaxVeinsize(int rarity, Random rnd){
		int num = 10 - rarity*2;
		return num+rnd.nextInt(num);
	}
	

	/* (non-Javadoc)
	 * @see cpw.mods.fml.common.IWorldGenerator#generate(java.util.Random, int, int, net.minecraft.world.World, net.minecraft.world.chunk.IChunkProvider, net.minecraft.world.chunk.IChunkProvider)
	 */
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		
		//If OVERWORLD!
		if (world.provider.dimensionId == 0) {

			/*	this.addOreSpawn(Techguns.oreCopper,Blocks.stone, world, random, chunkX*16, chunkZ*16, 16, 16, 5+random.nextInt(3), 12, 5, 80);
				this.addOreSpawn(Techguns.oreTin,Blocks.stone, world, random, chunkX*16, chunkZ*16, 16, 16, 4+random.nextInt(3), 10, 5, 60);

				this.addOreSpawn(Techguns.oreLead,Blocks.stone, world, random, chunkX*16, chunkZ*16, 16, 16, 4+random.nextInt(2), 8, 5, 50);*/
			for(int i=0;i<TGBlocks.oreclusters.size();++i){
				OreCluster cluster = TGBlocks.oreclusters.get(i);
				
				for (int meta=0;meta <cluster.numOres;++meta){
					if(cluster.mininglevels[meta]>0){
					
						int rarity=cluster.rarity[meta];
						this.addOreSpawn(cluster, Blocks.stone, world, random, chunkX*16, chunkZ*16, 16, 16, getMaxVeinsize(rarity,random), rarity==1?2:1, 0, cluster.maxy[meta]);
						
					}
				}
				
				
				
				
			}
		}

	}

	
	/**
	 * Adds an Ore Spawn to Minecraft. Simply register all Ores to spawn with this method in your Generation method in your IWorldGeneration extending Class
	 * 
	 * @param The Block to spawn
	 * @param SpawnBlock, block to replace
	 * @param The World to spawn in
	 * @param A Random object for retrieving random positions within the world to spawn the Block
	 * @param An int for passing the X-Coordinate for the Generation method
	 * @param An int for passing the Z-Coordinate for the Generation method
	 * @param An int for setting the maximum X-Coordinate values for spawning on the X-Axis on a Per-Chunk basis
	 * @param An int for setting the maximum Z-Coordinate values for spawning on the Z-Axis on a Per-Chunk basis
	 * @param An int for setting the maximum size of a vein
	 * @param An int for the Number of chances available for the Block to spawn per-chunk
	 * @param An int for the minimum Y-Coordinate height at which this block may spawn
	 * @param An int for the maximum Y-Coordinate height at which this block may spawn
	 **/
	public void addOreSpawn(Block block, Block SpawnBlock, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int chancesToSpawn, int minY, int maxY)
	{
		/*assert maxY > minY : "The maximum Y must be greater than the Minimum Y";
		assert maxX > 0 && maxX <= 16 : "addOreSpawn: The Maximum X must be greater than 0 and less than 16";
		assert minY > 0 : "addOreSpawn: The Minimum Y must be greater than 0";
		assert maxY < 256 && maxY > 0 : "addOreSpawn: The Maximum Y must be less than 256 but greater than 0";
		assert maxZ > 0 && maxZ <= 16 : "addOreSpawn: The Maximum Z must be greater than 0 and less than 16";*/

		int diffBtwnMinMaxY = maxY - minY;
		for (int x = 0; x < chancesToSpawn; x++)
		{
			int posX = blockXPos + random.nextInt(maxX);
			int posY = minY + random.nextInt(diffBtwnMinMaxY);
			int posZ = blockZPos + random.nextInt(maxZ);
			new WorldGenMinable(block, maxVeinSize, SpawnBlock).generate(world, random, posX, posY, posZ);
		}
	}
}
