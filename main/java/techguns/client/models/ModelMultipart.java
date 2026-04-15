package techguns.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public abstract class ModelMultipart extends ModelBase {
	
		public void render(Entity entity, float f1, float f2,
				float f3, float f4, float f5,
				float f6) {
			render(entity, f1,f2,f3,f4,f5,f6, 100,-1.0f, ItemRenderType.ENTITY,0, 0.0f);
		}
		
		public abstract void render(Entity entity, float f1, float f2,
				float f3, float f4, float f5,
				float f6, int ammoLeft, float reloadProgress, ItemRenderType renderType, int part, float fireProgress);
	  
	  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
	  {
	    super.setRotationAngles(f, f1, f2, f3, f4, f5,e);
	  }

}
