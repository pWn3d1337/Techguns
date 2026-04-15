package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;

public class RenderM4_Infiltrator extends RenderM4 {

	public RenderM4_Infiltrator(ModelMultipart model, ResourceLocation texture, float scale, float x, float y, float z) {
		super(model, texture, scale, x, y, z, true,true);
	}

	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
	}

	@Override
	public void translateScopeRecoil(float recoil){
		GL11.glTranslatef(0.1f*recoil, 0.15f*recoil, 0.05f*recoil);
	}
}
