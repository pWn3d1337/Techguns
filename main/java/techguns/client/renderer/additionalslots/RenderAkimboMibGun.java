package techguns.client.renderer.additionalslots;

import org.lwjgl.opengl.GL11;

import net.minecraft.util.ResourceLocation;
import techguns.client.models.ModelMultipart;
import techguns.debug.Keybinds;

public class RenderAkimboMibGun extends RenderAkimboGun {

	public RenderAkimboMibGun(ModelMultipart model, ResourceLocation gunTex, int parts) {
		super(model, gunTex, parts, false);
	}

	@Override
	protected void applyTranslation() {
		GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
		GL11.glTranslatef(0.1f,-0.7f, -1.7f);
	}

	@Override
	protected void GL_TransformReload_Equipped(int part, float prog) {
		float offsetZ= 0.3f; //0.9f;
		float offsetY= 0.5f; //0.3f;
		GL11.glTranslatef(0,offsetY, offsetZ);
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		GL11.glRotatef(35.0f*-y,1.0f,0,0);
		GL11.glTranslatef(0,-offsetY, -offsetZ);
	}

	@Override
	protected void GL_TransformRecoil_Equipped(int part, float prog) {
		float degree=-15;
		float offsetZ= 0.5f; //+0.9f;
		float offsetY= 0.3f;
		if(prog<0.5f){
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
	
}
