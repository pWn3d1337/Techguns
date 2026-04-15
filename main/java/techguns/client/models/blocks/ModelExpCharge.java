package techguns.client.models.blocks;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelExpCharge extends ModelMachine {

	//fields
    ModelRenderer Shape1;
    ModelRenderer Charge01;
  
  public ModelExpCharge()
  {
	    textureWidth = 32;
	    textureHeight = 32;
    
      Shape1 = new ModelRenderer(this, 0, 17);
      Shape1.addBox(0F, 2F, 0F, 8, 4, 2);
      Shape1.setRotationPoint(-4F, 12F, -4F);
      Shape1.setTextureSize(32, 32);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Charge01 = new ModelRenderer(this, 0, 0);
      Charge01.addBox(0F, 2F, 0F, 10, 10, 5);
      Charge01.setRotationPoint(-5F, 9F, -8F);
      Charge01.setTextureSize(32, 32);
      Charge01.mirror = true;
      setRotation(Charge01, 0F, 0F, 0F);
  }
  
 
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, float progress) {
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	    Shape1.render(f5);
	    Charge01.render(f5);
	}

}
