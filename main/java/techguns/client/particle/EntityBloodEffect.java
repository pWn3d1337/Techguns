package techguns.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;
import techguns.client.ClientProxy;
import techguns.client.GoreData;
import techguns.client.particle.EntityParticleAnimated.DynamicColorType;
import techguns.client.renderer.TGRenderHelper.RenderType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityBloodEffect extends EntityFX{
	
	public float height = 1.0f;
	public float width = 1.0f;
	public GoreData data;

	public EntityBloodEffect(World world, double x,
			double y, double z, double motionX, double motionY, double motionZ, float width, float height, GoreData data) {
		this(world,x,y,z,motionX,motionY,motionZ, data);
		this.width = width;
		this.height = height;
	}
	
	public EntityBloodEffect(World world, double x,
			double y, double z, double motionX, double motionY, double motionZ, GoreData data) {
		super(world, x, y, z, motionX, motionY, motionZ);
		this.particleMaxAge = 5;
		this.particleGravity = 0.08f;
		this.data = data;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		int count = 1+(int)(((width+height)/2.0f)*2.0f);
		if (data.showBlood) {
			for (int i = 0; i < count; i++) {
				double d0 = rand.nextDouble();
				double d1 = rand.nextDouble();
				double d2 = rand.nextDouble();
				double v = 0.15;
				double vx = (d0-0.5)*v+this.motionX*0.5;
				double vy = (d1-0.5)*v+this.motionY*0.1;
				double vz = (d2-0.5)*v+this.motionZ*0.5;
				//Techguns.proxy.spawnParticle("Blood", this.worldObj, this.posX+(d0-0.5)*width, this.posY+(d1-0.5)*height, this.posZ+(d2-0.5)*width, vx, vy, vz);
				EntityParticleAnimated fx;
				fx = new EntityParticleAnimated(this.worldObj, this.posX+(d0-0.5)*width, this.posY+(d1-0.5)*height, this.posZ+(d2-0.5)*width, 5f, "blood4x4.png", 2, 2, 4, RenderType.ALPHA); 
				fx.motionX = vx;
	            fx.motionY = vy;
	            fx.motionZ = vz;
	            fx.setGravity(0.07f);
	            fx.setMaxAge(20);
	            fx.setScaleIncrease(5f, 10f, 1f, true);
	            fx.setDynamicColor(data.bloodColorR, data.bloodColorG, data.bloodColorB, 255, data.bloodColorR, data.bloodColorG, data.bloodColorB, 0, DynamicColorType.LINEAR);
		        fx.setDynamicColorStart(0.5f);
	            fx.setRandomRotation(true);
	            fx.setRandomness(0.50f);
	            fx.setMotionDamping(0.98D);
	            fx.setFramesAsVariations(true);
	            ClientProxy.spawnParticle(fx);
			}
		}else {
			double d0 = rand.nextDouble();
			double d1 = rand.nextDouble();
			double d2 = rand.nextDouble();
			double v = 0.10;
			double vx = (d0-0.5)*v+this.motionX*0.3;
			double vy = (d1-0.5)*v+this.motionY*0.1;
			double vz = (d2-0.5)*v+this.motionZ*0.3;
			EntityParticleAnimated fx = new EntityParticleAnimated(this.worldObj, this.posX+(d0-0.5)*width, this.posY+(d1-0.5)*height, this.posZ+(d2-0.5)*width, 5f, "dustclouds02.png", 3, 3, 9, RenderType.ALPHA_SHADED);
			fx.motionX = vx;
            fx.motionY = vy;
            fx.motionZ = vz;
            fx.setGravity(0.05f);
            fx.setMaxAge(40);
            fx.setScaleIncrease(5f, 10f, 1f, true);
            fx.setDynamicColor(data.bloodColorR, data.bloodColorG, data.bloodColorB, 255, data.bloodColorR, data.bloodColorG, data.bloodColorB, 0, DynamicColorType.LINEAR);
	        fx.setDynamicColorStart(0.5f);
            fx.setRandomRotation(true);
            fx.setRandomness(0.50f);
            fx.setMotionDamping(0.98D);

			ClientProxy.spawnParticle(fx);
		}
	}

	
	@Override
	public void renderParticle(Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {}

}
