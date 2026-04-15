package techguns.worldgen.structures;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDoor;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.blocks.TGChiselBlocks;
import techguns.util.BlockUtils;
import techguns.util.BlockUtils.BiomeColorType;
import techguns.util.BlockUtils.BlockType;
import techguns.util.MBlock;

public class Bunker extends Structure {

	 MBlock groundBlock = new MBlock(TGBlocks.concrete, 5);
	 MBlock stairsblock = new MBlock(Blocks.stone_stairs, 0);
	 MBlock wallBlock = new MBlock(TGBlocks.concrete, 6);
	 MBlock roofBlock = new MBlock(TGBlocks.concrete, 3);
	 MBlock sandbags = new MBlock(TGBlocks.sandbags, 0);
	 MBlock ladder = new MBlock(TGBlocks.ladder01, 0);
	 MBlock[] crateBlocks = new MBlock[] { TGChiselBlocks.factory_box, TGChiselBlocks.woodenCrate};
	 
	 int type = 0;
	
	public Bunker(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int type) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		this.type = type;
		this.lootTier=2;
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		
			//Adjust direction from size
			if ((sizeX <= 4) && (sizeZ >= 5)) {
				if (direction == 0 || direction == 2) {
					direction = (direction+1) % 4;
				}
			}else if ((sizeZ <= 4) && (sizeX >= 5)) {
				if (direction == 1 || direction == 3) {
					direction = (direction+1) % 4;
				}
			}
		
			switch (direction) {
			case 0:
				posX+=2;
				sizeX-=2;
				break;
			case 1:
				posZ+=2;
				sizeZ-=2;
				break;
			case 2:
				sizeX-=2;
				break;
			case 3:
				sizeZ-=2;
				break;
		 }
		 
		 /*  
		  * 2  WWWWWWWWW
		  * 1  W   W   W
		  * 0 BBWWWWWWWWWBB
		  *-1 BBGGGGGGGGGBB
		  */
	 
		 for (int y = -1; y < sizeY; y++) {
			for (int x = 0; x < sizeX; x++) {
				for (int z = 0; z < sizeZ; z++) {
					if (y == -1) {
						//setBlocks groundblock
						world.setBlock(posX+x, posY+y, posZ+z, groundBlock.block, groundBlock.meta, 2);
					}else if (y == 0) {
						if (x== 0 || x == sizeX-1 || z==0 || z == sizeZ-1) {
							//setBlocks wallblock
							world.setBlock(posX+x, posY+y, posZ+z, wallBlock.block, wallBlock.meta, 2);
						} else {
							world.setBlockToAir(posX+x, posY+y, posZ+z);
						}
					}else if (y == 1) {
						if (type == 2 || type == 3) { //no windows
							if (x== 0 || x == sizeX-1 || z==0 || z == sizeZ-1) {
								//setBlocks wallblock
								world.setBlock(posX+x, posY+y, posZ+z, wallBlock.block, wallBlock.meta, 2);
							}				
						}else {
							if (((x== 0 || x == sizeX-1) && (z==0 || z == sizeZ-1 || z % 3 == 0)) ||
									((x== 0 || x == sizeX-1 || x % 3 == 0) && (z==0 || z == sizeZ-1 ))) {
								//setBlocks wallblock 
								world.setBlock(posX+x, posY+y, posZ+z, wallBlock.block, wallBlock.meta, 2);
							}
						}
					}else if (y == 2) {
						if (x== 0 || x == sizeX-1 || z==0 || z == sizeZ-1) {
							//setBlocks wallblock
							world.setBlock(posX+x, posY+y, posZ+z, wallBlock.block, wallBlock.meta, 2);
						}else {
							//setBlocks roofblock
							world.setBlock(posX+x, posY+y, posZ+z, roofBlock.block, roofBlock.meta, 2);
						}
					}else if (y == 3 && (type == 1 || type == 3)) { //Sandbag roof;
						if (x== 0 || x == sizeX-1 || z==0 || z == sizeZ-1) {
							//setBlocks wallblock
							world.setBlock(posX+x, posY+y, posZ+z, sandbags.block, sandbags.meta, 2);
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
				xoffset = 0; zoffset=sizeZ/2;
				dx = -1; dz =0;
				break;
			case 1:
				xoffset = sizeX/2; zoffset=0;
				dx = 0; dz = -1;
				break;
			case 2:
				xoffset = sizeX-1; zoffset=sizeZ/2;
				dx = 1; dz=0;
				break;
			case 3:
			default:
				xoffset = sizeX/2; zoffset=sizeZ-1;
				dx = 0; dz=1;
				break;
		}
		
		//Door
		world.setBlock(posX+xoffset, posY+1, posZ+zoffset, Blocks.air);
		world.setBlock(posX+xoffset, posY+0, posZ+zoffset, Blocks.air);
		
		ItemDoor.placeDoorBlock(world, posX+xoffset, posY+0, posZ+zoffset, BlockUtils.getBlockDirMeta(BlockType.DOOR, direction), TGBlocks.door01);

		//Doorway
		world.setBlock(posX+xoffset+((dx == 0) ? 1 : 0), posY+0, posZ+zoffset+((dz == 0) ? 1 : 0), wallBlock.block, wallBlock.meta, 2);
		world.setBlock(posX+xoffset+((dx == 0) ? 1 : 0), posY+1, posZ+zoffset+((dz == 0) ? 1 : 0), wallBlock.block, wallBlock.meta, 2);
		world.setBlock(posX+xoffset+((dx == 0) ? -1 : 0), posY+0, posZ+zoffset+((dz == 0) ? -1 : 0), wallBlock.block, wallBlock.meta, 2);
		world.setBlock(posX+xoffset+((dx == 0) ? -1 : 0), posY+1, posZ+zoffset+((dz == 0) ? -1 : 0), wallBlock.block, wallBlock.meta, 2);
		
		world.setBlock(posX+xoffset+((dx == 0) ? 1 : dx), posY-1, posZ+zoffset+((dz == 0) ? 1 : dz), wallBlock.block, wallBlock.meta, 2);
		world.setBlock(posX+xoffset+((dx == 0) ? -1 : dx), posY-1, posZ+zoffset+((dz == 0) ? -1 : dz), wallBlock.block, wallBlock.meta, 2);
		world.setBlock(posX+xoffset+((dx == 0) ? 1 : dx*2), posY-1, posZ+zoffset+((dz == 0) ? 1 : dz*2), wallBlock.block, wallBlock.meta, 2);
		world.setBlock(posX+xoffset+((dx == 0) ? -1 : dx*2), posY-1, posZ+zoffset+((dz == 0) ? -1 : dz*2), wallBlock.block, wallBlock.meta, 2);		
		world.setBlock(posX+xoffset+((dx == 0) ? 1 : dx), posY+0, posZ+zoffset+((dz == 0) ? 1 : dz), wallBlock.block, wallBlock.meta, 2);
		world.setBlock(posX+xoffset+((dx == 0) ? -1 : dx), posY+0, posZ+zoffset+((dz == 0) ? -1 : dz), wallBlock.block, wallBlock.meta, 2);
		world.setBlock(posX+xoffset+((dx == 0) ? 1 : dx*2), posY+0, posZ+zoffset+((dz == 0) ? 1 : dz*2), wallBlock.block, wallBlock.meta, 2);
		world.setBlock(posX+xoffset+((dx == 0) ? -1 : dx*2), posY+0, posZ+zoffset+((dz == 0) ? -1 : dz*2), wallBlock.block, wallBlock.meta, 2);
		
		world.setBlock(posX+xoffset+((dx == 0) ? 1 : dx), posY+2, posZ+zoffset+((dz == 0) ? 1 : dz), TGBlocks.lamp01, 0, 2);
		world.setBlock(posX+xoffset+((dx == 0) ? -1 : dx), posY+2, posZ+zoffset+((dz == 0) ? -1 : dz), TGBlocks.lamp01, 0, 2);
		
		world.setBlock(posX+xoffset, posY-1, posZ+zoffset, groundBlock.block, groundBlock.meta, 2);
		world.setBlock(posX+xoffset+dx, posY-1, posZ+zoffset+dz, groundBlock.block, groundBlock.meta, 2);
		world.setBlockToAir(posX+xoffset+dx, posY+0, posZ+zoffset+dz);
		world.setBlockToAir(posX+xoffset+dx, posY+1, posZ+zoffset+dz);
		
		//Stairs
		world.setBlock(posX+xoffset+dx*2, posY+0, posZ+zoffset+dz*2, stairsblock.block, BlockUtils.getBlockDirMeta(BlockType.STAIRS, (direction+2) % 4), 2);
		world.setBlockToAir(posX+xoffset+dx*2, posY+1, posZ+zoffset+dz*2);
		
		
		//Inner objects
		//Crates
		if (type == 2 || type == 3) {
			//Lamp
			world.setBlock(posX+xoffset-dx, posY+1, posZ+zoffset-dz, TGBlocks.lamp01, 0, 2);
//			if (type == 2) {
//				setCrateBlocks(world, posX-dx+1, posY, posZ-dz+1, sizeX-1-Math.abs(dx), 2, sizeZ-1-Math.abs(dz));
//			}else {
				setCrateBlocks(world, posX+2, posY, posZ+2, sizeX-4, 2, sizeZ-4);
			//}
			
			
		}else {
			//Lamp
			world.setBlock(posX+(sizeX/2), posY+1, posZ+(sizeZ/2), TGBlocks.lamp01, 0, 2);
		
		}
		
		
		
		//Ladder
		if (type == 1 || type == 3) {
			int meta = 0;
			switch (direction) {
				case 0: //-X
					xoffset = sizeX-2;
					zoffset = sizeZ-2;
					dx = 1; dz=0;
					meta = 2;
					break;
				case 1: //-Z
					zoffset = sizeZ-2;
					xoffset = 1;
					dx = 0; dz = 1;
					meta = 0;
					break;
				case 2: //+X
					xoffset = 1;
					zoffset = 1;
					dx = -1; dz = 0;
					meta = 3;
					break;
				case 3: //+Z
					xoffset = sizeX-2;
					zoffset = 1;
					dx = 0; dz = -1;
					meta = 1;
					break;
			}
			//meta = (meta+2) % 4;
			
			world.setBlockToAir(posX+xoffset, posY+2, posZ+zoffset);
			world.setBlock(posX+xoffset+dx, posY+1, posZ+zoffset+dz, wallBlock.block, wallBlock.meta, 2);
			
			world.setBlock(posX+xoffset, posY+0, posZ+zoffset, ladder.block, ladder.meta+meta, 2);
			world.setBlock(posX+xoffset, posY+1, posZ+zoffset, ladder.block, ladder.meta+meta, 2);
			world.setBlock(posX+xoffset, posY+2, posZ+zoffset, ladder.block, ladder.meta+meta, 2);
		}
		
	}

	private void setCrateBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ) {
		Random r = world.rand;
		for (int x = 0; x < sizeX; x++) {
			for (int z=0; z <sizeZ; z++) {
				
				float chestroll = r.nextFloat();
				Block chest=null;
				
				if (chestroll<=0.05f){
					chest = TGBlocks.tgchest;
				} else if ( chestroll<=0.1f){
					chest = TGBlocks.tgchest_weapon;
				} 
				
				
				if (chest==null){
					
					for (int y = 0; y < sizeY; y++) {
						if (!world.isAirBlock(posX+x, posY+y-1, posZ+z) && r.nextFloat() > 0.5) {
							int index = r.nextInt(crateBlocks.length);
							world.setBlock(posX+x, posY+y, posZ+z, crateBlocks[index].block, crateBlocks[index].meta, 2);
						}
					}
				} else {
					
					int meta = 2+r.nextInt(4);
					MBlock TGChest = new MBlock(chest,meta);
					TGChest.setBlock(world, posX+x, posY, posZ+z, 0, this.lootTier);
					
				}
			}
		}
	}

}
