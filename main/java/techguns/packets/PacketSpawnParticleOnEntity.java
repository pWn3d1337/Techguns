package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import techguns.client.ClientProxy;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSpawnParticleOnEntity implements IMessage {

	String name;
	int entityID;

	
	public PacketSpawnParticleOnEntity() {
		super();
	}	
	
	public PacketSpawnParticleOnEntity(String name, Entity ent) {
		super();
		this.name=name;
		this.entityID=ent.getEntityId();
	}


	@Override
	public void fromBytes(ByteBuf buf) {
		int len = buf.readShort();
		ByteBuf bytes = buf.readBytes(len);
				
		this.name= new String(bytes.array());
		
		this.entityID=buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		byte[] bytes= name.getBytes();
		buf.writeShort(bytes.length);
		buf.writeBytes(bytes);
		
		buf.writeInt(entityID);
	}
	
	public static class Handler implements  IMessageHandler<PacketSpawnParticleOnEntity, IMessage> {

		@Override
		public IMessage onMessage(PacketSpawnParticleOnEntity m, MessageContext ctx) {
			
			//System.out.println("Spawning Particle:"+m.name);
			Entity ent = Minecraft.getMinecraft().thePlayer.worldObj.getEntityByID(m.entityID);
			if (ent!=null){
				ClientProxy.get().createFXOnEntity(m.name, ent);
			} else {
				System.out.println("Got Packet for FX "+m.name+" on Entity, but ent was null");
			}
			return null;
		}
		
	}
}
