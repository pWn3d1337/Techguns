package techguns.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BasicMachineTileEntity extends BasicPoweredTileEnt {

	
	//protected ItemStack[] content;
	
	public ItemStack currentOperation = null;
	public int totalTime=0;
	public int progress = 0;
	/**
	 * Saved as byte
	 */
	public int stackmultiplier=1;
		
	public BasicMachineTileEntity(int slotCount, int MaximumPower){
		super(slotCount,MaximumPower);
		//energy = new EnergyStorage(MaximumPower);
		//content = new ItemStack[slotCount];
	}
	
	@Override
	public void readEntityDataFromNBT(NBTTagCompound tags) {
		super.readEntityDataFromNBT(tags);
        
        this.progress = tags.getShort("Progress");
	 	this.totalTime=tags.getShort("TotalTime");
	 	this.stackmultiplier=tags.getByte("StackMultiplier");
 	  NBTTagCompound currentOp = tags.getCompoundTag("CurrentOperation");
        if (currentOp.hasNoTags()){
        	this.currentOperation=null;
        } else {
        	this.currentOperation = ItemStack.loadItemStackFromNBT(currentOp);
        }
	}



	@Override
	public void writeEntityDataToNBT(NBTTagCompound tags) {
		super.writeEntityDataToNBT(tags);

        
        tags.setShort("Progress", (short)this.progress);
	 	tags.setShort("TotalTime", (short)this.totalTime);
	 	tags.setByte("StackMultiplier", (byte)this.stackmultiplier);
	 	
        if (this.currentOperation != null){
	        NBTTagCompound currentOp = new NBTTagCompound();
	        this.currentOperation.writeToNBT(currentOp);
	        tags.setTag("CurrentOperation", currentOp);
        }
        
	}
	
	 /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    @SideOnly(Side.CLIENT)
    public int getProgressScaled(int limit)
    {
    	if (totalTime!=0){
    		return this.progress * limit / this.totalTime;
    	} else {
    		return 0;
    	}
    }
    
    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    @SideOnly(Side.CLIENT)
    public int getPowerScaled(int limit)
    {
    	if (totalTime!=0){
    		return this.energy.getMaxEnergyStored() * limit / this.energy.getMaxEnergyStored();
    	} else {
    		return 0;
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public float getProgress() {
    	if(this.totalTime>0){
    		return (float)this.progress / (float)this.totalTime;
    	}
    	return 0.0f;
    }
    
    public boolean isWorking(){
    	return !(this.currentOperation==null);
    }
    
    public ItemStack onFluidContainerInteract(ItemStack fluidContainer){
    	return fluidContainer;
    }
}
