package techguns.items.guns;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.damagesystem.DamageType;
import techguns.entities.projectiles.GrenadeLauncherProjectile;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.packets.PacketSpawnParticleOnEntity;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class GrenadeLauncher extends GenericGunRadiusGrav{
		
	//public float radius = 1.50f;
	//public float gravity = 0.01f;
	public float speed = 1.0f;
	
	
	public GrenadeLauncher(String name, ItemStack ammo, boolean semiAuto, int minFiretime,
			int clipsize, int reloadtime, float damage, String firesound,
			String reloadsound, int TTL, float accuracy) {
		super(name, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage,
				firesound, reloadsound, TTL, accuracy);
		radius=1.5f;
		gravity=0.01f;
	}
	
	@Override
	protected void spawnProjectile (final World world, final EntityLivingBase player, ItemStack itemStack, float spread,float offset,float damagebonus, boolean leftGun) {
		boolean blockdamage=false;

		if (player instanceof EntityPlayer){
			TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get((EntityPlayer) player);
			if(props!=null){
				blockdamage=!props.enableSafemode;
			}
		}

		//world.spawnEntityInWorld(new RocketProjectile(world, player, damage*damagebonus, radius, 1.0f, this.ticksToLive, spread, gravity, blockdamage, this.penetration));
		GrenadeLauncherProjectile ent = new GrenadeLauncherProjectile(world, player, this.damage, radius, speed, 100 ,0.05f, gravity,blockdamage,0.0f,2);
		world.spawnEntityInWorld(ent);
		TGPackets.network.sendToAllAround(new PacketSpawnParticleOnEntity("GrenadeLauncherTrail", ent), new TargetPoint(world.provider.dimensionId, ent.posX, ent.posY, ent.posZ, 200.0f));
	}

	@Override
	public DamageType getDamageType() {
		return DamageType.EXPLOSION;
	}
}

