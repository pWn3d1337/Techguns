package techguns.worldgen.structures;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import team.chisel.init.ChiselBlocks;
import techguns.TGBlocks;
import techguns.util.BlockUtils;
import techguns.util.BlockUtils.BiomeColorType;
import techguns.util.BlockUtils.BlockType;
import techguns.util.MBlock;

public class Submarine extends Structure {

	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static short[][] blocks;
	static {
		blockList.add(new MBlock(TGBlocks.metalpanel, 3));
		blockList.add(new MBlock(ChiselBlocks.technical, 5));
		blockList.add(new MBlock(Blocks.air, 0));
		blockList.add(new MBlock(TGBlocks.ladder01, 0, true, BlockType.TG).setPass(1));
		blockList.add(new MBlock(ChiselBlocks.factoryblock, 9));
		blockList.add(new MBlock(TGBlocks.tgchest_weapon, 5, true, BlockType.CHEST));
		blockList.add(new MBlock(ChiselBlocks.planks[0], 10));
		blockList.add(new MBlock(ChiselBlocks.planks[1], 10));
		blockList.add(new MBlock(ChiselBlocks.factoryblock, 4));
		blockList.add(new MBlock(TGBlocks.metalpanel2, 3));
		blockList.add(new MBlock(ChiselBlocks.aluminumStairs[1], 3, true, BlockType.STAIRS));
		blockList.add(new MBlock(ChiselBlocks.factoryblock, 8));
		blockList.add(new MBlock(TGBlocks.door01, 3, true, BlockType.DOOR).setPass(0));
		blockList.add(new MBlock(TGBlocks.door01, 8, true, BlockType.DOOR).setPass(1));
		blockList.add(new MBlock(ChiselBlocks.technical, 14));
		blockList.add(new MBlock(ChiselBlocks.warningSign, 0));
		blockList.add(new MBlock(TGBlocks.lamp01, 3, true, BlockType.TG).setPass(1));
		blockList.add(new MBlock(TGBlocks.lamp01, 4, true, BlockType.TG).setPass(1));
		blockList.add(new MBlock(Blocks.bed, 3, true, BlockType.BED));
		blockList.add(new MBlock(Blocks.trapdoor, 9,true, BlockType.LEVER));
		blockList.add(new MBlock(TGBlocks.tgchest, 4, true, BlockType.CHEST));
		blockList.add(new MBlock(ChiselBlocks.aluminumStairs[1], 2, true, BlockType.STAIRS));
		blockList.add(new MBlock(Blocks.bed, 11, true, BlockType.BED));
		blockList.add(new MBlock(Blocks.chest, 4, true, BlockType.CHEST));
		blockList.add(new MBlock(TGBlocks.tgchest_weapon, 4, true, BlockType.CHEST));
		
		blocks = BlockUtils.loadStructureFromFile("submarine");
	}
	
	
	
	public Submarine() {
		super();
		this.lootTier=2;
		//System.out.println("Size:"+blocks.length);
	}


	

	@Override
	public void spawnStructureWorldgen(World world, int chunkX, int chunkZ, int sizeX, int sizeY, int sizeZ, Random rnd, BiomeGenBase biome) {
		
		int y =BlockUtils.getValidSpawnYUnderwater(world, chunkX*16, chunkZ*16, sizeX, sizeZ,2);
		
		if (y<0){
			return;
		}
		
		int x = chunkX*16;
		int z = chunkZ*16;
		
		/*if(removeJunkOnWorldspawn){
			BlockUtils.removeJunkInArea(world, x-1, z-1, sizeX+2, sizeZ+2);
		}*/
		
		this.setBlocks(world, x, y, z, sizeX, sizeY, sizeZ, rnd.nextInt(4), getBiomeColorTypeFromBiome(biome), rnd);
	}




	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		int centerX, centerZ;

			centerX = (int) (sizeX/2.0f);
			centerZ = (int) (sizeZ/2.0f);

			BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0,this.lootTier);
			BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 1,this.lootTier);

	}

}
