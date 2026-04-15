package techguns.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class TGRenderHelper {
	
	
	/**
     * Sets the OpenGL lighting properties to the values used when rendering FX or GUI at full brightness
     */
    public static void enableFXLighting()
    {
    	GL11.glPushMatrix();		
		GL11.glDisable(GL11.GL_LIGHTING);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
    }
    
    public static void disableFXLighting()
    {
    	GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
    
    public static void enableAlphaBlend(){
    	GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }
    public static void enableAlphaBlendAdditive(){
    	GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
    }
    public static void disableAlphaBlend(){
    	GL11.glPopAttrib();
    }
    
    public static void enableBlendMode(RenderType renderType) {
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
    	if (renderType != RenderType.SOLID) {
    		GL11.glEnable(GL11.GL_BLEND);
    		//GL11.glEnable(GL11.GL_ALPHA_TEST);
    	}
        if (renderType == RenderType.ALPHA) {
        	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        } else if (renderType == RenderType.ADDITIVE) {
        	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        }
        if (renderType != RenderType.ALPHA_SHADED) TGRenderHelper.enableFXLighting();
	}
	
	public static void disableBlendMode(RenderType renderType) {
		if (renderType != RenderType.ALPHA_SHADED) TGRenderHelper.disableFXLighting();
		if (renderType != RenderType.SOLID) {
    		GL11.glDisable(GL11.GL_BLEND);
    		//GL11.glDisable(GL11.GL_ALPHA_TEST);
    	}
		GL11.glPopAttrib();
	}
    
    
    public enum RenderType {
    	ALPHA, ADDITIVE, SOLID, ALPHA_SHADED, NO_Z_TEST;
    }
    
  /**
     * Returns a rotation angle that is inbetween two other rotation angles. par1 and par2 are the angles between which
     * to interpolate, par3 is probably a float between 0.0 and 1.0 that tells us where "between" the two angles we are.
     * Example: par1 = 30, par2 = 50, par3 = 0.5, then return = 40
     */
    public static float interpolateRotation(float angle1, float angle2, float progress)
    {
        float f3;

        for (f3 = angle2 - angle1; f3 < -180.0F; f3 += 360.0F)
        {
            ;
        }

        while (f3 >= 180.0F)
        {
            f3 -= 360.0F;
        }

        return angle1 + progress * f3;
    }
    
    /**
     * Set lighting for tileentity rendering, from forge tutorials
     * @param world
     * @param i
     * @param j
     * @param k
     * @param block
     */
    public static void adjustLightFixture(World world, int i, int j, int k, Block block) {
            Tessellator tess = Tessellator.instance;
            //float brightness = block.getBlockBrightness(world, i, j, k);
            //As of MC 1.7+ block.getBlockBrightness() has become block.getLightValue():
            float brightness = block.getLightValue(world, i, j, k);
            int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
            int modulousModifier = skyLight % 65536;
            int divModifier = skyLight / 65536;
            tess.setColorOpaque_F(brightness, brightness, brightness);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,  (float) modulousModifier,  divModifier);
    }
}

