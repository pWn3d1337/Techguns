package techguns.plugins.tconstruct;

import tconstruct.client.tabs.AbstractTab;
import techguns.TGItems;
import techguns.TGPackets;
import techguns.packets.PacketOpenPlayerGUI;
import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "tconstruct.client.tabs.AbstractTab", modid = "TConstruct")
public class TGInventoryTab extends AbstractTab {
	
	public TGInventoryTab() {
		super(0,0,0, TGItems.newStack(TGItems.bullets9mm,1));
	}

	@Override
	public void onTabClicked() {
		TGPackets.network.sendToServer(new PacketOpenPlayerGUI((short) 17));
	}

	@Override
	public boolean shouldAddToList() {
		return true;
	}

}
