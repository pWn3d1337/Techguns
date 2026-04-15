package techguns.entities.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import techguns.TGItems;
import techguns.Techguns;
import techguns.damagesystem.INpcTGDamageSystem;
import techguns.damagesystem.TGDamageSource;
import techguns.entities.projectiles.GenericProjectile;
import techguns.entities.projectiles.RocketProjectile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AttackHelicopter extends EntityFlying implements IMob, IBossDisplayData, INpcTGDamageSystem{
	 public int courseChangeCooldown;
	    public double waypointX;
	    public double waypointY;
	    public double waypointZ;
	    private Entity targetedEntity;
	    /** Cooldown time between target loss and new target aquirement. */
	    private int aggroCooldown;
	    public int prevAttackCounter;
	    public int attackCounter;
	    /** The explosion radius of spawned fireballs. */
	    private int explosionStrength = 1;
	    private static final String __OBFID = "CL_00001689";
	    
	    private float preferredHeight = 25f;
	    private int ambientSoundTick = 0;
	    
	    public int maxDeathTime = 100;

	    public AttackHelicopter(World p_i1735_1_)
	    {
	        super(p_i1735_1_);
	        this.setSize(10.0f, 4.0f);
	        this.experienceValue = 50;
	    }
	    
	    

	    @SideOnly(Side.CLIENT)
	    public boolean func_110182_bF()
	    {
	        return this.dataWatcher.getWatchableObjectByte(16) != 0;
	    }
	    
	    @Override
	    public void onEntityUpdate()
	    {
	        super.onEntityUpdate();
	        
	        if (--ambientSoundTick <= 0 && this.deathTime == 0) {
	        	this.playSound("techguns:npcs.apacheRotor", 6.0f, 1.0f);
	        	ambientSoundTick = 61;
	        }
	    }
	    
	    /**
	     * handles entity death timer, experience orb and particle creation
	     */
	    @Override
	    protected void onDeathUpdate()
	    {	    	
	        if (this.deathTime == 0) {
	        	this.playSound("techguns:npcs.apacheDie", 6.0f, 1.0f);
	        }    	
	    	++this.deathTime;
	        
	    	float yoffset = (float) (Math.pow(((float)deathTime/(float)maxDeathTime),2)*10.0f);
	    	//this.setPosition(this.posX, this.posY-yoffset, this.posZ);
	    	
	        if (deathTime % 5 == 0) {
	           double d2 = this.rand.nextGaussian() * 0.02D;
	           double d0 = this.rand.nextGaussian() * 0.02D;
	           double d1 = this.rand.nextGaussian() * 0.02D;
	           Techguns.proxy.spawnParticle("LargeSmoke", this.worldObj, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1);
	        }
	        if (deathTime % 6 == 0) {
	        	float v = 1.0f;
	        	float angle = this.rand.nextFloat()*((float)Math.PI)*2f;
        		float x = (float) Math.cos(angle);
        		float z = (float) Math.sin(angle);
        		float r = 0.5f+(this.rand.nextFloat()*0.5f);
        		Techguns.proxy.spawnParticle("Fireblast", this.worldObj, this.posX+x, this.posY-yoffset, this.posZ+z, x*v*r, r, z*v*r);
        		Techguns.proxy.spawnParticle("Fireblast", this.worldObj, this.posX+x, this.posY-yoffset, this.posZ+z, x*v*r*0.75f, r, z*v*r*0.75f);
        		Techguns.proxy.spawnParticle("Fireblast", this.worldObj, this.posX+x, this.posY-yoffset, this.posZ+z, x*v*r*1.25f, r, z*v*r*1.25f);
	        }
	        

	        if (this.deathTime == maxDeathTime)
	        {
	            int i;

	            if (!this.worldObj.isRemote && (this.recentlyHit > 0 || this.isPlayer()) && this.func_146066_aG() && this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot"))
	            {
	                i = this.getExperiencePoints(this.attackingPlayer);

	                while (i > 0)
	                {
	                    int j = EntityXPOrb.getXPSplit(i);
	                    i -= j;
	                    this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, j));
	                }
	            }

	            this.setDead();

	            this.playSound("techguns:npcs.apacheExplode", 6.0f, 1.0f);
	            
	            for (i = 0; i < 10; ++i)
	            {
	                double d2 = this.rand.nextGaussian() * 0.02D;
	                double d0 = this.rand.nextGaussian() * 0.02D;
	                double d1 = this.rand.nextGaussian() * 0.02D;
	                Techguns.proxy.spawnParticle("ExplosionHuge", this.worldObj, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height*2.0f) -10.f, this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1);
	            	Techguns.proxy.spawnParticle("LargeSmoke", this.worldObj, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height*2.0f) -10.f, this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1);
	            }
	        }
	    }

	    /**
	     * Called when the entity is attacked.
	     */
	    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
	    {
	        if (this.isEntityInvulnerable())
	        {
	            return false;
	        }
	        else if ("fireball".equals(p_70097_1_.getDamageType()) && p_70097_1_.getEntity() instanceof EntityPlayer)
	        {
	            super.attackEntityFrom(p_70097_1_, 1000.0F);
	            //((EntityPlayer)p_70097_1_.getEntity()).triggerAchievement(AchievementList.ghast);
	            return true;
	        }
	        else
	        {
	            return super.attackEntityFrom(p_70097_1_, p_70097_2_);
	        }
	    }

	    
	    
	    protected void entityInit()
	    {
	        super.entityInit();
	        this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
	    }

	    protected void applyEntityAttributes()
	    {
	        super.applyEntityAttributes();
	        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
	    }

	    protected void updateEntityActionState()
	    {
	        if (!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL)
	        {
	            this.setDead();
	        }

	        this.despawnEntity();
	        this.prevAttackCounter = this.attackCounter;
	        double d0 = this.waypointX - this.posX;
	        double d1 = this.waypointY - this.posY;
	        double d2 = this.waypointZ - this.posZ;
	        double d3 = d0 * d0 + d1 * d1 + d2 * d2;

	        if (d3 < 1.0D || d3 > 3600.0D)
	        {
	        	//Go back to preferred Height
	        	if (this.rand.nextFloat() > 0.25f) {
	        		this.waypointY = getGroundLevel()+preferredHeight;
	        	}else {
		            this.waypointY = this.posY + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
	        	}
	        	
	            this.waypointX = this.posX + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
	            this.waypointZ = this.posZ + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
	        }

	        if (this.courseChangeCooldown-- <= 0)
	        {
	            this.courseChangeCooldown += this.rand.nextInt(5) + 2;
	            d3 = (double)MathHelper.sqrt_double(d3);

	            if (this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3))
	            {
	                this.motionX += d0 / d3 * 0.1D;
	                this.motionY += d1 / d3 * 0.1D;
	                this.motionZ += d2 / d3 * 0.1D;
	            }
	            else
	            {
	                this.waypointX = this.posX;
	                this.waypointY = this.posY;
	                this.waypointZ = this.posZ;
	            }
	        }

	        if (this.targetedEntity != null && this.targetedEntity.isDead)
	        {
	            this.targetedEntity = null;
	        }

	        if (this.targetedEntity == null || this.aggroCooldown-- <= 0)
	        {
	            this.targetedEntity = this.worldObj.getClosestVulnerablePlayerToEntity(this, 100.0D);

	            if (this.targetedEntity != null)
	            {
	                this.aggroCooldown = 20;
	            }
	        }

	        double d4 = 64.0D;

	        if (this.targetedEntity != null && this.targetedEntity.getDistanceSqToEntity(this) < d4 * d4)
	        {
	            double d5 = this.targetedEntity.posX - this.posX;
	            double d6 = this.targetedEntity.boundingBox.minY + (double)(this.targetedEntity.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
	            double d7 = this.targetedEntity.posZ - this.posZ;
	            this.renderYawOffset = this.rotationYaw = -((float)Math.atan2(d5, d7)) * 180.0F / (float)Math.PI;

	            if (this.canEntityBeSeen(this.targetedEntity))
	            {
	                /*if (this.attackCounter == 10)
	                {
	                    this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1007, (int)this.posX, (int)this.posY, (int)this.posZ, 0);	                
	                }*/

	                ++this.attackCounter;

	                if (this.attackCounter > 15)
	                {
	                	if (this.attackCounter == 16) this.worldObj.playSoundAtEntity(this, "techguns:npcs.apacheBurst", 5.0f, 1.0f);
	                	if (this.attackCounter % 2 != 0) {
		                	double d8 = 8.0D;
		                    Vec3 vec3 = this.getLook(1.0F);   
		                    Vec3 v1 = Vec3.createVectorHelper(this.targetedEntity.posX-this.posX, 0, this.targetedEntity.posZ-this.posZ).normalize();
		                    Vec3 v2 = Vec3.createVectorHelper(this.targetedEntity.posX-this.posX, this.targetedEntity.posY-(this.posY+2.25f), this.targetedEntity.posZ-this.posZ).normalize();
		                    double pitch = Math.acos(v1.dotProduct(v2))* (180.0/Math.PI);       
		                    GenericProjectile bullet = new GenericProjectile(this.worldObj, this.posX, this.posY, this.posZ, this.rotationYaw, (float)pitch, 12.0f, 1.0f, 100, 0.05f, 30, 40, 8.0f, 0.25f,false,false);         
		                    bullet.posX = this.posX + vec3.xCoord * d8;
		                    bullet.posY = this.posY + (double)(this.height / 2.0F) - 1.5D;
		                    bullet.posZ = this.posZ + vec3.zCoord * d8;
		                    this.worldObj.spawnEntityInWorld(bullet);
		                    //Techguns.proxy.spawnParticle("Fireblast", this.worldObj, bullet.posX+(rand.nextFloat()-0.5f)*0.2f, bullet.posY+(rand.nextFloat()-0.5f)*0.2f, bullet.posZ+(rand.nextFloat()-0.5f)*0.2f, 0,0,0);
		                    float fireX = (float) (this.posX + 4.0f * (float) Math.cos((Math.PI*-this.renderYawOffset)/180f));
		                    float fireY = (float) (this.posY -0.25f);
		                    float fireZ = (float) (this.posZ - 4.0f * (float) Math.sin((Math.PI*-this.renderYawOffset)/180f));
		                    Techguns.proxy.spawnParticle("Fireblast", this.worldObj, fireX+(rand.nextFloat()-0.5f)*0.2f, fireY+(rand.nextFloat()-0.5f)*0.2f, fireZ+(rand.nextFloat()-0.5f)*0.2f, 0,0,0);
	                	
		                    //createFireFX();
	                	
	                	}
	                }
	                
	                if (this.attackCounter == 30)
	                {
	                    this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1008, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
	                                             
	                    double d8 = 8.0D;
	                    Vec3 vec3 = this.getLook(1.0F);   
	                    Vec3 v1 = Vec3.createVectorHelper(this.targetedEntity.posX-this.posX, 0, this.targetedEntity.posZ-this.posZ).normalize();
	                    Vec3 v2 = Vec3.createVectorHelper(this.targetedEntity.posX-this.posX, this.targetedEntity.posY-this.posY, this.targetedEntity.posZ-this.posZ).normalize();
	                    double pitch = Math.acos(v1.dotProduct(v2))* (180.0/Math.PI);        
	                    //Correction
	                    pitch*=1.18;
	                    //--
	                    RocketProjectile rocket = new RocketProjectile(this.worldObj, this.posX, this.posY, this.posZ, this.rotationYaw, (float)pitch, 20, 2.0f, 1.0f, 100, 0.05f, 0.00f, false, 0.0f);         
	                    rocket.posX = this.posX + vec3.xCoord * d8;
	                    rocket.posY = this.posY + (double)(this.height / 2.0F) - 1.0D;
	                    rocket.posZ = this.posZ + vec3.zCoord * d8;
	                    this.worldObj.spawnEntityInWorld(rocket);
	                    this.worldObj.playSoundAtEntity(this, "techguns:guns.rocketFire", 5.0f, 1.0f);
	                    this.attackCounter = -40;
	                }
	            }
	            else if (this.attackCounter > 0)
	            {
	                --this.attackCounter;
	            }
	        }
	        else
	        {
	            this.renderYawOffset = this.rotationYaw = -((float)Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float)Math.PI;

	            if (this.attackCounter > 0)
	            {
	                --this.attackCounter;
	            }
	        }

	        if (!this.worldObj.isRemote)
	        {
	            byte b1 = this.dataWatcher.getWatchableObjectByte(16);
	            byte b0 = (byte)(this.attackCounter > 10 ? 1 : 0);

	            if (b1 != b0)
	            {
	                this.dataWatcher.updateObject(16, Byte.valueOf(b0));
	            }
	        }
	    }

//	    private void createFireFX() {
//	    	double x = this.posX;
//	    	double y = this.posY;
//	    	double z = this.posZ;
//	    	double offsetZ=(gun.muzzleFlashOffsetZ+shooterOffsetZ)*Techguns.cos360(rot) - (gun.muzzleFlashOffsetX+shooterOffsetX)*Techguns.sin360(rot);
//			double offsetX=(gun.muzzleFlashOffsetZ+shooterOffsetZ)*Techguns.sin360(rot) + (gun.muzzleFlashOffsetX+shooterOffsetX)*Techguns.cos360(rot);
//			double offsetY=shooterOffsetY+gun.muzzleFlashOffsetY;
//			
//			x+=offsetX;
//			y+=offsetY;
//			z+=offsetZ;
//
//			Techguns.proxy.spawnParticle(particle, Minecraft.getMinecraft().theWorld, x, y, z, shooter.motionX, 0,shooter.motionZ);
//		}



		private float getGroundLevel() {	
			return this.worldObj.getHeightValue((int)this.posX, (int)this.posZ);
		}

		/**
	     * True if the ghast has an unobstructed line of travel to the waypoint.
	     */
	    private boolean isCourseTraversable(double p_70790_1_, double p_70790_3_, double p_70790_5_, double p_70790_7_)
	    {
	        double d4 = (this.waypointX - this.posX) / p_70790_7_;
	        double d5 = (this.waypointY - this.posY) / p_70790_7_;
	        double d6 = (this.waypointZ - this.posZ) / p_70790_7_;
	        AxisAlignedBB axisalignedbb = this.boundingBox.copy();

	        for (int i = 1; (double)i < p_70790_7_; ++i)
	        {
	            axisalignedbb.offset(d4, d5, d6);

	            if (!this.worldObj.getCollidingBoundingBoxes(this, axisalignedbb).isEmpty())
	            {
	                return false;
	            }
	        }

	        return true;
	    }

	    /**
	     * Returns the sound this mob makes while it's alive.
	     */
	    protected String getLivingSound()
	    {
	        return ""; //"mob.ghast.moan";
	    }

	    /**
	     * Returns the sound this mob makes when it is hurt.
	     */
	    protected String getHurtSound()
	    {
	        return "techguns:npcs.apacheHit";
	    }

	    /**
	     * Returns the sound this mob makes on death.
	     */
	    protected String getDeathSound()
	    {
	        return "npcs.apacheHit";
	    }

	    protected Item getDropItem()
	    {
	        return Items.gunpowder;
	    }

	    /**
	     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
	     * par2 - Level of Looting used to kill this mob.
	     */
	    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	    {
	        int j = this.rand.nextInt(2) + this.rand.nextInt(1 + p_70628_2_);
	        int k;

	       /* for (k = 0; k < j; ++k)
	        {
	            this.dropItem(Items.ghast_tear, 1);
	        }

	        j = this.rand.nextInt(3) + this.rand.nextInt(1 + p_70628_2_);

	        for (k = 0; k < j; ++k)
	        {
	            this.dropItem(Items.gunpowder, 1);
	        }*/
	        
	        this.entityDropItem(TGItems.newStack(TGItems.rifleBullets, 16+this.rand.nextInt(16)), 0.0f);
	        
	        this.entityDropItem(TGItems.newStack(TGItems.rocketAmmo, 8+this.rand.nextInt(8)), 0.0f);
	        
	        this.entityDropItem(TGItems.newStack(TGItems.cyberneticParts, 2+this.rand.nextInt(2)), 0.0f);
	        
	        this.entityDropItem(new ItemStack(Blocks.iron_block, 4+this.rand.nextInt(4)), 0.0f);
	        
	    }

	    /**
	     * Returns the volume for the sounds this mob makes.
	     */
	    protected float getSoundVolume()
	    {
	        return 10.0F;
	    }

	    /**
	     * Checks if the entity's current position is a valid location to spawn this entity.
	     */
	    public boolean getCanSpawnHere()
	    {
	        return this.rand.nextInt(20) == 0 && super.getCanSpawnHere() && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL;
	    }

	    /**
	     * Will return how many at most can spawn in a chunk at once.
	     */
	    public int getMaxSpawnedInChunk()
	    {
	        return 1;
	    }

	    /**
	     * (abstract) Protected helper method to write subclass entity data to NBT.
	     */
	    public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	    {
	        super.writeEntityToNBT(p_70014_1_);
	        p_70014_1_.setInteger("ExplosionPower", this.explosionStrength);
	    }

	    /**
	     * (abstract) Protected helper method to read subclass entity data from NBT.
	     */
	    public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	    {
	        super.readEntityFromNBT(p_70037_1_);

	        if (p_70037_1_.hasKey("ExplosionPower", 99))
	        {
	            this.explosionStrength = p_70037_1_.getInteger("ExplosionPower");
	        }
	    }



		@Override
		public float getTotalArmorAgainstType(TGDamageSource dmgsrc) {
			switch(dmgsrc.damageType){
				case EXPLOSION:
					return 5.0f;
				case LIGHTNING:
				case ENERGY:
					return 10.0f;
				case FIRE:
				case ICE:
				case PHYSICAL:
				case PROJECTILE:
				case POISON:
				case RADIATION:
					return 20.0f;
				case UNRESISTABLE:
			default:
				return 0.0f;
			}
		}



		@Override
		public float getPenetrationResistance(TGDamageSource dmgsrc) {
			switch(dmgsrc.damageType){
			case PROJECTILE:
				return 0.4f;
			case EXPLOSION:
			case LIGHTNING:
			case ENERGY:
			case FIRE:
			case ICE:
			case PHYSICAL:
			case POISON:
			case RADIATION:
			case UNRESISTABLE:
				return 0.0f;
		default:
			return 0.0f;
		}
		}
	    
	    
}