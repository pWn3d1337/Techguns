package techguns.gui;

import java.util.Arrays;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

import org.lwjgl.opengl.GL11;

import techguns.util.TextUtil;

public abstract class GuiTechgunsTileEntBase extends GuiContainer {

	public GuiTechgunsTileEntBase(Container container) {
		super(container);
	}

	public void drawTooltipText(String text,int x, int y){
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT+GL11.GL_LIGHTING_BIT);
		this.func_146283_a(Arrays.asList(new String[]{text}), x, y);
		GL11.glPopAttrib();
	}
	public void drawTooltipText(String[] text,int x, int y){
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT+GL11.GL_LIGHTING_BIT);
		this.func_146283_a(Arrays.asList(text), x, y);
		GL11.glPopAttrib();
	}
	
	public boolean isInRect(int mouseX, int mouseY, int x, int y, int width, int height){
		return mouseX>=x && mouseX<=(x+width) && mouseY >=y && mouseY<= (y+height);
	}

	public static String trans(String key){
		return TextUtil.trans("techguns."+key);
	}
}
