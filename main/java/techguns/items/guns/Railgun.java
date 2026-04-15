package techguns.items.guns;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.entities.projectiles.RailgunProjectile;

public class Railgun extends GenericGun {

	public Railgun() {
		super();
	}

	public Railgun(ItemStack ammo) {
		super(ammo);
	}

	public Railgun(String name, ItemStack ammo, boolean semiAuto, int minFiretime,
			int clipsize, int reloadtime, float damage, String firesound,
			String reloadsound, int TTL, float accuracy) {
		super(name, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage,
				firesound, reloadsound, TTL, accuracy);
	}

	/* (non-Javadoc)
	 * @see techguns.items.guns.GenericGun#spawnProjectile(net.minecraft.world.World, net.minecraft.entity.player.EntityPlayer, net.minecraft.item.ItemStack, float)
	 */
	@Override
	protected void spawnProjectile(World world, EntityLivingBase player,
			ItemStack itemStack, float spread,float offset, float damagebonus, boolean leftGun) {
		
		world.spawnEntityInWorld(new RailgunProjectile(world, player, damage*damagebonus, speed, this.ticksToLive, spread, this.damageDropStart, this.damageDropEnd, this.damageMin*damagebonus, this.penetration, getDoBlockDamage(player)));
	}

	
}
