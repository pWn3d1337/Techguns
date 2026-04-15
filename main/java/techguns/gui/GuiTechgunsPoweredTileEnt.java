package techguns.gui;

import java.util.UUID;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import techguns.TGPackets;
import techguns.client.ClientProxy;
import techguns.packets.GUIButtonClick;
import techguns.tileentities.BasicPoweredTileEnt;
import techguns.util.TextUtil;

public class GuiTechgunsPoweredTileEnt extends GuiTechgunsTileEntBase {
	/**
	 * used by other guis, should be highest used ID
	 */
	public static int BUTTON_ID=2;
	public static int BUTTON2_ID=1;
	public static final ResourceLocation power_texture = new ResourceLocation("techguns:textures/gui/ammoPress_GUI.png");//new ResourceLocation("textures/gui/container/ammopressgui.png");
	protected BasicPoweredTileEnt poweredTileEnt;
	
	public GuiTechgunsPoweredTileEnt(InventoryPlayer ply,BasicPoweredTileEnt tileent,Container container) {
		super(container);
		this.poweredTileEnt=tileent;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX,
			int mouseY) {
		int mx = mouseX - (this.width-this.xSize)/2;
        int my = mouseY - (this.height-this.ySize)/2;
        
        if (isInRect(mx,my,7,17,5,49)){
        	this.drawTooltipText(TextUtil.trans("techguns.container.power")+": "+this.poweredTileEnt.getPowerStored()+"/"+this.poweredTileEnt.getPowerMax()+" RF", mx, my);
        } else if (isInRect(mx, my, 6, 5, 10, 5) || isInRect(mx,my,-22,10,20,20)){
        	String[] tooltip = new String[3];
        	tooltip[0]=TextUtil.trans("techguns.container.redstone");
        	tooltip[1]=TextUtil.trans("techguns.container.redstone.mode")+": "+TextUtil.trans(poweredTileEnt.getRedstoneModeText());
        	tooltip[2]=TextUtil.trans("techguns.container.redstone.signal")+": "+TextUtil.trans(poweredTileEnt.getSignalStateText());
        	this.drawTooltipText(tooltip, mx, my);
        } else if (isInRect(mx,my,-22,40,20,20)){
        	String[] tooltip = new String[4];
        	String color="";
        	byte type =poweredTileEnt.getSecurity(); 
        	switch(type){
	        	case 0:
	        		color +=EnumChatFormatting.GREEN;
	        		break;
	        	case 1:
	        		color +=EnumChatFormatting.YELLOW;
	        		break;
	        	case 2:
	        		color +=EnumChatFormatting.RED;
	        		break;
        	}
        	
            String own=TextUtil.trans("techguns.container.security.tooltip.unowned");
            UUID owner = poweredTileEnt.getOwner();
            if (owner!=null){
            	own = TextUtil.trans("techguns.container.security.tooltip.owner")+": "+ ClientProxy.get().resolvePlayerNameFromUUID(owner);
            }
            //this.fontRendererObj.drawString(own, 8, this.ySize - 76 + 2, 4210752);
        	
        	tooltip[0]=TextUtil.trans("techguns.container.security.owner")+": "+own;
        	tooltip[1]=TextUtil.trans("techguns.container.security.tooltip")+": "+color+ TextUtil.trans("techguns.container.security.tooltip."+type);
        	tooltip[2]=TextUtil.trans("techguns.container.security.tooltip.description."+type);
        	tooltip[3]=TextUtil.trans("techguns.container.security.tooltip.descr2");
        	this.drawTooltipText(tooltip, mx, my);
        }
	}

	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTickTime,
			int mouseX, int mouseY) {
		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        
		 //draw power bar
        int power = (int) (this.poweredTileEnt.getPowerStored()*48 / this.poweredTileEnt.getPowerMax());
        int diff=48-power;
        this.mc.getTextureManager().bindTexture(power_texture);
        this.drawTexturedModalRect(k+8, l+17+diff, 251, 1+diff, 4, 48);
        //System.out.println("Power:"+power+" diff:"+diff);
        
        //draw redstone button backbround:
        this.drawTexturedModalRect(k-22-5, l+10-5, 195, 0, 22+5, 30);
        
        //draw security button background
        this.drawTexturedModalRect(k-22-5, l+40-5, 195, 0, 22+5, 30);
        
        //draw Redstone control;
        if(poweredTileEnt.isRedstoneEnabled()){
        	this.drawTexturedModalRect(k+6, l+5, 240, 0, 5, 5);
        	this.drawTexturedModalRect(k+6+5, l+5, 245, 5, 5, 5);
        } else {
        	this.drawTexturedModalRect(k+6, l+5, 240, 5, 5, 5);
        	this.drawTexturedModalRect(k+6+5, l+5, 245, 0, 5, 5);
        }
        
	}

	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(new GuiButtonRedstoneState(GuiTechgunsPoweredTileEnt.BUTTON_ID, this.guiLeft-22, this.guiTop+10, 20, 20, "", poweredTileEnt));
		this.buttonList.add(new GuiButtonSecurity(GuiTechgunsPoweredTileEnt.BUTTON2_ID, this.guiLeft-22, this.guiTop+40, 20, 20, "", poweredTileEnt));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
        //id is the id you give your button
		TGPackets.network.sendToServer(new GUIButtonClick(this.poweredTileEnt,guibutton.id));
	}
	
	protected void drawFluidWithTesselator(IIcon tex, int x, int y, int width, int height, int px){
		int offset =0;
		
		if (tex==null){
			return;
		}
		
    	int len=tex.getIconHeight();
    	while (offset < px){
    		if(offset+len>px){
    			len=px-offset;
    		}
    		this.drawTexturedModelRectFromIconFluidTank(x, y+height-offset-len, tex,width,len);
    		offset+=tex.getIconHeight();
    	}
	}
	
	protected void drawTexturedModelRectFromIconFluidTank(int x, int y, IIcon icon, int w, int h)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + h), (double)this.zLevel, (double)icon.getMinU(), (double)icon.getMaxV());
        tessellator.addVertexWithUV((double)(x + w), (double)(y + h), (double)this.zLevel, (double)icon.getMaxU(), (double)icon.getMaxV());
        tessellator.addVertexWithUV((double)(x + w), (double)(y + 0), (double)this.zLevel, (double)icon.getMaxU(), (double)icon.getInterpolatedV(icon.getIconHeight()-h));
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)this.zLevel, (double)icon.getMinU(), (double)icon.getInterpolatedV(icon.getIconHeight()-h));
        tessellator.draw();
    }
}
