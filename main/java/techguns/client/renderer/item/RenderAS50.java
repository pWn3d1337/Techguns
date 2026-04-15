package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ibm.icu.impl.ICUService.Key;

import techguns.client.models.ModelMultipart;
import techguns.client.renderer.TGRenderHelper;
import techguns.client.renderer.FX.ScreenEffect;
import techguns.debug.Keybinds;

public class RenderAS50 extends RenderGunBase {

	public RenderAS50(ModelMultipart model, ResourceLocation texture, float scale,
			float x, float y, float z, boolean handleDamageValue) {
		super(model, texture, scale, x, y, z, handleDamageValue, false,1,true);
	}

	public RenderAS50(ModelMultipart model, ResourceLocation texture, float scale,
			float x, float y, float z) {
		super(model, texture, scale, x, y, z,false,false,1,true);
	}

	@Override
	protected void GL_TransformRecoil_Equipped(int part, float prog, byte attackType) {
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
	protected void GL_Transform_FirstPerson(int part,float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(225.0f, 0, 1.0f, 0);
		GL11.glScalef(1.4f, 1.4f, 1.4f);

	}

	@Override
	protected void GL_Transform_Equipped(int part,float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(135.0f, 0, 1.0f, 0);
		GL11.glScalef(0.7f, 0.7f, 0.7f);
		GL11.glTranslatef(0.1f, -0.5f, 1.35f);
	}

	@Override
	protected void GL_Transform_Ground(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glScaled(0.55f, 0.55f, 0.55f);
		GL11.glTranslatef(0.0f, -0.45f, 0.0f);
	}

	@Override
	protected void GL_Transform_reloading(int part,float prog, byte attackType) {
		//float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		float y = ((float) Math.min(0.66f,Math.sin(prog*Math.PI))*1.5f);
		GL11.glRotatef(30.0f*y,1.0f,0,0);
		GL11.glRotatef(15.0f*-y,0,1.0f,0);
		
		GL11.glTranslatef(-0.55f, -0.35f+(y*0.5f), -0.65f);
		
		//GL11.glTranslatef(-0.55f, -0.45f, -0.8f);
		//GL11.glRotatef(0, 1.0f*-y, 0, 1.0f);
		//GL11.glTranslatef(0.1f-y, y/2.0f, -0.5f);

	}

	/*@Override
	protected void GL_Transform_reload_done(int part) {
		GL_Transform_Translate(part);
	}*/

	@Override
	protected void GL_Transform_Translate(int part) {
		//GL11.glTranslatef(0.1f, 0, -0.5f);
		GL11.glTranslatef(-0.55f, -0.35f, -0.65f);
	}

	@Override
	protected void GL_Transform_firing(int part,float prog, byte attackType) {
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f));
		GL11.glRotatef(y*12, 1.0f, 0, 0);
		GL11.glTranslatef(-0.55f, -0.35f, -0.65f-(y*0.5f));
	}

	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
		// ?. ?, ?
		//TGRenderHelper.enableAlphaBlend();
		ScreenEffect.muzzleFlash2.doRender(prog, -0.28f, -4.0f, 0.93f, 2.0f);
		//ScreenEffect.muzzleFlashNew.doRender(prog, -0.28f, -4.0f, 0.83f, 2.5f);
		
		//TGRenderHelper.disableAlphaBlend();
	}

	
	
	/* (non-Javadoc)
	 * @see techguns.client.renderer.item.RenderGunScoped#renderScope()
	 */
	@Override
	public void renderScope() {
		GL11.glTranslatef(1.795f, 0f, -0.685f);
		ScreenEffect.sniperScope.doRender(0.0f, 0,0,0, ScreenEffect.SNIPER_SCOPE_SCALE); //2.25f
	}

	@Override
	protected void GL_Transform_Icon(int part) {
		//GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glScalef(0.33f, 0.33f, 0.33f);
		GL11.glTranslatef(0.35f, 0.25f, 0.5f);
		
		GL11.glRotatef(90, 0.0f, 1, 0);
	}

	@Override
	protected void GL_TransformReload_Equipped(int part, float prog, byte attackType) {
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		GL11.glRotatef(30.0f*-y,1.0f,0,0);
		GL11.glRotatef(40.0f*y,0,1,0);
	}

	@Override
	protected void transformEquippedAimed(int part) {
		super.transformEquippedAimed(part);
		//GL11.glTranslatef(0, 0, 0.25f);
		GL11.glTranslated(0,0.2,0);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
	}
	
	@Override
	public void translateScopeRecoil(float recoil){
		GL11.glTranslatef(0.25f*recoil, 0.5f*recoil, 0.1f*recoil);
	}
	

}
