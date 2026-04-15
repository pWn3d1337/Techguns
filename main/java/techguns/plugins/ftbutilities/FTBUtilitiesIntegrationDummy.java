package techguns.plugins.ftbutilities;

import java.util.UUID;

public class FTBUtilitiesIntegrationDummy implements IFTBUtilitiesIntegration {

	@Override
	public boolean areFriends(UUID player, UUID otherPlayer) {
		return true;
	}

}
