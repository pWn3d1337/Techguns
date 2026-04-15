package techguns.client.renderer.additionalslots;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import techguns.Techguns;
import techguns.client.ClientProxy;
import techguns.client.models.ModelBipedMultipart;
import techguns.client.models.ModelMultipart;
import techguns.client.models.ModelPistol3;
import techguns.client.models.ModelPistol3Akimbo;
import techguns.debug.Keybinds;
import techguns.entities.npc.GenericNPC;
import techguns.entities.npc.NPCTurret;

public class RenderAkimboGun extends RenderAdditionalSlotItem {
	

	protected ModelMultipart model; // = new ModelPistol3(); //new ModelPistol3Akimbo();
	protected ResourceLocation gunTex; // = new ResourceLocation("techguns","textures/guns/pistol3.png");
	
	protected int parts=1; // = 2;
	
	protected boolean doubleShot=false;
	
	public RenderAkimboGun(ModelMultipart model, ResourceLocation gunTex, int parts) {
		super();
		this.model = model;
		this.gunTex = gunTex;
		this.parts = parts;
	}
	public RenderAkimboGun(ModelMultipart model, ResourceLocation gunTex, int parts, boolean doubleShot) {
		this(model, gunTex, parts);
		this.doubleShot=doubleShot;
	}
	
	
	@Override
	public void render(ItemStack item, EntityPlayer entityPlayer, RenderPlayer renderplayer, float partialTickTime, boolean hideSlot) {
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(gunTex);
	
       
		GL11.glPushMatrix();

		//GL11.glRotatef(-90, 1, 0,0);
        
        //GL11.glRotatef(90.0f, 1, 0, 0);
		
		EntityPlayer self = Minecraft.getMinecraft().thePlayer;
		//this.applyTranslationAndRotation(self, entityPlayer, partialTickTime,true);
		GL11.glRotatef(180.0f, 1, 0, 0);
			
/*		float x= (float) Keybinds.X;
		float y= (float) Keybinds.Y;
		float z= (float) Keybinds.Z;*/
		
	//	float x=0;
	//	float y=0;
	//	float z=0;
		

        
		//GL11.glTranslatef(-x,-y,-z);
      //  GL11.glRotatef(-90.0f, 1, 0, 0);
		//GL11.glTranslatef(x,y,z);

      	

		//GL11.glTranslatef(0.0f, 0.225f, 0);
	
//		float yawHead = interpolateRotation(entityPlayer.prevRotationYawHead, entityPlayer.rotationYawHead, partialTickTime);
//		float pitch = interpolateRotation(entityPlayer.prevRotationPitch, entityPlayer.rotationPitch, partialTickTime);
		
	//	this.model.aimedBow=true;
		
    /*    float yawOffset = this.interpolateRotation(entityPlayer.prevRenderYawOffset, entityPlayer.renderYawOffset, partialTickTime);
        float yawHead = this.interpolateRotation(entityPlayer.prevRotationYawHead, entityPlayer.rotationYawHead, partialTickTime);
        float f4;

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
        }*/

    //    float rotationPitch = entityPlayer.prevRotationPitch + (entityPlayer.rotationPitch - entityPlayer.prevRotationPitch) * partialTickTime;
        
        
        
     //   this.renderLivingAt(entityPlayer, p_76986_2_, p_76986_4_, p_76986_6_);
  //      f4 = this.handleRotationFloat(entityPlayer, partialTickTime);
     //   this.rotateCorpse(entityPlayer, f4, yawOffset, partialTickTime);
        float modelScale = 0.0625F;
    //    GL11.glEnable(GL12.GL_RESCALE_NORMAL);
   //     GL11.glScalef(-1.0F, -1.0F, 1.0F);
        //GL11.glRotatef(180.0f, 0, 0, 1);
       // this.preRenderCallback(entityPlayer, partialTickTime);
    //    GL11.glTranslatef(0.0F, -24.0F * modelScale - 0.0078125F, 0.0F);´
        
     //   GL11.glPushMatrix();

//		GL11.glPushMatrix();
        
      /*  System.out.println("RotYaw:"+entityPlayer.rotationYaw);
        System.out.println("RotYawHead:"+entityPlayer.rotationYawHead);
        System.out.println("RendYawOffset:"+entityPlayer.renderYawOffset);
        System.out.println("F4:"+f4);*/
        
        
   /*    float limbSwingAmount = entityPlayer.prevLimbSwingAmount + (entityPlayer.limbSwingAmount - entityPlayer.prevLimbSwingAmount) * partialTickTime;
        float limbSwing = entityPlayer.limbSwing - entityPlayer.limbSwingAmount * (1.0F - partialTickTime);

        if (entityPlayer.isChild())
        {
            limbSwing *= 3.0F;
        }

        if (limbSwingAmount > 1.0F)
        {
            limbSwingAmount = 1.0F;
        }*/
		
     //   GL11.glPushMatrix();

        
        //model.heldItemLeft=1;
		//model.setLivingAnimations(entityPlayer, limbSwing, limbSwingAmount, partialTickTime);
		
	//	model.bipedLeftArm.postRender(modelScale);
			
        //GL11.glRotatef(yawOffset, 0, 1, 0);
        
        
      GL11.glScalef(1,-1,-1);
        renderplayer.modelBipedMain.bipedLeftArm.postRender(modelScale);   
      GL11.glScalef(1,-1,-1);
        
     GL11.glTranslatef(0,0.3f,0);
      
   //   GL11.glTranslatef(0, -0.3f, 0);
      
    /*    GL11.glRotatef(180, 0, 0, 1);
        
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glRotatef(180, 0, 1, 0);*/
        
       this.applyWeaponRotation();
        
   //     GL11.glTranslatef(-x,-y,-z);
        

        
      //  GL11.glTranslatef(0.5f, -0.4f, -1.5f);
        
    //    GL11.glRotatef(90, 0, 0, 1);
 //       GL11.glScalef(1,1,-1);

 //       GL11.glScalef(1,1,-1);
     //   GL11.glRotatef(-90, 0, 0, 1);
 
    //    GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    //    GL11.glScalef(1.0F, -1.0F, -1.0F);
        
       /* GL11.glRotatef(-90.0f, 1, 0, 0);
        
        GL11.glRotatef(180, 0, 0, 1);*/
        
				//GL11.glRotated(Keybinds.Z, 0, 0, 1);		

		
        
        
		//model.render(entityPlayer, limbSwing, limbSwingAmount, f4, (yawHead-yawOffset), rotationPitch, modelScale, 0, 0.0f, ItemRenderType.EQUIPPED, 0, 0.0f);
		//model.heldItemLeft=0;
		
		//model.render(entityPlayer, 0, 0, f4, (yawHead-yawOffset), rotationPitch, modelScale, 0, 0.0f, ItemRenderType.EQUIPPED, 0, 0.0f);
		
		float s = this.getScaleValue();
		GL11.glScalef(s,s,s);
        
        //GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
		
		this.applyTranslation();
		
        for (int i=0; i<parts;i++){
        	GL11.glPushMatrix();
        	this.handleReloadAndRecoil(entityPlayer, i);
        	model.render(entityPlayer, 0, 0, 0, 0, 0, modelScale, 0, 0.0f, ItemRenderType.EQUIPPED, i, 0.0f);
        	GL11.glPopMatrix();
        }
		/*if (entityPlayer==self && self.isSneaking()){
			GL11.glTranslated(0, 0.05D, 0);
		} else if (entityPlayer.isSneaking()){
			GL11.glTranslated(0, 0.1775D, 0);
		}*/
		//model.render(entityPlayer, 0, 0, 0, yawHead, pitch, 0.0625f);
		//model.setLivingAnimations(entityPlayer, limbSwing, limbSwingAmount, partialTickTime);
		//model.render(entityPlayer, limbSwingAmount, limbSwing, 0, yawHead, pitch, 0.0625f, 10, 0, ItemRenderType.EQUIPPED, 0, 0);
	//	GL11.glPopMatrix();
	//	GL11.glPopMatrix();
	//	GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     * copied from renderLivingEntity
     */
 /*   protected float handleRotationFloat(EntityLivingBase ent, float f1)
    {
        return (float)ent.ticksExisted + f1;
    }
	*/
	
	protected void handleReloadAndRecoil(EntityLivingBase shooter, int part){
		ClientProxy cp = ClientProxy.get();
		byte attackType = cp.getplayerAttackType(shooter);
			//float progress=0.0f;
			float prog = 0.0f;
			float reloadProgress = 0;
			boolean reloading = false;
			
			if (cp.getplayerReloadtime(shooter) > 0){
				
				long diff = cp.getplayerReloadtime(shooter) - System.currentTimeMillis();
				
				if (diff <= 0) {
					//ClientProxy.setplayerReloadtime(shooter,0);
					//ClientProxy.setplayerReloadtimeTotal(shooter,0);
					cp.setplayerReloadtime(shooter, 0, 0,(byte)0);
					
					//this.GL_Transform_Translate(i);

				}else {

					long reloadtimeTotal = cp.getplayerReloadtimeTotal(shooter);
					reloadProgress = 1.0f-((float)diff / (float)reloadtimeTotal);
					reloading=true;
					//this.GL_TransformReload_Equipped(i,reloadProgress);

				}
				
			} else if( cp.getplayerRecoiltime(shooter) > 0) {
				long diff = cp.getplayerRecoiltime(shooter) - System.currentTimeMillis();
				//System.out.println("firing: "+diff);
				if (diff <= 0 || diff > cp.getplayerRecoiltimeTotal(shooter)) {
					//System.out.println("firing done");
					//ClientProxy.setplayerRecoiltime(shooter, 0);
					//ClientProxy.setplayerRecoiltimeTotal(shooter, 0);
					cp.setplayerRecoiltime(shooter, 0, 0, (byte)0);
					
					//this.GL_Transform_Translate(i);
				}else{

					prog = 1.0f-((float)diff / (float)cp.getplayerRecoiltimeTotal(shooter));
					
					
					//System.out.println("FireProgress"+prog);
					//this.GL_Transform_firing(i,fireProgress);
				}
			}
	//		boolean aimed = shooter instanceof EntityPlayer || (shooter instanceof GenericNPC  && ((GenericNPC)shooter).getHasAimedBowAnim() ) ;
	/*		if(aimed){
				this.transformEquippedAimedRotation(i);
			}
			
			this.GL_Transform_Equipped(i,prog, attackType);*/
			
			/*if(aimed){
				this.transformRotateAimed(i);
			}*/
			
			//no recoil for turrets
/*			if ((shooter instanceof NPCTurret)){
				prog=0.0f;
			}*/

				if(reloading){
					this.GL_TransformReload_Equipped(part,reloadProgress);
				} else if (this.doubleShot || attackType==1) {
					this.GL_TransformRecoil_Equipped(part,prog);
				}
		}

		protected void GL_TransformReload_Equipped(int part, float prog) {
			float offsetZ=0.9f;
			float offsetY=0.3f;
			GL11.glTranslatef(0,offsetY, offsetZ);
			float y = Math.min(0.5f,(Math.abs((0.5f-prog))-0.5f)*2.0f);
			GL11.glRotatef(40.0f*-y,1.0f,0,0);
			//GL11.glRotatef(-40.0f*y,0,1,0);
			GL11.glTranslatef(0,-offsetY, -offsetZ);
			
		}
	
		protected void GL_TransformRecoil_Equipped(int part, float prog) {
			float offsetZ=0.7f;
		//	float offsetY=(float) Keybinds.Y;
		//	float offsetX=(float) Keybinds.X;
			GL11.glTranslatef(0,0,offsetZ);
			if(prog<0.3f){
				GL11.glRotatef(prog/0.3f*-30, 1, 0, 0);
			}
			GL11.glTranslatef(-0,-0,-offsetZ);
			//GL11.glTranslatef(0,0,-offsetZ);
		}
		
		protected void applyWeaponRotation(){
			 GL11.glRotatef(-90.0f, 1, 0, 0);
		}
		
		protected float getScaleValue(){
			return 0.55f;
		}
		
		protected void applyTranslation(){
			GL11.glTranslatef(0.2f,-0.5f, -2.0f);
		}
}
