package techguns.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class OreDrillTileEntSlave extends MultiBlockSlaveTileEntBase {

	/**
	 * needed for rendering
	 */
	private boolean bottomrow=false;
	/**
	 * direction of the drill
	 * -1: invalid
	 * 0/1 : +y/-y
	 * 2/3 : +x/-x
	 * 4/5 : +z/-z
	 */
	private byte direction=-1;
	

	public OreDrillTileEntSlave() {
		super();
	}

	
	/**
	 * direction of the drill
	 * -1: invalid
	 * 0/1 : +y/-y
	 * 2/3 : +x/-x
	 * 4/5 : +z/-z
	 */
	public byte getDirection() {
		return direction;
	}


	/**
	 * direction of the drill
	 * -1: invalid
	 * 0/1 : +y/-y
	 * 2/3 : +x/-x
	 * 4/5 : +z/-z
	 */
	public void setDirection(byte direction) {
		this.direction = direction;
	}



	public boolean isBottomrow() {
		return bottomrow;
	}

	public void setBottomrow(boolean bottomrow) {
		this.bottomrow = bottomrow;
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
	}
	

	/**
	 * called serverside when this tileent should send out updated to client
	 */
	public void needUpdate(){
		if(!this.worldObj.isRemote){
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			//this.worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, this.worldObj.getBlock(xCoord, yCoord, zCoord), 1);
			this.markDirty();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tags) {
		super.readFromNBT(tags);
		if (this.hasMaster()){
			this.bottomrow=tags.getBoolean("bottomrow");
			this.direction=tags.getByte("direction");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tags) {
		super.writeToNBT(tags);
		if(this.hasMaster()){
			tags.setBoolean("bottomrow", this.bottomrow);
			tags.setByte("direction", this.direction);
		}
	}

}
