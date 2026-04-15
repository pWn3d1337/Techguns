package techguns.inventory;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import techguns.TGItems;

public class AmmoPressBuildPlans {
	public static final int AMMOUNT_9MM=12;
	public static final int AMMOUNT_SHOTGUN=16;
	public static final int AMMOUNT_RIFLE=8;
	public static final int AMMOUNT_HIGHCALIBRE=4;
	public static ArrayList<ItemStack> metal1 = new ArrayList<ItemStack>();
	public static ArrayList<ItemStack> metal2 = new ArrayList<ItemStack>();
	public static ArrayList<ItemStack> powder = new ArrayList<ItemStack>();
	
	public static void init(ArrayList<String> metal1_names, ArrayList<String> metal2_names, ArrayList<String> powder_names){
		
		if (metal1_names!=null){
			for(int i=0;i<metal1_names.size(); i++){
				String name = metal1_names.get(i);
				ArrayList<ItemStack> ores =OreDictionary.getOres(name);
				if (ores.size()>0){
					for(int j=0;j<ores.size();j++){
						metal1.add(ores.get(j));
					}
				}
			}
		}
		
		if (metal2_names!=null){
			for(int i=0;i<metal2_names.size(); i++){
				String name = metal2_names.get(i);
				ArrayList<ItemStack> ores =OreDictionary.getOres(name);
				if (ores.size()>0){
					for(int j=0;j<ores.size();j++){
						ItemStack stack = ores.get(j).copy();
						stack.stackSize=2;
						metal2.add(stack);
					}			
				}
			}
		}
		
		if(powder_names!=null){
			for(int i=0;i<powder_names.size(); i++){
				String name = powder_names.get(i);
				ArrayList<ItemStack> ores =OreDictionary.getOres(name);
				if (ores.size()>0){
					for(int j=0;j<ores.size();j++){
						powder.add(ores.get(j));
					}	
				}
			}
		}
		
	}
	
	public static int getAmountForRecipe(int itemdmg){
		if(itemdmg==TGItems.bullets9mm.getItemDamage()){
			return AMMOUNT_9MM;
		} else if (itemdmg==TGItems.shotgunShell.getItemDamage()){
			return AMMOUNT_SHOTGUN;
		} else if (itemdmg==TGItems.rifleBullets.getItemDamage()){
			return AMMOUNT_RIFLE;
		} else if (itemdmg==TGItems.highCalibreBullets.getItemDamage()){
			return AMMOUNT_HIGHCALIBRE;
		}
		return 1;
	}
	
	public static ItemStack[] getPossibleResults(){
		return new ItemStack[]{TGItems.newStack(TGItems.bullets9mm,AMMOUNT_9MM),TGItems.newStack(TGItems.shotgunShell, AMMOUNT_SHOTGUN),TGItems.newStack(TGItems.rifleBullets,AMMOUNT_RIFLE),TGItems.newStack(TGItems.highCalibreBullets, AMMOUNT_HIGHCALIBRE)};
	}
	
	public static boolean isInList(ItemStack it, ArrayList<ItemStack> list){
		boolean valid=false;
		//System.out.println("Check for:"+it.getDisplayName());
		for (int i=0; i<list.size(); i++){
			
			//System.out.println("---Check against:"+list.get(i).getDisplayName());
			if (OreDictionary.itemMatches(list.get(i),it, false) ){
				//System.out.println("---Valid");
				valid=true;
				break;
			} else {
				//System.out.println("---NOT VALID");
			}
		}
		return valid;
	}
	
	
	public static boolean isValidFor(ItemStack input, int slot){
		switch(slot){
			case 0:
				return isInList(input, metal1);
			case 1:
				return isInList(input, metal2);
			case 2:
				return isInList(input, powder);
			default:
				return false;
		}
	}
	
	public static int getTotalPower(int recipeIndex){
		return 100*5;
	}
	
	/*private static boolean itemStacksEqualForRecipe(ItemStack i1, ItemStack i2){
		return (OreDictionary.itemMatches(i1, i2,true) ) && (i2.stackSize>=i1.stackSize);
		
	}*/
	
	public static ItemStack getOutputFor(ItemStack metal1, ItemStack metal2, ItemStack powder, int plan){
		if (isValidFor(metal1,0) && isValidFor(metal2,1) && isValidFor(powder,2)){
			
			if (metal1.stackSize>=1 && metal2.stackSize>=2 && powder.stackSize>=1){
				
				switch(plan){
					case 0:
						return new ItemStack(TGItems.bullets9mm.getItem(),AMMOUNT_9MM,TGItems.bullets9mm.getItemDamage());
					case 1:
						return new ItemStack(TGItems.shotgunShell.getItem(),AMMOUNT_SHOTGUN,TGItems.shotgunShell.getItemDamage());
					case 2:
						return new ItemStack(TGItems.rifleBullets.getItem(),AMMOUNT_RIFLE,TGItems.rifleBullets.getItemDamage());
					case 3:
						return new ItemStack(TGItems.highCalibreBullets.getItem(),AMMOUNT_HIGHCALIBRE,TGItems.highCalibreBullets.getItemDamage());
				}
				
			} 
			
		} else{
			//System.out.println("Not valid inputs");
		}
		return null;
	}

	
}
