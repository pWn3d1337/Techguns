package techguns.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.inventory.ChargingStationContainer;
import techguns.tileentities.ChargingStationTileEnt;
import techguns.util.TextUtil;

public class GuiChargingStation extends GuiTechgunsPoweredTileEnt {
	
	public static final ResourceLocation texture = new ResourceLocation("techguns:textures/gui/ChargingStation_Gui.png");
	protected ChargingStationTileEnt tileent;
	
	public GuiChargingStation(InventoryPlayer ply,ChargingStationTileEnt tileent) {
		super(ply,tileent,new ChargingStationContainer(ply,tileent));
		this.tileent=tileent;
	}

	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
		int x = 0;
        int y = 0;
        int color = 4210752; //0xff101010;
        
        String s = this.tileent.hasCustomInventoryName() ? this.tileent.getInventoryName() : TextUtil.trans(this.tileent.getInventoryName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, color);
        this.fontRendererObj.drawString(TextUtil.trans("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
        
        //this.fontRendererObj.drawString("Power:",x+10, y+16, color);
        //this.fontRendererObj.drawString(this.tileent.getPowerStored()+"/"+this.tileent.getPowerMax()+" RF",x+10, y+26, color);
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTickTime, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        //draw slot for upgrade
        this.drawTexturedModalRect(k+149, l+49, 7, 83, 18,18);
        
        

        if (this.tileent.isWorking()){
	        int i1 = this.tileent.getProgressScaled(26);
	        //this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	        this.drawTexturedModalRect(k + 39, l + 19, 0, 167, i1+1, 10);
        }
        
        super.drawGuiContainerBackgroundLayer(partialTickTime, mouseX, mouseY);
    }
}
