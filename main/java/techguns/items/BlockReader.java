package techguns.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import techguns.blocks.BlockTGDoor;

public class BlockReader extends GenericItem {
	
	public BlockReader() {
		setMaxStackSize(1);
	}

	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if (world.isRemote) player.addChatMessage(new ChatComponentText("Block:"+Block.getIdFromBlock(block)+" - Meta: "+meta+" - Side: "+side));
			
		if (block instanceof BlockTGDoor) {
			int i1 = ((BlockTGDoor)block).func_150012_g(world, x, y, z);
	        int j1 = i1 & 3;
	        boolean flag = (i1 & 4) != 0;
	        if (world.isRemote) player.addChatMessage(new ChatComponentText("Door: i1 = "+i1+" - j1 = "+j1+ " - flag = "+flag));
		}
		
		if(world.isRemote){
			player.addChatMessage(new ChatComponentText("Blockbounds: X:"+block.getBlockBoundsMinX()+","+block.getBlockBoundsMaxX()+"|Y:"+
					block.getBlockBoundsMinY()+","+block.getBlockBoundsMaxY()+"|Z:"+block.getBlockBoundsMinZ()+","+block.getBlockBoundsMaxZ()));
		} else {
			System.out.println("Server Bounds:"+"Blockbounds: X:"+block.getBlockBoundsMinX()+","+block.getBlockBoundsMaxX()+"|Y:"+
					block.getBlockBoundsMinY()+","+block.getBlockBoundsMaxY()+"|Z:"+block.getBlockBoundsMinZ()+","+block.getBlockBoundsMaxZ());
		}
		
		return true;
    }
}
