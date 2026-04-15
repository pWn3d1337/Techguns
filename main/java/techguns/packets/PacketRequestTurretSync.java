package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import techguns.TGPackets;
import techguns.entities.npc.NPCTurret;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestTurretSync implements IMessage{
	
	public int turret_entityId;

	public PacketRequestTurretSync() {
		super();
	}

	public PacketRequestTurretSync(NPCTurret turret) {
		super();
		this.turret_entityId = turret.getEntityId();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.turret_entityId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.turret_entityId);
	}
	
	public static class Handler implements  IMessageHandler<PacketRequestTurretSync, IMessage> {

		@Override
		public IMessage onMessage(PacketRequestTurretSync message, MessageContext ctx) {
			EntityPlayerMP ply = (EntityPlayerMP) TGPackets.getPlayerFromContext(ctx);
			
			Entity ent = ply.worldObj.getEntityByID(message.turret_entityId);
			if (ent != null && ent instanceof NPCTurret){
				NPCTurret turret = (NPCTurret) ent;
				
				TileEntity base = turret.mountedTileEnt;
				if (base!=null){
					ply.playerNetServerHandler.sendPacket(base.getDescriptionPacket());
					//System.out.println("Sent Tile entity update for turret!");
				} else {
					//System.out.println("Turret base is NULL!");
				}
				
			} else {
				//System.out.println("Got Turret relink request but entityid is null serverside");
			}
			
			return null;
		}
		
	}

}
