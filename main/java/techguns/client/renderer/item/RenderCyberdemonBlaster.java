package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;
import techguns.client.renderer.FX.ScreenEffect;
import techguns.debug.Keybinds;

public class RenderCyberdemonBlaster extends RenderGunBase {

	
	public RenderCyberdemonBlaster(ModelMultipart model, ResourceLocation texture,
			float scale, float x, float y, float z) {
		super(model, texture, scale, x, y, z,false,false,1,false);
	}

	@Override
	protected void GL_Transform_FirstPerson(int part, float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(225.0f, 0, 1.0f, 0);
		GL11.glScalef(2.0f, 2.0f, 2.0f);

	}

	@Override
	protected void GL_Transform_Equipped(int part, float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(135.0f, 0, 1.0f, 0);
		GL11.glScalef(1.35f, 1.35f, 1.35f);
		//-X.. right, +X .. left
		//-Y.. up, +Y .. down
		//-Z.. front, +Z .. back
		GL11.glTranslatef(0.20f, -0.25f, 0.07f);
	}

	@Override
	protected void GL_Transform_Ground(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glScaled(0.8f, 0.8f, 0.8f);
		GL11.glTranslatef(0.0f, -0.45f, 0.0f);
	}

	@Override
	protected void GL_Transform_reloading(int part, float prog, byte attackType) {
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
		GL11.glTranslatef(-0.85f, -0.20f, -0.75f);
	}

	@Override
	protected void GL_Transform_firing(int part, float prog, byte attackType) {
		//float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f));
		float y =(float) Math.sin(Math.sqrt(prog)*Math.PI);
		GL11.glRotatef(-y*10, 1.0f, 0, 0);
		GL11.glTranslatef(-0.85f, -0.20f, -0.75f+(y*0.25f));
	}

	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
        ScreenEffect.muzzleFlashBlaster.doRender(prog, -0.72f, -1.0f, 1.35f, 1.5f);
		GL11.glPopAttrib();
	}

	@Override
	protected void GL_Transform_Icon(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glScalef(0.68f, 0.68f, 0.68f);
		GL11.glTranslatef(0.3f, 0.0f, 0.0f);
		
		GL11.glRotatef(90, 0.0f, 1, 0);
	}

	@Override
	public void renderScope() {
	}

	@Override
	protected void GL_TransformRecoil_Equipped(int part, float prog, byte attackType) {
		float degree=-30;
		if(prog<0.5f){
			float offsetZ=1.0f;
			float offsetY=0.0f;
			GL11.glTranslatef(0,offsetY, offsetZ);
				float progress=prog*2.0f;
				
				if(progress<0.25f){
					GL11.glRotatef(progress*4*degree, 1, 0, 0);
				} else {
					GL11.glRotatef((1-progress)*1.25f*degree, 1, 0, 0);
				}
			GL11.glTranslatef(0,-offsetY, -offsetZ);
		}
	}

	@Override
	protected void GL_TransformReload_Equipped(int part, float prog, byte attackType) {
		float offsetZ=1.0f;
		float offsetY=0.0f;
		GL11.glTranslatef(0,offsetY, offsetZ);
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		GL11.glRotatef(-40.0f*y,1.0f,0,0);
		//GL11.glRotatef(40.0f*y,0,1,0);
		GL11.glTranslatef(0,-offsetY, -offsetZ);
	}

	@Override
	protected void transformEquippedAimed(int part) {
		super.transformEquippedAimed(part);
		//GL11.glRotated(90.0f-21.0f, 0, 0, 1);
		//GL11.glTranslated(0.45f,0.1f,0.0f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);		
	}
	
}
