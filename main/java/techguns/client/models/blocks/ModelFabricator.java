package techguns.client.models.blocks;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFabricator extends ModelMachine {

	  public ModelRenderer Glass;
	    public ModelRenderer basePlate;
	    public ModelRenderer Corner1;
	    public ModelRenderer Corner2;
	    public ModelRenderer Corner3;
	    public ModelRenderer Corner4;
	    public ModelRenderer SidePlate1;
	    public ModelRenderer SidePlate2;
	    public ModelRenderer SidePlate3;
	    public ModelRenderer SidePlate4;
	    public ModelRenderer Controller;
	    public ModelRenderer Connection1;
	    public ModelRenderer Connection2;
	    public ModelRenderer Connection3;
	    public ModelRenderer Connection4;
	    public ModelRenderer Connection5;
	    public ModelRenderer Connection6;
	    public ModelRenderer Connection7;
	    public ModelRenderer SideBarTop1;
	    public ModelRenderer SideBarTop2;
	    public ModelRenderer SideBarTop3;
	    public ModelRenderer SideBarTop4;
	    public ModelRenderer innerPlate;
	    public ModelRenderer MidPlate;
	    public ModelRenderer Frame1;
	    public ModelRenderer Frame2;
	    public ModelRenderer Frame3;
	    public ModelRenderer Frame4;
	    public ModelRenderer FrameTop1;
	    public ModelRenderer FrameTop2;
	    public ModelRenderer FrameMove1;
	    public ModelRenderer WorkingHead;
	    public ModelRenderer WorkingDrill1;
	    public ModelRenderer WorkingDrill2;
	    public ModelRenderer WorkingLaser1;
	    public ModelRenderer WorkingLaser2;
	    public ModelRenderer WorkingTube1;
	    public ModelRenderer WorkingTube2;
	    public ModelRenderer WorkingLaserBeam;

	    public ModelFabricator() {
	        this.textureWidth = 128;
	        this.textureHeight = 128;
	        this.WorkingLaser1 = new ModelRenderer(this, 14, 102);
	        this.WorkingLaser1.setRotationPoint(-6.7F, 2.5F, 6.5F);
	        this.WorkingLaser1.addBox(-0.5F, 0.0F, -0.5F, 1, 7, 1, 0.0F);
	        this.setRotateAngle(WorkingLaser1, 0.0F, 0.7853981633974483F, 0.0F);
	        this.Corner3 = new ModelRenderer(this, 0, 0);
	        this.Corner3.setRotationPoint(-24.0F, 8.0F, 21.0F);
	        this.Corner3.addBox(0.0F, 0.0F, 0.0F, 3, 14, 3, 0.0F);
	        this.Connection2 = new ModelRenderer(this, 0, 21);
	        this.Connection2.setRotationPoint(-24.0F, 16.0F, 0.0F);
	        this.Connection2.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 1, 0.0F);
	        this.setRotateAngle(Connection2, 0.0F, 1.5707963267948966F, 0.0F);
	        this.Corner4 = new ModelRenderer(this, 0, 0);
	        this.Corner4.setRotationPoint(5.0F, 8.0F, 21.0F);
	        this.Corner4.addBox(0.0F, 0.0F, 0.0F, 3, 14, 3, 0.0F);
	        this.Glass = new ModelRenderer(this, 8, 86);
	        this.Glass.setRotationPoint(-8.0F, -6.0F, 8.0F);
	        this.Glass.addBox(-15.0F, 0.0F, -15.0F, 30, 12, 30, 0.0F);
	        this.Corner1 = new ModelRenderer(this, 0, 0);
	        this.Corner1.setRotationPoint(5.0F, 8.0F, -8.0F);
	        this.Corner1.addBox(0.0F, 0.0F, 0.0F, 3, 14, 3, 0.0F);
	        this.Connection7 = new ModelRenderer(this, 0, 21);
	        this.Connection7.setRotationPoint(8.0F, 16.0F, 16.0F);
	        this.Connection7.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 1, 0.0F);
	        this.setRotateAngle(Connection7, 0.0F, -1.5707963267948966F, 0.0F);
	        this.FrameMove1 = new ModelRenderer(this, 0, 71);
	        this.FrameMove1.setRotationPoint(-8.0F, -5.0F, 8.0F);
	        this.FrameMove1.addBox(-10.0F, 0.0F, -2.0F, 20, 2, 4, 0.0F);
	        this.Frame4 = new ModelRenderer(this, 18, 0);
	        this.Frame4.setRotationPoint(3.0F, -5.0F, 18.0F);
	        this.Frame4.addBox(-1.0F, 0.0F, -2.0F, 2, 16, 4, 0.0F);
	        this.setRotateAngle(Frame4, 0.0F, 3.141592653589793F, 0.0F);
	        this.Frame2 = new ModelRenderer(this, 18, 0);
	        this.Frame2.setRotationPoint(-19.0F, -5.0F, 18.0F);
	        this.Frame2.addBox(-1.0F, 0.0F, -2.0F, 2, 16, 4, 0.0F);
	        this.SidePlate2 = new ModelRenderer(this, 0, 35);
	        this.SidePlate2.setRotationPoint(-8.0F, 8.0F, 23.0F);
	        this.SidePlate2.addBox(-13.0F, 0.0F, 0.0F, 26, 14, 3, 0.0F);
	        this.setRotateAngle(SidePlate2, 0.0F, 3.141592653589793F, 0.0F);
	        this.SidePlate1 = new ModelRenderer(this, 0, 35);
	        this.SidePlate1.setRotationPoint(-8.0F, 8.0F, -7.0F);
	        this.SidePlate1.addBox(-13.0F, 0.0F, 0.0F, 26, 14, 3, 0.0F);
	        this.Connection3 = new ModelRenderer(this, 0, 21);
	        this.Connection3.setRotationPoint(-24.0F, 16.0F, 16.0F);
	        this.Connection3.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 1, 0.0F);
	        this.setRotateAngle(Connection3, 0.0F, 1.5707963267948966F, 0.0F);
	        this.SideBarTop4 = new ModelRenderer(this, 0, 62);
	        this.SideBarTop4.setRotationPoint(8.0F, 6.0F, 8.0F);
	        this.SideBarTop4.addBox(-12.0F, 0.0F, 0.0F, 24, 2, 4, 0.0F);
	        this.setRotateAngle(SideBarTop4, 0.0F, -1.5707963267948966F, 0.0F);
	        this.WorkingLaser2 = new ModelRenderer(this, 0, 102);
	        this.WorkingLaser2.setRotationPoint(-6.7F, -2.5F, 6.5F);
	        this.WorkingLaser2.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
	        this.setRotateAngle(WorkingLaser2, 0.0F, 0.7853981633974483F, 0.0F);
	        this.SideBarTop1 = new ModelRenderer(this, 0, 53);
	        this.SideBarTop1.setRotationPoint(-8.0F, 6.0F, -8.0F);
	        this.SideBarTop1.addBox(-16.0F, 0.0F, 0.0F, 32, 2, 4, 0.0F);
	        this.SideBarTop2 = new ModelRenderer(this, 0, 53);
	        this.SideBarTop2.setRotationPoint(-8.0F, 6.0F, 24.0F);
	        this.SideBarTop2.addBox(-16.0F, 0.0F, 0.0F, 32, 2, 4, 0.0F);
	        this.setRotateAngle(SideBarTop2, 0.0F, 3.141592653589793F, 0.0F);
	        this.SidePlate3 = new ModelRenderer(this, 0, 35);
	        this.SidePlate3.setRotationPoint(-23.0F, 8.0F, 8.0F);
	        this.SidePlate3.addBox(-13.0F, 0.0F, 0.0F, 26, 14, 3, 0.0F);
	        this.setRotateAngle(SidePlate3, 0.0F, 1.5707963267948966F, 0.0F);
	        this.FrameTop2 = new ModelRenderer(this, 92, 35);
	        this.FrameTop2.setRotationPoint(3.0F, -4.0F, 8.0F);
	        this.FrameTop2.addBox(-1.0F, -1.0F, -8.0F, 2, 2, 16, 0.0F);
	        this.setRotateAngle(FrameTop2, 0.0F, 3.141592653589793F, 0.0F);
	        this.WorkingTube1 = new ModelRenderer(this, 9, 77);
	        this.WorkingTube1.setRotationPoint(-6.7F, -2.5F, 9.5F);
	        this.WorkingTube1.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
	        this.setRotateAngle(WorkingTube1, 0.0F, 0.7853981633974483F, 0.0F);
	        this.SidePlate4 = new ModelRenderer(this, 0, 35);
	        this.SidePlate4.setRotationPoint(7.0F, 8.0F, 8.0F);
	        this.SidePlate4.addBox(-13.0F, 0.0F, 0.0F, 26, 14, 3, 0.0F);
	        this.setRotateAngle(SidePlate4, 0.0F, -1.5707963267948966F, 0.0F);
	        this.SideBarTop3 = new ModelRenderer(this, 0, 62);
	        this.SideBarTop3.setRotationPoint(-24.0F, 6.0F, 8.0F);
	        this.SideBarTop3.addBox(-12.0F, 0.0F, 0.0F, 24, 2, 4, 0.0F);
	        this.setRotateAngle(SideBarTop3, 0.0F, 1.5707963267948966F, 0.0F);
	        this.Connection1 = new ModelRenderer(this, 0, 21);
	        this.Connection1.setRotationPoint(-16.0F, 16.0F, -8.0F);
	        this.Connection1.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 1, 0.0F);
	        this.WorkingDrill2 = new ModelRenderer(this, 0, 85);
	        this.WorkingDrill2.setRotationPoint(-9.3F, -3.5F, 8.0F);
	        this.WorkingDrill2.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
	        this.setRotateAngle(WorkingDrill2, 0.0F, 0.7853981633974483F, 0.0F);
	        this.WorkingTube2 = new ModelRenderer(this, 18, 78);
	        this.WorkingTube2.setRotationPoint(-6.7F, -3.5F, 9.5F);
	        this.WorkingTube2.addBox(-0.5F, 0.0F, -0.5F, 1, 9, 1, 0.0F);
	        this.setRotateAngle(WorkingTube2, 0.0F, 0.7853981633974483F, 0.0F);
	        this.WorkingDrill1 = new ModelRenderer(this, 0, 77);
	        this.WorkingDrill1.setRotationPoint(-9.3F, -2.5F, 8.0F);
	        this.WorkingDrill1.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
	        this.setRotateAngle(WorkingDrill1, 0.0F, 0.7853981633974483F, 0.0F);
	        this.innerPlate = new ModelRenderer(this, 32, 59);
	        this.innerPlate.setRotationPoint(-8.0F, 11.0F, 8.0F);
	        this.innerPlate.addBox(-12.0F, 0.0F, -12.0F, 24, 2, 24, 0.0F);
	        this.FrameTop1 = new ModelRenderer(this, 92, 35);
	        this.FrameTop1.setRotationPoint(-19.0F, -4.0F, 8.0F);
	        this.FrameTop1.addBox(-1.0F, -1.0F, -8.0F, 2, 2, 16, 0.0F);
	        this.Connection5 = new ModelRenderer(this, 0, 21);
	        this.Connection5.setRotationPoint(0.0F, 16.0F, 24.0F);
	        this.Connection5.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 1, 0.0F);
	        this.setRotateAngle(Connection5, 0.0F, 3.141592653589793F, 0.0F);
	        this.Connection6 = new ModelRenderer(this, 0, 21);
	        this.Connection6.setRotationPoint(8.0F, 17.0F, 0.0F);
	        this.Connection6.addBox(-4.0F, -5.0F, 0.0F, 8, 8, 1, 0.0F);
	        this.setRotateAngle(Connection6, 0.0F, -1.5707963267948966F, 0.0F);
	        this.WorkingLaserBeam = new ModelRenderer(this, 9, 102);
	        this.WorkingLaserBeam.setRotationPoint(-6.7F, 0.5F, 6.5F);
	        this.WorkingLaserBeam.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
	        this.setRotateAngle(WorkingLaserBeam, 0.0F, 0.7853981633974483F, 0.0F);
	        this.WorkingHead = new ModelRenderer(this, 0, 94);
	        this.WorkingHead.setRotationPoint(-8.0F, -4.5F, 8.0F);
	        this.WorkingHead.addBox(-3.0F, 0.0F, -3.0F, 6, 2, 6, 0.0F);
	        this.basePlate = new ModelRenderer(this, 0, 0);
	        this.basePlate.setRotationPoint(-8.0F, 22.0F, 8.0F);
	        this.basePlate.addBox(-16.0F, 0.0F, -16.0F, 32, 2, 32, 0.0F);
	        this.Connection4 = new ModelRenderer(this, 0, 21);
	        this.Connection4.setRotationPoint(-16.0F, 16.0F, 24.0F);
	        this.Connection4.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 1, 0.0F);
	        this.setRotateAngle(Connection4, 0.0F, 3.141592653589793F, 0.0F);
	        this.Frame3 = new ModelRenderer(this, 18, 0);
	        this.Frame3.setRotationPoint(3.0F, -5.0F, -2.0F);
	        this.Frame3.addBox(-1.0F, 0.0F, -2.0F, 2, 16, 4, 0.0F);
	        this.setRotateAngle(Frame3, 0.0F, 3.141592653589793F, 0.0F);
	        this.Controller = new ModelRenderer(this, 103, 0);
	        this.Controller.setRotationPoint(-4.0F, 12.0F, -7.0F);
	        this.Controller.addBox(0.0F, 0.0F, 0.0F, 8, 8, 3, 0.0F);
	        this.setRotateAngle(Controller, -0.1308996938995747F, 0.0F, 0.0F);
	        this.MidPlate = new ModelRenderer(this, 58, 36);
	        this.MidPlate.setRotationPoint(-8.0F, 10.0F, 8.0F);
	        this.MidPlate.addBox(-6.0F, 0.0F, -6.0F, 12, 1, 12, 0.0F);
	        this.Corner2 = new ModelRenderer(this, 0, 0);
	        this.Corner2.setRotationPoint(-24.0F, 8.0F, -8.0F);
	        this.Corner2.addBox(0.0F, 0.0F, 0.0F, 3, 14, 3, 0.0F);
	        this.Frame1 = new ModelRenderer(this, 18, 0);
	        this.Frame1.setRotationPoint(-19.0F, -5.0F, -2.0F);
	        this.Frame1.addBox(-1.0F, 0.0F, -2.0F, 2, 16, 4, 0.0F);
	    }


		@Override
		public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, float progress) {
			this.render(entity, f, f1, f2, f3, f4, f5, 0);
		}
	    
		public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int part) {
			if(part==0){
		        this.innerPlate.render(f5);
		        this.FrameTop1.render(f5);
		        this.Connection7.render(f5);
		        this.Controller.render(f5);
		        this.basePlate.render(f5);
		        this.Frame2.render(f5);
		        this.Frame4.render(f5);
		        this.Connection1.render(f5);
		        this.FrameTop2.render(f5);
		        this.SideBarTop1.render(f5);
		        this.Frame3.render(f5);
		        this.SidePlate4.render(f5);
		        this.Connection5.render(f5);
		        this.Corner3.render(f5);
		        this.SidePlate3.render(f5);
		        this.Corner4.render(f5);
		        this.Connection6.render(f5);
		        this.Corner1.render(f5);
		        this.Connection2.render(f5);
		        this.Connection3.render(f5);
		        this.Frame1.render(f5);
		        this.MidPlate.render(f5);
		        this.Connection4.render(f5);
		        this.SidePlate2.render(f5);
		        this.Corner2.render(f5);
		        this.SidePlate1.render(f5);
		        this.SideBarTop2.render(f5);
		        this.SideBarTop4.render(f5);
		        this.SideBarTop3.render(f5);
			} else if (part==1){
		        this.FrameMove1.render(f5);
			} else if(part==2){
		        this.WorkingHead.render(f5);
		        this.WorkingDrill1.render(f5);
		        this.WorkingTube1.render(f5);
		        this.WorkingLaser2.render(f5);
		        this.WorkingLaserBeam.render(f5);
		        
			} else if (part==3){   
		        this.WorkingDrill2.render(f5); 
			} else if (part==5){    
		        this.WorkingLaser1.render(f5);
			} else if (part==4){
		        this.WorkingTube2.render(f5);
			} else if (part==6){
	        
				this.Glass.render(f5);
			}
	    }

	    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
	        modelRenderer.rotateAngleX = x;
	        modelRenderer.rotateAngleY = y;
	        modelRenderer.rotateAngleZ = z;
	    }

}
