package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import techguns.TGPackets;
import techguns.client.ClientProxy;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class ReloadStartedMessage implements IMessage {

	protected int entityID;
	protected int time;
	protected byte attackType;

    public ReloadStartedMessage() 
    { 
     // need this constructor
    }
    
    public ReloadStartedMessage(EntityLivingBase shooter, int firetime,int attackType) 
    { 
    	entityID = shooter.getEntityId();
    	time = firetime;
    	this.attackType=(byte)attackType;
    }
	
	@Override
	public void fromBytes(ByteBuf buf) {
		entityID=buf.readInt();
		time=buf.readInt();
		attackType=buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
		buf.writeInt(time);
		buf.writeByte(attackType);
	}

	
	public static class Handler implements  IMessageHandler<ReloadStartedMessage, IMessage> {

		@Override
		public IMessage onMessage(ReloadStartedMessage message, MessageContext ctx) {
			EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
			EntityLivingBase shooter = (EntityLivingBase) ply.worldObj.getEntityByID(message.entityID);
			
			if (shooter !=null){
				if (shooter!=Minecraft.getMinecraft().thePlayer){
					//ClientProxy.setplayerReloadtime(shooter, System.currentTimeMillis()+message.time);
					//ClientProxy.setplayerReloadtimeTotal(shooter,message.time);
					
					ClientProxy.get().setplayerReloadtime(shooter, System.currentTimeMillis()+message.time, message.time, message.attackType);
					//System.out.println("MESSAGE, gun fired:"+shooter.getClass().toString()+" "+message.time);
				}
			}		
			
			return null;
		}
		
	}

}
