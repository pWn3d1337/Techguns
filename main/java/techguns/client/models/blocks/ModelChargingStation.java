package techguns.client.models.blocks;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelChargingStation extends ModelMachine {
    public ModelRenderer BottomPlate;
    public ModelRenderer TopPlate;
    public ModelRenderer Pillar2;
    public ModelRenderer Pillar3;
    public ModelRenderer Pillar1;
    public ModelRenderer Pillar4;
    public ModelRenderer Connector1;
    public ModelRenderer Connector2;
    public ModelRenderer SidePlate3;
    public ModelRenderer SidePlate2;
    public ModelRenderer SidePlate1;
    public ModelRenderer Connector3;
    public ModelRenderer TopPlateInner;
    public ModelRenderer TopPlateInner2;
    public ModelRenderer BottomPlateInner;
    public ModelRenderer BottomPlateInner2;
    public ModelRenderer Power2;
    public ModelRenderer Power1;
    public ModelRenderer Power3;
    public ModelRenderer PowerS2;
    public ModelRenderer PowerS1;
    public ModelRenderer PowerS3;
    public ModelRenderer Glass;

    public ModelChargingStation() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.SidePlate3 = new ModelRenderer(this, 38, 19);
        this.SidePlate3.setRotationPoint(0.0F, 16.0F, 7.5F);
        this.SidePlate3.addBox(0.0F, -6.0F, -6.0F, 1, 12, 12, 0.0F);
        this.setRotateAngle(SidePlate3, 0.0F, 1.5707963267948966F, 0.0F);
        this.Connector3 = new ModelRenderer(this, 48, 49);
        this.Connector3.setRotationPoint(0.0F, 16.0F, 8.0F);
        this.Connector3.addBox(0.0F, -3.0F, -3.0F, 2, 6, 6, 0.0F);
        this.setRotateAngle(Connector3, 0.0F, 1.5707963267948966F, 0.0F);
        this.TopPlate = new ModelRenderer(this, 0, 46);
        this.TopPlate.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.TopPlate.addBox(-8.0F, 0.0F, -8.0F, 16, 2, 16, 0.0F);
        this.setRotateAngle(TopPlate, 0.0F, 0.0F, 3.141592653589793F);
        this.Pillar1 = new ModelRenderer(this, 0, 47);
        this.Pillar1.setRotationPoint(-8.0F, 10.0F, -8.0F);
        this.Pillar1.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2, 0.0F);
        this.Connector2 = new ModelRenderer(this, 48, 49);
        this.Connector2.setRotationPoint(-8.0F, 16.0F, 0.0F);
        this.Connector2.addBox(0.0F, -3.0F, -3.0F, 2, 6, 6, 0.0F);
        this.BottomPlateInner2 = new ModelRenderer(this, 0, 24);
        this.BottomPlateInner2.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.BottomPlateInner2.addBox(-4.0F, 0.0F, -4.0F, 8, 1, 8, 0.0F);
        this.Pillar3 = new ModelRenderer(this, 0, 47);
        this.Pillar3.setRotationPoint(6.0F, 10.0F, -8.0F);
        this.Pillar3.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2, 0.0F);
        this.SidePlate2 = new ModelRenderer(this, 38, 19);
        this.SidePlate2.setRotationPoint(-7.5F, 16.0F, 0.0F);
        this.SidePlate2.addBox(0.0F, -6.0F, -6.0F, 1, 12, 12, 0.0F);
        this.PowerS1 = new ModelRenderer(this, 0, 27);
        this.PowerS1.setRotationPoint(5.0F, 16.0F, 0.0F);
        this.PowerS1.addBox(0.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(PowerS1, 0.0F, 3.141592653589793F, 0.0F);
        this.PowerS3 = new ModelRenderer(this, 0, 27);
        this.PowerS3.setRotationPoint(0.0F, 16.0F, 5.0F);
        this.PowerS3.addBox(0.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(PowerS3, 0.0F, 1.5707963267948966F, 0.0F);
        this.Power3 = new ModelRenderer(this, 0, 35);
        this.Power3.setRotationPoint(0.0F, 16.0F, 6.0F);
        this.Power3.addBox(0.0F, -2.0F, -2.0F, 1, 4, 4, 0.0F);
        this.setRotateAngle(Power3, 0.0F, 1.5707963267948966F, 0.0F);
        this.Glass = new ModelRenderer(this, 40, -12);
        this.Glass.mirror = true;
        this.Glass.setRotationPoint(0.0F, 16.0F, -7.0F);
        this.Glass.addBox(0.0F, -6.0F, -6.0F, 0, 12, 12, 0.0F);
        this.setRotateAngle(Glass, 0.0F, 1.5707963267948966F, 0.0F);
        this.SidePlate1 = new ModelRenderer(this, 38, 19);
        this.SidePlate1.setRotationPoint(7.5F, 16.0F, 0.0F);
        this.SidePlate1.addBox(0.0F, -6.0F, -6.0F, 1, 12, 12, 0.0F);
        this.setRotateAngle(SidePlate1, 0.0F, 3.141592653589793F, 0.0F);
        this.Power1 = new ModelRenderer(this, 0, 35);
        this.Power1.setRotationPoint(6.0F, 16.0F, 0.0F);
        this.Power1.addBox(0.0F, -2.0F, -2.0F, 1, 4, 4, 0.0F);
        this.setRotateAngle(Power1, 0.0F, 3.141592653589793F, 0.0F);
        this.Pillar4 = new ModelRenderer(this, 0, 47);
        this.Pillar4.setRotationPoint(6.0F, 10.0F, 6.0F);
        this.Pillar4.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2, 0.0F);
        this.PowerS2 = new ModelRenderer(this, 0, 27);
        this.PowerS2.setRotationPoint(-5.0F, 16.0F, 0.0F);
        this.PowerS2.addBox(0.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
        this.TopPlateInner = new ModelRenderer(this, 0, 34);
        this.TopPlateInner.setRotationPoint(0.0F, 11.0F, 0.0F);
        this.TopPlateInner.addBox(-5.0F, 0.0F, -5.0F, 10, 1, 10, 0.0F);
        this.setRotateAngle(TopPlateInner, 3.141592653589793F, 0.0F, 0.0F);
        this.Power2 = new ModelRenderer(this, 0, 35);
        this.Power2.setRotationPoint(-6.0F, 16.0F, 0.0F);
        this.Power2.addBox(0.0F, -2.0F, -2.0F, 1, 4, 4, 0.0F);
        this.Connector1 = new ModelRenderer(this, 48, 49);
        this.Connector1.setRotationPoint(8.0F, 16.0F, 0.0F);
        this.Connector1.addBox(0.0F, -3.0F, -3.0F, 2, 6, 6, 0.0F);
        this.setRotateAngle(Connector1, 0.0F, 3.141592653589793F, 0.0F);
        this.BottomPlateInner = new ModelRenderer(this, 0, 34);
        this.BottomPlateInner.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.BottomPlateInner.addBox(-5.0F, 0.0F, -5.0F, 10, 1, 10, 0.0F);
        this.BottomPlate = new ModelRenderer(this, 0, 46);
        this.BottomPlate.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.BottomPlate.addBox(-8.0F, 0.0F, -8.0F, 16, 2, 16, 0.0F);
        this.Pillar2 = new ModelRenderer(this, 0, 47);
        this.Pillar2.setRotationPoint(-8.0F, 10.0F, 6.0F);
        this.Pillar2.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2, 0.0F);
        this.TopPlateInner2 = new ModelRenderer(this, 0, 24);
        this.TopPlateInner2.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.TopPlateInner2.addBox(-4.0F, 0.0F, -4.0F, 8, 1, 8, 0.0F);
        this.setRotateAngle(TopPlateInner2, 0.0F, 0.0F, 3.141592653589793F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, float progress) { 
        this.SidePlate3.render(f5);
        this.Connector3.render(f5);
        this.TopPlate.render(f5);
        this.Pillar1.render(f5);
        this.Connector2.render(f5);
        this.BottomPlateInner2.render(f5);
        this.Pillar3.render(f5);
        this.SidePlate2.render(f5);
        this.PowerS1.render(f5);
        this.PowerS3.render(f5);
        this.Power3.render(f5);
        this.SidePlate1.render(f5);
        this.Power1.render(f5);
        this.Pillar4.render(f5);
        this.PowerS2.render(f5);
        this.TopPlateInner.render(f5);
        this.Power2.render(f5);
        this.Connector1.render(f5);
        this.BottomPlateInner.render(f5);
        this.BottomPlate.render(f5);
        this.Pillar2.render(f5);
        this.TopPlateInner2.render(f5);
        
        //TGRenderHelper.enableAlphaBlend();
        //this.Glass.render(f5);
	    //TGRenderHelper.disableAlphaBlend();
    }

    protected void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
