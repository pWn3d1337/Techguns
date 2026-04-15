package techguns.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityParticleTrail extends EntityFX {
	
	protected EntityParticleTrail(World p_i1218_1_, double p_i1218_2_,
			double p_i1218_4_, double p_i1218_6_) {
		super(p_i1218_1_, p_i1218_2_, p_i1218_4_, p_i1218_6_);
		this.noClip=true;
		this.particleGravity = 0.0f;
	}

	
	public EntityParticleTrail(World p_i1219_1_, double p_i1219_2_,
			double p_i1219_4_, double p_i1219_6_, double p_i1219_8_,
			double p_i1219_10_, double p_i1219_12_) {
		super(p_i1219_1_, p_i1219_2_, p_i1219_4_, p_i1219_6_, p_i1219_8_, p_i1219_10_,
				p_i1219_12_);
		this.noClip=true;
		this.particleGravity = 0.0f;
	}

	public EntityParticleTrail(World world, double posX, double posY,
			double posZ, Item item, float r, float g, float b, float scale) {
		super(world, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
		this.setParticleIcon(item.getIconFromDamage(0));
        this.particleRed = r;
        this.particleGreen = g;
        this.particleBlue = b;
        this.particleGravity = 0.0f;
        this.particleScale *= scale;
        this.noClip=true;
	}


	public int getFXLayer()
    {
        return 2;
    }

	@Override
    public void renderParticle(Tessellator tesselator, float partialTickTime, float rotX, float rotXZ, float rotZ, float rotYZ, float rotXY)
    {
    	tesselator.draw();
    	GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("techguns","textures/fx/glow.png"));
        
        tesselator.startDrawingQuads();
        
        float f6 = ((float)this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
        float f7 = f6 + 0.015609375F;
        float f8 = ((float)this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
        float f9 = f8 + 0.015609375F;
        float f10 = 0.1F * this.particleScale;

        /*if (this.particleIcon != null)
        {
            f6 = this.particleIcon.getInterpolatedU((double)(this.particleTextureJitterX / 4.0F * 16.0F));
            f7 = this.particleIcon.getInterpolatedU((double)((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F));
            f8 = this.particleIcon.getInterpolatedV((double)(this.particleTextureJitterY / 4.0F * 16.0F));
            f9 = this.particleIcon.getInterpolatedV((double)((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F));
        }
*/	
        /*System.out.println("PrevPos:"+this.prevPosX+", "+this.prevPosY+", "+this.prevPosZ);
        System.out.println("Diffs::"+(this.posX-this.prevPosX)+", "+(this.posY-this.prevPosY)+", "+(this.posZ-this.prevPosZ));
        System.out.println("PartialTick"+partialTickTime+", Interpos:"+interpPosX+", "+interpPosY+", "+interpPosZ);*/
        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTickTime - interpPosX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTickTime - interpPosY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTickTime - interpPosZ);
        
        //System.out.println("F11:"+f11+"F12:"+f12+"F13:"+f13);
        
        /*p_70539_1_.setColorOpaque_F(this.particleRed, this.particleGreen, this.particleBlue);
        p_70539_1_.addVertexWithUV((double)(f11 - p_70539_3_ * f10 - p_70539_6_ * f10), (double)(f12 - p_70539_4_ * f10), (double)(f13 - p_70539_5_ * f10 - p_70539_7_ * f10), (double)f6, (double)f9);
        p_70539_1_.addVertexWithUV((double)(f11 - p_70539_3_ * f10 + p_70539_6_ * f10), (double)(f12 + p_70539_4_ * f10), (double)(f13 - p_70539_5_ * f10 + p_70539_7_ * f10), (double)f6, (double)f8);
        p_70539_1_.addVertexWithUV((double)(f11 + p_70539_3_ * f10 + p_70539_6_ * f10), (double)(f12 + p_70539_4_ * f10), (double)(f13 + p_70539_5_ * f10 + p_70539_7_ * f10), (double)f7, (double)f8);
        p_70539_1_.addVertexWithUV((double)(f11 + p_70539_3_ * f10 - p_70539_6_ * f10), (double)(f12 - p_70539_4_ * f10), (double)(f13 + p_70539_5_ * f10 - p_70539_7_ * f10), (double)f7, (double)f9);
    	*/
        tesselator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
       // System.out.println("Coords:"+(f11 - rotX * f10 - rotYZ * f10)+", "+(f12 - rotXZ * f10)+", "+(f13 - rotZ * f10 - rotXY * f10));
        tesselator.addVertexWithUV((double)(f11 - rotX * f10 - rotYZ * f10), (double)(f12 - rotXZ * f10), (double)(f13 - rotZ * f10 - rotXY * f10), (double)1, (double)1);
        tesselator.addVertexWithUV((double)(f11 - rotX * f10 + rotYZ * f10), (double)(f12 + rotXZ * f10), (double)(f13 - rotZ * f10 + rotXY * f10), (double)1, (double)0);
        tesselator.addVertexWithUV((double)(f11 + rotX * f10 + rotYZ * f10), (double)(f12 + rotXZ * f10), (double)(f13 + rotZ * f10 + rotXY * f10), (double)0, (double)0);
        tesselator.addVertexWithUV((double)(f11 + rotX * f10 - rotYZ * f10), (double)(f12 - rotXZ * f10), (double)(f13 + rotZ * f10 - rotXY * f10), (double)0, (double)1);
    	
        tesselator.draw();
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("textures/particle/particles.png"));
        GL11.glPopAttrib();
        tesselator.startDrawingQuads();
        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture(new ResourceLocation()).getGlTextureId());
    }
}
