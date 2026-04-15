package techguns.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;

public abstract class RenderGrenade implements IItemRenderer {

	ResourceLocation texture;
	ModelMultipart model;
	float scale; // uniform scale
	float x; // offset
	float y; // offset
	float z; // offset

	protected abstract void GL_Transform_FirstPerson(float prog);

	protected abstract void GL_Transform_Equipped();

	protected abstract void GL_Transform_Ground();

	protected abstract void GL_Transform_Icon();

	// protected abstract void GL_Transform_reloading(float prog);
	// protected abstract void GL_Transform_reload_done();
	// protected abstract void GL_Transform_firing(float prog);
	protected abstract void GL_Transform_Translate();

	// protected abstract void drawMuzzleFX(float prog);

	public RenderGrenade(ModelMultipart model, ResourceLocation texture,
			float scale, float x, float y, float z) {
		this.texture = texture;
		this.model = model;
		this.scale = scale;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		GL11.glPushMatrix();

		float prog =0.0f;
		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			int dur = Minecraft.getMinecraft().thePlayer.getItemInUseDuration();
			int maxdur = 20; //item.getMaxItemUseDuration();
			//System.out.println("dur:"+dur+" maxDur:"+maxdur);
			//System.out.println("X:"+(maxdur-dur));
				prog = dur / (maxdur*1.0f);
			//System.out.println("Prog:"+prog);
			if (prog < 0.0f) {
				prog = 0.0f;
			} else if (prog > 1.0f) {
				prog = 1.0f;
			}
			this.GL_Transform_FirstPerson(prog);

		} else if (type == ItemRenderType.EQUIPPED) {

			this.GL_Transform_Equipped();

		} else if (type == ItemRenderType.ENTITY) {

			this.GL_Transform_Ground();

		} else if (type == ItemRenderType.INVENTORY) {

			this.GL_Transform_Icon();
		}

		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		//model.render(null, 0, 0, 0, 0, 0, 0.0625F );
		model.render(null, 0, 0, 0, 0, 0, 0.0625F, 0, 0, type, 0, prog);
				
		GL11.glPopMatrix();
	}

}
