package techguns.items.guns;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.Techguns;
import techguns.client.ClientProxy;
import techguns.entities.projectiles.GenericProjectile;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;

public class GenericGunCharge extends GenericGun {
	
	/**
	 * In ticks
	 */
	public float fullChargeTime=20.0f;
	public int ammoConsumedOnFullCharge=10;
	
	public GenericGunCharge() {
		super();
	}

	public GenericGunCharge(ItemStack ammo) {
		super(ammo);
	}

	public GenericGunCharge(String name, ItemStack ammo, boolean semiAuto, int minFiretime, int clipsize, int reloadtime, float damage, String firesound,
			String reloadsound, int TTL, float accuracy, float fullChargeTime) {
		super(name, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL, accuracy);
		this.fullChargeTime=fullChargeTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * techguns.items.guns.GenericGun#onItemRightClick(net.minecraft.item.ItemStack
	 * , net.minecraft.world.World, net.minecraft.entity.player.EntityPlayer)
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {

		TechgunsExtendedPlayerProperties extendedPlayer = TechgunsExtendedPlayerProperties.get(player);
		
		/**
		 * Get firetime for player (world time in ticks when last firing
		 * happened)
		 */
		/*String playername = player.getCommandSenderName();
		long firetime;

		if (world.isRemote) {
			// System.out.println(System.currentTimeMillis()+": fire");
			if (firetimes_client.containsKey(playername)) {
				firetime = firetimes_client.get(playername);
			} else {
				firetime = 0L;
			}
		} else {
			if (firetimes_server.containsKey(playername)) {
				firetime = firetimes_server.get(playername);
			} else {
				firetime = 0L;
			}
		}*/
		// System.out.println(Thread.currentThread().toString()+": firetime:"+firetime);

		/*
		 * Check if player wants to zoom
		 */
		if (canZoom && player.isSneaking() && this.toggleZoom) {
			if (world.isRemote) {
				ClientProxy cp = ClientProxy.get();
				if (cp.player_zoom != 1.0f) {
					cp.player_zoom = 1.0f;
				} else {
					cp.player_zoom = this.zoomMult;
				}
			}

		} else {
			/*
			 * Check for shoot / reload
			 */
			// System.out.println(Thread.currentThread().toString()+": Try shooting");

			//int dur = item.getItemDamage();
			int ammo = this.getCurrentAmmo(item);
			
			if (ammo > 0) {
				// bullets left

				//long delay = world.getWorldTime() - firetime;

				int firedelay = extendedPlayer.fireDelay;
				// System.out.println(Thread.currentThread().toString()+": delay:"+delay);

				if (firedelay<=0) {
					/*if (world.isRemote) {
						firetimes_client.put(playername, world.getWorldTime());
					} else {
						firetimes_server.put(playername, world.getWorldTime());
					}*/
					extendedPlayer.fireDelay=this.minFiretime;

					/*
					 * ArrowNockEvent event = new ArrowNockEvent(player, item);
					 * MinecraftForge.EVENT_BUS.post(event); if
					 * (event.isCanceled()) { return event.result; }
					 */
					player.setItemInUse(item, this.getMaxItemUseDuration(item));
					
					this.startCharge( item, world, player);
					/*
					 * if (!player.capabilities.isCreativeMode) {
					 * item.setItemDamage(dur+1); } if (!world.isRemote) {
					 * 
					 * //If SERVER, create projectile
					 * 
					 * spawnProjectile(world, player, item, accuracy); if
					 * (shotgun){ for (int i=0; i<bulletcount; i++) {
					 * spawnProjectile(world, player, item, spread); } }
					 * 
					 * if (maxLoopDelay > 0 && (world.getWorldTime() - firetime)
					 * > maxLoopDelay) { world.playSoundAtEntity(player,
					 * firesoundStart, 1.0F, 1.0F ); //ClientProxy.lastShotFired
					 * = world.getWorldTime(); }else {
					 * world.playSoundAtEntity(player, firesound, 1.0F, 1.0F );
					 * }
					 * 
					 * if (!rechamberSound.isEmpty()) {
					 * world.playSoundAtEntity(player, rechamberSound, 1.0F,
					 * 1.0F ); }
					 * 
					 * } else {
					 * 
					 * // If CLIENT, do Effects
					 * 
					 * long recoiltime_l =
					 * ((long)(((float)recoiltime/20.0f)*1000.0f)); long
					 * muzzleFlashtime_l =
					 * ((long)(((float)muzzleFlashtime/20.0f)*1000.0f));
					 * ClientProxy.player_recoiltime =
					 * System.currentTimeMillis() + recoiltime_l;
					 * ClientProxy.player_recoiltime_total = recoiltime_l;
					 * ClientProxy.player_muzzleFlashtime =
					 * System.currentTimeMillis() + muzzleFlashtime_l;
					 * ClientProxy.player_muzzleFlashtime_total =
					 * muzzleFlashtime_l;
					 * 
					 * client_weaponFired(); }
					 */

				} else {
					// System.out.println(Thread.currentThread().toString()+": Skip shot, can't fire yet");
				}

			} else {
				// mag empty, reload needed

				// look for ammo
				if (Techguns.consumeAmmoPlayer(player, this.ammo)) {

					
					if (emptyMag != null) {
						player.inventory.addItemStackToInventory(new ItemStack(emptyMag.getItem(), 1, emptyMag.getItemDamage()));
					}

					// stop toggle zooming when reloading
					if (world.isRemote) {
						if (canZoom && this.toggleZoom) {
							ClientProxy cp = ClientProxy.get();
							if (cp.player_zoom != 1.0f) {
								cp.player_zoom = 1.0f;
							}
						}
					}

					// START RELOAD
					/*if (world.isRemote) {
						firetimes_client.put(playername, world.getWorldTime() + reloadtime - this.minFiretime);
					} else {
						firetimes_server.put(playername, world.getWorldTime() + reloadtime - this.minFiretime);
					}*/
					extendedPlayer.fireDelay = this.reloadtime-this.minFiretime;
					// System.out.println(Thread.currentThread().toString()+": reloadtime:"+reloadtime);

					if (ammoCount > 1) {
						int i = 1;
						while (i < ammoCount && Techguns.consumeAmmoPlayer(player, this.ammo)) {
							i++;
						}

						//item.setItemDamage(ammoCount - i);
						this.reloadAmmo(item, i);
					} else {
						this.reloadAmmo(item);
						//item.setItemDamage(0);
					}

					world.playSoundAtEntity(player, reloadsound, 1.0F, 1.0F);

					if (world.isRemote) {
						int time = (int) (((float) reloadtime / 20.0f) * 1000);
					//	ClientProxy.setplayerReloadtime(System.currentTimeMillis() + ((long) (((float) reloadtime / 20.0f) * 1000)));
						//ClientProxy.setplayerReloadtimeTotal(((int) (((float) reloadtime / 20.0f) * 1000)));
						ClientProxy cp = ClientProxy.get();
						cp.setplayerReloadtime(player,System.currentTimeMillis()+time, time, (byte)1);
						
						client_startReload();
					}

				} else {
					// can't reload, no ammo left
					/*if (world.isRemote) {
						firetimes_client.put(playername, world.getWorldTime());
					} else {
						firetimes_server.put(playername, world.getWorldTime());
					}*/
					// System.out.println(Thread.currentThread().toString()+": Can't reload");

					if (!world.isRemote) {
						world.playSoundAtEntity(player, "mob.villager.idle", 1.0F, 1.0F);
					}
				}
			}
		}

		return item;
	}

	/**
	 * Override for onCharge code.
	 */
	protected void startCharge(ItemStack item, World world, EntityPlayer player) {}

	/**
	 * called when the player releases the use item button. Args: itemstack,
	 * world, entityplayer, itemInUseCount
	 */
	public void onPlayerStoppedUsing(ItemStack item, World world, EntityPlayer player, int itemInUseCount) {
		int j = this.getMaxItemUseDuration(item) - itemInUseCount;

		
		// System.out.println("itemInUseCount:"+itemInUseCount);

		/*
		 * ArrowLooseEvent event = new ArrowLooseEvent(player, item, j);
		 * MinecraftForge.EVENT_BUS.post(event); if (event.isCanceled()) {
		 * return; } j = event.charge;
		 */

		/*
		 * boolean flag = player.capabilities.isCreativeMode ||
		 * EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId,
		 * item) > 0;
		 * 
		 * if (flag || player.inventory.hasItem(Items.arrow)) {
		 */
		/*float f = (float) j / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;*/

		float f = j/this.fullChargeTime;
		
		/*
		 * if ((double) f < 0.1D) { return; }
		 */

		if (f > 1.0F) {
			f = 1.0F;
		}

		//int ammoConsumed = 0;
		//if (!player.capabilities.isCreativeMode) {
		int	ammoConsumed = this.consumeAmmoCharge(item, f,player.capabilities.isCreativeMode);
		//}
		if (!world.isRemote) {

			// If SERVER, create projectile

			spawnChargedProjectile(world, player, item, accuracy, f, ammoConsumed);
			if (shotgun) {
				for (int i = 0; i < bulletcount; i++) {
					spawnChargedProjectile(world, player, item, spread, f, ammoConsumed);
				}
			}

			world.playSoundAtEntity(player, firesound, 1.0F, 1.0F);

			if (!rechamberSound.isEmpty()) {
				world.playSoundAtEntity(player, rechamberSound, 1.0F, 1.0F);
			}

		} else {

			// If CLIENT, do Effects

			int recoiltime_l = getRecoilTime(f);
			long muzzleFlashtime_l = getMuzzleFlashTime(f);
			//ClientProxy.setplayerRecoiltime(System.currentTimeMillis() + recoiltime_l);
			//ClientProxy.setplayerRecoiltimeTotal(recoiltime_l);
			ClientProxy cp = ClientProxy.get();
			cp.setplayerRecoiltime(player,System.currentTimeMillis() + recoiltime_l, recoiltime_l, (byte)1);
			cp.player_muzzleFlashtime = System.currentTimeMillis() + muzzleFlashtime_l;
			cp.player_muzzleFlashtime_total = muzzleFlashtime_l;

			client_weaponFired();
		}
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#getMaxItemUseDuration(net.minecraft.item.ItemStack)
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 288000;
	}

	public int getRecoilTime(float charge) {
		return ((int) (((float) recoiltime / 20.0f) * 1000.0f));
	}

	public long getMuzzleFlashTime(float charge) {
		return ((long) (((float) muzzleFlashtime / 20.0f) * 1000.0f));
	}

	/**
	 * consume ammo from NBTTag
	 * @param item
	 * @param f charge amount
	 * @return ammount of ammo actually consumed
	 */
	public int consumeAmmoCharge(ItemStack item, float f, boolean creative) {
		if(!creative){
			this.useAmmo(item, 1);
		}
		return 1;
	}

	public void spawnChargedProjectile(final World world, final EntityLivingBase player, ItemStack itemStack, float spread, float charge, int ammoConsumed) {
		world.spawnEntityInWorld(new GenericProjectile(world, player, damage, speed, this.ticksToLive, spread, this.damageDropStart, this.damageDropEnd,
				this.damageMin, this.penetration, getDoBlockDamage(player)));
	}

}
