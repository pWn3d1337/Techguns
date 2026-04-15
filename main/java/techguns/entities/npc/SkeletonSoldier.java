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

public class SkeletonSoldier extends GenericNPC {

	public SkeletonSoldier(World world) {
		super(world);
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

		Random r = new Random();
		
		// Armors
		/*double chance = 0.5;
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
		*/
		switch(r.nextInt(2)) {
			case 0:
				 this.setCurrentItemOrArmor(4, new ItemStack(TGArmors.t1_combat_Helmet));
				 break;
			case 1:
				this.setCurrentItemOrArmor(4, new ItemStack(TGArmors.t1_scout_Helmet));
				break;
		}
		switch(r.nextInt(2)) {
			case 0:
				this.setCurrentItemOrArmor(1, new ItemStack(TGArmors.t1_combat_Boots));
				 break;
			case 1:
				this.setCurrentItemOrArmor(1, new ItemStack(TGArmors.t1_scout_Boots));
				break;
		}

		
		// Weapons
	
		Item weapon = null;
		switch (r.nextInt(3)) {
		case 0:
			weapon = TGuns.revolver;
			break;
		case 1:
			weapon = TGuns.thompson;
			break;
		case 2:
			weapon = TGuns.handcannon;
			break;
		case 3:
		default:
			weapon = Items.stone_shovel;
			break;
		}
		if (weapon != null) this.setCurrentItemOrArmor(0, new ItemStack(weapon));

	}

	/**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.skeleton.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.skeleton.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.skeleton.death";
    }

    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
        this.playSound("mob.skeleton.step", 0.15F, 1.0F);
    }
    
    
	@Override
	protected ItemStack getRandomItemFromLoottable() {
		if (TGConfig.reducedLoottables){
			return this.reducedLoottable();
		}
		
		int x = this.rand.nextInt(3);
		float y = this.rand.nextFloat();
		switch (x){
			case 0:
				return new ItemStack(Items.bone,1+Math.round(1*y));
			case 1:
				return new ItemStack(Items.coal,1+Math.round(2*y));
			case 2:
				return new ItemStack(Items.gunpowder,1);
		}
		return null;
	}
	
	protected ItemStack reducedLoottable(){
		int x = this.rand.nextInt(9);
		switch (x){
			case 0:
				return TGItems.newStack(TGItems.heavyCloth, 1);
			case 1:
				return new ItemStack(Items.bone,1);
			case 2:
				return new ItemStack(Items.gunpowder,1);
			default:
				return null;
		}
		//return null;
	}
}
