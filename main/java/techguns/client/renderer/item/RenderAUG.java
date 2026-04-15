package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;
import techguns.client.renderer.FX.ScreenEffect;

public class RenderAUG extends RenderGunBase {

	public RenderAUG(ModelMultipart model, ResourceLocation texture, float scale,
			float x, float y, float z, boolean dmgvalues,boolean scope) {
		super(model, texture, scale, x, y, z,dmgvalues,false,2,scope);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void GL_Transform_FirstPerson(int part,float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(225.0f, 0, 1.0f, 0);
		GL11.glScalef(1.4f, 1.4f, 1.4f);

	}

	@Override
	protected void GL_Transform_Equipped(int part,float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(135.0f, 0, 1.0f, 0);
		GL11.glScalef(0.8f, 0.8f, 0.8f);
		GL11.glTranslatef(0.1f, -0.6f, 1.4f);
	}

	@Override
	protected void GL_Transform_Ground(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glScaled(0.6f, 0.6f, 0.6f);
		GL11.glTranslatef(0.0f, -0.45f, 0.0f);
	}

	@Override
	protected void GL_Transform_reloading(int part,float prog, byte attackType) {
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		GL11.glRotatef(50.0f*-y,1.0f,0,0);
		GL_Transform_Translate(part);
		//GL11.glTranslatef(-0.55f, -0.45f, -0.8f);
		//GL11.glRotatef(0, 1.0f*-y, 0, 1.0f);
		//GL11.glTranslatef(0.1f-y, y/2.0f, -0.5f);

	}

	/*@Override
	protected void GL_Transform_reload_done(int part) {
		GL_Transform_Translate();
	}*/

	@Override
	protected void GL_Transform_Translate(int part) {
		//GL11.glTranslatef(0.1f, 0, -0.5f);
		GL11.glTranslatef(-0.55f, -0.35f, -0.65f);
	}

	@Override
	protected void GL_Transform_firing(int part,float prog, byte attackType) {

		
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f));
		GL11.glRotatef(y*10, 1.0f, 0, 0);
		GL11.glTranslatef(-0.55f, -0.35f, -0.65f-(y*0.3f));
		
		
		if(part==1){
			float trans=0;
			if(prog<0.15f){
				trans = prog*(1f/0.15f);
			} else {
				trans = (1-prog) * (1f/(1f-0.15f));
			}
			GL11.glTranslatef(0,0,0.6f*trans);
		}
	}

	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
		// ?. ?, ?
		ScreenEffect.muzzleFlash2.doRender(prog, -0.5f, -3.4f, 1.10f, 1.5f);
	}

	@Override
	protected void GL_Transform_Icon(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glScalef(0.48f, 0.48f, 0.48f);
		GL11.glTranslatef(0.3f, 0.0f, 0.0f);
		
		GL11.glRotatef(90, 0.0f, 1, 0);
	}

	@Override
	public void renderScope() {
		GL11.glTranslatef(1.795f, 0f, -0.685f);
		ScreenEffect.sniperScope.doRender(0.0f, 0,0,0, ScreenEffect.SNIPER_SCOPE_SCALE);
	}

	@Override
	protected void GL_TransformRecoil_Equipped(int part, float prog, byte attackType) {
		
		/*if(part==1){
			float trans=0;
			if(prog<0.15f){
				trans = prog*(1f/0.15f);
			} else {
				trans = (1-prog) * (1f/(1f-0.15f));
			}
			GL11.glTranslatef(0.5f*trans,0, 0);
		}*/
		
		float degree=-15;
		if(prog<0.5f){
			float progress=prog*2.0f;
			
			if(progress<0.25f){
				GL11.glRotatef(progress*4*degree, 1, 0, 0);
			} else {
				GL11.glRotatef((1-progress)*1.25f*degree, 1, 0, 0);
			}
		}
	}

	@Override
	protected void GL_TransformReload_Equipped(int part, float prog, byte attackType) {
		float offsetX=0;
		float offsetY=-0.2f;
		float offsetZ=0.05f;
		
		GL11.glTranslatef(offsetX, offsetY, offsetZ);
		
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		GL11.glRotatef(30.0f*-y,1.0f,0,0);
		GL11.glRotatef(40.0f*y,0,1,0);
		
		GL11.glTranslatef(-offsetX, -offsetY, -offsetZ);
	}

	@Override
	protected void transformEquippedAimed(int part) {
		super.transformEquippedAimed(part);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
		GL11.glTranslatef(0, 0.1f, 0);
	}
	
	@Override
	public void translateScopeRecoil(float recoil){
		GL11.glTranslatef(0.15f*recoil, 0.20f*recoil, 0.10f*recoil);
	}
}
