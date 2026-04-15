package techguns.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import techguns.damagesystem.DamageType;
import techguns.damagesystem.TGDamageSource;
import techguns.util.EntityDeathUtils.DeathType;

public class InvisibleProjectile extends GenericProjectile{

	public InvisibleProjectile(World par2World, double posX, double posY,
			double posZ, float yaw, float pitch, float damage, float speed,
			int TTL, float spread, int dmgDropStart, int dmgDropEnd,
			float dmgMin, float penetration,boolean blockdamage) {
		super(par2World, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread,
				dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, false);
		// TODO Auto-generated constructor stub
	}

	public InvisibleProjectile(World par2World, double posX, double posY,
			double posZ, float damage, float speed, int TTL, float spread,
			int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration,boolean blockdamage) {
		super(par2World, posX, posY, posZ, damage, speed, TTL, spread, dmgDropStart,
				dmgDropEnd, dmgMin, penetration, blockdamage);
		// TODO Auto-generated constructor stub
	}

	public InvisibleProjectile(World par2World, EntityLivingBase par3Player,
			float damage, float speed, int TTL, float spread, int dmgDropStart,
			int dmgDropEnd, float dmgMin, float penetration,boolean blockdamage) {
		super(par2World, par3Player, damage, speed, TTL, spread, dmgDropStart,
				dmgDropEnd, dmgMin, penetration, blockdamage);
		// TODO Auto-generated constructor stub
	}

	public InvisibleProjectile(World par1World) {
		super(par1World);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = new TGDamageSource("chainsaw", this, this.shooter, DamageType.PHYSICAL, DeathType.GORE);
		src.ignoreHurtresistTime=true;
    	src.goreChance=1.0f;
    	src.knockbackMultiplier=0.0f;
    	src.armorPenetration=this.penetration;
    	return src;
	}

	

}
