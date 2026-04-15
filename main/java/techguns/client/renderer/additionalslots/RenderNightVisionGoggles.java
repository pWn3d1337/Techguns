package techguns.client.renderer.additionalslots;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class RenderNightVisionGoggles extends RenderAdditionalArmorModel {

	//private ResourceLocation texture; // = new ResourceLocation("techguns:textures/armor/gasMask.png");
	//private ModelBiped model; //==new ModelGasMask();
	protected ModelBiped model2;
	public RenderNightVisionGoggles(ResourceLocation texture, ModelBiped model,ModelBiped model2) {
		super(new ResourceLocation[]{texture}, model);
	//	this.texture = texture;
		//this.model = model;
		this.model2 = model2;
	}
	@Override
	protected void renderModel(EntityLivingBase elb, float limb1, float limb2, float f4, float yaw, float pitch, boolean showslot) {
	if(elb.getActivePotionEffect(Potion.nightVision)!=null){
			model.render(elb, limb1,limb2,f4,yaw,pitch, 0.0625f);
		} else {
			model2.render(elb, limb1,limb2,f4,yaw,pitch, 0.0625f);
		}
		
	}
	@Override
	protected void setModelProperties(ModelBiped model, ModelBiped mainModel) {
		super.setModelProperties(model, mainModel);
		super.setModelProperties(model2, mainModel);
	}

	
	
	
	
//	@Override
//	public void render(ItemStack item, EntityPlayer entityPlayer,RenderPlayer renderplayer,float partialTickTime, boolean showSlot){
//		if (showSlot){
		//event.renderer.
		/*	Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			EntityPlayer self = Minecraft.getMinecraft().thePlayer;
			GL11.glPushMatrix();
			float offset=0.0f;
			if(entityPlayer!=self){
				offset=1.6f;
			}
			GL11.glTranslated(-interp(self.prevPosX,self.posX, partialTickTime)+interp(entityPlayer.prevPosX, entityPlayer.posX,partialTickTime),
					-interp(self.prevPosY,self.posY, partialTickTime)+interp(entityPlayer.prevPosY, entityPlayer.posY,partialTickTime)+offset,
					-interp(self.prevPosZ,self.posZ, partialTickTime)+interp(entityPlayer.prevPosZ, entityPlayer.posZ,partialTickTime));
			GL11.glRotatef(180.0f, 1, 0, 0);
			GL11.glTranslatef(0.0f, 0.225f, 0);
		
			float yawHead = interpolateRotation(entityPlayer.prevRotationYawHead, entityPlayer.rotationYawHead, partialTickTime);
			float pitch = interpolateRotation(entityPlayer.prevRotationPitch, entityPlayer.rotationPitch, partialTickTime);
			GL11.glRotatef(yawHead, 0, 1, 0);
			GL11.glRotatef(pitch, 1, 0, 0);

			if(entityPlayer.getActivePotionEffect(Potion.nightVision)!=null){
				model.render(entityPlayer, (float)entityPlayer.posX, (float)entityPlayer.posY, (float)entityPlayer.posZ, 0,0, 0.0625f);
			} else {
				model2.render(entityPlayer, (float)entityPlayer.posX, (float)entityPlayer.posY, (float)entityPlayer.posZ, 0,0, 0.0625f);
			}
			GL11.glPopMatrix();*/
			
//			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
//			EntityPlayer self = Minecraft.getMinecraft().thePlayer;
//			GL11.glPushMatrix();
			
//			this.applyTranslationAndRotation(self, entityPlayer, partialTickTime,false);
//			GL11.glTranslatef(0.0f, 0.225f, 0);
			
			//float yawHead = -renderplayer.modelBipedMain.bipedHeadwear.rotateAngleY* (180F / (float)Math.PI);
			//float pitch = renderplayer.modelBipedMain.bipedHeadwear.rotateAngleX* (180F / (float)Math.PI);
//			float yawHead = interpolateRotation(entityPlayer.prevRotationYawHead, entityPlayer.rotationYawHead, partialTickTime);
//			float pitch = interpolateRotation(entityPlayer.prevRotationPitch, entityPlayer.rotationPitch, partialTickTime);
			
//			if (entityPlayer==self && self.isSneaking()){
//				GL11.glTranslated(0, 0.05D, 0);
//			} else if (entityPlayer.isSneaking()){
//				GL11.glTranslated(0, 0.1775D, 0);
//			}
			
//			if(entityPlayer.getActivePotionEffect(Potion.nightVision)!=null){
//				model.render(entityPlayer, 0, 0, 0, yawHead, pitch, 0.0625f);
//			} else {
//				model2.render(entityPlayer, 0, 0, 0, yawHead, pitch, 0.0625f);
//			}
//			
//			GL11.glPopMatrix();
//		}
//	}

}
