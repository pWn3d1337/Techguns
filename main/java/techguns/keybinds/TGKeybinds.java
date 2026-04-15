package techguns.keybinds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

import techguns.TGPackets;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.items.guns.GenericGun;
import techguns.packets.PacketTGKeybindPress;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TGKeybinds {
	public static KeyBinding KEY_TOGGLE_NIGHTVISION;
	public static KeyBinding KEY_TOGGLE_SAFEMODE;
	public static KeyBinding KEY_FORCE_RELOAD;
	public static KeyBinding KEY_TOGGLE_JETPACK;
	
	/**
	 * client only
	 */
	public static KeyBinding KEY_TOGGLE_STEPASSIST;
	
	public static void init(){
		KEY_TOGGLE_NIGHTVISION=new KeyBinding("techguns.key.toggleNightvision", Keyboard.KEY_N, "techguns.key.categories.techguns");
		KEY_TOGGLE_SAFEMODE=new KeyBinding("techguns.key.toggleSafemode", Keyboard.KEY_B, "techguns.key.categories.techguns");
		KEY_TOGGLE_STEPASSIST=new KeyBinding("techguns.key.toggleStepassist", Keyboard.KEY_V, "techguns.key.categories.techguns");
		KEY_FORCE_RELOAD=new KeyBinding("techguns.key.forceReload", Keyboard.KEY_R, "techguns.key.categories.techguns");
		KEY_TOGGLE_JETPACK=new KeyBinding("techguns.key.toggleJetpack", Keyboard.KEY_J, "techguns.key.categories.techguns");
		
		ClientRegistry.registerKeyBinding(KEY_TOGGLE_NIGHTVISION);
		ClientRegistry.registerKeyBinding(KEY_TOGGLE_SAFEMODE);
		ClientRegistry.registerKeyBinding(KEY_TOGGLE_STEPASSIST);
		ClientRegistry.registerKeyBinding(KEY_FORCE_RELOAD);
		ClientRegistry.registerKeyBinding(KEY_TOGGLE_JETPACK);
	}
	
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
    	
        if(KEY_TOGGLE_NIGHTVISION.isPressed()){
        	TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.TOGGLE_NIGHTVISION));
        } else if(KEY_TOGGLE_SAFEMODE.isPressed()){
        	TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.TOGGLE_SAFEMODE));
        } else if(KEY_TOGGLE_JETPACK.isPressed()){
        	TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.TOGGLE_JETPACK));
        } else if(KEY_TOGGLE_STEPASSIST.isPressed()){
        	
        	/*TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(Minecraft.getMinecraft().thePlayer);
        	if (props!=null){
        		props.enableStepAssist=!props.enableStepAssist;
        	}*/
        	TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.TOGGLE_STEP_ASSIST));
        } else if (KEY_FORCE_RELOAD.isPressed()){
        	EntityPlayer ply = Minecraft.getMinecraft().thePlayer;
        	
        	ItemStack currentItem = ply.getCurrentEquippedItem();
        	if (currentItem!=null && currentItem.getItem() instanceof GenericGun){
        		
        		TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(Minecraft.getMinecraft().thePlayer);
            	if (props!=null){
	            	
	        		GenericGun gun = (GenericGun) currentItem.getItem();
	        		if (props.fireDelay<=0 && !gun.isFullyLoaded(currentItem)){
		        					
		        		TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.FORCE_RELOAD));
		        		gun.tryForcedReload(currentItem, ply.worldObj,ply);
	        		}
        		}
        	}
        	
        }
    }

}
