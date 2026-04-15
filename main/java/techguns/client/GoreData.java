package techguns.client;

import net.minecraft.util.ResourceLocation;
import techguns.client.models.ModelGibs;

public class GoreData {
	public ModelGibs model;
	public ResourceLocation texture;
	public int numGibs;
	public float scale;
	 	
	public int bloodColorR;
	public int bloodColorG;
	public int bloodColorB;
	
	public boolean showBlood = true;

	public float minPartScale = 1.0f;
	public float maxPartScale = 1.0f;

	public GoreData(ModelGibs model, int numGibs, ResourceLocation texture, float scale, int bloodColorR, int bloodColorG, int bloodColorB) {
		this.model = model;
		this.texture = texture;
		this.numGibs = numGibs;// model.getNumGibs();
		this.scale = scale;
		this.bloodColorR = bloodColorR;
		this.bloodColorG = bloodColorG;
		this.bloodColorB = bloodColorB;
	}
	
	public GoreData setBlood(boolean showBlood) {
		this.showBlood = showBlood;
		return this;
	}

	/**
	 * Add a random scale to individual gibs.
	 * @param f
	 * @param g
	 */
	public void setRandomScale(float min, float max) {
		minPartScale = min;
		maxPartScale = max;
	}
}
