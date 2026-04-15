package techguns.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRocketProjectile extends Render {

	private float scale;
	private ResourceLocation textureLoc;
	private ModelBase model;

	public RenderRocketProjectile(ModelBase model, String texture) {
		this.model = model;
		this.textureLoc = new ResourceLocation(texture);
	}

	public void doRender(Entity par1Entity, double par2, double par4,
			double par6, float par8, float par9) {
		// par1Entity.readFromNBT(par1Entity.getEntityData());
		// float scale = par1Entity.getScale();

		// System.out.println("scale:"+scale);

		GL11.glPushMatrix();
		
		

		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
//		GL11.glRotatef(par1Entity.rotationYaw - 90, 0.0F, 1.0F, 0.0F);
//		GL11.glRotatef(par1Entity.rotationPitch, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(par1Entity.prevRotationYaw + (par1Entity.rotationYaw - par1Entity.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(par1Entity.prevRotationPitch + (par1Entity.rotationPitch - par1Entity.prevRotationPitch) * par9, 0.0F, 0.0F, 1.0F);
		
		GL11.glScalef(0.9f, 0.9f, 0.9f);
		
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