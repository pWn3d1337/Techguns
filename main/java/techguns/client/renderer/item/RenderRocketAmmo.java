package techguns.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RenderRocketAmmo implements IItemRenderer {

	ResourceLocation texture;
	ModelBase model;
	float scale; //uniform scale
	float x; //offset
	float y; //offset
	float z; //offset
	
	public RenderRocketAmmo(ModelBase model, ResourceLocation texture, float scale, float x, float y, float z) {
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

			GL11.glTranslatef(1.0f, 0.0f, 0.5f);
			
			GL11.glRotatef(90, 0f, 0f, 1.0f);
			//GL11.glRotatef(225.0f, 0, 1.0f, 0);
			
			GL11.glScaled(1.2f, 1.2f, 1.2f);
			
			// ?, -UP, +Back

			
		} else if (type == ItemRenderType.EQUIPPED) {
			
//			GL11.glTranslatef(0.0f, 0, -1.0f);
//			
//			GL11.glRotatef(90, 1.0f, 0, 0);
//			GL11.glRotatef(135.0f, 0, 1.0f, 0);
//			GL11.glScalef(1.2f, 1.2f, 1.2f);
			
			GL11.glRotatef(180, 1.0f, 0, 0);
			GL11.glRotatef(225, 0, 1.0f, 0);
			//GL11.glRotatef(90, 0, 0, 1.0f);
			GL11.glScalef(1.5f, 1.5f, 1.5f);
			GL11.glTranslatef(-0.5f, -0.25f, 0.0f);
			
			// ?, -up, +back
			
		} else if (type == ItemRenderType.ENTITY) {
			GL11.glRotatef(180, 1.0f, 0, 0);
			GL11.glRotatef(90, 0, 1.0f, 0);
			//GL11.glTranslatef(0f, -0.65f, 0f);
			
		} else if (type == ItemRenderType.INVENTORY) {
			GL11.glRotatef(180, 1.0f, 0, 0);
			GL11.glScaled(1.45f, 1.45f, 1.45f);
			
			//-y -> up, +Z -> right
			GL11.glTranslatef(0, 0.0f, -0.025f);
		}
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		model.render(null, 0, 0, 0, 0, 0, 0.0625F );
		
		GL11.glPopMatrix();
	}
	
}

