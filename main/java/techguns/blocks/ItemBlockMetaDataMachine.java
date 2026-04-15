package techguns.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import techguns.tileentities.BasicInventoryTileEnt;
import techguns.tileentities.ExplosiveChargeTileEnt;
import techguns.util.TextUtil;

public class ItemBlockMetaDataMachine extends GenericItemBlockMetadata {

	public ItemBlockMetaDataMachine(Block block) {
		super(block);
	}

	

	@Override
	public void addInformation(ItemStack item, EntityPlayer player,
			List list, boolean b) {
		super.addInformation(item, player, list, b);
		NBTTagCompound tags=item.getTagCompound();
		if(tags!=null && tags.hasKey("TileEntityData")){
			list.add(TextUtil.trans("techguns.block.hasTileEntityData"));
		}
	}


	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ,
			int metadata) {
		if(super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata)){
			
			if (metadata==6||metadata==8){
				
				//System.out.println("Side:"+side);
				TileEntity tile = world.getTileEntity(x, y, z);
				if(tile instanceof ExplosiveChargeTileEnt){
					ExplosiveChargeTileEnt charge = (ExplosiveChargeTileEnt) tile;
					charge.orientation=(byte) side;
					
					if (!world.isRemote){
						world.playSoundEffect(x+0.5D,y+0.5D, z+0.5D, charge.getSoundPlant(), 4.0F, 1.0f/*(1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F*/);
						
					}
					
				}
			} else {
				TileEntity tile = world.getTileEntity(x, y, z);
				
				if (metadata == 0 || metadata==1 || metadata==4 || metadata==9){
					if (tile instanceof BasicInventoryTileEnt){
						int facing = MathHelper.floor_double((player.rotationYaw*4.0D/360.0D)+0.5D) & 3;
						//System.out.println("Facing:"+facing);
						((BasicInventoryTileEnt) tile).rotation = (byte) ((facing+2)%4);
					}
				} else if (metadata==3 || metadata==5){
					if (tile instanceof BasicInventoryTileEnt){
						int facing = MathHelper.floor_double((player.rotationYaw*4.0D/360.0D)+0.5D) & 3;
						
						//just don't ask why...
						if (facing==0){
							facing =2;
						} else if (facing==2){
							facing =0;
						}
						((BasicInventoryTileEnt) tile).rotation = (byte) ((facing-2)%4);
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "techguns."+ super.getUnlocalizedName(stack);
	}
	
}
