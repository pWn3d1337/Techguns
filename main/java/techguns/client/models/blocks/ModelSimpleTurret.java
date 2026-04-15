package techguns.client.models.blocks;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import techguns.client.models.ModelMultipart;

public class ModelSimpleTurret extends ModelMultipart {

	//fields
    ModelRenderer Mount;
    ModelRenderer Base;
    ModelRenderer Stick;
  
  public ModelSimpleTurret()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Mount = new ModelRenderer(this, 0, 12);
      Mount.addBox(0F, 0F, -2F, 2, 1, 4);
      Mount.setRotationPoint(-1F, 13F, 0F);
      Mount.setTextureSize(64, 32);
      Mount.mirror = true;
      setRotation(Mount, 0F, 0F, 0F);
      Base = new ModelRenderer(this, 0, 0);
      Base.addBox(-4F, 0F, -4F, 8, 1, 8);
      Base.setRotationPoint(0F, 23F, 0F);
      Base.setTextureSize(64, 32);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
      Stick = new ModelRenderer(this, 0, 19);
      Stick.addBox(0F, 0F, 0F, 2, 11, 2);
      Stick.setRotationPoint(-3F, 12F, -1F);
      Stick.setTextureSize(64, 32);
      Stick.mirror = true;
      setRotation(Stick, 0F, 0F, 0F);
  }
  
  /*public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    setRotationAngles(f, f1, f2, f3, f4, f5,entity);
    Mount.render(f5);
    Base.render(f5);
    Stick.render(f5);
  }*/
  
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
					Stick.render(f6);
			}else{
					Mount.render(f6);
				
			}
		
	}
	}
