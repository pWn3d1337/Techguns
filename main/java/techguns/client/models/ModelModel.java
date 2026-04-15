package techguns.client.models;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

public class ModelModel extends ModelMultipart{
	
    private IModelCustom model;
	
	public ModelModel(String filename) {
		this.model = AdvancedModelLoader.loadModel(new ResourceLocation("techguns", filename));
	}

	@Override
	public void render(Entity entity, float f1, float f2, float f3, float f4,
			float f5, float f6) {
		GL11.glPushMatrix();
		GL11.glScalef(f6, f6, f6);
		model.renderAll();
		GL11.glPopMatrix();
	}
	
	@Override
	public void render(Entity entity, float f1, float f2, float f3, float f4,
			float f5, float f6, int ammoLeft, float reloadProgress,
			ItemRenderType renderType, int part, float fireProgress) {		
		GL11.glPushMatrix();
		GL11.glScalef(f6, f6, f6);
		//setRotationAngles(f1, f2, f3, f4, f5, f6, entity);
		model.renderPart(""+part);
		GL11.glPopMatrix();
	}

}
