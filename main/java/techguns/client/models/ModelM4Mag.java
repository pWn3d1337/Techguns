package techguns.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelM4Mag extends ModelBase
{
	  //fields
	    ModelRenderer Magazine02;
	    ModelRenderer Magazine01;
	    ModelRenderer Magazine03;
	    ModelRenderer Magazine04;
	  
	  public ModelM4Mag()
	  {
	    textureWidth = 128;
	    textureHeight = 64;
	    
	      Magazine02 = new ModelRenderer(this, 95, 19);
	      Magazine02.addBox(0F, 0F, 0F, 2, 4, 6);
	      Magazine02.setRotationPoint(-1.5F, 9F, -4.4F);
	      Magazine02.setTextureSize(128, 64);
	      Magazine02.mirror = true;
	      setRotation(Magazine02, -0.1570796F, 0F, 0F);
	      Magazine01 = new ModelRenderer(this, 112, 19);
	      Magazine01.addBox(0F, 0F, 0F, 2, 5, 6);
	      Magazine01.setRotationPoint(-1.5F, 5F, -4.5F);
	      Magazine01.setTextureSize(128, 64);
	      Magazine01.mirror = true;
	      setRotation(Magazine01, 0F, 0F, 0F);
	      Magazine03 = new ModelRenderer(this, 112, 19);
	      Magazine03.addBox(0F, 0F, 0F, 2, 2, 6);
	      Magazine03.setRotationPoint(-1.5F, 3F, -4.5F);
	      Magazine03.setTextureSize(128, 64);
	      Magazine03.mirror = true;
	      setRotation(Magazine03, 0F, 0F, 0F);
	      Magazine04 = new ModelRenderer(this, 112, 30);
	      Magazine04.addBox(0F, 0F, 0F, 1, 1, 5);
	      Magazine04.setRotationPoint(-1.25F, 3F, -4F);
	      Magazine04.setTextureSize(128, 64);
	      Magazine04.mirror = true;
	      setRotation(Magazine04, 0F, 0F, -0.7853982F);
	  }
	  
	  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	  {
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	    Magazine02.render(f5);
	    Magazine01.render(f5);
	    Magazine03.render(f5);
	    Magazine04.render(f5);
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