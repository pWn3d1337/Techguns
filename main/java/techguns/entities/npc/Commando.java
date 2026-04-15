package techguns.entities.npc;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.TGArmors;
import techguns.TGBlocks;
import techguns.TGItems;
import techguns.TGuns;

public class Commando extends GenericNPC {

	public Commando(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(75.0D);
	}

	@Override
	protected void addRandomArmor(int difficulty) {

		// Armors
				this.setCurrentItemOrArmor(4,
						new ItemStack(TGArmors.t2_commando_Helmet));
			this.setCurrentItemOrArmor(3, new ItemStack(
					TGArmors.t2_commando_Chestplate));
			this.setCurrentItemOrArmor(2, new ItemStack(
					TGArmors.t2_commando_Leggings));
			this.setCurrentItemOrArmor(1, new ItemStack(TGArmors.t2_commando_Boots));

		// Weapons

		this.setCurrentItemOrArmor(0, new ItemStack(TGuns.m4_uq));

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
		int x = this.rand.nextInt(10);
		float y = this.rand.nextFloat();
		switch (x){
			case 0:
				return TGItems.newStack(TGItems.ingotObsidianSteel,1);
			case 2:
			case 3:
				return TGItems.newStack(TGItems.rubberBar,1);
			case 4:
			case 5:
				return new ItemStack(Items.gunpowder,1);
			case 6:
			case 7:
				return TGItems.newStack(TGItems.rifleBullets,1+Math.round(2*y));
			case 8:
				return new ItemStack(TGBlocks.basicMachine,1,6);
			case 9:
				return new ItemStack(TGBlocks.basicMachine,1,8);
			}
		return null;
	}

	
}

