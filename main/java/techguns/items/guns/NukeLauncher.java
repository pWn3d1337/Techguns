package techguns.items.guns;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.client.ClientProxy;
import techguns.damagesystem.DamageType;
import techguns.entities.projectiles.NukeRocketProjectile;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;

public class NukeLauncher extends GenericGunRadiusGrav{
	
	//private float radius = 10.0f;
	//private float gravity = 0.01f;
	
	
	public NukeLauncher(String name, ItemStack ammo, boolean semiAuto, int minFiretime,
			int clipsize, int reloadtime, float damage, String firesound,
			String reloadsound, int TTL, float accuracy) {
		super(name, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage,
				firesound, reloadsound, TTL, accuracy);
		radius=10.0f;
		gravity=0.01f;
	}
	

	@Override
	protected void spawnProjectile (final World world, final EntityLivingBase player, ItemStack itemStack, float spread,float offset,float damagebonus, boolean leftGun) {
		boolean blockdamage=false;
		/*if (itemStack != null){
			blockdamage = (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("blockdamage") && itemStack.getTagCompound().getBoolean("blockdamage"));
		}*/
		
		if (player instanceof EntityPlayer){
			TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get((EntityPlayer) player);
			if(props!=null){
				blockdamage=!props.enableSafemode;
			}
		}
		
		//System.out.println("ignite = "+ignite);
		world.spawnEntityInWorld(new NukeRocketProjectile(world, player, damage*damagebonus, radius, 1.0f, this.ticksToLive, spread, gravity, blockdamage, this.penetration));
	}
	
	/*@Override
	protected void client_weaponFired() {
		ClientProxy.get().showWarhead = false;
	}
	
	@Override
	protected void client_startReload() {	
		ClientProxy.get().showWarhead = true;
	}*/


	@Override
	public DamageType getDamageType() {
		return DamageType.EXPLOSION;
	}
}
