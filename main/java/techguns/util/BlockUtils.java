package techguns.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.ForgeDirection;
import techguns.worldgen.structures.Structure;

public class BlockUtils {
	private static final String FILEPATH="/assets/techguns/structures/";
	
	public static final float[][] FILTER_GAUSSIAN_3x3 = new float[][] {
		{1/16f, 1/8f, 1/16f}, 
	    {1/8f, 1/4f, 1/8f},
	    {1/16f, 1/8f, 1/16f}
	};
	
	public static final float[][] FILTER_GAUSSIAN_5x5 = new float[][] {
		{.00325f, .01375f, .0235f, .01375f, .00325f},
        {.01375f, .059f, .097f, .059f, .01375f},
        {.0235f, .097f, .159f, .097f, .0235f},
        {.01375f, .059f, .097f, .059f, .01375f},
        {.00325f, .01375f, .0235f, .01375f, .00325f}
	};
	
	
	public static void fillBlocks(World world, int x, int y, int z, int sizeX, int sizeY, int sizeZ, Block block) {
		for (int i = 0; i < sizeX; i++) {			
			for (int j = 0; j < sizeZ; j++) {
				for (int k = 0; k < sizeY; k++) {
					world.setBlock(x+i, y+k, z+j, block);
				}	
			}
		}
	}
	
	public static void fillBlocksAir(World world, int x, int y, int z, int sizeX, int sizeY, int sizeZ) {
		for (int i = 0; i < sizeX; i++) {			
			for (int j = 0; j < sizeZ; j++) {
				for (int k = 0; k < sizeY; k++) {
					world.setBlock(x+i, y+k, z+j, Blocks.air);
				}	
			}
		}
	}
	
	public static void fillBlocksWithMeta(World world, int x, int y, int z, int sizeX, int sizeY, int sizeZ, Block block, int meta, int type) {
		for (int i = 0; i < sizeX; i++) {			
			for (int j = 0; j < sizeZ; j++) {
				for (int k = 0; k < sizeY; k++) {
					world.setBlock(x+i, y+k, z+j, block, meta, type);
				}	
			}
		}
	}
	
	public static void fillBlocksHollow(World world, int x, int y, int z, int sizeX, int sizeY, int sizeZ, Block block) {
		for (int i = 0; i < sizeX; i++) {			
			for (int j = 0; j < sizeZ; j++) {
				for (int k = 0; k < sizeY; k++) {
					if (j == 0 || j == sizeZ-1 || i == 0 || i == sizeX-1) {
						world.setBlock(x+i, y+k, z+j, block);
					}
				}	
			}
		}
	}
	
	public static int getGroundY(World world, int x, int z) {
		return world.getHeightValue(x, z)-1;
	}
	
	public static void flattenArea(World world, int posX, int posZ, int sizeX, int sizeZ, int maxHeightDiff) {
		int posY;
		int heightSum = 0;
		int count = (sizeX*sizeZ);
		int min = -1;
		int max = 0;
		ArrayList<Integer> heights = new ArrayList<Integer>();
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z <sizeZ; z++) {
				int y = getHeightValueNoTrees(world,posX+x, posZ+z)-1;
				
				heights.add(new Integer(y));
				heightSum+=y;
				if (y > max) max = y;
				if (y < min || min == -1) min = y;
			}
		}
		
		int span = max-min;
		
		float median=0;
		Collections.sort(heights);
		if (heights.size()>2){
			if (heights.size() % 2 ==0){
				median = (heights.get(((int)Math.floor(heights.size()/2))-1) + heights.get(((int)Math.ceil(heights.size()/2))-1)) /2;
			} else {
				median = heights.get((heights.size()/2)-1);
			}
		}
		
		
		float averageHeight = median; //(float)heightSum / (float)count;
		float f = averageHeight - ((float)maxHeightDiff/2.0f);
		//System.out.println("Avg:"+averageHeight+" - f:"+f + " - min:"+min+" - max:"+max);
		
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z <sizeZ; z++) {
				int y =getHeightValueNoTrees(world, posX+x, posZ+z)-1;
				int newY;
				if (span <= maxHeightDiff) newY = y;
				else newY = Math.round(((((float)(y-min)/(float)span)*(float)maxHeightDiff) + f));
				//System.out.println("y: "+y+" - newY:"+newY);
				adjustHeightAtPos(world, posX+x, posZ+z, y, newY);
//				if (newY  > y) {
//					Block b = world.getBlock(posX+x, y, posZ+z);
//					if (!isStackableBlock(b)) {
//						b = Blocks.dirt;
//					}
//					int m = world.getBlockMetadata(posX+x, y, posZ+z);
//					for (int i = y+1; i <= newY; i++) {
//						world.setBlock(posX+x, i, posZ+z, b, m, 2);
//					}
//				}else if (newY < y) {
//					for (int i = y; i > newY; i--) {
//						world.setBlockToAir(posX+x, i, posZ+z);
//					}
//				}
			}
		}
	}
	
	
	public static void flattenAreaToMaxHeight(World world, int posX, int posZ, int sizeX, int sizeZ) {
		int posY;
		int max = 0;
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z <sizeZ; z++) {
				int y = getHeightValueNoTrees(world,posX+x, posZ+z)-1;
				if (y > max) max = y;
			}
		}
		
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z <sizeZ; z++) {
				int y =getHeightValueNoTrees(world, posX+x, posZ+z)-1;
				int newY = max;
				adjustHeightAtPos(world, posX+x, posZ+z, y, newY);
			}
		}
	}
	
	
	private static void adjustHeightAtPos(World world, int x, int z, int y, int newY) {
		if (newY  > y) {
			Block b = world.getBlock(x, y, z);
			if (!isGroundBlock(b)) {
				b = Blocks.dirt;
			}
			int m = world.getBlockMetadata(x, y, z);
			for (int i = y+1; i <= newY; i++) {
				world.setBlock(x, i, z, b, m, 2);
			}
		}else if (newY < y) {
			for (int i = y; i > newY; i--) {
				world.setBlockToAir(x, i, z);
			}
		}
	}
	
//	private static boolean isGroundBlock(Block b) {
//		return b == Blocks.dirt ||
//			   b == Blocks.stone ||
//			   b == Blocks.sandstone ||
//			   b == Blocks.sand ||
//			   b == Blocks.gravel;
//	}
	
	private static boolean isGroundBlock(Block b) {
		Material mat = b.getMaterial();
		return mat == Material.ground ||
				mat == Material.grass ||
				mat == Material.rock ||
				mat == Material.sand ||
				mat == Material.clay;
	}
	
	public static void apply2DHeightmapFilter(World world, int posX, int posZ, int sizeX, int sizeZ, float[][] filter) {
		int xoffset = -filter.length/2;
		int zoffset = -filter[0].length/2;
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z <sizeZ; z++) {
				int y = getHeightValueNoTrees(world,posX+x, posZ+z)-1;
				float newY = 0.0f;
				for (int i = 0; i < filter.length; i++) {
					for (int j = 0; j < filter[i].length; j++){
						newY += (getHeightValueNoTrees(world,posX+x+xoffset+i, posZ+z+zoffset+j)-1.0f) * filter[i][j];
					}
				}			
				adjustHeightAtPos(world, posX+x, posZ+z, y, Math.round(newY));						
			}
		}
	}

	@Deprecated
	public static void removeJunkInAreaOLD(World world, int posX, int posZ, int sizeX, int sizeZ) {
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z <sizeZ; z++) {
				int y = world.getHeightValue(posX+x, posZ+z)-1;
				boolean stop = false;
				for (; y > 0 && !stop; y--) {
					Block b = world.getBlock(posX+x, y, posZ+z);
					if (!isGroundBlock(b)) {
						world.setBlockToAir(posX+x, y, posZ+z);
					}else {
						stop = true;
					}
				}
			}
		}
	}
	
	public static void removeJunkInArea(World world, int posX, int posZ, int sizeX, int sizeZ) {
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z <sizeZ; z++) {
				int y = getHeightValueTopBlockAll(world, posX+x, posZ+z);//world.getHeightValue(posX+x, posZ+z);
				boolean stop = false;
				for (; y > 0 && !stop; y--) {
					Block b = world.getBlock(posX+x, y, posZ+z);
					if (!isGroundBlock(b)) {
						world.setBlockToAir(posX+x, y, posZ+z);
					}else {
						stop = true;
					}
				}
			}
		}
	}
	
	public static int getBlockForgeDirMeta(BlockType type, ForgeDirection fdir) {
		int dir= 0;
		switch (fdir) {
		case DOWN:
			dir = 4;
			break;
		case EAST:
			dir = 2;
			break;
		case NORTH:
			dir = 1;
			break;
		case SOUTH:
			dir = 3;
			break;
		case UP:
			dir = 5;
			break;
		case WEST:
		default:
			dir = 0;
			break;
		}
		return getBlockDirMeta(type, dir);
	}
	
	public static int getBlockDirMeta(BlockType type, int direction) {
		switch(type) {
			case DOOR:
				//System.out.println("Dir:"+direction);
				switch (direction) {
				case 2:
					return 0;
				case 3:
					return 1;
				case 0:
					return 2;
				case 1:
					return 3;
				}	
				break;
			case FENCE_GATE:
				return (direction+1) % 4;
			case LADDER:
				switch (direction) {
				case 0:
					return 5;
				case 1:
					return 3;
				case 2:
					return 4;
				case 3:
					return 2;
				}	
				break;
			case STAIRS:
				switch (direction) {
				case 0:
					return 0;
				case 1:
					return 2;
				case 2:
					return 1;
				case 3:
					return 3;
				}	
				break;
			/*case STAIRS_FLIPPED:
				switch (direction) {
				case 2:
					return 4;
				case 3:
					return 6;
				case 0:
					return 5;
				case 1:
					return 7;
				}	
				break;*/
			default:
				return 0;
		
		}
		return 0;
	}
	
	
	
	
	public enum BlockType {
		STAIRS, LADDER, DOOR, FENCE_GATE, TG, BED, STAIRS2_CHISEL, LEVER, CHEST;
	}
	
	/**
	 * @param rotationOffset number of clockwise rotations for this Block;
	 */
	public static void setBlock(World world, int posX, int posY, int posZ, MBlock mblock, int rotationOffset, int lootTier) {
		mblock.setBlock(world, posX, posY, posZ, rotationOffset, lootTier);
	}
	
	/**
	 * @param rotationOffset number of clockwise rotations for this Block;
	 */
	public static void setBlock(World world, int posX, int posY, int posZ, MBlock mblock, int rotationOffset) {
		mblock.setBlock(world, posX, posY, posZ, rotationOffset);
	}
	
	
	/**
	 * @param rotationOffset number of clockwise rotations for this Block;
	 */
	public static void setBlock(World world, int posX, int posY, int posZ, MultiMBlock mblock, int rotationOffset) {
		mblock.setBlock(world, posX, posY, posZ, rotationOffset);
	}
	
	/**
	 * @param posX worldX of structure origin
	 * @param posY worldY of structure origin
	 * @param posZ worldZ of structure origin
	 * @param x this Block's x offset from origin
	 * @param z this Block's z offset from origin
	 * @param cntX structures rotation center (offset from structure origin)
	 * @param cntZ structures rotation center (offset from structure origin)
	 * @param rotation 
	 */
	public static void setBlockRotated(World world, MBlock mblock, int posX, int posY, int posZ, int x, int z, int cntX, int cntZ, int rotation, int lootTier) {
		
		int[] pos = Structure.rotatePoint(x,z,rotation,cntX,cntZ);
		
		/*int a = x-cntX;
		int b = z-cntZ;
		
		for (int i = 0; i < rotation; i++) {
			int a2 = a;		
			a = b;
			b = -a2;
		}
		
		x = a + cntX;
		z = b + cntZ;*/
		//System.out.printf("x:%d, y:%d, z:%d\n.", x,posY,z);
		
		BlockUtils.setBlock(world, posX+pos[0], posY, posZ+pos[1], mblock, rotation, lootTier);
	}
	
	
	/**
	 * @param posX worldX of structure origin
	 * @param posY worldY of structure origin
	 * @param posZ worldZ of structure origin
	 * @param x this Block's x offset from origin
	 * @param z this Block's z offset from origin
	 * @param cntX structures rotation center (offset from structure origin)
	 * @param cntZ structures rotation center (offset from structure origin)
	 * @param rotation 
	 */
	public static void setBlockRotatedReplaceAirOnly(World world, MBlock mblock, int posX, int posY, int posZ, int x, int z, int cntX, int cntZ, int rotation) {
		int a = x-cntX;
		int b = z-cntZ;
		
		for (int i = 0; i < rotation; i++) {
			int a2 = a;		
			a = b;
			b = -a2;
		}
		
		x = a + cntX;
		z = b + cntZ;
		//System.out.printf("x:%d, y:%d, z:%d\n.", x,posY,z);
		if (world.isAirBlock(posX+x, posY, posZ+z)){
			BlockUtils.setBlock(world, posX+x, posY, posZ+z, mblock, rotation);
		}
	}
    
	/**
	 * Checks if a block texture exists
	 */
 	public static boolean exists(String path, String postfix) {
 		ResourceLocation rl = new ResourceLocation("techguns", "textures/blocks/" + path + postfix + ".png");
 		try {
 			Minecraft.getMinecraft().getResourceManager().getAllResources(rl);
 			return true;
 		} catch (Throwable t) {
 			return false;
 		}
 	}

 	
 	/**
 	 * Like "getTopSolidOrLiquidBlock" from World, but gets first non air block
 	 * @param w
 	 * @param xCoord
 	 * @param zCoord
 	 * @return
 	 */
 	public static int getHeightValueTopBlockAll(World w, int xCoord, int zCoord)
    {
        Chunk chunk = w.getChunkFromBlockCoords(xCoord, zCoord);
        int x = xCoord;
        int z = zCoord;
        int k = chunk.getTopFilledSegment() + 15;
        xCoord &= 15;

        for (zCoord &= 15; k > 0; --k)
        {
            Block block = chunk.getBlock(xCoord, k, zCoord);

            if (!block.isAir(w, x, k, z))
            {
                return k + 1;
            }
        }

        return -1;
    }
 	
 	public static int getHeightValueNoTrees(World w, int x, int z){
 		int y = getTopSolidOrLiquidBlock(w, x, z);
 		//w.getTopBlock(x, z);
 		
 		Block b = w.getBlock(x, y, z);
 		
 		//Block b2 = w.getBlock(x, y+2, z);
 		//System.out.println("Block2:"+b2);
 		/*if(b.getMaterial().isLiquid()){
 			return -1;
 		}*/
 		
 		
 		while (y>0 && (b.isLeaves(w, x, y, z) || b.isWood(w, x, y, z) || b.isFoliage(w, x, y, z))){
 			y--;
 			b = w.getBlock(x, y, z);
 		}
 		
 		return y;
 	}
 	
 	/**
 	 * return -1 when liquid is top block, used to determine valid landspawns
 	 * @param w
 	 * @param x
 	 * @param z
 	 * @return
 	 */
 	public static int getHeightValueNoTreesIgnoreLiquid(World w, int x, int z){
 		int y = w.getTopSolidOrLiquidBlock(x, z);
 		//w.getTopBlock(x, z);
 		
 		Block b = w.getBlock(x, y, z);
 		
 		//Block b2 = w.getBlock(x, y+2, z);
 		//System.out.println("Block2:"+b2);
 		if(b.getMaterial().isLiquid()){
 			return -1;
 		}
 		
 		while (y>0 && (b.isLeaves(w, x, y, z) /*|| b.isWood(w, x, y, z)*/ || b.isFoliage(w, x, y, z))){
 			y--;
 			b = w.getBlock(x, y, z);
 		}
 		
 		
 		/*for(int i=0; i<20; i++){
 			w.setBlock(x, y+i, z, Blocks.diamond_block);
 		}*/
 		
 		
 		return y;
 	}
 	
 	/**
 	 * return -1 when liquid is top block, used to determine valid landspawns
 	 * @param w
 	 * @param x
 	 * @param z
 	 * @return
 	 */
 	public static int getHeightValueUnderwater(World w, int x, int z){
 		int y = w.getTopSolidOrLiquidBlock(x, z);
 		//w.getTopBlock(x, z);
 		
 		Block b = w.getBlock(x, y, z);

 		while (y>0 && (b.isLeaves(w, x, y, z) /*|| b.isWood(w, x, y, z)*/ || b.isFoliage(w, x, y, z) || b.getMaterial().isLiquid())){
 			y--;
 			b = w.getBlock(x, y, z);
 		}
 		
 		
 		/*for(int i=0; i<20; i++){
 			w.setBlock(x, y+i, z, Blocks.diamond_block);
 		}*/
 		
 		
 		return y;
 	}
 	
 	/**
 	 * return -1 when top block is not liquid , used to determine valid waterspawns
 	 * @param w
 	 * @param x
 	 * @param z
 	 * @return
 	 */
 	public static int getHeightValueLiquid(World w, int wx, int wz){
 		//int y = w.getTopSolidOrLiquidBlock(x, z);
 		
 		//w.getTopBlock(wx, wz);
 		
 		
 		Chunk chunk = w.getChunkFromBlockCoords(wx, wz);
        int x = wx;
        int z = wz;
        int k = chunk.getTopFilledSegment() + 15;
        wx &= 15;

        int y=0;
        
        Block block = null;
        for (wz &= 15; k > 0; --k)
        {
            block = chunk.getBlock(wx, k, wz);

            if (block.getMaterial()!=Material.air)
            {
                y = k + 1;
                break;
            }
        }
 			
 		//Block b = w.getBlock(x, y, z);
 		
 		if(block!=null && !block.getMaterial().isLiquid()){
 			return -1;
 		}
 		return y;
 	}
 	
 	 /**
     * get the highest block, liquid or Solid
     */
    private static int getTopSolidOrLiquidBlock(World w, int posx, int posz)
    {
        Chunk chunk = w.getChunkFromBlockCoords(posx, posz);
        int x = posx;
        int z = posz;
        int k = chunk.getTopFilledSegment() + 15;
        posx &= 15;

        for (posz &= 15; k > 0; --k)
        {
            Block block = chunk.getBlock(posx, k, posz);

            if ( (block.getMaterial().blocksMovement() && block.getMaterial() != Material.leaves && !block.isFoliage(w, x, k, z)) || block.getMaterial().isLiquid() )
            {
                return k + 1;
            }
        }

        return -1;
    }
 	
 	public static boolean blockIsValidSpawn(Block b){
 		
 		return !b.getMaterial().isLiquid();
 	}
 	
 	
 	private static int getMedianHeight(int h0, int h1, int h2, int h3){
 		int[] height = {h0,h1,h2,h3};
 		Arrays.sort(height);
 		return (height[1]+height[2])/2;
 	}
 	
 	public static int getValidSpawnYWater(World world, int x, int z, int sizeX, int sizeZ, int heightDiffLimit){
 		int h0 = getHeightValueLiquid(world, x, z);
		int h1 = getHeightValueLiquid(world, x+sizeX, z);
		int h2 = getHeightValueLiquid(world, x, z+sizeZ);
		int h3 = getHeightValueLiquid(world, x+sizeX, z+sizeZ);
		
		if(h0<0 || h1<0 || h2 <0 || h3<0){
			return -1;
		}

 		//boolean solidblocks = blockIsValidSpawn(b0) && blockIsValidSpawn(b1) && blockIsValidSpawn(b2) && blockIsValidSpawn(b3);
 		if( !((MathUtil.abs(h0-h1)<heightDiffLimit) && (MathUtil.abs(h0-h2)<heightDiffLimit) && (MathUtil.abs(h0-h3)<heightDiffLimit)) ){
 			return -1;
 		}

 		return getMedianHeight(h0,h1,h2,h3);
 	}
 	
 	public static int getValidSpawnYArea(World world, int x, int z, int sizeX, int sizeZ, int heightDiffLimit, int step){
 		
 		int count=0;
 		int min=255;
 		int max=0;
 		int sum=0;
 		
 		
 		
 		int ix=0;
 		while(ix<=sizeX){
 		//for (int ix = 0; ix<=sizeX; ix+=step){
 		
 			int iz=0;
 			while(iz<=sizeZ){
 			//for (int iz = 0; iz<=sizeZ; iz+=step){
 	 			
 				int h=getHeightValueNoTreesIgnoreLiquid(world, x+ix, z+iz); 
 				
 				if(h<0){
 					return -1;
 				}
 				
 				if (h<min){
 					min=h;
 				}
 				if(h>max){
 					max=h;
 				}
 				sum+=h;
 				count++;	
 				
 				if(iz<=sizeZ+step){
 					iz+=step;
 				} else if(iz<sizeZ && iz+step > sizeZ){
 					iz=sizeZ;
 				}
 				
 	 		}
 			
 			if(ix<=sizeX+step){
				ix+=step;
			} else if(ix<sizeX && ix+step > sizeX){
				ix=sizeX;
			}
 		}
 		
 		if((max-min)>heightDiffLimit){
 			return -1;
 		}
 		
 		return sum/count;
 		
 	}
 	
 	public static int getValidSpawnY(World world, int x, int z, int sizeX, int sizeZ, int heightDiffLimit){
 		
 		//System.out.println("GetSpawnYfor:"+x+","+z+" with size:"+sizeX+","+sizeZ);
 		
 		int h0 = getHeightValueNoTreesIgnoreLiquid(world, x, z);
		int h1 = getHeightValueNoTreesIgnoreLiquid(world, x+sizeX, z);
		int h2 = getHeightValueNoTreesIgnoreLiquid(world, x, z+sizeZ);
		int h3 = getHeightValueNoTreesIgnoreLiquid(world, x+sizeX, z+sizeZ);
 		
		//System.out.println("Height values:"+h0+","+h1+","+h2+","+h3);
		
		if(h0<0 || h1<0 || h2 <0 || h3<0){
			//System.out.println("FAIL HEIGHT TEST");
			return -1;
		}
 	//	Block b0 = world.getBlock(x, h0, z);
 	//	Block b1 = world.getBlock(x+sizeX, h1, z);
 	//	Block b2 = world.getBlock(x, h2, z+sizeZ);
 	//	Block b3 = world.getBlock(x+sizeX, h3, z+sizeZ);
 		
 		
 		//boolean solidblocks = blockIsValidSpawn(b0) && blockIsValidSpawn(b1) && blockIsValidSpawn(b2) && blockIsValidSpawn(b3);
 		if( !((MathUtil.abs(h0-h1)<heightDiffLimit) && (MathUtil.abs(h0-h2)<heightDiffLimit) && (MathUtil.abs(h0-h3)<heightDiffLimit)) ){
 			//System.out.println("FAIL HEIGHT TEST");
 			return -1;
 		}
 		//System.out.println("PASS HEIGHT TEST");
 		
 		return getMedianHeight(h0,h1,h2,h3); /*solidblocks &&*/ 
 	}
 	
public static int getValidSpawnYUnderwater(World world, int x, int z, int sizeX, int sizeZ, int heightDiffLimit){
 		
 		//System.out.println("GetSpawnYfor:"+x+","+z+" with size:"+sizeX+","+sizeZ);
 		
 		int h0 = getHeightValueUnderwater(world, x, z);
		int h1 = getHeightValueUnderwater(world, x+sizeX, z);
		int h2 = getHeightValueUnderwater(world, x, z+sizeZ);
		int h3 = getHeightValueUnderwater(world, x+sizeX, z+sizeZ);
 		
		//System.out.println("Height values:"+h0+","+h1+","+h2+","+h3);
		
		if(h0<0 || h1<0 || h2 <0 || h3<0){
			//System.out.println("FAIL HEIGHT TEST");
			return -1;
		}
 	//	Block b0 = world.getBlock(x, h0, z);
 	//	Block b1 = world.getBlock(x+sizeX, h1, z);
 	//	Block b2 = world.getBlock(x, h2, z+sizeZ);
 	//	Block b3 = world.getBlock(x+sizeX, h3, z+sizeZ);
 		
 		
 		//boolean solidblocks = blockIsValidSpawn(b0) && blockIsValidSpawn(b1) && blockIsValidSpawn(b2) && blockIsValidSpawn(b3);
 		if( !((MathUtil.abs(h0-h1)<heightDiffLimit) && (MathUtil.abs(h0-h2)<heightDiffLimit) && (MathUtil.abs(h0-h3)<heightDiffLimit)) ){
 			//System.out.println("FAIL HEIGHT TEST");
 			return -1;
 		}
 		//System.out.println("PASS HEIGHT TEST");
 		
 		return getMedianHeight(h0,h1,h2,h3); /*solidblocks &&*/ 
 	}
 	
	public static BiomeColorType getBiomeType(World world, int posX, int posZ) {
		BiomeGenBase biome = world.getBiomeGenForCoords(posX, posZ);
		
//		if (biome == BiomeGenBase.hell) {
//			return BiomeColorType.NETHER;
//		}
//		
//		List<BiomeEntry> biomes = BiomeManager.getBiomes(BiomeType.WARM);
//		for (BiomeEntry b : biomes) {
//			if (b.biome == biome) {
//				return BiomeColorType.WOODLAND;
//			}
//		}
//		biomes = BiomeManager.getBiomes(BiomeType.ICY);
//		for (BiomeEntry b : biomes) {
//			if (b.biome == biome) {
//				return BiomeColorType.SNOW;
//			}
//		}
//		biomes = BiomeManager.getBiomes(BiomeType.COOL);
//		for (BiomeEntry b : biomes) {
//			if (b.biome == biome) {
//				return BiomeColorType.SNOW;
//			}
//		}
//		biomes = BiomeManager.getBiomes(BiomeType.DESERT);
//		for (BiomeEntry b : biomes) {
//			if (b.biome == biome) {
//				return BiomeColorType.DESERT;
//			}
//		}
//		
//		return BiomeColorType.WOODLAND;
		
		if (biome == BiomeGenBase.beach ||
			biome == BiomeGenBase.desert ||
			biome == BiomeGenBase.desertHills || 
			biome == BiomeGenBase.mesa || 
			biome == BiomeGenBase.mesaPlateau || 
			biome == BiomeGenBase.mesaPlateau_F || 
			biome == BiomeGenBase.savanna || 
			biome == BiomeGenBase.savannaPlateau) {
			return BiomeColorType.DESERT;
		}else if (biome == BiomeGenBase.coldBeach ||
			biome == BiomeGenBase.coldTaiga || 
			biome == BiomeGenBase.coldTaigaHills || 
			biome == BiomeGenBase.frozenOcean || 
			biome == BiomeGenBase.frozenRiver || 
			biome == BiomeGenBase.iceMountains || 
			biome == BiomeGenBase.icePlains ||
			biome == BiomeGenBase.megaTaiga || 
			biome == BiomeGenBase.megaTaigaHills || 
			biome == BiomeGenBase.taiga || 
			biome == BiomeGenBase.taigaHills) {
			return BiomeColorType.SNOW;
		}else if (biome == BiomeGenBase.hell) {
			return BiomeColorType.NETHER;
		}else {
			return BiomeColorType.WOODLAND;
		}
	}
	
	public enum BiomeColorType {
		WOODLAND, SNOW, DESERT, NETHER;
	}
	
	
	public static short[][] loadStructureFromFile(String filename) {
		try {
			String path = FILEPATH+filename;
			BufferedReader br = new BufferedReader(new InputStreamReader(Structure.class.getResourceAsStream(path)));
			int count = Integer.parseInt(br.readLine());
			short[][] blocks = new short[count][4];
			String line;
			int i = 0;
			while ((line = br.readLine()) != null) {
				String[] s = line.split(",");
				for (int j = 0; j < s.length; j++) {
					blocks[i][j] = Short.parseShort(s[j]);
				}
				i++;
			}
			
			return blocks;
			
		}catch (IOException e) {
			e.printStackTrace();
			return new short[0][0];
		}
	}
	
	public static void placeScannedStructure(World world, short[][]blocks, ArrayList<MBlock> blockList, int posX, int posY, int posZ, int centerX, int centerZ, int rotation, int pass, int lootTier) {
		//System.out.println("Blocks.length = "+blocks.length + "Blocks[0].length = "+blocks[0].length);
		for (int i = 0; i < blocks.length; i++) {
			int x = blocks[i][0];
			int y = blocks[i][1];
			int z = blocks[i][2];
			//System.out.println("blocklist:"+blockList.size());
			
			/*if(i==0){
				System.out.println("place with Center:"+centerX+","+centerZ);
			}*/
			
			MBlock block = blockList.get(blocks[i][3]);
						
			if (block.pass == pass) {
				BlockUtils.setBlockRotated(world, block, posX, posY+y, posZ, x, z, centerX, centerZ, rotation, lootTier);
			}
		}
	}
	
	
	public static void placeFoundation(World world, short[][]blocks, ArrayList<MBlock> blockList, int posX, int posY, int posZ, int centerX, int centerZ, int rotation, int pass, int range) {
		//System.out.println("Blocks.length = "+blocks.length + "Blocks[0].length = "+blocks[0].length);
		for (int i = 0; i < blocks.length; i++) {
			int x = blocks[i][0];
			int y = blocks[i][1];
			int z = blocks[i][2];
			//System.out.println("blocklist:"+blockList.size());
			if (y==0){
				MBlock block = blockList.get(blocks[i][3]);
				if (block.pass == pass) {
					
					for (int o=1; o<=range; o++){
						
						/*if(world.isAirBlock(posX+x, posY-o, posZ+z)) {
							BlockUtils.setBlockRotated(world, block, posX, posY-o, posZ, x, z, centerX, centerZ, rotation);
						}*/
						setBlockRotatedReplaceAirOnly(world, block, posX, posY-o, posZ, x, z, centerX, centerZ, rotation);
						//world.setBlock(posX+x, posY-o, posZ+z, Blocks.diamond_block);
					}
				}
			}
		}
	}
	
	public static void cleanUpwards(World world, short[][]blocks, ArrayList<MBlock> blockList, int posX, int posY, int posZ, int centerX, int centerZ, int rotation, int pass, int range) {
		//System.out.println("Blocks.length = "+blocks.length + "Blocks[0].length = "+blocks[0].length);
		for (int i = 0; i < blocks.length; i++) {
			int x = blocks[i][0];
			int y = blocks[i][1];
			int z = blocks[i][2];
			//System.out.println("blocklist:"+blockList.size());
			if (y==0){
				MBlock block = new MBlock(Blocks.air, 0);
				if (block.pass == pass) {
					
					for (int o=1; o<=range; o++){

						BlockUtils.setBlockRotated(world, block, posX, posY+o, posZ, x, z, centerX, centerZ, rotation,0);
							
						//setBlockRotatedReplaceAirOnly(world, block, posX, posY-o, posZ, x, z, centerX, centerZ, rotation);
						//world.setBlock(posX+x, posY-o, posZ+z, Blocks.diamond_block);
					}
				}
			}
		}
	}
	
	
	public static List<Entity> selectEntitiesWithinAABB(World world, AxisAlignedBB bb, IEntitySelector filter)
    {
        int i = MathHelper.floor_double((bb.minX - world.MAX_ENTITY_RADIUS) / 16.0D);
        int j = MathHelper.floor_double((bb.maxX + world.MAX_ENTITY_RADIUS) / 16.0D);
        int k = MathHelper.floor_double((bb.minZ - world.MAX_ENTITY_RADIUS) / 16.0D);
        int l = MathHelper.floor_double((bb.maxZ + world.MAX_ENTITY_RADIUS) / 16.0D);
        ArrayList<Entity> arraylist = new ArrayList<Entity>();

        for (int i1 = i; i1 <= j; ++i1)
        {
            for (int j1 = k; j1 <= l; ++j1)
            {
//            	if (world.chunkExists(i1, j1))
//                {
                Chunk chunk = world.getChunkFromChunkCoords(i1, j1);
                if (chunk != null) {
                	getEntitiesWithinAAAB(chunk, bb, arraylist, filter);
                }
//                }
            }
        }

        return arraylist;
    }
	
	//Get entities from chunk
	 private static void getEntitiesWithinAAAB(Chunk chunk, AxisAlignedBB bb, List entityList, IEntitySelector filter)
    {
        int i = MathHelper.floor_double((bb.minY - World.MAX_ENTITY_RADIUS) / 16.0D);
        int j = MathHelper.floor_double((bb.maxY + World.MAX_ENTITY_RADIUS) / 16.0D);
        i = MathHelper.clamp_int(i, 0, chunk.entityLists.length - 1);
        j = MathHelper.clamp_int(j, 0, chunk.entityLists.length - 1);

        for (int k = i; k <= j; ++k)
        {
            List list1 = chunk.entityLists[k];

            for (int l = 0; l < list1.size(); ++l)
            {
                Entity entity = (Entity)list1.get(l);

                if (entity.boundingBox.intersectsWith(bb) && (filter == null || filter.isEntityApplicable(entity)))
                {
                    entityList.add(entity);
                }
            }
        }
    }
	 
	 public static void fillSphere(World world, int posX, int posY, int posZ, float radius, MBlock block) {
		Vec3 center = Vec3.createVectorHelper(posX, posY, posZ);
		
		float rsquared = (float) Math.pow(radius, 2.0f);
		
		int r = ((int)(radius));
		for(int i = -r; i <= r; i++) {
			for(int j = -r; j <= r; j++) {
				for(int k = -r; k <= r; k++) {
					int x = posX + i;
					int y = posY + j;
					int z = posZ + k;
					Vec3 v = Vec3.createVectorHelper(x, y, z);
					float dsquared = (float) center.squareDistanceTo(v);
					if (dsquared < rsquared) {
						block.setBlock(world, x, y, z, 0);
					}
				}
			}
		}	
	}
	 
	 /**
	  * Fills a 'hollow' sphere, where block1 is the inner block and block2 is the hull
	  */
	 public static void fillSphere2(World world, int posX, int posY, int posZ, float radius, MBlock block1, MBlock block2) {
		 fillSphere2(world,posX,posY,posZ,radius, 1.0f, block1, block2);
	 }
	 
	 public static void fillSphere2(World world, int posX, int posY, int posZ, float radius, float thickness, MBlock block1, MBlock block2) {
		Vec3 center = Vec3.createVectorHelper(posX, posY, posZ);
		
		float rsquared = (float) Math.pow(radius, 2.0f);
		float rsquared2 = (float) Math.pow(radius-thickness, 2.0f);
		
		int r = ((int)(radius));
		for(int i = -r; i <= r; i++) {
			for(int j = -r; j <= r; j++) {
				for(int k = -r; k <= r; k++) {
					int x = posX + i;
					int y = posY + j;
					int z = posZ + k;
					Vec3 v = Vec3.createVectorHelper(x, y, z);
					float dsquared = (float) center.squareDistanceTo(v);
					if (dsquared < rsquared2) {
						block1.setBlock(world, x, y, z, 0);
					}else if (dsquared < rsquared) {
						block2.setBlock(world, x, y, z, 0);
					}
				}
			}
		}	
	}
	 
	 public static void fillCylinder(World world, int x1, int y1, int z1, int x2, int y2, int z2, float radius, MBlock block1, MBlock block2) {
			Vec3 v1 = Vec3.createVectorHelper(x1, y1, z1);
			Vec3 v2 = Vec3.createVectorHelper(x2, y2, z2);
			Vec3 v2v1 = v2.subtract(v1);
			double l = v2v1.lengthVector();
			//Vec3 dir = v2v1.normalize();
			int tmp = 0;
			if (x1 > x2) { tmp = x2; x2 = x1; x1 = tmp;}
			if (y1 > y2) {tmp = y2; y2 = y1; y1 = tmp;}
			if (z1 > z2) {tmp = z2; z2 = z1; z1 = tmp;}
			
			int r = (int)radius;
			
			for (int x = x1-r; x < x2+r; x++) {
				for (int y = y1-r; y < y2+r; y++) {
					for (int z = z1-r; z < z2+r; z++) {
						Vec3 v0 = Vec3.createVectorHelper(x, y, z);
						Vec3 v1v0 = v0.subtract(v1);
						Vec3 v_ = v2v1.crossProduct(v1v0);
						double distance = v_.lengthVector()/l;
						
						if (distance < radius-1.0f) {
							block1.setBlock(world, x, y, z, 0);
						}else if (distance < radius) {
							block2.setBlock(world, x, y, z, 0);
						}
					}
				}
			}
			
		}

	    public static void drawLine(World world, int x1, int y1, int z1, int x2, int y2, int z2, MBlock block) {

	        int dx = x2 - x1;
	        int dy = y2 - y1;
	        int dz = z2 - z1;

	        int ax = Math.abs(dx) << 1;
	        int ay = Math.abs(dy) << 1;
	        int az = Math.abs(dz) << 1;

	        int signx = (int) Math.signum(dx);
	        int signy = (int) Math.signum(dy);
	        int signz = (int) Math.signum(dz);

	        int x = x1;
	        int y = y1;
	        int z = z1;

	        int deltax, deltay, deltaz;
	        if (ax >= Math.max(ay, az)) /* x dominant */ {
	            deltay = ay - (ax >> 1);
	            deltaz = az - (ax >> 1);
	            while (true) {
	                block.setBlock(world, x, y, z, 0);
	                if (x == x2) {
	                    return;
	                }


	                if (deltay >= 0) {
	                    y += signy;
	                    deltay -= ax;
	                }

	                if (deltaz >= 0) {
	                    z += signz;
	                    deltaz -= ax;
	                }

	                x += signx;
	                deltay += ay;
	                deltaz += az;
	            }
	        } else if (ay >= Math.max(ax, az)) /* y dominant */ {
	            deltax = ax - (ay >> 1);
	            deltaz = az - (ay >> 1);
	            while (true) {
	            	block.setBlock(world, x, y, z, 0);
	                if (y == y2) {
	                    return;
	                }
	                
//	                //Keep 4-way neighborhood when going up/down
//	                if (deltax >= 0 && deltaz >= 0) {
//	                	block.setBlock(world, x+signx, y, z+signz, 0);
//	                }

	                if (deltax >= 0) {
	                	block.setBlock(world, x+signx, y, z, 0);
	                    x += signx;
	                    deltax -= ay;
	                }

	                if (deltaz >= 0) {
	                	block.setBlock(world, x, y, z+signz, 0);
	                    z += signz;
	                    deltaz -= ay;
	                }

	                y += signy;
	                deltax += ax;
	                deltaz += az;
	            }
	        } else if (az >= Math.max(ax, ay)) /* z dominant */ {
	            deltax = ax - (az >> 1);
	            deltay = ay - (az >> 1);
	            while (true) {
	            	block.setBlock(world, x, y, z, 0);
	                if (z == z2) {
	                    return;
	                }

	                if (deltax >= 0) {
	                    x += signx;
	                    deltax -= az;
	                }

	                if (deltay >= 0) {
	                    y += signy;
	                    deltay -= az;
	                }

	                z += signz;
	                deltax += ax;
	                deltay += ay;
	            }
	        }
	}
}
