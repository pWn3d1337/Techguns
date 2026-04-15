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

public class ZombieMiner extends GenericNPC {

	public ZombieMiner(World world) {
		super(world);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(50.0D);
	}

	@Override
	protected void addRandomArmor(int difficulty) {

		// Armors
		double chance = 0.5;
		
		int camo = GenericArmorMultiCamo.getRandomCamoIndexFor((GenericArmorMultiCamo) TGArmors.t1_miner_Chestplate);
		
		this.setCurrentItemOrArmor(4, GenericArmorMultiCamo.getNewWithCamo(
				TGArmors.t1_miner_Helmet,camo));
		if (Math.random() <= chance)
			this.setCurrentItemOrArmor(3, GenericArmorMultiCamo.getNewWithCamo(
					TGArmors.t1_miner_Chestplate,camo));
		if (Math.random() <= chance)
			this.setCurrentItemOrArmor(2, GenericArmorMultiCamo.getNewWithCamo(
					TGArmors.t1_miner_Leggings,camo));
		if (Math.random() <= chance)
			this.setCurrentItemOrArmor(1, GenericArmorMultiCamo.getNewWithCamo(
					TGArmors.t1_miner_Boots,camo));

		// Weapons
		Random r = new Random();
		Item weapon = null;
		switch (r.nextInt(4)) {
		case 0:
			weapon = Items.wooden_pickaxe;
			break;
		case 1:
			weapon = Items.iron_pickaxe;
			break;
		case 2:
			weapon = Items.stone_pickaxe;
			break;
		case 3:
		default:
			weapon = TGuns.handcannon;
			break;
		}
		if (weapon != null) this.setCurrentItemOrArmor(0, new ItemStack(weapon));

	}

	@Override
	protected String getLivingSound() {
		return "mob.zombie.say";
	}

	@Override
	protected String getHurtSound() {
		return "mob.zombie.hurt";
	}

	@Override
	protected String getDeathSound() {
		return "mob.zombie.death";
	}

	/**
	 * plays the sound this mob makes with each step
	 */
	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block block) {
		super.func_145780_a(par1, par2, par3, block);
		this.playSound("mob.zombie.step", 0.15F, 1.0F);
	}

	/* (non-Javadoc)
	 * @see techguns.entities.npc.GenericNPC#getRandomItemFromLoottable()
	 */
	@Override
	protected ItemStack getRandomItemFromLoottable() {
		if (TGConfig.reducedLoottables){
			return this.reducedLoottable();
		}
		int x = this.rand.nextInt(5);
		//float y = this.rand.nextFloat();
		switch (x){
			case 0:
				return TGItems.newStack(TGItems.heavyCloth, 1);
			case 1:
				return new ItemStack(Items.coal,1);
			case 2:
				return new ItemStack(Items.gunpowder,1);
			case 3:
				return new ItemStack(Items.iron_ingot,1);
			case 4:
				return new ItemStack(Items.redstone,1);
		}
		return null;
	}
	
	protected ItemStack reducedLoottable(){
		int x = this.rand.nextInt(9);
		switch (x){
			case 0:
				return TGItems.newStack(TGItems.heavyCloth, 1);
			case 2:
				return new ItemStack(Items.gunpowder,1);
			default:
				return null;
		}
		//return null;
	}
}
