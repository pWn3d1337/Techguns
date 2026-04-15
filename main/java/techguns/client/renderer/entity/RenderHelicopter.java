package techguns.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import techguns.client.models.ModelNull;
import techguns.client.renderer.TGRenderHelper;
import techguns.entities.npc.AttackHelicopter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHelicopter extends RendererLivingEntity
{
    private ResourceLocation texture;
    private IModelCustom model;

    public RenderHelicopter(String model, String texture)
    {
    	super (new ModelNull(), 5.0f);
    	this.texture = new ResourceLocation("techguns", texture);
    	this.model = AdvancedModelLoader.loadModel(new ResourceLocation("techguns",model));
    }
    

    public void doRender(AttackHelicopter entity, double x,
    		double y, double z, float par8,
    		float partialTickTime) {
    	
    	 float yaw = TGRenderHelper.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTickTime);
    	 float yawHead = TGRenderHelper.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTickTime);
    	 float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTickTime;
    	 
    	 BossStatus.setBossStatus(entity, true);
    	 GL11.glPushMatrix();
         GL11.glTranslated(x, y, z);
         GL11.glScaled(0.25, 0.25, 0.25);
         GL11.glRotated(yaw, 0, 1, 0);
         GL11.glRotated(pitch, 0, 0, 1);
         
        

         bindTexture(texture);
         
         if (entity.deathTime == 0) {
	         model.renderAllExcept("ROTOR", "GUN");     
	         //Gun
	         GL11.glPushMatrix();
	         GL11.glTranslatef(16.0f, -1f, 0.0f);
	         GL11.glRotatef(-yaw+yawHead, 0, 1f, 0);
	         GL11.glRotatef(-25f, 0, 0, 1f);
	         GL11.glTranslatef(-16.0f, 1f, 0.0f);
	         model.renderOnly("GUN");
	         GL11.glPopMatrix();
	         //---
	         
	         //-Animate rotor-
	         double angle = ((float)((Minecraft.getMinecraft().theWorld.getTotalWorldTime() % 60)+partialTickTime) * 24.0f);
	         GL11.glRotated(angle, 0, 1, 0);
	         model.renderOnly("ROTOR");
	         
         }else {
        	 float deathProgress = ((float)entity.deathTime+partialTickTime) / (float)entity.maxDeathTime;
        	 float p = (float) Math.pow(deathProgress, 2);
        	   
        	 GL11.glTranslatef(0, p*-40f, 0);
        	 
        	 float angle = p * 720.0f;
	         GL11.glRotatef(angle, 0, 1.0f, 0);
        	 model.renderAllExcept("ROTOR");      
	         angle = ((float)((Minecraft.getMinecraft().theWorld.getTotalWorldTime() % 60)+partialTickTime) * 12.0f);
	         GL11.glRotatef(angle, 0, 1.0f, 0);
	         model.renderOnly("ROTOR");
         }

         //OpenGL stuff to put everything back
         GL11.glPopMatrix();
    }
    
    
    

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(Entity e)
    {
        return texture;//p_110775_1_.func_110182_bF() ? ghastShootingTextures : ghastTextures;
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    @Override
    protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
    {
    	
    }
    
    @Override
    public void doRender(Entity e, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((AttackHelicopter)e, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

}