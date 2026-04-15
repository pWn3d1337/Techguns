package techguns.blocks.multipart;

import net.minecraft.block.Block;
import net.minecraftforge.common.util.ForgeDirection;
import techguns.TGBlocks;
import codechicken.lib.vec.Cuboid6;
import codechicken.multipart.minecraft.McSidedMetaPart;

public class Lamp01Part extends McSidedMetaPart {
	
	public Lamp01Part() {
		super();
	}
	public Lamp01Part(int meta) {
		super(meta);
	}

	@Override
	public Cuboid6 getBounds() {
        return new Cuboid6(0.2d, 0.0d, 0.2d, 0.8d, 0.1d, 0.8d);
	}

	@Override
	public Block getBlock() {
		return TGBlocks.lamp01FMP;
	}

	@Override
	public String getType() {
		return "techguns:lamp01FMP";
	}

	@Override
	public int sideForMeta(int meta) {
		 return ForgeDirection.getOrientation(meta).getOpposite().ordinal();
	}

}
