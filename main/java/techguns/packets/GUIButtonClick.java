package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import techguns.TGPackets;
import techguns.tileentities.BasicInventoryTileEnt;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class GUIButtonClick implements IMessage {
	private int x;
	private int y;
	private int z;
	private int buttonId;

    public GUIButtonClick() 
    { 
     // need this constructor
    }

    public GUIButtonClick(BasicInventoryTileEnt tileEnt,int buttonId) 
    {
    	this.x = tileEnt.xCoord;
    	this.y = tileEnt.yCoord;
    	this.z = tileEnt.zCoord;
    	this.buttonId = buttonId;
    }
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.x=buf.readInt();
		this.y=buf.readInt();
		this.z=buf.readInt();
		this.buttonId=buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(buttonId);
	}

	public static class Handler implements  IMessageHandler<GUIButtonClick, IMessage> {

		@Override
		public IMessage onMessage(GUIButtonClick message, MessageContext ctx) {
			EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
			
			TileEntity tile = ply.worldObj.getTileEntity(message.x, message.y, message.z);
			
			if(tile != null){
				if (tile instanceof BasicInventoryTileEnt){
					BasicInventoryTileEnt tileent = (BasicInventoryTileEnt) tile;
					tileent.buttonClicked(message.buttonId, ply);
				}
			}
			
			
			return null;
		}
		
	}
}
