package techguns.entities.npc;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.TGArmors;
import techguns.TGConfig;
import techguns.TGItems;
import techguns.TGuns;
import techguns.items.armors.GenericArmorMultiCamo;

public class ZombiePigmanSoldier extends GenericNPC {

	public ZombiePigmanSoldier(World world) {
		super(world);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(50.0D);
		this.isImmuneToFire = true;
	}
	
	
	@Override
	protected void addRandomArmor(int difficulty) {

		// Armors
		double chance = 0.5;
		int camo=3;
		if (Math.random() <= chance)
			GenericArmorMultiCamo.getNewWithCamo(TGArmors.t2_combat_Helmet, camo);
			this.setCurrentItemOrArmor(4,
					GenericArmorMultiCamo.getNewWithCamo(TGArmors.t2_combat_Helmet, camo));
		if (Math.random() <= chance)
			this.setCurrentItemOrArmor(3, 
					GenericArmorMultiCamo.getNewWithCamo(TGArmors.t2_combat_Chestplate, camo));
		if (Math.random() <= chance)
			this.setCurrentItemOrArmor(2, GenericArmorMultiCamo.getNewWithCamo(TGArmors.t2_combat_Leggings, camo));
		if (Math.random() <= chance)
			this.setCurrentItemOrArmor(1, GenericArmorMultiCamo.getNewWithCamo(TGArmors.t2_combat_Boots, camo));

		// Weapons
		Random r = new Random();
		Item weapon = null;
		
		
		int bound = 15;
		if (difficulty<1){
			bound = 5;
		} else if (difficulty <2){
			bound = 9;
		}
		
		
		switch (r.nextInt(bound)) {
		case 0:
		case 1:
		case 2:
			weapon = TGuns.thompson;
			break;
		case 3:
		case 4:
			weapon = TGuns.revolver;
			break;
		case 5:
		case 6:
			weapon = TGuns.ak47;
			break;
		case 7:
		case 8:
			weapon = TGuns.pistol;
			break;
		case 9:
		case 10:
			weapon = TGuns.flamethrower;
			break;
		case 11:
		case 12:
			weapon = TGuns.combatShotgun;
			break;
		case 13:
			weapon = TGuns.lmg;
			break;
		case 14:
		default:
			weapon = TGuns.rocketlauncher;
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
        return "mob.zombiepig.zpig";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
	@Override
    protected String getHurtSound()
    {
        return "mob.zombiepig.zpighurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
	@Override
    protected String getDeathSound()
    {
        return "mob.zombiepig.zpigdeath";
    }

	/**
	 * plays the sound this mob makes with each step
	 */
	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block block) {
		super.func_145780_a(par1, par2, par3, block);
		this.playSound("mob.zombie.step", 0.15F, 1.0F);
	}
	
	@Override
	protected ItemStack getRandomItemFromLoottable() {
		if (TGConfig.reducedLoottables){
			return this.reducedLoottable();
		}
		
		int x = this.rand.nextInt(6);
		switch (x){
			case 0:
				return TGItems.newStack(TGItems.plateSteel,1);
			case 1:
				return TGItems.newStack(TGItems.ingotObsidianSteel,this.rand.nextInt(2));
			case 2:
				return new ItemStack(Items.gold_ingot,1+this.rand.nextInt(3));
			case 3:
				return new ItemStack(Items.gunpowder,1+this.rand.nextInt(5));
			case 4:
				return TGItems.newStack(TGItems.shotgunShell,1+this.rand.nextInt(7));
			case 5:
				return TGItems.newStack(TGItems.rifleBullets,1+this.rand.nextInt(3));
		}
		return null;
	}
	
	protected ItemStack reducedLoottable(){
		int x = this.rand.nextInt(7);
		switch (x){
			case 0:
				return new ItemStack(Items.gold_nugget,1);
			case 1:
				return new ItemStack(Items.gunpowder,1);
			case 2:
				return TGItems.newStack(TGItems.shotgunShell,1+this.rand.nextInt(7));
			case 3:
				return TGItems.newStack(TGItems.rifleBullets,1+this.rand.nextInt(3));
		}
		return null;
	}
}
