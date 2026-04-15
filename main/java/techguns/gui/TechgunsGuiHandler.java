package techguns.gui;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import techguns.blocks.BlockTGChest;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.gui.playerinventory.GuiTGPlayerInventory;
import techguns.gui.playerinventory.TGPlayerContainer;
import techguns.inventory.AmmoPressContainer;
import techguns.inventory.CamoBenchContainer;
import techguns.inventory.ChargingStationContainer;
import techguns.inventory.ChemLabContainer;
import techguns.inventory.ExplosiveChargeContainer;
import techguns.inventory.FabricatorContainer;
import techguns.inventory.MetalPressContainer;
import techguns.inventory.OreDrillContainer;
import techguns.inventory.ReactionChamberContainer;
import techguns.inventory.RepairBenchContainer;
import techguns.inventory.ScrapperContainer;
import techguns.inventory.TGChestContainer;
import techguns.inventory.TurretBaseContainer;
import techguns.tileentities.AmmoPressTileEnt;
import techguns.tileentities.CamoBenchTileEnt;
import techguns.tileentities.ChargingStationTileEnt;
import techguns.tileentities.ChemLabTileEnt;
import techguns.tileentities.ExplosiveChargeTileEnt;
import techguns.tileentities.FabricatorTileEntMaster;
import techguns.tileentities.MetalPressTileEnt;
import techguns.tileentities.OreDrillTileEntMaster;
import techguns.tileentities.ReactionChamberTileEntMaster;
import techguns.tileentities.RepairBenchTileEnt;
import techguns.tileentities.ScrapperTileEnt;
import techguns.tileentities.TGChestTileEnt;
import techguns.tileentities.TurretBaseEnt;
import cpw.mods.fml.common.network.IGuiHandler;

public class TechgunsGuiHandler implements IGuiHandler {
	//public static final short GUI_ID_TGPLAYER_INV=17;
	
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		switch(ID){
			case 0:
				return new AmmoPressContainer(player.inventory,(AmmoPressTileEnt) tileEntity);
			case 1:
				return new MetalPressContainer(player.inventory,(MetalPressTileEnt) tileEntity);
			case 2:
				return new TurretBaseContainer(player.inventory,(TurretBaseEnt) tileEntity);
			case 3:
				return new CamoBenchContainer(player.inventory,(CamoBenchTileEnt) tileEntity);
			case 4:
				return new ChemLabContainer(player.inventory, (ChemLabTileEnt) tileEntity);
			case 5:
				return new RepairBenchContainer(player.inventory, (RepairBenchTileEnt) tileEntity);
			case 6:
				return new ExplosiveChargeContainer(player.inventory, (ExplosiveChargeTileEnt) tileEntity);
			case 9:
				return new ChargingStationContainer(player.inventory, (ChargingStationTileEnt) tileEntity);
			case 10:
				return new ScrapperContainer(player.inventory, (ScrapperTileEnt) tileEntity);
			case 16:
				return new OreDrillContainer(player.inventory, (OreDrillTileEntMaster) tileEntity);
			case 17:
				return new TGPlayerContainer(player, player.inventory, TechgunsExtendedPlayerProperties.get(player).TG_inventory);
			case 18:
				return new ReactionChamberContainer(player.inventory, (ReactionChamberTileEntMaster)tileEntity);
			case 19:
				return new FabricatorContainer(player.inventory, (FabricatorTileEntMaster)tileEntity);
			case 20:
				BlockTGChest block = (BlockTGChest) world.getBlock(x, y, z);
				//TGChestTileEnt tile = (TGChestTileEnt)tileEntity;
				IInventory inv =block.func_149951_m(world, x, y, z);
				if (inv!=null){
					return new TGChestContainer(player,inv);
				}else {
					return null;
				}
			/*default:
				if (ID== TGConfig.GUI_ID_tgplayerInventory) { //17
					
				}
				break;*/
				
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		switch(ID){
			case 0:
				return new GuiAmmoPress(player.inventory,(AmmoPressTileEnt) tileEntity);
			case 1:
				return new GuiMetalPress(player.inventory,(MetalPressTileEnt) tileEntity);
			case 2:
				return new GuiTurretBase(player.inventory,(TurretBaseEnt) tileEntity);
			case 3:
				return new GuiCamoBench(player.inventory,(CamoBenchTileEnt) tileEntity);
			case 4:
				return new GuiChemLab(player.inventory, (ChemLabTileEnt) tileEntity);
			case 5:
				return new GuiRepairBench(player.inventory, (RepairBenchTileEnt) tileEntity);
			case 6:
				return new GuiExplosiveCharge(player.inventory, (ExplosiveChargeTileEnt) tileEntity);
			case 9:
				return new GuiChargingStation(player.inventory, (ChargingStationTileEnt) tileEntity);
			case 10:
				return new GuiScrapper(player.inventory,(ScrapperTileEnt) tileEntity);
			case 16:
				return new GuiOreDrill(player.inventory, (OreDrillTileEntMaster) tileEntity);
			case 17:
				return new GuiTGPlayerInventory(player, player.inventory, TechgunsExtendedPlayerProperties.get(player).TG_inventory);
			case 18:
				return new GuiReactionChamber(player.inventory, (ReactionChamberTileEntMaster) tileEntity);
			case 19:
				return new GuiFabricator(player.inventory, (FabricatorTileEntMaster) tileEntity);
			case 20:
				BlockTGChest block = (BlockTGChest) world.getBlock(x, y, z);
				IInventory inv = block.func_149951_m(world, x, y, z);
				if (inv!=null){
					return new GuiTechgunsChest(player,(TGChestTileEnt)tileEntity, inv);
				} else {
					return null;
				}
			/*default: //17
				if (ID==TGConfig.GUI_ID_tgplayerInventory){
					return new GuiTGPlayerInventory(player, player.inventory, TechgunsExtendedPlayerProperties.get(player).TG_inventory);
				}
				break;*/
		}
		
		return null;
	}

}
