package techguns.client.renderer.item;

import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.renderer.FX.ScreenEffect;

public class RenderM4Scoped extends RenderGunScoped {

	public RenderM4Scoped(ModelBase model, ResourceLocation texture,
			float scale, float x, float y, float z) {
		super(model, texture, scale, x, y, z,true, false);
	}

	@Override
	public void renderScope() {
		GL11.glTranslatef(1.795f, 0f, -0.685f);
		ScreenEffect.sniperScope.doRender(0.0f, 0,0,0, ScreenEffect.SNIPER_SCOPE_SCALE);
	}

	@Override
	protected void GL_Transform_FirstPerson(float prog) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(225.0f, 0, 1.0f, 0);
		GL11.glScalef(1.4f, 1.4f, 1.4f);

	}

	@Override
	protected void GL_Transform_Equipped() {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(135.0f, 0, 1.0f, 0);
		GL11.glScalef(0.8f, 0.8f, 0.8f);
		GL11.glTranslatef(0.1f, -0.5f, 0.9f);
	}

	@Override
	protected void GL_Transform_Ground() {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glScaled(0.6f, 0.6f, 0.6f);
		GL11.glTranslatef(0.0f, -0.45f, 0.0f);
	}

	@Override
	protected void GL_Transform_reloading(float prog) {
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		GL11.glRotatef(50.0f*-y,1.0f,0,0);
		GL_Transform_Translate();
		//GL11.glTranslatef(-0.55f, -0.45f, -0.8f);
		//GL11.glRotatef(0, 1.0f*-y, 0, 1.0f);
		//GL11.glTranslatef(0.1f-y, y/2.0f, -0.5f);

	}

	@Override
	protected void GL_Transform_reload_done() {
		GL_Transform_Translate();
	}

	@Override
	protected void GL_Transform_Translate() {
		//GL11.glTranslatef(0.1f, 0, -0.5f);
		GL11.glTranslatef(-0.55f, -0.35f, -0.65f);
	}

	@Override
	protected void GL_Transform_firing(float prog) {
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f));
		GL11.glRotatef(y*10, 1.0f, 0, 0);
		GL11.glTranslatef(-0.55f, -0.35f, -0.65f-(y*0.3f));
	}

	@Override
	protected void drawMuzzleFX(float prog) {
		// ?. ?, ?
		//ScreenEffect.muzzleFlash2.doRender(prog, -0.38f, -3.4f, 0.95f, 1.5f);
		//ScreenEffect.muzzleFlash.doRender(prog,-0.38f, -3.4f, 0.95f, 1.0f);
	}

	@Override
	protected void GL_Transform_Icon() {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glScalef(0.48f, 0.48f, 0.48f);
		GL11.glTranslatef(0.3f, 0.0f, 0.0f);
		
		GL11.glRotatef(90, 0.0f, 1, 0);
	}

}
