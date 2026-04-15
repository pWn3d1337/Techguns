package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import techguns.TGPackets;
import techguns.util.TextUtil;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketTGMessage implements IMessage {

	protected byte messageType;
	
	int paramCoordx;
	int paramCoordy;
	int paramCoordz;
	
	public PacketTGMessage() {
		super();
	}

	public PacketTGMessage(byte messageType, int paramCoordx, int paramCoordy, int paramCoordz) {
		super();
		this.messageType = messageType;
		this.paramCoordx = paramCoordx;
		this.paramCoordy = paramCoordy;
		this.paramCoordz = paramCoordz;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.messageType = buf.readByte();
		//if (messageType==0){
			this.paramCoordx = buf.readInt();
			this.paramCoordy = buf.readInt();
			this.paramCoordz = buf.readInt();
		//}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(messageType);
		
		//if (messageType==0){
			buf.writeInt(paramCoordx);
			buf.writeInt(paramCoordy);
			buf.writeInt(paramCoordz);
		//}

	}

	public static class Handler implements  IMessageHandler<PacketTGMessage, IMessage> {

		@Override
		public IMessage onMessage(PacketTGMessage message, MessageContext ctx) {
			EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
			
			//if(message.messageType==0){
				ply.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.tgmessage."+message.messageType)+" "+message.paramCoordx+", "+message.paramCoordy+", "+message.paramCoordz));
			//}
			
			
			return null;
		}
		
	}
}
