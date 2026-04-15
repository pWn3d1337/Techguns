package techguns.entities.projectiles;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import techguns.damagesystem.DamageSystem;
import techguns.damagesystem.TGDamageSource;
import techguns.debug.Keybinds;
import techguns.entities.npc.ITGNpcTeam;
import techguns.entities.npc.TGNpcFactions;
import techguns.util.EntityDeathUtils.DeathType;
import techguns.util.MathUtil;
import techguns.util.MathUtil.Vec2;

public class GenericProjectile extends Entity implements IProjectile
{
	
	float damage;
	float speed=1.0f; //multiplier to default speed
	
	protected int ticksToLive = 100;
	protected int lifetime=100; //TTL but not reduced per tick
	
	//DMG drop stats
	int damageDropStart;
	int damageDropEnd;
	float damageMin;
	protected EntityLivingBase shooter;
	
	float penetration = 0.0f;
	
	boolean silenced = false;
	protected boolean blockdamage=false;
	
	
	
	
	boolean posInitialized=false;
	double startX;
	double startY;
	double startZ;
	
//	public GenericProjectile() {
//		
//	}	
	
    public GenericProjectile(World par1World)
    {
        super(par1World);
        this.isImmuneToFire=true;
    }
    /*public GenericProjectile(World par1World, EntityLivingBase par2EntityLivingBase)
    {
        super(par1World, par2EntityLivingBase);
    }
    public GenericProjectile(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }*/
    
   /*public GenericProjectile(World par2World, EntityPlayer par3Player,
			float damage, float speed, int TTL) {
		this (par2World, par3Player);
        this.motionX*=speed;
        this.motionY*=speed;
        this.motionZ*=speed;
        this.damage = damage;
        this.speed = speed;
        this.ticksToLive=TTL;
	}*/
    
    private void initStartPos(){
    	//System.out.println("Init start position");
    	this.startX=this.posX;
    	this.startY=this.posY;
    	this.startZ=this.posZ;
    	this.posInitialized=true;
    	//System.out.println("Pos:"+this.startX+","+this.startY+","+this.startZ);
    }
    
    
    public GenericProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration,boolean blockdamage) {
    	this(par2World,p, damage,  speed,  TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration,blockdamage, false);
    }   
    
    public GenericProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration,boolean blockdamage, boolean leftGun) {
    	this(par2World, p.posX, p.posY+p.getEyeHeight(), p.posZ, p.rotationYawHead, p.rotationPitch, damage, speed, TTL,spread, dmgDropStart, dmgDropEnd, dmgMin,penetration,blockdamage, leftGun);
    	this.shooter = p;
    	
    	/*super(par2World);
    	this.isImmuneToFire=true;
    	this.shooter = par3Player;
        this.setSize(0.25F, 0.25F);*/
        
    /*    if(this.shooter!=null){
	        float yaw=shooter.rotationYawHead;
	        float pitch=shooter.rotationPitch;
	        this.setLocationAndAngles(shooter.posX, shooter.posY + (double)shooter.getEyeHeight(), shooter.posZ, yaw, pitch);
        }
        
        this.rotationPitch += spread-(2*Math.random()*spread);
        this.rotationYaw += spread-(2*Math.random()*spread);
        
      //  this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
      //  this.posY -= 0.10000000149011612D;
      //  this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        float f = 0.4F;
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        this.motionY = (double)(-MathHelper.sin((this.rotationPitch ) / 180.0F * (float)Math.PI) * f);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 1.5f, 1.0F);

        Vec3 m =  Vec3.createVectorHelper(this.motionX, this.motionY, this.motionZ);
        Vec3 motion = m.normalize();
        
    	/*this.motionX+=spread-(2*Math.random()*spread);
    	this.motionY+=spread-(2*Math.random()*spread);
    	this.motionZ+=spread-(2*Math.random()*spread);
    	Vec3 motion = Vec3.createVectorHelper(this.motionX, this.motionY, this.motionZ);
    	motion.normalize();*/
        
      //  normalized_motion.xCoord+=spread-(2*Math.random()*spread);
      //  normalized_motion.yCoord+=spread-(2*Math.random()*spread);
      //  normalized_motion.yCoord+=spread-(2*Math.random()*spread);
        
        //Normalize again 
     //   Vec3 motion = normalized_motion.normalize();
        
    /*	this.motionX=motion.xCoord;
    	this.motionY=motion.yCoord;
    	this.motionZ=motion.zCoord;
    	
        this.motionX*=speed;
        this.motionY*=speed;
        this.motionZ*=speed;
        this.damage = damage;
        this.speed = speed;
        this.ticksToLive=TTL;
        this.lifetime=TTL;
        this.penetration = penetration;
        
        this.damageMin=dmgMin;
        this.damageDropStart=dmgDropStart;
        this.damageDropEnd=dmgDropEnd; */
    }
    
    public GenericProjectile(World par2World,double posX, double posY, double posZ, float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage) {
    	this(par2World, posX, posY, posZ, 0.0f, 0.0f, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, false);
    }
    
    public GenericProjectile(World par2World,double posX, double posY, double posZ, float yaw, float pitch, float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, boolean leftGun) {
    	super(par2World);
    	this.isImmuneToFire=true;
    	this.shooter = null;
        this.setSize(0.25F, 0.25F);
        
       /* System.out.println("LeftGun?:"+leftGun);
        
        float offsetX=0;
        float offsetZ=0;
        
        if (leftGun) {
        	Vec3 offset = Vec3.createVectorHelper(Keybinds.X, 0, -0.5f+Keybinds.Z);
        	MathUtil.rotateAroundY(offset, -yaw);
        	offsetX = (float) offset.xCoord;
        	offsetZ = (float) offset.zCoord;
        }*/
        
       // float yaw=shooter.rotationYawHead;
       // float pitch=shooter.rotationPitch;
        //this.setLocationAndAngles(shooter.posX, shooter.posY + (double)shooter.getEyeHeight(), shooter.posZ, yaw, pitch);
        this.setLocationAndAngles(posX , posY, posZ, yaw+(float)(spread-(2*Math.random()*spread))*40.0f,pitch+(float)(spread-(2*Math.random()*spread))*40.0f);
        
       // System.out.println("1: X:"+this.posX+" Y:"+this.posY+" Z:"+this.posZ);
        if (!leftGun){
	        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
	        this.posY -= 0.10000000149011612D;
	        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        } else {
        	this.posX += (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
 	        this.posY -= 0.10000000149011612D;
 	        this.posZ += (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        }
        
       // System.out.println("2: X:"+this.posX+" Y:"+this.posY+" Z:"+this.posZ);
        
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        float f = 0.4F;
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        this.motionY = (double)(-MathHelper.sin((this.rotationPitch ) / 180.0F * (float)Math.PI) * f);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 1.5f, 1.0F);
    	
    	//this.motionX+=spread-(2*Math.random()*spread);
    	//this.motionY+=spread-(2*Math.random()*spread);
    	//this.motionZ+=spread-(2*Math.random()*spread);
    	Vec3 motion = Vec3.createVectorHelper(this.motionX, this.motionY, this.motionZ);
    	motion.normalize();
    	this.motionX=motion.xCoord;
    	this.motionY=motion.yCoord;
    	this.motionZ=motion.zCoord;
    	
        this.motionX*=speed;
        this.motionY*=speed;
        this.motionZ*=speed;
        this.damage = damage;
        this.speed = speed;
        this.ticksToLive=TTL;
        this.lifetime=TTL;
        this.penetration = penetration;
        
        this.damageMin=dmgMin;
        this.damageDropStart=dmgDropStart;
        this.damageDropEnd=dmgDropEnd;
        this.blockdamage = blockdamage;
    }
    
    public GenericProjectile setSilenced(){
    	this.silenced=true;
    	return this;
    }
    
    protected int getTicksAlive(){
    	return this.lifetime-this.ticksToLive;
    }
    
    /**
     * Return the travelled distance
     * @return
     */
    protected double getDistanceTravelled(){
    	Vec3 start = Vec3.createVectorHelper(this.startX, this.startY, this.startZ);
    	Vec3 end = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		return start.distanceTo(end);		
    }
    
    protected boolean isHeadshot(Entity ent, double height){
    	float threshold=0.0f;
    	if (ent instanceof EntityCreeper){
    		threshold = 1.60f;
    	} else if (ent instanceof EntitySkeleton){
    		threshold = 1.60f;
    	} else if (ent instanceof EntityZombie){
    		threshold = 1.60f;
    	} else if (ent instanceof EntityEnderman){
    		threshold = 2.0f;
    	}
    		
    	if (threshold >0.0f){
    		return height > threshold;
    	} else {
    		return false;
    	}
    }
    
	/**
	 * Get Damage for distance (Ticks alive)
	 * @return
	 */
    protected float getDamage() {
    	//System.out.println("Ticks:"+this.getTicksAlive()+"Start/End:"+this.damageDropStart+","+this.damageDropEnd);
    	
    	double distance = this.getDistanceTravelled();

    	double dropStart = this.damageDropStart;
    	double dropEnd = this.damageDropEnd;
    	
    	//System.out.println("Dist:"+distance+"Start/End:"+dropStart+","+dropEnd);
    	
    	if (distance<=dropStart){
    	//	System.out.println("Dmg(max):"+this.damage);
    		return this.damage;
    	} else if (distance>dropEnd) {
    		//System.out.println("Dmg(min):"+this.damageMin);
    		return this.damageMin;
    	} else {
    		float factor = 1.0f- (float)((distance-dropStart) / (dropEnd-dropStart));
    		//System.out.println("Factor:"+factor);
    		//System.out.println("Dmg(drop):"+(this.damageMin + (this.damage-this.damageMin)*factor));
    		return (this.damageMin + (this.damage-this.damageMin)*factor);
    	}
    	/*if (getTicksAlive()<=damageDropStart){
    		System.out.println("Dmg(max):"+this.damage);
    		return this.damage;
    	} else if (getTicksAlive()>damageDropEnd) {
    		System.out.println("Dmg(min):"+this.damageMin);
    		return this.damageMin;
    	} else {

    		float factor = 1.0f-((getTicksAlive()-damageDropStart) / (float)(damageDropEnd-damageDropStart));
    		//System.out.println("Factor:"+factor);
    		System.out.println("Dmg(drop):"+(this.damageMin + (this.damage-this.damageMin)*factor));
    		return this.damageMin + (this.damage-this.damageMin)*factor;
    	}*/
    }
    
    
    /**
     * Override to change damage type etc
     * @return
     */
    protected TGDamageSource getProjectileDamageSource(){
    	TGDamageSource src = TGDamageSource.causeBulletDamage(this, this.shooter, DeathType.GORE);
    	src.armorPenetration = this.penetration;
    	src.setNoKnockback();
    	return src;
    }
    
    //called when a block is hit, spawn fx, destroy world etc
    protected void hitBlock(MovingObjectPosition mop){
    	Block target = this.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
		Material mat = target.getMaterial();
		
		//System.out.println("Blockdamage:"+this.blockdamage);
		/*if (this.blockdamage) {
			if ( mat == Material.plants || mat == Material.vine){
				//System.out.println("PLANT");
				if (!this.worldObj.isRemote){
					target.dropBlockAsItem(this.worldObj, mop.blockX, mop.blockY, mop.blockZ, this.worldObj.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ), 0);			
					this.worldObj.setBlockToAir(mop.blockX, mop.blockY, mop.blockZ);
				}
			//} else {
				//System.out.println("Material:"+mat);
			//}
		}*/
		
		this.spawnImpactParticle(mat);
    }
    
 /**
  * add hit effect like burn etc
  *   * @param ent
  */
    protected void onHitEffect(EntityLivingBase ent){
    }
	/**
    * Called when this EntityThrowable hits a block or entity.
    */
    
    protected void setAIRevengeTarget(EntityLiving ent){
    	boolean shootBack=false;
		if (this.shooter!=null){
			if (shooter instanceof ITGNpcTeam && ent instanceof ITGNpcTeam){
				shootBack = TGNpcFactions.isHostile(((ITGNpcTeam)shooter).getTGFaction(), ((ITGNpcTeam)ent).getTGFaction());
				//System.out.println("Shoot Back?:"+shootBack);
			} else {
				
				if (!(shooter instanceof EntityPlayer)){
					shootBack=true;
					//System.out.println("Shoot Back!:"+shooter.toString());
				}
			}
		}

		if(shootBack){
			//System.out.println("shooting back against:"+shooter+" from: "+ent);
			ent.setRevengeTarget(shooter);
			ent.setLastAttacker(shooter);
		}
    }
    
    protected void onImpact(MovingObjectPosition mop)
    {
    	       
    	
        if (mop.entityHit != null )
        {

        	TGDamageSource src = getProjectileDamageSource();
        	
        	if (mop.entityHit instanceof EntityLivingBase){
        		EntityLivingBase ent = (EntityLivingBase) mop.entityHit;
        		
        		//Check for Headshot
        		
        		/*double heightDiff = this.posY-ent.posY;
        		//System.out.println("HeightDiff:"+heightDiff);
        		if (this.isHeadshot(ent, heightDiff)){
        			this.worldObj.playSoundAtEntity(ent, "techguns:effects.bulletimpact", 1.0F, 1.0F );
        			dmg=dmg*2.0f;
        		}*/
        		
        		float dmg = DamageSystem.getDamageFactor(this.shooter, ent) * this.getDamage();
        		
        		if (dmg > 0.0f){
        			if (src.knockbackMultiplier==0.0f){
	                	TGDamageSource knockback = TGDamageSource.getKnockbackDummyDmgSrc(this, this.shooter);
		        		ent.attackEntityFrom(knockback, 0.01f);
        			}
	        		ent.attackEntityFrom(src, dmg);
	        		
	        		if ( ent instanceof EntityLiving){
	        			this.setAIRevengeTarget(((EntityLiving) ent));
	        		}
	        		
	        		this.onHitEffect(ent);
        		}
        		
        	} else {
        		mop.entityHit.attackEntityFrom(src,this.getDamage());
            	
        	}
        	
            this.setDead();
        }

       // if (!this.worldObj.isRemote)
       // {
        	if (mop.typeOfHit == MovingObjectType.BLOCK) {
        		
        		Block b = this.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
        		
        		//System.out.println("HitBlock of Type:"+b);
        		
        		if (!b.getBlocksMovement(this.worldObj, mop.blockX, mop.blockY, mop.blockZ)) {
        		
	        		//hit a block
	        		this.hitBlock(mop);
	        	//} else {
	        		
	        	//}
	            
	        		this.setDead();
        		}
        	}
        //System.out.println("Impact!");
    }
    
    protected void spawnImpactParticle(Material mat){
    	if(mat==Material.rock) {
			this.worldObj.playSoundAtEntity(this, "dig.stone", 2.0F, 1.0F );
			this.worldObj.spawnParticle("lava", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			
		} else if(mat==Material.wood) {
			this.worldObj.playSoundAtEntity(this, "dig.wood", 2.0F, 1.0F );
			this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			
		} else if(mat==Material.glass) {
			this.worldObj.playSoundAtEntity(this, "dig.glass", 1.0F, 1.0F );
			this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			
		} else if(mat==Material.iron||mat==Material.anvil) {
			this.worldObj.playSoundAtEntity(this, "mob.irongolem.hit", 1.0F, 1.0F );
			this.worldObj.spawnParticle("lava", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			
		} else {
			this.worldObj.playSoundAtEntity(this, "techguns:effects.bulletimpact", 1.0F, 1.0F );
			this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		}
    }

    public void shiftForward(float factor){
    	this.posX += this.motionX*factor;
    	this.posY += this.motionY*factor;
    	this.posZ += this.motionZ*factor;
    }
    
    protected float getGravityVelocity() 
    {
    	return 0.0f;
    }
    
	/* (non-Javadoc)
	 * @see net.minecraft.entity.projectile.EntityThrowable#setThrowableHeading(double, double, double, float, float)
	 */
	@Override
	public void setThrowableHeading(double p_70186_1_, double p_70186_3_,
			double p_70186_5_, float p_70186_7_, float p_70186_8_) {

		//super.setThrowableHeading(p_70186_1_, p_70186_3_, p_70186_5_, p_70186_7_,p_70186_8_);
		
        float f2 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
        p_70186_1_ /= (double)f2;
        p_70186_3_ /= (double)f2;
        p_70186_5_ /= (double)f2;
       // p_70186_1_ += this.rand.nextGaussian() * 0.007499999832361937D * (double)p_70186_8_;
      //  p_70186_3_ += this.rand.nextGaussian() * 0.007499999832361937D * (double)p_70186_8_;
      //  p_70186_5_ += this.rand.nextGaussian() * 0.007499999832361937D * (double)p_70186_8_;
        p_70186_1_ *= (double)p_70186_7_;
        p_70186_3_ *= (double)p_70186_7_;
        p_70186_5_ *= (double)p_70186_7_;
        this.motionX = p_70186_1_;
        this.motionY = p_70186_3_;
        this.motionZ = p_70186_5_;
        float f3 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(p_70186_1_, p_70186_5_) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(p_70186_3_, (double)f3) * 180.0D / Math.PI);

	}
	/* (non-Javadoc)
	 * @see net.minecraft.entity.projectile.EntityThrowable#onUpdate()
	 */
	@Override
	public void onUpdate() {
		if (!this.posInitialized && !this.worldObj.isRemote){
			this.initStartPos();
		}
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();

        Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        Vec3 vec31 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(vec3, vec31,false,true,false);
        vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        vec31 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        if (movingobjectposition != null)
        {
            vec31 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }

        if (!this.worldObj.isRemote)
        {
            Entity entity = null;
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            EntityLivingBase entitylivingbase = this.shooter;

            for (int j = 0; j < list.size(); ++j)
            {
                Entity entity1 = (Entity)list.get(j);

                if (entity1.canBeCollidedWith() && (entity1 != entitylivingbase))
                {
                    float f = 0.3F;
                    AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double)f, (double)f, (double)f);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);

                    if (movingobjectposition1 != null)
                    {
                        double d1 = vec3.distanceTo(movingobjectposition1.hitVec);

                        if (d1 < d0 || d0 == 0.0D)
                        {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }

            if (entity != null)
            {
                movingobjectposition = new MovingObjectPosition(entity);
            }
        }

        if (movingobjectposition != null)
        {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.worldObj.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ) == Blocks.portal)
            {
                this.setInPortal();
            }
            else
            {
                this.onImpact(movingobjectposition);
            }
        }

        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f1) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
        {
            ;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
        {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }

        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
        float f2 = 0.99F;
        float f3 = this.getGravityVelocity();

        if (this.isInWater())
        {
            for (int i = 0; i < 4; ++i)
            {
                float f4 = 0.25F;
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ);
            }

            f2 = 0.8F;
        }

        this.motionX *= (double)f2;
        this.motionY *= (double)f2;
        this.motionZ *= (double)f2;
        this.motionY -= (double)f3;
        this.setPosition(this.posX, this.posY, this.posZ);
		
		--ticksToLive;
		//System.out.println("Ticking projectile:"+ticksToLive);
		
		//Vec3 prevPos = Vec3.createVectorHelper(this.prevPosX, this.prevPosY, this.prevPosZ);
		//Vec3 pos = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		
		//double dist =Math.sqrt(prevPos.squareDistanceTo(pos));
		//System.out.println("Covered distance:"+dist);
		
		if (ticksToLive <=0){
			this.setDead();
		}
	}

	
	@Override
	protected void readEntityFromNBT(NBTTagCompound tags) {

		this.damage=tags.getFloat("damage");
		this.speed=tags.getFloat("speed");
		
		this.ticksToLive=tags.getInteger("ticksToLive");
		this.lifetime=tags.getInteger("lifetime");

		this.damageDropStart=tags.getInteger("damageDropStart");
		this.damageDropEnd = tags.getInteger("damageDropEnd");
		this.damageMin=tags.getFloat("damageMin");
		
	}
	@Override
	protected void writeEntityToNBT(NBTTagCompound tags) {

		tags.setFloat("damage", this.damage);
		tags.setFloat("speed", this.speed);
		
		tags.setInteger("ticksToLive", this.ticksToLive);
		tags.setInteger("lifetime", this.lifetime);
		
		tags.setInteger("damageDropStart", this.damageDropStart);
		tags.setInteger("damageDropEnd", this.damageDropEnd);
		tags.setFloat("damageMin", this.damageMin);
	}

	@Override
	protected void entityInit() {
	}
	
}
