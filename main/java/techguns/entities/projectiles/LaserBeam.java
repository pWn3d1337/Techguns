package techguns.entities.projectiles;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import techguns.Techguns;
import techguns.items.guns.Lasergun;
import techguns.util.MathUtil;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

/*
 * Laser Beam for LaserGun. Visuals only.
 */

@Deprecated
public class LaserBeam extends Entity implements IEntityAdditionalSpawnData {
	
	protected int ticksToLive = Lasergun.LIFETIME;
	protected int lifetime= Lasergun.LIFETIME; //TTL but not reduced per tick
	protected EntityLivingBase shooter;
	
	public double targetX;
	public double targetY;
	public double targetZ;
	
	
	public LaserBeam(World worldObj, EntityLivingBase shooter) {
		super(worldObj);
		this.shooter = shooter;
		
        float yaw=shooter.rotationYawHead;
        float pitch=shooter.rotationPitch;
        this.setLocationAndAngles(shooter.posX, shooter.posY + (double)shooter.getEyeHeight(), shooter.posZ, yaw, pitch);

        //this.setLocationAndAngles(shooter.posX, shooter.posY + (double)shooter.getEyeHeight(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
		
        float f = 0.36f;
        double dx = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        double dz = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        double dy = (double)(-MathHelper.sin((this.rotationPitch ) / 180.0F * (float)Math.PI) * f);
        this.posX+=dx;
        this.posY+=dy;
        this.posZ+=dz;
        Techguns.proxy.spawnParticle("LaserFlareStart", this.worldObj, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        
        
        
        //calculate Target impact
		f = 500.0F;
        dx = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        dz = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        dy = (double)(-MathHelper.sin((this.rotationPitch ) / 180.0F * (float)Math.PI) * f);
		
		 Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
	     Vec3 vec31 = Vec3.createVectorHelper(this.posX + dx, this.posY + dy, this.posZ + dz);
	     
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
					if (movingobjectposition.entityHit != this.shooter) {
						targetX =movingobjectposition.hitVec.xCoord;
						targetY =movingobjectposition.hitVec.yCoord;
						targetZ =movingobjectposition.hitVec.zCoord;
						break;
					}		
					//System.out.println("Entity hit!");
					//movingobjectposition.entityHit.attackEntityFrom(BulletDamageSource.causeBulletDamage(this, this.shooter, false), this.damage);		
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

	}

	//Client sided constructor
	public LaserBeam(World worldObj) {
		super(worldObj);
		this.ignoreFrustumCheck = true;
		// TODO Auto-generated constructor stub
	}

	
	
	
	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass==1;
	}

	@Override
	public void onUpdate() {	
		
	if (ticksExisted == 1) {

		/*
			float f = 500.0F;
	        double dx = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
	        double dz = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
	        double dy = (double)(-MathHelper.sin((this.rotationPitch ) / 180.0F * (float)Math.PI) * f);
			
			 Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		     Vec3 vec31 = Vec3.createVectorHelper(this.posX + dx, this.posY + dy, this.posZ + dz);
		     
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
						if (movingobjectposition.entityHit != this.shooter) {
							targetX =movingobjectposition.hitVec.xCoord;
							targetY =movingobjectposition.hitVec.yCoord;
							targetZ =movingobjectposition.hitVec.zCoord;
							break;
						}		
						//System.out.println("Entity hit!");
						//movingobjectposition.entityHit.attackEntityFrom(BulletDamageSource.causeBulletDamage(this, this.shooter, false), this.damage);		
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
		     
		    if (!this.worldObj.isRemote) {
		    	Techguns.proxy.spawnParticle("LaserFlareEnd", this.worldObj, targetX, targetY, targetZ, 0.0D, 0.0D, 0.0D);
		    }
		     
		     //Techguns.proxy.spawnParticle("LaserBeam", this.worldObj, this.posX, this.posY, this.posZ, targetX,targetY,targetZ);
		     
		     
		     
			
			//System.out.printf(Thread.currentThread().toString()+"[Projectile] Target(%3.2f, %3.2f, %3.2f)\n", targetX, targetY, targetZ);
		} */

		    if (this.worldObj.isRemote) {
		    	Techguns.proxy.spawnParticle("LaserFlareEnd", this.worldObj, targetX, targetY, targetZ, 0.0D, 0.0D, 0.0D);
		    }
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
		buffer.writeDouble(targetX);
		buffer.writeDouble(targetY);
		buffer.writeDouble(targetZ);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.targetX=additionalData.readDouble();
		this.targetY=additionalData.readDouble();
		this.targetZ=additionalData.readDouble();
		//System.out.println("Target:"+this.targetX+" "+this.targetY+" "+this.targetZ);
	}

}
