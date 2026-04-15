package techguns.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLmgMagE extends ModelBase {
	//fields
    ModelRenderer Mag03;
    ModelRenderer Mag01;
    ModelRenderer Mag02;

  
  public ModelLmgMagE()
  {
    textureWidth = 128;
    textureHeight = 128;
    
      
      Mag03 = new ModelRenderer(this, 39, 66);
      Mag03.addBox(0F, 0F, 0F, 3, 6, 7);
      Mag03.setRotationPoint(-3.5F, 1F, -6F);
      Mag03.setTextureSize(128, 128);
      Mag03.mirror = true;
      setRotation(Mag03, 0F, 0F, 0.7888888F);
      Mag01 = new ModelRenderer(this, 0, 71);
      Mag01.addBox(0F, 0F, 0F, 13, 6, 7);
      Mag01.setRotationPoint(-7.5F, 5F, -6F);
      Mag01.setTextureSize(128, 128);
      Mag01.mirror = true;
      setRotation(Mag01, 0F, 0F, 0F);
      Mag02 = new ModelRenderer(this, 0, 59);
      Mag02.addBox(0F, 0F, 0F, 9, 4, 7);
      Mag02.setRotationPoint(-3.5F, 1F, -6F);
      Mag02.setTextureSize(128, 128);
      Mag02.mirror = true;
      setRotation(Mag02, 0F, 0F, 0F);
     
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5,entity);
    Mag03.render(f5);
    Mag01.render(f5);
    Mag02.render(f5);

  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5,e);
  }
}
