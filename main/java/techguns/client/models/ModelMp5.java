package techguns.client.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class ModelMp5 extends ModelMultipart
{
  //fields
    ModelRenderer Top;
    ModelRenderer Stock03;
    ModelRenderer Grip1;
    ModelRenderer Grip2;
    ModelRenderer Mag03;
    ModelRenderer Stock01;
    ModelRenderer Barrel;
    ModelRenderer Mag01;
    ModelRenderer Mag02;
    ModelRenderer Box02;
    ModelRenderer Trigger02;
    ModelRenderer Sight01;
    ModelRenderer Box03;
    ModelRenderer Sight02;
    ModelRenderer Box01;
    ModelRenderer Grip3;
    ModelRenderer Stock02;
    ModelRenderer Trigger01;
  
  public ModelMp5()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Top = new ModelRenderer(this, 24, 9);
      Top.addBox(0F, 0F, 0F, 1, 1, 19);
      Top.setRotationPoint(-1.5F, -2F, -8F);
      Top.setTextureSize(64, 32);
      Top.mirror = true;
      setRotation(Top, 0F, 0F, 0F);
      Stock03 = new ModelRenderer(this, 13, 0);
      Stock03.addBox(0F, 0F, 0F, 2, 6, 1);
      Stock03.setRotationPoint(-2F, -1F, 21F);
      Stock03.setTextureSize(64, 32);
      Stock03.mirror = true;
      setRotation(Stock03, 0F, 0F, 0F);
      Grip1 = new ModelRenderer(this, 34, 0);
      Grip1.addBox(0F, 0F, 0F, 2, 3, 1);
      Grip1.setRotationPoint(-2F, 3F, 7F);
      Grip1.setTextureSize(64, 32);
      Grip1.mirror = true;
      setRotation(Grip1, 0F, 0F, 0F);
      Grip2 = new ModelRenderer(this, 41, 0);
      Grip2.addBox(0F, 0F, 0F, 2, 5, 1);
      Grip2.setRotationPoint(-2F, 3F, 8F);
      Grip2.setTextureSize(64, 32);
      Grip2.mirror = true;
      setRotation(Grip2, 0F, 0F, 0F);
      Mag03 = new ModelRenderer(this, 0, 21);
      Mag03.addBox(0F, 0F, 0F, 2, 2, 2);
      Mag03.setRotationPoint(-2F, 8F, -1F);
      Mag03.setTextureSize(64, 32);
      Mag03.mirror = true;
      setRotation(Mag03, 0F, 0F, 0F);
      Stock01 = new ModelRenderer(this, 0, 0);
      Stock01.addBox(0F, 0F, 0F, 2, 1, 8);
      Stock01.setRotationPoint(-2F, 0F, 12F);
      Stock01.setTextureSize(64, 32);
      Stock01.mirror = true;
      setRotation(Stock01, 0F, 0F, 0F);
      Barrel = new ModelRenderer(this, 21, 0);
      Barrel.addBox(0F, 0F, 0F, 1, 1, 2);
      Barrel.setRotationPoint(-1.5F, -0.5F, -11F);
      Barrel.setTextureSize(64, 32);
      Barrel.mirror = true;
      setRotation(Barrel, 0F, 0F, 0F);
      Mag01 = new ModelRenderer(this, 0, 10);
      Mag01.addBox(0F, 0F, 0F, 2, 2, 2);
      Mag01.setRotationPoint(-2F, 3F, 1F);
      Mag01.setTextureSize(64, 32);
      Mag01.mirror = true;
      setRotation(Mag01, 0F, 0F, 0F);
      Mag02 = new ModelRenderer(this, 0, 15);
      Mag02.addBox(0F, 0F, 0F, 2, 3, 2);
      Mag02.setRotationPoint(-2F, 5F, 0F);
      Mag02.setTextureSize(64, 32);
      Mag02.mirror = true;
      setRotation(Mag02, 0F, 0F, 0F);
      Box02 = new ModelRenderer(this, 9, 15);
      Box02.addBox(0F, 0F, 0F, 2, 1, 11);
      Box02.setRotationPoint(-2F, 1F, 1F);
      Box02.setTextureSize(64, 32);
      Box02.mirror = true;
      setRotation(Box02, 0F, 0F, 0F);
      Trigger02 = new ModelRenderer(this, 50, 0);
      Trigger02.addBox(0F, 0F, 0F, 1, 2, 1);
      Trigger02.setRotationPoint(-1.5F, 3F, 4F);
      Trigger02.setTextureSize(64, 32);
      Trigger02.mirror = true;
      setRotation(Trigger02, 0F, 0F, 0F);
      Sight01 = new ModelRenderer(this, 21, 4);
      Sight01.addBox(0F, 0F, 0F, 1, 4, 1);
      Sight01.setRotationPoint(-1.5F, -3.5F, -9F);
      Sight01.setTextureSize(64, 32);
      Sight01.mirror = true;
      setRotation(Sight01, 0F, 0F, 0F);
      Box03 = new ModelRenderer(this, 0, 10);
      Box03.addBox(0F, 0F, 0F, 2, 2, 20);
      Box03.setRotationPoint(-2F, -1F, -8F);
      Box03.setTextureSize(64, 32);
      Box03.mirror = true;
      setRotation(Box03, 0F, 0F, 0F);
      Sight02 = new ModelRenderer(this, 26, 6);
      Sight02.addBox(0F, 0F, 0F, 1, 1, 2);
      Sight02.setRotationPoint(-1.5F, -3F, 7F);
      Sight02.setTextureSize(64, 32);
      Sight02.mirror = true;
      setRotation(Sight02, 0F, 0F, 0F);
      Box01 = new ModelRenderer(this, 12, 17);
      Box01.addBox(0F, 0F, 0F, 2, 1, 8);
      Box01.setRotationPoint(-2F, 2F, 1F);
      Box01.setTextureSize(64, 32);
      Box01.mirror = true;
      setRotation(Box01, 0F, 0F, 0F);
      Grip3 = new ModelRenderer(this, 34, 5);
      Grip3.addBox(0F, 0F, 0F, 2, 4, 1);
      Grip3.setRotationPoint(-2F, 4F, 9F);
      Grip3.setTextureSize(64, 32);
      Grip3.mirror = true;
      setRotation(Grip3, 0F, 0F, 0F);
      Stock02 = new ModelRenderer(this, 0, 0);
      Stock02.addBox(0F, 0F, 0F, 2, 3, 1);
      Stock02.setRotationPoint(-2F, -1F, 20F);
      Stock02.setTextureSize(64, 32);
      Stock02.mirror = true;
      setRotation(Stock02, 0F, 0F, 0F);
      Trigger01 = new ModelRenderer(this, 50, 4);
      Trigger01.addBox(0F, 0F, 0F, 1, 1, 2);
      Trigger01.setRotationPoint(-1.5F, 4F, 5F);
      Trigger01.setTextureSize(64, 32);
      Trigger01.mirror = true;
      setRotation(Trigger01, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5,int ammoLeft, float reloadProgress, ItemRenderType renderType, int part, float fireProgress)
  {
    //super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Top.render(f5);
    Stock03.render(f5);
    Grip1.render(f5);
    Grip2.render(f5);
    Mag03.render(f5);
    Stock01.render(f5);
    Barrel.render(f5);
    Mag01.render(f5);
    Mag02.render(f5);
    Box02.render(f5);
    Trigger02.render(f5);
    Sight01.render(f5);
    Box03.render(f5);
    Sight02.render(f5);
    Box01.render(f5);
    Grip3.render(f5);
    Stock02.render(f5);
    Trigger01.render(f5);
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

