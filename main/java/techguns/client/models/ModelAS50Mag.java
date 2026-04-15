package techguns.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAS50Mag extends ModelBase {

	 //fields
    ModelRenderer Magazine01;
    ModelRenderer Magazine03;
    ModelRenderer Bullet2;
    ModelRenderer Magazine02;
    ModelRenderer Bullet;
	boolean empty;
    
	  public ModelAS50Mag(boolean empty)
	  {
	    textureWidth = 256;
	    textureHeight = 128;
	    
	    this.empty=empty;
	    
	      Magazine01 = new ModelRenderer(this, 52, 101);
	      Magazine01.addBox(0F, 0F, 0F, 2, 2, 1);
	      Magazine01.setRotationPoint(-1.5F, 7F, -0.5F);
	      Magazine01.setTextureSize(256, 128);
	      Magazine01.mirror = true;
	      setRotation(Magazine01, 0F, 0F, 0F);
	      Magazine03 = new ModelRenderer(this, 63, 100);
	      Magazine03.addBox(0F, 0F, 0F, 2, 2, 11);
	      Magazine03.setRotationPoint(-1.5F, 6F, -11F);
	      Magazine03.setTextureSize(256, 128);
	      Magazine03.mirror = true;
	      setRotation(Magazine03, -0.0916298F, 0F, 0F);
	      if (!empty){
		      Bullet2 = new ModelRenderer(this, 18, 122);
		      Bullet2.addBox(0F, 0F, 0F, 1, 1, 5);
		      Bullet2.setRotationPoint(-0.5F, -1.1F, -11F);
		      Bullet2.setTextureSize(256, 128);
		      Bullet2.mirror = true;
		      setRotation(Bullet2, 0F, 0F, 0.7853982F);
	      }
	      Magazine02 = new ModelRenderer(this, 34, 99);
	      Magazine02.addBox(0F, 0F, 0F, 2, 8, 12);
	      Magazine02.setRotationPoint(-1.5F, 0F, -11.5F);
	      Magazine02.setTextureSize(256, 128);
	      Magazine02.mirror = true;
	      setRotation(Magazine02, 0F, 0F, 0F);
	      if (!empty){
		      Bullet = new ModelRenderer(this, 0, 119);
		      Bullet.addBox(0F, 0F, 0F, 2, 2, 6);
		      Bullet.setRotationPoint(-0.5F, -1.8F, -6F);
		      Bullet.setTextureSize(256, 128);
		      Bullet.mirror = true;
		      setRotation(Bullet, 0F, 0F, 0.7853982F);
	      }
	  }
	  
	  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	  {
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	    Magazine01.render(f5);
	    Magazine03.render(f5);
	    Magazine02.render(f5);
	    if (!empty){
		    Bullet2.render(f5);
		    Bullet.render(f5);
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
	    super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	  }
}
