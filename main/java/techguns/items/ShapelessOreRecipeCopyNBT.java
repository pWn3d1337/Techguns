package techguns.items;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ShapelessOreRecipeCopyNBT extends ShapelessOreRecipe {

	public ShapelessOreRecipeCopyNBT(Block result, Object[] recipe) {
		super(result, recipe);
	}

	public ShapelessOreRecipeCopyNBT(Item result, Object... recipe) {
		super(result, recipe);
	}

	public ShapelessOreRecipeCopyNBT(ItemStack result, Object... recipe) {
		super(result, recipe);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1) {
		
		Item item0 = ((ItemStack)this.getInput().get(0)).getItem();
		int slot=0;
		
		for (int i=0;i<var1.getSizeInventory();i++){
			//System.out.println("Slot "+i+" :"+var1.getStackInSlot(i));
			if (var1.getStackInSlot(i)!=null){
				if (var1.getStackInSlot(i).getItem()==item0){
					//System.out.println("Copy from Slot:"+i);
					slot=i;
					break;
				}
			}
		}
		NBTTagCompound tags = ((ItemStack)var1.getStackInSlot(slot)).getTagCompound();
		NBTTagCompound newTags=null;
		if(tags!=null){
			newTags = (NBTTagCompound) tags.copy();
		}
		ItemStack out = super.getCraftingResult(var1);
		if(newTags!=null){
			out.setTagCompound(newTags);
		}
		
		return out;
	}


	
	
}
