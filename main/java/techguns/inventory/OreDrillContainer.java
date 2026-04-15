package techguns.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import techguns.gui.playerinventory.TGPlayerInventory;
import techguns.tileentities.OreDrillTileEntMaster;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class OreDrillContainer extends Container {
	protected OreDrillTileEntMaster tile;
	private int lastProgress = 0;
	private int lastTotaltime = 0;
	private int lastPowerStored = 0;
	private int lastFluidStoredIn = 0;
	private int lastFluidStoredOut = 0;
	private int lastFuelBuffer = 0 ;
	private int lastFuelBufferMax = 0 ;
	private Fluid lastFluidInType = null;
	private Fluid lastFluidOutType = null;
	
	public OreDrillContainer(InventoryPlayer player, OreDrillTileEntMaster ent) {
		this.tile = ent;
		this.addSlotToContainer(new Slot(ent, 0, 30, 17));

		for (int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				this.addSlotToContainer(new Slot(ent, 1+(i*3)+j, 102+18*i, 17+18*j));
			}
		}
		
		//fuel slot
		this.addSlotToContainer(new Slot(ent, 10, 30, 51));
		
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
		
		return tile.isUseableByPlayer(player);
	}

	@Override
	public void addCraftingToCrafters(ICrafting craft) {
		super.addCraftingToCrafters(craft);
		craft.sendProgressBarUpdate(this, 0, this.tile.progress);
		craft.sendProgressBarUpdate(this, 1, this.tile.totalTime);
		craft.sendProgressBarUpdate(this, 2, this.tile.getPowerStored()/100);
		craft.sendProgressBarUpdate(this, 3, this.tile.getFluidType(false));
		craft.sendProgressBarUpdate(this, 4, this.tile.getFluidType(true));
		craft.sendProgressBarUpdate(this, 5, this.tile.inputTank.getFluidAmount());
		craft.sendProgressBarUpdate(this, 6, this.tile.outputTank.getFluidAmount());
		craft.sendProgressBarUpdate(this, 7, this.tile.getFuelBuffer()/10);
		craft.sendProgressBarUpdate(this, 8, this.tile.getCurrentFuelBufferMax()/10);
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
				icrafting.sendProgressBarUpdate(this, 2, this.tile.getPowerStored()/100);
			}
			
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
			
			if (this.lastFuelBuffer !=this.tile.getFuelBuffer()){
				int buf = this.tile.getFuelBuffer();
				icrafting.sendProgressBarUpdate(this, 7, buf>0?buf/10:0);
			}
			
			if (this.lastFuelBufferMax !=this.tile.getCurrentFuelBufferMax()){
				int max = this.tile.getCurrentFuelBufferMax();
				icrafting.sendProgressBarUpdate(this, 8, max>0?max/10:0);
			}
		}

		
		
		this.lastProgress = this.tile.progress;
		this.lastTotaltime = this.tile.totalTime;
		this.lastPowerStored = this.tile.getPowerStored();
		this.lastFuelBuffer = this.tile.getFuelBuffer();
		this.lastFuelBufferMax = this.tile.getCurrentFuelBufferMax();
		
		this.lastFluidStoredIn = this.tile.inputTank.getFluidAmount();
		this.lastFluidStoredOut = this.tile.outputTank.getFluidAmount();
		
		FluidStack f = this.tile.inputTank.getFluid();
		this.lastFluidInType = f!=null?f.getFluid():null;
		
		FluidStack f2 = this.tile.outputTank.getFluid();
		this.lastFluidOutType = f2!=null?f2.getFluid():null;
		
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		if (id == 0) {
			this.tile.progress = value;
		} else if (id == 1) {
			this.tile.totalTime = value;
		} else  if(id==2){
			this.tile.setPowerStored(value*100);
		} else if (id==3){
			this.tile.setFluidTypeIn(value);
		} else if (id==4){
			this.tile.setFluidTypeOut(value);
		}else if (id==5){
			this.tile.setFluidAmountIn(value);
		} else if (id==6){
			this.tile.setFluidAmountOut(value);
		} else if (id==7){
			this.tile.setFuelBuffer(value*10);
		} else if (id==8){
			this.tile.setCurrentFuelBufferMax(value*10);
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
					
					if (id==0){
						//NO SHIFT+CLICK OUT!!!
						return null;
					} else if (id >0 && id<=10){
						//PRESSED IN MACHINE GUI 1-9 output, 10 fuelinput
						if (!this.mergeItemStack(stack1, 11, 11+36, true)) {
							return null;
						}
						slot.onSlotChange(stack1, stack);
					} else if (id >10 && id <10+36){
						
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
