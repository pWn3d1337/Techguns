package techguns.util;

import static net.minecraftforge.common.ChestGenHooks.DUNGEON_CHEST;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import techguns.blocks.GenericBlock;
import techguns.util.BlockUtils.BlockType;

public class MultiMBlock extends MBlock {

	Block[] blocks;
	int[] metas;
	int[] weights;
	BlockType[] rotationTypes;
	boolean hasRotations[];
	
	int totalWeight;
	
	public MultiMBlock(Block block, int meta) {
		super(block, meta);
	}
	
	public MultiMBlock(Block[] blocks, int[] metas, int[] weights, BlockType[] rotationTypes, boolean[] hasRotations){
		super(blocks[0], metas[0], hasRotations[0], rotationTypes[0]);
		
		this.blocks = blocks;
		this.metas = metas;
		this.weights = weights;
		this.rotationTypes = rotationTypes;
		this.hasRotations=hasRotations;
		this.calcTotalweight();
	}
	
	public MultiMBlock(Block[] blocks, int[] metas, int[] weights){
		super(blocks[0], metas[0]);
		
		this.blocks = blocks;
		this.metas = metas;
		this.weights = weights;
		this.hasRotations = new boolean[blocks.length];
		this.calcTotalweight();
	}

	private void calcTotalweight(){
		this.totalWeight=0;
		for (int i=0;i<this.weights.length;i++){
			this.totalWeight += weights[i];
		}
	}
	
	/**
	 * Roll index of the next block to use;
	 * @return
	 */
	protected int rollBlockIndex(){
		int roll = RND.nextInt(this.totalWeight+1);
		//System.out.println("Roll:"+roll+" Totalweight"+this.totalWeight);
		int sum =0;
		for (int i =0; i<this.blocks.length; i++){
			sum+=this.weights[i];
			if (roll <= sum){
				//System.out.println("Index:"+i);
				return i;
			}		
		}		
		//System.out.println("Index:"+"MAX");
		return weights.length;
	}
	
	/*public void setRandomMultiBlock(World world, int posX, int posY, int posZ){
		int index = this.rollBlockIndex();
		
		world.setBlock(posX, posY, posZ, this.blocks[index], this.getRotationMeta(this.metas[index]), 2);
	}*/
	
	public int getRotationMetaMulti(int rotationOffset,int index) {
		
		boolean hasRotationi = this.hasRotations[index];
		int metai = this.metas[index];

		if (hasRotationi){
			return getRotationMetaBase(this.blocks[index],rotationOffset, metai,true,this.rotationTypes[index]);
		} else {
			return metai;
		}
		
		/*if (hasRotationi) {
			int m;
			
			switch(this.rotationTypes[index]) {
			case TG:
				if (this.block instanceof GenericBlock) {
					return ((GenericBlock)block).getRotationMeta(metai, rotationOffset);
				}else {
					return metai;
				}
			case DOOR:
				//if (meta < 4) return (meta+rotationOffset) % 4;						
				//else if (meta < 8) return 4+(meta+rotationOffset) % 4;	
				//else return meta;
				m = metai;
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
				if (metai < 4) return (metai+rotationOffset) % 4;			
				else return 4+(metai+rotationOffset) % 4;	
			case LADDER:
				m = metai;
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
				m = metai;
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
				m = metai;
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
				m = metai;
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
			default:
				return metai;

			}
		}else {
			return metai;
		}*/
	}
	
	
	
	@Override
	public void setBlock(World world, int posX, int posY, int posZ, int rotation) {
		this.setBlock(world, posX, posY, posZ, rotation,0);
	}

	@Override
	public void setBlock(World world, int posX, int posY, int posZ, int rotation, int loottier) {
		if (this.isAirBlock()){
			world.setBlockToAir(posX, posY, posZ);
		} else {
			
			int index = this.rollBlockIndex();
					
			world.setBlock(posX, posY, posZ, this.blocks[index], this.getRotationMetaMulti(rotation,index), 2);
			
			if ( this.block == Blocks.chest){
				
                TileEntityChest tileentitychest = (TileEntityChest)world.getTileEntity(posX,posY,posZ);

                if (tileentitychest != null)
                {
                    WeightedRandomChestContent.generateChestContents(RND, ChestGenHooks.getItems(DUNGEON_CHEST, RND), tileentitychest, ChestGenHooks.getCount(DUNGEON_CHEST, RND));
                }
				
			}
		}
	}
	
	
	

}
