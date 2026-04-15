package techguns.util;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCoords {
	public int x;
	public int y;
	public int z;
	
	/**
	 * cloning constructor
	 * @param coords
	 */
	public BlockCoords(BlockCoords coords){
		this.x=coords.x;
		this.y=coords.y;
		this.z=coords.z;
	}

	/**
	 * Create BlockCoords at the position of the given tile entity
	 * @param tile
	 */
	public BlockCoords(TileEntity tile){
		this.x=tile.xCoord;
		this.y=tile.yCoord;
		this.z=tile.zCoord;
	}
	
	public BlockCoords(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}

	public void shift(ForgeDirection dir, int amount){
		switch(dir){
			case DOWN:
				y-=amount;
				break;
			case UP:
				y+=amount;
				break;
			case NORTH:
				z-=amount;
				break;
			case SOUTH:
				z+=amount;
				break;
			case EAST:
				x+=amount;
				break;
			case WEST:
				x-=amount;
				break;
			case UNKNOWN:
				break;
			default:
				break;
		}
	}
	
	/**
	 * 
	 * @param minX - should be negative, offset in -x axis
	 * @param maxX - offset in x axis
	 * @param minY - should be negative, offset in -y axis
	 * @param maxY - offset in y axis
	 * @param minZ - should be negative, offset in -z axis
	 * @param maxZ - offset in z axis
	 * @param addOrigin - add the Starting position?
	 * @return - ArrayList of Positions in Box
	 */
	public ArrayList<BlockCoords> getBoxWithOffsets(int minX, int maxX, int minY, int maxY, int minZ, int maxZ, boolean addOrigin){
		ArrayList<BlockCoords> box = new ArrayList<BlockCoords>();
		
		for (int x = this.x+minX; x <= this.x+maxX; x++){
			for (int y = this.y+minY; y <= this.y+maxY; y++){
				for (int z = this.z+minZ; z <= this.z+maxZ; z++){
					//System.out.println("XYZ:"+x+","+y+","+z);
					BlockCoords pos = new BlockCoords(x,y,z);
					
					if(addOrigin || !pos.equals(this)){
						box.add(pos);
					}
				}	
			}	
		}
		
		return box;
	}
	
	/**
	 * Get a list of Positions forming a cuboid in a direction
	 * @param direction the Direction
	 * @param radius 0: 1Block line, 1: 3x3, 2:5x5 .....
	 * @param length how many blocks the cuboid should extend in given direction
	 * @param round? only works for 5x5 properly , leaves out the corners
	 * @return
	 */
	public ArrayList<BlockCoords> getCuboid(ForgeDirection direction, int radius, int length, boolean round){
		ArrayList<BlockCoords> cuboid = new ArrayList<BlockCoords>();
		
		int dir;
		if(direction==ForgeDirection.UP || direction==ForgeDirection.DOWN){
			dir = direction ==ForgeDirection.UP ? 1 : -1; 
			
			BlockCoords pos;
			
			for(int y = 0; y<length;y++){
				for(int x = -radius; x <= radius; x++){
					for(int z = -radius; z <= radius; z++){
						
						if(!round || !( MathUtil.abs(x) == radius &&  MathUtil.abs(z)==radius)){
							pos = new BlockCoords(this.x+x,this.y+(y*dir),this.z+z);
							cuboid.add(pos);
						}
					}
				}
			}
			
		}
		
		else if (direction==ForgeDirection.NORTH || direction==ForgeDirection.SOUTH) {
			dir = direction ==ForgeDirection.SOUTH ? 1 : -1; 
			
			BlockCoords pos;
			for(int z = 0; z<length;z++){
				for(int x = -radius; x <= radius; x++){
					for(int y = -radius; y <= radius; y++){
						
						if(!round ||!( MathUtil.abs(x) == radius &&  MathUtil.abs(y)==radius)){
							pos = new BlockCoords(this.x+x,this.y+y,this.z+(z*dir));
							cuboid.add(pos);
						}
					}
				}
			}
			
		}
		
		else if (direction==ForgeDirection.EAST || direction==ForgeDirection.WEST) {
			dir = direction ==ForgeDirection.EAST ? 1 : -1; 
			
			BlockCoords pos;
			for(int x = 0; x<length;x++){
				for(int z = -radius; z <= radius; z++){
					for(int y = -radius; y <= radius; y++){
						if(!round || !( MathUtil.abs(z) == radius &&  MathUtil.abs(y)==radius)){
							pos = new BlockCoords(this.x+(x*dir),this.y+y,this.z+z);
							cuboid.add(pos);
						}
					}
				}
			}
			
		}
		
		
		return cuboid;
	}
	
	
	public BlockCoords getShifted(ForgeDirection dir, int amount){
		BlockCoords newPos = new BlockCoords(this.x,this.y,this.z);
		newPos.shift(dir, amount);
		return newPos;
	}
	
	@Override
	public String toString() {
		return "BlockCoords:("+this.x+","+this.y+","+this.z+")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj!=null && obj instanceof BlockCoords){
			BlockCoords other= (BlockCoords) obj;
			return this.x==other.x && this.y==other.y && this.z==other.z;
		} else {
			return false;
		}
	}
	
}
