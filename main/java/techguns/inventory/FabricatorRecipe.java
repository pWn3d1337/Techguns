package techguns.inventory;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import techguns.TGItems;

public class FabricatorRecipe {

	public static ArrayList<ItemStackOreDict> items_wireslot = new ArrayList<>(4);
	
	public static ArrayList<ItemStackOreDict> items_powderslot = new ArrayList<>(2);
	
	public static ArrayList<ItemStackOreDict> items_plateslot = new ArrayList<>(4);
	
	
	public static ItemStackOreDict copperWires = new ItemStackOreDict(TGItems.copperWire,1);
	public static ItemStackOreDict goldWires = new ItemStackOreDict(TGItems.goldWire,1);
	
	public static ItemStackOreDict redstone = new ItemStackOreDict("dustRedstone",1);
	
	public static ItemStackOreDict plastic = new ItemStackOreDict("sheetPlastic",1);
	
	public static ItemStackOreDict circuit_basic = new ItemStackOreDict("circuitBasic",1);
	public static ItemStackOreDict circuit_elite = new ItemStackOreDict("circuitElite",1);
	
	public static ItemStackOreDict mechanicalPartsT3 = new ItemStackOreDict(TGItems.mechanicalPartsT3,1);
	
	public static ItemStackOreDict carbonPlate = new ItemStackOreDict("plateCarbon",1);
	public static ItemStackOreDict titaniumPlate = new ItemStackOreDict("plateTitanium",1);
	public static ItemStackOreDict leadPlate = new ItemStackOreDict("plateLead",1);
	
	static{
		items_wireslot.add(copperWires);
		items_wireslot.add(goldWires);
		items_wireslot.add(circuit_basic);
		items_wireslot.add(circuit_elite);
		
		items_powderslot.add(redstone);
		items_powderslot.add(mechanicalPartsT3);
		
		items_plateslot.add(plastic);
		items_plateslot.add(carbonPlate);
		items_plateslot.add(titaniumPlate);
		items_plateslot.add(leadPlate);
	}

	
	public static ArrayList<FabricatorRecipe> recipes = new ArrayList<FabricatorRecipe>();
	
	/**
	 * NON-STATIC VARIABLES
	 */
	
	public ItemStackOreDict inputItem;
	public byte amountInput;
	
	public ItemStackOreDict wireSlot;
	public byte amountWire;
	
	public ItemStackOreDict powderSlot;
	public byte amountPowder;
	
	public ItemStackOreDict plateSlot;
	public byte amountPlates;

	public ItemStack outputItem;
	public byte amountOutput;
	
	
	public static void addRecipe(ItemStackOreDict inputItem, int amountInput, ItemStackOreDict wireSlot, int amountWire, ItemStackOreDict powderSlot,
			int amountPowder, ItemStackOreDict plateSlot, int amountPlates, ItemStack outputItem, int amountOutput){
		recipes.add(new FabricatorRecipe(inputItem, amountInput, wireSlot, amountWire, powderSlot, amountPowder, plateSlot, amountPlates, outputItem, amountOutput));
	}
	
	
	public boolean usesItem(ItemStack itm){
		return this.inputItem.isEqualWithOreDict(itm) || this.wireSlot.isEqualWithOreDict(itm) || this.powderSlot.isEqualWithOreDict(itm) || this.plateSlot.isEqualWithOreDict(itm);
	}
	
	
	public FabricatorRecipe(ItemStackOreDict inputItem, int amountInput, ItemStackOreDict wireSlot, int amountWire, ItemStackOreDict powderSlot,
			int amountPowder, ItemStackOreDict plateSlot, int amountPlates, ItemStack outputItem, int amountOutput) {
		this.inputItem = inputItem;
		this.amountInput = (byte) amountInput;
		this.wireSlot = wireSlot;
		this.amountWire = (byte) amountWire;
		this.powderSlot = powderSlot;
		this.amountPowder = (byte) amountPowder;
		this.plateSlot = plateSlot;
		this.amountPlates = (byte) amountPlates;
		this.outputItem = outputItem;
		this.amountOutput = (byte) amountOutput;
	}
	
	private boolean matches(ItemStack input, ItemStack wires, ItemStack powder, ItemStack plate){
		
		if (this.inputItem.isEqualWithOreDict(input) && input.stackSize>=this.amountInput){
			if (this.wireSlot.isNullStack() || (wires!=null && this.wireSlot.isEqualWithOreDict(wires) && wires.stackSize>=this.amountWire)){
				if (this.powderSlot.isNullStack() || (powder!=null && this.powderSlot.isEqualWithOreDict(powder) && powder.stackSize>=this.amountPowder)){
					if (this.plateSlot.isNullStack() || (plate!=null && this.plateSlot.isEqualWithOreDict(plate) && plate.stackSize>=this.amountPlates)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	public static RecipeData getRecipeDataFor(ItemStack input, ItemStack wires, ItemStack powder, ItemStack plate, int maxMultiplier, ItemStack outputSlot){
		FabricatorRecipe match=null;	
		
		for (int i=0; i<recipes.size();i++){
			FabricatorRecipe rec = recipes.get(i);
			
			if(rec.matches(input, wires, powder, plate)){
				match=rec;
				break;
			}
		}
		
		if (match!=null && canMerge(outputSlot, match.outputItem, match.amountOutput)){
			
			//calculate multiplier
			int maxMultiInput = getMaxMulti(input.stackSize, match.amountInput, maxMultiplier);
			int maxMultiWires=0;
			if (wires!=null){
				maxMultiWires = getMaxMulti(wires.stackSize, match.amountWire, maxMultiplier);
			} else {
				maxMultiWires = 8;
			}
			int maxMultiPowder;
			if (powder!=null){
				maxMultiPowder = getMaxMulti(powder.stackSize, match.amountPowder, maxMultiplier);
			} else {
				maxMultiPowder = 8;
			}
			int maxMultiPlate = getMaxMulti(plate.stackSize, match.amountPlates, maxMultiplier);
			int maxMultiOutput = getMaxMultiOutput(outputSlot==null?0:outputSlot.stackSize, match.amountOutput, maxMultiplier, match.outputItem.getMaxStackSize());
			
			int multi = getMinArgument(maxMultiInput, maxMultiWires, maxMultiPowder, maxMultiPlate, maxMultiOutput);
			
			RecipeData data = new RecipeData(match.outputItem, match.amountOutput, match.amountInput, match.amountWire, match.amountPowder, match.amountPlates, multi);
			return data;
		}
		
		
		return null;
	}
	
	private static boolean canMerge(ItemStack content, ItemStack newStack, int newAmount){
		if (content==null){
			return true;
		}
		if(content.getItem()==newStack.getItem() && content.getItemDamage()== newStack.getItemDamage()){
			return content.stackSize+newAmount <= content.getMaxStackSize();
		}
		return false;
	}
	
	
	private static int getMaxMulti(int stackSize, int consumption, int maxMulti){
		int mult = maxMulti;
		
		while(mult*consumption > stackSize){
			mult--;
		}
		return mult;
	}
	
	private static int getMaxMultiOutput(int stackSize, int amount, int maxMulti, int maxStackSize){
		int mult = maxMulti;
		
		while((mult*amount)+stackSize > maxStackSize){
			mult--;
		}
		return mult;
	}
	
	private static int getMinArgument(int... values){
		int min=Integer.MAX_VALUE;
		for(int i=0;i<values.length;i++){
			int x = values[i];
			if (x<min){
				min=x;
			}
		}
		return min;
	}
	
	
	public static class RecipeData {
		public ItemStack output;
		public byte outputAmount;
		public byte inputConsumption;
		public byte wireConsumption;
		public byte powderConsumption;
		public byte plateConsumption;
		public byte stackMultiplier;
		public RecipeData(ItemStack output, int outputAmount, int inputConsumption, int wireConsumption, int powderConsumption, int plateConsumption, int stackMultiplier) {
			super();
			this.output = output;
			this.outputAmount = (byte) outputAmount;
			this.inputConsumption = (byte) inputConsumption;
			this.wireConsumption = (byte) wireConsumption;
			this.powderConsumption = (byte) powderConsumption;
			this.plateConsumption = (byte) plateConsumption;
			this.stackMultiplier = (byte) stackMultiplier;
		}
		
		
	}
	
	public static ArrayList<FabricatorRecipe> getRecipes() {
		return recipes;
	}
}
