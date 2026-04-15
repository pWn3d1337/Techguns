package techguns.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cofh.api.energy.IEnergyReceiver;

public class ReactionChamberTileEntSlave extends MultiBlockSlaveTileEntBase implements IEnergyReceiver, ISidedInventory, IFluidHandler {
	
	protected ReactionChamberTileEntMaster master=null;
	
	/**
	 * Type of Input
	 * 0 - no
	 * 1 - item/liquid
	 * 2 - power
	 */
	protected byte type=0;

	public ReactionChamberTileEntSlave() {
		super();
	} 
	
	public void setType(byte type){
		this.type=type;
	}
	
	public byte getType(){
		return this.type;
	}
	
	public ReactionChamberTileEntMaster getMaster(){
		if(master==null){
			TileEntity tile = this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
			if (tile instanceof ReactionChamberTileEntMaster){
				master = (ReactionChamberTileEntMaster) tile;
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
		if (this.type!=2){
			return false;
		} else {
			return true;
		}
	}

	@Override
	public int getEnergyStored(ForgeDirection dir) {
		if (this.hasMaster && type==2){
			return this.getMaster().getEnergyStored(dir);
		}
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection dir) {
		if (this.hasMaster && type==2){
			return this.getMaster().getMaxEnergyStored(dir);
		}
		return 0;
	}

	@Override
	public int receiveEnergy(ForgeDirection dir, int maxReceive, boolean simulate) {
		if (this.hasMaster && type==2){
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
			return new int[]{0,2,3,4,5};
		}
		return new int[]{};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		if (this.hasMaster && type==1){
			return slot==0;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		if (this.hasMaster && type==1){
			return slot>=2 && slot<=5;
		}
		return false;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (this.hasMaster && type==1){
			return this.getMaster().fillFromSlave(from, resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (this.hasMaster && type==1){
			return this.getMaster().drainFromSlave(from, resource, doDrain);
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (this.hasMaster && type==1){
			return this.getMaster().drainFromSlave(from, maxDrain, doDrain);
		}
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if (this.hasMaster && type==1){
			return this.getMaster().canFillFromSlave(from, fluid);
		}
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if (this.hasMaster && type==1){
			return this.getMaster().canDrainFromSlave(from, fluid);
		}
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if (this.hasMaster && type==1){
			return this.getMaster().getTankInfo(from);
		}
		return null;
	}
	
}
