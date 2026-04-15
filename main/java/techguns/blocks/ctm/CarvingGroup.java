package techguns.blocks.ctm;

import java.util.ArrayList;
import java.util.List;

import com.cricketcraft.chisel.api.carving.ICarvingGroup;
import com.cricketcraft.chisel.api.carving.ICarvingVariation;

public class CarvingGroup implements ICarvingGroup{
	
	private String name;
	private String sound;
	private String oreName;
	private List<ICarvingVariation> variations = new ArrayList<ICarvingVariation>();

	public CarvingGroup(String name) {
		this.name = name;
	}
	
	public CarvingGroup(String name, String sound, String oreName) {
		this.name = name;
		this.sound = sound;
		this.oreName = oreName;
	}
	
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getSound() {
		return sound;
	}

	@Override
	public void setSound(String sound) {
		this.sound = sound;
	}

	@Override
	public String getOreName() {
		return oreName;
	}

	@Override
	public void setOreName(String oreName) {
		this.oreName = oreName;
	}

	@Override
	public List<ICarvingVariation> getVariations() {
		return variations;
	}

	@Override
	public void addVariation(ICarvingVariation variation) {
		this.variations.add(variation);
	}

	@Override
	public boolean removeVariation(ICarvingVariation variation) {
		return this.variations.remove(variation);
	}

}
