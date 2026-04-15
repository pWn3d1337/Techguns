package techguns.entities.projectiles;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import techguns.damagesystem.TGDamageSource;
import techguns.util.EntityDeathUtils.DeathType;

public class BlasterRifleProjectile extends GenericProjectile {

	public BlasterRifleProjectile(World par1World) {
		super(par1World);
	}

	public BlasterRifleProjectile(World par2World, double posX, double posY,
			double posZ, float yaw, float pitch, float damage, float speed,
			int TTL, float spread, int dmgDropStart, int dmgDropEnd,
			float dmgMin, float penetration, boolean blockdamage) {
		super(par2World, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread,
				dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, false);
	}

	public BlasterRifleProjectile(World par2World, double posX, double posY,
			double posZ, float damage, float speed, int TTL, float spread,
			int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration,boolean blockdamage) {
		super(par2World, posX, posY, posZ, damage, speed, TTL, spread, dmgDropStart,
				dmgDropEnd, dmgMin, penetration, blockdamage);
	}

	public BlasterRifleProjectile(World par2World, EntityLivingBase par3Player,
			float damage, float speed, int TTL, float spread, int dmgDropStart,
			int dmgDropEnd, float dmgMin, float penetration,boolean blockdamage) {
		super(par2World, par3Player, damage, speed, TTL, spread, dmgDropStart,
				dmgDropEnd, dmgMin, penetration, blockdamage);
	}

	@Override
	protected void spawnImpactParticle(Material mat) {
		//this.worldObj.playSoundAtEntity(this, "techguns:effects.bulletimpact", 1.0F, 1.0F );
		this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
	}

	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causeEnergyDamage(this, this.shooter, DeathType.LASER);
    	src.armorPenetration = this.penetration;
    	src.setNoKnockback();
    	src.goreChance = 0.25f;
    	return src;
	}

	
	
}
