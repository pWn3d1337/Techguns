package techguns.worldgen.structures;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.util.BlockUtils;
import techguns.util.BlockUtils.BiomeColorType;

public class WatchTowerSmall extends Structure{
	
	int towerSize = 3;

	public WatchTowerSmall(int minX, int minY, int minZ, int maxX, int maxY,
			int maxZ, int towerSize) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		this.towerSize = towerSize;
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		// TODO Auto-generated method stub
		
		//Center
		if (sizeX >= towerSize+2) {
			posX+= (sizeX-towerSize)/2;
		}
		if (sizeZ >= towerSize+2) {
			posZ+= (sizeZ-towerSize)/2;
		}
		
		//Posts
		for (int y = 1; y < sizeY-1; y++) {
			world.setBlock(posX, posY+y, posZ, Blocks.fence);
			world.setBlock(posX+towerSize-1, posY+y, posZ, Blocks.fence);
			world.setBlock(posX, posY+y, posZ+towerSize-1, Blocks.fence);
			world.setBlock(posX+towerSize-1, posY+y, posZ+towerSize-1, Blocks.fence);
			//center
			if (y < sizeY-3) {
				for (int x = 1; x < towerSize-1; x++ ) {
					for (int z = 1; z < towerSize-1; z++) {
						world.setBlock(posX+x, posY+y, posZ+z, Blocks.planks);
					}
				}
			}
			
		}
		//top
		BlockUtils.fillBlocksHollow(world, posX, posY+sizeY-4, posZ, towerSize, 2, towerSize, Blocks.fence);
		BlockUtils.fillBlocks(world, posX, posY+sizeY-1, posZ, towerSize, 1, towerSize, TGBlocks.camoNetRoof);
		
		//Ladder
		
		int xoffset = 0; int zoffset = 0;
		int meta = 0;
		switch (direction) {
			case 0:
				xoffset = 0; zoffset=1;
				meta = 4;
				break;
			case 1:
				xoffset = 1; zoffset=0;
				meta = 2;
				break;
			case 2:
				xoffset = towerSize-1; zoffset=towerSize-2;
				meta = 5;
				break;
			case 3:
				xoffset = towerSize-2; zoffset=towerSize-1;
				meta = 3;
				break;
		}
		
		//BlockUtils.fillBlocks(world, posX+xoffset, posY+1, posZ+zoffset, 1, sizeY-3, 1, Blocks.ladder);
		for (int y = 1; y < sizeY-3; y++) {
			world.setBlock(posX+xoffset, posY+y, posZ+zoffset, Blocks.ladder, meta, 3);
		}
		world.setBlock(posX+xoffset, posY+sizeY-3, posZ+zoffset, Blocks.fence_gate, (direction+1) % 4, 3);
		
		//TODO: Walkway
		//BlockUtils.fillBlocks(world, posX+xoffset, posY, posZ+zoffset, 1, 1, 1, Blocks.gravel);
	}

}
