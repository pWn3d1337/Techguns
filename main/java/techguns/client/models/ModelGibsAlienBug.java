package techguns.client.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGibsAlienBug extends ModelGibs {

	public ModelRenderer JAW_L;
	public ModelRenderer h1;
	public ModelRenderer JAW_U;
	public ModelRenderer h2;
	public ModelRenderer b4;
	public ModelRenderer b2;
	public ModelRenderer b3;
	public ModelRenderer b1;
	public ModelRenderer L1_A;
	public ModelRenderer L1_B;

	public ModelGibsAlienBug() {
		this.textureWidth = 64;
		this.textureHeight = 64;

		this.b1 = new ModelRenderer(this, 0, 33);
		this.b1.addBox(0.0F, 0.0F, 0.0F, 5, 5, 9, 0.0F);
	
		this.b2 = new ModelRenderer(this, 23, 33);
		this.b2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.b2.addBox(0.0F, 0.0F, 0.0F, 6, 4, 4, 0.0F);
		this.b4 = new ModelRenderer(this, 56, 30);
		this.b4.setRotationPoint(2.0F, 2.0F, 8.5F);
		this.b4.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
		this.setRotateAngle(b4, -0.36425021489121656F, 0.0F, 0.0F);
		this.b3 = new ModelRenderer(this, 43, 31);
		this.b3.setRotationPoint(1.0F, 0.5F, 4.5F);
		this.b3.addBox(0.0F, 0.0F, 0.0F, 4, 3, 4, 0.0F);
		this.setRotateAngle(b3, -0.18203784098300857F, 0.0F, 0.0F);
		
		this.h1 = new ModelRenderer(this, 20, 9);
		//this.h1.setRotationPoint(0.0F, 11.5F, -1.0F);
		this.h1.addBox(-2.5F, -2.5F, -3.0F, 5, 5, 6, 0.0F);
		this.setRotateAngle(h1, 0.136659280431156F, 0.0F, 0.0F);
		
		this.h2 = new ModelRenderer(this, 36, 18);
		this.h2.addBox(0.0F, 0.0F, -7.0F, 6, 3, 8, 0.0F);
		
		this.JAW_U = new ModelRenderer(this, 2, 15);
		this.JAW_U.setRotationPoint(-2.0F, -1.0F, -2.0F);
		this.JAW_U.addBox(0.0F, -2.5F, -10.0F, 4, 4, 10, 0.0F);
		this.setRotateAngle(JAW_U, -0.18203784098300857F, 0.0F, 0.0F);

		this.JAW_L = new ModelRenderer(this, 21, 20);
		this.JAW_L.setRotationPoint(-1.5F, -1.5F, -2.5F);
		this.JAW_L.addBox(0.0F, 0.0F, -9.0F, 3, 4, 9, 0.0F);
		this.setRotateAngle(JAW_L, 0.4553564018453205F, 0.0F, 0.0F);
		
		this.L1_A = new ModelRenderer(this, 0, 4);
		//this.L1_A.setRotationPoint(-1.5F, 17.0F, 0.0F);
		this.L1_A.addBox(0.0F, -1.5F, -1.5F, 9, 3, 3, 0.0F);
		this.setRotateAngle(L1_A, 0.0F, 2.792526803190927F, 0.5235987755982988F);

		this.L1_B = new ModelRenderer(this, 0, 0);
		this.L1_B.setRotationPoint(7.5F, 0.0F, 0.0F);
		this.L1_B.addBox(0.0F, -1.0F, -1.0F, 11, 2, 2, 0.0F);
		this.setRotateAngle(L1_B, 0.0F, 0.0F, 1.7453292519943295F);

		this.h1.addChild(this.JAW_U);
		this.h1.addChild(this.JAW_L);
		this.L1_A.addChild(this.L1_B);
        this.b2.addChild(this.b4);
        this.b2.addChild(this.b3);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5, int part) {		
		if (part == 0)
			this.h1.render(f5);
		if (part == 1)
			this.h2.render(f5);
		if (part >= 2 && part <= 5)
			this.L1_A.render(f5);
		if (part == 6)
			this.b1.render(f5);
		if (part == 7) {
			this.b2.render(f5);
		}
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y,
			float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public int getNumGibs() {
		return 8;
	}

}
