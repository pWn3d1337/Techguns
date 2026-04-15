package techguns.packets;

import techguns.TGPackets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketGrenadeBounce implements IMessage {

	int entityId;
	double motionX;
	double motionY;
	double motionZ;
	
	public PacketGrenadeBounce(){
	}
	
	public PacketGrenadeBounce(Entity ent, double motionX, double motionY, double motionZ) {
		entityId = ent.getEntityId();
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
	}
	
	public PacketGrenadeBounce(Entity ent) {
		entityId = ent.getEntityId();
		this.motionX = ent.motionX;
		this.motionY = ent.motionY;
		this.motionZ = ent.motionZ;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		entityId = buf.readInt();
		motionX=buf.readDouble();
		motionY=buf.readDouble();
		motionZ=buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityId);
		buf.writeDouble(motionX);
		buf.writeDouble(motionY);
		buf.writeDouble(motionZ);
	}

	public static class Handler implements  IMessageHandler<PacketGrenadeBounce, IMessage> {

		@Override
		public IMessage onMessage(PacketGrenadeBounce message, MessageContext ctx) {
			EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
			
			Entity entity = (Entity) ply.worldObj.getEntityByID(message.entityId);
			if (entity!=null){
				entity.motionX=message.motionX;
				entity.motionY=message.motionY;
				entity.motionZ=message.motionZ;
			}
			
			return null;
		}
		
	}
	
}
