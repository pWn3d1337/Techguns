package techguns.plugins.minetweaker;

import net.minecraft.item.ItemStack;
import techguns.inventory.ItemStackOreDict;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;

public class TGMineTweakerHelper {
	public static String getItemNames(IIngredient ingr){
		boolean first=true;
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(IItemStack st: ingr.getItems()){
			if(!first){
				sb.append(",");
			} else {
				first=false;
			}
			sb.append(st.getName());
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static ItemStackOreDict toItemStackOreDict(IItemStack stack){
		return new ItemStackOreDict(MineTweakerMC.getItemStack(stack));
	}
	
	public static ItemStackOreDict toItemStackOreDict(String oreDictName){
		return new ItemStackOreDict(oreDictName);
	}
	
	public static ItemStackOreDict toItemStackOreDict(Object o){
		//System.out.println("OBJECT:"+o);
		if (o instanceof IItemStack){
			return new ItemStackOreDict(MineTweakerMC.getItemStack((IItemStack)o));
		} else if (o instanceof String){
			return new ItemStackOreDict((String)o);
		}
		return new ItemStackOreDict((ItemStack)null);
	}
}
