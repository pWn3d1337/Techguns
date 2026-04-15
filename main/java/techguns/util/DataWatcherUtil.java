package techguns.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.ReflectionHelper;

/**
 * Using reflection on Data watcher... 
 * bad idea i guess
 *
 */
public class DataWatcherUtil {
	
	protected static Method DW_METHOD_getWatchedObject;
	
	protected static Field DW_FIELD_field_151511_a;
	protected static Field DW_FIELD_objectChanged;
	static{
		DW_FIELD_field_151511_a = ReflectionHelper.findField(DataWatcher.class, "field_151511_a");
		DW_FIELD_objectChanged = ReflectionHelper.findField(DataWatcher.class, "objectChanged");
	}
	
	
	
	/**
	 * Force an update on a DataWatcher object, needed for itemStack
	 * @param instance
	 * @param id
	 * @param o
	 */
	public static void forceUpdateObject(DataWatcher instance, int id){
		try{
        //instance.field_151511_a.func_145781_i(id);
	        Entity e = (Entity) DW_FIELD_field_151511_a.get(instance);
	        e.func_145781_i(id);
	        
	        //watchableobject.setWatched(true);
	        
	        //instance.objectChanged = true;
	        DW_FIELD_objectChanged.setBoolean(instance, true);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	
}
