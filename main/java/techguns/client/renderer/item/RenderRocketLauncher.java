package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;

public class RenderRocketLauncher extends RenderGunBase{

	
	public RenderRocketLauncher(ModelMultipart model, ResourceLocation texture,
			float scale, float x, float y, float z, boolean handleDamageValue, boolean handleProgress) {
		super(model, texture, scale, x, y, z, handleDamageValue, handleProgress,1,false);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void GL_Transform_FirstPerson(int part,float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(-45, 0, 1.0f, 0);
		GL11.glScalef(2.25f, 2.25f, 2.25f);
	}

	@Override
	protected void GL_Transform_Equipped(int part,float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(225, 0, 1.0f, 0);
		//GL11.glRotatef(90, 0, 0, 1.0f);
		GL11.glScalef(1.5f, 1.5f, 1.5f);
		//X(+/-): Front/Back
		//Y(+/-): Down/Up
		//Z(+/-): left/right
		GL11.glTranslatef(-1.2f, -1.8f, 0.0f);
	}

	@Override
	protected void GL_Transform_Ground(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(90, 0, 1.0f, 0);
		GL11.glTranslatef(0.0f, -0.25f, 0);
	}

	@Override
	protected void GL_Transform_reloading(int part,float prog, byte attackType) {
		//float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2f);
		float y = ((float) Math.min(0.66f,Math.sin(prog*Math.PI))*1.5f);
		GL11.glRotatef(40.0f*y, 0, 0, 1.0f);
		GL11.glRotatef(20.0f*-y, 0f, 1.0f, 0f);
		//GL11.glRotatef(10.0f*y, 1.0f, 0f, 0f);
		GL11.glTranslatef(0.1f+(y*0.5f), -0.25f+(y*0.15f), -0.5f+(y*0.15f));
	}

	/*@Override
	protected void GL_Transform_reload_done(int part) {
		//GL11.glTranslatef(0.1f, 0, -0.5f);
		GL_Transform_Translate();
	}*/

	@Override
	protected void GL_Transform_Translate(int part) {
		GL11.glTranslatef(0.15f, -0.25f, -0.5f);
	}

	@Override
	protected void GL_Transform_firing(int part,float prog, byte attackType) {
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f));
		GL11.glTranslatef(0.1f+(y*0.75f), -0.25f, -0.5f);
		GL11.glRotatef(25.0f*y, 0, 0, 1.0f);
	}

	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
//		ScreenEffect.muzzleFlash.doRender(prog, -0.4f, -2.5f, 0.4f, 1.85f);
	}

	@Override
	protected void GL_Transform_Icon(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(180, 0, 1.0f, 0);
		GL11.glScalef(0.68f, 0.68f, 0.68f);
		GL11.glTranslatef(-0.3f, 0.3f, 0.0f);
	}

	@Override
	public void renderScope() {
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
		GL11.glRotatef(40.0f*y,1.0f,0,0);
		GL11.glRotatef(60.0f*y,0,1,0);
	}
	
	@Override
	protected void transformEquippedAimed(int part) {
		super.transformEquippedAimed(part);
		//GL11.glRotated(90.0f-21.0f, 0, 0, 1);
		GL11.glTranslatef(0,-1.8f,0.2f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);		
	}
	
}
