package techguns.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.inventory.MetalPressContainer;
import techguns.tileentities.MetalPressTileEnt;
import techguns.util.TextUtil;
import cpw.mods.fml.client.config.GuiButtonExt;

public class GuiMetalPress extends GuiTechgunsPoweredTileEnt {

	public static final ResourceLocation texture = new ResourceLocation("techguns:textures/gui/metalPress_GUI.png");//new ResourceLocation("textures/gui/container/ammopressgui.png");
	protected MetalPressTileEnt tileent;
	
	public GuiMetalPress(InventoryPlayer ply,MetalPressTileEnt tileent) {
		super(ply,tileent,new MetalPressContainer(ply,tileent));
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
        int color = 4210752; //0xff101010;
        
        String s = this.tileent.hasCustomInventoryName() ? this.tileent.getInventoryName() : TextUtil.trans(this.tileent.getInventoryName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, color);
        this.fontRendererObj.drawString(TextUtil.trans("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
        
        String s2="";
        switch(this.tileent.getAutoSplitMode()){
        	case 0:
        		s2=TextUtil.trans("techguns.container.info.off");
        		break;
        	case 1:
        		s2=TextUtil.trans("techguns.container.info.on");
        		break;
        }
        this.fontRendererObj.drawString(TextUtil.trans("techguns.container.metalpress.autosplit")+":", x+20, y+30, color);
        this.fontRendererObj.drawString(s2, x+20, y+40, color);
        
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
        this.drawTexturedModalRect(k+149, l+59, 119, 59, 20,20);
        
        

        if (this.tileent.isWorking()){
	        int i1 = this.tileent.getProgressScaled(21);
	        //this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	        this.drawTexturedModalRect(k + 119, l + 36, 176, 0, 20, i1 + 1);
        }
        
        
        //draw power bar:
        //int i2 = this.tileent.getPowerScaled(64);
        //this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
        //this.drawTexturedModalRect(k + 10, l + 16, 176, 14, 16, i2 + 1);
        
        
        /*if (this.tileFurnace.isBurning())
        {
            int i1 = this.tileent.getBurnTimeRemainingScaled(13);
            this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
            i1 = this.tileent.getCookProgressScaled(24);
            this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
        }*/
        super.drawGuiContainerBackgroundLayer(partialTickTime, mouseX, mouseY);
    }
	
	/* (non-Javadoc)
	 * @see net.minecraft.client.gui.inventory.GuiContainer#initGui()
	 */
	@Override
	public void initGui() {
		super.initGui();
										//id, x, y, width, height, text
		this.buttonList.add(new GuiButtonExt(GuiTechgunsPoweredTileEnt.BUTTON_ID+1, this.guiLeft+20, this.guiTop+50, 40, 20, TextUtil.trans("techguns.button.switch")));
	}

}
