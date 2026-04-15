package techguns.entities.projectiles;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import techguns.items.guns.Lasergun;

public class LaserBeam2 extends TeslaBeam {

	public LaserBeam2(World worldObj) {
		super(worldObj);
		this.lifetime = Lasergun.BEAM_LIFETIME;
		this.ticksToLive =Lasergun.BEAM_LIFETIME;
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeDouble(beamlength);
		//buffer.writeLong(seed);
	}


	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.beamlength = additionalData.readDouble();
		//this.seed = additionalData.readLong();
		//System.out.println("BeamLenght:"+this.beamlength);
	}

	/*public LaserBeam2(World worldObj, EntityLivingBase shooter, double x, double y, double z, Entity target, double beamlength) {
		super(worldObj, shooter, x, y, z, target, beamlength);
		this.lifetime = Lasergun.BEAM_LIFETIME;
		this.ticksToLive =Lasergun.BEAM_LIFETIME;
	}

	public LaserBeam2(World worldObj, EntityLivingBase shooter, double x, double y, double z, Entity target) {
		super(worldObj, shooter, x, y, z, target);
		this.lifetime = Lasergun.BEAM_LIFETIME;
		this.ticksToLive =Lasergun.BEAM_LIFETIME;
	}

	public LaserBeam2(World worldObj, EntityLivingBase shooter, double beamlength) {
		super(worldObj, shooter, beamlength);
		this.lifetime = Lasergun.BEAM_LIFETIME;
		this.ticksToLive =Lasergun.BEAM_LIFETIME;
	}*/

	public LaserBeam2(World worldObj, EntityLivingBase shooter, GenericProjectile p) {
		super(worldObj, shooter);
		this.lifetime = Lasergun.BEAM_LIFETIME;
		this.ticksToLive =Lasergun.BEAM_LIFETIME;
		
		this.setLocationAndAngles(p.posX, p.posY, p.posZ, -p.rotationYaw, -p.rotationPitch);
	}

	@Override
	protected String getParticleName() {
		return "LaserFlareEnd";
	}
	
	@Override
	protected String getParticleNameStart() {
		return "LaserFlareStart";
	}

	@Override
	protected void setInitialPosition(EntityLivingBase shooter, float yaw, float pitch){
		float offset=0.3f;
		
		Vec3 aimDir = shooter.getLookVec();
		
        this.setLocationAndAngles(shooter.posX+aimDir.xCoord*offset, shooter.posY + (double)shooter.getEyeHeight()+aimDir.yCoord*offset, shooter.posZ+aimDir.zCoord*offset, yaw, pitch);
	}
}
