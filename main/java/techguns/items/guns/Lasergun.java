package techguns.items.guns;


import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.damagesystem.DamageType;
import techguns.entities.projectiles.LaserProjectile;

public class Lasergun extends GenericGun{

	
	public static final int BEAM_LIFETIME = 5;
	public static final int LIFETIME=5;
	public static final float SPEED =5.0f;
	
	public Lasergun() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Lasergun(ItemStack ammo) {
		super(ammo);
		// TODO Auto-generated constructor stub
	}

	public Lasergun(String name, ItemStack ammo, boolean semiAuto, int minFiretime,
			int clipsize, int reloadtime, float damage, String firesound,
			String reloadsound, int TTL, float accuracy) {
		super(name, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage,
				firesound, reloadsound, TTL, accuracy);
	}

	/* (non-Javadoc)
	 * @see techguns.items.guns.GenericGun#spawnProjectile(net.minecraft.world.World, net.minecraft.entity.player.EntityPlayer, net.minecraft.item.ItemStack, float)
	 */
	@Override
	protected void spawnProjectile(World world, EntityLivingBase player, ItemStack itemstack, float spread,float offset, float damagebonus, boolean leftGun) {
		//world.spawnEntityInWorld(new BlasterProjectile(world, player, damage, speed, this.ticksToLive, spread, this.damageDropStart, this.damageDropEnd, this.damageMin));
		//world.spawnEntityInWorld(new LaserBeam2(world, player));
		world.spawnEntityInWorld(new LaserProjectile(world, player, damage*damagebonus, speed, this.ticksToLive, spread, this.damageDropStart, this.damageDropEnd, this.damageMin*damagebonus, this.penetration, getDoBlockDamage(player)));
	}
	

	@Override
	public DamageType getDamageType() {
		return DamageType.ENERGY;
	}

	
	/*@Override
	public void initGunStats(){
		this.gunStats = new double[3];
		
		this.gunStats[0]=this.damage;
		//this.gunStats[1]=this.damageMin;
		//this.gunStats[2]=this.damageDropStart;
		//this.gunStats[3]=this.damageDropEnd;
		//this.gunStats[4]=this.ticksToLive;
		this.gunStats[1]=this.accuracy;
		this.gunStats[2]=this.penetration;
	}
	
	@Override
	public void applyGunStats(double[] stats){
		this.gunStats=stats;	
		
		this.damage=(float) this.gunStats[0];
		this.damageMin=(float) this.gunStats[0];
		//this.damageDropStart=(int) this.gunStats[2];
		//this.damageDropEnd=(int) this.gunStats[3];
		//this.ticksToLive=(int) this.gunStats[4];
		this.accuracy=(float) this.gunStats[1];
		this.penetration=(float) this.gunStats[2];
	}

	@Override
	public String getStatComment() {
		return "[DamageMax - float: damage close range]\n"
				+ "[Accuracy - float: max random angular offset when shooting]\n"
				+ "[Penetration - float: amount of armor penetration between 0.0 and 1.0. 1.0=100%.]";	}
				*/
	/*@Override
	public String getStatComment() {
		return "[NOT ALL VALUES HAVE AN EFFECT ON THE LASER GUN!!!]\n"+super.getStatComment();
	}*/

}
