package techguns.gui;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTankInfo;

import org.lwjgl.opengl.GL11;

import techguns.inventory.ReactionChamberContainer;
import techguns.tileentities.ReactionChamberTileEntMaster;
import techguns.util.TextUtil;
import cpw.mods.fml.client.config.GuiButtonExt;

public class GuiReactionChamber extends GuiTechgunsPoweredTileEnt {

	public static final ResourceLocation texture = new ResourceLocation("techguns:textures/gui/ReactionChamber_Gui.png");//new ResourceLocation("textures/gui/container/ammopressgui.png");
	protected ReactionChamberTileEntMaster tileent;
	
	public GuiReactionChamber(InventoryPlayer ply,ReactionChamberTileEntMaster tileent) {
		super(ply,tileent,new ReactionChamberContainer(ply,tileent));
		this.tileent=tileent;
	}
	
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
     
        
        int fluidLevel=this.tileent.liquidLevel*10;
        int intensity = this.tileent.intensity;
        
        this.fontRendererObj.drawString(fluidLevel+"%", 34, 50, 4210752);
        this.fontRendererObj.drawString(intensity+"", 121, 18, 4210752);
        
        
        if (this.isInRect(mx, my, 18, 16, 11, 51)){
        	FluidTankInfo info = this.tileent.getTankInfo(null)[0];
        	this.drawTooltipText(new String[]{info.fluid!=null?TextUtil.trans(info.fluid.getFluid().getUnlocalizedName()):"Empty",tileent.inputTank.getFluidAmount()+"/"+(info.capacity+"mB")}, mx, my);
        } else if(this.isInRect(mx, my, 20, 7, 8, 8)){
        	this.drawTooltipText(new String[]{TextUtil.trans("techguns.chemlab.dumpinput"),TextUtil.trans("techguns.chemlab.dumpinput.tooltip")}, mx, my);
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
        //this.drawTexturedModalRect(k+134, l+46, 134, 16, 20,20);
        
        
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        if(this.tileent.inputTank.getFluidAmount()>0){
            //int fluidIDin = this.tileent.getFluidType(false)-1;
        	int px = this.tileent.inputTank.getFluidAmount()*50 / this.tileent.inputTank.getCapacity();
        	
        	/*float scalefactor = 16.0f/52.0f;
        	int scaledpx = (int) (px*scalefactor);*/
        	
        	IIcon tex = this.tileent.inputTank.getFluid().getFluid().getIcon();
        	this.drawFluidWithTesselator(tex, k+19, l+17, 10, 50, px);
        }        
        
        this.mc.getTextureManager().bindTexture(this.texture);
        
        int px_height = 50-this.tileent.liquidLevel*5;

        this.drawTexturedModalRect(k+18, l+17, 176, 32, 12, 52);
        
        this.drawTexturedModalRect(k+19, l+17+px_height, 177, 32, 7, 1);
        //this.drawTexturedModalRect(k+157, l+17, 176, 32, 12, 52);
        
        

        
        
        if (this.tileent.isWorking()){
        	
        	int compl = this.tileent.currentReaction.completion;
        	int req_compl = this.tileent.currentReaction.requiredCompletion;
        	
        	int compl_px = Math.round(((compl*1.0f)/(req_compl*1.0f))*100f);
        	
        	int dur = this.tileent.currentReaction.duration;
        	int maxdur = this.tileent.currentReaction.maxDuration;
        	
        	int dur_px = Math.round(((dur*1.0f)/(maxdur*1.0f))*100f);
        	
        	this.drawTexturedModalRect(k+67, l+61, 0, 167, compl_px, 4);
        	
        	this.drawTexturedModalRect(k+67, l+69, 0, 175, dur_px, 4);
        	
        	
        	
        	  //draw intensity
        	int px = (10-this.tileent.intensity)*4;
        	
        	if (this.tileent.currentReaction.preferedIntensity!=this.tileent.intensity) {
        	
        		this.drawTexturedModalRect(k+114, l+16+px, 198, px, 5, 40-px);

        	} else {
        		this.drawTexturedModalRect(k+114, l+16+px, 190, px, 5, 40-px);
        	}
        	
        	int h = 40-this.tileent.currentReaction.preferedIntensity*4;
            this.drawTexturedModalRect(k+112, l+15+h, 178, 22, 5, 3);
        	
        	
        }  else {
            //draw intensity
        	int px = (10-this.tileent.intensity)*4;
        	this.drawTexturedModalRect(k+114, l+16+px, 190, px, 5, 40-px);
        	
        }

        super.drawGuiContainerBackgroundLayer(partialTickTime, mouseX, mouseY);
    }
	
	@Override
	public void initGui() {
		super.initGui();
		int buttonid = GuiTechgunsPoweredTileEnt.BUTTON_ID+1;
		
		int left=33;
		this.buttonList.add(new GuiButtonExt(buttonid++, this.guiLeft+left, this.guiTop+58, 10, 10, "+"));
		this.buttonList.add(new GuiButtonExt(buttonid++, this.guiLeft+left+10, this.guiTop+58, 10, 10, "-"));
		
		left=91;
		this.buttonList.add(new GuiButtonExt(buttonid++, this.guiLeft+left, this.guiTop+36, 10, 10, "+"));
		this.buttonList.add(new GuiButtonExt(buttonid++, this.guiLeft+left+10, this.guiTop+36, 10, 10, "-"));
		
		this.buttonList.add(new GuiButtonExt(buttonid++, this.guiLeft+20, this.guiTop+7, 8, 8, "x"));
	}
}
