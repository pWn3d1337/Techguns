package techguns.client.renderer.entity;

import java.util.Random;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.DeathEffect;
import techguns.client.GoreData;
import techguns.client.models.ModelGibs;
import techguns.entities.projectiles.FlyingGibs;

public class RenderFlyingGibs extends Render {
	
//	ModelGibs model1 = new ModelGibsBiped(1.0f);
//	ModelGibs model2 = new ModelGibsBiped(1.0f, 0.0f, 64, 32); //Player
//	ModelGibs modelSkeleton = new ModelGibsSkeleton(1.0f);
//	ModelGibs modelVillager = new ModelGibsVillager(1.0f);
//	ModelGibs modelCow = new ModelGibsGeneric(new ModelCow());
//	ModelGibs modelSheep = new ModelGibsGeneric(new ModelSheep1());
//	ModelGibs modelChicken = new ModelGibsGeneric(new ModelChicken());
//	ModelGibs modelCreeper = new ModelGibsGeneric(new ModelCreeper());
//	ModelGibs modelEnderman = new ModelGibsGeneric(new ModelEnderman());
//	ModelGibs modelPig = new ModelGibsGeneric(new ModelPig());
//	ModelGibs modelSpider = new ModelGibsGeneric(new ModelSpider());
//	
	
//	ResourceLocation[] entityTextures;
	
	public RenderFlyingGibs() {
//		entityTextures = new ResourceLocation[13];
//		entityTextures[0] = new ResourceLocation("textures/entity/steve.png");
//		entityTextures[1] = new ResourceLocation("textures/entity/zombie/zombie.png");
//		entityTextures[2] = new ResourceLocation(Techguns.MODNAME.toLowerCase(), "textures/entity/zombie_soldier.png");
//		entityTextures[3] = new ResourceLocation(Techguns.MODNAME.toLowerCase(), "textures/entity/army_soldier.png");
//		entityTextures[4] = new ResourceLocation("textures/entity/skeleton/skeleton.png");
//		entityTextures[5] = new ResourceLocation("textures/entity/villager/villager.png");
//		entityTextures[6] = new ResourceLocation("textures/entity/cow/cow.png");
//		entityTextures[7] = new ResourceLocation("textures/entity/sheep/sheep.png");
//		entityTextures[8] = new ResourceLocation("textures/entity/chicken.png");
//		entityTextures[9] = new ResourceLocation("textures/entity/creeper/creeper.png");
//		entityTextures[10] = new ResourceLocation("textures/entity/pig/pig.png");
//		entityTextures[11] = new ResourceLocation("textures/entity/enderman/enderman.png");
//		entityTextures[12] = new ResourceLocation("textures/entity/spider/spider.png");
	}

	@Override
	public void doRender(Entity par1entity, double x, double y, double z, float par8,
			float partialTickTime) {
		
		if (!(par1entity instanceof FlyingGibs)) return;
		
		FlyingGibs entity = (FlyingGibs)par1entity;
		
		this.bindEntityTexture(entity);
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        
        double angle = 0.0;
        double rot_angle = 90;
        if (entity.onGround) {
        	 angle = 5 + ((double)entity.hitGroundTTL / (double)entity.maxTimeToLive) * 15.0;
        	 rot_angle += ((double)(entity.maxTimeToLive-entity.hitGroundTTL) * angle); 
        	 
        	 //rot_angle += (360.0-rot_angle) * (double)(entity.hitGroundTTL-5) / (double)entity.maxTimeToLive;
        	 
        	 if (entity.hitGroundTTL-20 > entity.timeToLive) {
        		 double offsetY = ((double)((entity.hitGroundTTL-20)-entity.timeToLive) + partialTickTime) * -0.05f;
        		 GL11.glTranslated(0.0, offsetY, 0.0);
        	 }
        	 
        }else {
        	angle = 5 + ((double)entity.timeToLive / (double)entity.maxTimeToLive) * 15.0;
        	rot_angle += ((double)entity.ticksExisted+partialTickTime)*angle; 
        }
		
        GL11.glRotated(rot_angle, entity.rotationAxis.xCoord, entity.rotationAxis.yCoord, entity.rotationAxis.zCoord);
        
        GoreData data = DeathEffect.getGoreData(entity.entity.getClass());
        
        ModelGibs model = data.model;
        float scale = data.scale;
        
        Random rand = new Random(entity.getEntityId()+entity.bodypart);
        scale*= data.minPartScale+(rand.nextFloat()*(data.maxPartScale-data.minPartScale));
        
        
//        switch (entity.entityDT) {
//			case PLAYER:
//				model = model2;
//				scale *= 0.66f;
//				break;
//			case SKELETON:
//				model = modelSkeleton;
//				scale *= 0.66f;
//				break;
//			case VILLAGER:
//				model = modelVillager;
//				scale *= 0.66f;
//				break;
//			case COW:
//				model = modelCow;
//				break;
//			case SHEEP:
//				model = modelSheep;
//				break;
//			case CHICKEN:
//				model = modelChicken;
//				break;
//			case CREEPER:
//				model = modelCreeper;
//				break;
//			case PIG:
//				model = modelPig;
//				break;
//			case ENDERMAN:
//				model = modelEnderman;
//				break;
//			case SPIDER:
//				model = modelSpider;
//				break;
//			default:
//				model = model1;
//				scale *= 0.66f;
//				break;
//        }
        
        
        if (entity.entity.isChild()) {
        	scale*= 0.5f;
        }
        
        GL11.glDisable(GL11.GL_CULL_FACE);
        
        model.render(entity, (float)x,(float)y,(float)z, 0.0f, 0.0f, 0.0625f*scale, entity.bodypart);
        
        GL11.glEnable(GL11.GL_CULL_FACE);
        
        GL11.glPopMatrix();
		
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		if (entity instanceof FlyingGibs) {
//			switch (((FlyingGibs)entity).entityDT) {
//			case ARMY_SOLDIER:
//				return entityTextures[3];
//			case PLAYER:
//				return entityTextures[0];
//			case SKELETON:
//				return entityTextures[4];
//			case ZOMBIE:
//				return entityTextures[1];
//			case ZOMBIE_SOLDIER:
//				return entityTextures[2];
//			case VILLAGER:
//				return entityTextures[5];
//			case COW:
//				return entityTextures[6];
//			case SHEEP:
//				return entityTextures[7];
//			case CHICKEN:
//				return entityTextures[8];
//			case CREEPER:
//				return entityTextures[9];
//			case PIG:
//				return entityTextures[10];
//			case ENDERMAN:
//				return entityTextures[11];
//			case SPIDER:
//				return entityTextures[12];
//			case GENERIC:
//			default:
//				return entityTextures[1];
//			}
			return DeathEffect.getGoreData(((FlyingGibs)entity).entity.getClass()).texture;
		}
		//return entityTextures[1];
		return null;
	}

}
