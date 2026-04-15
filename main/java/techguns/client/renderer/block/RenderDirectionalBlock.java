package techguns.client.renderer.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import techguns.blocks.GenericBlockDirectional;
import techguns.client.ClientProxy;
import techguns.client.renderer.TGRenderHelper;

public class RenderDirectionalBlock extends RenderGenericBlock {

	public RenderDirectionalBlock() {

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		boolean flag = true;
		
		//<shitty_workaround>
		Tessellator t = Tessellator.instance;
        for(int i = 0; i<8; i++)
        {
            t.addVertex(0,0,0);
        }
		//</shitty_workaround>
		
		
		if (ClientProxy.get().renderPass == 0) {
			flag = renderer.renderStandardBlock(block, x, y, z); //super.renderWorldBlock(world, x, y, z, block, modelId, renderer);
		}else if (ClientProxy.get().renderPass == 1) {	
			if (block instanceof GenericBlockDirectional) {
				GenericBlockDirectional dblock = (GenericBlockDirectional) block;
				if (dblock.hasOverlay()) {
					TGRenderHelper.enableFXLighting();
					
					int meta = world.getBlockMetadata(x, y, z);
					for (int i = 0; i < 6; i++) {
						IIcon texture = dblock.getOverlayIcon(i, meta);
						if (texture != null) {		
							switch (world.getBlockMetadata(x, y, z)) {
							case 0:
								renderer.renderFaceYNeg(block, x, y, z, texture);
								break;
							case 1:
								renderer.renderFaceYPos(block, x, y, z, texture);
								break;
							case 4:
								renderer.renderFaceXNeg(block, x, y, z, texture);
								break;
							case 3:
								renderer.renderFaceXPos(block, x, y, z, texture);
								break;
							case 2:
								renderer.renderFaceZNeg(block, x, y, z, texture);
								break;
							case 5:
								renderer.renderFaceZPos(block, x, y, z, texture);
								break;
							default:
								break;
							}
						}
					}
					
					TGRenderHelper.disableFXLighting();
				}
			}
		}

		return flag;
	}

}
