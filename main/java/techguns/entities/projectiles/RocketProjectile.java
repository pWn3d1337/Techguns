package techguns.entities.projectiles;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.damagesystem.TGDamageSource;
import techguns.packets.PacketSpawnParticle;
import techguns.util.EntityDeathUtils.DeathType;
import techguns.util.ProjectileExplosion;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class RocketProjectile extends GenericProjectile {

	protected float radius;
	protected float gravity;
	
	public RocketProjectile(World par1World) {
		super(par1World);
		this.gravity = 0.01f;
	}

	
	public RocketProjectile(World par2World, double posX, double posY, double posZ,
			float damage, float radius, float speed, int TTL, float spread, float gravity, boolean blockdamage, float penetration) {
		super(par2World, posX, posY, posZ, damage, speed, TTL, spread, 0,
				0, 0, penetration, blockdamage);
		
		this.radius = radius;
		this.gravity = gravity;
	}	
	
	public RocketProjectile(World par2World, double posX, double posY, double posZ, float yaw, float pitch,
			float damage, float radius, float speed, int TTL, float spread, float gravity, boolean blockdamage, float penetration) {
		super(par2World, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, 0,
				0, 0, penetration, blockdamage, false);
		
		this.radius = radius;
		this.gravity = gravity;
	}	
	
	public RocketProjectile(World par2World, EntityLivingBase par3Player,
			float damage, float radius, float speed, int TTL, float spread, float gravity, boolean blockdamage, float penetration) {
		super(par2World, par3Player, damage, speed, TTL, spread, 0,
				0, 0, penetration, blockdamage);
		
		this.radius = radius;
		this.gravity = gravity;
	}
	
	
	
	
	@Override
	protected void onImpact(MovingObjectPosition mop) {

		//Techguns.proxy.createFX("RocketExplosion", this.worldObj, this.posX, this.posY, this.posZ, 0, 0, 0);
		
		
		if (mop.entityHit != null)
        {
            //par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.setExplosionSource(explosion.explosion), damage);
			//TGDamageSource dmgsrc = TGDamageSource.causeExplosionDamage(this, this.shooter, DeathType.GORE);
			//dmgsrc.setExplosionSource(explosion.explosion);
			//dmgsrc.goreChance = 1.0f;
			//dmgsrc.knockbackMultiplier=3.0f;
			mop.entityHit.attackEntityFrom(this.getProjectileDamageSource(), damage);
			if (mop.entityHit instanceof EntityLiving){
				this.setAIRevengeTarget((EntityLiving) mop.entityHit);
			}
			
			this.explodeRocket();
			
        } else if (mop.typeOfHit == MovingObjectType.BLOCK) { {
        	
        	//hit block
        	
        		Block b = this.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
        		
        		if (!b.getBlocksMovement(this.worldObj, mop.blockX, mop.blockY, mop.blockZ)) {
        			this.explodeRocket();
        		}
        	}
        	
        }
		

	}

	protected void explodeRocket(){
		if (!this.worldObj.isRemote){
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("RocketExplosion", this.posX,this.posY,this.posZ), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 50.0f));

    		ProjectileExplosion explosion = new ProjectileExplosion(worldObj, this.posX, this.posY, this.posZ, this.shooter, radius, (int)damage, radius*0.5f, radius*1.5f);
			
			explosion.doExplosion(blockdamage, this.shooter);
		}
		this.setDead();
	}
	
	
	
	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource dmgsrc = TGDamageSource.causeExplosionDamage(this, this.shooter, DeathType.GORE);
		//dmgsrc.setExplosionSource(explosion.explosion);
		dmgsrc.goreChance = 1.0f;
		dmgsrc.knockbackMultiplier=3.0f;
		return dmgsrc;
	}


	@Override
	protected float getGravityVelocity() 
    {
    	return gravity;
    }
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.worldObj.isRemote){
			if (this.isInWater())
	        {
	            for (int l = 0; l < 2; ++l)
	            {
	                float f4 = 0.5F;
	                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ);
	            }
	            //this.setDead();
	        } else {
	        	float f5 = 0.1f;
	//            this.worldObj.spawnParticle("flame", this.posX, this.posY, this.posZ, this.motionX*f5, (this.motionY*f5)-this.getGravityVelocity(), this.motionZ*f5);
	//        	this.worldObj.spawnParticle("explode", this.posX, this.posY, this.posZ, this.motionX*f5, (this.motionY*f5)-this.getGravityVelocity(), this.motionZ*f5);
	        	
	        	Techguns.proxy.spawnParticle("Fireblast",this.worldObj, this.posX, this.posY, this.posZ, this.motionX*f5, (this.motionY*f5)-this.getGravityVelocity(), this.motionZ*f5);
	        }
		}
	}
	

}
