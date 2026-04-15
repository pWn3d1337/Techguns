package techguns.client.renderer.entity;

import java.util.Random;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.renderer.TGRenderHelper;
import techguns.entities.projectiles.TeslaBeam;
import techguns.items.guns.Teslagun;

public class RenderTeslaBeam extends Render {
	private static final ResourceLocation LaserTextures = new ResourceLocation(
			"techguns:textures/fx/laser_blue.png");

	double offset = 0.20; // Distance per bolt vertex
	int sinCount = 5; // Number of overlapping sin functions
	double WIDTH = 0.5;

	double SIN_DISTANCE = 10.0; //ideal distance for one sinus curve;
	
	double xOffset; // initial offset

	private int maxTicks = Teslagun.BEAM_LIFETIME; // TODO: Change

	public RenderTeslaBeam(double xOffset) {
		this.xOffset = xOffset;
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void func_76986_a(T entity, double d, double d1, double d2, float f,
	 * float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
	    		 
    	TeslaBeam laser = (TeslaBeam)par1Entity;	  
    		
        Random rand = new Random(laser.seed); //Fixed seed for 1 bolt;
	    	
    	double distance = laser.beamlength;
    	//System.out.println("distance:"+distance);
    	double[] dY = new double[sinCount];
    	double[] dZ = new double[sinCount];
    	
    	for (int i = 0; i < sinCount; i++) {	    	
    		//TODO: Y/Z random vectors with fixed length, or whatever
    		dY[i] = 0.5-rand.nextDouble(); //fixed for this bolt
    		dZ[i] = 0.5-rand.nextDouble();
    	}
    	
    	float progress = ((float)par1Entity.ticksExisted / maxTicks);
    	
    	distance-= xOffset;
    	int count = (int) Math.round(distance / offset);
    	offset = (distance / (double) count);
    	
    	int xreps = Math.max(1, (int) Math.round(distance / SIN_DISTANCE)); //TODO: get modulo as additional y/z scale?
    	//double xscale = (distance/(double)xreps);
    	
    	double xprev = 0, yprev = 0, zprev = 0, widthprev = 1.0, alphaprev = 1.0;

    				
    	this.bindEntityTexture(par1Entity);	         
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glTranslated(par2, par4, par6);
        GL11.glRotatef(-(par1Entity.rotationYaw+90), 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-par1Entity.rotationPitch, 0.0F, 0.0F, 1.0F);
        //GL11.glScaled(0.0625, 0.0625, 0.0625);
        
        Tessellator tessellator = Tessellator.instance;
        
        TGRenderHelper.enableAlphaBlendAdditive();
        TGRenderHelper.enableFXLighting();
	        
		//tessellator.startDrawingQuads();
	
	    	
    	for (int i = 0; i <= count; i++) {
    		double d = (double)i/(double)count; //distance progress (0-1)
    		
    		
    		double x = xOffset + (double)i*offset;
    		double y = 0;
    		double z = 0;
    		double randomness = 0.00;
    		
    		if (i > 1) {
	    		for (int j = 1; j <= sinCount; j++) {
	    			double yfactor = ((rand.nextDouble()-0.5) + (progress*dY[j-1] *1.0)) * (2.0/(double)j);
	    			double zfactor = ((rand.nextDouble()-0.5) + (progress*dZ[j-1] *1.0)) * (2.0/(double)j);
	    			y+= Math.sin((d * Math.PI) * (double)( j * xreps))*yfactor;
	    			z+= Math.sin((d * Math.PI) * (double)( j * xreps))*zfactor;
	    		}
//	    		y+=randomness*(Math.random()-0.5);
//	    		z+=randomness*(Math.random()-0.5);
//	    		x+=randomness*(Math.random()-0.5);
    		}
    		
    		
    		double pulse = 1.0 - Math.sqrt(Math.abs(progress-d)*2.0);
    		//1-WURZEL(ABS(B2-C2))
    		
    		//System.out.printf("X/Y/Z: (%2.2f/%2.2f/%2.2f)\n",x,y,z);
    		double width =  Math.max(0.0, WIDTH*pulse); //WIDTH+(WIDTH*10.0*pulse);
    		if (i >= 1) {
    			drawSegment(tessellator, xprev, yprev, zprev, x,y,z, widthprev, width, alphaprev, pulse);
    		}
    		widthprev = width;
    		alphaprev = pulse;
    		xprev = x;
    		yprev = y;
    		zprev = z;
    	}
        
    	//tessellator.draw();
    	
        TGRenderHelper.disableFXLighting();
        TGRenderHelper.disableAlphaBlend();
    	GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
		       
	}

	
	void drawSegment(Tessellator tessellator, double x1, double y1, double z1, double x2, double y2, double z2, double width1, double width2, double a1, double a2) {
		double scale = 0.5;
		y1*=scale;
		y2*=scale;
		z1*=scale;
		z2*=scale;
		width1*=scale;
		width2*=scale;
//		for (int i = 0; i < 4; i++) { //crossed planes with backsides
			//TODO:<disable backface cull>
			//GL11.glDisable(GL11.GL_CULL_FACE);
		
			GL11.glPushMatrix();
			GL11.glRotated(45.0, 1.0, 0.0, 0.0);
			//Tessellator tessellator = Tessellator.instance;
			
			//set alpha1
			tessellator.setColorRGBA_F(1.0f, 1.0f, 1.0f, (float)a1);
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(x1, y1-width1, z1, 0, 0);
			tessellator.addVertexWithUV(x1, y1+width1, z1, 0, 1);
			tessellator.addVertexWithUV(x2, y2+width2, z2, 1, 1);
			tessellator.addVertexWithUV(x2, y2-width2, z2, 1, 0);
			tessellator.draw();
			//--
			//set alpha2
			
			tessellator.setColorRGBA_F(1.0f, 1.0f, 1.0f, (float)a2);
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(x1, y1, z1-width1, 0, 0);
			tessellator.addVertexWithUV(x1, y1, z1+width1, 0, 1);
			tessellator.addVertexWithUV(x2, y2, z2+width2, 1, 1);
			tessellator.addVertexWithUV(x2, y2, z2-width2, 1, 0);
			tessellator.draw();
			
			GL11.glPopMatrix();
			//GL11.glEnable(GL11.GL_CULL_FACE);
	//}
			
			

	}
	
	
	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return LaserTextures;
	}
}
