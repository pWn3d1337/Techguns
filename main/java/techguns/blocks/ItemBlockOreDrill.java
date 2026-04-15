package techguns.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.util.BlockCoords;
import techguns.util.TextUtil;

public class ItemBlockOreDrill extends GenericItemBlockMetadataStringPrefix {

	public ItemBlockOreDrill(Block block) {
		super(block);
	}

	public static boolean isOreCluster(World w, int x, int y, int z){
		
		for (int i=0;i<TGBlocks.oreclusters.size(); i++){
			if (isBlock(w, x, y, z, TGBlocks.oreclusters.get(i), -1)){
				return true;
			}
		}
		return false;
		//return isBlock(w, x, y, z, TGBlocks.oreCluster, -1) || isBlock(w,x,y,z, TGBlocks.oreCluster2,-1);
	}
	
	public static boolean isBlock(World w, int x, int y, int z, Block block, int meta){
		Block b = w.getBlock(x, y, z);
		if(Block.getIdFromBlock(b)==Block.getIdFromBlock(block)){
			if (meta != -1){
				int m = w.getBlockMetadata(x, y, z);
				
				boolean valid = (m==meta);
				
				/*if(!valid){
					System.out.println("Block not valid, got "+Block.getIdFromBlock(b)+":"+m +" but expected: "+Block.getIdFromBlock(block)+":"+meta + " at "+x+", "+y+","+z);
				}*/
				
				return valid;
			} else {
				return true;
			}
		} else {
			//System.out.println("Block not valid, got "+Block.getIdFromBlock(b)+" but expected: "+Block.getIdFromBlock(block)+" at "+x+", "+y+","+z);
			return false;
		}
	}
	
	
	/*private boolean checkForBlockInNeighbourhood(World w, int x, int y, int z, Block b, int m){
		return isBlock(w,x+1,y,z,b,m) || isBlock(w,x-1,y,z,b,m) || isBlock(w,x,y+1,z,b,m) || isBlock(w,x,y-1,z,b,m) || isBlock(w,x,y,z+1,b,m) || isBlock(w,x+1,y,z-1,b,m);
	}*/
	
	public static int[] checkForBlockInNeighbourhood(World w, int x, int y, int z, Block b, int m){
		int[] ret = new int[]{0,0,0};
		if(isBlock(w,x,y+1,z,b,m)){
			ret[1]=1;
		} else if (isBlock(w,x,y-1,z,b,m)){
			ret[1]=-1;
		} else if (isBlock(w,x,y,z-1,b,m)){
			ret[2]=-1;
		} else if (isBlock(w,x+1,y,z,b,m)){
			ret[0]=+1;
		} else if (isBlock(w,x,y,z+1,b,m)){
			ret[2]=+1;
		} else if (isBlock(w,x-1,y,z,b,m)){
			ret[0]=-1;
		} else {
			return null;
		}
		return ret;
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ, int metadata) {
		
		int ROD_META=2;
		int CONTROLLER_META=4;
		if (metadata==ROD_META){
			
			//System.out.println("check for Cluster");
			
			//Check for Ore Cluster
			int[] offset = null;
			for (OreCluster clust: TGBlocks.oreclusters){
				offset= checkForBlockInNeighbourhood(world, x, y, z, clust, -1);
				if (offset!=null){
					break;
				}
			}
			
			if (offset !=null){
				
				Block cluster = world.getBlock(x+offset[0], y+offset[1], z+offset[2]);
				int meta = world.getBlockMetadata(x+offset[0], y+offset[1], z+offset[2]);
				//Check if cluster has a connected rod already
				
				if (getHasConnectedDrillRod(world, x+offset[0], y+offset[1], z+offset[2], new ArrayList<BlockCoords>(), cluster, meta, TGBlocks.oreDrill, ROD_META)){
					//System.out.println("Cluster has already Rod , can't place");
					if(world.isRemote){
						player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.msg.error.alreadyrod")));
					}
					return false;
				} else {
					return super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
				}

			} else {
			
				//Check for other rod neighbour
				//System.out.println("No cluster, check for rod");
				if( checkForBlockInNeighbourhood(world, x, y, z, TGBlocks.oreDrill, ROD_META) !=null ){
						//System.out.println("Neighbouring Rod , can place");
						return super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
							
					} else {
						
						

						//System.out.println("No Rod, can't place");
						return false;
						
					}

			
			}
		} else if (metadata==CONTROLLER_META){
			if( checkForBlockInNeighbourhood(world, x, y, z, TGBlocks.oreDrill, CONTROLLER_META) !=null ){
				if(world.isRemote){
					player.addChatMessage(new ChatComponentText(I18n.format("msg.error.oredrill.controllernext", new Object[0])));
				}
				return false;
			} else {
				return super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
			}

		} else {
			return super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
		}
	}
	
	public boolean getHasConnectedDrillRod(World world,int x, int y, int z, ArrayList<BlockCoords> visited, Block clusterType, int clusterMeta, Block DrillRodBlock, int meta){

		Block target = world.getBlock(x,y,z);
		int targetMeta = world.getBlockMetadata(x, y, z);
		BlockCoords block=new BlockCoords(x,y,z);
		if (!visited.contains(block)){
			if (target == clusterType && targetMeta == clusterMeta) {
				visited.add(block);

				boolean b1 = getHasConnectedDrillRod(world, x+1, y, z, visited, clusterType, clusterMeta, DrillRodBlock, meta);
				boolean b2 = getHasConnectedDrillRod(world, x-1, y, z, visited, clusterType, clusterMeta, DrillRodBlock, meta);
				boolean b3 = getHasConnectedDrillRod(world, x, y+1, z, visited, clusterType, clusterMeta, DrillRodBlock, meta);
				boolean b4 = getHasConnectedDrillRod(world, x, y-1, z, visited, clusterType, clusterMeta, DrillRodBlock, meta);
				boolean b5 = getHasConnectedDrillRod(world, x, y, z+1, visited, clusterType, clusterMeta, DrillRodBlock, meta);
				boolean b6 = getHasConnectedDrillRod(world, x, y, z-1, visited, clusterType, clusterMeta, DrillRodBlock, meta);
				
				return b1||b2||b3||b4||b5||b6;
			} else if(target == DrillRodBlock && targetMeta == meta) {
				
				return true;
				
			} else {
				//stop recursion, no cluster
				return false;
			}
		} else {
			return false;
		}
	}
	
}
