package techguns.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.damagesystem.TGDamageSource;
import techguns.packets.PacketSpawnParticle;
import techguns.util.EntityDeathUtils.DeathType;
import techguns.util.ProjectileExplosion;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class NukeRocketProjectile extends RocketProjectile {

	//private float radius;
	//private float gravity;
	//private boolean blockdamage;
	
	public NukeRocketProjectile(World par1World) {
		super(par1World);
		this.gravity = 0.01f;
	}


	
	public NukeRocketProjectile(World par2World, double posX, double posY, double posZ, float yaw, float pitch, float damage, float radius, float speed,
			int TTL, float spread, float gravity, boolean blockdamage, float penetration) {
		super(par2World, posX, posY, posZ, yaw, pitch, damage, radius, speed, TTL, spread, gravity, blockdamage, penetration);
	}



	public NukeRocketProjectile(World par2World, double posX, double posY, double posZ, float damage, float radius, float speed, int TTL, float spread,
			float gravity, boolean blockdamage, float penetration) {
		super(par2World, posX, posY, posZ, damage, radius, speed, TTL, spread, gravity, blockdamage, penetration);
	}



	public NukeRocketProjectile(World par2World, EntityLivingBase par3Player, float damage, float radius, float speed, int TTL, float spread, float gravity,
			boolean blockdamage, float penetration) {
		super(par2World, par3Player, damage, radius, speed, TTL, spread, gravity, blockdamage, penetration);
	}


/*	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
		
		//this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, radius, blockdamage);
		
//		Explosion explosion = new Explosion(this.worldObj, this, this.posX, this.posY, this.posZ, radius);
//	    explosion.isFlaming = false;
//	    explosion.isSmoking = true;
//	    if (blockdamage) explosion.doExplosionA();
//	    explosion.doExplosionB(true);
		
		ProjectileExplosion explosion = new ProjectileExplosion(worldObj, this.posX, this.posY, this.posZ, this.shooter, radius, (int)damage, radius*0.5f, radius*1.5f);
		
		//Techguns.proxy.createFX("NukeExplosion", this.worldObj, this.posX, this.posY, this.posZ, 0, 0, 0);
//		Techguns.proxy.createFX("NukeExplosionBlastwave", this.worldObj, this.posX, this.posY, this.posZ, 0, 0, 0); //DONE
//	    Techguns.proxy.createFX("NukeExplosionSmoke", this.worldObj, this.posX, this.posY, this.posZ, 0, 0, 0); //DONE
//	    Techguns.proxy.createFX("NukeExplosion", this.worldObj, this.posX, this.posY, this.posZ, 0, 0, 0); //DONE
//	    Techguns.proxy.createFX("NukeExplosionBlast", this.worldObj, this.posX, this.posY, this.posZ, 0, 0, 0); //DONE
//	    Techguns.proxy.createFX("NukeExplosionBall", this.worldObj, this.posX, this.posY, this.posZ, 0, 0, 0); //DONE
//	    Techguns.proxy.createFX("NukeExplosionRing", this.worldObj, this.posX, this.posY, this.posZ, 0, 0, 0); //DONE
//	    Techguns.proxy.createFX("NukeExplosionMushroomStem", this.worldObj, this.posX, this.posY, this.posZ, 0, 0, 0); //DONE
//	    Techguns.proxy.createFX("NukeExplosionMushroomCloud", this.worldObj, this.posX, this.posY, this.posZ, 0, 0, 0); //DONE
		
		if (!this.worldObj.isRemote){
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("NukeExplosion", this.posX,this.posY,this.posZ), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 150.0f));
		}
		
	    this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "techguns:effects.nukeexplosion", 8.0F, 1.0F); //(1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

		if (par1MovingObjectPosition.entityHit != null)
        {
            //par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.setExplosionSource(explosion.explosion), damage);
			TGDamageSource dmgsrc = TGDamageSource.causeExplosionDamage(this, this.shooter, DeathType.GORE);
			//dmgsrc.setExplosionSource(explosion.explosion);
			dmgsrc.goreChance = 1.0f;
			dmgsrc.knockbackMultiplier=10.0f;
			par1MovingObjectPosition.entityHit.attackEntityFrom(dmgsrc, damage);
        }
		
		explosion.doExplosion(blockdamage, this.shooter);
		
        this.setDead();
	}*/
	
	
	
	
	@Override
	protected float getGravityVelocity() 
    {
    	return gravity;
    }
	
	@Override
	protected void explodeRocket() {
		if (!this.worldObj.isRemote){
	
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("NukeExplosion", this.posX,this.posY,this.posZ), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 150.0f));
			
		    this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "techguns:effects.nukeexplosion", 8.0F, 1.0F); //(1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

			ProjectileExplosion explosion = new ProjectileExplosion(worldObj, this.posX, this.posY, this.posZ, this.shooter, radius, (int)damage, radius*0.5f, radius*1.5f);			
		    
			explosion.doExplosion(blockdamage, this.shooter);
		}
		this.setDead();
	}



	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource dmgsrc = TGDamageSource.causeExplosionDamage(this, this.shooter, DeathType.GORE);
		dmgsrc.goreChance = 1.0f;
		dmgsrc.knockbackMultiplier=10.0f;
		return dmgsrc;
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
