package techguns.items.guns;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.damagesystem.DamageType;
import techguns.entities.projectiles.BlasterRifleProjectile;

public class BlasterRifle extends GenericGun {

	public BlasterRifle() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BlasterRifle(ItemStack ammo) {
		super(ammo);
		// TODO Auto-generated constructor stub
	}

	public BlasterRifle(String name, ItemStack ammo, boolean semiAuto,
			int minFiretime, int clipsize, int reloadtime, float damage,
			String firesound, String reloadsound, int TTL, float accuracy) {
		super(name, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage,
				firesound, reloadsound, TTL, accuracy);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void spawnProjectile(World world, EntityLivingBase player,
			ItemStack itemstack, float spread, float offset, float damagebonus, boolean leftGun) {
		
    	BlasterRifleProjectile proj = new BlasterRifleProjectile(world, player, damage*damagebonus, speed, this.ticksToLive, spread, this.damageDropStart, this.damageDropEnd, this.damageMin*damagebonus, this.penetration, getDoBlockDamage(player));
    	
    	if (silenced){
    		proj.setSilenced();
    	}
    	if (offset>0.0f){
    		proj.shiftForward(offset);
    	}
    	world.spawnEntityInWorld(proj);
	}


	@Override
	public DamageType getDamageType() {
		return DamageType.ENERGY;
	}
	
}
