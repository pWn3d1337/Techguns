package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;
import techguns.client.renderer.FX.ScreenEffect;
import techguns.debug.Keybinds;

public class RenderMac10Akimbo extends RenderGunAkimbo {

	public RenderMac10Akimbo(ModelMultipart model, ResourceLocation texture, float scale, float x, float y, float z, boolean handledamage,
			boolean handleprogress, int parts, boolean hasZoom) {
		super(model, texture, scale, x, y, z, handledamage, handleprogress, parts, hasZoom);
	}

	@Override
	protected boolean is2ndGun(int part) {
		return part >=1;
	}

	@Override
	protected void GL_Transform_FirstPerson(int part, float prog, byte attackType, boolean leftGun) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(225.0f, 0, 1.0f, 0);
		GL11.glScalef(2.0f, 2.0f, 2.0f);
		if (leftGun){
			GL11.glTranslatef(1.85f,0,0);
		}
	}

	@Override
	protected void GL_Transform_Equipped(int part, float prog, byte attackType, boolean leftGun) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(135.0f, 0, 1.0f, 0);
		GL11.glScalef(1.2f, 1.2f, 1.2f);
		//GL11.glTranslatef(0.05f, -0.55f, 0.2f);
		GL11.glTranslatef(0.05f, -0.6f, 0.4f);
		/*if (leftGun){
			GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
		}*/
	}

	@Override
	protected void GL_Transform_Ground(int part, boolean leftGun) {
		if (leftGun){
			GL11.glTranslatef(0.2f, 0.2f, 0);
		}
		
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glScalef(0.9f,0.9f,0.9f);
		GL11.glTranslatef(0.0f, -0.5f, -0.3f);
	}

	@Override
	protected void GL_Transform_Icon(int part, boolean leftGun) {
		if (leftGun){
			GL11.glTranslatef(0,0,-0.5f);
		}
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glScalef(1.1f, 1.1f, 1.1f);

		GL11.glRotatef(90, 0.0f, 1, 0);
		
		if (leftGun){
			//GL11.glRotatef(90.0f, 0.0f, 1, 0);
			//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
			GL11.glTranslatef(0.3f, 0.4f, -0.6f);
		}
		
		GL11.glTranslatef(0.3f, -0.7f, 0.15f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
	}

	@Override
	protected void GL_Transform_reloading(int part, float prog, byte attackType, boolean leftGun) {
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		GL11.glRotatef(50.0f*-y,1.0f,0,0);
		GL11.glTranslatef(-0.55f, -0.45f, -0.8f);
	}

	@Override
	protected void GL_Transform_firing(int part, float prog, byte attackType, boolean leftGun) {
		float y = 0;
//		if (attackType ==0 && !leftGun || attackType==1 && leftGun){
			y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f));
//		}
		GL11.glRotatef(y*10, 1.0f, 0, 0);
		GL11.glTranslatef(-0.55f, -0.45f, (-0.8f)-(y*(0.3f+0.2f*part)));
	}

	@Override
	protected void GL_Transform_Translate(int part, boolean leftGun) {
		GL11.glTranslatef(-0.55f, -0.45f, -0.8f);
	}

	/*@Override
	protected void GL_TransformReload_Equipped(int part, float prog, byte attackType, boolean leftGun) {
		float degree=-15;
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
	}*/

	@Override
	protected void transformEquippedAimed(int part) {
		super.transformEquippedAimed(part);
		GL11.glTranslatef(0, 0, 0.2f);
	}

	@Override
	protected void GL_TransformReload_Equipped(int part, float prog, byte attackType, boolean leftgun) {
		float offsetZ=0.9f;
		float offsetY=0.3f;
		GL11.glTranslatef(0,offsetY, offsetZ);
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		GL11.glRotatef(40.0f*-y,1.0f,0,0);
		GL11.glTranslatef(0,-offsetY, -offsetZ);
	}
	
	
	
	
	@Override
	public void renderScope() {
	}

	@Override
	protected void GL_TransformRecoil_Equipped(int part, float prog, byte attackType) {
	//	if (attackType==0){
			float offsetZ=1.0f;
			float offsetY=1.0f;
			//float offsetX=1
			GL11.glTranslatef(0,offsetY,offsetZ);
			if(prog<0.3f){
				GL11.glRotatef(prog/0.3f*-15, 1, 0, 0);
			}
			GL11.glTranslatef(0, -offsetY, -offsetZ);
	//	}
	}

	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
		//System.out.println("DrawMuzzle type:"+attackType);
	//	if (attackType==0){
			ScreenEffect.muzzleFlash2.doRender(prog, -0.65f,-1.25f,1.6f, 1.0f);
	//	} else {
			ScreenEffect.muzzleFlash2.doRender(prog, -0.65f,-1.25f,-2.0f, 1.0f);
	//	}
	}

}
