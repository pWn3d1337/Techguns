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

public class HouseMedium extends Structure {

	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static short[][] blocks;
	static {
		
		Block roofStairsBlock = Blocks.nether_brick_stairs;
		Block interiorStairs = Blocks.oak_stairs;
		Block darkStairs = Blocks.dark_oak_stairs;
		
		//ArrayList<MBlock> blockList = new ArrayList<MBlock>();
		blockList.add(new MBlock(TGBlocks.airMarker, 0));
		blockList.add(new MBlock(TGBlocks.lamp01,7,true, BlockType.TG).setPass(1));
		blockList.add(new MBlock(roofStairsBlock, 0, true, BlockType.STAIRS));
		blockList.add(new MBlock(ChiselBlocks.cobblestone, 1));
		blockList.add(new MBlock(ChiselBlocks.cobblestone, 10));
		blockList.add(new MBlock(Blocks.wooden_door, 4,true, BlockType.DOOR).setPass(0));
		blockList.add(new MBlock(Blocks.wooden_door, 8, true, BlockType.DOOR).setPass(1));
		blockList.add(new MBlock(Blocks.glass_pane, 0));
		blockList.add(new MBlock(ChiselBlocks.cobblestone, 13));
		blockList.add(new MBlock(roofStairsBlock, 5, true, BlockType.STAIRS));
		blockList.add(new MBlock(ChiselBlocks.cobblestone, 4));
		blockList.add(new MBlock(ChiselBlocks.carpet, 8));
		blockList.add(new MBlock(Blocks.wall_sign, 2, true, BlockType.LADDER).setPass(1));
		blockList.add(new MBlock(interiorStairs, 1, true, BlockType.STAIRS));
		blockList.add(new MBlock(Blocks.bookshelf, 0));
		blockList.add(new MBlock(darkStairs, 4, true, BlockType.STAIRS));
		blockList.add(new MBlock(roofStairsBlock, 2, true, BlockType.STAIRS));
		blockList.add(new MBlock(Blocks.nether_brick, 0));
		blockList.add(new MBlock(darkStairs, 3, true, BlockType.STAIRS));
		blockList.add(new MBlock(darkStairs, 1, true, BlockType.STAIRS));
		blockList.add(new MBlock(Blocks.bed, 9, true, BlockType.BED));
		blockList.add(new MBlock(ChiselBlocks.carpet, 4));
		blockList.add(new MBlock(Blocks.chest, 2,true,BlockType.CHEST));
		blockList.add(new MBlock(Blocks.fence, 0));
		blockList.add(new MBlock(Blocks.heavy_weighted_pressure_plate, 0).setPass(1));
		blockList.add(new MBlock(Blocks.bed, 1, true, BlockType.BED));
		blockList.add(new MBlock(Blocks.planks, 5));
		blockList.add(new MBlock(TGBlocks.lamp01, 1, true, BlockType.TG).setPass(1));
		blockList.add(new MBlock(TGBlocks.lamp01, 5 ,true, BlockType.TG).setPass(1));
		blockList.add(new MBlock(Blocks.crafting_table, 0));
		blockList.add(new MBlock(ChiselBlocks.leadblock, 1));
		blockList.add(new MBlock(TGBlocks.lamp01, 6,true, BlockType.TG).setPass(1));
		blockList.add(new MBlock(ChiselBlocks.copperblock, 1));
		blockList.add(new MBlock(Blocks.wooden_door, 6,true, BlockType.DOOR));
		blockList.add(new MBlock(ChiselBlocks.iron_block, 3));
		blockList.add(new MBlock(ChiselBlocks.tinblock, 1));
		blockList.add(new MBlock(ChiselBlocks.carpet, 10));
		blockList.add(new MBlock(TGBlocks.tgchest, 4, true, BlockType.CHEST));
		blockList.add(new MBlock(roofStairsBlock, 3, true, BlockType.STAIRS));
		blockList.add(new MBlock(ChiselBlocks.iron_block, 7));
		blockList.add(new MBlock(ChiselBlocks.gold_block, 5));
		blockList.add(new MBlock(roofStairsBlock, 4, true, BlockType.STAIRS));
		blockList.add(new MBlock(roofStairsBlock, 1, true, BlockType.STAIRS));
		//blockList.add(new MBlock(Blocks.wooden_door, 0,true, BlockType.DOOR).setPass(0));
		blockList.add(new MBlock(Blocks.wooden_door, 3,true, BlockType.DOOR).setPass(1));
		blockList.add(new MBlock(TGBlocks.lamp01, 3,true, BlockType.TG).setPass(1));
		blockList.add(new MBlock(ChiselBlocks.iron_bars, 5));
		blockList.add(new MBlock(TGBlocks.ladder01, 1, true, BlockType.TG).setPass(1));
		blockList.add(new MBlock(Blocks.wooden_door, 5,true, BlockType.DOOR).setPass(0));
		blockList.add(new MBlock(TGBlocks.lamp01, 4,true, BlockType.TG).setPass(1));
		blockList.add(new MBlock(TGBlocks.tgchest_weapon, 3, true, BlockType.CHEST));
		blockList.add(new MBlock(Blocks.wooden_door, 1,true, BlockType.DOOR).setPass(1));
		blockList.add(new MBlock(ChiselBlocks.iron_block, 1));
		blockList.add(new MBlock(Blocks.bed, 3,true, BlockType.BED));
		blockList.add(new MBlock(ChiselBlocks.iron_block, 10));
		blockList.add(new MBlock(ChiselBlocks.iron_block, 9));
		blockList.add(new MBlock(Blocks.bed, 11,true, BlockType.BED));
		
		blocks = BlockUtils.loadStructureFromFile("houseMedium");
		//System.out.println("Loaded: houseMedium " + blocks.length);
		
	}
	
	public HouseMedium(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
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
		BlockUtils.placeScannedStructure(world, HouseMedium.blocks, HouseMedium.blockList, posX, posY, posZ, centerX, centerZ, direction, 0,this.lootTier);
		BlockUtils.placeScannedStructure(world, HouseMedium.blocks, HouseMedium.blockList, posX, posY, posZ, centerX, centerZ, direction, 1,this.lootTier);
		BlockUtils.placeFoundation(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0,4);
	}

}
