package techguns.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyReceiver;

public class FabricatorTileEntSlave extends MultiBlockSlaveTileEntBase implements IEnergyReceiver, ISidedInventory{
	protected FabricatorTileEntMaster master=null;
	
	
	/*private static float[][] blockbounds = {
		{0f,0.1f,0.9f,1f},
		{0f,0f,0.9f,0.9f},
		{0.1f,0f,1f,0.9f},
		{0.1f,0.1f,1f,1f}
	};*/
	
	
	/**
	 * Type of Input
	 * 0 - Top row, no input
	 * 1 - item/power
	 */
	protected byte type=0;

	public FabricatorTileEntSlave() {
		super();
	} 
	
	public void setType(byte type){
		this.type=type;
	}
	
	public byte getType(){
		return this.type;
	}
	
	public FabricatorTileEntMaster getMaster(){
		if(master==null){
			TileEntity tile = this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
			if (tile instanceof FabricatorTileEntMaster){
				master = (FabricatorTileEntMaster) tile;
			}
		}
		return master;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tags) {
		super.readFromNBT(tags);
		if(this.hasMaster){
			this.type=tags.getByte("type");
		}
	}



	@Override
	public void writeToNBT(NBTTagCompound tags) {
		super.writeToNBT(tags);
		if (this.hasMaster){
			tags.setByte("type", this.type);
		}
	}

	@Override
	public void unlink() {
		super.unlink();
		this.type=0;
		this.master=null;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection dir) {
		if (this.type!=1){
			return false;
		} else {
			return true;
		}
	}

	@Override
	public int getEnergyStored(ForgeDirection dir) {
		if (this.hasMaster && type==1){
			return this.getMaster().getEnergyStored(dir);
		}
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection dir) {
		if (this.hasMaster && type==1){
			return this.getMaster().getMaxEnergyStored(dir);
		}
		return 0;
	}

	@Override
	public int receiveEnergy(ForgeDirection dir, int maxReceive, boolean simulate) {
		if (this.hasMaster && type==1){
			return this.getMaster().receiveEnergy(dir, maxReceive, simulate);
		}
		return 0;
	}

	@Override
	public int getSizeInventory() {
		if (this.hasMaster && type==1){
			return this.getMaster().getSizeInventory();
		}
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (this.hasMaster && type==1){
			return this.getMaster().getStackInSlot(slot);
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (this.hasMaster && type==1){
			return this.getMaster().decrStackSize(slot, amount);
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.hasMaster && type==1){
			return this.getMaster().getStackInSlotOnClosing(slot);
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if (this.hasMaster && type==1){
			this.getMaster().setInventorySlotContents(slot, stack);
		}
	}

	@Override
	public String getInventoryName() {
		if (this.hasMaster && type==1){
			return this.getMaster().getInventoryName();
		}
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		if (this.hasMaster && type==1){
			return this.getMaster().getInventoryStackLimit();
		}
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (this.hasMaster && type==1){
			return this.getMaster().isUseableByPlayer(player);
		}
		return false;
	}

	@Override
	public void openInventory() {
		if (this.hasMaster && type==1){
			this.getMaster().openInventory();
		}
	}

	@Override
	public void closeInventory() {
		if (this.hasMaster && type==1){
			this.getMaster().closeInventory();
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (this.hasMaster && type==1){
			return this.getMaster().isItemValidForSlot(slot, stack);
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if (this.hasMaster && type==1){
			return new int[]{FabricatorTileEntMaster.SLOT_INPUT,FabricatorTileEntMaster.SLOT_WIRES,FabricatorTileEntMaster.SLOT_POWDER,FabricatorTileEntMaster.SLOT_PLATE,FabricatorTileEntMaster.SLOT_OUTPUT};
		}
		return new int[]{};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		if (this.hasMaster && type==1){
			return slot==FabricatorTileEntMaster.SLOT_INPUT||slot==FabricatorTileEntMaster.SLOT_WIRES||slot==FabricatorTileEntMaster.SLOT_POWDER||slot==FabricatorTileEntMaster.SLOT_PLATE;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		if (this.hasMaster && type==1){
			return slot==FabricatorTileEntMaster.SLOT_OUTPUT;
		}
		return false;
	}
	
}
