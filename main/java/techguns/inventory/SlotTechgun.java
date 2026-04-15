package techguns.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import techguns.items.guns.GenericGun;

public class SlotTechgun extends Slot {

	public SlotTechgun(IInventory inv, int index, int posx, int posy) {
		super(inv, index, posx, posy);
	}

	@Override
	public boolean isItemValid(ItemStack item) {
		if (item!=null && item.getItem() instanceof GenericGun){
			GenericGun gun = (GenericGun) item.getItem();
			return !gun.isAkimbo();
		}
		return false;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}
	
	
}
