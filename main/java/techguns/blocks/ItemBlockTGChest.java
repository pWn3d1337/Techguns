package techguns.blocks;

import java.util.List;

import techguns.util.TextUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemBlockTGChest extends ItemBlock {

	public ItemBlockTGChest(Block b) {
		super(b);
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer player,
			List list, boolean b) {
		super.addInformation(item, player, list, b);
		NBTTagCompound tags=item.getTagCompound();
		if(tags!=null && tags.hasKey("TileEntityData")){
			list.add(TextUtil.trans("techguns.block.chestHasContents"));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "techguns."+ super.getUnlocalizedName(stack);
	}
	
}
