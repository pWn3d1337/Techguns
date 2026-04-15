package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import techguns.Techguns;
import techguns.entities.TurretEntityUNUSED;
import techguns.tileentities.TurretBaseEnt;

public class TurretBase extends BlockContainer {
	
	public TurretBase(Material mat) {
		super(mat);
		
		setCreativeTab(Techguns.tabTechgun);
		setHardness(4.0f);
		setBlockName("Turret Base");
		setStepSound(soundTypeStone);
		setHarvestLevel("pickaxe", 2);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TurretBaseEnt();
	}

	/* (non-Javadoc)
	 * @see net.minecraft.block.BlockContainer#breakBlock(net.minecraft.world.World, int, int, int, net.minecraft.block.Block, int)
	 */
	@Override
	public void breakBlock(World world, int x, int y,
			int z, Block b, int p_149749_6_) {
		((TurretBaseEnt)world.getTileEntity(x, y, z)).breakTurret();
		world.removeTileEntity(x, y, z);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.block.BlockContainer#onBlockAdded(net.minecraft.world.World, int, int, int)
	 */
	@Override
	public void onBlockAdded(World w, int x,
			int y, int z) {
		//if (!w.isRemote){
			TurretEntityUNUSED turret = new TurretEntityUNUSED(w);//new TurretEntity(w);
			turret.setPosition(x+0.5d, y+1, z+0.5d);
			w.spawnEntityInWorld(turret);
			//((TurretBaseEnt)w.getTileEntity(x, y, z)).setMountedTurret(turret);
		//}
	}

	
	
}
