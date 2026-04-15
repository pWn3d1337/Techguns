package techguns.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotOutput extends Slot {

	public SlotOutput(IInventory inv, int index, int posx, int posy) {
		super(inv, index, posx,posy);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}

	
}
