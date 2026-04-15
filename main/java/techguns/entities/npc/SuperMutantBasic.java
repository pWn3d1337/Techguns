package techguns.entities.npc;

import java.util.Random;

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
import techguns.damagesystem.TGDamageSource;
import techguns.debug.Keybinds;

public class SuperMutantBasic extends GenericNPC {

	public SuperMutantBasic(World world) {
		super(world);
		this.height=1.8F*this.getModelScale();
	}
	
	public double getModelHeightOffset(){
		return 0.55d;
	}
	
	public float getModelScale() {
		return 1.35f;
	}

	@Override
	public float getWeaponPosY() {
		return 2.2f; //+(float)Keybinds.Y;
	}
	
	@Override
	public float getWeaponPosX() {
		return -0.2f; //+(float)Keybinds.X;
	}

	@Override
	public float getWeaponPosZ() {
		return 0.3f; //(float)Keybinds.Z;
	}

	
	@Override
	public float getTotalArmorAgainstType(TGDamageSource dmgsrc) {
		switch(dmgsrc.damageType){
			case EXPLOSION:
			case LIGHTNING:
			case ENERGY:
			case FIRE:
			case ICE:
				return 10.0f;
			case PHYSICAL:
			case PROJECTILE:
				return 7.0f;
			case POISON:
			case RADIATION:
				return 15.0f;
			case UNRESISTABLE:
		default:
			return 0.0f;
		}
	}

	@Override
	public int getTotalArmorValue() {
		return 7;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(50.0D);
	}

	
	/* @Override
	    protected void dropRareDrop(int i1){
    		this.entityDropItem(new ItemStack(TGuns.cyberdemonBlaster, 1), 0.0F);
	    }*/

	@Override
	protected void addRandomArmor(int difficulty) {

			// Weapons
		Random r = new Random();
		Item weapon = null;
		switch (r.nextInt(5)) {
			case 0:
				weapon = TGuns.rocketlauncher;
				break;
			case 1:
				weapon = TGuns.ak47;
				break;
			case 2:
				weapon = TGuns.combatShotgun;
				break;
			case 3:
			case 4:
			default:
				weapon = TGuns.lasergun;
				break;
		}
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

	}
	
	protected ItemStack reducedLoottable(){
		int x = this.rand.nextInt(2);
		switch (x){
			case 0:
				return TGItems.newStack(TGItems.cyberneticParts, 1);
			default:
				return null;
		}

	}

}
