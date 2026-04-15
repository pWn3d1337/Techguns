package techguns.client.renderer.additionalslots;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import techguns.client.models.ModelBipedMultipart;

public class RenderGlider extends RenderAdditionalArmorModel {

	protected ModelBipedMultipart modelMultipart;
	
	public RenderGlider(ResourceLocation[] textures, ModelBipedMultipart model) {
		super(textures, model);
		this.modelMultipart=model;
		this.ignoreShowSlot=true;
	}

	@Override
	protected void renderModel(EntityLivingBase elb, float limb1, float limb2, float f4, float yaw, float pitch, boolean showSlot) {

		if(showSlot){
			modelMultipart.render(elb, limb1, limb2, f4, yaw, pitch, 0.0625f, 0, 0.0f, ItemRenderType.EQUIPPED, 0, 0.0f);
		}
	//	System.out.println("G:"+elb.onGround+" S:"+elb.isSneaking());
		if (!elb.onGround && elb.isSneaking()){
			modelMultipart.render(elb, limb1, limb2, f4, yaw, pitch, 0.0625f, 0, 0.0f, ItemRenderType.EQUIPPED, 1, 0.0f);
		}
	}
	
	
	
//	private static ResourceLocation texture = new ResourceLocation("techguns:textures/armor/glider.png");
//	private static ModelGlider model= new ModelGlider();

	
	/*@Override
	public void render(ItemStack item, EntityPlayer entityPlayer, RenderPlayer renderplayer,float partialTickTime, boolean showSlot) {
		// TODO Auto-generated method stub
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
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
		GL11.glTranslatef(0.0f, 0.175f, 0);
		float pitchRotOffset=0.15f;//(float) Keybinds.Y;
		float yaw = interpolateRotation(entityPlayer.prevRenderYawOffset, entityPlayer.renderYawOffset, partialTickTime);
		float f = renderplayer.modelBipedMain.bipedBody.rotateAngleX;
		//float pitch = interpolateRotation(entityPlayer.prevRotationPitch, entityPlayer.rotationPitch, partialTickTime);
		float pitch = 70.0f*f;
		GL11.glRotatef(yaw, 0, 1, 0);
		//System.out.println("BodyRotate"+pitch);
		GL11.glTranslatef(0,pitchRotOffset, 0);
		GL11.glRotatef(pitch, 1, 0, 0);
		GL11.glTranslatef(0,-pitchRotOffset, 0);
		//System.out.println("HeadrotX:"+);
		//System.out.println("HeadrotY:"+renderplayer.modelBipedMain.bipedHead.rotateAngleY);
		//System.out.println("HeadrotZ:"+renderplayer.modelBipedMain.bipedHead.rotateAngleZ);
		//GL11.glRotatef(renderplayer.modelBipedMain.bipedHead.rotateAngleY, 0, 1, 0);
		//GL11.glRotatef(renderplayer.modelBipedMain.bipedHead.rotateAngleX, 1, 0, 0);
		if(showSlot){
			model.render(entityPlayer, (float)entityPlayer.posX, (float)entityPlayer.posY, (float)entityPlayer.posZ, 0,0, 0.0625f,0,0.0f, ItemRenderType.ENTITY,0, 0.0f);
		}
		if (!entityPlayer.onGround && entityPlayer.isSneaking()){
			model.render(entityPlayer, (float)entityPlayer.posX, (float)entityPlayer.posY, (float)entityPlayer.posZ, 0,0, 0.0625f,0,0.0f, ItemRenderType.ENTITY,1, 0.0f);
		}
		GL11.glPopMatrix();
	}
*/
}
