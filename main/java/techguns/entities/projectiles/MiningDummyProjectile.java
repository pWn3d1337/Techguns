package techguns.entities.projectiles;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import techguns.damagesystem.DamageSystem;
import techguns.damagesystem.TGDamageSource;
import techguns.util.EntityDeathUtils.DeathType;

public class MiningDummyProjectile extends GenericProjectile {

	
	
	public MiningDummyProjectile(World par2World, EntityLivingBase par3Player,
			float damage, float speed, int TTL, float spread, int dmgDropStart,
			int dmgDropEnd, float dmgMin, float penetration) {
		super(par2World, par3Player, damage, speed, TTL, spread, dmgDropStart,
				dmgDropEnd, dmgMin, penetration,true);
	}

	public MiningDummyProjectile(World par1World) {
		super(par1World);
		// TODO Auto-generated constructor stub
	}

	/*
    * Called when this EntityThrowable hits a block or entity.
    */
	@Override
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
    	 //this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 1.0f, false);           
    	
        if (par1MovingObjectPosition.entityHit != null)
        {
        
        	TGDamageSource src = TGDamageSource.causeBulletDamage(this, this.shooter, DeathType.GORE);
        	src.armorPenetration=this.penetration;
        	float dmg = (float)DamageSystem.getDamage(src, this.getDamage(), par1MovingObjectPosition.entityHit);
 
        	
        	if (par1MovingObjectPosition.entityHit instanceof EntityLiving){
        		EntityLiving ent = (EntityLiving) par1MovingObjectPosition.entityHit;
        		
        		if(ent.getHealth()>0.0f){
	        		ent.setHealth(ent.getHealth()-dmg);
	        		if (!this.silenced){
	        			ent.attackEntityFrom(src,0.1f);
	        		}
	        		if (ent.getHealth() <= 0.0F)
	                {
	        			if (this.rand.nextFloat()<src.goreChance){
	        				src.deathType = DeathType.DEFAULT;
	        			}
	                    ent.onDeath(src);
	                }
        		}

        		
        	} else {
        		par1MovingObjectPosition.entityHit.attackEntityFrom(src,this.getDamage());
            	
        	}
        	
        	
            this.setDead();
        }

       // if (!this.worldObj.isRemote)
       // {
        	if (par1MovingObjectPosition.typeOfHit == MovingObjectType.BLOCK) {
        		//hit a block
        		Block target = this.worldObj.getBlock(par1MovingObjectPosition.blockX, par1MovingObjectPosition.blockY, par1MovingObjectPosition.blockZ);
        		Material mat = target.getMaterial();
        		float hardness = target.getBlockHardness(worldObj, par1MovingObjectPosition.blockX, par1MovingObjectPosition.blockY, par1MovingObjectPosition.blockZ); 
        		
        		if ( hardness < 0.0f){
        			this.worldObj.playSoundAtEntity(this, "dig.stone", 2.0F, 1.0F );
        			this.worldObj.spawnParticle("lava", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        		} else {
        		
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
	        		
	        		//System.out.println("HIT A BLOCK");
	        		if (!worldObj.isRemote){
	        			//target.breakBlock(worldObj, par1MovingObjectPosition.blockX, par1MovingObjectPosition.blockY, par1MovingObjectPosition.blockZ, target, worldObj.getBlockMetadata(par1MovingObjectPosition.blockX, par1MovingObjectPosition.blockY, par1MovingObjectPosition.blockZ));
	        			target.dropBlockAsItem(worldObj, par1MovingObjectPosition.blockX, par1MovingObjectPosition.blockY, par1MovingObjectPosition.blockZ, worldObj.getBlockMetadata(par1MovingObjectPosition.blockX, par1MovingObjectPosition.blockY, par1MovingObjectPosition.blockZ),0);
	        			worldObj.setBlock(par1MovingObjectPosition.blockX, par1MovingObjectPosition.blockY, par1MovingObjectPosition.blockZ, Blocks.air);
	        		}
        	//} else {
        		
        	//}
        		}
            this.setDead();
        //}else {
        	//this.worldObj.spawnParticle("explode", this.posX, this.posY, this.posZ, 0.0D, 0.1D, 0.0D);
        	
        //}
        	}
        //System.out.println("Impact!");
    }
	
}
