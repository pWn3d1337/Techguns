package techguns.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.inventory.TGChestContainer;
import techguns.tileentities.TGChestTileEnt;

public class GuiTechgunsChest extends GuiTechgunsTileEntBase {

	public static int BUTTON_SEC_ID=1;
	
	 protected static final ResourceLocation texture_Chest = new ResourceLocation("techguns:textures/gui/tgchest_gui.png");//new ResourceLocation("textures/gui/container/generic_54.png");
	 protected IInventory plyInventory;
    protected IInventory chestInventory;
    /** window height is calculated with these values; the more rows, the heigher */
    protected int inventoryRows;
	
	//for TG GUI ELEMENTS
	public static final ResourceLocation tg_texture = new ResourceLocation("techguns:textures/gui/ammoPress_GUI.png");
	
	protected TGChestTileEnt chest;
	
	public GuiTechgunsChest(EntityPlayer ply,TGChestTileEnt tileent, IInventory lower) {
		super(new TGChestContainer(ply, lower));
		this.xSize=230;
		this.chest=tileent;
		
		this.plyInventory = ply.inventory;
        this.chestInventory = lower;
        this.allowUserInput = false;
        short short1 = 222;
        int i = short1 - 108;
        this.inventoryRows = lower.getSizeInventory() / 12;
        this.ySize = i + this.inventoryRows * 18;
	}
	
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX,
			int mouseY) {
		int mx = mouseX - (this.width-this.xSize)/2;
        int my = mouseY - (this.height-this.ySize)/2;
        
		/*if (isInRect(mx,my,-22,40,20,20)){
        	String[] tooltip = new String[4];
        	String color="";
        	byte type =chest.getSecurity(); 
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
            UUID owner = chest.getOwner();
            if (owner!=null){
            	own = TextUtil.trans("techguns.container.security.tooltip.owner")+": "+ ClientProxy.get().resolvePlayerNameFromUUID(owner);
            }
            //this.fontRendererObj.drawString(own, 8, this.ySize - 76 + 2, 4210752);
        	
        	tooltip[0]=TextUtil.trans("techguns.container.security.owner")+": "+own;
        	tooltip[1]=TextUtil.trans("techguns.container.security.tooltip")+": "+color+ TextUtil.trans("techguns.container.security.tooltip."+type);
        	tooltip[2]=TextUtil.trans("techguns.container.security.tooltip.description."+type);
        	tooltip[3]=TextUtil.trans("techguns.container.security.tooltip.descr2");
        	this.drawTooltipText(tooltip, mx, my);
        }*/
		
		 this.fontRendererObj.drawString(this.chestInventory.hasCustomInventoryName() ? this.chestInventory.getInventoryName() : I18n.format(this.chestInventory.getInventoryName(), new Object[0]), 8, 6, 4210752);
	     this.fontRendererObj.drawString(this.plyInventory.hasCustomInventoryName() ? this.plyInventory.getInventoryName() : I18n.format(this.plyInventory.getInventoryName(), new Object[0]), 8, this.ySize - 96 + 2, 4210752);
	   
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTickTime,
			int mouseX, int mouseY) {
		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        
        //this.mc.getTextureManager().bindTexture(tg_texture);
        
        //draw security button background
        //this.drawTexturedModalRect(k-22-5, l+40-5, 195, 0, 22+5, 30);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture_Chest);
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(k, l + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
	}

	/*@Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(new GuiButtonSecurity(BUTTON_SEC_ID, this.guiLeft-22, this.guiTop+40, 20, 20, "", chest));
	}*/

	/*@Override
	protected void actionPerformed(GuiButton guibutton) {
        //id is the id you give your button
		TGPackets.network.sendToServer(new GUIButtonClick(this.chest,guibutton.id));
	}*/
	
}
