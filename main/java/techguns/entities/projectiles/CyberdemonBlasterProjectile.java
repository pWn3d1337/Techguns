package techguns.entities.projectiles;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.Techguns;
import techguns.damagesystem.TGDamageSource;
import techguns.util.EntityDeathUtils.DeathType;

public class CyberdemonBlasterProjectile extends GenericProjectile{

	boolean piercing = false;
	
	boolean ignite;
	float chanceToIgnite = 0.5f;
	int entityIgniteTime = 3;
	
	float gravity;
	
	
	
	
	/*public FlamethrowerProjectile(World par1World, double par2, double par4,
			double par6) {
		super(par1World, par2, par4, par6);
	}

	public FlamethrowerProjectile(World par1World,
			EntityLivingBase par2EntityLivingBase) {
		super(par1World, par2EntityLivingBase);
	}*/

	public CyberdemonBlasterProjectile(World par2World, EntityPlayer par3Player,
			float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage) {
		super(par2World, par3Player, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage);
	}

	public CyberdemonBlasterProjectile(World par1World) {
		super(par1World);
		this.speed = 0.85f;
		this.motionX*=speed;
        this.motionY*=speed;
        this.motionZ*=speed;
	}

	public CyberdemonBlasterProjectile(World par2World, EntityLivingBase player,
			float damage, float speed, int TTL, float gravity, float spread, boolean piercing, boolean ignite, int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration) {
		super(par2World, player, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin,penetration,ignite);
		this.gravity = gravity;
		this.piercing = piercing;
		this.ignite = ignite;
		float f5 = 0.2f;
		Techguns.proxy.spawnParticle("BlasterFlare", this.worldObj, this.posX, this.posY, this.posZ, this.motionX*f5, this.motionY*f5, this.motionZ*f5);
	}
	
	  @Override
    protected float getGravityVelocity() 
    {
    	return gravity;
    }

	  
	  
	  
	 @Override
	protected void hitBlock(MovingObjectPosition mop) {
		 int x = mop.blockX;
 		int y = mop.blockY;
 		int z = mop.blockZ;
 		
        Techguns.proxy.createFX("NetherBlasterImpactFlare", this.worldObj, this.posX, this.posY, this.posZ, 0, 0, 0);
        Techguns.proxy.createFX("MediumExplosionSmoke", this.worldObj, this.posX, this.posY, this.posZ, 0, 0, 0);
 		
 		if (this.blockdamage) {
 			if (Math.random() <= this.chanceToIgnite) {
		    			
	    			switch (mop.sideHit) {
	    				case 0:
	    					if (this.worldObj.isAirBlock(x, y-1, z)) this.worldObj.setBlock(x, y-1, z, Blocks.fire);
	    					break;
	    				case 1:
	    					if (this.worldObj.isAirBlock(x, y+1, z)) {
	    						if (this.worldObj.getBlock(x, y, z) == Blocks.farmland) this.worldObj.setBlock(x, y, z, Blocks.dirt);
	    						this.worldObj.setBlock(x, y+1, z, Blocks.fire);
	    					}
	    					break;
	    				case 2:	
	    					if (this.worldObj.isAirBlock(x, y, z-1)) this.worldObj.setBlock(x, y, z-1, Blocks.fire);
	    					break;
	    				case 3:
			    			if (this.worldObj.isAirBlock(x, y, z+1)) this.worldObj.setBlock(x, y, z+1, Blocks.fire);
			    			break;
	    				case 4:
	    					if (this.worldObj.isAirBlock(x-1, y, z)) this.worldObj.setBlock(x-1, y, z, Blocks.fire);
	    					break;
	    				case 5:
			    			if (this.worldObj.isAirBlock(x+1, y, z)) this.worldObj.setBlock(x+1, y, z, Blocks.fire);
			    			break;
	    			}
		    			
		    		//Burn special blocks
		    		Block b = this.worldObj.getBlock(x, y, z);
		    		if (b ==TGBlocks.camoNetRoof || b == TGBlocks.camoNetPane) {
		    			// || b == Blocks.wheat || b == Blocks.pumpkin_stem || b == Blocks.melon_stem || b == Blocks.carrots || b == Blocks.potatoes) {
		    			this.worldObj.spawnParticle("lava", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		    			this.worldObj.setBlock(x, y, z, Blocks.fire);
		    		//}else if (b == Blocks.farmland) {
		    		//	this.worldObj.setBlock(x, y, z, Blocks.dirt);
		    		//	this.worldObj.setBlock(x, y+1, z, Blocks.fire);
		    		}else if (this.worldObj.getBlock(x, y-1, z) == Blocks.farmland) {
		    			this.worldObj.setBlock(x, y-1, z, Blocks.dirt);
		    			this.worldObj.setBlock(x, y, z, Blocks.fire);
		    		}
	            }
 		}

	}

	@Override
	protected void onHitEffect(EntityLivingBase ent) {
       Techguns.proxy.createFX("NetherBlasterImpactFlare", this.worldObj, this.posX, this.posY, this.posZ, 0, 0, 0);
       Techguns.proxy.createFX("MediumExplosionSmoke", this.worldObj, this.posX, this.posY, this.posZ, 0, 0, 0);
	}

	/*@Override
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
    	 //this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 1.0f, false);           
    	
		// if (this.worldObj.isRemote) {
	       Techguns.proxy.createFX("NetherBlasterImpactFlare", this.worldObj, this.posX, this.posY, this.posZ, 0, 0, 0);
	       Techguns.proxy.createFX("MediumExplosionSmoke", this.worldObj, this.posX, this.posY, this.posZ, 0, 0, 0);
	    //}
		 
        if (par1MovingObjectPosition.entityHit != null)
        {
            par1MovingObjectPosition.entityHit.attackEntityFrom(this.getProjectileDamageSource(), damage);
            par1MovingObjectPosition.entityHit.setFire(this.entityIgniteTime);
            if (!piercing) {
            	this.setDead();
            }
        }

        
    	if (par1MovingObjectPosition.typeOfHit == MovingObjectType.BLOCK) {
    		//hit a block
    		
    		int x = par1MovingObjectPosition.blockX;
    		int y = par1MovingObjectPosition.blockY;
    		int z = par1MovingObjectPosition.blockZ;
    		
    		if (ignite) {
    			if (Math.random() <= this.chanceToIgnite) {
		    			
	    			switch (par1MovingObjectPosition.sideHit) {
	    				case 0:
	    					if (this.worldObj.isAirBlock(x, y-1, z)) this.worldObj.setBlock(x, y-1, z, Blocks.fire);
	    					break;
	    				case 1:
	    					if (this.worldObj.isAirBlock(x, y+1, z)) {
	    						if (this.worldObj.getBlock(x, y, z) == Blocks.farmland) this.worldObj.setBlock(x, y, z, Blocks.dirt);
	    						this.worldObj.setBlock(x, y+1, z, Blocks.fire);
	    					}
	    					break;
	    				case 2:	
	    					if (this.worldObj.isAirBlock(x, y, z-1)) this.worldObj.setBlock(x, y, z-1, Blocks.fire);
	    					break;
	    				case 3:
			    			if (this.worldObj.isAirBlock(x, y, z+1)) this.worldObj.setBlock(x, y, z+1, Blocks.fire);
			    			break;
	    				case 4:
	    					if (this.worldObj.isAirBlock(x-1, y, z)) this.worldObj.setBlock(x-1, y, z, Blocks.fire);
	    					break;
	    				case 5:
			    			if (this.worldObj.isAirBlock(x+1, y, z)) this.worldObj.setBlock(x+1, y, z, Blocks.fire);
			    			break;
	    			}
		    			
		    		//Burn special blocks
		    		Block b = this.worldObj.getBlock(x, y, z);
		    		if (b ==TGBlocks.camoNetRoof || b == TGBlocks.camoNetPane) {
		    			// || b == Blocks.wheat || b == Blocks.pumpkin_stem || b == Blocks.melon_stem || b == Blocks.carrots || b == Blocks.potatoes) {
		    			this.worldObj.spawnParticle("lava", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		    			this.worldObj.setBlock(x, y, z, Blocks.fire);
		    		//}else if (b == Blocks.farmland) {
		    		//	this.worldObj.setBlock(x, y, z, Blocks.dirt);
		    		//	this.worldObj.setBlock(x, y+1, z, Blocks.fire);
		    		}else if (this.worldObj.getBlock(x, y-1, z) == Blocks.farmland) {
		    			this.worldObj.setBlock(x, y-1, z, Blocks.dirt);
		    			this.worldObj.setBlock(x, y, z, Blocks.fire);
		    		}
	            }
	    		
    		}

            this.setDead();
            
            
            //Techguns.proxy.spawnParticle("FireballImpact", this.worldObj, this.posX, this.posY, this.posZ, 0, 0.05f, 0);
            //this.worldObj.spawnParticle("lava", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        	
    	}
    	
    	
   	
    }*/
	
	 
	
	
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
        	//this.worldObj.spawnParticle("flame", this.posX, this.posY, this.posZ, this.motionX*f5, (this.motionY*f5)-this.getGravityVelocity(), this.motionZ*f5);
        	//this.worldObj.spawnParticle("flame", this.posX, this.posY, this.posZ, 0, this.getGravityVelocity(), 0);
        	
        	//Techguns.proxy.spawnParticle("BlasterFlare", this.worldObj, this.posX, this.posY, this.posZ, this.motionX*f5, this.motionY*f5, this.motionZ*f5);
        }
	}

	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causeFireDamage(this, this.shooter, DeathType.DEFAULT);
    	src.armorPenetration = this.penetration;
    	//src.setNoKnockback();
    	src.knockbackMultiplier=1.0f;
    	//src.goreChance = 0.35f;
    	return src;
	}
	
	
}
