package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;
import techguns.client.renderer.FX.ScreenEffect;
import techguns.debug.Keybinds;

public class RenderMac10 extends RenderGunBase {

	public RenderMac10(ModelMultipart model, ResourceLocation texture, float scale, float x, float y, float z) {
		//super(model, texture, scale, x, y, z, handledamage, handleprogress, parts, hasZoom);
		//super(model, texture, scale, x, y, z,true,false,2,false);
		super(model, texture, scale, x, y, z,false,false,1,false);
	}

	@Override
	protected void GL_Transform_FirstPerson(int part,float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(225.0f, 0, 1.0f, 0);
		GL11.glScalef(2.0f, 2.0f, 2.0f);

	}

	@Override
	protected void GL_Transform_Equipped(int part,float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(135.0f, 0, 1.0f, 0);
		GL11.glScalef(1.2f, 1.2f, 1.2f);
		GL11.glTranslatef(0.05f, -0.6f, 0.4f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
	}

	@Override
	protected void GL_Transform_Ground(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glScaled(0.9f,0.9f, 0.9f);
		GL11.glTranslatef(0.0f, -0.5f, -0.3f);
	}

	@Override
	protected void GL_Transform_reloading(int part,float prog, byte attackType) {
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		GL11.glRotatef(50.0f*-y,1.0f,0,0);
		GL11.glTranslatef(-0.55f, -0.45f, -0.8f);
		//GL11.glRotatef(0, 1.0f*-y, 0, 1.0f);
		//GL11.glTranslatef(0.1f-y, y/2.0f, -0.5f);

	}

	/*@Override
	protected void GL_Transform_reload_done(int part) {
		GL_Transform_Translate();
	}*/

	@Override
	protected void GL_Transform_Translate(int part) {
		//GL11.glTranslatef(0.1f, 0, -0.5f);
		GL11.glTranslatef(-0.55f, -0.45f, -0.8f);
	}

	@Override
	protected void GL_Transform_firing(int part,float prog, byte attackType) {
		
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f));
		GL11.glRotatef(y*7, 1.0f, 0, 0);
		/*if(prog<0.5f){
			float progslide=prog*2.0f;
			GL11.glTranslatef(-0.55f, -0.45f, (-0.8f)-(y*0.3f)+(0.2f*part*progslide));
		}*/
		GL11.glTranslatef(-0.55f, -0.45f, (-0.8f)-(y*(0.3f+0.2f*part)));
	}

	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
		ScreenEffect.muzzleFlash2.doRender(prog, -0.65f,-1.25f,1.6f, 1.0f);
	}

	@Override
	protected void GL_Transform_Icon(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glScalef(1.1f, 1.1f, 1.1f);
		GL11.glRotatef(90, 0.0f, 1, 0);
		GL11.glTranslatef(0.3f, -0.5f, 0f);
	}

	@Override
	public void renderScope() {
	}

	@Override
	protected void GL_TransformRecoil_Equipped(int part, float prog, byte attackType) {
		float degree=-10;
		float offsetZ=+0.9f;
		if(prog<0.5f){
			GL11.glTranslatef(0,0, offsetZ);
			float progress=prog*2.0f;
			
			if(progress<0.25f){
				GL11.glRotatef(progress*4*degree, 1, 0, 0);
			} else {
				GL11.glRotatef((1-progress)*1.25f*degree, 1, 0, 0);
			}
			if(part==1){
				GL11.glTranslatef(0, 0, 0.15f*progress);
			}
			GL11.glTranslatef(0,0, -offsetZ);
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
		float offsetZ=0.9f;
		float offsetY=0.3f;
		GL11.glTranslatef(0,offsetY, offsetZ);
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		GL11.glRotatef(40.0f*-y,1.0f,0,0);
		//GL11.glRotatef(-40.0f*y,0,1,0);
		GL11.glTranslatef(0,-offsetY, -offsetZ);
	}
	
}
