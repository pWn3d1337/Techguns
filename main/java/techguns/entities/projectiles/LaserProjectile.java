package techguns.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import techguns.damagesystem.DamageSystem;
import techguns.damagesystem.TGDamageSource;
import techguns.items.guns.Lasergun;
import techguns.util.EntityDeathUtils.DeathType;

/*
 * Invisible Projectile for LaserGun. DMG and Impact code.
 */
public class LaserProjectile extends GenericProjectile{
	
	public LaserProjectile(World par1World) {
		super(par1World);
		this.speed=Lasergun.SPEED;
		this.ticksToLive=Lasergun.LIFETIME;
		this.lifetime=Lasergun.LIFETIME;
	}

	public LaserProjectile(World par2World, EntityLivingBase par3Player,
			float damage, float speed, int TTL, float spread, int dmgDropStart,
			int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage) {
		super(par2World, par3Player, damage, speed, TTL, spread, dmgDropStart,
				dmgDropEnd, dmgMin, penetration, blockdamage);
		this.speed=Lasergun.SPEED;
		this.ticksToLive=Lasergun.LIFETIME;
		this.lifetime =Lasergun.LIFETIME;
		
		this.spawnBeamProjectile(par2World, par3Player);
	}

	
	private void spawnBeamProjectile(World world, EntityLivingBase player){
		LaserBeam2 beam = new LaserBeam2(world, player,this);
		//beam.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
		//beam.setInitialPosition(player, this.rotationYaw, this.rotationPitch);
		//beam.motionX = this.motionX;
		//beam.motionY = this.motionY;
		//beam.motionZ = this.motionZ;
		world.spawnEntityInWorld(beam);
		
	}
	
	/* (non-Javadoc)
	 * @see techguns.entities.projectiles.GenericProjectile#onImpact(net.minecraft.util.MovingObjectPosition)
	 */
	@Override
	protected void onImpact(MovingObjectPosition mop) {
		if (mop.entityHit != null)
        {
			if (mop.entityHit instanceof EntityLivingBase){
        		EntityLivingBase ent = (EntityLivingBase) mop.entityHit;
        		float dmg = DamageSystem.getDamageFactor(this.shooter, ent);
        		if (dmg>0){
        			ent.attackEntityFrom(this.getProjectileDamageSource(), this.getDamage()*dmg);
        		}
        		
			} else {
			
			
				mop.entityHit.attackEntityFrom(this.getProjectileDamageSource(), this.getDamage());
			}
			this.setDead();

        } else if (mop.typeOfHit == MovingObjectType.BLOCK) {   		
			//this.worldObj.spawnParticle("reddust", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        	if (!this.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ).getBlocksMovement(this.worldObj, mop.blockX, mop.blockY, mop.blockZ)) {
    			this.setDead();
    		}
		}
    	//if (!this.worldObj.isRemote) {
    	//	Techguns.proxy.spawnParticle("LaserFlareEnd", this.worldObj, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
    	//}

	}
	
	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causeEnergyDamage(this, this.shooter, DeathType.LASER);
    	src.armorPenetration = this.penetration;
    	src.setNoKnockback();
    	src.goreChance = 0.35f;
    	return src;
	}
}
