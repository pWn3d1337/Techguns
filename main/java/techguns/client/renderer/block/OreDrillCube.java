package techguns.client.renderer.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class OreDrillCube extends TesselatorCube {

	
	
	public OreDrillCube(ResourceLocation texture) {
		this.texture=texture;
	}

	public void drawCube(float sizeX, float sizeY, float sizeZ) {
		Tessellator tess = Tessellator.instance;
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        tess.startDrawingQuads();
       

       drawTop(tess,sizeX,sizeY,sizeZ);
       drawBottom(tess,sizeX,sizeY,sizeZ);
       drawFront(tess,sizeX,sizeY,sizeZ);
      drawBack(tess,sizeX,sizeY,sizeZ);
      drawRight(tess,sizeX,sizeY,sizeZ);
      drawLeft(tess,sizeX,sizeY,sizeZ);
      
       

        tess.draw();
	}
	
	protected void drawTop(Tessellator tess,float sizeX, float sizeY, float sizeZ){
        //top
        tess.addVertexWithUV(-sizeX, sizeY, -sizeZ, t_x0, t_y1);
        tess.addVertexWithUV(-sizeX, sizeY, sizeZ, t_x0, t_y0);
        tess.addVertexWithUV(sizeX, sizeY, sizeZ, t_x1, t_y0);
        tess.addVertexWithUV(sizeX, sizeY, -sizeZ, t_x1, t_y1);
	}
	protected void drawBottom(Tessellator tess,float sizeX, float sizeY, float sizeZ){
		 //bottom
        tess.addVertexWithUV(sizeX, -sizeY, -sizeZ, bo_x1, bo_y0);
        tess.addVertexWithUV(sizeX, -sizeY, sizeZ, bo_x1, bo_y1);
        tess.addVertexWithUV(-sizeX, -sizeY, sizeZ, bo_x0, bo_y1);
        tess.addVertexWithUV(-sizeX, -sizeY, -sizeZ, bo_x0, bo_y0);
	}
	protected void drawFront(Tessellator tess,float sizeX, float sizeY, float sizeZ){
		 //front
        tess.addVertexWithUV(-sizeX, -sizeY, -sizeZ, f_x0, f_y1);
        tess.addVertexWithUV(-sizeX, sizeY, -sizeZ, f_x0, f_y0);
        tess.addVertexWithUV(sizeX, sizeY, -sizeZ, f_x1, f_y0);
        tess.addVertexWithUV(sizeX, -sizeY, -sizeZ, f_x1, f_y1);
	}
	protected void drawBack(Tessellator tess,float sizeX, float sizeY, float sizeZ){
		  //back
        tess.addVertexWithUV(sizeX, -sizeY, sizeZ, ba_x1, ba_y0);
        tess.addVertexWithUV(sizeX, sizeY, sizeZ, ba_x1, ba_y1);
        tess.addVertexWithUV(-sizeX, sizeY, sizeZ, ba_x0, ba_y1);
        tess.addVertexWithUV(-sizeX, -sizeY, sizeZ, ba_x0, ba_y0);
	}
	protected void drawLeft(Tessellator tess,float sizeX, float sizeY, float sizeZ){
		  //left
        tess.addVertexWithUV(-sizeX, -sizeY, sizeZ, l_x1, l_y0);
        tess.addVertexWithUV(-sizeX, sizeY, sizeZ, l_x1, l_y1);
        tess.addVertexWithUV(-sizeX, sizeY, -sizeZ, l_x0, l_y1);
        tess.addVertexWithUV(-sizeX, -sizeY, -sizeZ, l_x0, l_y0);
	}
	protected void drawRight(Tessellator tess,float sizeX, float sizeY, float sizeZ){
		 //right
        tess.addVertexWithUV(sizeX, -sizeY, -sizeZ, r_x0, r_y0);
        tess.addVertexWithUV(sizeX, sizeY, -sizeZ, r_x0, r_y1);
        tess.addVertexWithUV(sizeX, sizeY, sizeZ, r_x1, r_y1);
        tess.addVertexWithUV(sizeX, -sizeY, sizeZ, r_x1, r_y0);
	}
	
}
