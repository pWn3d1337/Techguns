package techguns.client.particle;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import techguns.client.ClientProxy;
import techguns.util.MathUtil;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityParticleSystem extends Entity implements IEntityAdditionalSpawnData{
	
	ParticleSystemType type;
	int systemLifetime;
	int spawnDelay = 0;
	
	public float scale; //global scale
	public float rotationYaw;
	public float rotationPitch;
	
	public int initialDelay;
	public int ticksExisted = 0;
	
	public float startSizeRate;
	public float startSizeRateDamping;
	public float startSize = 0.0f;;
	
	Entity entity; //parent entity
	
	public EntityParticleSystem(World world) {
		super(world);
		//System.out.println("Lol clientSide Shitty constructor");
	}
	
	public EntityParticleSystem(Entity entity, ParticleSystemType type) {
		this(entity.worldObj, type, entity.posX,entity.posY,entity.posZ,0,0,0);
		this.entity = entity;
		//System.out.println("Created ParticleSystem on "+entity);
	}
	
	
	public EntityParticleSystem(World world, ParticleSystemType type, double posX, double posY, double posZ) {
		this(world, type, posX, posY, posZ, 0,0,0);
	}

	public EntityParticleSystem(World world, ParticleSystemType type, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
		super(world);
		this.type = type;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
		this.init();
	}
	
	protected void init() {
		this.systemLifetime = MathUtil.randomInt(this.rand, type.systemLifetimeMin, type.systemLifetimeMax);
		this.initialDelay = MathUtil.randomInt(this.rand, type.initialDelayMin, type.initialDelayMax);
		this.startSizeRate = MathUtil.randomFloat(this.rand, type.startSizeRateMin, type.startSizeRateMax);
		this.startSizeRateDamping = MathUtil.randomFloat(this.rand, type.startSizeRateDampingMin, type.startSizeRateDampingMax);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		//System.out.println("Update Tick");
		
		if (type == null) {
			//System.out.println("WTF?");
			this.setDead();
			return;
		}
		
		if (this.entity!=null){
			//System.out.println("Updating Position");
			
			this.posX=entity.posX;
			this.posY=entity.posY;
			this.posZ=entity.posZ;
			

//			if (entity instanceof EntityLivingBase) {
//				this.posY+= entity.getEyeHeight();
//				this.rotationPitch = entity.rotationPitch;
//				this.rotationYaw = ((EntityLivingBase) entity).rotationYawHead;				
//			}else {
				this.rotationPitch=entity.rotationPitch;
				this.rotationYaw=entity.rotationYaw;
			//}
			
		} else {
			this.posX+=motionX;
			this.posY+=motionY;
			this.posZ+=motionZ;
		}
		
		if (initialDelay-- > 0) {
			return;
		}
		
		this.startSize = this.startSize+this.startSizeRate;
		this.startSizeRate = (this.startSizeRate * this.startSizeRateDamping);
		
		
		if (spawnDelay-- <= 0) {
			int count = MathUtil.randomInt(rand, type.particleCountMin, type.particleCountMax);
			for (int i = 0; i < count; i++) {
				//Get position and motion data
				Vec3 dir = Vec3.createVectorHelper(0,0,0);
				Vec3 position = type.volumeType.getPosition(this, dir, i, count);
				position = position.addVector(type.offset.xCoord, type.offset.yCoord, type.offset.zCoord);
				Vec3 motion = type.velocityType.getVelocity(this, dir);
				//apply ParticleSystem's entity rotation
				//System.out.println("Motion: ("+motion.xCoord+ ", "+motion.yCoord + ", "+ motion.zCoord+")");
				//System.out.println("ParticleSystemRotation - Pitch: "+this.rotationPitch+" - Yaw: "+this.rotationYaw);
				MathUtil.rotateAroundZ(motion, this.rotationPitch*MathUtil.D2R);
				MathUtil.rotateAroundY(motion, ((-90.0-this.rotationYaw)*MathUtil.D2R));
				if (type.volumeType != ParticleSystemType.VOL_TRAIL) {
					MathUtil.rotateAroundZ(position, this.rotationPitch*MathUtil.D2R);
					MathUtil.rotateAroundY(position, (-90-this.rotationYaw)*MathUtil.D2R);			
				}
				//Spawn particle
				double mf = 0.05D; //Per Second instead of Per Tick
				EntityParticle particle = new EntityParticle(this.worldObj, this.posX+position.xCoord, this.posY+position.yCoord, this.posZ+position.zCoord, motion.xCoord*mf, motion.yCoord*mf, motion.zCoord*mf, this);
				ClientProxy.spawnParticle(particle);
			}			
			spawnDelay = MathUtil.randomInt(this.rand, type.spawnDelayMin, type.spawnDelayMax);
		}
		
		if (this.entity!=null){
			if (this.entity.isDead){
				this.setDead();
			} else if ( this.systemLifetime>0 && ticksExisted>= this.systemLifetime){
				this.setDead();
			}
			
		} else {
			if (ticksExisted >= this.systemLifetime) {
				this.setDead();
			}
		}
		ticksExisted++;
	}
	

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		if (this.entity!=null){
			buffer.writeBoolean(true);
			buffer.writeInt(this.entity.getEntityId());
		} else {
			buffer.writeBoolean(false);
		}
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		boolean hasEnt = additionalData.readBoolean();
		if (hasEnt){
			int entID = additionalData.readInt();
			this.entity=this.worldObj.getEntityByID(entID);
		}
	}

}
