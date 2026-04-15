package techguns.client.particle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import techguns.client.renderer.TGRenderHelper;
import techguns.client.renderer.TGRenderHelper.RenderType;
import techguns.util.MathUtil;

public class ParticleSystemType extends FXType{

	TGRenderHelper.RenderType renderType = RenderType.SOLID; //Type of Transparency
	ResourceLocation texture = new ResourceLocation("techguns:textures/fx/fireball.png"); //Particle Texture
	int rows = 1; //number of rows in the animated texture (1 for no animation)
	int columns = 1; //number of columns in the animated texture (1 for no animation)
	int frames = 1; //number of animated frames (1 for no animation)
	boolean hasVariations = false; //Frames are used as variation instead of animation
	boolean randomRotation = false;
	
	int lifetimeMin = 20; //Particle lifetime in ticks
	int lifetimeMax = 20;
	//int systemLifetimeMin = 5; //System lifetime in ticks
	//int systemLifetimeMax = 5;
	int initialDelayMin = 0; //Start after this many ticks.
	int initialDelayMax = 0;
	float animationSpeedMin = 1.0f; //1.0 = 1 full animation over particle lifetime
	float animationSpeedMax = 1.0f;
	
	float sizeMin = 1.0f; //Start size
	float sizeMax = 2.0f;
	float sizeRateMin = 1.0f; //Size increase per tick: Size = Size + (sizeRate * ticks)
	float sizeRateMax = 1.0f;
	float sizeRateDampingMin = 1.0f; //each tick: SizeRate = SizeRate * sizeRateDamping
	float sizeRateDampingMax = 1.0f;
	
	float startSizeRateMin = 0.0f; //particle start size increment per tick
	float startSizeRateMax = 0.0f;
	float startSizeRateDampingMin = 1.0f; //each tick: startSizeRate = startSizeRate * startsizeRateDamping
	float startSizeRateDampingMax = 1.0f;
	
	List<ColorEntry> colorEntries = new ArrayList<ColorEntry>();
	List<AlphaEntry> alphaEntries = new ArrayList<AlphaEntry>();
	
	int particleCountMin = 1; //number of Spawned particles each spawn/burst
	int particleCountMax = 1;
	int spawnDelayMin = 1; //time between spawns
	int spawnDelayMax = 1;
	int systemLifetimeMin = 5; //System's lifetime in ticks.
	int systemLifetimeMax = 5;

	IVelocityType velocityType = VEL_SPHERICAL;
	float[] velocityDataMin = new float[3];
	float[] velocityDataMax = new float[3];
	float velocityDampingMin = 0.98f; //each tick: Velocity = Velocity*VelocityDamping
	float velocityDampingMax = 0.98f;
	float velocityDampingOnGroundMin = 1f; //additional damping on ground
	float velocityDampingOnGroundMax = 1f;
	float gravity = 0.02f; //-Y motion increase per second
	float systemVelocityFactorMin = 0.0f; //How much of the system's velocity is added to the particle's velocity
	float systemVelocityFactorMax = 0.0f;
	
	Vec3 offset = Vec3.createVectorHelper(0.0f, 0.0f, 0.0f); //TODO! Particles are spawned at this offset
	
	IVolumeType volumeType;
	float[] volumeData = new float[3];
	boolean isHollow;
	
	boolean removeOnGround = false; //remove this particle if it touches the ground
	
	boolean particlesMoveWithSystem = false;
	
	public static class ColorEntry {
		float time;
		float r;
		float g;
		float b;
		
		public ColorEntry(int r, int g, int b, float time) {
			this(((float)r)/255.0f, ((float)g)/255.0f, ((float)b)/255.0f, time); 
		}
		
		public ColorEntry(float r, float g, float b, float time) {
			super();
			this.time = time;
			this.r = r;
			this.g = g;
			this.b = b;
		}
	
	}
	
	public static class AlphaEntry {
		float time;
		float alpha;
		public AlphaEntry(float alpha, float time) {
			super();
			this.time = time;
			this.alpha = 1.0f-alpha;
		}
	}
	
	public static IVelocityType VEL_ORTHO = new IVelocityType(){
		@Override
		public Vec3 getVelocity(EntityParticleSystem sys, Vec3 dir) {
			Random rand = new Random();
			Vec3 velocity = Vec3.createVectorHelper(0,0,0);
			if (sys.type.velocityDataMin.length >= 3) {
				float x = MathUtil.randomFloat(rand, sys.type.velocityDataMin[0], sys.type.velocityDataMax[0]);
				float y = MathUtil.randomFloat(rand, sys.type.velocityDataMin[1], sys.type.velocityDataMax[1]);
				float z = MathUtil.randomFloat(rand, sys.type.velocityDataMin[2], sys.type.velocityDataMax[2]);
				
				//if (sys.rotationPitch == 0.0f && sys.rotationYaw == 0.0f) {
				velocity.xCoord = x;
				velocity.yCoord = y;
				velocity.zCoord = z;
//				}else {
////					velocity.xCoord = z*MathUtil.sin360(sys.rotationYaw) + x*MathUtil.cos360(sys.rotationYaw);
////					velocity.zCoord = z*MathUtil.cos360(sys.rotationYaw) - x*MathUtil.sin360(sys.rotationYaw);				
//					velocity.rotateAroundZ(sys.rotationPitch);
//					velocity.rotateAroundY(sys.rotationYaw);
//				}
				
				//if (sys.entity != null) {
				//	velocity.rotateAroundZ(sys.rotationPitch);
				//	velocity.rotateAroundY(sys.rotationYaw);
				//}
			}
			return velocity;
		}		
	};
	
	public static IVelocityType VEL_OUTWARD = new IVelocityType(){
		@Override
		public Vec3 getVelocity(EntityParticleSystem sys, Vec3 dir) {
			Random rand = new Random();
			Vec3 velocity = Vec3.createVectorHelper(0,0,0);
			if (sys.type.velocityDataMin.length >= 2) {
				float r = MathUtil.randomFloat(rand, sys.type.velocityDataMin[0], sys.type.velocityDataMax[0]);
				float y = MathUtil.randomFloat(rand, sys.type.velocityDataMin[1], sys.type.velocityDataMax[1]);

				if (dir.xCoord != 0 || dir.yCoord != 0 || dir.zCoord != 0) {
					velocity.xCoord = dir.xCoord*r;
					velocity.zCoord = dir.zCoord*r;
					velocity.yCoord = y;
				}else {
					double angle = rand.nextDouble()*2.0*Math.PI;
					velocity.xCoord = r*Math.cos(angle);
					velocity.zCoord = r*Math.sin(angle);	
					velocity.yCoord = y;
				}
			}
			return velocity;
		}		
	};
	
	public static IVelocityType VEL_SPHERICAL = new IVelocityType(){
		@Override
		public Vec3 getVelocity(EntityParticleSystem sys, Vec3 dir) {
			Random rand = new Random();
			Vec3 velocity = Vec3.createVectorHelper(0,0,0);
			if (sys.type.velocityDataMin.length >= 1) {
				float r = MathUtil.randomFloat(rand, sys.type.velocityDataMin[0], sys.type.velocityDataMax[0]);

				if (dir.xCoord != 0 || dir.yCoord != 0 || dir.zCoord != 0) {
					velocity.xCoord = dir.xCoord*r;
					velocity.yCoord = dir.yCoord*r;
					velocity.zCoord = dir.zCoord*r;
				}else {
					double angle = rand.nextDouble()*2.0*Math.PI;
					double y = 1.0-(rand.nextDouble()*2.0);
					double a = Math.sqrt(1.0-Math.pow(y, 2));
					
					velocity.xCoord = r*a*Math.cos(angle);
					velocity.zCoord = r*a*Math.sin(angle);	
					velocity.yCoord = r*y;
				}
		
			}
			return velocity;
		}		
	};
	
	public static IVelocityType VEL_HEMISPHERICAL = new IVelocityType(){
		@Override
		public Vec3 getVelocity(EntityParticleSystem sys, Vec3 dir) {
			Random rand = new Random();
			Vec3 velocity = Vec3.createVectorHelper(0,0,0);
			if (sys.type.velocityDataMin.length >= 1) {
				float r = MathUtil.randomFloat(rand, sys.type.velocityDataMin[0], sys.type.velocityDataMax[0]);

				if (dir.xCoord != 0 || dir.yCoord != 0 || dir.zCoord != 0) {
					velocity.xCoord = dir.xCoord*r;
					velocity.yCoord = dir.yCoord*r;
					velocity.zCoord = dir.zCoord*r;
				}else {
					double angle = rand.nextDouble()*2.0*Math.PI;
					double y = rand.nextDouble();
					double a = Math.sqrt(1.0-Math.pow(y, 2));
					
					velocity.xCoord = r*a*Math.cos(angle);
					velocity.zCoord = r*a*Math.sin(angle);	
					velocity.yCoord = r*y;
				}
		
			}
			return velocity;
		}		
	};
	
	public static IVolumeType VOL_POINT = new IVolumeType() {

		@Override
		public Vec3 getPosition(EntityParticleSystem sys, Vec3 dir, int i, int count) {
//			if (sys.rotationPitch != 0.0f || sys.rotationYaw != 0.0f) {	
//				dir.rotateAroundZ(sys.rotationPitch);
//				dir.rotateAroundY(sys.rotationYaw);
//			}
			return Vec3.createVectorHelper(0, 0, 0);
		}
		
	};
	
	
	public static IVolumeType VOL_CYLINDER = new IVolumeType() {
		@Override
		public Vec3 getPosition(EntityParticleSystem sys, Vec3 dir, int i, int count) {
			Random rand = new Random();
			if (sys.type.volumeData.length >= 2) {
				float r;
				float y;
				float angle = rand.nextFloat()*(float)Math.PI*2.0f;
				if (sys.type.isHollow) {
					r = sys.type.volumeData[0];
				}else {
					r = MathUtil.randomFloat(rand, 0.0f, sys.type.volumeData[0]);
				}
				y = rand.nextFloat()*sys.type.volumeData[1];
				dir.xCoord = 1.0;
				dir.yCoord = 0.0;
				dir.zCoord = 0.0;
				dir.rotateAroundY(angle);
				Vec3 position = Vec3.createVectorHelper(dir.xCoord*r, y, dir.zCoord*r);
				return position;
			}else {
				//fail;
				return Vec3.createVectorHelper(0,0,0);
			}	
		}		
	};
	
	public static IVolumeType VOL_CYLINDER2 = new IVolumeType() { //Tilted cylinder
		@Override
		public Vec3 getPosition(EntityParticleSystem sys, Vec3 dir, int i, int count) {
			Random rand = new Random();
			if (sys.type.volumeData.length >= 2) {
				float r;
				float x;
				float angle = rand.nextFloat()*(float)Math.PI*2.0f;
				if (sys.type.isHollow) {
					r = sys.type.volumeData[0];
				}else {
					r = MathUtil.randomFloat(rand, 0.0f, sys.type.volumeData[0]);
				}
				x = rand.nextFloat()*sys.type.volumeData[1];
				dir.xCoord = 0.0;
				dir.yCoord = 1.0;
				dir.zCoord = 0.0;
				dir.rotateAroundX(angle);
				Vec3 position = Vec3.createVectorHelper(x, dir.yCoord*r, dir.zCoord*r);
				return position;
			}else {
				//fail;
				return Vec3.createVectorHelper(0,0,0);
			}	
		}		
	};
	
	public static IVolumeType VOL_SPHERE = new IVolumeType() {
		@Override
		public Vec3 getPosition(EntityParticleSystem sys, Vec3 dir, int i, int count) {
			Random rand = new Random();
			if (sys.type.volumeData.length >= 1) {
				float r;
				double angle = rand.nextDouble()*2.0*Math.PI;
				double y = 1.0-(rand.nextDouble()*2.0);
				double a = Math.sqrt(1.0-Math.pow(y, 2));
				
				if (sys.type.isHollow) {
					r = sys.type.volumeData[0];
				}else {
					r = MathUtil.randomFloat(rand, 0.0f, sys.type.volumeData[0]);
				}

				dir.xCoord = a*Math.cos(angle);
				dir.zCoord = a*Math.sin(angle);	
				dir.yCoord = y;
				
				Vec3 position = Vec3.createVectorHelper(dir.xCoord*r, dir.yCoord*r, dir.zCoord*r);
				return position;
			}else {
				//fail;
				return Vec3.createVectorHelper(0,0,0);
			}	
		}		
	};
	
	public static IVolumeType VOL_HEMISPHERE = new IVolumeType() {
		@Override
		public Vec3 getPosition(EntityParticleSystem sys, Vec3 dir, int i, int count) {
			Random rand = new Random();
			if (sys.type.volumeData.length >= 1) {
				float r;
				double angle = rand.nextDouble()*2.0*Math.PI;
				double y = rand.nextDouble();
				double a = Math.sqrt(1.0-Math.pow(y, 2));
				
				if (sys.type.isHollow) {
					r = sys.type.volumeData[0];
				}else {
					r = MathUtil.randomFloat(rand, 0.0f, sys.type.volumeData[0]);
				}

				dir.xCoord = a*Math.cos(angle);
				dir.zCoord = a*Math.sin(angle);	
				dir.yCoord = y;
				
				Vec3 position = Vec3.createVectorHelper(dir.xCoord*r, dir.yCoord*r, dir.zCoord*r);
				return position;
			}else {
				//fail;
				return Vec3.createVectorHelper(0,0,0);
			}	
		}		
	};
	
	public static IVolumeType VOL_TRAIL = new IVolumeType() {
		@Override
		public Vec3 getPosition(EntityParticleSystem sys, Vec3 dir, int i, int count) {
			Random rand = new Random();
			if (sys.type.volumeData.length >= 1) {

				float len;
				
				Entity ent = sys.entity;
				if (ent==null){
					return Vec3.createVectorHelper(sys.posX,sys.posY,sys.posZ);
				} else {
					
					Vec3 motion = Vec3.createVectorHelper(ent.motionX, ent.motionY, ent.motionZ).normalize();
					//Vec3 motion = Vec3.createVectorHelper(1.0, 0.0, 0.0);
					
					len = sys.type.volumeData[0];
					float t = (i*1.0f) / (count*1.0f) * len;
					
					Vec3 position = Vec3.createVectorHelper(-motion.xCoord*t, -motion.yCoord*t, -motion.zCoord*t); 
					return position;
				}

			}else {
				//fail;
				return Vec3.createVectorHelper(0,0,0);
			}	
		}		
	};
	
	public interface IVelocityType {		
		public Vec3 getVelocity(EntityParticleSystem sys, Vec3 dir);
	}
	
	public interface IVolumeType {
		public Vec3 getPosition(EntityParticleSystem sys, Vec3 dir, int i, int count);
	}

	public void extend(ParticleSystemType other) {
		this.alphaEntries = other.alphaEntries;
		this.animationSpeedMax = other.animationSpeedMax;
		this.animationSpeedMin = other.animationSpeedMin;
		this.colorEntries = other.colorEntries;
		this.columns = other.columns;
		this.frames = other.frames;
		this.gravity = other.gravity;
		this.hasVariations = other.hasVariations;
		this.initialDelayMax = other.initialDelayMin;
		this.isHollow = other.isHollow;
		this.lifetimeMax = other.lifetimeMax;
		this.lifetimeMin = other.lifetimeMin;
		this.particleCountMax = other.particleCountMax;
		this.randomRotation = other.randomRotation;
		this.removeOnGround = other.removeOnGround;
		this.renderType = other.renderType;
		this.rows = other.rows;
		this.sizeMax = other.sizeMax;
		this.sizeMin = other.sizeMin;
		this.sizeRateDampingMax = other.sizeRateDampingMax;
		this.sizeRateDampingMin = other.sizeRateDampingMin;
		this.sizeRateMax = other.sizeRateMax;
		this.sizeRateMin = other.sizeRateMin;
		this.spawnDelayMax = other.spawnDelayMax;
		this.spawnDelayMin = other.spawnDelayMin;
		this.texture = other.texture;
		this.velocityDampingMax = other.velocityDampingMax;
		this.velocityDampingMin = other.velocityDampingMin;
		this.velocityDampingOnGroundMax = other.velocityDampingOnGroundMax;
		this.velocityDampingOnGroundMin = other.velocityDampingOnGroundMin;
		this.velocityDataMax = other.velocityDataMax;
		this.velocityDataMin = other.velocityDataMin;
		this.velocityType = other.velocityType;
		this.volumeData = other.volumeData;
		this.volumeType = other.volumeType;
		this.offset = other.offset;
		this.particlesMoveWithSystem = other.particlesMoveWithSystem;
	}

	@Override
	public List<EntityParticleSystem> createParticleSystems(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
		ArrayList<EntityParticleSystem> list = new ArrayList<EntityParticleSystem>();
		list.add(new EntityParticleSystem(world, this, posX, posY, posZ, motionX, motionY, motionZ));
		return list;
	}

	@Override
	public List<EntityParticleSystem> createParticleSystemsOnEntity(Entity ent) {
		ArrayList<EntityParticleSystem> list = new ArrayList<EntityParticleSystem>();
		list.add(new EntityParticleSystem(ent,this));
		return list;
	}
	
	 /**
	 * z' = z*cos( angle) - x*sin (angle)
	 * x' = z*sin( angle) + x*cos (angle)
	 */

}
