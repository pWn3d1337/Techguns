package techguns.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class MultiBlockSlaveTileEntBase extends TileEntity {

	protected int masterX;
	protected short masterY;
	protected int masterZ;
	protected boolean hasMaster=false;

	public MultiBlockSlaveTileEntBase() {
		super();
		this.masterX = 0;
		this.masterY = 0;
		this.masterZ = 0;
		this.hasMaster=false;
	}

	@Override
	public void readFromNBT(NBTTagCompound tags) {
		super.readFromNBT(tags);
		
		boolean formed = this.hasMaster;
		
		this.hasMaster=tags.getBoolean("hasMaster");
		if (this.hasMaster){
			this.masterX=tags.getInteger("masterX");
			this.masterY=tags.getShort("masterY");
			this.masterZ=tags.getInteger("masterZ");
		}
		
		if (formed!= this.hasMaster){
			this.causeRenderUpdate();
		}
		
	}

	@Override
	public void writeToNBT(NBTTagCompound tags) {
		super.writeToNBT(tags);
		tags.setBoolean("hasMaster", this.hasMaster);
		if (this.hasMaster){
			tags.setInteger("masterX", masterX);
			tags.setShort("masterY", masterY);
			tags.setInteger("masterZ", masterZ);
		}
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
	
	public void updateMaster(int x,int y, int z){
		this.masterX=x;
		this.masterY=(short) y;
		this.masterZ=z;
		this.hasMaster=true;
	}
	
	public void notifyMasterMultiblockBreak(){
		//System.out.println("Notify slave block break");
		if (this.hasMaster){
			
			TileEntity ent = this.worldObj.getTileEntity(masterX, masterY, masterZ);
			if(ent!=null && ent instanceof MultiBlockMasterTileEntBase){
				MultiBlockMasterTileEntBase master = (MultiBlockMasterTileEntBase) ent;
				master.notifiyMultiblockBreak();
			}
		}
	}
	
	public int[] getMasterPos(){
		if (this.hasMaster){
			return new int[]{masterX,masterY,masterZ};
		} else {
			return null;
		}
	}

	public boolean hasMaster() {
		return hasMaster;
	}
	
	public void unlink(){
		this.hasMaster=false;
		this.masterX=0;
		this.masterY=0;
		this.masterZ=0;
	}
	
	
	public void needBlockUpdate(){
		if(!this.worldObj.isRemote){
			this.worldObj.notifyBlockChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
		}
	}
	
	/**
	 * called serverside when this tileent should send out updated to client
	 */
	public void needUpdate(){
		if(!this.worldObj.isRemote){
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			this.markDirty();
		}
	}
	
	protected void causeRenderUpdate(){
		if (this.worldObj!=null && this.worldObj.isRemote){
			this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
}
