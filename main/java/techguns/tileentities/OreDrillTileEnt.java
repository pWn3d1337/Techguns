package techguns.tileentities;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import techguns.TGBlocks;
import techguns.util.BlockCoords;

public class OreDrillTileEnt extends TileEntity implements ISidedInventory {

	protected int nextDrill=100;
	
	
	@Override
	public void updateEntity(){
		
		Block drillTarget = this.worldObj.getBlock(this.xCoord, this.yCoord-1, this.zCoord);
		if (Block.getIdFromBlock(drillTarget) == Block.getIdFromBlock(TGBlocks.oreCluster)){
			//System.out.println("Its a Cluster");
			nextDrill--;
			
			if (nextDrill <=0){
				
				int count = getClusterSize(0,this.xCoord,this.yCoord-1, this.zCoord,new ArrayList<BlockCoords>(), drillTarget);
				if(!this.worldObj.isRemote){
									
					ItemStack drop = new ItemStack(Blocks.iron_ore,1);
					this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord+1, this.zCoord, drop));
				}
				nextDrill=1000/count;
			}
		} else {
			nextDrill=100;
		}
		
		
		
	}
	
	public int getClusterSize(int count, int x, int y, int z, ArrayList<BlockCoords> visited, Block clusterType){

		Block target = this.worldObj.getBlock(x,y,z);
		BlockCoords block=new BlockCoords(x,y,z);
		if (!visited.contains(block)){
			if (Block.getIdFromBlock(target) == Block.getIdFromBlock(clusterType)) {
				count++;
				visited.add(block);
				
				count=getClusterSize(count,x+1,y,z, visited,clusterType);
				count=getClusterSize(count,x-1,y,z, visited,clusterType);
				count=getClusterSize(count,x,y+1,z, visited,clusterType);
				count=getClusterSize(count,x,y-1,z, visited,clusterType);
				count=getClusterSize(count,x,y,z+1, visited,clusterType);
				count=getClusterSize(count,x,y,z-1, visited,clusterType);
				
				return count;
			} else {
				//stop recursion, no cluster
				return count;
			}
		} else {
			return count;
		}
	}
	
	
	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int p_70301_1_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getInventoryName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_,
			int p_102007_3_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_,
			int p_102008_3_) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
