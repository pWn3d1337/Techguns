package techguns.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import techguns.client.renderer.TGRenderHelper;
import techguns.client.renderer.TGRenderHelper.RenderType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Deprecated
public class EntityParticleAnimated extends EntityFX {
	
	ResourceLocation fxTexture;
	int cols;
	int rows;
	int numSprites;
	float scale;
	RenderType renderType;
	private float maxScale;
	private float minScale;
	private float scaleFactor;
	private boolean softScaling;
	
	public boolean variations=false; //use frames as variations;
	private int variationFrame = 0;
	
	private double motionDamping = 0.9800000190734863D;
	
	private float randomness = 0.20f;
	private int random_rot = 0;
	
	private float rot_offset;
	
	private int delay = 0;
	
	//Color
	private float R1;
	private float G1;
	private float B1;
	private float A1;
	private float R2;
	private float G2;
	private float B2;
	private float A2;
	private DynamicColorType dynamicColorType = DynamicColorType.NONE;
	private float dynamicColorStart;
	
	@Deprecated
	protected EntityParticleAnimated(World p_i1218_1_, double p_i1218_2_,
			double p_i1218_4_, double p_i1218_6_) {
		super(p_i1218_1_, p_i1218_2_, p_i1218_4_, p_i1218_6_);
		this.noClip=true;
		this.particleGravity = 0.0f;
	}

	@Deprecated
	public EntityParticleAnimated(World p_i1219_1_, double p_i1219_2_,
			double p_i1219_4_, double p_i1219_6_, double p_i1219_8_,
			double p_i1219_10_, double p_i1219_12_) {
		super(p_i1219_1_, p_i1219_2_, p_i1219_4_, p_i1219_6_, p_i1219_8_, p_i1219_10_,
				p_i1219_12_);
		this.noClip=true;
		this.particleGravity = 0.0f;
	}

	@Deprecated
	public EntityParticleAnimated(World world, double posX, double posY, double posZ, float scale, String fxTexture, int cols, int rows, int numSprites, RenderType renderType) {
		super(world, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
		this.noClip=true;
		this.particleGravity = 0.0f;
		this.fxTexture = new ResourceLocation("techguns","textures/fx/"+fxTexture);
		this.cols = cols;
		this.rows = rows;
		this.numSprites = numSprites;
		this.particleScale = scale;
		this.renderType = renderType;
		this.particleRed = 1f;
		this.particleGreen = 1f;
		this.particleBlue = 1f;
		this.particleMaxAge = 10+this.rand.nextInt(5);
	}

	public int getFXLayer()
    {
        return 2;
    }
	
	
	
	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass==1;
	}


	@Override
	public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        //Wait until delay is up.
        if (delay <= 0) {
	
	        if (this.particleAge++ >= this.particleMaxAge)
	        {
	            this.setDead();
	        }
	
	        this.motionY -= 0.04D * (double)this.particleGravity;
	        this.moveEntity(this.motionX, this.motionY, this.motionZ);
	        this.motionX *= motionDamping;
	        this.motionY *= motionDamping;
	        this.motionZ *= motionDamping;
	
	        if (this.onGround)
	        {
	            this.motionX *= 0.699999988079071D;
	            this.motionZ *= 0.699999988079071D;
	        }
	        
	        //COLOR
	        if (dynamicColorType != DynamicColorType.NONE) {
	        	float prog = ((float)this.particleAge/(float)this.particleMaxAge);
	        	if (prog >= dynamicColorStart) {
	        		prog = (prog-dynamicColorStart) * (1.0f/(1f-dynamicColorStart));
	        	}else {
	        		prog = 0.0f;
	        	}
		        switch(dynamicColorType) {
		        case LINEAR:
		        default:
		        	break;
		        case TENT:
		        	prog*=2f;
		        	if (prog > 1f) prog = 2f-prog;
		        	break;
		        case SINUS:
		        	prog = (float) Math.sin(Math.PI*2*prog);
		        	break;
		        case SMOOTH:
		        	prog = (float) Math.sin(Math.PI*prog);
		        	break;
		        case FAST:
		        	prog = (float) Math.sqrt(prog);
		        	break;
		        
		        }
		        this.particleRed = this.R1 * (1-prog) + this.R2*prog;
		        this.particleGreen = this.G1 * (1-prog) + this.G2*prog;
		        this.particleBlue = this.B1 * (1-prog) + this.B2*prog;
		        this.particleAlpha = this.A1 * (1-prog) + this.A2*prog;
	        	
		    }
	        
        }else {
        	delay--;
        }
    }
	

	@Override
    public void renderParticle(Tessellator tesselator, float partialTickTime, float rotX, float rotXZ, float rotZ, float rotYZ, float rotXY)
    {
		//Do not render
		if (delay > 0) {
			return;
		}
		
		float prog = ((float)this.particleAge/(float)this.particleMaxAge);
        int currentFrame = 0;
        if (variations) {
        	currentFrame = variationFrame;
        }else {
        	currentFrame = (int)((float)numSprites*prog);
        }
        if (currentFrame < numSprites) {
		
        	
        	//Scaling
        	if (this.maxScale != 0) {
        		float p = (float) (softScaling ? Math.sqrt(prog) : prog);
        		
        		this.particleScale = Math.min(minScale+((maxScale-minScale)*p*(1f/scaleFactor)),maxScale);
        	}
        	
	    	tesselator.draw();
	    	GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
	    	if (renderType != RenderType.SOLID) {
	    		GL11.glEnable(GL11.GL_BLEND);
	    		GL11.glEnable(GL11.GL_ALPHA_TEST);
	    	}
	        if (renderType == RenderType.ALPHA) {
	        	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        } else if (renderType == RenderType.ADDITIVE) {
	        	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	        }
	        if (renderType != RenderType.ALPHA_SHADED) TGRenderHelper.enableFXLighting();
	        
	        Minecraft.getMinecraft().renderEngine.bindTexture(this.fxTexture);
	        
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
	        
			int col = currentFrame % cols;
			int row = (currentFrame / cols);
			
			float u = 1.f/cols;
			float v = 1.f/rows;
			
			float U1 = col*u;
			float V1 = row*v;
			float U2 = (col+1)*u;
			float V2 = (row+1)*v;
			
			float ua, va, ub, vb, uc, vc, ud, vd;
			
			switch (random_rot) {
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

//			if (this.particleAge == 10 && this.A1 != 0) {
//				System.out.println("stop! debugtime: RGBA: "+this.particleRed+", "+this.particleGreen+", "+this.particleBlue+", "+this.particleAlpha);
//			}
			
	        tesselator.addVertexWithUV((double)(fPosX - rotX * fscale - rotYZ * fscale), (double)(fPosY - rotXZ * fscale), (double)(fPosZ - rotZ * fscale - rotXY * fscale), (double)ua, (double)va);
	        tesselator.addVertexWithUV((double)(fPosX - rotX * fscale + rotYZ * fscale), (double)(fPosY + rotXZ * fscale), (double)(fPosZ - rotZ * fscale + rotXY * fscale), (double)ub, (double)vb);
	        tesselator.addVertexWithUV((double)(fPosX + rotX * fscale + rotYZ * fscale), (double)(fPosY + rotXZ * fscale), (double)(fPosZ + rotZ * fscale + rotXY * fscale), (double)uc, (double)vc);
	        tesselator.addVertexWithUV((double)(fPosX + rotX * fscale - rotYZ * fscale), (double)(fPosY - rotXZ * fscale), (double)(fPosZ + rotZ * fscale - rotXY * fscale), (double)ud, (double)vd);
    	
	        tesselator.draw();

	        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("textures/particle/particles.png"));
	        GL11.glPopAttrib();
	        tesselator.startDrawingQuads();
	        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture(new ResourceLocation()).getGlTextureId());
	        if (renderType != RenderType.ALPHA_SHADED) TGRenderHelper.disableFXLighting();
        }
    }
	
	
	
	public void setMaxAge(int maxAge) {
		this.particleMaxAge = Math.round(getRandom(maxAge));
	}
	
	public void setGravity(float gravity) {
		this.particleGravity = gravity;
	}


	/**
	 * 
	 * @param minScale initial particle size
	 * @param maxScale end particle size
	 * @param scaleFactor progress (0 to 1) when maxScale is reached
	 * @param softScaling smoother curve
	 */
	public void setScaleIncrease(float minScale, float maxScale, float scaleFactor, boolean softScaling) {
		this.minScale = getRandom(minScale);
		this.particleScale = minScale;
		this.maxScale = getRandom(maxScale);
		this.scaleFactor = scaleFactor;
		this.softScaling = softScaling;
	}
	
	/**
	 * RGBA1: Start Color value
	 * RGBA2: End Color value
	 * @param type interpolation type
	 */
	public void setDynamicColor(float R1, float G1, float B1, float A1, float R2, float G2, float B2, float A2, DynamicColorType type) {
		this.R1 = R1/255.f;
		this.G1 = G1/255.f;
		this.B1 = B1/255.f;
		this.A1 = A1/255.f;
		this.R2 = R2/255.f;
		this.G2 = G2/255.f;
		this.B2 = B2/255.f;
		this.A2 = A2/255.f;
		this.dynamicColorType = type;
	}
	
	/**
	 * Particles will be spawned with a random rotation between rot_min and rot_max (in degrees);
	 */
	@Deprecated
	public void setRandomRotation(float rot_min, float rot_max) {
		this.rot_offset = (rot_min+(rand.nextFloat()*(rot_max-rot_min)))*(float)Math.PI/180f;
		
	}
	
	public void setRandomRotation(boolean randomRotation) {
		if (!randomRotation){ random_rot = 0;}
		else {
			random_rot = this.rand.nextInt(4);
		}
	}
	
	public void setRandomness(float randomness) {
		this.randomness = randomness;
	}
	
	public void setMotionDamping (double damping) {
		this.motionDamping = damping;
	}
	
	/**
	 * @param delay in ticks
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	private float getRandom(float value) {
		return value*(1f-randomness) + (2f*value*randomness*this.rand.nextFloat());
	}
	
	public enum DynamicColorType {
		LINEAR, TENT, SINUS, SMOOTH, FAST, NONE; 
	}

	public void setDynamicColorStart(float f) {
		this.dynamicColorStart = f;
	}
	
	public void setFramesAsVariations(boolean b) {
		variations = b;
		if (b) variationFrame = this.rand.nextInt(this.numSprites);
	}
	
}
