package techguns;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import techguns.items.RecipeAkimboGun;
import techguns.items.RecipeAkimboSplit;
import techguns.items.ShapelessOreRecipeCopyNBT;
import techguns.items.guns.AlienBlaster;
import techguns.items.guns.Beamgun;
import techguns.items.guns.Biogun;
import techguns.items.guns.BlasterRifle;
import techguns.items.guns.Chainsaw;
import techguns.items.guns.CyberdemonBlaster;
import techguns.items.guns.Flamethrower;
import techguns.items.guns.Flashbang;
import techguns.items.guns.FragGrenade;
import techguns.items.guns.GenericGrenade;
import techguns.items.guns.GenericGun;
import techguns.items.guns.GenericGunTool;
import techguns.items.guns.GrenadeLauncher;
import techguns.items.guns.Handgun;
import techguns.items.guns.Lasergun;
import techguns.items.guns.MibGun;
import techguns.items.guns.NeutrinoGun;
import techguns.items.guns.NukeLauncher;
import techguns.items.guns.PowerHammer;
import techguns.items.guns.Railgun;
import techguns.items.guns.RocketLauncher;
import techguns.items.guns.SonicShotgun;
import techguns.items.guns.Teslagun;
import cpw.mods.fml.common.registry.GameRegistry;



public class TGuns {

	private static final float RANGE_MELEE=3.0f;
	private static final float RANGE_CLOSE=12.0f;
	private static final float RANGE_SHORT=18.0f;
	private static final float RANGE_MEDIUM=24.0f;
	private static final float RANGE_FAR=36.0f;
	
	//Weapons
	public static Item handcannon;
	public static Item pistol;
	public static Item sawedoff;
	//public static Item mp5;
	public static Item boltaction;
	public static Item flamethrower;
	public static Item m4;
	public static Item combatShotgun;
	public static Item lasergun;
	public static Item rocketlauncher;
	public static Item grimreaper;
	public static Item biogun;
	public static Item thompson;
	public static Item revolver;
	public static Item ak47;
	//public static Item railgun;
	public static Item as50;
	public static Item teslagun;
	public static Item pdw;
	public static Item lmg;
	public static Item m4_uq;
	public static Item goldenrevolver;
	public static Item pulseRifle;
	public static Item alienBlaster;
	public static Item cyberdemonBlaster;
	public static Item minigun;
	public static Item blasterRifle;
	public static Item powerHammer;
	public static Item nukeLauncher;
	public static Item powerHammerAdv;
	public static Item aug;
	public static Item grenadeLauncher;
	public static Item sonicShotgun;
	public static Item chainsaw;
	public static Item scatterBeamGun;
	public static Item mac10;
	
	public static Item ndr;
	
	//public static Item driller;
	
	public static Item stielGranate;
	public static Item flashbang;
	public static Item fragGrenade;
	
	public static Item akimbo_pistol;
	public static Item akimbo_revoler;
	public static Item akimbo_goldenrevolver;
	public static Item akimbo_mac10;
	
	public static Item mibGun;
	public static Item neutrinoGun;
	
	public static Item scar;
	public static Item vector;
	
	public static Item akimbo_mibGun;
	/**
	 * create guns
	 */
	public static void init(){
		
   	 handcannon = new Handgun("handcannon", TGItems.stonebullets, false, 12,1,30, 8.0f, "techguns:guns.handgunFire", "techguns:guns.handgunReload",25,0.035f).setDamageDrop(10, 25, 5.0f).setAIStats(RANGE_CLOSE, 60, 0, 0).setTexture("textures/guns/handgun").setMuzzleParticle(2,0.2f);
   	 pistol = new GenericGun("pistol", TGItems.pistolMag9mm, true, 4, 18, 35, 8.0f, "techguns:guns.pistolFire", "techguns:guns.pistolReload",30, 0.025f).setEmptyMag(TGItems.pistolMag9mmEmtpy, TGItems.bullets9mm, 3).setDamageDrop(15, 22, 6.0f).setTexture("textures/guns/pistol3").setMuzzleParticle(1).setDamageDrop(18, 25, 5.0f).setAIStats(RANGE_MEDIUM, 30, 3, 10).setMuzzleParticle(1,0,0.05f,-0.45f).setRecoiltime(3);
   	 sawedoff = new GenericGun("sawedoff",TGItems.shotgunShell, false, 4, 2, 28, 4.0f, "techguns:guns.sawedoffFire", "techguns:guns.sawedoffReload",10, 0.01f).setAmmoCount(2).setShotgunSpread(7,0.2f,false).setDamageDrop(1, 4, 1.5f).setAIStats(RANGE_CLOSE, 60, 2, 20).setTexture("textures/guns/sawedOff").setMuzzleParticle(2,0.1f);
   	 //mp5 = new GenericGun("mp5",TGItems.smgMag, false, 2, 30,40,8.0f, "techguns:guns.pistolFire", "techguns:guns.pistolReload",25,0.035f).setEmptyMag(TGItems.smgMagEmpty, TGItems.bullets9mm,2).setDamageDrop(18, 25, 5.0f).setPenetration(0.25f).setTexture("textures/guns/mp5Texture").setMuzzleParticle(1);;
   	 boltaction = new GenericGun("boltaction", TGItems.rifleBullets, true, 25, 6, 50, 16.0f, "techguns:guns.boltactionFire", "techguns:guns.boltactionReload",40,0.05f).setZoom(0.35f, true,0.125f).setSpeed(1.5f).setRecoiltime(15).setRechamberSound("techguns:guns.boltactionRechamber").setDamageDrop(20, 30, 10.0f).setPenetration(0.15f).setAIStats(RANGE_FAR, 60, 0, 0).setTextures("textures/guns/boltactionrifle",3).setMuzzleParticle(1,0,0.05f,0.3f);
   	 m4 = new GenericGun("m4", TGItems.m4Mag, false, 3, 30,45,8.0f, "techguns:guns.assaultRifleFire", "techguns:guns.assaultRifleReload", 35, 0.015f).setEmptyMag(TGItems.m4MagEmpty, TGItems.rifleBullets,3).setZoom(0.75f, true,0.75f).setDamageDrop(25, 35, 6.0f).setAIStats(RANGE_MEDIUM, 30, 3, 3).setTextures("textures/guns/m4Texture", 5).setMuzzleParticle(1,0,0.025f,0).setPenetration(0.15f);
   	 flamethrower = new Flamethrower("flamethrower", TGItems.flamethrowerTank, false, 2, 100, 45, 5.0f, "techguns:guns.flamethrowerFire", "techguns:guns.flamethrowerReload",16,0.05f).setEmptyMag(TGItems.flamethrowerTankEmpty,null,1).setFiresoundStart("techguns:guns.flamethrowerStart").setMaxLoopDelay(10).setDamageDrop(4, 16, 2.0f).setAIStats(RANGE_CLOSE, 20, 5, 5).setTexture("textures/guns/Flamethrower");
   	 combatShotgun = new GenericGun("combatshotgun",TGItems.shotgunShell, false, 14, 8, 50, 4.0f, "techguns:guns.combatshotgunFire", "techguns:guns.combatshotgunReload",15, 0.01f).setAmmoCount(8).setShotgunSpread(7,0.15f,false).setRecoiltime(12).setRechamberSound("techguns:guns.combatshotgunPump").setDamageDrop(2, 5, 1.5f).setPenetration(0.15f).setAIStats(RANGE_CLOSE,30,0,0).setTexture("textures/guns/combatShotgun").setTurretPosOffset(0, 0.075f, 0).setMuzzleParticle(2,0.1f);
   	 lasergun = new Lasergun("lasergun", TGItems.energyCell, false, 5, 45, 45, 12.0f, "techguns:guns.lasergunFire", "techguns:guns.lasergunReload", Lasergun.LIFETIME, 0.0f).setEmptyMag(TGItems.energyCellEmpty,null,0).setZoom(0.75f, true,0.75f).setSpeed(Lasergun.SPEED).setAIStats(RANGE_MEDIUM, 30, 0, 0).setTexture("textures/guns/Lasergun"); //.setTexture("textures/guns/laserGunNew");//
   	 rocketlauncher = new RocketLauncher("rocketlauncher", TGItems.rocketAmmo, false, 10, 1 , 40, 50.0f, "techguns:guns.rocketFire", "techguns:guns.rocketReload", 100, 0.05f).setRecoiltime(10).setAIStats(RANGE_MEDIUM,80,0,0).setTexture("textures/guns/rocketlauncher").setTurretPosOffset(0f, -0.7f, -0.2f);
   	 grimreaper = new RocketLauncher("grimreaper", TGItems.rocketAmmo, false, 6, 4 , 40, 50.0f, "techguns:guns.rocketFire", "techguns:guns.rocketReload", 100, 0.05f).setRecoiltime(10).setAmmoCount(4).setAIStats(RANGE_MEDIUM, 120, 4, 30).setTexture("textures/guns/grimreaper").setTurretPosOffset(0f, -0.7f, -0.4f);
   	 biogun = new Biogun("biogun", TGItems.bioTank, false, 6, 30,45,10.0f, "techguns:guns.biogunFire", "techguns:guns.biogunReload", 35, 0.015f).setEmptyMag(TGItems.bioTankEmpty,null,0).setPenetration(0.15f).setTexture("textures/guns/BioGun").setAIStats(RANGE_SHORT, 30, 0, 0).setDamageDrop(8, 15, 8.0f);
   	 thompson = new GenericGun("thompson",TGItems.smgMag, false, 3, 20,40,5.0f, "techguns:guns.thompsonFire", "techguns:guns.thompsonReload",25,0.05f).setEmptyMag(TGItems.smgMagEmpty,TGItems.bullets9mm,2).setDamageDrop(15, 24, 3.0f).setAIStats(RANGE_SHORT, 45, 3, 3).setTexture("textures/guns/thompson").setMuzzleParticle(1,0,0.05f,0);
   	 revolver = new GenericGun("revolver",TGItems.bullets9mm, true, 6, 6,45,8.0f, "techguns:guns.revolverFire", "techguns:guns.revolverReload",25,0.025f).setDamageDrop(12, 20, 6.0f).setRecoiltime(6).setAIStats(RANGE_SHORT, 90, 6, 20).setTexture("textures/guns/revolver").setMuzzleParticle(2,0,0.05f,-0.45f);
 	 ak47 = new GenericGun("ak47", TGItems.m4Mag, false, 3, 30,45,9.0f, "techguns:guns.ak47Fire", "techguns:guns.ak47Reload", 35, 0.030f).setEmptyMag(TGItems.m4MagEmpty,TGItems.rifleBullets,3).setDamageDrop(20, 30, 5.0f).setPenetration(0.15f).setAIStats(RANGE_MEDIUM, 30, 3, 3).setTextures("textures/guns/ak47Texture",2).setMuzzleParticle(1,0,0.05f,0.12f);
 	 //railgun = new Railgun("railgun", TGItems.energyCell, false, 25, 15, 45, 25.0f, "techguns:guns.lasergunFire", "techguns:guns.lasergunReload", Lasergun.LIFETIME, 0.005f).setEmptyMag(TGItems.energyCellEmpty,null,0).setZoom(0.75f, true,0.75f).setSpeed(Lasergun.SPEED).setPenetration(1.0f).setTexture("textures/guns/Lasergun");
 	 as50 = new GenericGun("as50", TGItems.as50Mag, true, 10, 10, 80, 32.0f, "techguns:guns.as50Fire", "techguns:guns.as50Reload", 50,0.0625f).setEmptyMag(TGItems.as50MagEmpty,TGItems.highCalibreBullets,2).setDamageDrop(30, 40, 24.0f).setZoom(0.35f, true,0.125f).setSpeed(1.5f).setRecoiltime(10).setPenetration(0.25f).setAIStats(RANGE_FAR, 30, 0, 0).setTexture("textures/guns/as50Texture").setMuzzleParticle(1,0,0.05f,0.3f);
 	 teslagun = new Teslagun("teslagun", TGItems.energyCell, false, 8, 25, 45, 12.0f, "techguns:guns.teslaFire", "techguns:guns.teslaReload", Teslagun.LIFETIME, 0.0f).setEmptyMag(TGItems.energyCellEmpty,null,0).setZoom(0.75f, true,1.0f).setSpeed(Lasergun.SPEED).setMuzzleFlashTime(10).setTexture("textures/guns/TeslaGun").setAIStats(RANGE_MEDIUM, 30, 0, 0);
 	 pdw = new GenericGun("pdw",TGItems.pdwMag, false, 2, 20, 40, 6.0f, "techguns:guns.pdwFire", "techguns:guns.pdwReload",25,0.03f).setEmptyMag(TGItems.pdwMagEmpty,TGItems.advBullets,3).setDamageDrop(16, 25, 3.0f).setPenetration(0.25f).setShotgunSpread(1, 0.03f,true).setAIStats(RANGE_SHORT, 30, 4, 2).setTextures("textures/guns/PDW",3).setMuzzleParticle(3,0,0.05f,-0.35f).setCheckRecoil();
   	 lmg = new GenericGun("lmg",TGItems.lmgMag,false, 2, 100,100,8.0f, "techguns:guns.lmgFire", "techguns:guns.lmgReload", 50, 0.020f).setEmptyMag(TGItems.lmgMagEmpty,TGItems.rifleBullets,8).setZoom(0.75f, true,0.75f).setDamageDrop(30, 40, 6.0f).setPenetration(0.15f).setAIStats(RANGE_MEDIUM, 40, 6, 3).setTexture("textures/guns/mg2_texture").setMuzzleParticle(1,0,0.1f,0.02f);
   	 m4_uq = new GenericGun("m4_uq", TGItems.m4Mag, false, 3, 30,45,8.0f, "techguns:guns.silencedM4Fire", "techguns:guns.assaultRifleReload", 35, 0.010f).setEmptyMag(TGItems.m4MagEmpty,TGItems.rifleBullets,3).setZoom(0.50f, true,0.5f).setDamageDrop(25, 35, 6.0f).setSilenced(true).setAIStats(RANGE_MEDIUM, 30, 3, 3).setTexture("textures/guns/m4_uq_Texture").setPenetration(0.15f);
	 //driller = new GenericGunTool("driller", TGItems.flamethrowerTank, false, 0, 100,2,3.0f,"techguns:guns.flamethrowerFire", "techguns:guns.flamethrowerReload",2,0.0f).setEmptyMag(TGItems.flamethrowerTankEmpty,null,1).setTexture("textures/guns/drillTexture");
	 goldenrevolver = new GenericGun("goldenrevolver",TGItems.bullets9mm, true, 8, 6,45,14.0f, "techguns:guns.goldenrevolverFire", "techguns:guns.revolverReload",25,0.015f).setDamageDrop(12, 30, 6.0f).setRecoiltime(8).setAIStats(RANGE_MEDIUM, 60, 6, 15).setTexture("textures/guns/goldenrevolver").setMuzzleParticle(2,0,0.05f,-0.4f);
	 pulseRifle = new GenericGun("pulseRifle", TGItems.pdwMag, false, 7, 12, 45, 10.0f,  "techguns:guns.pulserifleFire", "techguns:guns.pulserifleReload", 50,0.024f).setEmptyMag(TGItems.pdwMagEmpty,TGItems.advBullets,3).setDamageDrop(25, 35, 8f).setZoom(0.35f, true,0.5f).setSpeed(1.5f).setRecoiltime(8).setPenetration(0.25f).setShotgunSpread(2, 0.015f,true).setAIStats(RANGE_MEDIUM, 30, 0, 0).setTextures("textures/guns/pulseRifle",3).setMuzzleParticle(3,0,0.025f,-0.05f);
	 alienBlaster = new AlienBlaster("alienBlaster", TGItems.energyCell, false, 8, 10, 35, 16.0f, "techguns:guns.alienblasterFire", "techguns:guns.alienblasterReload", 50, 0.0f).setEmptyMag(TGItems.energyCellEmpty,null,0).setMuzzleFlashTime(10).setPenetration(0.2f).setAIStats(RANGE_MEDIUM, 40, 0, 0).setTexture("textures/guns/alien_blaster");
	 cyberdemonBlaster = new CyberdemonBlaster("cyberdemonBlaster", TGItems.nethercharge, false, 8, 10, 35, 14.0f, "techguns:guns.cyberdemonblasterFire", "techguns:guns.teslaReload", 50, 0.0f).setMuzzleFlashTime(10).setPenetration(0.15f).setAIStats(RANGE_MEDIUM, 40, 0, 0).setTexture("textures/guns/cyberdemonblaster").setDamageDrop(15, 30, 8.0f);
	 minigun = new GenericGun("minigun",TGItems.minigunMag,false, 0, 200, 100, 5.0f, "techguns:guns.minigunFire", "techguns:guns.minigunReload", 50, 0.025f).setEmptyMag(TGItems.minigunMagEmpty,TGItems.rifleBullets,16).setDamageDrop(20, 40, 3.0f).setPenetration(0.15f)/*.setShotgunSpread(2, 0.025f,true)*/.setAIStats(RANGE_MEDIUM, 40, 10, 1).setTexture("textures/guns/minigun").setTurretPosOffset(0, 0.20f, 0).setMuzzleParticle(1,0,-0.8f,0.4f).setMuzzleParticleOffsetTurret(0,0.6f,0).setCheckRecoil();//.setRecoiltime(30).setFiresoundStart("techguns:guns.minigunStart").setMaxLoopDelay(10)
	 blasterRifle = new BlasterRifle("blasterRifle", TGItems.energyCell, false, 5, 50, 45, 10.0f, "techguns:guns.blasterrifleFire", "techguns:guns.lasergunReload", 50, 0.025f).setEmptyMag(TGItems.energyCellEmpty,null,0).setZoom(0.5f, true,0.75f).setAIStats(RANGE_MEDIUM, 30, 5, 3).setTexture("textures/guns/blasterRifle").setDamageDrop(25, 35, 8.0f).setPenetration(0.2f).setMuzzleParticle(4, 0f,-0.1f,0.1f).setMuzzleParticleOffsetTurret(0, 0, -0.4f);
	 powerHammer = new PowerHammer("powerHammer", TGItems.compressedAirTank, false, 4, 300, 45, 2.5f, "techguns:guns.powerhammerFire", "techguns:guns.powerhammerReload").setMeleeDmg(6.0f, 2.0f).setTexture("textures/guns/powerHammer").setRecoiltime(12).setShootWithLeftClick(false).setTool("pickaxe", 2).setTool("shovel", 2).setEmptyMag(TGItems.compressedAirTankEmpty,null,1).setDigSpeed(12.0f).setAIStats(RANGE_MELEE, 30, 0, 0).setDamageDrop(3, 3, 2.5f);
	 nukeLauncher = new NukeLauncher("nukelauncher", TGItems.rocketAmmoNuclear, false, 10, 1 , 40, 250.0f, "techguns:guns.rocketFire", "techguns:guns.rocketReload", 100, 0.05f).setRecoiltime(10).setAIStats(RANGE_MEDIUM,80,0,0).setTexture("textures/guns/nukerocketlauncher").setTurretPosOffset(0f, -0.7f, -0.2f);
	 powerHammerAdv = new PowerHammer("powerHammerAdv", TGItems.compressedAirTank, false, 4, 300, 45, 2.5f, "techguns:guns.powerhammerFire", "techguns:guns.powerhammerReload").setMeleeDmg(7.0f, 2.0f).setTexture("textures/guns/powerHammerAdv").setRecoiltime(12).setShootWithLeftClick(false).setTool("pickaxe", 3).setTool("shovel", 3).setEmptyMag(TGItems.compressedAirTankEmpty,null,1).setDigSpeed(15.0f).setAIStats(RANGE_MELEE, 30, 0, 0);
   	 grenadeLauncher = new GrenadeLauncher("grenadelauncher", TGItems._40mmGrenade, true, 5, 6, 100, 30.0f, "techguns:guns.grenadeLauncherFire", "techguns:guns.grenadeLauncherReload", 35, 0.015f).setTexture("textures/guns/grenadelauncher").setAIStats(RANGE_MEDIUM, 40, 3, 20).setAmmoCount(6);
	 aug = new GenericGun("aug", TGItems.m4Mag, false, 3, 30,45,8.0f, "techguns:guns.augFire", "techguns:guns.augReload", 35, 0.010f).setEmptyMag(TGItems.m4MagEmpty,TGItems.rifleBullets,3).setZoom(0.50f, true,0.5f).setDamageDrop(25, 40, 7.0f).setAIStats(RANGE_MEDIUM, 30, 3, 3).setTextures("textures/guns/AugTexture",5).setMuzzleParticle(1,0,0.025f,0).setPenetration(0.15f);
	 sonicShotgun = new SonicShotgun("sonicShotgun",TGItems.energyCell, true, 12, 8, 40, 25.0f, "techguns:guns.sonicshotgunFire", "techguns:guns.sonicshotgunReload",20,0.0f).setEmptyMag(TGItems.energyCellEmpty,null,0).setDamageDrop(5, 15, 5.0f).setPenetration(0.25f).setAIStats(RANGE_SHORT, 40, 0, 0).setTexture("textures/guns/sonicshotgun").setMuzzleParticle(3,0,0.05f,-0.35f);
	 chainsaw = new Chainsaw("chainsaw", TGItems.flamethrowerTank, false, 3, 300, 45, 10.0f, "techguns:guns.chainsawLoop", "techguns:guns.powerhammerReload", 2, 0.0f).setMeleeDmg(12.0f, 2.0f).setTexture("textures/guns/chainsaw").setRecoiltime(5).setShootWithLeftClick(false).setTool("axe", 3).setFiresoundStart("techguns:guns.chainsawLoopStart").setMaxLoopDelay(10).setEmptyMag(TGItems.flamethrowerTankEmpty,null,1).setDigSpeed(14.0f).setPenetration(0.25f).setAIStats(RANGE_MELEE, 10, 0, 0).setTurretPosOffset(0, 0.50f, 0);
	 scatterBeamGun = new Lasergun("scatterBeamGun", TGItems.energyCell, false, 7, 40, 45, 6.0f, "techguns:guns.lasergunFire", "techguns:guns.lasergunReload", Lasergun.LIFETIME, 0.1f).setEmptyMag(TGItems.energyCellEmpty,null,0).setShotgunSpread(4,0.15f,false).setZoom(0.75f, true,0.75f).setSpeed(Lasergun.SPEED).setAIStats(RANGE_SHORT, 30, 0, 0).setTexture("textures/guns/laserGunNew");
     ndr = new Beamgun("nuclearDeathRay", TGItems.nuclearPowerCell, false, 5, 100, 30, 6.0f, "techguns:guns.beamgunFire", "techguns:guns.lasergunReload", Beamgun.LIFETIME, 0.0f).setFiresoundStart("techguns:guns.beamgunStart").setMaxLoopDelay(10).setEmptyMag(TGItems.nuclearPowerCellDepleted,null,0)/*.setRecoiltime(4)*/.setSpeed(Lasergun.SPEED).setAIStats(RANGE_MEDIUM, 40, 5, 5).setTexture("textures/guns/NDR").setTurretPosOffset(0.1f, 0, -0.4f).setMuzzleParticleOffsetTurret(-0.2f, 0, 0).setPenetration(0.35f).setDamageDrop(20, 40, 1.0f);//.setCheckRecoil(); 
	 akimbo_pistol = new GenericGun("pistolAkimbo", TGItems.pistolMag9mm, true, 3, 36, 52, 8.0f, "techguns:guns.pistolFire", "techguns:guns.pistolReloadAkimbo",30, 0.035f).setEmptyMag(TGItems.pistolMag9mmEmtpy, TGItems.bullets9mm, 6).setDamageDrop(15, 22, 6.0f).setTexture("textures/guns/pistol3").setMuzzleParticle(1).setDamageDrop(18, 25, 5.0f).setAIStats(RANGE_MEDIUM, 30, 3, 10).setMuzzleParticle(1,0,0.05f,-0.45f).setRecoiltime(3).setMuzzleFlashTime(3).setAkimbo(false);
	 akimbo_revoler = new GenericGun("revolverAkimbo",TGItems.bullets9mm, true, 4, 12,70,8.0f, "techguns:guns.revolverFire", "techguns:guns.revolverReloadAkimbo",25,0.035f).setDamageDrop(12, 20, 6.0f).setRecoiltime(6).setAIStats(RANGE_SHORT, 90, 6, 20).setTexture("textures/guns/revolver").setMuzzleParticle(2,0,0.05f,-0.45f).setAkimbo(false);
	 akimbo_goldenrevolver = new GenericGun("goldenrevolverAkimbo",TGItems.bullets9mm, true, 6, 12,70,16.0f, "techguns:guns.goldenrevolverFire", "techguns:guns.revolverReloadAkimbo",25,0.035f).setDamageDrop(15, 30, 6.0f).setRecoiltime(8).setAIStats(RANGE_MEDIUM, 60, 6, 15).setTexture("textures/guns/goldenrevolver").setMuzzleParticle(2,0,0.05f,-0.4f).setAkimbo(false);
	 mac10 = new GenericGun("mac10",TGItems.smgMag, false, 2, 32,40,5.0f, "techguns:guns.mac10Fire", "techguns:guns.mac10Reload",25,0.05f).setEmptyMag(TGItems.smgMagEmpty,TGItems.bullets9mm,2).setDamageDrop(15, 24, 3.0f).setAIStats(RANGE_SHORT, 35, 3, 2).setTexture("textures/guns/mac10texture").setMuzzleParticle(1,0,0.05f,0).setRecoiltime(2).setMuzzleParticleOffsetTurret(0, 0, -0.45f);
	 akimbo_mac10 = new GenericGun("mac10Akimbo",TGItems.smgMag, false, 2, 64,68,5.0f, "techguns:guns.mac10Fire", "techguns:guns.mac10ReloadAkimbo",25,0.07f).setEmptyMag(TGItems.smgMagEmpty,TGItems.bullets9mm,2).setDamageDrop(15, 24, 3.0f).setAIStats(RANGE_SHORT, 45, 3, 3).setTexture("textures/guns/mac10texture").setMuzzleParticle(1,0,0.05f,0).setRecoiltime(2).setAkimbo(true);
	 mibGun = new MibGun("mibGun", TGItems.energyCell, true, 8, 20, 45, 16.0f, "techguns:guns.MIBblasterFire", "techguns:guns.MIBblasterReload", 50, 0.035f).setEmptyMag(TGItems.energyCellEmpty,null,0).setAIStats(RANGE_MEDIUM, 60, 0, 0).setTexture("textures/guns/mibgun").setDamageDrop(20, 30, 8.0f).setPenetration(0.2f).setMuzzleParticle(5, 0f,-0.1f,0.1f).setMuzzleParticleOffsetTurret(0, 0, -0.2f);	
	// neutrinoGun = new NeutrinoGun("neutrinoGun", TGItems.nuclearPowerCell, false, 5, 100, 30, 8.0f, "techguns:guns.beamgunFire", "techguns:guns.lasergunReload", Beamgun.LIFETIME, 0.0f).setFiresoundStart("techguns:guns.beamgunStart").setMaxLoopDelay(10).setEmptyMag(TGItems.nuclearPowerCellDepleted,null,0)/*.setRecoiltime(4)*/.setSpeed(Lasergun.SPEED).setAIStats(RANGE_MEDIUM, 40, 5, 5).setTexture("textures/guns/neutrinogun").setTurretPosOffset(0.0f, 0,0.1f).setMuzzleParticleOffsetTurret(-0.2f, 0, 0).setPenetration(0.35f).setDamageDrop(24, 48, 1.0f);//.setCheckRecoil(); 
	 scar = new GenericGun("scar", TGItems.m4Mag, false, 4, 20,45,12.0f, "techguns:guns.scarFire", "techguns:guns.scarReload", 50, 0.015f).setEmptyMag(TGItems.m4MagEmpty, TGItems.rifleBullets,3).setZoom(0.65f, true,0.5f).setDamageDrop(30, 40, 10.0f).setAIStats(RANGE_MEDIUM, 30, 5, 2).setTextures("textures/guns/scar_texture", 2).setMuzzleParticle(1,0,0.030f,0).setPenetration(0.25f);
	 vector = new GenericGun("vector",TGItems.smgMag, false, 2, 25,40,6.0f, "techguns:guns.vectorFire", "techguns:guns.vectorReload",30,0.05f).setEmptyMag(TGItems.smgMagEmpty,TGItems.bullets9mm,2).setZoom(0.75f, true,0.35f).setDamageDrop(17, 25, 4.0f).setAIStats(RANGE_SHORT, 35, 3, 2).setTextures("textures/guns/vector_texture",2).setMuzzleParticle(1,0,0.05f,0).setRecoiltime(2).setMuzzleParticleOffsetTurret(0, 0, -0.45f).setPenetration(0.15f);
	
	 akimbo_mibGun = new MibGun("mibGunAkimbo", TGItems.energyCell, true, 5, 40, 60, 16.0f, "techguns:guns.MIBblasterFire", "techguns:guns.MIBblasterReloadAkimbo", 50, 0.040f).setEmptyMag(TGItems.energyCellEmpty,null,0).setAIStats(RANGE_MEDIUM, 60, 0, 0).setTexture("textures/guns/mibgun").setDamageDrop(20, 30, 8.0f).setPenetration(0.2f).setMuzzleParticle(5, 0f,-0.1f,0.1f).setMuzzleParticleOffsetTurret(0, 0, -0.2f).setAkimbo(false);	

	 
    	 //Grenades
   	 stielGranate = new GenericGrenade("stielGranate",16,144000);
   	 flashbang = new Flashbang("flashbang",16,144000);
	fragGrenade = new FragGrenade("fragGrenade",16,144000);
		
        //Guns
        GameRegistry.registerItem(handcannon, "handcannon");
        GameRegistry.registerItem(pistol, "pistol");
        GameRegistry.registerItem(sawedoff, "sawedoff");
        //GameRegistry.registerItem(mp5, "mp5");
        GameRegistry.registerItem(boltaction, "boltaction");
        GameRegistry.registerItem(m4, "m4");
        GameRegistry.registerItem(flamethrower, "flamethrower");
        GameRegistry.registerItem(combatShotgun, "combatshotgun");
        GameRegistry.registerItem(lasergun, "lasergun");
        GameRegistry.registerItem(rocketlauncher, "rocketlauncher");
        GameRegistry.registerItem(biogun, "biogun");
        GameRegistry.registerItem(thompson, "thompson");
        GameRegistry.registerItem(revolver, "revolver");
        GameRegistry.registerItem(ak47, "ak47");
        //GameRegistry.registerItem(railgun, "railgun");
        GameRegistry.registerItem(as50, "as50");
        GameRegistry.registerItem(teslagun, "teslagun");
        GameRegistry.registerItem(pdw, "pdw");
        GameRegistry.registerItem(lmg, "lmg");
        GameRegistry.registerItem(m4_uq, "m4_uq");
        GameRegistry.registerItem(grimreaper, "grimreaper");
        GameRegistry.registerItem(goldenrevolver, "goldenrevolver");
        GameRegistry.registerItem(pulseRifle, "pulserifle");
        GameRegistry.registerItem(alienBlaster,"alienBlaster");
        GameRegistry.registerItem(cyberdemonBlaster,"cyberdemonBlaster");
        GameRegistry.registerItem(minigun,"minigun");
        GameRegistry.registerItem(blasterRifle,"blasterRifle");
        GameRegistry.registerItem(powerHammer,"powerHammer");
        GameRegistry.registerItem(nukeLauncher, "nukelauncher");
        GameRegistry.registerItem(powerHammerAdv,"powerHammerAdv");
        GameRegistry.registerItem(aug,"aug");
        GameRegistry.registerItem(grenadeLauncher,"grenadelauncher");
        GameRegistry.registerItem(sonicShotgun, "sonicShotgun");
        GameRegistry.registerItem(chainsaw, "chainsaw");
        GameRegistry.registerItem(scatterBeamGun, "scatterBeamGun");
        GameRegistry.registerItem(ndr, "beamtestgun");
        GameRegistry.registerItem(mac10, "mac10");
        
        GameRegistry.registerItem(akimbo_pistol, "pistolAkimbo");
        GameRegistry.registerItem(akimbo_revoler, "revolverAkimbo");
        GameRegistry.registerItem(akimbo_goldenrevolver, "goldenrevolverAkimbo");
        GameRegistry.registerItem(akimbo_mac10, "mac10Akimbo");
        
        GameRegistry.registerItem(mibGun, "mibGun");
        //GameRegistry.registerItem(neutrinoGun, "neutrinoGun");
        GameRegistry.registerItem(scar, "scar");
        GameRegistry.registerItem(vector, "vector");
        
        GameRegistry.registerItem(akimbo_mibGun, "mibGunAkimbo");

        GameRegistry.registerItem(stielGranate,"stielGranate");

        GameRegistry.registerItem(fragGrenade,"fragGrenade");


	}
	
	
	/**
	 * Add gun recipes
	 */
	public static void postInit(){
		   //Guns
        GameRegistry.addShapedRecipe(new ItemStack(handcannon,1, handcannon.getMaxDamage()), "bfs", 'b', TGItems.stoneBarrel, 'f', Items.flint_and_steel, 's', TGItems.woodStock);
        
        //GameRegistry.addShapedRecipe(new ItemStack(pistol,1), "bfi"," mi", 'b', TGItems.ironBarrel, 'f', TGItems.mechanicalParts, 'i', Items.iron_ingot, 'm', TGItems.pistolMag9mm);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pistol,1), "bsp"," fp","  m", 'b', TGItems.obsidianSteelBarrel, 'f', TGItems.mechanicalPartsT2, 'p', TGItems.plasticSheet, 's', "plateSteel", 'm', TGItems.pistolMag9mm));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pistol,1,pistol.getMaxDamage()), "bsp"," fp","  m", 'b', TGItems.obsidianSteelBarrel, 'f', TGItems.mechanicalPartsT2, 'p', TGItems.plasticSheet,'s',"plateSteel", 'm', TGItems.pistolMag9mmEmtpy));
        
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(revolver,1, revolver.getMaxDamage()), "bmf"," s ", 'f', Items.flint_and_steel, 'b', TGItems.ironBarrel, 's', TGItems.woodStock, 'm', TGItems.mechanicalParts)); 
        
        
        GameRegistry.addShapedRecipe(new ItemStack(sawedoff,1, sawedoff.getMaxDamage()), "bfw","bf ", 'b', TGItems.ironBarrel, 'f', Items.flint_and_steel, 'w', TGItems.woodStock);
        
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mp5,1), "ssi","bfg"," mi", 's', TGItems.compressedAirTankEmpty, 'i', "ingotIron", 'f', TGItems.mechanicalParts, 'b', TGItems.ironBarrel, 'g', TGItems.plasticGunstock, 'm', TGItems.smgMag));
       
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(thompson,1), "bfs"," m ", 'f', TGItems.ironReceiver, 'b', TGItems.ironBarrel, 's', TGItems.woodStock, 'm', TGItems.smgMag)); 
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(thompson,1,thompson.getMaxDamage()), "bfs"," m ", 'f', TGItems.ironReceiver, 'b', TGItems.ironBarrel, 's', TGItems.woodStock, 'm', TGItems.smgMagEmpty)); 
        
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(m4,1)," gr","bfs","nmi", 'g', "paneGlass", 'r', Items.redstone, 'b', TGItems.obsidianSteelBarrel, 'f', TGItems.steelReceiver, 's', TGItems.plasticGunstock, 'n', "nuggetIron",'m', TGItems.m4Mag, 'i', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(m4,1,m4.getMaxDamage())," gr","bfs","nmi", 'g', "paneGlass", 'r', Items.redstone, 'b', TGItems.obsidianSteelBarrel, 'f', TGItems.steelReceiver, 's', TGItems.plasticGunstock, 'n', "nuggetIron",'m', TGItems.m4MagEmpty, 'i', "ingotIron"));
        
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(boltaction,1, boltaction.getMaxDamage()), "gi ", "bfs", 'g', "blockGlass", 'b', TGItems.obsidianSteelBarrel, 'f', TGItems.ironReceiver, 's', TGItems.woodStock,'i', "plateIron"));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(combatShotgun,1, combatShotgun.getMaxDamage()), "bfs","i  ",'b', TGItems.obsidianSteelBarrel, 'f', TGItems.steelReceiver, 's', TGItems.plasticGunstock, 'i', "ingotSteel"));
        
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(flamethrower,1), "bfs","rim",'b', TGItems.ironBarrel, 'f', TGItems.pumpMechanism, 's', TGItems.ironGunstock, 'i', "ingotInvar", 'm', TGItems.flamethrowerTank, 'r',"dyeRed"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(flamethrower,1), "prs","fm ",'r', TGItems.ironReceiver, 'p', TGItems.pumpMechanism, 's', TGItems.plasticGunstock, 'f', Items.flint_and_steel, 'm', TGItems.flamethrowerTank));
        
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(biogun,1), "frs"," m ",'r', TGItems.steelReceiver, 'f', TGItems.pumpMechanism, 's', TGItems.plasticGunstock, 'm', TGItems.bioTank));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(lasergun,1)," gd","brs"," em", 'g', "paneGlass", 'd', Items.redstone, 'b', TGItems.laserBarrel, 'm', TGItems.mechanicalPartsT3, 's', TGItems.plasticGunstock, 'e', TGItems.energyCell, 'r', TGItems.obsidianSteelReceiver));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blasterRifle,1),"pcg","bms"," e ", 'g', TGItems.hardenedGlassOrGlass,'p', "plateCarbon", 'c', "circuitElite", 'r', Items.redstone, 'b', TGItems.laserBarrel, 'm', TGItems.carbonReceiver, 's', TGItems.carbonGunstock, 'e', TGItems.energyCell));
        
        //Ammo

        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ak47,1),"bfs","wm ", 'b', TGItems.obsidianSteelBarrel, 'f', TGItems.ironReceiver, 's', TGItems.woodStock, 'w', "logWood",'m', TGItems.m4Mag));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ak47,1,ak47.getMaxDamage()),"bfs","wm ", 'b', TGItems.obsidianSteelBarrel, 'f', TGItems.ironReceiver, 's', TGItems.woodStock, 'w', "logWood",'m', TGItems.m4MagEmpty));
        
       
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(as50,1),"dpd","bbf", " mt", 'f', TGItems.obsidianSteelReceiver, 'b', TGItems.obsidianSteelBarrel, 't', TGItems.plasticGunstock,'m', TGItems.as50Mag, 'd', "gemDiamond", 'p', "plateObsidianSteel"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(as50,1,as50.getMaxDamage()),"dpd","bbf", " mt", 'f', TGItems.obsidianSteelReceiver, 'b', TGItems.obsidianSteelBarrel, 't', TGItems.plasticGunstock,'m', TGItems.as50MagEmpty, 'd', "gemDiamond", 'p', "plateObsidianSteel"));
        
        
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(lasergun,1)," pr","lfs"," mt", 'p', "paneGlass",'r', "dustRedstone", 'l', TGItems.laserBarrel, 'f', TGItems.laserFocus, 's', TGItems.plasticGunstock, 'm', TGItems.energyCell, 't', TGItems.mechanicalPartsT3));
        
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rocketAmmo,8)," t "," s ","sgs", 'g', Items.gunpowder,'s', "ingotSteel", 't', Blocks.tnt));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rocketlauncher,1),"rbb"," f ", 'r', TGItems.rocketAmmo, 'b', TGItems.obsidianSteelBarrel, 'f', TGItems.steelReceiver));

        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(lmg,1), " gr","bfs"," m ", 'g', "paneGlass", 'r', Items.redstone, 'b', TGItems.obsidianSteelBarrel, 'f', TGItems.obsidianSteelReceiver, 's', TGItems.plasticGunstock,'m', TGItems.lmgMag));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(lmg,1,lmg.getMaxDamage()), " gr","bfs"," m ", 'g', "paneGlass", 'r', Items.redstone, 'b', TGItems.obsidianSteelBarrel, 'f', TGItems.obsidianSteelReceiver, 's', TGItems.plasticGunstock,'m', TGItems.lmgMagEmpty));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stielGranate,16), " it"," wi","i  ",'i', "ingotIron",'w', "plankWood", 't', Blocks.tnt));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(minigun,1),"bbe","bbr","bbm", 'b', TGItems.obsidianSteelBarrel, 'e', TGItems.electricEngine, 'r', TGItems.obsidianSteelReceiver, 'm', TGItems.minigunMag));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(minigun,1,minigun.getMaxDamage()),"bbe","bbr","bbm", 'b', TGItems.obsidianSteelBarrel, 'e', TGItems.electricEngine, 'r', TGItems.obsidianSteelReceiver, 'm', TGItems.minigunMagEmpty));
        
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plasticGunstock, 1), "ppp", " bp", 'p', plastic, 'b', "dyeBlack"));
        
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plasticForegrip, 1), "pbp", " p ", " p ", 'p', plastic, 'b', "dyeBlack"));
        
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pdw,1),"brs"," m ", 'b', TGItems.carbonBarrel, 'r', TGItems.carbonReceiver, 's', TGItems.carbonGunstock, 'm', TGItems.pdwMag));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pdw,1,pdw.getMaxDamage()),"brs"," m ", 'b', TGItems.carbonBarrel, 'r', TGItems.carbonReceiver, 's', TGItems.carbonGunstock, 'm', TGItems.pdwMagEmpty));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pulseRifle,1),"dpc","brs"," m ", 'b', TGItems.carbonBarrel, 'r', TGItems.carbonReceiver, 's', TGItems.carbonGunstock, 'm', TGItems.pdwMag, 'd', "gemDiamond", 'p', "plateTitanium", 'c', "circuitElite"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pulseRifle,1,pulseRifle.getMaxDamage()),"dpc","brs"," m ", 'b', TGItems.carbonBarrel, 'r', TGItems.carbonReceiver, 's', TGItems.carbonGunstock, 'm', TGItems.pdwMagEmpty, 'd', "gemDiamond", 'p', "plateTitanium", 'c', "circuitElite"));
         

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(powerHammer,1), " m ", "spr", " mc", 'p', new ItemStack(Blocks.piston,1), 'r', TGItems.ironReceiver, 'm', TGItems.mechanicalParts, 's', "plateIron", 'c', TGItems.compressedAirTank));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(powerHammer,1,powerHammer.getMaxDamage()), " m ", "spr", " mc", 'p', new ItemStack(Blocks.piston,1), 'r', TGItems.ironReceiver, 'm', TGItems.mechanicalParts, 's', "plateIron", 'c', TGItems.compressedAirTankEmpty));
        
        
        GameRegistry.addRecipe(new ShapelessOreRecipeCopyNBT(new ItemStack(powerHammerAdv,1), new ItemStack(powerHammer,1), "gemDiamond", "gemDiamond", "gemDiamond"));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(teslagun,1), " gd", "crs", " e ", 'g', "paneGlass", 'd', "dustRedstone", 'c', TGItems.coil, 'r', TGItems.carbonReceiver, 's', TGItems.carbonGunstock, 'e', TGItems.energyCell));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(teslagun,1,teslagun.getMaxDamage()), " gd", "crs", " e ", 'g', "paneGlass", 'd', "dustRedstone", 'c', TGItems.coil, 'r', TGItems.carbonReceiver, 's', TGItems.carbonGunstock, 'e', TGItems.energyCellEmpty));
        
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(goldenrevolver,1), "ggg","grg","ggg", 'g', "ingotGold", 'r', new ItemStack(revolver,1,0)));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(grimreaper,1), "rr ", "rrc", 'r', new ItemStack(rocketlauncher,1,0), 'c', TGItems.carbonReceiver));
        
        //GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(m4_uq,1), new ItemStack(m4,1,OreDictionary.WILDCARD_VALUE), Items.nether_star));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(m4_uq,1), "gpg","wwm","rri", 'm', new ItemStack(m4,1), 'g', TGItems.hardenedGlassOrGlass, 'p', "plateSteel", 'w', "blockWool",'i',"ingotSteel", 'r', "dustRedstone"));
                
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cyberdemonBlaster,1), "cpc","bns","cpc", 'c', TGItems.cyberneticParts, 'p', "plateObsidianSteel", 'b', TGItems.obsidianSteelBarrel, 'n', TGItems.nethercharge, 's', TGItems.pumpMechanism));
        
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aug,1),"gpg","bfs","i m", 'g', "paneGlass", 'p', "plateSteel", 'b', TGItems.obsidianSteelBarrel, 'f', TGItems.steelReceiver, 's', TGItems.plasticGunstock, 'm', TGItems.m4Mag, 'i', "sheetPlastic"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aug,1,aug.getMaxDamage()),"gpg","bfs","i m", 'g', "paneGlass", 'p', "plateSteel", 'b', TGItems.obsidianSteelBarrel, 'f', TGItems.steelReceiver, 's', TGItems.plasticGunstock, 'm', TGItems.m4MagEmpty, 'i', "sheetPlastic"));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(grenadeLauncher,1,grenadeLauncher.getMaxDamage()), "mrs"," p ", 'm', TGItems.obsidianSteelBarrel, 'r', TGItems.steelReceiver, 's', TGItems.plasticGunstock, 'p', "plateSteel"));
    
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chainsaw,1), "ccp","mmr","ccf", 'c', TGItems.mechanicalParts, 'm', "plateIron", 'r', TGItems.ironReceiver, 'p', TGItems.plasticSheet, 'f', TGItems.flamethrowerTank));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chainsaw,1,chainsaw.getMaxDamage()), "ccp","mmr","ccf", 'c', TGItems.mechanicalParts, 'm', "plateIron", 'r', TGItems.ironReceiver, 'p', TGItems.plasticSheet, 'f', TGItems.flamethrowerTankEmpty));
	
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sonicShotgun,1), " p ", "ers", " c ", 'p', "plateTitanium", 'e', TGItems.sonicEmitter, 'r', TGItems.carbonReceiver, 's', TGItems.carbonGunstock, 'c', TGItems.energyCell));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sonicShotgun,1,sonicShotgun.getMaxDamage()), " p ", "ers", " c ", 'p', "plateTitanium", 'e', TGItems.sonicEmitter, 'r', TGItems.carbonReceiver, 's', TGItems.carbonGunstock, 'c', TGItems.energyCellEmpty));
	
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(nukeLauncher,1)," pp","rbb"," f ", 'r', TGItems.rocketAmmoNuclear, 'b', TGItems.obsidianSteelBarrel, 'f', TGItems.carbonReceiver, 'p', "plateLead"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(nukeLauncher,1,nukeLauncher.getMaxDamage())," pp"," bb"," f ", 'b', TGItems.obsidianSteelBarrel, 'f', TGItems.carbonReceiver, 'p', "plateLead"));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ndr,1),"ltl","ers"," m ", 'l', "plateLead", 't', "plateTitanium", 'e', TGItems.radEmitter, 'r', TGItems.carbonReceiver, 's', TGItems.carbonGunstock, 'm', TGItems.nuclearPowerCell));
       
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ndr,1,ndr.getMaxDamage()),"ltl","ers"," m ", 'l', "plateLead", 't', "plateTitanium", 'e', TGItems.radEmitter, 'r', TGItems.carbonReceiver, 's', TGItems.carbonGunstock, 'm', TGItems.nuclearPowerCellDepleted));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mac10,1)," pi","bri"," m ", 'p', "sheetPlastic", 'i', "nuggetIron", 'r', TGItems.steelReceiver, 'b', TGItems.obsidianSteelBarrel, 'm', TGItems.smgMag));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mac10,1,mac10.getMaxDamage())," pi","bri"," m ", 'p', "sheetPlastic", 'i', "nuggetIron", 'r', TGItems.steelReceiver, 'b', TGItems.obsidianSteelBarrel, 'm', TGItems.smgMagEmpty));        
        
        
        GameRegistry.addRecipe(new RecipeAkimboGun(new ItemStack(akimbo_pistol,1), pistol));  
        GameRegistry.addRecipe(new RecipeAkimboSplit(new ItemStack(pistol,1), akimbo_pistol));
        
        GameRegistry.addRecipe(new RecipeAkimboGun(new ItemStack(akimbo_revoler,1), revolver));
        GameRegistry.addRecipe(new RecipeAkimboSplit(new ItemStack(revolver,1), akimbo_revoler));
        
        GameRegistry.addRecipe(new RecipeAkimboGun(new ItemStack(akimbo_goldenrevolver,1), goldenrevolver));
        GameRegistry.addRecipe(new RecipeAkimboSplit(new ItemStack(goldenrevolver,1), akimbo_goldenrevolver));
        
        GameRegistry.addRecipe(new RecipeAkimboGun(new ItemStack(akimbo_mac10,1), mac10));
        GameRegistry.addRecipe(new RecipeAkimboSplit(new ItemStack(mac10,1), akimbo_mac10));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(scar,1),"gpd","brs", " m ", 'r', TGItems.obsidianSteelReceiver, 'b', TGItems.obsidianSteelBarrel, 's', TGItems.plasticGunstock,'m', TGItems.m4Mag, 'd', "gemDiamond", 'p', "sheetPlastic", 'g', TGItems.hardenedGlassOrGlass));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(scar,1,scar.getMaxDamage()),"gpd","brs", " m ", 'r', TGItems.obsidianSteelReceiver, 'b', TGItems.obsidianSteelBarrel, 's', TGItems.plasticGunstock,'m', TGItems.m4MagEmpty, 'd', "gemDiamond", 'p', "sheetPlastic", 'g', TGItems.hardenedGlassOrGlass));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(vector,1)," gd","brs", " m ", 'r', TGItems.obsidianSteelReceiver, 'b', TGItems.obsidianSteelBarrel, 's', TGItems.plasticGunstock,'m', TGItems.smgMag, 'd', "dustRedstone", 'g', "paneGlass"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(vector,1,vector.getMaxDamage())," gd","brs", " m ", 'r', TGItems.obsidianSteelReceiver, 'b', TGItems.obsidianSteelBarrel, 's', TGItems.plasticGunstock,'m', TGItems.smgMagEmpty, 'd', "dustRedstone", 'g', "paneGlass"));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mibGun,1), "bsp"," fp","  m", 'b', TGItems.laserBarrel, 'f', TGItems.mechanicalPartsT3, 'p', "plateTitanium", 's', TGItems.sonicEmitter, 'm', TGItems.energyCell));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mibGun,1,mibGun.getMaxDamage()), "bsp"," fp","  m", 'b', TGItems.laserBarrel, 'f', TGItems.mechanicalPartsT3, 'p', "plateTitanium", 's', TGItems.sonicEmitter, 'm', TGItems.energyCellEmpty));
       
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fragGrenade,16), " if","iti"," i ",'i', "ingotSteel",'f', Items.flint_and_steel, 't', Blocks.tnt));
        
        
        GameRegistry.addRecipe(new RecipeAkimboGun(new ItemStack(akimbo_mibGun,1), mibGun));
        GameRegistry.addRecipe(new RecipeAkimboSplit(new ItemStack(mibGun,1), akimbo_mibGun));
	}
	
}
