package techguns.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import techguns.client.renderer.TGRenderHelper;
import techguns.client.renderer.block.TessellatorCubeProper;
import techguns.inventory.ReactionBeamFocus;
import techguns.tileentities.ReactionChamberTileEntMaster;
import techguns.util.BlockCoords;

public class RenderReactionChamber extends TileEntitySpecialRenderer {

	private ResourceLocation mainTexture;
	private ResourceLocation glassTexture = new ResourceLocation("techguns", "textures/entity/reactionchamberglass.png");
	private ResourceLocation heatrayTexture = new ResourceLocation("techguns", "textures/fx/heatray.png");
    private IModelCustom mainModel;
    
    private EntityItem entItem = null;
	
    private TessellatorCubeProper liquidCube = new TessellatorCubeProper(Blocks.dirt,0);
    
	public RenderReactionChamber(String model, String texture) {
		this.mainTexture = new ResourceLocation("techguns", texture);
    	this.mainModel = AdvancedModelLoader.loadModel(new ResourceLocation("techguns",model));
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float ptt) {
		ReactionChamberTileEntMaster tile = (ReactionChamberTileEntMaster) te;
		
		if (tile.isComplete()){
				
			double scale = 0.03125;
			GL11.glPushMatrix();		
			TGRenderHelper.adjustLightFixture(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, te.getBlockType());

			GL11.glTranslated(x,y,z);
			
			GL11.glPushMatrix();
			
			GL11.glTranslatef(0.5f, 0, 0.5f);
	      //  GL11.glRotatef(-90.0f*tile.rotation, 0, 1, 0);
	        float rot=-180;
	        switch(tile.dir){
	        	case 3:
	        		rot = 0; //-180;
	        		break;
	        	case 4:
	        		rot = -90;
	        		break;
	        	case 5:
	        		rot = 90;
	        		break;
	        }
	        
	        GL11.glRotatef(rot, 0, 1, 0);
			GL11.glTranslated(0, 0, -1.0);
					
			
			/*===============
			 * Render Item
			 *===============*/
			
			ItemStack itemStack=null;
			if (tile.isWorking()){
				itemStack = tile.currentOperation;
			} else {
				itemStack = tile.getStackInSlot(0);
			}
			if (itemStack != null) {
				 if((entItem == null) || entItem.getEntityItem() != itemStack) {
					 entItem = new EntityItem(tile.getWorldObj(), x, y, z, itemStack);
				 }
				 this.entItem.hoverStart = 0.0F;
				 RenderManager.instance.renderEntityWithPosYaw(this.entItem, 0.0D, 1.5D, 0.0D, 0.0F, 0.0F);
				 GL11.glColor3f(1.0f, 1.0f, 1.0f);
				 //TGRenderHelper.adjustLightFixture(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, te.getBlockType());
			}
			
			
		    /*===============
			 * Render Chamber
			 *===============*/
			
			GL11.glPushMatrix();
			GL11.glScaled(scale, scale, scale);
			bindTexture(mainTexture);
			
			mainModel.renderAllExcept("GLASS");

			GL11.glPopMatrix();
	
			

			 /*===============
			 * Render Emitter FX
			 *===============*/
			 GL11.glRotatef(-rot, 0, 1, 0);
			Tessellator tess = Tessellator.instance;
			
        	GL11.glDepthMask(false);		
			if (tile.isWorking()) {
				itemStack = tile.getStackInSlot(1);
				if (itemStack != null) {
					 ReactionBeamFocus focus = ReactionBeamFocus.getBeamFocus(itemStack);
					 renderBeamFocusEffect(focus, tile, tess, ptt);
				}
			}
			GL11.glDepthMask(true);
			
			 GL11.glRotatef(rot, 0, 1, 0);
			
			 /*===============
			 * Render Liquid
			 *===============*/

			//tess.addTranslation(xoffset, yoffset, zoffset);
			FluidStack liquid = tile.inputTank.getFluid();
			if (liquid!=null){
				//System.out.println("RenderingLIQUID");
				//liquidCube = new ReactionChamberFluidBox();
				bindTexture(TextureMap.locationBlocksTexture);
				//bindTexture(glassTexture);
				tess.startDrawingQuads();
				//liquidCube.drawCube(tess, 0, 1.5f, 0, 1, 2.5f, 1, false);
				
				double w = 1.875 * 0.5;
				double h1 = 0.5;
				double h2 = 2.9375;
				double level = (double)tile.inputTank.getFluidAmount()/(double)tile.inputTank.getCapacity();
				TGRenderHelper.enableAlphaBlend();
				
				IIcon tex;
				if (tile.isWorking()){
					tex = liquid.getFluid().getFlowingIcon();
				} else {
					tex = liquid.getFluid().getIcon();
				}
				liquidCube.drawStandardCube(tess, -w, h1, -w, w, (h2*level)+0.5, w, true,tex);
				tess.draw();
				TGRenderHelper.disableAlphaBlend();
			}
			
			
			 /*===============
			 * Render Glass
			 *===============*/
			
			GL11.glPushMatrix();
			GL11.glScaled(scale, scale, scale);
			bindTexture(glassTexture);
			//TGRenderHelper.enableAlphaBlend();
			mainModel.renderPart("GLASS");
			
			//TGRenderHelper.disableAlphaBlend();
			GL11.glPopMatrix();		
		    
			
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
	}
	
	
	private void renderBeamFocusEffect(ReactionBeamFocus focus, ReactionChamberTileEntMaster tile, Tessellator tess, float ptt) {
		GL11.glPushMatrix();
		
		//TODO: welcher focus?

		//----------------
		//HEAT RAY
		//TGRenderHelper.enableAlphaBlendAdditive();
		TGRenderHelper.enableAlphaBlendAdditive();
		TGRenderHelper.enableFXLighting();
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		bindTexture(heatrayTexture);	
		
		double d = ((double)(tile.currentReaction.duration % 10)+ptt)/10.0;
		d = 0.875+(0.125*(1.0- Math.cos(Math.PI*d*2.0)));
		//System.out.println("D="+d);
		
		double w = 1.875 * 0.5*d;
		double h1 = 0.5;
		double h2 = 2.9375;		
		float b = (((float)tile.intensity)/10.0f) *(float)d-0.25f;
		
		double x1 = Minecraft.getMinecraft().thePlayer.posX;
		double z1 = Minecraft.getMinecraft().thePlayer.posZ;
		BlockCoords bc = tile.getCenterPos();
		double angle = Math.atan2(bc.z+0.5d - z1, bc.x+0.5d - x1) * 180 / Math.PI;
		GL11.glRotated(-angle+90.0d, 0.0, 1.0, 0.0);
		//GL11.glRotatef(ptt*45.0f, 0.0F, 1.0F, 0.0F);
		//for (int i = 0; i < 4; i++) {
		//	GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
	
        tess.setColorRGBA_F(1.0f, 1.0f, 1.0f, b);
        tess.startDrawingQuads();
        tess.addVertexWithUV(-w, h1, 0.0D, 0, 1);
        tess.addVertexWithUV(-w, h2, 0.0D, 0, 0);
        tess.addVertexWithUV(w, h2, 0.0D, 1, 0);
        tess.addVertexWithUV(w, h1, 0.0D, 1, 1);
        tess.draw();
		
		//}
		
		 GL11.glEnable(GL11.GL_CULL_FACE);
		TGRenderHelper.disableFXLighting();
		TGRenderHelper.disableAlphaBlend();
		//------------------
		
		
		GL11.glPopMatrix();
	}

}
