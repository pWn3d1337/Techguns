package techguns.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGibsGeneric extends ModelGibs{

	ModelBase model;
	
	public ModelGibsGeneric(ModelBase model) {
		this.model = model;
		
		for (Object o : this.model.boxList) {
			ModelRenderer box  = (ModelRenderer)o;
			box.setRotationPoint(0.0f, 0.0f, 0.0f);
		}
	}
	
	@Override
	public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_,
			float p_78088_4_, float p_78088_5_, float p_78088_6_,
			float p_78088_7_, int part) {
		int i = 0;
		for (Object o : this.model.boxList) {
			ModelRenderer box  = (ModelRenderer)o;
			if (i++ == part) {
				box.render(p_78088_7_);
			}
		}
	}

	@Override
	public int getNumGibs() {
		return this.model.boxList.size();
	}

}
