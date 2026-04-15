package techguns.items.guns;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.damagesystem.DamageType;
import techguns.entities.projectiles.BlasterRifleProjectile;
import techguns.entities.projectiles.EnergyBlastProjectile;
import techguns.packets.PacketSpawnParticleOnEntity;

public class MibGun extends GenericGun {

	public MibGun() {
		super();
	}

	public MibGun(ItemStack ammo) {
		super(ammo);
	}

	public MibGun(String name, ItemStack ammo, boolean semiAuto, int minFiretime, int clipsize, int reloadtime, float damage, String firesound,
			String reloadsound, int TTL, float accuracy) {
		super(name, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL, accuracy);
	}

	@Override
	protected void spawnProjectile(World world, EntityLivingBase player,
			ItemStack itemstack, float spread, float offset, float damagebonus, boolean leftGun) {
		
		EnergyBlastProjectile proj = new EnergyBlastProjectile(world, player, damage*damagebonus, speed, this.ticksToLive, spread, this.damageDropStart, this.damageDropEnd, this.damageMin*damagebonus, this.penetration, getDoBlockDamage(player), leftGun);
    	
    	if (silenced){
    		proj.setSilenced();
    	}
    	if (offset>0.0f){
    		proj.shiftForward(offset);
    	}
    	world.spawnEntityInWorld(proj);
    	TGPackets.network.sendToAllAround(new PacketSpawnParticleOnEntity("BlueBlasterTrail", proj), new TargetPoint(world.provider.dimensionId, proj.posX, proj.posY, proj.posZ, 200.0f));
	}


	@Override
	public DamageType getDamageType() {
		return DamageType.ENERGY;
	}
	
}
