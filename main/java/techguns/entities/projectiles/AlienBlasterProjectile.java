package techguns.entities.projectiles;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.TGPackets;
import techguns.damagesystem.TGDamageSource;
import techguns.packets.PacketSpawnParticle;
import techguns.util.EntityDeathUtils.DeathType;
import techguns.util.ProjectileExplosion;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class AlienBlasterProjectile extends GenericProjectile {

	boolean ignite=false;
	float chanceToIgnite = 1.0f;
	int entityIgniteTime = 3;
	
	public AlienBlasterProjectile(World par1World) {
		super(par1World);
	}

	
	
	public AlienBlasterProjectile(World par2World, double posX, double posY,
			double posZ, float damage, float speed, int TTL, float spread,
			int dmgDropStart, int dmgDropEnd, float dmgMin,boolean ignite, float penetration, boolean blockdamage) {
		super(par2World, posX, posY, posZ, damage, speed, TTL, spread, dmgDropStart,
				dmgDropEnd, dmgMin,  penetration, blockdamage);
		this.ignite=ignite;
		//Techguns.proxy.createFXOnEntity("AlienBlasterTrail", this);
	}

	public AlienBlasterProjectile(World par2World, EntityLivingBase par3Player,
			float damage, float speed, int TTL, float spread, int dmgDropStart,
			int dmgDropEnd, float dmgMin, boolean ignite, float penetration, boolean blockdamage) {
		super(par2World, par3Player, damage, speed, TTL, spread, dmgDropStart,
				dmgDropEnd, dmgMin, penetration, blockdamage);
		this.ignite=ignite;
		//Techguns.proxy.createFXOnEntity("AlienBlasterTrail", this);
	}

	
	
	
	 @Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causeFireDamage(this, this.shooter, DeathType.LASER);
    	src.armorPenetration = this.penetration;
    	src.goreChance=0.5f;
    	src.setNoKnockback();
    	return src;
	}


	 private void doExplosion(){
		if (!this.worldObj.isRemote){
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("AlienExplosion", this.posX,this.posY,this.posZ), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 50.0f));
		}
		
		
		float radius=0.5f;
		ProjectileExplosion explosion = new ProjectileExplosion(worldObj, this.posX, this.posY, this.posZ, this.shooter, radius, (int)this.getDamage(), radius*0.5f, radius*1.5f);
		explosion.doExplosion(false, this.shooter);
				
	 }

	@Override
	protected void hitBlock(MovingObjectPosition mop) {
		 if (this.blockdamage){
			 burnblock(mop,mop.blockX,mop.blockY,mop.blockZ);
		 }
		 this.doExplosion();
			
		 //this.setDead();
	}


	 


/*	    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	    {
	    	 //this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 1.0f, false);           
	    	
	        if (par1MovingObjectPosition.entityHit != null)
	        {
	            par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.shooter), damage);
	            par1MovingObjectPosition.entityHit.setFire(this.entityIgniteTime);

	        }

	        
	    	if (par1MovingObjectPosition.typeOfHit == MovingObjectType.BLOCK) {
	    		//hit a block
	    		
	    		int x = par1MovingObjectPosition.blockX;
	    		int y = par1MovingObjectPosition.blockY;
	    		int z = par1MovingObjectPosition.blockZ;
	    		
	    		if(ignite){
	    			burnblock(par1MovingObjectPosition,x,y,z);
	    		}
    		*/
	    		/*burnblock(par1MovingObjectPosition,x+1,y,z);
	    		burnblock(par1MovingObjectPosition,x-1,y,z);
	    		burnblock(par1MovingObjectPosition,x,y+1,z);
	    		burnblock(par1MovingObjectPosition,x,y-1,z);
	    		burnblock(par1MovingObjectPosition,x,y,z+1);
	    		burnblock(par1MovingObjectPosition,x,y,z-1);*/
 			/*
	 
	            
	            //Techguns.proxy.spawnParticle("FireballImpact", this.worldObj, this.posX, this.posY, this.posZ, 0, 0.05f, 0);
	            //this.worldObj.spawnParticle("lava", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
	        	
	    	}
    		
	   		if (!this.worldObj.isRemote){
    			TGPackets.network.sendToAllAround(new PacketSpawnParticle("AlienExplosion", this.posX,this.posY,this.posZ), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 50.0f));
    		}
    		
	    	
	    	float radius=0.5f;
    		ProjectileExplosion explosion = new ProjectileExplosion(worldObj, this.posX, this.posY, this.posZ, this, radius, (int)damage, radius*0.5f, radius*1.5f);
    		explosion.doExplosion(false);
    		
            this.setDead();
	    }*/
	
	 @Override
	protected void onHitEffect(EntityLivingBase ent) {
		ent.setFire(this.entityIgniteTime);
		this.doExplosion();
	}



	private void burnblock(MovingObjectPosition par1MovingObjectPosition,int x,int y, int z){
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
	 
	 /* (non-Javadoc)
		 * @see techguns.entities.projectiles.GenericProjectile#onUpdate()
		 */
		@Override
		public void onUpdate() {
			super.onUpdate();
			
			//System.out.println("ALIEN BLASTER ON UPDATE");
			
			if (this.isInWater())
	        {
	            for (int l = 0; l < 4; ++l)
	            {
	                float f4 = 0.25F;
	                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ);
	            }
	            //this.setDead();
	        } else {
	        	float f5 = 0.1f;
	        	float f2 = 0.2f;
	        	//this.worldObj.spawnParticle("slime", this.posX, this.posY, this.posZ, this.motionX*f5, (this.motionY*f5)-this.getGravityVelocity(), this.motionZ*f5);
	        	
	        	/*Techguns.proxy.spawnParticle("AlienBlasterFlare", this.worldObj, this.posX, this.posY, this.posZ, this.motionX*f5, this.motionY*f5, this.motionZ*f5);
	        	for (int i=0; i<3;i++){
	        		Techguns.proxy.spawnParticle("AlienBlasterFlare", this.worldObj, this.posX+this.motionX*f2, this.posY+this.motionY*f2, this.posZ+this.motionZ*f2, this.motionX*f5, this.motionY*f5, this.motionZ*f5);
	        		Techguns.proxy.spawnParticle("AlienBlasterFlare", this.worldObj, this.posX-this.motionX*f2, this.posY-this.motionY*f2, this.posZ-this.motionZ*f2, this.motionX*f5, this.motionY*f5, this.motionZ*f5);
	        		f2+=0.2;
	        	}*/
	        }

		}
		
		
}
