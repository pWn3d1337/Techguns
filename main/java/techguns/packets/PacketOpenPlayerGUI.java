package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import techguns.TGPackets;
import techguns.Techguns;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketOpenPlayerGUI implements IMessage {
	private short guiID;

	public PacketOpenPlayerGUI(short guiID) {
		this.guiID = guiID;
	}

	public PacketOpenPlayerGUI() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.guiID=buf.readShort();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeShort(this.guiID);
	}

	public static class Handler implements  IMessageHandler<PacketOpenPlayerGUI, IMessage> {

		@Override
		public IMessage onMessage(PacketOpenPlayerGUI message, MessageContext ctx) {
			EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
			if(message.guiID>0){
				ply.openGui(Techguns.instance, message.guiID, ply.worldObj, (int)ply.posX, (int)ply.posY, (int)ply.posZ);
			} /*else {
				TGPackets.network.sendTo(new PacketClientOpenGui((byte) (message.guiID*-1)), (EntityPlayerMP) ply);
			}*/
			//System.out.println("Open player gui:"+message.guiID);
			return null;
		}
		
	}
}
