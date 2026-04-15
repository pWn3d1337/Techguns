package techguns.client.renderer.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;



public class TesselatorCube {
	public ResourceLocation texture;
	
	/*Bottome 0/1 values*/
	public double bo_x0 = 0.0D;
	public double bo_y0 = 0.0D;
	public double bo_x1 = 1.0D;
	public double bo_y1 = 1.0D;
	/*top*/
	public double t_x0 = 0.0D;
	public double t_x1 = 1.0D;
	public double t_y0 = 0.0D;
	public double t_y1 = 1.0D;
	
	/*front*/
	public double f_x0 = 0.0D;
	public double f_x1 = 1.0D;
	public double f_y0 = 0.0D;
	public double f_y1 = 1.0D;
	
	/*back*/
	public double ba_x0 = 0.0D;
	public double ba_x1 = 1.0D;
	public double ba_y0 = 0.0D;
	public double ba_y1 = 1.0D;
	
	/*right*/
	public double r_x0 = 0.0D;
	public double r_x1 = 1.0D;
	public double r_y0 = 0.0D;
	public double r_y1 = 1.0D;
	
	/*left*/
	public double l_x0 = 0.0D;
	public double l_x1 = 1.0D;
	public double l_y0 = 0.0D;
	public double l_y1 = 1.0D;
	
	public void drawCube(){
		Tessellator tess = Tessellator.instance;
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        tess.startDrawingQuads();
       

       drawTop(tess);
       drawBottom(tess);
       drawFront(tess);
       drawBack(tess);
       drawRight(tess);
       drawLeft(tess);
      
       

        tess.draw();
	}
	
	protected void drawTop(Tessellator tess){
        //top
		tess.setNormal(0.0F, 1.0F, 0.0F);
        tess.addVertexWithUV(0, 1, 0, t_x0, t_y1);
        tess.addVertexWithUV(0, 1, 1, t_x0, t_y0);
        tess.addVertexWithUV(1, 1, 1, t_x1, t_y0);
        tess.addVertexWithUV(1, 1, 0, t_x1, t_y1);
	}
	protected void drawBottom(Tessellator tess){
		 //bottom
		tess.setNormal(0.0F, -1.0F, 0.0F);
        tess.addVertexWithUV(1, 0, 0, bo_x1, bo_y0);
        tess.addVertexWithUV(1, 0, 1, bo_x1, bo_y1);
        tess.addVertexWithUV(0, 0, 1, bo_x0, bo_y1);
        tess.addVertexWithUV(0, 0, 0, bo_x0, bo_y0);
	}
	protected void drawFront(Tessellator tess){
		 //front
		tess.setNormal(1.0F, 0.0F, 0.0F);
        tess.addVertexWithUV(0, 0, 0, f_x0, f_y1);
        tess.addVertexWithUV(0, 1, 0, f_x0, f_y0);
        tess.addVertexWithUV(1, 1, 0, f_x1, f_y0);
        tess.addVertexWithUV(1, 0, 0, f_x1, f_y1);
	}
	protected void drawBack(Tessellator tess){
		  //back
		tess.setNormal(-1.0F, 0.0F, 0.0F);
        tess.addVertexWithUV(1, 0, 1, ba_x1, ba_y0);
        tess.addVertexWithUV(1, 1, 1, ba_x1, ba_y1);
        tess.addVertexWithUV(0, 1, 1, ba_x0, ba_y1);
        tess.addVertexWithUV(0, 0, 1, ba_x0, ba_y0);
	}
	protected void drawLeft(Tessellator tess){
		  //left
		tess.setNormal(0.0F, 0.0F, 1.0F);
        tess.addVertexWithUV(0, 0, 1, l_x1, l_y0);
        tess.addVertexWithUV(0, 1, 1, l_x1, l_y1);
        tess.addVertexWithUV(0, 1, 0, l_x0, l_y1);
        tess.addVertexWithUV(0, 0, 0, l_x0, l_y0);
	}
	protected void drawRight(Tessellator tess){
		 //right
		tess.setNormal(0.0F, 0.0F, -1.0F);
        tess.addVertexWithUV(1, 0, 0, r_x0, r_y0);
        tess.addVertexWithUV(1, 1, 0, r_x0, r_y1);
        tess.addVertexWithUV(1, 1, 1, r_x1, r_y1);
        tess.addVertexWithUV(1, 0, 1, r_x1, r_y0);
	}

}
