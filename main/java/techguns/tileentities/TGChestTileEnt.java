package techguns.tileentities;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import techguns.blocks.BlockTGChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Contains a lot of code from TileEntityChest
 *
 */
public class TGChestTileEnt extends BasicInventoryTileEnt { //implements ITGTileEntSecurity {

	public boolean adjacentChestChecked;
    /** Contains the chest tile located adjacent to this one (if any) */
    public TGChestTileEnt adjacentChestZNeg;
    /** Contains the chest tile located adjacent to this one (if any) */
    public TGChestTileEnt adjacentChestXPos;
    /** Contains the chest tile located adjacent to this one (if any) */
    public TGChestTileEnt adjacentChestXNeg;
    /** Contains the chest tile located adjacent to this one (if any) */
    public TGChestTileEnt adjacentChestZPos;
    /** The current angle of the lid (between 0 and 1) */
    public float lidAngle;
    /** The angle of the lid last tick */
    public float prevLidAngle;
    /** The number of players currently using this chest */
    public int numPlayersUsing;
    /** Server sync counter (once per 20 ticks) */
    protected int ticksSinceSync;
    protected int cachedChestType;
	
    
    protected static final int CHEST_SIZE=36;
    
    //public Block chestBlockType;
    //public byte chestBlockMeta;
    
	protected UUID owner;
        
    private int[] accessibleSlots;
    
    public TGChestTileEnt()
    {
    	super(CHEST_SIZE);
        this.cachedChestType = -1;
        initArrays();
    }

    @SideOnly(Side.CLIENT)
    public TGChestTileEnt(int i)
    {
    	super(CHEST_SIZE);
        this.cachedChestType = i;
        initArrays();
    }
    
    private void initArrays(){
    	this.accessibleSlots = new int[this.content.length];
    	for (int i=0;i<this.content.length; i++){
    		this.accessibleSlots[i]=i;
    	}
    }
    
    
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return this.accessibleSlots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return true;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return true;
	}

	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
	}

	@Override
	public String getInventoryName() {
		int type =this.getCachedChestType();
		if (type==1){
			return "techguns.container.tgchest_weapon";
		}
		return  "techguns.container.tgchest";
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return true;
	}

	public void writeEntityDataToNBTForItemStack(NBTTagCompound tags){
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
	        if(nbttaglist.tagCount()>0){
	        	tags.setTag("Items", nbttaglist);
	        }
	}
	
	/**
	 * Sets the owner of this tileent on placing
	 * @param ply
	 */
	/*@Override
	public void setOwner(EntityPlayer ply){
		UUID id = ply.getGameProfile().getId();
		if (id!=null){
			this.owner=id;
			this.security=0;
			//this.ownerDisplayName=ply.getGameProfile().getName();
		}
	}
	
	public void setOwnerSec(UUID id, byte sec){
			this.owner=id;
			this.security=sec;
	}

	@Override
	public boolean isOwnedByPlayer(EntityPlayer ply){
		if(this.owner==null){
			return false;
		}
		return this.owner.equals(ply.getGameProfile().getId());
	}
	
	@Override
	public UUID getOwner(){
		return this.owner;
	}*/
	
	/**
	 * 0 - everyone
	 * 1 - friends if FTButilities installed, otherwise like 0
	 * 2 - owner only
	 * @return
	 */
/*	@Override
	public byte getSecurity(){
		return this.security;
	}*/


/*	@Override
	public void readEntityDataFromNBT(NBTTagCompound tags) {
		super.readEntityDataFromNBT(tags);
		
		this.security = tags.getByte("security");
		String own = tags.getString("owner");
        if(own!=null && !own.equals("")){
        	try {
        		this.owner = UUID.fromString(own);
        		//this.ownerDisplayName = tags.getString("ownerName");
        	} catch (IllegalArgumentException e){
        		this.owner = null;
        		System.out.println("Error Parsing Owner, machine is not owned anymore.");
        	}
        }
	}

	@Override
	public void writeEntityDataToNBT(NBTTagCompound tags) {
		super.writeEntityDataToNBT(tags);
		 if (this.owner != null){
	        tags.setByte("security", this.security);
	        tags.setString("owner", this.owner.toString());
        }
	}*/

	/**
     * Causes the TileEntity to reset all it's cached values for it's container Block, metadata and in the case of
     * chests, the adjacent chest check
     */
    public void updateContainingBlockInfo()
    {
        super.updateContainingBlockInfo();
        this.adjacentChestChecked = false;
    }

    private void checkChestInDir(TGChestTileEnt chest, int dir)
    {
        if (chest.isInvalid())
        {
            this.adjacentChestChecked = false;
        }
        else if (this.adjacentChestChecked)
        {
            switch (dir)
            {
                case 0:
                    if (this.adjacentChestZPos != chest)
                    {
                        this.adjacentChestChecked = false;
                    }

                    break;
                case 1:
                    if (this.adjacentChestXNeg != chest)
                    {
                        this.adjacentChestChecked = false;
                    }

                    break;
                case 2:
                    if (this.adjacentChestZNeg != chest)
                    {
                        this.adjacentChestChecked = false;
                    }

                    break;
                case 3:
                    if (this.adjacentChestXPos != chest)
                    {
                        this.adjacentChestChecked = false;
                    }
            }
        }
    }
	
    /**
     * Performs the check for adjacent chests to determine if this chest is double or not.
     */
    public void checkForAdjacentChests()
    {
        if (!this.adjacentChestChecked)
        {
            this.adjacentChestChecked = true;
            this.adjacentChestZNeg = null;
            this.adjacentChestXPos = null;
            this.adjacentChestXNeg = null;
            this.adjacentChestZPos = null;

            if (this.checkChestBlockType(this.xCoord - 1, this.yCoord, this.zCoord))
            {
                this.adjacentChestXNeg = (TGChestTileEnt)this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
            }

            if (this.checkChestBlockType(this.xCoord + 1, this.yCoord, this.zCoord))
            {
                this.adjacentChestXPos = (TGChestTileEnt)this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
            }

            if (this.checkChestBlockType(this.xCoord, this.yCoord, this.zCoord - 1))
            {
                this.adjacentChestZNeg = (TGChestTileEnt)this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
            }

            if (this.checkChestBlockType(this.xCoord, this.yCoord, this.zCoord + 1))
            {
                this.adjacentChestZPos = (TGChestTileEnt)this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
            }

            if (this.adjacentChestZNeg != null)
            {
                this.adjacentChestZNeg.checkChestInDir(this, 0);
            }

            if (this.adjacentChestZPos != null)
            {
                this.adjacentChestZPos.checkChestInDir(this, 2);
            }

            if (this.adjacentChestXPos != null)
            {
                this.adjacentChestXPos.checkChestInDir(this, 1);
            }

            if (this.adjacentChestXNeg != null)
            {
                this.adjacentChestXNeg.checkChestInDir(this, 3);
            }
        }
    }
    
    private boolean checkChestBlockType(int x, int y, int z)
    {
        if (this.worldObj == null)
        {
            return false;
        }
        else
        {
            Block block = this.worldObj.getBlock(x, y, z);
            return block instanceof BlockTGChest && ((BlockTGChest)block).field1 == this.getCachedChestType();
        }
    }
    
    public void updateEntity()
    {
        super.updateEntity();
        this.checkForAdjacentChests();
        ++this.ticksSinceSync;
        float f;

        if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0)
        {
            this.numPlayersUsing = 0;
            f = 5.0F;
            List list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox((double)((float)this.xCoord - f), (double)((float)this.yCoord - f), (double)((float)this.zCoord - f), (double)((float)(this.xCoord + 1) + f), (double)((float)(this.yCoord + 1) + f), (double)((float)(this.zCoord + 1) + f)));
            Iterator iterator = list.iterator();

            while (iterator.hasNext())
            {
                EntityPlayer entityplayer = (EntityPlayer)iterator.next();

                if (entityplayer.openContainer instanceof ContainerChest)
                {
                    IInventory iinventory = ((ContainerChest)entityplayer.openContainer).getLowerChestInventory();

                    if (iinventory == this || iinventory instanceof InventoryLargeChest && ((InventoryLargeChest)iinventory).isPartOfLargeChest(this))
                    {
                        ++this.numPlayersUsing;
                    }
                }
            }
        }

        this.prevLidAngle = this.lidAngle;
        f = 0.1F;
        double d2;

        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null)
        {
            double d1 = (double)this.xCoord + 0.5D;
            d2 = (double)this.zCoord + 0.5D;

            if (this.adjacentChestZPos != null)
            {
                d2 += 0.5D;
            }

            if (this.adjacentChestXPos != null)
            {
                d1 += 0.5D;
            }

            this.worldObj.playSoundEffect(d1, (double)this.yCoord + 0.5D, d2, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F)
        {
            float f1 = this.lidAngle;

            if (this.numPlayersUsing > 0)
            {
                this.lidAngle += f;
            }
            else
            {
                this.lidAngle -= f;
            }

            if (this.lidAngle > 1.0F)
            {
                this.lidAngle = 1.0F;
            }

            float f2 = 0.5F;

            if (this.lidAngle < f2 && f1 >= f2 && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null)
            {
                d2 = (double)this.xCoord + 0.5D;
                double d0 = (double)this.zCoord + 0.5D;

                if (this.adjacentChestZPos != null)
                {
                    d0 += 0.5D;
                }

                if (this.adjacentChestXPos != null)
                {
                    d2 += 0.5D;
                }

                this.worldObj.playSoundEffect(d2, (double)this.yCoord + 0.5D, d0, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F)
            {
                this.lidAngle = 0.0F;
            }
        }
    }

    /**
     * Called when a client event is received with the event number and argument, see World.sendClientEvent
     */
    public boolean receiveClientEvent(int number, int arg)
    {
        if (number == 1)
        {
            this.numPlayersUsing = arg;
            return true;
        }
        else
        {
            return super.receiveClientEvent(number, arg);
        }
    }

    public void openInventory()
    {
        if (this.numPlayersUsing < 0)
        {
            this.numPlayersUsing = 0;
        }

        ++this.numPlayersUsing;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
    }

    public void closeInventory()
    {
    	//System.out.println("CLOSE INVENTORY at:"+this.xCoord+","+this.yCoord+","+this.zCoord);
        if (this.getBlockType() instanceof BlockTGChest)
        {
            --this.numPlayersUsing;
            this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
        }
    }

    /**
     * invalidates a tile entity
     */
    public void invalidate()
    {
        super.invalidate();
        this.updateContainingBlockInfo();
        this.checkForAdjacentChests();
    }

    public int getCachedChestType()
    {
        if (this.cachedChestType == -1)
        {
            if (this.worldObj == null || !(this.getBlockType() instanceof BlockTGChest))
            {
                return 0;
            }

            this.cachedChestType = ((BlockTGChest)this.getBlockType()).field1;
        }

        return this.cachedChestType;
    }
    
  /*  @Override
	public void buttonClicked(int id, EntityPlayer ply) {
		if (id==1 &&(this.isOwnedByPlayer(ply))){
			this.security++;
			this.security = (byte) (this.security%3);
			
			if (this.adjacentChestXNeg!=null){
				this.adjacentChestXNeg.security=this.security;
				this.adjacentChestXNeg.needUpdate();
			} else if (this.adjacentChestXPos!=null){
				this.adjacentChestXPos.security=this.security;
				this.adjacentChestXPos.needUpdate();
			} else if (this.adjacentChestZNeg!=null){
				this.adjacentChestZNeg.security=this.security;
				this.adjacentChestZNeg.needUpdate();
			} else if (this.adjacentChestZPos!=null){
				this.adjacentChestZPos.security=this.security;
				this.adjacentChestZPos.needUpdate();
			}
			
			if(!this.worldObj.isRemote){
				this.needUpdate();
			}
		}
	}*/
    
   /* @Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(super.isUseableByPlayer(player)){
			if (this.owner==null || this.security ==0){
				return true;
			} else if (this.isOwnedByPlayer(player)) {
				return true;
			} else if (this.security==1){
				return !TGNpcFactions.isHostile(this.owner, player.getGameProfile().getId());
			}
						
		}
		return false;
	}*/
}
