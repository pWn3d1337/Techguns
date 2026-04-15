package techguns.inventory;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class AmmoPressRecipe {
	public static ArrayList<AmmoPressRecipe> recipes = new ArrayList<AmmoPressRecipe>();
	protected ItemStack output;
	protected ItemStack[] inputs;
		
	public AmmoPressRecipe(ItemStack output, ItemStack[] inputs) {
		this.output = output;
		this.inputs = inputs;
	}
	
	//OreDictNames
	public AmmoPressRecipe(ItemStack output, String[] inputs, int[] count) {
		this.output = output;
		
		this.inputs= new ItemStack[inputs.length];
		
		for(int i=0;i<inputs.length;i++){
			ArrayList<ItemStack> ores = OreDictionary.getOres(inputs[i]);
			if (!ores.isEmpty()){
				this.inputs[i]=ores.get(0);
				this.inputs[i].stackSize=count[i];
			} else {
				System.out.println("No Ore known for "+inputs[i]);
			}
		}
	}

	
	private boolean itemStacksEqualForRecipe(ItemStack i1, ItemStack i2){
		return ((Item.getIdFromItem(i1.getItem()) == Item.getIdFromItem(i1.getItem()))  || OreDictionary.itemMatches(i1, i2,false) ) && (i2.stackSize>=i1.stackSize);
		
	}
	
	protected boolean isInputFor(ItemStack[] content){
		boolean ret =true;
		for (int i=0; i<inputs.length; i++){
			if (!itemStacksEqualForRecipe(inputs[i],content[i])){
				ret=false;
				break;
			}			
		}
		return ret;
	}
	
	public static void addRecipe(ItemStack output, ItemStack[] slots){
		recipes.add(new AmmoPressRecipe(output, slots));
	}
	
	public static void addRecipe(ItemStack output, String[] inputs, int[] count){
		recipes.add(new AmmoPressRecipe(output, inputs, count));
	}
	
	public static ItemStack isRecipe(ItemStack[] contents){
		
		for (int i=0; i<recipes.size(); i++){
			AmmoPressRecipe r = recipes.get(i);
			
			if (r.isInputFor(contents)){
				return r.output;
			}
			
		}
		
		return null;
	}
	
	public static int[] getAmmountsForRecipe(ItemStack[] contents){
		int[] values = new int[contents.length];
		
		for (int i=0; i<recipes.size(); i++){
			AmmoPressRecipe r = recipes.get(i);
			
			if (r.isInputFor(contents)){
				for (i=0;i<contents.length;i++){
					values[i]=r.inputs[i].stackSize;
				}
				return values;
			}
			
		}
		
		return null;
	}

}
