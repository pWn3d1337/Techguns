package techguns.worldgen.structures;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import techguns.util.BlockUtils;
import techguns.util.BlockUtils.BiomeColorType;
import techguns.worldgen.AlienBugNest;

public class AlienBugNestStructure extends Structure {

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		//NOT NEEDED, DUMMY
	}

	@Override
	public void spawnStructureWorldgen(World world, int chunkX, int chunkZ, int sizeX, int sizeY, int sizeZ, Random rnd, BiomeGenBase biome) {

		int x = chunkX*16;
		int z = chunkZ*16;
		
		int y = BlockUtils.getValidSpawnY(world, x, z, sizeX, sizeZ,5);
				
		new AlienBugNest(x,y-7,z,16+rnd.nextInt(16), sizeY, 16+rnd.nextInt(16), rnd).setBlocks(world);
		
	}

	
	
}
