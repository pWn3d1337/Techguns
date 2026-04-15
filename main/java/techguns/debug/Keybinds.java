package techguns.debug;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;

public class Keybinds {

	public static double X=0;
	public static double Y=0;
	public static double Z=0;
	
	
    // Declare two KeyBindings, ping and pong
    public static KeyBinding plusX;
    public static KeyBinding minusX;
    public static KeyBinding plusY;
    public static KeyBinding minusY;
    public static KeyBinding plusZ;
    public static KeyBinding minusZ;
    public static KeyBinding showXYZ;
    public static KeyBinding reloadFX;
    public static KeyBinding killAllEntityFX;

    public static void init() {
        // Define the "ping" binding, with (unlocalized) name "key.ping" and
        // the category with (unlocalized) name "key.categories.mymod" and
        // key code 24 ("O", LWJGL constant: Keyboard.KEY_O)
        plusX = new KeyBinding("key.+x", Keyboard.KEY_NUMPAD7, "key.categories.techguns");
        // Define the "pong" binding, with (unlocalized) name "key.pong" and
        // the category with (unlocalized) name "key.categories.mymod" and
        // key code 25 ("P", LWJGL constant: Keyboard.KEY_P)
        minusX = new KeyBinding("key.-x", Keyboard.KEY_NUMPAD8, "key.categories.techguns");
        
        plusY = new KeyBinding("key.+y", Keyboard.KEY_NUMPAD4, "key.categories.techguns");
        minusY = new KeyBinding("key.-y", Keyboard.KEY_NUMPAD5, "key.categories.techguns");
        
        plusZ = new KeyBinding("key.+z", Keyboard.KEY_NUMPAD1, "key.categories.techguns");
        minusZ = new KeyBinding("key.-z", Keyboard.KEY_NUMPAD2, "key.categories.techguns");
        
        showXYZ = new KeyBinding("key.show", Keyboard.KEY_NUMPAD0, "key.categories.techguns");
        
        reloadFX = new KeyBinding("key.reloadFX", Keyboard.KEY_NUMPADENTER, "key.categories.techguns");
        killAllEntityFX = new KeyBinding("key.killAllEntityFX", Keyboard.KEY_NUMPADCOMMA, "key.categories.techguns");
        // Register both KeyBindings to the ClientRegistry
        ClientRegistry.registerKeyBinding(plusX);
        ClientRegistry.registerKeyBinding(plusY);
        ClientRegistry.registerKeyBinding(plusZ);
        ClientRegistry.registerKeyBinding(minusX);
        ClientRegistry.registerKeyBinding(minusY);
        ClientRegistry.registerKeyBinding(minusZ);
        ClientRegistry.registerKeyBinding(showXYZ);
        ClientRegistry.registerKeyBinding(reloadFX);
        ClientRegistry.registerKeyBinding(killAllEntityFX);
        
    }

}
