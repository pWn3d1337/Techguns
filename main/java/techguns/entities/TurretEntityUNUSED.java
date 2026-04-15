package techguns.entities;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;


public class TurretEntityUNUSED extends EntityLiving {

	protected EntityLiving currentTarget;
	protected int ticksIdle =0;
	
	public TurretEntityUNUSED(World w) {
		super(w);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.entity.EntityLiving#applyEntityAttributes()
	 */
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(0.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
		
		//this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
		
		/*int priority=1;
		this.tasks.addTask(priority++, new EntityAISwimming(this));
		this.tasks.addTask(priority++, new EntityAIArrowAttack(this, 0, 1,1f));*/

	}

	
	@Override
	public boolean isAIEnabled()
	{
		 return true;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.entity.EntityLiving#onEntityUpdate()
	 */
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();

		
		/*If target is dead, set to null*/
		if(this.currentTarget!=null && this.currentTarget.isDead){
			this.currentTarget=null;
		}
		
		Vec3 turret_pos = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		
		/* if no target, try to get a new one*/
		if (this.currentTarget== null) {
			if (this.ticksIdle >20){
				this.ticksIdle=0;
				int vision=20;
				int visionY=5;
				List<EntityLiving> entities = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(this.posX-vision, this.posY-visionY, this.posZ-vision, this.posX+vision, this.posY+visionY, this.posZ+vision));
				
				/*for (EntityLiving ent: entities){
					this.worldObj.spawnEntityInWorld(new GenericProjectile(this.worldObj, this, 10.0f, 1.0f, 50, 0.05f, 25, 40, 5.0f));
				}*/
								
				double nearest = Double.MAX_VALUE;
				
				for (EntityLiving ent: entities){
					Vec3 ent_pos = Vec3.createVectorHelper(ent.posX, ent.posY, ent.posZ);
					MovingObjectPosition hit = this.worldObj.rayTraceBlocks(turret_pos, ent_pos);
					
					//check if we have free sight to target
					if (hit.typeOfHit== MovingObjectPosition.MovingObjectType.ENTITY){
					
						if (hit.entityHit == ent) {
						
							double distance = turret_pos.distanceTo(ent_pos);
							if (distance < nearest) {
								nearest = distance;
								currentTarget=ent;
							}
						}
					}
				}
			} else {
				this.ticksIdle++;
			}
		} 
		
		/* if we have a target, aim at it*/
		if (this.currentTarget!=null){
			Vec3 target_pos = Vec3.createVectorHelper(this.currentTarget.posX, this.currentTarget.posY, this.currentTarget.posZ);
			Vec3 targetAimVec = target_pos.subtract(turret_pos).normalize();
			Vec3 targetAimVec_xz = Vec3.createVectorHelper(targetAimVec.xCoord, targetAimVec.zCoord, 0);
			Vec3 aimVec = this.getLookVec();
			Vec3 aimVec_xz = Vec3.createVectorHelper(aimVec.xCoord, aimVec.yCoord, 0);
		}
		
		
		
	}
	
//	
}
