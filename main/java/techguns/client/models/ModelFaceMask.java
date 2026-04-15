package techguns.client.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFaceMask extends ModelBiped {
	//fields
    ModelRenderer Head;
    ModelRenderer Front;
  
  public ModelFaceMask(boolean hasFrontPart)
  {
	super(0.0625f,0,32,32);
    textureWidth = 32;
    textureHeight = 32;
    
      Head = new ModelRenderer(this, 0, 0);
      Head.addBox(-4F, -8F, -4F, 8, 8, 8);
      Head.setRotationPoint(0F, 0F, 0F);
      Head.setTextureSize(32, 32);
      Head.mirror = true;
      setRotation(Head, 0F, 0F, 0F);
      this.bipedHead.addChild(Head);
      
      if (hasFrontPart) {
	      Front = new ModelRenderer(this, 9, 17);
	      Front.addBox(0F, 0F, 0F, 4, 3, 1);
	      Front.setRotationPoint(-2F, -3F, -5F);
	      Front.setTextureSize(32, 32);
	      Front.mirror = true;
	      setRotation(Front, 0F, 0F, 0F);
	      this.bipedHead.addChild(Front);
      }
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    setRotationAngles(f, f1, f2, f3, f4, f5,entity);
    this.bipedHead.render(f5);

  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5,Entity e)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5,e);
  }
}
