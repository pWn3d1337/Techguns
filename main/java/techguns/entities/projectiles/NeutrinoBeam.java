package techguns.entities.projectiles;

import techguns.debug.Keybinds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class NeutrinoBeam extends MovingBeam {

	public NeutrinoBeam(World worldObj) {
		super(worldObj);
	}

	public NeutrinoBeam(World par2World, double posX, double posY, double posZ, float yaw, float pitch, float damage, float speed, int TTL, float spread,
			int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage) {
		super(par2World, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage);
	}

	public NeutrinoBeam(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd, float dmgMin,
			float penetration, boolean blockdamage) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage);
	}

	@Override
	public Vec3 getFPOffset() {
		//return super.getFPOffset();
		return Vec3.createVectorHelper(0, -0.08, 0.24);
	}

	@Override
	public float get3PYOffset() {
		return -0.2f;
	}

	@Override
	public float getBeam3PYOffset() {
		return -0.3f;
	}

	
	
}
