package techguns.extendedproperties;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * UNUSED !!!!!!!!!!!!!!!!!!!
 * Saves firetime, totalfiretime, recoiltime, totalrecoiltime to entities
 * used for players and NPCS
 *
 *	From tutorial:
 *	https://github.com/coolAlias/Forge_Tutorials/blob/master/IExtendedEntityPropertiesTutorial.java
 *
 */
@Deprecated
public class TechgunsShooterValuesProperties implements IExtendedEntityProperties {
	public final static String EXT_PROP_NAME = "ExtendedEntityShooter";
	
	private final EntityLivingBase shooter;
	
	/**
	 * When the reload started (timestamp)
	 */
	private long reloadtime=0L;
	/**
	 * how long the reload lasts in total
	 */
	private int totalreloadtime=0;
	/**
	 * When the recoil started (timestamp)
	 */
	private long recoiltime=0L;
	/**
	 * how long the recoil lasts in total
	 */
	private int totalrecoiltime=0;
	
	
	public TechgunsShooterValuesProperties(EntityLivingBase shooter) {
		this.shooter = shooter;
	}

	/**
	 * Used to register these extended properties for the player during EntityConstructing event
	 * This method is for convenience only; it will make your code look nicer
	 */
	public static final void register(EntityLivingBase elb)
	{
		elb.registerExtendedProperties(TechgunsShooterValuesProperties.EXT_PROP_NAME, new TechgunsShooterValuesProperties(elb));
	}
	
	/**
	 * Returns ExtendedPlayer properties for player
	 * This method is for convenience only; it will make your code look nicer
	 */
	public static final TechgunsShooterValuesProperties get(EntityLivingBase elb)
	{
		return (TechgunsShooterValuesProperties) elb.getExtendedProperties(EXT_PROP_NAME);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		if (reloadtime>0){
			compound.setLong("reloadtime", reloadtime);
		}
		if (recoiltime>0){
			compound.setLong("recoiltime", recoiltime);
		}
		if(totalreloadtime>0){
			compound.setInteger("totalreloadtime", totalreloadtime);
		}
		if(totalrecoiltime>0){
			compound.setInteger("totalrecoiltime", totalrecoiltime);
		}

	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		totalreloadtime=compound.getInteger("totalreloadtime");
		totalrecoiltime=compound.getInteger("totalrecoiltime");
		recoiltime=compound.getLong("recoiltime");
		reloadtime=compound.getLong("reloadtime");
	}

	@Override
	public void init(Entity entity, World world) {
	}

	public long getReloadtime() {
		return reloadtime;
	}

	public void setReloadtime(long reloadtime) {
		this.reloadtime = reloadtime;
	}

	public int getTotalreloadtime() {
		return totalreloadtime;
	}

	public void setTotalreloadtime(int totalreloadtime) {
		this.totalreloadtime = totalreloadtime;
	}

	public long getRecoiltime() {
		return recoiltime;
	}

	public void setRecoiltime(long recoiltime) {
		this.recoiltime = recoiltime;
	}

	public int getTotalrecoiltime() {
		return totalrecoiltime;
	}

	public void setTotalrecoiltime(int totalrecoiltime) {
		this.totalrecoiltime = totalrecoiltime;
	}

	public static String getExtPropName() {
		return EXT_PROP_NAME;
	}

	public EntityLivingBase getShooter() {
		return shooter;
	}

	
	
}
