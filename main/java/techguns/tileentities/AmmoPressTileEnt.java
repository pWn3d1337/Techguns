package techguns.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import techguns.TGItems;
import techguns.inventory.AmmoPressBuildPlans;

public class AmmoPressTileEnt extends BasicMachineTileEntity {
	
	public AmmoPressTileEnt() {
		super(5,20000);
	}

	public byte buildPlan = 0;
	private final byte maxPlan=3;
	
	@Override
	public void readEntityDataFromNBT(NBTTagCompound tags) {
		super.readEntityDataFromNBT(tags);
		this.buildPlan=tags.getByte("BuildPlan");
	}

	@Override
	public void writeEntityDataToNBT(NBTTagCompound tags) {
		super.writeEntityDataToNBT(tags);
		tags.setByte("BuildPlan", this.buildPlan);		
	}


	/* (non-Javadoc)
	 * @see net.minecraft.tileentity.TileEntity#getDescriptionPacket()
	 */
	/*@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tags = new NBTTagCompound();
		this.writeToNBT(tags);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, tags);
	}*/

	
	/* (non-Javadoc)
	 * @see net.minecraft.tileentity.TileEntity#updateEntity()
	 */
	@Override
	public void updateEntity() {
		super.updateEntity();
		//System.out.println("I AM THE TABLE");
		if (this.isRedstoneEnabled()){
			if (this.currentOperation != null) {

				if (this.consumePower(5*this.stackmultiplier)) {
					this.progress++;

					playAmbientSound();

					if (progress >= totalTime) {

						if (!this.worldObj.isRemote) {
							if (this.content[3] == null) {
								this.content[3] = new ItemStack(currentOperation.getItem(), currentOperation.stackSize*this.stackmultiplier, currentOperation.getItemDamage());
							} else {
								this.content[3].stackSize += (currentOperation.stackSize*this.stackmultiplier);
							}
							
						}
						this.progress = 0;
						this.totalTime = 0;
						this.currentOperation = null;
						this.stackmultiplier=1;

						checkAndStartOperation();
						
						if (!this.worldObj.isRemote){
							this.needUpdate();
						}
					}
				}

			} else if (this.content[0] == null || this.content[1] == null || this.content[2] == null) {
				// System.out.println("Not FULL");

			} else {
				// ItemStack[] input = new
				// ItemStack[]{content[0],content[1],content[2]};
				checkAndStartOperation();

			}
		}
		
	}

   
	public void checkAndStartOperation(){
		ItemStack output = AmmoPressBuildPlans.getOutputFor(content[0], content[1], content[2], this.buildPlan);// AmmoPressRecipe.isRecipe(input);

		// System.out.println("Output:"+output);

		if (output != null && canOutput(output, 3)) {
			
			int maxStack=1;
			if (this.content[4]!=null){
				if (this.content[4].getItem()==TGItems.machineStackUpgrade.getItem() && this.content[4].getItemDamage()==TGItems.machineStackUpgrade.getItemDamage()){
					maxStack = this.content[4].stackSize+1;
				}
			}
			
			
			//try higher stacksize
			int originalStacksize = output.stackSize;
			ItemStack outputStack = TGItems.newStack(output, output.stackSize);
			int i;
			for (i=maxStack;i>1;--i){
				outputStack.stackSize=output.stackSize*i;
				if (canOutput(outputStack,3)){
					if(content[0].stackSize>=i && content[1].stackSize>=i*2 && content[2].stackSize>=i){
						output=outputStack;
						this.stackmultiplier=i;
						break;
					}
				}
				
			}
			
			/*
			 * int[] values =
			 * AmmoPressRecipe.getAmmountsForRecipe(input); for (int
			 * i=0;i<values.length;i++){
			 * this.content[i].stackSize-=values[i]; if
			 * (this.content[i].stackSize<=0){ this.content[i]=null; } }
			 */
			this.content[0].stackSize -= 1*this.stackmultiplier;
			if (this.content[0].stackSize <= 0) {
				this.content[0] = null;
			}
			this.content[1].stackSize -= 2*this.stackmultiplier;
			if (this.content[1].stackSize <= 0) {
				this.content[1] = null;
			}
			this.content[2].stackSize -= 1*this.stackmultiplier;
			if (this.content[2].stackSize <= 0) {
				this.content[2] = null;
			}

			//
			//System.out.println("Starting with stackmultiplier:"+this.stackmultiplier);
			this.currentOperation = new ItemStack(output.getItem(), originalStacksize, output.getItemDamage());
			this.progress = 0;
			this.totalTime = 100;
			
			if (!this.worldObj.isRemote){
				this.needUpdate();
			}
		}
	}
	
	public static int getValidSlotForItemInMachine(ItemStack item){
		if (AmmoPressBuildPlans.isValidFor(item, 0)){
			return 0;
		} else if (AmmoPressBuildPlans.isValidFor(item, 1)){
			return 1;
		} else if (AmmoPressBuildPlans.isValidFor(item, 2)){
			return 2;
		} else if( item!=null && item.getItem()==TGItems.machineStackUpgrade.getItem() && item.getItemDamage()==TGItems.machineStackUpgrade.getItemDamage()){
			return 4;
		}
		return -1;
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return AmmoPressBuildPlans.isValidFor(item, slot);
	}

	@Override
	public void buttonClicked(int id, EntityPlayer ply){
		if(id<=MAX_BUTTON_USED){
			super.buttonClicked(id, ply);

		} else {
		
			if (this.isUseableByPlayer(ply)){
				//System.out.println("button"+id);
				switch(id){
					case MAX_BUTTON_USED+1:
						this.buildPlan++;
						break;
					case MAX_BUTTON_USED+2:
						this.buildPlan--;
						break;
					}
					if (this.buildPlan>this.maxPlan){
						this.buildPlan=0;
					} else if (this.buildPlan<0) {
						this.buildPlan=this.maxPlan;
					}
			}
		}
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		/*switch(side){
		case 0:
			return new int[]{3};
		default:
			return new int[]{0,1,2};
		}*/
		return new int[]{0,1,2,3};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item,
			int side) {
		return this.isItemValidForSlot(slot, item);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item,
			int side) {
		return slot==3;
	}

	
	private void playAmbientSound() {
//		float prog = (float)this.progress / (float)this.totalTime;
//		if (prog > 0.5f) {
//			prog-= 0.5f;
//		}
		
		int soundTick1 = Math.round((float)this.totalTime*0.05f);
		int soundTick2 = Math.round((float)this.totalTime*0.30f);
		//System.out.println("T1: "+soundTick1+"; T2: "+soundTick2);
		
		int halfTime = (Math.round((float)totalTime*0.5f));
		
		if (this.progress == soundTick1 || this.progress == soundTick1+halfTime) {
			worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "techguns:machines.ammopressWork1", 1.0F, 1.0F, true );
		}else if (this.progress == soundTick2 || this.progress == soundTick2+halfTime) {
			worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "techguns:machines.ammopressWork2", 1.0F, 1.0F, true );
		}
	}

    /**
     * Returns the name of the inventory
     */
	@Override
    public String getInventoryName()
    {
        return this.hasCustomInventoryName() ? this.name : "techguns.container.ammopress";
    }



}
