package techguns.worldgen.structures;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import techguns.blocks.TGChiselBlocks;
import techguns.util.BlockUtils.BiomeColorType;
import techguns.util.MBlock;

public class Tanks extends Structure {
	protected int tankWidth;
	protected MBlock baseBlock = TGChiselBlocks.technical_scaffold;
	protected MBlock wallBlock = TGChiselBlocks.factory_wall;
	protected MBlock borderBlock = TGChiselBlocks.factory_hazard;
	protected MBlock fillBlock = new MBlock(Blocks.lava,0);//TGChiselBlocks.glowblock;
	
	public Tanks(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		tankWidth=4;
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		
		//dir: 0: -x, 1:-z, 2:+x, 3:+z
		
		int count; //Number of containers
		int offset = 0;
		if (sizeX > sizeZ) {
			count = (int)(((float)sizeZ+1.0f)/((float)tankWidth+1.0f));		
			for (int i = 0;  i < count; i++) {
				//BlockUtils.fillBlocks(world, posX, posY+1, posZ+offset, sizeX, containerHeight, tank, containerBlock);
				world.setBlock(posX+1, posY+1, posZ+offset, baseBlock.block, baseBlock.meta,2);
				world.setBlock(posX+sizeX-2, posY+1, posZ+offset, baseBlock.block, baseBlock.meta,2);
				world.setBlock(posX+1, posY+2, posZ+offset, baseBlock.block, baseBlock.meta,2);
				world.setBlock(posX+sizeX-2, posY+2, posZ+offset, baseBlock.block, baseBlock.meta,2);
				for (int ix=0;ix<sizeX-2;ix++){
					world.setBlock(posX+1+ix, posY+1, posZ+offset+1, baseBlock.block, baseBlock.meta,2);
					world.setBlock(posX+1+ix, posY+1, posZ+offset+2, baseBlock.block, baseBlock.meta,2);
					
				}
				world.setBlock(posX+1, posY+1, posZ+offset+tankWidth-1, baseBlock.block, baseBlock.meta,2);
				world.setBlock(posX+sizeX-2, posY+1, posZ+offset+tankWidth-1, baseBlock.block, baseBlock.meta,2);
				world.setBlock(posX+1, posY+2, posZ+offset+tankWidth-1, baseBlock.block, baseBlock.meta,2);
				world.setBlock(posX+sizeX-2, posY+2, posZ+offset+tankWidth-1, baseBlock.block, baseBlock.meta,2);
				
				//bottom wall
				for (int ix=0;ix<sizeX-2;ix++){
					world.setBlock(posX+1+ix, posY+2, posZ+offset+1, wallBlock.block, wallBlock.meta,2);
					world.setBlock(posX+1+ix, posY+2, posZ+offset+2, wallBlock.block, wallBlock.meta,2);	
				}
				world.setBlock(posX, posY+2, posZ+offset+1, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX+sizeX-1, posY+2, posZ+offset+1, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX, posY+2, posZ+offset+2, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX+sizeX-1, posY+2, posZ+offset+2, borderBlock.block, borderBlock.meta,2);
				
				
				//mid layers
				for (int ix=0;ix<sizeX-2;ix++){
					world.setBlock(posX+1+ix, posY+3, posZ+offset, wallBlock.block, wallBlock.meta,2);
					world.setBlock(posX+1+ix, posY+3, posZ+offset+3, wallBlock.block, wallBlock.meta,2);	
				}
				world.setBlock(posX, posY+3, posZ+offset, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX+sizeX-1, posY+3, posZ+offset, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX, posY+3, posZ+offset+3, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX+sizeX-1, posY+3, posZ+offset+3, borderBlock.block, borderBlock.meta,2);
				
				world.setBlock(posX, posY+3, posZ+offset+1, wallBlock.block, wallBlock.meta,2);
				world.setBlock(posX+sizeX-1, posY+3, posZ+offset+1, wallBlock.block, wallBlock.meta,2);
				world.setBlock(posX, posY+3, posZ+offset+2, wallBlock.block, wallBlock.meta,2);
				world.setBlock(posX+sizeX-1, posY+3, posZ+offset+2, wallBlock.block, wallBlock.meta,2);
				
				
				
				for (int ix=0;ix<sizeX-2;ix++){
					world.setBlock(posX+1+ix, posY+4, posZ+offset, wallBlock.block, wallBlock.meta,2);
					world.setBlock(posX+1+ix, posY+4, posZ+offset+3, wallBlock.block, wallBlock.meta,2);	
				}
				world.setBlock(posX, posY+4, posZ+offset, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX+sizeX-1, posY+4, posZ+offset, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX, posY+4, posZ+offset+3, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX+sizeX-1, posY+4, posZ+offset+3, borderBlock.block, borderBlock.meta,2);
				
				world.setBlock(posX, posY+4, posZ+offset+1, wallBlock.block, wallBlock.meta,2);
				world.setBlock(posX+sizeX-1, posY+4, posZ+offset+1, wallBlock.block, wallBlock.meta,2);
				world.setBlock(posX, posY+4, posZ+offset+2, wallBlock.block, wallBlock.meta,2);
				world.setBlock(posX+sizeX-1, posY+4, posZ+offset+2, wallBlock.block, wallBlock.meta,2);
				
				//Fill
				for (int ix=0;ix<sizeX-2;ix++){
					world.setBlock(posX+1+ix, posY+3, posZ+offset+1, fillBlock.block, fillBlock.meta,2);
					world.setBlock(posX+1+ix, posY+3, posZ+offset+2, fillBlock.block, fillBlock.meta,2);
					world.setBlock(posX+1+ix, posY+4, posZ+offset+1, fillBlock.block, fillBlock.meta,2);
					world.setBlock(posX+1+ix, posY+4, posZ+offset+2, fillBlock.block, fillBlock.meta,2);
				}
				
				
				
				
				//Top layer
				for (int ix=0;ix<sizeX-2;ix++){
					world.setBlock(posX+1+ix, posY+5, posZ+offset+1, wallBlock.block, wallBlock.meta,2);
					world.setBlock(posX+1+ix, posY+5, posZ+offset+2, wallBlock.block, wallBlock.meta,2);	
				}
				world.setBlock(posX, posY+5, posZ+offset+1, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX+sizeX-1, posY+5, posZ+offset+1, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX, posY+5, posZ+offset+2, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX+sizeX-1, posY+5, posZ+offset+2, borderBlock.block, borderBlock.meta,2);
				
				offset+=(tankWidth+1);
			}
			
		}else {
			count = (int)(((float)sizeX+1.0f)/((float)tankWidth+1.0f));			
			for (int i = 0;  i < count; i++) {
				//BlockUtils.fillBlocks(world, posX+offset, posY+1, posZ, containerWidth, containerHeight, sizeZ, containerBlock);
				
				world.setBlock(posX+offset, posY+1, posZ+1, baseBlock.block, baseBlock.meta,2);
				world.setBlock(posX+offset, posY+1, posZ+sizeZ-2, baseBlock.block, baseBlock.meta,2);
				world.setBlock(posX+offset, posY+2, posZ+1, baseBlock.block, baseBlock.meta,2);
				world.setBlock(posX+offset, posY+2, posZ+sizeZ-2, baseBlock.block, baseBlock.meta,2);
				for (int iz=0;iz<sizeZ-2;iz++){
					world.setBlock(posX+offset+1, posY+1, posZ+1+iz, baseBlock.block, baseBlock.meta,2);
					world.setBlock(posX+offset+2, posY+1, posZ+1+iz, baseBlock.block, baseBlock.meta,2);
					
				}
				world.setBlock(posX+offset+tankWidth-1, posY+1, posZ+1, baseBlock.block, baseBlock.meta,2);
				world.setBlock(posX+offset+tankWidth-1, posY+1, posZ+sizeZ-2, baseBlock.block, baseBlock.meta,2);
				world.setBlock(posX+offset+tankWidth-1, posY+2, posZ+1, baseBlock.block, baseBlock.meta,2);
				world.setBlock(posX+offset+tankWidth-1, posY+2, posZ+sizeZ-2, baseBlock.block, baseBlock.meta,2);
				
				//bottom wall
				for (int iz=0;iz<sizeZ-2;iz++){
					world.setBlock(posX+offset+1, posY+2, posZ+iz+1, wallBlock.block, wallBlock.meta,2);
					world.setBlock(posX+offset+2, posY+2, posZ+iz+1, wallBlock.block, wallBlock.meta,2);	
				}
				world.setBlock(posX+offset+1, posY+2, posZ, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX+offset+1, posY+2, posZ+sizeZ-1, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX+offset+2, posY+2, posZ, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX+offset+2, posY+2, posZ+sizeZ-1, borderBlock.block, borderBlock.meta,2);
				
				
				//mid layers
				for (int iz=0;iz<sizeZ-2;iz++){
					world.setBlock(posX+offset, posY+3, posZ+1+iz, wallBlock.block, wallBlock.meta,2);
					world.setBlock(posX+offset+3, posY+3, posZ+1+iz, wallBlock.block, wallBlock.meta,2);	
				}
				world.setBlock(posX+offset, posY+3, posZ, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX+offset, posY+3, posZ+sizeZ-1, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX+offset+3, posY+3, posZ, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX+offset+3, posY+3, posZ+sizeZ-1, borderBlock.block, borderBlock.meta,2);
				
				world.setBlock(posX+offset+1, posY+3, posZ, wallBlock.block, wallBlock.meta,2);
				world.setBlock(posX+offset+1, posY+3, posZ+sizeZ-1, wallBlock.block, wallBlock.meta,2);
				world.setBlock(posX+offset+2, posY+3, posZ, wallBlock.block, wallBlock.meta,2);
				world.setBlock(posX+offset+2, posY+3, posZ+sizeZ-1, wallBlock.block, wallBlock.meta,2);
				
				
				
				for (int iz=0;iz<sizeZ-2;iz++){
					world.setBlock(posX+offset, posY+4, posZ+1+iz, wallBlock.block, wallBlock.meta,2);
					world.setBlock(posX+offset+3, posY+4, posZ+1+iz, wallBlock.block, wallBlock.meta,2);	
				}
				world.setBlock(posX+offset, posY+4, posZ, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX+offset, posY+4, posZ+sizeZ-1, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX+offset+3, posY+4, posZ, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX+offset+3, posY+4, posZ+sizeZ-1, borderBlock.block, borderBlock.meta,2);
				
				world.setBlock(posX+offset+1, posY+4, posZ, wallBlock.block, wallBlock.meta,2);
				world.setBlock(posX+offset+1, posY+4, posZ+sizeZ-1, wallBlock.block, wallBlock.meta,2);
				world.setBlock(posX+offset+2, posY+4, posZ, wallBlock.block, wallBlock.meta,2);
				world.setBlock(posX+offset+2, posY+4, posZ+sizeZ-1, wallBlock.block, wallBlock.meta,2);
				
				//Fill
				for (int iz=0;iz<sizeZ-2;iz++){
					world.setBlock(posX+offset+1, posY+3, posZ+1+iz, fillBlock.block, fillBlock.meta,2);
					world.setBlock(posX+offset+2, posY+3, posZ+1+iz, fillBlock.block, fillBlock.meta,2);
					world.setBlock(posX+offset+1, posY+4, posZ+1+iz, fillBlock.block, fillBlock.meta,2);
					world.setBlock(posX+offset+2, posY+4, posZ+1+iz, fillBlock.block, fillBlock.meta,2);
				}
				

				//Top layer
				for (int iz=0;iz<sizeZ-2;iz++){
					world.setBlock(posX+offset+1, posY+5, posZ+1+iz, wallBlock.block, wallBlock.meta,2);
					world.setBlock(posX+offset+2, posY+5, posZ+1+iz, wallBlock.block, wallBlock.meta,2);	
				}
				world.setBlock(posX+offset+1, posY+5, posZ, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX+offset+1, posY+5, posZ+sizeZ-1, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX+offset+2, posY+5, posZ, borderBlock.block, borderBlock.meta,2);
				world.setBlock(posX+offset+2, posY+5, posZ+sizeZ-1, borderBlock.block, borderBlock.meta,2);
				
				offset+=(tankWidth+1);
			}
		}
		
		
		

	}

}
