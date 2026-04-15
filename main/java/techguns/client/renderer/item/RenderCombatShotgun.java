package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;
import techguns.client.renderer.TGRenderHelper;
import techguns.client.renderer.FX.ScreenEffect;

public class RenderCombatShotgun extends RenderGunBase {

	public RenderCombatShotgun(ModelMultipart model, ResourceLocation texture,
			float scale, float x, float y, float z) {
		super(model, texture, scale, x, y, z,true,false,2,false);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void GL_Transform_FirstPerson(int part, float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(-45, 0, 1.0f, 0);
		GL11.glScalef(2.25f, 2.25f, 2.25f);
	}

	@Override
	protected void GL_Transform_Equipped(int part, float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(225, 0, 1.0f, 0);
		//GL11.glRotatef(90, 0, 0, 1.0f);
		GL11.glScalef(1.15f, 1.15f, 1.15f);
		
		//X(+/-): Front/Back
		//Y(+/-): Down/Up
		//Z(+/-): left/right
		GL11.glTranslatef(-0.8f, -0.40f, 0.0f);
	}

	@Override
	protected void GL_Transform_Ground(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(90, 0, 1.0f, 0);
		GL11.glScaled(0.75f, 0.75f, 0.75f);
		GL11.glTranslatef(0.0f, -0.30f, 0.0f);
	}

	@Override
	protected void GL_Transform_reloading(int part, float prog, byte attackType) {
		float f = 0.8f;
		if (prog < f) {
			float y = ((float) Math.min(0.66f,Math.sin((prog/f)*Math.PI))*1.5f);
			GL11.glRotatef(10.0f*y, 0, 0, 1.0f);
			GL11.glRotatef(50.0f*-y, 0f, 1.0f, 0f);
			GL11.glRotatef(10.0f*-y, 1.0f, 0f, 0f);
			GL11.glTranslatef(0.1f+y, -0.25f+(y*0.5f), -0.5f+(y*0.25f));
		}else {
			float y = ((float) Math.sin(((prog-f)/(1-f))*Math.PI));
			GL11.glRotatef(8.0f*y, 0, 0, 1.0f);
			GL11.glRotatef(18.0f*-y, 0f, 1.0f, 0f);
			GL11.glRotatef(6.0f*-y, 1.0f, 0f, 0f);
			if(part==1){
				GL11.glTranslatef(0.1f+(y*-0.10f), -0.25f+(y*0.05f), -0.5f+(y*0.05f));
			}else{
				GL11.glTranslatef(0.1f+(y*0.50f), -0.25f+(y*0.05f), -0.5f+(y*0.05f));
			}
		}
	}

	/*@Override
	protected void GL_Transform_reload_done() {
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
			GL11.glRotatef(y*20, 0, 0, 1.0f);
			GL11.glTranslatef(0.1f+(y*1.2f), -0.25f, -0.5f);
		}else { //rechamber
			float y = ((float) Math.sin((prog-0.25f)*1.333f*Math.PI));
			GL11.glRotatef(8.0f*y, 0, 0, 1.0f);
			GL11.glRotatef(18.0f*-y, 0f, 1.0f, 0f);
			GL11.glRotatef(6.0f*-y, 1.0f, 0f, 0f);
			if(part==1){
				GL11.glTranslatef(0.1f+(y*-0.10f), -0.25f+(y*0.05f), -0.5f+(y*0.05f));
			} else {
				GL11.glTranslatef(0.1f+(y*0.50f), -0.25f+(y*0.05f), -0.5f+(y*0.05f));
			}
		}

	}

	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
		//TGRenderHelper.enableAlphaBlendAdditive();
		ScreenEffect.muzzleFlash.doRender(prog, -0.4f, -2.5f, 0.7f, 1.85f);
		//ScreenEffect.muzzleFlashNew2.doRender(prog, -0.4f, -2.5f, 0.7f, 2.15f);
	//GRenderHelper.disableAlphaBlend();
	}

	@Override
	protected void GL_Transform_Icon(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(180, 0, 1.0f, 0);
		GL11.glScalef(0.6f, 0.6f, 0.6f);
		GL11.glTranslatef(-0.3f, 0.1f, 0.0f);
	}

	@Override
	public void renderScope() {
	}

	@Override
	protected void GL_TransformReload_Equipped(int part, float prog, byte attackType) {
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		if (part==1){
			if(prog>0.8f){
				GL11.glTranslatef(-0.5f*prog/0.8f, 0, 0);
			}
		}
		GL11.glRotatef(30.0f*y,1.0f,0,0);
		GL11.glRotatef(40.0f*y,0,1,0);
	}

	@Override
	protected void GL_TransformRecoil_Equipped(int part, float prog, byte attackType) {
		//float offsetZ=-0.9f;
		//float offsetY=0.3f;
		
		
		float degree=-20;
		if(prog<0.5f){

			float progress=prog*2.0f;
			
			if(progress<0.25f){
				GL11.glRotatef(progress*4*degree, 0, 0, 1);
			} else {
				GL11.glRotatef((1-progress)*1.25f*degree, 0, 0, 1);
			}
		} else {
			if (part==1){
				float progress=(prog-0.5f)*2.0f;
				float y = Math.min(0.5f,(Math.abs((0.5f-progress))-0.5f)*2.0f);
				GL11.glTranslatef(0.45f*y, 0, 0);
			}
		}
	}
	
	@Override
	protected void transformEquippedAimed(int part) {
		super.transformEquippedAimed(part);
		GL11.glTranslated(0,0.25f,-0.25f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);		
	}
}
