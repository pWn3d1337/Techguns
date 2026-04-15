package techguns.blocks.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import techguns.TGBlocks;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.packets.PacketTGMessage;
import techguns.tileentities.BasicOwnedTileEnt;
import techguns.tileentities.BasicPoweredTileEnt;
import techguns.tileentities.FabricatorTileEntMaster;
import techguns.tileentities.FabricatorTileEntSlave;
import techguns.tileentities.MultiBlockMasterTileEntBase;
import techguns.tileentities.MultiBlockSlaveTileEntBase;
import techguns.tileentities.ReactionChamberTileEntMaster;
import techguns.tileentities.ReactionChamberTileEntSlave;
import techguns.util.BlockCoords;
import techguns.util.TextUtil;

import com.cricketcraft.chisel.api.ICarvable;
import com.cricketcraft.chisel.api.carving.CarvableHelper;
import com.cricketcraft.chisel.api.carving.IVariationInfo;
import com.cricketcraft.chisel.api.rendering.ClientUtils;

public class MultiBlockMachineBlock extends BlockContainer implements ICarvable {
	//public IIcon[] icons = new IIcon[16];
	public CarvableHelper carverHelper;

	private static byte META_INVISIBLE=15;
	
	/**
	 * Highest Metadata used
	 */
	private static byte MAX_META=5;
	
	public MultiBlockMachineBlock() {
		super(Material.iron);
		this.carverHelper = new CarvableHelper(this);
		setBlockName("machineMultiBlock");
		setStepSound(soundTypeMetal);
		setHarvestLevel("pickaxe", 1);
		setCreativeTab(Techguns.tabTechgun);
		setHardness(8.0f);
		setResistance(50.0f);
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
		return false;
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(this); // new ItemBlockMetaData(this);
	}
	
	@Override
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register) {
		carverHelper.registerBlockIcons(Techguns.MODID, this, register);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
		//carverHelper.registerSubBlocks(this, tabs, list);
		for (IVariationInfo info : carverHelper.infoList) {
			if (info.getVariation().getBlockMeta() <= MAX_META) {
				list.add(new ItemStack(this, 1, info.getVariation().getItemMeta()));
			}
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if (meta==0 || meta==2){
			return new ReactionChamberTileEntSlave();
		} else if (meta==1){
			return new ReactionChamberTileEntMaster();
		} else if (meta==3 || meta==5){
			return new FabricatorTileEntSlave();
		} else if (meta==4){
			return new FabricatorTileEntMaster();
		}
		return null;
	}
	
	@Override
	public int getRenderType() {
		return ClientUtils.renderCTMId;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		super.onNeighborBlockChange(world, x, y, z, block);
		//System.out.println("GotNeightbourChange!");
		
		//world.
		
		/*TileEntity tile = world.getTileEntity(x, y, z);
		if(tile!=null && tile instanceof BasicPoweredTileEnt){
			((BasicPoweredTileEnt)tile).onNeighborBlockChange();		
		}
		if(tile!=null && tile instanceof MultiBlockMasterTileEntBase){
			MultiBlockMasterTileEntBase master = ((MultiBlockMasterTileEntBase)tile);
			if(master.isComplete()){
				master.needUpdate();
			}
		}
		if(tile!=null && tile instanceof MultiBlockSlaveTileEntBase){
			MultiBlockSlaveTileEntBase slave = ((MultiBlockSlaveTileEntBase)tile);
			if(slave.hasMaster()){
				slave.needUpdate();
			}
		}*/
	}

	
	@Override
	public void onBlockPlacedBy(World world, int x,
			int y, int z, EntityLivingBase ent,
			ItemStack item) {
		
		int meta = world.getBlockMetadata(x, y, z);
		
		//if Controller, set owner
		if (meta == 1 || meta == 4){
		TileEntity tile = world.getTileEntity(x, y, z);
			
			if (ent instanceof EntityPlayer && tile instanceof BasicOwnedTileEnt){
				((BasicOwnedTileEnt)tile).setOwner((EntityPlayer)ent);
			}
		}
	}
		
	@Override
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		//System.out.println("Block Break!");
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {

			if (tile instanceof MultiBlockSlaveTileEntBase) {
				MultiBlockSlaveTileEntBase slave = (MultiBlockSlaveTileEntBase) tile;
				slave.notifyMasterMultiblockBreak();
			}
			if(tile instanceof MultiBlockMasterTileEntBase){
				MultiBlockMasterTileEntBase master = (MultiBlockMasterTileEntBase) tile;
				master.notifiyMultiblockBreak();
				master.onBlockBreak();
			}
			
			
			world.removeTileEntity(x, y, z);
		} else {
			//System.out.println("No tile entity");
		}
	}
	
	 @Override
	 public boolean isOpaqueCube(){
		 return false;
	 }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int sideHit, float f1, float f2, float f3) {
		
		TileEntity tile = world.getTileEntity(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		
		//Reaction Chamber
		if (meta==0 || meta==2){
			if (tile instanceof ReactionChamberTileEntSlave){
				ReactionChamberTileEntSlave slave = (ReactionChamberTileEntSlave) tile;
				
				if(slave.hasMaster()){
					
					//debug
					ItemStack stack =player.getCurrentEquippedItem();
					if (stack!=null && stack.getItem() == Techguns.blockReaderTool){
						
						player.addChatMessage(new ChatComponentText("Master:"+slave.hasMaster()));
						player.addChatMessage(new ChatComponentText("Pos:"+slave.getMasterPos()[0]+","+slave.getMasterPos()[1]+","+slave.getMasterPos()[2]));
						player.addChatMessage(new ChatComponentText("Type:"+slave.getType()));
						
					} else {
						
						//Check Master & Open GUI
						//System.out.println("open Master Gui");
						
						//System.out.println("Slave Type:"+slave.getType());
						int[] masterpos = slave.getMasterPos();
						TileEntity mt = world.getTileEntity(masterpos[0], masterpos[1], masterpos[2]);
						if (mt!=null && mt instanceof BasicPoweredTileEnt){
							if (((BasicPoweredTileEnt)mt).isUseableByPlayer(player)){
								player.openGui(Techguns.MODID, 18, world, masterpos[0], masterpos[1], masterpos[2]);
								return true;
							} else {
								if(player.worldObj.isRemote){
									player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.container.security.denied")));
								}
								return true;
							}
						}

					}
					return true;
					
				} else {
					//System.out.println("No Multiblock");
					//do nothing
					return false;
				}
			}
			
		} else if (meta==1){
			if (tile instanceof ReactionChamberTileEntMaster){
				ReactionChamberTileEntMaster master = (ReactionChamberTileEntMaster) tile;
				
				if (master.isComplete()){
					
					//debug
					ItemStack stack =player.getCurrentEquippedItem();
					if (stack!=null && stack.getItem() == Techguns.blockReaderTool){
						
						player.addChatMessage(new ChatComponentText("Complete:"+master.isComplete()));
						player.addChatMessage(new ChatComponentText("Direction:"+master.dir));
						player.addChatMessage(new ChatComponentText("Pos:"+master.xCoord+","+master.yCoord+","+master.zCoord));
						
						
					} else {
					
						//open GUI
						//System.out.println("open Gui");

						if (master.isUseableByPlayer(player)){
							player.openGui(Techguns.MODID, 18, world, x, y, z);
							return true;
						} else {
							if(player.worldObj.isRemote){
								player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.container.security.denied")));
							}
							return true;
						}
						
					}
					return true;
				} else {
					
					//TRY FORM
					BlockCoords pos = new BlockCoords(x,y,z);
					return this.CheckFormReactionChamber(player,pos, sideHit, world, master);
				}
				
			}
			
			
			
		} else if (meta==4){
			if (tile instanceof FabricatorTileEntMaster){
				FabricatorTileEntMaster master = (FabricatorTileEntMaster) tile;
				
				if (master.isComplete()){
					
					//open GUI
					//System.out.println("open Gui");
					player.openGui(Techguns.MODID, 19, world, x, y, z);
					return true;
				} else {
					
					//TRY FORM
					BlockCoords pos = new BlockCoords(x,y,z);
					return this.CheckFormFabricator(player,pos, sideHit, world, master);
				}
				
			}

		} else if (meta==3 || meta==5){
			if (tile instanceof FabricatorTileEntSlave){
				FabricatorTileEntSlave slave = (FabricatorTileEntSlave) tile;
				
				if(slave.hasMaster()){
					
					//Check Master & Open GUI
					int[] masterpos = slave.getMasterPos();
					//System.out.println("open Master Gui");
					player.openGui(Techguns.MODID, 19, world, masterpos[0], masterpos[1], masterpos[2]);
					return true;
				} else {
					return false;
				}
			}
		}
		
		
		return false;
	}

	
	private boolean CheckFormReactionChamber(EntityPlayer player, BlockCoords masterPos, int direction, World w, ReactionChamberTileEntMaster master){
		
		//System.out.println("Direction"+direction);
		
		ArrayList<BlockCoords> positions_block = null;
		ArrayList<BlockCoords> positions_glass = null;
		if(direction==2){
			// +Z;
			positions_block = masterPos.getBoxWithOffsets(-1, +1, 0, 0, 0, 2, false);
			ArrayList<BlockCoords> top = masterPos.getBoxWithOffsets(-1, +1, 3, 3, 0, 2, false);
			positions_block.addAll(top);
			positions_glass = masterPos.getBoxWithOffsets(-1, +1, 1, 2, 0, 2, false);
			
		} else if(direction==3){
			// -Z;
			//positions = masterPos.getBoxWithOffsets(-1, +1, 0, 3, -2, 0, false);
			
			positions_block = masterPos.getBoxWithOffsets(-1, +1, 0, 0, -2, 0, false);
			ArrayList<BlockCoords> top = masterPos.getBoxWithOffsets(-1, +1, 3, 3, -2, 0, false);
			positions_block.addAll(top);
			positions_glass = masterPos.getBoxWithOffsets(-1, +1, 1, 2, -2, 0, false);
			
		} else if (direction==4){
			// +X;
			//positions = masterPos.getBoxWithOffsets(0, 2, 0, 3, -1, +1, false);
			
			positions_block = masterPos.getBoxWithOffsets(0, 2, 0, 0, -1, +1, false);
			ArrayList<BlockCoords> top = masterPos.getBoxWithOffsets(0, 2, 3, 3, -1, +1, false);
			positions_block.addAll(top);
			positions_glass = masterPos.getBoxWithOffsets(0, 2, 1, 2, -1, +1, false);
			
			
		} else if (direction==5){
			// -x;
			//positions = masterPos.getBoxWithOffsets(-2, 0, 0, 3, -1, +1, false);
			
			positions_block = masterPos.getBoxWithOffsets(-2, 0, 0, 0, -1, +1, false);
			ArrayList<BlockCoords> top = masterPos.getBoxWithOffsets(-2, 0, 3, 3, -1, +1, false);
			positions_block.addAll(top);
			positions_glass = masterPos.getBoxWithOffsets(-2, 0, 1, 2, -1, +1, false);
			
		}
		
		boolean valid=false;
		if (positions_block!=null){
			
			valid=true;
			for (int i =0;i<positions_block.size();i++){
				BlockCoords pos = positions_block.get(i);

				Block b = w.getBlock(pos.x, pos.y, pos.z);
				int meta = w.getBlockMetadata(pos.x, pos.y, pos.z);
				
				if (!(b==TGBlocks.machineMultiBlock && meta ==0)){
					//System.out.println("MultiBlock invalid Block at"+pos);
					if (!w.isRemote){
						TGPackets.network.sendTo(new PacketTGMessage((byte) 0,pos.x,pos.y,pos.z), (EntityPlayerMP) player);
					}
					//player.addChatComponentMessage(new ChatComponentText(I18n.format("msg.reactionchamber.invalidBlock")+" "+ pos));
					valid=false;
					break;
				} else {
					ReactionChamberTileEntSlave tile = (ReactionChamberTileEntSlave) w.getTileEntity(pos.x, pos.y, pos.z);
					if(tile.hasMaster()){
						if (!w.isRemote){
							TGPackets.network.sendTo(new PacketTGMessage((byte) 0,pos.x,pos.y,pos.z), (EntityPlayerMP) player);
						}
						valid=false;
						break;
					}
					
				}
			}
			//Check glass row
			if (valid){
				for (int i =0;i<positions_glass.size();i++){
					BlockCoords pos = positions_glass.get(i);

					Block b = w.getBlock(pos.x, pos.y, pos.z);
					int meta = w.getBlockMetadata(pos.x, pos.y, pos.z);
					
					if (!(b==TGBlocks.machineMultiBlock && meta ==2)){
						//System.out.println("MultiBlock invalid Block at"+pos);
						if (!w.isRemote){
							TGPackets.network.sendTo(new PacketTGMessage((byte) 0,pos.x,pos.y,pos.z), (EntityPlayerMP) player);
						}
						//player.addChatComponentMessage(new ChatComponentText(I18n.format("msg.reactionchamber.invalidBlock")+" "+ pos));
						valid=false;
						break;
					} else {
						ReactionChamberTileEntSlave tile = (ReactionChamberTileEntSlave) w.getTileEntity(pos.x, pos.y, pos.z);
						if(tile.hasMaster()){
							if (!w.isRemote){
								TGPackets.network.sendTo(new PacketTGMessage((byte) 0,pos.x,pos.y,pos.z), (EntityPlayerMP) player);
							}
							valid=false;
							break;
						}
						
					}
				}
			}
			
			
			if (valid){
				
				ArrayList<BlockCoords> inputsBottom=new ArrayList<BlockCoords>();
				
				BlockCoords middle = masterPos.getShifted(ForgeDirection.getOrientation(direction).getOpposite(), 1);
				BlockCoords inputTop=middle.getShifted(ForgeDirection.UP,3);
				
				if(direction==2){
					// +Z;
					inputsBottom.add(middle.getShifted(ForgeDirection.SOUTH, 1));
					inputsBottom.add(middle.getShifted(ForgeDirection.EAST, 1));
					inputsBottom.add(middle.getShifted(ForgeDirection.WEST, 1));
				} else if(direction==3){
					// -Z;
					inputsBottom.add(middle.getShifted(ForgeDirection.NORTH, 1));
					inputsBottom.add(middle.getShifted(ForgeDirection.EAST, 1));
					inputsBottom.add(middle.getShifted(ForgeDirection.WEST, 1));
				} else if (direction==4){
					// +X;
					inputsBottom.add(middle.getShifted(ForgeDirection.SOUTH, 1));
					inputsBottom.add(middle.getShifted(ForgeDirection.EAST, 1));
					inputsBottom.add(middle.getShifted(ForgeDirection.NORTH, 1));
				} else if (direction==5){
					// -x;
					inputsBottom.add(middle.getShifted(ForgeDirection.SOUTH, 1));
					inputsBottom.add(middle.getShifted(ForgeDirection.WEST, 1));
					inputsBottom.add(middle.getShifted(ForgeDirection.NORTH, 1));
				}
				
				//System.out.println("Master:"+masterPos);
				//System.out.println("inputTop: "+inputTop);
				
				positions_block.addAll(positions_glass);
				
				for (int i =0;i<positions_block.size();i++){
					BlockCoords pos = positions_block.get(i);

					TileEntity tile = w.getTileEntity(pos.x, pos.y, pos.z);
					if(tile!=null && tile instanceof ReactionChamberTileEntSlave){
						ReactionChamberTileEntSlave slave = (ReactionChamberTileEntSlave) tile;
						
						slave.updateMaster(masterPos.x, masterPos.y, masterPos.z);
						
						if(pos.equals(inputTop)){
							slave.setType((byte)2);
							//System.out.println("setType to Power");
						} else if (inputsBottom.contains(pos)){
							slave.setType((byte)1);
							//System.out.println("setType to Item/Liquid");
						}
						
						
						slave.needUpdate();

					} else {
						System.out.println("TileEnt null or wrong type, this should not happen!");
					}
				}
				
				master.setComplete(true,(byte)direction);
				master.needUpdate();
				//System.out.println("MultiBlock Formed!");
				return true;
			}
			
			
			
		} 
		return false;
	}
	
	private boolean CheckFormFabricator(EntityPlayer player, BlockCoords masterPos, int direction, World w, FabricatorTileEntMaster master){
		
		ArrayList<BlockCoords> positions_block = null;
		ArrayList<BlockCoords> positions_glass = null;
		if(direction==2){
			// +Z;
			positions_block = masterPos.getBoxWithOffsets(0, +1, 0, 0, 0, 1, false);
			positions_glass = masterPos.getBoxWithOffsets(0, +1, 1, 1, 0, 1, false);
			
		} else if(direction==3){
			// -Z;
			positions_block = masterPos.getBoxWithOffsets(-1, 0, 0, 0, -1, 0, false);
			positions_glass = masterPos.getBoxWithOffsets(-1, 0, 1, 1, -1, 0, false);
			
		} else if (direction==4){
			// +X;			
			positions_block = masterPos.getBoxWithOffsets(0, 1, 0, 0, -1, 0, false);
			positions_glass = masterPos.getBoxWithOffsets(0, 1, 1, 1, -1, 0, false);
	
		} else if (direction==5){
			// -x;
			positions_block = masterPos.getBoxWithOffsets(-1, 0, 0, 0, 0, +1, false);
			positions_glass = masterPos.getBoxWithOffsets(-1, 0, 1, 1, 0, +1, false);
			
		}
		
		boolean valid=false;
		if (positions_block!=null){
			
			valid=true;
			for (int i =0;i<positions_block.size();i++){
				BlockCoords pos = positions_block.get(i);

				Block b = w.getBlock(pos.x, pos.y, pos.z);
				int meta = w.getBlockMetadata(pos.x, pos.y, pos.z);
				
				if (!(b==TGBlocks.machineMultiBlock && meta ==3)){
					//System.out.println("MultiBlock invalid Block at"+pos);
					if (!w.isRemote){
						TGPackets.network.sendTo(new PacketTGMessage((byte) 0,pos.x,pos.y,pos.z), (EntityPlayerMP) player);
					}
					//player.addChatComponentMessage(new ChatComponentText(I18n.format("msg.reactionchamber.invalidBlock")+" "+ pos));
					valid=false;
					break;
				} else {
					FabricatorTileEntSlave tile = (FabricatorTileEntSlave) w.getTileEntity(pos.x, pos.y, pos.z);
					if(tile.hasMaster()){
						if (!w.isRemote){
							TGPackets.network.sendTo(new PacketTGMessage((byte) 0,pos.x,pos.y,pos.z), (EntityPlayerMP) player);
						}
						valid=false;
						break;
					}
					
				}
			}
			
			//Check glass row
			if (valid){
				for (int i =0;i<positions_glass.size();i++){
					BlockCoords pos = positions_glass.get(i);

					Block b = w.getBlock(pos.x, pos.y, pos.z);
					int meta = w.getBlockMetadata(pos.x, pos.y, pos.z);
					
					if (!(b==TGBlocks.machineMultiBlock && meta ==5)){
						//System.out.println("MultiBlock invalid Block at"+pos);
						if (!w.isRemote){
							TGPackets.network.sendTo(new PacketTGMessage((byte) 0,pos.x,pos.y,pos.z), (EntityPlayerMP) player);
						}
						//player.addChatComponentMessage(new ChatComponentText(I18n.format("msg.reactionchamber.invalidBlock")+" "+ pos));
						valid=false;
						break;
					} else {
						FabricatorTileEntSlave tile = (FabricatorTileEntSlave) w.getTileEntity(pos.x, pos.y, pos.z);
						if(tile.hasMaster()){
							if (!w.isRemote){
								TGPackets.network.sendTo(new PacketTGMessage((byte) 0,pos.x,pos.y,pos.z), (EntityPlayerMP) player);
							}
							valid=false;
							break;
						}
						
					}
				}
			}
			
			
			if (valid){
	
				for (int i =0;i<positions_block.size();i++){
					BlockCoords pos = positions_block.get(i);

					TileEntity tile = w.getTileEntity(pos.x, pos.y, pos.z);
					if(tile!=null && tile instanceof FabricatorTileEntSlave){
						FabricatorTileEntSlave slave = (FabricatorTileEntSlave) tile;
						slave.updateMaster(masterPos.x, masterPos.y, masterPos.z);
						slave.setType((byte)1);
						slave.needUpdate();

					} else {
						System.out.println("TileEnt null or wrong type, this should not happen!");
					}
				}
				for (int i =0;i<positions_glass.size();i++){
					BlockCoords pos = positions_glass.get(i);

					TileEntity tile = w.getTileEntity(pos.x, pos.y, pos.z);
					if(tile!=null && tile instanceof FabricatorTileEntSlave){
						FabricatorTileEntSlave slave = (FabricatorTileEntSlave) tile;
						slave.updateMaster(masterPos.x, masterPos.y, masterPos.z);
						slave.setType((byte)0);
						slave.needBlockUpdate();
						slave.needUpdate();

					} else {
						System.out.println("TileEnt null or wrong type, this should not happen!");
					}
				}
				
				master.setComplete(true,(byte)direction);
				master.needBlockUpdate();
				master.needUpdate();
				//System.out.println("MultiBlock Formed!");
				return true;
			}
		}
		return false;
	}
		
	
	
	@Override
	public IVariationInfo getManager(IBlockAccess world, int x, int y, int z, int metadata) {
		int meta=metadata;
		
		if(metadata<=MAX_META){
			TileEntity tile = world.getTileEntity(x, y, z);
			
			if (tile instanceof MultiBlockSlaveTileEntBase){
				if (((MultiBlockSlaveTileEntBase)tile).hasMaster()){
					meta = META_INVISIBLE;
				}
			} else if (tile instanceof MultiBlockMasterTileEntBase){
				if (((MultiBlockMasterTileEntBase)tile).isComplete()){
					meta = META_INVISIBLE;
				}
			}
		}
		
		return carverHelper.getVariation(meta);
	}

	@Override
	public IVariationInfo getManager(int meta) {
		return carverHelper.getVariation(meta);
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return this.carverHelper.getIcon(side, meta);
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		int metadata = world.getBlockMetadata(x, y, z);		
		
		int meta=metadata;
		
		if(metadata<=MAX_META){
			TileEntity tile = world.getTileEntity(x, y, z);
			
			if (tile instanceof MultiBlockSlaveTileEntBase){
				if (((MultiBlockSlaveTileEntBase)tile).hasMaster()){
					meta = META_INVISIBLE;
				}
			} else if (tile instanceof MultiBlockMasterTileEntBase){
				if (((MultiBlockMasterTileEntBase)tile).isComplete()){
					meta = META_INVISIBLE;
				}
			}
		}
		// return this.getIcon(side, meta);
		return this.carverHelper.getIcon(side, meta);
	}
	
}
