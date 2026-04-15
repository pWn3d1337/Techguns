package techguns.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.config.GuiButtonExt;
import techguns.inventory.TurretBaseContainer;
import techguns.tileentities.TurretBaseEnt;
import techguns.util.TextUtil;

public class GuiTurretBase extends GuiTechgunsPoweredTileEnt {

	protected static final ResourceLocation texture = new ResourceLocation("techguns:textures/gui/turretBase_GUI.png");//new ResourceLocation("textures/gui/container/ammopressgui.png");
	protected TurretBaseEnt tileent;
	
	public GuiTurretBase(InventoryPlayer ply,TurretBaseEnt tileent) {
		super(ply,tileent,new TurretBaseContainer(ply,tileent));
		this.tileent=tileent;
	}

	/**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
		int x = 0;
        int y = 0;
        int color = 4210752; //0xff101010;
        int mx = mouseX - (this.width-this.xSize)/2;
        int my = mouseY - (this.height-this.ySize)/2;
        
        String s = this.tileent.hasCustomInventoryName() ? this.tileent.getInventoryName() : TextUtil.trans(this.tileent.getInventoryName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, color);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
        
        //this.fontRendererObj.drawString(this.tileent.getPowerStored()+"/"+this.tileent.getPowerMax()+" RF",x+8, y+73, color);
        //this.fontRendererObj.drawString(this.tileent.getPowerStored()+"/"+this.tileent.getPowerMax()+" RF",x+10, y+26, color);
        
        if (this.tileent.isTurretDeath()){
        	//this.fontRendererObj.drawString("Repair: "+this.tileent.getRepairTime()/20+" s",x+98, y+73, color);
        	if (this.isInRect(mx, my, 115, 73, 53, 5)){
        		this.drawTooltipText(TextUtil.trans("techguns.container.repair")+" "+this.tileent.getRepairTime()/20+"s", mx, my);
        	}
        }
        
        if (this.isInRect(mx, my, -22,70,20,20)){
    		this.drawTooltipText(TextUtil.trans("techguns.turret.targetAnimals")+": "+(this.tileent.attackAnimals?EnumChatFormatting.RED+TextUtil.trans("techguns.yes"):EnumChatFormatting.GREEN+TextUtil.trans("techguns.no")), mx, my);
    	}
        
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

        if (this.tileent.isTurretDeath()){
        	int i1= this.tileent.getRepairTimeScaled(52);
        	this.drawTexturedModalRect(k+116,l+74,177,1,i1+1,4);
        }
        
        /*if (this.tileent.isWorking()){
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
        super.drawGuiContainerBackgroundLayer(partialTickTime, mouseX, mouseY);
        
        
        this.mc.getTextureManager().bindTexture(power_texture);
        
        //draw security button background
        this.drawTexturedModalRect(k-22-5, l+70-5, 195, 0, 22+5, 30);
        
        //draw Redstone control;
        /*if(poweredTileEnt.isRedstoneEnabled()){
        	this.drawTexturedModalRect(k+6, l+5, 240, 0, 5, 5);
        	this.drawTexturedModalRect(k+6+5, l+5, 245, 5, 5, 5);
        } else {
        	this.drawTexturedModalRect(k+6, l+5, 240, 5, 5, 5);
        	this.drawTexturedModalRect(k+6+5, l+5, 245, 0, 5, 5);
        }*/
    }

	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(new GuiButtonTurretTargetAnimals(GuiTechgunsPoweredTileEnt.BUTTON_ID+1, this.guiLeft-22, this.guiTop+70,20,20, this.tileent));
	}

	
}
