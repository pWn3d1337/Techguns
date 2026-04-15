package techguns.worldgen.structures;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import team.chisel.init.ChiselBlocks;
import techguns.TGBlocks;
import techguns.tileentities.CampFlagTileEnt;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.util.BlockUtils.BiomeColorType;
import techguns.util.BlockUtils.BlockType;

public class AircraftCarrierNew extends Structure {

	static String[] airCraftCarrierSpawns = new String[]{"ArmySoldier","Commando"};
	
	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static short[][] blocks;
	static {
		blockList.add(new MBlock(ChiselBlocks.factoryblock, 6));
		blockList.add(new MBlock(ChiselBlocks.factoryblock, 3));
		blockList.add(new MBlock(ChiselBlocks.factoryblock, 2));
		blockList.add(new MBlock(ChiselBlocks.factoryblock, 15));
		blockList.add(new MBlock(ChiselBlocks.factoryblock, 4));
		blockList.add(new MBlock(Blocks.crafting_table,0));
		blockList.add(new MBlock(TGBlocks.tgchest, 5, true, BlockType.CHEST));
		//blockList.add(new MBlock(TGBlocks.basicMachine,5));
		blockList.add(new MBlock(TGBlocks.lamp01, 4, true, BlockType.TG).setPass(1));
		blockList.add(new MBlock(TGBlocks.lamp01, 6, true, BlockType.TG).setPass(1));
		blockList.add(new MBlock(Blocks.chest, 5, true, BlockType.CHEST));
		blockList.add(new MBlock(Blocks.air,0));
		//blockList.add(new MBlock(TGBlocks.basicMachine,3));
		blockList.add(new MBlock(TGBlocks.tgchest_weapon, 3, true, BlockType.CHEST));
		blockList.add(new MBlock(TGBlocks.tgchest_weapon, 2, true, BlockType.CHEST));
		blockList.add(new MBlock(ChiselBlocks.glass_pane, 4));
		blockList.add(new MBlock(ChiselBlocks.technical, 5));
		blockList.add(new MBlock(TGBlocks.lamp01, 3, true, BlockType.TG).setPass(1));
		blockList.add(new MBlock(TGBlocks.lamp01, 5, true, BlockType.TG).setPass(1));
		blockList.add(new MBlock(TGBlocks.door01, 2, true, BlockType.DOOR).setPass(0));
		blockList.add(new MBlock(TGBlocks.door01, 9, true, BlockType.DOOR).setPass(1));
		blockList.add(new MBlock(TGBlocks.door01, 8, true, BlockType.DOOR).setPass(1));
		blockList.add(new MBlock(ChiselBlocks.factoryblock, 12));
		blockList.add(new MBlock(ChiselBlocks.glass, 12));
		blockList.add(new MBlock(ChiselBlocks.iron_bars, 3));
		blockList.add(new MBlock(ChiselBlocks.technical2, 0));
		//blockList.add(new MBlock(TGBlocks.screen, 3, true, BlockType.TG));
		//blockList.add(new MBlock(TGBlocks.controlpanel, 3, true, BlockType.TG));
		blockList.add(new MBlock(TGBlocks.lamp01, 1, true, BlockType.TG).setPass(1));
		blockList.add(new MBlock(TGBlocks.door01, 0, true, BlockType.DOOR).setPass(0));
		blockList.add(new MBlock(TGBlocks.door01, 1, true, BlockType.DOOR).setPass(0));
		blockList.add(new MBlock(TGBlocks.door01, 3, true, BlockType.DOOR).setPass(0));
		blockList.add(new MBlock(Blocks.chest, 3, true, BlockType.CHEST));
		blockList.add(new MBlock(Blocks.chest, 2, true, BlockType.CHEST));
		blockList.add(new MBlock(ChiselBlocks.aluminumStairs[1], 1, true, BlockType.STAIRS));
		blockList.add(new MBlock(ChiselBlocks.aluminumStairs[1], 0, true, BlockType.STAIRS));
		blockList.add(new MBlock(TGBlocks.ladder01, 1, true, BlockType.TG).setPass(1));
		blockList.add(new MBlock(ChiselBlocks.iron_block, 14));
		blockList.add(new MBlock(ChiselBlocks.technical, 13));
		blockList.add(new MBlock(TGBlocks.tgchest, 3, true, BlockType.CHEST));
		blockList.add(new MBlock(ChiselBlocks.technical2, 1));
		blockList.add(new MBlock(TGBlocks.tgchest, 4, true, BlockType.CHEST));
		
		blocks = BlockUtils.loadStructureFromFile("airCraftCarrier");
	}
	
	
	public AircraftCarrierNew(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		this.lootTier=2;
	}
	
	@Override
	public void spawnStructureWorldgen(World world, int chunkX, int chunkZ, int sizeX, int sizeY, int sizeZ, Random rnd, BiomeGenBase biome) {
		int y =BlockUtils.getValidSpawnYWater(world, chunkX*16, chunkZ*16, sizeX, sizeZ,2);
		
		if (y<0){
			return;
		}
		
		int x = chunkX*16;
		int z = chunkZ*16;
		
		/*if(removeJunkOnWorldspawn){
			BlockUtils.removeJunkInArea(world, x-1, z-1, sizeX+2, sizeZ+2);
		}*/
		
		this.setBlocks(world, x, y-3, z, sizeX, sizeY, sizeZ, rnd.nextInt(4), getBiomeColorTypeFromBiome(biome), rnd);
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

		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0, this.lootTier);
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 1,  this.lootTier);
		
		//Place flag
		BlockUtils.setBlockRotated(world, new MBlock(TGBlocks.basicMachine, 7), posX, posY+8, posZ, 24, 9, centerX, centerZ, direction, this.lootTier);
		int[] pos = Structure.rotatePoint(24,9,direction,centerX,centerZ);
		
		
		
		//spawn points
	/*	BlockUtils.setBlockRotated(world, new MBlock(Blocks.lapis_block, 0), posX, posY+8, posZ, 22, 9, centerX, centerZ, direction, this.lootTier);
		
		BlockUtils.setBlockRotated(world, new MBlock(Blocks.lapis_block, 0), posX, posY+12, posZ, 28, 6, centerX, centerZ, direction, this.lootTier);
		BlockUtils.setBlockRotated(world, new MBlock(Blocks.lapis_block, 0), posX, posY+16, posZ, 28, 6, centerX, centerZ, direction, this.lootTier);
		
		BlockUtils.setBlockRotated(world, new MBlock(Blocks.lapis_block, 0), posX, posY+8, posZ, 43, 6, centerX, centerZ, direction, this.lootTier);
		BlockUtils.setBlockRotated(world, new MBlock(Blocks.lapis_block, 0), posX, posY+12, posZ, 43, 6, centerX, centerZ, direction, this.lootTier);
		
		BlockUtils.setBlockRotated(world, new MBlock(Blocks.lapis_block, 0), posX, posY+4, posZ, 18, 9, centerX, centerZ, direction, this.lootTier);
		
		BlockUtils.setBlockRotated(world, new MBlock(Blocks.lapis_block, 0), posX, posY+1, posZ, 18, 9, centerX, centerZ, direction, this.lootTier);
		
		BlockUtils.setBlockRotated(world, new MBlock(Blocks.lapis_block, 0), posX, posY+4, posZ, 43, 9, centerX, centerZ, direction, this.lootTier);
		
		BlockUtils.setBlockRotated(world, new MBlock(Blocks.lapis_block, 0), posX, posY+1, posZ, 39, 9, centerX, centerZ, direction, this.lootTier);
	*/	
		
		int[] spawnPos = new int[9*3];
		setSpawnPoint(spawnPos, 0, 22, 8, 9, centerX, centerZ, direction);
				
		setSpawnPoint(spawnPos, 1, 28, 12, 6, centerX, centerZ, direction);
		setSpawnPoint(spawnPos, 2, 28, 16, 6, centerX, centerZ, direction);
		
		setSpawnPoint(spawnPos, 3, 43, 8, 6, centerX, centerZ, direction);
		setSpawnPoint(spawnPos, 4, 43, 12, 6, centerX, centerZ, direction);
		
		setSpawnPoint(spawnPos, 5, 18, 4, 9, centerX, centerZ, direction);
		setSpawnPoint(spawnPos, 6, 18, 1, 9, centerX, centerZ, direction);
		
		setSpawnPoint(spawnPos, 7, 43, 4, 9, centerX, centerZ, direction);
		setSpawnPoint(spawnPos, 8, 39, 1, 9, centerX, centerZ, direction);
		
		TileEntity t = world.getTileEntity(posX+pos[0], posY+8, posZ+pos[1]);
		if (t!=null && t instanceof CampFlagTileEnt){
			CampFlagTileEnt flag = (CampFlagTileEnt) t;
			flag.init(54, 21, 24, 8, 9, spawnPos, airCraftCarrierSpawns, 9);
		}
		
		//world.setBlock(, TGBlocks.basicMachine, 7, 2);
	}

	private static void setSpawnPoint(int[] spawnPos, int index, int x, int y, int z, int cx, int cz, int direction){
		int ind = index*3;
				
		int pos[] = Structure.rotatePoint(x, z, direction, cx, cz);
		
		spawnPos[ind]=pos[0];
		spawnPos[ind+1]=y;
		spawnPos[ind+2]=pos[1];
	}
	
	
}
