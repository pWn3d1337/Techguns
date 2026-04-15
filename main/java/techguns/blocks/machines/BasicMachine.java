package techguns.blocks.machines;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import techguns.TGBlocks;
import techguns.Techguns;
import techguns.tileentities.AmmoPressTileEnt;
import techguns.tileentities.BasicInventoryTileEnt;
import techguns.tileentities.BasicMachineTileEntity;
import techguns.tileentities.BasicOwnedTileEnt;
import techguns.tileentities.BasicPoweredTileEnt;
import techguns.tileentities.CamoBenchTileEnt;
import techguns.tileentities.CampFlagTileEnt;
import techguns.tileentities.ChargingStationTileEnt;
import techguns.tileentities.ChemLabTileEnt;
import techguns.tileentities.ExplosiveChargeAdvTileEnt;
import techguns.tileentities.ExplosiveChargeTileEnt;
import techguns.tileentities.MetalPressTileEnt;
import techguns.tileentities.RepairBenchTileEnt;
import techguns.tileentities.TurretBaseEnt;
import techguns.util.TextUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BasicMachine extends BlockContainer {
	protected static final int MAXMETADATA_VALUE = 10;
	
	
	public BasicMachine(){
		this(Material.iron);
	}
	
	public BasicMachine(Material mat) {
		super(mat);
		setCreativeTab(Techguns.tabTechgun);
		setHardness(4.0f);
		setBlockName("BasicMachine");
		setStepSound(soundTypeMetal);
		setResistance(20.0f);
		for(int i=0;i<6;i++){
			setHarvestLevel("pickaxe", 1,i);
		}
		
		//camp flag
		setHarvestLevel("pickaxe", 1,7);
		
		//for effects
		setBlockTextureName("techguns:screen_top");
	}

	
	
	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta) {
		if(meta==6 || meta==8){
			return true;
		}
		return super.canHarvestBlock(player, meta);
	}
	
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta==6 || meta==8){
			return 0.10f;
		}
		return this.blockHardness;
	}

	@Override
	public int damageDropped (int metadata) {
		return metadata;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		//super.onNeighborBlockChange(world, x, y, z, block);
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile!=null && tile instanceof BasicPoweredTileEnt){
			((BasicPoweredTileEnt)tile).onNeighborBlockChange();		
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab,
			List subItems) {
		for (int ix = 0; ix < MAXMETADATA_VALUE; ix++) {
			if (ix!=7){
				subItems.add(new ItemStack(item, 1, ix));
			}
		}
	}
	
	
	 /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        this.setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
        return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
    {
        this.setBlockBoundsBasedOnState(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
        return super.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
    }
	
    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
       int meta =world.getBlockMetadata(x, y, z);
       if(meta==6||meta==8){ //Explosive Charge
    	   TileEntity tile = world.getTileEntity(x, y, z);
    	   if(tile instanceof ExplosiveChargeTileEnt){
    		   ExplosiveChargeTileEnt charge = (ExplosiveChargeTileEnt) tile;
    		   
    		   float w=0.2f;
    		   float h=0.3f;
    		   
    		   if (charge.orientation==0){
    			   this.setBlockBounds(w, 1-h, w, 1-w, 1.0f, 1-w);
    		   } else if (charge.orientation==1){
    			   this.setBlockBounds(w, 0, w, 1-w,h, 1-w);
    			   
    		   } else if (charge.orientation==2){
    			   this.setBlockBounds(w, w,1-h, 1-w, 1-w,1);
    		   } else if (charge.orientation==3){
    			   this.setBlockBounds(w, w,0, 1-w, 1-w,h);
    		   }
    		   
    		   else if (charge.orientation==4){
    			   this.setBlockBounds(1-h,w, w,1, 1-w, 1-w);
    		   } else if (charge.orientation==5){
    			   this.setBlockBounds(0,w, w,h, 1-w, 1-w);
    		   }
    	   } else {
    		   this.setBlockBounds(0, 0, 0, 1, 1, 1);
    	   }
       } else {
    	   this.setBlockBounds(0, 0, 0, 1, 1, 1);
       }
    }
	
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		//System.out.println("META:"+meta);
		switch(meta){
			case 0:
				return new AmmoPressTileEnt();
			case 1:
				return new MetalPressTileEnt();
			case 2:
				return new TurretBaseEnt();
			case 3:
				return new CamoBenchTileEnt();
			case 4:
				return new ChemLabTileEnt();
			case 5:
				return new RepairBenchTileEnt();
			case 6:
				return new ExplosiveChargeTileEnt();
			case 7:
				return new CampFlagTileEnt();
			case 8:
				return new ExplosiveChargeAdvTileEnt();
			case 9:
				return new ChargingStationTileEnt();
			/*case 10:
				return new ScrapperTileEnt();*/
			default:
				return null;
		}
		
	}

	/* (non-Javadoc)
	 * @see net.minecraft.block.BlockContainer#breakBlock(net.minecraft.world.World, int, int, int, net.minecraft.block.Block, int)
	 */
	@Override
	public void breakBlock(World world, int x, int y,
			int z, Block block, int meta) {
		
		if(meta==2){ //Turret
			TurretBaseEnt ent = (TurretBaseEnt) world.getTileEntity(x, y, z);
			if(ent.mountedTurret!=null){
				ent.mountedTurret.destroy();
			}
		}
		
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof BasicInventoryTileEnt){
			((BasicInventoryTileEnt) tile).onBlockBreak();
		}
		
		world.removeTileEntity(x, y, z);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#getItemDropped(int, java.util.Random, int)
	 */
	@Override
	public Item getItemDropped(int meta, Random rnd,
			int i2) {
		//System.out.println("I1:"+i1+" I2:"+i2);
		if (meta != 7){
			return Item.getItemFromBlock(this); //new ItemBlockMetaData(this);
		} else {
			//Flag doesn't drop itself
			return Items.iron_ingot;
		}
	}

	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#getItem(net.minecraft.world.World, int, int, int)
	 */
	@Override
	public Item getItem(World world, int x, int y,
			int z) {
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
        this.direction(world, x, y, z);
        //System.out.println("ON BLOCK ADDED");
        
    }

    protected void direction(World world, int x, int y, int z)
    {
        if (!world.isRemote)
        {
            Block block = world.getBlock(x, y, z - 1);
            Block block1 = world.getBlock(x, y, z + 1);
            Block block2 = world.getBlock(x - 1, y, z);
            Block block3 = world.getBlock(x + 1, y, z);
            byte b0 = 3;

            if (block.func_149730_j() && !block1.func_149730_j())
            {
                b0 = 1;
            }

            if (block1.func_149730_j() && !block.func_149730_j())
            {
                b0 = 0;
            }

            if (block2.func_149730_j() && !block3.func_149730_j())
            {
                b0 = 3;
            }

            if (block3.func_149730_j() && !block2.func_149730_j())
            {
                b0 = 2;
            }

            //world.setBlockMetadataWithNotify(x, y, z, b0, 2);
            BasicInventoryTileEnt tile = (BasicInventoryTileEnt) world.getTileEntity(x, y, z);
            tile.rotation=b0;
            //System.out.println("Rotation:"+tile.rotation);
        }
    }

	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#onBlockPlacedBy(net.minecraft.world.World, int, int, int, net.minecraft.entity.EntityLivingBase, net.minecraft.item.ItemStack)
	 */
	@Override
	public void onBlockPlacedBy(World world, int x,
			int y, int z, EntityLivingBase ent,
			ItemStack item) {
		//System.out.println("ON BLOCK PLACED");
		int dir = MathHelper.floor_double((double)(ent.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		BasicInventoryTileEnt tile = (BasicInventoryTileEnt) world.getTileEntity(x, y, z);
		
		if (ent instanceof EntityPlayer && tile instanceof BasicOwnedTileEnt){
			((BasicOwnedTileEnt)tile).setOwner((EntityPlayer)ent);
		}
		
		
		/*switch(dir){
			case 0:
				//world.setBlockMetadataWithNotify(x, y, z, 2, 2);
				tile.rotation=0;
				break;
			case 1:
				//world.setBlockMetadataWithNotify(x, y, z, 5, 2);
				tile.rotation=1;
				break;
			case 2:
				//world.setBlockMetadataWithNotify(x, y, z, 3, 2);
				tile.rotation=2;
				break;
			case 3:
				//world.setBlockMetadataWithNotify(x, y, z, 4, 2);
				tile.rotation=3;
				break;
		}
		System.out.println("Rotation:"+tile.rotation);*/
		if(!world.isRemote){
			NBTTagCompound data= item.getTagCompound();
			if (data!=null){
				if(data.hasKey("TileEntityData")){
					tile.readEntityDataFromNBT(data.getCompoundTag("TileEntityData"));
					//System.out.println("SET TILEENTITY DATA FROM NBT");
					tile.rotation=(byte) ((dir+2)%4);
				}
			}			
			if(tile instanceof TurretBaseEnt){
	        	if(!((TurretBaseEnt) tile).turretDeath){
	        		((TurretBaseEnt) tile).spawnTurret(world,x,y,z);
	        	}
			}
        	tile.needUpdate();
			
		} else {
			tile.rotation=(byte) ((dir+2)%4);
		}
		
		
		//if(item.hasDisplayName()){
			//((TileEntity) world.getTileEntity(x, y, z)).;
		//}
	}

	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#onBlockActivated(net.minecraft.world.World, int, int, int, net.minecraft.entity.player.EntityPlayer, int, float, float, float)
	 */
	@Override
	public boolean onBlockActivated(World world, int x,
			int y, int z, EntityPlayer player,
			int i1, float f1, float f2,
			float f3) {
		
		//System.out.println("ON ACTIVATED!!!");
				int meta =world.getBlockMetadata(x,y,z);
				
				ItemStack helditem = player.getHeldItem();
				if (helditem!=null && helditem.getItem().getToolClasses(helditem).contains("wrench") && meta != 6 && meta !=8 && meta!=7){
						
					BasicInventoryTileEnt tileent = (BasicInventoryTileEnt) world.getTileEntity(x, y, z);
					if (!player.isSneaking()){
						tileent.rotate();
						//System.out.println("Rotation:"+tileent.rotation);
						return true;
				
					} else {
						//System.out.println("Wrench dismantle");
						
						if (!world.isRemote){
							NBTTagCompound tileEntTags =new NBTTagCompound();
							tileent.writeEntityDataToNBT(tileEntTags);
							ItemStack item = new ItemStack(Item.getItemFromBlock(TGBlocks.basicMachine),1,meta);
							NBTTagCompound itemnbt = item.getTagCompound();
							if (itemnbt==null){
								itemnbt=new NBTTagCompound();
								item.setTagCompound(itemnbt);
							}
							itemnbt.setTag("TileEntityData", tileEntTags);
							tileent.emptyContent();
							world.setBlockToAir(x, y, z);
							world.spawnEntityInWorld(new EntityItem(world,x, y,z, item));
						}
						
						return false;
					}
				
				} else if (helditem!=null && hasBucketInteraction(meta) && FluidContainerRegistry.isContainer(helditem)) {
					
					BasicMachineTileEntity ent =  (BasicMachineTileEntity) world.getTileEntity(x, y, z);
					
					//System.out.println("fluid container interact");
					
					if (ent!=null){
						ItemStack newHeldItem = ent.onFluidContainerInteract(helditem);
						if (newHeldItem!=helditem){
							if (helditem.stackSize==1 && !player.capabilities.isCreativeMode){
								int slot = player.inventory.currentItem;
								player.inventory.setInventorySlotContents(slot, newHeldItem);
								if (!world.isRemote){
									player.inventoryContainer.detectAndSendChanges();
									//((EntityPlayerMP) player).playerNetServerHandler.sendPacket(new S2FPacketSetSlot(player.inventoryContainer.windowId, slot, newHeldItem));
								}
								helditem.stackSize=0;
							} else {
								if (!player.capabilities.isCreativeMode){
									helditem.stackSize--;
								}
								if(!player.inventory.addItemStackToInventory(newHeldItem)){
									player.dropPlayerItemWithRandomChoice(newHeldItem, false);
								}
							}
							
							return true;
						}
						return false;
					} else {
						return false;
					}
				} else {
						
					if (meta<MAXMETADATA_VALUE){
						//System.out.println("BLA");
						if(meta==6 || meta==8){
							ExplosiveChargeTileEnt charge = (ExplosiveChargeTileEnt) world.getTileEntity(x, y, z);
							if(charge.isArmed()){
								return false;
							} else {
								//both charges have same gui
								player.openGui(Techguns.MODID, 6, world, x, y, z);
								return true;
							}
							
						}else if (meta ==7) { //camp flag
							return false;
							
						} else {
							//System.out.println("BLUB");
							TileEntity tile = world.getTileEntity(x, y, z);
							if (tile instanceof BasicPoweredTileEnt){
								BasicPoweredTileEnt pwrtile = (BasicPoweredTileEnt) tile;
								
								//System.out.println("CheckUsable");
								if (pwrtile.isUseableByPlayer(player)){
									player.openGui(Techguns.MODID, meta, world, x, y, z);
									return true;
								} else {
									if(player.worldObj.isRemote){
										player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.container.security.denied")));
									}
									return true;
								}
								
							} else {
								//System.out.println("No pwrtile"+tile);
								player.openGui(Techguns.MODID, meta, world, x, y, z);
								return true;
							}
						}
					}
					return false;
				}
	}

    protected boolean hasBucketInteraction(int meta){
    	if (meta==4) {
    		return true;
    	} else {
    		return false;
    	}
    }

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world,
			int x, int y, int z) {
		return false;
	}

	//You don't want the normal render type, or it wont render properly.
    @Override
    public int getRenderType() {
            return -1;
    }
   
    //It's not an opaque cube, so you need this.
    @Override
    public boolean isOpaqueCube() {
            return false;
    }
   
    //It's not a normal block, so you need this too.
    public boolean renderAsNormalBlock() {
            return false;
    }

	@Override
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta<=5 && meta <9){
			return true;
		}
		//6-8: explosive charge, military camp flag
		return false;
	}
	
    
}
