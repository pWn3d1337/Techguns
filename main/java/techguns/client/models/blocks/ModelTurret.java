package techguns.client.models.blocks;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import techguns.client.models.ModelMultipart;

public class ModelTurret extends ModelMultipart {

	 //fields
    ModelRenderer Mount;
    ModelRenderer Base;
    ModelRenderer Stick01;
    ModelRenderer Stick02;
  
  public ModelTurret()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      Mount = new ModelRenderer(this, 34, 12);
      Mount.addBox(0F, 0F, -2F, 4, 1, 4);
      Mount.setRotationPoint(-2F, 13F, 0F);
      Mount.setTextureSize(64, 64);
      Mount.mirror = true;
      setRotation(Mount, 0F, 0F, 0F);
      Base = new ModelRenderer(this, 32, 22);
      Base.addBox(-4F, 0F, -4F, 8, 1, 8);
      Base.setRotationPoint(0F, 23F, 0F);
      Base.setTextureSize(64, 64);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
      Stick01 = new ModelRenderer(this, 9, 30);
      Stick01.addBox(0F, 0F, 0F, 2, 11, 2);
      Stick01.setRotationPoint(2F, 12F, -1F);
      Stick01.setTextureSize(64, 64);
      Stick01.mirror = true;
      setRotation(Stick01, 0F, 0F, 0F);
      Stick02 = new ModelRenderer(this, 9, 30);
      Stick02.addBox(0F, 0F, 0F, 2, 11, 2);
      Stick02.setRotationPoint(-4F, 12F, -1F);
      Stick02.setTextureSize(64, 64);
      Stick02.mirror = true;
      setRotation(Stick02, 0F, 0F, 0F);
  }

	
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
	  model.rotateAngleX = x;
	  model.rotateAngleY = y;
	  model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
	  super.setRotationAngles(f, f1, f2, f3, f4, f5,entity);
	}
	
	@Override
	public void render(Entity entity, float f1, float f2, float f3, float f4,
			float f5, float f6, int ammoLeft, float reloadProgress,
			ItemRenderType renderType, int part, float fireProgress) {
	
			setRotationAngles(f1, f2,f3,f4,f5,f6,entity);
			
			if(part==0){
				
					Base.render(f6);
					Stick01.render(f6);
					Stick02.render(f6);
			}else{
					Mount.render(f6);
				
			}
		
	}

}
