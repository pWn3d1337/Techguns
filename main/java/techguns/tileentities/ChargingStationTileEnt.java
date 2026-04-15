package techguns.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import techguns.TGItems;
import techguns.Techguns;
import techguns.inventory.ChargingStationRecipe;
import cofh.api.energy.IEnergyContainerItem;

public class ChargingStationTileEnt extends BasicMachineTileEntity {
	
	protected int CHARGERATE=100;
	
	private int lastsound=0;
	
	public boolean charging =false;

	public ChargingStationTileEnt() {
		super(3,100000);
	}

	@Override
	public void readEntityDataFromNBT(NBTTagCompound tags) {
		super.readEntityDataFromNBT(tags);
		this.charging=tags.getBoolean("charging");
	}

	@Override
	public void writeEntityDataToNBT(NBTTagCompound tags) {
		super.writeEntityDataToNBT(tags);
		tags.setBoolean("charging", this.charging);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[]{0,1};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return slot == 0;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return slot==1;
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.name : "techguns.container.chargingStation";
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return slot == this.getValidSlotForItemInMachine(item);
	}

	public int getValidSlotForItemInMachine(ItemStack item) {
		if( item!=null && item.getItem()==TGItems.machineStackUpgrade.getItem() && item.getItemDamage()==TGItems.machineStackUpgrade.getItemDamage()){
			return 2;
		}
		if(item!=null && item.getItem() instanceof IEnergyContainerItem){
			return 0;
		}
		if (ChargingStationRecipe.getRecipeFor(item)!=null){
			return 0;
		}
		return -1;
	}

	
	
	@Override
	public boolean isWorking() {
		if (this.currentOperation!=null){
			return true;
		}
		if (this.content[0]!=null && this.content[0].getItem() instanceof IEnergyContainerItem){
			return true;
		}
		return false;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (this.isRedstoneEnabled()){
						
			if (this.currentOperation!= null) {
				
				if (this.consumePower(20*this.stackmultiplier)){
					this.progress++;
					
					playAmbientSound();
					
					if (!this.worldObj.isRemote && progress >= totalTime){
			
						if (this.content[1]==null){
							this.content[1]=new ItemStack(currentOperation.getItem(), currentOperation.stackSize*this.stackmultiplier,currentOperation.getItemDamage());
						} else {
							this.content[1].stackSize+=(currentOperation.stackSize*this.stackmultiplier);
						}

						this.progress=0;
						this.totalTime=0;
						this.currentOperation=null;
						this.stackmultiplier=1;
						
						checkAndStartOperation();
						this.needUpdate();
					}
				}
				
			} else if (this.content[0]!=null && this.content[0].getItem() instanceof IEnergyContainerItem){
				
				IEnergyContainerItem itm = (IEnergyContainerItem) this.content[0].getItem();
				
				if(itm.receiveEnergy(this.content[0], this.CHARGERATE, true) > 0){
							
					int maxcharge = Math.min(this.energy.getEnergyStored(),this.CHARGERATE);
									
					int pwr = itm.receiveEnergy(this.content[0], maxcharge, false);
					this.energy.extractEnergy(pwr, false);
					
					if (!this.worldObj.isRemote){
						if(!this.charging){
							this.charging=true;
							this.needUpdate();
						}
					} else {
						playAmbientSound();
					} 
					
				} else {
					if (!this.worldObj.isRemote){
						if(this.content[1]==null){
							this.content[1]=this.content[0];
							this.content[0]=null;
						}
						this.charging=false;
						this.needUpdate();
					}
				}

			} else {
				if(!this.worldObj.isRemote){
					if(this.charging){
						this.charging=false;
						this.needUpdate();
					}
					if (this.checkAndStartOperation()){
						this.needUpdate();
					}
				}
			}
		}
	
		
		
	}

	private boolean checkAndStartOperation() {
		if (this.content[0]!=null){
			ChargingStationRecipe rec = ChargingStationRecipe.getRecipeFor(this.content[0]);
			
			if (rec!=null){
				int stackMultiplier = this.getStackMulti();
				
				stackMultiplier = Math.min(stackMultiplier, this.content[0].stackSize/rec.input.stackSize);
				
				stackMultiplier = this.getMaxOutputAmount(rec.output,stackMultiplier);
				
				
				if( stackMultiplier>0){
					this.content[0].stackSize -= rec.input.stackSize * stackMultiplier;
					if(this.content[0].stackSize<=0){
						this.content[0]=null;
					}
					
					this.currentOperation = new ItemStack(rec.output.getItem(), rec.output.stackSize,rec.output.getItemDamage());
					this.stackmultiplier = stackMultiplier;
					this.progress=0;
					this.totalTime = rec.chargeAmount/(this.CHARGERATE*10);
					return true;
				}
			}
			
		}
		return false;
	}

	
	private int getMaxOutputAmount(ItemStack item, int maxMulti){
		if (this.content[1]==null){
			return Math.min(item.getMaxStackSize(),item.stackSize*maxMulti);
		} else {
			
			if (this.content[1].getItem()==item.getItem() && this.content[1].getItemDamage()==item.getItemDamage()){
				int multi=maxMulti;
				while(multi>0 && (content[1].stackSize + item.stackSize*multi)>content[1].getMaxStackSize()){
					multi--;
				}
				return multi;
			}
			return 0;
		}
		
		
		
		
	}
	
	private int getStackMulti(){
		if (this.content[2]!=null){
			if (this.content[2].getItem() == TGItems.sharedItem && this.content[2].getItemDamage()==TGItems.machineStackUpgrade.getItemDamage()){
				return Math.min(this.content[2].stackSize+1,8);
			}
		}
		return 1;
	}
	
	private void playAmbientSound() {
		if (this.worldObj.isRemote){
			
			
			
			EntityPlayer ply = Techguns.proxy.getPlayerClient();
			Double distSq = this.getDistanceFrom(ply.posX, ply.posY, ply.posZ);
			
			if (distSq < 100){
			
				if(this.rotation!=2){
					Techguns.proxy.createFX("ChargeFlare", this.worldObj, this.xCoord+0.5d, this.yCoord+0.5d, this.zCoord+0.3d, 0,0,0);
				}
				if(this.rotation!=0){
					Techguns.proxy.createFX("ChargeFlare", this.worldObj, this.xCoord+0.5d, this.yCoord+0.5d, this.zCoord+0.7d, 0,0,0);
				}
				if(this.rotation!=1){
					Techguns.proxy.createFX("ChargeFlare", this.worldObj, this.xCoord+0.3d, this.yCoord+0.5d, this.zCoord+0.5d, 0,0,0);
				}
				if(this.rotation!=3){
					Techguns.proxy.createFX("ChargeFlare", this.worldObj, this.xCoord+0.7d, this.yCoord+0.5d, this.zCoord+0.5d, 0,0,0);
				}
			}
			
			if (this.lastsound>0){
				this.lastsound--;
			}
			if (this.lastsound<=0){
				this.lastsound=20;
				worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "techguns:machines.chargingstationwork", 1.0F, 1.0F, true );
			}
			
		}
	}
}