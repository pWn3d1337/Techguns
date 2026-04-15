package techguns.gui.playerinventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import techguns.items.TGSlotType;
import techguns.items.armors.ITGSpecialSlot;

public class SlotTG extends Slot {
	private final TGSlotType type;
	
	public SlotTG(IInventory inv, int index, int posx, int posy, TGSlotType type) {
		super(inv, index, posx, posy);
		this.type=type;
	}
	
	@Override
	public boolean isItemValid(ItemStack itemstack) {
		if (itemstack!=null && itemstack.getItem() instanceof ITGSpecialSlot){
			ITGSpecialSlot slot = (ITGSpecialSlot) itemstack.getItem();
			return slot.getSlot(itemstack)==type;
		}
		return TGSlotType.NORMAL==type;
	}

	public TGSlotType getType() {
		return type;
	}

	
}
