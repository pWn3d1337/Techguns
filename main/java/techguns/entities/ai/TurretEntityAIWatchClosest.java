package techguns.entities.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Copy of EntityAIWatchClosest.class with minor changes:
 * Allow different Y height difference for targeting
 *
 * Can't use inheritance because of too many private fields
 */
public class TurretEntityAIWatchClosest extends EntityAIBase {

	  	protected EntityLiving theWatcher;
	    /** The closest entity which is being watched by this one. */
	    protected Entity closestEntity;
	    /** This is the Maximum distance that the AI will look for the Entity */
	    protected float maxDistanceForPlayer;
	    protected int lookTime;
	    protected float field_75331_e;
	    protected Class watchedClass;
	    protected double YOffset;
	    //private static final String __OBFID = "CL_00001592";

	    public TurretEntityAIWatchClosest(EntityLiving p_i1631_1_, Class p_i1631_2_, float p_i1631_3_, double YOffset)
	    {
	        this.theWatcher = p_i1631_1_;
	        this.watchedClass = p_i1631_2_;
	        this.maxDistanceForPlayer = p_i1631_3_;
	        this.field_75331_e = 0.02F;
	        this.setMutexBits(2);
	        this.YOffset=YOffset;
	    }

	    public TurretEntityAIWatchClosest(EntityLiving p_i1632_1_, Class p_i1632_2_, float p_i1632_3_, float p_i1632_4_, double YOffset)
	    {
	        this.theWatcher = p_i1632_1_;
	        this.watchedClass = p_i1632_2_;
	        this.maxDistanceForPlayer = p_i1632_3_;
	        this.field_75331_e = p_i1632_4_;
	        this.setMutexBits(2);
	        this.YOffset=YOffset;
	    }

	    /**
	     * Returns whether the EntityAIBase should begin execution.
	     */
	    public boolean shouldExecute()
	    {
	        if (this.theWatcher.getRNG().nextFloat() >= this.field_75331_e)
	        {
	            return false;
	        }
	        else
	        {
	            if (this.theWatcher.getAttackTarget() != null)
	            {
	                this.closestEntity = this.theWatcher.getAttackTarget();
	            }

	            if (this.watchedClass == EntityPlayer.class)
	            {
	                this.closestEntity = this.theWatcher.worldObj.getClosestPlayerToEntity(this.theWatcher, (double)this.maxDistanceForPlayer);
	            }
	            else
	            {
	                this.closestEntity = this.theWatcher.worldObj.findNearestEntityWithinAABB(this.watchedClass, this.theWatcher.boundingBox.expand((double)this.maxDistanceForPlayer, this.YOffset, (double)this.maxDistanceForPlayer), this.theWatcher);
	            }

	            return this.closestEntity != null;
	        }
	    }

	    /**
	     * Returns whether an in-progress EntityAIBase should continue executing
	     */
	    public boolean continueExecuting()
	    {
	        return !this.closestEntity.isEntityAlive() ? false : (this.theWatcher.getDistanceSqToEntity(this.closestEntity) > (double)(this.maxDistanceForPlayer * this.maxDistanceForPlayer) ? false : this.lookTime > 0);
	    }

	    /**
	     * Execute a one shot task or start executing a continuous task
	     */
	    public void startExecuting()
	    {
	        this.lookTime = 40 + this.theWatcher.getRNG().nextInt(40);
	    }

	    /**
	     * Resets the task
	     */
	    public void resetTask()
	    {
	        this.closestEntity = null;
	    }

	    /**
	     * Updates the task
	     */
	    public void updateTask()
	    {
	        this.theWatcher.getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + (double)this.closestEntity.getEyeHeight(), this.closestEntity.posZ, 10.0F, (float)this.theWatcher.getVerticalFaceSpeed());
	        --this.lookTime;
	    }
}
