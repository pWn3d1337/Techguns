package techguns.damagesystem;

import techguns.util.TextUtil;

import com.mojang.realmsclient.gui.ChatFormatting;

public enum DamageType {
	PHYSICAL, //Melee hits
	PROJECTILE, //Arrows, bullets,..
	FIRE, //fire, flamethrower
	EXPLOSION, // Explosions like TNT, Rockets
	ENERGY, // Energy Weapons, Lasers, Magic
	POISON, //gas, slimy stuff
	UNRESISTABLE, //Things that should never get reduced with armor value: starve, falldmg, suffocate...
	ICE,
	LIGHTNING,
	RADIATION;

	@Override
	public String toString() {
		switch(this){
			
			case PROJECTILE:
				return ChatFormatting.GRAY+TextUtil.trans("techguns.TGDamageType.PROJECTILE");
			case FIRE:
				return ChatFormatting.RED+TextUtil.trans("techguns.TGDamageType.FIRE");
			case EXPLOSION:
				return ChatFormatting.DARK_RED+TextUtil.trans("techguns.TGDamageType.EXPLOSION");
			case ENERGY:
				return ChatFormatting.DARK_AQUA+TextUtil.trans("techguns.TGDamageType.ENERGY");
			case POISON:
				return ChatFormatting.DARK_GREEN+TextUtil.trans("techguns.TGDamageType.POISON");
			case UNRESISTABLE:
				return ChatFormatting.BLACK+TextUtil.trans("techguns.TGDamageType.UNRESISTABLE");
			case ICE:
				return ChatFormatting.AQUA+TextUtil.trans("techguns.TGDamageType.ICE");
			case LIGHTNING:
				return ChatFormatting.YELLOW+TextUtil.trans("techguns.TGDamageType.LIGHTNING");
			case RADIATION:
				return ChatFormatting.GREEN+TextUtil.trans("techguns.TGDamageType.RADIATION");
			case PHYSICAL:
			default:
				return ChatFormatting.DARK_GRAY+TextUtil.trans("techguns.TGDamageType.PHYSICAL");
		}
	}
	
	
}
