package techguns.worldgen.structures;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.util.BlockUtils.BiomeColorType;
import techguns.util.MBlock;

public class Tent extends Structure{
	
	int type;
	MBlock roofBlock = new MBlock(TGBlocks.camoNetRoof, 0);
	MBlock wallBlock = new MBlock(TGBlocks.camoNetPane, 0);
	MBlock groundBlock = new MBlock(Blocks.gravel, 0);
	
	public Tent(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int type) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		this.type = type;
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		
		if (type == 0) {
		//1 sized border
			posX++;
			posZ++;
			sizeX-=2;
			sizeZ-=2;	
		//
		}
		
		//BiomeColorType biometype = BlockUtils.getBiomeType(world, posX, posZ);
		int camoMeta;
		switch (colorType) {
		case DESERT:
			camoMeta = 2;
			break;
		case SNOW:
			camoMeta = 1;
			break;
		case WOODLAND:
		default:
			camoMeta = 0;
			break;	
		}
		
		
		//Tent walls and roof;
		for (int i = 0; i < sizeX; i++) {			
			for (int j = 0; j < sizeZ; j++) {
				for (int k = 0; k < sizeY; k++) {
					if (k==0) {
						world.setBlock(posX+i, posY+k, posZ+j, groundBlock.block, groundBlock.meta, 2);
					}else if (k<sizeY-1) {
						if (j == 0 || j == sizeZ-1 || i == 0 || i == sizeX-1) {
							world.setBlock(posX+i, posY+k, posZ+j, wallBlock.block, camoMeta, 2);
						}
					}else {
						world.setBlock(posX+i, posY+k, posZ+j, roofBlock.block, camoMeta, 2);
					}
				}	
			}
		}
		
		if (type == 0 || type == 1) {
		//Tent door and walkway
			switch (direction) {
				case 0:// (-x)
					world.setBlock(posX, posY+1, posZ+(sizeZ/2), Blocks.air);
					world.setBlock(posX, posY+2, posZ+(sizeZ/2), Blocks.air);
					if (type==0) world.setBlock(posX-1, posY, posZ+(sizeZ/2), groundBlock.block, groundBlock.meta, 2);
					break;
				case 1://(-z)
					world.setBlock(posX+(sizeX/2), posY+1, posZ, Blocks.air);
					world.setBlock(posX+(sizeX/2), posY+2, posZ, Blocks.air);
					if (type==0) world.setBlock(posX+(sizeX/2), posY, posZ-1, groundBlock.block, groundBlock.meta, 2);
					break;
				case 2://(+x)
					world.setBlock(posX+sizeX-1, posY+1, posZ+(sizeZ/2), Blocks.air);
					world.setBlock(posX+sizeX-1, posY+2, posZ+(sizeZ/2), Blocks.air);
					if (type==0) world.setBlock(posX+sizeX, posY, posZ+(sizeZ/2), groundBlock.block, groundBlock.meta, 2);
					break;		
				case 3:// (+z)
					world.setBlock(posX+(sizeX/2), posY+1, posZ+sizeZ-1, Blocks.air);
					world.setBlock(posX+(sizeX/2), posY+2, posZ+sizeZ-1, Blocks.air);
					if (type==0) world.setBlock(posX+(sizeX/2), posY, posZ+sizeZ, groundBlock.block, groundBlock.meta, 2);
					break;
			}
		}
		
		if (type == 2) {
			
			
			for (int y = 1; y < sizeY-1; y++) {
				if (direction == 0 || direction == 2) {
					for (int z = 1; z < sizeZ-1; z++) {
						if (z % 3 != 0) {
							int x = (direction==0) ? 0 : sizeX-1;
							world.setBlockToAir(posX+x, posY+y, posZ+z);
						}
					}
				}else {
					for (int x = 1; x < sizeX-1; x++) {
						if (x % 3 != 1) {
							int z = (direction==1) ? 0 : sizeZ-1;
							world.setBlockToAir(posX+x, posY+y, posZ+z);
						}
					}
				}
			}
		}
	}

}
