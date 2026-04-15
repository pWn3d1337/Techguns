package techguns.tileentities;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import techguns.Techguns;
import techguns.items.armors.GenericArmor;

public class RepairBenchTileEnt extends BasicInventoryTileEnt {

	public RepairBenchTileEnt() {
		super(9);

	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[]{0,1,2,3,4,5,6,7,8};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return true;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getInventoryName() {
		return "techguns.container.repairbench";
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return true;
	}

	@Override
	public void buttonClicked(int id, EntityPlayer ply) {
		if(id>=1 && id<=4 && this.isUseableByPlayer(ply)){
			int slot=3-(id-1);
			ItemStack item = ply.inventory.armorInventory[slot];
			if(item!=null && item.getItem() instanceof GenericArmor){
				GenericArmor armor = (GenericArmor) item.getItem();
				
				
				List<ItemStack> mats = armor.getRepairMats(item);
				if(mats.size()>0){
				
					boolean canConsume=true;
					for(int i=0; i< mats.size(); i++){
						if (Techguns.canConsumeItem(content, mats.get(i), 0, content.length)>0){
							canConsume=false;
							break;
						};
					}
					
					if(canConsume){
						for(int i=0; i< mats.size(); i++){
							Techguns.consumeItem(content, mats.get(i), 0, content.length);
						}
						item.setItemDamage(0);
						//this.needUpdate();
					}
				
				}
			}
			
			
			
		}
	}

	
}
