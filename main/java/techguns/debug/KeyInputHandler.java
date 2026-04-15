package techguns.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import techguns.client.particle.FXList;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyInputHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
    	double d=0.1D;//0.05D;
    	
        if(Keybinds.plusX.isPressed()){
        	Keybinds.X+=d;
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("X:"+Keybinds.X));
        }
        if(Keybinds.minusX.isPressed()){
        	Keybinds.X-=d;
        	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("X:"+Keybinds.X));
        }
        if(Keybinds.plusY.isPressed()){
        	Keybinds.Y+=d;
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Y:"+Keybinds.Y));
        }
        if(Keybinds.minusY.isPressed()){
        	Keybinds.Y-=d;
        	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Y:"+Keybinds.Y));
        }
        if(Keybinds.plusZ.isPressed()){
        	Keybinds.Z+=d;
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Z:"+Keybinds.Z));
        }
        if(Keybinds.minusZ.isPressed()){
        	Keybinds.Z-=d;
        	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Z:"+Keybinds.Z));
        }
        if(Keybinds.showXYZ.isPressed()){
        	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("XYZ: "+Keybinds.X+", "+Keybinds.Y+", "+Keybinds.Z));
        }
        if (Keybinds.reloadFX.isPressed()) {
        	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Reloading FXList..."));
        	FXList.loadFXList();
        	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Done!"));
        }
        if (Keybinds.killAllEntityFX.isPressed()) {
        	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Removing all Particles..."));
        	FXList.killAllParticles();	
        	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Done!"));
        }
    }

}