
package techguns.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.ClientProxy;
import techguns.client.models.ModelMultipart;
import techguns.client.renderer.TGRenderHelper;
import techguns.client.renderer.FX.ScreenEffect;

public class RenderFlamethrower extends RenderGunBase {

	public RenderFlamethrower(ModelMultipart model, ResourceLocation texture,
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
		GL11.glScalef(1.5f, 1.5f, 1.5f);
		
		//X(+/-): Front/Back
		//Y(+/-): Down/Up
		//Z(+/-): left/right
		GL11.glTranslatef(-0.8f, -0.6f, 0.0f);
	}

	@Override
	protected void GL_Transform_Ground(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(90, 0, 1.0f, 0);
		GL11.glScalef(0.85f, 0.85f, 0.85f);
		GL11.glTranslatef(0.0f, -0.45f, 0.0f);
	}

	@Override
	protected void GL_Transform_reloading(int part,float prog, byte attackType) {
		//float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2f);
		float y = ((float) Math.min(0.66f,Math.sin(prog*Math.PI))*1.5f);
		GL11.glRotatef(20.0f*y, 0, 0, 1.0f);
		GL11.glRotatef(10.0f*-y, 0f, 1.0f, 0f);
		//GL11.glRotatef(10.0f*y, 1.0f, 0f, 0f);
		GL11.glTranslatef((y*0.5f), (y*0.25f), (y*0.25f));
		GL_Transform_Translate(part);
	}

	/*@Override
	protected void GL_Transform_reload_done() {
		//GL11.glTranslatef(0.1f, 0, -0.5f);
		GL_Transform_Translate();
	}*/

	@Override
	protected void GL_Transform_Translate(int part) {
		//GL11.glTranslatef(0.1f, -0.25f, -0.5f);
		GL11.glTranslatef(0.55f, -0.3f, -0.7f);
	}

	@Override
	protected void GL_Transform_firing(int part,float prog, byte attackType) {
		this.GL_Transform_Translate(part);
	}

	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
//		ScreenEffect.muzzleFlash.doRender(prog, -0.4f, -2.5f, 0.4f, 1.85f);
	}

	@Override
	protected void GL_Transform_Icon(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(180, 0, 1.0f, 0);
		GL11.glScalef(0.7f, 0.7f, 0.7f);
		GL11.glTranslatef(-0.3f, 0.0f, 0.0f);
	}
	
	@Override
	protected void drawExtraEffects() {
		//show permanent flame effect
		ClientProxy cp = ClientProxy.get();
		if (cp.getplayerReloadtime(ClientProxy.get().getPlayerClient()) <=0 &&
			cp.getplayerRecoiltime(ClientProxy.get().getPlayerClient()) <= 0 &&
			Minecraft.getMinecraft().thePlayer.isOffsetPositionInLiquid(0, 1.5, 0)) {
			float p = ((float)(Minecraft.getMinecraft().theWorld.getTotalWorldTime() % 30) / 30.0f);
			TGRenderHelper.enableAlphaBlendAdditive();
			//ScreenEffect.FlamethrowerMuzzleFlame.doRender(p, -0.9f, -2.85f, 0.6f, 0.75f, 90f, 0f, 0f);
			ScreenEffect.FlamethrowerMuzzleFlame.doRender(p, -1.85f, -3.3f, -0.65f, 1.0f, 0f, 90f, 0f);
			//ScreenEffect.FlamethrowerMuzzleFlame.doRender(p, (float)Keybinds.X, (float)Keybinds.Y, (float)Keybinds.Z, 1.00f, 0f, 90f, 0f);
			TGRenderHelper.disableAlphaBlend();
		}
	}

	@Override
	public void renderScope() {
	}

	@Override
	protected void GL_TransformRecoil_Equipped(int part, float prog, byte attackType) {
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
		GL11.glTranslated(0,-0.15f,-0.4f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);		
	}
}
