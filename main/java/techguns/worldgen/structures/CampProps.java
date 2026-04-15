package techguns.worldgen.structures;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.blocks.BlockTGChest;
import techguns.blocks.TGChiselBlocks;
import techguns.util.BlockUtils;
import techguns.util.BlockUtils.BiomeColorType;
import techguns.util.MBlock;

public class CampProps extends Structure {
	
	MBlock groundBlock = new MBlock(Blocks.gravel, 0);
	MBlock[] crateBlocks = new MBlock[] { TGChiselBlocks.factory_box, TGChiselBlocks.woodenCrate};
	
	
	int variant = -1;

	public CampProps(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		this.lootTier=2;
	}
	
	public CampProps(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int variant) {
		this(minX, minY, minZ, maxX, maxY, maxZ);
		this.variant = variant;
		this.lootTier=2;
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		
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

		if (this.variant == -1) variant = world.rand.nextInt(2);
		
		switch (variant) {
			case 0: //pile of crates
		    default:
				setCrateBlocks(world, posX, posY, posZ, sizeX, sizeY, sizeZ,rnd);
				break;
			case 1: //crate + roof
				setRoofBlocks(world, posX, posY, posZ, sizeX, sizeY, sizeZ, camoMeta);
				setCrateBlocks(world, posX+1, posY, posZ+1, sizeX-2, sizeY-2, sizeZ-2,rnd);				
				break;
			case 2: //Sandbags
				setSandbagBlocks(world, posX, posY, posZ, sizeX, sizeY, sizeZ, direction);
				break;
			case 3: //Sandbags + roof
				setRoofBlocks(world, posX, posY+1, posZ, sizeX, sizeY-1, sizeZ, camoMeta);
				setSandbagBlocks(world, posX, posY, posZ, sizeX, sizeY, sizeZ, direction);
				break;
			case 4: //row of sandbags
				setSandbagRow(world, posX, posY, posZ, sizeX, sizeY, sizeZ, direction);
				break;
			case 5: //Sandbags + roof
				setRoofBlocks(world, posX, posY+1, posZ, sizeX, sizeY-1, sizeZ, camoMeta);
				setSandbagBlocks(world, posX, posY, posZ, sizeX, sizeY, sizeZ, direction);
				setCrateBlocks(world, posX+1, posY, posZ+1, sizeX-2, sizeY-2, sizeZ-2,rnd);				
				break;
		}
		
	}
	
	private void setSandbagRow(World world, int posX, int posY, int posZ,
			int sizeX, int sizeY, int sizeZ, int direction) {
		MBlock sandbags = new MBlock(TGBlocks.sandbags, 0);
		
		int x = 0;
		int z = 0;
		int i = 1;
		for (int y = 1; y < sizeY; y++) {
			if (sizeX > sizeZ) {
				z = sizeZ/2;
				for (x = i; x < sizeX-i; x++) {
					world.setBlock(posX+x, posY+y, posZ+z, sandbags.block, sandbags.meta, 2);
				}
			}else {
				x = sizeX/2;
				for (z = i; z < sizeZ-i; z++) {
					world.setBlock(posX+x, posY+y, posZ+z, sandbags.block, sandbags.meta, 2);
				}
			}
			i++;
		}
	}

	private void setSandbagBlocks(World world, int posX, int posY, int posZ,
			int sizeX, int sizeY, int sizeZ, int direction) {
		
		 MBlock sandbags = new MBlock(TGBlocks.sandbags, 0);
		
	//	for (int y = 0; y < 2; y++) {
			for (int x = 0; x < sizeX; x++) {
				for (int z = 0; z < sizeZ; z++ ) {
			//		if (y == 0) {
			//			world.setBlock(posX+x, posY+y, posZ+z, groundBlock.block, groundBlock.meta, 2);
			//		}else if (y == 1) {
						if (x== 0 || x == sizeX-1 || z==0 || z == sizeZ-1) {
							world.setBlock(posX+x, posY+1, posZ+z, sandbags.block, sandbags.meta, 2);
						}
			//		}
				}
			}
		//}
			
			
			int xoffset, zoffset; //Door/center positions
			int dx, dz;
			
			switch (direction) {
				case 0:
					xoffset = 0; zoffset=(sizeZ/2);
					break;
				case 1:
					xoffset = (sizeX/2); zoffset=0;
					break;
				case 2:
					xoffset = sizeX-1; zoffset=(sizeZ/2);
					break;
				case 3:
				default:
					xoffset = (sizeX/2); zoffset=sizeZ-1;
					break;
			}
			
			//opening
			world.setBlock(posX+xoffset, posY+1, posZ+zoffset, Blocks.air);
		
	}

	private void setRoofBlocks(World world, int posX, int posY, int posZ,
			int sizeX, int sizeY, int sizeZ, int camoMeta) {
		BlockUtils.fillBlocks(world, posX, posY+1, posZ, 1, sizeY-2, 1, Blocks.fence);
		BlockUtils.fillBlocks(world, posX+sizeX-1, posY+1, posZ, 1, sizeY-2, 1, Blocks.fence);
		BlockUtils.fillBlocks(world, posX, posY+1, posZ+sizeZ-1, 1, sizeY-2, 1, Blocks.fence);
		BlockUtils.fillBlocks(world, posX+sizeX-1, posY+1, posZ+sizeZ-1, 1, sizeY-2, 1, Blocks.fence);
		BlockUtils.fillBlocksWithMeta(world, posX, posY+sizeY-1, posZ, sizeX, 1, sizeZ, TGBlocks.camoNetRoof, camoMeta, 2);
	}

	public void setCrateBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, Random rnd) {

//		boolean placeLamp = false;
//		if (((sizeX+sizeZ)/2.0f)/6.0f > r.nextFloat()) placeLamp = true;
		
		for (int x = 0; x < sizeX; x++) {
			for (int z=0; z <sizeZ; z++) {
				world.setBlock(posX+x, posY, posZ+z, groundBlock.block, groundBlock.meta, 2);
				
				//roll for special chest
				float chestroll = rnd.nextFloat();
				Block chest=null;
				
				if (chestroll<=0.05f){
					chest = TGBlocks.tgchest;
				} else if ( chestroll<=0.1f){
					chest = TGBlocks.tgchest_weapon;
				} 
				
				
				if (chest==null){
				
					for (int y = 1; y < sizeY; y++) {
						if (!isFreeSpace(world, posX+x, posY+y-1, posZ+z) && rnd.nextFloat() > 0.5f) {
							int index = rnd.nextInt(crateBlocks.length);
	//						if (placeLamp && (y == sizeY-1 || r.nextFloat() > 0.25f*y)) {
	//							world.setBlock(posX+x, posY+y, posZ+z, TGBlocks.lamp01, 7, 2);
	//							placeLamp = false;
	//						}else {
								world.setBlock(posX+x, posY+y, posZ+z, crateBlocks[index].block, crateBlocks[index].meta, 2);
							//}
						}
					}
				} else {
					int meta = 2+rnd.nextInt(4);
					MBlock TGChest = new MBlock(chest,meta);
					TGChest.setBlock(world, posX+x, posY+1, posZ+z, 0, this.lootTier);

				}
			}
		}
		
		if (((sizeX+sizeZ)/2.0f)/6.0f > rnd.nextFloat()) { //place lantern
			for (int y = sizeY; y > 1; y--) {
				for (int t = 0; t < 3; t++) {
					int x = rnd.nextInt(sizeX);
					int z = rnd.nextInt(sizeZ);
					if (world.isAirBlock(posX+x, posY+y, posZ+z) && !isFreeSpace(world,posX+x,posY+y-1,posZ+z)) {
						world.setBlock(posX+x, posY+y, posZ+z, TGBlocks.lamp01, 7, 2);
						return;
					}
				}
			}
		}
	}
	
	public boolean isFreeSpace(World world, int x, int y, int z) {
		return world.isAirBlock(x, y, z) || world.getBlock(x, y, z) == Blocks.snow_layer || world.getBlock(x, y, z) == TGBlocks.lamp01;
	}

}
