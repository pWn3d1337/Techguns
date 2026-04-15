package techguns.plugins.tconstruct;

import java.util.List;

import tconstruct.client.tabs.TabRegistry;

public class TechgunsTConstructIntegrationClient extends TechgunsTConstructIntegration {
	
	@Override
	public void init() {
		super.init();
		TabRegistry.registerTab(new TGInventoryTab());
	}

	public void addTabs(int guiLeft, int guiTop, List buttonList) {
		TabRegistry.updateTabValues(guiLeft, guiTop, TGInventoryTab.class);
        TabRegistry.addTabsToList(buttonList);
	}

}
