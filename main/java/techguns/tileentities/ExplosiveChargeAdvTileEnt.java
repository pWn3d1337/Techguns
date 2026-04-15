package techguns.tileentities;

import techguns.TGConfig;

public class ExplosiveChargeAdvTileEnt extends ExplosiveChargeTileEnt {
	
	public ExplosiveChargeAdvTileEnt() {
		this.maxBlastradius=7;
		this.maxBlastLength=10;
		
		this.blastradius=5;
		this.blastlength=10;
		
		this.soundInit="techguns:c4charge.init";
		this.soundTick="techguns:c4charge.tick";
		this.soundPlant="techguns:c4charge.plant";
	}

	@Override
	protected boolean canBreakBlockHardness(float hardness) {
		return hardness >=0 && hardness <= TGConfig.explosiveChargeAdvancedMaxBlockHardness;
	}
	
}
