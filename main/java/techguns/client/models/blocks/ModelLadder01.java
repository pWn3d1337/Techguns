package techguns.client.models.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLadder01 extends ModelBase {
	 //fields
    ModelRenderer Side02;
    ModelRenderer Mid04;
    ModelRenderer Side01;
    ModelRenderer Mid01;
    ModelRenderer Mid02;
    ModelRenderer Mid03;
  
  public ModelLadder01()
  {
    textureWidth = 32;
    textureHeight = 32;
    
      Side02 = new ModelRenderer(this, 0, 0);
      Side02.addBox(0F, 0F, 0F, 2, 16, 2);
      Side02.setRotationPoint(6F, 8F, 6F);
      Side02.setTextureSize(64, 32);
      Side02.mirror = true;
      setRotation(Side02, 0F, 0F, 0F);
      Mid04 = new ModelRenderer(this, 0, 19);
      Mid04.addBox(0F, 0F, 0F, 12, 2, 2);
      Mid04.setRotationPoint(-6F, 9F, 6F);
      Mid04.setTextureSize(64, 32);
      Mid04.mirror = true;
      setRotation(Mid04, 0F, 0F, 0F);
      Side01 = new ModelRenderer(this, 0, 0);
      Side01.addBox(0F, 0F, 0F, 2, 16, 2);
      Side01.setRotationPoint(-8F, 8F, 6F);
      Side01.setTextureSize(64, 32);
      Side01.mirror = true;
      setRotation(Side01, 0F, 0F, 0F);
      Mid01 = new ModelRenderer(this, 0, 19);
      Mid01.addBox(0F, 0F, 0F, 12, 2, 2);
      Mid01.setRotationPoint(-6F, 21F, 6F);
      Mid01.setTextureSize(64, 32);
      Mid01.mirror = true;
      setRotation(Mid01, 0F, 0F, 0F);
      Mid02 = new ModelRenderer(this, 0, 19);
      Mid02.addBox(0F, 0F, 0F, 12, 2, 2);
      Mid02.setRotationPoint(-6F, 17F, 6F);
      Mid02.setTextureSize(64, 32);
      Mid02.mirror = true;
      setRotation(Mid02, 0F, 0F, 0F);
      Mid03 = new ModelRenderer(this, 0, 19);
      Mid03.addBox(0F, 0F, 0F, 12, 2, 2);
      Mid03.setRotationPoint(-6F, 13F, 6F);
      Mid03.setTextureSize(64, 32);
      Mid03.mirror = true;
      setRotation(Mid03, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    //super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5,entity);
    Side02.render(f5);
    Mid04.render(f5);
    Side01.render(f5);
    Mid01.render(f5);
    Mid02.render(f5);
    Mid03.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5,Entity ent)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5,ent);
  }
}
