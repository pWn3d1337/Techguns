package techguns.worldgen.structures;

import net.minecraft.world.World;
import techguns.util.BlockUtils.BiomeColorType;

public interface IRoad {
	
	public void setRoadBlocks(World world, int x, int y, int z, int roadWidth, int roadLength, int heightDif, boolean alignment, BiomeColorType colorType);

}
