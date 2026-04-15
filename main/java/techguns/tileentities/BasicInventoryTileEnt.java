package techguns.tileentities;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class BasicInventoryTileEnt extends TileEntity implements
		ISidedInventory {
	protected String name = null;
	protected ItemStack[] content;
	
	protected double useRangeSquared = 64.0D;
	
	
	public BasicInventoryTileEnt(int slots){
		content = new ItemStack[slots];
	}
	
	//How the block is rotated
	public byte rotation=0;
	private static Random random = new Random();
	
	/**
	 * Called when a gui button is clicked, does nothing on default, override in subclasss
	 * @param id
	 * @param ply
	 */
	public void buttonClicked(int id, EntityPlayer ply){}
	
	/**
	 * Sets all inventory to null, called when wrenched, after inv is saved to nbt tags
	 */
	public void emptyContent(){
		for (int i=0;i<content.length;++i){
			content[i]=null;
		}
	}
	
	/**
	 * Throw out inventory to world
	 * mostly copied from BlockChest
	 */
	public void onBlockBreak(){
		for (int i1 = 0; i1 < this.getSizeInventory(); ++i1)
        {
            ItemStack itemstack = this.getStackInSlot(i1);

            if (itemstack != null)
            {
                float f = this.random.nextFloat() * 0.8F + 0.1F;
                float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                EntityItem entityitem;

                for (float f2 = this.random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; this.worldObj.spawnEntityInWorld(entityitem))
                {
                    int j1 = this.random.nextInt(21) + 10;

                    if (j1 > itemstack.stackSize)
                    {
                        j1 = itemstack.stackSize;
                    }

                    itemstack.stackSize -= j1;
                    entityitem = new EntityItem(this.worldObj, (double)((float)this.xCoord + f), (double)((float)this.yCoord + f1), (double)((float)this.zCoord + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                    float f3 = 0.05F;
                    entityitem.motionX = (double)((float)this.random.nextGaussian() * f3);
                    entityitem.motionY = (double)((float)this.random.nextGaussian() * f3 + 0.2F);
                    entityitem.motionZ = (double)((float)this.random.nextGaussian() * f3);

                    if (itemstack.hasTagCompound())
                    {
                        entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                    }
                }
            }
        }

        this.worldObj.func_147453_f(xCoord, yCoord, zCoord, null); //4th param seems to be pointless
	}
	
	
	@Override
	public int getSizeInventory() {
		return content.length;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		return content[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int number) {
		 if (this.content[slot] != null)
	        {
	            ItemStack itemstack;

	            if (this.content[slot].stackSize <= number)
	            {
	                itemstack = this.content[slot];
	                this.content[slot] = null;
	                return itemstack;
	            }
	            else
	            {
	                itemstack = this.content[slot].splitStack(number);

	                if (this.content[slot].stackSize == 0)
	                {
	                    this.content[slot] = null;
	                }

	                return itemstack;
	            }
	        }
	        else
	        {
	            return null;
	        }
	}


	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.content[slot] != null)
       {
           ItemStack itemstack = this.content[slot];
           this.content[slot] = null;
           return itemstack;
       }
       else
       {
           return null;
       }
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack item) {
		content[slot] = item;

       if (item != null && item.stackSize > this.getInventoryStackLimit())
       {
           item.stackSize = this.getInventoryStackLimit();
       }
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tags) {
		super.readFromNBT(tags);
        this.readEntityDataFromNBT(tags);
	}

	@Override
	public void writeToNBT(NBTTagCompound tags) {
		super.writeToNBT(tags);
		this.writeEntityDataToNBT(tags);
	}
	
	public void readEntityDataFromNBT(NBTTagCompound tags){
		if (tags.hasKey("CustomName", 8))
        {
            this.name = tags.getString("CustomName");
        }
		NBTTagList nbttaglist = tags.getTagList("Items", 10);
        this.content = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.content.length)
            {
                this.content[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        this.rotation= tags.getByte("rotation");
	}
	
	public void writeEntityDataToNBT(NBTTagCompound tags){
		 if (this.hasCustomInventoryName())
	        {
	            tags.setString("CustomName", this.name);
	        }
		 
		 	NBTTagList nbttaglist = new NBTTagList();

	        for (int i = 0; i < this.content.length; ++i)
	        {
	            if (this.content[i] != null)
	            {
	                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
	                nbttagcompound1.setByte("Slot", (byte)i);
	                this.content[i].writeToNBT(nbttagcompound1);
	                nbttaglist.appendTag(nbttagcompound1);
	                //System.out.println("Wrote to slot"+i+":"+this.content[i].toString());
	            }
	        }
	        tags.setTag("Items", nbttaglist);
	        tags.setByte("rotation", this.rotation);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tags= new NBTTagCompound();
		this.writeToNBT(tags);
		S35PacketUpdateTileEntity pkt = new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, tags);
		return pkt;
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
		//System.out.println("Got a update Paket");
	}
	
	public void rotate(){
		this.rotation++;
		if(this.rotation>=4){
			this.rotation=0;
		}
		if(!this.worldObj.isRemote){
			this.needUpdate();
		}
	}
	
    /**
     * Returns if the inventory is named
     */
	@Override
    public boolean hasCustomInventoryName()
    {
        return this.name != null && this.name.length() > 0;
    }
    
	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * called serverside when this tileent should send out updated to client
	 */
	public void needUpdate(){
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		this.markDirty();
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= this.useRangeSquared;
	}
	
	/**
	 * Helper method to check if ItemStack output can be put into slot
	 * @param output
	 * @param slot
	 * @return
	 */
	 protected boolean canOutput(ItemStack output,int slot){
		 if (output==null){
			 return true;
		 }
		if (this.content[slot]==null){
			return output.stackSize<=64;
		} else {
			if (this.content[slot].isItemEqual(output) && (this.content[slot].stackSize+output.stackSize <= this.content[slot].getMaxStackSize())) {
				return true;
			} else {
				return false;
			}
		}
		}

	public ItemStack[] getContent() {
		return content;
	}
	
	 
}
