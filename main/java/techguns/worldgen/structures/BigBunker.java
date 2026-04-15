package techguns.worldgen.structures;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.util.BlockUtils.BiomeColorType;
import techguns.util.BlockUtils.BlockType;

public class BigBunker extends Structure {

	
	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static short[][] blocks;
	static {
		blockList.add(new MBlock(Blocks.dirt,2));
		blockList.add(new MBlock(TGBlocks.sandbags, 0));
		blockList.add(new MBlock(Blocks.air, 0));
		blockList.add(new MBlock(TGBlocks.concrete, 6));
		blockList.add(new MBlock(Blocks.stone_brick_stairs, 2, true, BlockType.STAIRS));
		blockList.add(new MBlock(Blocks.stone_brick_stairs, 0, true, BlockType.STAIRS));
		blockList.add(new MBlock(Blocks.stone_brick_stairs, 3, true, BlockType.STAIRS));
		blockList.add(new MBlock(TGBlocks.camoNetRoof, 0));
		blockList.add(new MBlock(TGBlocks.concrete, 1));
		blockList.add(new MBlock(TGBlocks.concrete, 4));
		blockList.add(new MBlock(Blocks.stone_brick_stairs, 1, true, BlockType.STAIRS));
		blockList.add(new MBlock(TGBlocks.concrete, 0));
		blockList.add(new MBlock(Blocks.spruce_stairs, 3, true, BlockType.STAIRS));
		blockList.add(new MBlock(TGBlocks.door01, 3, true, BlockType.DOOR).setPass(0));
		blockList.add(new MBlock(TGBlocks.door01, 1, true, BlockType.DOOR).setPass(0));
		blockList.add(new MBlock(Blocks.spruce_stairs, 2, true, BlockType.STAIRS));
		blockList.add(new MBlock(TGBlocks.door01, 8, true, BlockType.DOOR).setPass(1));
		blockList.add(new MBlock(TGBlocks.lamp01, 1, true, BlockType.TG).setPass(1));
		blockList.add(new MBlock(TGBlocks.concrete, 5));
		blockList.add(new MBlock(TGBlocks.door01, 0, true, BlockType.DOOR).setPass(0));
		blockList.add(new MBlock(TGBlocks.door01, 9, true, BlockType.DOOR).setPass(1));
		blockList.add(new MBlock(Blocks.stone_brick_stairs, 4, true, BlockType.STAIRS));
		blockList.add(new MBlock(TGBlocks.door01, 2, true, BlockType.DOOR).setPass(0));
		blockList.add(new MBlock(TGBlocks.tgchest_weapon, 3, true, BlockType.CHEST));
		blockList.add(new MBlock(TGBlocks.tgchest_weapon, 2, true, BlockType.CHEST));
		blockList.add(new MBlock(TGBlocks.monsterspawner, 1));
		blockList.add(new MBlock(TGBlocks.tgchest, 2, true, BlockType.CHEST));
		blockList.add(new MBlock(Blocks.chest, 3, true, BlockType.CHEST));
		blockList.add(new MBlock(Blocks.chest, 2, true, BlockType.CHEST));
		blockList.add(new MBlock(TGBlocks.tgchest, 3, true, BlockType.CHEST));
		blockList.add(new MBlock(Blocks.stone_brick_stairs, 5, true, BlockType.STAIRS));		
		
		blocks = BlockUtils.loadStructureFromFile("bigbunker");
	}
	
	
	
	public BigBunker(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		this.lootTier=1;
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		
		int centerX, centerZ;
		
	/*	if (((sizeX < this.minX) && (sizeZ > this.minX) && (sizeX >= this.minZ))
		   ||((sizeZ < this.minZ) && (sizeX > this.minZ) && (sizeZ >= this.minX)))
		{
			direction = (direction+1) % 4;
			centerZ = (int) (sizeX/2.0f);
			centerX = (int) (sizeZ/2.0f);
		}else {*/
			centerX = (int) (sizeX/2.0f);
			centerZ = (int) (sizeZ/2.0f);
		//}
		
		/*for (int i=0; i<20; i++){
			world.setBlock(posX+centerX, posY+i, posZ+centerZ, Blocks.redstone_block);
		}*/

		//System.out.println("setBlocks: Bunker Rotation center:"+centerX+","+centerZ);
		//System.out.println("setBlocks: Bunker Origin:"+posX+","+posZ);
		
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY-10, posZ, centerX, centerZ, direction, 0,this.lootTier);
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY-10, posZ, centerX, centerZ, direction, 1,this.lootTier);
	}

	@Override
	protected int getRotationShiftX(int direction) {
		int offsetX=0;
		switch(direction){
			case 1:
			case 3:
				offsetX=(int)(centX/2.0)-1;

		}
	
		return offsetX;
	}

	@Override
	protected int getRotationShiftZ(int direction) {
		int offsetZ=0;
		switch(direction){
			case 1:
			case 3:
				offsetZ=-(int)(centZ/2.0)-3;
				break;
		}
	
		return offsetZ;
	}

	
}
