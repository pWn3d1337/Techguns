package techguns.client.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;



public class ModelGlider extends ModelBipedMultipart {
	
    ModelRenderer BackBack;
    ModelRenderer MidR01;
    ModelRenderer TopR01;
    ModelRenderer TopR02;
    ModelRenderer MidR02;
    ModelRenderer TopL01;
    ModelRenderer TopL02;
    ModelRenderer InnerR02;
    ModelRenderer MidL02;
    ModelRenderer MidL01;
    ModelRenderer InnerR01;
    ModelRenderer InnerL02;
    ModelRenderer InnerL01;
  
    public ModelGlider(){
	
	 //fields
    textureWidth = 32;
    textureHeight = 32;
    
      BackBack = new ModelRenderer(this, 0, 0);
      BackBack.addBox(-3F, 0.5F, 2F, 7, 10, 4);
      BackBack.setRotationPoint(-0.5F, 0F, 0F);
      BackBack.setTextureSize(32, 32);
      BackBack.mirror = true;
      setRotation(BackBack, 0F, 0F, 0F);
//      this.bipedBody.addChild(BackBack);
      
      MidR01 = new ModelRenderer(this, 23, 0);
      MidR01.addBox(0F, -1.0F, 1.0F, 1, 7, 2);
      MidR01.setRotationPoint(-9.5F, 3F, 3F);
      MidR01.setTextureSize(32, 32);
      MidR01.mirror = true;
      setRotation(MidR01, 0F, 0F, 0F);
    //  this.BackBack.addChild(MidR01);
      
      TopR01 = new ModelRenderer(this, 13, 15);
      TopR01.addBox(0F, -1.0F, 1.0F, 6, 1, 2);
      TopR01.setRotationPoint(-9.5F, 2F, 3F);
      TopR01.setTextureSize(32, 32);
      TopR01.mirror = true;
      setRotation(TopR01, 0F, 0F, 0F);
  //    this.BackBack.addChild(TopR01);
      
      TopR02 = new ModelRenderer(this, 13, 15);
      TopR02.addBox(0F, -1.0F, 1.0F, 6, 1, 2);
      TopR02.setRotationPoint(-15.5F, 3F, 3F);
      TopR02.setTextureSize(32, 32);
      TopR02.mirror = true;
      setRotation(TopR02, 0F, 0F, 0F);
 //     this.MidR01.addChild(TopR02);
      
      MidR02 = new ModelRenderer(this, 21, 19);
      MidR02.addBox(0F, -1.0F, 1.0F, 1, 6, 2);
      MidR02.setRotationPoint(-16.5F, 4F, 3F);
      MidR02.setTextureSize(32, 32);
      MidR02.mirror = true;
      setRotation(MidR02, 0F, 0F, 0F);
 //     this.MidR01.addChild(MidR02);
      
      TopL01 = new ModelRenderer(this, 13, 15);
      TopL01.addBox(0F, -1.0F, 1.0F, 6, 1, 2);
      TopL01.setRotationPoint(3.5F, 2F, 3F);
      TopL01.setTextureSize(32, 32);
      TopL01.mirror = true;
      setRotation(TopL01, 0F, 0F, 0F);
  //    this.MidR01.addChild(TopL01);
      
      TopL02 = new ModelRenderer(this, 13, 15);
      TopL02.addBox(0F, -1.0F, 1.0F, 6, 1, 2);
      TopL02.setRotationPoint(9.5F, 3F, 3F);
      TopL02.setTextureSize(32, 32);
      TopL02.mirror = true;
      setRotation(TopL02, 0F, 0F, 0F);
   //   this.MidR01.addChild(TopL02);
      
      InnerR02 = new ModelRenderer(this, 0, 24);
      InnerR02.addBox(0F, -1.0F, 1.0F, 6, 6, 1);
      InnerR02.setRotationPoint(-15.5F, 4F, 3.5F);
      InnerR02.setTextureSize(32, 32);
      InnerR02.mirror = true;
      setRotation(InnerR02, 0F, 0F, 0F);
   //   this.MidR01.addChild(InnerR02);
      
      MidL02 = new ModelRenderer(this, 21, 19);
      MidL02.addBox(0F, -1.0F, 1.0F, 1, 6, 2);
      MidL02.setRotationPoint(15.5F, 4F, 3F);
      MidL02.setTextureSize(32, 32);
      MidL02.mirror = true;
      setRotation(MidL02, 0F, 0F, 0F);
  //    this.MidR01.addChild(MidL02);
      
      MidL01 = new ModelRenderer(this, 23, 0);
      MidL01.addBox(0F, -1.0F, 1.0F, 1, 7, 2);
      MidL01.setRotationPoint(8.5F, 3F, 3F);
      MidL01.setTextureSize(32, 32);
      MidL01.mirror = true;
      setRotation(MidL01, 0F, 0F, 0F);
  //    this.MidR01.addChild(MidL01);
      
      InnerR01 = new ModelRenderer(this, 0, 15);
      InnerR01.addBox(0F, -1.0F, 1.0F, 5, 7, 1);
      InnerR01.setRotationPoint(-8.5F, 3F, 3.5F);
      InnerR01.setTextureSize(32, 32);
      InnerR01.mirror = true;
      setRotation(InnerR01, 0F, 0F, 0F);
  //    this.MidR01.addChild(InnerR01);
      
      InnerL02 = new ModelRenderer(this, 0, 24);
      InnerL02.addBox(0F, -1.0F, 1.0F, 6, 6, 1);
      InnerL02.setRotationPoint(9.5F, 4F, 3.5F);
      InnerL02.setTextureSize(32, 32);
      InnerL02.mirror = true;
      setRotation(InnerL02, 0F, 0F, 0F);
  //    this.MidR01.addChild(InnerL02);
      
      InnerL01 = new ModelRenderer(this, 0, 15);
      InnerL01.addBox(0F, -1.0F, 1.0F, 5, 7, 1);
      InnerL01.setRotationPoint(3.5F, 3F, 3.5F);
      InnerL01.setTextureSize(32, 32);
      InnerL01.mirror = true;
      setRotation(InnerL01, 0F, 0F, 0F);
   //   this.MidR01.addChild(InnerL01);

  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5,int ammoLeft, float reloadProgress, ItemRenderType renderType, int part, float fireProgress)
  {
    setRotationAngles(f, f1, f2, f3, f4, f5,entity);
    if(part==0){
    	BackBack.render(f5);
    } else {
	    MidR01.render(f5);
	    TopR01.render(f5);
	    TopR02.render(f5);
	    MidR02.render(f5);
	    TopL01.render(f5);
	    TopL02.render(f5);
	    InnerR02.render(f5);
	    MidL02.render(f5);
	    MidL01.render(f5);
	    InnerR01.render(f5);
	    InnerL02.render(f5);
	    InnerL01.render(f5);
    }
  //  System.out.println("PART:"+part);
   // this.bipedBody.render(f5);
    
   /* if (part==0){
    	this.MidR01.isHidden=true;
    } else {
    	this.MidR01.isHidden=false;
    }
    this.BackBack.render(f5);*/
    
  }
  
  /*private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }*/
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5,Entity e)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5,e);
    
    this.BackBack.rotateAngleX=this.bipedBody.rotateAngleX;
    MidR01.rotateAngleX = this.bipedBody.rotateAngleX;
    TopR01.rotateAngleX = this.bipedBody.rotateAngleX;
    TopR02.rotateAngleX = this.bipedBody.rotateAngleX;
    MidR02.rotateAngleX = this.bipedBody.rotateAngleX;
    TopL01.rotateAngleX = this.bipedBody.rotateAngleX;
    TopL02.rotateAngleX = this.bipedBody.rotateAngleX;
    InnerR02.rotateAngleX = this.bipedBody.rotateAngleX;
    MidL02.rotateAngleX = this.bipedBody.rotateAngleX;
    MidL01.rotateAngleX = this.bipedBody.rotateAngleX;
    InnerR01.rotateAngleX = this.bipedBody.rotateAngleX;
    InnerL02.rotateAngleX = this.bipedBody.rotateAngleX;
    InnerL01.rotateAngleX = this.bipedBody.rotateAngleX;
    
    /*if(this.isSneak){
    	this.BackBack.rotateAngleX+=0.4f;
    }*/
  }

}
