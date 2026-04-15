package techguns;

import net.minecraft.entity.player.EntityPlayer;
import techguns.packets.GUIButtonClick;
import techguns.packets.GunFiredMessage;
import techguns.packets.PacketEntityDeathType;
import techguns.packets.PacketOpenPlayerGUI;
import techguns.packets.PacketPlaySound;
import techguns.packets.PacketRequestTGPlayerSync;
import techguns.packets.PacketRequestTurretSync;
import techguns.packets.PacketShootGun;
import techguns.packets.PacketSpawnParticle;
import techguns.packets.PacketSpawnParticleOnEntity;
import techguns.packets.PacketTGExtendedPlayerSync;
import techguns.packets.PacketTGKeybindPress;
import techguns.packets.PacketTGMessage;
import techguns.packets.PacketTGPlayerFieldSync;
import techguns.packets.ReloadStartedMessage;
import techguns.packets.RequestTileEntitySync;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * Class for dealing with packets
 *
 */
public class TGPackets {
	public static SimpleNetworkWrapper network;
	
	@EventHandler
	public static void init(){
	
		network = NetworkRegistry.INSTANCE.newSimpleChannel("Techguns");
		int packetid=0;
		network.registerMessage(GUIButtonClick.Handler.class, GUIButtonClick.class, packetid++, Side.SERVER);
		network.registerMessage(GunFiredMessage.Handler.class, GunFiredMessage.class, packetid++, Side.CLIENT);
		network.registerMessage(RequestTileEntitySync.Handler.class, RequestTileEntitySync.class, packetid++, Side.SERVER);
		network.registerMessage(ReloadStartedMessage.Handler.class, ReloadStartedMessage.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketOpenPlayerGUI.Handler.class, PacketOpenPlayerGUI.class, packetid++, Side.SERVER);
		//network.registerMessage(PacketClientOpenGui.Handler.class, PacketClientOpenGui.class, 5, Side.CLIENT);
		network.registerMessage(PacketTGExtendedPlayerSync.Handler.class, PacketTGExtendedPlayerSync.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketEntityDeathType.Handler.class, PacketEntityDeathType.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketTGKeybindPress.Handler.class, PacketTGKeybindPress.class, packetid++, Side.SERVER);
		network.registerMessage(PacketShootGun.Handler.class, PacketShootGun.class, packetid++, Side.SERVER);
		network.registerMessage(PacketPlaySound.Handler.class, PacketPlaySound.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketRequestTGPlayerSync.Handler.class, PacketRequestTGPlayerSync.class, packetid++, Side.SERVER);
		network.registerMessage(PacketTGPlayerFieldSync.Handler.class, PacketTGPlayerFieldSync.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketRequestTurretSync.Handler.class, PacketRequestTurretSync.class, packetid++, Side.SERVER);
		network.registerMessage(PacketSpawnParticle.Handler.class, PacketSpawnParticle.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketSpawnParticleOnEntity.Handler.class, PacketSpawnParticleOnEntity.class, packetid++, Side.CLIENT);
		network.registerMessage(PacketTGMessage.Handler.class, PacketTGMessage.class, packetid++, Side.CLIENT);
		//network.registerMessage(MyMessage.Handler.class, MyMessage.class, 0, Side.SERVER);
	       // network.registerMessage(SecondMessage.Handler.class, SecondMessage.class, 1, Side.CLIENT);
	       // ...
    }
	
	public static EntityPlayer getPlayerFromContext(MessageContext ctx){
		EntityPlayer thePlayer = (ctx.side.isClient() ? Techguns.proxy.getPlayerClient() : ctx.getServerHandler().playerEntity);
		return thePlayer;
	}
}
