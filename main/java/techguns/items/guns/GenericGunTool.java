package techguns.items.guns;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.entities.projectiles.MiningDummyProjectile;

public class GenericGunTool extends GenericGun {

	
	
	public GenericGunTool(ItemStack ammo) {
		super(ammo);
		// TODO Auto-generated constructor stub
	}

	public GenericGunTool(String name, ItemStack ammo, boolean semiAuto,
			int minFiretime, int clipsize, int reloadtime, float damage,
			String firesound, String reloadsound, int TTL, float accuracy) {
		super(name, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage,
				firesound, reloadsound, TTL, accuracy);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see techguns.items.guns.GenericGun#spawnProjectile(net.minecraft.world.World, net.minecraft.entity.player.EntityPlayer, net.minecraft.item.ItemStack, float)
	 */
	@Override
	protected void spawnProjectile(World world, EntityLivingBase player, ItemStack itemstack, float spread, float offset, float damagebonus, boolean leftGun) {
		System.out.println("FIRE MINING PROJ");
		world.spawnEntityInWorld(new MiningDummyProjectile(world, player, damage*damagebonus, speed, this.ticksToLive, spread, this.damageDropStart, this.damageDropEnd, this.damageMin, this.penetration));
	}

		
	
}
