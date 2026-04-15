package techguns.client.renderer.block;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import techguns.blocks.IBlockCamoChangeable;

public class RenderLadder extends RenderGenericBlock {
	
	ResourceLocation texture;
	ModelBase model;
	float scale; //uniform scale	
	
	public RenderLadder(ModelBase model, ResourceLocation texture, float scale) {
		super();
		this.texture = texture;
		this.model = model;
		this.scale = scale;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) {
		Tessellator tess = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.6f, -0.45f, -1.0f);
		//RenderHelper.enableGUIStandardItemLighting();
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
		//Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		//model.render(null, 0,0,0,0,0, 0.0625f);
		tess.startDrawingQuads();
		renderLadder(block,metadata);
		tess.draw();
		//RenderHelper.disableStandardItemLighting();
		GL11.glPopMatrix();
		//renderer.renderBlockAsItem(block, 1, 1.0f);
		
		//renderer.
		
		//renderLadder(0,0,0,block,renderer,5);
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		int meta = world.getBlockMetadata(x, y, z);
		
		//meta = meta % ((IBlockCamoChangeable)block).getMetaOffset();
		
		//System.out.println("META:"+meta);
		Tessellator tess = Tessellator.instance;
		tess.addTranslation(x, y, z);
		this.adjustLightFixture(world, x, y, z, block);
		tess.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
	    tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		//renderLadder(x,y,z,block,renderer,meta);
		renderLadder(block,meta);
		tess.addTranslation(-x, -y, -z);
		
		
		return true;
		/*GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		System.out.println("Meta:");
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		model.render(null, 0,0,0,0,0, 0.0625f);
		GL11.glPopMatrix();
		return true;*/
		//return super.renderWorldBlock(world, x, y, z, block, modelId, renderer);
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	/*protected void renderLadder(int x, int y, int z,
			Block block,RenderBlocks renderer, int meta){
		double px = 0.0625D;
		
		if (meta == 3){ //-X
			//minX,minY,minZ,MaxX,MaxY,MaxZ
			renderer.setRenderBounds(0, 0, 0, 2*px, 1, 2*px);
			renderer.renderStandardBlock(block, x, y, z);
			
			renderer.setRenderBounds(0, 0, 14*px, 2*px, 1, 1);
			renderer.renderStandardBlock(block, x, y, z);
			
			
			for (int i=0;i<16;i+=4){
				renderer.setRenderBounds(0, (i+1)*px, 2*px, 2*px, (i+3)*px, 14*px);
				renderer.renderStandardBlock(block, x, y, z);
			}
		} else if(meta == 1){ //-Z
			renderer.setRenderBounds(0, 0, 0, 2*px, 1, 2*px);
			renderer.renderStandardBlock(block, x, y, z);
			
			renderer.setRenderBounds(14*px, 0, 0, 1, 1, 2*px);
			renderer.renderStandardBlock(block, x, y, z);
			
			
			for (int i=0;i<16;i+=4){
				renderer.setRenderBounds(2*px, (i+1)*px, 0, 14*px, (i+3)*px, 2*px);
				renderer.renderStandardBlock(block, x, y, z);
			}
		} else if (meta==2){ //+X
			renderer.setRenderBounds(14*px, 0, 14*px, 1, 1, 1);
			renderer.renderStandardBlock(block, x, y, z);
			
			renderer.setRenderBounds(14*px, 0, 0, 1, 1, 2*px);
			renderer.renderStandardBlock(block, x, y, z);
			
			
			for (int i=0;i<16;i+=4){
				renderer.setRenderBounds(14*px, (i+1)*px, 2*px, 1, (i+3)*px, 14*px);
				renderer.renderStandardBlock(block, x, y, z);
			}
		} else { //+Z
			renderer.setRenderBounds(0, 0, 14*px, 2*px, 1, 1);
			renderer.renderStandardBlock(block, x, y, z);
			
			renderer.setRenderBounds(14*px, 0, 14*px, 1, 1, 1);
			renderer.renderStandardBlock(block, x, y, z);
			
			
			for (int i=0;i<16;i+=4){
				renderer.setRenderBounds(2*px, (i+1)*px, 14*px, 14*px, (i+3)*px, 1);
				renderer.renderStandardBlock(block, x, y, z);
			}
		}
	}*/
	
	protected void renderLadder(Block block, int meta){
		double px = 0.0625D;
		TessellatorCubeProper cube = new TessellatorCubeProper(block,meta);
		Tessellator tess = Tessellator.instance;
		//tess.startDrawingQuads();
		
		meta = meta % ((IBlockCamoChangeable)block).getMetaOffset();
		
		if (meta == 3){
			//minX,minY,minZ,MaxX,MaxY,MaxZ
			cube.drawStandardCube(tess, 0, 0, 0, 2*px, 1, 2*px);
			cube.drawStandardCube(tess,0, 0, 14*px, 2*px, 1, 1);
			
			for (int i=0;i<16;i+=4){
				cube.drawStandardCube(tess,0, (i+1)*px, 2*px, 2*px, (i+3)*px, 14*px);
			}
		} else if(meta == 1){
			cube.drawStandardCube(tess, 0, 0, 0, 2*px, 1, 2*px);
			cube.drawStandardCube(tess,14*px, 0, 0, 1, 1, 2*px);
			
			for (int i=0;i<16;i+=4){
				cube.drawStandardCube(tess,2*px, (i+1)*px, 0, 14*px, (i+3)*px, 2*px);
			}
		} else if (meta==2){		
			cube.drawStandardCube(tess, 14*px, 0, 14*px, 1, 1, 1);
			cube.drawStandardCube(tess,14*px, 0, 0, 1, 1, 2*px);
			
			for (int i=0;i<16;i+=4){
				cube.drawStandardCube(tess,14*px, (i+1)*px, 2*px, 1, (i+3)*px, 14*px);
			}
		} else {
			cube.drawStandardCube(tess, 0, 0, 14*px, 2*px, 1, 1);
			cube.drawStandardCube(tess,14*px, 0, 14*px, 1, 1, 1);
			
			for (int i=0;i<16;i+=4){
				cube.drawStandardCube(tess,2*px, (i+1)*px, 14*px, 14*px, (i+3)*px, 1);
			}
		}
		
		//tess.draw();
	}
	
    //Set the lighting stuff, so it changes it's brightness properly.      
    private void adjustLightFixture(IBlockAccess world, int i, int j, int k, Block block) {
            Tessellator tess = Tessellator.instance;
            //float brightness = block.getBlockBrightness(world, i, j, k);
            //As of MC 1.7+ block.getBlockBrightness() has become block.getLightValue():
            float brightness = block.getLightValue(world, i, j, k);
            int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
            int modulousModifier = skyLight % 65536;
            int divModifier = skyLight / 65536;
            tess.setColorOpaque_F(brightness, brightness, brightness);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,  (float) modulousModifier,  divModifier);
    }
}
