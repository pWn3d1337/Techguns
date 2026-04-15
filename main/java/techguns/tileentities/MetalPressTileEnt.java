package techguns.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import techguns.TGItems;
import techguns.inventory.MetalPressRecipes;

public class MetalPressTileEnt extends BasicMachineTileEntity {
	
	int minSoundDelay = 0;
	byte autoSlitMode =0;
	
	public MetalPressTileEnt() {
		super(4, 20000);
	}

	
	
	@Override
	public void readEntityDataFromNBT(NBTTagCompound tags) {
		super.readEntityDataFromNBT(tags);
		this.autoSlitMode=tags.getByte("autoSplitMode");
	}



	@Override
	public void writeEntityDataToNBT(NBTTagCompound tags) {
		super.writeEntityDataToNBT(tags);
		tags.setByte("autoSplitMode",this.autoSlitMode);
	}



	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		/*if (side==0 || side ==1){
			//Top or bottom
			return new int[]{0,2};
		} else {
			//Sides
			return new int[]{1,2};
		}*/
		return new int[]{0,1,2};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item,
			int side) {
		return this.isItemValidForSlot(slot, item);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item,
			int side) {
		return slot ==2;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		//can stack?
		if(content[slot]!=null && OreDictionary.itemMatches(content[slot], item, true) && (content[slot].stackSize+item.stackSize<=item.getMaxStackSize())){
			return true;
			//can start new Recipe?
		} else if (content[0]==null && slot==0 && MetalPressRecipes.hasRecipeUsing(item)){
			return true;
			//can finish recipe?
		} else if(content[0]!=null && slot==1 && content[1]==null && (MetalPressRecipes.getOutputFor(content[0],item) != null)){
			return true;
		}
		return false;
	}

	public int getValidSlotForItemInMachine(ItemStack item){
		if(content[0]!=null && OreDictionary.itemMatches(content[0], item, true)){
			return 0;
		} else if (content[1]!=null && OreDictionary.itemMatches(content[1], item, true)){
			return 1;
		} else if (content[0]==null && MetalPressRecipes.hasRecipeUsing(item)){
			return 0;
		} else if(content[0]!=null && content[1]==null && (MetalPressRecipes.getOutputFor(content[0],item) != null)){
			return 1;
		} else if( item!=null && item.getItem()==TGItems.machineStackUpgrade.getItem() && item.getItemDamage()==TGItems.machineStackUpgrade.getItemDamage()){
			return 3;
		}
		return -1;
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.tileentity.TileEntity#updateEntity()
	 */
	@Override
	public void updateEntity() {
		super.updateEntity();
		//System.out.println("I AM THE TABLE");
		if (this.isRedstoneEnabled()){
			if (this.currentOperation!= null) {
				
				if (this.consumePower(20*this.stackmultiplier)){
					this.progress++;
					
					playAmbientSound();
					
					if (progress >= totalTime){
			
						if(!this.worldObj.isRemote) {
							if (this.content[2]==null){
								this.content[2]=new ItemStack(currentOperation.getItem(), currentOperation.stackSize*this.stackmultiplier,currentOperation.getItemDamage());
							} else {
								this.content[2].stackSize+=(currentOperation.stackSize*this.stackmultiplier);
							}
							
						}
						this.progress=0;
						this.totalTime=0;
						this.currentOperation=null;
						this.stackmultiplier=1;
						
						checkAndStartOperation();
						if(!this.worldObj.isRemote){
							this.needUpdate();
						}
					}
				}
				
			} else if( this.content[0]==null && this.content[1] == null){
				//System.out.println("Not FULL");
	
			} else if((this.autoSlitMode>0) && (this.content[0]==null || this.content[1] == null)){
				//System.out.println("Not FULL");
	
				if(content[1]==null && content[0].stackSize>1){
				
					ItemStack output = MetalPressRecipes.getOutputFor(content[0], content[0]);
					
					if (output!=null){
						int split1 = content[0].stackSize/2+(content[0].stackSize%2);
						int split2 = content[0].stackSize/2;
						content[1] = new ItemStack(content[0].getItem(),split2,content[0].getItemDamage());
						content[0].stackSize=split1;
					}
					
				} else if(content[0]==null && content[1].stackSize>1){
					
					ItemStack output = MetalPressRecipes.getOutputFor(content[1], content[1]);
					
					if (output!=null){
						int split1 = content[1].stackSize/2+(content[1].stackSize%2);
						int split2 = content[1].stackSize/2;
						content[0] = new ItemStack(content[1].getItem(),split2,content[1].getItemDamage());
						content[1].stackSize=split1;
					}
					
				}
				
				
				checkAndStartOperation();
			} else if ((this.autoSlitMode>0)&&(this.content[0]!=null && this.content[1]!=null)){
				
				if (this.content[0].getItem()==this.content[1].getItem() && this.content[0].getItemDamage()== this.content[1].getItemDamage()){
					
					if (Math.abs(this.content[1].stackSize-this.content[0].stackSize)>1){
						
						int total = this.content[0].stackSize+this.content[1].stackSize;
						int split1 = total/2+total%2;
						int split2 = total/2;
						
						content[0].stackSize=split1;
						content[1].stackSize=split2;
					}
					
					
				}
				checkAndStartOperation();
					
			} else {
									
				checkAndStartOperation();
				
			}
		}
		
	}

	public void checkAndStartOperation(){
		ItemStack output = MetalPressRecipes.getOutputFor(content[0], content[1]);//AmmoPressBuildPlans.getOutputFor(content[0],content[1], this.buildPlan);//AmmoPressRecipe.isRecipe(input);
		
		//System.out.println("Output:"+output);
		
		if (output!=null && canOutput(output,2)){
			
			
			//check for multistack operation
			ItemStack outputStack = TGItems.newStack(output, output.stackSize);
			int maxStack=1;
			if (this.content[3]!=null){
				if (this.content[3].getItem()==TGItems.machineStackUpgrade.getItem() && this.content[3].getItemDamage()==TGItems.machineStackUpgrade.getItemDamage()){
					maxStack = this.content[3].stackSize+1;
				}
			}
			
			int originalStacksize = output.stackSize;
			int i;
			for (i=maxStack;i>1;--i){
				outputStack.stackSize=output.stackSize*i;

				if (canOutput(outputStack,2)){
					if(content[0].stackSize>=i && content[1].stackSize>=i){
						output=outputStack;
						this.stackmultiplier=i;
						break;
					}
				}
				
			}
			
			this.content[0].stackSize-=this.stackmultiplier;
			if (this.content[0].stackSize<=0){
				this.content[0]=null;
			}
			this.content[1].stackSize-=this.stackmultiplier;
			if (this.content[1].stackSize<=0){
				this.content[1]=null;
			}
			//
			
			this.progress=0;
			this.totalTime=100;
			this.currentOperation= new ItemStack(output.getItem(), originalStacksize,output.getItemDamage());
			
			if (!this.worldObj.isRemote){
				this.needUpdate();
			}
		}
	}
	
	private void playAmbientSound() {
//		float prog = (float)this.progress / (float)this.totalTime;
//		if (prog > 0.5f) {
//			prog-= 0.5f;
//		}
		
		int soundTick1 = Math.round((float)this.totalTime*0.075f);
		//int soundTick2 = Math.round((float)this.totalTime*0.25f);
		//System.out.println("T1: "+soundTick1+"; T2: "+soundTick2);
		
		int halfTime = (Math.round((float)totalTime*0.5f));
		
		if (this.progress == soundTick1 || this.progress == soundTick1+halfTime) {
			worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "techguns:machines.metalpressWork", 1.0F, 1.0F, true );
//		}else if (this.progress == soundTick2 || this.progress == soundTick2+halfTime) {
//			worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "tile.piston.in", 1.0F, 1.0F, true );
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.name : "techguns.container.metalpress";
	}
	
	public byte getAutoSplitMode(){
		return this.autoSlitMode;
	}
	
	public void setAutoSplitMode(byte mode){
		this.autoSlitMode=mode;
	}
	
	
	@Override
	public void buttonClicked(int id, EntityPlayer ply) {
		if(id<=MAX_BUTTON_USED){
			super.buttonClicked(id, ply);
		} else if(id==MAX_BUTTON_USED+1){
			if (this.isUseableByPlayer(ply)){
				this.changeAutoSplitMode();
			}
		}
	}



	public void changeAutoSplitMode(){
		if(this.autoSlitMode==0){
			this.autoSlitMode=1;
		} else {
			this.autoSlitMode=0;
		}
	}
}
