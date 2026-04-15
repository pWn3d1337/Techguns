package techguns.entities.npc;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import techguns.damagesystem.INpcTGDamageSystem;
import techguns.damagesystem.TGDamageSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AlienBug extends EntitySpider implements ITGNpcTeam, INpcTGDamageSystem {

	 private int attackTimer;
	
	protected void entityInit() {
		super.entityInit();
	}
	
	
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.0D);
    }
	
	public AlienBug(World p_i1738_1_) {
		super(p_i1738_1_);
		this.setSize(1.1F, 1.2F);
	}
	
	
	 /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "techguns:npcs.alienbugIdle";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "techguns:npcs.alienbugHurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "techguns:npcs.alienbugDeath";
    }

    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
        this.playSound("techguns:npcs.alienbugStep", 0.15F, 1.0F);
    }
    
    
    

	@Override
	protected boolean isValidLightLevel() {
		return true;
	}


	@SideOnly(Side.CLIENT)
    public int getAttackTimer()
    {
        return this.attackTimer;
    }
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (this.attackTimer > 0)
        {
            --this.attackTimer;
        }
	}
	
	@Override
	protected Entity findPlayerToAttack()
    {
        EntityPlayer entityplayer = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);
        if (entityplayer != null) {
			this.worldObj.playSoundAtEntity(this, "techguns:npcs.alienbugAggro", 1.0F, 1.0F);
		}
        return entityplayer != null && this.canEntityBeSeen(entityplayer) ? entityplayer : null;
    }
	
	@Override
	public boolean attackEntityAsMob(Entity p_70652_1_) {
		boolean b =super.attackEntityAsMob(p_70652_1_);
        this.attackTimer = 10;
        
        this.worldObj.playSoundAtEntity(this, "techguns:npcs.alienbugBite", 0.75F, 1.0F);
        //this.motionY = 0.5;
        
        this.worldObj.setEntityState(this, (byte)4);
           
        return b;
	}
	
	@SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte p_70103_1_)
    {
        if (p_70103_1_ == 4)
        {
            this.attackTimer = 10;
            //this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
        }
        else
        {
            super.handleHealthUpdate(p_70103_1_);
        }
    }
	
	/**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity p_70785_1_, float p_70785_2_)
    {
    	if (this.attackTimer <= 0 && p_70785_2_ < 2.0F && p_70785_1_.boundingBox.maxY > this.boundingBox.minY && p_70785_1_.boundingBox.minY < this.boundingBox.maxY)
        {
            this.attackEntityAsMob(p_70785_1_);
        }
    }


	@Override
	public TGNpcFaction getTGFaction() {
		return TGNpcFaction.HOSTILE;
	}


	@Override
	public float getTotalArmorAgainstType(TGDamageSource dmgsrc) {
		switch( dmgsrc.damageType){
			case ENERGY:
			case EXPLOSION:
			case ICE:
			case LIGHTNING:
				return 5.0f;
			case PHYSICAL:
				return 10.0f;
			case POISON:
				return 20.0f;
			case PROJECTILE:
				return 10.0f;
			case RADIATION:
				return 20.0f;
			case FIRE:
			case UNRESISTABLE:
			default:
				return 0;
		}
	}


	@Override
	public float getPenetrationResistance(TGDamageSource dmgsrc) {
		return 0.0f;
	}
}
