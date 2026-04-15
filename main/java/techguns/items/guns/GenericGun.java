package techguns.items.guns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import techguns.TGArmorBonus;
import techguns.TGItems;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.client.ClientProxy;
import techguns.client.audio.TGSoundCategory;
import techguns.damagesystem.DamageType;
import techguns.damagesystem.TGDamageSource;
import techguns.entities.ai.EntityAIRangedAttack;
import techguns.entities.npc.NPCTurret;
import techguns.entities.projectiles.GenericProjectile;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.items.GenericItem;
import techguns.items.armors.GenericArmor;
import techguns.items.armors.ICamoChangeable;
import techguns.packets.GunFiredMessage;
import techguns.packets.ReloadStartedMessage;
import techguns.util.EntityDeathUtils.DeathType;
import techguns.util.ItemUtil;
import techguns.util.SoundUtil;
import techguns.util.TextUtil;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class GenericGun extends GenericItem implements ICamoChangeable{
	protected static final float SOUND_DISTANCE=4.0f;

	//@SideOnly(value=Side.SERVER)
	//public static HashMap<String, Long> firetimes_server = new HashMap<String, Long>();
	
	//@SideOnly(value=Side.CLIENT)
	//public static HashMap<String, Long> firetimes_client = new HashMap<String, Long>();
	
	public double[] gunStats;
	
	
	public HashMap<String, Integer> mininglevels;
	
	boolean shootWithLeftClick=true;
	
	//Weapon Stats
	boolean semiAuto; // = false;
	int minFiretime; // = 0;
	int clipsize; // = 10;
	int reloadtime; // = 40; //ticks
	float damage; // = 10;
	String firesound = "techguns:guns.assaultRifleFire";
	String reloadsound = "techguns:guns.assaultRifleReload";
	String firesoundStart = "techguns:guns.assaultRifleFire";
	String rechamberSound = "";
	int ammoCount; //ammo per reload
	//float recoil = 25.0f;
	float zoomMult =1.0f;
	boolean canZoom = false;
	boolean toggleZoom = false;
	int ticksToLive=10;
	float speed=1.5f;
	
	float damageMin;
	int damageDropStart;
	int damageDropEnd;
	float penetration=0.0f;
	
	ItemStack ammo;
	ItemStack emptyMag;
	ItemStack bullet;
	
	boolean shotgun=false;
	boolean burst=false;
	float spread=1.0f;
	int bulletcount=7;
	float accuracy=0.0f;
	
	int maxLoopDelay = 0;
	int recoiltime = 5; //ticks;
	
	int muzzleFlashtime = 5;
	boolean silenced=false;
	
	private Class projectileClass;
	
	boolean checkRecoil=false;
	
	/**
	 * spread multiplier while shooting zoomed
	 */
	float zoombonus=1.0f;
	//coding stuff
	//protected boolean canFire = true;

	float AI_attackRange = 15.0F; //sqrt of actual distance
	int AI_attackTime = 60;
	int AI_burstCount = 0;
	int AI_burstAttackTime = 0;
	
	int camoCount=1;
	
	public float turretPosOffsetX=0.0f;
	public float turretPosOffsetY=0.0f;
	public float turretPosOffsetZ=0.0f;
	
	public float muzzleFlashOffsetX=0.0f;
	public float muzzleFlashOffsetY=0.0f;
	public float muzzleFlashOffsetZ=0.0f;

	public float digSpeed=1.0f;
	
	/**
	* additional height difference for turret
	* when weapon is held below hand etc.
	*/
	public float muzzleFlashOffsetTurretY=0.0f;
	
	public float muzzleFlashOffsetTurretX=0.0f;
	public float muzzleFlashOffsetTurretZ=0.0f;
	
	private static String[] muzzleflash={null,"MuzzleFlashYellow","MuzzleFlashExp","MuzzleFlashBlue","MuzzleFlashLaser","MuzzleFlashAlienBlue"};
	private byte muzzleflashIndex=0;
	
	protected int meleeHitAmmoConsumption=1;
	protected int miningAmmoConsumption=1;

	//Ammount of bullets the gun gets per bullet item
	protected float shotsPerBullet;

	//@SideOnly(Side.CLIENT)
	public ArrayList<ResourceLocation> textures = new ArrayList<ResourceLocation>();
	

	private static ArrayList<GenericGun> guns = new ArrayList<GenericGun>();
	
	protected boolean akimbo=false;

	protected boolean doubleShot=false;;
	
	public GenericGun() {
		// Constructor Configuration
		setMaxStackSize(1);
		setNoRepair();
		setMaxDamage(clipsize);
		setCreativeTab(Techguns.tabTechgun);
		setUnlocalizedName("testGun");
		setTextureName("techguns:testGun");
		
		semiAuto=false;
		minFiretime =0;
		clipsize=10;
		shotsPerBullet=clipsize;
		reloadtime=40;
		damage=10.0f;
		ammoCount =1;
		ticksToLive=40;
		
		damageDropStart=40;
		damageDropEnd=40;
		damageMin=10.0f;
		
		guns.add(this);
	}
	
	public GenericGun(ItemStack ammo) {
		this();
		this.ammo = ammo;
	}
	
	/**
	 * Set Akimbo to true and doubleShot to passed argument!!!
	 * @param doubleShot
	 * @return
	 */
	public GenericGun setAkimbo(boolean doubleShot){
		this.akimbo=true;
		this.ammo = TGItems.newStack(this.ammo, this.ammo.stackSize*2);
		this.doubleShot=doubleShot;
		return this;
	}
	
	public boolean isAkimbo() {
		return akimbo;
	}

	/**
	 * @param name
	 * @param texture
	 * @param ammo
	 * @param semiAuto
	 * @param minFiretime
	 * @param clipsize
	 * @param reloadtime
	 * @param damage
	 */
	public GenericGun(String name, ItemStack ammo, boolean semiAuto, int minFiretime, int clipsize, int reloadtime, float damage, String firesound, String reloadsound, int TTL, float accuracy){
		setMaxStackSize(1);
		setNoRepair();
		setMaxDamage(clipsize);
		setCreativeTab(Techguns.tabTechgun);
		setUnlocalizedName(name);
		
		this.ammo = ammo;
		this.semiAuto = semiAuto;
		this.minFiretime = minFiretime;
		this.clipsize = clipsize;
		this.reloadtime = reloadtime;
		this.damage = damage;
		this.firesound = firesound;
		this.reloadsound = reloadsound;
		this.emptyMag=null;
		this.ammoCount=1;
		this.shotsPerBullet=clipsize;
		this.ticksToLive=TTL;
		this.accuracy =accuracy;
		
		//Defaults to no drop
		this.damageDropStart=TTL;
		this.damageDropEnd=TTL;
		this.damageMin=damage;
		
		guns.add(this);
	}
	
	public void initGunStats(){
		this.gunStats = new double[7];
		
		this.gunStats[0]=this.damage;
		this.gunStats[1]=this.damageMin;
		this.gunStats[2]=this.damageDropStart;
		this.gunStats[3]=this.damageDropEnd;
		this.gunStats[4]=this.ticksToLive;
		this.gunStats[5]=this.accuracy;
		this.gunStats[6]=this.penetration;
	}
	
	public void applyGunStats(double[] stats){
		this.gunStats=stats;	
		
		this.damage=(float) this.gunStats[0];
		this.damageMin=(float) this.gunStats[1];
		this.damageDropStart=(int) this.gunStats[2];
		this.damageDropEnd=(int) this.gunStats[3];
		this.ticksToLive=(int) this.gunStats[4];
		this.accuracy=(float) this.gunStats[5];
		this.penetration=(float) this.gunStats[6];
	}
	
	
	
/*	public static void loadStatsFromFileForAllGuns(){
		for (int i=0; i< guns.size(); i++){
			guns.get(i).loadStatsFromFile();
		}
		//guns = null;
	}*/
	
	/**
	 * Load the gun stats from file and overwrite existing values if file exists, if file does not exist, write file with default values.
	 * @param name
	 * @param sharedItem
	 * @param firesound
	 * @param reloadsound
	 * @return
	 */
/*	public void loadStatsFromFile() {
		Properties props = new Properties();
		String path = System.getProperty("file.separator","/")+"config"+System.getProperty("file.separator","/")+Techguns.MODID+System.getProperty("file.separator","/")+this.getUnlocalizedName()+".txt";
		FileInputStream in;
		try {
			System.out.println(path);
			in = new FileInputStream(path);
			props.load(in);
			in.close();
			
			//this.semiAuto = Boolean.parseBoolean(props.getProperty("semiAuto"));
			this.minFiretime = Integer.parseInt(props.getProperty("minFiretime"));
			this.reloadtime = Integer.parseInt(props.getProperty("reloadtime"));
			this.clipsize = Integer.parseInt(props.getProperty("clipsize"));
			this.damage = Float.parseFloat(props.getProperty("damage"));
			this.damageMin=Float.parseFloat(props.getProperty("damageMin"));
			this.damageDropStart=Integer.parseInt(props.getProperty("damageDropStart"));
			this.damageDropEnd=Integer.parseInt(props.getProperty("damageDropEnd"));
			this.ticksToLive = Integer.parseInt(props.getProperty("ticksToLive"));
			this.accuracy = Float.parseFloat(props.getProperty("accuracy"));
			this.penetration = Float.parseFloat(props.getProperty("penetration"));
			
		} catch (FileNotFoundException e) {
			//if file does not exist, write it
			
			//props.setProperty("semiAuto", this.semiAuto ? "true" : "false");
			props.setProperty("minFiretime", ""+this.minFiretime);
			props.setProperty("reloadtime", ""+this.reloadtime);
			props.setProperty("clipsize", ""+this.clipsize);
			props.setProperty("damage", ""+this.damage);
			props.setProperty("damageMin", ""+this.damageMin);
			props.setProperty("damageDropStart", ""+this.damageDropStart);
			props.setProperty("damageDropEnd", ""+this.damageDropEnd);
			props.setProperty("ticksToLive", ""+this.ticksToLive);
			props.setProperty("accuracy", ""+this.accuracy);
			props.setProperty("penetration", ""+this.penetration);
			
			FileOutputStream out;
			try {
				out = new FileOutputStream(path);
				props.store(out, "Weaponstats for"+this.getUnlocalizedName());
				out.close();
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				System.out.println(e1.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println(e1.getMessage());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}*/
	
	
	public double[] getGunStats() {
		return gunStats;
	}

	/**
	 * Get Damagetype for Tooltip, override in subclasses with different damagetypes
	 * @return
	 */
	public DamageType getDamageType(){
		return DamageType.PROJECTILE;
	}
	
	public GenericGun setDigSpeed(float speed){
		this.digSpeed=speed;
		return this;
	}
	
	 /**
	 * @param emptyMag the emptyMag to set
	 */
	public GenericGun setEmptyMag(ItemStack emptyMag, ItemStack bullet, int bulletsPerMag) {
		this.emptyMag = emptyMag;
		this.bullet = bullet;
		this.shotsPerBullet = (this.clipsize*1.0f)/(bulletsPerMag*1.0f);
		return this;
	}
	public GenericGun setAmmoCount(int count) {
		this.ammoCount=count;
		this.shotsPerBullet=(this.clipsize*1.0f)/(this.ammoCount*1.0f);
		return this;
	}

	public GenericGun setZoom(float mult, boolean toggle, float bonus){
		this.canZoom=true;
		this.zoomMult = mult;
		this.toggleZoom = toggle;
		this.zoombonus=bonus;
		return this;
	}
	
	
	public GenericGun setShotgunSpred(int count, float spread){
		return this.setShotgunSpread(count, spread, false);
	}
	
	public GenericGun setShotgunSpread(int count, float spread, boolean burst){
		this.shotgun=true;
		this.spread=spread;
		this.bulletcount=count;
		this.burst=burst;
		return this;
	}
	
	public GenericGun setPenetration(float pen){
		this.penetration = pen;
		return this;
	}
	
	public GenericGun setMuzzleFlashTime(int time) {
		this.muzzleFlashtime = time;
		return this;
	}
	
	@Deprecated
	public GenericGun setProjectileClass(Class projectileClass) {
		this.projectileClass = projectileClass;
		return this;
	}
	
	public GenericGun setFiresoundStart(String firesoundStart) {
		this.firesoundStart = firesoundStart;
		return this;
	}
	
	public GenericGun setMaxLoopDelay(int maxLoopDelay) {
		this.maxLoopDelay = maxLoopDelay;
		return this;
	}
	
	public GenericGun setRecoiltime(int recoiltime){
		this.recoiltime = recoiltime;
		return this;
	}
	
	public GenericGun setRechamberSound(String rechamberSound) {
		this.rechamberSound = rechamberSound;
		return this;
	}
	
	public boolean hasZoom() {
		return this.canZoom;
	}
	
	public boolean isZooming(){
		return ClientProxy.get().player_zoom==this.zoomMult;
	}
	
	public boolean isHoldZoom(){
		return (this.toggleZoom==false)&&this.hasZoom();
	}
	
	public GenericGun setCheckRecoil(){
		this.checkRecoil=true;
		return this;
	}
	
	/**
	 * @return the zoomMult
	 */
	public float getZoomMult() {
		return zoomMult;
	}

	/**
	 * Multiply base bullet speed with speedMult
	 * @param speedMult
	 * @return
	 */
	public GenericGun setSpeed(float speedMult){
		this.speed=this.speed*speedMult;
		return this;
	}
	
	public GenericGun setDamageDrop(int start, int end, float minDamage){
		this.damageDropStart=start;
		this.damageDropEnd=end;
		this.damageMin=minDamage;
		return this;
	}
	
	public GenericGun setSilenced(boolean s){
		this.silenced=s;
		return this;
	}
	
	/**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack p_item, World p_world, EntityPlayer p_player, int p_itemInUseCount) {
    	//System.out.println("stoppedUsing");
    	/*if (semiAuto) {
    		canFire = true;
    		//System.out.println("Can fire again");
    	}*/
    }

	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#onItemRightClick(net.minecraft.item.ItemStack, net.minecraft.world.World, net.minecraft.entity.player.EntityPlayer)
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world,
			EntityPlayer player) {
		
		if (canZoom  && this.toggleZoom) {
    		if (world.isRemote) {
				ClientProxy cp = ClientProxy.get();
    			if (cp.player_zoom != 1.0f) {
    				cp.player_zoom= 1.0f;
    			} else {
    				cp.player_zoom = this.zoomMult;
    			}
    		}
    		
    	}
		
		return super.onItemRightClick(item, world, player);
	}
	
	public void shootGunLeftClick(ItemStack item, World world,
			EntityPlayer player, boolean isZooming){
		TechgunsExtendedPlayerProperties extendedPlayer = TechgunsExtendedPlayerProperties.get(player);
		
		//System.out.println("shootGunLeftClick for"+player.getCommandSenderName());
		
		/**
		 * Get firetime for player (world time in ticks when last firing happened)
		 */
    	/*String playername = player.getGameProfile().getName();
    	long firetime;
    	
    	if (world.isRemote){
    		//System.out.println(System.currentTimeMillis()+": fire");
	    	if (firetimes_client.containsKey(playername)) {
	    		firetime= firetimes_client.get(playername);
	    	} else {
	    		firetime = 0L;
	    	}
    	} else {
    		if (firetimes_server.containsKey(playername)) {
	    		firetime= firetimes_server.get(playername);
	    	} else {
	    		firetime = 0L;
	    	}
    	}-*/
    	//System.out.println(Thread.currentThread().toString()+": firetime:"+firetime);
    	
    	
    	/*
    	 * Check if player wants to zoom
    	 */
		/*if (canZoom && player.isSneaking() && this.toggleZoom) {
    		if (world.isRemote) {
    			if (ClientProxy.player_zoom != 1.0f) {
    				ClientProxy.player_zoom= 1.0f;
    			} else {
    				ClientProxy.player_zoom = this.zoomMult;
    			}
    		}
    	*/

    		/*
    		 * Check for shoot / reload
    		 */
    		//System.out.println(Thread.currentThread().toString()+": Try shooting");
    		int ammo = this.getCurrentAmmo(item);
    	
    		byte ATTACK_TYPE = 0;
    		if ( this.akimbo){
    			if (!this.doubleShot){
    				ATTACK_TYPE = (byte) (ammo % 2);
    			}
    		}
    		
    		if (ammo>0) {
    			//bullets left
    			
    			//long delay = world.getWorldTime()-firetime;
		    	int firedelay = extendedPlayer.fireDelay;
    			
    			//System.out.println(Thread.currentThread().toString()+": delay:"+delay);
    			
		    	if (firedelay <=0) {
		    		/*if (world.isRemote){
		    			firetimes_client.put(playername,world.getWorldTime());
		    		} else {
		    			firetimes_server.put(playername,world.getWorldTime());
		    		}*/
		    		extendedPlayer.fireDelay=this.minFiretime;
		    
		    		if (!player.capabilities.isCreativeMode) {
		    			if (!this.doubleShot){
		    				this.useAmmo(item, 1);
		    			} else {
		    				this.useAmmo(item, 2);
		    			}
		    		}
		    		if (this.isAkimbo() && player.capabilities.isCreativeMode && !this.doubleShot) {
		    			if (this.getCurrentAmmo(item)%2 == 0){
		    				this.useAmmo(item, 1);
		    			} else {
		    				this.reloadAmmo(item, 1);
		    			}
		    		}
			        if (!world.isRemote)
			        {
			        	/*
			        	 * If SERVER, create projectile
			        	 */
			        	
			        	float accuracybonus = 1.0f-GenericArmor.getArmorBonusForPlayer(player, TGArmorBonus.GUN_ACCURACY,false);
			        	
			        	if(isZooming){
			        		accuracybonus*=this.zoombonus;
			        	}
			        	this.shootGun(world, player,item, accuracybonus,1.0f,ATTACK_TYPE);
			        	        	
			        } else {
			        	/*
			        	 * If CLIENT, do Effects
			        	 */	
			          	int recoiltime_l = (int) ((((float)recoiltime/20.0f)*1000.0f));
			        	long muzzleFlashtime_l = ((long)(((float)muzzleFlashtime/20.0f)*1000.0f));
			        	//ClientProxy.setplayerRecoiltime(System.currentTimeMillis() + recoiltime_l);
			        	//ClientProxy.setplayerRecoiltimeTotal(recoiltime_l);
			        	ClientProxy cp = ClientProxy.get();
			        	//System.out.println("CheckRecoil:"+checkRecoil);
			        	if (!checkRecoil || !cp.isStillRecoiling(player, ATTACK_TYPE) ){
			        		//System.out.println("SettingRecoilTime");
				        	cp.setplayerRecoiltime(player, System.currentTimeMillis() + recoiltime_l, recoiltime_l,ATTACK_TYPE);
			        	}
			        	cp.player_muzzleFlashtime = System.currentTimeMillis() + muzzleFlashtime_l;
			        	cp.player_muzzleFlashtime_total = muzzleFlashtime_l;
			        	
				        client_weaponFired();
			        }
			        /*
			         * Do sounds on Client & Server
			         */
			        //System.out.println("Maxloopdelay:"+maxLoopDelay+"   LoopSoundDelay:"+extendedPlayer.loopSoundDelay);
		        	if (maxLoopDelay > 0 && extendedPlayer.loopSoundDelay<=0) {
		        		//System.out.println("Play loop sound");
		        		//world.playSoundAtEntity(player, firesoundStart, 1.0F, 1.0F );
		        		SoundUtil.playSoundOnEntityGunPosition(world, player, firesoundStart, SOUND_DISTANCE, 1.0F, false, false, TGSoundCategory.GUN_FIRE);
		        		//ClientProxy.lastShotFired = world.getWorldTime();
		        		extendedPlayer.loopSoundDelay=this.maxLoopDelay;
		        	}else {
		        		//world.playSoundAtEntity(player, firesound, SOUND_DISTANCE, 1.0F ); 
		        		//System.out.println("Play nonloop sound");
		        		SoundUtil.playSoundOnEntityGunPosition(world, player, firesound, SOUND_DISTANCE, 1.0F, false, false, TGSoundCategory.GUN_FIRE);
		        		if (this.maxLoopDelay>0){
		        			extendedPlayer.loopSoundDelay=this.maxLoopDelay;
		        		}
		        	}
		        	
		        	if (!rechamberSound.isEmpty()) {
		        		//world.playSoundAtEntity(player, rechamberSound, 1.0F, 1.0F ); 
		        		SoundUtil.playSoundOnEntityGunPosition(world, player, rechamberSound, 1.0F, 1.0F, false, false, TGSoundCategory.RELOAD);
		        	}
			        
		    	} else {
		    		//System.out.println(Thread.currentThread().toString()+": Skip shot, can't fire yet");
		    	}
		    	
    		} else {
    			//mag empty, reload needed
    			
    			//look for ammo
    			if (Techguns.consumeAmmoPlayer(player,this.ammo)) {
    			
    				if (emptyMag!=null){
    					//player.inventory.addItemStackToInventory(new ItemStack(emptyMag.getItem(),1,emptyMag.getItemDamage()));
    					int amount=Techguns.addAmmoToPlayerInventory(player, TGItems.newStack(emptyMag, this.akimbo?2:1));
    					if(amount >0 && !world.isRemote){
    						player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, TGItems.newStack(emptyMag, amount)));
    					}
    				}
    				
    				//stop toggle zooming when reloading
    				if (world.isRemote) {
    					if (canZoom  && this.toggleZoom) {
    						ClientProxy cp = ClientProxy.get();
    		    			if (cp.player_zoom != 1.0f) {
    		    				cp.player_zoom= 1.0f;
    		    			}
    		    		}
    				}
    				
	    			//START RELOAD
    				/*if (world.isRemote){
    					firetimes_client.put(playername, world.getWorldTime()+reloadtime-this.minFiretime);
    				} else {
    					firetimes_server.put(playername, world.getWorldTime()+reloadtime-this.minFiretime);
    				}*/
    				extendedPlayer.fireDelay = this.reloadtime-this.minFiretime;
	    			//System.out.println(Thread.currentThread().toString()+": reloadtime:"+reloadtime);
    						    			
	    			if (ammoCount >1) {
	    				int i =1;
	    				while (i<ammoCount && Techguns.consumeAmmoPlayer(player,this.ammo)){
	    					i++;
	    				}
	    				
	    				//item.setItemDamage(ammoCount-i);
	    				this.reloadAmmo(item, i);
	    			} else {
	    				this.reloadAmmo(item);
	    			}
	    			
	    			SoundUtil.playReloadSoundOnEntity(world,player,reloadsound, 1.0F, 1.0F, false, true, TGSoundCategory.RELOAD);

	    			
    				if (world.isRemote) {
    					//ClientProxy.setplayerReloadtime(System.currentTimeMillis() + ((long)(((float)reloadtime/20.0f)*1000)));
    					//ClientProxy.setplayerReloadtimeTotal(((int)(((float)reloadtime/20.0f)*1000)));
    					
    					int time = (int) (((float)reloadtime/20.0f)*1000);
    					ClientProxy.get().setplayerReloadtime(player,System.currentTimeMillis()+time, time, ATTACK_TYPE);
    					
    					client_startReload();
    				} else{
    					//send reloadpacket
    					//send pakets to clients
    					
				    	int msg_reloadtime = ((int)(((float)reloadtime/20.0f)*1000.0f));
				    	TGPackets.network.sendToAllAround(new ReloadStartedMessage(player,msg_reloadtime,ATTACK_TYPE), new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 100.0f));
    			    	//
    				}

    			} else {
    				//can't reload, no ammo left
    				/*if(world.isRemote){
    					firetimes_client.put(playername, world.getWorldTime());
    				} else {
    					firetimes_server.put(playername, world.getWorldTime());
    				}*/
    				//System.out.println(Thread.currentThread().toString()+": Can't reload");
    				
    				if (!world.isRemote)
			        {
	    				world.playSoundAtEntity(player, "mob.villager.idle", 1.0F, 1.0F );
			        }
    			}
    		}
    	
		
		//return item;
	}

	/**
	 * try to force reload the gun, might lose some ammo
	 */
	public void tryForcedReload(ItemStack item, World world,EntityPlayer player){
		TechgunsExtendedPlayerProperties extendedPlayer = TechgunsExtendedPlayerProperties.get(player);
		
		if (extendedPlayer.fireDelay<=0 && !this.isFullyLoaded(item)){
			
			int oldAmmo = this.getCurrentAmmo(item);
			
			//look for ammo
			if (Techguns.consumeAmmoPlayer(player,this.ammo)) {
			
				//empty gun and do reload if we can't put in ammo individual
				if (ammoCount <= 1){
					this.useAmmo(item, oldAmmo);
				}
				
				if (emptyMag!=null){
					//player.inventory.addItemStackToInventory(new ItemStack(emptyMag.getItem(),1,emptyMag.getItemDamage()));
					int amount=Techguns.addAmmoToPlayerInventory(player, TGItems.newStack(emptyMag, this.akimbo?2:1));
					if(amount >0 && !world.isRemote){
						player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, TGItems.newStack(emptyMag, amount)));
					}
					
					int bulletsBack = (int) Math.floor(oldAmmo/this.shotsPerBullet);
					if (bulletsBack>0){
						int amount2=Techguns.addAmmoToPlayerInventory(player, TGItems.newStack(bullet, bulletsBack));
						if(amount2 >0 && !world.isRemote){
							player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, TGItems.newStack(bullet, amount2)));
						}
					}
					
				}
				
				//stop toggle zooming when reloading
				if (world.isRemote) {
					if (canZoom  && this.toggleZoom) {
						ClientProxy cp = ClientProxy.get();
		    			if (cp.player_zoom != 1.0f) {
		    				cp.player_zoom= 1.0f;
		    			}
		    		}
				}
				
				extendedPlayer.fireDelay = this.reloadtime-this.minFiretime;
    			//System.out.println(Thread.currentThread().toString()+": reloadtime:"+reloadtime);
						    			
    			if (ammoCount >1) {
    				int i =1;
    				while (i<(ammoCount-oldAmmo) && Techguns.consumeAmmoPlayer(player,this.ammo)){
    					i++;
    				}
    				
    				//item.setItemDamage(ammoCount-i);
    				this.reloadAmmo(item, i);
    			} else {
    				this.reloadAmmo(item);
    			}
    			SoundUtil.playReloadSoundOnEntity(world,player,reloadsound, 1.0F, 1.0F, false, true, TGSoundCategory.RELOAD);

				if (world.isRemote) {

					int time = (int) (((float)reloadtime/20.0f)*1000);
					ClientProxy.get().setplayerReloadtime(player,System.currentTimeMillis()+time, time, (byte)0);
					
					client_startReload();
				} else{
					//send reloadpacket
					//send pakets to clients
					
			    	int msg_reloadtime = ((int)(((float)reloadtime/20.0f)*1000.0f));
			    	TGPackets.network.sendToAllAround(new ReloadStartedMessage(player,msg_reloadtime,0), new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 100.0f));
			    	//
				}

			} else {

				if (!world.isRemote)
		        {
    				world.playSoundAtEntity(player, "mob.villager.idle", 1.0F, 1.0F );
		        }
			}

			
		}
		
	}
	
	
	public float getDamage() {
		return damage;
	}

	protected void shootGun(World world, EntityLivingBase player,ItemStack itemstack,float accuracybonus,float damagebonus, int attackType){
		
		//send pakets to clients
		if (!world.isRemote){
	    	int msg_recoiltime = ((int)(((float)recoiltime/20.0f)*1000.0f));
	    	TGPackets.network.sendToAllAround(new GunFiredMessage(player,msg_recoiltime,attackType,checkRecoil,this.akimbo,this.doubleShot), new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 100.0f));
		}
    	//
		boolean leftGun = this.akimbo && attackType==1;
		spawnProjectile(world, player,itemstack, accuracy*accuracybonus,0,damagebonus, leftGun);
		if(this.akimbo&&this.doubleShot){
			spawnProjectile(world, player,itemstack, accuracy*accuracybonus,0,damagebonus, true);
		}
        if (shotgun){
        	float offset=0;
        	if (this.burst){
        		offset = 1.0f/(this.bulletcount);
        	}
        	
        	for (int i=0; i<bulletcount; i++) {
        		spawnProjectile(world, player,itemstack, spread*accuracybonus,offset*(i+1.0f),damagebonus, leftGun);
        	}
        }		
	}
	
	/**
	 * for extra actions in subclass
	 */
	protected void client_startReload() {	
	}

	/**
	 * for extra actions in subclass
	 */
	protected void client_weaponFired() {	
	}

//    private void causeViewKick(EntityPlayer par3Player) {
//		System.out.println("recoil");
//		par3Player.setPositionAndRotation(par3Player.posX, par3Player.posY, par3Player.posZ, par3Player.cameraPitch+recoil, par3Player.cameraYaw);
//		//ar3Player.cameraPitch = par3Player.cameraPitch+recoil;
//	}

	/*protected void spawnProjectile (final World world, final EntityPlayer player) {
    	world.spawnEntityInWorld(new GenericProjectile(world, player, damage, 1.5f, this.ticksToLive));
    }*/
    
	protected static boolean getDoBlockDamage(EntityLivingBase elb){
		boolean blockdamage=false;
		if (elb instanceof EntityPlayer){
			TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get((EntityPlayer) elb);
			if(props!=null){
				blockdamage=!props.enableSafemode;
			}
		}
		return blockdamage;
	}
	
    protected void spawnProjectile (final World world, final EntityLivingBase player, final ItemStack itemstack, float spread, float offset,float damagebonus, boolean leftGun) {
    	GenericProjectile proj = new GenericProjectile(world, player, damage*damagebonus, speed, this.ticksToLive, spread, this.damageDropStart, this.damageDropEnd, this.damageMin*damagebonus, this.penetration, getDoBlockDamage(player), leftGun);
    	
    	if (silenced){
    		proj.setSilenced();
    	}
    	if (offset>0.0f){
    		proj.shiftForward(offset);
    		//System.out.println("Shifted by"+offset);
    	}
    	/*if (!silenced){
    		
    	} else {
    		world.spawnEntityInWorld(new GenericProjectile(world, player, damage, speed, this.ticksToLive, spread, this.damageDropStart, this.damageDropEnd, this.damageMin, this.penetration).setSilenced());
    	}*/
    	world.spawnEntityInWorld(proj);
    }
    
    /**
     * Weapon is used by NPC
     */
    public void fireWeaponFromNPC(EntityLivingBase shooter, float dmgscale, float accscale) {
    	
    	//shooter.worldObj.playSoundAtEntity(shooter, firesound, SOUND_DISTANCE, 1.0F );
    	//System.out.println("FireWeaponFromNPC!");
    	SoundUtil.playSoundOnEntityGunPosition(shooter.worldObj, shooter ,firesound, SOUND_DISTANCE, 1.0F, false, false, TGSoundCategory.GUN_FIRE);
    	
    	
    	float dmg=0.5f;
    	float acc=1.0f;
    	
    	if (shooter instanceof NPCTurret){
    		dmg=1.0f;
    		acc=1.0f;
    	} else {
	    	
	    	EnumDifficulty difficulty = shooter.worldObj.difficultySetting;
	    	
	    	switch(difficulty){
	    		case EASY:
	    			dmg=0.5f;
	    			acc=1.3f;
	    			break;
	    		case NORMAL:
	    			dmg = 0.6f;
	    			acc = 1.15f;
	    			break;
	    		case HARD:
	    			dmg = 0.75f;
	    			acc = 1.0f;
	    			break;
	    		case PEACEFUL:
	    			dmg = 0.0f;
	    			acc = 1.3f;
	    			break;
	    	}
    	}
    	
    	if (!shooter.worldObj.isRemote){
    		this.shootGun(shooter.worldObj, shooter, null, this.zoombonus*accscale*acc,dmgscale*dmg,0);
    	}
    	//TODO: ADD MOAR CODE!
    	
    }
    

	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#addInformation(net.minecraft.item.ItemStack, net.minecraft.entity.player.EntityPlayer, java.util.List, boolean)
	 */
	@Override
	public void addInformation(ItemStack item, EntityPlayer player,
			List list, boolean b) {
		super.addInformation(item, player, list, b);
		//if (player.) {
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)){
		
			list.add(TextUtil.trans("techguns.gun.tooltip.ammo")+": "+(this.ammoCount>1 ? this.ammoCount+"x " : "")+EnumChatFormatting.WHITE+TextUtil.trans(this.getAmmo().getUnlocalizedName()+".name"));
			list.add(TextUtil.trans("techguns.gun.tooltip.damageType")+": "+this.getDamageType().toString());
			list.add(TextUtil.trans("techguns.gun.tooltip.damage")+(this.shotgun ? ("(x"+ (this.bulletcount+1)+")") : "" )+": "+this.damage+(this.damageMin<this.damage?"-"+this.damageMin:""));
			list.add(TextUtil.trans("techguns.gun.tooltip.range")+": "+this.damageDropStart+","+this.damageDropEnd+","+this.ticksToLive);
			list.add(TextUtil.trans("techguns.gun.tooltip.spread")+": "+this.accuracy);
			list.add(TextUtil.trans("techguns.gun.tooltip.clipsize")+": "+this.clipsize);
			list.add(TextUtil.trans("techguns.gun.tooltip.reloadTime")+": "+this.reloadtime*0.05f+"s");
			if (this.penetration>0.0f){
				list.add(TextUtil.trans("techguns.gun.tooltip.armorPen")+": "+this.penetration*100+"%");
			}
			if (this.canZoom) {
				list.add(TextUtil.trans("techguns.gun.tooltip.zoom")+":"+(this.toggleZoom ? "("+TextUtil.trans("techguns.gun.tooltip.zoom.toogle")+")":"("+TextUtil.trans("techguns.gun.tooltip.zoom.hold")+")")+" "+TextUtil.trans("techguns.gun.tooltip.zoom.multiplier")+":"+this.zoomMult);
			}
			if (this.mininglevels!=null){
				ItemUtil.addToolClassesTooltip(mininglevels, list);
			}
		} else {
			list.add(TextUtil.trans("techguns.gun.tooltip.ammo")+": "+(this.ammoCount>1 ? this.ammoCount+"x " : "")+EnumChatFormatting.WHITE+TextUtil.trans(this.getAmmo().getUnlocalizedName()+".name"));
			list.add(TextUtil.trans("techguns.gun.tooltip.damage")+(this.shotgun ? ("(x"+ (this.bulletcount+1)+")") : "" )+": "+this.damage+"-"+this.damageMin);
			list.add(TextUtil.trans("techguns.gun.tooltip.shift1")+" "+EnumChatFormatting.GREEN+TextUtil.trans("techguns.gun.tooltip.shift2")+" "+EnumChatFormatting.GRAY+TextUtil.trans("techguns.gun.tooltip.shift3"));
		}
		//} else {
		//	list.add("Sneak to view stats");
		//}
	}

	public GenericGun setAIStats(float attackRange, int attackTime, int burstCount, int burstAttackTime) {
		this.AI_attackRange = attackRange;
		this.AI_attackTime = attackTime;
		this.AI_burstCount = burstCount;
		this.AI_burstAttackTime = burstAttackTime;
		return this;
	}
	
	public EntityAIRangedAttack getAIAttack(IRangedAttackMob shooter) {
		return new EntityAIRangedAttack(shooter, 1.0D, this.AI_attackTime/3, this.AI_attackTime, this.AI_attackRange, this.AI_burstCount, this.AI_burstAttackTime);
	}

	public ItemStack getAmmo() {
		return ammo;
	}

	public ItemStack getEmptyMag() {
		return emptyMag;
	}

	public int getAmmoCount() {
		return ammoCount;
	}

	public float getAI_attackRange() {
		return AI_attackRange;
	}

	public GenericGun setCamoCount(byte count){
		this.camoCount=count;
		return this;
	}

	public GenericGun setTexture(String path){
		return setTextures(path,1);
	}
	
	public GenericGun setTextures(String path, int variations){
		Techguns.proxy.setGunTextures(this, path, variations);
		this.camoCount=variations;
		return this;
	}
	
	
	@Override
	public int getCamoCount() {
		return this.camoCount;
	}

	@Override
	public String getCurrentCamoName(ItemStack item) {
		NBTTagCompound tags = item.getTagCompound();
		byte camoID=0;
		if (tags!=null && tags.hasKey("camo")){
			camoID=tags.getByte("camo");
		}
		if(camoID>0){
			return TextUtil.trans(this.getUnlocalizedName()+".camoname."+camoID);
		} else {
			return TextUtil.trans("techguns.item.defaultcamo");
		}
	}

	@Override
	public int getCurrentCamoIndex(ItemStack item) {
		NBTTagCompound tags = item.getTagCompound();
		byte camoID=0;
		if (tags!=null && tags.hasKey("camo")){
			camoID=tags.getByte("camo");
		}
		return camoID;
	}

	@Override
	public int switchCamo(ItemStack item) {
		return switchCamo(item,false);
	}

	@Override
	public int switchCamo(ItemStack item, boolean back) {
		byte camoID=0;
		NBTTagCompound tags = item.getTagCompound();
		if (tags!=null && tags.hasKey("camo")){
			camoID=tags.getByte("camo");
		}
		
		if(tags==null){
			tags=new NBTTagCompound();
			tags.setByte("camo", (byte) 0);
			item.setTagCompound(tags);
		}
		
		if(this.camoCount>1){
			
			if(!back){
				camoID++;
			} else {
				camoID--;
			}
			
			if(camoID>=camoCount){
				camoID=0;
			} else if (camoID<0){
				camoID=(byte) (camoCount-1);
			}
			tags.setByte("camo", camoID);
			return camoID;
			
		} else {
			return 0;
		}

	}
    
	public int getAmmoLeftCountTooltip(ItemStack item){
		int ammo = this.getCurrentAmmo(item);
		if(this.burst){
			return ammo*(this.bulletcount+1);
		} else {
			return ammo;
		}
	}

	public int getClipsizeTooltip() {
		if(this.burst){
			return clipsize*(this.bulletcount+1);
		} else {
			return clipsize;
		}
	}
	
	public int getAmmoLeftCount(int mags){
		int count=(this.clipsize/this.ammoCount) * mags; 
		if(this.burst){
			return count*(this.bulletcount+1);
		} else {
			return count;
		}
	}
	
	/**
	 * @param x - Offset sideways, +right -left
	 * @param y - Offset height
	 * @param z - offset forward/backward
	 * @return
	 */
	public GenericGun setTurretPosOffset(float x, float y, float z){
		this.turretPosOffsetX=x;
		this.turretPosOffsetY=y;
		this.turretPosOffsetZ=z;
		return this;
	}
	
	public GenericGun setMuzzleParticle(int i){
		this.muzzleflashIndex=(byte) i;
		return this;
	}
	/**
	 * Set particle and z-offset
	 * @param i
	 * @param z
	 * @return
	 */
	public GenericGun setMuzzleParticle(int i,float z){
		this.muzzleflashIndex=(byte) i;
		this.muzzleFlashOffsetZ=z;
		return this;
	}
	
	/**
	 * Set particle and all-offsets
	 * @param i
	 * @param z
	 * @return
	 */
	public GenericGun setMuzzleParticle(int i,float x,float y,float z){
		this.muzzleflashIndex=(byte) i;
		this.muzzleFlashOffsetX=x;
		this.muzzleFlashOffsetY=y;
		this.muzzleFlashOffsetZ=z;
		return this;
	}
	
	public GenericGun setMuzzleParticleOffsetTurret(float x, float y, float z){
		this.muzzleFlashOffsetTurretX=x;
		this.muzzleFlashOffsetTurretY=y;
		this.muzzleFlashOffsetTurretZ=z;
		return this;
	}
	
    /**
     * @return string name of particle or null
     */
    public String getMuzzleflashParticle(){
    	return muzzleflash[this.muzzleflashIndex];
    }

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		//System.out.println("SWINGING");
		//return super.onEntitySwing(entityLiving, stack);
		

		
		/*if(entityLiving instanceof EntityPlayer){
			
			this.shootGunLeftClick(stack, entityLiving.worldObj, (EntityPlayer) entityLiving);
		}
		return true;*/
		
		if(this.shootWithLeftClick){
			//System.out.println("CANCEL SWING");
			return true;
		} else {
			if(this.getCurrentAmmo(stack)>=this.miningAmmoConsumption){
				//System.out.println("CANCEL SWING");
				return true;
			} else {
				return false;
			}
			
		}
		
		//return !this.shootWithLeftClick;
		
	}

	/**
	 * Override in Subclass to define damagesource used for player melee attacks, only relevant if "shootWithLeftClick" is not set
	 * @return
	 */
	protected TGDamageSource getMeleeDamageSource(EntityPlayer player, ItemStack stack){
		TGDamageSource src = new TGDamageSource("player", player, player, DamageType.PHYSICAL, DeathType.GORE);
		return src;
	}

	
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		// TODO Auto-generated method stub
		//return super.onLeftClickEntity(stack, player, entity);
		//return true;
		if(this.shootWithLeftClick){
			return true;
		} else {
			if(player.worldObj.isRemote){
				
				//ClientProxy.player_swingAnimTime=System.currentTimeMillis()+1000;
				//ClientProxy.player_swingAnimTimeTotal=1000;
				int time = (int) (((float)this.recoiltime/20.0f)*1000);
				ClientProxy.get().setplayerRecoiltime(player,System.currentTimeMillis()+time, time, (byte)0);
			}
			
			
			/**
			 * COPY FROM EntityPlayer.class attackTargetEntityWithCurrentItem
			 * 
			 * use different DamageSource
			 */
			
			if (player.canAttackWithItem())
	        {
	            if (!entity.hitByEntity(player))
	            {
	                float f = (float)player.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
	                int i = 0;
	                float f1 = 0.0F;

	                if (entity instanceof EntityLivingBase)
	                {
	                    f1 = EnchantmentHelper.getEnchantmentModifierLiving(player, (EntityLivingBase)entity);
	                    i += EnchantmentHelper.getKnockbackModifier(player, (EntityLivingBase)entity);
	                }

	                if (player.isSprinting())
	                {
	                    ++i;
	                }

	                if (f > 0.0F || f1 > 0.0F)
	                {
	                    boolean flag = player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(Potion.blindness) && player.ridingEntity == null && entity instanceof EntityLivingBase;

	                    if (flag && f > 0.0F)
	                    {
	                        f *= 1.5F;
	                    }

	                    f += f1;
	                    boolean flag1 = false;
	                    int j = EnchantmentHelper.getFireAspectModifier(player);

	                    if (entity instanceof EntityLivingBase && j > 0 && !entity.isBurning())
	                    {
	                        flag1 = true;
	                        entity.setFire(1);
	                    }

	                    /**
	                     * Other Damage source
	                     */
	                    TGDamageSource src = this.getMeleeDamageSource(player,stack);
	                    //f =(float) DamageSystem.getDamage(src, f, entity);
	                    boolean flag2 = entity.attackEntityFrom(src, f);
	                    //boolean flag2 = entity.attackEntityFrom(DamageSource.causePlayerDamage(player), f);

	                    if(this.getCurrentAmmo(stack)>=this.meleeHitAmmoConsumption){
	                    	this.useAmmo(stack, this.meleeHitAmmoConsumption);
	                    }
	                    
	                    if (flag2)
	                    {
	                        if (i > 0)
	                        {
	                            entity.addVelocity((double)(-MathHelper.sin(player.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(player.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
	                            player.motionX *= 0.6D;
	                            player.motionZ *= 0.6D;
	                            player.setSprinting(false);
	                        }

	                        if (flag)
	                        {
	                            player.onCriticalHit(entity);
	                        }

	                        if (f1 > 0.0F)
	                        {
	                            player.onEnchantmentCritical(entity);
	                        }

	                        if (f >= 18.0F)
	                        {
	                            player.triggerAchievement(AchievementList.overkill);
	                        }

	                        player.setLastAttacker(entity);

	                        if (entity instanceof EntityLivingBase)
	                        {
	                            EnchantmentHelper.func_151384_a((EntityLivingBase)entity, player);
	                        }

	                        EnchantmentHelper.func_151385_b(player, entity);
	                        ItemStack itemstack = player.getCurrentEquippedItem();
	                        Object object = entity;

	                        if (entity instanceof EntityDragonPart)
	                        {
	                            IEntityMultiPart ientitymultipart = ((EntityDragonPart)entity).entityDragonObj;

	                            if (ientitymultipart != null && ientitymultipart instanceof EntityLivingBase)
	                            {
	                                object = (EntityLivingBase)ientitymultipart;
	                            }
	                        }

	                        if (itemstack != null && object instanceof EntityLivingBase)
	                        {
	                            itemstack.hitEntity((EntityLivingBase)object, player);

	                            if (itemstack.stackSize <= 0)
	                            {
	                                player.destroyCurrentEquippedItem();
	                            }
	                        }

	                        if (entity instanceof EntityLivingBase)
	                        {
	                            player.addStat(StatList.damageDealtStat, Math.round(f * 10.0F));

	                            if (j > 0)
	                            {
	                                entity.setFire(j * 4);
	                            }
	                        }

	                        player.addExhaustion(0.3F);
	                    }
	                    else if (flag1)
	                    {
	                        entity.extinguish();
	                    }
	                }
	            }
	        }
			
			
			
			
			
			
			
			//CANCEL attackTargetEntityWithCurrentItem
			return true;
		}
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
		if(this.shootWithLeftClick){
			//System.out.println("CANCEL SWING");
			return true;
		} else {
			
			/*if(this.getCurrentAmmo(itemstack)>=this.miningAmmoConsumption){
				return true;
			} else {
				return false;
			}*/
			return false;
		}
	}

	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		//super.onCreated(stack, world, player);
		NBTTagCompound tags = stack.getTagCompound();
		if(tags==null){
			tags=new NBTTagCompound();
			stack.setTagCompound(tags);
		
			int dmg = stack.getItemDamage();
			
			tags.setByte("camo", (byte) 0);
			tags.setShort("ammo", dmg==0 ? (short)this.clipsize : (short)(this.clipsize-dmg));
			stack.setItemDamage(0);
		}
	}
	
	public int getCurrentAmmo(ItemStack stack){
		NBTTagCompound tags = stack.getTagCompound();
		if(tags==null){
			this.onCreated(stack, null, null); //world and player are not needed
			tags = stack.getTagCompound();
		}
		return tags.getShort("ammo");
	}
	
	/**
	 * Reduces ammo by amount, use positive values! amount=3 means ammo-=3;
	 * @param stack
	 * @param amount
	 * @return
	 */
	public void useAmmo(ItemStack stack, int amount){
		int ammo = this.getCurrentAmmo(stack);
		NBTTagCompound tags = stack.getTagCompound();
		if(ammo-amount>=0){
			tags.setShort("ammo", (short) (ammo-amount));
		} else {
			tags.setShort("ammo", (short) 0);
		}
	}
	
	public void reloadAmmo(ItemStack stack){
		this.reloadAmmo(stack, this.clipsize);
	}
	
	public void reloadAmmo(ItemStack stack, int amount){
		int ammo = this.getCurrentAmmo(stack);
		NBTTagCompound tags = stack.getTagCompound();
		tags.setShort("ammo", (short) (ammo+amount));
	}

	
	
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		ItemStack gun = new ItemStack(item, 1,0);
		NBTTagCompound tags = new NBTTagCompound();
		tags.setByte("camo", (byte) 0);
		tags.setShort("ammo", (short)this.clipsize);
		gun.setTagCompound(tags);
		
		list.add(gun);
	}

	public boolean isFullyLoaded(ItemStack stack){
		return this.clipsize == this.getCurrentAmmo(stack);
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		NBTTagCompound tags = stack.getTagCompound();
		if(tags==null){
			return ((stack.getItemDamage()*1.0f)/(stack.getMaxDamage()*1.0f));
		}
		int ammo = this.getCurrentAmmo(stack);
		float percent = (ammo*1.0f) / (1.0f*this.clipsize);
		//System.out.println("Display: "+percent+" ,Clip:"+ammo+"/"+this.clipsize);
		return 1.0f-percent;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	public boolean isSemiAuto() {
		return semiAuto;
	}

	/**
	 * Should this weapon shoot with left click instead of mine, defaults to true
	 * @return
	 */
	public boolean isShootWithLeftClick() {
		return shootWithLeftClick;
	}

	/**
	 * Should this weapon shoot with left click instead of mine, defaults to true
	 * @param shootWithLeftClick
	 */
	public GenericGun setShootWithLeftClick(boolean shootWithLeftClick) {
		this.shootWithLeftClick = shootWithLeftClick;
		return this;
	}
	
	public GenericGun setTool(String toolclass, int harvestLevel){
		if(this.mininglevels==null){
			this.mininglevels=new HashMap<String,Integer>();
			this.mininglevels.put("default", harvestLevel);
		}
		this.mininglevels.put(toolclass, harvestLevel);
		return this;
	}

	@Override
	public float getDigSpeed(ItemStack itemstack, Block block, int metadata) {
		//System.out.println("Getting digspeed for"+block);
		if(this.getCurrentAmmo(itemstack)>=this.miningAmmoConsumption){
			if(this.mininglevels==null){
				return 1.0f;
			}
			
			String toolclass = block.getHarvestTool(metadata);
			//System.out.println("Toolclass:"+toolclass);
			if (toolclass!=null){
				if(this.mininglevels.containsKey(toolclass)){
					return this.digSpeed;
				} else{
					return 1.0f;
				}
			} else{
				return this.digSpeed;
			}
		} else {
			return 1.0f;
		}
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass) {
		//System.out.println("HarvestLevel for: "+ toolClass);
		if(this.mininglevels==null){
			return 0;
		}
		if(toolClass==null){
			//System.out.println("return:"+this.mininglevels.get("default"));
			return this.mininglevels.get("default");
		} else if(this.mininglevels.containsKey(toolClass)){
			return this.mininglevels.get(toolClass);
		} else {
			return 0;
		}
	}
	
	
	@Override
	public boolean canHarvestBlock(Block block, ItemStack itemStack) {
		int harvestLevel = block.getHarvestLevel(0);
		String tool = block.getHarvestTool(0);
		//System.out.println("HarvestLevel: "+harvestLevel);
		//System.out.println("HarvestTool: "+tool);
		return this.getHarvestLevel(itemStack, tool) >= harvestLevel;
	}

	public int getMeleeHitAmmoConsumption() {
		return meleeHitAmmoConsumption;
	}

	public GenericGun setMeleeHitAmmoConsumption(int meleeHitAmmoConsumption) {
		this.meleeHitAmmoConsumption = meleeHitAmmoConsumption;
		return this;
	}

	public static ArrayList<GenericGun> getGuns() {
		return guns;
	}
	
	/*@Override
	public boolean canHarvestBlock(Block par1Block, ItemStack itemStack) {
		return super.canHarvestBlock(par1Block, itemStack);
	}*/
	
	public String getStatComment(){
		return "[DamageMax - float: damage close range]\n"
				+ "[DamageMin - float: damage high range]\n"
				+ "[DamageDropStart - int: distance in blocks for damage drop start, damage is max up to this range]\n"
				+ "[DamageDropEnd - int: distance in blocks for damage drop end, damage is min after this distance]\n"
				+ "[TimeToLive - int: distance when the bullet gets deleted]\n"
				+ "[Accuracy - float: max random angular offset when shooting]\n"
				+ "[Penetration - float: amount of armor penetration between 0.0 and 1.0. 1.0=100%.]";
	}
	
}
