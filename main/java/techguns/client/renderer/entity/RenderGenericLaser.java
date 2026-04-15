package techguns.client.renderer.entity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import techguns.client.renderer.TGRenderHelper;
import techguns.entities.projectiles.LaserBeam;
import techguns.items.guns.Lasergun;

public class RenderGenericLaser extends Render {
	private static final ResourceLocation LaserTextures = new ResourceLocation("techguns:textures/fx/laser3.png");

	private double laserWidth = 2.0D;
	private int maxTicks = Lasergun.BEAM_LIFETIME;
	
	  public RenderGenericLaser()
	    {
	    }

	    /**
	     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
	     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	     */
	    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	    {
	    	
	    		 
	    		 LaserBeam laser = (LaserBeam)par1Entity;

		         Vec3 source = Vec3.createVectorHelper(laser.posX, laser.posY, laser.posZ);
		         Vec3 target = Vec3.createVectorHelper(laser.targetX, laser.targetY, laser.targetZ);    	 		 
		         Vec3 viewer = Vec3.createVectorHelper(this.renderManager.viewerPosX, this.renderManager.viewerPosY, this.renderManager.viewerPosZ);
		         
	    		 double distance = source.distanceTo(target);
	    		 float prog = ((float)par1Entity.ticksExisted)/((float)maxTicks);
	    		 double width = laserWidth * (Math.sin(Math.sqrt(prog)*Math.PI))*2;
	    		 
	    		 double u = (distance / laserWidth) * 2.0D;
	    		 
	    		 //System.out.printf("[Laser] width = %3.3f; ticksExisted = %d\n", width, par1Entity.ticksExisted);
	    		 
		    	 this.bindEntityTexture(par1Entity);
		         
		    	 GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		    	 
		    	 TGRenderHelper.enableFXLighting();
		         //GL11.glDepthMask(true);
		    	 //GL11.glEnable(GL11.GL_DEPTH_TEST);
		    	 
		    	 //GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		         GL11.glPushMatrix();
		    	 
		         GL11.glEnable(GL11.GL_BLEND);
		         GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		         //GL11.glEnable(GL11.GL_ALPHA_TEST);
		         
		         //GL11.glDisable(GL11.GL_DEPTH_TEST);
		        // GL11.glDepthMask(false);
		         
		         //GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
		         //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		         


		         
		    	 this.bindEntityTexture(par1Entity);
		         GL11.glTranslated(par2, par4, par6);
		         
		         
		         GL11.glRotatef(-(par1Entity.rotationYaw-90F), 0.0F, 1.0F, 0.0F);
		               
		         GL11.glRotatef(par1Entity.rotationPitch, 0.0F, 0.0F, 1.0F);
		       
		         Tessellator tessellator = Tessellator.instance;
		         float f10 = 0.0125F;
		         double scale = 0.0125;
		         
		      
		         distance*=80.0D;
		         
		         GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		         GL11.glDisable(GL11.GL_CULL_FACE);
		        
//		         Vec3 VS = viewer.subtract(source);
//		         Vec3 VT = viewer.subtract(target);
//		         Vec3 v_normal = VS.crossProduct(VT);
//		         float rot_x = (float) (Math.acos(viewer.normalize().dotProduct(v_normal.normalize())) * (180/Math.PI));
//		         System.out.println("rot_x = "+rot_x);

		         //float rot_x = 0;
		         
		         float rot_x = 45f+(prog*180f);
		         
		         GL11.glRotatef(rot_x+90f , 1.0F, 0.0F, 0.0F);
		         
		         
		         GL11.glScalef(f10, f10, f10);

	             float brightness = (float) Math.sin(Math.sqrt(prog)*Math.PI);
	            		 
		         for (int i = 0; i < 2; ++i)
		         {
		             GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
		             GL11.glNormal3f(0.0F, 0.0F, f10);

		             tessellator.setColorRGBA_F(1.0f, 1.0f, 1.0f, brightness);
		             tessellator.startDrawingQuads();
		             tessellator.addVertexWithUV(-distance, -width, 0.0D, prog, 0);
		             tessellator.addVertexWithUV(0, -width, 0.0D, u+prog, 0);
		             tessellator.addVertexWithUV(0, width, 0.0D, u+prog, 1);
		             tessellator.addVertexWithUV(-distance, width, 0.0D, prog, 1);
		             tessellator.draw();
		         }
	
		         GL11.glEnable(GL11.GL_CULL_FACE);
		         GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		         
		         GL11.glDisable(GL11.GL_BLEND);
		         //GL11.glDisable(GL11.GL_ALPHA_TEST);
		         //GL11.glDepthMask(true);
		         //GL11.glEnable(GL11.GL_DEPTH_TEST);
		         
		         GL11.glPopMatrix();	
		         
			 	 GL11.glPopAttrib();
			 	 
		         //GL11.glDepthMask(false);
			 	 //GL11.glDisable(GL11.GL_DEPTH_TEST);
		         TGRenderHelper.disableFXLighting();
		    	 
	    	// }
	    }
	    /**
	     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	     */
	    protected ResourceLocation getEntityTexture(Entity par1Entity)
	    {
	        return LaserTextures;
	    }
	}
