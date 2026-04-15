package techguns.entities.projectiles;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.damagesystem.DamageSystem;
import techguns.damagesystem.DamageType;
import techguns.damagesystem.TGDamageSource;
import techguns.packets.PacketSpawnParticle;
import techguns.util.EntityDeathUtils.DeathType;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class SonicShotgunProjectile extends GenericProjectile implements IEntityAdditionalSpawnData{
	
	public ArrayList<Entity> entitiesHit;

	public boolean mainProjectile = false;
	
	public SonicShotgunProjectile(World par2World, double posX, double posY,
			double posZ, float yaw, float pitch, float damage, float speed,
			int TTL, float spread, int dmgDropStart, int dmgDropEnd,
			float dmgMin, float penetration, boolean blockdamage) {
		super(par2World, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread,
				dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, false);
	}

	public SonicShotgunProjectile(World par2World, double posX, double posY,
			double posZ, float damage, float speed, int TTL, float spread,
			int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration,boolean blockdamage) {
		super(par2World, posX, posY, posZ, damage, speed, TTL, spread, dmgDropStart,
				dmgDropEnd, dmgMin, penetration, blockdamage);
	}

	public SonicShotgunProjectile(World par2World, EntityLivingBase par3Player,
			float damage, float speed, int TTL, float spread, int dmgDropStart,
			int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage) {
		super(par2World, par3Player, damage, speed, TTL, spread, dmgDropStart,
				dmgDropEnd, dmgMin, penetration, blockdamage);
	}

	public SonicShotgunProjectile(World par1World) {
		super(par1World);
	}

	
	
	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = new TGDamageSource("tg_sonic",this, shooter, DamageType.PHYSICAL, DeathType.GORE);
		src.ignoreHurtresistTime=true;
    	src.armorPenetration = this.penetration;
    	src.setKnockback(2.0f);
    	src.goreChance=1.0f;
    	return src;
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		if (mop.entityHit != null )
        {
			if (entitiesHit != null && !entitiesHit.contains(mop.entityHit)) {
	        	TGDamageSource src = getProjectileDamageSource();        	
	        	TGDamageSource knockback = TGDamageSource.getKnockbackDummyDmgSrc(this, this.shooter);
	        	
	        	if (mop.entityHit instanceof EntityLivingBase){
	        		EntityLivingBase ent = (EntityLivingBase) mop.entityHit;
	        		
	        		float dmg = DamageSystem.getDamageFactor(this.shooter, ent);
	        		if (dmg>0){
	        		
		        		ent.attackEntityFrom(knockback, 0.01f);
		        		ent.attackEntityFrom(src, this.getDamage()*dmg);
		        		this.onHitEffect(ent);
		        		
		        		if ( ent instanceof EntityLiving) {
		        			this.setAIRevengeTarget((EntityLiving) ent);
		        		}
	        		}
	        	} else {
	        		mop.entityHit.attackEntityFrom(src,this.getDamage());            	
	        	}
	        	
	        	entitiesHit.add(mop.entityHit);
            }
        } else if (mop.typeOfHit == MovingObjectType.BLOCK) {
        	Block b = this.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
    		
    		if (!b.getBlocksMovement(this.worldObj, mop.blockX, mop.blockY, mop.blockZ)) {
        		this.hitBlock(mop);
        		this.setDead();
    		}
    	}
	}
	
	
	@Override
	public boolean shouldRenderInPass(int pass) {
		if ( pass==1) {
			return mainProjectile;
		}else {
			return !mainProjectile;
		}
	}
	

	@Override
	protected void spawnImpactParticle(Material mat) {
		if (mainProjectile) {
			if (!this.worldObj.isRemote){
    			TGPackets.network.sendToAllAround(new PacketSpawnParticle("SonicShotgunImpact", this.posX,this.posY,this.posZ), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 50.0f));
    		}
		}
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeBoolean(mainProjectile);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.mainProjectile = additionalData.readBoolean();
	}

}
