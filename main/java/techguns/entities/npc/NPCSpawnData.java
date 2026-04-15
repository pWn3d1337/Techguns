//package techguns.entities.npc;
//
//import java.util.EnumSet;
//import java.util.HashMap;
//import java.util.Map;
//
//import net.minecraft.world.World;
//
//public class NPCSpawnData {
//	static void spawnNPC(World world, NPCType npcType, float posX, float posY, float posZ)
//	{
//		switch (npcType) {
//		case ARMYSOLDIER:
//			break;
//		case CYBERDEMON:
//			break;
//		case ZOMBIESOLDIER:
//			break;
//		default:
//			break;
//		
//		}
//		
//		
//	}
//	
//	
//	public enum NPCType {
//		ZOMBIESOLDIER(0), ARMYSOLDIER(1), CYBERDEMON(2);
//		
//		
//		private static final Map<Integer,NPCType> lookup 
//        = new HashMap<Integer,NPCType>();
//
//	   static {
//	        for(NPCType n: EnumSet.allOf(NPCType.class))
//	             lookup.put(n.getIndex(), n);
//	   }
//	
//	   private int index;
//	
//	   private NPCType(int index) {
//	        this.index = index;
//	   }
//	
//	   public int getIndex() { return index; }
//	
//	   public static NPCType get(int index) { 
//	        return lookup.get(index); 
//	   }
//	}
//}
