package techguns.items.guns;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.entities.projectiles.FragGrenadeProjectile;

public class FragGrenade extends GenericGrenade {

	public FragGrenade(String name, int stacksize, int maxUseDur) {
		super(name, stacksize, maxUseDur);
		this.maxBounces=2;
		this.damage=25;
		this.radius=6;
	}

	@Override
	public void spawnGrenade(World world, EntityPlayer player, float charge) {
    	float grav = (0.015f/charge);
    	world.spawnEntityInWorld(new FragGrenadeProjectile(world, player, this.damage,this.radius,0.5f+0.5f*charge,120,0.05f,grav,false,0.0f,this.maxBounces));
    	world.playSoundAtEntity(player, "techguns:guns.grenade_throw", 1.0F, 1.0f);//1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);
	}
	
	/**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
	@Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
        player.setItemInUse(item, this.getMaxItemUseDuration(item));
        world.playSoundAtEntity(player, "techguns:guns.grenade_pin", 1.0F, 1.0f);
        return item;
    }
	
}
