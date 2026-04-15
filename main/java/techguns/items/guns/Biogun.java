package techguns.items.guns;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.damagesystem.DamageType;
import techguns.entities.projectiles.BiogunProjectile;
import techguns.packets.PacketSpawnParticleOnEntity;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class Biogun extends GenericGunCharge{

	
	
	public Biogun(String name, ItemStack ammo, boolean semiAuto, int minFiretime,
			int clipsize, int reloadtime, float damage, String firesound,
			String reloadsound, int TTL, float accuracy) {
		super(name, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage,
				firesound, reloadsound, TTL, accuracy,30.0f);
		this.ammoConsumedOnFullCharge=3;
		// TODO Auto-generated constructor stub
	}

	/*protected void spawnProjectile (final World world, final EntityPlayer player, ItemStack itemStack, float spread) {
		world.spawnEntityInWorld(new BiogunProjectile(world, player, damage, 1.0f, this.ticksToLive, spread,  damageDropStart, damageDropEnd, damageMin, penetration));
	}*/

	@Override
	public void spawnChargedProjectile(World world, EntityLivingBase player,
			ItemStack itemStack, float spread, float charge, int ammoConsumed) {
		
		/*int level=1;
		if (charge>0.95f){
			level=3;
		} else if(charge >0.45f){
			level=2;
		}
		level-=magDiffToCharge;*/
		Entity ent = new BiogunProjectile(world, player, damage, 1.0f, this.ticksToLive, spread,  damageDropStart, damageDropEnd, damageMin, penetration, ammoConsumed, getDoBlockDamage(player));
		world.spawnEntityInWorld(ent);
		TGPackets.network.sendToAllAround(new PacketSpawnParticleOnEntity("BioGunTrail", ent), new TargetPoint(world.provider.dimensionId, ent.posX, ent.posY, ent.posZ, 200.0f));
		
		//System.out.println("Charge:"+charge);
	}

	

	@Override
	protected void spawnProjectile(World world, EntityLivingBase player, ItemStack itemstack, float spread, float offset, float damagebonus, boolean leftGun) {
		this.spawnChargedProjectile(world, player, itemstack, spread, 0.0f, 1);
	}

	@Override
	public int consumeAmmoCharge(ItemStack item, float f, boolean creative) {
		int level=1;
		if (f>0.95f){
			level=3;
		} else if(f >0.45f){
			level=2;
		}

		int ammo=this.getCurrentAmmo(item);
		if(ammo<level){
			level-=ammo;
		}
		/*if ((item.getItemDamage()+level)>item.getMaxDamage()){
			dif = (item.getItemDamage()+level - item.getMaxDamage());
		}
		level-=dif;*/

		//item.setItemDamage(item.getItemDamage()+level);
		if(!creative){
			this.useAmmo(item, level);
		}
		return level;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return Integer.MAX_VALUE;
	}	
	

	@Override
	public DamageType getDamageType() {
		return DamageType.POISON;
	}
	
}
