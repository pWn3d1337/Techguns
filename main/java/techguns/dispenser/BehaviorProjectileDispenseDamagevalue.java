package techguns.dispenser;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import techguns.TGItems;
import techguns.TGPackets;
import techguns.TGuns;
import techguns.entities.projectiles.GrenadeLauncherProjectile;
import techguns.entities.projectiles.NukeRocketProjectile;
import techguns.entities.projectiles.RocketProjectile;
import techguns.items.guns.GrenadeLauncher;
import techguns.items.guns.NukeLauncher;
import techguns.items.guns.RocketLauncher;
import techguns.packets.PacketSpawnParticleOnEntity;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class BehaviorProjectileDispenseDamagevalue extends BehaviorDefaultDispenseItem {

	public BehaviorProjectileDispenseDamagevalue(){
	}

	@Override
	protected ItemStack dispenseStack(IBlockSource src, ItemStack stack) {
		if (this.hasSpecialDispense(stack.getItemDamage())){
			return this.dispenseStackProjectile(src, stack);
		} else {
			return super.dispenseStack(src, stack);
		}
	}
	
	/**
	 * copied from BehaviorProjectileDispenseDamagevalue
	 * @param src
	 * @param stack
	 * @return
	 */
    private ItemStack dispenseStackProjectile(IBlockSource src, ItemStack stack)
    {
    	int damageValue = stack.getItemDamage();
    	if (this.hasSpecialDispense(damageValue)){   	
	        World world = src.getWorld();
	        IPosition iposition = BlockDispenser.func_149939_a(src);
	        EnumFacing enumfacing = BlockDispenser.func_149937_b(src.getBlockMetadata());
	        IProjectile iprojectile = this.getProjectileEntity(world, iposition, damageValue);
	        iprojectile.setThrowableHeading((double)enumfacing.getFrontOffsetX(), (double)((float)enumfacing.getFrontOffsetY() + 0.1F), (double)enumfacing.getFrontOffsetZ(), 1.1F, 6.0F);
	        Entity e = (Entity)iprojectile;
	        world.spawnEntityInWorld(e);
	        this.postLaunchAction(world, e, damageValue);
	        stack.splitStack(1);
	        return stack;
    	} else {
    		return super.dispenseStack(src, stack);
    	}
    }

    /**
     * Play the dispense sound from the specified block.
     */

    
    protected boolean hasSpecialDispense(int damageValue){
    	return damageValue==TGItems.rocketAmmo.getItemDamage() || damageValue == TGItems._40mmGrenade.getItemDamage() || damageValue==TGItems.rocketAmmoNuclear.getItemDamage();
    }
    

    protected IProjectile getProjectileEntity(World world, IPosition pos, int damageValue)
    {
    	if(damageValue ==TGItems.rocketAmmo.getItemDamage()){
		   	RocketLauncher launcher = (RocketLauncher) TGuns.rocketlauncher;
		   	RocketProjectile rocket = new RocketProjectile(world, pos.getX(), pos.getY(), pos.getZ(), launcher.getDamage() , launcher.radius, 1.0f, 200, 0.3f, 0.01f, false, 0);
	        return rocket;
    	} else if (damageValue==TGItems._40mmGrenade.getItemDamage()){
    		GrenadeLauncher launcher = (GrenadeLauncher) TGuns.grenadeLauncher;
    		//GrenadeLauncherProjectile grenade = new GrenadeLauncherProjectile(world, pos.getX(), pos.getY(), pos.getZ(), launcher.getDamage(), launcher.radius, launcher.speed, 100 ,0.05f, launcher.gravity,false,0.0f,2);
    		GrenadeLauncherProjectile grenade = new GrenadeLauncherProjectile(world, pos.getX(), pos.getY(), pos.getZ(), launcher.getDamage(), launcher.radius, launcher.speed, 100, 0.05f, launcher.gravity, false, 0.0f, 2, pos.getX(), pos.getY(), pos.getZ());
    		grenade.setPosition(pos.getX(), pos.getY(), pos.getZ());
    		return grenade;
    	} else if (damageValue==TGItems.rocketAmmoNuclear.getItemDamage()){
    		NukeLauncher launcher = (NukeLauncher) TGuns.nukeLauncher;
		   	NukeRocketProjectile rocket = new NukeRocketProjectile(world, pos.getX(), pos.getY(), pos.getZ(), launcher.getDamage() , launcher.radius, 1.0f, 200, 0.3f, 0.01f, false, 0);
	        return rocket;
    	}
    	//should not happen
    	return null;
    }

    protected void postLaunchAction(World w, Entity e, int dmg){
    	if(dmg ==TGItems.rocketAmmo.getItemDamage()){
    		//w.playSound(e.posX, e.posY, e.posZ, "techguns:guns.rocketFire", 1.0F, 1.0F, true);
    	} else if (dmg==TGItems._40mmGrenade.getItemDamage()){
    		//w.playSound(e.posX, e.posY, e.posZ, "techguns:guns.grenadeLauncherFire", 1.0F, 1.0F, true);
			TGPackets.network.sendToAllAround(new PacketSpawnParticleOnEntity("GrenadeLauncherTrail", e), new TargetPoint(w.provider.dimensionId, e.posX, e.posY, e.posZ, 200.0f));
    	}
    }
    
	/*@Override
	protected void playDispenseSound(IBlockSource src) {
		if (damagevalue == TGItems.rocketAmmo.getItemDamage()){
			src.getWorld().playSound(src.getX(), src.getY(), src.getZ(), "techguns:guns.rocketFire", 1.0F, 1.0F, true);
		} else if (damagevalue == TGItems._40mmGrenade.getItemDamage()){
			src.getWorld().playSound(src.getX(), src.getY(), src.getZ(), "techguns:guns.grenadeLauncherFire", 1.0F, 1.0F, true);
		} else {
			super.playDispenseSound(src);
		}
	}*/

}
