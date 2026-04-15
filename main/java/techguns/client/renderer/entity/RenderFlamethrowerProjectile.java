package techguns.client.renderer.entity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import techguns.entities.projectiles.FlamethrowerProjectile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFlamethrowerProjectile extends Render {
	
	private float scale;
	private ResourceLocation textureLoc;
	
    public RenderFlamethrowerProjectile(float scale, String texture)
    {
    	this.scale = scale;
    	this.textureLoc = new ResourceLocation(texture);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    { 	
    	this.doRender((FlamethrowerProjectile)par1Entity, par2, par4, par6, par8, par9);
    }
    
    public void doRender(FlamethrowerProjectile par1Entity, double par2, double par4, double par6, float par8, float par9)
    { 	
    	//par1Entity.readFromNBT(par1Entity.getEntityData());
    	//float scale = par1Entity.getScale();
    	
    		//System.out.println("scale:"+scale);
    		
            GL11.glPushMatrix();
            GL11.glTranslatef((float)par2, (float)par4, (float)par6);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glScalef(0.1F*scale, 0.1F*scale, 0.1F*scale);
            this.bindEntityTexture(par1Entity);
            Tessellator tessellator = Tessellator.instance;
            this.drawProjectile(tessellator);
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
    }
    
    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return getEntityTexture((FlamethrowerProjectile)par1Entity);
    }
    
    protected ResourceLocation getEntityTexture(FlamethrowerProjectile par1Entity)
    {
        return this.textureLoc; //par1Entity.getProjectileTexture();
    }
    
    private void drawProjectile(Tessellator par1Tessellator)
    {
    	
//        float f = par2Icon.getMinU();
//        float f1 = par2Icon.getMaxU();
//        float f2 = par2Icon.getMinV();
//        float f3 = par2Icon.getMaxV();
        
        float f = 0.f;
        float f1 = 1.f;
        float f2 = 0.f;
        float f3 = 1.f;
        

        /*
         *         float f = 0.06253906F;
        float f1 = 0.12496094F;
        float f2 = 0.68753904F;
        float f3 = 0.74996096F;
         */
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setNormal(0.0F, 1.0F, 0.0F);
        par1Tessellator.addVertexWithUV((double)(0.0F - f5), (double)(0.0F - f6), 0.0D, (double)f, (double)f3);
        par1Tessellator.addVertexWithUV((double)(f4 - f5), (double)(0.0F - f6), 0.0D, (double)f1, (double)f3);
        par1Tessellator.addVertexWithUV((double)(f4 - f5), (double)(f4 - f6), 0.0D, (double)f1, (double)f2);
        par1Tessellator.addVertexWithUV((double)(0.0F - f5), (double)(f4 - f6), 0.0D, (double)f, (double)f2);
        par1Tessellator.draw();
    }

}
