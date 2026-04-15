package techguns.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.inventory.ScrapperContainer;
import techguns.tileentities.ScrapperTileEnt;
import techguns.util.TextUtil;

public class GuiScrapper extends GuiTechgunsPoweredTileEnt {
	protected ScrapperTileEnt tile;

	public static final ResourceLocation texture = new ResourceLocation("techguns:textures/gui/ammoPress_GUI.png");//new ResourceLocation("textures/gui/container/ammopressgui.png");
	protected ScrapperTileEnt tileent;
	
	public GuiScrapper(InventoryPlayer ply,ScrapperTileEnt tileent) {
		super(ply,tileent,new ScrapperContainer(ply,tileent));
		this.tileent=tileent;
	}
	
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
		int x = 0;
        int y = 0;
        int mx = mouseX - (this.width-this.xSize)/2;
        int my = mouseY - (this.height-this.ySize)/2;
        int color = 4210752; //0xff101010;
        
        String s = this.tileent.hasCustomInventoryName() ? this.tileent.getInventoryName() : TextUtil.trans(this.tileent.getInventoryName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, color);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);

        super.drawGuiContainerForegroundLayer(mouseX,mouseY);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTickTime, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        this.drawTexturedModalRect(k+151, l+59, 119, 59, 20, 20);
        
        if (this.tileent.isWorking()){
	        int i1 = this.tileent.getProgressScaled(21);
	        //this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	        this.drawTexturedModalRect(k + 119, l + 36, 176, 0, 19, i1 + 1);
        }        

        super.drawGuiContainerBackgroundLayer(partialTickTime, mouseX, mouseY);
    }


}
