package techguns.worldgen.structures;

import java.util.Random;

import net.minecraft.world.World;
import techguns.util.BlockUtils.BiomeColorType;
import techguns.util.MBlock;

public class BlockStructure extends Structure{
	
	MBlock block;

	public BlockStructure(MBlock block, int minX, int minY, int minZ, int maxX, int maxY,
			int maxZ) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		this.block = block;
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		for (int i = posX; i < posX+sizeX; i++) {
			for (int j = posY; j < posY+sizeY; j++) {
				for (int k = posZ; k < posZ+sizeZ; k++) {
					block.setBlock(world, i, j, k, 0);
				}
			}
		}
	}

}
