package techguns.client.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelNightVisionGoggles extends ModelBiped {
	 //fields
    ModelRenderer Head;
    ModelRenderer Right;
    ModelRenderer Left;
    ModelRenderer Mount;
    ModelRenderer Mid;
  
  public ModelNightVisionGoggles() {
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
      
      Right = new ModelRenderer(this, 0, 17);
      Right.addBox(-1F, -1F, 0F, 2, 2, 4);
      Right.setRotationPoint(-1.7F, -4F, -8F);
      Right.setTextureSize(32, 32);
      Right.mirror = true;
      setRotation(Right, 0F, 0F, 0.7853982F);
      this.bipedHead.addChild(Right);
      
      Left = new ModelRenderer(this, 0, 17);
      Left.addBox(-1F, -1F, 0F, 2, 2, 4);
      Left.setRotationPoint(1.7F, -4F, -8F);
      Left.setTextureSize(32, 32);
      Left.mirror = true;
      setRotation(Left, 0F, 0F, 0.7853982F);
      this.bipedHead.addChild(Left);
      
      Mount = new ModelRenderer(this, 13, 21);
      Mount.addBox(-1F, 0F, 0F, 2, 1, 4);
      Mount.setRotationPoint(0F, -5F, -6.5F);
      Mount.setTextureSize(32, 32);
      Mount.mirror = true;
      setRotation(Mount, 0.7853982F, 0F, 0F);
      this.bipedHead.addChild(Mount);
      
      Mid = new ModelRenderer(this, 13, 17);
      Mid.addBox(0F, 0F, 0F, 3, 1, 2);
      Mid.setRotationPoint(-1.5F, -5F, -7F);
      Mid.setTextureSize(32, 32);
      Mid.mirror = true;
      setRotation(Mid, 0F, 0F, 0F);
      this.bipedHead.addChild(Mid);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    setRotationAngles(f, f1, f2, f3, f4, f5,entity);
    this.bipedHead.render(f5);
   /* Head.render(f5);
    Right.render(f5);
    Left.render(f5);
    Mount.render(f5);
    Mid.render(f5);*/
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
