package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import techguns.TGPackets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class RequestTileEntitySync implements IMessage {
	private int x;
	private short y;
	private int z;
	
	public RequestTileEntitySync() {
		// need this constructor
	}
	
	public RequestTileEntitySync(int x, short y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.x=buf.readInt();
		this.y=buf.readShort();
		this.z=buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeShort(y);
		buf.writeInt(z);
	}

	public static class Handler implements  IMessageHandler<RequestTileEntitySync, IMessage> {

		@Override
		public IMessage onMessage(RequestTileEntitySync message, MessageContext ctx) {
			EntityPlayerMP ply = (EntityPlayerMP) TGPackets.getPlayerFromContext(ctx);
			
			TileEntity tile = ply.worldObj.getTileEntity(message.x, message.y, message.z);
			
	//		System.out.println("Got sync request");
			
			if(tile != null){
				//ply.worldObj.markBlockForUpdate(message.x, message.y, message.z);
				//tile.markDirty();
//				System.out.println("Sent tile entity Update");
				ply.playerNetServerHandler.sendPacket(tile.getDescriptionPacket());
			}
			
			
			
			return null;
		}
		
	}
}
