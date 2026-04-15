package techguns.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.packets.PacketSpawnParticleOnEntity;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class GrenadeLauncherProjectile extends GrenadeProjectile{

	public GrenadeLauncherProjectile(World par2World,
			EntityLivingBase par3Player, float damage, float radius,
			float speed, int TTL, float spread, float gravity,
			boolean blockdamage, float penetration, int maxBounces, double x,
			double y, double z) {
		super(par2World, par3Player, damage, radius, speed, TTL, spread, gravity,
				blockdamage, penetration, maxBounces, x, y, z);
	}
	
	
	public GrenadeLauncherProjectile(World par1World) {
		super(par1World);
		this.gravity = 0.01f;
	}

	public GrenadeLauncherProjectile(World par2World, EntityLivingBase par3Player,
			float damage, float radius, float speed, int TTL, float spread, float gravity, boolean blockdamage, float penetration,int maxBounces) {
		super(par2World, par3Player, damage, radius, speed, TTL, spread, gravity, blockdamage, penetration, maxBounces);
	}
	
	
	public GrenadeLauncherProjectile(World par2World, double posx, double posy, double posz, float damage, float radius, float speed, int TTL, float spread,
			float gravity, boolean blockdamage, float penetration, int maxBounces, double x, double y, double z) {
		super(par2World, posx, posy, posz, damage, radius, speed, TTL, spread, gravity, blockdamage, penetration, maxBounces, x, y, z);
	}


	@Override
	protected void createProjectileTrail(GrenadeProjectile gren) {
		TGPackets.network.sendToAllAround(new PacketSpawnParticleOnEntity("GrenadeLauncherTrail", gren), new TargetPoint(this.worldObj.provider.dimensionId, gren.posX, gren.posY, gren.posZ, 200.0f));
	}
	
	@Override
	protected GrenadeProjectile createBounceProjectile() {
		float s = this.speed*0.75f;
		int ttl = this.ticksToLive-20;
		if(this.shooter!=null){
			return new GrenadeLauncherProjectile(this.worldObj, this.shooter, this.damage,this.radius, s , ttl ,0.0f,this.gravity,this.blockdamage,this.penetration,this.bounces,this.posX,this.posY,this.posZ);
		} else {
			return new GrenadeLauncherProjectile(this.worldObj, this.posX, this.posY, this.posZ, this.damage,this.radius, s , ttl ,0.0f,this.gravity,this.blockdamage,this.penetration,this.bounces,this.posX,this.posY,this.posZ);
		}
	}

}
