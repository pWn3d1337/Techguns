package techguns.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGasMask2 extends ModelBase {
	//fields
    ModelRenderer Head;
    ModelRenderer Front;
    ModelRenderer FilterR;
    ModelRenderer FilterMountR;
    ModelRenderer FilterMiddle;
    ModelRenderer FilterL;
    ModelRenderer FilterMountL;
  
  public ModelGasMask2()
  {
	    textureWidth = 32;
	    textureHeight = 32;
	    
	      Head = new ModelRenderer(this, 0, 0);
	      Head.addBox(-4F, -8F, -4F, 8, 8, 8);
	      Head.setRotationPoint(0F, 0F, 0F);
	      Head.setTextureSize(32, 32);
	      Head.mirror = true;
	      setRotation(Head, 0F, 0F, 0F);
	      Front = new ModelRenderer(this, 9, 17);
	      Front.addBox(0F, 0F, 0F, 4, 2, 1);
	      Front.setRotationPoint(-2F, -2F, -5F);
	      Front.setTextureSize(32, 32);
	      Front.mirror = true;
	      setRotation(Front, 0F, 0F, 0F);
	      FilterR = new ModelRenderer(this, 0, 17);
	      FilterR.addBox(-2F, -1F, 0F, 3, 3, 1);
	      FilterR.setRotationPoint(3.5F, -0.5F, -6F);
	      FilterR.setTextureSize(32, 32);
	      FilterR.mirror = true;
	      setRotation(FilterR, 0.3490659F, -0.5964077F, 0F);
	      FilterMountR = new ModelRenderer(this, 20, 17);
	      FilterMountR.addBox(-1F, -1F, -2F, 2, 2, 3);
	      FilterMountR.setRotationPoint(1.5F, -0.9F, -4F);
	      FilterMountR.setTextureSize(32, 32);
	      FilterMountR.mirror = true;
	      setRotation(FilterMountR, 0.4089647F, -0.8907376F, 0.7853982F);
	      FilterMiddle = new ModelRenderer(this, 20, 17);
	      FilterMiddle.addBox(-1F, -1F, -2F, 2, 2, 3);
	      FilterMiddle.setRotationPoint(0F, -0.9F, -4F);
	      FilterMiddle.setTextureSize(32, 32);
	      FilterMiddle.mirror = true;
	      setRotation(FilterMiddle, 0F, 0F, 0.7853982F);
	      FilterL = new ModelRenderer(this, 0, 17);
	      FilterL.addBox(-1F, -1F, 0F, 3, 3, 1);
	      FilterL.setRotationPoint(-3.4F, -0.5F, -6F);
	      FilterL.setTextureSize(32, 32);
	      FilterL.mirror = true;
	      setRotation(FilterL, 0.3490659F, 0.5964069F, 0F);
	      FilterMountL = new ModelRenderer(this, 20, 17);
	      FilterMountL.addBox(-1F, -1F, -2F, 2, 2, 3);
	      FilterMountL.setRotationPoint(-1.5F, -0.9F, -4F);
	      FilterMountL.setTextureSize(32, 32);
	      FilterMountL.mirror = true;
	      setRotation(FilterMountL, 0.4105138F, 0.298978F, 0.7853982F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Head.render(f5);
    Front.render(f5);
    FilterR.render(f5);
    FilterMountR.render(f5);
    FilterMiddle.render(f5);
    FilterL.render(f5);
    FilterMountL.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5,Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5,entity);
  }
}
