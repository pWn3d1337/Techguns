package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;
import techguns.client.renderer.TGRenderHelper;
import techguns.client.renderer.FX.ScreenEffect;

public class RenderSonicShotgun extends RenderGunBase {

	public RenderSonicShotgun(ModelMultipart model, ResourceLocation texture,
			float scale, float x, float y, float z) {
		super(model, texture, scale, x, y, z,false,false,1,false);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void GL_Transform_FirstPerson(int part,float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(-45, 0, 1.0f, 0);
		GL11.glScalef(2.0f, 2.0f, 2.0f);
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
		GL11.glTranslatef(-0.85f, -0.6f, 0);
	}

	@Override
	protected void GL_Transform_Ground(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(90, 0, 1.0f, 0);
		GL11.glScalef(0.65f, 0.65f, 0.65f);
		GL11.glTranslatef(0.0f, -0.60f, 0.0f);
	}

	@Override
	protected void GL_Transform_reloading(int part,float prog, byte attackType) {
		float y = ((float) Math.min(0.66f,Math.sin(prog*Math.PI))*1.5f);
		GL11.glRotatef(25.0f*y, 0, 0, 1.0f);
		GL11.glRotatef(15.0f*-y, 0f, 1.0f, 0f);
		GL_Transform_Translate(part);
		GL11.glTranslatef((y*0.5f), (y*0.25f), (y*0.25f));
	}


	@Override
	protected void GL_Transform_Translate(int part) {
		//X(+/-) = Far/Close
		//Y(+/-) = Down/Up
		//Z(+/-) = left/right ?
		
		GL11.glTranslatef(0.2f, -0.2f, -0.7f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
	}

	@Override
	protected void GL_Transform_firing(int part,float prog, byte attackType) {
		float y=(float) (0.5d+(Math.sin((Math.sqrt((double)prog)+0.75d)*2.0d*Math.PI)/2.0));
		
		GL11.glRotatef(-y*8.0f, 0, 0, 1.0f);
		
		GL_Transform_Translate(part);
		GL11.glTranslatef((-y*0.6f), 0, 0);
	}

	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
		TGRenderHelper.enableAlphaBlendAdditive();
		// XYZ: 0.8999999999999999, -1.5, 0.30000000000000004
		//ScreenEffect.muzzleFlashSonic.doRender(prog, (float)Keybinds.X, (float)Keybinds.Y, (float)Keybinds.Z, 2.5f,0,-90.0f,0);
		ScreenEffect.muzzleFlashSonic.doRender(prog, 0.95f, -2.0f, 0.35f, 2.5f,0,-90.0f,0);
		TGRenderHelper.disableAlphaBlend();
	}

	@Override
	protected void GL_Transform_Icon(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(180, 0, 1.0f, 0);
		GL11.glScalef(0.55f, 0.55f, 0.55f);
		GL11.glTranslatef(-0.25f, 0.1f, 0.0f);
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
	
		if(prog<0.25f){
			GL11.glRotatef(prog*4*degree, 0, 0, 1);
		} else {
			GL11.glRotatef((1-prog)*1.25f*degree, 0, 0, 1);
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
		//GL11.glRotated(90.0f-21.0f, 0, 0, 1);
		GL11.glTranslatef(0,0.1f,0.2f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);		
	}
}
