package techguns.client.renderer.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

public class TessellatorCubeProper {
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
	
	public double minX = 0;
	public double minY = 0;
	public double minZ = 0;
	public double maxX = 1;
	public double maxY = 1;
	public double maxZ = 1;
	
	public Block block;
	public int meta;
	
	public TessellatorCubeProper(Block block, int meta)
	{
		this.block = block;
		this.meta = meta;
	}
	public void drawStandardCube(Tessellator tess, double minX, double minY, double minZ, double maxX, double maxY, double maxZ){
		this.drawCube(tess, minX, minY, minZ, maxX, maxY, maxZ, false);
	}
	
	public void drawStandardCube(Tessellator tess, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, IIcon overrideIcon){
		this.drawCube(tess, minX, minY, minZ, maxX, maxY, maxZ, false, overrideIcon);
	}
	
	public void drawStandardCube(Tessellator tess, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, boolean scaleUVs, IIcon overrideIcon){
		this.drawCube(tess, minX, minY, minZ, maxX, maxY, maxZ, scaleUVs, overrideIcon);
	}
	
	public void drawCube(Tessellator tess, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, boolean scaleUVs){
		this.drawCube(tess, minX, minY, minZ, maxX, maxY, maxZ, false, null);
	}
	
	public void drawCube(Tessellator tess, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, boolean scaleUVs,  IIcon overrideIcon){
	   //Tessellator tess = Tessellator.instance;
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
		
		IIcon iicon;
		if (scaleUVs) {
			//Bottom			
			if (overrideIcon == null) iicon = block.getIcon(0, meta);
			else iicon = overrideIcon;
			bo_x0 = (double)iicon.getMinU();
			bo_y0 = (double)iicon.getMinV();
			bo_x1 = (double)iicon.getMaxU();
			bo_y1 = (double)iicon.getMaxV();	
			//Top
			if (overrideIcon == null) iicon = block.getIcon(1, meta);
			else iicon = overrideIcon;
			t_x0 = (double)iicon.getMinU();
			t_y0 = (double)iicon.getMinV();
			t_x1 = (double)iicon.getMaxU();
			t_y1 = (double)iicon.getMaxV();	
			//front
			if (overrideIcon == null) iicon = block.getIcon(2, meta);
			else iicon = overrideIcon;
			f_x0 = (double)iicon.getMinU();
			f_y0 = (double)iicon.getMinV();
			f_x1 = (double)iicon.getMaxU();
			f_y1 = (double)iicon.getMaxV();	
			//back
			if (overrideIcon == null) iicon = block.getIcon(3, meta);
			else iicon = overrideIcon;
			ba_x0 = (double)iicon.getMinU();
			ba_y1 = (double)iicon.getMinV();
			ba_x1 = (double)iicon.getMaxU();
			ba_y0 = (double)iicon.getMaxV();	
			//left
			if (overrideIcon == null) iicon = block.getIcon(4, meta);
			else iicon = overrideIcon;
			l_x0 = (double)iicon.getMinU();
			l_y1 = (double)iicon.getMinV();
			l_x1 = (double)iicon.getMaxU();
			l_y0 = (double)iicon.getMaxV();	
			//right
			if (overrideIcon == null) iicon = block.getIcon(5, meta);
			else iicon = overrideIcon;
			r_x0 = (double)iicon.getMinU();
			r_y1 = (double)iicon.getMinV();
			r_x1 = (double)iicon.getMaxU();
			r_y0 = (double)iicon.getMaxV();	
			
		}else {
			//bottom
			if (overrideIcon == null) iicon = block.getIcon(0, meta);
			else iicon = overrideIcon;
			bo_x0 = (double)iicon.getInterpolatedU(minX*16D);
			bo_x1 = (double)iicon.getInterpolatedU(maxX*16D);
			bo_y0 = (double)iicon.getInterpolatedV(minZ*16D);
			bo_y1 = (double)iicon.getInterpolatedV(maxZ*16D);
				
			//top		
			if (overrideIcon == null) iicon = block.getIcon(1, meta);
			else iicon = overrideIcon;
			t_x0 = (double)iicon.getInterpolatedU(minX*16D);
			t_x1 = (double)iicon.getInterpolatedU(maxX*16D);
			t_y0 = (double)iicon.getInterpolatedV(minZ*16D);
			t_y1 = (double)iicon.getInterpolatedV(maxZ*16D);
			
			//front
			if (overrideIcon == null) iicon = block.getIcon(2, meta);
			else iicon = overrideIcon;
			f_x0 = (double)iicon.getInterpolatedU(minX*16D);
			f_x1 = (double)iicon.getInterpolatedU(maxX*16D);
			f_y0 = (double)iicon.getInterpolatedV(minY*16D);
			f_y1 = (double)iicon.getInterpolatedV(maxY*16D);			
			
			//back
			if (overrideIcon == null) iicon = block.getIcon(3, meta);
			else iicon = overrideIcon;
			ba_x0 = (double)iicon.getInterpolatedU(minX*16D);
			ba_x1 = (double)iicon.getInterpolatedU(maxX*16D);
			ba_y1 = (double)iicon.getInterpolatedV(minY*16D);
			ba_y0 = (double)iicon.getInterpolatedV(maxY*16D);
			//left
			if (overrideIcon == null) iicon = block.getIcon(4, meta);
			else iicon = overrideIcon;
			l_x0 = (double)iicon.getInterpolatedU(minZ*16D);
			l_x1 = (double)iicon.getInterpolatedU(maxZ*16D);
			l_y1 = (double)iicon.getInterpolatedV(minY*16D);
			l_y0 = (double)iicon.getInterpolatedV(maxY*16D);
			
			//right
			if (overrideIcon == null) iicon = block.getIcon(5, meta);
			else iicon = overrideIcon;
			r_x0 = (double)iicon.getInterpolatedU(minZ*16D);
			r_x1 = (double)iicon.getInterpolatedU(maxZ*16D);
			r_y1 = (double)iicon.getInterpolatedV(minY*16D);
			r_y0 = (double)iicon.getInterpolatedV(maxY*16D);
			
		}	
		
	     drawBottom(tess);
	     drawTop(tess);
	     drawFront(tess);
	     drawBack(tess);
	     drawLeft(tess);
	     drawRight(tess);
	     //tess.draw();
		}
		
	protected void drawTop(Tessellator tess){
        //top
		tess.setNormal(0.0F, 1.0F, 0.0F);
        tess.addVertexWithUV(minX, maxY, minZ, t_x0, t_y0);
        tess.addVertexWithUV(minX, maxY, maxZ, t_x0, t_y1);
        tess.addVertexWithUV(maxX, maxY, maxZ, t_x1, t_y1);
        tess.addVertexWithUV(maxX, maxY, minZ, t_x1, t_y0);

	}
	protected void drawBottom(Tessellator tess){
		 //bottom
		tess.setNormal(0.0F, -1.0F, 0.0F);
        tess.addVertexWithUV(maxX, minY, minZ, bo_x1, bo_y0);
        tess.addVertexWithUV(maxX, minY, maxZ, bo_x1, bo_y1);
        tess.addVertexWithUV(minX, minY, maxZ, bo_x0, bo_y1);
        tess.addVertexWithUV(minX, minY, minZ, bo_x0, bo_y0);
	}
	protected void drawFront(Tessellator tess){
		 //front
		tess.setNormal(1.0F, 0.0F, 0.0F);
        tess.addVertexWithUV(minX, minY, minZ, f_x0, f_y1);
        tess.addVertexWithUV(minX, maxY, minZ, f_x0, f_y0);
        tess.addVertexWithUV(maxX, maxY, minZ, f_x1, f_y0);
        tess.addVertexWithUV(maxX, minY, minZ, f_x1, f_y1);
	}
	protected void drawBack(Tessellator tess){
		  //back
		tess.setNormal(-1.0F, 1.0F, 0.0F);
        tess.addVertexWithUV(maxX, minY, maxZ, ba_x1, ba_y0);
        tess.addVertexWithUV(maxX, maxY, maxZ, ba_x1, ba_y1);
        tess.addVertexWithUV(minX, maxY, maxZ, ba_x0, ba_y1);
        tess.addVertexWithUV(minX, minY, maxZ, ba_x0, ba_y0);
	}
	protected void drawLeft(Tessellator tess){
		  //left
		tess.setNormal(0.0F, 0.0F, 1.0F);
        tess.addVertexWithUV(minX, minY, maxZ, l_x1, l_y0);
        tess.addVertexWithUV(minX, maxY, maxZ, l_x1, l_y1);
        tess.addVertexWithUV(minX, maxY, minZ, l_x0, l_y1);
        tess.addVertexWithUV(minX, minY, minZ, l_x0, l_y0);
	}
	protected void drawRight(Tessellator tess){
		 //right
		tess.setNormal(0.0F, 0.0F, -1.0F);
        tess.addVertexWithUV(maxX, minY, minZ, r_x0, r_y0);
        tess.addVertexWithUV(maxX, maxY, minZ, r_x0, r_y1);
        tess.addVertexWithUV(maxX, maxY, maxZ, r_x1, r_y1);
        tess.addVertexWithUV(maxX, minY, maxZ, r_x1, r_y0);
	}
}
