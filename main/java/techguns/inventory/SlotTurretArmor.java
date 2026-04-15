package techguns.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import techguns.items.TGSlotType;
import techguns.items.armors.ITGSpecialSlot;

public class SlotTurretArmor extends Slot {

	public SlotTurretArmor(IInventory inv, int index, int posx, int posy) {
		super(inv, index, posx, posy);
	}

	@Override
	public boolean isItemValid(ItemStack item) {
		if (item!=null && item.getItem() instanceof ITGSpecialSlot){
			ITGSpecialSlot slot = (ITGSpecialSlot) item.getItem();
			return slot.getSlot(item)==TGSlotType.TURRETARMOR;
		}
		return false;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	
	
}
