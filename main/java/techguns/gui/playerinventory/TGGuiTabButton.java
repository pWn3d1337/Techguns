package techguns.gui.playerinventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.TGPackets;
import techguns.packets.PacketOpenPlayerGUI;

public class TGGuiTabButton extends GuiButton {
	private static final ResourceLocation tabButtonTextures = new ResourceLocation("techguns:textures/gui/TGPlayerInventory.png");
	RenderItem renderItem=new RenderItem();
	ItemStack iconItem;
	private final short GUI_ID;

	public TGGuiTabButton(int id, int xPos, int yPos,boolean enabled, ItemStack iconItem,int guid) {
		super(id, xPos, yPos, 26, 26, "");
		this.enabled=enabled;
		this.iconItem = iconItem;
		this.GUI_ID=(short) guid;
	}

	@Override
	public boolean mousePressed(Minecraft mc, int mx, int my) {
		if(this.enabled){
			if (super.mousePressed(mc, mx, my)){
				
				if(GUI_ID<=0){
					mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(mc.thePlayer.openContainer.windowId));
					Minecraft.getMinecraft().displayGuiScreen(new GuiInventory(mc.thePlayer));
				} else {
					TGPackets.network.sendToServer(new PacketOpenPlayerGUI(GUI_ID));
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void func_146113_a(SoundHandler p_146113_1_) {
		if(this.enabled){
			p_146113_1_.playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
		}
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		if (this.visible)
        {
            //FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(tabButtonTextures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = mx >= this.xPosition && my >= this.yPosition && mx < this.xPosition + this.width && my < this.yPosition + this.height;
     //       int k = this.getHoverState(this.field_146123_n);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            
            //this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
            //this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
            int i=0;
            if (this.enabled){
            	i=1;
            }
            
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 177, 26*i, this.width, this.height);
            
            if(!this.enabled){
            	this.drawTexturedModalRect(this.xPosition+this.width, this.yPosition, 213, 0, 3, this.height);
            }
            
            RenderHelper.enableStandardItemLighting();
            renderItem.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, this.iconItem, this.xPosition+6, this.yPosition+5);
            RenderHelper.disableStandardItemLighting();
            
            this.mouseDragged(mc, mx, my);
          /*  int l = 14737632;

            if (packedFGColour != 0)
            {
                l = packedFGColour;
            }
            else if (!this.enabled)
            {
                l = 10526880;
            }
            else if (this.field_146123_n)
            {
                l = 16777120;
            }*/

            //this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
        }
	}
	
	
	

}
