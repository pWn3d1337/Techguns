package techguns.tileentities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import techguns.TGPackets;
import techguns.damagesystem.TGDamageSource;
import techguns.packets.PacketSpawnParticle;
import techguns.util.EntityDeathUtils.DeathType;
import techguns.util.ProjectileExplosion;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class BioblobTileEnt extends TileEntity {
	
	protected static final int NUMTICKS=60;
	protected int ticks=NUMTICKS;
	
	/**
	 * 0: stick to bottom, 1: stick to ceiling, 2: east, 3: west, 4:north, 5:south
	 */
	public byte side =0; //Side hit
	public byte size =1;
	
	public BioblobTileEnt() {
		super();
	}

	/* (non-Javadoc)
	 * @see net.minecraft.tileentity.TileEntity#readFromNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	public void readFromNBT(NBTTagCompound tags) {
		super.readFromNBT(tags);
		byte oldSize = size;
		ticks=tags.getInteger("BlobTicks");
		side = tags.getByte("side");
		size= tags.getByte("size");
		if (this.worldObj!=null && this.worldObj.isRemote && size!=oldSize){
			this.worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
		}
	}

	/* (non-Javadoc)
	 * @see net.minecraft.tileentity.TileEntity#writeToNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	public void writeToNBT(NBTTagCompound tags) {
		super.writeToNBT(tags);
		tags.setInteger("BlobTicks", ticks);
		tags.setByte("side", side);
		tags.setByte("size", size);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.tileentity.TileEntity#getDescriptionPacket()
	 */
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tags = new NBTTagCompound();
		this.writeToNBT(tags);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, tags);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.tileentity.TileEntity#onDataPacket(net.minecraft.network.NetworkManager, net.minecraft.network.play.server.S35PacketUpdateTileEntity)
	 */
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
	}

	/* (non-Javadoc)
	 * @see net.minecraft.tileentity.TileEntity#canUpdate()
	 */
	@Override
	public boolean canUpdate() {
		return true;
	}

	/**
	 * Return the bioblob size [1-3]
	 * @return
	 */
	public int getBlobSize(){
		return size;
	}
	
	public void hitBlob(int power, EntityLivingBase attacker){
		this.size+=power;
		if(size>3){
			//kaboom
			float radius = 3.0f;
			
			TGDamageSource dmgSrc = TGDamageSource.causePoisonDamage(null, null, DeathType.BIO);
			dmgSrc.goreChance=1.0f;
			dmgSrc.armorPenetration=0.35f;
			
			ProjectileExplosion explosion = new ProjectileExplosion(worldObj, this.xCoord+0.5, this.yCoord+0.5, this.zCoord+0.5, null, radius, 15, radius*0.5f, radius*1.5f);
			explosion.setDamageSource(dmgSrc);
			explosion.setExplosionSound("techguns:effects.biodeath");
			
			this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord,Blocks.air);
			explosion.doExplosion(false, attacker);
			
			if(!this.worldObj.isRemote){
				TGPackets.network.sendToAllAround(new PacketSpawnParticle("bioblobExplosion", this.xCoord+0.5,this.yCoord+0.5, this.zCoord+0.5), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord+0.5,this.yCoord+0.5, this.zCoord+0.5, 50.0D));
			}
			
		}
		this.ticks=NUMTICKS;
		this.needUpdate();
	}
	
	
	/* (non-Javadoc)
	 * @see net.minecraft.tileentity.TileEntity#updateEntity()
	 */
	@Override
	public void updateEntity() {
		super.updateEntity();
		this.ticks--;
		if (ticks <=0){
			//Bioblob blob = (Bioblob) this.worldObj.getBlock(this.xCoord,this.yCoord, this.zCoord);
			/*switch(this.size){
				case 1:
					//die
	    			this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord,Blocks.air);
					break;
				case 2:
					this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord,TGBlocks.bioBlobSmall);
					break;
				case 3:
					//this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord,TGBlocks.bioBlobMedium);
					break;
				default:
					this.worldObj.removeTileEntity(this.xCoord, this.yCoord, this.zCoord);
					break;
			}*/
			
			if (this.size>1){
				size--;
				this.ticks = NUMTICKS;
				if(!this.worldObj.isRemote){
					this.needUpdate();
				}
			} else {
				//remove blob
				this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord,Blocks.air);
			}
			
		}
	}
	
	public void needUpdate(){
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		this.markDirty();
	}
	
	
}
