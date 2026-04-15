package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import techguns.TGPackets;
import techguns.client.ClientProxy;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class GunFiredMessage implements IMessage {
	protected int entityID;
	protected int time;
	protected byte attackType;
	protected boolean checkRecoil;
	protected boolean akimboGun;
	protected boolean doubleShot;
	
    public GunFiredMessage() 
    { 
     // need this constructor
    }
    
    public GunFiredMessage(EntityLivingBase shooter, int firetime, int attackType, boolean checkRecoil, boolean akimboGun, boolean doubleShot) 
    { 
    	entityID = shooter.getEntityId();
    	time = firetime;
    	this.attackType=(byte)attackType;
    	this.checkRecoil=checkRecoil;
    	this.akimboGun=akimboGun;
    	this.doubleShot=doubleShot;
    }
	
	@Override
	public void fromBytes(ByteBuf buf) {
		entityID=buf.readInt();
		time=buf.readInt();
		attackType=buf.readByte();
		checkRecoil=buf.readBoolean();
		akimboGun=buf.readBoolean();
		this.doubleShot=buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
		buf.writeInt(time);
		buf.writeByte(attackType);
		buf.writeBoolean(checkRecoil);
		buf.writeBoolean(akimboGun);
		buf.writeBoolean(doubleShot);
	}

	
	public static class Handler implements  IMessageHandler<GunFiredMessage, IMessage> {

		@Override
		public IMessage onMessage(GunFiredMessage message, MessageContext ctx) {
			EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
			EntityLivingBase shooter = (EntityLivingBase) ply.worldObj.getEntityByID(message.entityID);
			
			if (shooter !=null){
				ClientProxy cp = ClientProxy.get();
				//if (shooter.getUniqueID()!=Minecraft.getMinecraft().thePlayer.getUniqueID()){
					/*ClientProxy.setplayerRecoiltime(shooter, System.currentTimeMillis()+message.time);
					ClientProxy.setplayerRecoiltimeTotal(shooter,message.time);*/
				
					if (!message.checkRecoil || !cp.isStillRecoiling(shooter, message.attackType)){
						cp.setplayerRecoiltime(shooter, System.currentTimeMillis()+message.time, message.time,message.attackType);
							
							//System.out.println("MESSAGE, gun fired:"+shooter.getClass().toString()+" "+message.time);
						//}
						if ( !message.doubleShot){
							cp.spawnMuzzleFXForEntity(shooter,message.akimboGun&&(message.attackType==1));
						} else {
							cp.spawnMuzzleFXForEntity(shooter,true);
							cp.spawnMuzzleFXForEntity(shooter,false);
						}
					}
			}		
			
			return null;
		}
		
	}
}
