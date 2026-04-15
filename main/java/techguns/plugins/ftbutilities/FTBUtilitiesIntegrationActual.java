package techguns.plugins.ftbutilities;

import java.util.UUID;

import ftb.utils.api.FriendsAPI;

public class FTBUtilitiesIntegrationActual implements IFTBUtilitiesIntegration {

	@Override
	public boolean areFriends(UUID player, UUID otherPlayer) {
		boolean friend = FriendsAPI.areFriends(player, otherPlayer);
		//System.out.println("Checking Friendship:"+friend);
		return friend;
	}

}
