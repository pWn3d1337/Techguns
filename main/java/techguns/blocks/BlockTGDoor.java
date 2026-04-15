package techguns.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.Techguns;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTGDoor extends BlockDoor{
	
	@SideOnly(Side.CLIENT)
	public IIcon[] doorIconsUpper;
	public IIcon[] doorIconsLower;
	public IIcon doorIconOther;
	public Item doorItem;

	public BlockTGDoor(Material mat) {
		super(mat);
		setCreativeTab(Techguns.tabTechgun);
		float f = 0.5F;
        float f1 = 1.0F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
        //this.doorItem = doorItem;
        this.textureName = "techguns:door01";
	}

	public void setDoorItem(Item doorItem) {
		this.doorItem = doorItem;
	}
	
	/**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        return this.doorIconsUpper[0];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        if (side != 1 && side != 0)
        {
            int i1 = this.func_150012_g(world, x, y, z);
            int meta = i1 & 3; //(vorher j1) = actual metadata (from lower part)
            boolean open = (i1 & 4) != 0;
            boolean flipped = false;
            boolean upper = (i1 & 8) != 0;
            
            boolean flippedSide = (i1>=16);

//            int meta = world.getBlockMetadata(x, y, z);
//            if ((meta == 1 || meta == 3) && (side == 4 || side == 5)) return this.doorIconOther;
//            else if ((meta == 0 || meta == 2) && (side == 2 || side == 3)) return this.doorIconOther;
            
            
            if (open)
            {
                if (meta == 0 && side == 2)
                {       	
                    flipped = !flipped;
                }
                else if (meta == 1 && side == 5)
                {
                    flipped = !flipped;
                }
                else if (meta == 2 && side == 3)
                {
                    flipped = !flipped;
                }
                else if (meta == 3 && side == 4)
                {
                    flipped = !flipped;
                }
                
                else if ((meta == 1) && (side == 2 || side == 3)) flipped = !flipped;
	            else if ((meta == 2) && (side == 4 || side == 5)) flipped = !flipped;
                
                if ((i1 & 16) != 0) {
             	   if ((meta == 0) && (side == 4 || side == 5)) flipped = !flipped;
    	           else if ((meta == 1) && (side == 2 || side == 3)) flipped = !flipped;
    	           else if ((meta == 2) && (side == 4 || side == 5)) flipped = !flipped;
    	           else if ((meta == 3) && (side == 2 || side == 3)) flipped = !flipped;
                }
            }
            else
            {
                if (meta == 0 && side == 5)
                {
                    flipped = !flipped;
                }
                else if (meta == 1 && side == 3)
                {
                    flipped = !flipped;
                }
                else if (meta == 2 && side == 4)
                {
                    flipped = !flipped;
                }
                else if (meta == 3 && side == 2)
                {
                    flipped = !flipped;
                }

                if ((i1 & 16) != 0) //doubledoor flipped side
                {
                   flipped = !flipped;
                   
                   if ((meta == 0) && (side == 2 || side == 3)) flipped = !flipped;
    	           else if ((meta == 1) && (side == 4 || side == 5)) flipped = !flipped;
    	           else if ((meta == 2) && (side == 2 || side == 3)) flipped = !flipped;
    	           else if ((meta == 3) && (side == 4 || side == 5)) flipped = !flipped;
                }
                
              //thin door sides
	            if ((meta == 2 || meta == 5) && side == 2) flipped = !flipped;
	            else if ((meta == 2 || meta == 5) && side == 3) flipped = !flipped;
	            else if ((meta == 3 || meta == 6) && side == 4) flipped = !flipped;
	            else if ((meta == 3 || meta == 6) && side == 5) flipped = !flipped;
            }
            
          
              
            

            return upper ? this.doorIconsUpper[flipped ? 1 : 0] : this.doorIconsLower[flipped ? 1 : 0];
        }
        else
        {
            return this.doorIconOther;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg)
    {
    	this.doorIconOther = reg.registerIcon(this.getTextureName());
        this.doorIconsUpper = new IIcon[2];
        this.doorIconsLower = new IIcon[2];
        this.doorIconsUpper[0] = reg.registerIcon(this.getTextureName() + "_upper");
        this.doorIconsLower[0] = reg.registerIcon(this.getTextureName() + "_lower");
        this.doorIconsUpper[1] = new IconFlipped(this.doorIconsUpper[0], true, false);
        this.doorIconsLower[1] = new IconFlipped(this.doorIconsLower[0], true, false);
    }
    
    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
//        if (this.blockMaterial == Material.iron)
//        {
//            return false; //Allow items to interact with the door
//        }
//        else
//        {
           
       // }
        this.doorOpen(world, x, y, z, true);
        return true;
    }
    
    private void doorOpen(World world, int x, int y, int z, boolean check){
    	 int i1 = this.func_150012_g(world, x, y, z);
         int j1 = i1 & 7;
         j1 ^= 4;

         if ((i1 & 8) == 0)
         {
             world.setBlockMetadataWithNotify(x, y, z, j1, 2);
             world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
         }
         else
         {
             world.setBlockMetadataWithNotify(x, y - 1, z, j1, 2);
             world.markBlockRangeForRenderUpdate(x, y - 1, z, x, y, z);
         }
         
         if (check){
        	 int meta = world.getBlockMetadata(x, y, z);
        	 if (meta ==8 || meta == 9){
        		 meta = world.getBlockMetadata(x, y-1, z);
        	 }
        	
        	 if (meta==1|| meta==3 || meta ==5 || meta == 7) {
	        	 checkNeighbour(world, x+1, y, z);
	        	 checkNeighbour(world, x-1, y, z);
         	 } else if (meta == 0 || meta==2 || meta==4 || meta == 6){
         		 checkNeighbour(world, x, y, z+1);
	        	 checkNeighbour(world, x, y, z-1);
	         }
         }
         
         
         world.playSound(x, y, z, "techguns:blocks.metaldoorOpen", 1.0f, 1.0f, false);
    }
    
    private void checkNeighbour(World world, int x, int y, int z){
    	Block b = world.getBlock(x, y, z);
	   	 if (b == TGBlocks.door01){
	   		 TGBlocks.door01.doorOpen(world, x, y, z, false);
	   	 }
    }
    
    
    
    @Override
	public Item getItemDropped(int meta, Random rnd, int fortune) {
    	if (meta==8){
    		return null;
    	}
		return this.doorItem;
	}

	/**
     * Gets an item for the block being called on. Args: world, x, y, z
     */
    @SideOnly(Side.CLIENT)
    @Override
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return this.doorItem;
    }
}
