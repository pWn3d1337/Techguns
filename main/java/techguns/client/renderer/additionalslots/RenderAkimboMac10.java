package techguns.client.renderer.additionalslots;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;

public class RenderAkimboMac10 extends RenderAkimboGun {

	public RenderAkimboMac10(ModelMultipart model, ResourceLocation gunTex, int parts) {
		super(model, gunTex, parts, true);
	}

	@Override
	protected void GL_TransformReload_Equipped(int part, float prog) {
		// TODO Auto-generated method stub
		super.GL_TransformReload_Equipped(part, prog);
	}

	@Override
	protected void GL_TransformRecoil_Equipped(int part, float prog) {
		float offsetZ=0.7f;
		//	float offsetY=(float) Keybinds.Y;
		//	float offsetX=(float) Keybinds.X;
		GL11.glTranslatef(0,0,offsetZ);
		if(prog<0.3f){
			GL11.glRotatef(prog/0.3f*-15, 1, 0, 0);
		}
		GL11.glTranslatef(-0,-0,-offsetZ);
			//GL11.glTranslatef(0,0,-offsetZ);
	}

	@Override
	protected void applyWeaponRotation() {
		// TODO Auto-generated method stub
		super.applyWeaponRotation();
	}

	@Override
	protected float getScaleValue() {
		return 0.5f;
	}

	@Override
	protected void applyTranslation() {
		GL11.glTranslatef(0.2f,-0.45f, -2.15f);
	}

	
	
}
