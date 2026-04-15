package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;

public class RenderPowerHammer extends RenderGunBase {

	public RenderPowerHammer(ModelMultipart model, ResourceLocation texture,
			float scale, float x, float y, float z) {
		super(model, texture, scale, x, y, z,true,true,2,false);
	}

	@Override
	protected void GL_Transform_FirstPerson(int part,float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(-45, 0, 1.0f, 0);
		GL11.glScalef(1.5f, 1.5f, 1.5f);

		
		//System.out.println("Progress: "+prog);
		//System.out.println("AttackType: "+attackType);
		
		if (part ==0 ) {
			GL11.glTranslatef(-0.15f*prog,0,0);
		}else {
			GL11.glTranslatef(-0.45f*prog,0,0);
		}
	}

	@Override
	protected void GL_Transform_Equipped(int part,float prog, byte attackType) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(225, 0, 1.0f, 0);
		//GL11.glRotatef(90, 0, 0, 1.0f);
		GL11.glScalef(1.45f, 1.45f, 1.45f);
		//X(+/-): Front/Back
		//Y(+/-): Down/Up
		//Z(+/-): left/right
		GL11.glTranslatef(-0.5f, -0.65f, 0);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
		
//		if (part==1){
//			GL11.glRotatef(360.0f*prog, 1,0,0);
//		}
		
	}

	@Override
	protected void GL_Transform_Ground(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(90, 0, 1.0f, 0);
		GL11.glScalef(0.8f, 0.8f, 0.8f);
		GL11.glTranslatef(0.0f, -0.60f, 0.0f);
	}

	@Override
	protected void GL_Transform_reloading(int part,float prog, byte attackType) {
		float y = ((float) Math.min(0.75f,Math.sin(prog*Math.PI))*1.333f);
		GL11.glRotatef(32.0f*-y, 0, 0, 1.0f);
		GL11.glRotatef(15.0f*-y, 0f, 1.0f, 0f);
		//GL11.glRotatef(10.0f*y, 1.0f, 0f, 0f);
		GL11.glTranslatef((y*0.05f), (y*0.4f), (y*-0.20f));
		//GL11.glTranslated(y*Keybinds.X, y*Keybinds.Y, y*Keybinds.Z);
		GL_Transform_Translate(part);

	}

	/*@Override
	protected void GL_Transform_reload_done(int part) {
		GL_Transform_Translate(part);
	}*/

	@Override
	protected void GL_Transform_Translate(int part) {
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
		GL11.glTranslatef(0f, -0.75f, -0.3f);
		
	}

	@Override
	protected void GL_Transform_firing(int part,float prog, byte attackType) {
		//System.out.println("AttackType: "+attackType);
		if (attackType == 1) {
			double p = Math.sqrt(prog);
			double p2 = Math.min(prog*15.0, 1.0);		
			double d = Math.sin(p*2.0*Math.PI);
			GL11.glRotated(d*2.5, 0, 0, 1.0);
			//GL11.glRotated(-d*0.5, 1.0, 0, 0);
			//GL11.glRotated(-d*0.5, 0, 1.0, 0);
			
			//0,5+(SIN((WURZEL(B3)+0,75)*2*PI())/2)
			//=0,5+(SIN((B2-0,25)*PI()*2))/2
			double p4 = 0.5+(Math.sin((prog-0.25)*Math.PI*2.0))/2.0;
			double p3 = 0.5+(Math.sin((Math.sqrt(prog)+0.75)*2.0*Math.PI)/2.0);
			GL11.glTranslated(-0.15f+(p*0.15), 0, 0);
			GL11.glTranslated(((p3/2.0)-p4) * 0.15, 0, 0);
			
	
			if (part == 1) {
				GL11.glTranslated(-0.30f+(p2*0.3), 0, 0);
			}
			this.GL_Transform_Translate(part);
		
		}else {
			double p = Math.sqrt(prog);
			double d = Math.sin(p*2.0*Math.PI);
			double d1 = Math.pow(Math.sin(prog*Math.PI), 2.0);
			//double d2 = 0.5+(Math.sin((Math.sqrt(prog)+0.75)*2.0*Math.PI)/2.0);
			GL11.glRotated(d*1.5, 0, 0, 1.0);
			
			GL11.glTranslated(d1*0.15, 0, 0);

			if (part == 1) {
				double d2 = 0;
				if (prog < 0.25) {
					d2 = prog*-4;
				}else {
					double prog2 = Math.min(1.0,(prog-0.25)*1.3333);
					//d2 = (1.0 - Math.cos(Math.sqrt(prog2)*2.0*Math.PI))*0.5;
					d2 = Math.sqrt(prog2)-1;
				}
				GL11.glTranslated(0.45*d2, 0, 0);
			}
			this.GL_Transform_Translate(part);
		}

	}

	@Override
	protected void drawMuzzleFX(float prog, byte attackType) {
		//ScreenEffect.muzzleFlash2.doRender(prog, -0.75f, -2.2f, -0.45f, 1.65f,0,90.0f,0);
	}

	@Override
	protected void GL_Transform_Icon(int part) {
		GL11.glRotatef(180, 1.0f, 0, 0);
		GL11.glRotatef(180, 0, 1.0f, 0);
		GL11.glScalef(1.0f, 1.0f, 1.0f);
		//GL11.glTranslatef(0.25f, 0.55f, -0.6f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
	}

	@Override
	public void renderScope() {
	}

	@Override
	protected void GL_TransformRecoil_Equipped(int part, float prog, byte attackType) {
		double p2 = Math.min(prog*15.0, 1.0);
		if (part == 1) {
			GL11.glTranslated(-0.30f+(p2*0.3), 0, 0);
		}
	}

	@Override
	protected void GL_TransformReload_Equipped(int part, float prog, byte attackType) {
		float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
		GL11.glRotatef(30.0f*y,1.0f,0,0);
		GL11.glRotatef(40.0f*y,0,1,0);
	}

	@Override
	protected void transformEquippedAimed(int part) {
		super.transformEquippedAimed(part);
		GL11.glTranslated(0.0f,-0.15f,0.3f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);		
	}

}
