package techguns.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import techguns.client.models.blocks.ModelFabricator;
import techguns.tileentities.BasicMachineTileEntity;
import techguns.tileentities.FabricatorTileEntMaster;

public class RenderFabricator extends RenderAmmoPress {
private EntityItem entItem = null;
	private ModelFabricator model;

	public RenderFabricator(ModelFabricator model, String texture) {
		super(model, texture);
		this.model=model;
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
        FabricatorTileEntMaster fab = (FabricatorTileEntMaster) te;
		
        if(fab.isComplete()){
	        
	        GL11.glPushMatrix();
	        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
	        
	        float progress = 0.0f;
	        if (te != null){
	        	progress = ((BasicMachineTileEntity) te).getProgress();
	        }
	        
			
	        GL11.glPushMatrix();
	        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
	        
	        //System.out.println("Dir:"+fab.dir);
	        //byte rot = (byte) Keybinds.X;//(byte) (fab.dir-2);
	        //System.out.println("Dir: "+fab.dir+" Rot:"+(180.0f-90.0f*fab.dir));
	        float rot=0;
	        switch(fab.dir){
	        	case 3:
	        		rot = -180;
	        		break;
	        	case 4:
	        		rot = -90;
	        		break;
	        	case 5:
	        		rot = 90;
	        		break;
	        }
	        
	        
	        GL11.glRotatef(rot, 0, 1, 0);
	        
			ItemStack itemStack = null;
			if (fab.currentOperation!=null){
				
				if(progress<0.8f){
					itemStack=fab.plateUsed;
				} else {
					itemStack=fab.currentOperation;
				}
			} 
			if (itemStack != null) {
				 if((entItem == null) || entItem.getEntityItem() != itemStack) {
					 entItem = new EntityItem(fab.getWorldObj(), x, y, z, itemStack);
				 }
				 this.entItem.hoverStart = 0.0F;
				 GL11.glPushMatrix();
				 GL11.glRotatef(-90, 0, 0, 1);
				 GL11.glRotatef(180, 0, 1, 0);
				 GL11.glRotatef(-90, 1, 0, 0);
				 RenderManager.instance.renderEntityWithPosYaw(this.entItem, 0.6, /*-1.25D +0.625D +0.9*/ 0.275, -0.5, 0.0F, 93.5F);
				 GL11.glPopMatrix();
			}
	     
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);

	        
	        float zdif=-0.1f;
	        
	        if (progress==0.0f){
	        	for (int i=0;i<5;i++){
	        		this.model.render((Entity)null, 0.0F, 0.0F, zdif, 0.0F, 0.0F, 0.0625F, i);
	        	}
	        } else {
	        	this.model.render((Entity)null, 0.0F, 0.0F, zdif, 0.0F, 0.0F, 0.0625F, 0);
	
	        	//MoveFrameTop
	        	GL11.glPushMatrix();
	        	
	        		GL11.glTranslated(0,0,0.25f*Math.sin(progress*Math.PI*4));
	        	
	        		this.model.render((Entity)null, 0.0F, 0.0F, zdif, 0.0F, 0.0F, 0.0625F, 1);
	        		
	        		//MoveHead
	        		GL11.glPushMatrix();
	        			GL11.glTranslated(0.125f*Math.sin(progress*Math.PI*8),0,0);
	        			this.model.render((Entity)null, 0.0F, 0.0F, zdif, 0.0F, 0.0F, 0.0625F, 2);
	        		
	        			if (progress <0.1f || progress >0.9f) {
	        				this.model.render((Entity)null, 0.0F, 0.0F, zdif, 0.0F, 0.0F, 0.0625F, 3);
	        				this.model.render((Entity)null, 0.0F, 0.0F, zdif, 0.0F, 0.0F, 0.0625F, 4);
	        			}
	        			else if(progress>=0.1f && progress<0.3f) {
	        				float progScaled = (progress - 0.1f)*5.0f;
		        			GL11.glPushMatrix();
		        				float offsetX=-0.575f;
		        				float offsetZ=0.5f;
		        			
		        				GL11.glTranslatef(offsetX,0,offsetZ);
		        				GL11.glRotatef(progScaled*360*8, 0, 1, 0);
		        				GL11.glTranslatef(-offsetX,0,-offsetZ);
		        				double sin = Math.sin(progScaled*Math.PI);
		        				GL11.glTranslated(0, 0.3d*sin*sin, 0);
		        				this.model.render((Entity)null, 0.0F, 0.0F, zdif, 0.0F, 0.0F, 0.0625F, 3);
		        			GL11.glPopMatrix();
		        			this.model.render((Entity)null, 0.0F, 0.0F, zdif, 0.0F, 0.0F, 0.0625F, 4);
		        			//this.model.render((Entity)null, 0.0F, 0.0F, zdif, 0.0F, 0.0F, 0.0625F, 5);
		        			
	        			} else if (progress>=0.3f && progress <0.6f){
	        				this.model.render((Entity)null, 0.0F, 0.0F, zdif, 0.0F, 0.0F, 0.0625F, 3);
	        				
	        				float progScaled = (progress - 0.3f)* (1.0f/0.3f);
	        				GL11.glPushMatrix();
	        					double sin = Math.sin(progScaled*Math.PI*4);
	        					GL11.glTranslated(0, 0.3d*sin*sin, 0);
	        					this.model.render((Entity)null, 0.0F, 0.0F, zdif, 0.0F, 0.0F, 0.0625F, 4);
	        				GL11.glPopMatrix();
	        				
	        				//this.model.render((Entity)null, 0.0F, 0.0F, zdif, 0.0F, 0.0F, 0.0625F, 5);
	        			} else {
	        				//float progScaled = (progress -0.6f) * (1.0f/0.3f);
	        				this.model.render((Entity)null, 0.0F, 0.0F, zdif, 0.0F, 0.0F, 0.0625F, 3);
	        				this.model.render((Entity)null, 0.0F, 0.0F, zdif, 0.0F, 0.0F, 0.0625F, 4);
	        				this.model.render((Entity)null, 0.0F, 0.0F, zdif, 0.0F, 0.0F, 0.0625F, 5);
	        			}
	        			
	        		GL11.glPopMatrix();
	        	
	        	GL11.glPopMatrix();
	        	
	        	
	        }
	        //render the glass
	        //TGRenderHelper.enableAlphaBlend();
	        this.model.render((Entity)null, 0.0F, 0.0F, zdif, 0.0F, 0.0F, 0.0625F, 6);
	        //TGRenderHelper.disableAlphaBlend();
	        
	        GL11.glPopMatrix();
	        GL11.glPopMatrix();
        }
	}
}
