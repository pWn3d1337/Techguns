package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;
import techguns.client.renderer.FX.ScreenEffect;

public class RenderRevolverAkimbo extends RenderGunAkimbo {

	public RenderRevolverAkimbo(ModelMultipart model, ResourceLocation texture, float scale, float x, float y, float z, boolean handledamage,
			boolean handleprogress, int parts, boolean hasZoom) {
		super(model, texture, scale, x, y, z, handledamage, handleprogress, parts, hasZoom);
	}

	@Override
	protected boolean is2ndGun(int part) {
		return part >=1;
	}


	@Override
	protected void GL_Transform_FirstPerson(int part,float prog, byte attackType, boolean leftGun) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(-45, 0, 1.0f, 0);
		GL11.glScalef(1.5f, 1.5f, 1.5f);
		if (leftGun){
			GL11.glTranslatef(0,0,1.75f);
		}
	}

	@Override
	protected void GL_Transform_Equipped(int part,float prog, byte attackType, boolean leftGun) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(225, 0, 1.0f, 0);
		//GL11.glRotatef(90, 0, 0, 1.0f);
		GL11.glScalef(0.85f, 0.85f, 0.85f);
		//X(+/-): Front/Back
		//Y(+/-): Down/Up
		//Z(+/-): left/right
		GL11.glTranslatef(-1.4f, -0.8f, 0.0f);
	}

	@Override
	protected void GL_Transform_Ground(int part, boolean leftGun) {
		if (leftGun){
			GL11.glTranslatef(0.2f, 0.2f, 0);
		}
		
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(90, 0, 1.0f, 0);
		GL11.glTranslatef(0.0f, -0.35f, 0.0f);
		GL11.glScalef(0.8f, 0.8f, 0.8f);
	}

	@Override
	protected void GL_Transform_reloading(int part,float prog, byte attackType, boolean leftGun) {
		//float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2f);
		float y = ((float) Math.min(0.66f,Math.sin(prog*Math.PI))*1.5f);
		if (leftGun){
			GL11.glRotatef(25.0f*y, 0, 0, 1.0f);
			GL11.glRotatef(15.0f*y, 0f, 1.0f, 0f);
		} else {
			GL11.glRotatef(25.0f*y, 0, 0, 1.0f);
			GL11.glRotatef(15.0f*-y, 0f, 1.0f, 0f);
		}
		
		//GL11.glRotatef(10.0f*y, 1.0f, 0f, 0f);
		GL11.glTranslatef(0.2f+(y*0.6f), -0.5f+(y*0.30f), -0.45f+(y*0.25f));
	}

	/*@Override
	protected void GL_Transform_reload_done(int pa) {
		//GL11.glTranslatef(0.1f, 0, -0.5f);
		GL_Transform_Translate();
	}*/

	@Override
	protected void GL_Transform_Translate(int part, boolean leftGun) {
		//X(+/-) = Up/Down
		//Y(+/-) = Close/Far
		//Z(+/-) = left/right
		GL11.glTranslatef(0.20f, -0.5f, -0.45f);
	}

	@Override
	protected void GL_Transform_firing(int part,float prog, byte attackType, boolean leftGun) {
		float y=0;
		if (attackType ==0 && !leftGun || attackType==1 && leftGun){
			y= Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f));
		}
		GL11.glRotatef(y*35, 0, 0, 1.0f);
		GL11.glTranslatef(0.2f+(y*0.5f), -0.5f+(y*0.4f), -0.45f);
	}

	@Override
	protected void GL_Transform_Icon(int part, boolean leftGun) {
		GL11.glScalef(0.8f, 0.8f, 0.8f);
		if (leftGun){
			GL11.glTranslatef(0,0,-0.5f);
		}
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(180, 0, 1.0f, 0);
		GL11.glScalef(1.0f, 1.0f, 1.0f);
		
		if (leftGun){
			GL11.glTranslatef(0.3f, 0.4f, -0.6f);
		}
		
		GL11.glTranslatef(-0.4f, -0.2f, 0.0f);
	}

	@Override
	protected void GL_TransformRecoil_Equipped(int part, float prog, byte attackType) {
		if (attackType==0){
			float degree=-30;
			if(prog<0.5f){
				float offsetZ=1.0f;
				float offsetY=0.25f;
				GL11.glTranslatef(0,offsetY, offsetZ);
					float progress=prog*2.0f;
					
					if(progress<0.25f){
						GL11.glRotatef(progress*4*degree, 0, 0, 1);
					} else {
						GL11.glRotatef((1-progress)*1.25f*degree, 0, 0, 1);
					}
				GL11.glTranslatef(0,-offsetY, -offsetZ);
			}
		}
	}

	@Override
	protected void GL_TransformReload_Equipped(int part, float prog, byte attackType, boolean leftGun) {
		float offsetZ=1.0f;
		float offsetY=0.25f;
		GL11.glTranslatef(0,offsetY, offsetZ);
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		GL11.glRotatef(-40.0f*y,0,0,1.0f);
		//GL11.glRotatef(40.0f*y,0,1,0);
		GL11.glTranslatef(0,-offsetY, -offsetZ);
	}

	@Override
	protected void transformEquippedAimed(int part) {
		super.transformEquippedAimed(part);
		//GL11.glRotated(90.0f-21.0f, 0, 0, 1);
		GL11.glTranslated(0.0f, -0.05f,0.2f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);		
	}
	

	@Override
	public void renderScope() {
	}

	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
		if (attackType==0){
			ScreenEffect.muzzleFlash.doRender(prog, -0.5f, -2.0f, 1.05f, 1.35f);
		} else {
			ScreenEffect.muzzleFlash.doRender(prog, -0.5f, -2.0f, -1.4f, 1.35f);
		}

	}

}
