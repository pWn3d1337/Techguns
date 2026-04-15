package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import techguns.TGPackets;
import techguns.items.guns.GenericGun;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * Tells the server that the player who sent this message wants to shoot with his current gun
 *
 */
public class PacketShootGun implements IMessage {
	public boolean isZooming=false;
	
	public PacketShootGun() {
	}

	public PacketShootGun(boolean isZooming) {
		this.isZooming = isZooming;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		isZooming = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(isZooming);
	}

	public static class Handler implements  IMessageHandler<PacketShootGun, IMessage> {

		@Override
		public IMessage onMessage(PacketShootGun message, MessageContext ctx) {
			EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
			ItemStack stack = ply.getCurrentEquippedItem();
			if(stack!=null && stack.getItem() instanceof GenericGun){
				((GenericGun) stack.getItem()).shootGunLeftClick(stack, ply.worldObj, ply, message.isZooming);
			}
			return null;
		}
		
	}
}
