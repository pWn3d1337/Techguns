package techguns.client.models;

import net.minecraft.entity.Entity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class ModelAkimbo extends ModelMultipart {
	protected ModelMultipart gunModel;
	int partsPerModel;
	
	
	
	public ModelAkimbo(ModelMultipart gunModel, int partsPerModel) {
		super();
		this.gunModel = gunModel;
		this.partsPerModel = partsPerModel;
	}

	@Override
	public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float f6, int ammoLeft, float reloadProgress,
			ItemRenderType renderType, int part, float fireProgress) {
		if (part >=partsPerModel){
			part -= partsPerModel;
		}
		gunModel.render(entity, f1, f2, f3, f4, f5, f6, ammoLeft, reloadProgress, renderType, part, fireProgress);
	}
	
	
	
}
