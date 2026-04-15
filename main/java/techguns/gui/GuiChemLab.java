package techguns.gui;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTankInfo;

import org.lwjgl.opengl.GL11;

import techguns.inventory.ChemLabContainer;
import techguns.tileentities.ChemLabTileEnt;
import techguns.util.TextUtil;
import cpw.mods.fml.client.config.GuiButtonExt;

public class GuiChemLab extends GuiTechgunsPoweredTileEnt {
	public static final ResourceLocation texture = new ResourceLocation("techguns:textures/gui/chemLab_Gui.png");//new ResourceLocation("textures/gui/container/ammopressgui.png");
	protected ChemLabTileEnt tileent;
	
	public GuiChemLab(InventoryPlayer ply,ChemLabTileEnt tileent) {
		super(ply,tileent,new ChemLabContainer(ply,tileent));
		this.tileent=tileent;
	}

	/**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
		//int x = 0;
        //int y = 0;
        int color = 4210752; //0xff101010;
        int mx = mouseX - (this.width-this.xSize)/2;
        int my = mouseY - (this.height-this.ySize)/2;
        
        String s = this.tileent.hasCustomInventoryName() ? this.tileent.getInventoryName() : TextUtil.trans(this.tileent.getInventoryName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, color);
        this.fontRendererObj.drawString(TextUtil.trans("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
        
        //this.fontRendererObj.drawString("Power:",x+24, y+58, color);
        //this.fontRendererObj.drawString(this.tileent.getPowerStored()+"/"+this.tileent.getPowerMax()+" RF",x+24, y+68, color);
        
        if (this.isInRect(mx, my, 18, 16, 11, 51)){
        	FluidTankInfo info = this.tileent.getTankInfo(null)[0];
        	this.drawTooltipText(new String[]{info.fluid!=null?TextUtil.trans(info.fluid.getFluid().getUnlocalizedName()):TextUtil.trans("techguns.gui.empty"),tileent.inputTank.getFluidAmount()+"/"+(info.capacity+"mB")}, mx, my);
        } else if (this.isInRect(mx, my, 157, 16, 11, 51)){
        	FluidTankInfo info = this.tileent.getTankInfo(null)[1];
        	this.drawTooltipText(new String[]{info.fluid!=null?TextUtil.trans(info.fluid.getFluid().getUnlocalizedName()):TextUtil.trans("techguns.gui.empty"),tileent.outputTank.getFluidAmount()+"/"+(info.capacity+"mB")}, mx, my);
        } else if(this.isInRect(mx, my, 20, 7, 8, 8)){
        	this.drawTooltipText(new String[]{TextUtil.trans("techguns.chemlab.dumpinput"),TextUtil.trans("techguns.chemlab.dumpinput.tooltip")}, mx, my);
        } else if(this.isInRect(mx, my, 159, 7, 8, 8)){
        	this.drawTooltipText(new String[]{TextUtil.trans("techguns.chemlab.dumpoutput"),TextUtil.trans("techguns.chemlab.dumpoutput.tooltip")}, mx, my);
        } else if(this.isInRect(mx, my, 156, 68, 14, 14)){
        	this.drawTooltipText(new String[]{TextUtil.trans("techguns.chemlab.toogledrain"),TextUtil.trans("techguns.chemlab.toggledrain.tooltip."+this.tileent.getDrainMode())}, mx, my);
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
        this.drawTexturedModalRect(k+134, l+46, 134, 16, 20,20);

        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        if(this.tileent.inputTank.getFluidAmount()>0){
            //int fluidIDin = this.tileent.getFluidType(false)-1;
        	int px = this.tileent.inputTank.getFluidAmount()*50 / this.tileent.inputTank.getCapacity();
        	
        	/*float scalefactor = 16.0f/52.0f;
        	int scaledpx = (int) (px*scalefactor);*/
        	
        	IIcon tex = this.tileent.inputTank.getFluid().getFluid().getIcon();

        	this.drawFluidWithTesselator(tex, k+19, l+17, 10, 50, px);
        }
        
        if(this.tileent.outputTank.getFluidAmount()>0){
            //int fluidIOut = this.tileent.getFluidType(true)-1;
        	int px = this.tileent.outputTank.getFluidAmount()*50 / this.tileent.outputTank.getCapacity();
        	//this.drawTexturedModalRect(k+157, l+17+52-px, 176+(12*fluidIOut), 32+52-px, 12, px);
        	IIcon tex = this.tileent.outputTank.getFluid().getFluid().getIcon();
        	this.drawFluidWithTesselator(tex, k+158, l+17, 10, 50, px);
        }
        
        this.mc.getTextureManager().bindTexture(this.texture);
        this.drawTexturedModalRect(k+18, l+17, 176, 32, 12, 52);
        this.drawTexturedModalRect(k+157, l+17, 176, 32, 12, 52);
        
        if (this.tileent.isWorking()){
	        //int i1 = this.tileent.getProgressScaled(21);
	        //this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	        //this.drawTexturedModalRect(k + 119, l + 36, 176, 0, 20, i1 + 1);
        	
        	float prog = this.tileent.getProgress();
        	int i1 = (int) ((prog<0.2f?(prog*5):1.0f)*25);
        	this.drawTexturedModalRect(k+81, l+19, 178, 5, 8, i1);
        	
        	if (prog>=0.2f){
	        	int i2 = (int) ((prog<0.4f?(prog-0.2f)*5:1.0f)*9);
	        	this.drawTexturedModalRect(k+88, l+39, 186, 25, i2, 1);
        	}
        	
        	if (prog>=0.4f){
	        	int i3 = (int) ((prog<0.6f?(prog-0.4f)*5:1.0f)*20);
	        	this.drawTexturedModalRect(k+97, l+23+(20-i3), 194, 9+(20-i3), 14, i3);
        	}
        	
        	if (prog>=0.6f){
	        	int i4 = (int) ((prog<0.8f?(prog-0.6f)*5:1.0f)*16);
	        	this.drawTexturedModalRect(k+108, l+17, 205, 3, i4, 6);
        	}
        	if (prog>=0.8f){
	        	int i5 = (int) (((prog-0.8f)*5)*24);
	        	this.drawTexturedModalRect(k+117, l+20, 214, 6, 14, i5+1);
        	}
        }
        
        
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
    }

	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(new GuiButtonExt(GuiTechgunsPoweredTileEnt.BUTTON_ID+1, this.guiLeft+20, this.guiTop+7, 8, 8, "x"));
		this.buttonList.add(new GuiButtonExt(GuiTechgunsPoweredTileEnt.BUTTON_ID+2, this.guiLeft+159, this.guiTop+7, 8, 8, "x"));
		this.buttonList.add(new GuiButtonToggleDrainTank(GuiTechgunsPoweredTileEnt.BUTTON_ID+3, this.guiLeft+156, this.guiTop+68, 14, 14, this.tileent));
	}
	
	
}
