
package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;
import techguns.client.renderer.TGRenderHelper;
import techguns.client.renderer.FX.ScreenEffect;

public class RenderTeslaGun extends RenderGunBase {

	public RenderTeslaGun(ModelMultipart model, ResourceLocation texture,
			float scale, float x, float y, float z) {
		super(model, texture, scale, x, y, z,false,false,1,false);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void GL_Transform_FirstPerson(int part,float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(-45, 0, 1.0f, 0);
		GL11.glScalef(1.3f, 1.3f, 1.3f);
		GL11.glTranslatef(0.0f, -0.5f, 0.0f);
	}

	@Override
	protected void GL_Transform_Equipped(int part, float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(225, 0, 1.0f, 0);
		//GL11.glRotatef(90, 0, 0, 1.0f);
		GL11.glScalef(1.25f, 1.25f, 1.25f);
		GL11.glTranslatef(-0.7f, -0.5f, 0);
	}

	@Override
	protected void GL_Transform_Ground(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(90, 0, 1.0f, 0);
		GL11.glTranslatef(0.3f, -0.35f, 0.0f);
		GL11.glScalef(0.8f, 0.8f, 0.8f);
	}

	@Override
	protected void GL_Transform_reloading(int part,float prog, byte attackType) {
		//float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2f);
		float y = ((float) Math.min(0.66f,Math.sin(prog*Math.PI))*1.5f);
		GL11.glRotatef(20.0f*y, 0, 0, 1.0f);
		GL11.glRotatef(10.0f*-y, 0f, 1.0f, 0f);
		//GL11.glRotatef(10.0f*y, 1.0f, 0f, 0f);
		GL11.glTranslatef(0.1f+(y*0.5f), -0.25f+(y*0.25f), -0.5f+(y*0.25f));
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
	protected void GL_Transform_firing(int part,float prog, byte attackType) {
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f));
		GL11.glTranslatef(0.1f+(y*0.5f), -0.25f, -0.5f);
	}

	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
		                                           // -right, +near, ?
		TGRenderHelper.enableAlphaBlendAdditive();
		ScreenEffect.muzzleFlashLightning.doRender(prog, -0.65f, -1.0f, -0.30f, 1.75f, 0, 90.0f, 0);
		TGRenderHelper.disableAlphaBlend();
	}

	@Override
	protected void GL_Transform_Icon(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(180, 0, 1.0f, 0);
		GL11.glTranslatef(-0.1f, 0.0f, 0.0f);
		GL11.glScalef(0.55f, 0.55f, 0.55f);
	}

	@Override
	public void renderScope() {
	}

	@Override
	protected void GL_TransformRecoil_Equipped(int part, float prog, byte attackType) {
		// TODO Auto-generated method stub
		super.GL_TransformRecoil_Equipped(part, prog, attackType);
	}

	@Override
	protected void GL_TransformReload_Equipped(int part, float prog, byte attackType) {
		// TODO Auto-generated method stub	
	}
	
	@Override
	protected void transformEquippedAimed(int part) {
		super.transformEquippedAimed(part);
		//GL11.glRotated(90.0f-21.0f, 0, 0, 1);
		//GL11.glTranslated(0.7f, -0.15f,0.0f);
		GL11.glTranslatef(0,0.2f, -0.55f);		
	}

}
