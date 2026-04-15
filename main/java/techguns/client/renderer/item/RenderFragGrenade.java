package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;
import techguns.debug.Keybinds;

public class RenderFragGrenade extends RenderGrenade {

	public RenderFragGrenade(ModelMultipart model, ResourceLocation texture, float scale, float x, float y, float z) {
		super(model, texture, scale, x, y, z);
	}

	@Override
	protected void GL_Transform_FirstPerson(float prog) {
		this.GL_Transform_Translate();
		GL11.glScalef(1.5f, 1.5f, 1.5f);
		//back,-front ; -up,down, ?
		GL11.glTranslatef(-0.2f, 0.5f, 0.7f);
		//GL11.glRotatef(-45.0f, 0.0f, 0.0f, 1.0f);
		//GL11.glTranslatef(-1.0f, 0.0f, 0);
		//System.out.println(prog);
		GL11.glRotatef(20.0f*prog, 0.0f, 0.0f, 1.0f);
	}

	@Override
	protected void GL_Transform_Equipped() {
		this.GL_Transform_Translate();
		GL11.glScalef(1.8f,1.8f,1.8f);
		//GL11.glTranslated(0, -0.1f, 1.7f);
		GL11.glTranslatef(0, 0.2f , 1.3f);
		GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
		GL11.glRotated(60.0f, 1, 0, 0);
		GL11.glRotatef(-90.0f, 0, 1, 0);
	}

	@Override
	protected void GL_Transform_Ground() {
		this.GL_Transform_Translate();
		GL11.glScalef(1.15f, 1.15f, 1.15f);
		GL11.glTranslatef(0, -0.5f, 0);
	}

	@Override
	protected void GL_Transform_Icon() {
		this.GL_Transform_Translate();
		GL11.glRotated(-125.0F, 0, 1, 0);
		GL11.glScalef(2.75f, 2.75f, 2.75f);
		GL11.glTranslatef(0, 0.9f, 0);
	}

	@Override
	protected void GL_Transform_Translate() {
		GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
		GL11.glRotatef(-45.0f, 0.0f, 1.0f, 0.0f);
	}
}
