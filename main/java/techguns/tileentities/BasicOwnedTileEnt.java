package techguns.tileentities;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public abstract class BasicOwnedTileEnt extends BasicInventoryTileEnt implements ITGTileEntSecurity{

	public BasicOwnedTileEnt(int slots) {
		super(slots);
	}


	/**
	 * Accessible by:
	 * 0 - anyone
	 * 1 - friends FTBUtilities is installed, else anyone
	 * 2 - restricted to owner
	 */
	protected byte security=0;
	
	protected UUID owner;
	
	/**
	 * Sets the owner of this tileent on placing
	 * @param ply
	 */
	@Override
	public void setOwner(EntityPlayer ply){
		UUID id = ply.getGameProfile().getId();
		if (id!=null){
			this.owner=id;
			this.security=0;
			//this.ownerDisplayName=ply.getGameProfile().getName();
		}
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
	}
	

	/**
	 * 0 - everyone
	 * 1 - friends if FTButilities installed, otherwise like 0
	 * 2 - owner only
	 * @return
	 */
	@Override
	public byte getSecurity(){
		return this.security;
	}
	
	@Override
	public void readEntityDataFromNBT(NBTTagCompound tags){
		super.readEntityDataFromNBT(tags);
		
        this.security = tags.getByte("security");
        
        String own = tags.getString("owner");
        if(own!=null && !own.equals("")){
        	try {
        		this.owner = UUID.fromString(own);
        		//this.ownerDisplayName = tags.getString("ownerName");
        	} catch (IllegalArgumentException e){
        		this.owner = null;
        		//System.out.println("Error Parsing Owner, machine is not owned anymore.");
        	}
        }
	}
	
	@Override
	public void writeEntityDataToNBT(NBTTagCompound tags){
			super.writeEntityDataToNBT(tags);

	        if (this.owner != null){
		        tags.setByte("security", this.security);
		        tags.setString("owner", this.owner.toString());
		        //tags.setString("ownerName", this.ownerDisplayName);
	        }
	}
}
