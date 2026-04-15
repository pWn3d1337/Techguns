package techguns.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import techguns.gui.SlotMachineUpgrade;
import techguns.gui.SlotOutput;
import techguns.gui.SlotRestrictedToList;
import techguns.tileentities.FabricatorTileEntMaster;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FabricatorContainer extends Container {
	FabricatorTileEntMaster tile;
	
	private int lastProgress = 0;
	private int lastTotaltime = 0;
	private int lastPowerStored = 0;
	
	public FabricatorContainer(InventoryPlayer player, FabricatorTileEntMaster ent) {
		this.tile = ent;

		this.addSlotToContainer(new Slot(ent, FabricatorTileEntMaster.SLOT_INPUT, 19, 17));
		this.addSlotToContainer(new SlotRestrictedToList(ent, FabricatorTileEntMaster.SLOT_WIRES, 47, 17,FabricatorRecipe.items_wireslot));
		this.addSlotToContainer(new SlotRestrictedToList(ent, FabricatorTileEntMaster.SLOT_POWDER, 68, 17,FabricatorRecipe.items_powderslot));
		this.addSlotToContainer(new SlotRestrictedToList(ent, FabricatorTileEntMaster.SLOT_PLATE, 89, 17,FabricatorRecipe.items_plateslot));
		
		this.addSlotToContainer(new SlotOutput(ent, FabricatorTileEntMaster.SLOT_OUTPUT, 116, 50));
		
		this.addSlotToContainer(new SlotMachineUpgrade(ent, FabricatorTileEntMaster.SLOT_UPGRADE, 150, 50));
		

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
		} else if (id == 1) {
			this.tile.totalTime = value;
		} else if (id==2){
			this.tile.setPowerStored(value*100);
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer ply, int id) {
		int MAXSLOTS = FabricatorTileEntMaster.SLOT_UPGRADE+36+1; //36 = inventory size
		
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(id);

			if(slot.getHasStack()){
				ItemStack stack1 = slot.getStack();
				stack=stack1.copy();
				if (stack != null){
					
					if (id >=0 && id<=FabricatorTileEntMaster.SLOT_UPGRADE){
						if (!this.mergeItemStack(stack1, FabricatorTileEntMaster.SLOT_UPGRADE+1, MAXSLOTS, true)) {
							return null;
						}
						slot.onSlotChange(stack1, stack);
					} else if (id >FabricatorTileEntMaster.SLOT_UPGRADE && id <MAXSLOTS){
						
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
