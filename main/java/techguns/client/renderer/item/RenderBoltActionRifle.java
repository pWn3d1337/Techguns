package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;
import techguns.client.renderer.TGRenderHelper;
import techguns.client.renderer.FX.ScreenEffect;

public class RenderBoltActionRifle extends RenderGunBase {

	public RenderBoltActionRifle(ModelMultipart model, ResourceLocation texture,
			float scale, float x, float y, float z) {
		super(model, texture, scale, x, y, z,false,false,1,true);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void GL_Transform_FirstPerson(int part, float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(-45, 0, 1.0f, 0);
		GL11.glScalef(1.5f, 1.5f, 1.5f);
		GL11.glTranslatef(-0.09f,-0.37f,0.07f);
	}

	@Override
	protected void GL_Transform_Equipped(int part, float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(225, 0, 1.0f, 0);
		//GL11.glRotatef(90, 0, 0, 1.0f);
		GL11.glScalef(1.5f, 1.5f, 1.5f);
		
		//X(+/-): Front/Back
		//Y(+/-): Down/Up
		//Z(+/-): left/right
		GL11.glTranslatef(-0.65f, -0.25f, 0.0f);
	}

	@Override
	protected void GL_Transform_Ground(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(90, 0, 1.0f, 0);
		GL11.glScalef(0.9f, 0.9f, 0.9f);
		GL11.glTranslatef(0.0f, -0.25f, 0.0f);
	}

	@Override
	protected void GL_Transform_reloading(int part, float prog, byte attackType) {
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2f);
		GL11.glRotatef(10.0f*-y, 0, 0, 1.0f);
		GL11.glRotatef(50.0f*y, 0f, 1.0f, 0f);
		GL11.glRotatef(10.0f*y, 1.0f, 0f, 0f);
		GL11.glTranslatef(0.1f-y, -0.25f-(y*0.5f), -0.5f-(y*0.25f));
	}

	/*@Override
	protected void GL_Transform_reload_done(int part) {
		//GL11.glTranslatef(0.1f, 0, -0.5f);
		GL_Transform_Translate();
	}*/

	@Override
	protected void GL_Transform_Translate(int part) {
		GL11.glTranslatef(0.1f, -0.25f, -0.5f);
	}

	@Override
	protected void GL_Transform_firing(int part, float prog, byte attackType) {
		if (prog < 0.25f) { //recoil
			float y= Math.min(0.5f,(Math.abs((0.5f-prog*4f))-0.5f));
			GL11.glRotatef(y*25, 0, 0, 1.0f);
			GL11.glTranslatef(0.1f+y, -0.25f, -0.5f);
		}else { //rechamber
			float y = ((float) Math.sin((prog-0.25f)*1.333f*Math.PI));
			GL11.glRotatef(4.0f*y, 0, 0, 1.0f);
			GL11.glRotatef(16.0f*-y, 0f, 1.0f, 0f);
			GL11.glRotatef(4.0f*-y, 1.0f, 0f, 0f);
			GL11.glTranslatef(0.1f+(y*0.25f), -0.25f+(y*0.05f), -0.5f+(y*0.05f));
		}

	}

	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
		ScreenEffect.muzzleFlash2.doRender(prog, -0.4f, -3.0f, 1.15f, 1.85f);
	//	TGRenderHelper.enableAlphaBlendAdditive();
		//ScreenEffect.muzzleFlash.doRender(prog, -0.4f, -2.5f, 0.7f, 1.85f);
	//	ScreenEffect.muzzleFlashNew2.doRender(prog, -0.4f, -3.0f, 1.15f, 2.05f);
	//	TGRenderHelper.disableAlphaBlend();
	}

	@Override
	protected void GL_Transform_Icon(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(180, 0, 1.0f, 0);
		GL11.glScalef(0.6f, 0.6f, 0.6f);
		GL11.glTranslatef(-0.4f, 0.1f, 0.0f);
	}

	@Override
	public void renderScope() {
		GL11.glTranslatef(1.795f, 0f, -0.685f);
		ScreenEffect.sniperScope.doRender(0.0f, 0,0,0, ScreenEffect.SNIPER_SCOPE_SCALE);
	}

	@Override
	protected void GL_TransformRecoil_Equipped(int part, float prog, byte attackType) {
		float offsetZ=-0.9f;
		float offsetY=0.3f;
		GL11.glTranslatef(0,offsetY, offsetZ);
		/*if(prog<0.3f){
			GL11.glRotatef(prog/0.3f*-30, 0, 0, 1);
		}*/
		float degree=-20;
		if(prog<0.5f){

			float progress=prog*2.0f;
			
			if(progress<0.25f){
				GL11.glRotatef(progress*4*degree, 0, 0, 1);
			} else {
				GL11.glRotatef((1-progress)*1.25f*degree, 0, 0, 1);
			}
		}
		
		GL11.glTranslatef(0,-offsetY, -offsetZ);
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
		GL11.glTranslated(0,0.2,-0.25f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);		
	}

	
	
}
