package techguns.entities.projectiles;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import techguns.damagesystem.TGDamageSource;
import techguns.util.MathUtil;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

/**
 * Wave-like projectile that passes through enemies. Can expand. Hits a target only once.
 */
@Deprecated
public class PiercingProjectile extends GenericProjectile implements IEntityAdditionalSpawnData{

	public float minSize; //Start Size
	public float maxSize; //End Size
	public int sizeEnd; //Time in ticks when size change is finished
	public int ticksToLive;
	
	Vec3 startPos;
	
	private ArrayList<Entity> entitiesHit = new ArrayList<Entity>();
	
	public PiercingProjectile(World par1World) {
		super(par1World);
	}

	public PiercingProjectile(World par2World, EntityLivingBase par3Player,
			float damage, float speed, int TTL, float spread, int dmgDropStart,
			int dmgDropEnd, float dmgMin, float penetration, float minSize, float maxSize, int sizeEnd, boolean blockdamage) {
		super(par2World, par3Player, damage, speed, TTL, spread, dmgDropStart,
				dmgDropEnd, dmgMin, penetration,blockdamage);
		this.minSize = minSize;
		this.maxSize = maxSize;
		this.sizeEnd = sizeEnd;
		this.ticksToLive = TTL;
		startPos = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
	}
	
	/**
    * Called when this EntityThrowable hits a block or entity.
    */
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {   	         	
        if (par1MovingObjectPosition.entityHit != null )
        {

        	Entity ent = par1MovingObjectPosition.entityHit;
        	if (!entitiesHit.contains(ent)) {
        		
        		//Check if Entity is in Line of Sight/Fire
        		boolean hit = true;
        		float distance= 100.0f;
        		MovingObjectPosition mop = MathUtil.traceVector(this.worldObj, startPos, startPos.subtract(par1MovingObjectPosition.hitVec), distance, 1, this, shooter);
        		boolean ok = true;;
        		while (mop.entityHit != null && mop.entityHit != ent && ok) {
        			distance = distance - (float) Math.sqrt(mop.hitVec.squareDistanceTo(par1MovingObjectPosition.hitVec));
        			if (distance > 0) {
        				mop = MathUtil.traceVector(this.worldObj, startPos, mop.hitVec.subtract(par1MovingObjectPosition.hitVec), distance, 1, this, shooter);
        			}else {
        				ok = false;
        			}
        		}
        		if (mop.entityHit != null && mop.entityHit != ent) {
        			hit = false;
        		}
        		if (mop.entityHit == null && mop.typeOfHit == MovingObjectType.BLOCK) {
        			hit = false;
        		}
        		//----
        	
        		if (hit) {
		        	TGDamageSource src = getProjectileDamageSource();	        	
		        	TGDamageSource knockback = TGDamageSource.getKnockbackDummyDmgSrc(this, this.shooter);
		        	
		        	if (par1MovingObjectPosition.entityHit instanceof EntityLiving){
		        		EntityLiving ent2 = (EntityLiving) ent;
		        		
		        		ent2.attackEntityFrom(knockback, 0.01f);
		        		ent2.attackEntityFrom(src, this.getDamage());
		        		this.onHitEffect(ent2);
		        		
		        	} else {
		        		ent.attackEntityFrom(src,this.getDamage());   	
		        	}
        		}
        	}
        	
        }

    	if (par1MovingObjectPosition.typeOfHit == MovingObjectType.BLOCK) {
    		//hit a block
    		//this.hitBlock(par1MovingObjectPosition);   
    		//this.setDead();
    	}
    }

	@Override
	public void onUpdate() {
		if (this.ticksExisted < this.sizeEnd) {
			float sizeprog = (float)(ticksExisted)/(float)sizeEnd;
			float size = minSize + (maxSize-minSize)*sizeprog;
			this.width = size;
			this.height = size;
		}else {
			this.width = maxSize;
			this.height = maxSize;
		}
		super.onUpdate();
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeFloat(minSize);
		buffer.writeFloat(maxSize);
		buffer.writeShort(sizeEnd);
		buffer.writeShort(ticksToLive);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.minSize=additionalData.readFloat();
		this.maxSize=additionalData.readFloat();
		this.sizeEnd=additionalData.readShort();
		this.ticksToLive=additionalData.readShort();
	}
    
    
    

}
