package techguns.inventory;

import java.util.ArrayList;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import techguns.util.ItemUtil;
import techguns.util.TextUtil;

public class ReactionChamberRecipe {
	
	public short ID;
	
	public ItemStackOreDict input;
	public ReactionBeamFocus beamFocus;
	public Fluid liquidIn;
	public ArrayList<ItemStack> outputs;
	
	public byte ticks; //time of reaction in ReactionTicks
	
	public byte requiredCompletion; //Required completion level for success
	public byte preferredIntensity; //[0-10]
	public byte intensityMargin; //[0-10]
	public byte liquidLevel; //[0-10] in Buckets
	//public byte LiquidMargin; //[0-10] ?? Brauchen wir das? ??
	public int liquidConsumtion; // < LiquidLevel; in milliBuckets
	public float instability; //[0.0f-1.0f] chance to change preferredIntensity per ReactionTick
	
	public RiskType risk; //What happens when reaction fails.
	
	/**
	 * RF per Recipe tick
	 */
	public int RFTick;
	
	public static ReactionChamberRecipe getById(short id){
		ReactionChamberRecipe rec = recipes.get(id);
		return rec;
	}
	
	private static ArrayList<ReactionChamberRecipe> recipes = new ArrayList<ReactionChamberRecipe>();
	
	public static ArrayList<ReactionChamberRecipe> getRecipes(){
		return recipes;
	}
	
	public static ReactionChamberRecipe addRecipe(ItemStackOreDict input,
			ItemStack beamFocus, Fluid liquidIn,
			ItemStack output, int ticks, int requiredCompletion,
			int preferredIntensity, int intensityMargin, int liquidLevel,
			int liquidConsumtion, float instability, RiskType risk, int RFTick) {
		ReactionChamberRecipe rec = new ReactionChamberRecipe(input, beamFocus, liquidIn, output, ticks, requiredCompletion, preferredIntensity, intensityMargin, liquidLevel, (short)liquidConsumtion, instability, risk, RFTick);
		recipes.add(rec);
		return rec;
	}
	
	public static ReactionChamberRecipe addRecipe(ItemStackOreDict input,
			ItemStack beamFocus, Fluid liquidIn,
			ItemStack[] outputs, int ticks, int requiredCompletion,
			int preferredIntensity, int intensityMargin, int liquidLevel,
			int liquidConsumtion, float instability, RiskType risk, int RFTick) {
		ReactionChamberRecipe rec = new ReactionChamberRecipe(input, beamFocus, liquidIn, outputs, ticks, requiredCompletion, preferredIntensity, intensityMargin, liquidLevel, (short)liquidConsumtion, instability, risk, RFTick);
		recipes.add(rec);
		return rec;
	}
	
	private ReactionChamberRecipe(ItemStackOreDict input,
			ItemStack beamFocus, Fluid liquidIn,
			ItemStack output, int ticks, int requiredCompletion,
			int preferredIntensity, int intensityMargin, int liquidLevel,
			short liquidConsumtion, float instability, RiskType risk, int RFTick) {
		this.input=input;
		this.beamFocus=ReactionBeamFocus.getBeamFocus(beamFocus);
		this.liquidIn=liquidIn;
		this.outputs = new ArrayList<ItemStack>();
		this.outputs.add(output);
		this.ticks = (byte) ticks;
		this.requiredCompletion = (byte) requiredCompletion;
		this.preferredIntensity = (byte) preferredIntensity;
		this.intensityMargin = (byte) intensityMargin;
		this.liquidLevel = (byte) liquidLevel;
		this.liquidConsumtion = liquidConsumtion;
		this.instability = instability;
		this.risk = risk;
		this.RFTick = RFTick;
		
		this.ID = (short) recipes.size();
	}
	
	private ReactionChamberRecipe(ItemStackOreDict input,
			ItemStack beamFocus, Fluid liquidIn,
			ItemStack[] outputs, int ticks, int requiredCompletion,
			int preferredIntensity, int intensityMargin, int liquidLevel,
			short liquidConsumtion, float instability, RiskType risk, int RFTick) {
		this.input=input;
		this.beamFocus=ReactionBeamFocus.getBeamFocus(beamFocus);
		this.liquidIn=liquidIn;
		this.outputs = new ArrayList<ItemStack>();
		for (ItemStack item : outputs) {
			this.outputs.add(item);
		}
		this.ticks = (byte) ticks;
		this.requiredCompletion = (byte) requiredCompletion;
		this.preferredIntensity = (byte) preferredIntensity;
		this.intensityMargin = (byte) intensityMargin;
		this.liquidLevel = (byte) liquidLevel;
		this.liquidConsumtion = liquidConsumtion;
		this.instability = instability;
		this.risk = risk;
		this.RFTick = RFTick;
		
		this.ID = (short) recipes.size();
	}
	
	
	public static ReactionChamberRecipe getMatchingRecipe(ItemStack input, ItemStack focus, FluidStack tank, byte liquidLevel, byte intensity){
		for(int i=0;i<recipes.size();i++){
			ReactionChamberRecipe rec = recipes.get(i);
			/*System.out.println("Checking:"+input+ " w "+rec.input.getItemStacks().get(0).toString());
			System.out.println("Checking:"+tank==null?"null":tank.getFluid()+ " w "+rec.liquidIn);
			System.out.println("Checking:"+liquidLevel+ " w "+rec.liquidLevel);
			System.out.println("Checking:"+intensity+" w "+ rec.preferredIntensity);*/
			if (rec.matches(input, focus, tank, liquidLevel, intensity)){

				return rec;
			}
		}
		return null;
	}
	
	
	
	public boolean matches(ItemStack input, ItemStack focus, FluidStack tank, byte liquidLevel, byte intensity){
		
		if (input !=null && this.input.isEqualWithOreDict(input)){
			//System.out.println("Item Match");
			ReactionBeamFocus f = ReactionBeamFocus.getBeamFocus(focus);
			if (f!=null && f == this.beamFocus){
				//System.out.println("Focus Match");
				if (tank!=null && tank.getFluid()==this.liquidIn && liquidLevel == this.liquidLevel && tank.amount == (this.liquidLevel*1000)) {
					//System.out.println("Fluid Match");	
					//if (intensity >= (this.preferredIntensity -this.intensityMargin) && intensity <= (this.preferredIntensity +this.intensityMargin)){
						//System.out.println("Intensity Match");	
					if (intensity == this.preferredIntensity) {
						return true;
					}
					
				}
				
			}
			
			
		}
		return false;	
		
	}
	
	public short getID() {
		return ID;
	}

	public void addOutput(ItemStack output){
		outputs.add(output);
	}
	
	/**
	 * Returns if this method has the passed itemstack as result, needed for NEI handler
	 * @param result
	 * @return
	 */
	public boolean hasResult(ItemStack result){
		for(int i=0; i<this.outputs.size(); i++){
			if (ItemUtil.isItemEqual(result, this.outputs.get(i))){
				return true;
			}
		}
		return false;
	}

	public boolean usesItem(ItemStack itm){
		return this.input.isEqualWithOreDict(itm);
	}
	
	public enum RiskType {
		NONE, BREAK_ITEM, RAD_LOW, RAD_MEDIUM, RAD_HIGH, EXPLOSION_LOW, EXPLOSION_MEDIUM, EXPLOSION_HIGH, UNFORSEEN_CONSEQUENCES;
		
		@Override
		public String toString() {
			switch(this){
				
				case BREAK_ITEM:
					return ChatFormatting.GREEN+TextUtil.trans("techguns.reactionChamber.RiskType.breakItem");
				case EXPLOSION_LOW:
					return ChatFormatting.YELLOW+TextUtil.trans("techguns.reactionChamber.RiskType.expLow");
				case EXPLOSION_MEDIUM:
					return ChatFormatting.RED+TextUtil.trans("techguns.reactionChamber.RiskType.expMed");
				case RAD_LOW:
				case RAD_MEDIUM:
				case RAD_HIGH:
				case EXPLOSION_HIGH:
				case UNFORSEEN_CONSEQUENCES:
					return "NOT_YET_IMPLEMENTED";
					
				case NONE:
				default:
					return ChatFormatting.WHITE+TextUtil.trans("techguns.reactionChamber.RiskType.none");
			}
		}
	}

}
