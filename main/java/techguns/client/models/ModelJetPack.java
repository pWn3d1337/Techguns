package techguns.client.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class ModelJetPack extends ModelBipedMultipart {
	
	//fields
    ModelRenderer RocketTopR;
    ModelRenderer TopR02;
    ModelRenderer MidR02;
    ModelRenderer TopL02;
    ModelRenderer InnerR02;
    ModelRenderer MidL02;
    ModelRenderer InnerL02;
    ModelRenderer BackBack;
    ModelRenderer RocketR;
    ModelRenderer RocketL;
    ModelRenderer RocketTopL;
  
    private int variant;
    
  public ModelJetPack(int variant)
  {
	//super(0.0625f,0,32,32);
    textureWidth = 32;
    textureHeight = 32;
    
    
    this.variant=variant;
    
	    BackBack = new ModelRenderer(this, 16, 0);
	    BackBack.addBox(-2F, 0F, 0F, 4, 7, 4);
	    BackBack.setRotationPoint(0F, 1F, 2F);
	    BackBack.setTextureSize(32, 32);
	    BackBack.mirror = true;
	    setRotation(BackBack, 0F, 0F, 0F);
    
	  RocketR = new ModelRenderer(this, 0, 0);
      RocketR.addBox(-3F, 0F, 0F, 3, 9, 3);
      RocketR.setRotationPoint(-2F, -1F, 0.5F);
      RocketR.setTextureSize(32, 32);
      RocketR.mirror = true;
      setRotation(RocketR, 0F, 0F, 0F);
      this.BackBack.addChild(RocketR);
      //this.bipedBody.addChild(RocketR);
	      
	      RocketL = new ModelRenderer(this, 0, 0);
	      RocketL.addBox(-3F, 0F, 0F, 3, 9, 3);
	      RocketL.setRotationPoint(5F, -1F, 0.5F);
	      RocketL.setTextureSize(32, 32);
	      RocketL.mirror = true;
	      setRotation(RocketL, 0F, 0F, 0F);
	      this.BackBack.addChild(RocketL);
	    
	    
      RocketTopR = new ModelRenderer(this, 0, 13);
      RocketTopR.addBox(1F, 0F, -3.5F, 2, 2, 2);
      RocketTopR.setRotationPoint(-3.5F, -2F, 4F);
      RocketTopR.setTextureSize(32, 32);
      RocketTopR.mirror = true;
      setRotation(RocketTopR, 0F, 0F, 0F);
      this.RocketR.addChild(RocketTopR);
      //this.bipedBody.addChild(RocketTopR);
      
      RocketTopL = new ModelRenderer(this, 0, 13);
      RocketTopL.addBox(-6F, 0F, -3.5F, 2, 2, 2);
      RocketTopL.setRotationPoint(3.5F, -2F, 4F);
      RocketTopL.setTextureSize(32, 32);
      RocketTopL.mirror = true;
      setRotation(RocketTopL, 0F, 0F, 0F);
      this.RocketL.addChild(RocketTopL);
      
      
      if (this.variant ==0){
	      InnerR02 = new ModelRenderer(this, 0, 24);
	      InnerR02.addBox(0F, -1F, -2F, 5, 6, 1);
	      InnerR02.setRotationPoint(-10F, 2F, 3.5F);
	      InnerR02.setTextureSize(32, 32);
	      InnerR02.mirror = true;
	      setRotation(InnerR02, 0F, 0F, 0F);
	      this.BackBack.addChild(InnerR02);
	      
	      
	      TopR02 = new ModelRenderer(this, 13, 15);
	      TopR02.addBox(10F, -3F, -5.5F, 5, 1, 2);
	      TopR02.setRotationPoint(-10F, 1F, 3F);
	      TopR02.setTextureSize(32, 32);
	      TopR02.mirror = true;
	      setRotation(TopR02, 0F, 0F, 0F);
	      this.InnerR02.addChild(TopR02);
	      //this.bipedBody.addChild(TopR02);
	      
	      MidR02 = new ModelRenderer(this, 21, 19);
	      MidR02.addBox(10F, -3F, -5.5F, 1, 6, 2);
	      MidR02.setRotationPoint(-11F, 2F, 3F);
	      MidR02.setTextureSize(32, 32);
	      MidR02.mirror = true;
	      setRotation(MidR02, 0F, 0F, 0F);
	      this.InnerR02.addChild(MidR02);
	      //this.bipedBody.addChild(MidR02);
	      
	      InnerL02 = new ModelRenderer(this, 0, 24);
	      InnerL02.addBox(0F, -1F, -2F, 5, 6, 1);
	      InnerL02.setRotationPoint(5F, 2F, 3.5F);
	      InnerL02.setTextureSize(32, 32);
	      InnerL02.mirror = true;
	      setRotation(InnerL02, 0F, 0F, 0F);
	      this.BackBack.addChild(InnerL02);
	      
	      TopL02 = new ModelRenderer(this, 13, 15);
	      TopL02.addBox(-5F, -3F, -5.5F, 5, 1, 2);
	      TopL02.setRotationPoint(5F, 1F, 3F);
	      TopL02.setTextureSize(32, 32);
	      TopL02.mirror = true;
	      setRotation(TopL02, 0F, 0F, 0F);
	      this.InnerL02.addChild(TopL02);
	      //this.bipedBody.addChild(TopL02);
	      
	      
	      //this.bipedBody.addChild(InnerR02);
	      
	      MidL02 = new ModelRenderer(this, 21, 19);
	      MidL02.addBox(-5F, -3F, -5.5F, 1, 6, 2);
	      MidL02.setRotationPoint(10F, 2F, 3F);
	      MidL02.setTextureSize(32, 32);
	      MidL02.mirror = true;
	      setRotation(MidL02, 0F, 0F, 0F);
	      this.InnerL02.addChild(MidL02);
	      //this.bipedBody.addChild(MidL02);
      }
     
      //this.bipedBody.addChild(InnerL02);
      

      //this.bipedBody.addChild(BackBack);
      
      //this.bipedBody.addChild(RocketL);

      //this.bipedBody.addChild(RocketTopL);

  }
  
  /*public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {*/
  @Override
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int ammoLeft, float reloadProgress, ItemRenderType renderType,
  		int part, float fireProgress) {
    //super.render(entity, f, f1, f2, f3, f4, f5);
    this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

    BackBack.render(f5);
    
/*	   RocketTopR.render(f5);
	   if (variant==0){
		    TopR02.render(f5);
		    MidR02.render(f5);
		    TopL02.render(f5);
		    InnerR02.render(f5);
		    MidL02.render(f5);
		    InnerL02.render(f5);
	   }
	    RocketR.render(f5);
	    RocketL.render(f5);
	    RocketTopL.render(f5);
	if(part==0){ 
        BackBack.render(f5);
    }*/
    //this.bipedBody.render(f5);
  }
  
  @Override
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5,Entity e)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5,e);
  
 //   System.out.println("RotAngle:"+this.bipedBody.rotateAngleX);
    
	/*    RocketTopR.rotateAngleX = this.bipedBody.rotateAngleX;
	    if (variant==0){
		    TopR02.rotateAngleX =this.bipedBody.rotateAngleX;
		    MidR02.rotateAngleX=this.bipedBody.rotateAngleX;
		    TopL02.rotateAngleX = this.bipedBody.rotateAngleX;
		    InnerR02.rotateAngleX = this.bipedBody.rotateAngleX;
		    MidL02.rotateAngleX = this.bipedBody.rotateAngleX;
		    InnerL02.rotateAngleX = this.bipedBody.rotateAngleX;
	   }
	    RocketR.rotateAngleX= this.bipedBody.rotateAngleX;
	    RocketL.rotateAngleX = this.bipedBody.rotateAngleX;
	    RocketTopL.rotateAngleX = this.bipedBody.rotateAngleX;*/
        BackBack.rotateAngleX = this.bipedBody.rotateAngleX;
  }
  
 /* private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
*/

 /* public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5,Entity e)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
  }*/
}
