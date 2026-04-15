package techguns.items.guns;


import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.damagesystem.DamageType;
import techguns.entities.projectiles.TeslaBeam;
import techguns.entities.projectiles.TeslaProjectile;

public class Teslagun extends GenericGun{

	
	public static final int BEAM_LIFETIME = 10;
	public static final int LIFETIME=10;
	public static final float SPEED =5.0f;
	
	public static final float CHAIN_RANGE = 5.0f;
	
	public int chain = 4;
	public int chainDmgDrop = 4;
	
	public Teslagun() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Teslagun(ItemStack ammo) {
		super(ammo);
		// TODO Auto-generated constructor stub
	}

	public Teslagun(String name, ItemStack ammo, boolean semiAuto, int minFiretime,
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
		//world.spawnEntityInWorld(new BlasterProjectile(world, player, damage, speed, this.ticksToLive, spread, this.damageDropStart, this.damageDropEnd, this.damageMin));
		world.spawnEntityInWorld(new TeslaBeam(world, player));
		world.spawnEntityInWorld(new TeslaProjectile(world, player, damage*damagebonus, this.damageDropStart, this.damageDropEnd, this.damageMin*damagebonus,this.penetration,3,0, getDoBlockDamage(player)));
	}
	

	@Override
	public DamageType getDamageType() {
		return DamageType.LIGHTNING;
	}
}
