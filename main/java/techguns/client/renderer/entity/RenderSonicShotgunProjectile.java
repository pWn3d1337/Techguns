package techguns.client.renderer.entity;

import java.util.Random;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import techguns.client.renderer.TGRenderHelper;
import techguns.entities.projectiles.SonicShotgunProjectile;

public class RenderSonicShotgunProjectile extends Render {
	private static final ResourceLocation bulletTexture = new ResourceLocation("techguns:textures/entity/bullet.png");
	private static final ResourceLocation waveTexture_A = new ResourceLocation("techguns:textures/fx/sonicwave4x4.png");
	private static final ResourceLocation waveTexture = new ResourceLocation("techguns:textures/fx/shockwave.png");

	public RenderSonicShotgunProjectile() {
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void func_76986_a(T entity, double d, double d1, double d2, float f,
	 * float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(Entity par1Entity, double par2, double par4,
			double par6, float par8, float par9) {

		if (par1Entity.ticksExisted >= 3) {
			SonicShotgunProjectile proj = (SonicShotgunProjectile) par1Entity;

			if (proj.mainProjectile) {
				 Random rand = new Random(proj.getEntityId());
				
		         GL11.glPushMatrix();
		         GL11.glTranslatef((float)par2, (float)par4, (float)par6);
		         GL11.glRotatef(par1Entity.rotationYaw-90, 0.0F, 1.0F, 0.0F);
		         GL11.glRotatef(par1Entity.rotationPitch, 0.0F, 0.0F, 1.0F);
		         Tessellator tessellator = Tessellator.instance;
		         GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		         double d = ((float)par1Entity.ticksExisted+par9)*0.3;
		         TGRenderHelper.enableAlphaBlendAdditive();  
		         
		         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
		         GL11.glNormal3f(0.0F, 0.0F, 0.0125f);    
		         
		         float prog = (float)Math.min(1.0,((float)proj.ticksExisted+par9) / 20.0f);

		         //Render static waves
		         
		         double opacity = 1.0-(Math.pow(prog,2.0));

		         bindTexture(waveTexture);
		         for (int i = 0; i <= 5; i++) {		        	 
			         double r = opacity*rand.nextDouble();
			         double g = opacity*rand.nextDouble();
			         double b = opacity*(0.5+(rand.nextDouble()*0.5));
			         GL11.glColor4d(r,g,b, opacity);
		        	 double offset = 2.5 - (double)i;
		        	 double size = d * (0.5+(rand.nextDouble()));
			         drawWave(tessellator, size, offset);
		         }
		         
		         //Render Animated Wave
		         
//		         bindTexture(waveTexture_A);
//		         GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
//		         drawAnimatedWave(tessellator, d*3, 0, prog);
	             
	             TGRenderHelper.disableAlphaBlend();
		         GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		         GL11.glPopMatrix();

			} else {
//				// (DUMMY) Bullet Render Code
//				bindTexture(this.bulletTexture);
//				GL11.glPushMatrix();
//				GL11.glTranslatef((float) par2, (float) par4, (float) par6);
//				GL11.glRotatef(par1Entity.rotationYaw - 90, 0.0F, 1.0F, 0.0F);
//				GL11.glRotatef(par1Entity.rotationPitch, 0.0F, 0.0F, 1.0F);
//				Tessellator tessellator = Tessellator.instance;
//				byte b0 = 0;
//				float f2 = 0.0F;
//				float f3 = 0.5F;
//				float f4 = (float) (0 + b0 * 10) / 32.0F;
//				float f5 = (float) (5 + b0 * 10) / 32.0F;
//				float f6 = 0.0F;
//				float f7 = 0.15625F;
//				float f8 = (float) (5 + b0 * 10) / 32.0F;
//				float f9 = (float) (10 + b0 * 10) / 32.0F;
//				float f10 = 0.0125F;
//				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
//
//				GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
//				GL11.glScalef(f10, f10, f10);
//
//				for (int i = 0; i < 4; ++i) {
//					GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
//					GL11.glNormal3f(0.0F, 0.0F, f10);
//					tessellator.startDrawingQuads();
//					tessellator.addVertexWithUV(-8.0D, -2.0D, 0.0D,
//							(double) f2, (double) f4);
//					tessellator.addVertexWithUV(8.0D, -2.0D, 0.0D, (double) f3,
//							(double) f4);
//					tessellator.addVertexWithUV(8.0D, 2.0D, 0.0D, (double) f3,
//							(double) f5);
//					tessellator.addVertexWithUV(-8.0D, 2.0D, 0.0D, (double) f2,
//							(double) f5);
//					tessellator.draw();
//				}
//
//				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
//				GL11.glPopMatrix();
			}
		}
	}

	private void drawWave(Tessellator tessellator, double size, double offset) { 

        tessellator.startDrawingQuads();     	             
        tessellator.addVertexWithUV(offset, -size, -size, 0, 0);
        tessellator.addVertexWithUV(offset, -size, size, 0, 1);
        tessellator.addVertexWithUV(offset, size, size, 1, 1);
        tessellator.addVertexWithUV(offset, size, -size, 1, 0);
        tessellator.draw();
	}
	
	private void drawAnimatedWave(Tessellator tessellator, double size, double offset, float prog) { 
		int cols = 4;
		int rows = 4;
		int frames = 16;
		
		int currentFrame = (int) ((float)frames*prog);
		
		int col = currentFrame % cols;
		int row = (currentFrame / cols);
		
		float u = 1.f/cols;
		float v = 1.f/rows;
		
		float U1 = col*u;
		float V1 = row*v;
		float U2 = (col+1)*u;
		float V2 = (row+1)*v;
		
        tessellator.startDrawingQuads();     	             
        tessellator.addVertexWithUV(offset, -size, -size, U1, V1);
        tessellator.addVertexWithUV(offset, -size, size, U1, V2);
        tessellator.addVertexWithUV(offset, size, size, U2, V2);
        tessellator.addVertexWithUV(offset, size, -size, U2, V1);
        tessellator.draw();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return bulletTexture;
	}
}