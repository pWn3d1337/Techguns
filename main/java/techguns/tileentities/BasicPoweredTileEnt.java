package techguns.tileentities;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import techguns.TGConfig;
import techguns.entities.npc.TGNpcFactions;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BasicPoweredTileEnt extends BasicOwnedTileEnt implements IEnergyReceiver {
	
	public static final int MAX_BUTTON_USED=2;
	
	protected EnergyStorage energy;
	/**
	 * 0-ignore
	 * 1-high
	 * 2-low
	 */
	protected byte redstoneBehaviour=0;
		
	/**
	 * has currently a redstone signal?
	 */
	protected boolean hasSignal=false;
	
	public BasicPoweredTileEnt(int slots,int MaximumPower){
		super(slots);
		energy = new EnergyStorage(MaximumPower);
	}
	
	public void onNeighborBlockChange(){
		if (!this.worldObj.isRemote){
			boolean signal = this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
			if(signal!=hasSignal){
				hasSignal=signal;
				this.needUpdate();
			}
		}
	}
	
	/**
	 * Returns if this tileent should operate depending on current redstone signal and setting
	 */
	public boolean isRedstoneEnabled(){
		return this.redstoneBehaviour==0 || (this.hasSignal && this.redstoneBehaviour==1) || (!this.hasSignal && this.redstoneBehaviour==2);
	}
	
	@Override
	public void readEntityDataFromNBT(NBTTagCompound tags){
		super.readEntityDataFromNBT(tags);
        NBTTagCompound tagEnergy = tags.getCompoundTag("Energy");
        this.energy.readFromNBT(tagEnergy);
        this.hasSignal = tags.getBoolean("hasSignal");
        this.redstoneBehaviour=tags.getByte("redstoneBehaviour");
        
	}

	@Override
	public void writeEntityDataToNBT(NBTTagCompound tags){
		super.writeEntityDataToNBT(tags);

        NBTTagCompound tagEnergy = new NBTTagCompound();
        this.energy.writeToNBT(tagEnergy);
        tags.setTag("Energy", tagEnergy);
        tags.setBoolean("hasSignal",this.hasSignal);
        tags.setByte("redstoneBehaviour",this.redstoneBehaviour);
	}
	
    public int getPowerStored(){
    	return this.energy.getEnergyStored();
    }
    
    public void setPowerStored(int pwr){
    	this.energy.setEnergyStored(pwr);
    }
    
    @SideOnly(Side.CLIENT)
    public String getRedstoneModeText(){
    	switch(this.redstoneBehaviour){
	    	case 0:
	    		return "techguns.redstonemode.ignore";
	    	case 1:
	    		return "techguns.redstonemode.high";
	    	case 2:
	    		return "techguns.redstonemode.low";
    	}
    	return "invalid";
    }
    
    @SideOnly(Side.CLIENT)
    public String getSignalStateText(){
    	if(this.hasSignal){
    		return "techguns.redstonemode.signal.high";
    	} else {
    		return "techguns.redstonemode.signal.low";
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public int getPowerMax(){
    	return this.energy.getMaxEnergyStored();
    }

    @Override
	public int getEnergyStored(ForgeDirection from) {
		return energy.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return energy.getMaxEnergyStored();
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return energy.receiveEnergy(maxReceive, simulate);
	}
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}
	
	/**
	 * Drain amount power from internal storage, return if enough power left
	 * @param amount
	 * @return
	 */
	protected boolean consumePower(int amount){
		if ( TGConfig.machinesNeedNoPower){
			return true;
		}
		
		if (this.energy.getEnergyStored()>=amount){
			this.energy.modifyEnergyStored(-amount);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(super.isUseableByPlayer(player)){
			if (this.owner==null || this.security ==0){
				return true;
			} else if (this.isOwnedByPlayer(player)) {
				return true;
			} else if (this.security==1){
				return !TGNpcFactions.isHostile(this.owner, player.getGameProfile().getId());
			}
						
		}
		return false;
	}

	@Override
	public void buttonClicked(int id, EntityPlayer ply) {
		if ((id==2) && (this.isUseableByPlayer(ply))){
			this.redstoneBehaviour++;
			this.redstoneBehaviour=(byte) (this.redstoneBehaviour%3);
			if(!this.worldObj.isRemote){
				this.needUpdate();
			}
		} else if (id==1 &&(this.isOwnedByPlayer(ply))){
			this.security++;
			this.security = (byte) (this.security%3);
			if(!this.worldObj.isRemote){
				this.needUpdate();
			}
		}
	}

	public byte getRedstoneBehaviour() {
		return redstoneBehaviour;
	}
		

}
