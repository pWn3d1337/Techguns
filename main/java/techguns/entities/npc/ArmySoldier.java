package techguns.entities.npc;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.TGArmors;
import techguns.TGItems;
import techguns.TGuns;
import techguns.items.armors.GenericArmorMultiCamo;

public class ArmySoldier extends GenericNPC {

	public ArmySoldier(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(75.0D);
	}

	@Override
	protected void addRandomArmor(int difficulty) {

		// Armors
		double chance = 0.5;
		if (Math.random() <= 0.66)
			
			if (Math.random() <= 0.5){
				this.setCurrentItemOrArmor(4,
						new ItemStack(TGArmors.t2_combat_Helmet));
			} else {
				this.setCurrentItemOrArmor(4,
						GenericArmorMultiCamo.getNewWithCamo(TGArmors.t2_beret, 2));
			}
		
		if (Math.random() <= chance)
			this.setCurrentItemOrArmor(3, new ItemStack(
					TGArmors.t2_combat_Chestplate));
		if (Math.random() <= chance)
			this.setCurrentItemOrArmor(2, new ItemStack(
					TGArmors.t2_combat_Leggings));
		if (Math.random() <= chance)
			this.setCurrentItemOrArmor(1, new ItemStack(TGArmors.t2_combat_Boots));

		// Weapons
		Random r = new Random();
		Item weapon = null;
		switch (r.nextInt(3)) {
		case 0:
			weapon = TGuns.m4;
			break;
		case 1:
			weapon = TGuns.combatShotgun;
			break;
		case 2:
		default:
			weapon = TGuns.boltaction;
			break;
		}
		if (weapon != null) this.setCurrentItemOrArmor(0, new ItemStack(weapon));

	}

    @Override
    protected String getLivingSound()
    {
        return "mob.villager.idle";
    }

    @Override
    protected String getHurtSound()
    {
        return "mob.villager.hit";
    }

    @Override
    protected String getDeathSound()
    {
        return "mob.villager.death";
    }

	/**
	 * plays the sound this mob makes with each step
	 */
	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block block) {
		super.func_145780_a(par1, par2, par3, block);
		//this.playSound("mob.zombie.step", 0.15F, 1.0F);
	}

	/* (non-Javadoc)
	 * @see techguns.entities.npc.GenericNPC#getRandomItemFromLoottable()
	 */
	@Override
	protected ItemStack getRandomItemFromLoottable() {
		int x = this.rand.nextInt(5);
		float y = this.rand.nextFloat();
		switch (x){
			case 0:
				return TGItems.newStack(TGItems.heavyCloth,1);
			case 1:
				return new ItemStack(Items.iron_ingot,1);
			case 2:
				return new ItemStack(Items.gunpowder,1);
			case 3:
				return TGItems.newStack(TGItems.shotgunShell,1+Math.round(7*y));
			case 4:
				return TGItems.newStack(TGItems.rifleBullets,1+Math.round(2*y));
		}
		return null;
	}

	
}
