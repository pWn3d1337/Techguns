package techguns.entities.npc;

import java.util.UUID;

import techguns.plugins.ftbutilities.IFTBUtilitiesIntegration;

public class TGNpcFactions {
	
	public static IFTBUtilitiesIntegration friendsAPI;
	
	protected static boolean[][] hostilityTable = new boolean[TGNpcFaction.values().length][TGNpcFaction.values().length];
		
	static {
		
		//init hostility table
		setHostile(TGNpcFaction.HOSTILE, false);
		setHostile(TGNpcFaction.TURRET, false);
		setHostile(TGNpcFaction.NEUTRAL, false);
		
		
		setHostile(TGNpcFaction.HOSTILE, TGNpcFaction.TURRET,true);
		setHostile(TGNpcFaction.HOSTILE, TGNpcFaction.NEUTRAL,true);
		setHostile(TGNpcFaction.TURRET, TGNpcFaction.NEUTRAL,false);
		
		
		
		
	}
	
	private static void setHostile(TGNpcFaction f1, TGNpcFaction f2, boolean hostile){
		hostilityTable[f1.ordinal()][f2.ordinal()]=hostile;
		hostilityTable[f2.ordinal()][f1.ordinal()]=hostile;
	}
	
	private static void setHostile(TGNpcFaction f1, boolean hostile){
		hostilityTable[f1.ordinal()][f1.ordinal()]=hostile;
	}
	
	//public static boolean isHostile(EntityPlayer ply1, EntityPlayer ply2){
	//	return friendsAPI.areFriends(ply1.getGameProfile().getId(), ply2.getGameProfile().getId());
	//}
	
	public static boolean isHostile(UUID ply1, UUID ply2){
		return !friendsAPI.areFriends(ply1, ply2);
	}
	
	public static boolean isHostile(TGNpcFaction faction1, TGNpcFaction faction2){
		/*if (faction1==faction2){
			return false;
		}	
		
		if ((faction1==TGNpcFaction.TURRET && faction2 == TGNpcFaction.HOSTILE) || (faction2==TGNpcFaction.TURRET && faction1 == TGNpcFaction.HOSTILE)){
			return true;
		}
		
		
		return false;*/
		return hostilityTable[faction1.ordinal()][faction2.ordinal()];
	}
}
