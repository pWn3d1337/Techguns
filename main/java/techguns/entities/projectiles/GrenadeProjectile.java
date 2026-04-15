package techguns.entities.projectiles;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.damagesystem.DamageSystem;
import techguns.packets.PacketSpawnParticle;
import techguns.util.ProjectileExplosion;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class GrenadeProjectile extends GenericProjectile {
	
	protected float radius;
	protected float gravity;
	protected boolean blockdamage;
	protected int bounces;
	
	public GrenadeProjectile(World par1World) {
		super(par1World);
		this.gravity = 0.01f;
	}

	public GrenadeProjectile(World par2World, EntityLivingBase par3Player,
			float damage, float radius, float speed, int TTL, float spread, float gravity, boolean blockdamage, float penetration,int maxBounces) {
		super(par2World, par3Player, damage, speed, TTL, spread, 0,
				0, 0, penetration, blockdamage);
		
		this.radius = radius;
		this.gravity = gravity;
		this.blockdamage = blockdamage;
		this.bounces = maxBounces;
	}
	
	public GrenadeProjectile(World par2World, EntityLivingBase par3Player,
			float damage, float radius, float speed, int TTL, float spread, float gravity, boolean blockdamage, float penetration,int maxBounces, double x, double y, double z) {
		super(par2World, par3Player, damage, speed, TTL, spread, 0,
				0, 0, penetration, blockdamage);
		this.setPosition(x, y, z);
		
		this.radius = radius;
		this.gravity = gravity;
		this.blockdamage = blockdamage;
		this.bounces = maxBounces;
	}

	public GrenadeProjectile(World par2World, double posx, double posy, double posz,
			float damage, float radius, float speed, int TTL, float spread, float gravity, boolean blockdamage, float penetration,int maxBounces, double x, double y, double z) {
		super(par2World, posx,posy,posz, damage, speed, TTL, spread, 0,
				0, 0, penetration, blockdamage);
		this.setPosition(x, y, z);
		
		this.radius = radius;
		this.gravity = gravity;
		this.blockdamage = blockdamage;
		this.bounces = maxBounces;
	}
	
	protected String getExplosionEffect(){
		return "RocketExplosion";
	}

	protected void explode(Entity enthit){
		ProjectileExplosion explosion = new ProjectileExplosion(worldObj, this.posX, this.posY, this.posZ, this.shooter, radius, (int)damage, radius*0.5f, radius*1.5f);
		explosion.setExplosionSound("techguns:effects.explosion1");
		if (enthit != null){
			
			
			float dmg = 1.0f;
			if (enthit instanceof EntityLivingBase){
				dmg = DamageSystem.getDamageFactor(this.shooter, (EntityLivingBase)enthit);
			}
			if (dmg>0){
				enthit.attackEntityFrom(DamageSource.setExplosionSource(explosion.explosion), dmg*damage);
				if (enthit instanceof EntityLiving){
					this.setAIRevengeTarget((EntityLiving) enthit);
				}
			}
		}
		explosion.doExplosion(blockdamage, this.shooter);
		
		if (!this.worldObj.isRemote){
			TGPackets.network.sendToAllAround(new PacketSpawnParticle(this.getExplosionEffect(), this.posX,this.posY,this.posZ), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 50.0f));
		}
	}
	
	@Override
	protected void onImpact(MovingObjectPosition mop) {
		
		//this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, radius, blockdamage);
		
//		Explosion explosion = new Explosion(this.worldObj, this, this.posX, this.posY, this.posZ, radius);
//	    explosion.isFlaming = false;
//	    explosion.isSmoking = true;
//	    if (blockdamage) explosion.doExplosionA();
//	    explosion.doExplosionB(true);
		
		if (mop.typeOfHit == MovingObjectType.BLOCK){
    		if (this.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ).getBlocksMovement(this.worldObj, mop.blockX, mop.blockY, mop.blockZ)) {
    			return;
    		}
		}
		
		
		
		if (mop.entityHit != null || bounces <=0)
        {
			
			
            explode(mop.entityHit);
    		
            this.setDead();
        } else {
        	// Bounce
        	bounces--;
        	int sideHit = (mop.sideHit);
        	if (sideHit != -1) {
        		
        		Vec3 normal = Vec3.createVectorHelper(0, 1.0f,0);;
        		switch (sideHit) {
	        		case 0:
	        			//bottom
	        			normal = Vec3.createVectorHelper(0, -1.0f,0);
	        			break;
	        		case 1:
	        			//top
	        			normal = Vec3.createVectorHelper(0, 1.0f, 0f);
	        			break;
	        		case 2:
	        			//East
	        			normal = Vec3.createVectorHelper(0f, 0f, -1.0f);
	        			break;
	        		case 3:
	        			//west
	        			normal = Vec3.createVectorHelper(0.0f, 0f, 1.0f);
	        			break;
	        		case 4:
	        			//north
	        			normal = Vec3.createVectorHelper(-1.0f, 0.0f, 0.0f);
	        			break;
	        		case 5:
	        			//south
	        			normal = Vec3.createVectorHelper(1.0f, 0.0f, 0.0f);
	        			break;
        		}
        		
	        	
	        	Vec3 motion = Vec3.createVectorHelper(this.motionX, this.motionY, this.motionZ);
	        	//System.out.println("Motion:"+motion.xCoord+","+motion.yCoord+","+motion.zCoord);
	        	float len = this.getBounceSpeedFactor();//(float) motion.lengthVector()*0.4f;
	        	//motion.normalize();
	        	
	        	//System.out.println("Normal:"+normal.xCoord+","+normal.yCoord+","+normal.zCoord);
	        	
	        	double factor = (2 * normal.dotProduct(motion));
	        	Vec3 n = Vec3.createVectorHelper(normal.xCoord*factor, normal.yCoord*factor,normal.zCoord*factor);
	        	Vec3 refl = n.subtract(motion);
	        	//refl.normalize();
	        	
	        	//System.out.println("Refl"+ refl.xCoord+","+ refl.yCoord+","+ refl.zCoord);
	        	
	        	/*this.motionX = refl.xCoord * len;
	        	this.motionY = refl.yCoord * len;
	        	this.motionZ = refl.zCoord * len;*/
	        	//System.out.println("BOUNCE");
	        	
	        	this.playBounceSound();
	        	
	        	if (!this.worldObj.isRemote){
	        		GrenadeProjectile gren = createBounceProjectile();	
	        		//new GrenadeProjectile(this.worldObj, this.shooter, this.damage,this.radius,this.speed,this.ticksToLive,0.0f,this.gravity,false,this.penetration,this.bounces,this.posX,this.posY,this.posZ);

	        		gren.motionX = refl.xCoord * len;
	        		gren.motionY = refl.yCoord * len;
	        		gren.motionZ = refl.zCoord * len;
	        		
	        		this.worldObj.spawnEntityInWorld(gren);
	        		
	        		createProjectileTrail(gren);
	        		
	        		//gren.setPosition(this.posX, this.posY, this.posZ);

	        	}
	        	this.setDead();
        	}
        }
		

	}
	
	/**
	 * How much the grenade slows down on bouncing
	 * @return
	 */
	protected float getBounceSpeedFactor(){
		return 0.5f;
	}
	
	protected void playBounceSound(){
    	this.worldObj.playSoundAtEntity(this, "mob.irongolem.hit", 2.0F, 1.0F );
	}
	
	protected void createProjectileTrail(GrenadeProjectile gren) {

	}

	protected GrenadeProjectile createBounceProjectile() {
		if (shooter !=null){
			return new GrenadeProjectile(this.worldObj, this.shooter, this.damage,this.radius,this.speed,this.ticksToLive,0.0f,this.gravity,false,this.penetration,this.bounces,this.posX,this.posY,this.posZ);
		} else {
			return new GrenadeProjectile(this.worldObj, this.posX, this.posY, this.posZ, this.damage,this.radius,this.speed,this.ticksToLive,0.0f,this.gravity,false,this.penetration,this.bounces,this.posX,this.posY,this.posZ);
		}
	}
	
	@Override
	protected float getGravityVelocity() 
    {
    	return gravity;
    }
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if (this.ticksToLive<=1){
			explode(null);
			this.setDead();
		}
		
		if (this.isInWater())
        {
            for (int l = 0; l < 2; ++l)
            {
                float f4 = 0.5F;
               // this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ);
            }
            this.setDead();
        } else {
        	float f5 = 0.1f;
            //this.worldObj.spawnParticle("flame", this.posX, this.posY, this.posZ, this.motionX*f5, (this.motionY*f5)-this.getGravityVelocity(), this.motionZ*f5);
        	//this.worldObj.spawnParticle("explode", this.posX, this.posY, this.posZ, this.motionX*f5, (this.motionY*f5)-this.getGravityVelocity(), this.motionZ*f5);
        }
	}

}
