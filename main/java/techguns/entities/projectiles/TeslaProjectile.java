package techguns.entities.projectiles;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import techguns.damagesystem.DamageSystem;
import techguns.damagesystem.TGDamageSource;
import techguns.items.guns.Teslagun;
import techguns.util.EntityDeathUtils.DeathType;

/*
 * Invisible Projectile for TeslaGun. DMG and Impact code.
 */
public class TeslaProjectile extends GenericProjectile {

	float chainDmgDrop;
	int chain;

	public TeslaProjectile(World par1World) {
		super(par1World);
		this.speed = Teslagun.SPEED;
		this.ticksToLive = Teslagun.LIFETIME;
		this.lifetime = Teslagun.LIFETIME;
	}

	public TeslaProjectile(World par2World, EntityLivingBase par3Player,
			float damage, int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration,
			int chain, float chainDmgDrop, boolean blockdamage) {
		super(par2World, par3Player, damage, Teslagun.SPEED, Teslagun.LIFETIME,
				0, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage);
		this.speed = Teslagun.SPEED;
		this.ticksToLive = Teslagun.LIFETIME;
		this.lifetime = Teslagun.LIFETIME;

		this.chain = chain;
		this.chainDmgDrop = chainDmgDrop;
	}

	/**
	 * 
	 * Chaining bolts
	 */
	public TeslaProjectile(World par2World, EntityLivingBase par3Player,
			double x, double y, double z, Entity target, float damage,
			int dmgDropStart, int dmgDropEnd, float dmgMin, int chain,
			float chainDmgDrop) {
		super(par2World);
		this.speed = Teslagun.SPEED;
		this.ticksToLive = Teslagun.LIFETIME;
		this.lifetime = Teslagun.LIFETIME;

		this.shooter = par3Player;
		this.setSize(0.25F, 0.25F);

		double dX = x - target.posX;
		double dY = y - target.posY;
		double dZ = z - target.posZ;

		double yaw = Math.atan2(dZ, dX);
		double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

		this.setLocationAndAngles(x, y, z, (float) yaw, (float) pitch);
		this.posX -= (double) (MathHelper.cos(this.rotationYaw / 180.0F
				* (float) Math.PI) * 0.16F);
		this.posY -= 0.10000000149011612D;
		this.posZ -= (double) (MathHelper.sin(this.rotationYaw / 180.0F
				* (float) Math.PI) * 0.16F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		float f = 0.4F;
		this.motionX = (double) (-MathHelper.sin(this.rotationYaw / 180.0F
				* (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * f);
		this.motionZ = (double) (MathHelper.cos(this.rotationYaw / 180.0F
				* (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * f);
		this.motionY = (double) (-MathHelper.sin((this.rotationPitch) / 180.0F
				* (float) Math.PI) * f);
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ,
				1.5f, 1.0F);

		Vec3 motion = Vec3.createVectorHelper(this.motionX, this.motionY,
				this.motionZ);
		motion.normalize();
		this.motionX = motion.xCoord;
		this.motionY = motion.yCoord;
		this.motionZ = motion.zCoord;

		this.motionX *= speed;
		this.motionY *= speed;
		this.motionZ *= speed;

		this.damage = damage;

		this.damageMin = dmgMin;
		this.damageDropStart = dmgDropStart;
		this.damageDropEnd = dmgDropEnd;

		this.chain = chain;
		this.chainDmgDrop = chainDmgDrop;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * techguns.entities.projectiles.GenericProjectile#onImpact(net.minecraft
	 * .util.MovingObjectPosition)
	 */
	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
		if (par1MovingObjectPosition.entityHit != null) {
			
			float dmg=1.0f;
			if ( par1MovingObjectPosition.entityHit instanceof EntityLivingBase){
				dmg = DamageSystem.getDamageFactor(this.shooter, (EntityLivingBase) par1MovingObjectPosition.entityHit);
			}
			
			par1MovingObjectPosition.entityHit.attackEntityFrom(this.getProjectileDamageSource(), this.getDamage()*dmg);
			if (par1MovingObjectPosition.entityHit instanceof EntityLiving){
				this.setAIRevengeTarget((EntityLiving) par1MovingObjectPosition.entityHit);
			}

			/*if (chain > 0) {
				Entity target = findNextTarget(par1MovingObjectPosition.entityHit);
				if (target != null) {
					this.worldObj.spawnEntityInWorld(new TeslaBeam(this.worldObj,
							this.shooter, this.posX, this.posY, this.posZ, target));
					this.worldObj.spawnEntityInWorld(new TeslaProjectile(
							this.worldObj, this.shooter, this.posX, this.posY,
							this.posZ, target, (damage - chainDmgDrop),
							this.damageDropStart, this.damageDropEnd, Math.min(1,
									this.damageMin - chainDmgDrop), chain - 1,
							chainDmgDrop));
				}
			}*/
//			double lastx=this.posX;
//			double lasty=this.posY;
//			double lastz=this.posZ;
			
			Entity target = par1MovingObjectPosition.entityHit;
			double lastx= target.posX;
			double lasty= target.posY+(target.height/2.0f);
			double lastz= target.posZ;
			
			//System.out.println("Target Height:"+target.height);
			
			List<Entity> targets = getChainingTargets(par1MovingObjectPosition.entityHit, this.chain);
			for (int i=0; i<targets.size(); i++){
				Entity t = targets.get(i);
				
				this.worldObj.spawnEntityInWorld(new TeslaBeam(this.worldObj,
						this.shooter, lastx, lasty, lastz, t));
				
				/*t.attackEntityFrom(BulletDamageSource.causeBulletDamage(this, this.shooter,
							false), 0); //this.getDamage()-(this.chainDmgDrop*(i+1)));*/
				
				dmg=1.0f;
				if ( t instanceof EntityLivingBase){
					dmg = DamageSystem.getDamageFactor(this.shooter, (EntityLivingBase) t);
				}
				
				t.attackEntityFrom(this.getProjectileDamageSource(), (this.getDamage()-(this.chainDmgDrop*(i+1)) *dmg));
				if (t instanceof EntityLiving){
					this.setAIRevengeTarget((EntityLiving) t);
				}
				
				lastx=t.posX;
				lasty=t.posY + (t.height/2.0f);
				lastz=t.posZ;		
			}
		}

		/*if (par1MovingObjectPosition.typeOfHit == MovingObjectType.BLOCK) {
			this.worldObj.spawnParticle("reddust", this.posX, this.posY,
					this.posZ, 0.0D, 0.0D, 0.0D);
			// Techguns.proxy.spawnParticle("LaserImpact", this.worldObj,
			// this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		}*/

		this.setDead();
	}

	@Override
    protected TGDamageSource getProjectileDamageSource(){
    	TGDamageSource src = TGDamageSource.causeLightningDamage(this, this.shooter, DeathType.LASER);
    	src.armorPenetration = this.penetration;
    	src.setNoKnockback();
    	return src;
    }
	
	private List<Entity> getChainingTargets(Entity lastTarget,int num){
		List targets = this.worldObj.getEntitiesWithinAABBExcludingEntity(
				lastTarget,
				AxisAlignedBB.getBoundingBox(lastTarget.posX
						- Teslagun.CHAIN_RANGE, lastTarget.posY
						- Teslagun.CHAIN_RANGE, lastTarget.posZ
						- Teslagun.CHAIN_RANGE, lastTarget.posX
						+ Teslagun.CHAIN_RANGE, lastTarget.posY
						+ Teslagun.CHAIN_RANGE, lastTarget.posZ
						+ Teslagun.CHAIN_RANGE));
		
		
		List<Entity> chainTargets = new ArrayList<Entity>(num);
		
		for (int i1 = 0; i1 < targets.size(); ++i1)
        {
            Entity entity = (Entity)targets.get(i1);
            
        	try {
        		EntityLivingBase elb = (EntityLivingBase)entity;
        		if (!elb.equals(shooter)) {
        			num--;
//        			Vec3 src = Vec3.createVectorHelper(lastTarget.posX, lastTarget.posY+(lastTarget.height/2.0),lastTarget.posZ);
//        			Vec3 dst = Vec3.createVectorHelper(entity.posX, entity.posY+(entity.height/2.0),entity.posZ);
//        			MovingObjectPosition mop = MathUtil.traceVector(this.worldObj, src, src.subtract(dst), 20.0, 1);
//        			if (mop != null && mop.typeOfHit== MovingObjectType.ENTITY && mop.entityHit == entity) {
        				chainTargets.add(elb);
        			//}
        			if (num<=0){
        				break;
        			}
        		}
        	}catch(Exception e) {
        		//System.out.println("Not a living entity.");
        	}
            
            
        }
		
		return chainTargets;
	}
	
	private Entity findNextTarget(Entity lastTarget) {
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(
				lastTarget,
				AxisAlignedBB.getBoundingBox(lastTarget.posX
						- Teslagun.CHAIN_RANGE, lastTarget.posY
						- Teslagun.CHAIN_RANGE, lastTarget.posZ
						- Teslagun.CHAIN_RANGE, lastTarget.posX
						+ Teslagun.CHAIN_RANGE, lastTarget.posY
						+ Teslagun.CHAIN_RANGE, lastTarget.posZ
						+ Teslagun.CHAIN_RANGE));
		
		for (int i1 = 0; i1 < list.size(); ++i1)
        {
            Entity entity = (Entity)list.get(i1);
            System.out.println(entity.toString());
            
            double distance = entity.getDistance(lastTarget.posX, lastTarget.posY, lastTarget.posZ);
            
            if (distance < Teslagun.CHAIN_RANGE && entity.isEntityAlive() && entity != lastTarget) {
            	try {
            		EntityLivingBase elb = (EntityLivingBase)entity;
            		if (!elb.equals(shooter)) {
            			return entity;
            		}
            	}catch(Exception e) {
            		System.out.println("Not a living entity.");
            	}
            }
        }
		return null;
	}

}
