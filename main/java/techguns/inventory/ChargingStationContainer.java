package techguns.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import techguns.gui.SlotMachineUpgrade;
import techguns.gui.SlotOutput;
import techguns.tileentities.ChargingStationTileEnt;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ChargingStationContainer extends Container {
	ChargingStationTileEnt tile;

	private int lastProgress = 0;
	private int lastTotaltime = 0;
	private int lastPowerStored = 0;
	
	public ChargingStationContainer(InventoryPlayer player, ChargingStationTileEnt ent) {
		this.tile = ent;
		
		this.addSlotToContainer(new Slot(ent, 0, 19, 17));
		this.addSlotToContainer(new SlotOutput(ent, 1, 68, 17));
		this.addSlotToContainer(new SlotMachineUpgrade(ent, 2, 150, 50));
		
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
	public boolean canInteractWith(EntityPlayer ply) {
		return tile.isUseableByPlayer(ply);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer ply, int id) {
		int HIGHEST_SLOT=2; //index +1
		int MAXSLOTS = HIGHEST_SLOT+36+1; //36 = inventory size
		
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(id);

			if(slot.getHasStack()){
				ItemStack stack1 = slot.getStack();
				stack=stack1.copy();
				if (stack != null){
					
					if (id >=0 && id<=HIGHEST_SLOT){
						if (!this.mergeItemStack(stack1, HIGHEST_SLOT+1, MAXSLOTS, true)) {
							return null;
						}
						slot.onSlotChange(stack1, stack);
					} else if (id >HIGHEST_SLOT && id <MAXSLOTS){
						
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

	@Override
	public void addCraftingToCrafters(ICrafting craft) {
		super.addCraftingToCrafters(craft);
		craft.sendProgressBarUpdate(this, 0, this.tile.progress);
		craft.sendProgressBarUpdate(this, 1, this.tile.totalTime);
		craft.sendProgressBarUpdate(this, 2, this.tile.getPowerStored()/100);
	
	}
	
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
		}

		this.lastProgress = this.tile.progress;
		this.lastTotaltime = this.tile.totalTime;
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
			this.tile.setPowerStored(value*100);
		}
	}
}
