package techguns.util;

import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import techguns.Techguns;

public class NPCUtil {
	/**
	 * Spawns the creature specified by its name
	 * Parameters: world, x, y, z, entityName.
	 */
	public static EntityLiving spawnEntity(World parWorld, double parX, double parY,
			double parZ, String entityToSpawnName) {

		EntityLiving entityToSpawn = null;
		if (!parWorld.isRemote) // never spawn entity on client side
		{
			String entityToSpawnNameFull = Techguns.MODID + "." + entityToSpawnName;
			if (EntityList.stringToClassMapping
					.containsKey(entityToSpawnNameFull)) {
				entityToSpawn = (EntityLiving) EntityList.createEntityByName(entityToSpawnNameFull, parWorld);
				entityToSpawn.setLocationAndAngles(parX, parY, parZ, MathHelper.wrapAngleTo180_float(parWorld.rand.nextFloat() * 360.0F), 0.0F);
				parWorld.spawnEntityInWorld(entityToSpawn);
				entityToSpawn.onSpawnWithEgg((IEntityLivingData) null);
				entityToSpawn.playLivingSound();
			} else {
				// DEBUG
				System.out.println("Entity not found " + entityToSpawnName);
			}
		}

		return entityToSpawn;
	}
	
	public static class EntityByNameSelector implements IEntitySelector
    {
		List<String> names;
        public EntityByNameSelector(List<String> names)
        {
            this.names = names;
        }

        /**
         * Return whether the specified entity is applicable to this filter.
         */
        public boolean isEntityApplicable(Entity entity)
        {
            if (!entity.isEntityAlive())
            {
                return false;
            }
            else if (!(entity instanceof EntityLivingBase))
            {
                return false;
            }
           
            String name = (String) EntityList.classToStringMapping.get(entity.getClass());
            return (this.names.contains(name));
            
        }
    }
}
