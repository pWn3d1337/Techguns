package techguns.client.renderer.entity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderRailgunProjectile extends Render {
private static final ResourceLocation bulletTextures = new ResourceLocation("techguns:textures/entity/BlueBeam.png");
	
    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
    	 if (par1Entity.ticksExisted >=2){
    	
	    	 this.bindEntityTexture(par1Entity);
	         GL11.glPushMatrix();
	         GL11.glTranslatef((float)par2, (float)par4, (float)par6);
	         GL11.glRotatef(par1Entity.rotationYaw-90, 0.0F, 1.0F, 0.0F);
	         GL11.glRotatef(par1Entity.rotationPitch, 0.0F, 0.0F, 1.0F);
	         Tessellator tessellator = Tessellator.instance;
	         byte b0 = 0;
	         float f2 = 0.0F;
	         float f3 = 0.5F;
	         float f4 = (float)(0 + b0 * 10) / 32.0F;
	         float f5 = (float)(5 + b0 * 10) / 32.0F;
	         float f6 = 0.0F;
	         float f7 = 0.15625F;
	         float f8 = (float)(5 + b0 * 10) / 32.0F;
	         float f9 = (float)(10 + b0 * 10) / 32.0F;
	         float f10 = 0.0125F;
	         GL11.glEnable(GL12.GL_RESCALE_NORMAL);

	         GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
	         GL11.glScalef(f10, f10, f10);


	         for (int i = 0; i < 4; ++i)
	         {
	             GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
	             GL11.glNormal3f(0.0F, 0.0F, f10);
	             tessellator.startDrawingQuads();
	             tessellator.addVertexWithUV(-80.0D, -2.0D, 0.0D, (double)f2, (double)f4);
	             tessellator.addVertexWithUV(80.0D, -2.0D, 0.0D, (double)f3, (double)f4);
	             tessellator.addVertexWithUV(80.0D, 2.0D, 0.0D, (double)f3, (double)f5);
	             tessellator.addVertexWithUV(-80.0D, 2.0D, 0.0D, (double)f2, (double)f5);
	             tessellator.draw();
	         }

	         GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	         GL11.glPopMatrix();
    	 }
    }
    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return bulletTextures;
    }
}
