package techguns.client.renderer.entity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import techguns.client.models.ModelModel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGrenadeLauncherProjectile extends Render {

		private float scale;
		private ResourceLocation textureLoc;
		private ModelModel model;

		public RenderGrenadeLauncherProjectile(ModelModel model, String texture) {
			this.model = model;
			this.textureLoc = new ResourceLocation(texture);
		}

		public void doRender(Entity par1Entity, double par2, double par4,
				double par6, float par8, float par9) {
			GL11.glPushMatrix();
					
			//GL11.glTranslatef(0, 0.95f, 0);
			

			GL11.glTranslatef((float) par2, (float) par4, (float) par6);
	        GL11.glRotatef(par1Entity.prevRotationYaw + (par1Entity.rotationYaw - par1Entity.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
	        GL11.glRotatef(par1Entity.prevRotationPitch + (par1Entity.rotationPitch - par1Entity.prevRotationPitch) * par9, 0.0F, 0.0F, 1.0F);
			


	        //float x = -0.85f;
	        //GL11.glTranslatef(0, x, 0);
	        int rotate = par1Entity.ticksExisted % 40;
	        float f = rotate/40.0f;
	        //GL11.glRotatef(-360.0f*f, 0, 0, 1);
	        GL11.glRotatef(-360.0f*f -(18.0f*par9), 0, 0, 1);
	        //GL11.glTranslatef(0, -x, 0);
	        
			GL11.glScalef(0.8f, 0.8f, 0.8f);
	        
			this.bindEntityTexture(par1Entity);
			Tessellator tessellator = Tessellator.instance;
			
			model.render(par1Entity, 0, 0, 0, 0, 0, 0.0625F);

			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
		}

		/**
		 * Returns the location of an entity's texture. Doesn't seem to be called
		 * unless you call Render.bindEntityTexture.
		 */
		@Override
		protected ResourceLocation getEntityTexture(Entity par1Entity) {
			return this.textureLoc;
		}

	}
