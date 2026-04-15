package techguns.items.guns;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.client.ClientProxy;
import techguns.damagesystem.DamageType;
import techguns.entities.projectiles.RocketProjectile;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;

public class RocketLauncher extends GenericGunRadiusGrav{
	
	//private float radius = 1.75f;
	//private float gravity = 0.01f;
	
	
	public RocketLauncher(String name, ItemStack ammo, boolean semiAuto, int minFiretime,
			int clipsize, int reloadtime, float damage, String firesound,
			String reloadsound, int TTL, float accuracy) {
		super(name, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage,
				firesound, reloadsound, TTL, accuracy);
		radius=1.75f;
		gravity=0.01f;
	}
	
	/*@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
		EntityPlayer par3Player) {
		
		if (par3Player.isSneaking()) {
			if (!par1ItemStack.hasTagCompound()) {
				par1ItemStack.setTagCompound(new NBTTagCompound());
			}
			boolean blockdamage = par1ItemStack.getTagCompound().hasKey("blockdamage") && par1ItemStack.getTagCompound().getBoolean("blockdamage");
			blockdamage = !blockdamage;
			par1ItemStack.getTagCompound().setBoolean("blockdamage", blockdamage);
			
			if (par2World.isRemote) {
				if (!blockdamage) {
					par3Player.addChatMessage(new ChatComponentText("Block Damage OFF"));
				}else {
					par3Player.addChatMessage(new ChatComponentText("Block Damage ON"));
				}
			}
			
			return par1ItemStack;
		} else {	
			return super.onItemRightClick(par1ItemStack, par2World, par3Player);
		}
	}*/

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
		world.spawnEntityInWorld(new RocketProjectile(world, player, damage*damagebonus, radius, 1.0f, this.ticksToLive, spread, gravity, blockdamage, this.penetration));
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
