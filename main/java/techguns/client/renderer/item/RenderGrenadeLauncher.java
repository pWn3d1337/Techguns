package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;
import techguns.client.renderer.FX.ScreenEffect;

public class RenderGrenadeLauncher extends RenderGunBase{

	public RenderGrenadeLauncher(ModelMultipart model,
			ResourceLocation texture, float scale, float x, float y, float z) {
		super(model, texture, scale, x, y, z, true, true, 2, false);
	}

	@Override
	protected void GL_Transform_FirstPerson(int part,float prog, byte attackType) {
		//GL11.glRotatef(180, 1.0f, 0, 0);
		//GL11.glRotatef(180, 0, 0, 1.0f);
		//GL11.glRotatef(-135, 0, 1.0f, 0);
		GL11.glRotatef(45, 0, 1.0f, 0);
		GL11.glScalef(2.0f, 2.0f, 2.0f);
	}

	@Override
	protected void GL_Transform_Equipped(int part,float prog, byte attackType) {
		//GL11.glRotatef(180, 1.0f, 0, 0);
		//GL11.glRotatef(180, 0, 0, 1.0f);
		//GL11.glRotatef(-45, 0, 1.0f, 0);
		GL11.glRotatef(135, 0,  1.0f, 0);
		GL11.glScalef(1.3f, 1.3f, 1.3f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
		GL11.glTranslatef(-0.2f, 0.35f, 0);
	}

	@Override
	protected void GL_Transform_Ground(int part) {
		GL11.glScalef(0.80f, 0.80f, 0.80f);
		//GL11.glRotatef(180, 1.0f, 0, 0);
		//GL11.glRotatef(180, 0, 0, 1.0f);
		GL11.glRotatef(-90, 0, 1.0f, 0);
		GL11.glTranslated(0,0.35f,0);
	}

	@Override
	protected void GL_Transform_reloading(int part,float prog, byte attackType) {
		//float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		float y = ((float) Math.min(0.5f,Math.sin(prog*Math.PI))*2f);
		GL11.glRotatef(25.0f*-y, 0, 0, 1.0f);
		GL11.glRotatef(25.0f*-y, 0, 1.0f, 0);
		GL11.glRotatef(30.0f*y, 0f, 1.0f, 0f);
		
		GL_Transform_Translate(part);
		GL11.glTranslatef((-y*0.25f), (-y*0.20f), (y*-0.15f));
	}

	/*@Override
	protected void GL_Transform_reload_done(int part) {
		GL11.glTranslatef(0.1f, -0.1f, -0.5f);
	}*/

	@Override
	protected void GL_Transform_Translate(int part) {
		GL11.glTranslatef(1.05f, 0.1f, 0.85f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
	}

	@Override
	protected void GL_Transform_firing(int part,float prog, byte attackType) {
		//0,5+(SIN((WURZEL(B3)+0,75)*2*PI())/2)
		//0,5+(SIN((B2/2-0,25)*PI()*2))/2
		
		float y=(float) (0.5d+(Math.sin((Math.sqrt((double)prog)+0.75d)*2.0d*Math.PI)/2.0));
		//float y = 0.0f;
		float y2 = (float) (0.5d+(Math.sin((prog*0.5 - 0.25)*Math.PI))*0.5);
		if (part == 1) {
			float offsetZ = 0.85f;//(float) Keybinds.Z; //0.8
			float offsetY = -0.15f; //(float) Keybinds.Y; //-0.15
			GL11.glTranslatef(0, offsetY, offsetZ);
			GL11.glRotatef(y2*60.0f, 1.0f, 0, 0);
			GL11.glTranslatef(0, -offsetY, -offsetZ);
		}
		
		GL11.glRotatef(y*10, 0, 0, 1.0f);
		GL11.glTranslatef(1.05f-(y*0.25f), 0.1f+(y*0.10f), 0.85f);
	}

	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
		//ScreenEffect.muzzleFlash.doRender(prog, -0.55f, -2.15f, 1.05f, 1.85f);
		ScreenEffect.muzzleFlash.doRender(prog, -0.6f, -2.2f, 1.6f, 1.5f);
		//ScreenEffect.muzzleFlash.doRender(prog, (float)Keybinds.X, (float)Keybinds.Y, (float)Keybinds.Z, 1.25f);
	}

	@Override
	protected void GL_Transform_Icon(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		//GL11.glRotatef(180, 0, 1.0f, 0);
		GL11.glRotatef(180, 0, 0, 1.0f);
		GL11.glScalef(0.7f, 0.7f, 0.7f);
		
		GL11.glTranslatef(0, 0.15f,-0.15f);
	}

	@Override
	public void renderScope() {
	}

	@Override
	protected void GL_TransformRecoil_Equipped(int part, float prog, byte attackType) {
		float degree=40;
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
		GL11.glRotatef(40.0f*y,0,0,1);
		//GL11.glRotatef(40.0f*y,0,1,0);
		GL11.glTranslatef(-offsetX,-offsetY, 0);
	}

	@Override
	protected void transformEquippedAimed(int part) {
		super.transformEquippedAimed(part);
		//GL11.glRotated(90.0f-21.0f, 0, 0, 1);
		//GL11.glTranslated(0.15f, 0.15f,0.0f);
		GL11.glTranslated(0,0.15f,0);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);		
	}

}
