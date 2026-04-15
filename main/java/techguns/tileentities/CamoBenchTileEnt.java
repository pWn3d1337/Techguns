package techguns.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.gui.playerinventory.TGPlayerInventory;
import techguns.items.armors.ICamoChangeable;

public class CamoBenchTileEnt extends BasicInventoryTileEnt {

	public CamoBenchTileEnt() {
		super(1);
	}
	
	/**
	 * Returns current ItemStack if it has changeable camo or null
	 * @return
	 */
	public ItemStack getItem(){
		if(this.content[0]!=null){
			if(this.content[0].getItem() instanceof ICamoChangeable){
				return this.content[0];
			}
		}
		return null;
	}
	
	
	
	@Override
	public void buttonClicked(int id, EntityPlayer ply) {
		if(this.isUseableByPlayer(ply)){
			
			
			if (id<3){
				ItemStack item = this.getItem();
				if(item!=null && item.getItem() instanceof ICamoChangeable){
					ICamoChangeable camoitem = (ICamoChangeable) item.getItem();
					camoitem.switchCamo(item, id==2);
					this.needUpdate();
				}
			} else if (id<11) {
				
				int slotid = 3-(((int) (Math.ceil((id-2)*0.5)))-1);
				
				ItemStack item = ply.inventory.armorInventory[slotid];//this.content[slotid];
				if(item!=null && item.getItem() instanceof ICamoChangeable){
					boolean back = id%2 == 0;
					
					ICamoChangeable camoitem = (ICamoChangeable) item.getItem();
					camoitem.switchCamo(item, back);
					//this.needUpdate();
					((EntityPlayerMP) ply).playerNetServerHandler.sendPacket(new S2FPacketSetSlot(ply.openContainer.windowId, 37+(3-slotid), item));
				}
				
			} else if (id<13) {
				boolean back = id %2==0;
				TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(ply);
				if (props!=null){
					ItemStack item = props.TG_inventory.inventory[TGPlayerInventory.SLOT_BACK];
					if(item!=null && item.getItem() instanceof ICamoChangeable) {
						ICamoChangeable camoitem = (ICamoChangeable) item.getItem();
						camoitem.switchCamo(item, back);
						//TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(ply, props, false), (EntityPlayerMP) ply);
					}				
				}
			} else if (id<15) {
				boolean back = id %2==0;
				TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(ply);
				if (props!=null){
					ItemStack item = props.TG_inventory.inventory[TGPlayerInventory.SLOT_FACE];
					if(item!=null && item.getItem() instanceof ICamoChangeable) {
						ICamoChangeable camoitem = (ICamoChangeable) item.getItem();
						camoitem.switchCamo(item, back);
						//TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(ply, props, false), (EntityPlayerMP) ply);
					}				
				}
			}
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[]{};
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_,
			int p_102007_3_) {
		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_,
			int p_102008_3_) {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return item.getItem() instanceof ICamoChangeable;
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.name : "techguns.container.camobench";
	}
}
