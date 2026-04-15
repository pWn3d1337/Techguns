package techguns.client.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class ModelDriller extends ModelMultipart {
	
	 //fields
    ModelRenderer Grip1;
    ModelRenderer Trigger01;
    ModelRenderer Trigger02;
    ModelRenderer Grip02;
    ModelRenderer Receiver03;
    ModelRenderer Grip01;
    ModelRenderer MagSocket;
    ModelRenderer Shape1;
    ModelRenderer Drill8;
    ModelRenderer Drill9;
    ModelRenderer Drill1;
    ModelRenderer Drill2;
    ModelRenderer Drill3;
    ModelRenderer Drill4;
    ModelRenderer Drill5;
    ModelRenderer Drill10;
    ModelRenderer Drill7;
    ModelRenderer Drill6;
  
  public ModelDriller()
  {
    textureWidth = 128;
    textureHeight = 64;
    
      Grip1 = new ModelRenderer(this, 68, 0);
      Grip1.addBox(0F, 0F, 0F, 3, 9, 4);
      Grip1.setRotationPoint(-2F, 2F, 6F);
      Grip1.setTextureSize(128, 64);
      Grip1.mirror = true;
      setRotation(Grip1, 0.4461433F, 0F, 0F);
      Trigger01 = new ModelRenderer(this, 42, 12);
      Trigger01.addBox(0F, 0F, 0F, 2, 1, 5);
      Trigger01.setRotationPoint(-1.5F, 4F, 3F);
      Trigger01.setTextureSize(128, 64);
      Trigger01.mirror = true;
      setRotation(Trigger01, 0F, 0F, 0F);
      Trigger02 = new ModelRenderer(this, 87, 1);
      Trigger02.addBox(0F, 0F, 0F, 1, 2, 1);
      Trigger02.setRotationPoint(-1F, 1.5F, 5F);
      Trigger02.setTextureSize(128, 64);
      Trigger02.mirror = true;
      setRotation(Trigger02, -0.2617994F, 0F, 0F);
      Grip02 = new ModelRenderer(this, 56, 6);
      Grip02.addBox(0F, 0F, 0F, 2, 8, 2);
      Grip02.setRotationPoint(-2F, 0.5F, -13.5F);
      Grip02.setTextureSize(128, 64);
      Grip02.mirror = true;
      setRotation(Grip02, 0F, 0.7853982F, 0F);
      Receiver03 = new ModelRenderer(this, 92, 0);
      Receiver03.addBox(0F, 0F, 0F, 3, 3, 15);
      Receiver03.setRotationPoint(-2F, -1F, -5F);
      Receiver03.setTextureSize(128, 64);
      Receiver03.mirror = true;
      setRotation(Receiver03, 0F, 0F, 0F);
      Grip01 = new ModelRenderer(this, 53, 0);
      Grip01.addBox(0F, 0F, 0F, 3, 2, 4);
      Grip01.setRotationPoint(-2F, -1.5F, -15.5F);
      Grip01.setTextureSize(128, 64);
      Grip01.mirror = true;
      setRotation(Grip01, 0F, 0F, 0F);
      MagSocket = new ModelRenderer(this, 43, 0);
      MagSocket.addBox(0F, 0F, 0F, 3, 3, 1);
      MagSocket.setRotationPoint(-2F, 2F, 2F);
      MagSocket.setTextureSize(128, 64);
      MagSocket.mirror = true;
      setRotation(MagSocket, 0F, 0F, 0F);
      Shape1 = new ModelRenderer(this, 0, 0);
      Shape1.addBox(0F, 0F, 0F, 7, 7, 28);
      Shape1.setRotationPoint(-4F, -8F, -16F);
      Shape1.setTextureSize(128, 64);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Drill8 = new ModelRenderer(this, 120, 39);
      Drill8.addBox(-1F, -1F, 0F, 2, 2, 2);
      Drill8.setRotationPoint(-0.5F, -5F, -32F);
      Drill8.setTextureSize(128, 64);
      Drill8.mirror = true;
      setRotation(Drill8, 0F, 0F, 0F);
      Drill9 = new ModelRenderer(this, 100, 38);
      Drill9.addBox(-1.5F, -1.5F, 0F, 3, 3, 2);
      Drill9.setRotationPoint(-0.5F, -5F, -34F);
      Drill9.setTextureSize(128, 64);
      Drill9.mirror = true;
      setRotation(Drill9, 0F, 0F, 0.7853982F);
      Drill1 = new ModelRenderer(this, 78, 51);
      Drill1.addBox(-5.5F, -5.5F, 0F, 11, 11, 2);
      Drill1.setRotationPoint(-0.5F, -5F, -18F);
      Drill1.setTextureSize(128, 64);
      Drill1.mirror = true;
      setRotation(Drill1, 0F, 0F, 0.7853982F);
      Drill2 = new ModelRenderer(this, 110, 55);
      Drill2.addBox(-3.5F, -3.5F, 0F, 7, 7, 2);
      Drill2.setRotationPoint(-0.5F, -5F, -20F);
      Drill2.setTextureSize(128, 64);
      Drill2.mirror = true;
      setRotation(Drill2, 0F, 0F, 0F);
      Drill3 = new ModelRenderer(this, 78, 40);
      Drill3.addBox(-4.5F, -4.5F, 0F, 9, 9, 2);
      Drill3.setRotationPoint(-0.5F, -5F, -22F);
      Drill3.setTextureSize(128, 64);
      Drill3.mirror = true;
      setRotation(Drill3, 0F, 0F, 0.7853982F);
      Drill4 = new ModelRenderer(this, 114, 48);
      Drill4.addBox(-2.5F, -2.5F, 0F, 5, 5, 2);
      Drill4.setRotationPoint(-0.5F, -5F, -24F);
      Drill4.setTextureSize(128, 64);
      Drill4.mirror = true;
      setRotation(Drill4, 0F, 0F, 0F);
      Drill5 = new ModelRenderer(this, 78, 31);
      Drill5.addBox(-3.5F, -3.5F, 0F, 7, 7, 2);
      Drill5.setRotationPoint(-0.5F, -5F, -26F);
      Drill5.setTextureSize(128, 64);
      Drill5.mirror = true;
      setRotation(Drill5, 0F, 0F, 0.7853982F);
      Drill10 = new ModelRenderer(this, 122, 36);
      Drill10.addBox(-0.5F, -0.5F, 0F, 1, 1, 2);
      Drill10.setRotationPoint(-0.5F, -5F, -36F);
      Drill10.setTextureSize(128, 64);
      Drill10.mirror = true;
      setRotation(Drill10, 0F, 0F, 0F);
      Drill7 = new ModelRenderer(this, 96, 31);
      Drill7.addBox(-2.5F, -2.5F, 0F, 5, 5, 2);
      Drill7.setRotationPoint(-0.5F, -5F, -30F);
      Drill7.setTextureSize(128, 64);
      Drill7.mirror = true;
      setRotation(Drill7, 0F, 0F, 0.7853982F);
      Drill6 = new ModelRenderer(this, 118, 43);
      Drill6.addBox(-1.5F, -1.5F, 0F, 3, 3, 2);
      Drill6.setRotationPoint(-0.5F, -5F, -28F);
      Drill6.setTextureSize(128, 64);
      Drill6.mirror = true;
      setRotation(Drill6, 0F, 0F, 0F);
  }
  
  @Override
  public void render(Entity entity, float f1, float f2, float f3, float f4,
  		float f5, float f6, int ammoLeft, float reloadProgress,
  		ItemRenderType renderType, int part, float fireProgress) {
    //super.render(entity, f1, f2, f3, f4, f5, f6);
    setRotationAngles(f1, f2, f3, f4, f5, f6, entity);
    //System.out.println("PART:"+part);
    if(part==0){
	    Grip1.render(f6);
	    Trigger01.render(f6);
	    Trigger02.render(f6);
	    Grip02.render(f6);
	    Receiver03.render(f6);
	    Grip01.render(f6);
	    MagSocket.render(f6);
	    Shape1.render(f6);
    } else {
	    Drill8.render(f6);
	    Drill9.render(f6);
	    Drill1.render(f6);
	    Drill2.render(f6);
	    Drill3.render(f6);
	    Drill4.render(f6);
	    Drill5.render(f6);
	    Drill10.render(f6);
	    Drill7.render(f6);
	    Drill6.render(f6);
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
