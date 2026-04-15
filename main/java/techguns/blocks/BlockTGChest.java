package techguns.blocks;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.Techguns;
import techguns.tileentities.TGChestTileEnt;
import techguns.util.TextUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Mostly copied from vanilla BlockChest
 *
 */
public class BlockTGChest extends BlockContainer{
	  private final Random rnd = new Random();
	    public final int field1;
	   
	    public static final int GUI_ID_TGCHEST=20;
	    
	    protected static String[] doubleChestName = {"techguns.container.tgchest.double", "techguns.container.tgchest_weapon.double"};
	    
	    public int type;
	    
	    public BlockTGChest(int arg)
	    {
	        super(Material.wood);
	        this.field1 = arg;
	        this.type=arg;
	        this.setCreativeTab(Techguns.tabTechgun);
	        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	    }

	    /**
	     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	     */
	    public boolean isOpaqueCube()
	    {
	        return false;
	    }

	    /**
	     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	     */
	    public boolean renderAsNormalBlock()
	    {
	        return false;
	    }

	    /**
	     * The type of render function that is called for this block
	     */
	    public int getRenderType()
	    {
	        return -1; //return 22;
	    }

	    /**
	     * Updates the blocks bounds based on its current state. Args: world, x, y, z
	     */
	    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
	    {
	        if (p_149719_1_.getBlock(p_149719_2_, p_149719_3_, p_149719_4_ - 1) == this)
	        {
	            this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
	        }
	        else if (p_149719_1_.getBlock(p_149719_2_, p_149719_3_, p_149719_4_ + 1) == this)
	        {
	            this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
	        }
	        else if (p_149719_1_.getBlock(p_149719_2_ - 1, p_149719_3_, p_149719_4_) == this)
	        {
	            this.setBlockBounds(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	        }
	        else if (p_149719_1_.getBlock(p_149719_2_ + 1, p_149719_3_, p_149719_4_) == this)
	        {
	            this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
	        }
	        else
	        {
	            this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	        }
	    }

	    /**
	     * Called whenever the block is added into the world. Args: world, x, y, z
	     */
	    public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
	    {
	        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
	        this.func_149954_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
	        Block block = p_149726_1_.getBlock(p_149726_2_, p_149726_3_, p_149726_4_ - 1);
	        Block block1 = p_149726_1_.getBlock(p_149726_2_, p_149726_3_, p_149726_4_ + 1);
	        Block block2 = p_149726_1_.getBlock(p_149726_2_ - 1, p_149726_3_, p_149726_4_);
	        Block block3 = p_149726_1_.getBlock(p_149726_2_ + 1, p_149726_3_, p_149726_4_);

	        if (block == this)
	        {
	            this.func_149954_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_ - 1);
	        }

	        if (block1 == this)
	        {
	            this.func_149954_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_ + 1);
	        }

	        if (block2 == this)
	        {
	            this.func_149954_e(p_149726_1_, p_149726_2_ - 1, p_149726_3_, p_149726_4_);
	        }

	        if (block3 == this)
	        {
	            this.func_149954_e(p_149726_1_, p_149726_2_ + 1, p_149726_3_, p_149726_4_);
	        }
	    }

	    /**
	     * Called when the block is placed in the world.
	     */
	    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase elb, ItemStack item)
	    {
	        Block block = world.getBlock(x, y, z - 1);
	        Block block1 = world.getBlock(x, y, z + 1);
	        Block block2 = world.getBlock(x - 1, y, z);
	        Block block3 = world.getBlock(x + 1, y, z);
	        byte b0 = 0;
	        int l = MathHelper.floor_double((double)(elb.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

	        if (l == 0)
	        {
	            b0 = 2;
	        }

	        if (l == 1)
	        {
	            b0 = 5;
	        }

	        if (l == 2)
	        {
	            b0 = 3;
	        }

	        if (l == 3)
	        {
	            b0 = 4;
	        }

	        if (block != this && block1 != this && block2 != this && block3 != this)
	        {
	            world.setBlockMetadataWithNotify(x, y, z, b0, 3);
	            //No neighbouring chest
	           
	        }
	        else
	        {
	        	//UUID owner = null;
	        	//byte security = 0;
	            if ((block == this || block1 == this) && (b0 == 4 || b0 == 5))
	            {
	                if (block == this)
	                {
	                	
	                	/*TGChestTileEnt tileOther = (TGChestTileEnt) world.getTileEntity(x, y, z-1);
	                	owner = tileOther.getOwner();
	                	security = tileOther.getSecurity();*/
	                    world.setBlockMetadataWithNotify(x, y, z - 1, b0, 3);
	                }
	                else
	                {
	                	/*TGChestTileEnt tileOther = (TGChestTileEnt) world.getTileEntity(x, y, z+1);
	                	owner = tileOther.getOwner();
	                	security = tileOther.getSecurity();*/
	                    world.setBlockMetadataWithNotify(x, y, z + 1, b0, 3);
	                }

	                world.setBlockMetadataWithNotify(x, y, z, b0, 3);
	            }

	            if ((block2 == this || block3 == this) && (b0 == 2 || b0 == 3))
	            {
	                if (block2 == this)
	                {
	                	/*TGChestTileEnt tileOther = (TGChestTileEnt) world.getTileEntity(x-1, y, z);
	                	owner = tileOther.getOwner();
	                	security = tileOther.getSecurity();*/
	                    world.setBlockMetadataWithNotify(x - 1, y, z, b0, 3);
	                }
	                else
	                {
	                	/*TGChestTileEnt tileOther = (TGChestTileEnt) world.getTileEntity(x+1, y, z);
	                	owner = tileOther.getOwner();
	                	security = tileOther.getSecurity();*/
	                    world.setBlockMetadataWithNotify(x + 1, y, z, b0, 3);
	                }

	                world.setBlockMetadataWithNotify(x, y, z, b0, 3);
	            }
	            
	            /*TGChestTileEnt tile = (TGChestTileEnt) world.getTileEntity(x, y, z);
	            if (owner!=null){
	            	tile.setOwnerSec(owner, security);
	            }*/
	        }

	        if(!world.isRemote){
            	
            	TGChestTileEnt tile = (TGChestTileEnt) world.getTileEntity(x, y, z);
        		
        		/*if (elb instanceof EntityPlayer && tile instanceof TGChestTileEnt){
        			((TGChestTileEnt)tile).setOwner((EntityPlayer)elb);
        		}*/
            	
    			NBTTagCompound data= item.getTagCompound();
    			if (data!=null){
    				if(data.hasKey("TileEntityData")){
    					tile.readEntityDataFromNBT(data.getCompoundTag("TileEntityData"));
    					//System.out.println("SET TILEENTITY DATA FROM NBT");
    					//tile.rotation=(byte) ((dir+2)%4);
    				}
    			}			
    		}
	        
	        
	        
	       /* if (item.hasDisplayName())
	        {
	            ((TGChestTileEnt)world.getTileEntity(x, y, z)).func_145976_a(item.getDisplayName());
	        }*/
	    }

	    public void func_149954_e(World world, int x, int y, int z)
	    {
	        if (!world.isRemote)
	        {
	            Block block = world.getBlock(x, y, z - 1);
	            Block block1 = world.getBlock(x, y, z + 1);
	            Block block2 = world.getBlock(x - 1, y, z);
	            Block block3 = world.getBlock(x + 1, y, z);
	            boolean flag = true;
	            int l;
	            Block block4;
	            int i1;
	            Block block5;
	            boolean flag1;
	            byte b0;
	            int j1;

	            if (block != this && block1 != this)
	            {
	                if (block2 != this && block3 != this)
	                {
	                    b0 = 3;

	                    if (block.func_149730_j() && !block1.func_149730_j())
	                    {
	                        b0 = 3;
	                    }

	                    if (block1.func_149730_j() && !block.func_149730_j())
	                    {
	                        b0 = 2;
	                    }

	                    if (block2.func_149730_j() && !block3.func_149730_j())
	                    {
	                        b0 = 5;
	                    }

	                    if (block3.func_149730_j() && !block2.func_149730_j())
	                    {
	                        b0 = 4;
	                    }
	                }
	                else
	                {
	                    l = block2 == this ? x - 1 : x + 1;
	                    block4 = world.getBlock(l, y, z - 1);
	                    i1 = block2 == this ? x - 1 : x + 1;
	                    block5 = world.getBlock(i1, y, z + 1);
	                    b0 = 3;
	                    flag1 = true;

	                    if (block2 == this)
	                    {
	                        j1 = world.getBlockMetadata(x - 1, y, z);
	                    }
	                    else
	                    {
	                        j1 = world.getBlockMetadata(x + 1, y, z);
	                    }

	                    if (j1 == 2)
	                    {
	                        b0 = 2;
	                    }

	                    if ((block.func_149730_j() || block4.func_149730_j()) && !block1.func_149730_j() && !block5.func_149730_j())
	                    {
	                        b0 = 3;
	                    }

	                    if ((block1.func_149730_j() || block5.func_149730_j()) && !block.func_149730_j() && !block4.func_149730_j())
	                    {
	                        b0 = 2;
	                    }
	                }
	            }
	            else
	            {
	                l = block == this ? z - 1 : z + 1;
	                block4 = world.getBlock(x - 1, y, l);
	                i1 = block == this ? z - 1 : z + 1;
	                block5 = world.getBlock(x + 1, y, i1);
	                b0 = 5;
	                flag1 = true;

	                if (block == this)
	                {
	                    j1 = world.getBlockMetadata(x, y, z - 1);
	                }
	                else
	                {
	                    j1 = world.getBlockMetadata(x, y, z + 1);
	                }

	                if (j1 == 4)
	                {
	                    b0 = 4;
	                }

	                if ((block2.func_149730_j() || block4.func_149730_j()) && !block3.func_149730_j() && !block5.func_149730_j())
	                {
	                    b0 = 5;
	                }

	                if ((block3.func_149730_j() || block5.func_149730_j()) && !block2.func_149730_j() && !block4.func_149730_j())
	                {
	                    b0 = 4;
	                }
	            }

	            world.setBlockMetadataWithNotify(x, y, z, b0, 3);
	        }
	    }

	    /**
	     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	     */
	    public boolean canPlaceBlockAt(World world, int x, int y, int z)
	    {
	        int l = 0;

	        if (world.getBlock(x - 1, y, z) == this)
	        {
	            ++l;
	        }

	        if (world.getBlock(x + 1, y, z) == this)
	        {
	            ++l;
	        }

	        if (world.getBlock(x, y, z - 1) == this)
	        {
	            ++l;
	        }

	        if (world.getBlock(x, y, z + 1) == this)
	        {
	            ++l;
	        }

	        return l > 1 ? false : (this.func_149952_n(world, x - 1, y, z) ? false : (this.func_149952_n(world, x + 1, y, z) ? false : (this.func_149952_n(world, x, y, z - 1) ? false : !this.func_149952_n(world, x, y, z + 1))));
	    }

	    private boolean func_149952_n(World p_149952_1_, int p_149952_2_, int p_149952_3_, int p_149952_4_)
	    {
	        return p_149952_1_.getBlock(p_149952_2_, p_149952_3_, p_149952_4_) != this ? false : (p_149952_1_.getBlock(p_149952_2_ - 1, p_149952_3_, p_149952_4_) == this ? true : (p_149952_1_.getBlock(p_149952_2_ + 1, p_149952_3_, p_149952_4_) == this ? true : (p_149952_1_.getBlock(p_149952_2_, p_149952_3_, p_149952_4_ - 1) == this ? true : p_149952_1_.getBlock(p_149952_2_, p_149952_3_, p_149952_4_ + 1) == this)));
	    }

	    /**
	     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	     * their own) Args: x, y, z, neighbor Block
	     */
	    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
	    {
	        super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
	        TGChestTileEnt tileentitychest = (TGChestTileEnt)p_149695_1_.getTileEntity(p_149695_2_, p_149695_3_, p_149695_4_);

	        if (tileentitychest != null)
	        {
	            tileentitychest.updateContainingBlockInfo();
	        }
	    }

	    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
	    {
	        TGChestTileEnt tileentitychest = (TGChestTileEnt)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

	        if (tileentitychest != null)
	        {
	            for (int i1 = 0; i1 < tileentitychest.getSizeInventory(); ++i1)
	            {
	                ItemStack itemstack = tileentitychest.getStackInSlot(i1);

	                if (itemstack != null)
	                {
	                    float f = this.rnd.nextFloat() * 0.8F + 0.1F;
	                    float f1 = this.rnd.nextFloat() * 0.8F + 0.1F;
	                    EntityItem entityitem;

	                    for (float f2 = this.rnd.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; p_149749_1_.spawnEntityInWorld(entityitem))
	                    {
	                        int j1 = this.rnd.nextInt(21) + 10;

	                        if (j1 > itemstack.stackSize)
	                        {
	                            j1 = itemstack.stackSize;
	                        }

	                        itemstack.stackSize -= j1;
	                        entityitem = new EntityItem(p_149749_1_, (double)((float)p_149749_2_ + f), (double)((float)p_149749_3_ + f1), (double)((float)p_149749_4_ + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
	                        float f3 = 0.05F;
	                        entityitem.motionX = (double)((float)this.rnd.nextGaussian() * f3);
	                        entityitem.motionY = (double)((float)this.rnd.nextGaussian() * f3 + 0.2F);
	                        entityitem.motionZ = (double)((float)this.rnd.nextGaussian() * f3);

	                        if (itemstack.hasTagCompound())
	                        {
	                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
	                        }
	                    }
	                }
	            }

	            p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
	        }

	        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
	    }

	    /**
	     * Called upon block activation (right click on the block.)
	     */
	    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer ply, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	    {
	       /* if (world.isRemote)
	        {
	            return true;
	        }
	        else
	        {
	            IInventory iinventory = this.func_149951_m(world, x, y, z);

	            if (iinventory != null)
	            {
	                ply.displayGUIChest(iinventory);
	            }

	            return true;
	        }*/
	    	
	    	TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof TGChestTileEnt){
				TGChestTileEnt chestile = (TGChestTileEnt) tile;
				
				//System.out.println("CheckUsable");
				if (chestile.isUseableByPlayer(ply)){
					
					if (ply.isSneaking() && (ply.getHeldItem()!=null && ply.getHeldItem().getItem().getToolClasses(ply.getHeldItem()).contains("wrench"))){
						
						
						if (!world.isRemote){
							NBTTagCompound tileEntTags =new NBTTagCompound();
							chestile.writeEntityDataToNBTForItemStack(tileEntTags);
							
							ItemStack item = new ItemStack(Item.getItemFromBlock(this),1,0);
							
							if (!tileEntTags.hasNoTags()){
								NBTTagCompound itemnbt = item.getTagCompound();
								if (itemnbt==null){
									itemnbt=new NBTTagCompound();
									item.setTagCompound(itemnbt);
								}
								itemnbt.setTag("TileEntityData", tileEntTags);
							}
							chestile.emptyContent();
							world.setBlockToAir(x, y, z);
							world.spawnEntityInWorld(new EntityItem(world,x, y,z, item));
						}
						
						return false;
						
						
					} else {
	
						ply.openGui(Techguns.MODID, GUI_ID_TGCHEST, world, x, y, z);
						return true;
					}
				} else {
					if(ply.worldObj.isRemote){
						ply.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.container.security.denied")));
					}
					return true;
				}
			} else {
				return false;
			}
	    }

	    public IInventory func_149951_m(World world, int x, int y, int z)
	    {
	        Object object = (TGChestTileEnt)world.getTileEntity(x, y, z);

	        if (object == null)
	        {
	            return null;
	        }
	        else if (world.isSideSolid(x, y + 1, z, DOWN))
	        {
	            return null;
	        }
	        else if (func_149953_o(world, x, y, z))
	        {
	            return null;
	        }
	        else if (world.getBlock(x - 1, y, z) == this && (world.isSideSolid(x - 1, y + 1, z, DOWN) || func_149953_o(world, x - 1, y, z)))
	        {
	            return null;
	        }
	        else if (world.getBlock(x + 1, y, z) == this && (world.isSideSolid(x + 1, y + 1, z, DOWN) || func_149953_o(world, x + 1, y, z)))
	        {
	            return null;
	        }
	        else if (world.getBlock(x, y, z - 1) == this && (world.isSideSolid(x, y + 1, z - 1, DOWN) || func_149953_o(world, x, y, z - 1)))
	        {
	            return null;
	        }
	        else if (world.getBlock(x, y, z + 1) == this && (world.isSideSolid(x, y + 1, z + 1, DOWN) || func_149953_o(world, x, y, z + 1)))
	        {
	            return null;
	        }
	        else
	        {
	        	int chestType = ((TGChestTileEnt)world.getTileEntity(x, y, z)).getCachedChestType();
	        	
	            if (world.getBlock(x - 1, y, z) == this)
	            {
	                object = new InventoryLargeChest(doubleChestName[chestType], (TGChestTileEnt)world.getTileEntity(x - 1, y, z), (IInventory)object);
	            }

	            if (world.getBlock(x + 1, y, z) == this)
	            {
	                object = new InventoryLargeChest(doubleChestName[chestType], (IInventory)object, (TGChestTileEnt)world.getTileEntity(x + 1, y, z));
	            }

	            if (world.getBlock(x, y, z - 1) == this)
	            {
	                object = new InventoryLargeChest(doubleChestName[chestType], (TGChestTileEnt)world.getTileEntity(x, y, z - 1), (IInventory)object);
	            }

	            if (world.getBlock(x, y, z + 1) == this)
	            {
	                object = new InventoryLargeChest(doubleChestName[chestType], (IInventory)object, (TGChestTileEnt)world.getTileEntity(x, y, z + 1));
	            }

	            return (IInventory)object;
	        }
	    }

	    /**
	     * Returns a new instance of a block's tile entity class. Called on placing the block.
	     */
	    public TileEntity createNewTileEntity(World world, int meta)
	    {
	    	TGChestTileEnt tileentitychest = new TGChestTileEnt();
	        return tileentitychest;
	    }

	    /**
	     * Can this block provide power. Only wire currently seems to have this change based on its state.
	     */
	    public boolean canProvidePower()
	    {
	        return this.field1 == 1;
	    }

	    public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_)
	    {
	        if (!this.canProvidePower())
	        {
	            return 0;
	        }
	        else
	        {
	            int i1 = ((TGChestTileEnt)p_149709_1_.getTileEntity(p_149709_2_, p_149709_3_, p_149709_4_)).numPlayersUsing;
	            return MathHelper.clamp_int(i1, 0, 15);
	        }
	    }

	    public int isProvidingStrongPower(IBlockAccess p_149748_1_, int p_149748_2_, int p_149748_3_, int p_149748_4_, int p_149748_5_)
	    {
	        return p_149748_5_ == 1 ? this.isProvidingWeakPower(p_149748_1_, p_149748_2_, p_149748_3_, p_149748_4_, p_149748_5_) : 0;
	    }

	    private static boolean func_149953_o(World p_149953_0_, int p_149953_1_, int p_149953_2_, int p_149953_3_)
	    {
	        Iterator iterator = p_149953_0_.getEntitiesWithinAABB(EntityOcelot.class, AxisAlignedBB.getBoundingBox((double)p_149953_1_, (double)(p_149953_2_ + 1), (double)p_149953_3_, (double)(p_149953_1_ + 1), (double)(p_149953_2_ + 2), (double)(p_149953_3_ + 1))).iterator();
	        EntityOcelot entityocelot;

	        do
	        {
	            if (!iterator.hasNext())
	            {
	                return false;
	            }

	            Entity entity = (Entity)iterator.next();
	            entityocelot = (EntityOcelot)entity;
	        }
	        while (!entityocelot.isSitting());

	        return true;
	    }

	    /**
	     * If this returns true, then comparators facing away from this block will use the value from
	     * getComparatorInputOverride instead of the actual redstone signal strength.
	     */
	    public boolean hasComparatorInputOverride()
	    {
	        return true;
	    }

	    /**
	     * If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal
	     * strength when this block inputs to a comparator.
	     */
	    public int getComparatorInputOverride(World world, int x, int y, int z, int m)
	    {
	        return Container.calcRedstoneFromInventory(this.func_149951_m(world, x, y, z));
	    }

	    @SideOnly(Side.CLIENT)
	    public void registerBlockIcons(IIconRegister p_149651_1_)
	    {
	        this.blockIcon = p_149651_1_.registerIcon("planks_oak");
	    }
}
