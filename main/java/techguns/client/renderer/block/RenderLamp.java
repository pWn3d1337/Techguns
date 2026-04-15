package techguns.client.renderer.block;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import techguns.blocks.BlockTGLamp;

public class RenderLamp extends RenderGenericBlock {
	
	ResourceLocation texture;
	ModelBase model;
	float scale; //uniform scale	
	
	public RenderLamp(ModelBase model, ResourceLocation texture, float scale) {
		super();
		this.texture = texture;
		this.model = model;
		this.scale = scale;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) {

		GL11.glPushMatrix();
		
		GL11.glRotatef(180.0f, 1, 0, 0);
		GL11.glScalef(1.5f, 1.5f, 1.50f);
		//GL11.glTranslatef(1.5f, 1.15f, -0.5f);
		if (metadata != 7 && metadata != 15) {
			GL11.glTranslatef(-0.55f, -0.80f, -0.45f);
		}else {
			GL11.glTranslatef(-1.0f, -0.05f, 0.05f);
		}
		/*Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		model.render(null, 0,0,0,0,0, 0.0625f);*/
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		renderLampInventory(block,metadata);
		tess.draw();
		GL11.glPopMatrix();
		//renderer.renderBlockAsItem(block, 1, 1.0f);
		
		//renderer.
		
		//renderLadder(0,0,0,block,renderer,5);
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		int meta = world.getBlockMetadata(x, y, z);
		
		//System.out.println("META:"+meta);
		Tessellator tess = Tessellator.instance;
		tess.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
	    tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		
		renderLamp(world,x,y,z,block,renderer,meta);
		
		return true;

	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}
	
	protected void renderLampInventory(Block block, int meta){
		TessellatorCubeProper cube = new TessellatorCubeProper(block, meta);
		
		Tessellator tess = Tessellator.instance;

		final int width = 32; //width of block in pixels (double res)
		double px = 1.0/(double)width; //0.03125D; //double res
		int w1 = 16; //lamp socket width in pixels (doubleres);
		int w2 = 14; //lamp width in pixels (doubleres);
		int h1 = 2; //lamp socket height
		int h2 = 4; //lamp height
		
		double minW1 = ((width-w1)/2.0)*px;
		double maxW1 = (width - ((width-w1)/2.0))*px;
		double minW2 = ((width-w2)/2.0)*px;
		double maxW2 = (width - ((width-w2)/2.0))*px;
		
		double minH1 = (h1)*px;
		double maxH1 = (width-h1)*px;
		double minH2 = (h1+h2)*px;
		double maxH2 = (width-(h1+h2))*px;
		
		if (meta == 1 || meta == 9 || meta==0 || meta==8){ //TOP (Y+)
			//socket
			cube.drawStandardCube(tess, minW1, maxH1, minW1, maxW1, 1, maxW1);
			
			//lamp
			cube.drawStandardCube(tess, minW2, maxH2, minW2, maxW2, maxH1, maxW2);
		}else if (meta == 2 || meta == 10) { //BOTTOM (Y-)
			//socket
			cube.drawStandardCube(tess, minW1, 0, minW1, maxW1, minH1, maxW1);
			
			//lamp
			cube.drawStandardCube(tess, minW2, minH1, minW2, maxW2, minH2, maxW2);
		} else if(meta == 3 || meta == 11) { //SOUTH (Z+)
			//socket
			cube.drawStandardCube(tess,minW1, minW1, maxH1, maxW1, maxW1, 1);
			
			//lamp
			cube.drawStandardCube(tess, minW2, minW2, maxH2, maxW2, maxW2, maxH1);
		} else if (meta == 4 || meta == 12) { //NORTH (Z-)
			//socket
			cube.drawStandardCube(tess, minW1, minW1,0,  maxW1, maxW1, minH1);
			
			//lamp
			cube.drawStandardCube(tess, minW2, minW2, minH1, maxW2, maxW2, minH2);
		} else if(meta == 5 || meta == 13) { //EAST (X+)
			//socket
			cube.drawStandardCube(tess, maxH1, minW1, minW1, 1, maxW1, maxW1);
			
			//lamp
			cube.drawStandardCube(tess, maxH2, minW2, minW2,  maxH1, maxW2, maxW2);
		} else if (meta == 6 || meta == 14) { //WEST (X-)
			//socket
			cube.drawStandardCube(tess, 0, minW1, minW1, minH1, maxW1, maxW1);
			
			//lamp
			cube.drawStandardCube(tess, minH1, minW2, minW2, minH2, maxW2, maxW2);					
		} else if (meta == 7 || meta == 15) { //LANTERN
			cube.drawStandardCube(tess, 0.375, 0.125, 0.375, 0.625, 0.875, 0.625);
			
			cube.drawStandardCube(tess, 0.28125, 0.1875, 0.28125, 0.71875, 0.25, 0.71875);
			cube.drawStandardCube(tess, 0.28125, 0.75, 0.28125, 0.71875, 0.8125, 0.71875);
			
			cube.drawStandardCube(tess, 0.3125, 0.25, 0.3125, 0.6875, 0.75, 0.6875);
		}
		
	}
	
	protected void renderLamp(IBlockAccess world,int x, int y, int z,
			Block block,RenderBlocks renderer, int meta){
		final int width = 32; //width of block in pixels (double res)
		double px = 1.0/(double)width; //0.03125D; //double res
		int w1 = 16; //lamp socket width in pixels (doubleres);
		int w2 = 14; //lamp width in pixels (doubleres);
		int h1 = 2; //lamp socket height
		int h2 = 4; //lamp height
		
		double minW1 = ((width-w1)/2.0)*px;
		double maxW1 = (width - ((width-w1)/2.0))*px;
		double minW2 = ((width-w2)/2.0)*px;
		double maxW2 = (width - ((width-w2)/2.0))*px;
		
		double minH1 = (h1)*px;
		double maxH1 = (width-h1)*px;
		double minH2 = (h1+h2)*px;
		double maxH2 = (width-(h1+h2))*px;
		
		double d = 0.001; //correction
		minW2+=d;
		maxW2-=d;
		minH2-=d;
		maxH2+=d;
		minH1-=d;
		maxH1+=d;
		//
		int dir = meta % 8;
		
		
		TessellatorCubeProper cube = new TessellatorCubeProper(block, meta);
		
		Tessellator tess = Tessellator.instance;
		
		tess.addTranslation(x, y, z);
		
	    //tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		//tess.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
	    //tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		//tess.startDrawingQuads();
		
		//minX,minY,minZ,MaxX,MaxY,MaxZ
		if (meta == 1 || meta == 9){ //TOP (Y+)
			//socket
			cube.drawStandardCube(tess, minW1, maxH1, minW1, maxW1, 1, maxW1);
			
			//lamp
			cube.drawStandardCube(tess, minW2, maxH2, minW2, maxW2, maxH1, maxW2);
		}else if (meta == 2 || meta == 10) { //BOTTOM (Y-)
			//socket
			cube.drawStandardCube(tess, minW1, 0, minW1, maxW1, minH1, maxW1);
			
			//lamp
			cube.drawStandardCube(tess, minW2, minH1, minW2, maxW2, minH2, maxW2);
		} else if(meta == 3 || meta == 11) { //SOUTH (Z+)
			//socket
			cube.drawStandardCube(tess,minW1, minW1, maxH1, maxW1, maxW1, 1);
			
			//lamp
			cube.drawStandardCube(tess, minW2, minW2, maxH2, maxW2, maxW2, maxH1);
		} else if (meta == 4 || meta == 12) { //NORTH (Z-)
			//socket
			cube.drawStandardCube(tess, minW1, minW1,0,  maxW1, maxW1, minH1);
			
			//lamp
			cube.drawStandardCube(tess, minW2, minW2, minH1, maxW2, maxW2, minH2);
		} else if(meta == 5 || meta == 13) { //EAST (X+)
			//socket
			cube.drawStandardCube(tess, maxH1, minW1, minW1, 1, maxW1, maxW1);
			
			//lamp
			cube.drawStandardCube(tess, maxH2, minW2, minW2,  maxH1, maxW2, maxW2);
		} else if (meta == 6 || meta == 14) { //WEST (X-)
			//socket
			cube.drawStandardCube(tess, 0, minW1, minW1, minH1, maxW1, maxW1);
			
			//lamp
			cube.drawStandardCube(tess, minH1, minW2, minW2, minH2, maxW2, maxW2);					
		} else if (meta == 7 || meta ==15) { //Lantern
			
			//Render Lamp
			
			
			cube.drawStandardCube(tess, 0.375, 0.125, 0.375, 0.625, 0.875, 0.625);
			
			cube.drawStandardCube(tess, 0.28125, 0.1875, 0.28125, 0.71875, 0.25-d, 0.71875);
			cube.drawStandardCube(tess, 0.28125, 0.75+d, 0.28125, 0.71875, 0.8125, 0.71875);
			
			cube.drawStandardCube(tess, 0.3125, 0.25-d, 0.3125, 0.6875, 0.75+d, 0.6875);
			
			//Render connections
			
			BlockTGLamp lamp = (BlockTGLamp) block;
			
			
			boolean top = lamp.canConnectTo(world, x, y+1, z);
			boolean bottom = lamp.canConnectTo(world, x, y-1, z);
			boolean north = lamp.canConnectTo(world, x, y, z-1);
			boolean east = lamp.canConnectTo(world, x+1, y, z);
			boolean south = lamp.canConnectTo(world, x, y, z+1);
			boolean west = lamp.canConnectTo(world, x-1, y, z);
			
			if (top) { //+y
				cube.drawStandardCube(tess, 0.375, 0.875, 0.375, 0.625, 1, 0.625);
			}
			if (bottom) { //+y
				cube.drawStandardCube(tess, 0.375, 0.0, 0.375, 0.625, 0.125, 0.625);
			}			
			if (north) { //-z
				cube.drawStandardCube(tess, 0.375, 0.1875, 0.0, 0.625, 0.25-d, 0.28125);
				cube.drawStandardCube(tess, 0.375, 0.75+d, 0.0, 0.625, 0.8125, 0.28125);
			}
			if (east) { //-x
				cube.drawStandardCube(tess, 0.71875, 0.1875, 0.375, 1, 0.25-d, 0.625);
				cube.drawStandardCube(tess, 0.71875, 0.75+d, 0.375, 1, 0.8125, 0.625);
			}
			if (south) { //+z
				cube.drawStandardCube(tess, 0.375, 0.1875, 0.71875, 0.625, 0.25-d, 1);
				cube.drawStandardCube(tess, 0.375, 0.75+d, 0.71875, 0.625, 0.8125, 1);
			}		
			if (west) { //-x
				cube.drawStandardCube(tess, 0, 0.1875, 0.375, 0.28125, 0.25-d, 0.625);
				cube.drawStandardCube(tess, 0, 0.75+d, 0.375, 0.28125, 0.8125, 0.625);
			}
		}
		
		tess.addTranslation(-x, -y, -z);

	}

//	protected void renderLamp(int x, int y, int z,
//			Block block,RenderBlocks renderer, int meta){
//		final int width = 32; //width of block in pixels (double res)
//		double px = 1.0/(double)width; //0.03125D; //double res
//		int w1 = 16; //lamp socket width in pixels (doubleres);
//		int w2 = 14; //lamp width in pixels (doubleres);
//		int h1 = 2; //lamp socket height
//		int h2 = 4; //lamp height
//		
//		double minW1 = ((width-w1)/2.0)*px;
//		double maxW1 = (width - ((width-w1)/2.0))*px;
//		double minW2 = ((width-w2)/2.0)*px;
//		double maxW2 = (width - ((width-w2)/2.0))*px;
//		
//		double minH1 = (h1)*px;
//		double maxH1 = (width-h1)*px;
//		double minH2 = (h1+h2)*px;
//		double maxH2 = (width-(h1+h2))*px;
//		
//		//minX,minY,minZ,MaxX,MaxY,MaxZ
//		if (meta == 1 || meta == 9){ //TOP (Y+)
//			//socket
//			renderer.setRenderBounds(minW1, maxH1, minW1, maxW1, 1, maxW1);
//			renderer.renderStandardBlock(block, x, y, z);
//			
//			//lamp
//			renderer.setRenderBounds(minW2, maxH2, minW2, maxW2, maxH1, maxW2);
//			renderer.renderStandardBlock(block, x, y, z);
//		}else if (meta == 2 || meta == 10) { //BOTTOM (Y-)
//			//socket
//			renderer.setRenderBounds(minW1, 0, minW1, maxW1, minH1, maxW1);
//			renderer.renderStandardBlock(block, x, y, z);
//			
//			//lamp
//			renderer.setRenderBounds(minW2, minH1, minW2, maxW2, minH2, maxW2);
//			renderer.renderStandardBlock(block, x, y, z);
//		} else if(meta == 3 || meta == 11) { //SOUTH (Z+)
//			//socket
//			renderer.setRenderBounds(minW1, minW1, maxH1, maxW1, maxW1, 1);
//			renderer.renderStandardBlock(block, x, y, z);
//			
//			//lamp
//			renderer.setRenderBounds(minW2, minW2, maxH2, maxW2, maxW2, maxH1);
//			renderer.renderStandardBlock(block, x, y, z);
//		} else if (meta == 4 || meta == 12) { //NORTH (Z-)
//			//socket
//			renderer.setRenderBounds(minW1, minW1,0,  maxW1, maxW1, minH1);
//			renderer.renderStandardBlock(block, x, y, z);
//			
//			//lamp
//			renderer.setRenderBounds(minW2, minW2, minH1, maxW2, maxW2, minH2);
//			renderer.renderStandardBlock(block, x, y, z);
//		} else if(meta == 5 || meta == 13) { //EAST (X+)
//			//socket
//			renderer.setRenderBounds(maxH1, minW1, minW1, 1, maxW1, maxW1);
//			renderer.renderStandardBlock(block, x, y, z);
//			
//			//lamp
//			renderer.setRenderBounds(maxH2, minW2, minW2,  maxH1, maxW2, maxW2);
//			renderer.renderStandardBlock(block, x, y, z);
//		} else if (meta == 6 || meta == 14) { //WEST (X-)
//			//socket
//			renderer.setRenderBounds(0, minW1, minW1, minH1, maxW1, maxW1);
//			renderer.renderStandardBlock(block, x, y, z);
//			
//			//lamp
//			renderer.setRenderBounds( minH1, minW2, minW2, minH2, maxW2, maxW2);
//			renderer.renderStandardBlock(block, x, y, z);
//						
//		}
//	}
}
