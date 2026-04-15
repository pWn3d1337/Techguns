package techguns.items.guns;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.damagesystem.DamageType;
import techguns.entities.projectiles.CyberdemonBlasterProjectile;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.packets.PacketSpawnParticleOnEntity;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class CyberdemonBlaster extends GenericGun {

	public CyberdemonBlaster() {
		super();
	}

	public CyberdemonBlaster(ItemStack ammo) {
		super(ammo);
	}

	public CyberdemonBlaster(String name, ItemStack ammo, boolean semiAuto,
			int minFiretime, int clipsize, int reloadtime, float damage,
			String firesound, String reloadsound, int TTL, float accuracy) {
		super(name, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage,
				firesound, reloadsound, TTL, accuracy);
	}

	/* (non-Javadoc)
	 * @see techguns.items.guns.GenericGun#spawnProjectile(net.minecraft.world.World, net.minecraft.entity.EntityLivingBase, net.minecraft.item.ItemStack, float, float, float)
	 */
	@Override
	protected void spawnProjectile(World world, EntityLivingBase player,
			ItemStack itemstack, float spread, float offset, float damagebonus, boolean leftGun) {
		
		boolean ignite = true;
		if (player instanceof EntityPlayer){
			TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get((EntityPlayer) player);
			if(props!=null){
				ignite=!props.enableSafemode;
			}
		}
		
		CyberdemonBlasterProjectile ent = new CyberdemonBlasterProjectile(world, player, damage*damagebonus, 0.85f, this.ticksToLive, 0.00f, spread, false, ignite, damageDropStart, damageDropEnd, damageMin*damagebonus,penetration);
		world.spawnEntityInWorld(ent);
		TGPackets.network.sendToAllAround(new PacketSpawnParticleOnEntity("CyberDemonBlasterTrail", ent), new TargetPoint(world.provider.dimensionId, ent.posX, ent.posY, ent.posZ, 200.0f));
	}
	

	@Override
	public DamageType getDamageType() {
		return DamageType.FIRE;
	}
}
