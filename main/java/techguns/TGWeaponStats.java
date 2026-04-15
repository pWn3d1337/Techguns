package techguns;

import java.io.File;
import java.util.ArrayList;

import techguns.items.guns.GenericGun;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

public class TGWeaponStats {
	public static Configuration config;
	
	//public static boolean test;
	
	private static String CATEGORY_WEAPON_STATS = "Weapon Stats";
	
	public static void init(FMLPreInitializationEvent event){
		//Load the config file
		String path = event.getSuggestedConfigurationFile().getAbsolutePath();
		File f = new File(path.substring(0,path.length()-4)+"_WeaponStats.cfg");
		config = new Configuration(f);
		config.load();
	
		config.setCategoryComment(CATEGORY_WEAPON_STATS, "Techguns Weapon Stats configuration.\n\nDON'T REMOVE ENTRIES IT WILL CRASH MINECRAFT ON LOADING!\n\nVariable names are generated from the unlocalized name of the guns.\n"
				+ "\nNOT EVERY STAT HAS AN EFFECT ON EVERY GUN!\n"
				+ "Numbers like '0.05000000074505806' instead of '0.5' are because of data type rounding. The config file only supports double, while ingame its stored as float.\n\n"
				+ "Difficult gun names:\n"
				+ "m4_uq is 'The Infiltrator'\n"
				+ "mibGun is 'De-Atomizer Pistol'\n"
				+ "thompson is 'Submachine Gun'\n"
				+ "ak47 is 'Kalashnikov'\n\n"
				+ "You can regenerate this file by deleting it and launching minecraft.");
		//test = config.getBoolean("test", config.CATEGORY_GENERAL, true, "test configuration");		
		
		ArrayList<GenericGun> guns =GenericGun.getGuns();
		for (int i=0; i<guns.size(); i++)
		{		
			loadStats(guns.get(i));
		}
		//((GenericGun)TGuns.handcannon).applyGunStats(config.get(CATEGORY_WEAPON_STATS,));
		
		config.save();
	}
	
	public static void loadStats(Item it){
		GenericGun g = (GenericGun) it;
		g.initGunStats();
		g.applyGunStats(config.get(CATEGORY_WEAPON_STATS, "WeaponStats_"+g.getUnlocalizedName(), g.getGunStats(),g.getStatComment(),0f,Float.MAX_VALUE).getDoubleList());
	}
}
