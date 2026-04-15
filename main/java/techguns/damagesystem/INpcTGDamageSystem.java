package techguns.damagesystem;

public interface INpcTGDamageSystem {
	public float getTotalArmorAgainstType(TGDamageSource dmgsrc);
	public float getPenetrationResistance(TGDamageSource dmgsrc);
}
