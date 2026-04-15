package techguns.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRocket extends ModelBase
{
  //fields
    ModelRenderer R9;
    ModelRenderer R11;
    ModelRenderer R8;
    ModelRenderer R3;
    ModelRenderer R10;
    ModelRenderer R2;
    ModelRenderer R4;
    ModelRenderer R5;
    ModelRenderer R6;
    ModelRenderer R7;
    ModelRenderer R1;
  
  public ModelRocket()
  {
    textureWidth = 128;
    textureHeight = 64;
    
      R9 = new ModelRenderer(this, 0, 0);
      R9.addBox(0F, 0F, 0F, 6, 4, 4);
      R9.setRotationPoint(-9.9F, -1.5F, -1.5F);
      R9.setTextureSize(128, 64);
      R9.mirror = true;
      setRotation(R9, 0F, 0F, 0F);
      R11 = new ModelRenderer(this, 30, 0);
      R11.addBox(0F, 0F, 0F, 2, 1, 6);
      R11.setRotationPoint(-9.8F, 0F, -2.5F);
      R11.setTextureSize(128, 64);
      R11.mirror = true;
      setRotation(R11, 0F, 0F, 0F);
      R8 = new ModelRenderer(this, 0, 16);
      R8.addBox(0F, 0F, 0F, 10, 3, 3);
      R8.setRotationPoint(-10F, -1F, -1F);
      R8.setTextureSize(128, 64);
      R8.mirror = true;
      setRotation(R8, 0F, 0F, 0F);
      R3 = new ModelRenderer(this, 20, 3);
      R3.addBox(0F, 0F, 0F, 1, 2, 2);
      R3.setRotationPoint(10F, -0.5F, -0.5F);
      R3.setTextureSize(128, 64);
      R3.mirror = true;
      setRotation(R3, 0F, 0F, 0F);
      R10 = new ModelRenderer(this, 30, 7);
      R10.addBox(0F, 0F, 0F, 2, 6, 1);
      R10.setRotationPoint(-9.8F, -2.5F, 0F);
      R10.setTextureSize(128, 64);
      R10.mirror = true;
      setRotation(R10, 0F, 0F, 0F);
      R2 = new ModelRenderer(this, 0, 16);
      R2.addBox(0F, 0F, 0F, 10, 3, 3);
      R2.setRotationPoint(0F, -1F, -1F);
      R2.setTextureSize(128, 64);
      R2.mirror = true;
      setRotation(R2, 0F, 0F, 0F);
      R4 = new ModelRenderer(this, 12, 9);
      R4.addBox(0F, 0F, 0F, 4, 2, 5);
      R4.setRotationPoint(4F, -0.5F, -2F);
      R4.setTextureSize(128, 64);
      R4.mirror = true;
      setRotation(R4, 0F, 0F, 0F);
      R5 = new ModelRenderer(this, 0, 8);
      R5.addBox(0F, 0F, 0F, 4, 5, 2);
      R5.setRotationPoint(4F, -2F, -0.5F);
      R5.setTextureSize(128, 64);
      R5.mirror = true;
      setRotation(R5, 0F, 0F, 0F);
      R6 = new ModelRenderer(this, 30, 7);
      R6.addBox(0F, 0F, 0F, 2, 6, 1);
      R6.setRotationPoint(4.1F, -2.5F, 0F);
      R6.setTextureSize(128, 64);
      R6.mirror = true;
      setRotation(R6, 0F, 0F, 0F);
      R7 = new ModelRenderer(this, 30, 0);
      R7.addBox(0F, 0F, 0F, 2, 1, 6);
      R7.setRotationPoint(4.1F, 0F, -2.5F);
      R7.setTextureSize(128, 64);
      R7.mirror = true;
      setRotation(R7, 0F, 0F, 0F);
      R1 = new ModelRenderer(this, 0, 0);
      R1.addBox(0F, 0F, 0F, 6, 4, 4);
      R1.setRotationPoint(3F, -1.5F, -1.5F);
      R1.setTextureSize(128, 64);
      R1.mirror = true;
      setRotation(R1, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    if (entity != null) {
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
    R9.render(f5);
    R11.render(f5);
    R8.render(f5);
    R3.render(f5);
    R10.render(f5);
    R2.render(f5);
    R4.render(f5);
    R5.render(f5);
    R6.render(f5);
    R7.render(f5);
    R1.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
  }

}
