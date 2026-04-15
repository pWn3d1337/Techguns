package techguns.items.guns;


import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.damagesystem.DamageType;
import techguns.entities.projectiles.MovingBeam;

public class Beamgun extends GenericGun{

	
	public static final int BEAM_LIFETIME = 10;
	public static final int LIFETIME=5;
	public static final float SPEED =5.0f;
	
	public Beamgun() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Beamgun(ItemStack ammo) {
		super(ammo);
		// TODO Auto-generated constructor stub
	}

	public Beamgun(String name, ItemStack ammo, boolean semiAuto, int minFiretime,
			int clipsize, int reloadtime, float damage, String firesound,
			String reloadsound, int TTL, float accuracy) {
		super(name, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage,
				firesound, reloadsound, TTL, accuracy);
	}

	/* (non-Javadoc)
	 * @see techguns.items.guns.GenericGun#spawnProjectile(net.minecraft.world.World, net.minecraft.entity.player.EntityPlayer, net.minecraft.item.ItemStack, float)
	 */
	@Override
	protected void spawnProjectile(World world, EntityLivingBase player, ItemStack itemstack, float spread,float offset, float damagebonus, boolean leftGun) {
		//world.spawnEntityInWorld(new BlasterProjectile(world, player, damage, speed, this.ticksToLive, spread, this.damageDropStart, this.damageDropEnd, this.damageMin));
		//world.spawnEntityInWorld(new LaserBeam2(world, player));
		world.spawnEntityInWorld(new MovingBeam(world, player, damage*damagebonus, speed, this.ticksToLive, spread, this.damageDropStart, this.damageDropEnd, this.damageMin*damagebonus, this.penetration, getDoBlockDamage(player)));
	}
	

	
	
	@Override
	public DamageType getDamageType() {
		return DamageType.RADIATION;
	}
}
