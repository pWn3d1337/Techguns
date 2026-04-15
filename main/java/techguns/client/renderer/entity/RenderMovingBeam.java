package techguns.client.renderer.entity;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import techguns.client.renderer.TGRenderHelper;
import techguns.entities.projectiles.MovingBeam;
import techguns.items.guns.Beamgun;
import techguns.util.MathUtil;
import techguns.util.MathUtil.Vec2;

public class RenderMovingBeam extends Render {
	private static final ResourceLocation LaserTextures = new ResourceLocation("techguns:textures/fx/nukebeam.png");

	private double laserWidth = 3.0D;
	private int maxTicks = Beamgun.BEAM_LIFETIME;

	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float ptt) {
		 MovingBeam laser = (MovingBeam)par1Entity;
		 Random rand = new Random(par1Entity.getEntityId()); //laser.seed);

        // Vec3 source = Vec3.createVectorHelper(laser.posX, laser.posY, laser.posZ);
         //Vec3 target = Vec3.createVectorHelper(laser.targetX, laser.targetY, laser.targetZ);    	 		 
         //Vec3 viewer = Vec3.createVectorHelper(this.renderManager.viewerPosX, this.renderManager.viewerPosY, this.renderManager.viewerPosZ);
         
		 double distance = laser.beamlength;//source.distanceTo(target);
		 float prog = ((float)par1Entity.ticksExisted+ptt)/((float)maxTicks);
		 double width = laserWidth * (Math.sin(Math.sqrt(prog)*Math.PI))*2;

    	 this.bindEntityTexture(par1Entity);
         
    	 GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
    	 
    	 TGRenderHelper.enableFXLighting();

         GL11.glPushMatrix();
    	 
         GL11.glEnable(GL11.GL_BLEND);
         GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

   
    	 this.bindEntityTexture(par1Entity);
         
         
         EntityLivingBase shooter = (EntityLivingBase) Minecraft.getMinecraft().thePlayer.worldObj.getEntityByID(laser.shooterID);
         if (shooter != null) {
	         //GL11.glTranslated(par2, par4, par6);
 
	         double x = shooter.posX-Minecraft.getMinecraft().thePlayer.posX;
	         double y = shooter.posY+shooter.getEyeHeight()-(Minecraft.getMinecraft().thePlayer.posY+Minecraft.getMinecraft().thePlayer.getEyeHeight());
	         double z = shooter.posZ-Minecraft.getMinecraft().thePlayer.posZ;
	         
	         float pitch = MathUtil.interpolateRotation(shooter.prevRotationPitch, shooter.rotationPitch, ptt);
	         float yaw = MathUtil.interpolateRotation(shooter.prevRotationYawHead, shooter.rotationYawHead, ptt);
	         
	         //System.out.println("ThirdPerson: "+Minecraft.getMinecraft().gameSettings.thirdPersonView);
	         if (shooter == Minecraft.getMinecraft().thePlayer && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
	        	 
//	        	 if (Minecraft.getMinecraft().gameSettings.viewBobbing)
//	             {
//	                 setupViewBobbing(ptt);
//	             }
//	        	 
	        	 Vec3 offset = Vec3.createVectorHelper(0, -0.08, 0.12);
		         MathUtil.rotateAroundZ(offset, pitch*MathUtil.D2R);
		         MathUtil.rotateAroundY(offset, ((-90.0-yaw)*MathUtil.D2R));
		         
		         x+=offset.xCoord;
		         y+=offset.yCoord;
		         z+=offset.zCoord;
	         }else {
		         x -= (double)(MathHelper.cos(shooter.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		         y -=-0.10000000149011612D - laser.get3PYOffset();
		         z -= (double)(MathHelper.sin(shooter.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		    }
	         
	         GL11.glTranslated(x, y, z);
	    	         
	         
	         GL11.glRotatef(-(yaw-90F), 0.0F, 1.0F, 0.0F);
	         GL11.glRotatef(pitch, 0.0F, 0.0F, 1.0F);  
	         
	         if (shooter == Minecraft.getMinecraft().thePlayer && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {       	 
	        	 if (Minecraft.getMinecraft().gameSettings.viewBobbing)
	             {
	                 setupViewBobbing(ptt);
	             }
	         }
	         
	         //Move forward
	         GL11.glTranslated(-0.25, 0, 0);
	         distance+= 0.25;
         
         
         
         //GL11.glTranslated(par2, par4, par6);
         //GL11.glRotatef(-(par1Entity.rotationYaw-90F), 0.0F, 1.0F, 0.0F);
         //GL11.glRotatef(par1Entity.rotationPitch, 0.0F, 0.0F, 1.0F);
       
         Tessellator tessellator = Tessellator.instance;
         float f10 = 0.0125F;
      
         distance*=80.0D;
         
         GL11.glEnable(GL12.GL_RESCALE_NORMAL);
         GL11.glDisable(GL11.GL_CULL_FACE);
        
         float rot_x = 45f+(prog*360f);
         
         GL11.glRotatef(rot_x+90f , 1.0F, 0.0F, 0.0F);
         
         
         GL11.glScalef(f10, f10, f10);

         float brightness = (float) Math.sin(Math.sqrt(prog)*Math.PI);
 
         //---
         // RENDER BEAM
         double UVscale = 2.0D; 
         int numFrames = 17;
         float frametime = 0.5f; //ticks per frame
         double u = distance / (laserWidth*8.0 * UVscale);
         	
         int frame = (int) ((((float)par1Entity.ticksExisted+ptt) * frametime) % numFrames);
         double v1 = (1.0D / (double)numFrames) * (double)frame;
         double v2 = (1.0D / (double)numFrames) * (double)(frame+1);
         
         for (int i = 0; i < 2; ++i)
         {
             GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
             GL11.glNormal3f(0.0F, 0.0F, f10);

             tessellator.setColorRGBA_F(1.0f, 1.0f, 1.0f, brightness);
             tessellator.startDrawingQuads();
             tessellator.addVertexWithUV(-distance, -width, 0.0D, 0+i, v1);
             tessellator.addVertexWithUV(0, -width, 0.0D, u+i, v1);
             tessellator.addVertexWithUV(0, width, 0.0D, u+i, v2);
             tessellator.addVertexWithUV(-distance, width, 0, 0+i, v2);
             tessellator.draw();
         }
         //-------
         
         //Render Spiral
         //---
         distance = Math.min(distance, 400.0); //SHORTEN LENGTH FOR SPIRAL
         int segments = (int) (distance/8.0);
         double w = 4.0; //width/2
         double radius = 0.0;
         double angle = Math.PI/8.0;
         double prevX = 0.0;
         double prevY = 0.0;
         double prevZ = 0.0;
         double prevu = 0.0;
         double cos2 = Math.cos((Math.PI-angle)*0.5)*2.0;
         double angleOffset = (rand.nextDouble()*Math.PI*2.0);
         UVscale = 8.0;
         //-
         double d = distance/segments;
         for (int i = 0; i < segments; i++) {
        	 /*double*/ x = -d*i;
        	 
        	 float iprog =  (float)i / (float)segments;
        	 radius = 0.5 * (1-Math.cos(2.0*Math.sqrt(iprog)*Math.PI)) * 8.0;
        	 
        	 Vec2 yz = MathUtil.polarOffsetXZ(0, 0, radius, angleOffset-angle*i);
        	 /*double*/ y = yz.x;
        	 /*double*/ z = yz.y;
        	 u = prevu+(cos2*radius/UVscale);
        	 
        	 tessellator.startDrawingQuads();
             tessellator.addVertexWithUV(prevX-w, prevY, prevZ, prevu, v2);
             tessellator.addVertexWithUV(x-w, y, z, u, v2);
             tessellator.addVertexWithUV(x+w, y, z, u, v1);
             tessellator.addVertexWithUV(prevX+w, prevY, prevZ, prevu, v1);
             tessellator.draw();
             
             prevX = x;
             prevY = y;
             prevZ = z;
             prevu = u;
         }
         
         
         //---
         //Render Flare

         
         
         //---
         }
         
         GL11.glEnable(GL11.GL_CULL_FACE);
         GL11.glDisable(GL12.GL_RESCALE_NORMAL);
         
         GL11.glDisable(GL11.GL_BLEND);

         GL11.glPopMatrix();	
         
	 	 GL11.glPopAttrib();

         TGRenderHelper.disableFXLighting();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return LaserTextures;
	}
	
	private void setupViewBobbing(float p_78475_1_)
    {
        if (Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)Minecraft.getMinecraft().renderViewEntity;
            float f1 = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
            float f2 = -(entityplayer.distanceWalkedModified + f1 * p_78475_1_);
            float f3 = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * p_78475_1_;
            float f4 = entityplayer.prevCameraPitch + (entityplayer.cameraPitch - entityplayer.prevCameraPitch) * p_78475_1_;
//            GL11.glTranslatef(MathHelper.sin(f2 * (float)Math.PI) * f3 * 0.5F, -Math.abs(MathHelper.cos(f2 * (float)Math.PI) * f3), 0.0F);
//            GL11.glRotatef(MathHelper.sin(f2 * (float)Math.PI) * f3 * 3.0F, 0.0F, 0.0F, 1.0F);
//            GL11.glRotatef(Math.abs(MathHelper.cos(f2 * (float)Math.PI - 0.2F) * f3) * 5.0F, 1.0F, 0.0F, 0.0F);
//            GL11.glRotatef(f4, 1.0F, 0.0F, 0.0F);
            
            float F1 = -1.0f; //(float) Keybinds.X;
            float F2 = -1.0f; //(float) Keybinds.Y;
            
            GL11.glTranslatef(MathHelper.sin(f2 * (float)Math.PI) * f3 * 0.5F * F1, -Math.abs(MathHelper.cos(f2 * (float)Math.PI) * f3) * F2, 0.0F);
            GL11.glRotatef(MathHelper.sin(f2 * (float)Math.PI) * f3 * 3.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(Math.abs(MathHelper.cos(f2 * (float)Math.PI - 0.2F) * f3) * 5.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(f4, 1.0F, 0.0F, 0.0F);

        }
    }

}
