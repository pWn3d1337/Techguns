package techguns.client.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public abstract class ModelBipedMultipart extends ModelBiped {
	
	@Override
	public void render(Entity entity, float f1, float f2,
			float f3, float f4, float f5,
			float f6) {
		render(entity, f1,f2,f3,f4,f5,f6, 100,-1.0f, ItemRenderType.ENTITY,0, 0.0f);
		//super.render(entity, f1, f2, f3, f4, f5, f6);
	}
	
	public abstract void render(Entity entity, float f1, float f2,
			float f3, float f4, float f5,
			float f6, int ammoLeft, float reloadProgress, ItemRenderType renderType, int part, float fireProgress);
  
	
	protected void setRotation(ModelRenderer model, float x, float y, float z)
	  {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	  }
}
