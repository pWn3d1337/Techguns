package techguns.entities.npc;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.TGConfig;
import techguns.TGItems;
import techguns.TGuns;

public class CyberDemon extends GenericNPC {

	public CyberDemon(World world) {
		super(world);
		hasAimedBowAnim=false;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(50.0D);
		this.isImmuneToFire = true;
	}
	
	 @Override
	    protected void dropRareDrop(int i1){
    		this.entityDropItem(new ItemStack(TGuns.cyberdemonBlaster, 1), 0.0F);
	    }

	@Override
	protected void addRandomArmor(int difficulty) {

		//No armor for Cyberdemons

		// Weapons
	//	Random r = new Random();
		Item weapon = null;
	/*	switch (-1){ //r.nextInt(5)) {
		case 0:
			weapon = Techguns.rocketlauncher;
			break;
		case 1:
			weapon = Techguns.ak47;
			break;
		case 2:
			weapon = Techguns.combatShotgun;
			break;
		case 3:
		case 4:
		default:*/
			weapon = TGuns.cyberdemonBlaster;
	//		break;
	//	}
		if (weapon != null) this.setCurrentItemOrArmor(0, new ItemStack(weapon));

	}

	/**
     * Returns the sound this mob makes while it's alive.
     */
	@Override
    protected String getLivingSound()
    {
        return "techguns:npcs.cyberdemonIdle";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
	@Override
    protected String getHurtSound()
    {
        return "techguns:npcs.cyberdemonHurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
	@Override
    protected String getDeathSound()
    {
        return "techguns:npcs.cyberdemonDeath";
    }

	/**
	 * plays the sound this mob makes with each step
	 */
	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block block) {
		super.func_145780_a(par1, par2, par3, block);
		this.worldObj.playSoundAtEntity(this, "techguns:npcs.cyberdemonStep", 0.15F, 1.0F);
	}

	@Override
	protected Entity findPlayerToAttack()
    {
        //EntityPlayer entityplayer = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);
        //return entityplayer != null && this.canEntityBeSeen(entityplayer) ? entityplayer : null;
		Entity e = super.findPlayerToAttack();
		if (e != null) {
			this.worldObj.playSoundAtEntity(this, "techguns:npcs.cyberdemonAggro", 1.0F, 1.0F);
		}
		return e;
    }
	
	@Override
	protected ItemStack getRandomItemFromLoottable() {
		if (TGConfig.reducedLoottables){
			return this.reducedLoottable();
		}
		
		int x = this.rand.nextInt(20);
		//float y = this.rand.nextFloat();
		switch (x){
			case 0:
				return new ItemStack(Items.ghast_tear,1);
			case 2:
			case 3:
			case 4:
			case 5:
				return new ItemStack(Items.blaze_rod,1);
			default:
				return TGItems.newStack(TGItems.cyberneticParts, 1);
		}
		//return null;*/
		//return TGItems.newStack(TGItems.cyberneticParts, 1);
	}
	
	protected ItemStack reducedLoottable(){
		int x = this.rand.nextInt(2);
		switch (x){
			case 0:
				return TGItems.newStack(TGItems.cyberneticParts, 1);
			default:
				return null;
		}
		//return null;
	}
}
