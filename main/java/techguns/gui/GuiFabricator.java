package techguns.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.inventory.FabricatorContainer;
import techguns.tileentities.FabricatorTileEntMaster;
import techguns.util.TextUtil;

public class GuiFabricator extends GuiTechgunsPoweredTileEnt {
	
	public static final ResourceLocation texture = new ResourceLocation("techguns:textures/gui/Fabricator_Gui.png");
	protected FabricatorTileEntMaster tile;
	
	public GuiFabricator(InventoryPlayer ply,FabricatorTileEntMaster tileent) {
		super(ply,tileent,new FabricatorContainer(ply,tileent));
		this.tile=tileent;
	}

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
		int x = 0;
        int y = 0;
        int color = 4210752;
        
        String s = this.tile.hasCustomInventoryName() ? this.tile.getInventoryName() : TextUtil.trans(this.tile.getInventoryName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, color);
        this.fontRendererObj.drawString(TextUtil.trans("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
        
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

        if (this.tile.isWorking()){
	        int i1 = this.tile.getProgressScaled(90);
	        this.drawTexturedModalRect(k + 21, l + 53, 0, 167, i1+1, 11);
        }
        
        super.drawGuiContainerBackgroundLayer(partialTickTime, mouseX, mouseY);
    }
}
