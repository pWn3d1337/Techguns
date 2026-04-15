package techguns.tileentities;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.oredict.OreDictionary;
import techguns.TGItems;
import techguns.inventory.ChemLabRecipes;
import techguns.inventory.ChemLabRecipes.ChemLabOutput;
import techguns.util.ItemUtil;

public class ChemLabTileEnt extends BasicMachineTileEntity implements IFluidHandler {
	public FluidTank inputTank;
	public FluidTank outputTank;

	private Random rng = new Random();

	protected ChemLabOutput currentChemOperation = null;

	protected boolean drainInput=false;
	
	public ChemLabTileEnt() {
		super(5, 20000);
		inputTank = new FluidTank(8 * FluidContainerRegistry.BUCKET_VOLUME);
		outputTank = new FluidTank(16 * FluidContainerRegistry.BUCKET_VOLUME);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0, 1, 2, 3 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item,
			int side) {
		return this.isItemValidForSlot(slot, item);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item,
			int side) {
		return slot ==3;
	}

	
	
	/**
	 * Returns the name of the inventory
	 */
	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.name : "techguns.container.chemlab";
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		if(slot==2){
			return ChemLabRecipes.allowInFlaskSlot(item);
		} else if(slot==0 && content[0]==null && content[1]==null){
			return ChemLabRecipes.hasRecipeUsing(item);
		} else if (slot==0 && content[0]==null && content[1]!=null){
			return ChemLabRecipes.allowAsInput2(this.content[1], item);
		} else if (slot==0 && content[0]!=null){
			return ItemUtil.isItemEqual(this.content[0], item);
		} else if (slot==1 && content[0]!=null){
			return ChemLabRecipes.allowAsInput2(this.content[0], item);
		} else if (slot==1 && content[1]!=null){
			return ItemUtil.isItemEqual(this.content[1], item);
		}
		
		return false;
	}

	@Override
	public void readEntityDataFromNBT(NBTTagCompound tag) {
		super.readEntityDataFromNBT(tag);
		NBTTagCompound tankIn = tag.getCompoundTag("inputTank");
		if (tankIn != null) {
			inputTank.readFromNBT(tankIn);
		}
		NBTTagCompound tankOut = tag.getCompoundTag("outputTank");
		if (tankOut != null) {
			outputTank.readFromNBT(tankOut);
		}
		drainInput=tag.getBoolean("drainInput");
		
		NBTTagCompound chemOp = tag.getCompoundTag("currentChemOperation");
		if(!chemOp.hasNoTags()){
			this.currentChemOperation=new ChemLabOutput(chemOp);
		} else {
			this.currentChemOperation=null;
		}
	}

	@Override
	public void writeEntityDataToNBT(NBTTagCompound tag) {
		super.writeEntityDataToNBT(tag);
		NBTTagCompound tagInputTank = new NBTTagCompound();
		NBTTagCompound tagOutputTank = new NBTTagCompound();
		inputTank.writeToNBT(tagInputTank);
		tag.setTag("inputTank", tagInputTank);
		outputTank.writeToNBT(tagOutputTank);
		tag.setTag("outputTank", tagOutputTank);
		tag.setBoolean("drainInput", drainInput);
		
		if(this.currentChemOperation!=null){
			tag.setTag("currentChemOperation", this.currentChemOperation.getAsNBTTags());
		}
	}

	/* IFluidHandler */
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return inputTank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if(!drainInput){
			if (resource == null || !resource.isFluidEqual(outputTank.getFluid())) {
				return null;
			}
			return outputTank.drain(resource.amount, doDrain);
		} else {
			if (resource == null || !resource.isFluidEqual(inputTank.getFluid())) {
				return null;
			}
			return inputTank.drain(resource.amount, doDrain);
		}
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if(drainInput){
			return inputTank.drain(maxDrain, doDrain);
		}
		return outputTank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean isWorking() {
		return !(this.currentChemOperation == null);
	}

	private boolean canMergeFluidOutput(FluidStack output) {
		if (output == null || this.outputTank.getFluid() == null) {
			return true;
		}
		return this.outputTank.getFluid().isFluidEqual(output) && (this.outputTank.getFluidAmount() + output.amount <= this.outputTank.getCapacity());
	}

	public boolean canOutput(ChemLabOutput output) {
		return this.canOutput(output.getItemOutput(), 3) && this.canMergeFluidOutput(output.getLiquidOutput());
	}
	
	public boolean canOutput(ChemLabOutput output, int multiplier) {
		ItemStack output_item =null;
		FluidStack output_fluid=null;
		if(output.getItemOutput()!=null){
			output_item = TGItems.newStack(output.getItemOutput(), output.getItemOutput().stackSize*multiplier);
		}
		if(output.getLiquidOutput()!=null){
			output_fluid = new FluidStack(output.getLiquidOutput().getFluid(),output.getLiquidOutput().amount*multiplier);
		}
		
		return (output_item==null || this.canOutput(output_item, 3)) && (output_fluid==null||this.canMergeFluidOutput(output_fluid));
	}
	
	public boolean canConsume(ChemLabOutput output, int multiplier){
		int amount0 = output.getAmount(0)*multiplier;
		int amount1 = output.getAmount(1)*multiplier;
		int amount2 = output.getAmount(2)*multiplier;
		int amount_liquid = output.getAmount(3)*multiplier;
		
		if ((amount0 > 0 && this.content[0]!=null && this.content[0].stackSize>=amount0) || (amount0<=0 && this.content[0]==null)){
			if ((amount1 > 0 && this.content[1]!=null && this.content[1].stackSize>=amount1) || (amount1<=0 && this.content[1]==null)){
				if ((amount2 > 0 && this.content[2]!=null && this.content[2].stackSize>=amount2) || (amount2<=0 && this.content[2]==null)){
					if( (amount_liquid>0 && this.inputTank.getFluid()!=null && this.inputTank.getFluidAmount() >=amount_liquid) || (amount_liquid<=0)){
						return true;
					}
				}
			}
		}
		
		return false;
	}

	@Override
	public void updateEntity() {

		super.updateEntity();
		// System.out.println("I AM THE TABLE");
		if (this.isRedstoneEnabled()) {
			if (this.currentChemOperation != null) {

				if (this.consumePower(this.currentChemOperation.getPowerPerTick())) {
					this.progress++;

					if (this.worldObj.isRemote){
						playAmbientSound();
	
						createParticles();
					}

					if (!this.worldObj.isRemote  && (progress >= totalTime)) {

						//if (!this.worldObj.isRemote) {
							if (this.content[3] == null && this.currentChemOperation.getItemOutput() != null) {
								this.content[3] = new ItemStack(currentChemOperation.getItemOutput().getItem(), currentChemOperation.getItemOutput().stackSize
										* this.stackmultiplier, currentChemOperation.getItemOutput().getItemDamage());
							} else {
								if (currentChemOperation.getItemOutput() != null) {
									this.content[3].stackSize += currentChemOperation.getItemOutput().stackSize * this.stackmultiplier;
								}
							}

							if (this.currentChemOperation.getLiquidOutput() != null) {

								if (this.outputTank.getFluid() == null) {
									this.outputTank.setFluid(new FluidStack(this.currentChemOperation.getLiquidOutput().getFluid(), this.currentChemOperation
											.getLiquidOutput().amount * this.stackmultiplier));
								} else {
									this.outputTank.getFluid().amount += this.currentChemOperation.getLiquidOutput().amount * this.stackmultiplier;
								}
							}

							//this.needUpdate();
						//}
						this.progress = 0;
						this.totalTime = 0;
						// this.currentOperation=null;
						this.currentChemOperation = null;
						this.stackmultiplier = 0;

						checkAndStartOperation();

						//if (!this.worldObj.isRemote) {
							this.needUpdate();
						//}
					}
				}

			} else if (this.content[0] == null && this.content[1] == null) {
				// System.out.println("Not FULL");

			} else {
				if (!this.worldObj.isRemote) {
					checkAndStartOperation();
				}
			}
		}
	}

	public void checkAndStartOperation() {
		ChemLabRecipes.ChemLabOutput output = ChemLabRecipes.getOutputFor(content[0], content[1], content[2], this.inputTank.getFluid());// AmmoPressBuildPlans.getOutputFor(content[0],content[1],
		// this.buildPlan);//AmmoPressRecipe.isRecipe(input);

		// System.out.println("Check output:"+output);

		if (output != null && canOutput(output)) {
			
			//check multiplier
			
			int maxStack=1;
			if (this.content[4]!=null){
				if (this.content[4].getItem()==TGItems.machineStackUpgrade.getItem() && this.content[4].getItemDamage()==TGItems.machineStackUpgrade.getItemDamage()){
					maxStack = this.content[4].stackSize+1;
				}
			}
			this.stackmultiplier=1;
			//try higher stacksize
			int i;
			for (i=maxStack;i>1;--i){
				if(this.canOutput(output, i) && canConsume(output,i)){
					this.stackmultiplier=i;
					break;
				}
				
			}
			
			

			if (output.getAmount(0) > 0) {
				this.content[0].stackSize -= output.getAmount(0)*this.stackmultiplier;
				if (this.content[0].stackSize <= 0) {
					this.content[0] = null;
				}
			}
			if (output.getAmount(1) > 0) {
				this.content[1].stackSize -= output.getAmount(1)*this.stackmultiplier;
				if (this.content[1].stackSize <= 0) {
					this.content[1] = null;
				}
			}
			if (output.getAmount(2) > 0) {
				this.content[2].stackSize -= output.getAmount(2)*this.stackmultiplier;
				if (this.content[2].stackSize <= 0) {
					this.content[2] = null;
				}
			}
			if (output.getAmount(3) > 0) {
				this.inputTank.drain(output.getAmount(3)*this.stackmultiplier, true);
			}

			//
			this.progress = 0;
			this.totalTime = 100;
			// this.currentOperation= new
			// ItemStack(output.getItemOutput().getItem(),
			// output.getItemOutput().stackSize,output.getItemOutput().getItemDamage());
			ItemStack itemoutput = null;
			if (output.getItemOutput() != null) {
				itemoutput = TGItems.newStack(output.getItemOutput(), output.getItemOutput().stackSize);
			}
			this.currentChemOperation = new ChemLabOutput(itemoutput, output.getLiquidOutput(), output.getAmount(0), output.getAmount(1), output.getAmount(2),
					output.getAmount(3), output.getPowerPerTick());
			// this.currentOperation=new ItemStack(Items.apple,1);
			// //DUMMY
			if (!this.worldObj.isRemote) {
				this.needUpdate();
			}
		}
	}

	public int getValidSlotForItemInMachine(ItemStack item) {
		if (content[0] != null && OreDictionary.itemMatches(content[0], item, true)) {
			return 0;
		} else if (content[1] != null && OreDictionary.itemMatches(content[1], item, true)) {
			return 1;
		} else if (content[0] == null && ChemLabRecipes.hasRecipeUsing(item)) {
			return 0;
		} else if (content[0] != null && content[1] == null && (ChemLabRecipes.allowAsInput2(content[0], item))) {
			return 1;
		} else if (content[2] != null && OreDictionary.itemMatches(content[2], item, true)) {
			return 2;
		} else if (content[2] == null && ChemLabRecipes.allowInFlaskSlot(item)) {
			return 2;
		} else if (item != null && item.getItem() == TGItems.machineStackUpgrade.getItem()
				&& item.getItemDamage() == TGItems.machineStackUpgrade.getItemDamage()) {
			return 4;
		}
		return -1;
	}

	private void playAmbientSound() {
		int soundTick1 = 1;
		int halfTime = (Math.round((float) totalTime * 0.5f));
		if (this.progress == soundTick1 || this.progress == soundTick1 + halfTime) {
			worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "techguns:machines.chemlabWork", 1.0F, 1.0F, true);
		}
	}

	private void createParticles() {
		int delay = 10;
		if (this.progress % delay == 0) {
			worldObj.spawnParticle("spell", this.xCoord + rng.nextFloat(), this.yCoord + rng.nextFloat(), this.zCoord + rng.nextFloat(), 0, 1, 0);
		}
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		/*if (fluid == FluidRegistry.WATER) {
			return true;
		} else if(fluid==FluidRegistry.LAVA){
			return true;
		} else if (TGFluids.FUEL!=null && fluid==TGFluids.FUEL){
			return true;
		} else if (TGFluids.OIL!=null && fluid==TGFluids.OIL){
			return true;
		}
		return false;*/
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { inputTank.getInfo(), outputTank.getInfo() };
	}

	public void setFluidAmountIn(int value) {
		FluidStack fluid = this.inputTank.getFluid();
		if (fluid != null) {
			fluid.amount = value;
		}
	}

	public void setFluidAmountOut(int value) {
		FluidStack fluid = this.outputTank.getFluid();
		if (fluid != null) {
			fluid.amount = value;
		}
	}

	/*public byte getFluidType(boolean output) {
		FluidStack fluidstack;
		FluidTank tank;
		if (output) {
			fluidstack = this.outputTank.getFluid();
			tank = this.outputTank;
		} else {
			fluidstack = this.inputTank.getFluid();
			tank = this.inputTank;
		}

		if (fluidstack == null) {
			return ChemLabRecipes.CHEM_LAB_NO_FLUID;
		} else if (tank.getFluid().getFluid() == FluidRegistry.WATER) {
			return ChemLabRecipes.CHEM_LAB_FLUID_WATER;
		} else if (tank.getFluid().getFluid() == FluidRegistry.LAVA) {
			return ChemLabRecipes.CHEM_LAB_FLUID_LAVA;
		} else if (tank.getFluid().getFluid() == TGFluids.OIL) {
			return ChemLabRecipes.CHEM_LAB_FLUID_OIL;
		} else if (tank.getFluid().getFluid() == TGFluids.FUEL) {
			return ChemLabRecipes.CHEM_LAB_FLUID_FUEL;
		} else {
			return ChemLabRecipes.CHEM_LAB_NO_FLUID;
		}
	}*/

	public int getFluidType(boolean output) {
		FluidStack fluidstack;
		if (output) {
			fluidstack = this.outputTank.getFluid();
		} else {
			fluidstack = this.inputTank.getFluid();
		}
		if (fluidstack!=null){
			return fluidstack.getFluidID();
		} else {
			return -1;
		}
	}
	/*public void setFluidTypeIn(byte type) {
		if (type == ChemLabRecipes.CHEM_LAB_NO_FLUID) {
			this.inputTank.setFluid(null);
		} else {
			Fluid f = ChemLabRecipes.getFluidType(type);
			this.inputTank.setFluid(new FluidStack(f, 1));
		}
	}

	public void setFluidTypeOut(byte type) {
		if (type == ChemLabRecipes.CHEM_LAB_NO_FLUID) {
			this.outputTank.setFluid(null);
		} else {
			Fluid f = ChemLabRecipes.getFluidType(type);
			this.outputTank.setFluid(new FluidStack(f, 1));
		}
	}*/
	
	public void setFluidTypeIn(int id) {
		Fluid f = FluidRegistry.getFluid(id);
		if (f!=null){
			this.inputTank.setFluid(new FluidStack(f, 0));
		} else {
			this.inputTank.setFluid(null);
		}
	}
	
	public void setFluidTypeOut(int id) {
		Fluid f = FluidRegistry.getFluid(id);
		if (f!=null){
			this.outputTank.setFluid(new FluidStack(f, 0));
		} else {
			this.outputTank.setFluid(null);
		}
	}
	
	public byte getDrainMode() {
		return (byte) (this.drainInput?1:0);
	}
	
	@Override
	public void buttonClicked(int id, EntityPlayer ply) {
		if(id<=MAX_BUTTON_USED){
			super.buttonClicked(id, ply);
		} else {
			if (this.isUseableByPlayer(ply)){
				switch(id){
					case MAX_BUTTON_USED+1: //drain input
						this.inputTank.setFluid(null);
						this.needUpdate();
						break;
					case MAX_BUTTON_USED+2: //drain output
						this.outputTank.setFluid(null);
						this.needUpdate();
						break;
					case MAX_BUTTON_USED+3: //toogle drain
						this.drainInput=!this.drainInput;
						this.needUpdate();
						break;
				}
			}
		}
	}

	@Override
	public ItemStack onFluidContainerInteract(ItemStack fluidContainer) {
		if (FluidContainerRegistry.isEmptyContainer(fluidContainer)){
			FluidTank drainTank = null;
			
			//System.out.println("is empty Container");
			
			if (this.drainInput){
				drainTank = this.inputTank;
			} else {
				drainTank = this.outputTank;
			}
			
			if (drainTank.getFluid()!=null){
				
				//System.out.println("Fluid:"+drainTank.getFluid().getUnlocalizedName());
				
				int capacity = FluidContainerRegistry.getContainerCapacity(drainTank.getFluid(), fluidContainer);
				
				if (capacity >0 && drainTank.getFluidAmount()>=capacity){
					ItemStack fullContainer = FluidContainerRegistry.fillFluidContainer(drainTank.getFluid(), fluidContainer);
					drainTank.drain(capacity, true);
					
					/*if(this.worldObj.isRemote){
						//System.out.println("Soundplay!");
						this.worldObj.playSound(this.xCoord+0.5d, this.yCoord+0.5d, this.zCoord+0.5d, "minecraft:game.player.swim.splash", 1.0f, 1.0f, false);
					}*/
					
					return fullContainer;
				}
			}
			
			
			
		} else if ( FluidContainerRegistry.isFilledContainer(fluidContainer)){
			FluidStack content = FluidContainerRegistry.getFluidForFilledItem(fluidContainer);
			if (content!=null){
				if (inputTank.getFluid()== null || (inputTank.getFluid()!=null && inputTank.getFluid().getFluid()==content.getFluid()) ){
					int capacityLeft = inputTank.getCapacity() - inputTank.getFluidAmount();
					if (content.amount<=capacityLeft){
						
						ItemStack emptyContainer = FluidContainerRegistry.drainFluidContainer(fluidContainer);
						inputTank.fill(content, true);
						return emptyContainer;
						
					}
					
				}
			}
		}
		
		
		return super.onFluidContainerInteract(fluidContainer);
	}
	
	
}
