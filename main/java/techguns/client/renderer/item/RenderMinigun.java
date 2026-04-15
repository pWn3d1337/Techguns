package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;
import techguns.client.renderer.FX.ScreenEffect;

public class RenderMinigun extends RenderGunBase {

	public RenderMinigun(ModelMultipart model, ResourceLocation texture,
			float scale, float x, float y, float z) {
		super(model, texture, scale, x, y, z,true,true,2,false);
	}

	@Override
	protected void GL_Transform_FirstPerson(int part,float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(-45, 0, 1.0f, 0);
		GL11.glScalef(1.5f, 1.5f, 1.5f);

	}

	@Override
	protected void GL_Transform_Equipped(int part,float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(225, 0, 1.0f, 0);
		//GL11.glRotatef(90, 0, 0, 1.0f);
		GL11.glScalef(1.25f, 1.25f, 1.25f);
		//X(+/-): Front/Back
		//Y(+/-): Down/Up
		//Z(+/-): left/right
		GL11.glTranslatef(-0.6f, 0, 0);
		
		if (part==1){
			GL11.glRotatef(360.0f*prog, 1,0,0);
		}
	}

	@Override
	protected void GL_Transform_Ground(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(90, 0, 1.0f, 0);
		GL11.glScalef(0.8f, 0.8f, 0.8f);
		GL11.glTranslatef(0.0f, -0.60f, 0.0f);
	}

	@Override
	protected void GL_Transform_reloading(int part,float prog, byte attackType) {
		float y = ((float) Math.min(0.75f,Math.sin(prog*Math.PI))*1.333f);
		GL11.glRotatef(25.0f*y, 0, 0, 1.0f);
		GL11.glRotatef(15.0f*-y, 0f, 1.0f, 0f);
		//GL11.glRotatef(10.0f*y, 1.0f, 0f, 0f);
		GL11.glTranslatef(0.70f+(y*0.5f), -0.30f+(y*0.25f), -0.6f+(y*0.25f));

	}

	/*@Override
	protected void GL_Transform_reload_done(int part) {
		GL_Transform_Translate(part);
	}*/

	@Override
	protected void GL_Transform_Translate(int part) {
		//GL11.glTranslatef(0.1f, 0, -0.5f);
		GL11.glTranslatef(0.70f, -0.30f, -0.6f);
		
	}

	@Override
	protected void GL_Transform_firing(int part,float prog, byte attackType) {

		double p = prog;
//		if (prog>0.5f) {
//			p-=0.5;
//		}
		double d = Math.sin(p*2.0*Math.PI);
		GL11.glRotated(-d*1.5, 0, 0, 1.0);
		//GL11.glRotated(-d*0.5, 1.0, 0, 0);
		//GL11.glRotated(-d*0.5, 0, 1.0, 0);
		GL11.glTranslated((-d*0.05), 0, 0);
			
		float y=-0.3f;
		float z=-0.6f;
		GL11.glTranslatef(0, y, z);
		if (part==1){
			GL11.glRotatef(360.0f*prog, 1,0,0);
		}
		GL11.glTranslatef(0, -y, -z);
		this.GL_Transform_Translate(part);

	}

	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
		//ScreenEffect.muzzleFlash_blue.doRender(prog, -0.60f, -2.00f, 1.20f, 1.40f);
		//ScreenEffect.muzzleFlash2.doRender(prog, 1.2f, -2.00f, 1.80f, 1.65f,0,-90.0f,0);
		if (prog>0.5f) {
			prog-=0.5f;
		}
		ScreenEffect.muzzleFlash2.doRender(prog*2.0f, -0.75f, -2.2f, -0.45f, 1.65f,0,90.0f,0);
	}

	@Override
	protected void GL_Transform_Icon(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(180, 0, 1.0f, 0);
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		GL11.glTranslatef(0.25f, 0.55f, -0.6f);
	}

	@Override
	public void renderScope() {
	}

	@Override
	protected void GL_TransformRecoil_Equipped(int part, float prog, byte attackType) {
	}

	@Override
	protected void GL_TransformReload_Equipped(int part, float prog, byte attackType) {
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		GL11.glRotatef(30.0f*y,1.0f,0,0);
		GL11.glRotatef(40.0f*y,0,1,0);
	}
	
	@Override
	protected void transformEquippedAimed(int part) {
		super.transformEquippedAimed(part);
		//GL11.glRotated(90.0f-21.0f, 1, 0, 0);
		//GL11.glTranslated(-0.85f,1.1f,0f);
		GL11.glTranslatef(-0.05f, -0.8f, 0.95f);		
	}


	
}
