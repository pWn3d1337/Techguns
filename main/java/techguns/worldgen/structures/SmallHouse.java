package techguns.worldgen.structures;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDoor;
import net.minecraft.world.World;
import team.chisel.init.ChiselBlocks;
import techguns.TGBlocks;
import techguns.util.BlockUtils;
import techguns.util.BlockUtils.BiomeColorType;
import techguns.util.BlockUtils.BlockType;
import techguns.util.MBlock;

public class SmallHouse extends Structure {

	MBlock b_floor = new MBlock(TGBlocks.concrete, 5);
	MBlock b_wall = new MBlock(Blocks.brick_block,0);
	MBlock b_scaffold = new MBlock(Blocks.stonebrick,0);
	MBlock b_window = new MBlock(ChiselBlocks.glass_pane,2);
	
	MBlock b_roofSlab = new MBlock(Blocks.stone_slab,0);
	MBlock b_roof = new MBlock(Blocks.double_stone_slab,0);
	MBlock b_torch = new MBlock(TGBlocks.lamp01,0);
	
	public SmallHouse(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super(minX, 5, minZ, maxX, 5, maxZ);
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		sizeX-= ((sizeX-1) % 4);
		sizeZ-= ((sizeZ-1) % 4);
				
		
		/*  TRRRRRRRRT   - 6 - roof
		 * SSSSSSSSSSSSS - 5 - roof
		 * SWOWSWOWSWOWS - 4 - wall
		 * SWWWSWWWSWWWS - 3 - wall
		 * SSSSSSSSSSSSS - 2 - floor
		 * P   P   P   P - 1 - pillars
		 * GGGGGGGGGGGGG - 0 - ground
		 */
		
		//fixed height;
		sizeY = 7;
		
		//Outer Building
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z < sizeZ; z++) {
				for (int y = 0; y < sizeY; y++) {
			/*		if (y ==0) { //Ground
						world.setBlock(posX+x, posY+y, posZ+z, b_ground.block, b_ground.meta, 2);
					}else if (y== 1) { //Pillars
						if (x % 4 == 0 && z % 4 == 0) {
							world.setBlock(posX+x, posY+y, posZ+z, b_pillar.block, b_pillar.meta, 2);
						}
					}else */if( y==0) { //Floor
						if (x==0 || z==0 || x== sizeX-1 || z == sizeZ-1) {
							world.setBlock(posX+x, posY+y, posZ+z, b_scaffold.block, b_scaffold.meta, 2);
						}else {
							world.setBlock(posX+x, posY+y, posZ+z, b_floor.block, b_floor.meta, 2);		
						}
					}else if (y <= 3){ //Wall
						if (x==0 || z==0 || x== sizeX-1 || z == sizeZ-1) {
							if (x % 4 == 0 && z % 4 == 0) {
								world.setBlock(posX+x, posY+y, posZ+z, b_scaffold.block, b_scaffold.meta, 2);
							}else {
								if (y==2 && (x % 4 ==2 || z % 4 == 2)) { //Window
									world.setBlock(posX+x, posY+y, posZ+z, b_window.block, b_window.meta, 2);
								}else{
									world.setBlock(posX+x, posY+y, posZ+z, b_wall.block, b_wall.meta, 2);
								}
							}
						}
					}else if (y == 4) { //roof/scaffold
						if (x==0 || z==0 || x== sizeX-1 || z == sizeZ-1) {
							world.setBlock(posX+x, posY+y, posZ+z, b_scaffold.block, b_scaffold.meta, 2);
						}
					}else if (y == 5) {
						if (x==0 || z==0 || x== sizeX-1 || z == sizeZ-1) {
							world.setBlock(posX+x, posY+y, posZ+z, b_roofSlab.block, b_roofSlab.meta, 2);
						}else {
							world.setBlock(posX+x, posY+y, posZ+z, b_roof.block, b_roof.meta, 2);		
						}
					}
				}
			}
		}
		
		//---------------------
		//Entrance
		//---------------------
		
		int xoffset, zoffset; //Door/center positions
		int dx, dz;
		
		switch (direction) {
			case 0:
				xoffset = 0; zoffset=((sizeZ/8)*4)+2;
				dx = -1; dz =0;
				break;
			case 1:
				xoffset = ((sizeX/8)*4)+2; zoffset=0;
				dx = 0; dz = -1;
				break;
			case 2:
				xoffset = sizeX-1; zoffset=((sizeZ/8)*4)+2;
				dx = 1; dz=0;
				break;
			case 3:
			default:
				xoffset = ((sizeX/8)*4)+2; zoffset=sizeZ-1;
				dx = 0; dz=1;
				break;
		}
		
		//Door
		world.setBlock(posX+xoffset, posY+2, posZ+zoffset, Blocks.air);
		world.setBlock(posX+xoffset, posY+1, posZ+zoffset, Blocks.air);
		ItemDoor.placeDoorBlock(world, posX+xoffset, posY+1, posZ+zoffset, BlockUtils.getBlockDirMeta(BlockType.DOOR, direction), Blocks.wooden_door);

		//Lamp
		world.setBlock(posX+xoffset+dx, posY+3, posZ+zoffset+dz, b_torch.block, 7, 2);
		
		destroyTop(world, sizeX, sizeY, posY+5, 3, posX, posZ);
		
		
		//Ramp
		//world.setBlock(posX+xoffset+dx, posY+2, posZ+zoffset+dz, b_stairs.block, BlockUtils.getBlockDirMeta(BlockType.STAIRS, direction), 2);
		//world.setBlock(posX+xoffset+dx*2, posY+1, posZ+zoffset+dz*2, b_stairs.block, BlockUtils.getBlockDirMeta(BlockType.STAIRS, direction), 2);
		
		//------------------------
		//Indoor stuff & Furniture
		//------------------------
		
	/*	for (int x = 1; x < sizeX-1; x++) {
			for (int z = 1; z < sizeZ-1; z++) {
				for (int y = 3; y < 6; y++) {
					if (x==1 || z==1 || x== sizeX-2 || z == sizeZ-2) {
						if (y==5 && (x % 4 ==2 || z % 4 == 2)) { //torch
							world.setBlock(posX+x, posY+y, posZ+z, b_torch.block, b_torch.meta, 2);
						}
						if (y == 3 && (x % 4 == 0 || z % 4 == 0) && x!= xoffset && z!= zoffset) {
							world.setBlock(posX+x, posY+y, posZ+z, Blocks.chest, 0, 2);
						}
					}else{
						if (x==2 || z == 2 || x== sizeX-3 || z== sizeZ-3) {		
							//Beds
							if (sizeX / 4 > 1 || sizeZ / 4 > 1) {
								if (y==3 && (x % 4 ==2 && z % 4 == 2) && x!= xoffset && z!= zoffset) {
									if (x==2) {
										world.setBlock(posX+x, posY+y, posZ+z, Blocks.bed, 1, 3);
										world.setBlock(posX+x-1, posY+y, posZ+z, Blocks.bed, 1+8, 3);
									}else if (x==sizeX-3) {
										world.setBlock(posX+x, posY+y, posZ+z, Blocks.bed, 3, 3);
										world.setBlock(posX+x+1, posY+y, posZ+z, Blocks.bed, 3+8, 3);
									}else if (z==2) {
										world.setBlock(posX+x, posY+y, posZ+z, Blocks.bed, 2, 3);
										world.setBlock(posX+x, posY+y, posZ+z-1, Blocks.bed, 2+8, 3);
									}else if (z==sizeZ-3) {
										world.setBlock(posX+x, posY+y, posZ+z, Blocks.bed, 0, 3);
										world.setBlock(posX+x, posY+y, posZ+z+1, Blocks.bed, 0+8, 3);
									}					
								}
							}
//							if (y == 3 && (x % 4 == 0 || z % 4 == 0) && x!= xoffset && z!= zoffset) {
//								world.setBlock(posX+x, posY+y, posZ+z, Blocks.chest, 0, 2);
//							}
								
						}
						//more lamps
						if (y == 5 && x % 8 == 0 && z % 8 == 0) {
							world.setBlock(posX+x, posY+y, posZ+z, b_torch.block, b_torch.meta, 2);
						}
					}
				}
			}
		}*/
	}
	
	public void destroyTop(World w, int sizeX, int sizeZ, int maxy, int miny, int posX, int posZ){
		
		Random rnd = new Random();
		
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z < sizeZ; z++) {
				if (rnd.nextBoolean()){
					int yroll = rnd.nextInt(miny);
					for (int i=maxy; i>= maxy-yroll; i--){
						w.setBlockToAir(posX+x, i, posZ+z);
					}
				}
			}
		}
	}
	

}
