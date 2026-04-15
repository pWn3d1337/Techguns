package techguns.client.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBeret extends ModelBiped {

	public ModelRenderer berettop;
    public ModelRenderer beretside;

    public ModelBeret() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        
        this.berettop = new ModelRenderer(this, 0, 0);
        this.berettop.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.berettop.addBox(-2.0F, -8.5F, -4.0F, 6, 2, 8, 0.6F);
        this.beretside = new ModelRenderer(this, 0, 11);
        this.beretside.setRotationPoint(-2.7F, -8.3F, -4.0F);
        this.beretside.addBox(-3.0F, 0.0F, 0.0F, 3, 2, 8, 0.55F);
        this.setRotateAngle(beretside, 0.0F, 0.0F, -0.6108652381980153F);
        
        this.bipedHead.addChild(berettop);
        this.bipedHead.addChild(beretside);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
       // this.berettop.render(f5);
       // this.beretside.render(f5);
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
	
}
