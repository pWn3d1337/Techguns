package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;
import techguns.client.renderer.FX.ScreenEffect;

public class RenderHandgun extends RenderGunBase {

	public RenderHandgun(ModelMultipart model, ResourceLocation texture,
			float scale, float x, float y, float z) {
		super(model, texture, scale, x, y, z,false,false,1,false);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void GL_Transform_FirstPerson(int part, float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(-45, 0, 1.0f, 0);
		GL11.glScalef(2.5f, 2.5f, 2.5f);
	}

	@Override
	protected void GL_Transform_Equipped(int part,float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(225, 0, 1.0f, 0);
		//GL11.glRotatef(90, 0, 0, 1.0f);
		GL11.glScalef(1.5f, 1.5f, 1.5f);
		GL11.glTranslatef(-0.3f, -0.45f, 0);
	}

	@Override
	protected void GL_Transform_Ground(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(90, 0, 1.0f, 0);
	}

	@Override
	protected void GL_Transform_reloading(int part,float prog, byte attackType) {
		//float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		float y = (float) Math.sin(prog*Math.PI);
		GL11.glRotatef(70.0f*y, 0, 0, 1.0f);
		GL11.glTranslatef(0.1f+y, -y/2.0f, -0.5f);
	}

	/*@Override
	protected void GL_Transform_reload_done(int part) {
		GL11.glTranslatef(0.1f, 0, -0.5f);
	}*/

	@Override
	protected void GL_Transform_Translate(int part) {
		GL11.glTranslatef(0.1f, 0, -0.5f);
	}

	@Override
	protected void GL_Transform_firing(int part,float prog, byte attackType) {
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f));
		GL11.glRotatef(y*30, 0, 0, 1.0f);
		GL11.glTranslatef(0.1f+y, 0+(y*0.5f), -0.5f);
	}

	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
		//ScreenEffect.muzzleFlash.doRender(prog, -0.6f, -2.0f, 0.6f, 1.85f);
		ScreenEffect.muzzleFlash.doRender(prog, -0.75f, -2.5f, 1.2f, 1.85f);
	}

	@Override
	protected void GL_Transform_Icon(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(180, 0, 1.0f, 0);
		GL11.glScalef(0.7f, 0.7f, 0.7f);
	}

	@Override
	public void renderScope() {
	}

	@Override
	protected void GL_TransformRecoil_Equipped(int part, float prog, byte attackType) {
		float degree=-40;
		if(prog<0.5f){
			float offsetX=-0.2f;
			float offsetY=-0.1f;
			GL11.glTranslatef(offsetX,offsetY, 0);
				float progress=prog*2.0f;
				
				if(progress<0.25f){
					GL11.glRotatef(progress*4*degree, 0, 0, 1);
				} else {
					GL11.glRotatef((1-progress)*1.25f*degree, 0, 0, 1);
				}
			GL11.glTranslatef(-offsetX,-offsetY, 0);
		}
	}

	@Override
	protected void GL_TransformReload_Equipped(int part, float prog, byte attackType) {
		float offsetX=-0.2f;
		float offsetY=-0.1f;
		GL11.glTranslatef(offsetX,offsetY, 0);
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		GL11.glRotatef(-40.0f*y,0,0,1);
		//GL11.glRotatef(40.0f*y,0,1,0);
		GL11.glTranslatef(-offsetX,-offsetY, 0);
	}
	
	@Override
	protected void transformEquippedAimed(int part) {
		super.transformEquippedAimed(part);
		//GL11.glRotated(90.0f-21.0f, 0, 0, 1);
		GL11.glTranslated(0,-0.1f,0);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);		
	}

}
