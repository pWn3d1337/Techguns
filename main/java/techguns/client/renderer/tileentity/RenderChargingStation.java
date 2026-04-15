package techguns.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import techguns.client.models.blocks.ModelMachine;
import techguns.tileentities.ChargingStationTileEnt;

public class RenderChargingStation extends RenderAmmoPress {

	private EntityItem entItem = null;
	
	public RenderChargingStation(ModelMachine model, String texture) {
		super(model, texture);
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

       

        
        ChargingStationTileEnt cstile = (ChargingStationTileEnt) te;
        
		
		ItemStack itemStack = null;
		if (cstile.currentOperation!=null){
			itemStack=cstile.currentOperation;
		} else if(cstile.charging) {
			itemStack=cstile.getStackInSlot(0);
		}
		
		if (itemStack != null) {
			 if((entItem == null) || entItem.getEntityItem() != itemStack) {
				 entItem = new EntityItem(cstile.getWorldObj(), x, y, z, itemStack);
			 }
			 this.entItem.hoverStart = 0.0F;
			 GL11.glPushMatrix();
			 byte rotation = cstile.rotation;
		     GL11.glRotatef(-90.0f-90.0f*rotation, 0, 1, 0);
			 RenderManager.instance.renderEntityWithPosYaw(this.entItem, 0.0D, -1.25D+0.025D, 0.0D, 0.0F, 93.5F);
			 GL11.glColor3f(1.0f, 1.0f, 1.0f);
			 GL11.glPopMatrix();
		}
     
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        
        float progress = 0.0f;
        if (te != null){
        	progress = cstile.getProgress();
        }
        byte rotation = cstile.rotation;
        GL11.glRotatef(180.0f+90.0f*rotation, 0, 1, 0);
        
        this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, progress);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
	}

	
	
}
