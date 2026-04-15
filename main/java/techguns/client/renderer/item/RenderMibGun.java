package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;
import techguns.client.renderer.TGRenderHelper;
import techguns.client.renderer.FX.ScreenEffect;
import techguns.debug.Keybinds;

public class RenderMibGun extends RenderGunBase {

	public RenderMibGun(ModelMultipart model, ResourceLocation texture, float scale,
			float x, float y, float z) {
		super(model, texture, scale, x, y, z,false,false,1,false);
	}

	@Override
	protected void GL_Transform_FirstPerson(int part,float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(225.0f, 0, 1.0f, 0);
		GL11.glScalef(2.5f, 2.5f, 2.5f);

	}

	@Override
	protected void GL_Transform_Equipped(int part,float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(135.0f, 0, 1.0f, 0);
		GL11.glScalef(1.5f, 1.5f, 1.5f);
		GL11.glTranslatef(0.05f, -0.80f, 0.6f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
	}

	@Override
	protected void GL_Transform_Ground(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		//GL11.glScaled(1.3f, 1.3f, 1.3f);
		GL11.glTranslatef(0.0f, -0.6f, 0f);
	}

	@Override
	protected void GL_Transform_reloading(int part,float prog, byte attackType) {
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		GL11.glRotatef(35.0f*-y,1.0f,0,0);
		//GL11.glTranslatef(-0.55f, -0.45f, -0.8f);

		this.GL_Transform_Translate(part);
	}

	@Override
	protected void GL_Transform_Translate(int part) {
		//GL11.glTranslatef(0.1f, 0, -0.5f);
		GL11.glTranslatef(-0.75f, -0.45f, -0.8f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
	}

	@Override
	protected void GL_Transform_firing(int part,float prog, byte attackType) {
		
		//float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f));
		//GL11.glRotatef(y*20, 1.0f, 0, 0);
		/*if(prog<0.5f){
			float progslide=prog*2.0f;
			GL11.glTranslatef(-0.55f, -0.45f, (-0.8f)-(y*0.3f)+(0.2f*part*progslide));
		}*/
		float r= Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f));
		
		/*double x,y,z;
		x=Keybinds.X;
		y=Keybinds.Y;
		z=Keybinds.Z;*/
		
		float  y= 0.4f;
		GL11.glTranslatef(0, y, 0);
		GL11.glRotatef(r*25, 1, 0, 0f);
		GL11.glTranslated(0,-y, 0);
		//GL11.glTranslatef(0.2f+(y*0.5f), -0.5f+(y*0.4f), -0.45f);
		
		this.GL_Transform_Translate(part);
		//GL11.glTranslatef(-0.75f, -0.45f, (-0.8f)-(y*0.3f));
	}

	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
		//ScreenEffect.muzzleFlashBlaster.doRender(prog, -0.65f, -0.75f, 1.55f, 1.0f);
		TGRenderHelper.enableAlphaBlendAdditive();
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor3f(0.2f, 1.0f, 0.7f);
		ScreenEffect.muzzleFlashAlienBlaster.doRender(prog, -2.15f, -2.45f, -0.85f, 1.0f, 0, 90.0f, 0);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		 GL11.glDisable(GL11.GL_ALPHA_TEST);
		TGRenderHelper.disableAlphaBlend();
	
	}

	@Override
	protected void GL_Transform_Icon(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glScalef(1.4f, 1.4f, 1.4f);
		GL11.glRotatef(90, 0.0f, 1, 0);
		GL11.glTranslatef(0.3f, -0.7f, 0.4f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
	}

	@Override
	public void renderScope() {
	}

	@Override
	protected void GL_TransformRecoil_Equipped(int part, float prog, byte attackType) {
		float degree=-15;
		float offsetZ= 0.3f; //+0.9f;
		float offsetY= 0.5f;
		if(prog<0.5f){
			GL11.glTranslatef(0,offsetY, offsetZ);
			float progress=prog*2.0f;
			
			if(progress<0.25f){
				GL11.glRotatef(progress*4*degree, 1, 0, 0);
			} else {
				GL11.glRotatef((1-progress)*1.25f*degree, 1, 0, 0);
			}
		/*	if(part==1){
				GL11.glTranslatef(0, 0, 0.15f*progress);
			}*/
			GL11.glTranslatef(0,-offsetY, -offsetZ);
		}
	}

	
	
	@Override
	protected void transformEquippedAimed(int part) {
		super.transformEquippedAimed(part);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
		GL11.glTranslatef(0, 0, 0.2f);
	}

	@Override
	protected void GL_TransformReload_Equipped(int part, float prog, byte attackType) {
		float offsetZ= 0.3f; //0.9f;
		float offsetY= 0.5f; //0.3f;
		GL11.glTranslatef(0,offsetY, offsetZ);
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		GL11.glRotatef(35.0f*-y,1.0f,0,0);
		//GL11.glRotatef(-40.0f*y,0,1,0);
		GL11.glTranslatef(0,-offsetY, -offsetZ);
	}

}
