package techguns.items.guns;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.entities.projectiles.SimpleProjectile;

public class Handgun extends GenericGun{

	public Handgun() {
		super();
	}

	public Handgun(ItemStack ammo) {
		super(ammo);
	}

	public Handgun(String name, ItemStack ammo, boolean semiAuto, int minFiretime,
			int clipsize, int reloadtime, float damage, String firesound,
			String reloadsound, int TTL, float accuracy) {
		super(name, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage,
				firesound, reloadsound, TTL, accuracy);
	}

	@Override
	protected void spawnProjectile (final World world, final EntityLivingBase player, ItemStack itemstack, float spread,float offset,float damagebonus, boolean leftGun) {
		world.spawnEntityInWorld(new SimpleProjectile(world, player, damage*damagebonus, 1.0f, this.ticksToLive, "techguns:textures/entity/handgunBullet.png", 1.0f, 0.015f, spread, this.damageDropStart, this.damageDropEnd, this.damageMin*damagebonus, this.penetration, getDoBlockDamage(player)));
	}
	
}
