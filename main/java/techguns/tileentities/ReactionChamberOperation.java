package techguns.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import techguns.inventory.ReactionBeamFocus;
import techguns.inventory.ReactionChamberRecipe;

public class ReactionChamberOperation {
	public static byte RECIPE_TICKRATE=60;

	public ReactionChamberRecipe recipe;
	
	public byte preferedIntensity;

	public byte nextTick;
	public short duration;
	public short maxDuration;
	
	public short requiredCompletion;
	public short completion;
	public byte liquidLevel;
	
	public int powerPerRecipeTick;
	
	public ReactionChamberOperation(ReactionChamberRecipe recipe) {
		super();
		this.recipe = recipe;
		nextTick = RECIPE_TICKRATE;
		if(recipe!=null){
			this.preferedIntensity = recipe.preferredIntensity;
			this.maxDuration= (short) (recipe.ticks*RECIPE_TICKRATE);
			this.requiredCompletion = (short) recipe.requiredCompletion;
			this.liquidLevel = recipe.liquidLevel;
			this.powerPerRecipeTick = recipe.RFTick;
		} else {
			this.preferedIntensity=0;
			this.duration=0;
			this.requiredCompletion=0;
			this.liquidLevel=0;
			this.powerPerRecipeTick=0;
		}
		this.duration=0;
		this.completion=0;
	}
	
	public void readDataFromNBT(NBTTagCompound operationData) {
		/*NBTTagCompound operationData = tags.getCompoundTag("operationData");
		if (!operationData.hasNoTags()){
			*/
			this.duration = operationData.getShort("duration");
			this.maxDuration = operationData.getShort("maxDuration");
			this.completion = operationData.getShort("completion");
			this.requiredCompletion = operationData.getShort("requiredCompletion");
			this.preferedIntensity = operationData.getByte("preferedIntensity");	
			this.liquidLevel = operationData.getByte("liquidLevel");
			this.powerPerRecipeTick = operationData.getInteger("powerPerTick");
			this.nextTick= operationData.getByte("nextTick");
		//}
	}

	public void writeDataToNBT(NBTTagCompound tags) {
		NBTTagCompound operationData = new NBTTagCompound();

		operationData.setShort("recipeID", this.recipe.getID());
		operationData.setShort("duration", this.duration);
		operationData.setShort("maxDuration", this.maxDuration);
		operationData.setShort("requiredCompletion", this.requiredCompletion);
		operationData.setShort("completion", this.completion);
		operationData.setByte("preferedIntensity", this.preferedIntensity);
		operationData.setByte("liquidLevel", this.liquidLevel);
		operationData.setInteger("powerPerTick", this.powerPerRecipeTick);
		operationData.setByte("nextTick", this.nextTick);
		
		tags.setTag("operationData", operationData);
	}
	
	public boolean focusMatches(ItemStack item){
		return this.recipe.beamFocus == ReactionBeamFocus.getBeamFocus(item);
	}
	
	public boolean tick(byte intensity,byte liquidLevel, boolean client, ReactionChamberTileEntMaster tile, int RFTick){
		nextTick--;
		duration++;
		//System.out.println("Ticking Reaction");
		if (!client && nextTick==0 && duration<maxDuration){
			boolean powered = tile.consumePower(RFTick);
			//do recipe tick			
			if (powered && this.preferedIntensity==intensity && this.liquidLevel==liquidLevel){
				completion++;
				//System.out.println("Completion increased");
			} else {
				//System.out.println("NO increase");
			}
			
			double f = Math.random();
			if(f<=0.333d){
				if (this.preferedIntensity>this.recipe.preferredIntensity-this.recipe.intensityMargin){
					this.preferedIntensity--;
				} else {
					//this.preferedIntensity++;
				}
			} else if (f<=0.666d){
				//stay
			} else {
				if (this.preferedIntensity<this.recipe.preferredIntensity+this.recipe.intensityMargin){
					this.preferedIntensity++;
				} else {
					//this.preferedIntensity--;
				}
			}
			
			//tile.playAmbientSound(this.preferedIntensity==tile.intensity);
			
			
			//System.out.println("RECIPE TICK");
			nextTick=RECIPE_TICKRATE;
			
			return true;
		} else {
			return this.isFinished();
		}
	}
	
	public boolean isFinished(){
		return this.duration>=this.maxDuration || this.completion>=requiredCompletion;
	}
	
	public boolean isSuccess(){
		return this.completion>=requiredCompletion;
	}
	
	public boolean isFailure(){
		return this.duration>=this.maxDuration;
	}
	
	public int getCurrentPreferedIntensity(){
		return this.preferedIntensity;
	}
}
