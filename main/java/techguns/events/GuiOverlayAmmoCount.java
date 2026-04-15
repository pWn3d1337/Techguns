package techguns.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.ForgeHooks;

import org.lwjgl.opengl.GL11;

import techguns.TGItems;
import techguns.Techguns;
import techguns.debug.Keybinds;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.gui.playerinventory.GuiTGPlayerInventory;
import techguns.gui.playerinventory.TGPlayerInventory;
import techguns.items.armors.ItemTGSpecialSlotAmmo;
import techguns.items.armors.PoweredArmor;
import techguns.items.guns.GenericGun;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;



public class GuiOverlayAmmoCount extends Gui {
	Minecraft mc = Minecraft.getMinecraft();
	private static final ResourceLocation ICONS  = new ResourceLocation("textures/gui/icons.png");
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public void onEvent(RenderGameOverlayEvent event){
		if(event.isCancelable() || event.type != ElementType.EXPERIENCE)
	    {      
	      return;
	    }


		
		EntityPlayer ply = mc.thePlayer;
		TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(ply);
		ItemStack item =ply.getCurrentEquippedItem();
		ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		
		int offsetY = sr.getScaledHeight()-20;
		
		if(item!=null && item.getItem() instanceof GenericGun){
			GenericGun gun = ((GenericGun) item.getItem());
			//int count =Techguns.countMags(ply.inventory.mainInventory, gun.getAmmo(), 0, ply.inventory.mainInventory.length);
						
			ItemStack ammoitem  = gun.getAmmo();
			int count = Techguns.countMags(ply.inventory.mainInventory, ammoitem, 0, ply.inventory.mainInventory.length);
			count += Techguns.countMags(props.TG_inventory.inventory, ammoitem, TGPlayerInventory.SLOTS_AMMO_START, TGPlayerInventory.SLOTS_AMMO_END+1);
			
			if (gun.getAmmoCount()>1){
				count = count / gun.getAmmoCount();
			} 
			
			if (gun.isAkimbo()){
				count = count /2;
			}
			
			
			String text= gun.getAmmoLeftCountTooltip(item)+"/"+gun.getClipsizeTooltip() +EnumChatFormatting.YELLOW+"x" +count;
			mc.fontRenderer.drawString(text, sr.getScaledWidth()+1-text.length()*6, sr.getScaledHeight()-mc.fontRenderer.FONT_HEIGHT-2, 0xFFFFFFFF);
			//System.out.println("FONT_HEIGHT:"+mc.fontRenderer.FONT_HEIGHT);
			if(props!=null && props.showTGHudElements){
				//String nv = "SAFE: "+(props.enableSafemode?"ON":"OFF");
				//mc.fontRenderer.drawString(nv, sr.getScaledWidth()-9*6, sr.getScaledHeight()-(mc.fontRenderer.FONT_HEIGHT+2)*2, 0xFFFFFFFF);
				mc.getTextureManager().bindTexture(GuiTGPlayerInventory.texture);
				this.drawTexturedModalRect(sr.getScaledWidth()-10, offsetY, 242+7*(props.enableSafemode?1:0), 14, 7,7);
				//offsetY-=10;
			}
		}

		if(props!=null && props.showTGHudElements){
			offsetY = sr.getScaledHeight()-30;
			/*String text ="Food Left:"+props.foodleft;
			String text2 ="Last Saturation: "+props.lastSaturation;
			
			mc.fontRenderer.drawString(text, sr.getScaledWidth()-2-text.length()*6, sr.getScaledHeight()-mc.fontRenderer.FONT_HEIGHT-2-10, 0xFFFFFFFF);
			mc.fontRenderer.drawString(text2, sr.getScaledWidth()-2-text2.length()*6, sr.getScaledHeight()-mc.fontRenderer.FONT_HEIGHT-2-20, 0xFFFFFFFF);*/
			
			/*String nv = "NV: "+(props.enableNightVision?"ON":"OFF");
			
			mc.fontRenderer.drawString(nv, sr.getScaledWidth()-7*6, sr.getScaledHeight()-(mc.fontRenderer.FONT_HEIGHT+2)*3, 0xFFFFFFFF);
			
			String step = "SA: "+(props.enableStepAssist?"ON":"OFF");
			
			mc.fontRenderer.drawString(step, sr.getScaledWidth()-7*6, sr.getScaledHeight()-(mc.fontRenderer.FONT_HEIGHT+2)*4, 0xFFFFFFFF);*/
			mc.getTextureManager().bindTexture(GuiTGPlayerInventory.texture);
			
		
			
			//draw power icon
			ItemStack chest =ply.inventory.armorInventory[2];
			if(chest!=null && chest.getItem() instanceof PoweredArmor){
				this.drawTexturedModalRect(sr.getScaledWidth()-10, offsetY, 249, 35, 7,7);
				
				PoweredArmor pwrchest = (PoweredArmor) chest.getItem();
				double percent = (pwrchest.getPower(chest)*1.0D) / (pwrchest.maxpower*1.0D);
				
				ItemStack ammoitem  = pwrchest.getBattery();
				int count = Techguns.countMags(ply.inventory.mainInventory, ammoitem, 0, ply.inventory.mainInventory.length);
				count += Techguns.countMags(props.TG_inventory.inventory, ammoitem, TGPlayerInventory.SLOTS_AMMO_START, TGPlayerInventory.SLOTS_AMMO_END+1);
				
				String text = EnumChatFormatting.YELLOW+""+count+"x"+EnumChatFormatting.WHITE+(int)(percent*100)+"%";
				mc.fontRenderer.drawString(text, sr.getScaledWidth()-2-text.length()*6-8+24, offsetY, 0xFFFFFFFF);
				//mc.fontRenderer.drawString(text2, sr.getScaledWidth()-2-(text.length()+text2.length())*6-8, offsetY, 0xFFFFFFFF);
				offsetY-=10;
				
			}
			
			ItemStack backslot = props.TG_inventory.inventory[props.TG_inventory.SLOT_BACK];
			if (backslot !=null){
				
				if (backslot.getItem() == TGItems.jetpack || backslot.getItem() == TGItems.jumppack || backslot.getItem() == TGItems.antiGravPack){

					int x = 242;
					if (props.enableJetpack){
						x+=7;
					}
					//bind again because string drawing fucks it up
					mc.getTextureManager().bindTexture(GuiTGPlayerInventory.texture);
					this.drawTexturedModalRect(sr.getScaledWidth()-10, offsetY, x, 42, 7,7);
			
					double percent = 1.0D-(backslot.getItemDamageForDisplay()*1.0f) / (backslot.getMaxDamage()*1.0f);
					
					ItemStack ammoitem  = ((ItemTGSpecialSlotAmmo)backslot.getItem()).getAmmo();
					int count = Techguns.countMags(ply.inventory.mainInventory, ammoitem, 0, ply.inventory.mainInventory.length);
					count += Techguns.countMags(props.TG_inventory.inventory, ammoitem, TGPlayerInventory.SLOTS_AMMO_START, TGPlayerInventory.SLOTS_AMMO_END+1);
					
					String text = EnumChatFormatting.YELLOW+""+count+"x"+EnumChatFormatting.WHITE+(int)(percent*100)+"%";
				
					//String text= ""+(int)(percent*100)+"%";
					mc.fontRenderer.drawString(text, sr.getScaledWidth()-2-text.length()*6-8+24, offsetY, 0xFFFFFFFF);
					
					offsetY-=10;
					
				}
				
			}
			
			//needs rebind after string drawing
			mc.getTextureManager().bindTexture(GuiTGPlayerInventory.texture);
			if(Techguns.proxy.getHasNightvision()){
				this.drawTexturedModalRect(sr.getScaledWidth()-10, offsetY, 242+7*(props.enableNightVision?1:0), 7, 7,7);
				offsetY-=10;
			}
			if(Techguns.proxy.getHasStepassist()){
				this.drawTexturedModalRect(sr.getScaledWidth()-10, offsetY, 242+7*(props.enableStepAssist?1:0), 21, 7,7);
				offsetY-=10;
			}
			
			//Check armor durability
			byte mode=0;
			for(int i=0;i<4;i++){
				ItemStack armor=ply.inventory.armorInventory[i];
				if(armor!=null){
					double dur = ((armor.getMaxDamage()-armor.getItemDamage())*1.0D)/(armor.getMaxDamage()*1.0D);
					if (dur<0.35D && mode<1){
						mode=1;
					} else if(armor.getItemDamage()>=(armor.getMaxDamage()-1)){
						mode=2;
						break;
					}
				}
				
			}
			if(mode==2){
				this.drawTexturedModalRect(sr.getScaledWidth()-10, offsetY, 242+7, 28, 7,7);
				offsetY-=10;
			} else if (mode==1){
				this.drawTexturedModalRect(sr.getScaledWidth()-10, offsetY, 242, 28, 7,7);
				offsetY-=10;
			}
			
			
			
		}
	}
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public void onEventArmorBar(RenderGameOverlayEvent.Pre event){
		if(event.type != ElementType.ARMOR)
	    {      
	      return;
	    }

		EntityPlayer ply = mc.thePlayer;
		TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(ply);
		//ItemStack item =ply.getCurrentEquippedItem();
		//ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		
		if(props!=null && props.showTGHudElements){
			event.setCanceled(true);
			
			 int width = event.resolution.getScaledWidth();
		     int height = event.resolution.getScaledHeight();
			
		     int left_height = 39 +10 -1;
		     
			 GL11.glEnable(GL11.GL_BLEND);
			 
	        int left = width / 2 - 91 -2;
	        int top = height - left_height;

	        int level = ForgeHooks.getTotalArmorValue(mc.thePlayer);
	        mc.getTextureManager().bindTexture(GuiTGPlayerInventory.texture);
	        
	        for (int i = 1; level > 0 && i < 24; i += 2)
	        {
	            if (i < level)
	            {
	                drawTexturedModalRect(left, top, 0, 168, 8, 8);
	            }
	            else if (i == level)
	            {
	                drawTexturedModalRect(left, top, 8, 168, 8, 8);
	            }
	            else if (i > level)
	            {
	                drawTexturedModalRect(left, top, 16, 168, 8, 8);
	            }
	            left += 7;
	        }
	        left_height += 10;

	        mc.getTextureManager().bindTexture(ICONS);
	        GL11.glDisable(GL11.GL_BLEND);
		}
		
	}
}
