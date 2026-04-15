package techguns.damagesystem;

import net.minecraft.util.DamageSource;

public interface IToolTGDamageSystem {
	public TGDamageSource getDamageSource(DamageSource original);
}
