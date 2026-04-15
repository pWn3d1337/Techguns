package techguns.items;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import techguns.items.guns.GenericGun;

public class RecipeAkimboGun extends ShapelessOreRecipe {

	public RecipeAkimboGun(ItemStack akimboWeapon, Item singleGun) {
		super(akimboWeapon, new Object[]{singleGun, singleGun});
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1) {
		
		Item item0 = ((ItemStack)this.getInput().get(0)).getItem();
		int slot1=-1;
		int slot2=-1;
		
		
		for (int i=0;i<var1.getSizeInventory();i++){
			//System.out.println("Slot "+i+" :"+var1.getStackInSlot(i));
			if (var1.getStackInSlot(i)!=null){
				if (var1.getStackInSlot(i).getItem()==item0){
					//System.out.println("Copy from Slot:"+i);
					
					if (slot1==-1){
						slot1=i;
					} else if (slot2==-1){
						slot2=i;
						break;
					}
				}
			}
		}
		
		ItemStack gunStack1 = ((ItemStack)var1.getStackInSlot(slot1));
		ItemStack gunStack2 = ((ItemStack)var1.getStackInSlot(slot2));
		
		GenericGun gun1 = (GenericGun) gunStack1.getItem(); 
		GenericGun gun2 = (GenericGun) gunStack2.getItem();		
		
		int totalAmmo = gun1.getCurrentAmmo(gunStack1)+gun2.getCurrentAmmo(gunStack2);
		
		NBTTagCompound tags1 = gunStack1.getTagCompound();
		
		NBTTagCompound newTags=null;
		if(tags1!=null){
			newTags = (NBTTagCompound) tags1.copy();
		}
		
		ItemStack out = super.getCraftingResult(var1);
		if(newTags!=null){
			out.setTagCompound(newTags);
			newTags.setShort("ammo", (short) totalAmmo);
		}
		
		return out;
	}
	
	
}
