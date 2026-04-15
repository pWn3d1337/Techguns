package techguns.tileentities;

import net.minecraft.nbt.NBTTagCompound;



public abstract class MultiBlockMasterTileEntBase extends BasicMachineTileEntity {
	protected boolean complete;
	
	public MultiBlockMasterTileEntBase(int slotCount, int MaximumPower) {
		super(slotCount, MaximumPower);
		this.complete=false;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public void notifiyMultiblockBreak(){
		this.setComplete(false);
		if(!worldObj.isRemote){
			this.needUpdate();
		}
	}
	
	public void needBlockUpdate(){
		if(!this.worldObj.isRemote){
			this.worldObj.notifyBlockChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
		}
	}
	
	@Override
	public void readEntityDataFromNBT(NBTTagCompound tags) {
		super.readEntityDataFromNBT(tags);
		
		boolean comp = this.complete=tags.getBoolean("complete");
		if (comp != this.complete){
			this.causeRenderUpdate();
		}
		this.complete=comp;
	}

	@Override
	public void writeEntityDataToNBT(NBTTagCompound tags) {
		super.writeEntityDataToNBT(tags);
		tags.setBoolean("complete", complete);
	}
	
	protected void causeRenderUpdate(){
		if (this.worldObj!=null && this.worldObj.isRemote){
			this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
			//System.out.println("RenderUpdate:"+this.xCoord+" "+this.yCoord+" "+this.zCoord);
		}
	}
}
