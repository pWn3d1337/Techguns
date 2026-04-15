package techguns.worldgen.structures;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import techguns.util.BlockUtils;
import techguns.util.BlockUtils.BiomeColorType;

public class Containers extends Structure{

	int containerWidth = 2;
	int containerHeight = 2;
	int containerMinLength = 2;
	Block containerBlock = Blocks.iron_block;
	
	public Containers(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int containerWidth, int containerHeight, int containerMinLength, Block containerBlock) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		this.containerWidth = containerWidth;
		this.containerHeight = containerHeight;
		this.containerMinLength = containerMinLength;
		this.containerBlock = containerBlock;
	}
	
	public Containers(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block containerBlock) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		this.containerBlock = containerBlock;
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		int count; //Number of containers
		int offset = 0;
		if (sizeX > sizeZ) {
			count = (int)(((float)sizeZ+1.0f)/((float)containerWidth+1.0f));		
			for (int i = 0;  i < count; i++) {
				BlockUtils.fillBlocks(world, posX, posY+1, posZ+offset, sizeX, containerHeight, containerWidth, containerBlock);
				offset+=(containerWidth+1);
			}
			
		}else {
			count = (int)(((float)sizeX+1.0f)/((float)containerWidth+1.0f));			
			for (int i = 0;  i < count; i++) {
				BlockUtils.fillBlocks(world, posX+offset, posY+1, posZ, containerWidth, containerHeight, sizeZ, containerBlock);
				offset+=(containerWidth+1);
			}
		}
		
		
	}

}
