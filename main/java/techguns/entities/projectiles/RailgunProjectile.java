package techguns.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import techguns.Techguns;
import techguns.items.guns.Lasergun;
import techguns.util.BulletDamageSource;

public class RailgunProjectile extends GenericProjectile {

	public RailgunProjectile(World par1World) {
		super(par1World);
		this.speed=Lasergun.SPEED;
		this.ticksToLive=50; //Lasergun.LIFETIME;
		this.lifetime =50; //Lasergun.LIFETIME;
	}

	public RailgunProjectile(World par2World, EntityLivingBase par3Player,
			float damage, float speed, int TTL, float spread, int dmgDropStart,
			int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage) {
		super(par2World, par3Player, damage, speed, TTL, spread, dmgDropStart,
				dmgDropEnd, dmgMin, penetration, blockdamage);
		this.speed=Lasergun.SPEED;
		this.ticksToLive=TTL;
		this.lifetime =TTL; //Lasergun.LIFETIME;
	}

	/* (non-Javadoc)
	 * @see techguns.entities.projectiles.GenericProjectile#onImpact(net.minecraft.util.MovingObjectPosition)
	 */
	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
		if (par1MovingObjectPosition.entityHit != null)
        {
            par1MovingObjectPosition.entityHit.attackEntityFrom(BulletDamageSource.causeBulletDamage(this, this.shooter, false), this.getDamage());
            if (this.getDamage()>2.0f) {
            	this.damage = this.damage-2.0f;
            } else {
            	this.setDead();
            }
        }

    	if (par1MovingObjectPosition.typeOfHit == MovingObjectType.BLOCK) {   		
			this.worldObj.spawnParticle("reddust", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			//Techguns.proxy.spawnParticle("LaserImpact", this.worldObj, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			this.setDead();
		}
            
    	
	}

	/* (non-Javadoc)
	 * @see techguns.entities.projectiles.GenericProjectile#onUpdate()
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.isInWater())
        {
            for (int l = 0; l < 4; ++l)
            {
                float f4 = 0.25F;
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ);
            }
            //this.setDead();
        } else {
        	float f5 = 0.1f;
        	float f2 = 0.2f;
        	//this.worldObj.spawnParticle("slime", this.posX, this.posY, this.posZ, this.motionX*f5, (this.motionY*f5)-this.getGravityVelocity(), this.motionZ*f5);
        	Techguns.proxy.spawnParticle("AlienBlasterFlare", this.worldObj, this.posX, this.posY, this.posZ, this.motionX*f5, this.motionY*f5, this.motionZ*f5);
        	for (int i=0; i<3;i++){
        		Techguns.proxy.spawnParticle("AlienBlasterFlare", this.worldObj, this.posX+this.motionX*f2, this.posY+this.motionY*f2, this.posZ+this.motionZ*f2, this.motionX*f5, this.motionY*f5, this.motionZ*f5);
        		Techguns.proxy.spawnParticle("AlienBlasterFlare", this.worldObj, this.posX-this.motionX*f2, this.posY-this.motionY*f2, this.posZ-this.motionZ*f2, this.motionX*f5, this.motionY*f5, this.motionZ*f5);
        		f2+=0.2;
        	}
        	
        }

	}

	
	
}
