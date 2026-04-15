package techguns.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import techguns.entities.npc.AlienBug;

public class BlockBugSand extends GenericBlockCTM {

	public BlockBugSand(Material material) {
		super(material);
		this.setTickRandomly(false);
	}

	@Override
	public void updateTick(World w, int x, int y, int z, Random rnd) {
		if(!w.isRemote){
			if (w.getBlock(x, y+1, z).isAir(w, x, y+1, z)){
				AlienBug bug = new AlienBug(w);
				bug.setPositionAndRotation(x+0.5, y+1, z+0.5, rnd.nextFloat()*360f, rnd.nextFloat()*360f);
				w.spawnEntityInWorld(bug);
			}
			
			
		}
		
		
		super.updateTick(w, x, y, z, rnd);
	}

	
	
	
}
