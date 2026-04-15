package techguns.blocks;

import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.WEST;
import net.minecraft.block.BlockLadder;
import net.minecraft.world.World;
import techguns.Techguns;

public class BlockBugSlime extends BlockLadder{
	public BlockBugSlime() {
		super();
		this.setStepSound(soundTypeCloth);
		setCreativeTab(Techguns.tabTechgun);
	}
	
	@Override
	public String getUnlocalizedName() {
		return "techguns."+super.getUnlocalizedName();
	}

	public int getDirectionMeta(World world, int x, int y, int z)
    {
        int j1 = 0;

        if ( world.isSideSolid(x, y, z + 1, NORTH))
        {
            j1 = 2;
        }

        if (world.isSideSolid(x, y, z - 1, SOUTH))
        {
            j1 = 3;
        }

        if (world.isSideSolid(x + 1, y, z, WEST))
        {
            j1 = 4;
        }

        if (world.isSideSolid(x - 1, y, z, EAST))
        {
            j1 = 5;
        }

        System.out.println("meta:"+j1);
        
        return j1;
    }
}
