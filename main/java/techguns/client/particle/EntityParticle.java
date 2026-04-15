package techguns.client.particle;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import techguns.client.particle.ParticleSystemType.AlphaEntry;
import techguns.client.particle.ParticleSystemType.ColorEntry;
import techguns.client.renderer.TGRenderHelper;
import techguns.client.renderer.TGRenderHelper.RenderType;
import techguns.util.MathUtil;

public class EntityParticle extends EntityFX{

	int lifetime;
	
	float size;
	float sizePrev;
	float sizeRate;
	float sizeRateDamping;
	
	float animationSpeed;
	
	double velX;
	double velY;
	double velZ;
	float velocityDamping;
	float velocityDampingOnGround;
	
	float systemVelocityFactor;
	
	EntityParticleSystem particleSystem;
	ParticleSystemType type;
	
	int variationFrame;
	
	int angle;
	
	public EntityParticle(World world, double x,
			double y, double z, double motionX,
			double motionY, double motionZ, EntityParticleSystem particleSystem) {
		super(world, x, y, z, 0, 0, 0);
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
		this.noClip=true;
		this.particleSystem = particleSystem;
		this.type = particleSystem.type;
		
		this.init();
	}
	
	private void init() {
		this.lifetime = MathUtil.randomInt(rand, type.lifetimeMin, type.lifetimeMax);
		this.particleMaxAge = lifetime;
		this.size = MathUtil.randomFloat(rand, type.sizeMin, type.sizeMax);
		this.size+= (this.particleSystem.startSize);
		this.sizeRate = MathUtil.randomFloat(rand, type.sizeRateMin, type.sizeRateMax);
		this.sizeRateDamping = MathUtil.randomFloat(rand, type.sizeRateDampingMin, type.sizeRateDampingMax);
		this.animationSpeed = MathUtil.randomFloat(rand, type.animationSpeedMin, type.animationSpeedMax);
		this.velocityDamping = MathUtil.randomFloat(rand, type.velocityDampingMin, type.velocityDampingMax);
		this.systemVelocityFactor = MathUtil.randomFloat(rand, type.systemVelocityFactorMin, type.systemVelocityFactorMax);
	    this.velocityDampingOnGround = MathUtil.randomFloat(rand, type.velocityDampingOnGroundMin, type.velocityDampingOnGroundMax);
		
		this.motionX+=(systemVelocityFactor*particleSystem.motionX);
		this.motionY+=(systemVelocityFactor*particleSystem.motionY);
		this.motionZ+=(systemVelocityFactor*particleSystem.motionZ);
		
		//System.out.printf("###INIT:Motion=(%.2f / %.2f / %.2f)\n",this.motionX, this.motionY, this.motionZ);
		//System.out.printf("###INIT:Type.VelocityData=[%.2f, %.2f, %.2f]\n",this.type.velocityDataMin[0], this.type.velocityDataMin[1], this.type.velocityDataMin[2]);
		
		this.velX = this.motionX;
		this.velY = this.motionY;
		this.velZ = this.motionZ;
		
		this.variationFrame = rand.nextInt(type.frames);
		
		if (type.randomRotation) {
			angle = rand.nextInt(4);
		}
	}
	
	@Override
	public void onUpdate() {

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		this.sizePrev = this.size;

		lifetime--;
		if (this.particleAge++ >= this.particleMaxAge) {
			this.setDead();
		}
		
		/*---
		 * Move with System
		 */
//		if (this.type.particlesMoveWithSystem) {		
//			double dP = this.particleSystem.rotationPitch - this.particleSystem.prevRotationPitch;
//			double dY = this.particleSystem.rotationYaw - this.particleSystem.prevRotationYaw;
//			
//			Vec3 pos = Vec3.createVectorHelper(this.posX,  this.posY, this.posZ);
//			Vec3 sysPos = Vec3.createVectorHelper(this.particleSystem.posX, this.particleSystem.posY, this.particleSystem.posZ);
//			
//			Vec3 offset = sysPos.subtract(pos);
//			offset.rotateAroundY((float)dY);
//			offset.rotateAroundZ((float)dP);
//			
//			Vec3 motion = Vec3.createVectorHelper(this.motionX, this.motionY, this.motionZ);
//			motion.rotateAroundY((float)dY);
//			motion.rotateAroundZ((float)dP);
//			
//			this.posX = sysPos.xCoord+offset.xCoord;
//			this.posY = sysPos.yCoord+offset.yCoord;
//			this.posZ = sysPos.zCoord+offset.zCoord;
//			
//			this.motionX = motion.xCoord;
//			this.motionY = motion.yCoord;
//			this.motionZ = motion.zCoord;
//		}
		
		
		/* -------------
		 * MOTION
		 */

		this.motionX = velX;
		this.motionY = velY;
		this.motionZ = velZ;
		this.motionY -= type.gravity; //(0.05d * (double) type.gravity * (double) this.ticksExisted);		
		//this.moveEntity(this.motionX, this.motionY, this.motionZ);
		//System.out.printf("Velocity=(%.2f / %.2f / %.2f)\n",this.velX, this.velY, this.velZ);
		//System.out.printf("Motion=(%.2f / %.2f / %.2f)\n",this.motionX, this.motionY, this.motionZ);
		this.setPosition(this.posX+this.motionX, this.posY+this.motionY, this.posZ+this.motionZ);
		
		
		this.velX *= velocityDamping;
		this.velY *= velocityDamping;
		this.velZ *= velocityDamping;

		if (this.onGround) {
			this.velX *= velocityDampingOnGround;
			this.velY *= velocityDampingOnGround; // ?
			this.velZ *= velocityDampingOnGround;
			if (type.removeOnGround)
				this.setDead();
		}

		/* ------------
		 * SIZE
		 */
		size += sizeRate;
		sizeRate *= sizeRateDamping;

	}
	
	@Override
    public void renderParticle(Tessellator tesselator, float partialTickTime, float rotX, float rotXZ, float rotZ, float rotYZ, float rotXY)
    {
		float progress = ((float)this.particleAge+partialTickTime) / (float)this.particleMaxAge;
	
		/* ------------------------
		 * INTERPOLATE COLOR VALUES
		 */
		ColorEntry c1 = null;
		ColorEntry c2 = null;
		ColorEntry prevC = null;
		for (ColorEntry c : this.type.colorEntries) {
			if (progress < c.time) {
				c1 = (prevC != null) ? prevC : c;
				c2 = c;
				break;
			}
			prevC = c;
		}
		if (c1 == null) c1 = this.type.colorEntries.get(this.type.colorEntries.size()-1);
		if (c2 == null) c2 = c1;
		float p = (progress-c1.time) / (c2.time-c1.time);		
		//RGB to HSB
		float[] hsb1 = Color.RGBtoHSB((int)(c1.r*255), (int)(c1.g*255), (int)(c1.b*255), null);
		float[] hsb2 = Color.RGBtoHSB((int)(c2.r*255), (int)(c2.g*255), (int)(c2.b*255), null);	
		//HSB to RGB;
		Color color = new Color(Color.HSBtoRGB(hsb1[0]*p + hsb2[0]*(1f-p), hsb1[1]*p + hsb2[1]*(1f-p), hsb1[2]*p + hsb2[2]*(1f-p)));
		this.particleRed = (float)color.getRed() / 255.0f;
		this.particleGreen = (float)color.getGreen() / 255.0f;
		this.particleBlue = (float)color.getBlue() / 255.0f;
		
		/*-------------------------
		 * INTERPOLATE ALPHA VALUES
		 */
		AlphaEntry a1 = null;
		AlphaEntry a2 = null;
		AlphaEntry prevA = null;
		for (AlphaEntry a : this.type.alphaEntries) {
			if (progress < a.time) {
				a1 = (prevA != null) ? prevA : a;
				a2 = a;
				break;
			}
			prevA = a;
		}
		if (a1 == null) a1 = this.type.alphaEntries.get(this.type.alphaEntries.size()-1);
		if (a2 == null) a2 = a1;
		p = (progress-a1.time) / (a2.time-a1.time);		
		//interpolate
		this.particleAlpha = a1.alpha*p + a2.alpha * (1f-p);
		
		/*-------------------
		 * ANIMATION
		 */	
		int currentFrame = 0;
        if (type.hasVariations) {
        	currentFrame = variationFrame;
        }else {
        	currentFrame = ((int)((float)type.frames*(progress * this.animationSpeed))) % type.frames;
        }

        /* -------------
         * RENDER PARTICLE
         */
        this.particleScale = sizePrev + (size-sizePrev)*partialTickTime;
        tesselator.draw();
    	
        enableBlendMode();
        
        Minecraft.getMinecraft().renderEngine.bindTexture(type.texture);
        
        tesselator.startDrawingQuads();
        
        float f6 = ((float)this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
        float f7 = f6 + 0.015609375F;
        float f8 = ((float)this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
        float f9 = f8 + 0.015609375F;
        float fscale = 0.1F * this.particleScale;

        float fPosX = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTickTime - interpPosX);
        float fPosY = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTickTime - interpPosY);
        float fPosZ = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTickTime - interpPosZ);
    
        float r = fscale;
        
		int col = currentFrame % type.columns;
		int row = (currentFrame / type.columns);
		
		float u = 1.f/type.columns;
		float v = 1.f/type.columns;
		
		float U1 = col*u;
		float V1 = row*v;
		float U2 = (col+1)*u;
		float V2 = (row+1)*v;
		
		float ua, va, ub, vb, uc, vc, ud, vd;
		
		switch (angle) {
			case 1:
				 ua=U1; va = V2; ub=U2; vb=V2; uc = U2; vc= V1; ud = U1; vd = V1;
				 break;
			case 2:
				 ua = U1; va = V1; ub=U1; vb = V2; uc=U2; vc=V2; ud = U2; vd= V1;
				 break;
			case 3:
				 ua = U2; va= V1; ub = U1; vb = V1; uc=U1; vc = V2; ud=U2; vd=V2;
				 break;
			case 0:					
			default:
				ua=U2; va=V2; ub = U2; vb= V1; uc = U1; vc = V1; ud=U1; vd = V2;
				break;
		}
		
      			
		tesselator.setNormal(0.0F, 1.0F, 0.0F);
		tesselator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
		
        tesselator.addVertexWithUV((double)(fPosX - rotX * fscale - rotYZ * fscale), (double)(fPosY - rotXZ * fscale), (double)(fPosZ - rotZ * fscale - rotXY * fscale), (double)ua, (double)va);
        tesselator.addVertexWithUV((double)(fPosX - rotX * fscale + rotYZ * fscale), (double)(fPosY + rotXZ * fscale), (double)(fPosZ - rotZ * fscale + rotXY * fscale), (double)ub, (double)vb);
        tesselator.addVertexWithUV((double)(fPosX + rotX * fscale + rotYZ * fscale), (double)(fPosY + rotXZ * fscale), (double)(fPosZ + rotZ * fscale + rotXY * fscale), (double)uc, (double)vc);
        tesselator.addVertexWithUV((double)(fPosX + rotX * fscale - rotYZ * fscale), (double)(fPosY - rotXZ * fscale), (double)(fPosZ + rotZ * fscale - rotXY * fscale), (double)ud, (double)vd);
	
        tesselator.draw();

        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("textures/particle/particles.png"));
        
        disableBlendMode();
        
        tesselator.startDrawingQuads();
        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture(new ResourceLocation()).getGlTextureId());
        
        
    }
	
	private void enableBlendMode() {
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
    	if (type.renderType != RenderType.SOLID) {
    		GL11.glEnable(GL11.GL_BLEND);
    		//GL11.glEnable(GL11.GL_ALPHA_TEST);
    	}
        if (type.renderType == RenderType.ALPHA) {
        	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        } else if (type.renderType == RenderType.ADDITIVE || type.renderType==RenderType.NO_Z_TEST) {
        	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        }
        
        if (type.renderType==RenderType.NO_Z_TEST){
        	GL11.glDepthMask(false);
        	GL11.glDisable(GL11.GL_DEPTH_TEST);
        }
        
        if (type.renderType != RenderType.ALPHA_SHADED) TGRenderHelper.enableFXLighting();
	}
	
	private void disableBlendMode() {
		if (type.renderType != RenderType.ALPHA_SHADED) TGRenderHelper.disableFXLighting();
		if (type.renderType != RenderType.SOLID) {
    		GL11.glDisable(GL11.GL_BLEND);
    		//GL11.glDisable(GL11.GL_ALPHA_TEST);
    	}
        if (type.renderType==RenderType.NO_Z_TEST){
        	GL11.glDepthMask(true);
        	GL11.glEnable(GL11.GL_DEPTH_TEST);
        }
		
		GL11.glPopAttrib();
	}

	@Override
	public int getFXLayer()
    {
        return 2;
    }
		
	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass==1;
	}

}
