package techguns.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public abstract class RenderMachineBlockItemBase implements IItemRenderer {

	protected ResourceLocation texture;
	protected ModelBase model;
	
	protected abstract void GL_Transform_FirstPerson();
	protected abstract void GL_Transform_Equipped();
	protected abstract void GL_Transform_Ground();
	protected abstract void GL_Transform_Icon();
	protected abstract void GL_Transform_Translate();
	
	
	public RenderMachineBlockItemBase(ResourceLocation texture, ModelBase model) {
		super();
		this.texture = texture;
		this.model = model;
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
		//GL11.glScalef(scale, scale, scale);
		
		this.GL_Transform_Translate();
		
		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			
			this.GL_Transform_FirstPerson();

		} else if (type == ItemRenderType.EQUIPPED){
			
			this.GL_Transform_Equipped();
			
		} else if (type == ItemRenderType.ENTITY){
			
			this.GL_Transform_Ground();
			
		} else if (type == ItemRenderType.INVENTORY){
			
			this.GL_Transform_Icon();
		}
		
		//int meta = item.getItemDamage();
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
		model.render(null, 0, 0, 0, 0, 0, 0.0625F );
		//System.out.println("Render Model with tex:"+texture.getResourcePath());

		GL11.glPopMatrix();
	}

}
