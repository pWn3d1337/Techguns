package techguns.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import techguns.damagesystem.DamageSystem;
import techguns.damagesystem.TGDamageSource;
import techguns.items.guns.Beamgun;
import techguns.util.EntityDeathUtils.DeathType;

/*
 * Invisible Projectile for LaserGun. DMG and Impact code.
 */
public class BeamProjectile extends GenericProjectile{
	
	public BeamProjectile(World par1World) {
		super(par1World);
		this.speed=Beamgun.SPEED;
		this.ticksToLive=Beamgun.LIFETIME;
		this.lifetime=Beamgun.LIFETIME;
	}

	public BeamProjectile(World par2World, EntityLivingBase par3Player,
			float damage, float speed, int TTL, float spread, int dmgDropStart,
			int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage) {
		super(par2World, par3Player, damage, speed, TTL, spread, dmgDropStart,
				dmgDropEnd, dmgMin, penetration, blockdamage);
		this.speed=Beamgun.SPEED;
		this.ticksToLive=Beamgun.LIFETIME;
		this.lifetime =Beamgun.LIFETIME;
		
		this.spawnBeamProjectile(par2World, par3Player);
	}

	public boolean canBeCollidedWith()
    {
        return false;
    }
	
	private void spawnBeamProjectile(World world, EntityLivingBase player){
	//	MovingBeam beam = new MovingBeam(world, player,this);
		//beam.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
		//beam.setInitialPosition(player, this.rotationYaw, this.rotationPitch);
		//beam.motionX = this.motionX;
		//beam.motionY = this.motionY;
		//beam.motionZ = this.motionZ;
	//	world.spawnEntityInWorld(beam);
		
	}
	
	/* (non-Javadoc)
	 * @see techguns.entities.projectiles.GenericProjectile#onImpact(net.minecraft.util.MovingObjectPosition)
	 */
	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
		if (par1MovingObjectPosition.entityHit != null)
        {
			if (par1MovingObjectPosition.entityHit instanceof EntityLivingBase){
        		EntityLivingBase ent = (EntityLivingBase) par1MovingObjectPosition.entityHit;
        		float dmg = DamageSystem.getDamageFactor(this.shooter, ent);
        		if (dmg>0){
        			ent.attackEntityFrom(this.getProjectileDamageSource(), this.getDamage()*dmg);
        		}
        		
			} else {
			
			
				par1MovingObjectPosition.entityHit.attackEntityFrom(this.getProjectileDamageSource(), this.getDamage());
			}

        }


    	if (par1MovingObjectPosition.typeOfHit == MovingObjectType.BLOCK) {   		
			//this.worldObj.spawnParticle("reddust", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			
		}

    	//if (!this.worldObj.isRemote) {
    	//	Techguns.proxy.spawnParticle("LaserFlareEnd", this.worldObj, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
    	//}
            
    	this.setDead();
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
