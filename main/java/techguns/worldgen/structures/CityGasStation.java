package techguns.worldgen.structures;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import team.chisel.init.ChiselBlocks;
import techguns.TGBlocks;
import techguns.util.BlockUtils;
import techguns.util.BlockUtils.BlockType;
import techguns.util.MBlock;
import techguns.util.BlockUtils.BiomeColorType;

public class CityGasStation extends Structure {
	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static short[][] blocks;
	static {
		blockList.add(new MBlock(ChiselBlocks.concrete, 10));
		blockList.add(new MBlock(ChiselBlocks.stonebricksmooth, 13));
		blockList.add(new MBlock(Blocks.air,0));
		blockList.add(new MBlock(ChiselBlocks.stonebricksmooth, 5));
		blockList.add(new MBlock(ChiselBlocks.glass_pane, 4));
		blockList.add(new MBlock(ChiselBlocks.factoryblock, 15));
		blockList.add(new MBlock(Blocks.chest, 3, true, BlockType.CHEST));
		blockList.add(new MBlock(ChiselBlocks.factoryblock, 6));
		blockList.add(new MBlock(TGBlocks.lamp01, 4,true,BlockType.TG).setPass(1));
		blockList.add(new MBlock(ChiselBlocks.bookshelf, 7));
		blockList.add(new MBlock(Blocks.bookshelf, 0));
		blockList.add(new MBlock(ChiselBlocks.bookshelf, 6));
		blockList.add(new MBlock(Blocks.lever, 10,true, BlockType.LEVER));
		blockList.add(new MBlock(TGBlocks.lamp01, 1,true,BlockType.TG).setPass(1));
		blockList.add(new MBlock(ChiselBlocks.bookshelf, 8));
		blockList.add(new MBlock(ChiselBlocks.bookshelf, 1));
		blockList.add(new MBlock(ChiselBlocks.factoryblock, 9));
		blockList.add(new MBlock(Blocks.fence_gate, 3,true,BlockType.FENCE_GATE));
		blockList.add(new MBlock(Blocks.fence, 0));
		blockList.add(new MBlock(Blocks.wooden_door, 3));
		blockList.add(new MBlock(Blocks.wooden_pressure_plate, 0));
		blockList.add(new MBlock(Blocks.wooden_door, 8).setPass(1));
		blockList.add(new MBlock(Blocks.wooden_door, 1));
		blockList.add(new MBlock(Blocks.chest, 4,true,BlockType.CHEST));

		blocks = BlockUtils.loadStructureFromFile("cityGasStation");
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

		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0,this.lootTier);
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 1,this.lootTier);
	}

}
