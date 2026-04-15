package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import techguns.client.ClientProxy;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSpawnParticle implements IMessage {

	String name;
	
	double posX;
	double posY;
	double posZ;
	
	double motionX=0.0D;
	double motionY=0.0D;
	double motionZ=0.0D;
	
	
	public PacketSpawnParticle() {
		super();
	}	
	
	public PacketSpawnParticle(String name, double posX, double posY, double posZ) {
		super();
		this.name=name;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	public PacketSpawnParticle(String name, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
		super();
		this.name=name;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int len = buf.readShort();
		ByteBuf bytes = buf.readBytes(len);
				
		this.name= new String(bytes.array());
		
		this.posX=buf.readDouble();
		this.posY=buf.readDouble();
		this.posZ=buf.readDouble();
		
		this.motionX=buf.readDouble();
		this.motionY=buf.readDouble();
		this.motionZ=buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		byte[] bytes= name.getBytes();
		buf.writeShort(bytes.length);
		buf.writeBytes(bytes);
		
		buf.writeDouble(posX);
		buf.writeDouble(posY);
		buf.writeDouble(posZ);
		
		buf.writeDouble(motionX);
		buf.writeDouble(motionY);
		buf.writeDouble(motionZ);
	}
	
	public static class Handler implements  IMessageHandler<PacketSpawnParticle, IMessage> {

		@Override
		public IMessage onMessage(PacketSpawnParticle m, MessageContext ctx) {
			
			//System.out.println("Spawning Particle:"+m.name);
			ClientProxy.get().createFX(m.name, Minecraft.getMinecraft().thePlayer.worldObj, m.posX, m.posY, m.posZ, m.motionX, m.motionY, m.motionZ);
			
			return null;
		}
		
	}

}
