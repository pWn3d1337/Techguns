package techguns.worldgen.structures;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import team.chisel.init.ChiselBlocks;
import techguns.TGBlocks;
import techguns.util.BlockUtils;
import techguns.util.BlockUtils.BiomeColorType;
import techguns.util.BlockUtils.BlockType;
import techguns.util.MBlock;
import techguns.util.MultiMBlock;

public class SmallTrainstation extends Structure {

	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static short[][] blocks;
	static {
		blockList.add(new MBlock(Blocks.gravel, 0));
		
		//blockList.add(new MBlock(Blocks.rail, 1));
		blockList.add(new MultiMBlock(new Block[]{Blocks.rail,Blocks.air},new int[]{0,0}, new int[]{2,1}));
		
		blockList.add(new MultiMBlock(new Block[]{ChiselBlocks.cobblestone,ChiselBlocks.mossy_cobblestone},new int[]{2,1}, new int[]{2,1}));
		blockList.add(new MultiMBlock(new Block[]{ChiselBlocks.limestoneStairs[0], ChiselBlocks.limestoneStairs[3]},new int[]{8,0}, new int[]{1,1}, new BlockType[]{BlockType.STAIRS2_CHISEL,BlockType.STAIRS}, new boolean[]{true,true}));
		
		//blockList.add(new MBlock(Block.getBlockFromName("tile.chisel.limestone_stairs.0"), 8));
		blockList.add(new MBlock(ChiselBlocks.cobblestone, 10));
		//blockList.add(new MBlock(Block.getBlockFromName("tile.chisel.brickCustom"), 3));
		blockList.add(new MultiMBlock(new Block[]{ChiselBlocks.brickCustom,ChiselBlocks.brickCustom},new int[]{3,5}, new int[]{2,2}));
		
		blockList.add(new MBlock(TGBlocks.airMarker, 0));
		//blockList.add(new MBlock(Block.getBlockFromName("tile.chisel.glass_pane"), 4));
		blockList.add(new MultiMBlock(new Block[]{ChiselBlocks.glass_pane,Blocks.air},new int[]{4,0}, new int[]{1,1}));
		
		//blockList.add(new MBlock(Block.getBlockFromName("tile.chisel.limestone_stairs.0"), 13));
		blockList.add(new MultiMBlock(new Block[]{ChiselBlocks.limestoneStairs[0], ChiselBlocks.limestoneStairs[3], Blocks.air},new int[]{13,5,0}, new int[]{2,2,1}, new BlockType[]{BlockType.STAIRS2_CHISEL,BlockType.STAIRS, BlockType.TG}, new boolean[]{true,true,false}));
		
		//blockList.add(new MBlock(Block.getBlockFromName("tile.chisel.limestone"), 1));
		blockList.add(new MultiMBlock(new Block[]{ChiselBlocks.limestone,ChiselBlocks.limestone,Blocks.air},new int[]{1,6,0}, new int[]{2,2,1}));
		
		
		blockList.add(new MBlock(Blocks.fence, 0));
		blockList.add(new MBlock(Blocks.wooden_pressure_plate, 0));
		blockList.add(new MBlock(Blocks.wooden_slab, 8));
		blockList.add(new MBlock(Blocks.ladder, 3, true, BlockType.LADDER));
		blockList.add(new MBlock(Blocks.chest, 2, true, BlockType.CHEST));
		//blockList.add(new MBlock(Block.getBlockFromName("tile.chisel.limestone_stairs.0"), 14));
		blockList.add(new MultiMBlock(new Block[]{ChiselBlocks.limestoneStairs[0], ChiselBlocks.limestoneStairs[3], Blocks.air},new int[]{14,6,0}, new int[]{2,2,1}, new BlockType[]{BlockType.STAIRS2_CHISEL,BlockType.STAIRS, BlockType.TG}, new boolean[]{true,true,false}));
		
		
		//blockList.add(new MBlock(Block.getBlockFromName("tile.chisel.limestone_stairs.0"), 15));
		blockList.add(new MultiMBlock(new Block[]{ChiselBlocks.limestoneStairs[0], ChiselBlocks.limestoneStairs[3], Blocks.air},new int[]{15,7,0}, new int[]{2,2,1}, new BlockType[]{BlockType.STAIRS2_CHISEL,BlockType.STAIRS, BlockType.TG}, new boolean[]{true,true,false}));
		
		blockList.add(new MBlock(Blocks.furnace, 4,true,BlockType.CHEST));
		blockList.add(new MBlock(Blocks.crafting_table, 0));
		//blockList.add(new MBlock(Block.getBlockFromName("tile.chisel.limestone_stairs.0"), 12));
		blockList.add(new MultiMBlock(new Block[]{ChiselBlocks.limestoneStairs[0], ChiselBlocks.limestoneStairs[3], Blocks.air},new int[]{12,4,0}, new int[]{2,2,1}, new BlockType[]{BlockType.STAIRS2_CHISEL,BlockType.STAIRS, BlockType.TG}, new boolean[]{true,true,false}));
		
		
		//blockList.add(new MBlock(Block.getBlockFromName("tile.chisel.limestone_stairs.0"), 9));
		blockList.add(new MultiMBlock(new Block[]{ChiselBlocks.limestoneStairs[0], ChiselBlocks.limestoneStairs[3], Blocks.air},new int[]{9,1,0}, new int[]{2,2,1}, new BlockType[]{BlockType.STAIRS2_CHISEL,BlockType.STAIRS, BlockType.TG}, new boolean[]{true,true,false}));
		
		blocks = BlockUtils.loadStructureFromFile("smallTrainstation");
	}
	
	public SmallTrainstation(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
	int centerX, centerZ;
		
		if (((sizeX < this.minX) && (sizeZ > this.minX) && (sizeX >= this.minZ))
		   ||((sizeZ < this.minZ) && (sizeX > this.minZ) && (sizeZ >= this.minX)))
		{
			direction = (direction+1) % 4;
			centerZ = (int) (sizeX/2.0f);
			centerX = (int) (sizeZ/2.0f);
		}else {
			centerX = (int) (sizeX/2.0f);
			centerZ = (int) (sizeZ/2.0f);
		}
		BlockUtils.cleanUpwards(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0, 7);
		BlockUtils.placeFoundation(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0,1);
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0,this.lootTier);
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 1,this.lootTier);
	}

}
