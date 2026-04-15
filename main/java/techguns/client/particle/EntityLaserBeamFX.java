package techguns.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class EntityLaserBeamFX extends EntityFX {

	protected double targetX;
	protected double targetY;
	protected double targetZ;
	
	public EntityLaserBeamFX(World world, double sourceX,
			double sourceY, double sourceZ, double targetX, double targetY, double targetZ) {
		super(world, sourceX, sourceY, sourceZ);
		this.targetX=targetX;
		this.targetY=targetY;
		this.targetZ=targetZ;
		this.noClip=true;
		this.particleGravity = 0.0f;
		this.motionX=0.0f;
		this.motionY=0.0f;
		this.motionZ=0.0f;
		this.ignoreFrustumCheck=true;
		System.out.println("I AM A NEW LASERBEAM HARRRRRRR!");
	}

	@Override
    public void renderParticle(Tessellator tesselator, float partialTickTime, float rotX, float rotXZ, float rotZ, float rotYZ, float rotXY)
    {
		
		//init
		tesselator.draw();
    	GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("techguns","textures/fx/laser2_a.png"));
        tesselator.startDrawingQuads();
        //
        GL11.glPushMatrix();
        
        Vec3 source = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        Vec3 target = Vec3.createVectorHelper(this.targetX, this.targetY, this.targetZ);
       
        Vec3 viewer = Vec3.createVectorHelper(Minecraft.getMinecraft().renderViewEntity.posX, Minecraft.getMinecraft().renderViewEntity.posY, Minecraft.getMinecraft().renderViewEntity.posZ);
        
        Vec3 VS = viewer.subtract(source).normalize();
        Vec3 VT = viewer.subtract(target).normalize();
        Vec3 v_normal = VS.crossProduct(VT);
        
        
        System.out.println("PrevPos:"+this.prevPosX+", "+this.prevPosY+", "+this.prevPosZ);
        System.out.println("Diffs::"+(this.posX-this.prevPosX)+", "+(this.posY-this.prevPosY)+", "+(this.posZ-this.prevPosZ));
        System.out.println("PartialTick"+partialTickTime+", Interpos:"+interpPosX+", "+interpPosY+", "+interpPosZ);
        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTickTime - interpPosX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTickTime - interpPosY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTickTime - interpPosZ);
        
        System.out.println("F11:"+f11+"F12:"+f12+"F13:"+f13);
        
        
        float factor = 1.0f;
        //tesselator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        tesselator.setColorRGBA_F(1.0f,1.0f,1.0f,1.0f);
        tesselator.addVertexWithUV(source.xCoord+v_normal.xCoord*factor, source.yCoord+v_normal.yCoord*factor, source.zCoord+v_normal.zCoord*factor, 0, 0);
        tesselator.addVertexWithUV(source.xCoord-v_normal.xCoord*factor, source.yCoord-v_normal.yCoord*factor, source.zCoord-v_normal.zCoord*factor, 1, 0);
        tesselator.addVertexWithUV(target.xCoord+v_normal.xCoord*factor, target.yCoord+v_normal.yCoord*factor, target.zCoord+v_normal.zCoord*factor, 0, 1);
        tesselator.addVertexWithUV(target.xCoord-v_normal.xCoord*factor, target.yCoord-v_normal.yCoord*factor, target.zCoord-v_normal.zCoord*factor, 1, 1);
        
       /* System.out.println("Viewer:"+viewer);
        System.out.println("Normal:"+v_normal);
        System.out.println("Render Laser From:"+source+" to:"+target);*/
        
        GL11.glPopMatrix();
        
        //reset
        tesselator.draw();
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("textures/particle/particles.png"));
        GL11.glPopAttrib();
        tesselator.startDrawingQuads();
        //
	}

	
	
	
}
