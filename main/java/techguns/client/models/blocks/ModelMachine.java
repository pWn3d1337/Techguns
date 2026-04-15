package techguns.client.models.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public abstract class ModelMachine extends ModelBase{
	
	 @Override
	 public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
	 	this.render(entity,f,f1,f2,f3,f4,f5,0.0f);
	 }
	 
	  protected void setRotation(ModelRenderer model, float x, float y, float z)
	  {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	  }
	  
	  @Override
	  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
	  {
	    super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	  }

	 public abstract void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, float progress);
}
