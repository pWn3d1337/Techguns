package techguns.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import techguns.blocks.machines.OreDrillBlock;
import techguns.client.renderer.block.OreDrillCube;
import techguns.tileentities.OreDrillTileEntMaster;

public class RenderOreDrill extends TileEntitySpecialRenderer {
	private static final int DRILLERCUBESPERBLOCK=4;
	private ResourceLocation texture = new ResourceLocation("techguns","textures/blocks/drillhead.png");
	private OreDrillCube tessellatorcube = new OreDrillCube(texture);
	
	private ResourceLocation texture2 = new ResourceLocation("techguns","textures/blocks/drillheadObsidian.png");
	private OreDrillCube tessellatorcube2 = new OreDrillCube(texture2);
	
	private ResourceLocation texture3 = new ResourceLocation("techguns","textures/blocks/drillheadCarbon.png");
	private OreDrillCube tessellatorcube3 = new OreDrillCube(texture3);

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTick) {
		OreDrillTileEntMaster tile = (OreDrillTileEntMaster) te;
		
		if (tile.isComplete()){
		
			byte direction =tile.dir;
			
			int[] vec = OreDrillBlock.getNextBlockInDirOffset(0,0,0, direction, tile.enginesize+1);
			int[] dir = OreDrillBlock.getNextBlockInDir(0,0,0, direction);
			
			GL11.glPushMatrix();
			    //This is setting the initial location.
	        GL11.glTranslatef((float) x +0.5f-vec[0], (float) y+1.25f -vec[1], (float) z+0.5f -vec[2]);
	        
	        GL11.glPushMatrix();
	        int rot = (int) (tile.getWorldObj().getTotalWorldTime()%360);
	        

           // System.out.println("Rotation:"+tile.rotation);
            if(dir[1]<0){
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            } else if (dir[1]>0) {
            	GL11.glTranslatef(0, -1.5f, 0);
            } else if (dir[0]!=0){
            	GL11.glRotatef(-90F*dir[0], 0.0F, 0.0F, 1.0F);
            	GL11.glTranslatef(dir[0]*0.75f, 0, 0);
            	GL11.glTranslatef(0, -0.75f, 0);
            } else if (dir[2]!=0){
            	GL11.glRotatef(90F*dir[2], 1.0F, 0.0F, 0.0F);
            	GL11.glTranslatef(0, 0, dir[2]*0.75f);
            	GL11.glTranslatef(0, -0.75f, 0);
            }
            GL11.glTranslatef(0, 2*(tile.enginesize+1), 0);
            
            //System.out.println("Rot:"+rot+" PartialTick:"+partialTick);
            if(tile.hasDrill&&tile.isRedstoneEnabled()&&tile.haspower){
            	GL11.glRotatef(rot*10f + ((1f/36f)*partialTick), 0, 1, 0);
            }
            
	        //Minecraft.getMinecraft().renderEngine.bindTexture(texture);
            if (tile.hasDrill){
            	if (tile.drillType==1){
            		drawDriller(tile,tessellatorcube);
            	} else if (tile.drillType==2){
            		drawDriller(tile,tessellatorcube2);
            	} else if (tile.drillType==3){
            		drawDriller(tile,tessellatorcube3);
            	}
            } else {
            	drawDrillRod(tile);
            }
	        
	        
	        GL11.glPopMatrix();
	        GL11.glPopMatrix();
		}
	}

	private void drawDriller(OreDrillTileEntMaster tile, OreDrillCube cube){

		//Tessellator tess = Tessellator.instance;
		int length = tile.rodsize;
		
		GL11.glPushMatrix();
		
		//tess.startDrawingQuads();
		int[] axis = OreDrillBlock.getNextBlockInDir(0, 0, 0, tile.rotation);
		float height = 1.0f/DRILLERCUBESPERBLOCK;
		float pos = height;
		boolean negative=false;
		/*if (axis[0]<0 || axis[1]<0 || axis[2]<0){
			negative=true;
		}*/
		axis[0]=0;
		axis[1]=1;
		axis[2]=0;
		
		float max = (float) Math.log((length*DRILLERCUBESPERBLOCK+1.0f));
		float radiusfactor=1.0f;
		switch(tile.radius){
			case 0:
			case 1:
				radiusfactor=0.35f;
				break;
			case 2:
				radiusfactor=0.5f;
				break;
			case 3:
				radiusfactor=0.55f;
				break;
			case 4:
				radiusfactor=0.60f;
				break;
		} //= tile.radius>=2?tile.radius*0.25f:0.35f;

		
		int[] worldpos = OreDrillBlock.getNextBlockInDirOffset(tile.xCoord, tile.yCoord, tile.zCoord, tile.rotation, tile.enginesize);
		//this.adjustLightFixture(tile.getWorldObj(), worldpos[0], worldpos[1], worldpos[2], tile.getBlockType());
		
		for (int i=0;i<length*DRILLERCUBESPERBLOCK;i++){
			boolean rotate=false;
			if(i%2==1){
				rotate=true;
			}
			float factor = (float) Math.log((length*DRILLERCUBESPERBLOCK)-i+1.0f);
			float size = ((tile.radius==0?1.0f:tile.radius*1.0f)*factor)*(1.0f/max)*radiusfactor;
			
			GL11.glTranslatef(axis[0]*height, axis[1]*height, axis[2]*height);
			
			if (rotate){
				GL11.glPushMatrix();
				GL11.glRotatef(45.0f, Math.abs(axis[0]),Math.abs(axis[1]),Math.abs(axis[2]));
			}
			
			if(i % DRILLERCUBESPERBLOCK==0){
				this.adjustLightFixture(tile.getWorldObj(), worldpos[0], worldpos[1], worldpos[2], tile.getBlockType());
				worldpos = OreDrillBlock.getNextBlockInDir(worldpos[0],worldpos[1],worldpos[2],tile.rotation);
			}
			
			cube.drawCube(axis[0]==0?size:pos, (axis[1]==0?size:pos), (axis[2]==0?size:pos));
			
			/*tess.addVertexWithUV(0, height*i, 0, 0, 0);
			tess.addVertexWithUV(size, height*i, 0, 1, 0);
			tess.addVertexWithUV(0, height*i, size, 0, 1);
			tess.addVertexWithUV(size, height*i, size, 1, 1);
			
			tess.addVertexWithUV(0, height*(i+1), 0, 0, 0);
			tess.addVertexWithUV(size, height*(i+1), 0, 1, 0);
			tess.addVertexWithUV(0, height*(i+1), size, 0, 1);
			tess.addVertexWithUV(size, height*(i+1), size, 1, 1);*/
			
			if(rotate){
				GL11.glPopMatrix();
			}
		
			//pos = pos+((negative?-1:1)*height);
		}
		GL11.glPopMatrix();
		//tess.draw();
	}
	
	private void drawDrillRod(OreDrillTileEntMaster tile){

	//	Tessellator tess = Tessellator.instance;
		int length = tile.rodsize;
		
		GL11.glPushMatrix();
		
		//tess.startDrawingQuads();
		int[] axis = OreDrillBlock.getNextBlockInDir(0, 0, 0, tile.rotation);
		float height = 0.5f;
		float pos = height;
	//	boolean negative=false;
		/*if (axis[0]<0 || axis[1]<0 || axis[2]<0){
			negative=true;
		}*/
		axis[0]=0;
		axis[1]=1;
		axis[2]=0;
		
		//float max = (float) Math.log((length*DRILLERCUBESPERBLOCK+1.0f));
		/*float radiusfactor=1.0f;
		switch(tile.radius){
			case 0:
			case 1:
				radiusfactor=0.35f;
				break;
			case 2:
				radiusfactor=0.5f;
				break;
			case 3:
				radiusfactor=0.55f;
				break;
			case 4:
				radiusfactor=0.60f;
				break;
		}*/ //= tile.radius>=2?tile.radius*0.25f:0.35f;

		
		int[] worldpos = OreDrillBlock.getNextBlockInDirOffset(tile.xCoord, tile.yCoord, tile.zCoord, tile.rotation, tile.enginesize);
		//this.adjustLightFixture(tile.getWorldObj(), worldpos[0], worldpos[1], worldpos[2], tile.getBlockType());
		
		float f = -0.4f;
		GL11.glTranslatef(axis[0]*height*f, axis[1]*height*f, axis[2]*height*f);
		for (int i=0;i<length;i++){
			
		//	float factor = 0.5f;//(float) Math.log((length*DRILLERCUBESPERBLOCK)-i+1.0f);
			float size = 0.2f;//((tile.radius==0?1.0f:tile.radius*1.0f)*factor)*(1.0f/max)*radiusfactor;
			
			GL11.glTranslatef(axis[0]*height*2, axis[1]*height*2, axis[2]*height*2);
			
			//if (rotate){
				GL11.glPushMatrix();
				GL11.glRotatef(45.0f, Math.abs(axis[0]),Math.abs(axis[1]),Math.abs(axis[2]));
			//}
			
			//if(i % DRILLERCUBESPERBLOCK==0){
				this.adjustLightFixture(tile.getWorldObj(), worldpos[0], worldpos[1], worldpos[2], tile.getBlockType());
				worldpos = OreDrillBlock.getNextBlockInDir(worldpos[0],worldpos[1],worldpos[2],tile.rotation);
			//}
			
			tessellatorcube.drawCube(axis[0]==0?size:pos, (axis[1]==0?size:pos), (axis[2]==0?size:pos));
			
			/*tess.addVertexWithUV(0, height*i, 0, 0, 0);
			tess.addVertexWithUV(size, height*i, 0, 1, 0);
			tess.addVertexWithUV(0, height*i, size, 0, 1);
			tess.addVertexWithUV(size, height*i, size, 1, 1);
			
			tess.addVertexWithUV(0, height*(i+1), 0, 0, 0);
			tess.addVertexWithUV(size, height*(i+1), 0, 1, 0);
			tess.addVertexWithUV(0, height*(i+1), size, 0, 1);
			tess.addVertexWithUV(size, height*(i+1), size, 1, 1);*/
			
			//if(rotate){
				GL11.glPopMatrix();
			//}
		
			//pos = pos+((negative?-1:1)*height);
		}
		GL11.glPopMatrix();
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
