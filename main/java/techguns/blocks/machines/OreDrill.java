package techguns.blocks.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import techguns.tileentities.OreDrillTileEnt;

public class OreDrill extends BasicMachine {
	
	public OreDrill(){
		super();
		setBlockName("OreDrill");
	}
	

	/* (non-Javadoc)
	 * @see techguns.blocks.machines.BasicMachine#createNewTileEntity(net.minecraft.world.World, int)
	 */
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new OreDrillTileEnt();
	}


	/* (non-Javadoc)
	 * @see techguns.blocks.machines.BasicMachine#onBlockActivated(net.minecraft.world.World, int, int, int, net.minecraft.entity.player.EntityPlayer, int, float, float, float)
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int i1, float f1, float f2, float f3) {
		return false;
	}


	
}
