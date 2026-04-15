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

public class StormTrooper extends GenericNPC {

	public StormTrooper(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(35);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(75.0D);
	}

	@Override
	protected void addRandomArmor(int difficulty) {

		// Armors

		this.setCurrentItemOrArmor(4,
					GenericArmorMultiCamo.getNewWithCamo(TGArmors.t3_miner_Helmet, 1));
		this.setCurrentItemOrArmor(3,
				GenericArmorMultiCamo.getNewWithCamo(TGArmors.t3_miner_Chestplate, 1));
		this.setCurrentItemOrArmor(2,
				GenericArmorMultiCamo.getNewWithCamo(TGArmors.t3_miner_Leggings, 1));
		this.setCurrentItemOrArmor(1,
				GenericArmorMultiCamo.getNewWithCamo(TGArmors.t3_miner_Boots, 1));

	
		// Weapons
		Random r = new Random();
		Item weapon = null;
		switch (r.nextInt(2)) {
		case 0:
			weapon = TGuns.blasterRifle;
			break;
		case 1:
			weapon = TGuns.lasergun;
			break;
		case 2:
		default:
			weapon = TGuns.lasergun;
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
		int x = this.rand.nextInt(9);
		float y = this.rand.nextFloat();
		switch (x){
			case 0:
			case 1:
				return TGItems.newStack(TGItems.plasticSheet,1);
			case 2:
			case 3:
				return TGItems.newStack(TGItems.ingotObsidianSteel, 1);
			case 4:
			case 5:
			case 6:
				return new ItemStack(Items.redstone,1+(int)y*3);
			case 7:
				return TGItems.newStack(TGItems.circuitBoard,1);
			case 8:
				return TGItems.newStack(TGItems.energyCell,1);
		}
		return null;
	}

	
}