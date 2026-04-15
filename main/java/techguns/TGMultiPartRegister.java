package techguns;


public class TGMultiPartRegister /*implements IPartFactory,IPartConverter*/{

	/*public void init(){
		 MultiPartRegistry.registerConverter(this);
	     MultiPartRegistry.registerParts(this, new String[]{"techguns:smallCube","techguns:lamp01FMP"});
	}
	
	@Override
	public Iterable<Block> blockTypes() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		blocks.add(TGBlocks.smallCube);
		blocks.add(TGBlocks.lamp01FMP);
		return blocks;
	}


	@Override
	public TMultiPart createPart(String name, boolean client) {
		if(name.equals("techguns:smallCube")){
			return new SmallCubePart();
		} else if(name.equals("techguns:lamp01FMP")){
			return new Lamp01Part();
		}
		return null;
	}


	@Override
	public TMultiPart convert(World world, codechicken.lib.vec.BlockCoord pos) {
		Block block = world.getBlock(pos.x, pos.y, pos.z);
		int meta = world.getBlockMetadata(pos.x, pos.y, pos.z);
		if (block == TGBlocks.smallCube){
			return new SmallCubePart();
		} else if (block == TGBlocks.lamp01FMP){
			return new Lamp01Part(meta);
		}
		return null;
	}
*/

}
