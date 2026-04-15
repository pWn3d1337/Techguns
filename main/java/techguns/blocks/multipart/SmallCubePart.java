package techguns.blocks.multipart;

import net.minecraft.block.Block;
import techguns.TGBlocks;
import codechicken.lib.vec.Cuboid6;
import codechicken.multipart.minecraft.McMetaPart;

public class SmallCubePart extends McMetaPart {

	@Override
	public String getType() {
		return "techguns:smallCube";
	}

	@Override
	public Cuboid6 getBounds() {
		return new Cuboid6(0.2,0.2,0.2, 0.8,0.8,0.8);
	}

	@Override
	public Block getBlock() {
		return TGBlocks.smallCube;
	}

}
