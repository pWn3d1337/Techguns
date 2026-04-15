package techguns.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import techguns.TGItems;

public class SlotMachineUpgrade extends Slot {

	public SlotMachineUpgrade(IInventory inv, int index, int posx, int posy) {
		super(inv, index, posx,posy);
	}

	@Override
	public boolean isItemValid(ItemStack item) {
		return (item !=null && item.getItem()==TGItems.sharedItem && item.getItemDamage()==TGItems.machineStackUpgrade.getItemDamage());
	}

	@Override
	public int getSlotStackLimit() {
		return 7;
	}	

	
}
