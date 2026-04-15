package techguns.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.gui.playerinventory.SlotTG;
import techguns.gui.playerinventory.TGPlayerInventory;
import techguns.items.TGSlotType;
import techguns.tileentities.CamoBenchTileEnt;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CamoBenchContainer extends Container {
	protected CamoBenchTileEnt tile;
	
	public CamoBenchContainer(InventoryPlayer player, CamoBenchTileEnt ent) {
		this.tile = ent;

		this.addSlotToContainer(new Slot(ent, 0, 21, 18));
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
		
		for (i = 0; i < 4; ++i) {
			this.addSlotToContainer(new SlotArmor(player, 39-i, 96+(i*19), 18,i,player.player));
		}
		
		TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(player.player);
		if (props!=null){
			
			this.addSlotToContainer(new SlotTG(props.TG_inventory, TGPlayerInventory.SLOT_BACK, 75, 18, TGSlotType.BACKSLOT));			
			this.addSlotToContainer(new SlotTG(props.TG_inventory, TGPlayerInventory.SLOT_FACE, 56, 18, TGSlotType.FACESLOT));	
			
		}
		

	}

	@Override
	public void addCraftingToCrafters(ICrafting craft) {
	}
	
	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	@Override
	public void detectAndSendChanges() {
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
	}

	@Override
	public boolean canInteractWith(EntityPlayer ply) {
		return tile.isUseableByPlayer(ply);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotid) {
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotid);
		
		if(slot.getHasStack()){
			ItemStack stack1 = slot.getStack();
			stack=stack1.copy();
			if (stack != null){
		
				//camoslot pressed
				if(slotid==0){
					/*if ((((Slot) this.inventorySlots.get(0)).getStack())==null){
						//nothing to do here
					} else {*/
						if(stack1.getItem() instanceof ItemArmor){
							int type=((ItemArmor)stack1.getItem()).armorType;
							//put armor back in player armor inventory
							if (!this.mergeItemStack(stack1, 37+type, 37+type+1, true)) {
								return null;
							}
							slot.onSlotChange(stack1, stack);
						} else {
						//put item back in player inventory
							if (!this.mergeItemStack(stack1, 1, 37, true)) {
								return null;
							}
							slot.onSlotChange(stack1, stack);
						}
					//}
				} else if(slotid>=1) {
					//pressed in player gui
					if( (((Slot) this.inventorySlots.get(0)).getStack())==null) {
						//System.out.println("Try put in slot 0");
						if(!this.mergeItemStack(stack1, 0, 0+1, false)){
							return null;
						}
						slot.onSlotChange(stack1, stack);
					} else {
						//System.out.println("SLOT 0 FULL");
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

				slot.onPickupFromSlot(player, stack1);
			}
		}
		return stack;
	}

	
	
}
