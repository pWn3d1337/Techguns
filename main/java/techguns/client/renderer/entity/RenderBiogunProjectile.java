package techguns.client.renderer.entity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import techguns.client.renderer.TGRenderHelper;
import techguns.entities.projectiles.BiogunProjectile;

public class RenderBiogunProjectile extends Render{

	//private float scale;
	private ResourceLocation textureLoc;
	//private ResourceLocation glowTextureLoc;
	
    public RenderBiogunProjectile(String texture)
    {
    	//this.scale = scale;
    	this.textureLoc = new ResourceLocation(texture);
    	//this.glowTextureLoc = new ResourceLocation("techguns:textures/fx/lensflare1.png");
    }
	
	  public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
	  			float scale = 1.0f;
		  
		  		//TODO: Get Projectile Scale from entity
		  		if (par1Entity instanceof BiogunProjectile) {
		  			switch (((BiogunProjectile)par1Entity).level) {
		  			case 1:
		  				scale = 1.25f;
		  				break;
		  			case 2:
		  				scale = 2.5f;
		  				break;
		  			case 3:
		  				scale = 4.0f;
		  				break;
		  			}
		  		}
		  
		  
	            GL11.glPushMatrix();
	            GL11.glTranslatef((float)par2, (float)par4, (float)par6);
	            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	            GL11.glScalef(0.15F*scale, 0.15F*scale, 0.15F*scale);
	            this.bindEntityTexture(par1Entity);
	            Tessellator tessellator = Tessellator.instance;
	            TGRenderHelper.enableAlphaBlendAdditive();
	            TGRenderHelper.enableFXLighting();
	            this.drawProjectile(tessellator, par9);
	            
	            //draw glow flare
	            //this.renderManager.renderEngine.bindTexture(glowTextureLoc);
	            //GL11.glColor4f(0, 1.0f, 0, 0.5f);
	            //float f = 0.75f + (par9 * 0.5f);
	            //GL11.glScalef(0.5F*scale*f, 0.5F*scale*f, 0.5F*scale*f);
	            //GL11.glScalef(2.0f*f, 2.0f*f, 2.0f*f);
	            //this.drawProjectile(tessellator);
	            //GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	            
	            TGRenderHelper.disableFXLighting();
	            TGRenderHelper.disableAlphaBlend();;
	            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	            GL11.glPopMatrix();
	    }
	    
	    /**
	     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	     */
	    @Override
	    protected ResourceLocation getEntityTexture(Entity par1Entity)
	    {
	        return this.textureLoc; //par1Entity.getProjectileTexture();
	    }
	    
	    private void drawProjectile(Tessellator par1Tessellator, float ptt)
	    {
	    
	        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
	        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
	        
	        drawQuad(par1Tessellator);
	        
//	        this.renderManager.renderEngine.bindTexture(glowTextureLoc);
//            GL11.glColor4f(0, 1.0f, 0, 0.5f);
//            float f = 0.75f + (ptt * 0.5f);
//            GL11.glScalef(4.0f*f, 4.0f*f, 4.0f*f);
//            //GL11.glTranslatef(0, -2.0f*f, 0);
//            drawQuad(par1Tessellator);
//            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	    }
	    
	    private void drawQuad(Tessellator par1Tessellator) {
	        float f = 0.f;
	        float f1 = 1.f;
	        float f2 = 0.f;
	        float f3 = 1.f;
	        
	        float f4 = 1.0F;
	        float f5 = 0.5F;
	        float f6 = 0.25F;
	        
	        GL11.glPushMatrix();
	        
	    	par1Tessellator.startDrawingQuads();
	        par1Tessellator.setNormal(0.0F, 1.0F, 0.0F);
	        par1Tessellator.addVertexWithUV((double)(0.0F - f5), (double)(0.0F - f6), 0.0D, (double)f, (double)f3);
	        par1Tessellator.addVertexWithUV((double)(f4 - f5), (double)(0.0F - f6), 0.0D, (double)f1, (double)f3);
	        par1Tessellator.addVertexWithUV((double)(f4 - f5), (double)(f4 - f6), 0.0D, (double)f1, (double)f2);
	        par1Tessellator.addVertexWithUV((double)(0.0F - f5), (double)(f4 - f6), 0.0D, (double)f, (double)f2);
	        par1Tessellator.draw();
	        
	        GL11.glPopMatrix();
	    }

}
