package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;

public class RenderDriller extends RenderGunMultipart {

	public RenderDriller(ModelMultipart model, ResourceLocation texture,
			float scale, float x, float y, float z) {
		super(model, texture, scale, x, y, z,false,false,2);
	}

	@Override
	protected void GL_Transform_FirstPerson(int part,float prog) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(225.0f, 0, 1.0f, 0);
		GL11.glScalef(1.4f, 1.4f, 1.4f);

	}

	@Override
	protected void GL_Transform_Equipped(int part,float prog) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(125.0f, 0, 1.0f, 0);
		GL11.glScalef(0.8f, 0.8f, 0.8f);
		GL11.glTranslatef(0.1f, -0.5f, 0.9f);
		
		if (part==1){
			GL11.glRotatef(360.0f*prog, 0,0,1);
		}
	}

	@Override
	protected void GL_Transform_Ground(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glScaled(0.6f, 0.6f, 0.6f);
		GL11.glTranslatef(0.0f, -0.45f, 0.0f);
	}

	@Override
	protected void GL_Transform_reloading(int part,float prog) {
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		GL11.glRotatef(50.0f*-y,1.0f,0,0);
		GL_Transform_Translate(part);
		//GL11.glTranslatef(-0.55f, -0.45f, -0.8f);
		//GL11.glRotatef(0, 1.0f*-y, 0, 1.0f);
		//GL11.glTranslatef(0.1f-y, y/2.0f, -0.5f);

	}

	@Override
	protected void GL_Transform_reload_done(int part) {
		GL_Transform_Translate(part);
	}

	@Override
	protected void GL_Transform_Translate(int part) {
		//GL11.glTranslatef(0.1f, 0, -0.5f);
		GL11.glTranslatef(-0.55f, -0.35f, -0.65f);
	}

	@Override
	protected void GL_Transform_firing(int part,float prog) {
		//this.GL_Transform_Translate(part);
		//GL11.glRotatef(45.0f, 0,1,0);
		/*GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(225.0f, 0, 1.0f, 0);
		GL11.glScalef(1.4f, 1.4f, 1.4f);*/
		
		/*float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f));
		GL11.glRotatef(y*10, 1.0f, 0, 0);
		GL11.glTranslatef(-0.55f, -0.35f, -0.65f-(y*0.3f));*/
		float x=-0.6f,y=-0.65f;
		GL11.glTranslatef(x, y, 0.0f);
		if (part==1){
			GL11.glRotatef(360.0f*prog, 0,0,1);
		}
		GL11.glTranslatef(-x, -y, 0.0f);
		this.GL_Transform_Translate(part);
		/*
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(225.0f, 0, 1.0f, 0);
		GL11.glScalef(1.4f, 1.4f, 1.4f);*/
	}

	@Override
	protected void drawMuzzleFX(float prog) {
		// ?. ?, ?
		//ScreenEffect.muzzleFlash2.doRender(prog, -0.38f, -3.4f, 0.95f, 1.5f);
	}

	@Override
	protected void GL_Transform_Icon(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glScalef(0.48f, 0.48f, 0.48f);
		GL11.glTranslatef(0.3f, 0.0f, 0.0f);
		
		GL11.glRotatef(90, 0.0f, 1, 0);
	}



}
