package techguns.gui.playerinventory;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.TGConfig;
import techguns.TGItems;
import techguns.Techguns;
import techguns.damagesystem.DamageSystem;
import techguns.damagesystem.DamageType;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.gui.GuiCamoBench;
import techguns.gui.GuiTechgunsTileEntBase;
import techguns.items.armors.GenericArmor;
import techguns.plugins.tconstruct.TechgunsTConstructIntegrationClient;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GuiTGPlayerInventory extends GuiTechgunsTileEntBase {
	public static ResourceLocation texture = new ResourceLocation("techguns:textures/gui/TGPlayerInventory.png");
	private final TGPlayerInventory inventory;
	private float sizex;
	private float sizey;
	private TechgunsExtendedPlayerProperties props;
	
	public GuiTGPlayerInventory(EntityPlayer player, InventoryPlayer inv, TGPlayerInventory tgplayerinv) {
		super(new TGPlayerContainer(player, inv, tgplayerinv));
		this.inventory=tgplayerinv;
		this.props = TechgunsExtendedPlayerProperties.get(player);
	}

	
	private String[] getTooltipForToggleButton(int index){
		String[] tooltip = new String[4];
		
		boolean state=false;
		if (index==0){
			state = props.showTGHudElements;
			tooltip[0]=trans("tgguitooltip.togglehud");
			tooltip[1]=trans("tgguitooltip.current")+": "+(!state?"\u00A7c":"\u00A7a")+trans("tgguitooltip."+(state?"on":"off"));
			tooltip[2]=trans("tgguitooltip.togglehud.tooltip1");
			tooltip[3]=trans("tgguitooltip.togglehud.tooltip2");
		} else if (index==1){
			state = props.enableNightVision;
			tooltip[0]=trans("tgguitooltip.togglenv");
			tooltip[1]=trans("tgguitooltip.current")+": "+(!state?"\u00A7c":"\u00A7a")+trans("tgguitooltip."+(state?"on":"off"));
			tooltip[2]=trans("tgguitooltip.togglenv.tooltip1");
			tooltip[3]=trans("tgguitooltip.togglenv.tooltip2");
		} else if (index==2){
			state=props.enableSafemode;
			tooltip[0]=trans("tgguitooltip.togglesafe");
			tooltip[1]=trans("tgguitooltip.current")+": "+(!state?"\u00A7c":"\u00A7a")+trans("tgguitooltip."+(state?"on":"off"));
			tooltip[2]=trans("tgguitooltip.togglesafe.tooltip1");
			tooltip[3]=trans("tgguitooltip.togglesafe.tooltip2");
		} else if (index==3){
			state=props.enableStepAssist;
			tooltip[0]=trans("tgguitooltip.togglestep");
			tooltip[1]=trans("tgguitooltip.current")+": "+(!state?"\u00A7c":"\u00A7a")+trans("tgguitooltip."+(state?"on":"off"));
			tooltip[2]=trans("tgguitooltip.togglestep.tooltip1");
			tooltip[3]=trans("tgguitooltip.togglestep.tooltip2");
		} else if (index==4){
			state=props.enableJetpack;
			tooltip[0]=trans("tgguitooltip.togglejetpack");
			tooltip[1]=trans("tgguitooltip.current")+": "+(!state?"\u00A7c":"\u00A7a")+trans("tgguitooltip."+(state?"on":"off"));
			tooltip[2]=trans("tgguitooltip.togglejetpack.tooltip1");
			tooltip[3]=trans("tgguitooltip.togglejetpack.tooltip2");
		}
		
		return tooltip;
	}
	
	@SideOnly(Side.CLIENT)
	private String[] getTooltipTotalArmorValues(){
		String[] tooltip = new String[10];
		tooltip[0]=EnumChatFormatting.UNDERLINE+trans("tgguitooltip.totalarmorvalues");
		
		/*
		list.add(EnumChatFormatting.DARK_GRAY+" AR: "+formatAV(this.material.getArmorValueSlot(slot, DamageType.PHYSICAL),DamageType.PHYSICAL));
		list.add(EnumChatFormatting.GRAY+" PR: "+formatAV(this.material.getArmorValueSlot(slot, DamageType.PROJECTILE),DamageType.PROJECTILE));
		list.add(EnumChatFormatting.DARK_RED+" EX: "+formatAV(this.material.getArmorValueSlot(slot, DamageType.EXPLOSION),DamageType.EXPLOSION));
		list.add(EnumChatFormatting.DARK_AQUA+" E: "+formatAV(this.material.getArmorValueSlot(slot, DamageType.ENERGY),DamageType.ENERGY));
				
		list.add(EnumChatFormatting.RED+" F: "+formatAV(this.material.getArmorValueSlot(slot, DamageType.FIRE),DamageType.FIRE));
		list.add(EnumChatFormatting.AQUA+" I: "+formatAV(this.material.getArmorValueSlot(slot, DamageType.ICE),DamageType.ICE));
		list.add(EnumChatFormatting.YELLOW+" L: "+formatAV(this.material.getArmorValueSlot(slot, DamageType.LIGHTNING),DamageType.LIGHTNING));
		list.add(EnumChatFormatting.DARK_GREEN+" P: "+formatAV(this.material.getArmorValueSlot(slot, DamageType.POISON),DamageType.POISON));
		list.add(EnumChatFormatting.GREEN+" RAD: "+formatAV(this.material.getArmorValueSlot(slot, DamageType.RADIATION),DamageType.RADIATION));	
		 */
		EntityPlayer ply = Minecraft.getMinecraft().thePlayer;
		tooltip[1]=EnumChatFormatting.DARK_GRAY+trans("TGDamageType.PHYSICAL")+": "+GenericArmor.formatAV(DamageSystem.getTotalArmorAgainstType(ply, DamageType.PHYSICAL), DamageType.PHYSICAL);
		tooltip[2]=EnumChatFormatting.GRAY+trans("TGDamageType.PROJECTILE")+": "+GenericArmor.formatAV(DamageSystem.getTotalArmorAgainstType(ply, DamageType.PROJECTILE), DamageType.PROJECTILE);
		
		tooltip[3]=EnumChatFormatting.DARK_RED+trans("TGDamageType.EXPLOSION")+": "+GenericArmor.formatAV(DamageSystem.getTotalArmorAgainstType(ply, DamageType.EXPLOSION), DamageType.EXPLOSION);
		
		tooltip[4]=EnumChatFormatting.DARK_AQUA+trans("TGDamageType.ENERGY")+": "+GenericArmor.formatAV(DamageSystem.getTotalArmorAgainstType(ply, DamageType.ENERGY), DamageType.ENERGY);
		
		tooltip[5]=EnumChatFormatting.RED+trans("TGDamageType.FIRE")+": "+GenericArmor.formatAV(DamageSystem.getTotalArmorAgainstType(ply, DamageType.FIRE), DamageType.FIRE);
		tooltip[6]=EnumChatFormatting.AQUA+trans("TGDamageType.ICE")+": "+GenericArmor.formatAV(DamageSystem.getTotalArmorAgainstType(ply, DamageType.ICE), DamageType.ICE);
		tooltip[7]=EnumChatFormatting.YELLOW+trans("TGDamageType.LIGHTNING")+": "+GenericArmor.formatAV(DamageSystem.getTotalArmorAgainstType(ply, DamageType.LIGHTNING), DamageType.LIGHTNING);
		
		tooltip[8]=EnumChatFormatting.DARK_GREEN+trans("TGDamageType.POISON")+": "+GenericArmor.formatAV(DamageSystem.getTotalArmorAgainstType(ply, DamageType.POISON), DamageType.POISON);
		
		tooltip[9]=EnumChatFormatting.GREEN+trans("TGDamageType.RADIATION")+": "+GenericArmor.formatAV(DamageSystem.getTotalArmorAgainstType(ply, DamageType.RADIATION), DamageType.RADIATION);
		
		
		return tooltip;
	}
	
	@SideOnly(Side.CLIENT)
	private String[] getTooltipRadiationBar(){
		String[] tooltip = new String[3];
		
		TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(Minecraft.getMinecraft().thePlayer);
		
		int displayedRad =0;
		if(props!=null){
			displayedRad = (int) (props.radlevel * 0.001);			
		}
		
		
		tooltip[0]=EnumChatFormatting.GREEN+""+EnumChatFormatting.UNDERLINE+trans("tgguitooltip.currentrad");
		tooltip[1]=displayedRad+"/"+"1000";
		tooltip[2]=trans("tgguitooltip.radeffects")+": "+EnumChatFormatting.GREEN+"NONE";
		
		return tooltip;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		 int mx = mouseX - (this.width-this.xSize)/2;
	     int my = mouseY - (this.height-this.ySize)/2;
	        
		 for(int i=0;i<5;i++){
	        if (this.isInRect(mx, my, 173, 7+i*11, 11, 11)){
	        	this.drawTooltipText(getTooltipForToggleButton(i), mx, my);
	        }
        }
		 
		 //Total Armor Tooltip
		 if(this.isInRect(mx, my, 99, 7, 14, 14)){
			 this.drawTooltipText(this.getTooltipTotalArmorValues(), mx, my);
		 }
		 
		 //Radiation Bar Tooltip
		 if(this.isInRect(mx, my, 115, 7, 54, 6)){
			 this.drawTooltipText(this.getTooltipRadiationBar(), mx, my);
		 }
	}


	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTickTime, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
       
        
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        /*this.drawTexturedModalRect(k-20-4, l+6, 177, 0, 24, 26);
        this.drawTexturedModalRect(k-20-4, l+6+26, 177, 26, 24, 26);*/
        
        //System.out.println("MX:"+mx+" MY:"+my+" k:"+k+" l:"+l);
        //tooltips:
       
        
        
        //this.drawTexturedModalRect(k-45, l+6, 178, 0, 45, 84);
        //int left = this.guiLeft;
        //int top = this.guiTop;
        //renderPlayer(left - 21, top + 75, 30, (float)(left + 51) - this.xSize, (float)(top + 75 - 50) - this.ySize, this.mc.thePlayer);
        //int mx = mouseX - (this.width-this.xSize)/2;
        //int my = mouseY - (this.height-this.ySize)/2;
        GuiCamoBench.renderPlayer(k + 51, l + 75, 30, (float)(k + 51) - this.sizex, (float)(l + 75 - 50) - this.sizey, this.mc.thePlayer);
	}

	@Override
	public void drawScreen(int i1, int i2, float f1) {
		super.drawScreen(i1, i2, f1);
		this.sizex=(float)i1;
		this.sizey=(float)i2;
	}



	@Override
	public void initGui() {
		super.initGui();
		int index=0;
		
		if (!Techguns.isTConstructLoaded){
			this.buttonList.add(new TGGuiTabButton(index, this.guiLeft-26, this.guiTop+5, true, new ItemStack(Blocks.crafting_table,1),0));
		    this.buttonList.add(new TGGuiTabButton(index, this.guiLeft-26, this.guiTop+5+26, false,TGItems.newStack(TGItems.bullets9mm,1),TGConfig.GUI_ID_tgplayerInventory));
		} else {
			((TechgunsTConstructIntegrationClient)Techguns.pluginTConstructIntegration).addTabs(this.guiLeft, this.guiTop, this.buttonList);

		}
		
	    for(int x =0; x<5;x++){
	    	this.buttonList.add(new TGGuiToggleButton(++index, this.guiLeft+173, this.guiTop+7+(x*11), x));
	    }
	}

	
	
}
