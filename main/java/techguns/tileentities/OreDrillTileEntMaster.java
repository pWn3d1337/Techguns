package techguns.tileentities;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import techguns.TGConfig;
import techguns.TGFluids;
import techguns.TGItems;
import techguns.Techguns;
import techguns.blocks.ItemBlockOreDrill;
import techguns.blocks.OreCluster;
import techguns.blocks.machines.OreDrillBlock;
import techguns.items.TGSlotType;
import techguns.util.BlockCoords;

public class OreDrillTileEntMaster extends MultiBlockMasterTileEntBase implements IFluidHandler {
	public byte dir=0;
	public byte radius=0;
	public short enginesize=0;
	public short rodsize=0;
	public boolean haspower=false;
	
	private int soundLoopDelay = 0;
	
	//public byte effectiveMiningLevel=0;
	//public byte effectiveClusterSize=0;
	
	public FluidStack currentLiquidOp=null;
	
	public int currentRFperTick=0;
	
	public ItemStack lastDrill=null;
	
	public boolean hasDrill=false;
	public int drillType =0;
	
	//amount of internalFuel
	protected int currentFuelBufferMax=0;
	protected int fuelBuffer=0;
	
	public FluidTank inputTank;
	public FluidTank outputTank;
		
	public OreDrillTileEntMaster() {
		super(11, 500000);
		this.hasDrill=false;
		this.useRangeSquared=128.0d;
		
		inputTank = new FluidTank(8 * FluidContainerRegistry.BUCKET_VOLUME);
		outputTank = new FluidTank(32 * FluidContainerRegistry.BUCKET_VOLUME);
	}

	public int getPowerPerTick(){
		return this.currentRFperTick;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if (this.complete){
			int r = radius==2?2:1;
			if (dir==0){
				//UP
				return AxisAlignedBB.getBoundingBox(xCoord-r, yCoord, zCoord-r, xCoord +r, yCoord +enginesize+rodsize, zCoord + r);
			} else if (dir==1){
				//down
				return AxisAlignedBB.getBoundingBox(xCoord-r, yCoord -enginesize-rodsize, zCoord-r, xCoord +r, yCoord, zCoord + r);
			} else if (dir==2){
				//+X
				return AxisAlignedBB.getBoundingBox(xCoord, yCoord-r, zCoord-r, xCoord +enginesize+rodsize, yCoord+r, zCoord + r);
			} else if (dir==3){
				//-X
				return AxisAlignedBB.getBoundingBox(xCoord-enginesize-rodsize, yCoord-r, zCoord-r, xCoord, yCoord+r, zCoord + r);
			} else if (dir==4){
				//+Z
				return AxisAlignedBB.getBoundingBox(xCoord-r, yCoord-r, zCoord, xCoord +r, yCoord +r, zCoord + enginesize+rodsize);
			} else if (dir==5){
				//-Z
				return AxisAlignedBB.getBoundingBox(xCoord-r, yCoord-r, zCoord- enginesize-rodsize, xCoord +r, yCoord +r, zCoord);
			} else {
				return super.getRenderBoundingBox();
			}
		} else {
			return super.getRenderBoundingBox();
		}
		//return super.getRenderBoundingBox();
	}

	@Override
	public void updateEntity() {
		if (complete){
			if (hasDrill){
				if(this.isRedstoneEnabled()){
	
					if (this.currentOperation==null){
						
						if(!this.worldObj.isRemote){
							checkAndStartOperation();
						}
						
					} else {
						
						if(this.progress<this.totalTime){
						
							if(!this.worldObj.isRemote && this.checkDrill()){
								if (consumePower(this.getPowerPerTick())){
									this.progress++;
									
									//if(!this.worldObj.isRemote){
										if (this.haspower==false){
											this.haspower=true;
											this.needUpdate();
										}
									//}
								} else {
									//if(!this.worldObj.isRemote){
										if (this.haspower==true){
											this.haspower=false;
											this.needUpdate();
			
										}
									//}
								}
							} else {
								//client behaviour
								if (this.haspower){
									this.progress++;
									this.playAmbientSound();
								}
							}
						} else {
							
							//finished operation
							if (!this.worldObj.isRemote){
								
								if (currentLiquidOp==null){
								
									int leftover = Techguns.addItemToInventory(this.content,new ItemStack(this.currentOperation.getItem(), this.currentOperation.stackSize, this.currentOperation.getItemDamage()), 1, 10);
									//if (!this.worldObj.isRemote){
									if (leftover >0){
										this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord+1, this.zCoord, TGItems.newStack(this.currentOperation, leftover)));
									}
									//}
									this.currentOperation=null;
									checkAndStartOperation();
								} else {
									
									this.outputTank.fill(this.currentLiquidOp, true);
									
									this.currentLiquidOp=null;
									//this.currentOperation=null;
									checkAndStartOperation();
								}
							}
						}
		
					}
				/*} else if (this.currentOperation!=null){
					this.currentOperation=null;
					this.totalTime=0;
					this.progress=0;*/
				}
			} else {
				//check if has Drill
				
				
			}
		}
		
	}
		
	private void playAmbientSound() {	
		if (soundLoopDelay-- <= 0) {
			if (this.radius == 0) {
				worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "techguns:machines.oredrillSmallWork", 1.5F, 1.0F, true );
				soundLoopDelay = 61; //62 ticks = 3.1 sec
			}else if (this.radius <= 2) {
				worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "techguns:machines.oredrillMediumWork", 2.25F, 1.0F, true );
				soundLoopDelay = 61; //62 ticks = 3.1 sec
			}else {
				worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "techguns:machines.oredrillLargeWork", 3.0F, 1.0F, true );
				soundLoopDelay = 61; //62 ticks = 3.1 sec
			}
		}
	}

	public int getClusterSize(int count, int x, int y, int z, ArrayList<BlockCoords> visited, OreCluster clusterType, int clusterMeta){

		Block target = this.worldObj.getBlock(x,y,z);
		int targetmeta = this.worldObj.getBlockMetadata(x, y, z);
		BlockCoords block=new BlockCoords(x,y,z);
		if (target instanceof OreCluster && !visited.contains(block)){
			OreCluster cluster = (OreCluster) target;
			if (clusterType == cluster && clusterMeta == targetmeta) {
				count++;
				visited.add(block);
				
				count=getClusterSize(count,x+1,y,z, visited,clusterType,clusterMeta);
				count=getClusterSize(count,x-1,y,z, visited,clusterType,clusterMeta);
				count=getClusterSize(count,x,y+1,z, visited,clusterType,clusterMeta);
				count=getClusterSize(count,x,y-1,z, visited,clusterType,clusterMeta);
				count=getClusterSize(count,x,y,z+1, visited,clusterType,clusterMeta);
				count=getClusterSize(count,x,y,z-1, visited,clusterType,clusterMeta);
				
				return count;
			} else {
				//stop recursion, no cluster
				return count;
			}
		} else {
			return count;
		}
	}
	protected boolean checkDrill(){
		if(this.worldObj.getTotalWorldTime()%20==0){
			if (this.lastDrill==null && this.content[0]==null) {
				//this.complete=false;
				this.hasDrill=false;
				this.needUpdate();
				return false;
			} else if (this.lastDrill==null){
				this.lastDrill=this.content[0];
				return true;
			} else {
				if(this.content[0]==null || !this.lastDrill.isItemEqual(this.content[0])){
					//System.out.println("Drill no longer matches");
					this.lastDrill=this.content[0];
					this.currentOperation=null;
					this.progress=0;
					this.totalTime=0;
					this.currentRFperTick=0;
					this.needUpdate();
					return false;
				}
				
			}
		}
		return true;
	}
	
	
	public void checkAndStartOperation(){
		//look for cluster
		int distance = this.rodsize+this.enginesize+1;
		int[] targetPos = OreDrillBlock.getNextBlockInDirOffset(this.xCoord, this.yCoord, this.zCoord, this.dir, distance);

		if (ItemBlockOreDrill.isOreCluster(this.worldObj, targetPos[0], targetPos[1],targetPos[2]))
		{
			
			OreCluster cluster = (OreCluster) this.worldObj.getBlock(targetPos[0],targetPos[1],targetPos[2]);
			int meta = this.worldObj.getBlockMetadata(targetPos[0],targetPos[1],targetPos[2]);

			int drillheadmininglevel = this.getDrillItemMiningLevel();
			if (drillheadmininglevel >0){
				this.hasDrill=true;
				int effectivemininglevel = drillheadmininglevel+this.radius-cluster.mininglevels[meta];
				this.drillType = this.getDrillType();
				
				if (effectivemininglevel >=0){
					int clustersize = this.getClusterSize(0, targetPos[0],targetPos[1],targetPos[2], new ArrayList<BlockCoords>(), cluster,meta);
					int effectiveclustersize = Math.min(clustersize, this.getTotalDrillLength());
					
					this.currentOperation=TGItems.newStack(cluster.ores[meta],1);
					if (cluster.fluids[meta]!=null){
						this.currentLiquidOp = cluster.fluids[meta].copy();
					} else {
						this.currentLiquidOp=null;
					}
					this.progress=0;
					
					double orePerHour = ((effectiveclustersize*3)+(effectiveclustersize*effectivemininglevel*0.5))*TGConfig.oreDrillMultiplierOres*cluster.multipliersAmount[meta];
					
					this.totalTime=(int)(60.0f/orePerHour*1200.0);
					this.currentRFperTick=(int)(80*orePerHour*Math.max(this.radius, 1)*TGConfig.oreDrillMultiplierPower*cluster.multipliersPower[meta]);
				} else {
					this.currentOperation=new ItemStack(Blocks.cobblestone,1);
					this.currentLiquidOp=null;
					this.progress=0;
					this.totalTime=20*20;
					this.currentRFperTick=(int) (240*TGConfig.oreDrillMultiplierPower);
					
				}
			} else {
				//no drillhead, broken
				//this.complete=false;
				this.hasDrill=false;
				this.drillType = 0;
			}
			this.needUpdate();
			
			
			
		} else {
			//System.out.println("There is no ore Cluster at drill point, multiblock broken");
			this.complete=false;
			this.needUpdate();
		}
	}

	
	
	/**
	 * Horrible hardcoded method to get the effective mining level
	 * @return
	 */
	public int getDrillItemMiningLevel(){
		ItemStack drill = this.content[0];
		if(drill!=null && this.isComplete()){
			
			int mininglevel=-1;
			if(this.radius==0){
				if (drill.getItem()==TGItems.sharedItem){
				
					if(drill.getItemDamage()==TGItems.oreDrillSmallSteel.getItemDamage()){
						mininglevel=1;
					} else if (drill.getItemDamage()==TGItems.oreDrillSmallObsidianSteel.getItemDamage()){
						mininglevel=2;
					} else if (drill.getItemDamage()==TGItems.oreDrillSmallCarbon.getItemDamage()){
						mininglevel=3;
					}	
				}
			} else if (radius==1 || radius==2){
				if (drill.getItem()==TGItems.sharedItem){
					
					if(drill.getItemDamage()==TGItems.oreDrillMediumSteel.getItemDamage()){
						mininglevel=1;
					} else if (drill.getItemDamage()==TGItems.oreDrillMediumObsidianSteel.getItemDamage()){
						mininglevel=2;
					} else if (drill.getItemDamage()==TGItems.oreDrillMediumCarbon.getItemDamage()){
						mininglevel=3;
					}	
				}
			} else if (radius==3 || radius==4){
				if (drill.getItem()==TGItems.sharedItem){
					
					if(drill.getItemDamage()==TGItems.oreDrillLargeSteel.getItemDamage()){
						mininglevel=1;
					} else if (drill.getItemDamage()==TGItems.oreDrillLargeObsidianSteel.getItemDamage()){
						mininglevel=2;
					} else if (drill.getItemDamage()==TGItems.oreDrillLargeCarbon.getItemDamage()){
						mininglevel=3;
					}	
				}
			}
			
			return mininglevel;
			/*if (mininglevel>0){
			
				int coords[]=OreDrillBlock.getNextBlockInDirOffset(xCoord, yCoord, zCoord, dir, enginesize+rodsize);
				if(ItemBlockOreDrill.isOreCluster(this.worldObj, coords[0], coords[1], coords[2])){
					OreCluster cluster = (OreCluster) this.worldObj.getBlock(coords[0], coords[1],coords[2]);
					int meta = this.worldObj.getBlockMetadata(coords[0], coords[1],coords[2]);
					
					int level = this.radius+mininglevel - cluster.mininglevels[meta]; 
					if(level>3){
						level=3;
					}
					return level;
					
				}
			}*/
		}
		
		return -1;
		
	}
	/**
	 * Return drill type for rendering, 0- none, 1 -steel, 2-obsidian, 3-carbon
	 * saved in nbt because it needs to be sent to client
	 * @return
	 */
	protected int getDrillType(){
		ItemStack drill = this.content[0];
		if(drill!=null && this.isComplete()){
			
			int drilltype=0;
			if(this.radius==0){
				if (drill.getItem()==TGItems.sharedItem){
				
					if(drill.getItemDamage()==TGItems.oreDrillSmallSteel.getItemDamage()){
						drilltype=1;
					} else if (drill.getItemDamage()==TGItems.oreDrillSmallObsidianSteel.getItemDamage()){
						drilltype=2;
					} else if (drill.getItemDamage()==TGItems.oreDrillSmallCarbon.getItemDamage()){
						drilltype=3;
					}	
				}
			} else if (radius==1 || radius==2){
				if (drill.getItem()==TGItems.sharedItem){
					
					if(drill.getItemDamage()==TGItems.oreDrillMediumSteel.getItemDamage()){
						drilltype=1;
					} else if (drill.getItemDamage()==TGItems.oreDrillMediumObsidianSteel.getItemDamage()){
						drilltype=2;
					} else if (drill.getItemDamage()==TGItems.oreDrillMediumCarbon.getItemDamage()){
						drilltype=3;
					}	
				}
			} else if (radius==3 || radius==4){
				if (drill.getItem()==TGItems.sharedItem){
					
					if(drill.getItemDamage()==TGItems.oreDrillLargeSteel.getItemDamage()){
						drilltype=1;
					} else if (drill.getItemDamage()==TGItems.oreDrillLargeObsidianSteel.getItemDamage()){
						drilltype=2;
					} else if (drill.getItemDamage()==TGItems.oreDrillLargeCarbon.getItemDamage()){
						drilltype=3;
					}	
				}
			}
			
			return drilltype;
			
		}
		
		return 0;
		
	}
	
	public int getTotalDrillLength(){
		return this.enginesize+this.rodsize;
	}
	
	public void setMultiblockFormed(byte dir, byte radius, short enginesize, short rodsize){
		this.dir=dir;
		this.radius=radius;
		this.enginesize=enginesize;
		this.rodsize=rodsize;
		this.complete=true;
		//this.hasDrill=false;
		
		this.hasDrill = this.getDrillItemMiningLevel() > -1;
		
		
	}


	@Override
	public void readEntityDataFromNBT(NBTTagCompound tags) {
		super.readEntityDataFromNBT(tags);
		this.complete=tags.getBoolean("complete");
		if(this.complete){
			this.dir=tags.getByte("dir");
			this.radius=tags.getByte("radius");
			this.enginesize = tags.getShort("enginesize");
			this.rodsize = tags.getShort("rodsize");
			this.haspower=tags.getBoolean("haspower");
			this.currentRFperTick=tags.getInteger("currentRFperTick");
			this.hasDrill=tags.getBoolean("hasDrill");
			this.drillType=tags.getByte("drillType");
		}
		this.fuelBuffer=tags.getInteger("fuelBuffer");
		this.currentFuelBufferMax = tags.getInteger("fuelBufferMax");
		
		NBTTagCompound tankIn = tags.getCompoundTag("inputTank");
		if (tankIn != null) {
			inputTank.readFromNBT(tankIn);
		}
		NBTTagCompound tankOut = tags.getCompoundTag("outputTank");
		if (tankOut != null) {
			outputTank.readFromNBT(tankOut);
		}
		
		NBTTagCompound liquidOpTag = tags.getCompoundTag("currentLiquidOp");
		if (liquidOpTag!=null){
			this.currentLiquidOp = FluidStack.loadFluidStackFromNBT(liquidOpTag);
		}
	}



	@Override
	public void writeEntityDataToNBT(NBTTagCompound tags) {
		super.writeEntityDataToNBT(tags);
		tags.setBoolean("complete", complete);
		if (this.complete){
			tags.setByte("dir", this.dir);
			tags.setByte("radius", this.radius);
			tags.setShort("enginesize",this.enginesize);
			tags.setShort("rodsize",this.rodsize);
			tags.setBoolean("haspower", this.haspower);
			tags.setInteger("currentRFperTick", this.currentRFperTick);
			tags.setBoolean("hasDrill", this.hasDrill);
			tags.setByte("drillType", (byte)this.drillType);
		}
		tags.setInteger("fuelBuffer", this.fuelBuffer);
		tags.setInteger("fuelBufferMax", this.currentFuelBufferMax);
		
		NBTTagCompound tagInputTank = new NBTTagCompound();
		NBTTagCompound tagOutputTank = new NBTTagCompound();
		
		inputTank.writeToNBT(tagInputTank);
		tags.setTag("inputTank", tagInputTank);
		outputTank.writeToNBT(tagOutputTank);
		tags.setTag("outputTank", tagOutputTank);
		
		if(this.currentLiquidOp!=null){
			NBTTagCompound liquidOpTag = new NBTTagCompound();
			this.currentLiquidOp.writeToNBT(liquidOpTag);
			tags.setTag("currentLiquidOp", liquidOpTag);
		}
	}


	/**
	 * The "radius" of the drill including the middle block, so 1 for 1x1xN, 2 for 3x3xN. 3 for 5x5xN and 4 for 7x7xN
	 * 1x1x1 drill with only controller has radius 0
	 * @return
	 */
	public int getDrillRadius(){
		if (this.isComplete()){
			return this.radius;
		} else {
			return 0;
		}
		
	}
	
	@Override
	public void notifiyMultiblockBreak() {
		this.unform();
	}
	
	
	protected void unform(){
		//System.out.println("UNFORM!!!");
		if(this.isComplete()){
		
			//System.out.println("DIR:"+this.dir);
			
			ArrayList<BlockCoords> positions;
			BlockCoords controller = new BlockCoords(this);
			

				
				
				int xsizeMax=0;
				int ysizeMax=0;
				int zsizeMax=0;
				
				int xsizeMin=0;
				int ysizeMin=0;
				int zsizeMin=0;
				
				
				int rad = this.getDrillRadius();
				
				switch(this.dir){
					case 0: //UP
						xsizeMax = rad;
						zsizeMax = rad;
						xsizeMin = -rad;
						zsizeMin = -rad;
						ysizeMin = 1;
						ysizeMax = this.getTotalDrillLength();
						break;
					case 1: //DOWN
						xsizeMax = rad;
						zsizeMax = rad;
						xsizeMin = -rad;
						zsizeMin = -rad;
						ysizeMin = -this.getTotalDrillLength();
						ysizeMax = -1;
						break;
					case 2: //+X
						
						ysizeMin = -rad;
						ysizeMax = rad;
						zsizeMin= -rad;
						zsizeMax = rad;
						
						xsizeMin = 1;
						xsizeMax = this.getTotalDrillLength(); 
						
						break;
						
					case 3: //-X
										
						ysizeMin = -rad;
						ysizeMax = rad;
						zsizeMin= -rad;
						zsizeMax = rad;
						
						xsizeMax = -1;
						xsizeMin = -this.getTotalDrillLength(); 
						
						break;
						
					case 4: //+Z
						
						ysizeMin = -rad;
						ysizeMax = rad;
						xsizeMin= -rad;
						xsizeMax = rad;
						
						zsizeMin = 1;
						zsizeMax = this.getTotalDrillLength(); 
						
						break;
						
					case 5: //-Z
										
						ysizeMin = -rad;
						ysizeMax = rad;
						xsizeMin= -rad;
						xsizeMax = rad;
						
						zsizeMax = -1;
						zsizeMin = -this.getTotalDrillLength(); 
						
						break;
				}
				
				
				positions = controller.getBoxWithOffsets(xsizeMin, xsizeMax, ysizeMin, ysizeMax, zsizeMin, zsizeMax, false);			
				
				if (positions !=null){
				
					//System.out.println("Pos:"+positions.size());
					
					for (int i =0;i<positions.size();i++){
						BlockCoords pos = positions.get(i);
	
						TileEntity tile = this.worldObj.getTileEntity(pos.x, pos.y, pos.z);
						if(tile!=null && tile instanceof OreDrillTileEntSlave){
							//System.out.println("UNLINK TILE");
							OreDrillTileEntSlave slave = (OreDrillTileEntSlave) tile;	
							slave.unlink();
							slave.needBlockUpdate();
							slave.needUpdate();
						} 
						//this.worldObj.setBlock(pos.x, pos.y, pos.z, Blocks.dirt);
					}
					
				}
				
				
			}
		
		this.setComplete(false);
		this.needBlockUpdate();
		this.needUpdate();
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[]{1,2,3,4,5,6,7,8,9,10};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item,
			int side) {
		return slot==10;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item,
			int side) {
		return slot>0 && slot <=9;
	}

	@Override
	public String getInventoryName() {
		return "techguns.container.oredrill";
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		if (slot==10){
			return TileEntityFurnace.getItemBurnTime(item)>0;
		} 
		return false;
	}

	/**
	 * Helper class for counting algorithm
	 */
	/*public static class BlockPos{
		private int x,y,z;
		public BlockPos(int x,int y,int z){
			this.x=x;
			this.y=y;
			this.z=z;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof BlockPos){
				BlockPos other= (BlockPos) obj;
				return this.x==other.x && this.y==other.y && this.z==other.z;
			} else {
				return false;
			}
		}
		
	}*/
	
	/**
	 * called serverside when this tileent should send out updated to client
	 */
	public void needUpdate(){
		if(!this.worldObj.isRemote){
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			this.markDirty();
		}
	}

	@Override
	public void onBlockBreak() {
		super.onBlockBreak();
		this.notifiyMultiblockBreak();
	}
	
	@Override
	protected boolean consumePower(int amount){
		if ( TGConfig.machinesNeedNoPower){
			return true;
		}
		
		float FACTOR=TGConfig.oreDrillFuelMultiplier;
		
		//check fuel buffer
		if (this.fuelBuffer*FACTOR>=amount){
			this.fuelBuffer -= Math.max(amount/FACTOR,1);
			return true;
		} else {
			amount -=this.fuelBuffer*FACTOR;
			this.fuelBuffer=0;
			this.currentFuelBufferMax=0;
			//check fuelBuffer refill;
			
			int fuelValue = 0;
			if (this.content[10]!=null){
				fuelValue = TileEntityFurnace.getItemBurnTime(this.content[10]);
			}
			
			//	if (FluidContainerRegistry.isContainer(this.content[10])){
			if (fuelValue > 0){
				ItemStack container = this.content[10].getItem().getContainerItem(this.content[10]);//FluidContainerRegistry.drainFluidContainer(this.content[10]);
				
					if (container!=null){
						int leftover = Techguns.addItemToInventory(this.content,container, 1, 10);
						//if (!this.worldObj.isRemote){
						if (leftover >0 && !this.worldObj.isRemote){
							ItemStack entityItem = container.copy();
							entityItem.stackSize=leftover;
							this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord+1, this.zCoord, entityItem));
						}
					}
				
				//}
				
				
				this.content[10].stackSize--;
				if(this.content[10].stackSize<=0){		
					this.content[10]=null;
				}
				this.fuelBuffer=fuelValue;
				this.currentFuelBufferMax=fuelValue;
			//System.out.println("Fuel Value:"+fuelValue);
			} else {
				
				//check input tank
				float fuelvalue = this.getFuelValueFromInputTank();
				if (fuelvalue >0){
					
					FluidStack drainedFluid = this.inputTank.drain(FluidContainerRegistry.BUCKET_VOLUME, true);
					this.fuelBuffer=(int) (drainedFluid.amount*fuelvalue);					
					this.currentFuelBufferMax = (int) (drainedFluid.amount*fuelvalue);
					
					
				}
				
				
			}
			
			
		}
		
		if (this.fuelBuffer*FACTOR>=amount){
			this.fuelBuffer -= Math.max(amount/FACTOR,1);
			return true;
		}
		
		if (amount >0 && this.energy.getEnergyStored()>=amount){
			this.energy.modifyEnergyStored(-amount);
			return true;
		} else if (amount==0){
				return true;
		} else {
			return false;
		}
	}

	protected float getFuelValueFromInputTank(){
		if (this.inputTank.getFluid()!=null){
			
			if (this.inputTank.getFluid().getFluid() == TGFluids.LAVA){
				return 20F; //Same as LAVA BUCKET /1000
			} else if (this.inputTank.getFluid().getFluid() == TGFluids.FUEL){
				return TGConfig.oreDrillFuelValueFuel;
			} else if (this.inputTank.getFluid().getFluid() == TGFluids.BIOFUEL){
				return TGConfig.oreDrillFuelValueBioFuel;
			} 
			
		}
		
		return 0F;
	}
	
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(resource !=null && (resource.getFluid()==TGFluids.LAVA || resource.getFluid()==TGFluids.FUEL || resource.getFluid()==TGFluids.BIOFUEL)){
			return inputTank.fill(resource, doFill);
		}
		
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (resource == null || !resource.isFluidEqual(outputTank.getFluid())) {
			return null;
		}
		return outputTank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return outputTank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { inputTank.getInfo(), outputTank.getInfo() };
	}

	public int getFuelBuffer() {
		return fuelBuffer;
	}

	public void setFluidTypeIn(int id) {
		Fluid f = FluidRegistry.getFluid(id);
		if (f!=null){
			this.inputTank.setFluid(new FluidStack(f, 0));
		} else {
			this.inputTank.setFluid(null);
		}
	}
	
	public void setFluidTypeOut(int id) {
		Fluid f = FluidRegistry.getFluid(id);
		if (f!=null){
			this.outputTank.setFluid(new FluidStack(f, 0));
		} else {
			this.outputTank.setFluid(null);
		}
	}
	
	public void setFluidAmountIn(int value) {
		FluidStack fluid = this.inputTank.getFluid();
		if (fluid != null) {
			fluid.amount = value;
		}
	}

	public void setFluidAmountOut(int value) {
		FluidStack fluid = this.outputTank.getFluid();
		if (fluid != null) {
			fluid.amount = value;
		}
	}

	public void setFuelBuffer(int fuelBuffer) {
		this.fuelBuffer = fuelBuffer;
	}

	public int getFluidType(boolean output) {
		FluidStack fluidstack;
		if (output) {
			fluidstack = this.outputTank.getFluid();
		} else {
			fluidstack = this.inputTank.getFluid();
		}
		if (fluidstack!=null){
			return fluidstack.getFluidID();
		} else {
			return -1;
		}
	}

	public int getCurrentFuelBufferMax() {
		return currentFuelBufferMax;
	}

	public void setCurrentFuelBufferMax(int currentFuelBufferMax) {
		this.currentFuelBufferMax = currentFuelBufferMax;
	}
	
	 /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    @SideOnly(Side.CLIENT)
    public int getFuelBufferScaled(int limit)
    {
    	if (this.currentFuelBufferMax>0){
    		return this.fuelBuffer * limit / this.currentFuelBufferMax;
    	} else {
    		return 0;
    	}
    }
	
	public int getValidSlotForItemInMachine(ItemStack stack1) {
		if (stack1.getItem()==TGItems.sharedItem){
			TGSlotType type = TGItems.sharedItem.slotType[stack1.getItemDamage()];
			if (type==TGSlotType.DRILL_SMALL || type == TGSlotType.DRILL_MEDIUM || type == TGSlotType.DRILL_LARGE){
				return 0;
			}
		}
		if (TileEntityFurnace.getItemBurnTime(stack1)>0){
			return 10;
		}
		
		return -1;
	}
}
