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

public class ZombieSoldier extends GenericNPC {

	public ZombieSoldier(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(50.0D);
	}

	@Override
	protected void addRandomArmor(int difficulty) {

		// Armors
		double chance = 0.5;
		if (Math.random() <= chance)
			this.setCurrentItemOrArmor(4,
					new ItemStack(TGArmors.t1_combat_Helmet));
		if (Math.random() <= chance)
			this.setCurrentItemOrArmor(3, new ItemStack(
					TGArmors.t1_combat_Chestplate));
		if (Math.random() <= chance)
			this.setCurrentItemOrArmor(2, new ItemStack(
					TGArmors.t1_combat_Leggings));
		if (Math.random() <= chance)
			this.setCurrentItemOrArmor(1, new ItemStack(TGArmors.t1_combat_Boots));

		// Weapons
		Random r = new Random();
		Item weapon = null;
		switch (r.nextInt(4)) {
		case 0:
			weapon = TGuns.revolver;
			break;
		case 1:
			weapon = TGuns.thompson;
			break;
		case 2:
			weapon = Items.iron_shovel;
			break;
		case 3:
		default:
			weapon = Items.stone_shovel;
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
		float y = this.rand.nextFloat();
		switch (x){
			case 0:
				return TGItems.newStack(TGItems.heavyCloth, 1);
			case 1:
				return new ItemStack(Items.iron_ingot,1);
			case 2:
				return new ItemStack(Items.gunpowder,1);
			case 3:
				return TGItems.newStack(TGItems.shotgunShell,1+Math.round(7*y));
			case 4:
				return TGItems.newStack(TGItems.bullets9mm,1+Math.round(2*y));
		}
		return null;
	}

	protected ItemStack reducedLoottable(){
		int x = this.rand.nextInt(11);
		float y = this.rand.nextFloat();
		switch (x){
			case 0:
				return TGItems.newStack(TGItems.heavyCloth, 1);
			case 1:
				return new ItemStack(Items.gunpowder,1);
			case 3:
			case 4:
				return new ItemStack(Items.rotten_flesh,1);
			case 5:
				return TGItems.newStack(TGItems.shotgunShell,1+Math.round(7*y));
			case 6:
				return TGItems.newStack(TGItems.bullets9mm,1+Math.round(2*y));
			default:
				return null;
		}
		//return null;
	}
}
