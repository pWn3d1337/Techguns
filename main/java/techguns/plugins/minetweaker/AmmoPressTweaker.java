package techguns.plugins.minetweaker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import techguns.inventory.AmmoPressBuildPlans;

@ZenClass("mods.techguns.AmmoPress")
public class AmmoPressTweaker {

	private AmmoPressTweaker(){};
	
	@ZenMethod
	public static void addMetal1(IIngredient input){
		MineTweakerAPI.apply(new addInputAction(input, (byte)0));
	}
	
	@ZenMethod
	public static void addMetal2(IIngredient input){
		MineTweakerAPI.apply(new addInputAction(input, (byte)1));
	}
	
	@ZenMethod
	public static void addPowder(IIngredient input){
		MineTweakerAPI.apply(new addInputAction(input, (byte)2));
	}
	
	@ZenMethod
	public static void removeMetal1(IIngredient input){
		MineTweakerAPI.apply(new removeInputAction(input, (byte)0));
	}
	
	@ZenMethod
	public static void removeMetal2(IIngredient input){
		MineTweakerAPI.apply(new removeInputAction(input, (byte)1));
	}
	
	@ZenMethod
	public static void removePowder(IIngredient input){
		MineTweakerAPI.apply(new removeInputAction(input, (byte)2));
	}
	

	private static void addToList(IIngredient item, ArrayList<ItemStack> list){
		List<IItemStack> items = item.getItems();
		
		for(int i =0;i<items.size();i++){
			ItemStack it = MineTweakerMC.getItemStack(items.get(i));
			if(it!=null){
				list.add(it);
			}
		}
	}

	private static void removeFromList(IIngredient item, ArrayList<ItemStack> list){
		List<IItemStack> items = item.getItems();
		
		for(int i =0;i<items.size();i++){
			ItemStack it = MineTweakerMC.getItemStack(items.get(i));
			
			Iterator<ItemStack> iter = list.iterator();
			while (iter.hasNext()){
				ItemStack input = iter.next();
				//System.out.println("Checking:"+it.toString()+"->"+input.toString());
				if (OreDictionary.itemMatches(it, input, false)){
					//System.out.println("MATCH!");
					iter.remove();
				}
			}
		}
	}

	private static String getSlotName(byte type){
		switch(type){
			case 1:
				return "Metal2";
			case 2:
				return "Powder";
			case 0:
			default:
				return "Metal1";
		}
	}
	
	private static class addInputAction implements IUndoableAction
	{

		IIngredient input;
		ArrayList<ItemStack> list;
		byte type;
		
		public addInputAction(IIngredient input, byte type) {
			super();
			this.input = input;
			this.type=type;
			switch(type){
				case 1:
					this.list = AmmoPressBuildPlans.metal2;
					break;
				case 2:
					this.list = AmmoPressBuildPlans.powder;
					break;
				case 0:
				default:
					this.list = AmmoPressBuildPlans.metal1;
					break;
			}
		}

		@Override
		public void apply() {
			addToList(input, list);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public String describe() {
			return "Add "+TGMineTweakerHelper.getItemNames(input)+" to AmmoPressSlot: "+getSlotName(type);
		}

		@Override
		public String describeUndo() {
			return "Remove "+TGMineTweakerHelper.getItemNames(input)+" from AmmoPressSlot: "+getSlotName(type);
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public void undo() {
			removeFromList(input, list);
		}
		
	}
	
	private static class removeInputAction implements IUndoableAction
	{

		IIngredient input;
		ArrayList<ItemStack> list;
		byte type;
		
		public removeInputAction(IIngredient input, byte type) {
			super();
			this.input = input;
			this.type=type;
			switch(type){
				case 1:
					this.list = AmmoPressBuildPlans.metal2;
					break;
				case 2:
					this.list = AmmoPressBuildPlans.powder;
					break;
				case 0:
				default:
					this.list = AmmoPressBuildPlans.metal1;
					break;
			}
		}

		@Override
		public void apply() {
			removeFromList(input, list);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public String describe() {
			return "Remove "+TGMineTweakerHelper.getItemNames(input)+" to AmmoPressSlot: "+getSlotName(type);
		}

		@Override
		public String describeUndo() {
			return "Add "+TGMineTweakerHelper.getItemNames(input)+" from AmmoPressSlot: "+getSlotName(type);
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public void undo() {
			addToList(input, list);
		}
		
	}
	
}
