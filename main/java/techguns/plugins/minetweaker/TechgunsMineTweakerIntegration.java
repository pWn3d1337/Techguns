package techguns.plugins.minetweaker;

import cpw.mods.fml.common.Optional;
import minetweaker.MineTweakerAPI;

@Optional.Interface(iface = "techguns.plugins.minetweaker.ITechgunsMineTweakerIntegration", modid = "MineTweaker3")
public class TechgunsMineTweakerIntegration implements ITechgunsMineTweakerIntegration {

	@Override
	public void init() {
		
		MineTweakerAPI.registerClass(AmmoPressTweaker.class);
		MineTweakerAPI.registerClass(CharginStationTweaker.class);
		MineTweakerAPI.registerClass(MetalPressTweaker.class);
		MineTweakerAPI.registerClass(ChemLabTweaker.class);
		MineTweakerAPI.registerClass(FabricatorTweaker.class);
		MineTweakerAPI.registerClass(ReactionChamberTweaker.class);
		MineTweakerAPI.registerClass(OreClusterTweaker.class);
	}

}
