package techguns.tileentities;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ScrapperTileEnt extends BasicMachineTileEntity {

	public ScrapperTileEnt() {
		super(10, 10000);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[]{0,1,2,3,4,5,6,7,8,9};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return slot==0;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot>0 && slot<=9;
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.name : "techguns.container.scrapper";
	}

	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (this.isRedstoneEnabled()){
			if (this.currentOperation!= null) {
				
			} else {
				
				if (this.content[0]!=null){
					
					this.canScrap(content[0]);
					
				}

			}
		}
	}
	
	
	
	protected boolean canScrap(ItemStack stack){
		Item item = stack.getItem();
		if (item.isRepairable()){
			
			if(item instanceof ItemArmor){
				ItemArmor itemArmor = (ItemArmor) item;
				Item repairMat = itemArmor.getArmorMaterial().func_151685_b();
				
				float dmgPercent = (stack.getItemDamage()*1.0f)/(stack.getMaxDamage())*1.0f;
				
				System.out.println("Repair:"+repairMat+" perc:"+dmgPercent);
			} else if (item instanceof ItemTool){
				
				ItemTool tool = (ItemTool) item;
				if(tool.getToolMaterialName()!=null && tool.getToolMaterialName().equals("")){
					ToolMaterial mat = ToolMaterial.valueOf(tool.getToolMaterialName());
					ItemStack repair = mat.getRepairItemStack();
					if(repair!=null){
						System.out.println("Repair:"+repair.getItem()+" "+repair.stackSize);
					}
				}
			} else {
				System.out.println("item:"+item);
			}
			
		}
		
		
		return false;
	}
	
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot!=0){
			return false;
		}
		
		
		
		return false;
	}

}
