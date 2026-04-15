package techguns.util;

import static net.minecraftforge.common.ChestGenHooks.DUNGEON_CHEST;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import techguns.TGBlocks;
import techguns.TGItems;
import techguns.blocks.GenericBlock;
import techguns.tileentities.TGChestTileEnt;
import techguns.util.BlockUtils.BlockType;

public class MBlock {
	
	public Block block;
	public int meta;
	
	public boolean hasRotation;
	public BlockType rotationType;
	public int pass;
	
	protected static Random RND = new Random();
	
	public MBlock(Block block, int meta) {
		super();
		this.block = block;
		this.meta = meta;
	}
	
	
	
	public MBlock(Block block, int meta, boolean hasRotation,
			BlockType rotationType) {
		super();
		this.block = block;
		this.meta = meta;
		this.hasRotation = hasRotation;
		this.rotationType = rotationType;
	}

	public MBlock setPass(int pass) {
		this.pass = pass;
		return this;
	}


	@Override
	public boolean equals(Object other) {
		if (other instanceof MBlock) {
			MBlock o = (MBlock)other;
			return this.block == o.block && this.meta == o.meta;
		}
		return false;
	}
	
	public boolean isAirBlock(){
		return this.block == TGBlocks.airMarker;
	}
	
	
	public int getRotationMeta(int rotationOffset){
		
		return getRotationMetaBase(this.block, rotationOffset, this.meta, this.hasRotation, this.rotationType);
		
	}
	
	
	public static int getRotationMetaBase(Block block,int rotationOffset, int meta2, boolean hasRotation, BlockType rotType) {
		if (hasRotation) {
			int m;
			
			switch(rotType) {
			case TG:
				if (block instanceof GenericBlock) {
					return ((GenericBlock)block).getRotationMeta(meta2, rotationOffset);
				}else {
					return meta2;
				}
			case DOOR:
				//if (meta < 4) return (meta+rotationOffset) % 4;						
				//else if (meta < 8) return 4+(meta+rotationOffset) % 4;	
				//else return meta;
				m = meta2;
				for (int i = 0; i < rotationOffset; i++) {
					switch(m) {
						case 0:
							m=3;
							break;
						case 3:
							m=2;
							break;
						case 2:
							m=1;
							break;
						case 1:
							m=0;
							break;
						case 7:
							m=6;
							break;
						case 6:
							m=5;
							break;
						case 5:
							m=4;
							break;
						case 4:
							m=7;
							break;
					}
				}
				return m;
			case FENCE_GATE:
				if (meta2 < 4) return (meta2+rotationOffset) % 4;			
				else return 4+(meta2+rotationOffset) % 4;	
			case LADDER:
				m = meta2;
				for (int i = 0; i < rotationOffset; i++) {
					switch(m) { //INVERTED
						case 5:
							m=2;
							break;
						case 4:
							m=3;
							break;
						case 3:
							m=5;
							break;
						case 2:
							m=4;
							break;
					}
				}
				return m;
			case STAIRS:
				m = meta2;
				for (int i = 0; i < rotationOffset; i++) {
					switch(m) { //INVERTED
						case 2:
							m=0;
							break;
						case 1:
							m=2;
							break;
						case 3:
							m=1;
							break;
						case 0:
							m=3;
							break;
						case 4:
							m=7;
							break;
						case 6:
							m=4;
							break;
						case 5:
							m=6;
							break;
						case 7:
							m=5;
							break;
					}
				}
				return m;
			case BED:
				m = meta2;
				for (int i = 0; i < rotationOffset; i++) {
					switch(m) {
						case 8:
							m=11;
							break;
						case 0:
							m=3;
							break;
						case 11:
							m=10;
							break;
						case 3:
							m=2;
							break;
						case 10:
							m=9;
							break;
						case 2:
							m=1;
							break;
						case 9:
							m=8;
							break;
						case 1:
							m=0;
							break;
					}
				}
				return m;
			case STAIRS2_CHISEL:
				m = meta2;
				for (int i = 0; i < rotationOffset; i++) {
					switch(m) { //INVERTED
						case 8:
							m=11;
							break;
						case 11:
							m=9;
							break;
						case 9:
							m=10;
							break;
						case 10:
							m=8;
							break;
						case 12:
							m=15;
							break;
						case 15:
							m=13;
							break;
						case 13:
							m=14;
							break;
						case 14:
							m=12;
							break;
					}
				}
				return m;
			case LEVER:
				m = meta2;
				for (int i = 0; i < rotationOffset; i++) {
					switch(m) {
						case 1:
							m=4;
							break;
						case 4:
							m=2;
							break;
						case 2:
							m=3;
							break;
						case 3:
							m=1;
							break;
							
						case 9:
							m=12;
							break;
						case 12:
							m=10;
							break;
						case 10:
							m=11;
							break;
						case 11:
							m=9;
							break;
					}
				}
				return m;
			case CHEST:
				m = meta2;
				for (int i = 0; i < rotationOffset; i++) {
					//System.out.println("M:"+m);
					switch(m) {
						case 3:
							m=5;
							break;
						case 5:
							m=2;
							break;
						case 2:
							m=4;
							break;
						case 4:
							m=3;
							break;
					}
				}
				//System.out.println("RETURN:"+m);
				return m;
			default:
				return meta2;

			}
		}else {
			return meta2;
		}
	}
	
	
	public void setBlock(World world, int posX, int posY, int posZ, int rotation){
		setBlock(world,posX,posY,posZ,rotation,0);
	}
	
	public void setBlock(World world, int posX, int posY, int posZ, int rotation, int lootTier) {
		if (this.isAirBlock()){
			world.setBlockToAir(posX, posY, posZ);
		} else {
			int meta = getRotationMeta(rotation);
			
			world.setBlock(posX, posY, posZ, block, meta, 2);
			
			//doesn't work without that
			if (this.rotationType == rotationType.CHEST){
				world.setBlockMetadataWithNotify(posX, posY, posZ, meta, 2);
			}
			
			if ( this.block == Blocks.chest){
				
                TileEntityChest tileentitychest = (TileEntityChest)world.getTileEntity(posX,posY,posZ);

                if (tileentitychest != null)
                {
                    WeightedRandomChestContent.generateChestContents(RND, ChestGenHooks.getItems(DUNGEON_CHEST, RND), tileentitychest, ChestGenHooks.getCount(DUNGEON_CHEST, RND));
                }
				
			} else if (this.block == TGBlocks.tgchest_weapon){
				TGChestTileEnt tile = (TGChestTileEnt) world.getTileEntity(posX, posY, posZ);
				if (tile !=null){

					String loot = TGItems.WEAPONCHEST_LOOT_TIER[lootTier];
					WeightedRandomChestContent[] contents = ChestGenHooks.getItems(loot, RND);
					int count = ChestGenHooks.getCount(loot, RND);
					
					WeightedRandomChestContent.generateChestContents(RND, contents, tile, count);
				}

			} else if (this.block == TGBlocks.tgchest){
				TGChestTileEnt tile = (TGChestTileEnt) world.getTileEntity(posX, posY, posZ);
				if (tile !=null){

					String loot = TGItems.REINFORCEDCHEST_LOOT_TIER[lootTier];
					WeightedRandomChestContent[] contents = ChestGenHooks.getItems(loot, RND);
					int count = ChestGenHooks.getCount(loot, RND);
					WeightedRandomChestContent.generateChestContents(RND, contents, tile, count);
				}

			}			
		}
	}
}