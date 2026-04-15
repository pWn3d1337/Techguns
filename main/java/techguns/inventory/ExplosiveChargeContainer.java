package techguns.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import techguns.tileentities.ExplosiveChargeTileEnt;

public class ExplosiveChargeContainer extends Container {
	protected ExplosiveChargeTileEnt tile;

	
	public ExplosiveChargeContainer(InventoryPlayer player, ExplosiveChargeTileEnt tile){
		this.tile=tile;
	}
	
	
	
	
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotid) {
		return null;
	};
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
	}

	
}
