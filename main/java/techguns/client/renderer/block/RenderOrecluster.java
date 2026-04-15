package techguns.client.renderer.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import techguns.TGBlocks;
import techguns.blocks.OreCluster;

public class RenderOrecluster extends RenderGenericBlock {
	TessellatorCubeProper cube;
	
	
	public RenderOrecluster() {
		super();
		cube=new TessellatorCubeProper(TGBlocks.oreCluster, 0);
		//cube.texture=new ResourceLocation("textures/blocks/iron_ore.png");
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		GL11.glPushMatrix();
		//GL11.glTranslatef(0.5f, -0.5f, 0);
		//GL11.glTranslatef(-0.5f, 0.5f, 0);
		GL11.glTranslated(0.15f,0,0.15f);
		/*OreCluster cluster = (OreCluster) block;
		IIcon tex = cluster.ores[metadata].getBlockTextureFromSide(0);*/
		//Minecraft.getMinecraft().getTextureManager().bindTexture(cube.texture);
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		//RenderHelper.enableGUIStandardItemLighting();
		RenderHelper.enableStandardItemLighting();
		
		OreCluster cluster = (OreCluster) block;
		
		/*System.out.println("Render Cluster:"+cluster);
		System.out.println("Metadata:"+metadata);
		System.out.println("Texture id="+cluster.textureType[metadata]);
		System.out.println("Tex:"+cluster.texture[cluster.textureType[metadata]].getIconName());*/
		float[] colors = cluster.colors[metadata];
		
		Tessellator.instance.startDrawingQuads();
		cube.drawCube(Tessellator.instance, 0, 0, 0, 1, 1, 1, false,cluster.texture[0]);
		
		Tessellator.instance.draw();
		
		Tessellator.instance.startDrawingQuads();
		Tessellator.instance.setColorOpaque_F(colors[0], colors[1], colors[2]);
		cube.drawCube(Tessellator.instance, 0, 0, 0, 1, 1, 1, false,cluster.overlay_texture[cluster.textureType[metadata]]);
		Tessellator.instance.draw();
		
		Tessellator.instance.setColorOpaque_F(1, 1, 1);
		
		//RenderHelper.enableGUIStandardItemLighting();
		RenderHelper.disableStandardItemLighting();
		GL11.glPopAttrib();
		
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		//Minecraft.getMinecraft().getTextureManager().bindTexture(cube.texture);

		//<shitty_workaround>
		Tessellator t = Tessellator.instance;
        for(int i = 0; i<8; i++)
        {
            t.addVertex(0,0,0);
        }
		//</shitty_workaround>
			
		//if (ClientProxy.renderPass == 0) {
			
			renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
			renderer.renderStandardBlock(block, x, y, z);
			//renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 0.75f, 0.5f, 0);
			
		/*}else if (ClientProxy.renderPass == 1) {	*/
			if (block instanceof OreCluster) {
				OreCluster oreclust = (OreCluster) block;
				
					//TGRenderHelper.enableFXLighting();
					
					int meta = world.getBlockMetadata(x, y, z);
					//for (int i = 0; i < 6; i++) {
					IIcon texture = oreclust.getOverlayIcon(0, meta);
					
					if (texture != null) {
						//renderer.setOverrideBlockTexture(texture);
						//renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 0.75f, 0.5f, 0);
						//GL11.glColor3f(0.75f, 0.5f, 0f);
						float[] col = oreclust.getColors(meta);
						//t.setColorOpaque_F(0.8f, 0.6f, 0.5f);
						//t.setColorOpaque_F(col[0]+(float)Keybinds.X, col[1]+(float)Keybinds.Y,col[2]+(float)Keybinds.Z);
						t.setColorOpaque_F(col[0], col[1], col[2]);
						renderer.renderFaceXNeg(block,x,y,z,texture);
						renderer.renderFaceXPos(block,x,y,z,texture);
						renderer.renderFaceYNeg(block,x,y,z,texture);
						renderer.renderFaceYPos(block,x,y,z,texture);
						renderer.renderFaceZNeg(block,x,y,z,texture);
						renderer.renderFaceZPos(block,x,y,z,texture);
						t.setColorOpaque(255, 255, 255);
						//GL11.glColor3f(1.0f, 1.0f, 1.0f);
						//renderer.renderStandardBlock(block, x, y, z)
						//Minecraft.getMinecraft().renderEngine.bindTexture(texture);
					}
					//}
					
					//TGRenderHelper.disableFXLighting();

			}
		//}
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	
}
