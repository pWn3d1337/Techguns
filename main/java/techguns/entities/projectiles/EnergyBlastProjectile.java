package techguns.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.damagesystem.TGDamageSource;
import techguns.packets.PacketSpawnParticle;
import techguns.util.EntityDeathUtils.DeathType;
import techguns.util.ProjectileExplosion;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class EnergyBlastProjectile extends GenericProjectile {

	public EnergyBlastProjectile(World par1World) {
		super(par1World);
	}

	public EnergyBlastProjectile(World par2World, double posX, double posY,
			double posZ, float damage, float speed, int TTL, float spread,
			int dmgDropStart, int dmgDropEnd, float dmgMin,float penetration, boolean blockdamage) {
		super(par2World, posX, posY, posZ, damage, speed, TTL, spread, dmgDropStart,
				dmgDropEnd, dmgMin,  penetration, blockdamage);
	}

	public EnergyBlastProjectile(World par2World, EntityLivingBase par3Player,
			float damage, float speed, int TTL, float spread, int dmgDropStart,
			int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, boolean leftgun) {
		super(par2World, par3Player, damage, speed, TTL, spread, dmgDropStart,
				dmgDropEnd, dmgMin, penetration, blockdamage, leftgun);
	}
	
	 @Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causeEnergyDamage(this, this.shooter, DeathType.GORE);
    	src.armorPenetration = this.penetration;
    	src.goreChance=1.0f;
    	//src.setNoKnockback();
    	return src;
	}


	 private void doExplosion(){
		if (!this.worldObj.isRemote){
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("BlueBlasterExplosion", this.posX,this.posY,this.posZ), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 50.0f));
		}
		
		/*float radius=0.5f;
		ProjectileExplosion explosion = new ProjectileExplosion(worldObj, this.posX, this.posY, this.posZ, this, radius, (int)this.getDamage(), radius*0.5f, radius*1.5f);
		explosion.doExplosion(false, this.shooter);*/
				
	 }

	@Override
	protected void hitBlock(MovingObjectPosition mop) {
		 this.doExplosion();
	}

	
	 @Override
	protected void onHitEffect(EntityLivingBase ent) {
		this.doExplosion();
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
