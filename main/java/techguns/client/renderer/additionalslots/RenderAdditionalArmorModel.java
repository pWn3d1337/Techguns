package techguns.client.renderer.additionalslots;

import org.lwjgl.opengl.GL11;

import techguns.items.armors.ICamoChangeable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderAdditionalArmorModel extends RenderAdditionalSlotItem {
	private ResourceLocation[] textures; // = new
											// ResourceLocation("techguns:textures/armor/gasMask.png");
	protected ModelBiped model;

	public RenderAdditionalArmorModel(ResourceLocation[] textures, ModelBiped model) {
		super();
		this.textures = textures;
		this.model = model;
	}

	protected boolean ignoreShowSlot=false;

	public void setIgnoreShowSlot(boolean ignoreShowSlot) {
		this.ignoreShowSlot = ignoreShowSlot;
	}

	@Override
	public void render(ItemStack item, EntityPlayer entityPlayer, RenderPlayer renderplayer, float partialTickTime, boolean showSlot) {
		if (ignoreShowSlot || showSlot) {

			if (item.getItem() instanceof ICamoChangeable) {
				ICamoChangeable camoitem = (ICamoChangeable) item.getItem();
				Minecraft.getMinecraft().getTextureManager().bindTexture(textures[camoitem.getCurrentCamoIndex(item)]);
			} else {
				Minecraft.getMinecraft().getTextureManager().bindTexture(textures[0]);
			}
			//EntityPlayer self = Minecraft.getMinecraft().thePlayer;
			GL11.glPushMatrix();

			//this.applyTranslationAndRotation(self, entityPlayer, partialTickTime, false);

			float yawOffset = this.interpolateRotation(entityPlayer.prevRenderYawOffset, entityPlayer.renderYawOffset, partialTickTime);
			float yawHead = this.interpolateRotation(entityPlayer.prevRotationYawHead, entityPlayer.rotationYawHead, partialTickTime);

			float f4 = 0;

			if (entityPlayer.isRiding() && entityPlayer.ridingEntity instanceof EntityLivingBase) {
				EntityLivingBase entitylivingbase1 = (EntityLivingBase) entityPlayer.ridingEntity;
				yawOffset = this.interpolateRotation(entitylivingbase1.prevRenderYawOffset, entitylivingbase1.renderYawOffset, partialTickTime);
				f4 = MathHelper.wrapAngleTo180_float(yawHead - yawOffset);

				if (f4 < -85.0F) {
					f4 = -85.0F;
				}

				if (f4 >= 85.0F) {
					f4 = 85.0F;
				}

				yawOffset = yawHead - f4;

				if (f4 * f4 > 2500.0F) {
					yawOffset += f4 * 0.2F;
				}
			}

			float rotationPitch = entityPlayer.prevRotationPitch + (entityPlayer.rotationPitch - entityPlayer.prevRotationPitch) * partialTickTime;

			this.setModelProperties(this.model, renderplayer.modelBipedMain);
			
			//model.render(entityPlayer, 0, 0, f4, (yawHead - yawOffset), rotationPitch, 0.0625f);
			this.renderModel(entityPlayer, 0, 0, f4, (yawHead - yawOffset), rotationPitch,showSlot);

			GL11.glPopMatrix();
		}
	}

	protected void setModelProperties(ModelBiped model, ModelBiped mainModel){
		model.isSneak = mainModel.isSneak;
		model.isChild = mainModel.isChild;
		model.isRiding = mainModel.isRiding;
		model.onGround = mainModel.onGround;
	}
	
	protected void renderModel(EntityLivingBase elb, float limb1, float limb2, float f4, float yaw, float pitch, boolean showslot){
		model.render(elb, limb1, limb2, f4, yaw, pitch, 0.0625f);
	}
	
}
