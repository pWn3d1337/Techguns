package techguns.tileentities;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import techguns.TGItems;
import techguns.inventory.FabricatorRecipe;
import techguns.inventory.FabricatorRecipe.RecipeData;
import techguns.inventory.ItemStackOreDict;
import techguns.util.BlockCoords;

public class FabricatorTileEntMaster extends MultiBlockMasterTileEntBase {
	
	public static byte SLOT_INPUT=0;
	public static byte SLOT_WIRES=1;
	public static byte SLOT_POWDER=2;
	public static byte SLOT_PLATE=3;
	public static byte SLOT_OUTPUT=4;
	public static byte SLOT_UPGRADE=5;
	
	public static short RF_TICK = 80;

	/**
	 * direction of the multiblock = sideHit when formed.
	 */
	public byte dir;
	
	public boolean haspower=false;
		
	public ItemStack plateUsed;
	
	public FabricatorTileEntMaster() {
		super(6, 100000);
		complete=false;
		dir=0;
		this.useRangeSquared=64.0d;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if (this.complete){
			return AxisAlignedBB.getBoundingBox(xCoord-1.5, yCoord, zCoord-1.5, xCoord+1.5, yCoord+2, zCoord+1.5);
		} else {
			return super.getRenderBoundingBox();
		}
	}
	
	public void setComplete(boolean complete, byte dir) {
		super.setComplete(complete);
		this.dir=dir;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if (!this.complete || side == dir){
			return new int[]{};
		}
		return new int[]{FabricatorTileEntMaster.SLOT_INPUT,FabricatorTileEntMaster.SLOT_WIRES,FabricatorTileEntMaster.SLOT_POWDER,FabricatorTileEntMaster.SLOT_PLATE,FabricatorTileEntMaster.SLOT_OUTPUT};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item,
			int side){
		if (!this.complete || side == dir){
			return false;
		}
		return slot==FabricatorTileEntMaster.SLOT_INPUT||slot==FabricatorTileEntMaster.SLOT_WIRES||slot==FabricatorTileEntMaster.SLOT_POWDER||slot==FabricatorTileEntMaster.SLOT_PLATE;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item,
			int side) {
		if (!this.complete || side == dir){
			return false;
		}
		return slot==FabricatorTileEntMaster.SLOT_OUTPUT;
	}

	@Override
	public String getInventoryName() {
		return "techguns.container.fabricator";
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return this.getValidSlotForItemInMachine(item) == slot;
	}
	
	public boolean canConnectEnergy(ForgeDirection dir) {
		if(this.complete){
			return dir.ordinal()!=this.dir;
		}
		return false;
	}

	@Override
	public void readEntityDataFromNBT(NBTTagCompound tags) {
		super.readEntityDataFromNBT(tags);
		if (this.complete){
			this.haspower= tags.getBoolean("hasPower");
			this.dir = tags.getByte("dir");
		}
		
		NBTTagCompound plate = tags.getCompoundTag("plateUsed");
        if (plate.hasNoTags()){
        	this.plateUsed=null;
        } else {
        	this.plateUsed = ItemStack.loadItemStackFromNBT(plate);
        }
	}

	@Override
	public void writeEntityDataToNBT(NBTTagCompound tags) {
		super.writeEntityDataToNBT(tags);
		if (this.complete){
			tags.setBoolean("hasPower",haspower);
			tags.setByte("dir",dir);
		}
		
		if (this.plateUsed != null){
	        NBTTagCompound plate = new NBTTagCompound();
	        this.plateUsed.writeToNBT(plate);
	        tags.setTag("plateUsed", plate);
        }
	}

	@Override
	public void notifiyMultiblockBreak() {
		this.unform();
	}

	public void unform(){
		if(this.isComplete()){
			BlockCoords masterPos = new BlockCoords(this);
			
			ArrayList<BlockCoords> positions = null;
			if(this.dir==2){
				// +Z;
				positions = masterPos.getBoxWithOffsets(0, +1, 0, 1, 0, 1, false);
			} else if(this.dir==3){
				// -Z;
				positions = masterPos.getBoxWithOffsets(-1, 0, 0, 1, -1, 0, false);
			} else if (this.dir==4){
				// +X;			
				positions = masterPos.getBoxWithOffsets(0, 1, 0, 1, -1, 0, false);
			} else if (this.dir==5){
				// -x;
				positions = masterPos.getBoxWithOffsets(-1, 0, 0, 1, 0, +1, false);	
			}
			
			if (positions !=null){
				for (int i =0;i<positions.size();i++){
					BlockCoords pos = positions.get(i);

					TileEntity tile = this.worldObj.getTileEntity(pos.x, pos.y, pos.z);
					if(tile!=null && tile instanceof FabricatorTileEntSlave){
						FabricatorTileEntSlave slave = (FabricatorTileEntSlave) tile;	
						slave.unlink();
						slave.needUpdate();
					} 
				}
			}
			
			this.setComplete(false,(byte)0);
			this.needUpdate();
		}
	}

	private boolean itemStackInList(ArrayList<ItemStackOreDict> list, ItemStack stack){
		for(int i=0;i<list.size();i++){
			ItemStackOreDict orestack = list.get(i);
			if(orestack.isEqualWithOreDict(stack)){
				return true;
			}
		}
		return false;
	}
	
	public int getValidSlotForItemInMachine(ItemStack stack) {
		if(itemStackInList(FabricatorRecipe.items_wireslot, stack)){
			return SLOT_WIRES;
		}
		if(itemStackInList(FabricatorRecipe.items_powderslot, stack)){
			return SLOT_POWDER;
		}
		if(itemStackInList(FabricatorRecipe.items_plateslot, stack)){
			return SLOT_PLATE;
		}
		
		if(stack.getItem()==TGItems.sharedItem && stack.getItemDamage()==TGItems.machineStackUpgrade.getItemDamage()){
			return SLOT_UPGRADE;
		}
		
		return SLOT_INPUT;
	}
	
	protected int getStackMultiplier(){
		ItemStack upgrades = this.content[FabricatorTileEntMaster.SLOT_UPGRADE];
		if (upgrades !=null){
			if (upgrades.getItem()==TGItems.sharedItem && upgrades.getItemDamage()==TGItems.machineStackUpgrade.getItemDamage()){
				return Math.min(upgrades.stackSize+1,8);
			}
		}
		return 1;
	}
	
	private void consume(int slot, int amount){
		if (this.content[slot]!=null){
			this.content[slot].stackSize-=amount;
			if(this.content[slot].stackSize<=0){
				this.content[slot]=null;
			}
		}
	}
	
	public boolean checkAndStartOperation(){
		RecipeData data = FabricatorRecipe.getRecipeDataFor(content[FabricatorTileEntMaster.SLOT_INPUT], content[FabricatorTileEntMaster.SLOT_WIRES], content[FabricatorTileEntMaster.SLOT_POWDER], content[FabricatorTileEntMaster.SLOT_PLATE], this.getStackMultiplier(), content[FabricatorTileEntMaster.SLOT_OUTPUT]);
		if (data!=null){
			this.currentOperation = data.output.copy();
			this.currentOperation.stackSize=data.outputAmount;
			
			if(data.plateConsumption>0){
				ItemStack plate = this.content[SLOT_PLATE];
				if (plate!=null){
					this.plateUsed = plate.copy();
					this.plateUsed.stackSize=1;
				}
			}
			
			consume(SLOT_INPUT, data.inputConsumption*data.stackMultiplier);
			consume(SLOT_WIRES, data.wireConsumption*data.stackMultiplier);
			consume(SLOT_POWDER, data.powderConsumption*data.stackMultiplier);
			consume(SLOT_PLATE,data.plateConsumption*data.stackMultiplier);
			
			this.stackmultiplier = data.stackMultiplier;
			this.progress=0;
			this.totalTime=100;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		//System.out.println("I AM THE TABLE");
		if (this.isRedstoneEnabled()){
			if (this.currentOperation!= null) {
				
				if (this.consumePower(RF_TICK*this.stackmultiplier)){
					this.progress++;
					
					if(this.worldObj.isRemote){
						playAmbientSound();
					}
					
					if (!this.worldObj.isRemote && progress >= totalTime){
			
						if (this.content[SLOT_OUTPUT]==null){
							this.content[SLOT_OUTPUT]=new ItemStack(currentOperation.getItem(), currentOperation.stackSize*this.stackmultiplier,currentOperation.getItemDamage());
						} else {
							this.content[SLOT_OUTPUT].stackSize+=(currentOperation.stackSize*this.stackmultiplier);
						}

						this.progress=0;
						this.totalTime=0;
						this.currentOperation=null;
						this.stackmultiplier=1;
						this.plateUsed=null;
						
						checkAndStartOperation();
						this.needUpdate();
					}
				}
			} else {
				if(!this.worldObj.isRemote){
					if (this.checkAndStartOperation()){
						this.needUpdate();
					}
				}
			}
		}
	}

	private void playAmbientSound() {
		if (this.progress == 1) {
			//machines.fabricatorWork
			worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "techguns:machines.fabricatorWork", 1.0F, 1.0F, true );
		}
		
	}	
	
	
	
}
