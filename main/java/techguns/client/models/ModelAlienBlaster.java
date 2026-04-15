package techguns.client.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class ModelAlienBlaster extends ModelMultipart {
	  //fields
    ModelRenderer Grip1;
    ModelRenderer Trigger02;
    ModelRenderer Muzzle;
    ModelRenderer Grip2;
    ModelRenderer Light06;
    ModelRenderer Barrel;
    ModelRenderer Wing03;
    ModelRenderer Muzzle2;
    ModelRenderer Gun03;
    ModelRenderer Gun05;
    ModelRenderer Muzzle3;
    ModelRenderer Gun04;
    ModelRenderer Gun02;
    ModelRenderer Gun06;
    ModelRenderer Cell02;
    ModelRenderer Cell01;
    ModelRenderer Wing01;
    ModelRenderer Wing02;
    ModelRenderer Gun01;
    ModelRenderer Light01;
    ModelRenderer Light02;
    ModelRenderer Light03;
    ModelRenderer Light04;
    ModelRenderer Light05;
  
  public ModelAlienBlaster()
  {
    textureWidth = 128;
    textureHeight = 64;
    
      Grip1 = new ModelRenderer(this, 68, 0);
      Grip1.addBox(0F, 0F, 0F, 3, 9, 4);
      Grip1.setRotationPoint(-2F, 2F, 6F);
      Grip1.setTextureSize(128, 64);
      Grip1.mirror = true;
      setRotation(Grip1, 0.4461433F, 0F, 0F);
      Trigger02 = new ModelRenderer(this, 83, 9);
      Trigger02.addBox(0F, 0F, 0F, 1, 3, 2);
      Trigger02.setRotationPoint(-1F, 0.5F, 4F);
      Trigger02.setTextureSize(128, 64);
      Trigger02.mirror = true;
      setRotation(Trigger02, 0.2617994F, 0F, 0F);
      Muzzle = new ModelRenderer(this, 50, 11);
      Muzzle.addBox(-3F, -3F, 0F, 2, 2, 2);
      Muzzle.setRotationPoint(-0.5F, -0.6F, -18F);
      Muzzle.setTextureSize(128, 64);
      Muzzle.mirror = true;
      setRotation(Muzzle, 0F, 0F, 0.7853982F);
      Grip2 = new ModelRenderer(this, 83, 0);
      Grip2.addBox(0F, 0F, 0F, 3, 3, 5);
      Grip2.setRotationPoint(-2F, -0.5F, 5F);
      Grip2.setTextureSize(128, 64);
      Grip2.mirror = true;
      setRotation(Grip2, 0F, 0F, 0F);
      Light06 = new ModelRenderer(this, 0, 0);
      Light06.addBox(0F, 0F, 0F, 1, 1, 1);
      Light06.setRotationPoint(1.5F, -6.5F, 6F);
      Light06.setTextureSize(128, 64);
      Light06.mirror = true;
      setRotation(Light06, 0F, 0F, 0.7853982F);
      Barrel = new ModelRenderer(this, 47, 4);
      Barrel.addBox(0F, 0F, 0F, 1, 1, 12);
      Barrel.setRotationPoint(-0.5F, -4F, -17F);
      Barrel.setTextureSize(128, 64);
      Barrel.mirror = true;
      setRotation(Barrel, 0F, 0F, 0.7853982F);
      Wing03 = new ModelRenderer(this, 40, 37);
      Wing03.addBox(0F, 0F, 0F, 1, 3, 7);
      Wing03.setRotationPoint(-1F, -7F, 4F);
      Wing03.setTextureSize(128, 64);
      Wing03.mirror = true;
      setRotation(Wing03, 0.4363323F, 0F, 0F);
      Muzzle2 = new ModelRenderer(this, 50, 6);
      Muzzle2.addBox(-3F, -3F, 0F, 3, 3, 1);
      Muzzle2.setRotationPoint(-0.5F, -1.3F, -15F);
      Muzzle2.setTextureSize(128, 64);
      Muzzle2.mirror = true;
      setRotation(Muzzle2, 0F, 0F, 0.7853982F);
      Gun03 = new ModelRenderer(this, 0, 27);
      Gun03.addBox(-3F, -3F, 0F, 4, 4, 2);
      Gun03.setRotationPoint(-0.5F, -2.1F, -3F);
      Gun03.setTextureSize(128, 64);
      Gun03.mirror = true;
      setRotation(Gun03, 0F, 0F, 0.7853982F);
      Gun05 = new ModelRenderer(this, 24, 19);
      Gun05.addBox(-3F, -3F, 0F, 2, 2, 1);
      Gun05.setRotationPoint(-0.5F, -0.6F, -5F);
      Gun05.setTextureSize(128, 64);
      Gun05.mirror = true;
      setRotation(Gun05, 0F, 0F, 0.7853982F);
      Muzzle3 = new ModelRenderer(this, 50, 1);
      Muzzle3.addBox(-3F, -3F, 0F, 3, 3, 1);
      Muzzle3.setRotationPoint(-0.5F, -1.3F, -13F);
      Muzzle3.setTextureSize(128, 64);
      Muzzle3.mirror = true;
      setRotation(Muzzle3, 0F, 0F, 0.7853982F);
      Gun04 = new ModelRenderer(this, 15, 19);
      Gun04.addBox(-3F, -3F, 0F, 3, 3, 1);
      Gun04.setRotationPoint(-0.5F, -1.3F, -4F);
      Gun04.setTextureSize(128, 64);
      Gun04.mirror = true;
      setRotation(Gun04, 0F, 0F, 0.7853982F);
      Gun02 = new ModelRenderer(this, 0, 19);
      Gun02.addBox(-3F, -3F, 0F, 5, 5, 2);
      Gun02.setRotationPoint(-0.5F, -2.8F, -1F);
      Gun02.setTextureSize(128, 64);
      Gun02.mirror = true;
      setRotation(Gun02, 0F, 0F, 0.7853982F);
      Gun06 = new ModelRenderer(this, 25, 0);
      Gun06.addBox(-3F, -3F, 0F, 5, 5, 2);
      Gun06.setRotationPoint(-0.5F, -2.8F, 13F);
      Gun06.setTextureSize(128, 64);
      Gun06.mirror = true;
      setRotation(Gun06, 0F, 0F, 0.7853982F);
      Cell02 = new ModelRenderer(this, 40, 1);
      Cell02.addBox(-3F, -3F, 0F, 2, 2, 2);
      Cell02.setRotationPoint(-0.5F, -0.7F, 15F);
      Cell02.setTextureSize(128, 64);
      Cell02.mirror = true;
      setRotation(Cell02, 0F, 0F, 0.7853982F);
      Cell01 = new ModelRenderer(this, 40, 6);
      Cell01.addBox(0F, 0F, 0F, 1, 1, 1);
      Cell01.setRotationPoint(-1F, -4F, 17F);
      Cell01.setTextureSize(128, 64);
      Cell01.mirror = true;
      setRotation(Cell01, 0F, 0F, 0F);
      Wing01 = new ModelRenderer(this, 40, 28);
      Wing01.addBox(0F, 0F, 0F, 3, 1, 7);
      Wing01.setRotationPoint(-4F, -4F, 4F);
      Wing01.setTextureSize(128, 64);
      Wing01.mirror = true;
      setRotation(Wing01, 0F, -0.4363323F, 0F);
      Wing02 = new ModelRenderer(this, 40, 19);
      Wing02.addBox(-3F, 0F, 0F, 3, 1, 7);
      Wing02.setRotationPoint(3F, -4F, 4F);
      Wing02.setTextureSize(128, 64);
      Wing02.mirror = true;
      setRotation(Wing02, 0F, 0.4363323F, 0F);
      Gun01 = new ModelRenderer(this, 0, 0);
      Gun01.addBox(-3F, -3F, 0F, 6, 6, 12);
      Gun01.setRotationPoint(-0.5F, -3.5F, 1F);
      Gun01.setTextureSize(128, 64);
      Gun01.mirror = true;
      setRotation(Gun01, 0F, 0F, 0.7853982F);
      Light01 = new ModelRenderer(this, 0, 0);
      Light01.addBox(0F, 0F, 0F, 1, 1, 1);
      Light01.setRotationPoint(1.5F, -6.5F, 4F);
      Light01.setTextureSize(128, 64);
      Light01.mirror = true;
      setRotation(Light01, 0F, 0F, 0.7853982F);
      Light02 = new ModelRenderer(this, 0, 0);
      Light02.addBox(0F, 0F, 0F, 1, 1, 1);
      Light02.setRotationPoint(-2.5F, -6.5F, 4F);
      Light02.setTextureSize(128, 64);
      Light02.mirror = true;
      setRotation(Light02, 0F, 0F, 0.7853982F);
      Light03 = new ModelRenderer(this, 0, 0);
      Light03.addBox(0F, 0F, 0F, 1, 1, 1);
      Light03.setRotationPoint(1.5F, -6.5F, 8F);
      Light03.setTextureSize(128, 64);
      Light03.mirror = true;
      setRotation(Light03, 0F, 0F, 0.7853982F);
      Light04 = new ModelRenderer(this, 0, 0);
      Light04.addBox(0F, 0F, 0F, 1, 1, 1);
      Light04.setRotationPoint(-2.5F, -6.5F, 8F);
      Light04.setTextureSize(128, 64);
      Light04.mirror = true;
      setRotation(Light04, 0F, 0F, 0.7853982F);
      Light05 = new ModelRenderer(this, 0, 0);
      Light05.addBox(0F, 0F, 0F, 1, 1, 1);
      Light05.setRotationPoint(-2.5F, -6.5F, 6F);
      Light05.setTextureSize(128, 64);
      Light05.mirror = true;
      setRotation(Light05, 0F, 0F, 0.7853982F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5,int ammoLeft, float reloadProgress, ItemRenderType renderType, int part, float fireProgress)
  {
    setRotationAngles(f, f1, f2, f3, f4, f5,entity);
    Grip1.render(f5);
    Trigger02.render(f5);
    Muzzle.render(f5);
    Grip2.render(f5);
    Light06.render(f5);
    Barrel.render(f5);
    Wing03.render(f5);
    Muzzle2.render(f5);
    Gun03.render(f5);
    Gun05.render(f5);
    Muzzle3.render(f5);
    Gun04.render(f5);
    Gun02.render(f5);
    Gun06.render(f5);
    Cell02.render(f5);
    Cell01.render(f5);
    Wing01.render(f5);
    Wing02.render(f5);
    Gun01.render(f5);
    Light01.render(f5);
    Light02.render(f5);
    Light03.render(f5);
    Light04.render(f5);
    Light05.render(f5);
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
