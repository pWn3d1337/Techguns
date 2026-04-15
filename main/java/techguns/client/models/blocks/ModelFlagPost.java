package techguns.client.models.blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class ModelFlagPost extends ModelBase{
	  //fields
    ModelRenderer Flag3;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape6;
    ModelRenderer Flag1;
    ModelRenderer Flag2;
  
  public ModelFlagPost()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      Flag3 = new ModelRenderer(this, 24, 50);
      Flag3.addBox(0F, 0F, 0F, 6, 14, 0);
      Flag3.setRotationPoint(13F, -37.5F, 0F);
      Flag3.setTextureSize(64, 64);
      Flag3.mirror = true;
      setRotation(Flag3, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 0, 0);
      Shape2.addBox(0F, 0F, 0F, 10, 2, 10);
      Shape2.setRotationPoint(-5F, 22F, -5F);
      Shape2.setTextureSize(64, 64);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 0, 14);
      Shape3.addBox(0F, 0F, 0F, 5, 14, 5);
      Shape3.setRotationPoint(-2.5F, 8F, -2.5F);
      Shape3.setTextureSize(64, 64);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0F);
      Shape4 = new ModelRenderer(this, 22, 14);
      Shape4.addBox(0F, 0F, 0F, 3, 23, 3);
      Shape4.setRotationPoint(-1.5F, -15F, -1.5F);
      Shape4.setTextureSize(64, 64);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, 0F);
      Shape5 = new ModelRenderer(this, 34, 15);
      Shape5.addBox(0F, 0F, 0F, 2, 23, 2);
      Shape5.setRotationPoint(-1F, -38F, -1F);
      Shape5.setTextureSize(64, 64);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, 0F);
      Shape6 = new ModelRenderer(this, 33, 3);
      Shape6.addBox(0F, 0F, 0F, 3, 2, 3);
      Shape6.setRotationPoint(-1.5F, -40F, -1.5F);
      Shape6.setTextureSize(64, 64);
      Shape6.mirror = true;
      setRotation(Shape6, 0F, 0F, 0F);
      Flag1 = new ModelRenderer(this, 0, 50);
      Flag1.addBox(0F, 0F, 0F, 6, 14, 0);
      Flag1.setRotationPoint(1F, -37.5F, 0F);
      Flag1.setTextureSize(64, 64);
      Flag1.mirror = true;
      setRotation(Flag1, 0F, 0F, 0F);
      Flag2 = new ModelRenderer(this, 12, 50);
      Flag2.addBox(0F, 0F, 0F, 6, 14, 0);
      Flag2.setRotationPoint(7F, -37.5F, 0F);
      Flag2.setTextureSize(64, 64);
      Flag2.mirror = true;
      setRotation(Flag2, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    //super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5,entity);
    Shape2.render(f5);
    Shape3.render(f5);
    Shape4.render(f5);
    Shape5.render(f5);
    Shape6.render(f5);

    long t = Minecraft.getMinecraft().theWorld.getTotalWorldTime();
    double angle = 30.0*Math.sin(((float)(t % 60)/60.0f)*Math.PI*2.0);
    GL11.glTranslatef(0.0625f, 0, 0);
    GL11.glRotated(angle, 0.0, 1.0, 0.0);
    GL11.glTranslatef(-0.0625f, 0, 0);
    Flag1.render(f5);  
    GL11.glPushMatrix();
    	angle = 30.0*Math.sin(((float)(t % 50)/50.0f)*Math.PI*2.0);
    	GL11.glTranslatef(0.4375f, 0, 0);
    	GL11.glRotated(angle, 0.0, 1.0, 0.0);
    	GL11.glTranslatef(-0.4375f, 0, 0);
    	Flag2.render(f5);
    	GL11.glPushMatrix();
    		GL11.glTranslatef(0.8125f, 0, 0);
    		angle = 30.0*Math.sin(((float)(t % 40)/40.0f)*Math.PI*2.0);
    		GL11.glRotated(angle, 0.0, 1.0, 0.0);
    		GL11.glTranslatef(-0.8125f, 0, 0);
    		Flag3.render(f5);
    	GL11.glPopMatrix();
    GL11.glPopMatrix();
    
    
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
