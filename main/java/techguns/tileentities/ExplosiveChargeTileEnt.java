package techguns.tileentities;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.BlockEvent;
import techguns.TGConfig;
import techguns.Techguns;
import techguns.util.BlockCoords;

public class ExplosiveChargeTileEnt extends BasicOwnedTileEnt {

	/**
	 * 6 sided position
	 */
	public byte orientation=0;
	
	protected short maxBlastradius=3;
	protected short minBlastradius=3;
	protected short minBlastLength=2;
	protected short maxBlastLength=5;
	protected short minFuseTime=60;
	protected short maxFuseTime=200;
	
	protected short blastradius=3;
	protected short blastlength=3;	
	
	protected short fusetime=100;
	protected boolean armed=false;
	
	protected String soundInit="techguns:tntcharge.init";
	protected String soundTick="techguns:tntcharge.tick";
	protected String soundPlant="techguns:tntcharge.plant";
	
	public String getSoundPlant() {
		return soundPlant;
	}

	protected int getBlastRadiusForCuboid(){
		return this.blastradius/2;
	}
	
	@Override
	public void updateEntity() {
		if(this.armed){
			this.fusetime--;
			
			if (!this.worldObj.isRemote && this.fusetime>0 && (this.fusetime % 20 ==0) ){
				this.worldObj.playSoundEffect(this.xCoord+0.5D,this.yCoord+0.5D, this.zCoord+0.5D, soundTick, 4.0F, 1.0f/*(1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F*/);
			}
			
			if(this.fusetime<=0){
				
			
				this.explode();
				
				//if(!this.worldObj.isRemote){
					
					
					
					/*BlockCoords pos = new BlockCoords(this);
					
					if (!this.worldObj.isRemote){
						this.worldObj.playSoundEffect(pos.x+0.5D,pos.y+0.5D, pos.z+0.5D, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
					}
					
					pos.shift(this.getDirection(),1);
					ArrayList<BlockCoords> blocks = pos.getCuboid(this.getDirection(), this.getBlastRadiusForCuboid(), this.blastlength, (this.blastradius % 2 == 0) ?true:false);				
					
					for (BlockCoords coords : blocks){
						Block block = this.worldObj.getBlock(coords.x, coords.y, coords.z);
						if(block.getBlockHardness(worldObj, coords.x, coords.y, coords.z)>=0.0f){
							
							if (!this.worldObj.isRemote){
								block.dropBlockAsItem(worldObj,coords.x, coords.y, coords.z, worldObj.getBlockMetadata(coords.x, coords.y, coords.z),0);
								worldObj.setBlock(coords.x, coords.y, coords.z, Blocks.air);
							} else {
								//Techguns.proxy.spawnParticle("Explosion", this.worldObj, coords.x+0.5D, coords.y+0.5D, coords.z+0.5D, 0, 0.025f, 0);
								if (worldObj.rand.nextFloat()>0.5f){
									Techguns.proxy.spawnParticle("LargeSmoke", this.worldObj, coords.x+0.25D+worldObj.rand.nextDouble()*0.5D, coords.y+0.25D+worldObj.rand.nextDouble()*0.5D, coords.z+0.25D+worldObj.rand.nextDouble()*0.5D, 0, 0.045f, 0);
									Techguns.proxy.spawnParticle("Explosion", this.worldObj, coords.x+0.25D+worldObj.rand.nextDouble()*0.5D, coords.y+0.25D+worldObj.rand.nextDouble()*0.5D, coords.z+0.25D+worldObj.rand.nextDouble()*0.5D, 0, 0.045f, 0);
								}
							}
						}
						
						
					}
					
					if (!this.worldObj.isRemote){
						worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.air);
					}*/
					
					//System.out.println("KABOOM");
				}				
				
			//}
			
		}
	}

	protected void explode(){
		
		BlockCoords pos = new BlockCoords(this);	
		if(!this.worldObj.isRemote){
			this.worldObj.playSoundEffect(pos.x+0.5D,pos.y+0.5D, pos.z+0.5D, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		}
		//pos.shift(this.getDirection(),1);
	
		//get the first slice of the explosion volume
		ArrayList<BlockCoords> blocks = pos.getCuboid(this.getDirection(), this.getBlastRadiusForCuboid(), 1, (this.blastradius % 2 == 0) ?true:false);				
		
		for (BlockCoords coords : blocks){
			explodeLine(coords);
		}
		
		//remove self
		if (!this.worldObj.isRemote){
			worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.air);
		}
		
	}
	
	protected void explodeLine(BlockCoords pos){
		int i =0;
		boolean blocked=false;
		
		while (!blocked && i < this.blastlength){
			pos.shift(getDirection(), 1);		
			blocked = explodeBlock(pos);
			i++;
		}		
	}
	
	/**
	 * Returns false when explosion is blocked
	 * @param coords
	 * @return
	 */
	protected boolean explodeBlock(BlockCoords coords){
		Block block = this.worldObj.getBlock(coords.x, coords.y, coords.z);
		if(this.canBreakBlockHardness(block.getBlockHardness(worldObj, coords.x, coords.y, coords.z))){
			
			//post blockBreakEvent
			final BlockEvent.BreakEvent breakEvent = new BlockEvent.BreakEvent(coords.x, coords.y, coords.z, this.worldObj,
					worldObj.getBlock(coords.x, coords.y, coords.z), worldObj.getBlockMetadata(coords.x, coords.y, coords.z),
					worldObj.func_152378_a(this.getOwner()));
			
			MinecraftForge.EVENT_BUS.post(breakEvent);
			
			
			if (!breakEvent.isCanceled()){
				if (!this.worldObj.isRemote){
					block.dropBlockAsItem(worldObj,coords.x, coords.y, coords.z, worldObj.getBlockMetadata(coords.x, coords.y, coords.z),0);
					worldObj.setBlock(coords.x, coords.y, coords.z, Blocks.air);
				} else {
					//Techguns.proxy.spawnParticle("Explosion", this.worldObj, coords.x+0.5D, coords.y+0.5D, coords.z+0.5D, 0, 0.025f, 0);
					if (worldObj.rand.nextFloat()>0.5f){
						Techguns.proxy.spawnParticle("LargeSmoke", this.worldObj, coords.x+0.25D+worldObj.rand.nextDouble()*0.5D, coords.y+0.25D+worldObj.rand.nextDouble()*0.5D, coords.z+0.25D+worldObj.rand.nextDouble()*0.5D, 0, 0.045f, 0);
						Techguns.proxy.spawnParticle("Explosion", this.worldObj, coords.x+0.25D+worldObj.rand.nextDouble()*0.5D, coords.y+0.25D+worldObj.rand.nextDouble()*0.5D, coords.z+0.25D+worldObj.rand.nextDouble()*0.5D, 0, 0.045f, 0);
					}
				}
				return false;
			} 
		}
		return true;
	}
	
	protected boolean canBreakBlockHardness(float hardness){
		return hardness >=0 && hardness < TGConfig.explosiveChargeMaxBlockHardness;
	}
	
	@Override
	public void readEntityDataFromNBT(NBTTagCompound tags) {
		super.readEntityDataFromNBT(tags);
		orientation=tags.getByte("orientation");
		blastradius=tags.getShort("blastradius");
		blastlength=tags.getShort("blastlength");
		fusetime=tags.getShort("fusetime");
		armed=tags.getBoolean("armed");
	}

	@Override
	public void writeEntityDataToNBT(NBTTagCompound tags) {
		super.writeEntityDataToNBT(tags);
		tags.setByte("orientation",orientation);
		tags.setShort("blastradius",blastradius);
		tags.setShort("blastlength",blastlength);
		tags.setShort("fusetime",fusetime);
		tags.setBoolean("armed",armed);
	}

	public ExplosiveChargeTileEnt() {
		super(0);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return false;
	}

	@Override
	public String getInventoryName() {
		return "techguns.inventory.explosivecharge";
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return false;
	}

	public byte getOrientation() {
		return orientation;
	}

	public void setOrientation(byte orientation) {
		this.orientation = orientation;
	}

	public short getBlastradius() {
		return blastradius;
	}

	public short getBlastlength() {
		return blastlength;
	}

	public short getFusetime() {
		return fusetime;
	}

	public boolean isArmed() {
		return armed;
	}

	public ForgeDirection getDirection(){
		if(this.orientation==0){
			return ForgeDirection.UP;
		} else if(this.orientation==1){
			return ForgeDirection.DOWN;
			
		} else if(this.orientation==2){
			return ForgeDirection.SOUTH;

		} else if(this.orientation==3){
			return ForgeDirection.NORTH;
			
		} else if(this.orientation==4){
			return ForgeDirection.EAST;
			
		} else if(this.orientation==5){
			return ForgeDirection.WEST;

		} else {
			return ForgeDirection.UNKNOWN;
		}
	}

	@Override
	public void buttonClicked(int id, EntityPlayer ply) {
		if (!this.armed){
			if ((id==0) && (this.isUseableByPlayer(ply))){
				this.armed=true;
				if(!this.worldObj.isRemote){
					this.worldObj.playSoundEffect(this.xCoord+0.5D,this.yCoord+0.5D, this.zCoord+0.5D, soundInit, 4.0F, 1.0f/*(1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F*/);
					this.needUpdate();
				}
			}else if(id==1){
				if(this.blastradius<this.maxBlastradius){
					this.blastradius++;
					this.needUpdate();
				}
			}
			else if(id==2){
				if(this.blastradius>this.minBlastradius){
					this.blastradius--;
					this.needUpdate();
				}
			}
			
			else if(id==3){
				if(this.blastlength< this.maxBlastLength){
					this.blastlength++;
					this.needUpdate();
				}
			}
			
			else if(id==4){
				if(this.blastlength > this.minBlastLength){
					this.blastlength--;
					this.needUpdate();
				}
			}
		
			else if(id==5){
				if(this.fusetime< this.maxFuseTime){
					this.fusetime+=20;
					this.needUpdate();
				}
			}
			else if(id==6){
				if(this.fusetime> this.minFuseTime){
					this.fusetime-=20;
					this.needUpdate();
				}
			}
		}
	}
	
}
