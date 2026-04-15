package techguns.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SlotArmor extends Slot {
	private int armortype;
	private EntityPlayer ply;
	
	public SlotArmor(IInventory inv, int slot, int x,
			int y,int armorType,EntityPlayer ply) {
		super(inv, slot, x, y);
		this.armortype=armorType;
		this.ply=ply;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}



    @Override
    public boolean isItemValid(ItemStack itemstack) {
        return itemstack != null && itemstack.getItem().isValidArmor(itemstack, this.armortype, this.ply);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getBackgroundIconIndex() {
        return ItemArmor.func_94602_b(this.armortype);
    }
	
	
}
