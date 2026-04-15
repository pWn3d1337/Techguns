package techguns.client.renderer.additionalslots;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.items.armors.ICamoChangeable;

public class RenderGasMask extends RenderAdditionalSlotItem {
	private ResourceLocation[] textures; // = new ResourceLocation("techguns:textures/armor/gasMask.png");
	private ModelBiped model; //==new ModelGasMask();
	
	public RenderGasMask(ResourceLocation[] textures, ModelBiped model) {
		super();
		this.textures = textures;
		this.model = model;
	}

	@Override
	public void render(ItemStack item, EntityPlayer entityPlayer,RenderPlayer renderplayer,float partialTickTime, boolean showSlot){
		if (showSlot){
		//event.renderer.
			
			if ( item.getItem() instanceof ICamoChangeable){
				ICamoChangeable camoitem = (ICamoChangeable)  item.getItem();
				Minecraft.getMinecraft().getTextureManager().bindTexture(textures[camoitem.getCurrentCamoIndex(item)]);
			} else {
				Minecraft.getMinecraft().getTextureManager().bindTexture(textures[0]);
			}
			EntityPlayer self = Minecraft.getMinecraft().thePlayer;
			GL11.glPushMatrix();
			/*float offset=0.0f;
			if(entityPlayer!=self){
				offset=1.6f;
			}
			GL11.glTranslated(-interp(self.prevPosX,self.posX, partialTickTime)+interp(entityPlayer.prevPosX, entityPlayer.posX,partialTickTime),
					-interp(self.prevPosY,self.posY, partialTickTime)+interp(entityPlayer.prevPosY, entityPlayer.posY,partialTickTime)+offset,
					-interp(self.prevPosZ,self.posZ, partialTickTime)+interp(entityPlayer.prevPosZ, entityPlayer.posZ,partialTickTime));
			GL11.glRotatef(180.0f, 1, 0, 0);
			GL11.glTranslatef(0.0f, 0.225f, 0);*/
			
		//	this.applyTranslationAndRotation(self, entityPlayer, partialTickTime,false);
			//GL11.glTranslatef(0.0f, 0.225f, 0);
			
			//float yawHead = -renderplayer.modelBipedMain.bipedHeadwear.rotateAngleY* (180F / (float)Math.PI);
			//float pitch = renderplayer.modelBipedMain.bipedHeadwear.rotateAngleX* (180F / (float)Math.PI);
			//float yawHead = interpolateRotation(entityPlayer.prevRotationYawHead, entityPlayer.rotationYawHead, partialTickTime);
			//float pitch = interpolateRotation(entityPlayer.prevRotationPitch, entityPlayer.rotationPitch, partialTickTime);

			 float yawOffset = this.interpolateRotation(entityPlayer.prevRenderYawOffset, entityPlayer.renderYawOffset, partialTickTime);
			 float yawHead = this.interpolateRotation(entityPlayer.prevRotationYawHead, entityPlayer.rotationYawHead, partialTickTime);
		       
			 float f4 = 0;

		        if (entityPlayer.isRiding() && entityPlayer.ridingEntity instanceof EntityLivingBase)
		        {
		            EntityLivingBase entitylivingbase1 = (EntityLivingBase)entityPlayer.ridingEntity;
		            yawOffset = this.interpolateRotation(entitylivingbase1.prevRenderYawOffset, entitylivingbase1.renderYawOffset, partialTickTime);
		            f4 = MathHelper.wrapAngleTo180_float(yawHead - yawOffset);

		            if (f4 < -85.0F)
		            {
		                f4 = -85.0F;
		            }

		            if (f4 >= 85.0F)
		            {
		                f4 = 85.0F;
		            }

		            yawOffset = yawHead - f4;

		            if (f4 * f4 > 2500.0F)
		            {
		                yawOffset += f4 * 0.2F;
		            }
		        }
			
		        float rotationPitch = entityPlayer.prevRotationPitch + (entityPlayer.rotationPitch - entityPlayer.prevRotationPitch) * partialTickTime;
		        
		        
			
			/*modelB.bipedBody.rotateAngleX = renderplayer.modelBipedMain.bipedBody.rotateAngleX;
			modelB.bipedBody.rotateAngleY = renderplayer.modelBipedMain.bipedBody.rotateAngleY;
			modelB.bipedBody.rotateAngleZ = renderplayer.modelBipedMain.bipedBody.rotateAngleZ;*/
			//modelB.bipedBody = renderplayer.modelBipedMain.bipedBody;
			//System.out.println("Sneak:?"+modelB.isSneak);
			//modelB.isSneak = renderplayer.modelBipedMain.isSneak;
			//System.out.println("Sneak:?"+modelB.isSneak);
			
			//float limbSwingAmount = (float) interp(entityPlayer.prevLimbSwingAmount, entityPlayer.limbSwingAmount,partialTickTime);
			
			//copyModelRendererValues(renderplayer.modelBipedMain.bipedHead, modelB.bipedHead);
			//copyModelRendererValues(renderplayer.modelBipedMain.bipedBody, modelB.bipedBody);
			//renderplayer.modelBipedMain.
			
			//GL11.glRotatef(yawHead, 0, 1, 0);
			//GL11.glRotatef(pitch, 1, 0, 0);
			
			//System.out.println("HeadrotX:"+);
			//System.out.println("HeadrotY:"+renderplayer.modelBipedMain.bipedHead.rotateAngleY);
			//System.out.println("HeadrotZ:"+renderplayer.modelBipedMain.bipedHead.rotateAngleZ);
			//GL11.glRotatef(renderplayer.modelBipedMain.bipedHead.rotateAngleY, 0, 1, 0);
			//GL11.glRotatef(renderplayer.modelBipedMain.bipedHead.rotateAngleX, 1, 0, 0);
			//model.render(entityPlayer, (float)entityPlayer.posX, (float)entityPlayer.posY, (float)entityPlayer.posZ, 0,0, 0.0625f);
			//modelB.setLivingAnimations(entityPlayer, 0, 0, partialTickTime);
		//	if (entityPlayer==self && self.isSneaking()){
		//		GL11.glTranslated(0, 0.05D, 0);
		//	} else if (entityPlayer.isSneaking()){
		//		GL11.glTranslated(0, 0.1775D, 0);
		//	}
			//GL11.glTranslated(0, Keybinds.Y, 0);
		    model.isSneak = renderplayer.modelBipedMain.isSneak;
		    //model.setLivingAnimations(p_78086_1_, p_78086_2_, p_78086_3_, p_78086_4_);
			model.render(entityPlayer, 0, 0, f4, (yawHead-yawOffset), rotationPitch, 0.0625f);
			//modelB.bipedHead.render(0.0625f);
			GL11.glPopMatrix();
		}
	}
	
	private void copyModelRendererValues(ModelRenderer from, ModelRenderer to){
		to.offsetX=from.offsetX;
		to.offsetY=from.offsetY;
		to.offsetZ=from.offsetZ;
		
		to.rotateAngleX=from.rotateAngleX;
		to.rotateAngleY=from.rotateAngleY;
		to.rotateAngleZ=from.rotateAngleZ;
		
		to.rotationPointX=from.rotationPointX;
		to.rotationPointY=from.rotationPointY;
		to.rotationPointZ=from.rotationPointZ;
	}
	 
}
