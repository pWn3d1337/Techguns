package techguns.inventory;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class ReactionBeamFocus {
	
	private static ArrayList<ReactionBeamFocus> beamFocusList = new ArrayList<ReactionBeamFocus>();
	
	ItemStack item;
	int powerConsumtion; //RF consumed per ReactionTick
	String sound; //Sound played each ReactionTick
	
	public ReactionBeamFocus(ItemStack item, int powerConsumtion, String sound) {
		super();
		this.item = item;
		this.powerConsumtion = powerConsumtion;
		this.sound = sound;
	}		
	
	public static void addBeamFocus(ItemStack item, int powerConsumption, String sound) {
		beamFocusList.add(new ReactionBeamFocus(item, powerConsumption, sound));
	}
	
	public static ItemStack getItemForType(ReactionBeamFocus type){
		if (type !=null){
			for (ReactionBeamFocus rbf : beamFocusList) {
				if (rbf.equals(type)) {
					return rbf.item;
				}
			}
		}
		return null;
	}
	
	public static ReactionBeamFocus getBeamFocus(ItemStack item) {
		if (item !=null){
			for (ReactionBeamFocus rbf : beamFocusList) {
				if (rbf.item.getItem() == item.getItem() && rbf.item.getItemDamage() == item.getItemDamage()) {
					return rbf;
				}
			}
		}
		return null;
	}

	public String getSound() {
		return sound;
	}
	
	
}
