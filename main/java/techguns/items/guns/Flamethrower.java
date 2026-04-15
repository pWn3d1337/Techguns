
package techguns.items.guns;

	import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.damagesystem.DamageType;
import techguns.entities.projectiles.FlamethrowerProjectile;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;

public class Flamethrower  extends GenericGun{
	

		public Flamethrower() {
			super();
		}

		public Flamethrower(ItemStack ammo) {
			super(ammo);
		}

		public Flamethrower(String name, ItemStack ammo, boolean semiAuto, int minFiretime,
				int clipsize, int reloadtime, float damage, String firesound,
				String reloadsound, int TTL, float accuracy) {
			super(name, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage,
					firesound, reloadsound, TTL, accuracy);
		}
		
		/*@Override
		public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Player) {
			
			if (par3Player.isSneaking()) {
				if (!par1ItemStack.hasTagCompound()) {
					par1ItemStack.setTagCompound(new NBTTagCompound());
				}
				boolean ignite = par1ItemStack.getTagCompound().hasKey("ignite") && par1ItemStack.getTagCompound().getBoolean("ignite");
				ignite = !ignite;
				par1ItemStack.getTagCompound().setBoolean("ignite", ignite);
				
				if (par2World.isRemote) {
					if (!ignite) {
						par3Player.addChatMessage(new ChatComponentText("ignite OFF"));
					}else {
						par3Player.addChatMessage(new ChatComponentText("ignite ON"));
					}
				}
				
				return par1ItemStack;
			} else {	
				return super.onItemRightClick(par1ItemStack, par2World, par3Player);
			}
		}*/
//		
//		@Override
//		public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2Player,
//			World par3World, int p_77648_4_, int p_77648_5_, int p_77648_6_,
//			int p_77648_7_, float p_77648_8_, float p_77648_9_,
//			float p_77648_10_) {
//		// TODO Auto-generated method stub
//		//return super.onItemUse(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_,p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
//			par2Player.setItemInUse(par1ItemStack, 10000);
//			par2Player.setVelocity(1, 1, 1);
//			return true;
//		}
//		
//		@Override
//		public void onPlayerStoppedUsing(ItemStack p_item, World p_world,
//			EntityPlayer p_player, int p_itemInUseCount) {
//		// TODO Auto-generated method stub
//		//super.onPlayerStoppedUsing(p_item, p_world, p_player, p_itemInUseCount);
//			System.out.println("Stopped Using");
//		}
//		
//		@Override
//		public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
//		// TODO Auto-generated method stub
//		//super.onUsingTick(stack, player, count);
//		System.out.println("usingTick");
//		}

		@Override
		protected void spawnProjectile (final World world, final EntityLivingBase player, ItemStack itemStack, float spread, float offset, float damagebonus, boolean leftGun) {
			boolean ignite = false;
			/*if (itemStack != null){
				ignite = (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("ignite") && itemStack.getTagCompound().getBoolean("ignite"));
			}*/
			if (player instanceof EntityPlayer){
				TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get((EntityPlayer) player);
				if(props!=null){
					ignite=!props.enableSafemode;
				}
			}
			
			//System.out.println("ignite = "+ignite);
			world.spawnEntityInWorld(new FlamethrowerProjectile(world, player, damage*damagebonus, 1.0f, this.ticksToLive, 0.01f, spread, false, ignite, damageDropStart, damageDropEnd, damageMin*damagebonus,penetration));
		}
		

		@Override
		public DamageType getDamageType() {
			return DamageType.FIRE;
		}
	}
