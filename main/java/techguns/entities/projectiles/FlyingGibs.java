package techguns.entities.projectiles;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import techguns.client.ClientProxy;
import techguns.client.GoreData;
import techguns.client.particle.EntityParticleAnimated;
import techguns.client.particle.EntityParticleAnimated.DynamicColorType;
import techguns.client.renderer.TGRenderHelper.RenderType;


public class FlyingGibs extends Entity{

	public int maxTimeToLive = 200;
	public int timeToLive = 200;

	public double gravity = 0.029999999329447746D;
	public float size;
	public Vec3 rotationAxis;
	public int bodypart;
	public int hitGroundTTL = 0;
	
	//the exploding entity
	public EntityLivingBase entity;
	
	public GoreData data;
	
	//public EntityDT entityDT;
	
	public FlyingGibs(World world, EntityLivingBase entity, GoreData data, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float size, int bodypart) {
		super(world);
		this.setPosition(posX, posY, posZ);
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
		this.size = size;
		this.maxTimeToLive = 150 + this.rand.nextInt(101);
		this.timeToLive = maxTimeToLive;
		this.entity = entity;
		this.bodypart = bodypart;
		//this.entityDT = entityDT;
		this.rotationAxis = Vec3.createVectorHelper(rand.nextDouble(), rand.nextDouble(), rand.nextDouble());
		this.data = data;
	}
	
	public FlyingGibs(World world) {
		super(world);
	}

	@Override
	public void onUpdate()
    {
        super.onUpdate();

        if (this.timeToLive > 0)
        {
            --timeToLive;
        }else {
        	this.setDead();
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= gravity;

        if (this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.lava)
        {
            this.motionY = 0.20000000298023224D;
            this.motionX = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
            this.motionZ = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
            this.playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
        }

        //this.func_145771_j(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0D, this.posZ);

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        float f = 0.98F;

        if (this.onGround)
        {
        	if (hitGroundTTL == 0) hitGroundTTL = timeToLive;
            f = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.98F;
        }

        this.motionX *= (double)f;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= (double)f;

        if (this.onGround)
        {
            this.motionY *= -0.8999999761581421D;
        }else {     
	        //BLood trail
        	if (this.data.showBlood) {
		        EntityParticleAnimated fx = new EntityParticleAnimated(this.worldObj, posX, posY, posZ, 5f, "blood4x4.png", 2, 2, 4, RenderType.ALPHA); 
		        fx.motionX = this.motionX;
		        fx.motionY = this.motionY;
		        fx.motionZ = this.motionZ;
		        fx.setGravity(0.07f);
		        fx.setMaxAge(15);
		        fx.setScaleIncrease(this.size*2f, this.size*4.0f, 1f, true);
		        fx.setDynamicColor(data.bloodColorR, data.bloodColorG, data.bloodColorB, 255, data.bloodColorR, data.bloodColorG, data.bloodColorB, 0, DynamicColorType.LINEAR);
		        fx.setDynamicColorStart(0.5f);
		        fx.setRandomRotation(true);
		        fx.setRandomness(0.80f);
		        fx.setMotionDamping(0.92D);
		        fx.setFramesAsVariations(true);
		        ClientProxy.spawnParticle(fx);
        	}
        }

    }

    /**
     * Returns if this entity is in water and will end up adding the waters velocity to the entity
     */
    public boolean handleWaterMovement()
    {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
    }

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {}
}
