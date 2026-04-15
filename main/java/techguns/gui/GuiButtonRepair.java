package techguns.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.client.config.GuiButtonExt;
import cpw.mods.fml.client.config.GuiUtils;

public class GuiButtonRepair extends GuiButtonExt {
	private static final ResourceLocation texture = new ResourceLocation("techguns:textures/gui/repairBench_GUI.png");
	
	
	public GuiButtonRepair(int id, int xPos, int yPos, int width, int height) {
		super(id, xPos, yPos, width, height, "");
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible)
        {
            this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            GuiUtils.drawContinuousTexturedBox(buttonTextures, this.xPosition, this.yPosition, 0, 46 + k * 20, this.width, this.height, 200, 20, 2, 3, 2, 2, this.zLevel);
            this.mouseDragged(mc, mouseX, mouseY);
                        
            Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
            this.drawTexturedModalRect(this.xPosition+1, this.yPosition+1, 176, 0, 12, 12);
        }
	}
}
