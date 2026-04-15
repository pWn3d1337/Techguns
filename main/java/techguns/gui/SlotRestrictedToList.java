package techguns.gui;

import java.util.ArrayList;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import techguns.inventory.ItemStackOreDict;

public class SlotRestrictedToList extends Slot {

	protected ArrayList<ItemStackOreDict> allowedItems;
	
	public SlotRestrictedToList(IInventory inv, int index, int posx, int posy, ArrayList<ItemStackOreDict> allowedItems) {
		super(inv, index, posx,posy);
		this.allowedItems=allowedItems;
	}
	
	@Override
	public boolean isItemValid(ItemStack item) {
		for(int i=0;i<allowedItems.size();i++){
			ItemStackOreDict stack  = allowedItems.get(i);
			
			if (stack.isEqualWithOreDict(item)){
				return true;
			}
		}
		return false;
	}
	
}
