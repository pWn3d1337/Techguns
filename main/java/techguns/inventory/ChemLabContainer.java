package techguns.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import techguns.gui.SlotMachineUpgrade;
import techguns.gui.SlotOutput;
import techguns.tileentities.ChemLabTileEnt;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ChemLabContainer extends Container {

	protected ChemLabTileEnt tile;
	private int lastProgress = 0;
	private int lastTotaltime = 0;
	private int lastPowerStored = 0;
	private int lastFluidStoredIn =0;
	private int lastFluidStoredOut=0;
	private Fluid lastFluidInType=null;
	private Fluid lastFluidOutType=null;

	public ChemLabContainer(InventoryPlayer player, ChemLabTileEnt ent) {
		this.tile = ent;

		this.addSlotToContainer(new Slot(ent, 0, 35, 17));
		this.addSlotToContainer(new Slot(ent, 1, 57, 17));
		this.addSlotToContainer(new Slot(ent, 2, 35, 40));
		this.addSlotToContainer(new SlotOutput(ent, 3, 135, 17));
		this.addSlotToContainer(new SlotMachineUpgrade(ent, 4, 135, 47));
		// this.addSlotToContainer(new SlotFurnace(player.player, ent, 3, 116,
		// 35));
		int i;

		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9,
						8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
		}
	}
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tile.isUseableByPlayer(player);
	}

	@Override
	public void addCraftingToCrafters(ICrafting craft) {
		super.addCraftingToCrafters(craft);
		craft.sendProgressBarUpdate(this, 0, this.tile.progress);
		craft.sendProgressBarUpdate(this, 1, this.tile.totalTime);
		craft.sendProgressBarUpdate(this, 2, this.tile.getPowerStored());
		craft.sendProgressBarUpdate(this, 3, this.tile.getFluidType(false));
		craft.sendProgressBarUpdate(this, 4, this.tile.getFluidType(true));
		craft.sendProgressBarUpdate(this, 5, this.tile.inputTank.getFluidAmount());
		craft.sendProgressBarUpdate(this, 6, this.tile.outputTank.getFluidAmount());
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if (this.lastProgress != this.tile.progress) {
				icrafting.sendProgressBarUpdate(this, 0, this.tile.progress);
			}

			if (this.lastTotaltime != this.tile.totalTime) {
				icrafting.sendProgressBarUpdate(this, 1, this.tile.totalTime);
			}
			if ((this.lastPowerStored != this.tile.getPowerStored())||this.tile.isWorking()){
				icrafting.sendProgressBarUpdate(this, 2, this.tile.getPowerStored());
			}
			/*if(this.lastFluidInType != this.tile.getFluidType(false)){
				icrafting.sendProgressBarUpdate(this, 3, this.tile.getFluidType(false));
			}
			if(this.lastFluidOutType != this.tile.getFluidType(true)){
				icrafting.sendProgressBarUpdate(this, 4, this.tile.getFluidType(true));
			}*/
			
			FluidStack f = this.tile.inputTank.getFluid();
			Fluid fl = f!=null?f.getFluid():null;
			if(this.lastFluidInType != fl){
				icrafting.sendProgressBarUpdate(this, 3, f!=null?f.getFluidID():-1);
			}
			
			f = this.tile.outputTank.getFluid();
			fl = f!=null?f.getFluid():null;
			if(this.lastFluidOutType != fl){
				icrafting.sendProgressBarUpdate(this, 4, f!=null?f.getFluidID():-1);
			}
			
			
			if ((this.lastFluidStoredIn != this.tile.inputTank.getFluidAmount())||this.tile.isWorking()){
				icrafting.sendProgressBarUpdate(this, 5, this.tile.inputTank.getFluidAmount());
			}
			if ((this.lastFluidStoredOut != this.tile.outputTank.getFluidAmount())||this.tile.isWorking()){
				icrafting.sendProgressBarUpdate(this, 6, this.tile.outputTank.getFluidAmount());
			}

			
		}
		this.lastProgress = this.tile.progress;
		this.lastTotaltime = this.tile.totalTime;
		this.lastPowerStored = this.tile.getPowerStored();
		this.lastFluidStoredIn = this.tile.inputTank.getFluidAmount();
		this.lastFluidStoredOut = this.tile.outputTank.getFluidAmount();
		
		FluidStack f = this.tile.inputTank.getFluid();
		this.lastFluidInType = f!=null?f.getFluid():null;
		
		FluidStack f2 = this.tile.outputTank.getFluid();
		this.lastFluidOutType = f2!=null?f2.getFluid():null;
		
		//this.lastFluidInType = this.tile.getFluidType(false);
		//this.lastFluidOutType=this.tile.getFluidType(true);
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		if (id == 0) {
			this.tile.progress = value;
		} else if (id == 1) {
			this.tile.totalTime = value;
		} else if (id==2){
			this.tile.setPowerStored(value);
		}  else if (id==3){
			this.tile.setFluidTypeIn(value);
		} else if (id==4){
			this.tile.setFluidTypeOut(value);
		}else if (id==5){
			this.tile.setFluidAmountIn(value);
		} else if (id==6){
			this.tile.setFluidAmountOut(value);
		}

	}
	@Override
	public ItemStack transferStackInSlot(EntityPlayer ply, int id) {
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(id);

			if(slot.getHasStack()){
				ItemStack stack1 = slot.getStack();
				stack=stack1.copy();
				if (stack != null){
					
					if (id >=0 && id<5){
						//PRESSED IN MACHINE GUI
						if (!this.mergeItemStack(stack1, 5, 41, true)) {
							return null;
						}
						slot.onSlotChange(stack1, stack);
					} else if (id >=5 && id <41){
						
						int validslot = tile.getValidSlotForItemInMachine(stack1);
						//System.out.println("put it in slot"+validslot);
						if (validslot >=0){
							
							if(!this.mergeItemStack(stack1, validslot, validslot+1, false)){
								return null;
							}
							slot.onSlotChange(stack1, stack);
							
						} else {
							return null;
						}
						
						
					}

					if (stack1.stackSize == 0) {
						slot.putStack((ItemStack) null);
					} else {
						slot.onSlotChanged();
					}

					if (stack1.stackSize == stack.stackSize) {
						return null;
					}

					slot.onPickupFromSlot(ply, stack1);
				}
			}
		
			return stack;
	}
	
	
}
