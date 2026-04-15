package techguns.client.renderer.additionalslots;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;
import techguns.debug.Keybinds;

public class RenderAkimboRevolver extends RenderAkimboGun {

	public RenderAkimboRevolver(ModelMultipart model, ResourceLocation gunTex, int parts) {
		super(model, gunTex, parts);
	}

	@Override
	protected void applyWeaponRotation() {
		super.applyWeaponRotation();
		GL11.glRotatef(90, 0, 1,0);
	}

	@Override
	protected float getScaleValue() {
		return 0.3f;
	}

	@Override
	protected void applyTranslation() {
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
		GL11.glTranslatef(2.7f, -0.55f, 0.15f);
	}

	@Override
	protected void GL_TransformRecoil_Equipped(int part, float prog) {
	//	float offsetZ=(float) Keybinds.Z;
	//	float offsetY=(float) Keybinds.Y;
	//	float offsetX=(float) Keybinds.X;
			//GL11.glTranslatef(offsetX,offsetY,offsetZ);
			if(prog<0.3f){
				GL11.glRotatef(prog/0.3f*-30, 0, 0, 1);
			}
	//		GL11.glTranslatef(-offsetX,-offsetY,-offsetZ);
			//GL11.glTranslatef(0,0,-offsetZ);
	}

	@Override
	protected void GL_TransformReload_Equipped(int part, float prog) {
		float offsetZ=0.9f;
		float offsetY=0.3f;
		GL11.glTranslatef(0,offsetY, offsetZ);
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		GL11.glRotatef(40.0f*-y,0,0,1);
		//GL11.glRotatef(-40.0f*y,0,1,0);
		GL11.glTranslatef(0,-offsetY, -offsetZ);
	}

	
	
	
	
	
}
