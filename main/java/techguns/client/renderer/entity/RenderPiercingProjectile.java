package techguns.client.renderer.entity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import techguns.client.renderer.TGRenderHelper;
import techguns.entities.projectiles.PiercingProjectile;

@Deprecated
public class RenderPiercingProjectile extends Render {
	private static final ResourceLocation texture = new ResourceLocation("techguns:textures/fx/bio.png");//shockwave.png");

	  public RenderPiercingProjectile()
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
	    	 //if (par1Entity.ticksExisted >=2){
	    	
		    	 this.bindEntityTexture(par1Entity);
		         GL11.glPushMatrix();
		         GL11.glTranslatef((float)par2, (float)par4, (float)par6);
		         GL11.glRotatef(par1Entity.rotationYaw-90, 0.0F, 1.0F, 0.0F);
		         GL11.glRotatef(par1Entity.rotationPitch, 0.0F, 0.0F, 1.0F);
		         Tessellator tessellator = Tessellator.instance;
		         byte b0 = 0;
		         float f6 = 0.0F;
		         float f7 = 0.15625F;
		         float f8 = (float)(5 + b0 * 10) / 32.0F;
		         float f9 = (float)(10 + b0 * 10) / 32.0F;
		         float f10 = 0.0125F;
		         GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		         
		         //double d = Math.pow(((float)par1Entity.ticksExisted+par9),2);
		         //GL11.glScaled(f10*d, f10*d, f10*d);

		         
		         PiercingProjectile proj = (PiercingProjectile)par1Entity;
		         float size = proj.maxSize;
		         if (proj.ticksExisted < proj.sizeEnd) {
		 			float sizeprog = (float)proj.ticksExisted/(float)proj.sizeEnd;
		 			size = proj.minSize + (proj.maxSize-proj.minSize)*sizeprog;
		 		}
		         
	
	             //GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
	             //GL11.glNormal3f(0.0F, 0.0F, f10);
	             
	             TGRenderHelper.enableAlphaBlendAdditive();
	             
	             
	             tessellator.startDrawingQuads();        	             
	             tessellator.addVertexWithUV(size/2, -size, -size, 0, 0);
	             tessellator.addVertexWithUV(size/2, -size, size, 0, 1);
	             tessellator.addVertexWithUV(size/2, size, size, 1, 1);
	             tessellator.addVertexWithUV(size/2, size, -size, 1, 0);
	             
	             tessellator.addVertexWithUV(-size/2, -size, -size, 0, 0);
	             tessellator.addVertexWithUV(-size/2, -size, size, 0, 1);
	             tessellator.addVertexWithUV(-size/2, size, size, 1, 1);
	             tessellator.addVertexWithUV(-size/2, size, -size, 1, 0);
	             
	             tessellator.draw();

	             TGRenderHelper.disableAlphaBlend();
	
		         GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		         GL11.glPopMatrix();
	    	// }
	    }
	    /**
	     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	     */
	    protected ResourceLocation getEntityTexture(Entity par1Entity)
	    {
	        return texture;
	    }
	}
