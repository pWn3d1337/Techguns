package techguns.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RenderLMGMag implements IItemRenderer {

	ResourceLocation texture;
	ModelBase model;
	float scale; //uniform scale
	float x; //offset
	float y; //offset
	float z; //offset
	
	public RenderLMGMag(ModelBase model, ResourceLocation texture, float scale, float x, float y, float z) {
		this.model = model;
		this.texture = texture;
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
		
		
		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glRotatef(180, 1.0f, 0, 0);
			GL11.glRotatef(225.0f, 0, 1.0f, 0);
			
			// ?, -UP, +Back
			GL11.glTranslatef(-0.3f, -1.5f, 0.6f);
			
		} else if (type == ItemRenderType.EQUIPPED) {
			
			GL11.glRotatef(180, 1.0f, 0, 0);
			GL11.glRotatef(135.0f, 0, 1.0f, 0);
			GL11.glScalef(0.8f, 0.8f, 0.8f);
			
			// ?, -up, +back
			GL11.glTranslatef(0.1f, -1.3f, 1.35f);
			
		} else if (type == ItemRenderType.ENTITY) {
			GL11.glRotatef(180, 1.0f, 0, 0);
			GL11.glRotatef(90, 0, 1.0f, 0);
			GL11.glTranslatef(0f, -0.45f, 0f);
			GL11.glScalef(0.65f, 0.65f, 0.65f);
			
		} else if (type == ItemRenderType.INVENTORY) {
			GL11.glRotatef(180, 1.0f, 0, 0);
			GL11.glScaled(1.35f, 1.35f, 1.35f);
			
			//-y -> up, +Z -> right
			GL11.glTranslatef(0, -0.45f, 0.1f);
		}
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		model.render(null, 0, 0, 0, 0, 0, 0.0625F );
		
		GL11.glPopMatrix();
	}

}
