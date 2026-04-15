package techguns.inventory;

import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.gui.playerinventory.SlotFood;
import techguns.gui.playerinventory.SlotTG;
import techguns.gui.playerinventory.TGPlayerInventory;
import techguns.items.TGSlotType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class TGChestContainer extends Container{

    private IInventory lowerChestInventory;
    private int numRows;

    public TGChestContainer(EntityPlayer player, IInventory lower)
    {
        this.lowerChestInventory = lower;
        int SLOTS_PER_ROW = 12;
		this.numRows = lower.getSizeInventory() / SLOTS_PER_ROW;
        lower.openInventory();
        int i = (this.numRows - 4) * 18;
        int j;
        int k;

        for (j = 0; j < this.numRows; ++j)
        {
            for (k = 0; k < SLOTS_PER_ROW; ++k)
            {
                this.addSlotToContainer(new Slot(lower, k + j * SLOTS_PER_ROW, 8 + k * 18, 18 + j * 18));
            }
        }

        for (j = 0; j < 3; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            }
        }

        for (j = 0; j < 9; ++j)
        {
            this.addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 161 + i));
        }
        
        TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(player);
        
        this.addSlotToContainer(new SlotFood(props.TG_inventory,TGPlayerInventory.SLOTS_AUTOFOOD_START,172,31+numRows*18));
		this.addSlotToContainer(new SlotFood(props.TG_inventory,TGPlayerInventory.SLOTS_AUTOFOOD_START+1,172+18,31+numRows*18));
		this.addSlotToContainer(new SlotFood(props.TG_inventory,TGPlayerInventory.SLOTS_AUTOFOOD_START+2,172+18*2,31+numRows*18));
			
		for (int m=0;m<2;m++){
			for(int l=0;l<3;l++){
				this.addSlotToContainer(new SlotTG(props.TG_inventory,TGPlayerInventory.SLOTS_AMMO_START+(m*3)+l,172+l*18,54+numRows*18+m*18,TGSlotType.AMMOSLOT));
			}
		}
        
    }

    public boolean canInteractWith(EntityPlayer p_75145_1_)
    {
        return this.lowerChestInventory.isUseableByPlayer(p_75145_1_);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer ply, int id)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(id);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (id < this.numRows * 12)
            {
                if (!this.mergeItemStack(itemstack1, this.numRows * 12, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 12, false))
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
        }

        return itemstack;
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer ply)
    {
        super.onContainerClosed(ply);
        this.lowerChestInventory.closeInventory();
    }

    /**
     * Return this chest container's lower chest inventory.
     */
    public IInventory getLowerChestInventory()
    {
        return this.lowerChestInventory;
    }

}
