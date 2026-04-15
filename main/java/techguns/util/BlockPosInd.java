package techguns.util;

/**
 * 
 * Used for scanning and placing structures
 */
public class BlockPosInd extends BlockCoords {
	int index; //index in corresponding (M)Blocklist
	
	public BlockPosInd(int x, int y, int z, int index) {
		super(x, y, z);
		this.index=index;
	}

	@Override
	public String toString() {
		return x+","+y+","+z+","+index;
	}
}
