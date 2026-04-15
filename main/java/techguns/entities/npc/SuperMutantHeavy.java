package techguns.entities.npc;

import techguns.damagesystem.TGDamageSource;
import techguns.debug.Keybinds;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class SuperMutantHeavy extends SuperMutantBasic {

	public SuperMutantHeavy(World world) {
		super(world);
	}

	public double getModelHeightOffset(){
		return 0.75d;
	}
	
	public float getModelScale() {
		return 1.5f;
	}
	
	@Override
	public float getWeaponPosY() {
		return 2.3f;//+(float)Keybinds.Y;
	}
	
	@Override
	public float getWeaponPosX() {
		return -0.2f; //+(float)Keybinds.X;
	}
	
	@Override
	public float getTotalArmorAgainstType(TGDamageSource dmgsrc) {
		switch(dmgsrc.damageType){
			case EXPLOSION:
			case LIGHTNING:
			case ENERGY:
			case FIRE:
			case ICE:
				return 16.0f;
			case PHYSICAL:
			case PROJECTILE:
				return 12.0f;
			case POISON:
			case RADIATION:
				return 18.0f;
			case UNRESISTABLE:
		default:
			return 0.0f;
		}
	}

	@Override
	public int getTotalArmorValue() {
		return 12;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(9);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(50.0D);
	}
	
}
