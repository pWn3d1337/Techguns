package techguns.blocks.ctm;

import net.minecraft.block.Block;

import com.cricketcraft.chisel.api.carving.ICarvingVariation;

public class CarvingVariation implements ICarvingVariation{

	private Block block;
	private int blockMeta;
	private int itemMeta;
	private int order;
	
	public CarvingVariation(Block block, int blockMeta, int itemMeta, int order) {
		super();
		this.block = block;
		this.blockMeta = blockMeta;
		this.itemMeta = itemMeta;
		this.order = order;
	}
	
	public CarvingVariation(Block block, int blockMeta) {
		this(block, blockMeta, blockMeta, blockMeta);
	}

	@Override
	public Block getBlock() {
		return block;
	}

	@Override
	public int getBlockMeta() {
		return blockMeta;
	}

	@Override
	public int getItemMeta() {
		return itemMeta;
	}

	@Override
	public int getOrder() {
		return order;
	}

}
