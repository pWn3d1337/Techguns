package techguns.blocks.machines;

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
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.Techguns;
import techguns.blocks.ItemBlockOreDrill;
import techguns.tileentities.BasicInventoryTileEnt;
import techguns.tileentities.BasicPoweredTileEnt;
import techguns.tileentities.OreDrillTileEntMaster;
import techguns.tileentities.OreDrillTileEntSlave;
import techguns.util.TextUtil;

import com.cricketcraft.chisel.api.ICarvable;
import com.cricketcraft.chisel.api.carving.CarvableHelper;
import com.cricketcraft.chisel.api.carving.IVariationInfo;
import com.cricketcraft.chisel.api.rendering.ClientUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class OreDrillBlock extends BlockContainer implements ICarvable{
	private static final int MAXMETADATA_VALUE = 5;
	//public IIcon[] icons = new IIcon[13];
	public CarvableHelper carverHelper;

	public OreDrillBlock() {
		super(Material.iron);
		this.carverHelper = new CarvableHelper(this);
		setBlockName("OreDrillBlock");
		setStepSound(soundTypeMetal);
		setHarvestLevel("pickaxe", 1);
		setCreativeTab(Techguns.tabTechgun);
		setHardness(8.0f);
		setResistance(20.0f);
	}

	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}

	// @SideOnly(Side.CLIENT)
	// @Override
	// public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
	// for (int ix = 0; ix < MAXMETADATA_VALUE; ix++) {
	// subItems.add(new ItemStack(item, 1, ix));
	// }
	// }

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	/**
	 * Returns true if the given side of this block type should be rendered, if
	 * the adjacent block is at the given coordinates. Args: blockAccess, x, y,
	 * z, side
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		Block block = world.getBlock(x, y, z);

		int x2 = x - Facing.offsetsXForSide[side];
		int y2 = y - Facing.offsetsYForSide[side];
		int z2 = z - Facing.offsetsZForSide[side];

		int meta = world.getBlockMetadata(x2, y2, z2);

		if (meta == 1) { // Outer frame
			TileEntity tile = world.getTileEntity(x2, y2, z2);
			if (tile != null) {
				//Don't render Bottom row frame
				if (((OreDrillTileEntSlave) tile).hasMaster() && ((OreDrillTileEntSlave) tile).isBottomrow()) {
					return false;
				}
			}
		}

		if (meta == 2) { // Drill rod
			TileEntity tile = world.getTileEntity(x2, y2, z2);
			if (tile != null) {
				if (((OreDrillTileEntSlave) tile).hasMaster()) {
					return false;
				}
			}
		}

		if (world.getBlockMetadata(x, y, z) != world.getBlockMetadata(x - Facing.offsetsXForSide[side], y - Facing.offsetsYForSide[side], z
				- Facing.offsetsZForSide[side])) {
			return true;
		}

		if (block == this) {
			return false;
		}

		return block == this ? false : super.shouldSideBeRendered(world, x, y, z, side);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.block.Block#getItemDropped(int, java.util.Random, int)
	 */
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(this); // new ItemBlockMetaData(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.block.Block#getItem(net.minecraft.world.World, int,
	 * int, int)
	 */
	@Override
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(this);
	}

	// @Override
	// public void registerBlockIcons(IIconRegister iconRegister) {
	// icons[0] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":" +
	// "drillFrameCorner");
	// icons[1] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":" +
	// "drillFrame");
	// icons[2] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":" +
	// "drillRodTop");
	// icons[3] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":" +
	// "drillEngine");
	// icons[4] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":" +
	// "drillEngineController");
	// icons[5] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":" +
	// "drillRodSide");
	// icons[6] = null;
	// icons[7] = null;
	// icons[8] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":" +
	// "drillFrameCorner");
	// icons[9] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":" +
	// "drillFrame_c");
	// icons[10] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":"
	// + "invisible");
	// icons[11] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":"
	// + "drillEngine");
	// icons[12] = iconRegister.registerIcon(Techguns.MODID.toLowerCase() + ":"
	// + "drillEngineController");
	// }
	//

	@Override
	public void registerBlockIcons(IIconRegister register) {
		carverHelper.registerBlockIcons(Techguns.MODID, this, register);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
		//carverHelper.registerSubBlocks(this, tabs, list);
		for (IVariationInfo info : carverHelper.infoList) {
			if (info.getVariation().getBlockMeta() <= 4) {
				list.add(new ItemStack(this, 1, info.getVariation().getItemMeta()));
			}
		}
	}

	@Override
	public IIcon getIcon(IBlockAccess b, int x, int y, int z, int side) {
		int meta = b.getBlockMetadata(x, y, z);
		
		//<test_code>
//		if (meta == 8) {
//			switch (side) {
//				case 0:
//					return this.carverHelper.getIcon(side, 0);
//				case 1:
//					return this.carverHelper.getIcon(side, 2);
//				case 2:
//					return this.carverHelper.getIcon(side, 9);
//				case 3:
//					return this.carverHelper.getIcon(side, 11);
//				case 4:
//					return this.carverHelper.getIcon(side, 12);
//				case 5:
//					return this.carverHelper.getIcon(side, 1);
//			}		
//		}
				
		//</test_code>
		
		
		if (meta != 4) {
			TileEntity tile = b.getTileEntity(x, y, z);		
			if (((OreDrillTileEntSlave) tile).hasMaster()) {
				if (meta == 3) {
					int dir = ((OreDrillTileEntSlave) tile).getDirection();
					//System.out.println("Direction: "+dir);
					//THIS CODE DOES NOT WORK!
					switch (dir) {
						case 0:
						case 1: 
							if (side == 0 || side==1) meta++;
							break;
						case 2:
						case 3:
							if (side == 2 || side ==3) meta++;
							break;
						case 4:
						case 5:
							if (side == 4 || side == 5) meta++;
							break;
						case -1:
						default:
							break;
					}
				}
				meta += 8;
			}
		}
		// return this.getIcon(side, meta);
		return this.carverHelper.getIcon(side, meta);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		// if (meta <8){
		// if (meta != 2){
		// return this.icons[meta];
		// } else {
		// if (side <2){
		// return this.icons[2];
		// } else {
		// return this.icons[5];
		// }
		// }
		// } else {
		// return this.icons[meta];
		// }
		return this.carverHelper.getIcon(side, meta);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {

			if (tile instanceof OreDrillTileEntSlave) {
				OreDrillTileEntSlave slave = (OreDrillTileEntSlave) tile;
				slave.notifyMasterMultiblockBreak();
			}
			if(tile instanceof BasicInventoryTileEnt){
				((BasicInventoryTileEnt) tile).onBlockBreak();
			}
			
			
			world.removeTileEntity(x, y, z);
		} else {
			//System.out.println("No tile entity");
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i1, float f1, float f2, float f3) {

		TileEntity tile = world.getTileEntity(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 4 && !player.isSneaking()) {

			// if (meta ==3){

			OreDrillTileEntMaster mastertile = (OreDrillTileEntMaster) tile;
			if (!mastertile.isComplete()) {
				// engine block
				// ItemStack helditem = player.getHeldItem();
				// if (helditem!=null &&
				// helditem.getItem().getToolClasses(helditem).contains("wrench")){

				// START MULTIBLOCK GENERATION
				byte dir = getDirectionForMultiblockDrill(world, x, y, z);
				if (dir == -1) {
					if (world.isRemote) {
						player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.msg.error.oredrill.invalidmulti.dir")));
					}
					return true;
				} else {
					// System.out.println("Continue multiblock creation check");
					int[] coords = getNextBlockInDir(x, y, z, dir);
					int enginelength = 0;
					int rodlength = 0;
					boolean valid = true;
					boolean finished = false;

					while (valid && !finished) {
						if (rodlength == 0) {

							if (ItemBlockOreDrill.isBlock(world, coords[0], coords[1], coords[2], TGBlocks.oreDrill, 3)) {
								// Engine continues
								enginelength++;

							} else if (ItemBlockOreDrill.isBlock(world, coords[0], coords[1], coords[2], TGBlocks.oreDrill, 2)) {
								// Rod starts
								rodlength++;
							} else {
								valid = false;
								if (world.isRemote) {
									player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.msg.error.oredrill.invalid1")));
								}
							}
							coords = getNextBlockInDir(coords[0], coords[1], coords[2], dir);
						} else {
							if (ItemBlockOreDrill.isBlock(world, coords[0], coords[1], coords[2], TGBlocks.oreDrill, 2)) {
								// Rod starts
								rodlength++;
								coords = getNextBlockInDir(coords[0], coords[1], coords[2], dir);

							} else if (ItemBlockOreDrill.isOreCluster(world, coords[0], coords[1], coords[2])) {
								// finished
								finished = true;
							} else {
								valid = false;
								if (world.isRemote) {
									player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.msg.error.oredrill.invalid1")));
								}
							}
						}
					}

					if (valid) {
						//System.out.println("Valid main axis:" + enginelength + " " + rodlength);

						// check side dimensions
						coords = getNextBlockInDir(x, y, z, dir);

						// try radius 2:
						int radius=0;
						// METAS:0frame, 1lightFrame, 2 rod, 3engine
						/*if (checkSliceInDir(world, coords[0], coords[1], coords[2], dir, 4, 0, 0, TGBlocks.oreDrill, 3)) {
							radius = 4;
						} else if (checkSliceInDir(world, coords[0], coords[1], coords[2], dir, 3, 0, 0, TGBlocks.oreDrill, 3)) {
							radius = 3;
						} else if (checkSliceInDir(world, coords[0], coords[1], coords[2], dir, 2, 0, 0, TGBlocks.oreDrill, 3)) {
							radius = 2;
						} else if (checkSliceInDir(world, coords[0], coords[1], coords[2], dir, 1, 0, 0, TGBlocks.oreDrill, 3)) {
							radius = 1;
						} else {
							radius = 0;
						}*/
						for (int i=4;i>0;--i){
							if (checkSliceInDir(world, coords[0], coords[1], coords[2], dir, i, 0, 0, TGBlocks.oreDrill, 3)) {
								radius = i;
								break;
							}
						}
						
						

						if (radius == 0 && (enginelength > 0 || rodlength > 1)) {
							if (world.isRemote) {
								player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.msg.error.oredrill.invalid2")));
							}
						} else if (radius == 1 && (enginelength < 1 || rodlength < 2)) {
							if (world.isRemote) {
								player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.msg.error.oredrill.invalid2.1")));
							}
						} else if (radius == 2 && (enginelength < 2 || rodlength < 3)) {
							if (world.isRemote) {
								player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.msg.error.oredrill.invalid2.2")));
							}
						} else if (radius ==  3 && ((enginelength < 2 || rodlength < 3) || (enginelength+rodlength < 7))) {
							if (world.isRemote) {
								player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.msg.error.oredrill.invalid2.2")));
							}
						} else if (radius ==  4 && ((enginelength < 2 || rodlength < 3) || (enginelength+rodlength < 9))) {
							if (world.isRemote) {
								player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.msg.error.oredrill.invalid2.2")));
							}
							
						} else {

							//System.out.println("RADIUS:" + radius);

							for (int i = 1; i < enginelength && valid; i++) {
								coords = getNextBlockInDir(coords[0], coords[1], coords[2], dir);
								//System.out.println("Checking engine slice");
								if (!checkSliceInDir(world, coords[0], coords[1], coords[2], dir, radius, 0, 1, TGBlocks.oreDrill, 3)) {
									if (world.isRemote) {
										player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.msg.error.oredrill.invalid3") + " ("
												+ coords[0] + "," + coords[1] + "," + coords[2] + ") "));
									}
									valid = false;
								}
							}
							for (int i = 1; i <= rodlength && valid; i++) {
								coords = getNextBlockInDir(coords[0], coords[1], coords[2], dir);
								if (i < rodlength) {
									//System.out.println("Checking rod slice");
									if (!checkSliceInDir(world, coords[0], coords[1], coords[2], dir, radius, 0, 1, Blocks.air, -1)) {
										if (world.isRemote) {
											player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.msg.error.oredrill.invalid3") + " ("
													+ coords[0] + "," + coords[1] + "," + coords[2] + ") "));
										}
										valid = false;
									}
								} else {
									//System.out.println("Checking end slice");
									if (!checkSliceInDir(world, coords[0], coords[1], coords[2], dir, radius, 0, 0, TGBlocks.oreDrill, 1)) {
										if (world.isRemote) {
											player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.msg.error.oredrill.invalid3") + " ("
													+ coords[0] + "," + coords[1] + "," + coords[2] + ") "));
										}
										valid = false;
									}
								}
							}

							if (valid) {
								// System.out.println("Multiblock Driller valid "+enginelength+" "+rodlength+" r:"+radius);
								if (!world.isRemote) {
									createMultiblockDrillerTileentities(world, x, y, z, dir, radius, enginelength, rodlength);
								} else {
									player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.msg.oredrill.formed1") + radius + " "
											+ TextUtil.trans("techguns.msg.oredrill.formed2") + enginelength + " "
											+ TextUtil.trans("techguns.msg.oredrill.formed3") + rodlength));
								}
								return true;
							}

						}

					}

				}
			} else {
				// Block is complete

				if(!player.worldObj.isRemote && !mastertile.hasDrill){
					mastertile.checkAndStartOperation();
				}
				
				player.openGui(Techguns.MODID, 16, world, x, y, z);
				return true;
			}

			// }
		} else {
			if (tile instanceof OreDrillTileEntSlave) {
				// System.out.println("SLAVE TILE");

				OreDrillTileEntSlave tileent = (OreDrillTileEntSlave) tile;
				int[] masterpos = tileent.getMasterPos();
				if (masterpos != null) {
					TileEntity master = world.getTileEntity(masterpos[0], masterpos[1], masterpos[2]);
					// System.out.println("Getting tileent at:"+masterpos[0]+","+masterpos[1]+","+masterpos[2]);
					// System.out.println("Slave tile pos:"+x+","+y+","+z);
					if (master != null && master instanceof OreDrillTileEntMaster) {
						player.openGui(Techguns.MODID, 16, world, masterpos[0], masterpos[1], masterpos[2]);
						return true;
					} else {
						//System.out.println("Master tileent invalid");
					}
				}
			} else if (tile instanceof OreDrillTileEntMaster) {
				// player is sneaking
				player.openGui(Techguns.MODID, 16, world, x, y, z);
				return true;
			} else {
				//System.out.println("DAFUQ");
			}
		}

		return false;
	}

	public static int[] getNextBlockInDir(int x, int y, int z, byte dir) {
		int[] pos = new int[] { x, y, z };
		switch (dir) {
		case 0:
			pos[1]++;
			break;
		case 1:
			pos[1]--;
			break;
		case 2:
			pos[0]++;
			break;
		case 3:
			pos[0]--;
			break;
		case 4:
			pos[2]++;
			break;
		case 5:
			pos[2]--;
			break;
		}
		return pos;
	}

	public static int[] getNextBlockInDirOffset(int x, int y, int z, byte dir, int offset) {
		int[] pos = new int[] { x, y, z };
		switch (dir) {
		case 0:
			pos[1] += offset;
			break;
		case 1:
			pos[1] -= offset;
			break;
		case 2:
			pos[0] += offset;
			break;
		case 3:
			pos[0] -= offset;
			break;
		case 4:
			pos[2] += offset;
			break;
		case 5:
			pos[2] -= offset;
			break;
		}
		return pos;
	}

	public boolean checkSliceInDir(World w, int x, int y, int z, byte dir, int radius, int metacorner, int metaside, Block inner, int metainner) {
		int MIN = 0;
		int MAX = 2 * radius;
		int offset = radius;

		int px = x;
		int py = y;
		int pz = z;

		// System.out.println("P:"+x+","+y+","+z);

		if (dir == 0 || dir == 1) {
			px = x - offset;
			pz = z - offset;
			// y-axis
			for (int i = 0; i < 1 + 2 * radius; i++) {
				for (int j = 0; j < 1 + 2 * radius; j++) {
					// Don't need to check middle axis
					if (!(px + i == x && pz + j == z)) {

						if ((i == MIN || i == MAX) && (j == MIN || j == MAX)) {
							// CORNER BLOCK
							if (!ItemBlockOreDrill.isBlock(w, px + i, py, pz + j, TGBlocks.oreDrill, metacorner)) {
								return false;
							}
						} else if (i == MIN || i == MAX || j == MIN || j == MAX) {
							// side block
							if (!ItemBlockOreDrill.isBlock(w, px + i, py, pz + j, TGBlocks.oreDrill, metaside)) {
								return false;
							}
						} else {
							if (!ItemBlockOreDrill.isBlock(w, px + i, py, pz + j, inner, metainner)) {
								return false;
							}
						}

					}
				}
			}
		} else if (dir == 2 || dir == 3) {
			// x-axis fixed
			pz = z - offset;
			py = y - offset;
			for (int i = 0; i < 1 + 2 * radius; i++) {
				for (int j = 0; j < 1 + 2 * radius; j++) {
					// Don't need to check middle axis
					if (!(py + i == y && pz + j == z)) {

						if ((i == MIN || i == MAX) && (j == MIN || j == MAX)) {
							// CORNER BLOCK
							if (!ItemBlockOreDrill.isBlock(w, px, py + i, pz + j, TGBlocks.oreDrill, metacorner)) {
								return false;
							}
						} else if (i == MIN || i == MAX || j == MIN || j == MAX) {
							// side block
							if (!ItemBlockOreDrill.isBlock(w, px, py + i, pz + j, TGBlocks.oreDrill, metaside)) {
								return false;
							}
						} else {
							if (!ItemBlockOreDrill.isBlock(w, px, py + i, pz + j, inner, metainner)) {
								return false;
							}
						}

					}
				}
			}
		} else if (dir == 4 || dir == 5) {
			// z-axis fixed
			px = x - offset;
			py = y - offset;
			for (int i = 0; i < 1 + 2 * radius; i++) {
				for (int j = 0; j < 1 + 2 * radius; j++) {
					// Don't need to check middle axis
					if (!(px + i == x && py + j == y)) {

						if ((i == MIN || i == MAX) && (j == MIN || j == MAX)) {
							// CORNER BLOCK
							if (!ItemBlockOreDrill.isBlock(w, px + i, py + j, pz, TGBlocks.oreDrill, metacorner)) {
								return false;
							}
						} else if (i == MIN || i == MAX || j == MIN || j == MAX) {
							// side block
							if (!ItemBlockOreDrill.isBlock(w, px + i, py + j, pz, TGBlocks.oreDrill, metaside)) {
								return false;
							}
						} else {
							if (!ItemBlockOreDrill.isBlock(w, px + i, py + j, pz, inner, metainner)) {
								return false;
							}
						}

					}
				}
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * -1 no valid axis 0 +y (up) 1 -y (down) 2 +x 3 -x 4 +z 5 -z
	 */
	protected byte getDirectionForMultiblockDrill(World world, int x, int y, int z) {
		Block drill = TGBlocks.oreDrill;

		byte dir = -1;

		// only 1 neighbour may be an engine or a rod
		if (ItemBlockOreDrill.isBlock(world, x, y + 1, z, drill, 2) || ItemBlockOreDrill.isBlock(world, x, y + 1, z, drill, 3)) {
			dir = 0;
		}

		// down
		if (ItemBlockOreDrill.isBlock(world, x, y - 1, z, drill, 2) || ItemBlockOreDrill.isBlock(world, x, y - 1, z, drill, 3)) {
			if (dir == -1) {
				dir = 1;
			} else {
				return -1;
			}
		}

		if (ItemBlockOreDrill.isBlock(world, x + 1, y, z, drill, 2) || ItemBlockOreDrill.isBlock(world, x + 1, y, z, drill, 3)) {
			if (dir == -1) {
				dir = 2;
			} else {
				return -1;
			}
		}

		if (ItemBlockOreDrill.isBlock(world, x - 1, y, z, drill, 2) || ItemBlockOreDrill.isBlock(world, x - 1, y, z, drill, 3)) {
			if (dir == -1) {
				dir = 3;
			} else {
				return -1;
			}
		}

		if (ItemBlockOreDrill.isBlock(world, x, y, z + 1, drill, 2) || ItemBlockOreDrill.isBlock(world, x, y, z + 1, drill, 3)) {
			if (dir == -1) {
				dir = 4;
			} else {
				return -1;
			}
		}

		if (ItemBlockOreDrill.isBlock(world, x, y, z - 1, drill, 2) || ItemBlockOreDrill.isBlock(world, x, y, z - 1, drill, 3)) {
			if (dir == -1) {
				dir = 5;
			} else {
				return -1;
			}
		}

		return dir;
	}

	public static void createMultiblockDrillerTileentities(World w, int x, int y, int z, byte dir, int radius, int enginelength, int rodlength) {

		int[] coords = getNextBlockInDir(x, y, z, dir);

		int length = enginelength + rodlength;
		// System.out.println("enginelength:"+enginelength+","+rodlength+" len:"+length);
		for (int i = 0; i < length; i++) {
			boolean hollow = !((i < enginelength) || (i == (length - 1)));
			// System.out.println("Hollow:"+hollow+" |i:"+i+",engine:"+enginelength+", len:"+length);
			updateTileEntsInSlice(w, coords[0], coords[1], coords[2], dir, radius, hollow, x, y, z,i==length-1);
			coords = getNextBlockInDir(coords[0], coords[1], coords[2], dir);
		}
		OreDrillTileEntMaster master = (OreDrillTileEntMaster) w.getTileEntity(x, y, z); // new
		// OreDrillTileEntMaster();
		// w.setTileEntity(x, y, z, master);
		master.setMultiblockFormed(dir, (byte) radius, (short) enginelength, (short) rodlength);

		//w.markBlockForUpdate(x, y, z);
		//master.markDirty();
		master.needUpdate();
	}

	public static void updateTileEntsInSlice(World w, int x, int y, int z, byte dir, int radius, boolean hollow, int masterX, int masterY, int masterZ, boolean bottomrow) {
		int MIN = 0;
		int MAX = 2 * radius;
		int offset = radius;

		int px = x;
		int py = y;
		int pz = z;

		// System.out.println("P:"+x+","+y+","+z);
		//if(bottomrow){
		//	System.out.println("BOTTOMROW!!!");
		//}

		if (dir == 0 || dir == 1) {
			px = x - offset;
			pz = z - offset;
			// y-axis
			for (int i = 0; i < 1 + 2 * radius; i++) {
				for (int j = 0; j < 1 + 2 * radius; j++) {

					// System.out.println("Checking for tileent:"+(px+i)+","+(py)+","+(pz+j));
					if ((px + i == x && pz + j == z) || !hollow || (i == MIN || i == MAX || j == MIN || j == MAX)) {

						/*
						 * OreDrillTileEntSlave tile = new
						 * OreDrillTileEntSlave(masterX,(short)masterY,masterZ);
						 * w.setTileEntity(px+i, py, pz+j, tile);
						 */
						OreDrillTileEntSlave tile = (OreDrillTileEntSlave) w.getTileEntity(px + i, py, pz + j);
						// if (tile != null){
						tile.updateMaster(masterX, masterY, masterZ);
						if(bottomrow){
							tile.setBottomrow(true);
						}
						tile.setDirection(dir);
						tile.needUpdate();
						//w.markBlockForUpdate(px + i, py, pz + j);
						//w.markTileEntityChunkModified(px+i, py, pz+j, tile);
						//System.out.println("Marking:"+(px+i)+","+py+","+(pz+j));
						//w.scheduleBlockUpdate(px+i,py,pz+j, w.getBlock(px+i,py,pz+j), 1);
						//tile.markDirty();
						// } else {
						// System.out.println("NULL TILEENT!!!!");
						// System.out.println("i"+i+",j:"+j+" hollow:"+hollow);
						// System.out.println("(px+i == x && pz+j == z):"+(px+i
						// == x && pz+j == z));
						// System.out.println("(i==MIN || i==MAX || j==MIN || j==MAX):"+(i==MIN
						// || i==MAX || j==MIN || j==MAX));
						// System.out.println("Block:"+w.getBlock(px+i, py,
						// pz+j).getUnlocalizedName());
						// System.out.println("META:"+w.getBlockMetadata(px+i,
						// py, pz+j));
						// }
					}

				}
			}
		} else if (dir == 2 || dir == 3) {
			// x-axis fixed
			pz = z - offset;
			py = y - offset;
			for (int i = 0; i < 1 + 2 * radius; i++) {
				for (int j = 0; j < 1 + 2 * radius; j++) {

					if ((py + i == y && pz + j == z) || !hollow || (i == MIN || i == MAX || j == MIN || j == MAX)) {

						OreDrillTileEntSlave tile = (OreDrillTileEntSlave) w.getTileEntity(px, py + i, pz + j);
						tile.updateMaster(masterX, masterY, masterZ);
						if(bottomrow){
							tile.setBottomrow(true);
						}
						tile.setDirection(dir);
						tile.needUpdate();
						//w.markBlockForUpdate(px, py + i, pz + j);
						//tile.markDirty();
					}

				}
			}
		} else if (dir == 4 || dir == 5) {
			// z-axis fixed
			px = x - offset;
			py = y - offset;
			for (int i = 0; i < 1 + 2 * radius; i++) {
				for (int j = 0; j < 1 + 2 * radius; j++) {

					if ((px + i == x && py + j == y) || !hollow || (i == MIN || i == MAX || j == MIN || j == MAX)) {

						OreDrillTileEntSlave tile = (OreDrillTileEntSlave) w.getTileEntity(px + i, py + j, pz);
						tile.updateMaster(masterX, masterY, masterZ);
						if(bottomrow){
							tile.setBottomrow(true);
						}
						tile.setDirection(dir);
						tile.needUpdate();
						//w.markBlockForUpdate(px + i, py + j, pz);
						//tile.markDirty();
					}

				}
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if (meta == 4) {
			return new OreDrillTileEntMaster();
		} else {
			return new OreDrillTileEntSlave();
		}
	}

	/*@Override
	public IVariationInfo getVariation(IBlockAccess world, int x, int y, int z, int metadata) {
		int meta = world.getBlockMetadata(x, y, z);
		if (meta != 4) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if (((OreDrillTileEntSlave) tile).hasMaster()) {
				meta += 8;
			}
		}
		return carverHelper.getVariation(meta);
	}

	@Override
	public IVariationInfo getVariation(ItemStack stack) {
		return carverHelper.getVariation(stack.getItemDamage());
	}
*/
	@Override
	public int getRenderType() {
		return ClientUtils.renderCTMId;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		//super.onNeighborBlockChange(world, x, y, z, block);
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile!=null && tile instanceof BasicPoweredTileEnt){
			((BasicPoweredTileEnt)tile).onNeighborBlockChange();		
		}
	}

	@Override
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
		//return getMaterial().isOpaque() && renderAsNormalBlock() && !canProvidePower();
		return true;
	}

	@Override
	public IVariationInfo getManager(IBlockAccess world, int x, int y, int z, int metadata) {
		//int meta = world.getBlockMetadata(x, y, z);
		int meta=metadata;
		if (metadata != 4) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if (((OreDrillTileEntSlave) tile).hasMaster()) {
				meta += 8;
			}
		}
		return carverHelper.getVariation(meta);
	}

	@Override
	public IVariationInfo getManager(int meta) {
		return carverHelper.getVariation(meta);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase ent, ItemStack item) {
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if (ent instanceof EntityPlayer && tile instanceof OreDrillTileEntMaster){
			((OreDrillTileEntMaster)tile).setOwner((EntityPlayer)ent);
		}
	}
	
}
