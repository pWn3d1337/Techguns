package techguns.util;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * utility class with static item related functions
 *
 */
public class ItemUtil {
	
	/**
	 * Returns if an item exists in OreDictionary with key
	 * @param key
	 * @return
	 */
	public static boolean existsInOredict(String key){
		return OreDictionary.getOres(key).size()>0;
	}
	
	
	/**
	 * like OreDictionary.itemMatches() but null checks
	 * @param item1
	 * @param item2
	 * @return true if both stacks are null or both are !=null AND item is equal AND itemdamage(meta) matches
	 */
	public static boolean isItemEqual(ItemStack item1, ItemStack item2){
		if(item1==null && item2==null){
			return true;
		} else if (item1==null || item2==null){
			return false;
		} else {
		//return ( (item1==null && item2==null) || ( (item1!=null && item2!=null) && (item1.getItem() == item2.getItem()) && (item1.getItemDamage()==item2.getItemDamage()) ) );
			return OreDictionary.itemMatches(item1, item2, true);
		}
	}
	
	/**
	 * like OreDictionary.itemMatches() but null checks, and strict param
	 * @param item1
	 * @param item2
	 * @return true if both stacks are null or both are !=null AND item is equal AND itemdamage(meta) matches
	 */
	public static boolean isItemEqual(ItemStack item1, ItemStack item2, boolean strict){
		if(item1==null && item2==null){
			return true;
		} else if (item1==null || item2==null){
			return false;
		} else {
		//return ( (item1==null && item2==null) || ( (item1!=null && item2!=null) && (item1.getItem() == item2.getItem()) && (item1.getItemDamage()==item2.getItemDamage()) ) );
			return OreDictionary.itemMatches(item1, item2, strict);
		}
	}
	
	/**
	 * Used to check if items are equal AND have.stacksize>=required.stacksize;
	 * @param have
	 * @param required
	 * @return
	 */
	public static boolean isItemEqualAndEnough(ItemStack have, ItemStack required){
		/**
		 * we have nothing and need nothing, so true
		 */
		if ((have==null && required==null)){
			return true;
		}
		
		/**
		 * Check if items are equal
		 */
		if( (have!=null && required!=null) && (have.getItem() == required.getItem()) && (have.getItemDamage()==required.getItemDamage())){
			return have.stackSize>=required.stackSize;
		}
		return false;
	}
	
	public static void setStackSizes(List<ItemStack> stacks, int size){
		for (int i=0; i< stacks.size(); i++){
			stacks.get(i).stackSize=size;
		}
	}

	/**
	 * like isItemEqual for FluidStacks
	 * @param item1
	 * @param item2
	 * @return true if both stacks are null or both are !=null AND Fluids are equal
	 */
	public static boolean isFluidEqual(FluidStack f1, FluidStack f2){
		if(f1==null && f2==null){
			return true;
		} else if (f1==null || f2==null){
			return false;
		} else {
		//return ( (item1==null && item2==null) || ( (item1!=null && item2!=null) && (item1.getItem() == item2.getItem()) && (item1.getItemDamage()==item2.getItemDamage()) ) );
			return f1.equals(f2);
		}
	}
	

	public static void addToolClassesTooltip(HashMap<String,Integer> toolclasses, List tooltip){
		Set<String> ss = toolclasses.keySet();
		if (ss.size()>0) {
			tooltip.add(TextUtil.trans("techguns.tooltip.toolclasses"));
			for (String s: toolclasses.keySet()){
				tooltip.add("  "+s+" : "+toolclasses.get(s));
			}
		}
	}	
	
	public static void addShiftExpandedTooltip(List tooltip){
		tooltip.add(TextUtil.trans("techguns.gun.tooltip.shift1")+" "+EnumChatFormatting.GREEN+TextUtil.trans("techguns.gun.tooltip.shift2")+" "+EnumChatFormatting.GRAY+TextUtil.trans("techguns.gun.tooltip.shift3"));
	}	
}
