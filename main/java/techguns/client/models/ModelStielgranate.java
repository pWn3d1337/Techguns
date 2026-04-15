package techguns.client.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class ModelStielgranate extends ModelMultipart {
	
	//fields
    ModelRenderer Granate01;
    ModelRenderer Stiel01;
    ModelRenderer Stiel02;
  
  public ModelStielgranate()
  {
    textureWidth = 32;
    textureHeight = 32;
    
    Granate01 = new ModelRenderer(this, 0, 5);
    Granate01.addBox(0F, 0F, 0F, 2, 12, 2);
    Granate01.setRotationPoint(-1F, -22F, 0.5F);
    Granate01.setTextureSize(32, 32);
    Granate01.mirror = true;
    setRotation(Granate01, 0F, 0.7853982F, 0F);
    Stiel01 = new ModelRenderer(this, 13, 0);
    Stiel01.addBox(0F, 0F, 0F, 4, 5, 4);
    Stiel01.setRotationPoint(-1.5F, -27F, -1.5F);
    Stiel01.setTextureSize(32, 32);
    Stiel01.mirror = true;
    setRotation(Stiel01, 0F, 0F, 0F);
    Stiel02 = new ModelRenderer(this, 0, 0);
    Stiel02.addBox(0F, 0F, 0F, 3, 1, 3);
    Stiel02.setRotationPoint(-1F, -10F, -1F);
    Stiel02.setTextureSize(32, 32);
    Stiel02.mirror = true;
    setRotation(Stiel02, 0F, 0F, 0F);
  }
  
  @Override
	public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float f6, int ammoLeft, float reloadProgress,
			ItemRenderType renderType, int part, float fireProgress) {
//    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f1, f2, f3, f4, f5, f6,entity);
    Granate01.render(f6);
    Stiel01.render(f6);
    Stiel02.render(f6);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	}

}
