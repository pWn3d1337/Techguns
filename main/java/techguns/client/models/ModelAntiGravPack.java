package techguns.client.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class ModelAntiGravPack extends ModelBipedMultipart {

	 public ModelRenderer BackBack;
	    public ModelRenderer RocketR;
	    public ModelRenderer box1;
	    public ModelRenderer Back2;
	    public ModelRenderer Back3;
	    public ModelRenderer RocketL;
	    public ModelRenderer box2;
	    public ModelRenderer box3;
	    public ModelRenderer box4;

	    public ModelAntiGravPack() {
	        this.textureWidth = 32;
	        this.textureHeight = 32;
	        
	        this.BackBack = new ModelRenderer(this, 16, 0);
	        this.BackBack.setRotationPoint(-1.0F, 1.0F, 2.0F);
	        this.BackBack.addBox(-2.0F, 0.0F, 0.0F, 6, 6, 3, 0.0F);
	        
	        this.RocketL = new ModelRenderer(this, 0, 0);
	        this.RocketL.setRotationPoint(6.0F, -0.5F, 0.5F);
	        this.RocketL.addBox(-2.0F, 0.0F, 0.0F, 2, 7, 2, 0.0F);
	        this.BackBack.addChild(RocketL);
	        
	        this.box1 = new ModelRenderer(this, 0, 10);
	        this.box1.setRotationPoint(5.0F, 3.5F, 2.5F);
	        this.box1.addBox(-2.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
	        this.BackBack.addChild(box1);
	        
	        
	        this.Back3 = new ModelRenderer(this, 6, 10);
	        this.Back3.setRotationPoint(0.0F, 4.0F, 6.0F);
	        this.Back3.addBox(-1.5F, -3.0F, -3.5F, 3, 3, 3, 0.0F);
	        this.setRotateAngle(Back3, 0.0F, 0.0F, 0.7853981633974483F);
	        this.BackBack.addChild(Back3);
	        
	        this.Back2 = new ModelRenderer(this, 20, 11);
	        this.Back2.setRotationPoint(-0.5F, 2.0F, 5.0F);
	        this.Back2.addBox(-1.0F, -1.0F, -2.0F, 5, 4, 1, 0.0F);
	        this.BackBack.addChild(Back2);
	        
	        this.box3 = new ModelRenderer(this, 0, 10);
	        this.box3.setRotationPoint(-2.0F, 4.5F, 5.5F);
	        this.box3.addBox(-1.0F, -1.0F, 2.0F, 2, 1, 1, 0.0F);
	        this.setRotateAngle(box3, 0.0F, 3.141592653589793F, 0.0F);
	        this.BackBack.addChild(box3);
	        
	        this.RocketR = new ModelRenderer(this, 0, 0);
	        this.RocketR.setRotationPoint(-2.0F, -0.5F, 0.5F);
	        this.RocketR.addBox(-2.0F, 0.0F, 0.0F, 2, 7, 2, 0.0F);
	        this.BackBack.addChild(RocketR);
	        
	        this.box4 = new ModelRenderer(this, 0, 10);
	        this.box4.setRotationPoint(-2.0F, 2.5F, 5.5F);
	        this.box4.addBox(-1.0F, -1.0F, 2.0F, 2, 1, 1, 0.0F);
	        this.setRotateAngle(box4, 0.0F, 3.141592653589793F, 0.0F);
	        this.BackBack.addChild(box4);
	        
	        this.box2 = new ModelRenderer(this, 0, 10);
	        this.box2.setRotationPoint(5.0F, 1.5F, 2.5F);
	        this.box2.addBox(-2.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
	        this.BackBack.addChild(box2);
	    }

		@Override
		public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float f6, int ammoLeft, float reloadProgress,
				ItemRenderType renderType, int part, float fireProgress) {
			
			this.setRotationAngles(f1, f2, f3, f4, f5, f6, entity);
			
	    /*    this.RocketL.render(f6);
	        this.box1.render(f6);
	        this.Back3.render(f6);
	        this.Back2.render(f6);
	        this.box3.render(f6);
	        this.RocketR.render(f6);*/
	        this.BackBack.render(f6);
	      /*  this.box4.render(f6);
	        this.box2.render(f6);*/
	    }

		
		 @Override
		  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5,Entity e)
		  {
		    super.setRotationAngles(f, f1, f2, f3, f4, f5,e);
		    this.BackBack.rotateAngleX = this.bipedBody.rotateAngleX;
		  }
		
	    private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
	        modelRenderer.rotateAngleX = x;
	        modelRenderer.rotateAngleY = y;
	        modelRenderer.rotateAngleZ = z;
	    }

}
