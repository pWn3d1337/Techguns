package techguns.worldgen.structures;

import java.util.Random;

import net.minecraft.world.World;
import techguns.util.BlockUtils.BiomeColorType;

public class CityBuilding extends Structure{
	
	CityBuildingType buildingType;
	
	public CityBuilding(CityBuildingType buildingType, int minX, int minY, int minZ, int maxX, int maxY,
			int maxZ) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		this.buildingType = buildingType;	
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		buildingType.setBlocks(world, posX, posY, posZ, sizeX, sizeY, sizeZ, direction, colorType);
	}		
			
}
