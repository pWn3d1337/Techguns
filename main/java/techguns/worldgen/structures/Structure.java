package techguns.worldgen.structures;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import techguns.util.BlockUtils;
import techguns.util.MathUtil;
import techguns.util.BlockUtils.BiomeColorType;

public abstract class Structure {
	public int minX; //Minimum size
	public int minY;
	public int minZ;
	
	public int maxX; //Maximum size
	public int maxY;
	public int maxZ;
	
//	public int prefX; //preferred size
//	public int prefY;
//	public int prefZ;
	
	public int sizeX;
	public int sizeZ;
	//unrotated center
	public int centX;
	public int centZ;
	
	public boolean canSwapXZ; //IF (min/max) X != Z ... X and Z can be swapped
	
	public boolean removeJunkOnWorldspawn=false;
		
	public static BiomeDictionary.Type[] coldTypes = {BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY};
	public static BiomeDictionary.Type[] sandTypes = {BiomeDictionary.Type.SANDY, BiomeDictionary.Type.SAVANNA.BEACH, BiomeDictionary.Type.MESA};
	public static BiomeDictionary.Type[] netherTypes = {BiomeDictionary.Type.NETHER};
	
	protected int lootTier=0;
	
	public Structure(){
		this(0,0,0,0,0,0);
	}
	
	protected int getStep(){
		return 4;
	}

	public Structure(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super();
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	public Structure setXZSize(int x, int z){
		this.sizeX=x;
		this.sizeZ=z;
		this.centX = (int) (sizeX/2.0f);
		this.centZ = (int) (sizeZ/2.0f);
		return this;
	}
	
	public int getSizeX(Random rnd) {
		return sizeX;
	}

	public int getSizeZ(Random rnd) {
		return sizeZ;
	}

	public Structure setSwapXZ(boolean b) {
		this.canSwapXZ = b;
		return this;
	}

/**
 * 
 * @param world
 * @param posX
 * @param posY
 * @param posZ
 * @param sizeX
 * @param sizeY
 * @param sizeZ
 * @param direction - where does it point; 0=WEST(-x), 1=NORTH(-z), 2=EAST(+x); 3=SOUTH(+z);
 * @param rnd TODO
 */
	public abstract void setBlocks(World world, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd);
	
	
	public static int[] rotatePoint(int x, int z, int direction, int centerX, int centerZ){
		// Rotate the height sample points around center.
		/*if (((sizeX < this.minX) && (sizeZ > this.minX) && (sizeX >= this.minZ))
			   ||((sizeZ < this.minZ) && (sizeX > this.minZ) && (sizeZ >= this.minX)))
			{
				direction = (direction+1) % 4;
				centerZ = (int) (sizeX/2.0f);
				centerX = (int) (sizeZ/2.0f);
			}else {*/
			
			//}
	
				
			//	System.out.println("Rotate Point("+x+","+z+") around ("+centerX+","+centerZ+") for dir:"+direction);
				
				
			int a = x-centerX;
			int b = z-centerZ;
			
			for (int i = 0; i < direction; i++) {
				int a2 = a;		
				a = b;
				b = -a2;
			}
			
			x = a + centerX;
			z = b + centerZ;
			//END ROTATION
			//System.out.println("Result: ("+x+","+z+")");
			
			return new int[]{x,z};
	}
	
	public void spawnStructureWorldgen(World world, int chunkX, int chunkZ, int sizeX, int sizeY, int sizeZ, Random rnd, BiomeGenBase biome){
		int direction = rnd.nextInt(4);
		
		/*for(int i=1; i<direction+1; i++){
 			world.setBlock(chunkX*16, 25+i, chunkZ*16, Blocks.iron_block);
 		}*/
		
		
		
		int sizeXr = direction==0||direction==2 ? sizeX : sizeZ;
		int sizeZr = direction==0||direction==2 ? sizeZ : sizeX;
		
		
		int centerX = (int) (sizeX/2.0f);
		int centerZ = (int) (sizeZ/2.0f);
		
		//System.out.println("Rotation Center: "+centerX+","+centerZ);
		
		//int centerXr = direction==0||direction==2 ? centerX : centerZ;
		//int centerZr = direction==0||direction==2 ? centerZ : centerX;
		
		//System.out.println("Origin Point: "+chunkX*16+","+chunkZ*16);
		
		/**
		 * Rotate 4 corner points
		 */
		int[] p0 = rotatePoint(0, 0, direction,centerX,centerZ);
		int[] p1 = rotatePoint(sizeX, 0, direction,centerX,centerZ);
		int[] p2 = rotatePoint(0, sizeZ, direction,centerX,centerZ);
		int[] p3 = rotatePoint(sizeX, sizeZ, direction,centerX,centerZ);
		
		int minX = Math.min(Math.min(p0[0], p1[0]),Math.min(p2[0], p3[0]));
		int minZ = Math.min(Math.min(p0[1], p1[1]),Math.min(p2[1], p3[1]));
		
		int x=minX+chunkX*16;//-cXr;
		int z=minZ+chunkZ*16;//-cZr;
				

		int offsetX=this.getRotationShiftX(direction);
		int offsetZ=this.getRotationShiftZ(direction);
		
		
		/*switch(direction){
			case 1:
				offsetX=(int)(centerX/2.0);
				offsetZ=-(int)(centerZ/2.0);
				break;
			case 3:
				offsetX=(int)(centerX/2.0);
				offsetZ=-(int)(centerZ/2.0);
				break;
		}*/
		
		
		//int y =BlockUtils.getValidSpawnY(world, x+offsetX, z+offsetZ, sizeXr, sizeZr,3);
		
		int y =BlockUtils.getValidSpawnYArea(world, x+offsetX, z+offsetZ, sizeXr, sizeZr,3,getStep());
		
		
		//System.out.println("Origin Point: "+x+","+z);
		
		/*for(int i=0; i<10; i++){
 			world.setBlock(chunkX*16+p0[0], y+i, chunkZ*16+p0[1], Blocks.gold_block);
 			world.setBlock(chunkX*16+p1[0], y+i, chunkZ*16+p1[1], Blocks.gold_block);
 			world.setBlock(chunkX*16+p2[0], y+i, chunkZ*16+p2[1], Blocks.gold_block);
 			world.setBlock(chunkX*16+p3[0], y+i, chunkZ*16+p3[1], Blocks.gold_block);
 		}*/
		
		if (y<0){
			return;
		}
		
		if(removeJunkOnWorldspawn){
			BlockUtils.removeJunkInArea(world, x-1, z-1, sizeXr+2, sizeZr+2);
		}
		
		this.setBlocks(world, x, y-1, z, sizeX, sizeY, sizeZ, direction, getBiomeColorTypeFromBiome(biome), rnd);
	}
	
	protected static BiomeColorType getBiomeColorTypeFromBiome(BiomeGenBase biome){
		if (isInBiomeList(coldTypes, biome)){
			return BiomeColorType.SNOW;
		} else if (isInBiomeList(sandTypes, biome)){
			return BiomeColorType.DESERT;
		} else if (isInBiomeList(netherTypes, biome)){
			return BiomeColorType.NETHER;
		}
		
		return BiomeColorType.WOODLAND;
	}
	
	private static boolean isInBiomeList(BiomeDictionary.Type[] list, BiomeGenBase biome){
		for (int i =0; i<list.length; i++){
			if (BiomeDictionary.isBiomeOfType(biome, list[i])){
				return true;
			}
		}
		return false;
	}
	
	protected int getRotationShiftX(int direction){
		return 0;
	}
	protected int getRotationShiftZ(int direction){
		return 0;
	}
}
