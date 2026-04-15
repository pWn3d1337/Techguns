package techguns.entities.projectiles;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.items.guns.Teslagun;
import techguns.packets.PacketSpawnParticle;
import techguns.util.MathUtil;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

/*
 * Laser Beam for LaserGun. Visuals only.
 */

public class TeslaBeam extends Entity implements IEntityAdditionalSpawnData{
	
	protected int ticksToLive = Teslagun.LIFETIME;
	protected int lifetime= Teslagun.LIFETIME; //TTL but not reduced per tick
	protected EntityLivingBase shooter;
	private double targetX;
	private double targetY;
	private double targetZ;
	
	public long seed;
	public double beamlength;
	
	public TeslaBeam(World worldObj, EntityLivingBase shooter) {
		this(worldObj, shooter, -1.0d);
	}
	
	protected String getParticleName(){
		return "TeslaFlare";
	}
	
	protected String getParticleNameStart(){
		return null;
	}
	
	protected void setInitialPosition(EntityLivingBase shooter, float yaw, float pitch){
        this.setLocationAndAngles(shooter.posX, shooter.posY + (double)shooter.getEyeHeight(), shooter.posZ, yaw, pitch);
	}
	
	public TeslaBeam(World worldObj, EntityLivingBase shooter, double beamlength) {
		super(worldObj);
		this.shooter = shooter;
        
		//this.setLocationAndAngles(shooter.posX, shooter.posY + (double)shooter.getEyeHeight(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
        
        if(this.shooter!=null){
	        float yaw=shooter.rotationYawHead;
	        float pitch=shooter.rotationPitch;
	        this.setInitialPosition(shooter,yaw,pitch);
        }
		
		this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        
        this.seed = this.rand.nextLong();        
        //----------------
        //- Trace Length -
        if (beamlength >= 0) {
        	this.beamlength = beamlength;
        }else {
        
	        float f = 1.0F;
	        double dx = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
	        double dz = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
	        double dy = (double)(-MathHelper.sin((this.rotationPitch ) / 180.0F * (float)Math.PI) * f);
			
			 Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		     Vec3 vec31 = Vec3.createVectorHelper(dx, dy, dz);
		     //MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
		    
		     MovingObjectPosition movingobjectposition = MathUtil.traceVector(this.worldObj, vec3, vec31, 10.0, 10, this, this.shooter);
		     
		     if (movingobjectposition != null) {
		    	 
			     switch (movingobjectposition.typeOfHit) {
					case BLOCK:
						targetX =movingobjectposition.hitVec.xCoord;
						targetY =movingobjectposition.hitVec.yCoord;
						targetZ =movingobjectposition.hitVec.zCoord;
						//System.out.println("Block hit!");
						break;
					case ENTITY:
						targetX =movingobjectposition.hitVec.xCoord;
						targetY =movingobjectposition.hitVec.yCoord;
						targetZ =movingobjectposition.hitVec.zCoord;
						
						//System.out.println("Entity hit!");
						//movingobjectposition.entityHit.attackEntityFrom(BulletDamageSource.causeBulletDamage(this, this.shooter, false), this.damage);
						
						break;
					case MISS:
					    targetX = dx;
					    targetY = dy;
					    targetZ = dz;
						break;
			     }
		     }else {
		        targetX = dx;
			    targetY = dy;
			    targetZ = dz;
		     }
		     
		     /*if (!this.worldObj.isRemote) {
			    	Techguns.proxy.spawnParticle(getParticleName(), this.worldObj, targetX, targetY, targetZ, 0.0D, 0.0D, 0.0D);
			    }*/
			
		     //System.out.println("From:"+vec3.toString());
		     //System.out.println("To:"+targetX+" "+targetY+" "+targetZ);
		     
			 if(!this.worldObj.isRemote){
				 if (this.getParticleName()!=null){
					 TGPackets.network.sendToAllAround(new PacketSpawnParticle(this.getParticleName(), targetX,targetY,targetZ), new TargetPoint(this.worldObj.provider.dimensionId, targetX,targetY,targetZ, 50.0f));
				 }
				 if (this.getParticleNameStart()!=null){
					 TGPackets.network.sendToAllAround(new PacketSpawnParticle(this.getParticleNameStart(), this.posX,this.posY,this.posZ), new TargetPoint(this.worldObj.provider.dimensionId, this.posX,this.posY,this.posZ, 50.0f));
				 }
			 }
		     
		     Vec3 pos = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			 this.beamlength = pos.distanceTo(Vec3.createVectorHelper(targetX, targetY, targetZ));
			 

			// System.out.println("BeamLenght:"+this.beamlength);
			 
        }
	}

	//Client sided constructor
	public TeslaBeam(World worldObj) {
		super(worldObj);
		this.ignoreFrustumCheck = true;
	}
	

	
	public TeslaBeam(World worldObj, EntityLivingBase shooter, double x,
			double y, double z, Entity target) {
		this (worldObj, shooter, x, y, z, target, -1.0);
	}
	
	public TeslaBeam(World worldObj, EntityLivingBase shooter, double x,
			double y, double z, Entity target, double beamlength) {
		super(worldObj);
		this.shooter = shooter;
		double dX = x - target.posX;
        double dY = y - target.posY+ (target.height/2.0f);
        double dZ = z - target.posZ;
        
        //double yaw = Math.atan2(dZ, dX);
        //double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;        
        
        double yaw = (Math.atan2(dZ, dX) * (180.0/Math.PI)) -90.0;
        double pitch = (Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) * (180.0/Math.PI)) + 90.0;        
        
       // System.out.println("Chaining Beam Yaw: "+yaw + " - pitch: "+pitch);
        
        this.setLocationAndAngles(x, y, z, (float)yaw, (float)pitch);
        //this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        //this.posY -= 0.10000000149011612D;
        //this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        //this.setPosition(this.posX, this.posY, this.posZ);
        //this.yOffset = 0.0F;
        
		this.seed = this.rand.nextLong();        	     
		/*if (!this.worldObj.isRemote) {
	    	Techguns.proxy.spawnParticle(getParticleName(), this.worldObj, target.posX, target.posY+(target.height/2.0f), target.posZ, 0.0D, 0.0D, 0.0D);
	    }*/
		Vec3 vec3 = Vec3.createVectorHelper(x, y, z);
		this.beamlength = vec3.distanceTo(Vec3.createVectorHelper(target.posX, target.posY+(target.height/2.0f), target.posZ));

	}

	@Override
	public void onUpdate() {	
		
		if (ticksToLive == lifetime) {
			 //System.out.println("Length:"+this.beamlength);
			 //System.out.printf(Thread.currentThread().toString()+"[Projectile] Target(%3.2f, %3.2f, %3.2f)\n", targetX, targetY, targetZ);
		}

		--ticksToLive;
		if (ticksToLive <=0){
			this.setDead();
		}
	}
	
	
	
	@Override
	protected void entityInit() {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		
	}


	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeDouble(beamlength);
		buffer.writeLong(seed);
	}


	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.beamlength = additionalData.readDouble();
		this.seed = additionalData.readLong();
		//System.out.println("BeamLenght:"+this.beamlength);
	}

}
