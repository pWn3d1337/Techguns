package techguns.inventory;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import techguns.util.ItemUtil;

public class ItemStackOreDict {
	public String oreDictName;
	public int stackSize;
	public ItemStack item;
	//strict mode, check for damage value
	public boolean strict = false;
	
	public ItemStackOreDict(String oreDictName, int stackSize) {
		this.oreDictName = oreDictName;
		this.stackSize = stackSize;
		this.item=null;
	}
	public ItemStackOreDict(String oreDictName) {
		this.oreDictName = oreDictName;
		this.stackSize = 1;
		this.item=null;
	}
	
	public ItemStackOreDict(ItemStack item, int stackSize) {
		this.stackSize = stackSize;
		this.item = item;
		this.oreDictName=null;
	}
	public ItemStackOreDict(ItemStack item) {
		if (item !=null){
			this.stackSize = item.stackSize;
			this.item = item;
			this.oreDictName=null;
		} else {
			this.stackSize=0;
			this.item=null;
			this.oreDictName=null;
		}
	}

	public ItemStackOreDict setNoStrictMode(){
		this.strict=false;
		//this.item.setItemDamage(OreDictionary.WILDCARD_VALUE);
		return this;
	}
	
	public ItemStackOreDict setStrictMode(){
		this.strict=true;
		//this.item.setItemDamage(OreDictionary.WILDCARD_VALUE);
		return this;
	}
	
	public boolean isNullStack(){
		return this.oreDictName==null && this.item==null;
	}
	
	public boolean isEqualWithOreDict(ItemStack other){
		if (this.oreDictName==null){
			return ItemUtil.isItemEqual(this.item, other, strict); //OreDictionary.itemMatches(this.item, other,strict); //ItemUtil.isItemEqual(this.item, other);
		} else {
			ArrayList<ItemStack> items = this.getItemStacks();
			
			for (int i=0; i<items.size();i++){
				//System.out.println("Check"+items.get(i)+ " with "+other+"strict:"+this.strict);
				if (ItemUtil.isItemEqual(items.get(i), other, strict)){
					//System.out.println("matches");
					return true;
				}
			}
			//System.out.println("mismatch");
			return false;
		}
	}

	public ArrayList<ItemStack> getItemStacks() {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		
		if(this.oreDictName==null){
			if(this.item!=null){
				list.add(new ItemStack(this.item.getItem(),this.stackSize,this.item.getItemDamage()));
			}
		} else {
			list.addAll(OreDictionary.getOres(oreDictName));
			ItemUtil.setStackSizes(list, this.stackSize);
		}
		
		return list;
		
	} 	
	
	public ArrayList<ItemStack> getItemStacks(int amountOverride) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		
		if(this.oreDictName==null){
			if(this.item!=null){
				list.add(new ItemStack(this.item.getItem(),amountOverride,this.item.getItemDamage()));
			}
		} else {
			ArrayList<ItemStack> oreDictItems = OreDictionary.getOres(oreDictName);
			for (int i=0; i< oreDictItems.size();i++){
				list.add(new ItemStack(oreDictItems.get(i).getItem(), amountOverride, oreDictItems.get(i).getItemDamage()));
			}	
		}
		
		return list;
		
	}
	
	@Override
	public String toString() {
		if (this.oreDictName!=null){
			return "ItemStackOreDict:"+this.oreDictName;
		} else if (this.item!=null){
			return this.item.toString();
		} else {
			return "EMPTY_ItemStackOreDict";
		}
	} 	
	/**
	 * Return if two itemStack ore dict match eachother
	 * @param other
	 * @return
	 */
	public boolean matches (ItemStackOreDict other){
		if (this.oreDictName!=null && other.oreDictName!=null){
			return this.oreDictName.equals(other.oreDictName);
		} else if (this.oreDictName!=null){
			return this.isEqualWithOreDict(other.item);
		} else if (other.oreDictName!=null){
			return other.isEqualWithOreDict(this.item);
		} else {
			return OreDictionary.itemMatches(this.item, other.item, false);
		}
	}
}
