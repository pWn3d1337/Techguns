package techguns.gui;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.TGPackets;
import techguns.Techguns;
import techguns.inventory.RepairBenchContainer;
import techguns.items.armors.GenericArmor;
import techguns.packets.GUIButtonClick;
import techguns.tileentities.RepairBenchTileEnt;
import techguns.util.TextUtil;

public class GuiRepairBench extends GuiTechgunsTileEntBase {

	public static final ResourceLocation texture = new ResourceLocation("techguns:textures/gui/repairBench_GUI.png");//new ResourceLocation("textures/gui/container/ammopressgui.png");
	protected RepairBenchTileEnt tileent;
	protected InventoryPlayer invplayer;
	
	public GuiRepairBench(InventoryPlayer ply,RepairBenchTileEnt tileent) {
		super(new RepairBenchContainer(ply,tileent));
		this.tileent=tileent;
		this.invplayer=ply;
	}
	
	private void drawTooltipRepairMats(ItemStack item, int mx, int my){
		if(item!=null){
			
			if(item.getItem() instanceof GenericArmor && ((GenericArmor)item.getItem()).canRepairOnRepairBench(item)){
				
				if (item.getItemDamage()>0){
					
					List<ItemStack> mats =((GenericArmor)item.getItem()).getRepairMats(item);
					String[] tooltips = new String[mats.size()+1];
					tooltips[0]=TextUtil.trans("techguns.repairBench.requiredMats");
					for(int i=1;i<mats.size()+1;i++){
						String prefix="";
						if(Techguns.canConsumeItem(this.tileent.getContent(), mats.get(i-1), 0, this.tileent.getContent().length)>0){
							prefix="\u00A7c";
						}
						tooltips[i]=prefix+mats.get(i-1).stackSize+"x "+TextUtil.trans(mats.get(i-1).getUnlocalizedName()+".name");
					}
					
					this.drawTooltipText(tooltips, mx, my);
					
				} else {
					this.drawTooltipText(TextUtil.trans("techguns.repairBench.fullcondition"),mx, my);
				}
				
			} else {
				this.drawTooltipText(TextUtil.trans("techguns.repairBench.cantrepair"),mx, my);
			}
			
		} else {
			this.drawTooltipText(TextUtil.trans("techguns.repairBench.noarmor"),mx, my);
		}
		
	}
	
	/**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
		int x = 0;
        int y = 0;
        int mx = mouseX - (this.width-this.xSize)/2;
        int my = mouseY - (this.height-this.ySize)/2;
        int color = 4210752; //0xff101010;
        
        String s = this.tileent.hasCustomInventoryName() ? this.tileent.getInventoryName() : TextUtil.trans(this.tileent.getInventoryName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, color);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 +4, 4210752);
        
        
        for(int i=0;i<4;i++){
	        if (this.isInRect(mx, my, 90+i*20, 39, 14, 14)){
	        	ItemStack armor = this.invplayer.armorInventory[3-i];  	
	        	this.drawTooltipRepairMats(armor, mx, my);
	        }
        }
        
       // String s2=I18n.format("item.invalidcamo", new Object[0]);
       // String s3= "";
        
      /*  ItemStack item = tileent.getItem();
        if(item!=null){
        	ICamoChangeable camoitem = (ICamoChangeable) item.getItem();
        	s2=I18n.format(camoitem.getCurrentCamoName(item), new Object[0]);
        	s3+="Camo ("+(camoitem.getCurrentCamoIndex(item)+1)+"/"+(camoitem.getCamoCount())+"):";
        }*/
        
        //this.fontRendererObj.drawString(s3, x+10, y+40, color);
        //this.fontRendererObj.drawString(s2, x+10, y+50, color);
        
    /*    for(int i=0;i<4;i++){
        	this.fontRendererObj.drawString(""+getArmorCamo(3-i), 94+(i*20), 39, color);
        }*/
        
       // this.fontRendererObj.drawString("Power:",x+10, y+16, color);
       // this.fontRendererObj.drawString(this.tileent.getPowerStored()+"/"+this.tileent.getPowerMax()+" RF",x+10, y+26, color);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTickTime, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        //this.drawTexturedModalRect(k-45, l+6, 178, 0, 45, 84);
        
        
       // int left = this.guiLeft;
       // int top = this.guiTop;
        //renderPlayer(left - 21, top + 75, 30, (float)(left + 51) - this.xSize, (float)(top + 75 - 50) - this.ySize, this.mc.thePlayer);
      //  int mx = mouseX - (this.width-this.xSize)/2;
       // int my = mouseY - (this.height-this.ySize)/2;
       // renderPlayer(left - 20, top + 77, 30, -mx, -my, this.mc.thePlayer);
        /*
        if (this.tileent.isWorking()){
	        int i1 = this.tileent.getProgressScaled(21);
	        //this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	        this.drawTexturedModalRect(k + 119, l + 36, 176, 0, 20, i1 + 1);
        }*/
        
        
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
    }

	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(new GuiButtonRepair(1, this.guiLeft+90, this.guiTop+39, 14, 14));
		this.buttonList.add(new GuiButtonRepair(2, this.guiLeft+90+20, this.guiTop+39, 14, 14));
		this.buttonList.add(new GuiButtonRepair(3, this.guiLeft+90+40, this.guiTop+39, 14, 14));
		this.buttonList.add(new GuiButtonRepair(4, this.guiLeft+90+60, this.guiTop+39, 14, 14));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
        //id is the id you give your button
		TGPackets.network.sendToServer(new GUIButtonClick(this.tileent,guibutton.id));
	}
	
}
