package techguns.entities.npc;

import java.util.Random;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.TGArmors;
import techguns.TGConfig;
import techguns.TGItems;
import techguns.TGuns;

public class Bandit extends GenericNPC {

	public Bandit(World world) {
		super(world);
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
	}

	@Override
	protected void addRandomArmor(int difficulty) {

		// Armors

		//this.setCurrentItemOrArmor(4, new ItemStack(TGArmors.t1_scout_Helmet));
		this.setCurrentItemOrArmor(3, new ItemStack(TGArmors.t1_scout_Chestplate));
		this.setCurrentItemOrArmor(2, new ItemStack(TGArmors.t1_scout_Leggings));
		this.setCurrentItemOrArmor(1, new ItemStack(TGArmors.t1_scout_Boots));

		// Armors
		double chance = 0.5;
		if (Math.random() <= chance)
			this.setCurrentItemOrArmor(4,
					new ItemStack(TGArmors.t1_scout_Helmet));
		/*if (Math.random() <= chance)
			this.setCurrentItemOrArmor(3, new ItemStack(
					TGArmors.t1_scout_Chestplate));
		if (Math.random() <= chance)
			this.setCurrentItemOrArmor(2, new ItemStack(
					TGArmors.t1_scout_Leggings));
		if (Math.random() <= chance)
			this.setCurrentItemOrArmor(1, new ItemStack(TGArmors.t1_scout_Boots));*/

		
		// Weapons
		Random r = new Random();
		Item weapon = null;
		switch (r.nextInt(6)) {
		case 0:
			weapon = TGuns.pistol;
			break;
		case 1:
			weapon = TGuns.ak47;
			break;
		case 2:
			weapon = TGuns.boltaction;
			break;
		case 3:
			weapon = TGuns.sawedoff;
			break;
		case 4:
			weapon = TGuns.thompson;
			break;
		case 5:
			weapon = TGuns.revolver;
			break;
		default:
			weapon = TGuns.pistol;
			break;
		}
		if (weapon != null) this.setCurrentItemOrArmor(0, new ItemStack(weapon));

	}
	
	@Override
	protected ItemStack getRandomItemFromLoottable() {
		if (TGConfig.reducedLoottables){
			return this.reducedLoottable();
		}
		
		int x = this.rand.nextInt(6);
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
			case 5:
				return TGItems.newStack(TGItems.bullets9mm,1+Math.round(3*y));
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
			case 2:
				return TGItems.newStack(TGItems.shotgunShell,1+Math.round(7*y));
			case 3:
				return TGItems.newStack(TGItems.rifleBullets,1+Math.round(2*y));
			case 4:
				return TGItems.newStack(TGItems.bullets9mm,1+Math.round(3*y));
			default:
				return null;
		}
		//return null;
	}
}
