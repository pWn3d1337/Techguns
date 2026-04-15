package techguns.worldgen.structures.parts;

import net.minecraft.world.World;
import techguns.util.BlockUtils.BiomeColorType;
import techguns.util.MBlock;

public class Staircase {

	
	
	public static void placeStaircase(World world, int posX, int posY, int posZ, int size, int direction, boolean inverted, BiomeColorType colorType, MBlock stairs, MBlock support) {
		int dx = 0, dz = 0;
		switch (direction) {
			case 0: dx=1; dz=0; break;
			case 1: dx=0; dz=1; break;
			case 2: dx=-1; dz = 0; break;
			case 3: dx=0; dz =-1; break;
		}
		if (!inverted) {
			for (int i = 0; i < size; i++) {
				int x = posX + ((i+1)*dx);
				int y = posY + i;
				int z = posZ + ((i+1)*dz);
				stairs.setBlock(world, x, y, z, ((3-direction)+1) % 4);
				for (int j = 1; j <= i; j++) {
					support.setBlock(world, x, y-j, z, 0);
				}
				world.setBlockToAir(x, y+1, z);
				world.setBlockToAir(x, y+2, z);
				world.setBlockToAir(x, y+3, z);
			}
		}else {
			for (int i = 0; i < size; i++) {
				int x = posX + ((i)*dx);
				int y = posY + (size-1)-i;
				int z = posZ + ((i)*dz);
				stairs.setBlock(world, x, y, z, ((3-((direction+2)%4))+1) % 4);
				for (int j = 1; j <= (size-1)-i; j++) {
					support.setBlock(world, x, y-j, z, 0);
				}
				world.setBlockToAir(x, y+1, z);
				world.setBlockToAir(x, y+2, z);
				world.setBlockToAir(x, y+3, z);
			}
		}
	
	}
}
