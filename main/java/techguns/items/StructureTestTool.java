package techguns.items;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.util.BlockUtils;
import techguns.util.BlockUtils.BiomeColorType;
import techguns.util.MBlock;
import techguns.util.TextUtil;
import techguns.worldgen.AlienBugNest;
import techguns.worldgen.GenericCity;
import techguns.worldgen.structures.AircraftCarrierNew;
import techguns.worldgen.structures.BigBunker;
import techguns.worldgen.structures.CityBuilding;
import techguns.worldgen.structures.CityBuildingType;
import techguns.worldgen.structures.CityGasStation;
import techguns.worldgen.structures.SmallTrainstation;
import techguns.worldgen.structures.Structure;
import techguns.worldgen.structures.Submarine;

public class StructureTestTool extends GenericItem{
	
	static String[] modes = new String[]{"Structure", "Camp", "Smooth", "Flatten", "Remove Junk", "Sphere", "Cylinder"};
	
	public StructureTestTool() {
		setMaxStackSize(1);
	}

	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if (!item.hasTagCompound()) {
			item.setTagCompound(new NBTTagCompound());
		}
		if (player.isSneaking()) {
			int mode = 1;
			if (item.getTagCompound().hasKey("mode")) {
				mode = (item.getTagCompound().getInteger("mode")+1) % modes.length;
			}
			item.getTagCompound().setInteger("mode", mode);
			if (world.isRemote) player.addChatMessage(new ChatComponentText("structure tool mode set to \""+modes[mode]+"\"."));
		}else {
			if (item.getTagCompound().hasKey("x1") && item.getTagCompound().hasKey("y1") && item.getTagCompound().hasKey("z1")) {
				
				if (world.isRemote) player.addChatMessage(new ChatComponentText("Position2 set ("+x+"/"+y+"/"+z+")."));
				int x1 = item.getTagCompound().getInteger("x1");
				int y1 = item.getTagCompound().getInteger("y1");
				int z1 = item.getTagCompound().getInteger("z1");
				int sizeX = Math.abs(x1-x)+1;
				int sizeY = Math.abs(y1-y)+1;
				int sizeZ = Math.abs(z1-z)+1;
				
				if (world.isRemote) player.addChatMessage(new ChatComponentText("Size: ("+sizeX+"/"+sizeY+"/"+sizeZ+")."));
				
				if (!world.isRemote) {
					int mode = 0;
					if (item.getTagCompound().hasKey("mode")) {
						mode = item.getTagCompound().getInteger("mode");
					}
					switch (mode) {
					case 6: //Cylinder
						BlockUtils.fillCylinder(world, x1, y1, z1, x, y, z, 3.0f, new MBlock(TGBlocks.airMarker,0), new MBlock(Blocks.glass,0));
						break;
					default:
						doOperation(world, Math.min(x, x1), Math.min(y, y1), Math.min(z, z1), sizeX, sizeY, sizeZ, mode);
						break;
					}				
					//placeTestStructure(world, x, y, z);
				}
	
				item.getTagCompound().removeTag("x1");
				item.getTagCompound().removeTag("y1");
				item.getTagCompound().removeTag("z1");
				
			}else {
				item.getTagCompound().setInteger("x1",x);
				item.getTagCompound().setInteger("y1",y);
				item.getTagCompound().setInteger("z1",z);
				if (world.isRemote) player.addChatMessage(new ChatComponentText("Position1 set ("+x+"/"+y+"/"+z+")."));
			}
		}
		return true;
    }
	
	
	private void doOperation(World world, int x, int y, int z, int sizeX, int sizeY, int sizeZ, int mode) {
		
		switch (mode) {
		case 0: //structure
			Random r = new Random();
			//Structure s = new FactoryHouseSmall(8,0,7,9,5,10);
			//Structure s = new HouseMedium(16, 12, 16, 16, 12, 16);
			
			//Structure s = new SmallHouse(sizeX, 5, sizeZ, sizeX, 5, sizeZ);
			//Structure s = new CityBuilding(CityBuildingType.building2, 0, 0, 0, 0, 0, 0);
			
			//Structure s = new CityGasStation();
			
			//Structure s = new Submarine();
			
			//Structure s = new SmallTrainstation(0, 0, 0, 0, 0, 0);
			
			//Structure s = new BigBunker(0,0,0,0,0,0);
			//s.setBlocks(world, x, y, z, sizeX, 12, sizeZ, r.nextInt(4), BiomeColorType.WOODLAND, r);
			
			AircraftCarrierNew s = new AircraftCarrierNew(0,0,0,0,0,0);
			s.setBlocks(world, x, y, z, sizeX, 24, sizeZ, r.nextInt(4), BiomeColorType.WOODLAND,r);
			System.out.println("Origin:"+x+","+y+","+z);
			
			//s.setBlocks(world, x, y-10, z, sizeX, 12, sizeZ, r.nextInt(4), BiomeColorType.WOODLAND);
			break;
		//case 1: //camp
			//MilitaryCamp camp = new MilitaryCamp(3,world);
		    //camp.init(x, y, z, sizeX, sizeZ);
		    //camp.setBlocks(world);
			//break;
		case 1: //city
			GenericCity.testCity.createCity(world, x, y, z, sizeX, sizeY, sizeZ, null, new Random());
			break;
	//	case 1: //Bug Nest
//			new AlienBugNest(x,y,z,sizeX, sizeY, sizeZ, new Random()).setBlocks(world);
//			break;
		case 2: //Smooth
			BlockUtils.apply2DHeightmapFilter(world, x, z, sizeX, sizeZ, BlockUtils.FILTER_GAUSSIAN_5x5);
			break;
		case 3: //Flatten
			BlockUtils.flattenArea(world, x, z, sizeX, sizeZ, 2);
			break;
		case 4: //remove junk
			BlockUtils.removeJunkInArea(world, x, z, sizeX, sizeZ);
			break;	
		case 5: //Sphare
			float radius = (sizeX+sizeZ)/4.0f;
			BlockUtils.fillSphere2(world, x+(sizeX/2), y+(int)radius+1, z+(sizeZ/2), radius, new MBlock(TGBlocks.airMarker,0), new MBlock(Blocks.glass,0));
			break;
		}
		

		
		
		/*Tanks tank = new Tanks(0,0,0,0,0,0);
		Random r = new Random();
		tank.setBlocks(world, x, y, z, sizeX, 8, sizeZ, r.nextInt(4));*/

//		AircraftCarrier ac = new AircraftCarrier();
//		ac.setBlocks(world, x, y, z, sizeX, sizeY, sizeZ, 0);
	}
	
//	private void placeTestStructure(World world, int posX, int posY, int posZ) {
//		int cntX = 0;
//		int cntY = 0;
//		int cntZ = 0;
//		
//		int rotation = 0;
//				
//		/*
//		 * START STRUCTURE SCAN TOOL CODE
//		 */
//		
//		ArrayList<MBlock> blockList = new ArrayList<MBlock>();
//		int[][] posIndices = new int[][] {};
//		
//		/*
//		 * END STRUCTURE SCAN TOOL CODE
//		 */
//		
//		for (int i = 0; i < posIndices.length; i++) {
//			BlockUtils.setBlockRotated(world, blockList.get(posIndices[i][3]), posX, posY, posZ, posIndices[i][0], posIndices[i][1], posIndices[i][2], cntX, cntY, cntZ, rotation);
//		}

//	}
	
	@Override
	public void addInformation(ItemStack item, EntityPlayer player,
			List list, boolean b) {
		super.addInformation(item, player, list, b);
		list.add(TextUtil.trans("techguns.structureTestTool.Tooltip"));
	}
}
