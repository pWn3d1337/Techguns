package techguns.client.renderer.FX;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.renderer.TGRenderHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ScreenEffect {
		
	public static ScreenEffect muzzleFlash = new ScreenEffect("textures/fx/muzzleflash4x4.png", 4, 4, 10);

	public static ScreenEffect muzzleFlash2 = new ScreenEffect("textures/fx/muzzleflash2.png", 4, 4, 8);
	public static ScreenEffect muzzleFlash_blue = new ScreenEffect("textures/fx/muzzleflash_blue.png", 4, 4, 8);
	//public static ScreenEffect muzzleFlash = new ScreenEffect("textures/guns/handgun.png", 2, 2, 4);
	public static ScreenEffect muzzleFlashYellow = new ScreenEffect("textures/fx/muzzleFlashYellow2x2.png", 2, 2, 4);
	public static ScreenEffect muzzleFlashLaser = new ScreenEffect("textures/fx/laserflare02.png", 4, 4, 7);
	public static ScreenEffect muzzleFlashLightningOld = new ScreenEffect("textures/fx/lightningflare4x4_2.png", 4, 4, 16);
	public static ScreenEffect muzzleFlashFireball_alpha = new ScreenEffect("textures/fx/fireball_4x4_alpha.png", 4, 4, 16);
	public static ScreenEffect muzzleFlashFireball_add = new ScreenEffect("textures/fx/fireball_4x4_add.png", 4, 4, 16);
	public static ScreenEffect muzzleFlashBlaster = new ScreenEffect("textures/fx/blasterflare01.png", 4, 4, 14);
	public static ScreenEffect muzzleFlashAlienBlaster = new ScreenEffect("textures/fx/alienflare.png", 4, 4, 14);
	public static ScreenEffect muzzleFlashLightning = new ScreenEffect("textures/fx/teslaflare01.png", 4, 4, 14);
	public static ScreenEffect FlamethrowerMuzzleFlame = new ScreenEffect("textures/fx/flamethrower2.png", 4, 4, 16);
	public static ScreenEffect muzzleFlashSonic = new ScreenEffect("textures/fx/sonicwave4x4.png", 4, 4, 16);
	public static ScreenEffect muzzleFlashNukeBeam = new ScreenEffect("textures/fx/nukebeamflare.png", 4, 4, 16);
	
	public static ScreenEffect muzzleFlashNew = new ScreenEffect("textures/fx/muzzleflashnew.png", 4, 4, 16);
	public static ScreenEffect muzzleFlashNew2 = new ScreenEffect("textures/fx/muzzleflashnew_2_add.png", 4, 4, 16);
	

	
	public static ScreenEffect sniperScope = new ScreenEffect("textures/fx/Testscope.png",1,1,1);
	public static ScreenEffect techScope = new ScreenEffect("textures/fx/techscope.png",1,1,1);
	
	public static final float SNIPER_SCOPE_SCALE = 2.75f;
	
	ResourceLocation fxTexture;
	int cols;
	int rows;
	int numSprites;
	
	public ScreenEffect(String fxTexture, int cols,
			int rows, int numSprites) {
		super();
		this.fxTexture = new ResourceLocation("techguns",fxTexture);
		this.cols = cols;
		this.rows = rows;
		this.numSprites = numSprites;
	}

	
	public void doRender(float progress, float offsetX, float offsetY, float offsetZ, float scale) {
		doRender(progress, offsetX, offsetY,offsetZ,scale,0,0,0);
	}
	
	public void doRender(float progress, float offsetX, float offsetY, float offsetZ, float scale, float rot_x, float rot_y, float rot_z) {
		//System.out.println("RenderMuzzleFlash");
		int currentFrame = (int)((float)numSprites*progress);
		if (currentFrame < numSprites) {
			int col = currentFrame % cols;
			int row = (currentFrame / cols);
			
			float u = 1.f/cols;
			float v = 1.f/rows;
			
			float U1 = col*u;
			float V1 = row*v;
			float U2 = (col+1)*u;
			float V2 = (row+1)*v;
			
			//RenderHelper.disableStandardItemLighting();
			TGRenderHelper.enableFXLighting();
			
			Minecraft.getMinecraft().getTextureManager().bindTexture(fxTexture);
			GL11.glPushMatrix();
			
//			GL11.glDisable(GL11.GL_LIGHTING);
//			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
			

			GL11.glTranslatef(0.f, 0.f, 0.f);
			
			GL11.glRotatef(rot_x, 1.0f, 0, 0);
			GL11.glRotatef(rot_y, 0, 1.0f, 0);
			GL11.glRotatef(rot_z, 0, 0, 1.0f);
			
			GL11.glScalef(scale, scale, scale);
			//GL11.glDisable(GL11.GL_DIFFUSE);
			//GL11.glDisable(GL11.GL_LIGHTING);
			Tessellator tessellator = Tessellator.instance;
			
			tessellator.setBrightness(15728880);
					
			tessellator.startDrawingQuads();
			float f = scale/2.0f;
            tessellator.addVertexWithUV(offsetX-f, offsetY, offsetZ+f, (double)U2, (double)V1);
            tessellator.addVertexWithUV(offsetX+f, offsetY, offsetZ+f, (double)U1, (double)V1);
            tessellator.addVertexWithUV(offsetX+f, offsetY, offsetZ-f, (double)U1, (double)V2);
            tessellator.addVertexWithUV(offsetX-f, offsetY, offsetZ-f, (double)U2, (double)V2);
                         
            //tessellator.setBrightness(15);
            //GL11.glColor3f(1, 1, 1);
            //tessellator.setColorOpaque(255, 255, 255);
            
            tessellator.draw();
            //GL11.glEnable(GL11.GL_DIFFUSE);
            //GL11.glEnable(GL11.GL_LIGHTING);
            
			//GL11.glEnable(GL11.GL_LIGHTING);
            
            GL11.glPopMatrix();
            
            TGRenderHelper.disableFXLighting();
            
            //RenderHelper.enableStandardItemLighting();
		}
	}
	
	
	
}
