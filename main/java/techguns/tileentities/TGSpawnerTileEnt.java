package techguns.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;

public class TGSpawnerTileEnt extends TileEntityMobSpawner {

	public TGSpawnerTileEnt() {
		super();
	}

	/**
	 * 
	 * @param entityName
	 * @param delay - default 20
	 * @param minSpawnDelay - 200
	 * @param maxSpawnDelay - 800
	 * @param spawnCount - 4
	 * @param maxNearbyEntities - 6
	 * @param requiredPlayerRange - 16
	 * @param spawnRange - 4
	 */
	public void setValues(String entityName, int delay, int minSpawnDelay, int maxSpawnDelay, int spawnCount, int maxNearbyEntities, int requiredPlayerRange, int spawnRange ){
		NBTTagCompound tags = new NBTTagCompound();
		this.writeToNBT(tags);
		
        tags.setString("EntityId", entityName);
        tags.setShort("Delay", (short) delay);
        tags.setShort("MinSpawnDelay", (short) minSpawnDelay);
        tags.setShort("MaxSpawnDelay", (short) maxSpawnDelay);
        tags.setShort("SpawnCount", (short) spawnCount);
        tags.setShort("MaxNearbyEntities", (short) maxNearbyEntities);
        tags.setShort("RequiredPlayerRange", (short) requiredPlayerRange);
        tags.setShort("SpawnRange", (short) spawnRange);
		
        this.readFromNBT(tags);
	}
	
}
