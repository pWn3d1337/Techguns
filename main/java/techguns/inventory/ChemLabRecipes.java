package techguns.inventory;

import java.util.ArrayList;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import techguns.util.ItemUtil;

public class ChemLabRecipes {
	/*public static final byte CHEM_LAB_NO_FLUID=0;
	public static final byte CHEM_LAB_FLUID_WATER =1;
	public static final byte CHEM_LAB_FLUID_LAVA =2;
	public static final byte CHEM_LAB_FLUID_OIL =3;
	public static final byte CHEM_LAB_FLUID_FUEL =4;*/
	
	private static ArrayList<ChemLabRecipe> recipes = new ArrayList<ChemLabRecipe>();
	
	public static ChemLabOutput getOutputFor(ItemStack slot1, ItemStack slot2, ItemStack slot3, FluidStack fluidIn){
		for (int i=0; i < recipes.size(); i++){
			ChemLabRecipe recipe = recipes.get(i);
			if ( recipe.isValidInput(slot1, slot2,slot3,fluidIn)) {
				return new ChemLabOutput(recipe.output,recipe.fluidOutput,recipe.amounts[0],recipe.amounts[1],recipe.amounts[2],recipe.amounts[3],recipe.powerPerTick);
			}
		}
		return null;		
	}
	
	public static ArrayList<ChemLabRecipe> getRecipes() {
		return recipes;
	}
	
	public static int getTotalPower(int recipeIndex){
		return recipes.get(recipeIndex).powerPerTick*100;
	}
	
	public static ChemLabRecipe getRecipe(int index){
		return recipes.get(index);
	}
	
	/*public static Fluid getFluidType(byte type){
		switch(type){
			case CHEM_LAB_FLUID_WATER:
				return FluidRegistry.WATER;
			case CHEM_LAB_FLUID_LAVA:
				return FluidRegistry.LAVA;
			case CHEM_LAB_FLUID_OIL:
				return TGFluids.OIL;
			case CHEM_LAB_FLUID_FUEL:
				return TGFluids.FUEL;
			default:
				return null;
		}
	}*/
	
	public static ArrayList<ChemLabRecipe> getRecipesUsing(ItemStack input){
		ArrayList<ChemLabRecipe> ret = new ArrayList<ChemLabRecipe>();
		
		for (ChemLabRecipe r : recipes){
			
			if (r.isItemPartOfRecipe(input)){
				ret.add(r);
			}
			
		}
		
		return ret;
	}
	
	public static ChemLabRecipe getRecipesFor(ItemStack output){
		for (ChemLabRecipe r: recipes){
			if (ItemUtil.isItemEqual(r.output,output)){
				return r;
			}
		}
		return null;
	}
	
	public static boolean allowInFlaskSlot(ItemStack item){
		return item!=null && item.getItem() == Items.glass_bottle;
	}
	
	public static boolean hasRecipeUsing(ItemStack item){
		for(int i=0;i<recipes.size();i++){
			if(recipes.get(i).isItemPartOfRecipe(item)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean allowAsInput2(ItemStack slot1,ItemStack item){
		for(int i=0;i<recipes.size();i++){
			if(recipes.get(i).isValidInputSlot1_2(slot1, item)){
				return true;
			}
		}
		return false;
	}
	
	public static void addRecipe(ItemStack input1,int amount1, ItemStack input2,int amount2, ItemStack bottle,int amount3, FluidStack fluidIn, FluidStack fluidOut, ItemStack output,boolean swap,int power){
		recipes.add(new ChemLabRecipe(new ItemStackOreDict(input1),amount1,new ItemStackOreDict(input2),amount2,bottle,amount3,fluidIn,swap,output,fluidOut,power));
	}
	public static void addRecipe(String input1,int amount1, String input2,int amount2, ItemStack bottle,int amount3, FluidStack fluidIn, FluidStack fluidOut, ItemStack output,boolean swap,int power){
		recipes.add(new ChemLabRecipe(new ItemStackOreDict(input1),amount1,new ItemStackOreDict(input2),amount2,bottle,amount3,fluidIn,swap,output,fluidOut,power));
	}
	public static void addRecipe(String input1,int amount1, ItemStack input2,int amount2, ItemStack bottle,int amount3, FluidStack fluidIn, FluidStack fluidOut, ItemStack output,boolean swap,int power){
		recipes.add(new ChemLabRecipe(new ItemStackOreDict(input1),amount1,new ItemStackOreDict(input2),amount2,bottle,amount3,fluidIn,swap,output,fluidOut,power));
	}
	public static void addRecipe(ItemStack input1,int amount1, String input2,int amount2, ItemStack bottle,int amount3, FluidStack fluidIn, FluidStack fluidOut, ItemStack output,boolean swap,int power){
		recipes.add(new ChemLabRecipe(new ItemStackOreDict(input1),amount1,new ItemStackOreDict(input2),amount2,bottle,amount3,fluidIn,swap,output,fluidOut,power));
	}
	
	public static void addRecipe(ItemStackOreDict input1,int amount1, ItemStackOreDict input2,int amount2, ItemStack bottle,int amount3, FluidStack fluidIn, FluidStack fluidOut, ItemStack output,boolean swap,int power){
		recipes.add(new ChemLabRecipe(input1,amount1,input2,amount2,bottle,amount3,fluidIn,swap,output,fluidOut,power));
	}
	
	
	
	public static class ChemLabOutput{
		private ItemStack output;
		private FluidStack liquidOutput;
		private int[] consumption;
		private int powerPerTick;
		public ChemLabOutput(ItemStack output, FluidStack liquidOutput, int i1,int i2, int i3,int i4,int power) {
			super();
			this.output = output;
			this.liquidOutput = liquidOutput;
			this.consumption=new int[4];
			this.consumption[0]=i1;
			this.consumption[1]=i2;
			this.consumption[2]=i3;
			this.consumption[3]=i4;
			this.powerPerTick=power;
		}
		
		public ChemLabOutput(NBTTagCompound tags) {
			this(null,null,0,0,0,0,0);
			this.loadFromNBTTags(tags);
		}
		
		public ItemStack getItemOutput() {
			return output;
		}
		public FluidStack getLiquidOutput() {
			return liquidOutput;
		}
		public int getAmount(int i){
			return this.consumption[i];
		}
		public int getPowerPerTick() {
			return powerPerTick;
		}
		
		
		public void loadFromNBTTags(NBTTagCompound tags){
			NBTTagCompound item = tags.getCompoundTag("output");
			if (!item.hasNoTags()){
				this.output = ItemStack.loadItemStackFromNBT(item);
			}else {
				this.output=null;
			}
			
			NBTTagCompound fluid = tags.getCompoundTag("liquidOutput");
			if (!fluid.hasNoTags()){
				this.liquidOutput = FluidStack.loadFluidStackFromNBT(fluid);
			}else {
				this.liquidOutput=null;
			}
			this.powerPerTick = tags.getInteger("powerPerTick");
		}
		
		public NBTTagCompound getAsNBTTags(){
			NBTTagCompound tags = new NBTTagCompound();
			
			if(this.output!=null){
				NBTTagCompound outputItem = new NBTTagCompound();
		        this.output.writeToNBT(outputItem);
		        tags.setTag("output", outputItem);
			}
	        
			if(this.liquidOutput!=null){
		        NBTTagCompound liquidOutput = new NBTTagCompound();
		        this.liquidOutput.writeToNBT(liquidOutput);
		        tags.setTag("liquidOutput", liquidOutput);
			}
			
	        tags.setInteger("powerPerTick", this.powerPerTick);
	        return tags;
		}
		
	}

	public static class ChemLabRecipe{
		public ItemStackOreDict slot1;
		public ItemStackOreDict slot2;
		public ItemStack slot3;
		public FluidStack fluidIn;
		public FluidStack fluidOutput;
		public boolean allowSwap;
		public ItemStack output;
		public int[] amounts=new int[4];
		public int powerPerTick=5;
		
		
		public boolean isItemPartOfRecipe(ItemStack item){
			return (this.slot1!=null && this.slot1.isEqualWithOreDict(item)) || (this.slot2!=null && this.slot2.isEqualWithOreDict(item)) || (this.slot3!=null && ItemUtil.isItemEqual(this.slot3, item));
		}
		
		public ChemLabRecipe(ItemStackOreDict slot1,int amount1, ItemStackOreDict slot2,int amount2, ItemStack bottle,int amount3, FluidStack fluidIn,
				boolean allowSwap, ItemStack output, FluidStack fluidOutput, int powerPerTick) {
			this.slot1 = slot1;
			this.slot2 = slot2;
			this.slot3 = bottle;
			this.fluidIn=fluidIn;
			this.fluidOutput=fluidOutput;
			this.allowSwap = allowSwap;
			this.output = output;
			this.amounts[0]=amount1;
			this.amounts[1]=amount2;
			this.amounts[2]=amount3;
			this.amounts[3]=fluidIn!=null?fluidIn.amount:0;
			this.powerPerTick=powerPerTick;
		}
		
		//Returns if this contents are a valid input for this recipe;
		public boolean isValidInput(ItemStack slot1, ItemStack slot2, ItemStack slot3, FluidStack fluidIn){
			//System.out.println("Slot1:"+slot1+"Slot2:"+slot2+"Slot3"+slot3);
			if (this.slot1.isEqualWithOreDict(slot1) && this.slot2.isEqualWithOreDict(slot2) && ((this.slot3!=null && OreDictionary.itemMatches(this.slot3,slot3,true)) || (this.slot3==null && slot3==null))) {
				return checkLiquid(fluidIn) && checkAmounts(slot1,slot2,slot3,false);
			}
			if (allowSwap && this.slot1.isEqualWithOreDict(slot2) && this.slot2.isEqualWithOreDict(slot1) && ((this.slot3!=null && OreDictionary.itemMatches(this.slot3,slot3,true)) || (this.slot3==null && slot3==null))){
				return checkLiquid(fluidIn) && checkAmounts(slot1,slot2,slot3,true);
			}
			return false;
		}
		
		public boolean isValidInputSlot1_2(ItemStack slot1, ItemStack slot2){
			if (this.slot1.isEqualWithOreDict(slot1) && this.slot2.isEqualWithOreDict(slot2)) {
				return true;
			}
			if (allowSwap && (this.slot1.isEqualWithOreDict(slot2) && this.slot2.isEqualWithOreDict(slot1))){
				return true;
			}
			return false;
		}
		
		
		private boolean isEnoughOrNull(ItemStack item, int amount){
			if (item==null){
				return amount==0;
			} else {
				return item.stackSize>=amount;
			}
		}
		
		private boolean checkAmounts(ItemStack slot1, ItemStack slot2, ItemStack slot3, boolean swapped12){
			ItemStack i1=slot1;
			ItemStack i2=slot2;
			if (swapped12){
				i2 = slot1;
				i1 = slot2;
			}
			
			return isEnoughOrNull(i1,this.amounts[0]) && isEnoughOrNull(i2,this.amounts[1]) && isEnoughOrNull(slot3,this.amounts[2]);
		}
		
		private boolean checkLiquid(FluidStack fluid){
			if( this.fluidIn==null){
				return true;
			} else if(fluid==null){
				//need fluid but input is null
				return false;
			}
			if (this.fluidIn.getFluid() == fluid.getFluid()){
				return this.fluidIn.amount<=fluid.amount;
			}
			return false;
		}
		
	}
}
