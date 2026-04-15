package techguns.entities.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderTurret extends RenderLiving {
	private static ResourceLocation texture;
	
	public RenderTurret(ModelBase m, float f, String texture) {
		super(m, f);
		this.texture = new ResourceLocation(texture);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity ent) {
		return texture;
	}

}
