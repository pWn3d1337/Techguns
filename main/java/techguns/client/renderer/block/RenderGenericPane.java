package techguns.client.renderer.block;

import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.WEST;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import techguns.blocks.GenericBlockPane;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RenderGenericPane implements ISimpleBlockRenderingHandler{

	int renderId = RenderingRegistry.getNextAvailableRenderId();
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) {
		// Render Icon only
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		if (block instanceof GenericBlockPane) {
			GenericBlockPane pane = (GenericBlockPane) block;
			//Render Pane
			
			boolean north = pane.canPaneConnectTo(world, x, y, z-1, NORTH);
			boolean east = pane.canPaneConnectTo(world, x+1, y, z, EAST);
			boolean south = pane.canPaneConnectTo(world, x, y, z+1, SOUTH);
			boolean west = pane.canPaneConnectTo(world, x-1, y, z, WEST);
			
			if (north||east||south||west) {
				renderer.renderBlockPane(pane, x, y, z);
			}
			
			
			
			//Render Pole
			if ((!north && !east &&!south && !west) ||		
				(north && east &&!south && !west) ||
				(!north && east && south && !west) ||
				(!north && !east && south && west) ||
				(north && !east && !south && west) ||
				(north && !east && !south && !west) ||
				(!north && east && !south && !west) ||
				(!north && !east && south && !west) ||
				(!north && !east && !south && west))				
			{
				renderPole(renderer,world,x,y,z, pane);
			}
			
			return true;
		}else {
			renderer.renderStandardBlock(block,x,y,z);
			return true;
			//return false;
		}
	}
	
	private void renderPole(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, GenericBlockPane block) {
		renderer.setOverrideBlockTexture(block.getPoleIcon(world.getBlockMetadata(x, y, z)));
		renderer.setRenderBounds(0.4375, 0.0, 0.4375, 0.5625, 1.0, 0.5625);
		renderer.renderAllFaces = true;
        renderer.renderStandardBlock(block,x,y,z);
        //Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        renderer.clearOverrideBlockTexture();
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return renderId;
	}


}
