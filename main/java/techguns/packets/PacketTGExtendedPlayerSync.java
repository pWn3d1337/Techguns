package techguns.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.util.PacketUtil;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketTGExtendedPlayerSync implements IMessage {
	public int entityId;
	public ItemStack[] inv;
	public int radlevel;
	public int foodLeft=0;
	public boolean nightVisionEnabled=false;
	public boolean safemodeEnabled=true;
	public float lastSaturation=0f;
	public boolean hovermodeEnabled=false;
	public byte size;
	public boolean stepAssist;
	public boolean showHUD;
	public boolean enableJetpack;
	private boolean allSlots=false;
	
	public PacketTGExtendedPlayerSync() {
	}
	
	public PacketTGExtendedPlayerSync(EntityPlayer ply, TechgunsExtendedPlayerProperties props,boolean allSlots) {
		super();
		this.entityId=ply.getEntityId();
		this.radlevel=props.radlevel;
		this.nightVisionEnabled=props.enableNightVision;
		this.safemodeEnabled=props.enableSafemode;
		this.hovermodeEnabled=props.enableHovermode;
		this.inv = props.TG_inventory.inventory;
		this.size=allSlots?(byte)inv.length:(byte)2;
		if (allSlots){
			this.foodLeft=props.foodleft;
			this.lastSaturation=props.lastSaturation;
			this.stepAssist=props.enableStepAssist;
			this.showHUD=props.showTGHudElements;
			this.enableJetpack=props.enableJetpack;
		}
		this.allSlots=allSlots;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityId = buf.readInt();
		radlevel = buf.readInt();
		size = buf.readByte();
		inv = new ItemStack[size];
		for(int i=0;i<size;i++){
			try {
				inv[i]=PacketUtil.readItemStackFromBuffer(buf);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(size>2){
			foodLeft=buf.readShort();
			lastSaturation=buf.readFloat();
			nightVisionEnabled=buf.readBoolean();
			safemodeEnabled=buf.readBoolean();
			hovermodeEnabled=buf.readBoolean();
			stepAssist=buf.readBoolean();
			showHUD=buf.readBoolean();
			enableJetpack=buf.readBoolean();
			this.allSlots=true;
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityId);
		buf.writeInt(radlevel);
		buf.writeByte(this.size);
		for(int i=0;i<size;i++){
			try {
				PacketUtil.writeItemStackToBuffer(buf, inv[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (size>2){
			buf.writeShort(foodLeft);
			buf.writeFloat(lastSaturation);
			buf.writeBoolean(this.nightVisionEnabled);
			buf.writeBoolean(this.safemodeEnabled);
			buf.writeBoolean(this.hovermodeEnabled);
			buf.writeBoolean(this.stepAssist);
			buf.writeBoolean(this.showHUD);
			buf.writeBoolean(this.enableJetpack);
		}
	}

	public static class Handler implements  IMessageHandler<PacketTGExtendedPlayerSync, IMessage> {

		@Override
		public IMessage onMessage(PacketTGExtendedPlayerSync message, MessageContext ctx) {
			EntityPlayer local_ply = TGPackets.getPlayerFromContext(ctx);
			
			Entity ent = local_ply.worldObj.getEntityByID(message.entityId);
			
			if (ent !=null && ent instanceof EntityPlayer){
				EntityPlayer ply = (EntityPlayer) ent;

				TechgunsExtendedPlayerProperties props= TechgunsExtendedPlayerProperties.get(ply);
				if (props==null){
					props = Techguns.proxy.registerExtendedProperties(ply);
				}
				
				props.radlevel=message.radlevel;
				props.lastSaturation=message.lastSaturation;
				props.foodleft=message.foodLeft;
				props.enableNightVision=message.nightVisionEnabled;
				props.enableSafemode=message.safemodeEnabled;
				props.enableHovermode=message.hovermodeEnabled;
				props.enableStepAssist=message.stepAssist;
				props.showTGHudElements = message.showHUD;
				props.enableJetpack = message.enableJetpack;
				for(int i=0;i<message.size;i++){
					props.TG_inventory.inventory[i]=ItemStack.copyItemStack(message.inv[i]);
				}
			}
			
			return null;
		}
		
	}
}
