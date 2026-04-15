package techguns.entities.projectiles;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import techguns.Techguns;
import techguns.damagesystem.DamageSystem;
import techguns.damagesystem.DamageType;
import techguns.damagesystem.TGDamageSource;
import techguns.util.EntityDeathUtils.DeathType;

public class PowerHammerProjectile extends GenericProjectile{	
	
	float charge;

	public PowerHammerProjectile(World par2World, EntityLivingBase par3Player,
			float damage, float speed, int TTL, float collisionSize, float charge, int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage) {
		super(par2World, par3Player, damage, speed, TTL, 0, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage);
		this.setSize(collisionSize, collisionSize);
		this.charge = charge;
		float f5 = 0.2f;
		//Techguns.proxy.spawnParticle("BlasterFlare", this.worldObj, this.posX, this.posY, this.posZ, this.motionX*f5, this.motionY*f5, this.motionZ*f5);
	}

	public PowerHammerProjectile(World par1World) {
		super(par1World);
	}
	
	  @Override
    protected float getGravityVelocity() 
    {
    	return 0;
    }
	  
	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass==1;
	}

	 @Override
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {

	        if (par1MovingObjectPosition.entityHit != null)
	        {
	        	//float dmg = BulletDamageSource.getDamageWithArmorPen(par1MovingObjectPosition.entityHit, this.getDamage(), this.penetration);
	        	
	        	TGDamageSource src = new TGDamageSource("powerhammer", this, this.shooter, DamageType.PHYSICAL, DeathType.GORE);
	        	src.goreChance=1.0f;
	        	src.armorPenetration=this.penetration;
	    	    //float dmg = (float)DamageSystem.getDamage(src, this.damage, par1MovingObjectPosition.entityHit);
	        	
	        	if (par1MovingObjectPosition.entityHit instanceof EntityLivingBase){
	        		EntityLivingBase ent = (EntityLivingBase) par1MovingObjectPosition.entityHit;
	        		float dmg = DamageSystem.getDamageFactor(this.shooter, ent);
	        		if ( dmg>0){
	        			ent.attackEntityFrom(src, this.getDamage()*dmg);
	        		}
	        	} else {
	        		par1MovingObjectPosition.entityHit.attackEntityFrom(src, this.getDamage());
	        	}
	        	
	        	
	        	
	        	if (par1MovingObjectPosition.entityHit instanceof EntityLiving){
	        		this.setAIRevengeTarget((EntityLiving) par1MovingObjectPosition.entityHit);
	        	}
	        	/*if (par1MovingObjectPosition.entityHit instanceof EntityLiving){
	        		EntityLiving ent = (EntityLiving) par1MovingObjectPosition.entityHit;
	        		
	        		double dx = shooter.posX - ent.posX;
	        		double dz = shooter.posZ - ent.posZ;
	        		double d = 15.0;
	        		ent.knockBack(this.shooter, this.damage*10f, dx*d, dz*d);
	        		
	        		if(ent.getHealth()>0.0f){
		        		ent.setHealth(ent.getHealth()-dmg);
		        		//DamageSource dmgsrc = BulletDamageSource.causeBulletDamage(this, this.shooter, true, DeathType.GORE);	        		
		        		if (ent.getHealth() <= 0.0F)
		                {
		                    ent.onDeath(src);
		                }
	        		}

	        		
	        	} else {
	        		par1MovingObjectPosition.entityHit.attackEntityFrom(src, damage);
	            	
	        	}*/
	        	
	        	
	        	this.worldObj.playSoundAtEntity(this, "techguns:guns.powerhammerImpact", 1.0F, 1.0F );
	    		
    		}else {
    			this.worldObj.playSoundAtEntity(this, "techguns:guns.powerhammerImpactGround", 1.0F, 1.0F );
    		}

            this.setDead();

            
            Techguns.proxy.spawnParticle("impact", this.worldObj, this.posX, this.posY, this.posZ, 0,0,0);
            //this.worldObj.spawnParticle("lava", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        	
 
   	
    }
	
	 
	
	
	/* (non-Javadoc)
	 * @see techguns.entities.projectiles.GenericProjectile#readEntityFromNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	protected void readEntityFromNBT(NBTTagCompound tags) {
		super.readEntityFromNBT(tags);
	}

	/* (non-Javadoc)
	 * @see techguns.entities.projectiles.GenericProjectile#writeEntityToNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	protected void writeEntityToNBT(NBTTagCompound tags) {
		super.writeEntityToNBT(tags);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.isInWater())
        {
            for (int l = 0; l < 4; ++l)
            {
                float f4 = 0.25F;
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ);
            }
            this.setDead();
        } else {
        	float f5 = 0.1f;
    
        	//Techguns.proxy.spawnParticle("BlasterFlare", this.worldObj, this.posX, this.posY, this.posZ, this.motionX*f5, this.motionY*f5, this.motionZ*f5);
        }
	}
}
