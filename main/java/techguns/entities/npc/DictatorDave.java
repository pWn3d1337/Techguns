package techguns.entities.npc;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.TGArmors;
import techguns.TGItems;
import techguns.TGuns;
import techguns.items.armors.GenericArmorMultiCamo;

public class DictatorDave extends GenericNPC {
	
	public DictatorDave(World world) {
		super(world);
		this.experienceValue = 35;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.40D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(60.0D);
	}

	@Override
	protected void addRandomArmor(int difficulty) {

		this.setCurrentItemOrArmor(4, GenericArmorMultiCamo.getNewWithCamo(
			TGArmors.t2_beret,0));
		this.setCurrentItemOrArmor(3, new ItemStack(TGArmors.t1_combat_Chestplate));
		this.setCurrentItemOrArmor(2, GenericArmorMultiCamo.getNewWithCamo(
				TGArmors.t1_scout_Leggings,2));
		this.setCurrentItemOrArmor(1, new ItemStack(TGArmors.t1_combat_Boots));

		this.setCurrentItemOrArmor(0, new ItemStack(TGuns.goldenrevolver));

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
  		this.entityDropItem(new ItemStack(TGuns.goldenrevolver,1), 0.0f);
  		this.entityDropItem(TGItems.newStack(TGItems.bullets9mm, 8+this.rand.nextInt(9)), 0.0f);
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
