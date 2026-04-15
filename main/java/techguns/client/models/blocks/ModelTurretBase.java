package techguns.client.models.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTurretBase extends ModelBase {
	//fields
    ModelRenderer Corner01;
    ModelRenderer Top;
    ModelRenderer Corner02;
    ModelRenderer Corner03;
    ModelRenderer Bar03;
    ModelRenderer Corner04;
    ModelRenderer Bottom04;
    ModelRenderer Bar04;
    ModelRenderer Bar01;
    ModelRenderer Bar02;
    ModelRenderer Base01;
    ModelRenderer Bottom01;
    ModelRenderer Socket02;
    ModelRenderer Socket04;
    ModelRenderer Bottom02;
    ModelRenderer Socket01;
    ModelRenderer Bottom03;
    ModelRenderer Socket03;
  
  public ModelTurretBase()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      Corner01 = new ModelRenderer(this, 0, 30);
      Corner01.addBox(0F, 0F, 0F, 2, 15, 2);
      Corner01.setRotationPoint(-8F, 9F, 6F);
      Corner01.setTextureSize(64, 64);
      Corner01.mirror = true;
      setRotation(Corner01, 0F, 0F, 0F);
      Top = new ModelRenderer(this, 30, 0);
      Top.addBox(0F, 0F, 0F, 8, 2, 8);
      Top.setRotationPoint(-4F, 8F, -4F);
      Top.setTextureSize(64, 64);
      Top.mirror = true;
      setRotation(Top, 0F, 0F, 0F);
      Corner02 = new ModelRenderer(this, 0, 30);
      Corner02.addBox(0F, 0F, 0F, 2, 15, 2);
      Corner02.setRotationPoint(-8F, 9F, -8F);
      Corner02.setTextureSize(64, 64);
      Corner02.mirror = true;
      setRotation(Corner02, 0F, 0F, 0F);
      Corner03 = new ModelRenderer(this, 0, 30);
      Corner03.addBox(0F, 0F, 0F, 2, 15, 2);
      Corner03.setRotationPoint(6F, 9F, -8F);
      Corner03.setTextureSize(64, 64);
      Corner03.mirror = true;
      setRotation(Corner03, 0F, 0F, 0F);
      Bar03 = new ModelRenderer(this, 36, 18);
      Bar03.addBox(0F, 0F, -1F, 6, 1, 2);
      Bar03.setRotationPoint(7F, 9F, -7F);
      Bar03.setTextureSize(64, 64);
      Bar03.mirror = true;
      setRotation(Bar03, 0F, -2.356194F, 0F);
      Corner04 = new ModelRenderer(this, 0, 30);
      Corner04.addBox(0F, 0F, 0F, 2, 15, 2);
      Corner04.setRotationPoint(6F, 9F, 6F);
      Corner04.setTextureSize(64, 64);
      Corner04.mirror = true;
      setRotation(Corner04, 0F, 0F, 0F);
      Bottom04 = new ModelRenderer(this, 0, 15);
      Bottom04.addBox(0F, 0F, 0F, 1, 2, 12);
      Bottom04.setRotationPoint(6F, 22F, 7F);
      Bottom04.setTextureSize(64, 64);
      Bottom04.mirror = true;
      setRotation(Bottom04, 0F, -1.570796F, 0F);
      Bar04 = new ModelRenderer(this, 36, 18);
      Bar04.addBox(0F, 0F, -1F, 6, 1, 2);
      Bar04.setRotationPoint(7F, 9F, 7F);
      Bar04.setTextureSize(64, 64);
      Bar04.mirror = true;
      setRotation(Bar04, 0F, 2.356194F, 0F);
      Bar01 = new ModelRenderer(this, 36, 18);
      Bar01.addBox(0F, 0F, -1F, 6, 1, 2);
      Bar01.setRotationPoint(-7F, 9F, 7F);
      Bar01.setTextureSize(64, 64);
      Bar01.mirror = true;
      setRotation(Bar01, 0F, 0.7853982F, 0F);
      Bar02 = new ModelRenderer(this, 36, 18);
      Bar02.addBox(0F, 0F, -1F, 6, 1, 2);
      Bar02.setRotationPoint(-7F, 9F, -7F);
      Bar02.setTextureSize(64, 64);
      Bar02.mirror = true;
      setRotation(Bar02, 0F, -0.7853982F, 0F);
      Base01 = new ModelRenderer(this, 8, 36);
      Base01.addBox(0F, 0F, 0F, 14, 14, 14);
      Base01.setRotationPoint(-7F, 10F, -7F);
      Base01.setTextureSize(64, 64);
      Base01.mirror = true;
      setRotation(Base01, 0F, 0F, 0F);
      Bottom01 = new ModelRenderer(this, 0, 15);
      Bottom01.addBox(0F, 0F, 0F, 1, 2, 12);
      Bottom01.setRotationPoint(-8F, 22F, -6F);
      Bottom01.setTextureSize(64, 64);
      Bottom01.mirror = true;
      setRotation(Bottom01, 0F, 0F, 0F);
      Socket02 = new ModelRenderer(this, 0, 0);
      Socket02.addBox(0F, 0F, 0F, 1, 6, 6);
      Socket02.setRotationPoint(-8F, 13F, -3F);
      Socket02.setTextureSize(64, 64);
      Socket02.mirror = true;
      setRotation(Socket02, 0F, 0F, 0F);
      Socket04 = new ModelRenderer(this, 0, 0);
      Socket04.addBox(0F, 0F, 0F, 1, 6, 6);
      Socket04.setRotationPoint(3F, 13F, 7F);
      Socket04.setTextureSize(64, 64);
      Socket04.mirror = true;
      setRotation(Socket04, 0F, -1.570796F, 0F);
      Bottom02 = new ModelRenderer(this, 0, 15);
      Bottom02.addBox(0F, 0F, 0F, 1, 2, 12);
      Bottom02.setRotationPoint(7F, 22F, -6F);
      Bottom02.setTextureSize(64, 64);
      Bottom02.mirror = true;
      setRotation(Bottom02, 0F, 0F, 0F);
      Socket01 = new ModelRenderer(this, 0, 0);
      Socket01.addBox(0F, 0F, 0F, 1, 6, 6);
      Socket01.setRotationPoint(7F, 13F, -3F);
      Socket01.setTextureSize(64, 64);
      Socket01.mirror = true;
      setRotation(Socket01, 0F, 0F, 0F);
      Bottom03 = new ModelRenderer(this, 0, 15);
      Bottom03.addBox(0F, 0F, 0F, 1, 2, 12);
      Bottom03.setRotationPoint(6F, 22F, -8F);
      Bottom03.setTextureSize(64, 64);
      Bottom03.mirror = true;
      setRotation(Bottom03, 0F, -1.570796F, 0F);
      Socket03 = new ModelRenderer(this, 0, 0);
      Socket03.addBox(0F, 0F, 0F, 1, 6, 6);
      Socket03.setRotationPoint(3F, 13F, -8F);
      Socket03.setTextureSize(64, 64);
      Socket03.mirror = true;
      setRotation(Socket03, 0F, -1.570796F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Corner01.render(f5);
    Top.render(f5);
    Corner02.render(f5);
    Corner03.render(f5);
    Bar03.render(f5);
    Corner04.render(f5);
    Bottom04.render(f5);
    Bar04.render(f5);
    Bar01.render(f5);
    Bar02.render(f5);
    Base01.render(f5);
    Bottom01.render(f5);
    Socket02.render(f5);
    Socket04.render(f5);
    Bottom02.render(f5);
    Socket01.render(f5);
    Bottom03.render(f5);
    Socket03.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity ent)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, ent);
  }

}
