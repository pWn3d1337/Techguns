package techguns.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import techguns.gui.SlotMachineUpgrade;
import techguns.gui.SlotOutput;
import techguns.tileentities.AmmoPressTileEnt;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AmmoPressContainer extends Container {
	protected AmmoPressTileEnt tile;
	private int lastProgress = 0;
	private int lastTotaltime = 0;
	private byte lastBuildPlan = 0;
	private int lastPowerStored = 0;

	public AmmoPressContainer(InventoryPlayer player, AmmoPressTileEnt ent) {
		this.tile = ent;

		this.addSlotToContainer(new Slot(ent, 0, 100, 17));
		this.addSlotToContainer(new Slot(ent, 1, 120, 17));
		this.addSlotToContainer(new Slot(ent, 2, 140, 17));
		this.addSlotToContainer(new SlotOutput(ent, 3, 120, 60));
		this.addSlotToContainer(new SlotMachineUpgrade(ent, 4, 152, 60));
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
	public void addCraftingToCrafters(ICrafting craft) {
		super.addCraftingToCrafters(craft);
		craft.sendProgressBarUpdate(this, 0, this.tile.progress);
		craft.sendProgressBarUpdate(this, 1, this.tile.totalTime);
		craft.sendProgressBarUpdate(this, 2, this.tile.buildPlan);
		craft.sendProgressBarUpdate(this, 3, this.tile.getPowerStored());
		/*
		 * p_75132_1_.sendProgressBarUpdate(this, 0,
		 * this.tileFurnace.furnaceCookTime);
		 * p_75132_1_.sendProgressBarUpdate(this, 1,
		 * this.tileFurnace.furnaceBurnTime);
		 * p_75132_1_.sendProgressBarUpdate(this, 2,
		 * this.tileFurnace.currentItemBurnTime);
		 */
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
			if (this.lastBuildPlan != this.tile.buildPlan) {
				icrafting.sendProgressBarUpdate(this, 2, this.tile.buildPlan);
			}
			if ((this.lastPowerStored != this.tile.getPowerStored())||this.tile.isWorking()){
				icrafting.sendProgressBarUpdate(this, 3, this.tile.getPowerStored());
			}
			
			/*
			 * if (this.lastItemBurnTime !=
			 * this.tileFurnace.currentItemBurnTime) {
			 * icrafting.sendProgressBarUpdate(this, 2,
			 * this.tileFurnace.currentItemBurnTime); }
			 */
		}

		/*
		 * this.lastCookTime = this.tileFurnace.furnaceCookTime;
		 * this.lastBurnTime = this.tileFurnace.furnaceBurnTime;
		 * this.lastItemBurnTime = this.tileFurnace.currentItemBurnTime;
		 */
		this.lastProgress = this.tile.progress;
		this.lastTotaltime = this.tile.totalTime;
		this.lastBuildPlan = this.tile.buildPlan;
		this.lastPowerStored = this.tile.getPowerStored();
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		if (id == 0) {
			this.tile.progress = value;
		}

		if (id == 1) {
			this.tile.totalTime = value;
		}

		if (id == 2) {
			this.tile.buildPlan = (byte) value;
		}
		
		if(id==3){
			this.tile.setPowerStored(value);
		}
		/*
		 * if (id == 2) { this.tileFurnace.currentItemBurnTime = value; }
		 */
	}

	@Override
	public boolean canInteractWith(EntityPlayer ply) {
		return tile.isUseableByPlayer(ply);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer ply, int id) {
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(id);

		//System.out.println("Shift+click on slot"+id);
		

			
			if(slot.getHasStack()){
				ItemStack stack1 = slot.getStack();
				stack=stack1.copy();
				if (stack != null){
					
					if (id >=0 && id<4){
						//PRESSED IN MACHINE GUI
						if (!this.mergeItemStack(stack1, 3, 39, true)) {
							return null;
						}
						slot.onSlotChange(stack1, stack);
					} else if (id >=4 && id <39){
						
						int validslot = AmmoPressTileEnt.getValidSlotForItemInMachine(stack1);
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
		
		
		/*if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (id == 3) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else if (id ==0 || id==1 || id==2) {
				// if ()
				int validslot = AmmoPressTileEnt
						.getValidSlotForItemInMachine(itemstack1);
				if (validslot >= 0) {
					if (!this.mergeItemStack(itemstack1, validslot, 1, false)) {
						return null;
					}
				}
			} else if (id >= 4 && id < 30) {
				if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
					return null;
				}
			} else if (id >= 30 && id < 39
					&& !this.mergeItemStack(itemstack1, 3, 30, false)) {
				return null;
			}
		}*/

		//return null;
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or
	 * you will crash when someone does that.
	 */
	// @Override
	/*public ItemStack transferStackInSlotOLD(EntityPlayer ply, int id) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(id);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (id == 3) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (id != 1 && id != 0 && id != 2) {
				if (FurnaceRecipes.smelting().getSmeltingResult(itemstack1) != null) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				} else if (TileEntityFurnace.isItemFuel(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				} else if (id >= 4 && id < 30) {
					if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
						return null;
					}
				} else if (id >= 30 && id < 39
						&& !this.mergeItemStack(itemstack1, 3, 30, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 4, 39, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(ply, itemstack1);
		}

		return itemstack;
	}*/

}
