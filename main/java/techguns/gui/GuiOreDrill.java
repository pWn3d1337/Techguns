package techguns.gui;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTankInfo;

import org.lwjgl.opengl.GL11;

import techguns.TGConfig;
import techguns.inventory.OreDrillContainer;
import techguns.tileentities.OreDrillTileEntMaster;
import techguns.util.TextUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiOreDrill extends GuiTechgunsPoweredTileEnt {
	public static final ResourceLocation texture = new ResourceLocation("techguns:textures/gui/oreDrill_GUI.png");//new ResourceLocation("textures/gui/container/ammopressgui.png");
	
	protected OreDrillTileEntMaster tileent;

	public GuiOreDrill(InventoryPlayer ply, OreDrillTileEntMaster tileent) {
		super(ply, tileent, new OreDrillContainer(ply,tileent));
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
        int mx = mouseX - (this.width-this.xSize)/2;
        int my = mouseY - (this.height-this.ySize)/2;
        int color = 4210752; //0xff101010;
        int red = 0xFFFF2020;
        
        String s = this.tileent.hasCustomInventoryName() ? this.tileent.getInventoryName() : TextUtil.trans(this.tileent.getInventoryName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, color);
        this.fontRendererObj.drawString(TextUtil.trans("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

        if(!tileent.isComplete()){
        	String s1= TextUtil.trans("techguns.container.oredrill.broken");
        	String s2= TextUtil.trans("techguns.container.oredrill.broken2");
        	int startx = this.xSize/2 - ((s1.length()*6)/2);
        	int startx2 = this.xSize/2 - ((s2.length()*6)/2);
        	 this.fontRendererObj.drawString(s1, startx-16, 44, red);
        	 this.fontRendererObj.drawString(s2, startx2-16, 52, red);
        } else {
        	//this.fontRendererObj.drawString(TextUtil.trans("techguns.MULTIBLOCK OK"), 50, 24, 4210752);
        }
        
        if (this.isInRect(mx, my, 74, 16, 27, 39)){
        	String[] text = {"RF/t: "+this.tileent.currentRFperTick, String.format("%.2f",(this.tileent.progress*1.0f/this.tileent.totalTime*100.0f))+"%","Ores/h: "+String.format("%.2f",1200.0/this.tileent.totalTime*60.0)};
        	this.drawTooltipText(text, mx, my);
       
        } else if (this.isInRect(mx, my, 15, 16, 11, 51)){
        	FluidTankInfo info = this.tileent.getTankInfo(null)[0];
        	this.drawTooltipText(new String[]{info.fluid!=null?TextUtil.trans(info.fluid.getFluid().getUnlocalizedName()):TextUtil.trans("techguns.gui.empty"),tileent.inputTank.getFluidAmount()+"/"+(info.capacity+"mB")}, mx, my);
       
        } else if (this.isInRect(mx, my, 157, 16, 11, 51)){
        	FluidTankInfo info = this.tileent.getTankInfo(null)[1];
        	this.drawTooltipText(new String[]{info.fluid!=null?TextUtil.trans(info.fluid.getFluid().getUnlocalizedName()):TextUtil.trans("techguns.gui.empty"),tileent.outputTank.getFluidAmount()+"/"+(info.capacity+"mB")}, mx, my);
        
        } else if (this.isInRect(mx, my, 34, 36, 8, 14)){
        	if (this.tileent.getCurrentFuelBufferMax()>0){
        		int buf = this.tileent.getCurrentFuelBufferMax();
        		int fuel = this.tileent.getFuelBuffer();
        		
        		int pwrtick = this.tileent.getPowerPerTick();
        		float FACTOR=TGConfig.oreDrillFuelMultiplier;

        		int ticksLeft = pwrtick>0?(int)(fuel/(pwrtick/FACTOR)):-1;
        		
        		
        		this.drawTooltipText(new String[]{TextUtil.trans("techguns.oredrill.currentfuel")+" "+fuel+"/"+buf, TextUtil.trans("techguns.oredrill.remaining")+": "+ticksLeft+" "+TextUtil.trans("techguns.oredrill.ticks")}, mx, my);
        	} else {
        		this.drawTooltipText(new String[]{TextUtil.trans("techguns.oredrill.nofuel")},mx,my);
        	}
        }
        
        super.drawGuiContainerForegroundLayer(mouseX,mouseY);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTickTime, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        if (this.tileent.isWorking()){
	        int i1 = this.tileent.getProgressScaled(36);
	        //this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	        this.drawTexturedModalRect(k + 73, l + 17, 177, 1, 25, i1+1);
        }        
        
        if (this.tileent.getCurrentFuelBufferMax()>0){
        	int s = 12-this.tileent.getFuelBufferScaled(12);
        	this.drawTexturedModalRect(k+35, l+36+s, 188, 39+s, 6, 12-s);
        	
        }
        
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        if(this.tileent.inputTank.getFluidAmount()>0){
        	int px = this.tileent.inputTank.getFluidAmount()*50 / this.tileent.inputTank.getCapacity();
        	IIcon tex = this.tileent.inputTank.getFluid().getFluid().getIcon();
        	this.drawFluidWithTesselator(tex, k+16, l+17, 10, 50, px);
        }
        
        if(this.tileent.outputTank.getFluidAmount()>0){
        	int px = this.tileent.outputTank.getFluidAmount()*50 / this.tileent.outputTank.getCapacity();
        	IIcon tex = this.tileent.outputTank.getFluid().getFluid().getIcon();
        	this.drawFluidWithTesselator(tex, k+158, l+17, 10, 50, px);
        }
        
        this.mc.getTextureManager().bindTexture(this.texture);
        this.drawTexturedModalRect(k+15, l+17, 176, 40, 11, 52);
        this.drawTexturedModalRect(k+157, l+17, 176, 40, 12, 52);
        
        super.drawGuiContainerBackgroundLayer(partialTickTime, mouseX, mouseY);
    }
}
