package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import techguns.TGPackets;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestTGPlayerSync implements IMessage {
	public int entityID;
	
	public PacketRequestTGPlayerSync() {
		super();
	}

	public PacketRequestTGPlayerSync(EntityPlayer ply) {
		super();
		this.entityID = ply.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityID= buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
	}
	
	public static class Handler implements  IMessageHandler<PacketRequestTGPlayerSync, IMessage> {

		@Override
		public IMessage onMessage(PacketRequestTGPlayerSync message, MessageContext ctx) {
			EntityPlayer sender = TGPackets.getPlayerFromContext(ctx);
			Entity ent = sender.worldObj.getEntityByID(message.entityID);
			if(ent!=null && ent instanceof EntityPlayer){
				EntityPlayer tracked = (EntityPlayer) ent;
				TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(tracked);
				if(props!=null) {
					TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(tracked, props, false), (EntityPlayerMP) sender);
				}
				
			}
			
			
			return null;
		}
		
	}

}
