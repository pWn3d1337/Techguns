package techguns.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPistol extends ModelBase {
	  //fields
    ModelRenderer Top;
    ModelRenderer Grip5;
    ModelRenderer Grip3;
    ModelRenderer Grip4;
    ModelRenderer Barrel;
    ModelRenderer Grip1;
    ModelRenderer IS2;
    ModelRenderer Grip2;
    ModelRenderer IS1;
  
  public ModelPistol()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Top = new ModelRenderer(this, 0, 0);
      Top.addBox(0F, 0F, 0F, 2, 3, 12);
      Top.setRotationPoint(-2F, 0F, -1F);
      Top.setTextureSize(64, 32);
      Top.mirror = true;
      setRotation(Top, 0F, 0F, 0F);
      Grip5 = new ModelRenderer(this, 34, 5);
      Grip5.addBox(0F, 0F, 0F, 2, 4, 1);
      Grip5.setRotationPoint(-2F, 4F, 9F);
      Grip5.setTextureSize(64, 32);
      Grip5.mirror = true;
      setRotation(Grip5, 0F, 0F, 0F);
      Grip3 = new ModelRenderer(this, 34, 0);
      Grip3.addBox(0F, 0F, 0F, 2, 3, 1);
      Grip3.setRotationPoint(-2F, 3F, 7F);
      Grip3.setTextureSize(64, 32);
      Grip3.mirror = true;
      setRotation(Grip3, 0F, 0F, 0F);
      Grip4 = new ModelRenderer(this, 41, 0);
      Grip4.addBox(0F, 0F, 0F, 2, 5, 1);
      Grip4.setRotationPoint(-2F, 3F, 8F);
      Grip4.setTextureSize(64, 32);
      Grip4.mirror = true;
      setRotation(Grip4, 0F, 0F, 0F);
      Barrel = new ModelRenderer(this, 0, 16);
      Barrel.addBox(0F, 0F, 0F, 1, 1, 2);
      Barrel.setRotationPoint(-1.5F, 0.5F, -3F);
      Barrel.setTextureSize(64, 32);
      Barrel.mirror = true;
      setRotation(Barrel, 0F, 0F, 0F);
      Grip1 = new ModelRenderer(this, 29, 0);
      Grip1.addBox(0F, 0F, 0F, 1, 2, 1);
      Grip1.setRotationPoint(-1.5F, 3F, 5F);
      Grip1.setTextureSize(64, 32);
      Grip1.mirror = true;
      setRotation(Grip1, 0F, 0F, 0F);
      IS2 = new ModelRenderer(this, 0, 20);
      IS2.addBox(0F, 0F, 0F, 1, 1, 1);
      IS2.setRotationPoint(-1.5F, -0.5333334F, -0.5F);
      IS2.setTextureSize(64, 32);
      IS2.mirror = true;
      setRotation(IS2, 0F, 0F, 0F);
      Grip2 = new ModelRenderer(this, 29, 4);
      Grip2.addBox(0F, 0F, 0F, 1, 1, 1);
      Grip2.setRotationPoint(-1.5F, 4F, 6F);
      Grip2.setTextureSize(64, 32);
      Grip2.mirror = true;
      setRotation(Grip2, 0F, 0F, 0F);
      IS1 = new ModelRenderer(this, 0, 23);
      IS1.addBox(0F, 0F, 0F, 1, 1, 1);
      IS1.setRotationPoint(-1.5F, -0.5F, 9.5F);
      IS1.setTextureSize(64, 32);
      IS1.mirror = true;
      setRotation(IS1, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Top.render(f5);
    Grip5.render(f5);
    Grip3.render(f5);
    Grip4.render(f5);
    Barrel.render(f5);
    Grip1.render(f5);
    IS2.render(f5);
    Grip2.render(f5);
    IS1.render(f5);
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
