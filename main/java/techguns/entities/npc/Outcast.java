package techguns.entities.npc;

import java.util.Random;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.TGArmors;
import techguns.TGuns;
import techguns.items.armors.GenericArmorMultiCamo;

public class Outcast extends GenericNPC {

	public Outcast(World world) {
		super(world);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(60.0D);
	}

	@Override
	protected void addRandomArmor(int difficulty) {

		Random r = new Random();
		// Armors

		int camo=r.nextInt(2);
		this.setCurrentItemOrArmor(4, GenericArmorMultiCamo.getNewWithCamo(TGArmors.t3_power_Helmet,camo+1));
		camo=r.nextInt(2);
		this.setCurrentItemOrArmor(3, GenericArmorMultiCamo.getNewWithCamo(TGArmors.t3_power_Chestplate,camo+1));
		camo=r.nextInt(2);
		this.setCurrentItemOrArmor(2, GenericArmorMultiCamo.getNewWithCamo(TGArmors.t3_power_Leggings,camo+1));
		camo=r.nextInt(2);
		this.setCurrentItemOrArmor(1, GenericArmorMultiCamo.getNewWithCamo(TGArmors.t3_power_Boots,camo+1));
		

		// Weapons

		Item weapon = null;
		switch (r.nextInt(7)) {
		case 0:
			weapon = TGuns.lasergun;
			break;
		case 1:
			weapon = TGuns.grimreaper;
			break;
		case 2:
			weapon = TGuns.minigun;
			break;
		case 3:
			weapon = TGuns.biogun;
			break;
		case 4:
			weapon = TGuns.flamethrower;
			break;
		case 5:
			weapon = TGuns.teslagun;
			break;
		case 6:
			weapon = TGuns.lmg;
			break;
		default:
			weapon = TGuns.lasergun;
			break;
		}
		if (weapon != null) this.setCurrentItemOrArmor(0, new ItemStack(weapon));
	}
}
