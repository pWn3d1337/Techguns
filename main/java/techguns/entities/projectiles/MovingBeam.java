package techguns.entities.projectiles;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import techguns.Techguns;
import techguns.damagesystem.TGDamageSource;
import techguns.entities.npc.NPCTurret;
import techguns.items.guns.Beamgun;
import techguns.util.EntityDeathUtils.DeathType;
import techguns.util.MathUtil;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class MovingBeam extends GenericProjectile implements IEntityAdditionalSpawnData {

	public int shooterID;
	//public int[] FXIDs; //Client sided particlesystem EntityIDs

	public double beamlength;
	
	public MovingBeam(World worldObj) {
		super(worldObj);
		this.ignoreFrustumCheck=true;
		this.lifetime = Beamgun.BEAM_LIFETIME;
		this.ticksToLive = Beamgun.BEAM_LIFETIME;
		
		if (worldObj.isRemote) {
			//this.FXIDs = Techguns.proxy.createFXOnEntity_ID("BeamGunMuzzleFX", this);
			Techguns.proxy.createFXOnEntity("BeamGunMuzzleFX", this);
		}
	}
	
	private double distance = 0;

	@Override
	public void writeSpawnData(ByteBuf buffer) {
//		buffer.writeDouble(beamlength);
		if (this.shooter != null)
			buffer.writeInt(this.shooter.getEntityId());
		else buffer.writeInt(0);
	//	buffer.writeLong(seed);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
//	    this.beamlength = additionalData.readDouble();
		this.shooterID = additionalData.readInt();
	//	this.seed = additionalData.readLong();
		// System.out.println("BeamLenght:"+this.beamlength);
	}

	/*
	 * public LaserBeam2(World worldObj, EntityLivingBase shooter, double x,
	 * double y, double z, Entity target, double beamlength) { super(worldObj,
	 * shooter, x, y, z, target, beamlength); this.lifetime =
	 * Lasergun.BEAM_LIFETIME; this.ticksToLive =Lasergun.BEAM_LIFETIME; }
	 * 
	 * public LaserBeam2(World worldObj, EntityLivingBase shooter, double x,
	 * double y, double z, Entity target) { super(worldObj, shooter, x, y, z,
	 * target); this.lifetime = Lasergun.BEAM_LIFETIME; this.ticksToLive
	 * =Lasergun.BEAM_LIFETIME; }
	 * 
	 * public LaserBeam2(World worldObj, EntityLivingBase shooter, double
	 * beamlength) { super(worldObj, shooter, beamlength); this.lifetime =
	 * Lasergun.BEAM_LIFETIME; this.ticksToLive =Lasergun.BEAM_LIFETIME; }
	 */

	public MovingBeam(World par2World, double posX, double posY, double posZ, float yaw, float pitch, float damage, float speed, int TTL, float spread,
			int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage) {
		super(par2World, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, false);
	}

	public MovingBeam(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd, float dmgMin,
			float penetration, boolean blockdamage) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage);
	}


	
	
	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causeRadiationDamage(this, this.shooter, DeathType.LASER);
		src.setNoKnockback();
		src.armorPenetration = this.penetration;
		src.goreChance=1.0f;
		return src;
	}



	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass==1;
	}

	
	public Vec3 getFPOffset(){
		return Vec3.createVectorHelper(0, -0.08, 0.12);
	}

	public float get3PYOffset(){
		return 0.0f;
	}
	
	public float getBeam3PYOffset() {
		return 0.0f;
	}
	
	@Override
	public void onUpdate() {
	//	super.onUpdate();
	//	if (this.worldObj.isRemote) {
			// ----------------
			// - Trace Length -
			double targetX = 0;
			double targetY = 0;
			double targetZ = 0;
			double dx, dy, dz;			
			float f = 100.0F;
			Vec3 vec3;
			
			if (this.shooter == null) {
				this.shooter = (EntityLivingBase) this.worldObj.getEntityByID(shooterID);
			}
			if (shooter != null) {
				
				//System.out.println("Shooter Yaw:"+this.shooter.rotationYaw);
				//System.out.println("Shooter YawHead:"+this.shooter.rotationYawHead);
				
				dx = (double) (-MathHelper.sin(shooter.rotationYawHead / 180.0F
						* (float) Math.PI)
						* MathHelper.cos(shooter.rotationPitch / 180.0F
								* (float) Math.PI) * f);
				dz = (double) (MathHelper.cos(shooter.rotationYawHead / 180.0F
						* (float) Math.PI)
						* MathHelper.cos(shooter.rotationPitch / 180.0F
								* (float) Math.PI) * f);
				dy = (double) (-MathHelper.sin((shooter.rotationPitch) / 180.0F
						* (float) Math.PI) * f);
		
				//Adjust position
				this.posX = shooter.posX;				
			    this.posY = shooter.posY;
			    this.posZ = shooter.posZ;
//			    if (!worldObj.isRemote || !(shooter == Minecraft.getMinecraft().thePlayer && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0)){
//			    	this.posY+= shooter.getEyeHeight();
//			    }
			    
			   // if (shooter == Minecraft.getMinecraft().thePlayer && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
		        	//System.out.println("move Beam in first person mode."); 
			    if (Techguns.proxy.isClientPlayerAndIn1stPerson(shooter)){
			    	Vec3 offset = this.getFPOffset();
			         MathUtil.rotateAroundZ(offset, shooter.rotationPitch*MathUtil.D2R);
			         MathUtil.rotateAroundY(offset, ((-90.0-shooter.rotationYawHead)*MathUtil.D2R));
			         
			         this.posX+=offset.xCoord;
			         this.posY+=offset.yCoord;
			         this.posZ+=offset.zCoord;
			    } else if (shooter instanceof NPCTurret) {
			    	Vec3 offset = Vec3.createVectorHelper(0, shooter.getEyeHeight() -0.10000000149011612D, -0.1);
			         MathUtil.rotateAroundZ(offset, shooter.rotationPitch*MathUtil.D2R);
			         MathUtil.rotateAroundY(offset, ((-90.0-shooter.rotationYawHead)*MathUtil.D2R));
			         
			         this.posX+=offset.xCoord;
			         this.posY+=offset.yCoord;
			         this.posZ+=offset.zCoord;
			    	
			    }else {
			    	this.posX-=(double)(MathHelper.cos(shooter.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
			        this.posY += shooter.getEyeHeight() -0.10000000149011612D + this.get3PYOffset();
			        this.posZ -= (double)(MathHelper.sin(shooter.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
			    }
			    
			    vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			    
			    this.rotationPitch = shooter.rotationPitch;
			    this.rotationYaw = shooter.rotationYawHead;
			}else {
				
				dx = (double) (-MathHelper.sin(this.rotationYaw / 180.0F
						* (float) Math.PI)
						* MathHelper.cos(this.rotationPitch / 180.0F
								* (float) Math.PI) * f);
				dz = (double) (MathHelper.cos(this.rotationYaw / 180.0F
						* (float) Math.PI)
						* MathHelper.cos(this.rotationPitch / 180.0F
								* (float) Math.PI) * f);
				dy = (double) (-MathHelper.sin((this.rotationPitch) / 180.0F
						* (float) Math.PI) * f);
				vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			}
			
			
			Vec3 vec31 = Vec3.createVectorHelper(dx, dy, dz);
			vec31.normalize();
			// MovingObjectPosition movingobjectposition =
			// this.worldObj.rayTraceBlocks(vec3, vec31);

			MovingObjectPosition movingobjectposition = MathUtil.traceVector(
					this.worldObj, vec3, vec31, 12.0, 4, this, this.shooter);

			if (movingobjectposition != null) {

				switch (movingobjectposition.typeOfHit) {
				case BLOCK:
					targetX = movingobjectposition.hitVec.xCoord;
					targetY = movingobjectposition.hitVec.yCoord;
					targetZ = movingobjectposition.hitVec.zCoord;
				 //System.out.println("Block hit!");
					break;
				case ENTITY:
					targetX = movingobjectposition.hitVec.xCoord;
					targetY = movingobjectposition.hitVec.yCoord;
					targetZ = movingobjectposition.hitVec.zCoord;

					if(!this.worldObj.isRemote){
						this.distance = Vec3.createVectorHelper(this.posX, this.posY, this.posZ).distanceTo(movingobjectposition.hitVec);
						
						this.onImpact(movingobjectposition);
						this.isDead=false;
					}
					
				  //System.out.println("Entity hit!:"+movingobjectposition.entityHit.getEntityId());
					// movingobjectposition.entityHit.attackEntityFrom(BulletDamageSource.causeBulletDamage(this,
					// this.shooter, false), this.damage);

					break;
				case MISS:
					targetX = dx;
					targetY = dy;
					targetZ = dz;
					//System.out.println("Miss!");
					break;
				}
			} else {
				targetX = dx;
				targetY = dy;
				targetZ = dz;
			}

			if (/*worldObj.isRemote && */ this.getParticleName() != null) {
				Techguns.proxy.createFX(getParticleName(), this.worldObj, targetX, targetY, targetZ, 0.0D, 0.0D, 0.0D);
			}

			Vec3 pos = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			this.beamlength = pos.distanceTo(Vec3.createVectorHelper(targetX,
					targetY, targetZ));
	//	}
			
		--ticksToLive;
		if (ticksToLive <=0){
			this.setDead();
		}
	}

	
	
	@Override
	protected double getDistanceTravelled() {
		return this.distance;
	}

	private String getParticleName() {
		return "BeamGunImpactFX";
	}



	public boolean canBeCollidedWith()
    {
        return false;
    }

}
