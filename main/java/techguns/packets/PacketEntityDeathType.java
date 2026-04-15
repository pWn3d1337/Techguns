package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import techguns.TGConfig;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.client.ClientProxy;
import techguns.client.DeathEffect;
import techguns.client.audio.TGSoundCategory;
import techguns.util.EntityDeathUtils.DeathType;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketEntityDeathType implements IMessage{
	
	int entityId;
	int deathtypeId;
	float motionX;
	float motionY;
	float motionZ;
	
	public PacketEntityDeathType() {
		
	}
	
	

	public PacketEntityDeathType(EntityLivingBase entity, DeathType deathtype) {
		super();
		this.entityId = entity.getEntityId();
		if(deathtype!=null){
			this.deathtypeId = deathtype.getValue();
		} else {
			this.deathtypeId=DeathType.DEFAULT.getValue();
		}
		this.motionX = (float) entity.motionX;
		this.motionY = (float) entity.motionY;
		this.motionZ = (float) entity.motionZ;
	}



	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityId = buf.readInt();
		this.deathtypeId = buf.readInt();
		this.motionX = buf.readFloat();
		this.motionY = buf.readFloat();
		this.motionZ = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.entityId);
		buf.writeInt(this.deathtypeId);
		buf.writeFloat(this.motionX);
		buf.writeFloat(this.motionY);
		buf.writeFloat(this.motionZ);
	}

	
	public static class Handler implements  IMessageHandler<PacketEntityDeathType, IMessage> {

		@Override
		public IMessage onMessage(PacketEntityDeathType message, MessageContext ctx) {
			
			//If deathFX are disabled, ignore packet
			if (TGConfig.cl_enableDeathFX){
			
				EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
				EntityLivingBase entity = (EntityLivingBase) ply.worldObj.getEntityByID(message.entityId);
				DeathType deathtype = DeathType.values()[message.deathtypeId];
				
				if (deathtype!=DeathType.GORE || (deathtype==DeathType.GORE && TGConfig.cl_enableDeathFX_Gore)){
				
					if (entity !=null){
						entity.motionX = message.motionX;
						entity.motionY = message.motionY;
						entity.motionZ = message.motionZ;
						//System.out.printf("(message)EntityMotion: (%.1f/%.1f/%.1f)\n",message.motionX,message.motionY,message.motionZ);
						
						ClientProxy.get().setEntityDeathType(entity, deathtype);
						DeathEffect.createDeathEffect(entity, deathtype, message.motionX, message.motionY, message.motionZ);
						
						switch (deathtype) {
						case BIO:
							//entity.worldObj.playSoundEffect(entity.posX, entity.posY, entity.posZ, "techguns:effects.biodeath", 1.0F, 1.0f);
							Techguns.proxy.playSoundOnEntity(entity, "techguns:effects.biodeath", 1.0f, 1.0f, false, false, false,TGSoundCategory.DEATHEFFECT);
							break;
						case DEFAULT:
							break;
						case GORE:
							Techguns.proxy.playSoundOnEntity(entity, "techguns:effects.gore", 1.0f, 1.0f, false, false, false,TGSoundCategory.DEATHEFFECT);
							break;
						case LASER:
							Techguns.proxy.playSoundOnEntity(entity, "techguns:effects.disintegrate", 1.0f, 1.0f, false, false, false,TGSoundCategory.DEATHEFFECT);
							break;
						default:
							break;
						}
						
					}
				}
			}
			return null;
		}
		
	}
	
}
