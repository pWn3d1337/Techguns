package techguns.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class FragGrenadeProjectile extends GrenadeProjectile {

	public FragGrenadeProjectile(World par1World) {
		super(par1World);
	}

	public FragGrenadeProjectile(World par2World, double posx, double posy, double posz, float damage, float radius, float speed, int TTL, float spread,
			float gravity, boolean blockdamage, float penetration, int maxBounces, double x, double y, double z) {
		super(par2World, posx, posy, posz, damage, radius, speed, TTL, spread, gravity, blockdamage, penetration, maxBounces, x, y, z);
	}

	public FragGrenadeProjectile(World par2World, EntityLivingBase par3Player, float damage, float radius, float speed, int TTL, float spread, float gravity,
			boolean blockdamage, float penetration, int maxBounces, double x, double y, double z) {
		super(par2World, par3Player, damage, radius, speed, TTL, spread, gravity, blockdamage, penetration, maxBounces, x, y, z);
	}

	public FragGrenadeProjectile(World par2World, EntityLivingBase par3Player, float damage, float radius, float speed, int TTL, float spread, float gravity,
			boolean blockdamage, float penetration, int maxBounces) {
		super(par2World, par3Player, damage, radius, speed, TTL, spread, gravity, blockdamage, penetration, maxBounces);
	}

	@Override
	protected GrenadeProjectile createBounceProjectile() {
		if (shooter !=null){
			return new FragGrenadeProjectile(this.worldObj, this.shooter, this.damage,this.radius,this.speed,this.ticksToLive,0.0f,this.gravity,false,this.penetration,this.bounces,this.posX,this.posY,this.posZ);
		} else {
			return new FragGrenadeProjectile(this.worldObj, this.posX, this.posY, this.posZ, this.damage,this.radius,this.speed,this.ticksToLive,0.0f,this.gravity,false,this.penetration,this.bounces,this.posX,this.posY,this.posZ);
		}
	}

	@Override
	protected String getExplosionEffect() {
		return "FragGrenadeExplosion";
	}
	
	
}
