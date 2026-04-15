package techguns.worldgen.structures;

import static net.minecraftforge.common.ChestGenHooks.DUNGEON_CHEST;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDoor;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import techguns.TGBlocks;
import techguns.TGItems;
import techguns.blocks.TGChiselBlocks;
import techguns.util.BlockUtils;
import techguns.util.BlockUtils.BiomeColorType;
import techguns.util.BlockUtils.BlockType;
import techguns.util.MBlock;

public class Barracks extends Structure {
	
	MBlock b_pillar = TGChiselBlocks.wood_pillar;
	MBlock b_wall = TGChiselBlocks.wood_wall;
	MBlock b_floor = TGChiselBlocks.wood_floor;
	MBlock b_scaffold = TGChiselBlocks.wood_scaffold;
	MBlock b_roof = TGChiselBlocks.wood_roof;
	MBlock b_roofSlab = TGChiselBlocks.wood_roofSlab;
	MBlock b_stairs = new MBlock (Blocks.oak_stairs, 0);
	MBlock b_torch = new MBlock (TGBlocks.lamp01, 0);
	MBlock b_ground = new MBlock (Blocks.gravel, 0);
	MBlock b_window = new MBlock (Blocks.glass, 0);
	
	
	public Barracks(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		
		//----------------------
		//Size adjustments
		//-----------------------
		
		//remove 2 in direction to fit Stairs/Walkway
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
		default:
			sizeZ-=2;
			break;
		}
			
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
					if (y ==0) { //Ground
						world.setBlock(posX+x, posY+y, posZ+z, b_ground.block, b_ground.meta, 2);
					}else if (y== 1) { //Pillars
						if (x % 4 == 0 && z % 4 == 0) {
							world.setBlock(posX+x, posY+y, posZ+z, b_pillar.block, b_pillar.meta, 2);
						}
					}else if( y==2) { //Floor
						if (x==0 || z==0 || x== sizeX-1 || z == sizeZ-1) {
							world.setBlock(posX+x, posY+y, posZ+z, b_scaffold.block, b_scaffold.meta, 2);
						}else {
							world.setBlock(posX+x, posY+y, posZ+z, b_floor.block, b_floor.meta, 2);		
						}
					}else if (y <= 4){ //Wall
						if (x==0 || z==0 || x== sizeX-1 || z == sizeZ-1) {
							if (x % 4 == 0 && z % 4 == 0) {
								world.setBlock(posX+x, posY+y, posZ+z, b_scaffold.block, b_scaffold.meta, 2);
							}else {
								if (y==4 && (x % 4 ==2 || z % 4 == 2)) { //Window
									world.setBlock(posX+x, posY+y, posZ+z, b_window.block, b_window.meta, 2);
								}else{
									world.setBlock(posX+x, posY+y, posZ+z, b_wall.block, b_wall.meta, 2);
								}
							}
						}
					}else if (y == 5) { //roof/scaffold
						if (x==0 || z==0 || x== sizeX-1 || z == sizeZ-1) {
							world.setBlock(posX+x, posY+y, posZ+z, b_scaffold.block, b_scaffold.meta, 2);
						}
					}else if (y == 6) {
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
		world.setBlock(posX+xoffset, posY+4, posZ+zoffset, Blocks.air);
		world.setBlock(posX+xoffset, posY+3, posZ+zoffset, Blocks.air);
		ItemDoor.placeDoorBlock(world, posX+xoffset, posY+3, posZ+zoffset, BlockUtils.getBlockDirMeta(BlockType.DOOR, direction), Blocks.wooden_door);

		//Lamp
		world.setBlock(posX+xoffset+dx, posY+5, posZ+zoffset+dz, b_torch.block, 7, 2);
		
		
		//Ramp
		world.setBlock(posX+xoffset+dx, posY+2, posZ+zoffset+dz, b_stairs.block, BlockUtils.getBlockDirMeta(BlockType.STAIRS, direction), 2);
		world.setBlock(posX+xoffset+dx*2, posY+1, posZ+zoffset+dz*2, b_stairs.block, BlockUtils.getBlockDirMeta(BlockType.STAIRS, direction), 2);
		
		//------------------------
		//Indoor stuff & Furniture
		//------------------------
		
		for (int x = 1; x < sizeX-1; x++) {
			for (int z = 1; z < sizeZ-1; z++) {
				for (int y = 3; y < 6; y++) {
					if (x==1 || z==1 || x== sizeX-2 || z == sizeZ-2) {
						if (y==5 && (x % 4 ==2 || z % 4 == 2)) { //torch
							world.setBlock(posX+x, posY+y, posZ+z, b_torch.block, b_torch.meta, 2);
						}
						if (y == 3 && (x % 4 == 0 || z % 4 == 0) && x!= xoffset && z!= zoffset) {
							world.setBlock(posX+x, posY+y, posZ+z, Blocks.chest, 0, 2);
							
							
							TileEntityChest tileentitychest = (TileEntityChest)world.getTileEntity(posX+x, posY+y, posZ+z);

			                if (tileentitychest != null)
			                {
			                    WeightedRandomChestContent.generateChestContents(world.rand, ChestGenHooks.getItems(TGItems.CHESTGEN_MILBASEBARRACKS, world.rand), tileentitychest, ChestGenHooks.getCount(TGItems.CHESTGEN_MILBASEBARRACKS,world.rand));
			                }
							
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
		}
	}
	

}
