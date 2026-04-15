package techguns.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

/**
 * Hide parts depending on damage value (example: empty = no rocket)
 *
 */
public abstract class ModelGunAmmo extends ModelBase {

	/* (non-Javadoc)
	 * @see net.minecraft.client.model.ModelBase#render(net.minecraft.entity.Entity, float, float, float, float, float, float)
	 */
	@Override
	public void render(Entity entity, float f1, float f2,
			float f3, float f4, float f5,
			float f6) {
		render(entity, f1,f2,f3,f4,f5,f6, 100,-1.0f, ItemRenderType.ENTITY);
	}
	
	public abstract void render(Entity entity, float f1, float f2,
			float f3, float f4, float f5,
			float f6, int ammoLeft, float reloadProgress, ItemRenderType renderType);
}
