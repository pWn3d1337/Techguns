package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;

public class RenderStielgranate extends RenderGrenade {

	public RenderStielgranate(ModelMultipart model,ResourceLocation texture,
			float scale, float x, float y, float z) {
		super(model,texture, scale, x, y, z);
	}

	
	
	@Override
	protected void GL_Transform_FirstPerson(float prog) {
		this.GL_Transform_Translate();
		//back,-front ; -up,down, ?
		GL11.glTranslatef(-0.2f, 0.2f, 0.7f);
		//GL11.glRotatef(-45.0f, 0.0f, 0.0f, 1.0f);
		//GL11.glTranslatef(-1.0f, 0.0f, 0);
		//System.out.println(prog);
		GL11.glRotatef(30.0f*prog, 0.0f, 0.0f, 1.0f);
	}

	@Override
	protected void GL_Transform_Equipped() {
		this.GL_Transform_Translate();
		GL11.glTranslated(0, -0.1f, 1.7f);
		GL11.glRotated(60.0f, 1, 0, 0);

	}

	@Override
	protected void GL_Transform_Ground() {
		this.GL_Transform_Translate();
	}

	@Override
	protected void GL_Transform_Icon() {
		this.GL_Transform_Translate();
		GL11.glTranslatef(0, 1.0f, 0);
	}

	@Override
	protected void GL_Transform_Translate() {
		GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
		GL11.glRotatef(-45.0f, 0.0f, 1.0f, 0.0f);
		GL11.glScalef(1.15f, 1.15f, 1.15f);
	}

}
