package techguns.entities.npc;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import techguns.TGArmors;
import techguns.TGEntities;
import techguns.TGItems;
import techguns.TGuns;
import techguns.damagesystem.DamageSystem;
import techguns.damagesystem.INpcTGDamageSystem;
import techguns.damagesystem.TGDamageSource;
import techguns.entities.ai.EntityAIHurtByTargetTGFactions;
import techguns.entities.ai.EntityAIRangedAttack;
import techguns.items.armors.GenericArmor;
import techguns.items.guns.GenericGun;

public class GenericNPC extends EntityMob implements IRangedAttackMob, INPCTechgunsShooter, INpcTGDamageSystem, ITGNpcTeam{
	 
		//private GenericGun gun = this.pickRandomGun();
	
		
		private EntityAIRangedAttack aiRangedAttack = null; //new EntityAIRangedAttack(this, 1.0D, 20, 60, 15.0F); //EntityMob, Movespeed, ?, MaxRangedAttackTime, Range?
	    private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);
	    //private static final String __OBFID = "CL_00001697";

	    protected boolean hasAimedBowAnim;
	    
	    public GenericNPC(World world)
	    {
	        super(world);
	        this.tasks.addTask(1, new EntityAISwimming(this));
	        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
	        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	        this.tasks.addTask(6, new EntityAILookIdle(this));
	        this.targetTasks.addTask(1, new EntityAIHurtByTargetTGFactions(this, false));
	        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));

	        if (world != null && !world.isRemote)
	        {
	            this.setCombatTask();
	        }
	        hasAimedBowAnim=true;
	    }

	    public boolean getHasAimedBowAnim() {
			return hasAimedBowAnim;
		}

		private GenericGun pickRandomGun(int difficulty) {
			Random r = new Random();
			GenericGun gun;
			switch (r.nextInt(4)) {
				case 0:
					gun = (GenericGun) TGuns.revolver;
					break;
				case 1:
					gun = (GenericGun) TGuns.thompson;
					break;
				case 2:
					gun = (GenericGun) TGuns.as50;
					break;
				case 3:
					gun = (GenericGun) TGuns.grimreaper;
					break;
				case 4:
				default:
					gun = (GenericGun) TGuns.handcannon;
			}
			return gun;
		}

		@Override
	    protected void applyEntityAttributes()
	    {
	        super.applyEntityAttributes();
	        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	    }


	    /**
	     * Returns true if the newer Entity AI code should be run
	     */
	    @Override
	    public boolean isAIEnabled()
	    {
	        return true;
	    }

	    /**
	     * Returns the sound this mob makes while it's alive.
	     */
	    @Override
	    protected String getLivingSound()
	    {
	        return "mob.villager.idle";
	    }

	    /**
	     * Returns the sound this mob makes when it is hurt.
	     */
	    @Override
	    protected String getHurtSound()
	    {
	        return "mob.villager.hit";
	    }

	    /**
	     * Returns the sound this mob makes on death.
	     */
	    @Override
	    protected String getDeathSound()
	    {
	        return "mob.villager.death";
	    }

	    /**
	     * plays the sound this mob makes with each step
	     */
	    @Override
	    protected void func_145780_a(int par1, int par2, int par3, Block block)
	    {
	    	super.func_145780_a(par1, par2, par3, block);
	        //this.playSound("mob.zombie.step", 0.15F, 1.0F);
	    }
	    
	    @Override
	    public boolean attackEntityAsMob(Entity target)
	    {
	        if (super.attackEntityAsMob(target))
	        {
	        	//ADD Special attack code here.
	        	
	        	return true;
	        }
	        else
	        {
	            return false;
	        }
	    }


	    /**
	     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	     * use this to react to sunlight and start to burn.
	     */
	    @Override
	    public void onLivingUpdate()
	    {
	    
	    	//Extra update code

	        super.onLivingUpdate();
	    }

	    
	    /**
	     * Called when the mob's health reaches 0.
	     */
	    @Override
	    public void onDeath(DamageSource damageSource)
	    {
	        super.onDeath(damageSource);

	    }

//	    @Override
//	    protected Item getDropItem()
//	    {
//	        return Items.arrow;
//	    }

//	    /**
//	     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
//	     * par2 - Level of Looting used to kill this mob.
//	     */
//	    @Override
//	    protected void dropFewItems(boolean hitByPlayer, int level)
//	    {
//	        int j;
//	        int k;
//
//	        j = this.rand.nextInt(3 + level);
//
//	        for (k = 0; k < j; ++k)
//	        {
//	            this.dropItem(Items.bone, 1);
//	        }
//	    }
//
//	    @Override
//	    protected void dropRareDrop(int p_70600_1_)
//	    {
//	    	this.entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0F);
//	    }
	    
	    @Override
	    protected void dropRareDrop(int i1){
	    	if (this.getHeldItem()!=null){
	    		this.entityDropItem(new ItemStack(this.getHeldItem().getItem(), 1), 0.0F);
	    	}
	    }

	    /**
	     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
	     * par2 - Level of Looting used to kill this mob.
	     */
	    @Override
	    protected void dropFewItems(boolean hitByPlayer, int level)
	    {
	        int j;
	        int k;

	        j = this.rand.nextInt(3 + level);

	        for (k = 0; k < j; ++k)
	        {
	        	ItemStack it = this.getRandomItemFromLoottable();
	        	if (it !=null){
	        		//this.dropItem(it.getItem(),it.stackSize,it.getItemDamage());
	        		this.entityDropItem(TGItems.newStack(it, it.stackSize), 0.0f);
	        	}
	        }
	    }
	    
	    /*@Override
	    protected Item getDropItem()
	    {
	        return this.getRandomItemFromLoottable().getItem();
	    }*/
	    
	    protected ItemStack getRandomItemFromLoottable(){
	    	return null;
	    }
	    
	    /**
	     * Makes entity wear random armor based on difficulty
	     */
	    @Override
	    protected void addRandomArmor()
	    {
	       this.addRandomArmor(0);
	    }

	    protected void addRandomArmor(int difficulty){
	    	this.setCurrentItemOrArmor(0, new ItemStack(this.pickRandomGun(difficulty)));
	        
	        this.setCurrentItemOrArmor(4, new ItemStack(TGArmors.t1_combat_Helmet));
	        this.setCurrentItemOrArmor(3, new ItemStack(TGArmors.t1_combat_Chestplate));
	        this.setCurrentItemOrArmor(2, new ItemStack(TGArmors.t1_combat_Leggings));
	        this.setCurrentItemOrArmor(1, new ItemStack(TGArmors.t1_combat_Boots));
	    }
	    
	    
	    @Override
	    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_)
	    {
	        p_110161_1_ = super.onSpawnWithEgg(p_110161_1_);

            //this.tasks.addTask(4, this.aiRangedAttack);
            //this.addRandomArmor();
            //this.enchantEquipment();
            //this.setCombatTask();
	        this.onSpawnByManager(this.rand.nextInt(TGEntities.MAX_NPC_DIFFICULTY+1));
	        
	        return p_110161_1_;
	    }

	    public void onSpawnByManager(int difficulty) {
	    	//super.onSpawnWithEgg(null);
	    	
	    	this.addRandomArmor(difficulty);
	    	this.setCombatTask();
	    }
	    
	    
	    /**
	     * sets this entity's combat AI.
	     */
	    public void setCombatTask()
	    {
	        this.tasks.removeTask(this.aiAttackOnCollide);
	        this.tasks.removeTask(this.aiRangedAttack);
	        ItemStack itemstack = this.getHeldItem();

	        if (itemstack != null && itemstack.getItem() instanceof techguns.items.guns.GenericGun)
	        {
	        	GenericGun gun = (GenericGun) itemstack.getItem();
	        	this.aiRangedAttack = gun.getAIAttack(this);
	            this.tasks.addTask(4, this.aiRangedAttack);
	        }
	        else
	        {
	            this.tasks.addTask(4, this.aiAttackOnCollide);
	        }
	    }

	    /**
	     * Attack the specified entity using a ranged attack.
	     */
	    @Override
	    public void attackEntityWithRangedAttack(EntityLivingBase target, float distance)
	    {

	    	if (this.getHeldItem() == null) {
	    		return;
	    	}
	    	else {
	    		Item gun = this.getHeldItem().getItem();
	    		if (gun instanceof techguns.items.guns.GenericGun) {
	    			
	    			EnumDifficulty difficulty = this.worldObj.difficultySetting;
	    	    	float acc=1.0f;
	    	    	switch(difficulty){
	    	    		case EASY:
	    	    			acc=1.3f;
	    	    			break;
	    	    		case NORMAL:
	    	    			acc = 1.15f;
	    	    			break;
	    	    		case HARD:
	    	    			acc = 1.0f;
	    	    			break;
						default:
							break;
	    	    	}
	    			
	    			((GenericGun) gun).fireWeaponFromNPC(this,0.5f,acc);
	    		}
		
	    	}
	    	
	    }


	    /**
	     * (abstract) Protected helper method to read subclass entity data from NBT.
	     */
	    @Override
	    public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	    {
	        super.readEntityFromNBT(p_70037_1_);

	        this.setCombatTask();
	    }

	    /**
	     * (abstract) Protected helper method to write subclass entity data to NBT.
	     */
	    @Override
	    public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	    {
	        super.writeEntityToNBT(p_70014_1_);
	    }

	    /**
	     * Sets the held item, or an armor slot. Slot 0 is held item. Slot 1-4 is armor. Params: Item, slot
	     */
	    
	    @Override
	    public void setCurrentItemOrArmor(int slot, ItemStack item)
	    {
	        super.setCurrentItemOrArmor(slot, item);

	        if (!this.worldObj.isRemote && slot == 0)
	        {
	            this.setCombatTask();
	        }
	    }

	    /**
	     * Returns the Y Offset of this entity.
	     */
	    public double getYOffset()
	    {
	        return super.getYOffset() - 0.5D;
	    }

		@Override
		public float getWeaponPosX() {
			return 0;//-0.35f;
		}

		@Override
		public float getWeaponPosY() {
			return 1.6f;//1.0f;
		}

		@Override
		public float getWeaponPosZ() {
			return 0;//;1.1f;
		}

		@Override
		public float getTotalArmorAgainstType(TGDamageSource dmgsrc) {
			ItemStack[] inv = new ItemStack[]{this.getEquipmentInSlot(1),this.getEquipmentInSlot(2),this.getEquipmentInSlot(3),this.getEquipmentInSlot(4)};
			float totalArmor=0.0f;
			for (ItemStack stack: inv){
				if(stack!=null && stack.getItem() instanceof ItemArmor){
					
					if(stack.getItem() instanceof GenericArmor){
						GenericArmor genArmor = (GenericArmor) stack.getItem();
						float armorvalue = genArmor.getArmorValue(stack, dmgsrc.damageType);
						totalArmor+=armorvalue;
					} else {
						ItemArmor armor = (ItemArmor) stack.getItem();
						float armorvalue = armor.getArmorMaterial().getDamageReductionAmount(armor.armorType);
						armorvalue = DamageSystem.getArmorAgainstDamageTypeDefault(this, armorvalue, dmgsrc.damageType);
						totalArmor+=armorvalue;
					}
				}
			}
			
			return totalArmor;
		}

		@Override
		public float getPenetrationResistance(TGDamageSource dmgsrc) {
			return 0.0f;
		}

		@Override
		public TGNpcFaction getTGFaction() {
			return TGNpcFaction.HOSTILE;
		}
	    
		
}
