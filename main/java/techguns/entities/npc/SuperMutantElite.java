package techguns.entities.npc;

import techguns.damagesystem.TGDamageSource;
import techguns.debug.Keybinds;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class SuperMutantElite extends SuperMutantBasic {

	public SuperMutantElite(World world) {
		super(world);
	}

	public double getModelHeightOffset(){
		return 0.95d;
	}
	
	public float getModelScale() {
		return 1.65f;
	}
	
	@Override
	public float getWeaponPosY() {
		return 2.4f;//+(float)Keybinds.Y;
	}
	
	@Override
	public float getWeaponPosX() {
		return -0.25f;//+(float)Keybinds.X;
	}

	
	@Override
	public float getTotalArmorAgainstType(TGDamageSource dmgsrc) {
		switch(dmgsrc.damageType){
			case EXPLOSION:
			case LIGHTNING:
			case ENERGY:
			case FIRE:
			case ICE:
				return 14.0f;
			case PHYSICAL:
			case PROJECTILE:
				return 10.0f;
			case POISON:
			case RADIATION:
				return 16.0f;
			case UNRESISTABLE:
		default:
			return 0.0f;
		}
	}

	@Override
	public int getTotalArmorValue() {
		return 10;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(11);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(50.0D);
	}
}
