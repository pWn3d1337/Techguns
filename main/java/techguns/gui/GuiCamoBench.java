package techguns.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import techguns.TGPackets;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.gui.playerinventory.TGPlayerInventory;
import techguns.inventory.CamoBenchContainer;
import techguns.items.armors.ICamoChangeable;
import techguns.packets.GUIButtonClick;
import techguns.tileentities.CamoBenchTileEnt;
import techguns.util.TextUtil;
import cpw.mods.fml.client.config.GuiButtonExt;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GuiCamoBench extends GuiContainer {

	public static final ResourceLocation texture = new ResourceLocation("techguns:textures/gui/camoBench_GUI.png");//new ResourceLocation("textures/gui/container/ammopressgui.png");
	protected CamoBenchTileEnt tileent;
	
	public GuiCamoBench(InventoryPlayer ply,CamoBenchTileEnt tileent) {
		super(new CamoBenchContainer(ply,tileent));
		this.tileent=tileent;
	}

	/**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
	@Override
    protected void drawGuiContainerForegroundLayer(int pari1, int pari2)
    {
		int x = 0;
        int y = 0;
        int color = 4210752; //0xff101010;
        
        String s = this.tileent.hasCustomInventoryName() ? this.tileent.getInventoryName() : TextUtil.trans(this.tileent.getInventoryName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, color);
        //this.fontRendererObj.drawString(TextUtil.trans("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
        
        String s2=TextUtil.trans("techguns.item.invalidcamo");
        String s3= "";
        
        ItemStack item = tileent.getItem();
        if(item!=null){
        	ICamoChangeable camoitem = (ICamoChangeable) item.getItem();
        	s2=TextUtil.trans(camoitem.getCurrentCamoName(item));
        	s3+="("+(camoitem.getCurrentCamoIndex(item)+1)+"/"+(camoitem.getCamoCount())+"):";
        }
        
        this.fontRendererObj.drawString(s3, x+10, y+40, color);
        this.fontRendererObj.drawString(s2, x+10, y+50, color);
        
        for(int i=0;i<4;i++){
        	this.fontRendererObj.drawString(""+getArmorCamo(3-i), 101+(i*19), 38, color);
        }
        
        this.fontRendererObj.drawString(""+getExtendedInvItemCamo(TGPlayerInventory.SLOT_BACK), 80, 38, color);
        this.fontRendererObj.drawString(""+getExtendedInvItemCamo(TGPlayerInventory.SLOT_FACE), 61, 38, color);
        
       // this.fontRendererObj.drawString("Power:",x+10, y+16, color);
       // this.fontRendererObj.drawString(this.tileent.getPowerStored()+"/"+this.tileent.getPowerMax()+" RF",x+10, y+26, color);
    }
	
	@SideOnly(Side.CLIENT)
	private int getArmorCamo(int slot){
		EntityPlayer ply = Minecraft.getMinecraft().thePlayer;
		
		ItemStack armor = ply.getCurrentArmor(slot);
		if(armor != null && armor.getItem() instanceof ICamoChangeable){
			ICamoChangeable camoArmor = (ICamoChangeable) armor.getItem();
			return camoArmor.getCurrentCamoIndex(armor)+1;
		}
		
		return 0;
	}
	
	@SideOnly(Side.CLIENT)
	private int getExtendedInvItemCamo(int slot){
		EntityPlayer ply = Minecraft.getMinecraft().thePlayer;
		
		TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(ply);
		if (props !=null){
		
			ItemStack item = props.TG_inventory.inventory[slot];
			
			if(item != null && item.getItem() instanceof ICamoChangeable){
				ICamoChangeable camoItem = (ICamoChangeable) item.getItem();
				return camoItem.getCurrentCamoIndex(item)+1;
			}
		}
		return 0;
	}
	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTickTime, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        this.drawTexturedModalRect(k-45, l+6, 178, 0, 45, 84);
        
        
        int left = this.guiLeft;
        int top = this.guiTop;
        //renderPlayer(left - 21, top + 75, 30, (float)(left + 51) - this.xSize, (float)(top + 75 - 50) - this.ySize, this.mc.thePlayer);
        int mx = mouseX - (this.width-this.xSize)/2;
        int my = mouseY - (this.height-this.ySize)/2;
        renderPlayer(left - 20, top + 77, 30, -mx, -my, this.mc.thePlayer);
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

	/* (non-Javadoc)
	 * @see net.minecraft.client.gui.inventory.GuiContainer#initGui()
	 */
	@Override
	public void initGui() {
		super.initGui();

										//id, x, y, width, height, text
		this.buttonList.add(new GuiButtonExt(1, this.guiLeft+10+16, this.guiTop+61, 16, 16, ">"));
		this.buttonList.add(new GuiButtonExt(2, this.guiLeft+10, this.guiTop+61, 16, 16, "<"));
		
		int posX=99;
		int offsetX=19;
		int offsetY=59;
		
		this.buttonList.add(new GuiButtonExt(3, this.guiLeft+posX, this.guiTop+offsetY, 10, 10, "+"));
		this.buttonList.add(new GuiButtonExt(4, this.guiLeft+posX, this.guiTop+offsetY+10, 10, 10, "-"));
		
		this.buttonList.add(new GuiButtonExt(5, this.guiLeft+posX+offsetX, this.guiTop+offsetY, 10, 10, "+"));
		this.buttonList.add(new GuiButtonExt(6, this.guiLeft+posX+offsetX, this.guiTop+offsetY+10, 10, 10, "-"));
		
		this.buttonList.add(new GuiButtonExt(7, this.guiLeft+posX+offsetX*2, this.guiTop+offsetY, 10, 10, "+"));
		this.buttonList.add(new GuiButtonExt(8, this.guiLeft+posX+offsetX*2, this.guiTop+offsetY+10, 10, 10, "-"));
		
		this.buttonList.add(new GuiButtonExt(9, this.guiLeft+posX+offsetX*3, this.guiTop+offsetY, 10, 10, "+"));
		this.buttonList.add(new GuiButtonExt(10, this.guiLeft+posX+offsetX*3, this.guiTop+offsetY+10, 10, 10, "-"));
		
		
		//Extended inventory face and back slots
		/*this.buttonList.add(new GuiButtonExt(11, this.guiLeft+posX-offsetX-2, this.guiTop+offsetY, 10, 10, "+"));
		this.buttonList.add(new GuiButtonExt(12, this.guiLeft+posX-offsetX-2, this.guiTop+offsetY+10, 10, 10, "-"));
		
		this.buttonList.add(new GuiButtonExt(13, this.guiLeft+posX-offsetX*2-2, this.guiTop+offsetY, 10, 10, "+"));
		this.buttonList.add(new GuiButtonExt(14, this.guiLeft+posX-offsetX*2-2, this.guiTop+offsetY+10, 10, 10, "-"));*/
		
		
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
        //id is the id you give your button
		TGPackets.network.sendToServer(new GUIButtonClick(this.tileent,guibutton.id));
	}

	/**
	 * Render player model, copied from vanilla inventory
	 * @param x
	 * @param y
	 * @param scale
	 * @param par_f1
	 * @param par_f2
	 * @param ent
	 */
    public static void renderPlayer(int x, int y, int scale, float par_f1, float par_f2, EntityLivingBase ent)
    {
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, 50.0F);
        GL11.glScalef((float)(-scale), (float)scale, (float)scale);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = ent.renderYawOffset;
        float f3 = ent.rotationYaw;
        float f4 = ent.rotationPitch;
        float f5 = ent.prevRotationYawHead;
        float f6 = ent.rotationYawHead;
        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-((float)Math.atan((double)(par_f2 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float)Math.atan((double)(par_f1 / 40.0F)) * 20.0F;
        ent.rotationYaw = (float)Math.atan((double)(par_f1 / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float)Math.atan((double)(par_f2 / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GL11.glTranslatef(0.0F, ent.yOffset, 0.0F);
        RenderManager.instance.playerViewY = 180.0F;
        RenderManager.instance.renderEntityWithPosYaw(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        ent.renderYawOffset = f2;
        ent.rotationYaw = f3;
        ent.rotationPitch = f4;
        ent.prevRotationYawHead = f5;
        ent.rotationYawHead = f6;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}
