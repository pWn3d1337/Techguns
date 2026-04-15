package techguns.items.guns;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import techguns.entities.projectiles.FlashBangProjectile;

public class Flashbang extends GenericGrenade {

	public Flashbang(String name, int stacksize, int maxUseDur) {
		super(name, stacksize, maxUseDur);
	}

	@Override
	public void spawnGrenade(World world, EntityPlayer player, float charge) {
		float grav = (0.015f/charge);
    	world.spawnEntityInWorld(new FlashBangProjectile(world, player, 20,5.0f,0.5f+0.5f*charge,200,0.05f,grav,false,0.0f,0));
    	world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);
	}
	

}
