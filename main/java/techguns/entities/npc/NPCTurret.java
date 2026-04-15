package techguns.entities.npc;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.entities.ai.EntityAIHurtByTargetTGFactions;
import techguns.entities.ai.EntityAIRangedAttack;
import techguns.entities.ai.TurretEntityAINearestAttackableTarget;
import techguns.entities.ai.TurretEntityAIWatchClosest;
import techguns.entities.ai.TurretTargetSelector;
import techguns.items.guns.GenericGun;
import techguns.packets.PacketRequestTurretSync;
import techguns.tileentities.TurretBaseEnt;

public class NPCTurret extends EntityCreature implements IAnimals, IRangedAttackMob, INPCTechgunsShooter, ITGNpcTeam {
	private static final ResourceLocation texture_0 = new ResourceLocation("techguns:textures/blocks/TurretBase.png");
	private static final ResourceLocation texture_1 = new ResourceLocation("techguns:textures/blocks/TurretBase_2.png");
	private static final ResourceLocation texture_2 = new ResourceLocation("techguns:textures/blocks/TurretBase_3.png");
	private static final ResourceLocation texture_3 = new ResourceLocation("techguns:textures/blocks/TurretBase_4.png");
	private static final ResourceLocation texture_4 = new ResourceLocation("techguns:textures/blocks/TurretBase_5.png");
	
	protected TurretEntityAIWatchClosest aiWatch=null;
	protected EntityAILookIdle aiIdle =null;
    protected EntityAIHurtByTargetTGFactions aiHurt=null;
    protected TurretEntityAINearestAttackableTarget aiTarget=null;

	private EntityAIRangedAttack aiRangedAttack = null; //EntityMob, Movespeed, ?, MaxRangedAttackTime, Range?
    //private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityMob.class, 1.2D, false);
	
    public TurretBaseEnt mountedTileEnt=null;
    
    public boolean active=true;
    
    //@SideOnly(Side.CLIENT)
    protected int networkUpdateRequestDelay=0;
   /* protected double mountPosX;
    protected double mountPosY;
    protected double mountPosZ;*/
    
    //ItemStack weapon = new ItemStack(Techguns.minigun);
    
	public NPCTurret(World world) {
		super(world);
		
       this.setAITasks();
        
        this.mountedTileEnt=null;
       /* if (world != null && !world.isRemote)
        {
            this.setCombatTask();
        }*/
        this.height=1.0f;

	}
    
	public NPCTurret(World world, TurretBaseEnt mountedTileEnt) {
		super(world);
		
        this.setAITasks();

        this.mountedTileEnt=mountedTileEnt;
        if (world != null && !world.isRemote)
        {
            this.setCombatTask();
        }
        this.height=1.0f;
	}
	
	public static ResourceLocation getTexture(int armor){
		switch(armor){
		case 5:
			return texture_1;
		case 10:
			return texture_2;
		case 15:
			return texture_3;
		case 20:
			return texture_4;
		default:
			return texture_0;
	}
	}
	
	public ResourceLocation getTexture(){
		return getTexture(this.getTotalArmorValue());
	}
	
    /**
     * Returns true if the newer Entity AI code should be run
     */
	@Override
    public boolean isAIEnabled()
    {
        return true;
    }
	
    @Override
	public float getEyeHeight() {
		return 0.8f;
	}

    
    
	@Override
	protected void onDeathUpdate() {
		// TODO Auto-generated method stub
		super.onDeathUpdate();
	}

	/**
     * Returns true if this entity can attack entities of the specified class.
     */
	@Override
    public boolean canAttackClass(Class target)
    {
		/*if (EntityPlayer.class.isAssignableFrom(target))
		{
			return false;
		}*/
        return true;
    }
	
	@Override
	public ItemStack getHeldItem() {
		return getWeapon();
	}

	protected ItemStack getWeapon(){
		if (mountedTileEnt!=null){
			return mountedTileEnt.getWeapon();
		}else{
	       //System.out.println("TileEntity is null");
			
		}
		return null;
	}
	
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0f);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30);
		//this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(20.0D);
    }
	
    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
    protected String getLivingSound()
    {
        return null; //"mob.villager.idle";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected String getHurtSound()
    {
        return "mob.irongolem.hit"; //"mob.villager.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    protected String getDeathSound()
    {
        return "mob.irongolem.death"; //"mob.villager.death";
    }

    /**
     * plays the sound this mob makes with each step
     */
    @Override
    protected void func_145780_a(int par1, int par2, int par3, Block block)
    {
    	//turrets don't move
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
     * Called when the mob's health reaches 0.
     */
    @Override
    public void onDeath(DamageSource damageSource)
    {
        //super.onDeath(damageSource);
    	//++this.deathTime;

    	//System.out.println("Dying:"+this.deathTime);
        this.mountedTileEnt.onTurretDeath();
        
    	
    	
       /* if (this.deathTime == 20)
        {
            int i;

           /* if (!this.worldObj.isRemote && (this.recentlyHit > 0 || this.isPlayer()) && this.func_146066_aG() && this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot"))
            {
                i = this.getExperiencePoints(this.attackingPlayer);

                while (i > 0)
                {
                    int j = EntityXPOrb.getXPSplit(i);
                    i -= j;
                    this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, j));
                }
            }*/
            /*
            this.setDead();

            for (i = 0; i < 20; ++i)
            {
                double d2 = this.rand.nextGaussian() * 0.02D;
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                this.worldObj.spawnParticle("explode", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1);
            }
        }*/
    }
    
    @Override
    protected void dropRareDrop(int i1){}

    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
    @Override
    protected void dropFewItems(boolean hitByPlayer, int level){}
    
    protected ItemStack getRandomItemFromLoottable(){
    	return null;
    }
    
    /**
     * Makes entity wear random armor based on difficulty
     */
    @Override
    protected void addRandomArmor(){}

    
    /**
     * Sets this turrets base, called from the base on nbt read
     * @param ent
     */
    public void setTileEnt(TurretBaseEnt ent){
    	this.mountedTileEnt=ent;
    	this.setCombatTask();
    }
    
    
    
    @Override
	public void onUpdate() {
    	super.onUpdate();
		this.setPosition(this.prevPosX, this.prevPosY, this.prevPosZ);
		/*if (this.getHealth()>0){
			this.isDead=false;
		}*/
		if(! worldObj.isRemote){
			if (this.mountedTileEnt==null){
				
				//try to relink
				int x=(int) Math.floor(this.posX);
				int y=((int) Math.round(this.posY))-1;
				int z = (int) Math.floor(this.posZ);
				//System.out.println("Trying to relink:"+x+":"+y+":"+z);
				TileEntity tile = this.worldObj.getTileEntity(x, y, z);
				if (tile !=null && tile instanceof TurretBaseEnt){
					((TurretBaseEnt) tile).setMountedTurret(this);
					this.mountedTileEnt=(TurretBaseEnt) tile;
					worldObj.markBlockForUpdate(x, y, z);
					this.setCombatTask();
				} else {
					//System.out.println("can't get tileentity");
				}
				
			} else {
				
				//normal logic;
				
				
			}
		} else {
			//Client Side
			if (networkUpdateRequestDelay>0){
				networkUpdateRequestDelay--;
			}
			
			//4096 = 64² -> squared Distance
			if (this.mountedTileEnt == null && networkUpdateRequestDelay <=0 &&  Techguns.proxy.clientInRangeSquared(this.posX, this.posZ, 4096)){
				TGPackets.network.sendToServer(new PacketRequestTurretSync(this));
				this.networkUpdateRequestDelay = 600; //don't spam server with packets
			}
		}
	}
    
	/**
     * Attack the specified entity using a ranged attack.
     */
    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distance)
    {

    	if (this.getHeldItem()== null) {
    		//System.out.println("NO GUN");
    		return;
    	}
    	else {
    		Item gun = this.getHeldItem().getItem();
    		if (gun instanceof techguns.items.guns.GenericGun) {
    			
    			if (this.mountedTileEnt!=null && this.mountedTileEnt.consumeAmmo()){    			
    			
    				((GenericGun) gun).fireWeaponFromNPC(this,1.0f,1.0f);
    			}
    		}
	
    	}
    	
    }
       
    

	@Override
	public void writeToNBT(NBTTagCompound tags) {
		super.writeToNBT(tags);
		//tags.setIntArray("mountedPos", new int[]{this.mountedTileEnt.xCoord,this.mountedTileEnt.yCoord,this.mountedTileEnt.zCoord});
	}

	@Override
	public void readFromNBT(NBTTagCompound tags) {
		super.readFromNBT(tags);
		/*int[] coords = tags.getIntArray("mountedPos");
		TileEntity mounted = this.worldObj.getTileEntity(coords[0], coords[1], coords[2]);
		if (mounted!=null && mounted instanceof TurretBaseEnt){
			TurretBaseEnt base = (TurretBaseEnt) mounted;
			this.mountedTileEnt=base;
			if (!base.hasMountedTurret()){
				base.setMountedTurret(this);
			}
		}*/
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	public void destroy(){
		this.setHealth(0);
		this.setDead();
	}
	
	public void setAITasks(){
		if (aiWatch==null){
			this.aiWatch = new TurretEntityAIWatchClosest(this, EntityLiving.class, 80.0F,20.0D);
		}
		if(aiIdle==null){
			this.aiIdle = new EntityAILookIdle(this);
		}
		if(aiHurt==null){
			this.aiHurt =new EntityAIHurtByTargetTGFactions(this, false);
		}
		if(aiTarget==null){
			this.aiTarget= new TurretEntityAINearestAttackableTarget(this, EntityLivingBase.class, 0, true, false, new TurretTargetSelector(this),20.0D);
		}
		this.tasks.addTask(2, this.aiWatch);
        this.tasks.addTask(3, this.aiIdle);
        this.targetTasks.addTask(1, this.aiHurt);
        this.targetTasks.addTask(2, this.aiTarget);
	}
	
	public void disable(){
		this.active=false;
		this.tasks.removeTask(this.aiWatch);
		this.tasks.removeTask(this.aiIdle);
		this.targetTasks.removeTask(this.aiHurt);
		this.targetTasks.removeTask(this.aiTarget);
		this.tasks.removeTask(this.aiRangedAttack);
	}
	
	/**
     * sets this entity's combat AI.
     */
    public void setCombatTask()
    {
        //this.tasks.removeTask(this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiRangedAttack);
        ItemStack itemstack = this.getHeldItem();

        if (itemstack != null && itemstack.getItem() instanceof techguns.items.guns.GenericGun)
        {
        	GenericGun gun = (GenericGun) itemstack.getItem();
        	this.aiRangedAttack = gun.getAIAttack(this);
            this.tasks.addTask(1, this.aiRangedAttack);
            this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(gun.getAI_attackRange());
            /*this.tasks.removeTask(this.aiWatch);
            this.aiWatch=new EntityAIWatchClosest(this, EntityLiving.class, gun.getAI_attackRange());
            this.tasks.addTask(2, this.aiWatch);*/
        }
        else
        {
        	//System.out.println("No Gun!");
            //this.tasks.addTask(4, this.aiAttackOnCollide);
        }
    }

	@Override
	public int getTotalArmorValue() {
		if (this.mountedTileEnt!=null){
			return mountedTileEnt.getTurretArmorValue();
		} else {
			return 0;
		}
		
	}

	@Override
	public float getWeaponPosX() {
		return 0.05f;
	}

	@Override
	public float getWeaponPosY() {
		return 0.7f;
	}

	@Override
	public float getWeaponPosZ() {
		return 0.8f;
	}

	@Override
	public TGNpcFaction getTGFaction() {
		return TGNpcFaction.TURRET;
	}

    

}
