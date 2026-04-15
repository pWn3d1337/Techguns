package techguns.worldgen.structures;

import java.util.Random;

import net.minecraft.world.World;
import techguns.blocks.TGChiselBlocks;
import techguns.util.BlockUtils.BiomeColorType;

public class Helipad extends Structure{

	public Helipad(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		if (sizeX < 7 || sizeZ < 7) {
			return;
		}
		if (sizeX > 9) {
			int offset = (sizeX-9)/2;
			posX+=offset;
			sizeX=9;
		}
		if (sizeZ > 9) {
			int offset = (sizeZ-9)/2;
			posZ+=offset;
			sizeZ=9;
		}
		
		//Outer wireframe floor
		if (sizeX==9 && sizeZ == 9) {
			for (int z = 1; z<sizeZ-1; z++) {
				world.setBlock(posX, posY, posZ+z, TGChiselBlocks.factory_wireframe.block, TGChiselBlocks.factory_wireframe.meta, 2);
				world.setBlock(posX+sizeX-1, posY, posZ+z, TGChiselBlocks.factory_wireframe.block, TGChiselBlocks.factory_wireframe.meta, 2);
			}
			posX++;
			sizeX=7;		
			for (int x = 0; x<sizeX; x++) {
				world.setBlock(posX+x, posY, posZ, TGChiselBlocks.factory_wireframe.block, TGChiselBlocks.factory_wireframe.meta, 2);
				world.setBlock(posX+x, posY, posZ+sizeZ-1, TGChiselBlocks.factory_wireframe.block, TGChiselBlocks.factory_wireframe.meta, 2);
			}
			posZ++;
			sizeZ=7;
		}else {
			sizeX=7;
			sizeZ=7;
		}
		
		//Pad
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z < sizeZ; z++) {
				if (x==0 || z==0 || x==sizeX-1 || z==sizeZ-1) {
					world.setBlock(posX+x, posY+1, posZ+z, TGChiselBlocks.factory_hazard.block, TGChiselBlocks.factory_hazard.meta, 2);
				}else {
					world.setBlock(posX+x, posY+1, posZ+z, TGChiselBlocks.concrete_dark.block, TGChiselBlocks.concrete_dark.meta, 2);
				}
			}
		}
		
		//H
		world.setBlock(posX+2, posY+1, posZ+2, TGChiselBlocks.glowblock.block, TGChiselBlocks.glowblock.meta, 2);
		world.setBlock(posX+3, posY+1, posZ+2, TGChiselBlocks.glowblock.block, TGChiselBlocks.glowblock.meta, 2);
		world.setBlock(posX+4, posY+1, posZ+2, TGChiselBlocks.glowblock.block, TGChiselBlocks.glowblock.meta, 2);
		world.setBlock(posX+3, posY+1, posZ+3, TGChiselBlocks.glowblock.block, TGChiselBlocks.glowblock.meta, 2);
		world.setBlock(posX+2, posY+1, posZ+4, TGChiselBlocks.glowblock.block, TGChiselBlocks.glowblock.meta, 2);
		world.setBlock(posX+3, posY+1, posZ+4, TGChiselBlocks.glowblock.block, TGChiselBlocks.glowblock.meta, 2);
		world.setBlock(posX+4, posY+1, posZ+4, TGChiselBlocks.glowblock.block, TGChiselBlocks.glowblock.meta, 2);
	}

}
