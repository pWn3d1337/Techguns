package techguns.client.renderer.item;

import org.lwjgl.opengl.GL11;

import net.minecraft.util.ResourceLocation;
import techguns.client.models.ModelMultipart;
import techguns.client.renderer.TGRenderHelper;
import techguns.client.renderer.FX.ScreenEffect;
import techguns.debug.Keybinds;

public class RenderMibGunAkimbo extends RenderGunAkimbo {

	public RenderMibGunAkimbo(ModelMultipart model, ResourceLocation texture, float scale, float x, float y, float z, boolean handledamage,
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
		GL11.glScalef(2.5f, 2.5f, 2.5f);
		if (leftGun){
			GL11.glTranslatef(2.05f,0,0);
		}
	}

	@Override
	protected void GL_Transform_Equipped(int part, float prog, byte attackType, boolean leftGun) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(135.0f, 0, 1.0f, 0);
		GL11.glScalef(1.5f, 1.5f, 1.5f);
		GL11.glTranslatef(0.05f, -0.80f, 0.7f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
	}

	@Override
	protected void GL_Transform_Ground(int part, boolean leftGun) {
		if (leftGun){
			GL11.glTranslatef(0.2f, 0.2f, 0);
		}
		
		GL11.glRotatef(180, 1.0f, 0, 0);
		//GL11.glScaled(1.3f, 1.3f, 1.3f);
		GL11.glTranslatef(0.0f, -0.6f, 0f);
	}

	@Override
	protected void GL_Transform_Icon(int part, boolean leftGun) {
		GL11.glScalef(0.9f, 0.9f, 0.9f);
		if (leftGun){
			GL11.glTranslatef(0,0,-0.5f);
		}
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glScalef(1.4f, 1.4f, 1.4f);
		GL11.glRotatef(90, 0.0f, 1, 0);
		
		if (leftGun){
			GL11.glTranslatef(0.3f, 0.4f, -0.6f);
		}
		
		GL11.glTranslatef(0.1f, -0.8f, 0.4f);
	}

	@Override
	protected void GL_Transform_reloading(int part, float prog, byte attackType, boolean leftGun) {
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);

		GL11.glRotatef(35.0f*-y,1.0f,0,0);

		//GL11.glTranslatef(-0.55f, -0.45f, -0.8f);

		this.GL_Transform_Translate(part);
	}

	@Override
	protected void GL_Transform_firing(int part, float prog, byte attackType, boolean leftGun) {
		float r = 0;
		if (attackType ==0 && !leftGun || attackType==1 && leftGun){
			r= Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f));
		}

		if (r!=0){
			float  y= 0.4f;
			GL11.glTranslatef(0, y, 0);
			GL11.glRotatef(r*25, 1, 0, 0f);
			GL11.glTranslatef(0,-y, 0);
		}
		
		this.GL_Transform_Translate(part);
	}

	@Override
	protected void GL_Transform_Translate(int part, boolean leftGun) {
		//GL11.glTranslatef(0.1f, 0, -0.5f);
		GL11.glTranslatef(-0.75f, -0.45f, -0.8f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
	}

	@Override
	protected void GL_TransformReload_Equipped(int part, float prog, byte attackType, boolean leftGun) {
		float offsetZ= 0.3f; //0.9f;
		float offsetY= 0.5f; //0.3f;
		GL11.glTranslatef(0,offsetY, offsetZ);
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		GL11.glRotatef(35.0f*-y,1.0f,0,0);
		//GL11.glRotatef(-40.0f*y,0,1,0);
		GL11.glTranslatef(0,-offsetY, -offsetZ);
	}

	@Override
	public void renderScope() {
	}

	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
		TGRenderHelper.enableAlphaBlendAdditive();
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor3f(0.2f, 1.0f, 0.7f);
		if (attackType==0){
			ScreenEffect.muzzleFlashAlienBlaster.doRender(prog, -2.15f, -2.45f, -0.85f, 1.0f, 0, 90.0f, 0);
		} else {
			ScreenEffect.muzzleFlashAlienBlaster.doRender(prog, 2.55f, -2.45f, -0.85f, 1.0f, 0, 90.0f, 0);
		}
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		 GL11.glDisable(GL11.GL_ALPHA_TEST);
		TGRenderHelper.disableAlphaBlend();
	
	}

	@Override
	protected void GL_TransformRecoil_Equipped(int part, float prog, byte attackType) {
		if (attackType==0){
			float degree=-15;
			float offsetZ= 0.3f; //+0.9f;
			float offsetY= 0.5f; //(float) Keybinds.Y;
			if(prog<0.5f){
				GL11.glTranslatef(0,offsetY, offsetZ);
				float progress=prog*2.0f;
				
				if(progress<0.25f){
					GL11.glRotatef(progress*4*degree, 1, 0, 0);
				} else {
					GL11.glRotatef((1-progress)*1.25f*degree, 1, 0, 0);
				}
			/*	if(part==1){
					GL11.glTranslatef(0, 0, 0.15f*progress);
				}*/
				GL11.glTranslatef(0,-offsetY, -offsetZ);
			}
		}
	}
	
}
