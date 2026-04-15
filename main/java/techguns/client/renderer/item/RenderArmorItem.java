package techguns.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RenderArmorItem implements IItemRenderer {

	ResourceLocation texture;
	ModelBiped model;
	int slot;
	
	public RenderArmorItem(ModelBiped model, int slot, String texture) {
		this.texture = new ResourceLocation("techguns","textures/armor/"+texture);
		this.model = model;
		this.slot = slot;
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
		switch(type) {
			case ENTITY:
				GL11.glRotatef(180, 1.0f, 0, 0);
				GL11.glRotatef(-90, 0, 1.0f, 0);
				switch (slot) {
					case 0:
						GL11.glTranslatef(0f, -1.25f+0.4f, 0f);
						GL11.glScalef(1.5f, 1.5f, 1.5f);
						break;
					case 1:
						GL11.glTranslatef(0f, -2.10f+0.2f, 0f);
						GL11.glScalef(2.f, 2.f, 2.f);
						break;
					case 2:
						GL11.glTranslatef(0f, -3.00f+0.5f, 0f);
						GL11.glScalef(2.f, 2.f, 2.f);
						break;
					case 3:
						GL11.glTranslatef(0f, -3.60f+0.7f, 0f);
						GL11.glScalef(2.f, 2.f, 2.f);
						break;
				}
				break;
			case EQUIPPED:
				GL11.glRotatef(180, 1.0f, 0, 0);
				switch (slot) {
				case 0:
					GL11.glTranslatef(1.0f, -1.25f, 0f);
					break;
				case 1:
					GL11.glTranslatef(1.0f, -2.10f, -0f);
					break;
				case 2:
					GL11.glTranslatef(1.0f, -3.00f, 0f);
					break;
				case 3:
					GL11.glTranslatef(1.0f, -3.60f, 0f);
					break;
				}
				GL11.glScalef(2.f, 2.f, 2.f);
				break;
			case EQUIPPED_FIRST_PERSON:
				GL11.glRotatef(180, 1.0f, 0, 0);
				GL11.glRotatef(90, 0, 1.0f, 0);
				switch (slot) {
				case 0:
					GL11.glTranslatef(0.f, -1.75f, 0.5f);
					break;
				case 1:
					GL11.glTranslatef(0.f, -2.60f, 0.5f);
					break;
				case 2:
					GL11.glTranslatef(0.f, -3.30f, 0.5f);
					break;
				case 3:
					GL11.glTranslatef(0.f, -3.60f, 0.5f);
					break;
				}
				GL11.glScalef(2.f, 2.f, 2.f);
				break;
			case INVENTORY:
				GL11.glRotatef(180, 1.0f, 0, 0);
				//-y -> up, +Z -> right
				switch (slot) {
					case 0:
						GL11.glTranslatef(0, -1.25f, 0f);
						GL11.glScalef(2.25f, 2.25f, 2.25f);
						break;
					case 1:
						GL11.glTranslatef(0, -2.10f, 0f);
						GL11.glScalef(2.30f, 2.30f, 2.30f);
						break;
					case 2:
						GL11.glTranslatef(0, -3.00f, 0f);
						GL11.glScalef(2.50f, 2.50f, 2.50f);
						break;
					case 3:
						GL11.glTranslatef(0, -3.60f, 0f);
						GL11.glScalef(2.70f, 2.70f, 2.70f);	
						break;
				}
				break;
			default:
				break;	
		}
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
		
		model.bipedBody.showModel = false;
		model.bipedCloak.showModel = false;
		model.bipedEars.showModel = false;
		model.bipedHead.showModel = false;
		model.bipedHeadwear.showModel = false;
		model.bipedLeftArm.showModel = false;
		model.bipedRightArm.showModel = false;
		model.bipedLeftLeg.showModel = false;
		model.bipedRightLeg.showModel = false;
		
		
		
		
		if (slot == 0) { //Helmet
			model.bipedHead.showModel = true;
			model.bipedHeadwear.showModel = true;
			model.render(null, 0, 0, 0, 0, 0, 0.0625F );
		}else if (slot ==1) { //Chestplate
			model.bipedBody.showModel = true;
			model.bipedLeftArm.showModel = true;
			model.bipedRightArm.showModel = true;
			model.render(null, 0, 0, 0, 0, 0, 0.0625F );
		}else if (slot ==2) { //Leggings
			model.bipedBody.showModel = true;
			model.bipedLeftLeg.showModel = true;
			model.bipedRightLeg.showModel = true;
			model.render(null, 0, 0, 0, 0, 0, 0.0625F );
		}else if (slot ==3) { //Boots
			model.bipedLeftLeg.showModel = true;
			model.bipedRightLeg.showModel = true;
			model.render(null, 0, 0, 0, 0, 0, 0.0625F );
		}
		
		
		GL11.glPopMatrix();

	}

}
