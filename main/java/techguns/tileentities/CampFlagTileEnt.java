package techguns.tileentities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import techguns.entities.npc.AttackHelicopter;
import techguns.util.BlockUtils;
import techguns.util.NPCUtil;

public class CampFlagTileEnt extends BasicInventoryTileEnt{
	//-------------------------
	//General Camp settings:
	//-------------------------
	static final int SPAWN_CHECK_INTERVAL = 60; //Check once per two seconds
	static final int SPAWN_INTERVAL = 200; //ten ,-five seconds;
	static final int SPAWN_AREA_RANGE = 4; //additional blocks where NPCs count to this Camp
	static final int SIZE_Y = 20; //Max camp height (for npc check)
	//-------------------------
	//Camp instance attributes:
	//-------------------------
	
	//The flag's offset from the camp origin position.
	public short xoffset;
	public short yoffset;
	public short zoffset;
	
	//camp size;
	public short sizeX;
	public short sizeZ;
	
	//NPC stuff
	public short maxNPCs;
	public short numSpawnPoints;
	public short spawnCheckTimer = SPAWN_CHECK_INTERVAL;
	public short spawnTimer = SPAWN_INTERVAL;
	boolean canSpawn = false;
	public String[] npcSpawnList;
	
	//x1, y1, z1, x2, y2, z2,...
	//offsets from camp origin
	public int[] spawnPositions;
	
	
	public CampFlagTileEnt() {
		super(0);
	}
	
	public void init(int sizeX, int sizeZ, int xoffset, int yoffset, int zoffset, int[] spawnPositions, String[] npcSpawnList) {
		this.sizeX = (short) sizeX;
		this.sizeZ = (short) sizeZ;
		this.xoffset = (short) xoffset;
		this.yoffset = (short) yoffset;
		this.zoffset = (short) zoffset;
		this.spawnPositions = spawnPositions;
		this.numSpawnPoints = (short) (spawnPositions.length/3);
		this.npcSpawnList = npcSpawnList;
		this.maxNPCs =  (short) (((float)(sizeX+sizeZ) / 2.0f) * 0.75f);
	}

	public void init(int sizeX, int sizeZ, int xoffset, int yoffset, int zoffset, int[] spawnPositions, String[] npcSpawnList, int maxNPCs) {
		this.sizeX = (short) sizeX;
		this.sizeZ = (short) sizeZ;
		this.xoffset = (short) xoffset;
		this.yoffset = (short) yoffset;
		this.zoffset = (short) zoffset;
		this.spawnPositions = spawnPositions;
		this.numSpawnPoints = (short) (spawnPositions.length/3);
		this.npcSpawnList = npcSpawnList;
		this.maxNPCs =  (short) maxNPCs;
	}
	
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (!this.worldObj.isRemote) {
		
			if (spawnCheckTimer-- <= 0) {
				spawnCheckTimer = SPAWN_CHECK_INTERVAL;
				int count = countNPCs();
				if (count < maxNPCs) {
					canSpawn = true;
				}else {
					canSpawn = false;
					spawnTimer = SPAWN_INTERVAL;
				}
			}
			
			if (canSpawn && this.numSpawnPoints > 0 && spawnTimer-- <= 0) {
				int spawnNum = this.worldObj.rand.nextInt(this.numSpawnPoints);
				int spawnX = this.spawnPositions[(spawnNum*3)+0];
				int spawnY = this.spawnPositions[(spawnNum*3)+1];
				int spawnZ = this.spawnPositions[(spawnNum*3)+2];
				
				String npc = this.npcSpawnList[this.worldObj.rand.nextInt(this.npcSpawnList.length)];
				
				NPCUtil.spawnEntity(this.worldObj, spawnX+(this.xCoord-xoffset)+0.5d, spawnY+(this.yCoord-yoffset), spawnZ+(this.zCoord-zoffset)+0.5d, npc);
				spawnTimer = SPAWN_INTERVAL;
			}
		}
		
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+4, zCoord+1);
	}
	
	
	private int countNPCs() {
		double posX = this.xCoord - xoffset;
		double posY = this.yCoord - yoffset;
		double posZ = this.zCoord - zoffset;
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(posX, posY-3, posZ, posX+sizeX, posY+SIZE_Y, posZ+sizeZ).expand(SPAWN_AREA_RANGE*2, 0, SPAWN_AREA_RANGE*2);
		
		IEntitySelector filter = new NPCUtil.EntityByNameSelector(new ArrayList<String>(Arrays.asList(npcSpawnList)));
		List<Entity> entityList = BlockUtils.selectEntitiesWithinAABB(this.worldObj, bb, filter );
				
		return entityList.size();
	}

	@Override
	public void readEntityDataFromNBT(NBTTagCompound tags) {
		super.readEntityDataFromNBT(tags);
		this.sizeX = tags.getShort("sizeX");
		this.sizeZ = tags.getShort("sizeZ");
		this.xoffset = tags.getShort("xoffset");
		this.yoffset = tags.getShort("yoffset");
		this.zoffset = tags.getShort("zoffset");
		this.spawnPositions = tags.getIntArray("spawnPositions");
		
		if(spawnPositions!=null){
			this.numSpawnPoints = (short) (spawnPositions.length/3);
		} else {
			this.numSpawnPoints=0;
		}
		this.maxNPCs =  (short) (((float)(sizeX+sizeZ) / 2.0f) * 0.75f);
		
		short npcListSize = tags.getShort("npcListSize");
		this.npcSpawnList = new String[npcListSize];
		for (int i = 0; i < npcSpawnList.length; i++) {
			npcSpawnList[i] = tags.getString("npcName"+i);
		}
	}

	@Override
	public void writeEntityDataToNBT(NBTTagCompound tags) {
		super.writeEntityDataToNBT(tags);
		tags.setShort("sizeX",sizeX);
		tags.setShort("sizeZ",sizeZ);
		tags.setShort("xoffset",xoffset);
		tags.setShort("yoffset",yoffset);
		tags.setShort("zoffset",zoffset);
		tags.setIntArray("spawnPositions", spawnPositions);
		
		if(npcSpawnList !=null){
			tags.setShort("npcListSize", (short) npcSpawnList.length);
			for (int i = 0; i < npcSpawnList.length; i++) {
				tags.setString("npcName"+i, npcSpawnList[i]);
			}
		}
	}
	
	
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_,
			int p_102007_3_) {
		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_,
			int p_102008_3_) {
		return false;
	}

	@Override
	public String getInventoryName() {
		return "container.campflag";
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return false;
	}

	
	
	@Override
	public void onBlockBreak() {
		super.onBlockBreak();
		this.onDestroyed();
	}

	/**
	 * When the Flag gets destroyed.
	 */
	protected void onDestroyed(){
		if(!this.worldObj.isRemote){
			AttackHelicopter heli = new AttackHelicopter(this.worldObj);
			heli.setPosition(this.xCoord, this.yCoord+50, this.zCoord);
			this.worldObj.spawnEntityInWorld(heli);
		}
	}
	
}
