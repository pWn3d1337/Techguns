package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;
import techguns.client.models.ModelMultipart;

public class RenderMP5 extends RenderPistol {

	public RenderMP5(ModelMultipart model, ResourceLocation texture, float scale,
			float x, float y, float z) {
		super(model, texture, scale, x, y, z);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see techguns.client.renderer.item.RenderPistol#drawMuzzleFX(float)
	 */
	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
		// TODO Auto-generated method stub
		super.drawMuzzleFX(prog, attackType);
	}

	
	
}
