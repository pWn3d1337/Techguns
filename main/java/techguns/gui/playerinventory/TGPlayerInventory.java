package techguns.gui.playerinventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;

public class TGPlayerInventory implements IInventory {
	private static final String name ="TechgunsPlayerInventory";
	
	public static final int NUMSLOTS=12;

	public static final int SLOT_FACE=0;
	public static final int SLOT_BACK=1;
	public static final int SLOTS_AUTOFOOD_START=2;
	public static final int SLOTS_AUTOFOOD_END=4;
	public static final int SLOT_AUTOHEAL=5;
	public static final int SLOTS_AMMO_START=6;
	public static final int SLOTS_AMMO_END=11;
	
	public ItemStack[] inventory = new ItemStack[NUMSLOTS];
	
	public boolean dirty=false;
	public EntityPlayer player;
	
	public TGPlayerInventory(EntityPlayer player) {
		this.player=player;
	}

	
	
	public void saveNBTData(NBTTagCompound tags) {
		NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.inventory.length; ++i)
        {
            if (this.inventory[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.inventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        tags.setTag(name, nbttaglist);
	}
	
	public void loadNBTData(NBTTagCompound tags){
		NBTTagList nbttaglist = tags.getTagList(name, 10);
	    this.inventory = new ItemStack[NUMSLOTS];
	
	    for (int i = 0; i < nbttaglist.tagCount(); ++i)
	    {
	        NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
	        byte b0 = nbttagcompound1.getByte("Slot");
	
	        if (b0 >= 0 && b0 < NUMSLOTS)
	        {
	            this.inventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
	        }
	    }
	}

	@Override
	public int getSizeInventory() {
		return NUMSLOTS;
	}

	@Override
	public ItemStack getStackInSlot(int slotid) {
		return slotid >= 0 && slotid < this.inventory.length ? this.inventory[slotid] : null;
	}

	@Override
	public ItemStack decrStackSize(int slotid, int amount) {
		if (this.inventory[slotid] != null)
        {
            ItemStack itemstack;

            if (this.inventory[slotid].stackSize <= amount)
            {
                itemstack = this.inventory[slotid];
                this.inventory[slotid] = null;
                this.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = this.inventory[slotid].splitStack(amount);

                if (this.inventory[slotid].stackSize == 0)
                {
                    this.inventory[slotid] = null;
                }

                this.markDirty();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}


	
	@Override
	public ItemStack getStackInSlotOnClosing(int slotid) {
		 if (this.inventory[slotid] != null)
	        {
	            ItemStack itemstack = this.inventory[slotid];
	            this.inventory[slotid] = null;
	            return itemstack;
	        }
	        else
	        {
	            return null;
	        }
	}

	@Override
	public void setInventorySlotContents(int slotid, ItemStack itemstack) {
	  	this.inventory[slotid] = itemstack;

        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
        {
            itemstack.stackSize = this.getInventoryStackLimit();
        }

        if (slotid == SLOT_FACE){
        	player.getDataWatcher().updateObject(TechgunsExtendedPlayerProperties.DATA_WATCHER_ID_FACESLOT, this.inventory[slotid]);
        } else if (slotid == SLOT_BACK){
        	player.getDataWatcher().updateObject(TechgunsExtendedPlayerProperties.DATA_WATCHER_ID_BACKSLOT, this.inventory[slotid]);
        }
        
        //this.markDirty();
	}

	@Override
	public String getInventoryName() {
		return this.name;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
		//System.out.println("Marked inv as dirty");
		this.dirty=true;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
		/*if(dirty&& !this.player.worldObj.isRemote){
			//System.out.println("Dirty and closed!");
			
			TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(player);
			if (props!=null){
				TGPackets.network.sendToDimension(new PacketTGExtendedPlayerSync(props, false), player.dimension);
				//TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(props, true), (EntityPlayerMP) player);
			}
			
		}*/
	}

	@Override
	public boolean isItemValidForSlot(int slotid, ItemStack itemstack) {
		return true;
	}
	
}
