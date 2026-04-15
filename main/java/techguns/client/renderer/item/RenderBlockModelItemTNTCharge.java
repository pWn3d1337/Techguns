package techguns.client.renderer.item;

import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderBlockModelItemTNTCharge extends RenderMachineBlockItemBase {

	public RenderBlockModelItemTNTCharge(ResourceLocation texture, ModelBase model) {
		super(texture, model);
	}

	@Override
	protected void GL_Transform_FirstPerson() {
		GL11.glTranslatef(0.3f, -1.7f, -0.5f);
		GL11.glScalef(1.2f, 1.2f, 1.2f);
		GL11.glRotated(-122.5f, 0, 1, 0);
		GL11.glTranslatef(-0.2f,-0.2f,0);
	}

	@Override
	protected void GL_Transform_Equipped() {
		GL11.glTranslatef(0.4f, -1.9f, -0.4f);
		GL11.glRotatef(-45.0f, 0,1,0);
		GL11.glScalef(1.5f, 1.5f, 1.5f);
	}

	@Override
	protected void GL_Transform_Ground() {
		GL11.glScalef(1.5f, 1.5f, 1.5f);
		GL11.glTranslated(0, -1.0f, 0.3f);
	}

	@Override
	protected void GL_Transform_Icon() {
		GL11.glTranslatef(0, -1.2f, 0);
		GL11.glRotatef(135.0f, 0, 1, 0);
		GL11.glScalef(1.5f, 1.5f,1.5f);
	}

	@Override
	protected void GL_Transform_Translate() {
		GL11.glRotatef(180.0f, 1, 0, 0);
	}

}
