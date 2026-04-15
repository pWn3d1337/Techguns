package techguns.worldgen.structures;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import techguns.util.BlockUtils.BiomeColorType;

public class EmptyPlane extends Structure {

	Block block;
	
	public EmptyPlane(Block block) {
		super (1, 1, 1, -1, -1, -1);
		this.block = block;
	}
	
	public EmptyPlane(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block block) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		this.block = block;
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		for (int i = 0; i < sizeX; i++) {			
			for (int j = 0; j < sizeZ; j++) {		
				world.setBlock(posX+i, posY, posZ+j, block);
			}
		}
	}
}


