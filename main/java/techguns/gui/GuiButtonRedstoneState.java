package techguns.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import techguns.tileentities.BasicPoweredTileEnt;
import cpw.mods.fml.client.config.GuiButtonExt;
import cpw.mods.fml.client.config.GuiUtils;

public class GuiButtonRedstoneState extends GuiButtonExt {
	protected BasicPoweredTileEnt tileent;
	public static final ResourceLocation texture_redstonestates = new ResourceLocation("techguns:textures/gui/ammoPress_GUI.png");//new ResourceLocation("textures/gui/container/ammopressgui.png");
	
	
	public GuiButtonRedstoneState(int id, int xPos, int yPos, int width, int height, String displayString,BasicPoweredTileEnt tile) {
		super(id, xPos, yPos, width, height, displayString);
		this.tileent=tile;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible)
        {
            this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            GuiUtils.drawContinuousTexturedBox(buttonTextures, this.xPosition, this.yPosition, 0, 46 + k * 20, this.width, this.height, 200, 20, 2, 3, 2, 2, this.zLevel);
            this.mouseDragged(mc, mouseX, mouseY);
            int color = 14737632;
            
            if (packedFGColour != 0)
            {
                color = packedFGColour;
            }
            else if (!this.enabled)
            {
                color = 10526880;
            }
            else if (this.field_146123_n)
            {
                color = 16777120;
            }
            
            Minecraft.getMinecraft().renderEngine.bindTexture(texture_redstonestates);
            byte mode = tileent.getRedstoneBehaviour();
            this.drawTexturedModalRect(this.xPosition+2, this.yPosition+2, 234, 10+(16*mode), 16, 16);
            
           /* String buttonText = this.displayString;
            int strWidth = mc.fontRenderer.getStringWidth(buttonText);
            int ellipsisWidth = mc.fontRenderer.getStringWidth("...");
            
            if (strWidth > width - 6 && strWidth > ellipsisWidth)
                buttonText = mc.fontRenderer.trimStringToWidth(buttonText, width - 6 - ellipsisWidth).trim() + "...";
            
            this.drawCenteredString(mc.fontRenderer, buttonText, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, color);*/
        }
	}
	
	

}
