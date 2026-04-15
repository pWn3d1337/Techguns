package techguns.client.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class ModelPistol3 extends ModelMultipart {

	//fields
    ModelRenderer Top;
    ModelRenderer Grip3;
    ModelRenderer Barrel;
    ModelRenderer IS2;
    ModelRenderer Grip2;
    ModelRenderer IS1;
    ModelRenderer Trigger;
    ModelRenderer Grip1;
    ModelRenderer Slide;
  
  public ModelPistol3()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Top = new ModelRenderer(this, 0, 17);
      Top.addBox(0F, 0F, 0F, 3, 1, 14);
      Top.setRotationPoint(-2F, 2F, -2F);
      Top.setTextureSize(64, 32);
      Top.mirror = true;
      setRotation(Top, 0F, 0F, 0F);
      Grip3 = new ModelRenderer(this, 3, 0);
      Grip3.addBox(0F, 0F, 0F, 2, 7, 3);
      Grip3.setRotationPoint(-1.5F, 3F, 7F);
      Grip3.setTextureSize(64, 32);
      Grip3.mirror = true;
      setRotation(Grip3, 0.2181662F, 0F, 0F);
      Barrel = new ModelRenderer(this, 0, 21);
      Barrel.addBox(0F, 0F, 0F, 1, 1, 6);
      Barrel.setRotationPoint(-0.5F, 0.25F, -3F);
      Barrel.setTextureSize(64, 32);
      Barrel.mirror = true;
      setRotation(Barrel, 0F, 0F, 0.7853982F);
      IS2 = new ModelRenderer(this, 0, 21);
      IS2.addBox(0F, 0F, 0F, 1, 1, 1);
      IS2.setRotationPoint(-1F, -0.5333334F, -0.5F);
      IS2.setTextureSize(64, 32);
      IS2.mirror = true;
      setRotation(IS2, 0F, 0F, 0F);
      Grip2 = new ModelRenderer(this, 20, 5);
      Grip2.addBox(0F, 0F, 0F, 1, 1, 3);
      Grip2.setRotationPoint(-1F, 5F, 5F);
      Grip2.setTextureSize(64, 32);
      Grip2.mirror = true;
      setRotation(Grip2, 0F, 0F, 0F);
      IS1 = new ModelRenderer(this, 0, 24);
      IS1.addBox(0F, 0F, 0F, 1, 1, 1);
      IS1.setRotationPoint(-1F, -0.5F, 9.5F);
      IS1.setTextureSize(64, 32);
      IS1.mirror = true;
      setRotation(IS1, 0F, 0F, 0F);
      Trigger = new ModelRenderer(this, 25, 0);
      Trigger.addBox(0F, 0F, 0F, 1, 2, 1);
      Trigger.setRotationPoint(-1F, 2.5F, 6.5F);
      Trigger.setTextureSize(64, 32);
      Trigger.mirror = true;
      setRotation(Trigger, -0.4089647F, 0F, 0F);
      Grip1 = new ModelRenderer(this, 20, 0);
      Grip1.addBox(0F, 0F, 0F, 1, 3, 1);
      Grip1.setRotationPoint(-1F, 3F, 4F);
      Grip1.setTextureSize(64, 32);
      Grip1.mirror = true;
      setRotation(Grip1, 0F, 0F, 0F);
      Slide = new ModelRenderer(this, 0, 0);
      Slide.addBox(0F, 0F, 0F, 3, 2, 14);
      Slide.setRotationPoint(-2F, 0F, -2F);
      Slide.setTextureSize(64, 32);
      Slide.mirror = true;
      setRotation(Slide, 0F, 0F, 0F);
  }
  
  @Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int ammoLeft, float reloadProgress,
			ItemRenderType renderType, int part, float fireProgress) {
	    setRotationAngles(f, f1, f2, f3, f4, f5,entity);
	    if(part==0){
		    Top.render(f5);
		    Grip3.render(f5);
		    Barrel.render(f5);
		    Grip2.render(f5);
		    Trigger.render(f5);
		    Grip1.render(f5);
	    } else {
		    IS1.render(f5);
		    IS2.render(f5);
		    Slide.render(f5);
	    }
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
