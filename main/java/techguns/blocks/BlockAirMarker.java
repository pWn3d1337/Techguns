package techguns.blocks;

import net.minecraft.block.material.Material;

/**
 * Dummy block for building scanning, used do define AIR
 *
 */
public class BlockAirMarker extends GenericBlock {

	public BlockAirMarker() {
		super(Material.cloth);
		this.blockHardness=1.0f;
		setBlockName("AirMarker");
		setStepSound(soundTypeCloth);
		setBlockTextureName("techguns:airMarker");
		this.useNeighborBrightness=true;
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	
	
}
