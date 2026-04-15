package techguns.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import techguns.tileentities.RepairBenchTileEnt;

public class RepairBenchContainer extends Container {
	protected RepairBenchTileEnt tile;
	
	public RepairBenchContainer(InventoryPlayer player, RepairBenchTileEnt ent) {
		this.tile = ent;
		
		int i;
		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(ent, i, 8 + i * 18, 57));
		}
		
		
		//Player slots
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9,
						8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
		}
		
		for (i = 0; i < 4; ++i) {
			this.addSlotToContainer(new SlotArmor(player, 39-i, 89+(i*20), 18,i,player.player));
		}
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotid) {
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotid);
		
		//System.out.println("SLOTID:"+slotid);
		
		if(slot.getHasStack()){
			ItemStack stack1 = slot.getStack();
			stack=stack1.copy();
			if (stack != null){
		
				//g
				if(slotid<9){
					//Pressed in machine gui, put in player inv
					
						if(!this.mergeItemStack(stack1, 9, 9+36, false)){
							return null;
						}
						slot.onSlotChange(stack1, stack);
					
				} else if(slotid>9 && slotid<9+36) {
					//pressed in player gui
					
					if(stack1.getItem() instanceof ItemArmor){
						int type=((ItemArmor)stack1.getItem()).armorType;
						//put armor into player armor inventory
						if (!this.mergeItemStack(stack1, 9+36+type, 9+36+type+1, true)) {
							return null;
						}
						slot.onSlotChange(stack1, stack);
					} else {
						if(!this.mergeItemStack(stack1, 0, 9, false)){
							return null;
						}
						slot.onSlotChange(stack1, stack);
					}
				} else if (slotid>=9+36){
					//pressed in armor gui
				
				//put item back in player inventory
					if (!this.mergeItemStack(stack1, 9, 9+36, true)) {
						return null;
					}
					slot.onSlotChange(stack1, stack);

				}
				
				if (stack1.stackSize == 0) {
					slot.putStack((ItemStack) null);
				} else {
					slot.onSlotChanged();
				}

				if (stack1.stackSize == stack.stackSize) {
					return null;
				}

				slot.onPickupFromSlot(player, stack1);
			}
		}
		return stack;
	}
}
