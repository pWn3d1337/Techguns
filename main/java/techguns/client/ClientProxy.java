package techguns.client;


import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.UsernameCache;
import techguns.CommonProxy;
import techguns.TGArmors;
import techguns.TGBlocks;
import techguns.TGConfig;
import techguns.TGItems;
import techguns.TGuns;
import techguns.Techguns;
import techguns.blocks.GenericBlock;
import techguns.client.audio.TGSound;
import techguns.client.audio.TGSoundCategory;
import techguns.client.models.ModelAK;
import techguns.client.models.ModelAS50;
import techguns.client.models.ModelAUG;
import techguns.client.models.ModelAkimbo;
import techguns.client.models.ModelAlienBlaster;
import techguns.client.models.ModelArmorCoat;
import techguns.client.models.ModelBeret;
import techguns.client.models.ModelBiogun;
import techguns.client.models.ModelBlasterRifle;
import techguns.client.models.ModelBoltaction;
import techguns.client.models.ModelChainsaw;
import techguns.client.models.ModelCombatShotgun;
import techguns.client.models.ModelCyberDemon;
import techguns.client.models.ModelCyberdemonBlaster;
import techguns.client.models.ModelExoSuit;
import techguns.client.models.ModelFlamethrower;
import techguns.client.models.ModelFragGrenade;
import techguns.client.models.ModelGenericNPC;
import techguns.client.models.ModelGoldenRevolver;
import techguns.client.models.ModelGrimReaper;
import techguns.client.models.ModelHandgun;
import techguns.client.models.ModelLMG;
import techguns.client.models.ModelLaserGunNew;
import techguns.client.models.ModelLasergun;
import techguns.client.models.ModelM4;
import techguns.client.models.ModelM4_UQ;
import techguns.client.models.ModelMac10;
import techguns.client.models.ModelMibGun;
import techguns.client.models.ModelMinigun;
import techguns.client.models.ModelModel;
import techguns.client.models.ModelNDR;
import techguns.client.models.ModelNeutrinoGun;
import techguns.client.models.ModelPDW;
import techguns.client.models.ModelPistol3;
import techguns.client.models.ModelPowerHammer;
import techguns.client.models.ModelPulseRifle;
import techguns.client.models.ModelRevolver;
import techguns.client.models.ModelRocket;
import techguns.client.models.ModelRocketLauncher;
import techguns.client.models.ModelSawedoff;
import techguns.client.models.ModelScar;
import techguns.client.models.ModelSkeletonSoldier;
import techguns.client.models.ModelSonicShotgun;
import techguns.client.models.ModelSteamArmor;
import techguns.client.models.ModelStielgranate;
import techguns.client.models.ModelSuperMutant;
import techguns.client.models.ModelT3PowerArmor;
import techguns.client.models.ModelTeslaGun;
import techguns.client.models.ModelThompson;
import techguns.client.models.ModelVector;
import techguns.client.models.blocks.ModelAmmoPress;
import techguns.client.models.blocks.ModelBaseTesselator;
import techguns.client.models.blocks.ModelChargingStation;
import techguns.client.models.blocks.ModelChemLab;
import techguns.client.models.blocks.ModelChestInv;
import techguns.client.models.blocks.ModelExpCharge;
import techguns.client.models.blocks.ModelFabricator;
import techguns.client.models.blocks.ModelFlagPost;
import techguns.client.models.blocks.ModelLadder01;
import techguns.client.models.blocks.ModelMetalPress;
import techguns.client.models.blocks.ModelTNTCharge;
import techguns.client.models.blocks.ModelTurret;
import techguns.client.models.blocks.ModelTurretBase;
import techguns.client.particle.EntityLaserBeamFX;
import techguns.client.particle.EntityParticleAnimated;
import techguns.client.particle.EntityParticleAnimated.DynamicColorType;
import techguns.client.particle.EntityParticleSystem;
import techguns.client.particle.EntityParticleTrail;
import techguns.client.particle.FXList;
import techguns.client.renderer.TGRenderHelper.RenderType;
import techguns.client.renderer.block.PaintMachineTesselatorCube;
import techguns.client.renderer.block.RenderDirectionalBlock;
import techguns.client.renderer.block.RenderGenericPane;
import techguns.client.renderer.block.RenderLadder;
import techguns.client.renderer.block.RenderLamp;
import techguns.client.renderer.block.RenderOrecluster;
import techguns.client.renderer.block.RepairBenchTesselatorCube;
import techguns.client.renderer.entity.RenderAlienBug;
import techguns.client.renderer.entity.RenderBiogunProjectile;
import techguns.client.renderer.entity.RenderBlasterRifleProjectile;
import techguns.client.renderer.entity.RenderDummy;
import techguns.client.renderer.entity.RenderFlyingGibs;
import techguns.client.renderer.entity.RenderGenericBullet;
import techguns.client.renderer.entity.RenderGenericLaser;
import techguns.client.renderer.entity.RenderGenericNPC;
import techguns.client.renderer.entity.RenderGrenadeLauncherProjectile;
import techguns.client.renderer.entity.RenderHelicopter;
import techguns.client.renderer.entity.RenderLaserBeam2;
import techguns.client.renderer.entity.RenderMovingBeam;
import techguns.client.renderer.entity.RenderPowerHammerProjectile;
import techguns.client.renderer.entity.RenderRailgunProjectile;
import techguns.client.renderer.entity.RenderRocketProjectile;
import techguns.client.renderer.entity.RenderSimpleProjectile;
import techguns.client.renderer.entity.RenderSimpleTurret;
import techguns.client.renderer.entity.RenderSonicShotgunProjectile;
import techguns.client.renderer.entity.RenderStielgranateProjectile;
import techguns.client.renderer.entity.RenderSuperMutant;
import techguns.client.renderer.entity.RenderTeslaBeam;
import techguns.client.renderer.item.RenderAK;
import techguns.client.renderer.item.RenderAS50;
import techguns.client.renderer.item.RenderAUG;
import techguns.client.renderer.item.RenderAlienBlaster;
import techguns.client.renderer.item.RenderArmorItem;
import techguns.client.renderer.item.RenderBeamgun;
import techguns.client.renderer.item.RenderBiogun;
import techguns.client.renderer.item.RenderBlasterRifle;
import techguns.client.renderer.item.RenderBlockModelItem;
import techguns.client.renderer.item.RenderBlockModelItemChest;
import techguns.client.renderer.item.RenderBlockModelItemTNTCharge;
import techguns.client.renderer.item.RenderBoltActionRifle;
import techguns.client.renderer.item.RenderChainsaw;
import techguns.client.renderer.item.RenderCombatShotgun;
import techguns.client.renderer.item.RenderCyberdemonBlaster;
import techguns.client.renderer.item.RenderFlamethrower;
import techguns.client.renderer.item.RenderFragGrenade;
import techguns.client.renderer.item.RenderGenericItem;
import techguns.client.renderer.item.RenderGrenadeLauncher;
import techguns.client.renderer.item.RenderGrimReaper;
import techguns.client.renderer.item.RenderHandgun;
import techguns.client.renderer.item.RenderLMG;
import techguns.client.renderer.item.RenderLaserGunNew;
import techguns.client.renderer.item.RenderLasergun;
import techguns.client.renderer.item.RenderM4;
import techguns.client.renderer.item.RenderM4_Infiltrator;
import techguns.client.renderer.item.RenderMac10;
import techguns.client.renderer.item.RenderMac10Akimbo;
import techguns.client.renderer.item.RenderMachineBlockItem;
import techguns.client.renderer.item.RenderMachineBlockItemBase;
import techguns.client.renderer.item.RenderMibGun;
import techguns.client.renderer.item.RenderMibGunAkimbo;
import techguns.client.renderer.item.RenderMinigun;
import techguns.client.renderer.item.RenderNeutrinoGun;
import techguns.client.renderer.item.RenderPDW;
import techguns.client.renderer.item.RenderPistol;
import techguns.client.renderer.item.RenderPistolAkimbo;
import techguns.client.renderer.item.RenderPowerHammer;
import techguns.client.renderer.item.RenderPulseRifle;
import techguns.client.renderer.item.RenderRevolver;
import techguns.client.renderer.item.RenderRevolverAkimbo;
import techguns.client.renderer.item.RenderRocketLauncher;
import techguns.client.renderer.item.RenderSawedoff;
import techguns.client.renderer.item.RenderScar;
import techguns.client.renderer.item.RenderSonicShotgun;
import techguns.client.renderer.item.RenderStielgranate;
import techguns.client.renderer.item.RenderTeslaGun;
import techguns.client.renderer.item.RenderThompson;
import techguns.client.renderer.item.RenderVector;
import techguns.client.renderer.tileentity.RenderAmmoPress;
import techguns.client.renderer.tileentity.RenderBlockTileEntModel;
import techguns.client.renderer.tileentity.RenderChargingStation;
import techguns.client.renderer.tileentity.RenderExplosiveCharge;
import techguns.client.renderer.tileentity.RenderFabricator;
import techguns.client.renderer.tileentity.RenderOreDrill;
import techguns.client.renderer.tileentity.RenderReactionChamber;
import techguns.client.renderer.tileentity.RenderTGChest;
import techguns.client.renderer.tileentity.RenderTileEntityBlock;
import techguns.debug.KeyInputHandler;
import techguns.debug.Keybinds;
import techguns.entities.npc.AlienBug;
import techguns.entities.npc.AlienBugDomesticated;
import techguns.entities.npc.ArmySoldier;
import techguns.entities.npc.AttackHelicopter;
import techguns.entities.npc.Bandit;
import techguns.entities.npc.Commando;
import techguns.entities.npc.CyberDemon;
import techguns.entities.npc.DictatorDave;
import techguns.entities.npc.GenericNPC;
import techguns.entities.npc.INPCTechgunsShooter;
import techguns.entities.npc.NPCTurret;
import techguns.entities.npc.Outcast;
import techguns.entities.npc.PsychoSteve;
import techguns.entities.npc.SkeletonSoldier;
import techguns.entities.npc.StormTrooper;
import techguns.entities.npc.SuperMutantBasic;
import techguns.entities.npc.SuperMutantElite;
import techguns.entities.npc.SuperMutantHeavy;
import techguns.entities.npc.ZombieFarmer;
import techguns.entities.npc.ZombieMiner;
import techguns.entities.npc.ZombiePigmanSoldier;
import techguns.entities.npc.ZombieSoldier;
import techguns.entities.projectiles.AlienBlasterProjectile;
import techguns.entities.projectiles.BeamProjectile;
import techguns.entities.projectiles.BiogunProjectile;
import techguns.entities.projectiles.BlasterRifleProjectile;
import techguns.entities.projectiles.CyberdemonBlasterProjectile;
import techguns.entities.projectiles.EnergyBlastProjectile;
import techguns.entities.projectiles.FlamethrowerProjectile;
import techguns.entities.projectiles.FlyingGibs;
import techguns.entities.projectiles.FragGrenadeProjectile;
import techguns.entities.projectiles.GenericProjectile;
import techguns.entities.projectiles.GrenadeLauncherProjectile;
import techguns.entities.projectiles.GrenadeProjectile;
import techguns.entities.projectiles.InvisibleProjectile;
import techguns.entities.projectiles.LaserBeam;
import techguns.entities.projectiles.LaserBeam2;
import techguns.entities.projectiles.LaserProjectile;
import techguns.entities.projectiles.MovingBeam;
import techguns.entities.projectiles.NeutrinoBeam;
import techguns.entities.projectiles.NukeRocketProjectile;
import techguns.entities.projectiles.PowerHammerProjectile;
import techguns.entities.projectiles.RailgunProjectile;
import techguns.entities.projectiles.RocketProjectile;
import techguns.entities.projectiles.SimpleProjectile;
import techguns.entities.projectiles.SonicShotgunProjectile;
import techguns.entities.projectiles.TeslaBeam;
import techguns.entities.projectiles.TeslaProjectile;
import techguns.events.GuiOverlayAmmoCount;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.extendedproperties.TechgunsExtendedPlayerPropertiesClient;
import techguns.items.guns.GenericGun;
import techguns.keybinds.TGKeybinds;
import techguns.tileentities.AmmoPressTileEnt;
import techguns.tileentities.CamoBenchTileEnt;
import techguns.tileentities.CampFlagTileEnt;
import techguns.tileentities.ChargingStationTileEnt;
import techguns.tileentities.ChemLabTileEnt;
import techguns.tileentities.ExplosiveChargeAdvTileEnt;
import techguns.tileentities.ExplosiveChargeTileEnt;
import techguns.tileentities.FabricatorTileEntMaster;
import techguns.tileentities.MetalPressTileEnt;
import techguns.tileentities.OreDrillTileEntMaster;
import techguns.tileentities.ReactionChamberTileEntMaster;
import techguns.tileentities.RepairBenchTileEnt;
import techguns.tileentities.TGChestTileEnt;
import techguns.tileentities.TurretBaseEnt;
import techguns.util.EntityDeathUtils.DeathType;
import techguns.util.MathUtil;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
//import com.cricketcraft.ctmlib.RendererCTM;

public class ClientProxy extends CommonProxy {
	
	    public int renderPass;
	
		@SideOnly(Side.CLIENT)
		public float player_zoom=1.0f;

		@SideOnly(Side.CLIENT)
		public TGVersionChecker versionChecker;
		
		public ResourceLocation player_inv_texture = new ResourceLocation("techguns:textures/gui/TGPlayerInventory.png");
		
		//Gun
		
		//@SideOnly(Side.CLIENT)
		//public static long player_reloadtime=0;
		
		//@SideOnly(Side.CLIENT)
		//public static long player_reloadtime_total=0;
		
		//@SideOnly(Side.CLIENT)
		//public static float player_fov=1.0f; //multiplier to player fov
		
		//@SideOnly(Side.CLIENT)
		//public static long player_recoiltime=0;
		
		//@SideOnly(Side.CLIENT)
		//public static long player_recoiltime_total=0;
		
		@SideOnly(Side.CLIENT)
		public HashMap<String, DeathType> entityDeathTypes = new HashMap<String, DeathType>();
		
		//@SideOnly(Side.CLIENT)
		//public static HashMap<String,Long> recoiltime = new HashMap<String,Long>();
		
		//@SideOnly(Side.CLIENT)
		//public static HashMap<String,Integer> recoiltime_total = new HashMap<String,Integer>();
		
		//@SideOnly(Side.CLIENT)
		//public static HashMap<String,Long> reloadtime = new HashMap<String,Long>();
		
		//@SideOnly(Side.CLIENT)
		//public static HashMap<String,Integer> reloadtime_total = new HashMap<String,Integer>();
		
		
		public HashMap<String,AttackTime> attackTimes = new HashMap<String,AttackTime>();
		
		
		@SideOnly(Side.CLIENT)
		public long player_muzzleFlashtime=0;
		
		@SideOnly(Side.CLIENT)
		public long player_muzzleFlashtime_total=0;
		
		@SideOnly(Side.CLIENT)
		public long lastShotFired = 0;
		
		@SideOnly(Side.CLIENT)
		public long lastReloadsoundPlayed=0L;
		
		//public static long player_swingAnimTime=0;
		//public static int player_swingAnimTimeTotal=0;
		
		//ROCKETLAUNCHER
	/*	@SideOnly(Side.CLIENT)
		public boolean showWarhead=true;*/
		
		@SideOnly(Side.CLIENT)
		public boolean hasStepassist=false;
		
		@SideOnly(Side.CLIENT)
		public boolean hasNightvision=false;
		
		
		@SideOnly(Side.CLIENT)
		/**
		 * SteamArmor (0,1)
		 * PowerArmor (2,3)
		 * ExoSuit (4,5,6)
		 */
		public static ModelBiped[] armorModels = {new ModelSteamArmor(0), new ModelSteamArmor(1), new ModelT3PowerArmor(0),new ModelT3PowerArmor(1), new ModelExoSuit(0,1.0f), new ModelExoSuit(1,0.5f),new ModelExoSuit(0,0.75f), new ModelBeret(), new ModelArmorCoat(0,1.0f), new ModelArmorCoat(1,0.51f), new ModelArmorCoat(2,0.49f), new ModelArmorCoat(3,0.75f) };
		
		/**
		 * needed for jetpack movement
		 */
		//public static boolean jumpKeyPressed=false;
		
		/**
		 * needed for jetpack movement
		 */
		public boolean forwardKeyPressed=false;
		
		public boolean keyFirePressed=false;
		
		public static ClientProxy get(){
			return (ClientProxy) Techguns.proxy;
		}
		
        @Override
        public void registerRenderers() {
        		//System.out.println("##RegisterRenderers");
        		
        		
        		//Entities
                RenderingRegistry.registerEntityRenderingHandler(GenericProjectile.class, new RenderGenericBullet());
                RenderingRegistry.registerEntityRenderingHandler(SimpleProjectile.class, new RenderSimpleProjectile(1.0f, "techguns:textures/entity/handgunBullet.png"));
                RenderingRegistry.registerEntityRenderingHandler(FlamethrowerProjectile.class, new RenderDummy()); //new RenderFlamethrowerProjectile(1.5f, "techguns:textures/fx/fireball.png"));
                RenderingRegistry.registerEntityRenderingHandler(LaserProjectile.class, new RenderDummy());
                RenderingRegistry.registerEntityRenderingHandler(TeslaProjectile.class, new RenderDummy());
                RenderingRegistry.registerEntityRenderingHandler(LaserBeam.class, new RenderGenericLaser());
                RenderingRegistry.registerEntityRenderingHandler(TeslaBeam.class, new RenderTeslaBeam(0.25D));
                RenderingRegistry.registerEntityRenderingHandler(RocketProjectile.class, new RenderRocketProjectile(new ModelRocket(), "techguns:textures/guns/rocketlauncher.png"));
                RenderingRegistry.registerEntityRenderingHandler(NukeRocketProjectile.class, new RenderRocketProjectile(new ModelRocket(), "techguns:textures/guns/nukerocketlauncher.png"));
                RenderingRegistry.registerEntityRenderingHandler(BiogunProjectile.class, new RenderBiogunProjectile("techguns:textures/entity/bioblob.png"));
                //RenderingRegistry.registerEntityRenderingHandler(BlasterProjectile.class,new RenderBlasterProjectile());
                RenderingRegistry.registerEntityRenderingHandler(RailgunProjectile.class, new RenderRailgunProjectile());            
                RenderingRegistry.registerEntityRenderingHandler(CyberdemonBlasterProjectile.class, new RenderDummy());
                RenderingRegistry.registerEntityRenderingHandler(PowerHammerProjectile.class, new RenderPowerHammerProjectile());
                RenderingRegistry.registerEntityRenderingHandler(GrenadeProjectile.class, new RenderStielgranateProjectile(new ModelStielgranate(), "techguns:textures/guns/stielgranate.png"));
                RenderingRegistry.registerEntityRenderingHandler(AlienBlasterProjectile.class, new RenderDummy());
                RenderingRegistry.registerEntityRenderingHandler(EnergyBlastProjectile.class, new RenderDummy());
                RenderingRegistry.registerEntityRenderingHandler(BlasterRifleProjectile.class, new RenderBlasterRifleProjectile());
                RenderingRegistry.registerEntityRenderingHandler(FlyingGibs.class, new RenderFlyingGibs());
                //RenderingRegistry.registerEntityRenderingHandler(TurretEntity.class, new RenderTurret(new ModelHandgun(), 1.0f, "techguns:textures/guns/handgun.png"));
                RenderingRegistry.registerEntityRenderingHandler(EntityParticleSystem.class, new RenderDummy());
                RenderingRegistry.registerEntityRenderingHandler(LaserBeam2.class, new RenderLaserBeam2());
                RenderingRegistry.registerEntityRenderingHandler(GrenadeLauncherProjectile.class, new RenderGrenadeLauncherProjectile(new ModelModel("models/entity/launchergrenade.obj"),"techguns:textures/entity/launchergrenade.png"));
                RenderingRegistry.registerEntityRenderingHandler(SonicShotgunProjectile.class, new RenderSonicShotgunProjectile());
                RenderingRegistry.registerEntityRenderingHandler(InvisibleProjectile.class, new RenderDummy());
                RenderingRegistry.registerEntityRenderingHandler(MovingBeam.class, new RenderMovingBeam());
                RenderingRegistry.registerEntityRenderingHandler(BeamProjectile.class, new RenderDummy());
                RenderingRegistry.registerEntityRenderingHandler(NeutrinoBeam.class, new RenderMovingBeam());
                //NPCs
				RenderingRegistry.registerEntityRenderingHandler(GenericNPC.class, new RenderGenericNPC( new ModelGenericNPC(), "textures/entity/zombie/zombie.png", 0.5F ));
				RenderingRegistry.registerEntityRenderingHandler(ZombieSoldier.class, new RenderGenericNPC( new ModelGenericNPC(), "techguns:textures/entity/zombie_soldier.png", 0.5F ));
				RenderingRegistry.registerEntityRenderingHandler(ZombiePigmanSoldier.class, new RenderGenericNPC( new ModelGenericNPC(), "textures/entity/zombie_pigman.png", 0.5F ));
				RenderingRegistry.registerEntityRenderingHandler(CyberDemon.class, new RenderGenericNPC( new ModelCyberDemon(), "techguns:textures/entity/cyberdemon.png", 0.75F, -0.11f, 0.15f, -0.18f));
				RenderingRegistry.registerEntityRenderingHandler(ArmySoldier.class, new RenderGenericNPC( new ModelGenericNPC(), "techguns:textures/entity/army_soldier.png", 0.5F ));

				RenderingRegistry.registerEntityRenderingHandler(NPCTurret.class, new RenderSimpleTurret( new ModelTurret()));
				RenderingRegistry.registerEntityRenderingHandler(AttackHelicopter.class, new RenderHelicopter("models/entity/apache.obj", "textures/entity/apache.png"));

				RenderingRegistry.registerEntityRenderingHandler(ZombieFarmer.class, new RenderGenericNPC( new ModelGenericNPC(), "techguns:textures/entity/zombie_soldier.png", 0.5F ));
				RenderingRegistry.registerEntityRenderingHandler(ZombieMiner.class, new RenderGenericNPC( new ModelGenericNPC(), "techguns:textures/entity/zombie_soldier.png", 0.5F ));
				
				RenderingRegistry.registerEntityRenderingHandler(Bandit.class, new RenderGenericNPC( new ModelGenericNPC(), "techguns:textures/entity/bandit.png", 0.5F ));
				RenderingRegistry.registerEntityRenderingHandler(Outcast.class, new RenderGenericNPC( new ModelGenericNPC(), "techguns:textures/entity/army_soldier.png", 0.5F ));

				RenderingRegistry.registerEntityRenderingHandler(SkeletonSoldier.class, new RenderGenericNPC( new ModelSkeletonSoldier(), "textures/entity/skeleton/skeleton.png", 0.5F, 0.1f,0f,0.05f));

				RenderingRegistry.registerEntityRenderingHandler(PsychoSteve.class, new RenderGenericNPC( new ModelGenericNPC(), "techguns:textures/entity/army_soldier.png", 0.5F ));
				RenderingRegistry.registerEntityRenderingHandler(DictatorDave.class, new RenderGenericNPC( new ModelGenericNPC(), "techguns:textures/entity/dictator.png", 0.5F ));
				
				RenderingRegistry.registerEntityRenderingHandler(AlienBug.class, new RenderAlienBug());
				RenderingRegistry.registerEntityRenderingHandler(AlienBugDomesticated.class, new RenderAlienBug());
                
				 RenderingRegistry.registerEntityRenderingHandler(FragGrenadeProjectile.class, new RenderStielgranateProjectile(new ModelFragGrenade(false), "techguns:textures/guns/fragGrenade_texture.png"));
	            
				 RenderingRegistry.registerEntityRenderingHandler(Commando.class, new RenderGenericNPC( new ModelGenericNPC(), "techguns:textures/entity/army_soldier.png", 0.5F ));
				
				 RenderingRegistry.registerEntityRenderingHandler(StormTrooper.class, new RenderGenericNPC( new ModelGenericNPC(), "techguns:textures/entity/army_soldier.png", 0.5F ));
				
				 RenderingRegistry.registerEntityRenderingHandler(SuperMutantBasic.class, new RenderSuperMutant( new ModelSuperMutant(), "techguns:textures/entity/supermutant_texture_", 0.6F ,-0.1f, 0.3f, 0.f, 0 ));
				 RenderingRegistry.registerEntityRenderingHandler(SuperMutantHeavy.class, new RenderSuperMutant( new ModelSuperMutant(), "techguns:textures/entity/supermutant_texture_", 0.65F ,-0.15f, 0.3f, 0.f, 1 ));
				 RenderingRegistry.registerEntityRenderingHandler(SuperMutantElite.class, new RenderSuperMutant( new ModelSuperMutant(), "techguns:textures/entity/supermutant_texture_", 0.7F ,-0.2f, 0.4f, 0.f, 2 ));
							
				 
                //Items
              //  MinecraftForgeClient.registerItemRenderer(Techguns.testGun, new RenderHandgun(new ModelHandgun(), new ResourceLocation("techguns","textures/guns/handgun.png"), 1.0f, 0.f, 0.f, 0.f));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.handcannon, new RenderHandgun(new ModelHandgun(), new ResourceLocation("techguns","textures/guns/handgun.png"), 1.0f, 0.f, 0.f, 0.f));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.pistol, new RenderPistol(new ModelPistol3(), new ResourceLocation("techguns","textures/guns/pistol3.png"), 1.0f, 0.f, 0.f, 0.f));
               
                MinecraftForgeClient.registerItemRenderer(TGuns.sawedoff, new RenderSawedoff(new ModelSawedoff(), new ResourceLocation("techguns","textures/guns/sawedOff.png"), 1.0f, 0.f, 0.f, 0.f));
                
                //MinecraftForgeClient.registerItemRenderer(TGuns.mp5, new RenderMP5(new ModelMp5(), new ResourceLocation("techguns","textures/guns/mp5Texture.png"), 1.0f, 0.f, 0.f, 0.f));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.boltaction, new RenderBoltActionRifle(new ModelBoltaction(), new ResourceLocation("techguns","textures/guns/boltactionrifle.png"), 1.0f, 0.f, 0.f, 0.f));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.flamethrower, new RenderFlamethrower(new ModelFlamethrower(), new ResourceLocation("techguns","textures/guns/Flamethrower.png"), 1.0f, 0.f, 0.f, 0.f));
                MinecraftForgeClient.registerItemRenderer(TGuns.m4, new RenderM4(new ModelM4(), new ResourceLocation("techguns","textures/guns/m4Texture.png"), 1.0f, 0.f, 0.f, 0.f, false,false));

               // MinecraftForgeClient.registerItemRenderer(Techguns.m4Mag, new RenderM4Mag(new ModelM4Mag(), new ResourceLocation("techguns","textures/guns/m4Texture.png"), 1.0f, 0.f, 0.f, 0.f));
               // MinecraftForgeClient.registerItemRenderer(Techguns.m4MagEmpty, new RenderM4Mag(new ModelM4MagE(), new ResourceLocation("techguns","textures/guns/m4Texture.png"), 1.0f, 0.f, 0.f, 0.f));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.combatShotgun, new RenderCombatShotgun(new ModelCombatShotgun(), new ResourceLocation("techguns","textures/guns/combatShotgun.png"), 1.0f, 0.f, 0.f, 0.f));
                MinecraftForgeClient.registerItemRenderer(TGuns.lasergun, new RenderLasergun(new ModelLasergun(), new ResourceLocation("techguns","textures/guns/Lasergun.png"), 1.0f, 0.f, 0.f, 0.f));
                MinecraftForgeClient.registerItemRenderer(TGuns.blasterRifle, new RenderBlasterRifle(new ModelBlasterRifle(), new ResourceLocation("techguns","textures/guns/blasterRifle.png"), 1.0f, 0.f, 0.f, 0.f));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.rocketlauncher, new RenderRocketLauncher(new ModelRocketLauncher(), new ResourceLocation("techguns","textures/guns/rocketlauncher.png"), 1.0f, 0.f, 0.f, 0.f, true, false));
            //    MinecraftForgeClient.registerItemRenderer(Techguns.rocketAmmo, new RenderRocketAmmo(new ModelRocket(), new ResourceLocation("techguns","textures/guns/rocketlauncher.png"), 1.0f, 0.f, 0.f, 0.f));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.biogun, new RenderBiogun(new ModelBiogun(), new ResourceLocation("techguns","textures/guns/BioGun.png"), 1.0f, 0.f, 0.f, 0.f));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.thompson, new RenderThompson(new ModelThompson(), new ResourceLocation("techguns","textures/guns/thompson.png"), 1.0f, 0.f, 0.f, 0.f));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.revolver, new RenderRevolver(new ModelRevolver(), new ResourceLocation("techguns","textures/guns/revolver.png"), 1.0f, 0.f, 0.f, 0.f));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.ak47, new RenderAK(new ModelAK(), new ResourceLocation("techguns","textures/guns/ak47Texture.png"), 1.0f, 0.f, 0.f, 0.f));

               // MinecraftForgeClient.registerItemRenderer(TGuns.railgun, new RenderLasergun(new ModelLasergun(), new ResourceLocation("techguns","textures/guns/Lasergun.png"), 1.0f, 0.f, 0.f, 0.f));

                MinecraftForgeClient.registerItemRenderer(TGuns.as50, new RenderAS50(new ModelAS50(), new ResourceLocation("techguns","textures/guns/as50Texture.png"), 1.0f, 0.f, 0.f, 0.f));

          //      MinecraftForgeClient.registerItemRenderer(Techguns.as50Mag, new RenderAS50Mag(new ModelAS50Mag(false), new ResourceLocation("techguns","textures/guns/as50Texture.png"), 1.0f, 0.f, 0.f, 0.f));
                MinecraftForgeClient.registerItemRenderer(TGuns.teslagun, new RenderTeslaGun(new ModelTeslaGun(), new ResourceLocation("techguns","textures/guns/TeslaGun.png"), 1.0f, 0.f, 0.f, 0.f));
                
           //     MinecraftForgeClient.registerItemRenderer(Techguns.as50MagEmpty, new RenderAS50Mag(new ModelAS50Mag(true), new ResourceLocation("techguns","textures/guns/as50Texture.png"), 1.0f, 0.f, 0.f, 0.f));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.pdw, new RenderPDW(new ModelPDW(), new ResourceLocation("techguns","textures/guns/PDW.png"), 1.0f, 0.f, 0.f, 0.f));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.lmg, new RenderLMG(new ModelLMG(), new ResourceLocation("techguns","textures/guns/mg2_texture.png"), 1.0f, 0.f, 0.f, 0.f));

         //       MinecraftForgeClient.registerItemRenderer(Techguns.lmgMag, new RenderLMGMag(new ModelLmgMag(), new ResourceLocation("techguns","textures/guns/mg2_texture.png"), 1.0f, 0.f, 0.f, 0.f));
         //       MinecraftForgeClient.registerItemRenderer(Techguns.lmgMagEmpty, new RenderLMGMag(new ModelLmgMagE(), new ResourceLocation("techguns","textures/guns/mg2_texture.png"), 1.0f, 0.f, 0.f, 0.f));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.m4_uq, new RenderM4_Infiltrator(new ModelM4_UQ(), new ResourceLocation("techguns","textures/guns/m4_uq_Texture.png"), 1.0f, 0.f, 0.f, 0.f));
                MinecraftForgeClient.registerItemRenderer(TGuns.grimreaper, new RenderGrimReaper(new ModelGrimReaper(), new ResourceLocation("techguns","textures/guns/grimreaper.png"), 1.0f, 0.f, 0.f, 0.f, false, false));
                MinecraftForgeClient.registerItemRenderer(TGuns.goldenrevolver, new RenderRevolver(new ModelGoldenRevolver(), new ResourceLocation("techguns","textures/guns/goldenrevolver.png"), 1.10f, 0.f, 0.f, 0.f));
               // MinecraftForgeClient.registerItemRenderer(TGuns.driller, new RenderDriller(new ModelDriller(), new ResourceLocation("techguns","textures/guns/drillTexture.png"), 1.0f, 0.f, 0.f, 0.f));

                MinecraftForgeClient.registerItemRenderer(TGuns.pulseRifle, new RenderPulseRifle(new ModelPulseRifle(), new ResourceLocation("techguns","textures/guns/pulseRifle.png"), 1.0f, 0.f, 0.f, 0.f));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.alienBlaster, new RenderAlienBlaster(new ModelAlienBlaster(), new ResourceLocation("techguns","textures/guns/alien_blaster.png"), 1.0f, 0.f, 0.f, 0.f));
                MinecraftForgeClient.registerItemRenderer(TGuns.cyberdemonBlaster, new RenderCyberdemonBlaster(new ModelCyberdemonBlaster(), new ResourceLocation("techguns","textures/guns/cyberdemonblaster.png"), 1.0f, 0.f, 0.f, 0.f));
                MinecraftForgeClient.registerItemRenderer(TGuns.minigun, new RenderMinigun(new ModelMinigun(), new ResourceLocation("techguns","textures/guns/minigun.png"), 1.0f, 0.f, 0.f, 0.f));

                MinecraftForgeClient.registerItemRenderer(TGuns.powerHammer, new RenderPowerHammer(new ModelPowerHammer(), new ResourceLocation("techguns","textures/guns/powerHammer.png"), 1.0f, 0.f, 0.f, 0.f));
                MinecraftForgeClient.registerItemRenderer(TGuns.nukeLauncher, new RenderRocketLauncher(new ModelRocketLauncher(), new ResourceLocation("techguns","textures/guns/nukerocketlauncher.png"), 1.0f, 0.f, 0.f, 0.f, true, false));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.powerHammerAdv, new RenderPowerHammer(new ModelPowerHammer(), new ResourceLocation("techguns","textures/guns/powerHammerAdv.png"), 1.0f, 0.f, 0.f, 0.f));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.aug, new RenderAUG(new ModelAUG(), new ResourceLocation("techguns","textures/guns/AugTexture.png"), 1.0f, 0.f, 0.f, 0.f, true,true));
                MinecraftForgeClient.registerItemRenderer(TGuns.grenadeLauncher, new RenderGrenadeLauncher(new ModelModel("models/guns/grenadelauncher.obj"), new ResourceLocation("techguns","textures/guns/grenadelauncher.png"), 0.0625f, 0.f, 0.f, 0.f));
                MinecraftForgeClient.registerItemRenderer(TGuns.sonicShotgun, new RenderSonicShotgun(new ModelSonicShotgun(), new ResourceLocation("techguns","textures/guns/sonicshotgun.png"), 0.0625f, 0.f, 0.f, 0.f));
                MinecraftForgeClient.registerItemRenderer(TGuns.chainsaw, new RenderChainsaw(new ModelChainsaw(), new ResourceLocation("techguns","textures/guns/chainsaw.png"), 0.0625f, 0.f, 0.f, 0.f));

                MinecraftForgeClient.registerItemRenderer(TGuns.scatterBeamGun, new RenderLaserGunNew(new ModelLaserGunNew(), new ResourceLocation("techguns","textures/guns/LasergunNew.png"), 0.0625f, 0.f, 0.f, 0.f));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.ndr, new RenderBeamgun(new ModelNDR(), new ResourceLocation("techguns","textures/guns/Lasergun.png"), 1.0f, 0.f, 0.f, 0.f));
                
                
                MinecraftForgeClient.registerItemRenderer(TGItems.sharedItem, new RenderGenericItem());
                
                //grenades
                MinecraftForgeClient.registerItemRenderer(TGuns.stielGranate, new RenderStielgranate(new ModelStielgranate(), new ResourceLocation("techguns","textures/guns/stielgranate.png"), 1.0f, 0.f, 0.f, 0.f));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.fragGrenade, new RenderFragGrenade(new ModelFragGrenade(true), new ResourceLocation("techguns","textures/guns/fragGrenade_texture.png"), 1.0f, 0.f, 0.f, 0.f));
                

                MinecraftForgeClient.registerItemRenderer(TGuns.akimbo_pistol, new RenderPistolAkimbo(new ModelAkimbo(new ModelPistol3(), 2), new ResourceLocation("techguns","textures/guns/pistol3.png"), 1.0f, 0.f, 0.f, 0.f, true, false, 2, false));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.akimbo_revoler, new RenderRevolverAkimbo(new ModelAkimbo(new ModelRevolver(), 1), new ResourceLocation("techguns","textures/guns/revolver.png"), 1.0f, 0.f, 0.f, 0.f, true, false, 1, false));
                MinecraftForgeClient.registerItemRenderer(TGuns.akimbo_goldenrevolver, new RenderRevolverAkimbo(new ModelAkimbo(new ModelGoldenRevolver(), 1), new ResourceLocation("techguns","textures/guns/goldenrevolver.png"), 1.0f, 0.f, 0.f, 0.f, true, false, 1, false));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.mac10, new RenderMac10(new ModelMac10(), new ResourceLocation("techguns","textures/guns/mac10texture.png"), 1.0f, 0.f, 0.f, 0.f));
                MinecraftForgeClient.registerItemRenderer(TGuns.akimbo_mac10, new RenderMac10Akimbo(new ModelAkimbo(new ModelMac10(),1), new ResourceLocation("techguns","textures/guns/mac10texture.png"), 1.0f, 0.f, 0.f, 0.f, true, false, 1, false));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.mibGun, new RenderMibGun(new ModelMibGun(), new ResourceLocation("techguns","textures/guns/mibgun.png"), 1.0f, 0.f, 0.f, 0.f));
                  
                MinecraftForgeClient.registerItemRenderer(TGuns.neutrinoGun, new RenderNeutrinoGun(new ModelNeutrinoGun(), new ResourceLocation("techguns","textures/guns/neutrinogun.png"), 1.0f, 0.f, 0.f, 0.f));
                
                MinecraftForgeClient.registerItemRenderer(TGuns.scar, new RenderScar(new ModelScar(), new ResourceLocation("techguns","textures/guns/scar_texture.png"), 1.0f, 0.f, 0.f, 0.f, true,true));

                MinecraftForgeClient.registerItemRenderer(TGuns.vector, new RenderVector(new ModelVector(), new ResourceLocation("techguns","textures/guns/vector_texture.png"), 1.0f, 0.f, 0.f, 0.f));

                MinecraftForgeClient.registerItemRenderer(TGuns.akimbo_mibGun, new RenderMibGunAkimbo(new ModelAkimbo(new ModelMibGun(),1), new ResourceLocation("techguns","textures/guns/mibgun.png"), 1.0f, 0.f, 0.f, 0.f, true, false, 1, false));
                
                
                //Blocks
                
                
                //ARMORS
                //MinecraftForgeClient.registerItemRenderer(Armors.steam_Chestplate, new ItemRenderer(new ModelPulseRifle(), new ResourceLocation("techguns","textures/guns/pulseRifle.png"), 1.0f, 0.f, 0.f, 0.f));
                ModelBiped armorModel1 = new ModelSteamArmor(0);
        		ModelBiped armorModel2 = new ModelSteamArmor(1);
                MinecraftForgeClient.registerItemRenderer(TGArmors.steam_Helmet, new RenderArmorItem(armorModel1, 0, "steam_armor.png"));
                MinecraftForgeClient.registerItemRenderer(TGArmors.steam_Chestplate, new RenderArmorItem(armorModel1, 1, "steam_armor.png"));
                MinecraftForgeClient.registerItemRenderer(TGArmors.steam_Leggings, new RenderArmorItem(armorModel2, 2, "steam_armor.png"));
                MinecraftForgeClient.registerItemRenderer(TGArmors.steam_Boots, new RenderArmorItem(armorModel1, 3, "steam_armor.png"));
                
                ModelBiped armorModel3 = new ModelT3PowerArmor(0);
        		ModelBiped armorModel4 = new ModelT3PowerArmor(1);
                MinecraftForgeClient.registerItemRenderer(TGArmors.t3_power_Helmet, new RenderArmorItem(armorModel3, 0, "powerarmor.png"));
                MinecraftForgeClient.registerItemRenderer(TGArmors.t3_power_Chestplate, new RenderArmorItem(armorModel3, 1, "powerarmor.png"));
                MinecraftForgeClient.registerItemRenderer(TGArmors.t3_power_Leggings, new RenderArmorItem(armorModel4, 2, "powerarmor.png"));
                MinecraftForgeClient.registerItemRenderer(TGArmors.t3_power_Boots, new RenderArmorItem(armorModel3, 3, "powerarmor.png"));
                
                //MinecraftForgeClient.registerItemRenderer(TGArmors.t2_beret, new RenderArmorItem(new ModelBeret(), 0, "beret_texture.png"));
                
               /* ModelBiped armorModel5 = new ModelExoSuit(0,1.0f);
        		ModelBiped armorModel6 = new ModelExoSuit(1,0.75f);
        		//ModelBiped armorModel7 = new ModelExoSuit(2);
                MinecraftForgeClient.registerItemRenderer(Armors.t3_exo_Helmet, new RenderArmorItem(armorModel5, 0, "t3_exo_1.png"));
                MinecraftForgeClient.registerItemRenderer(Armors.t3_exo_Chestplate, new RenderArmorItem(armorModel5, 1, "t3_exo_1.png"));
                MinecraftForgeClient.registerItemRenderer(Armors.t3_exo_Leggings, new RenderArmorItem(armorModel6, 2, "t3_exo_2.png"));
                MinecraftForgeClient.registerItemRenderer(Armors.t3_exo_Boots, new RenderArmorItem(armorModel5, 3, "t3_exo_1.png"));*/
                
                
                //Blocks
                ClientRegistry.bindTileEntitySpecialRenderer(AmmoPressTileEnt.class, new RenderAmmoPress(new ModelAmmoPress(), "AmmoPress.png"));
                ClientRegistry.bindTileEntitySpecialRenderer(MetalPressTileEnt.class, new RenderAmmoPress(new ModelMetalPress(), "MetalPress.png"));
                ClientRegistry.bindTileEntitySpecialRenderer(ChemLabTileEnt.class, new RenderAmmoPress(new ModelChemLab(), "ChemLab.png"));
                
                ClientRegistry.bindTileEntitySpecialRenderer(TurretBaseEnt.class, new RenderBlockTileEntModel(new ModelTurretBase(), new ResourceLocation("techguns","textures/blocks/TurretBase.png")));
                ClientRegistry.bindTileEntitySpecialRenderer(CamoBenchTileEnt.class, new RenderTileEntityBlock(new PaintMachineTesselatorCube()));
                ClientRegistry.bindTileEntitySpecialRenderer(RepairBenchTileEnt.class, new RenderTileEntityBlock(new RepairBenchTesselatorCube()));
                ClientRegistry.bindTileEntitySpecialRenderer(CampFlagTileEnt.class, new RenderBlockTileEntModel(new ModelFlagPost(), new ResourceLocation("techguns","textures/blocks/flagpost.png")));
                
                ClientRegistry.bindTileEntitySpecialRenderer(ExplosiveChargeTileEnt.class, new RenderExplosiveCharge(new ModelTNTCharge(),new ResourceLocation("techguns","textures/blocks/tntCharge.png"),new ResourceLocation("techguns","textures/blocks/tntCharge_2.png")));
                ClientRegistry.bindTileEntitySpecialRenderer(ExplosiveChargeAdvTileEnt.class, new RenderExplosiveCharge(new ModelExpCharge(),new ResourceLocation("techguns","textures/blocks/ExpCharge.png"),new ResourceLocation("techguns","textures/blocks/ExpCharge_2.png")));
                
                
                ClientRegistry.bindTileEntitySpecialRenderer(OreDrillTileEntMaster.class, new RenderOreDrill());
                
                ClientRegistry.bindTileEntitySpecialRenderer(ReactionChamberTileEntMaster.class, new RenderReactionChamber("models/entity/reactionchamber.obj","textures/entity/reactionchamber.png"));
                
                ClientRegistry.bindTileEntitySpecialRenderer(ChargingStationTileEnt.class, new RenderChargingStation(new ModelChargingStation(), "ChargingStation.png"));
                
                ClientRegistry.bindTileEntitySpecialRenderer(FabricatorTileEntMaster.class, new RenderFabricator(new ModelFabricator(), "Fabricator.png"));
                
                ClientRegistry.bindTileEntitySpecialRenderer(TGChestTileEnt.class, new RenderTGChest());
                
                //MinecraftForgeClient.registerItemRenderer((Item.getItemFromBlock(TGBlocks.basicMachine))., new RenderBlockModelItem(new ModelAmmoPress(), new ResourceLocation("techguns","textures/blocks/AmmoPress.png"), 1.0f, 0.f, 0.f, 0.f));

               /* ModelBase[] modelMachines= {new ModelAmmoPress(),new ModelMetalPress(),new ModelTurretBase(),new ModelBaseTesselator(new PaintMachineTesselatorCube()),
                		new ModelChemLab(),new ModelBaseTesselator(new RepairBenchTesselatorCube()),new ModelTNTCharge(135.0f,1.2f), new ModelBaseTesselator(new RepairBenchTesselatorCube())};
                ResourceLocation[] textures = {new ResourceLocation("techguns","textures/blocks/AmmoPress.png"), new ResourceLocation("techguns","textures/blocks/MetalPress.png"), new ResourceLocation("techguns","textures/blocks/TurretBase.png"),
                		new ResourceLocation("techguns","textures/blocks/paintmachine_textures.png"),new ResourceLocation("techguns","textures/blocks/ChemLab.png"),new ResourceLocation("techguns","textures/blocks/repairbench_textures.png"),
                		new ResourceLocation("techguns","textures/blocks/tntCharge.png"), new ResourceLocation("techguns","repairbench_textures.png")};
                MinecraftForgeClient.registerItemRenderer((Item.getItemFromBlock(TGBlocks.basicMachine)), new RenderBlockModelItem(modelMachines, textures, 1.0f, 0.f, 0.f, 0.f));*/
               
                RenderMachineBlockItemBase[] rendererMachines = { new RenderBlockModelItem(new ResourceLocation("techguns","textures/blocks/AmmoPress.png"), new ModelAmmoPress()),
                		new RenderBlockModelItem(new ResourceLocation("techguns","textures/blocks/MetalPress.png"), new ModelMetalPress()),
                		new RenderBlockModelItem(new ResourceLocation("techguns","textures/blocks/TurretBase.png"), new ModelTurretBase()),
                		new RenderBlockModelItem(new ResourceLocation("techguns","textures/blocks/paintmachine_textures.png"), new ModelBaseTesselator(new PaintMachineTesselatorCube())),
                		new RenderBlockModelItem(new ResourceLocation("techguns","textures/blocks/ChemLab.png"), new ModelChemLab()),
                		new RenderBlockModelItem(new ResourceLocation("techguns","textures/blocks/repairbench_textures.png"), new ModelBaseTesselator(new RepairBenchTesselatorCube())),
                		new RenderBlockModelItemTNTCharge(new ResourceLocation("techguns","textures/blocks/tntCharge.png"), new ModelTNTCharge()),
                		new RenderBlockModelItem(new ResourceLocation("techguns","textures/blocks/repairbench_textures.png"), new ModelBaseTesselator(new RepairBenchTesselatorCube())),
                		new RenderBlockModelItemTNTCharge(new ResourceLocation("techguns","textures/blocks/ExpCharge.png"), new ModelExpCharge()),
                		new RenderBlockModelItem(new ResourceLocation("techguns","textures/blocks/ChargingStation.png"), new ModelChargingStation()),
                		new RenderBlockModelItem(new ResourceLocation("techguns","textures/blocks/repairbench_textures.png"), new ModelBaseTesselator(new RepairBenchTesselatorCube())),
                		
                };
                MinecraftForgeClient.registerItemRenderer((Item.getItemFromBlock(TGBlocks.basicMachine)), new RenderMachineBlockItem(rendererMachines));
                 
                
                RenderMachineBlockItemBase[] renderChests = {new RenderBlockModelItemChest(new ResourceLocation("techguns","textures/blocks/reinforced_chest.png"), new ModelChestInv())};
                MinecraftForgeClient.registerItemRenderer((Item.getItemFromBlock(TGBlocks.tgchest)), new RenderMachineBlockItem(renderChests));
                
                RenderMachineBlockItemBase[] renderChests2 = {new RenderBlockModelItemChest(new ResourceLocation("techguns","textures/blocks/weapon_chest.png"), new ModelChestInv())};
                MinecraftForgeClient.registerItemRenderer((Item.getItemFromBlock(TGBlocks.tgchest_weapon)), new RenderMachineBlockItem(renderChests2));
                                
                
                RenderLadder renderer_ladder= new RenderLadder(new ModelLadder01(), new ResourceLocation("techguns","textures/blocks/ladder01_inv.png"), 1.0f);
                TGBlocks.ladder01.setRenderType(renderer_ladder.getRenderId());
                RenderingRegistry.registerBlockHandler(renderer_ladder.getRenderId(), renderer_ladder);
                
                RenderLamp renderer_lamp = new RenderLamp(new ModelLadder01(), new ResourceLocation("techguns","textures/blocks/ladder01_inv.png"), 1.0f);
                TGBlocks.lamp01.setRenderType(renderer_lamp.getRenderId());
                RenderingRegistry.registerBlockHandler(renderer_lamp.getRenderId(), renderer_lamp);
                
                RenderGenericPane renderer_pane = new RenderGenericPane();
                TGBlocks.camoNetPane.setRenderType(renderer_pane.getRenderId());
                RenderingRegistry.registerBlockHandler(renderer_pane.getRenderId(), renderer_pane);
                
                RenderDirectionalBlock renderer_advBlock= new RenderDirectionalBlock();
                ((GenericBlock) TGBlocks.screen).setRenderType(renderer_advBlock.getRenderId());
                ((GenericBlock) TGBlocks.controlpanel).setRenderType(renderer_advBlock.getRenderId());
                RenderingRegistry.registerBlockHandler(renderer_advBlock.getRenderId(), renderer_advBlock);
                
                
               //RenderingRegistry.registerBlockHandler(Block.getIdFromBlock(Techguns.bioBlobSmall), new RenderGenericBlock(new ModelBioBlockSmall(), 1.0f));
                RenderingRegistry.registerBlockHandler(new team.chisel.client.render.RendererCTM());
                
                RenderOrecluster renderer_cluster= new RenderOrecluster();
                TGBlocks.oreCluster.setRenderType(renderer_cluster.getRenderId());
                TGBlocks.oreCluster2.setRenderType(renderer_cluster.getRenderId());
                if (TGConfig.addOreClusterCustom){
                	TGBlocks.oreClusterCustom.setRenderType(renderer_cluster.getRenderId());
                }
                if (TGConfig.addOreClusterCustom2){
                	TGBlocks.oreClusterCustom2.setRenderType(renderer_cluster.getRenderId());
                }
                RenderingRegistry.registerBlockHandler(renderer_cluster.getRenderId(), renderer_cluster);
        }

		/* (non-Javadoc)
		 * @see techguns.CommonProxy#spawnParticle(java.lang.String, net.minecraft.world.World, double, double, double, double, double, double)
		 */
		@Override
		@Deprecated
		public void spawnParticle(String name, World world, double posX,
				double posY, double posZ, double MotionX, double MotionY,
				double MotionZ) {
			
			EntityFX fx=null;
			
			if (name.equals("BioTrail")){
	            fx = new EntityBreakingFX(world, posX,posY, posZ, Items.slime_ball);
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
			} else if (name.equals("LaserImpact")){
	            fx = new EntityBreakingFX(world, posX,posY, posZ, Items.redstone);
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
			} else if (name.equals("RailgunTrail")){
	            fx = new EntityParticleTrail(world, posX,posY, posZ, Items.diamond, 0.1f, 0.5f, 1.0f, 0.5f);
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
			} else if (name.equals("LaserBeam")){
				fx = new EntityLaserBeamFX(world,posX,posY,posZ, MotionX, MotionY, MotionZ);
				//Motion is target Pos for laser
			} else if (name.equals("Fireball")){
	            fx = new EntityParticleAnimated(world, posX, posY, posZ, 1.5f, "fireball_4x4_add.png", 4, 4, 16, RenderType.ADDITIVE);
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
	            ((EntityParticleAnimated) fx).setGravity(0.025f);
	            ((EntityParticleAnimated) fx).setRandomRotation(true);
			} else if (name.equals("FireballImpact")){
	            fx = new EntityParticleAnimated(world, posX, posY, posZ, 3.5f, "fireball_4x4_add.png", 4, 4, 16, RenderType.ADDITIVE); 
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
	            ((EntityParticleAnimated) fx).setGravity(0.025f);
	            ((EntityParticleAnimated) fx).setMaxAge(20);
	            ((EntityParticleAnimated) fx).setRandomRotation(true);
	            ((EntityParticleAnimated) fx).setScaleIncrease(3.5f, 6f, 1f, true);
			} else if (name.equals("Explosion")){
	            fx = new EntityParticleAnimated(world, posX, posY, posZ, 5f, "Explosion.png", 5, 5, 20, RenderType.ALPHA); 
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
	            ((EntityParticleAnimated) fx).setGravity(0.05f);
	            ((EntityParticleAnimated) fx).setMaxAge(20);
	            ((EntityParticleAnimated) fx).setScaleIncrease(5f, 10f, 1f, true);
	            //((EntityParticleAnimated) fx).setRandomRotation(0f, 360f);
	            ((EntityParticleAnimated) fx).setRandomRotation(true);
			} else if (name.equals("Fireblast")){
	            fx = new EntityParticleAnimated(world, posX, posY, posZ, 2.5f, "fireblast.png", 5, 5, 25, RenderType.ALPHA); 
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
	            ((EntityParticleAnimated) fx).setMaxAge(20);
	            ((EntityParticleAnimated) fx).setScaleIncrease(2.5f, 5f, 1f, true);
	            ((EntityParticleAnimated) fx).setRandomRotation(true);
	            ((EntityParticleAnimated) fx).setRandomness(0.33f);
	            ((EntityParticleAnimated) fx).setMotionDamping(0.90D);
			} else if (name.equals("BlasterFlare")){
	            fx = new EntityParticleAnimated(world, posX, posY, posZ, 2.5f, "blasterflare01.png", 4, 4, 14, RenderType.ADDITIVE); 
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
	            ((EntityParticleAnimated) fx).setMaxAge(16);
			} else if (name.equals("AlienBlasterFlare")){
		            fx = new EntityParticleAnimated(world, posX, posY, posZ, 3.0f, "alienflare.png", 4, 4, 14, RenderType.ADDITIVE); 
		            fx.motionX = MotionX;
		            fx.motionY = MotionY;
		            fx.motionZ = MotionZ;
		            ((EntityParticleAnimated) fx).setMaxAge(5);
			}
			else if (name.equals("LargeSmoke")){
	            fx = new EntityParticleAnimated(world, posX, posY, posZ, 7.5f, "largesmoke.png", 2, 2, 4, RenderType.ALPHA_SHADED); 
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
	            ((EntityParticleAnimated) fx).setMaxAge(40);
	            ((EntityParticleAnimated) fx).setDelay(7);
	            ((EntityParticleAnimated) fx).setScaleIncrease(7.5f, 12.5f, 1f, true);
	            ((EntityParticleAnimated) fx).setRandomRotation(true);
	            ((EntityParticleAnimated) fx).setRandomness(0.5f);
	            ((EntityParticleAnimated) fx).setMotionDamping(0.98D);
			}
			else if (name.equals("LaserFlareEnd")){
	            fx = new EntityParticleAnimated(world, posX, posY, posZ, 5.0f, "laserflare02.png", 4, 4, 7, RenderType.ADDITIVE); 
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
	            ((EntityParticleAnimated) fx).setMaxAge(18);
	            ((EntityParticleAnimated) fx).setScaleIncrease(5f, 7.5f, 1f, true);
			}
			else if (name.equals("LaserFlareStart")){
	            fx = new EntityParticleAnimated(world, posX, posY, posZ, 2.5f, "laserflare02.png", 4, 4, 7, RenderType.ADDITIVE); 
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
	            ((EntityParticleAnimated) fx).setMaxAge(12);
			}
			else if (name.equals("Flamethrower")){
	            fx = new EntityParticleAnimated(world, posX, posY, posZ, 0.25f, "flamethrower_a.png", 4, 4, 16, RenderType.ALPHA); 
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
	            ((EntityParticleAnimated) fx).setMaxAge(20);
	            //((EntityParticleAnimated) fx).setDynamicColor(0f,0f,0f,0f,0.9f,0.8f,0.8f,1f, DynamicColorType.SINUS);
	            ((EntityParticleAnimated) fx).setScaleIncrease(0.25f, 2.5f, 0.5f, true);
	            ((EntityParticleAnimated) fx).setRandomRotation(true);
	            ((EntityParticleAnimated) fx).setRandomness(0.75f);
	            ((EntityParticleAnimated) fx).setMotionDamping(0.98D);
			}
			else if (name.equals("Jetpack")){
	            fx = new EntityParticleAnimated(world, posX, posY, posZ, 0.125f, "flamethrower_a.png", 4, 4, 16, RenderType.ALPHA); 
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
	            ((EntityParticleAnimated) fx).setMaxAge(10);
	            //((EntityParticleAnimated) fx).setDynamicColor(0f,0f,0f,0f,0.9f,0.8f,0.8f,1f, DynamicColorType.SINUS);
	            ((EntityParticleAnimated) fx).setScaleIncrease(0.125f, 1.5f, 0.5f, true);
	            ((EntityParticleAnimated) fx).setRandomRotation(true);
	            ((EntityParticleAnimated) fx).setRandomness(0.75f);
	            ((EntityParticleAnimated) fx).setMotionDamping(0.98D);
			}
			else if (name.equals("MuzzleFlashYellow")){
	            fx = new EntityParticleAnimated(world, posX, posY, posZ, 1.5f, "muzzleflash2.png", 4, 4, 8, RenderType.ADDITIVE); 
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
	            ((EntityParticleAnimated) fx).setMaxAge(4);
			}
			else if (name.equals("MuzzleFlashExp")){
	            fx = new EntityParticleAnimated(world, posX, posY, posZ, 1.5f, "muzzleflash4x4.png", 4, 4, 10, RenderType.ADDITIVE); 
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
	            ((EntityParticleAnimated) fx).setMaxAge(4);
			}
			else if (name.equals("MuzzleFlashBlue")){
	            fx = new EntityParticleAnimated(world, posX, posY, posZ, 1.5f, "muzzleflash_blue.png", 4, 4, 8, RenderType.ADDITIVE); 
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
	            ((EntityParticleAnimated) fx).setMaxAge(4);
			}
			else if (name.equals("MuzzleFlashLaser")){
	            fx = new EntityParticleAnimated(world, posX, posY, posZ, 1.5f, "laserflare02.png", 4, 4, 7, RenderType.ADDITIVE); 
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
	            ((EntityParticleAnimated) fx).setMaxAge(4);
			} else if (name.equals("ExplosionHuge")){
	            fx = new EntityParticleAnimated(world, posX, posY, posZ, 5f, "Explosion.png", 5, 5, 20, RenderType.ALPHA); 
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
	            ((EntityParticleAnimated) fx).setGravity(0.05f);
	            ((EntityParticleAnimated) fx).setMaxAge(40);
	            ((EntityParticleAnimated) fx).setScaleIncrease(20f, 40f, 1f, true);
	            //((EntityParticleAnimated) fx).setRandomRotation(0f, 360f);
	            ((EntityParticleAnimated) fx).setRandomRotation(true);
			} else if (name.equals("Blood")){
            fx = new EntityParticleAnimated(world, posX, posY, posZ, 5f, "blood01.png", 1, 1, 1, RenderType.ALPHA); 
            fx.motionX = MotionX;
            fx.motionY = MotionY;
            fx.motionZ = MotionZ;
            ((EntityParticleAnimated) fx).setGravity(0.07f);
            ((EntityParticleAnimated) fx).setMaxAge(20);
            ((EntityParticleAnimated) fx).setScaleIncrease(5f, 10f, 1f, true);
            ((EntityParticleAnimated) fx).setDynamicColor(255, 255, 255, 255, 255, 255, 255, 0, DynamicColorType.LINEAR);
            ((EntityParticleAnimated) fx).setDynamicColorStart(0.5f);
            ((EntityParticleAnimated) fx).setRandomRotation(true);
            ((EntityParticleAnimated) fx).setRandomness(0.50f);
            ((EntityParticleAnimated) fx).setMotionDamping(0.98D);
			}else if (name.equals("impact")){
	            fx = new EntityParticleAnimated(world, posX, posY, posZ, 2.5f, "impact.png", 2, 2, 4, RenderType.ADDITIVE); 
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
	            ((EntityParticleAnimated) fx).setMaxAge(5);
	            ((EntityParticleAnimated) fx).setScaleIncrease(2.5f, 7.5f, 1f, true);
	            ((EntityParticleAnimated) fx).setDynamicColor(255, 215, 155, 128, 255, 215, 155, 0, DynamicColorType.LINEAR);
			}else if (name.equals("DustClouds")){
	            fx = new EntityParticleAnimated(world, posX, posY, posZ, 7.5f, "dustclouds.png", 4, 4, 16, RenderType.ALPHA_SHADED); 
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
	            ((EntityParticleAnimated) fx).setMaxAge(40);
	            ((EntityParticleAnimated) fx).setScaleIncrease(5.0f, 10.0f, 1f, true);
	            ((EntityParticleAnimated) fx).setRandomRotation(true);
	            ((EntityParticleAnimated) fx).setRandomness(0.5f);
	            ((EntityParticleAnimated) fx).setMotionDamping(0.98D);
	            ((EntityParticleAnimated) fx).setDynamicColor(255, 255, 255, 255, 255, 255, 255, 0, DynamicColorType.SINUS);
			
			}else if (name.equals("TeslaFlare")){
	            fx = new EntityParticleAnimated(world, posX, posY, posZ, 5.0f, "teslaflare01.png", 4, 4, 14, RenderType.ADDITIVE); 
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
	            ((EntityParticleAnimated) fx).setMaxAge(15);
	            ((EntityParticleAnimated) fx).setScaleIncrease(5f, 10.0f, 1f, true);
	            ((EntityParticleAnimated) fx).setDynamicColor(255, 255, 255, 255, 255, 255, 255, 0, DynamicColorType.LINEAR);
	            ((EntityParticleAnimated) fx).setDynamicColorStart(0.75f);
			} else if (name.equals("MuzzleFlashAlienBlue")){
	            fx = new EntityParticleAnimated(world, posX, posY, posZ, 1.5f, "alienflare.png", 4, 4, 14, RenderType.ADDITIVE); 
	            fx.motionX = MotionX;
	            fx.motionY = MotionY;
	            fx.motionZ = MotionZ;
	            ((EntityParticleAnimated) fx).setMaxAge(4);
	            ((EntityParticleAnimated) fx).setDynamicColor(40, 255, 140, 255, 40, 255, 140, 255, DynamicColorType.FAST);
			}
			if (fx!=null){
				Minecraft.getMinecraft().effectRenderer.addEffect(fx);
			}
			
		}
		
		@Override
		public void loadFXList(){
			FXList.loadFXList();
		}
		
		
		@Override
		public void createFX(String name, World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ){	
			//System.out.println("CreatingFX:"+name);
			List<EntityParticleSystem> systems = FXList.createFX(world, name, posX, posY, posZ, motionX, motionY, motionZ);
			for (EntityParticleSystem system : systems) {
				world.spawnEntityInWorld(system);
			}
		}
		
		
		
		@Override
		public void createFXOnEntity(String name, Entity ent) {
			//System.out.println("Create FX "+name+" on "+ent);
			List<EntityParticleSystem> systems = FXList.createFXOnEntity(ent, name);
			for (EntityParticleSystem system : systems) {
				ent.worldObj.spawnEntityInWorld(system);
			}
			
		}
		
		@Override
		public int[] createFXOnEntity_ID(String name, Entity ent) {
			//System.out.println("Create FX "+name+" on "+ent);
			List<EntityParticleSystem> systems = FXList.createFXOnEntity(ent, name);
			int[] IDs = new int[systems.size()];
			int i = 0;
			for (EntityParticleSystem system : systems) {
				ent.worldObj.spawnEntityInWorld(system);
				IDs[i++] = system.getEntityId();
			}
			return IDs;
		}
		

		/**
		 * Use on client only
		 */
		@SideOnly(Side.CLIENT)
		public static void spawnParticle(EntityFX fx) {
			Minecraft.getMinecraft().effectRenderer.addEffect(fx);
		}
       
		public static String getKey(EntityLivingBase ent){
			if (ent instanceof EntityPlayer){
				return ((EntityPlayer)ent).getCommandSenderName();
			} else {
				return ent.getEntityId()+"";
			}
		}
		
		public long getplayerRecoiltime(EntityLivingBase ent){
			String key = getKey(ent);
			if (attackTimes.containsKey(key)){
				return attackTimes.get(key).getRecoilTime();
			}
			return 0L;
		}
		
		/*public void setplayerRecoiltime(long time, int total,byte attacktype){
			setplayerRecoiltime(Minecraft.getMinecraft().thePlayer,time,total,attacktype);
		}
		public long getplayerRecoiltime(){
			return getplayerRecoiltime(Minecraft.getMinecraft().thePlayer);
		}*/
		
		public boolean isStillRecoiling(EntityLivingBase ent, byte attacktype){
			if (ent != this.getPlayerClient()){
				return false;
			} else if (Minecraft.getMinecraft().gameSettings.thirdPersonView!=0){
				return false;
			}
			
			
			
			String key = getKey(ent);
			AttackTime attack =attackTimes.get(key);
			
			if(attack==null){
				//System.out.println("No, attack is null");
				return false;
			} else {
				if (attack.getAttackType()!=attacktype){
				//	System.out.println("No, attacktype is different");
					return false;
				} else if ( attack.getRecoilTime()<=0){
					//System.out.println("No, attacktime is 0");
					return false;
				} else {
					boolean b = (System.currentTimeMillis()-attack.getRecoilTime()) < 0; 
					//System.out.println("CheckTime: "+b);
					return b;
				}				
			}
			
		}
		
		
		public void setplayerRecoiltime(EntityLivingBase ent,long time, int total,byte attacktype){
			String key = getKey(ent);
			AttackTime attack =attackTimes.get(key);
			if(attack==null){
				attack=new AttackTime();
				attackTimes.put(key, attack);
			}
			
			attack.setRecoilTime(time);
			attack.setRecoilTimeTotal(total);
			attack.setAttackType(attacktype);
		}
		
		
		
		/**
		 * TOTAL
		 */
		public long getplayerRecoiltimeTotal(EntityLivingBase ent){
			String key = getKey(ent);
			if (attackTimes.containsKey(key)){
				return attackTimes.get(key).getRecoilTimeTotal();
			}
			return 0L;
		}
		
		/*public static void setplayerRecoiltimeTotal(int value){
			setplayerRecoiltimeTotal(Minecraft.getMinecraft().thePlayer,value);
		}*/
		public long getplayerRecoiltimeTotal(){
			return getplayerRecoiltimeTotal(Minecraft.getMinecraft().thePlayer);
		}
		
		/*public static void setplayerRecoiltimeTotal(EntityLivingBase ent,int value){
			String key = getKey(ent);
			recoiltime_total.put(key, value);
		}*(
		/**
		 * RELOAD
		 */
		public long getplayerReloadtime(EntityLivingBase ent){
			String key = getKey(ent);
			if (attackTimes.containsKey(key)){
				return attackTimes.get(key).getReloadTime();
			}
			return 0L;
		}
		
		public byte getplayerAttackType(EntityLivingBase ent){
			String key = getKey(ent);
			if(attackTimes.containsKey(key)){
				return attackTimes.get(key).getAttackType();
			}
			return (byte)0;
		}
		
		/*public void setplayerReloadtime(long time, int total,byte attackType){
			setplayerReloadtime(Minecraft.getMinecraft().thePlayer,time,total,attackType);
		}
		public long getplayerReloadtime(){
			return getplayerReloadtime(Minecraft.getMinecraft().thePlayer);
		}*/
		
		public void setplayerReloadtime(EntityLivingBase ent,long time, int total,byte attackType){
			String key = getKey(ent);
			AttackTime attack =attackTimes.get(key);
			if(attack==null){
				attack=new AttackTime();
				attackTimes.put(key, attack);
			}
			attack.setReloadTime(time);
			attack.setReloadTimeTotal(total);
			attack.setAttackType(attackType);
		}
		/**
		 * TOTAL
		 */
		public int getplayerReloadtimeTotal(EntityLivingBase ent){
			String key = getKey(ent);
			if (attackTimes.containsKey(key)){
				return attackTimes.get(key).getReloadTimeTotal();
			}
			return 0;
		}
		
		/*public static void setplayerReloadtimeTotal(int value){
			setplayerReloadtimeTotal(Minecraft.getMinecraft().thePlayer,value);
		}*/
		public long getplayerReloadtimeTotal(){
			return getplayerReloadtimeTotal(Minecraft.getMinecraft().thePlayer);
		}
		
		/*public static void setplayerReloadtimeTotal(EntityLivingBase ent,int value){
			String key = getKey(ent);
			reloadtime_total.put(key, value);
		}*/
		
		/**
		 * END
		 */
		
		public void removeEntityFromMaps(EntityLivingBase ent){
			String key = getKey(ent);
			
			/*if (recoiltime.containsKey(key)){
				recoiltime.remove(key);
			}
			if (recoiltime_total.containsKey(key)){
				recoiltime_total.remove(key);
			}
			if (reloadtime.containsKey(key)){
				reloadtime.remove(key);
			}
			if (reloadtime_total.containsKey(key)){
				reloadtime_total.remove(key);
			}*/
			if (attackTimes.containsKey(key)){
				attackTimes.remove(key);
			}
		}
		
		/**
		 * EntityDeathType
		 */
		public void setEntityDeathType(EntityLivingBase entity, DeathType deathtype){
			String key = getKey(entity);
			entityDeathTypes.put(key, deathtype);
		}
		
		public DeathType getEntityDeathType(EntityLivingBase entity) {
			return entityDeathTypes.get(getKey(entity));
		}
		
		public boolean hasDeathType(EntityLivingBase entity) {
			return entityDeathTypes.containsKey(getKey(entity));
		}
		
		public void clearEntityDeathType(EntityLivingBase entity) {
			entityDeathTypes.remove(getKey(entity));
		}

		@Override
		public void setGunTextures(GenericGun gun, String path, int variations) {
			for(int i=0;i<variations;i++){
				gun.textures.add(new ResourceLocation("techguns",path+(i!=0?("_"+i):"")+".png"));
			}
		}

		/*public static void spawnMuzzleFXForEntity(EntityLivingBase shooter){
			boolean self=false;
			//dont' show in 1st person for self
			if(shooter == Minecraft.getMinecraft().thePlayer){
				if(Minecraft.getMinecraft().gameSettings.thirdPersonView==0){
					return;
				} else {
					self=true;
				}
			}
			
			ItemStack it = shooter.getHeldItem();
			if(it!=null && it.getItem() instanceof GenericGun){
				GenericGun gun = (GenericGun) it.getItem();
				
				String particle=gun.getMuzzleflashParticle();
				
				if(particle!=null){
					
					double x = shooter.posX;
					double y = shooter.posY+shooter.getEyeHeight();
					double z = shooter.posZ;
					
					float dist=1.0f;
					float r =0.3f;
					float u = (float) (0.2f+Keybinds.Y);
					Vec3 look = shooter.getLookVec().normalize();
					
					//world up
					Vec3 wup = Vec3.createVectorHelper(0, 1, 0);
					
					Vec3 right = look.crossProduct(wup);
					Vec3 up = right.crossProduct(look);
					
					
					x+= (look.xCoord*dist) + (right.xCoord*r)+ (up.xCoord*u);
					y+= (look.yCoord*dist) + (right.yCoord*r)+ (up.yCoord*u);
					z+= (look.zCoord*dist) + (right.zCoord*r)+ (up.zCoord*u);
					
					
					Techguns.proxy.spawnParticle(particle, Minecraft.getMinecraft().theWorld, x, y, z, shooter.motionX, 0,shooter.motionZ);
				}
			}
		}*/
		
		public static void spawnMuzzleFXForEntity(EntityLivingBase shooter, boolean leftGun){
			boolean self=false;
			//dont' show in 1st person for self
			if(shooter == Minecraft.getMinecraft().thePlayer){
				if(Minecraft.getMinecraft().gameSettings.thirdPersonView==0){
					return;
				} else {
					self=true;
				}
			}
			
			ItemStack it = shooter.getHeldItem();
			if(it!=null && it.getItem() instanceof GenericGun){
				GenericGun gun = (GenericGun) it.getItem();
				
				String particle=gun.getMuzzleflashParticle();
				
				if(particle!=null){
					
					double x = shooter.posX;
					double y = shooter.posY;
					double z = shooter.posZ;
					
					float shooterOffsetX=0;
					float shooterOffsetY=0;
					float shooterOffsetZ=0;
					if(shooter instanceof EntityPlayer){
						if(self){
							shooterOffsetY+=0.0f;//-0.8f;
						} else {
							shooterOffsetY+=1.6f;//0.875f;
						}
					}
					if(shooter instanceof EntityPlayer || shooter instanceof GenericNPC){
						/*shooterOffsetY=(float) Keybinds.Y;
						shooterOffsetX=(float) Keybinds.X;
						shooterOffsetZ=(float) Keybinds.Z;*/
						
						//shooterOffsetX=-0.35f;
						shooterOffsetX+=-0.2f;//(float) Keybinds.X;//-0.35f;
						shooterOffsetY+=0.05f;//(float) Keybinds.Y;
						shooterOffsetZ+=1.15f;
					} 
					if(shooter instanceof INPCTechgunsShooter){
						INPCTechgunsShooter tgshooter = (INPCTechgunsShooter) shooter;
						shooterOffsetX+=tgshooter.getWeaponPosX();
						shooterOffsetY+=tgshooter.getWeaponPosY();
						shooterOffsetZ+=tgshooter.getWeaponPosZ();
						if(shooter instanceof NPCTurret){
							shooterOffsetX+=gun.turretPosOffsetX+gun.muzzleFlashOffsetTurretX;
							shooterOffsetY+=gun.turretPosOffsetY+gun.muzzleFlashOffsetTurretY;
							shooterOffsetZ+=gun.turretPosOffsetZ+gun.muzzleFlashOffsetTurretZ;
						}
						/*shooterOffsetY=(float) Keybinds.Y;
						shooterOffsetX=(float) Keybinds.X;
						shooterOffsetZ=(float) Keybinds.Z;*/
					}
					
					if (leftGun){
						shooterOffsetX*=-1.0f;
					}
					
					/**
					 * z' = z*cos( angle) - x*sin (angle)
					 * x' = z*sin( angle) + x*cos (angle)
					 */
					float rot;
					
					double dZ = (gun.muzzleFlashOffsetZ+shooterOffsetZ);
					double dX = (gun.muzzleFlashOffsetX+shooterOffsetX);
					double dY = (shooterOffsetY+gun.muzzleFlashOffsetY);
					
					if(shooter instanceof NPCTurret || shooter instanceof EntityPlayer || shooter instanceof GenericNPC){
						rot = -shooter.rotationYawHead;
						
						float rotpitch = -shooter.rotationPitch;
						
						double dZ_new = dZ* MathUtil.cos360(rotpitch) - dY*MathUtil.sin360(rotpitch);
						double dY_new = dZ*MathUtil.sin360(rotpitch) + dY*MathUtil.cos360(rotpitch);
						
						dZ=dZ_new;
						dY=dY_new;
					} else {
						rot = -shooter.renderYawOffset;
					}
	
					
					double offsetZ = dZ*MathUtil.cos360(rot) - dX*MathUtil.sin360(rot);
					double offsetX = dZ*MathUtil.sin360(rot) + dX*MathUtil.cos360(rot);
					double offsetY = dY;
					//double offsetZ=(gun.muzzleFlashOffsetZ+shooterOffsetZ)*MathUtil.cos360(rot) - (gun.muzzleFlashOffsetX+shooterOffsetX)*MathUtil.sin360(rot);
					//double offsetX=(gun.muzzleFlashOffsetZ+shooterOffsetZ)*MathUtil.sin360(rot) + (gun.muzzleFlashOffsetX+shooterOffsetX)*MathUtil.cos360(rot);
					//double offsetY=shooterOffsetY+gun.muzzleFlashOffsetY;
					
					x+=offsetX;
					y+=offsetY;
					z+=offsetZ;

					Techguns.proxy.spawnParticle(particle, Minecraft.getMinecraft().theWorld, x, y, z, shooter.motionX, 0,shooter.motionZ);
				}
			}
		}

		@Override
		public void initChisel() {
			new team.chisel.client.render.RendererCTM();
		}

		@Override
		public EntityPlayer getPlayerClient() {
			return Minecraft.getMinecraft().thePlayer;
		}

		@Override
		public void RegisterClientHandlers() {
			MinecraftForge.EVENT_BUS.register(new GuiOverlayAmmoCount());
			
			if (TGConfig.enableDebugKeybinds){
	            FMLCommonHandler.instance().bus().register(new KeyInputHandler());
	            Keybinds.init();
			}
            
            FMLCommonHandler.instance().bus().register(new TGKeybinds());
            TGKeybinds.init();
		}

		@Override
		public void setHasStepassist(boolean value) {
			this.hasStepassist=value;
		}

		@Override
		public void setHasNightvision(boolean value) {
			this.hasNightvision=value;
		}

		@Override
		public boolean getHasStepassist() {
			return this.hasStepassist;
		}

		@Override
		public boolean getHasNightvision() {
			return this.hasNightvision;
		}

		
		
		@Override
		public void setFlySpeed(float value) {
			EntityPlayer ply = ClientProxy.get().getPlayerClient();
			ply.capabilities.setFlySpeed(value);
		}

		@Override
		public void handleSoundEvent(EntityPlayer ply, int entityId, String soundname, float volume, float pitch, boolean repeat, boolean moving,
				boolean gunPosition,boolean playOnOwnPlayer, TGSoundCategory soundCategory) {
			Entity entity = null;
			if (entityId != -1) {
				entity = ply.worldObj.getEntityByID(entityId);
			}
			
			//System.out.println("snd: "+soundname+" playown:"+playOnOwnPlayer+" ent:"+ply);
			
			if(entity!=null){
				if (entity != ply || playOnOwnPlayer ) {
					//System.out.println("Play sound by packet"+msg.soundname);
						Minecraft.getMinecraft().getSoundHandler().playSound(new TGSound(soundname,entity,volume,pitch, repeat, moving, gunPosition,soundCategory));
						//Minecraft.getMinecraft().getSoundHandler().playSound(new TGSound(msg.soundname, (float)entity.posX, (float)entity.posY, (float)entity.posZ ,msg.volume,msg.pitch, msg.repeat));
						
				}
			} else {
				//System.out.println("Handle Sound entity null, NEED FIX!");
			}
		}

			
		
		@Override
		public void playSoundOnPosition(String soundname, int posx, int posy, int posz, float volume, float pitch, boolean repeat, TGSoundCategory soundCategory) {
			Minecraft.getMinecraft().getSoundHandler().playSound(new TGSound(soundname,posx,posy,posz,volume,pitch, repeat,soundCategory));
		}

		@Override
		public void handlePlayerGliding(TechgunsExtendedPlayerProperties props_server, EntityPlayer player) {
			if (player.worldObj.isRemote){
				TechgunsExtendedPlayerPropertiesClient props = (TechgunsExtendedPlayerPropertiesClient) props_server;
				if(props.isGliding){
					 //System.out.println("PLAYER:"+event.player+" is gliding!");
					 if(props.gliderLoop==null){
						 props.gliderLoop=new TGSound("techguns:glider.loop", player, 1.0f, 1.0f, true, true, false,TGSoundCategory.PLAYER_EFFECT);
						 Minecraft.getMinecraft().getSoundHandler().playSound(props.gliderLoop);
						 //System.out.println("Adding Glider sound from Thread: "+Thread.currentThread().toString());
					 }/* else{
						 System.out.println("Already Gliding!");
					 }*/
				 } else {
					 if(props.gliderLoop!=null){
						 props.gliderLoop.setDonePlaying();
						 props.gliderLoop=null;
					 }
				 }
			}
		}

		@Override
		public TechgunsExtendedPlayerProperties registerExtendedProperties(EntityPlayer ply) {
			return TechgunsExtendedPlayerPropertiesClient.register(ply);
		}

		@Override
		public void playSoundOnEntity(Entity ent, String soundname, float volume, float pitch, boolean repeat, boolean moving, boolean gunPosition, TGSoundCategory category) {
			Minecraft.getMinecraft().getSoundHandler().playSound(new TGSound(soundname,ent, volume, pitch, repeat, moving, gunPosition, category));
		}

		
		public String resolvePlayerNameFromUUID(UUID uuid){
			String name = UsernameCache.getLastKnownUsername(uuid);
			if (name!=null){
				return name;
			} else {
				return "UNKNOW_PLAYERNAME";
			}
		}

		@Override
		public boolean isClientPlayerAndIn1stPerson(EntityLivingBase ent) {
			return ent == this.getPlayerClient() && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0;
		}

		@Override
		public boolean clientInRangeSquared(double posX, double posZ, double distSq) {
			EntityPlayer localPly = this.getPlayerClient();
			
			MathUtil.Vec2 posPly = new MathUtil.Vec2(localPly.posX, localPly.posZ);
			MathUtil.Vec2 pos = new MathUtil.Vec2(posX, posZ);
			
			return posPly.getVecTo(pos).lenSquared() <= distSq;
		}

		@Override
		public void postInit() {
			if (TGConfig.cl_enableUpdateChecker){
				versionChecker = new TGVersionChecker();
				Thread versionCheckThread = new Thread(versionChecker, "Techguns VersionChecker");
				versionCheckThread.start();
			}
		}
		
		
		
}