package techguns.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLmgMag extends ModelBase {
	//fields
    ModelRenderer Bullet01;
    ModelRenderer Mag03;
    ModelRenderer Mag01;
    ModelRenderer Mag02;
    ModelRenderer Bullet06;
    ModelRenderer Bullet05;
    ModelRenderer Bullet04;
    ModelRenderer Bullet03;
    ModelRenderer Bullet02;
  
  public ModelLmgMag()
  {
    textureWidth = 128;
    textureHeight = 128;
    
      Bullet01 = new ModelRenderer(this, 0, 26);
      Bullet01.addBox(0F, 0F, 0F, 1, 1, 6);
      Bullet01.setRotationPoint(0F, -0.5F, -5.5F);
      Bullet01.setTextureSize(128, 128);
      Bullet01.mirror = true;
      setRotation(Bullet01, 0F, 0F, 0.7853982F);
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
      Bullet06 = new ModelRenderer(this, 0, 26);
      Bullet06.addBox(0F, 0F, 0F, 1, 1, 6);
      Bullet06.setRotationPoint(4.5F, 0.5F, -5.5F);
      Bullet06.setTextureSize(128, 128);
      Bullet06.mirror = true;
      setRotation(Bullet06, 0F, 0F, 0.7853982F);
      Bullet05 = new ModelRenderer(this, 0, 26);
      Bullet05.addBox(0F, 0F, 0F, 1, 1, 6);
      Bullet05.setRotationPoint(4F, -0.5F, -5.5F);
      Bullet05.setTextureSize(128, 128);
      Bullet05.mirror = true;
      setRotation(Bullet05, 0F, 0F, 0.7853982F);
      Bullet04 = new ModelRenderer(this, 0, 26);
      Bullet04.addBox(0F, 0F, 0F, 1, 1, 6);
      Bullet04.setRotationPoint(3F, -0.5F, -5.5F);
      Bullet04.setTextureSize(128, 128);
      Bullet04.mirror = true;
      setRotation(Bullet04, 0F, 0F, 0.7853982F);
      Bullet03 = new ModelRenderer(this, 0, 26);
      Bullet03.addBox(0F, 0F, 0F, 1, 1, 6);
      Bullet03.setRotationPoint(2F, -0.5F, -5.5F);
      Bullet03.setTextureSize(128, 128);
      Bullet03.mirror = true;
      setRotation(Bullet03, 0F, 0F, 0.7853982F);
      Bullet02 = new ModelRenderer(this, 0, 26);
      Bullet02.addBox(0F, 0F, 0F, 1, 1, 6);
      Bullet02.setRotationPoint(1F, -0.5F, -5.5F);
      Bullet02.setTextureSize(128, 128);
      Bullet02.mirror = true;
      setRotation(Bullet02, 0F, 0F, 0.7853982F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5,entity);
    Bullet01.render(f5);
    Mag03.render(f5);
    Mag01.render(f5);
    Mag02.render(f5);
    Bullet06.render(f5);
    Bullet05.render(f5);
    Bullet04.render(f5);
    Bullet03.render(f5);
    Bullet02.render(f5);
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
