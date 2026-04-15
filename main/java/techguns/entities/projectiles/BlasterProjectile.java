package techguns.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import techguns.items.guns.Lasergun;
import techguns.util.BulletDamageSource;

@Deprecated
public class BlasterProjectile extends GenericProjectile {
	
	public BlasterProjectile(World par1World) {
		super(par1World);
		this.speed=Lasergun.SPEED;
		this.ticksToLive=Lasergun.LIFETIME;
		this.lifetime=Lasergun.LIFETIME;
	}

	public BlasterProjectile(World par2World, EntityLivingBase par3Player,
			float damage, float speed, int TTL, float spread, int dmgDropStart,
			int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage) {
		super(par2World, par3Player, damage, speed, TTL, spread, dmgDropStart,
				dmgDropEnd, dmgMin, penetration,blockdamage);
		this.speed=Lasergun.SPEED;
		this.ticksToLive=Lasergun.LIFETIME;
		this.lifetime =Lasergun.LIFETIME;
	}

	/* (non-Javadoc)
	 * @see techguns.entities.projectiles.GenericProjectile#onImpact(net.minecraft.util.MovingObjectPosition)
	 */
	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
		if (par1MovingObjectPosition.entityHit != null)
        {
            par1MovingObjectPosition.entityHit.attackEntityFrom(BulletDamageSource.causeBulletDamage(this, this.shooter, false), this.getDamage());
            this.setDead();
        }


    	if (par1MovingObjectPosition.typeOfHit == MovingObjectType.BLOCK) {   		
			this.worldObj.spawnParticle("reddust", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			//Techguns.proxy.spawnParticle("LaserImpact", this.worldObj, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		}

            
    	this.setDead();
	}

	
	
}
