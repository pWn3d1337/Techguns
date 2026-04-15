package techguns.inventory;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class ChargingStationRecipe {
	private static ArrayList<ChargingStationRecipe> recipes = new ArrayList<>();
	
	//members
	public ItemStackOreDict input;
	public ItemStack output;
	public int chargeAmount;
	
	public static ArrayList<ChargingStationRecipe> getRecipes() {
		return recipes;
	}

	private ChargingStationRecipe(ItemStackOreDict input, ItemStack output, int chargeAmount) {
		super();
		this.input = input;
		this.output = output;
		this.chargeAmount = chargeAmount;
	}

	public static ChargingStationRecipe addRecipe(ItemStackOreDict input, ItemStack output, int chargeAmount){
		ChargingStationRecipe rec = new ChargingStationRecipe(input, output, chargeAmount);
		recipes.add(rec);
		return rec;
	}
	
	public static ChargingStationRecipe getRecipeFor(ItemStack input){
		for(int i=0;i<recipes.size();i++){
			ChargingStationRecipe rec = recipes.get(i);
			if(rec.input.isEqualWithOreDict(input)){
				return rec;
			}
		}
		return null;
	}
}
