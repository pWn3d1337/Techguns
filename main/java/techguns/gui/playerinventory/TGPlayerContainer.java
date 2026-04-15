package techguns.gui.playerinventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import techguns.inventory.SlotArmor;
import techguns.items.TGSlotType;

public class TGPlayerContainer extends Container {

	private static final int DEFAULT_INV_ARMOR_START=TGPlayerInventory.NUMSLOTS;
	private static final int DEFAULT_INV_START=DEFAULT_INV_ARMOR_START+4;
	private static final int DEFAULT_INV_HOTBAR_START=DEFAULT_INV_START+27;
	
	private static final int FOOD_SLOTS_COUNT=3;
	private static final int AMMO_SLOTS_COUNT=6;
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	public TGPlayerContainer(EntityPlayer player, InventoryPlayer inv, TGPlayerInventory tgplayerinv){
		
		//custom slots:
		this.addSlotToContainer(new SlotTG(tgplayerinv,TGPlayerInventory.SLOT_FACE, 80, 8,TGSlotType.FACESLOT));

		this.addSlotToContainer(new SlotTG(tgplayerinv, TGPlayerInventory.SLOT_BACK, 80, 26,TGSlotType.BACKSLOT));

		this.addSlotToContainer(new SlotFood(tgplayerinv,TGPlayerInventory.SLOTS_AUTOFOOD_START,116,24));
		this.addSlotToContainer(new SlotFood(tgplayerinv,TGPlayerInventory.SLOTS_AUTOFOOD_START+1,116+18,24));
		this.addSlotToContainer(new SlotFood(tgplayerinv,TGPlayerInventory.SLOTS_AUTOFOOD_START+2,116+18*2,24));
		
		this.addSlotToContainer(new SlotTG(tgplayerinv,TGPlayerInventory.SLOT_AUTOHEAL,96,62,TGSlotType.HEALSLOT));
		
		for (int i=0;i<2;i++){
			for(int j=0;j<3;j++){
				this.addSlotToContainer(new SlotTG(tgplayerinv,TGPlayerInventory.SLOTS_AMMO_START+(i*3)+j,116+j*18,44+i*18,TGSlotType.AMMOSLOT));
			}
		}
		
		
		//Default Slots:
		
		//Armor:
		int i,j;
		for (i = 0; i < 4; ++i)
        {
            final int k = i;
            this.addSlotToContainer(new SlotArmor(inv, inv.getSizeInventory() - 1 - i, 8, 8 + i * 18, k, player));
        }
		//Inventory:
        for (i = 0; i < 3; ++i)
        {
            for (j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inv, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }
		
		//Hotbar:

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inv, i, 8 + i * 18, 142));
        }
		
        //System.out.println("SLOTS:"+this.inventorySlots.size());
	}

	/**
	 * Get the valid slot in the TG inventory tab
	 * @param stack
	 * @return
	 */
	private int[] getValidSlotForIteminInv(ItemStack stack){
		int[] ret = new int[2];
		ret[0]=-1;
		ret[1]=1;
		
		for (int i=0;i<TGPlayerInventory.NUMSLOTS;++i){
			Object o=this.inventorySlots.get(i);
			Slot s = (Slot) o;
			if(s.isItemValid(stack)){
				ret[0]=i;
				
				if(s instanceof SlotTG){
					SlotTG stg = (SlotTG) s;
					if(stg.getType()==TGSlotType.AMMOSLOT){
						ret[1]=AMMO_SLOTS_COUNT;
					} 
				} else if (s instanceof SlotFood){
					ret[1]=FOOD_SLOTS_COUNT;
				}
				
				return ret;
			}
		
		}
		
		
		return ret;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int id) {
		  int o = TGPlayerInventory.NUMSLOTS-5; //offset compared to Default inv, -5 craftmatrix + TG inventory size

		  
	      ItemStack itemstack = null;
	        Slot slot = (Slot)this.inventorySlots.get(id);

	        if (slot != null && slot.getHasStack())
	        {
	            ItemStack itemstack1 = slot.getStack();
	            itemstack = itemstack1.copy();

	            if (id < TGPlayerInventory.NUMSLOTS) {
	            	//CLICKED IN TG INVENTORY
	            	if (!this.mergeItemStack(itemstack1, DEFAULT_INV_START, DEFAULT_INV_START+36, true)) {
						return null;
					}
					slot.onSlotChange(itemstack1, itemstack);
	            	
	            }	
	            /*else if (id >= TGPlayerInventory.NUMSLOTS && id < TGPlayerInventory.NUMSLOTS+4)
	            {
	                if (!this.mergeItemStack(itemstack1, 9+o, 45+o, false))
	                {
	                    return null;
	                }
	            }*/
	            //Try merge Armor
	            else if (itemstack.getItem() instanceof ItemArmor && !((Slot)this.inventorySlots.get(TGPlayerInventory.NUMSLOTS + ((ItemArmor)itemstack.getItem()).armorType)).getHasStack())
	            {
	                int j = TGPlayerInventory.NUMSLOTS + ((ItemArmor)itemstack.getItem()).armorType;

	                if (!this.mergeItemStack(itemstack1, j, j + 1, false))
	                {
	                    return null;
	                }
	            } else if ( (id >= DEFAULT_INV_START && id < DEFAULT_INV_START+36) && hasTGSpecialSlot(itemstack) ){
	            	
	            	//Merge into tG inventory
	            	int slots[] = this.getValidSlotForIteminInv(itemstack1);
					int validslot=slots[0];
					int num=slots[1];
	            	if(!this.mergeItemStack(itemstack1, validslot, validslot+num, false)){
						return null;
					}
					slot.onSlotChange(itemstack1, itemstack);
	            }
	            else if (id >= 9+o && id < 36+o)
	            {
	                if (!this.mergeItemStack(itemstack1, 36+o, 45+o, false))
	                {
	                    return null;
	                }
	            }
	            else if (id >= 36+o && id < 45+o)
	            {
	                if (!this.mergeItemStack(itemstack1, 9+o, 36+o, false))
	                {
	                    return null;
	                }
	            }
	            else if (!this.mergeItemStack(itemstack1, 9+o, 45+o, false))
	            {
	                return null;
	            }

	            if (itemstack1.stackSize == 0)
	            {
	                slot.putStack((ItemStack)null);
	            }
	            else
	            {
	                slot.onSlotChanged();
	            }

	            if (itemstack1.stackSize == itemstack.stackSize)
	            {
	                return null;
	            }

	            slot.onPickupFromSlot(player, itemstack1);
	        }

	        return itemstack;
	}
	
	private boolean hasTGSpecialSlot(ItemStack stack){
		return this.getValidSlotForIteminInv(stack)[0]>=0;
	}
	
	/*@Override
	public ItemStack transferStackInSlot(EntityPlayer ply, int id) {
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(id);

		//System.out.println("Pressed ID:"+id);
		
			if(slot.getHasStack()){
				ItemStack stack1 = slot.getStack();
				stack=stack1.copy();
				if (stack != null){
					
					if (id >=0 && id<DEFAULT_INV_START){
						//PRESSED IN custom slot or armor slot, try merge player inv
						if (!this.mergeItemStack(stack1, DEFAULT_INV_START, DEFAULT_INV_START+36, true)) {
							return null;
						}
						slot.onSlotChange(stack1, stack);
					} else if (id <DEFAULT_INV_START+36){
						
						int slots[] = this.getValidSlotForIteminInv(stack1);
						int validslot=slots[0];
						int num=slots[1];
						//System.out.println("put it in slot"+validslot);
						if (validslot >=0){
							//System.out.println("Validslot:"+validslot);
							//System.out.println("Slotnum:"+num);
							if(!this.mergeItemStack(stack1, validslot, validslot+num, false)){
								return null;
							}
							slot.onSlotChange(stack1, stack);
							
						} else {
							slot.onSlotChange(stack1, stack);
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

	}*/
	
	

}
