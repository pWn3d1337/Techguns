package techguns.items;

import techguns.items.guns.GenericGun;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeAkimboSplit extends ShapelessOreRecipe {

	protected Item gunType;
	
	public RecipeAkimboSplit(ItemStack singleGun, Item akimboWeapon) {
		super(singleGun, new Object[]{akimboWeapon});
		this.gunType = singleGun.getItem();
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
		
		ItemStack gunStack = ((ItemStack)var1.getStackInSlot(slot));
		GenericGun gun = (GenericGun) gunStack.getItem(); 
		
		int ammoAkimbo = gun.getCurrentAmmo(gunStack);
		int halfAmmo = ammoAkimbo/2; //Int division
		
		NBTTagCompound tags = ((ItemStack)var1.getStackInSlot(slot)).getTagCompound();
		NBTTagCompound newTags=null;
		//NBTTagCompound newTags2=null;
		if(tags!=null){
			newTags = (NBTTagCompound) tags.copy();
			//newTags2 = (NBTTagCompound) tags.copy();
		}
		ItemStack out = super.getCraftingResult(var1);
		if(newTags!=null){
			out.setTagCompound(newTags);
			
			newTags.setShort("ammo", (short) halfAmmo);
			//newTags2.setInteger("ammo", ammoAkimbo-halfAmmo);
		}
	
		//ItemStack secondGun = new ItemStack(gunType,1);
		//secondGun.setTagCompound(newTags2);
		out.stackSize=1;
		return out;
	}
	
}
