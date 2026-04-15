package techguns.tileentities;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import techguns.TGItems;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.damagesystem.DamageSystem;
import techguns.inventory.ReactionBeamFocus;
import techguns.inventory.ReactionChamberRecipe;
import techguns.inventory.ReactionChamberRecipe.RiskType;
import techguns.items.TGSlotType;
import techguns.packets.PacketSpawnParticle;
import techguns.util.BlockCoords;
import techguns.util.ProjectileExplosion;

public class ReactionChamberTileEntMaster extends MultiBlockMasterTileEntBase implements IFluidHandler {
	public boolean haspower=false;
	
	public FluidTank inputTank;
	
	public ItemStack lastStack=null;
	public ReactionChamberOperation currentReaction=null;
	
	public byte intensity=0;
	public byte liquidLevel=0;
	
	/**
	 * direction of the multiblock = sideHit when formed.
	 */
	public byte dir;

	public ReactionChamberTileEntMaster() {
		super(6, 1000000);
		inputTank = new FluidTank(10 * FluidContainerRegistry.BUCKET_VOLUME);
		complete=false;
		dir=0;
		this.useRangeSquared=144.0d;
	}

	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if (this.complete){
			/*if (dir==4){
				//+X
				return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord-1, xCoord+2, yCoord+4, zCoord+1);
			} else if (dir==5){
				//-X
				return AxisAlignedBB.getBoundingBox(xCoord-2, yCoord, zCoord-1, xCoord, yCoord+4, zCoord+1);
			} else if (dir==2){
				//+Z
				return AxisAlignedBB.getBoundingBox(xCoord-1, yCoord, zCoord, xCoord+1, yCoord+4, zCoord+2);
			} else if (dir==3){
				//-Z
				return AxisAlignedBB.getBoundingBox(xCoord-1, yCoord, zCoord-2, xCoord+1, yCoord+4, zCoord);
			} else {
				return super.getRenderBoundingBox();
			}*/
			return AxisAlignedBB.getBoundingBox(xCoord-2, yCoord, zCoord-2, xCoord+2, yCoord+4, zCoord+2);
		} else {
			return super.getRenderBoundingBox();
		}
	}
	
	public BlockCoords getCenterPos(){
		BlockCoords pos = new BlockCoords(this);
		//pos.shift(ForgeDirection.getOrientation(this.dir), 1);
		switch(this.dir){
			case 2:
				pos.shift(ForgeDirection.SOUTH, 1);
				break;
			case 3:
				pos.shift(ForgeDirection.NORTH, 1);
				break;
			case 4:
				pos.shift(ForgeDirection.EAST, 1);
				break;
			case 5:
				pos.shift(ForgeDirection.WEST, 1);
				break;
		}
		pos.shift(ForgeDirection.UP, 1);
		return pos;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[]{};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item,
			int side){
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item,
			int side) {
		return false;
	}

	@Override
	public String getInventoryName() {
		return "techguns.container.reactionChamber";
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		if(slot==0){
			return true;
		}
		return false;
	}

	public void setComplete(boolean complete, byte dir) {
		super.setComplete(complete);
		this.dir=dir;
	}
	
	public boolean canConnectEnergy(ForgeDirection dir) {
		return false;
	}
	
	@Override
	public void readEntityDataFromNBT(NBTTagCompound tags) {
		super.readEntityDataFromNBT(tags);
		if (this.complete){
			this.haspower= tags.getBoolean("hasPower");
			this.dir = tags.getByte("dir");
		}
		NBTTagCompound tankIn = tags.getCompoundTag("inputTank");
		if (tankIn != null) {
			inputTank.readFromNBT(tankIn);
		}
		this.intensity= tags.getByte("intensity");
		this.liquidLevel=tags.getByte("liquidLevel");
		
		NBTTagCompound opdata=tags.getCompoundTag("operationData");
		if(!opdata.hasNoTags()){
			
			//System.out.println("Got OperationData");
			if(this.currentReaction==null){
				this.currentReaction = new ReactionChamberOperation(null);
			}
			this.currentReaction.readDataFromNBT(opdata);
			
			//if (!this.worldObj.isRemote){
				short recID = opdata.getShort("ID");
				this.currentReaction.recipe=ReactionChamberRecipe.getById(recID);
			//}
			
		} else {
			//System.out.println("OperationData is null");
			this.currentReaction=null;
		}
		
	}

	@Override
	public void writeEntityDataToNBT(NBTTagCompound tags) {
		super.writeEntityDataToNBT(tags);
		if (this.complete){
			tags.setBoolean("hasPower",haspower);
			tags.setByte("dir",dir);
		}
		NBTTagCompound tagInputTank = new NBTTagCompound();
		inputTank.writeToNBT(tagInputTank);
		tags.setTag("inputTank", tagInputTank);
		tags.setByte("intensity", this.intensity);
		tags.setByte("liquidLevel", this.liquidLevel);
		
		if (this.currentReaction!=null){
			this.currentReaction.writeDataToNBT(tags);
		}
	}

	private void mergeOutput(){
		ArrayList<ItemStack> outputs = this.currentReaction.recipe.outputs;
		
		for (int i=0;i<outputs.size();i++){
			ItemStack output = outputs.get(i).copy();
			int leftover = Techguns.addItemToInventory(this.content, output, 2, 6);
			if(leftover >0 && !this.worldObj.isRemote){
				this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord+0.5d, this.yCoord+0.5d, this.zCoord+0.5d, TGItems.newStack(output, leftover)));
			}
		}
		
		if(this.currentReaction.recipe.liquidConsumtion>0){
			this.inputTank.drain(this.currentReaction.recipe.liquidConsumtion, true);
		}
		
	}
	
	@Override
	public void notifiyMultiblockBreak() {
		this.unform();
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(this.isComplete() && this.isRedstoneEnabled()){
		
			//Check for operation
			if (this.currentReaction==null){
				if(!this.worldObj.isRemote){
					
					//System.out.println("Reaction is Null, check recipe");
					ReactionChamberRecipe rec = ReactionChamberRecipe.getMatchingRecipe(this.content[0], this.content[1], this.inputTank.getFluid(), this.liquidLevel, this.intensity);
					if (rec!=null){
						//System.out.println("Starting Operation!");
						
						if (rec.RFTick<=this.energy.getEnergyStored()){
						
							this.startOperation(rec);
							this.needUpdate();
						}
					}
				
					if (this.lastStack != this.content[0]){
						this.needUpdate();
						this.lastStack=this.content[0];
					}
					
					if (this.inputTank.getFluid()!=null){
						if (this.inputTank.getFluidAmount()<=0){
							this.inputTank.setFluid(null);
							this.needUpdate();
						}
					}
				}
			} else {
				boolean state = this.currentReaction.tick(this.intensity,this.liquidLevel,this.worldObj.isRemote,this,this.currentReaction.powerPerRecipeTick);
				
				if (!this.worldObj.isRemote && state) {
					if (this.currentReaction.isSuccess()){
						//System.out.println("Finished!");
						if (!this.worldObj.isRemote){
							this.mergeOutput();
						}
						this.setFinished();
					} else if (this.currentReaction.isFailure()){
						//System.out.println("Failed!");
						
						RiskType risk = this.currentReaction.recipe.risk;
						
						this.setFinished();
						
						
						if (risk == RiskType.EXPLOSION_LOW){
							this.explode(0);
						} else if (risk == RiskType.EXPLOSION_MEDIUM){
							this.explode(1);
						}
						
					} else {
						
					}
					
					this.needUpdate();
				}
				if (this.worldObj.isRemote){
					//System.out.println("Tick:"+this.currentReaction.nextTick);
					if (this.currentReaction.nextTick==ReactionChamberOperation.RECIPE_TICKRATE-1){
						this.playAmbientSound(this.currentReaction.preferedIntensity== this.intensity);
					}
				}
				
			}
		}
	}
	
	private void startOperation(ReactionChamberRecipe rec){
		this.currentReaction=new ReactionChamberOperation(rec);
		this.currentOperation = this.content[0].copy();
		this.currentOperation.stackSize = 1;
		
		this.content[0].stackSize--;
		if(this.content[0].stackSize<=0){
			this.content[0]=null;
		}
		
	}
	
	private void setFinished(){
		this.currentReaction=null;
		this.currentOperation=null;
	}
	
	public void unform(){
		
		if(this.isComplete()){
			BlockCoords masterPos = new BlockCoords(this);
			
			ArrayList<BlockCoords> positions = null;
			if(dir==2){
				// +Z;
				positions = masterPos.getBoxWithOffsets(-1, +1, 0, 3, 0, 2, false);
				
			} else if(dir==3){
				// -Z;
				positions = masterPos.getBoxWithOffsets(-1, +1, 0, 3, -2, 0, false);
			} else if (dir==4){
				// +X;
				positions = masterPos.getBoxWithOffsets(0, 2, 0, 3, -1, +1, false);
			} else if (dir==5){
				// -x;
				positions = masterPos.getBoxWithOffsets(-2, 0, 0, 3, -1, +1, false);
			}
			
			
			if (positions !=null){
				
				for (int i =0;i<positions.size();i++){
					BlockCoords pos = positions.get(i);

					TileEntity tile = this.worldObj.getTileEntity(pos.x, pos.y, pos.z);
					if(tile!=null && tile instanceof ReactionChamberTileEntSlave){
						ReactionChamberTileEntSlave slave = (ReactionChamberTileEntSlave) tile;	
						slave.unlink();
						slave.needBlockUpdate();
						slave.needUpdate();
					} 
					//this.worldObj.setBlock(pos.x, pos.y, pos.z, Blocks.dirt);
				}
				
			}
			
			this.setComplete(false,(byte)0);
			this.needBlockUpdate();
			this.needUpdate();
		}
		
	}

	public int fillFromSlave(ForgeDirection from, FluidStack resource, boolean doFill) {
		
		int max = this.getCurrentMaxFluidValue() - this.inputTank.getFluidAmount();
		if (max<0){
			max=0;
		}
		
		FluidStack f2 = resource.copy();
		if (f2.amount>max){
			f2.amount=max;
		}
		
		return inputTank.fill(f2, doFill);
	}

	public FluidStack drainFromSlave(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (resource == null || !resource.isFluidEqual(inputTank.getFluid())) {
			return null;
		}
		
		int max = this.inputTank.getFluidAmount() - this.getCurrentMaxFluidValue();
		if (max<0){
			max=0;
		}
		return inputTank.drain(resource.amount < max? resource.amount : max, doDrain);
	}

	public FluidStack drainFromSlave(ForgeDirection from, int maxDrain, boolean doDrain) {
		
		int max = this.inputTank.getFluidAmount() - this.getCurrentMaxFluidValue();
		if (max<0){
			max=0;
		}
		
		return inputTank.drain(maxDrain < max? maxDrain:max, doDrain);
	}
	
	public boolean canFillFromSlave(ForgeDirection from, Fluid fluid) {
		return true;
	}

	public boolean canDrainFromSlave(ForgeDirection from, Fluid fluid) {
		return true;
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}
	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}
	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}
	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}
	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}
	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{inputTank.getInfo()};
	}

	public void setFluidAmountIn(int value) {
		FluidStack fluid = this.inputTank.getFluid();
		if (fluid != null) {
			fluid.amount = value;
		}
	}

	public void setFluidTypeIn(int id) {
		Fluid f = FluidRegistry.getFluid(id);
		if (f!=null){
			this.inputTank.setFluid(new FluidStack(f, 0));
		} else {
			this.inputTank.setFluid(null);
		}
	}
	
	@Override
	public void buttonClicked(int id, EntityPlayer ply) {
		if(id<=MAX_BUTTON_USED){
			super.buttonClicked(id, ply);
		} else {
			if (this.isUseableByPlayer(ply)){
				switch(id){
					case MAX_BUTTON_USED+1: 
						if (this.liquidLevel<10){
							this.liquidLevel++;
						}
						break;
					case MAX_BUTTON_USED+2:
						if (this.liquidLevel>0){
							this.liquidLevel--;
						}
						break;
					case MAX_BUTTON_USED+3: 
						if (this.intensity<10){
							this.intensity++;
						}
						break;
					case MAX_BUTTON_USED+4: 
						if (this.intensity>0){
							this.intensity--;
						}
						break;
					case MAX_BUTTON_USED+5: //drain input tank
						this.inputTank.setFluid(null);
						this.needUpdate();
						break;
				}
			}
		}
	}

	
	void playAmbientSound(boolean goodTick) {

			//System.out.println("Play sound1");
			
		ItemStack focusitem = this.content[1];
		if(focusitem!=null){
			ReactionBeamFocus focus = ReactionBeamFocus.getBeamFocus(focusitem);
			if (focus!=null){
				worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, focus.getSound(), 1.0F, 1.0F, true );
			}
		}
		BlockCoords pos = getCenterPos();
		Random rng = new Random();
		for(int i=0; i<4;i++){
			worldObj.spawnParticle("spell", pos.x + rng.nextFloat(), pos.y + rng.nextFloat(), pos.z + rng.nextFloat(), 0, 1, 0);
		}
			
		if (goodTick){
			worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "techguns:machines.rc_beep", 1.0F, 1.0F, true );
		} else {
			//System.out.println("Play sound2");
			worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "techguns:machines.rc_warning", 1.0F, 1.0F, true );
		}
	}
	
	
	/*@Override
	public boolean isWorking() {
		return !(this.currentReaction==null);
	}*/


	public int getCurrentMaxFluidValue(){
		return this.liquidLevel*1000;
	}
	
	public int getValidSlotForItemInMachine(ItemStack stack1) {
		if (stack1.getItem()==TGItems.sharedItem){
			if(TGItems.sharedItem.slotType[stack1.getItemDamage()]==TGSlotType.REACTION_CHAMBER_FOCUS){
				return 1;
			}
		}
		return 0;
	}
	
	protected void explode(int type){
		byte dir = this.dir;
		this.unform();
		
		if (!this.worldObj.isRemote){
			
			Block fluidBlock = null;
			if (this.inputTank.getFluid()!=null){
				fluidBlock = this.inputTank.getFluid().getFluid().getBlock();
			}
			
			BlockCoords masterPos = new BlockCoords(this);
			
			BlockCoords centerPos=null;
			if(dir==2){
				// +Z;
				centerPos = masterPos.getShifted(ForgeDirection.SOUTH, 1);
			} else if(dir==3){
				// -Z;
				centerPos = masterPos.getShifted(ForgeDirection.NORTH, 1);
			} else if (dir==4){
				// +X;
				centerPos = masterPos.getShifted(ForgeDirection.EAST, 1);
			} else if (dir==5){
				// -x;
				centerPos = masterPos.getShifted(ForgeDirection.WEST, 1);
			}
			centerPos = centerPos.getShifted(ForgeDirection.UP, 1);
			

			this.worldObj.setBlockToAir(centerPos.x, centerPos.y, centerPos.z);
			
			
			BlockCoords[] blocksToRemove = null;
			
			if (type==0){
				blocksToRemove = new BlockCoords[]{
						centerPos.getShifted(ForgeDirection.SOUTH,1),
						centerPos.getShifted(ForgeDirection.NORTH,1),
						centerPos.getShifted(ForgeDirection.EAST,1),
						centerPos.getShifted(ForgeDirection.WEST,1),
				};
			} else if (type==1){
				blocksToRemove = new BlockCoords[0];
				blocksToRemove = centerPos.getBoxWithOffsets(-1, 1, 0, 1, -1, 1, false).toArray(blocksToRemove);
			}
			
			if (type==0 && fluidBlock!=null){
				this.worldObj.setBlock(centerPos.x, centerPos.y, centerPos.z, fluidBlock, 0, 3);
			} else if (type==0) {
				this.worldObj.setBlock(centerPos.x, centerPos.y, centerPos.z, Blocks.air, 0, 3);
			}
			
			for (int i=0; i<blocksToRemove.length; i++){
				BlockCoords bc = blocksToRemove[i];
				this.worldObj.setBlock(bc.x, bc.y, bc.z, Blocks.air, 0, 3);
			}
						
						
			if (type == 0){
			
				TGPackets.network.sendToAllAround(new PacketSpawnParticle("FragGrenadeExplosion", centerPos.x+0.5d, centerPos.y+0.5d, centerPos.z+0.5d), new TargetPoint(this.worldObj.provider.dimensionId, centerPos.x+0.5d, centerPos.y+0.5d, centerPos.z+0.5d, 50.0f));
				this.worldObj.playSoundEffect(centerPos.x+0.5d, centerPos.y+0.5d, centerPos.z+0.5d, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
			} else if (type == 1){
				//int radius=4;
				//TGPackets.network.sendToAllAround(new PacketSpawnParticle("RocketExplosion", centerPos.x+0.5d, centerPos.y+0.5d, centerPos.z+0.5d), new TargetPoint(this.worldObj.provider.dimensionId, centerPos.x+0.5d, centerPos.y+0.5d, centerPos.z+0.5d, 50.0f));
				//ProjectileExplosion explosion = new ProjectileExplosion(worldObj, centerPos.x+0.5d, centerPos.y+0.5d, centerPos.z+0.5d, null, radius, 25, radius*0.5f, radius*1.5f);
				//explosion.setExplosionSound("techguns:effects.explosion1");
				//explosion.doExplosion(true, null);
				
				// EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(this.worldObj, centerPos.x+0.5d, centerPos.y+0.5d, centerPos.z+0.5d, null);
	            // this.worldObj.spawnEntityInWorld(entitytntprimed);
				
				this.worldObj.createExplosion(null, centerPos.x+0.5d, centerPos.y+0.5d, centerPos.z+0.5d, 4, true);
			}
			 
			if (type==1 && fluidBlock!=null){
				this.worldObj.setBlock(centerPos.x, centerPos.y, centerPos.z, fluidBlock, 0, 3);
				this.worldObj.setBlock(centerPos.x, centerPos.y+1, centerPos.z, Blocks.glass, 0, 3);
			}
			
			this.energy.setEnergyStored(0);
			this.inputTank.setFluid(null);
			
			ItemStack content = this.content[0];
			if (content!=null){
				this.content[0]=null;
				this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, centerPos.x+0.5d, centerPos.y+0.5d, centerPos.z+0.5d, content));
			}
			
			this.needUpdate();
		}
	}
}
