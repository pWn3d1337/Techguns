package techguns.entities.npc;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.TGArmors;
import techguns.TGItems;
import techguns.TGuns;
import techguns.items.armors.GenericArmorMultiCamo;

public class PsychoSteve extends GenericNPC implements IBossDisplayData {

	public PsychoSteve(World world) {
		super(world);
		this.experienceValue = 25;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.60D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(75);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(60.0D);
	}

	@Override
	protected void addRandomArmor(int difficulty) {
		int camo = 1; //GenericArmorMultiCamo.getRandomCamoIndexFor((GenericArmorMultiCamo) TGArmors.t1_miner_Chestplate);

		this.setCurrentItemOrArmor(4, GenericArmorMultiCamo.getNewWithCamo(
			TGArmors.t1_miner_Helmet,camo));
		this.setCurrentItemOrArmor(3, GenericArmorMultiCamo.getNewWithCamo(
				TGArmors.t1_miner_Chestplate,camo));
		this.setCurrentItemOrArmor(2, GenericArmorMultiCamo.getNewWithCamo(
				TGArmors.t1_miner_Leggings,camo));
		this.setCurrentItemOrArmor(1, GenericArmorMultiCamo.getNewWithCamo(
				TGArmors.t1_miner_Boots,camo));

		this.setCurrentItemOrArmor(0, new ItemStack(TGuns.chainsaw));

	}
	
	

    @Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		//BossStatus.setBossStatus(this, true);
	}
    
    @Override
	protected void dropRareDrop(int i1) {
	}

	@Override
    protected void dropFewItems(boolean hitByPlayer, int level)
    {
  		this.entityDropItem(new ItemStack(TGuns.chainsaw,1), 0.0f);
  		this.entityDropItem(TGItems.newStack(TGItems.flamethrowerTank, 4+this.rand.nextInt(5)), 0.0f);
    }
    
	/**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.villager.idle";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.villager.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
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
		this.playSound("mob.zombie.step", 0.15F, 1.0F);
	}
}
