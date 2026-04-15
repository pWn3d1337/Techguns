package techguns.client.renderer.additionalslots;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import techguns.Techguns;
import techguns.client.models.ModelBipedMultipart;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.util.MathUtil;

public class RenderJetpack extends RenderAdditionalArmorModel {
//	private ResourceLocation[] textures;
//	private ModelMultipart model;

	protected ModelBipedMultipart modelMultipart;
	
	public RenderJetpack(ResourceLocation[] textures, ModelBipedMultipart model) {
		super(textures, model);
		this.modelMultipart = model;
		this.ignoreShowSlot=true;
	}

	/*@Override
	public void render(ItemStack item, EntityPlayer entityPlayer, RenderPlayer renderplayer, float partialTickTime, boolean showslot) {
		//if(showslot){
		
		
			if ( item.getItem() instanceof ICamoChangeable){
				ICamoChangeable camoitem = (ICamoChangeable)  item.getItem();
				Minecraft.getMinecraft().getTextureManager().bindTexture(textures[camoitem.getCurrentCamoIndex(item)]);
			} else {
				Minecraft.getMinecraft().getTextureManager().bindTexture(textures[0]);
			}
		
			//Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
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
			
			boolean showFlames=false;
			
			//showFlames = ((ItemJetpack)item.getItem()).getBoosting(item);
			
		
			//showFlames=((entityPlayer.posY-entityPlayer.prevPosY)>0);
			TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(entityPlayer);
			
			if(props!=null){
				showFlames=props.isJumpkeyPressed();
			} else {
				showFlames=false;
			}
			
			//System.out.println("Playerpos:"+entityPlayer.posX+","+entityPlayer.posY+","+entityPlayer.posZ);
			//System.out.println("Motion:"+entityPlayer.motionY);
	
				
			if(showFlames){
				
				float rot = -entityPlayer.renderYawOffset;
				
				double dZ = -0.3;//(Keybinds.Z);
				double dX = 0.2;//(Keybinds.X);
				
				if(entityPlayer.isSneaking()){
					dZ = -0.45;
				}
				
				
				double offsetZ = dZ*MathUtil.cos360(rot) - dX*MathUtil.sin360(rot);
				double offsetX = dZ*MathUtil.sin360(rot) + dX*MathUtil.cos360(rot);
				
				double offsetZ2 = dZ*MathUtil.cos360(rot) - (dX*-1)*MathUtil.sin360(rot);
				double offsetX2 = dZ*MathUtil.sin360(rot) + (dX*-1)*MathUtil.cos360(rot);
				
				
				float motiony= -0.025f;
				if (entityPlayer.motionY<motiony){
					motiony=(float) entityPlayer.motionY;
				}
				
				float offsetY=-0.7f;
				if(entityPlayer!=self){
					offsetY=+0.7f;
				}
				
				Techguns.proxy.spawnParticle("Jetpack", entityPlayer.worldObj, entityPlayer.posX+offsetX,entityPlayer.posY+offsetY, entityPlayer.posZ+offsetZ,0,motiony,0);
				Techguns.proxy.spawnParticle("Jetpack", entityPlayer.worldObj, entityPlayer.posX+offsetX2,entityPlayer.posY+offsetY, entityPlayer.posZ+offsetZ2,0,motiony,0);
				//System.out.println("Particles!!!");
			}

			
			
			GL11.glPopMatrix();
		//}
	}*/

	@Override
	protected void renderModel(EntityLivingBase elb, float limb1, float limb2, float f4, float yaw, float pitch, boolean showslot) {
		//super.renderModel(elb, limb1, limb2, f4, yaw, pitch, showslot);
		modelMultipart.render(elb, limb1, limb2, f4, yaw, pitch, 0.0625f, 0, 0.0f, ItemRenderType.EQUIPPED, 0, 0.0f);
		
		if (elb instanceof EntityPlayer){
			EntityPlayer entityPlayer = (EntityPlayer) elb;
			boolean showFlames=false;
			
			TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(entityPlayer);
			
			if(props!=null){
				showFlames=props.isJumpkeyPressed();
			} else {
				showFlames=false;
			}
				
			if(showFlames){
				
				float rot = -entityPlayer.renderYawOffset;
				
				double dZ = -0.3;//(Keybinds.Z);
				double dX = 0.2;//(Keybinds.X);
				
				if(entityPlayer.isSneaking()){
					dZ = -0.45;
				}
				
				double offsetZ = dZ*MathUtil.cos360(rot) - dX*MathUtil.sin360(rot);
				double offsetX = dZ*MathUtil.sin360(rot) + dX*MathUtil.cos360(rot);
				
				double offsetZ2 = dZ*MathUtil.cos360(rot) - (dX*-1)*MathUtil.sin360(rot);
				double offsetX2 = dZ*MathUtil.sin360(rot) + (dX*-1)*MathUtil.cos360(rot);
				
				
				float motiony= -0.025f;
				if (entityPlayer.motionY<motiony){
					motiony=(float) entityPlayer.motionY;
				}
				
				float offsetY=-0.7f;
				/*if(entityPlayer!=self){
					offsetY=+0.7f;
				}*/
				
				Techguns.proxy.spawnParticle("Jetpack", entityPlayer.worldObj, entityPlayer.posX+offsetX,entityPlayer.posY+offsetY, entityPlayer.posZ+offsetZ,0,motiony,0);
				Techguns.proxy.spawnParticle("Jetpack", entityPlayer.worldObj, entityPlayer.posX+offsetX2,entityPlayer.posY+offsetY, entityPlayer.posZ+offsetZ2,0,motiony,0);
			}
		}
	}

}
