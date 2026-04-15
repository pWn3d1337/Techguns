package techguns.client.models.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import techguns.client.renderer.block.TesselatorCube;

/**
 * A model base that actually draws with tesselator
 *
 */
public class ModelBaseTesselator extends ModelBase {
	public TesselatorCube block;

	public ModelBaseTesselator(TesselatorCube block) {
		super();
		this.block = block;
	}

	@Override
	public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_,
			float p_78088_4_, float p_78088_5_, float p_78088_6_,
			float p_78088_7_) {
		//GL11.glTranslatef(0, 0.9f, 0);
		GL11.glRotatef(180.0f, 1, 0, 0);
		GL11.glRotatef(180.0f, 0, 1, 0);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
		GL11.glTranslatef(-0.5f, -1.5f, -0.5f);
		block.drawCube();
	}
	
	
}
