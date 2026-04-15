package techguns.client.renderer.additionalslots;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelMultipart;
import techguns.items.armors.ICamoChangeable;

public class RenderAntiGravPack extends RenderAdditionalSlotItem {

	private ResourceLocation[] textures;
	private ModelMultipart model;

	public RenderAntiGravPack(ResourceLocation[] textures, ModelMultipart model) {
		this.textures = textures;
		this.model = model;
	}
	
	@Override
	public void render(ItemStack item, EntityPlayer entityPlayer, RenderPlayer renderplayer, float partialTickTime, boolean showslot) {
		if(showslot){
			
			if ( item.getItem() instanceof ICamoChangeable){
				ICamoChangeable camoitem = (ICamoChangeable)  item.getItem();
				Minecraft.getMinecraft().getTextureManager().bindTexture(textures[camoitem.getCurrentCamoIndex(item)]);
			} else {
				Minecraft.getMinecraft().getTextureManager().bindTexture(textures[0]);
			}
			
			EntityPlayer self = Minecraft.getMinecraft().thePlayer;
			GL11.glPushMatrix();
			
		//	this.applyTranslationAndRotation(self, entityPlayer, partialTickTime,false);
			/*float yaw = interpolateRotation(entityPlayer.prevRotationYaw, entityPlayer.rotationYaw, partialTickTime);		
			float yawHead = interpolateRotation(entityPlayer.prevRotationYawHead, entityPlayer.rotationYawHead, partialTickTime);
			float pitch = interpolateRotation(entityPlayer.prevRotationPitch, entityPlayer.rotationPitch, partialTickTime);
			*/
			
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
	}
}
