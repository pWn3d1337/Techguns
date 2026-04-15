package techguns.client.renderer.additionalslots;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import techguns.client.models.ModelBipedMultipart;

public class RenderOxygenFlasks extends RenderAdditionalArmorModel {

//	private ResourceLocation texture;
	private ModelBipedMultipart modelMultipart;

	public RenderOxygenFlasks(ResourceLocation[] textures, ModelBipedMultipart model) {
		super(textures, model);
		this.modelMultipart = model;
	}

	@Override
	protected void renderModel(EntityLivingBase elb, float limb1, float limb2, float f4, float yaw, float pitch, boolean showslot) {
		modelMultipart.render(elb, limb1,limb2, f4, yaw, pitch, 0.0625f, 0, 0f, ItemRenderType.EQUIPPED, 0, 0F);
	}
	
	/*@Override
	public void render(ItemStack item, EntityPlayer entityPlayer, RenderPlayer renderplayer, float partialTickTime, boolean showslot) {
		if(showslot){
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			EntityPlayer self = Minecraft.getMinecraft().thePlayer;
			GL11.glPushMatrix();
			
	//		this.applyTranslationAndRotation(self, entityPlayer, partialTickTime,false);
			float yaw = interpolateRotation(entityPlayer.prevRotationYaw, entityPlayer.rotationYaw, partialTickTime);		
			float yawHead = interpolateRotation(entityPlayer.prevRotationYawHead, entityPlayer.rotationYawHead, partialTickTime);
			float pitch = interpolateRotation(entityPlayer.prevRotationPitch, entityPlayer.rotationPitch, partialTickTime);
			
			
			GL11.glTranslatef(0.0f, 0.175f, 0);
			float pitchRotOffset=0.15f;//(float) Keybinds.Y;
			float yaw = interpolateRotation(entityPlayer.prevRenderYawOffset, entityPlayer.renderYawOffset, partialTickTime);
			float f = renderplayer.modelBipedMain.bipedBody.rotateAngleX;
			float pitch = 70.0f*f;
			GL11.glRotatef(yaw, 0, 1, 0);
			GL11.glTranslatef(0,pitchRotOffset, 0);
			GL11.glRotatef(pitch, 1, 0, 0);
			GL11.glTranslatef(0,-pitchRotOffset, 0);
			
			//model.render(entityPlayer,  0, 0, 0, 0, 0, 0.0625f);
			model.render(entityPlayer, (float)entityPlayer.posX, (float)entityPlayer.posY, (float)entityPlayer.posZ, 0,0, 0.0625f,0,0.0f, ItemRenderType.ENTITY,showslot?0:1, 0.0f);
			
			GL11.glPopMatrix();
		}
	}*/
	
	
	
}
