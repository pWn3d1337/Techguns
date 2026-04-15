package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import techguns.TGPackets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * Sends a packet to the client that a gui should be opened
 */
@Deprecated //no longer needed
public class PacketClientOpenGui implements IMessage {
	/**
	 * some id mapping, currently 0=Vanilla Inventory
	 */
	private byte id; 

	public PacketClientOpenGui() {
	}

	public PacketClientOpenGui(byte id) {
		super();
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		id = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(id);
	}
	
	public static class Handler implements  IMessageHandler<PacketClientOpenGui, IMessage> {

		@Override
		public IMessage onMessage(PacketClientOpenGui message, MessageContext ctx) {
			EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
			int id = message.id;
			if(id==0){
				Minecraft.getMinecraft().displayGuiScreen(new GuiInventory(ply));
			}
			return null;
		}
		
	}

}
