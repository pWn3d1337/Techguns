package techguns.worldgen.structures;

import net.minecraft.world.World;
import techguns.util.BlockUtils.BiomeColorType;
import techguns.util.MBlock;
import techguns.worldgen.GenericCity;

public class CityRoad implements IRoad{
	
	MBlock mainBlock;
	MBlock sideBlock;

	public CityRoad(MBlock mainBlock, MBlock sideBlock) {
		this.mainBlock = mainBlock;
		this.sideBlock = sideBlock;
	}
	
	
	public void setRoadBlocks(World world, int x, int y, int z, int roadWidth, int roadLength, int heightDif, boolean alignment, BiomeColorType colorType) {
		System.out.println("Set Road Blocks...");
		
		
		int a,b,c = 0;
		float h = 0;
		if (heightDif != 0) {
			h= ((float)roadLength / (float)heightDif);
		}
		
		//BlockUtils.flattenArea(world, x, z , alignment ? roadLength : roadWidth, !alignment ? roadLength : roadWidth, 1);
		
		int xl = 1; //Extra length
		
		for (a=0-xl; a<roadLength+(xl*2); a++) {
			if (h!= 0 && a == (int)((float)(c+1)*h) && c < heightDif) c++;
			for(b=0; b < roadWidth; b++) {
				MBlock block;
				if (roadWidth >=4 && (b > 0 && b < roadWidth-1)) block = mainBlock;
				else block = sideBlock;
				if (alignment) { //Road: in x dir
					for (int i = -1; i < 1; i++) {
						if (!(world.getBlock(x+a,y+c+i,z+b) == mainBlock.block && world.getBlockMetadata(x+a,y+c+i,z+b) == mainBlock.meta))
							world.setBlock(x+a,y+c+i,z+b, block.block, block.meta, 2);
					}
					for (int i = 1; i < 4; i++) {
						world.setBlockToAir(x+a,y+c+i,z+b);
					}
				}else {
					for (int i = -1; i < 1; i++) {
						if (!(world.getBlock(x+b,y+c+i,z+a) == mainBlock.block && world.getBlockMetadata(x+b,y+c+i,z+a) == mainBlock.meta))
							world.setBlock(x+b,y+c+i,z+a, block.block, block.meta, 2);
					}
					for (int i = 1; i < GenericCity.SET_AIR_HEIGHT; i++) {
						world.setBlockToAir(x+b,y+c+i,z+a);
					}
				}
			}
		}
		System.out.println("Set Road Blocks... DONE!");
	}

}
