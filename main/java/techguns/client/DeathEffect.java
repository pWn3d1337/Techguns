package techguns.client;


import java.util.HashMap;

import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.model.ModelWitch;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.client.models.ModelCyberDemon;
import techguns.client.models.ModelGibs;
import techguns.client.models.ModelGibsAlienBug;
import techguns.client.models.ModelGibsBiped;
import techguns.client.models.ModelGibsBipedGeneric;
import techguns.client.models.ModelGibsGeneric;
import techguns.client.models.ModelGibsSkeleton;
import techguns.client.models.ModelGibsVillagerGeneric;
import techguns.client.particle.EntityBloodEffect;
import techguns.entities.npc.AlienBug;
import techguns.entities.npc.ArmySoldier;
import techguns.entities.npc.Bandit;
import techguns.entities.npc.CyberDemon;
import techguns.entities.npc.SkeletonSoldier;
import techguns.entities.npc.ZombieFarmer;
import techguns.entities.npc.ZombieMiner;
import techguns.entities.npc.ZombiePigmanSoldier;
import techguns.entities.npc.ZombieSoldier;
import techguns.entities.projectiles.FlyingGibs;
import techguns.util.EntityDeathUtils.DeathType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DeathEffect {
	
	public static HashMap<Class<? extends EntityLivingBase>, GoreData> goreStats = new HashMap<Class<? extends EntityLivingBase>, GoreData>();
	
	private static GoreData genericGore;
	static {
		ModelGibs modelBiped = new ModelGibsBiped(1.0f);
		ModelGibs modelBipedPlayer = new ModelGibsBiped(1.0f, 0.0f, 64, 32);
		goreStats.put(EntityPlayer.class, (new GoreData(modelBipedPlayer, 6,new ResourceLocation("textures/entity/steve.png"), 0.66f, 160,21,31)));
		goreStats.put(EntityZombie.class, (new GoreData(modelBiped, 6,new ResourceLocation("textures/entity/zombie/zombie.png"), 0.66f, 110,21,41)));
		goreStats.put(ZombieSoldier.class, (new GoreData(modelBiped, 6,new ResourceLocation("techguns","textures/entity/zombie_soldier.png"), 0.66f, 110,21,41)));
		goreStats.put(ArmySoldier.class, (new GoreData(modelBiped, 6,new ResourceLocation("techguns","textures/entity/army_soldier.png"), 0.66f, 160,21,31)));
		goreStats.put(EntitySkeleton.class, (new GoreData(new ModelGibsSkeleton(1.0f), 6,new ResourceLocation("textures/entity/skeleton/skeleton.png"), 0.66f, 255,255,255).setBlood(false)));
		goreStats.put(EntityVillager.class, (new GoreData(new ModelGibsVillagerGeneric(new ModelVillager(1.0f)), 5,new ResourceLocation("textures/entity/villager/villager.png"), 0.66f, 150,21,51)));
		goreStats.put(EntityCow.class, (new GoreData(new ModelGibsGeneric(new ModelCow()), 6,new ResourceLocation("textures/entity/cow/cow.png"), 1.0f, 170,26,37)));
		goreStats.put(EntitySheep.class, (new GoreData(new ModelGibsGeneric(new ModelSheep1()), 6,new ResourceLocation("textures/entity/sheep/sheep.png"), 1.0f, 170,26,37)));
		goreStats.put(EntityChicken.class, (new GoreData(new ModelGibsGeneric(new ModelChicken()), 6,new ResourceLocation("textures/entity/chicken.png"), 1.0f, 170,26,37)));
		goreStats.put(EntityCreeper.class, (new GoreData(new ModelGibsGeneric(new ModelCreeper()), 7, new ResourceLocation("textures/entity/creeper/creeper.png"), 1.0f, 50,175,57)));		
		goreStats.put(EntityEnderman.class, (new GoreData(new ModelGibsGeneric(new ModelEnderman()), 7, new ResourceLocation("textures/entity/enderman/enderman.png"), 1.0f, 160,36,167)));
		goreStats.put(EntityPig.class, (new GoreData(new ModelGibsGeneric(new ModelPig()), 6,new ResourceLocation("textures/entity/pig/pig.png"), 0.8f, 170,26,37)));
		goreStats.put(EntitySpider.class, (new GoreData(new ModelGibsGeneric(new ModelSpider()), 11,new ResourceLocation("textures/entity/spider/spider.png"), 1.0f, 85,156,17)));		
		goreStats.put(EntityCaveSpider.class, (new GoreData(new ModelGibsGeneric(new ModelSpider()), 11,new ResourceLocation("textures/entity/spider/cave_spider.png"), 0.66f, 85,156,17)));		
		
		goreStats.put(EntityPigZombie.class, (new GoreData(modelBiped, 6,new ResourceLocation("textures/entity/zombie_pigman.png"), 0.66f, 110,51,11)));	
		goreStats.put(ZombiePigmanSoldier.class, (new GoreData(modelBiped, 6,new ResourceLocation("textures/entity/zombie_pigman.png"), 0.66f, 110,51,11)));	
		goreStats.put(CyberDemon.class, (new GoreData(new ModelGibsBipedGeneric(new ModelCyberDemon()), 6,new ResourceLocation("techguns","textures/entity/cyberdemon.png"), 1.00f, 85,156,17)));			
		goreStats.put(EntityWitch.class, (new GoreData(new ModelGibsVillagerGeneric(new ModelWitch(1.0f)), 5,new ResourceLocation("textures/entity/witch.png"), 0.66f, 160,21,31)));			
		goreStats.put(EntitySlime.class, (new GoreData(null,0,null,1.0f,40,255,40)));
		
		goreStats.put(ZombieFarmer.class, (new GoreData(modelBiped, 6,new ResourceLocation("techguns","textures/entity/zombie_soldier.png"), 0.66f, 110,21,41)));
		goreStats.put(ZombieMiner.class, (new GoreData(modelBiped, 6,new ResourceLocation("techguns","textures/entity/zombie_soldier.png"), 0.66f, 110,21,41)));
		
		goreStats.put(Bandit.class, new GoreData(modelBiped,6,new ResourceLocation("techguns","textures/entity/bandit.png"), 0.66f, 160,21,31));
		goreStats.put(SkeletonSoldier.class, (new GoreData(new ModelGibsSkeleton(1.0f), 6,new ResourceLocation("textures/entity/skeleton/skeleton.png"), 0.66f, 255,255,255).setBlood(false)));
		goreStats.put(AlienBug.class, (new GoreData(new ModelGibsAlienBug(),8, new ResourceLocation("techguns", "textures/entity/alienbug.png"), 0.8f, 235, 255, 70)));
		
		genericGore = (new GoreData(modelBiped, 6, new ResourceLocation("techguns","textures/entity/gore.png"), 0.66f, 160,21,31));
		genericGore.setRandomScale(0.5f, 0.8f);
		//ModelHorse horse = new ModelHorse();
		//goreStats.put(EntityHorse.class, new GoreData(new ModelGibsHorse(horse), horse.boxList.size(), new ResourceLocation("textures/entity/horse/horse_brown.png"), 0.66f, 150,21,51));
	}
	
	public static GoreData getGoreData(Class<? extends EntityLivingBase> entityClass) {
		GoreData data = DeathEffect.goreStats.get(entityClass);
		if (data != null) {
			return data;
		}else {
			//return DeathEffect.goreStats.get(EntityPlayer.class);
			return genericGore;
		}
	}
	
	public static void createDeathEffect(EntityLivingBase entity,
			DeathType deathtype, float motionX, float motionY, float motionZ) {
		//GetEntityType
		//EntityDT entityDT = EntityDeathUtils.getEntityDeathType(entity);
		
		double x = entity.posX;
		double y = entity.posY+(entity.height/2.0f);
		double z = entity.posZ;
		
		if (deathtype == DeathType.GORE) {			
			
			GoreData data = DeathEffect.getGoreData(entity.getClass());
			
			//entity.worldObj.playSoundEffect(x,y,z, "techguns:effects.gore", 1.0F, 1.0f);
			//Minecraft.getMinecraft().thePlayer.playSound("techguns:effects.gore", 1.0F, 1.0f);

			
//			if (!data.showBlood) {
//					//Techguns.proxy.spawnParticle("DustClouds", entity.worldObj, x, y, z, motionX, motionY, motionZ);
		//	}else{
			ClientProxy.spawnParticle(new EntityBloodEffect(entity.worldObj, x, y, z, motionX, motionY, motionZ, entity.width, entity.height, data));
			//}
			
			int count = data.numGibs; //getBodyPartCount(entityDT); //2+(int)(((entity.width+entity.height)/2.0f)*2.5f);
			//System.out.println(entity.getClass().toString()+" - numGibs: "+count);
			//if (entityDT == EntityDT.VILLAGER) count = 5;
			for (int i = 0; i < count; i++) {
				double vx = (0.5-entity.worldObj.rand.nextDouble()) * 0.35;
				double vy;			
				if (entity.onGround) {
					vy = (entity.worldObj.rand.nextDouble()) * 0.35;
				}else {
					vy = (0.5-entity.worldObj.rand.nextDouble()) * 0.35;
				}
				double vz = (0.5-entity.worldObj.rand.nextDouble()) * 0.35;
				
				FlyingGibs ent = new FlyingGibs(entity.worldObj, entity, data, x,y,z, motionX*0.35+vx, motionY*0.15+vy, motionZ*0.35+vz, (entity.width+entity.height)/2.0f, i);
				
				entity.worldObj.spawnEntityInWorld(ent);
			}
		}else if (deathtype == DeathType.BIO) {
			Techguns.proxy.createFX("biodeath", entity.worldObj, x,y,z, (double)motionX, (double)motionY, (double)motionZ);
		}else if (deathtype == DeathType.LASER) {
			Techguns.proxy.createFX("laserdeathFire", entity.worldObj, x,y,z, (double)motionX, 0, (double)motionZ);
			Techguns.proxy.createFX("laserdeathAsh", entity.worldObj, x,y,z, (double)motionX, 0, (double)motionZ);
		}
		
	}
	
//	public static int getBodyPartCount(EntityDT entityDT) {
//		switch (entityDT) {
//		case PLAYER:
//		case ARMY_SOLDIER:
//		case ZOMBIE_SOLDIER:	
//		case SKELETON:
//		case ZOMBIE:
//			return 6;
//		case CHICKEN:
//			return 6;
//		case COW:
//		case SHEEP:
//			return 6;
//		case CREEPER:
//			return 7;
//		case VILLAGER:
//			return 5;
//		case SPIDER:
//			return 11;
//		case ENDERMAN:
//			return 7;
//		case PIG:
//			return 6;
//		case GENERIC:
//		default:
//			return 5;		
//		}
//	}
	

}
