package techguns.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import techguns.tileentities.ChemLabTileEnt;
import cpw.mods.fml.client.config.GuiButtonExt;
import cpw.mods.fml.client.config.GuiUtils;

public class GuiButtonToggleDrainTank extends GuiButtonExt {
	private static final ResourceLocation texture = new ResourceLocation("techguns:textures/gui/chemLab_Gui.png");
	private ChemLabTileEnt tileent;
	
	
	public GuiButtonToggleDrainTank(int id, int xPos, int yPos, int width, int height, ChemLabTileEnt tileEnt) {
		super(id, xPos, yPos, width, height, "");
		this.tileent=tileEnt;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible)
        {
            this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            GuiUtils.drawContinuousTexturedBox(buttonTextures, this.xPosition, this.yPosition, 0, 46 + k * 20, this.width, this.height, 200, 20, 2, 3, 2, 2, this.zLevel);
            this.mouseDragged(mc, mouseX, mouseY);
            
            byte mode = tileent.getDrainMode();
            
            Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
            this.drawTexturedModalRect(this.xPosition+1, this.yPosition+1, 188, 31+(12*mode), 12, 12);
        }
	}

}
