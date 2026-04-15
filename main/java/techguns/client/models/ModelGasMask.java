package techguns.client.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGasMask extends ModelBiped {
	//fields
    ModelRenderer Head;
    ModelRenderer Front;
    ModelRenderer FilterMount;
    ModelRenderer Filter;
  
  public ModelGasMask()
  {
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
      
      Front = new ModelRenderer(this, 9, 17);
      Front.addBox(0F, 0F, 0F, 4, 3, 1);
      Front.setRotationPoint(-2F, -3F, -5F);
      Front.setTextureSize(32, 32);
      Front.mirror = true;
      setRotation(Front, 0F, 0F, 0F);
      this.bipedHead.addChild(Front);
      
      FilterMount = new ModelRenderer(this, 0, 17);
      FilterMount.addBox(0F, 0F, 0F, 3, 3, 1);
      FilterMount.setRotationPoint(-1.5F, -2.2F, -8F);
      FilterMount.setTextureSize(32, 32);
      FilterMount.mirror = true;
      setRotation(FilterMount, 0.3346075F, 0F, 0F);
      this.bipedHead.addChild(FilterMount);
      
      Filter = new ModelRenderer(this, 20, 17);
      Filter.addBox(-1F, -1F, 0F, 2, 2, 2);
      Filter.setRotationPoint(0F, -0.9F, -7F);
      Filter.setTextureSize(32, 32);
      Filter.mirror = true;
      setRotation(Filter, 0.3490659F, -0.2268928F, 0.7853982F);
      this.bipedHead.addChild(Filter);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    //super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5,entity);
    this.bipedHead.render(f5);
    /*Head.render(f5);
    Front.render(f5);
    FilterMount.render(f5);
    Filter.render(f5);*/
    
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
