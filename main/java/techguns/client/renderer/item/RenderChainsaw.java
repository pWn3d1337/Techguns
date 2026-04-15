package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;

public class RenderChainsaw extends RenderGunBase {

	public RenderChainsaw(ModelMultipart model, ResourceLocation texture,
			float scale, float x, float y, float z) {
		super(model, texture, scale, x, y, z,true,true,1,false);
	}

	@Override
	protected void GL_Transform_FirstPerson(int part,float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(-45, 0, 1.0f, 0);
		GL11.glScalef(1.35f, 1.35f, 1.35f);
	}

	@Override
	protected void GL_Transform_Equipped(int part,float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(225, 0, 1.0f, 0);
		GL11.glScalef(1.30f, 1.30f, 1.30f);

		GL11.glTranslatef(-1.1f, 0.55f, -0.1f);

		
	}

	@Override
	protected void GL_Transform_Ground(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(90, 0, 1.0f, 0);
		GL11.glScalef(0.7f, 0.7f, 0.7f);
		GL11.glTranslatef(0.0f, -0.60f, 0.0f);
	}

	@Override
	protected void GL_Transform_reloading(int part,float prog, byte attackType) {
		float y = ((float) Math.min(0.75f,Math.sin(prog*Math.PI))*1.333f);
		GL11.glRotatef(32.0f*-y, 0, 0, 1.0f);
		GL11.glRotatef(15.0f*-y, 0f, 1.0f, 0f);
		//GL11.glRotatef(10.0f*y, 1.0f, 0f, 0f);
		GL11.glTranslatef((y*0.05f), (y*0.4f), (y*-0.20f));
		//GL11.glTranslated(y*Keybinds.X, y*Keybinds.Y, y*Keybinds.Z);
		GL_Transform_Translate(part);

	}

	/*@Override
	protected void GL_Transform_reload_done(int part) {
		GL_Transform_Translate(part);
	}*/

	@Override
	protected void GL_Transform_Translate(int part) {
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
		GL11.glTranslatef(1.1f, 0f, -1.6f);
		//GL11.glRotated(-25, 0, 0, 1);
		
	}

	@Override
	protected void GL_Transform_firing(int part,float prog, byte attackType) {
		this.GL_Transform_Translate(part);
		if (attackType == 0){
			double y = Math.sin(prog*Math.PI*2.0);
			GL11.glTranslated(y*0.15, 0, 0);
			GL11.glRotated(-5*y, 0, 0, 1);
			GL11.glRotated(-2.5*y, 0, 1, 0);
			GL11.glRotated(-1.5*y, 1, 0, 0);
		}else {
			double y = Math.pow(Math.sin(prog*Math.PI),2.0);
			GL11.glTranslated(y*0.3, 0, 0);
			GL11.glRotated(-10*y, 0, 0, 1);
			GL11.glRotated(-5*y, 0, 1, 0);
			GL11.glRotated(-2.5*y, 1, 0, 0);
		
		}
	}
	

	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
		//ScreenEffect.muzzleFlash2.doRender(prog, -0.75f, -2.2f, -0.45f, 1.65f,0,90.0f,0);
	}

	@Override
	protected void GL_Transform_Icon(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(180, 0, 1.0f, 0);
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		GL11.glTranslatef(-0.3f, 0.3f, -0.4f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
	}

	@Override
	public void renderScope() {
	}

	@Override
	protected void GL_TransformRecoil_Equipped(int part, float prog, byte attackType) {
		double p2 = Math.min(prog*15.0, 1.0);
		if (part == 1) {
			GL11.glTranslated(-0.30f+(p2*0.3), 0, 0);
		}
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
		GL11.glTranslated(0.0f,-0.15f,0.3f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);		
	}
}
