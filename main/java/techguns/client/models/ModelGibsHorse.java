package techguns.client.models;

import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGibsHorse extends ModelGibs {

	public ModelHorse model;
	
	public ModelGibsHorse(ModelHorse model) {
		super();
		this.model = model;
		for(int i=0;i<this.model.boxList.size();i++){
			ModelRenderer part = (ModelRenderer) this.model.boxList.get(i);
			part.setRotationPoint(0, 0, 0);
		}
	}

	@Override
	public void render(Entity ent, float f1, float f2, float f3, float f4, float f5, float f6, int part) {
		this.setRotationAngles(f1, f2, f3, f4, f5, f6, ent);
		((ModelRenderer)this.model.boxList.get(part)).render(f6);
	}

	@Override
	public int getNumGibs() {
		return this.boxList.size();
	}

}
