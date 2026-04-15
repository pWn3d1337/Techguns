package techguns.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.TGPackets;
import techguns.inventory.ExplosiveChargeContainer;
import techguns.packets.GUIButtonClick;
import techguns.tileentities.ExplosiveChargeAdvTileEnt;
import techguns.tileentities.ExplosiveChargeTileEnt;
import techguns.util.TextUtil;
import cpw.mods.fml.client.config.GuiButtonExt;

public class GuiExplosiveCharge extends GuiTechgunsTileEntBase {
	protected ExplosiveChargeTileEnt tileent;
	
	public static final ResourceLocation texture = new ResourceLocation("techguns:textures/gui/tntChargeGui.png");//new ResourceLocation("textures/gui/container/ammopressgui.png");
	public static final ResourceLocation texture2 = new ResourceLocation("techguns:textures/gui/expChargeGui.png");
	
	protected byte guiType = 0;
	
	public GuiExplosiveCharge(InventoryPlayer ply,ExplosiveChargeTileEnt tileent) {
		super(new ExplosiveChargeContainer(ply, tileent));
		this.tileent=tileent;
		if (this.tileent instanceof ExplosiveChargeAdvTileEnt){
			this.guiType=1;
		}
	}

	
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mousex, int mousey) {
		int x = 0;
        int y = 0;
        int color = 4210752; //0xff101010;
        
        String s = this.tileent.hasCustomInventoryName() ? this.tileent.getInventoryName() : TextUtil.trans(this.tileent.getInventoryName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 28, color);
        

        int radius = tileent.getBlastradius();
        int blastlength = tileent.getBlastlength();
        int fusetime = tileent.getFusetime();
        
        String sradius = "Radius: "+radius;
        this.fontRendererObj.drawString(sradius, 37,40, color);
        String slength = "Length: "+blastlength;
        this.fontRendererObj.drawString(slength, 37,50, color);
        String sfusetime = "Delay: "+fusetime/20+"s";
        this.fontRendererObj.drawString(sfusetime, 37,60, color);
	   
	}



	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTickTime, int mousex, int mousey) {
		 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if (this.guiType==0){
			this.mc.getTextureManager().bindTexture(texture);
		} else {
			this.mc.getTextureManager().bindTexture(texture2);
		}
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}



	@Override
	public void initGui() {
		super.initGui();
		int buttonid=0;
		this.buttonList.add(new GuiButtonExt(buttonid++, this.guiLeft+57, this.guiTop+73, 60, 20, "Detonate"));
		
		int left=120;
		this.buttonList.add(new GuiButtonExt(buttonid++, this.guiLeft+left, this.guiTop+40, 10, 10, "+"));
		this.buttonList.add(new GuiButtonExt(buttonid++, this.guiLeft+left+10, this.guiTop+40, 10, 10, "-"));
		
		this.buttonList.add(new GuiButtonExt(buttonid++, this.guiLeft+left, this.guiTop+50, 10, 10, "+"));
		this.buttonList.add(new GuiButtonExt(buttonid++, this.guiLeft+left+10, this.guiTop+50, 10, 10, "-"));
		
		this.buttonList.add(new GuiButtonExt(buttonid++, this.guiLeft+left, this.guiTop+60, 10, 10, "+"));
		this.buttonList.add(new GuiButtonExt(buttonid++, this.guiLeft+left+10, this.guiTop+60, 10, 10, "-"));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		TGPackets.network.sendToServer(new GUIButtonClick(this.tileent,guibutton.id));
		if(guibutton.id==0){
			Minecraft.getMinecraft().thePlayer.closeScreen();
		}
	}
	
	
}
