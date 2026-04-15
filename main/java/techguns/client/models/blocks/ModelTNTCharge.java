package techguns.client.models.blocks;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTNTCharge extends ModelMachine {

	//fields
    ModelRenderer Cylinder03;
    ModelRenderer Cylinder01;
    ModelRenderer Cylinder02;
    ModelRenderer Shape1;
    
  public ModelTNTCharge()
  {
    textureWidth = 32;
    textureHeight = 32;
    
      Cylinder03 = new ModelRenderer(this, 0, 0);
      Cylinder03.addBox(0F, 0F, 0F, 4, 12, 4);
      Cylinder03.setRotationPoint(-6.8F, 10F, -5F);
      Cylinder03.setTextureSize(32, 32);
      Cylinder03.mirror = true;
      setRotation(Cylinder03, 0F, 0.7853982F, 0F);
      Cylinder01 = new ModelRenderer(this, 0, 0);
      Cylinder01.addBox(0F, 0F, 0F, 4, 12, 4);
      Cylinder01.setRotationPoint(1.2F, 10F, -5F);
      Cylinder01.setTextureSize(32, 32);
      Cylinder01.mirror = true;
      setRotation(Cylinder01, 0F, 0.7853982F, 0F);
      Cylinder02 = new ModelRenderer(this, 0, 0);
      Cylinder02.addBox(0F, 0F, 0F, 4, 12, 4);
      Cylinder02.setRotationPoint(-2.7F, 10.1F, -5F);
      Cylinder02.setTextureSize(32, 32);
      Cylinder02.mirror = true;
      setRotation(Cylinder02, 0F, 0.7853982F, 0F);
      Shape1 = new ModelRenderer(this, 0, 17);
      Shape1.addBox(0F, 2F, 0F, 8, 4, 2);
      Shape1.setRotationPoint(-4F, 12F, -4F);
      Shape1.setTextureSize(32, 32);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
  }
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, float progress) {
		 super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
 
	    Cylinder03.render(f5);
	    Cylinder01.render(f5);
	    Cylinder02.render(f5);
	    Shape1.render(f5);
	}

}
