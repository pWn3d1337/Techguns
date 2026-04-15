package techguns.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.models.blocks.ModelMachine;
import techguns.client.renderer.TGRenderHelper;
import techguns.tileentities.ExplosiveChargeTileEnt;

public class RenderExplosiveCharge extends TileEntitySpecialRenderer {
	 protected ModelMachine model;
	 protected ResourceLocation texture1;
	 protected ResourceLocation texture2;

	 
	 
	public RenderExplosiveCharge(ModelMachine model, ResourceLocation texture1, ResourceLocation texture2) {
		super();
		this.model = model;
		this.texture1 = texture1;
		this.texture2 = texture2;
	}



	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float scale) {
		GL11.glPushMatrix();
        GL11.glTranslated(x,y,z);
        
        
        /*Block block=tile.getBlockType();
        
        block.setBlockBoundsBasedOnState(tile.getWorldObj(), tile.xCoord,tile.yCoord,tile.zCoord);
        
        double minX=block.getBlockBoundsMinX();
        double minY=block.getBlockBoundsMinY();
        double minZ=block.getBlockBoundsMinZ();
        
        double maxX=block.getBlockBoundsMaxX();
        double maxY=block.getBlockBoundsMaxY();
        double maxZ=block.getBlockBoundsMaxZ();
        
        TGRenderHelper.adjustLightFixture(tile.getWorldObj(), tile.xCoord,tile.yCoord,tile.zCoord, block);
        
        Tessellator.instance.startDrawingQuads();
        cube.drawStandardCube(Tessellator.instance, minX, minY, minZ, maxX, maxY, maxZ, Blocks.tnt.getIcon(0, 0));
        Tessellator.instance.draw();*/
        boolean armed=false;
        byte dir = ((ExplosiveChargeTileEnt) tile).orientation;
        if (tile != null){
        	armed = ((ExplosiveChargeTileEnt) tile).isArmed();
        }
        if(armed){
        	Minecraft.getMinecraft().renderEngine.bindTexture(texture2);
        } else {
        	Minecraft.getMinecraft().renderEngine.bindTexture(texture1);
        }
        TGRenderHelper.adjustLightFixture(tile.getWorldObj(), tile.xCoord,tile.yCoord,tile.zCoord, tile.getBlockType());
        
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslated(-0.5D,-1.5D,0.5D);
        
        if(dir>1){
        	float rot = 0f;
        	switch(dir){
        		case 2:
	        		rot=180.0f;
	        		break;
        		case 3:
	        		rot=0.0f;
	        		break;
        		case 4:
	        		rot=90.0f;
	        		break;
        		case 5:
	        		rot=270.0f;
	        		break;
        	
        	}
        	GL11.glRotatef(rot, 0, 1, 0);
        } else {
        	if( dir==0){
        		GL11.glRotatef(-90.0f, 1, 0, 0);
        		GL11.glTranslated(0, -1,1);
        	} else if (dir==1){
        		GL11.glRotatef(90.0f, 1, 0, 0);
        		GL11.glTranslated(0, -1,-1);
        	}
        }
        
        
        this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
        
        GL11.glPopMatrix(); 
	}

}
