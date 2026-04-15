package techguns.items.guns;

import net.minecraft.item.ItemStack;

public abstract class GenericGunRadiusGrav extends GenericGunRadius {

	public float gravity=0.01f;
	
	public GenericGunRadiusGrav() {
		super();
	}

	public GenericGunRadiusGrav(ItemStack ammo) {
		super(ammo);
	}

	public GenericGunRadiusGrav(String name, ItemStack ammo, boolean semiAuto, int minFiretime, int clipsize, int reloadtime, float damage, String firesound,
			String reloadsound, int TTL, float accuracy) {
		super(name, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL, accuracy);
	}

	@Override
	public void initGunStats(){
		this.gunStats = new double[9];
		
		this.gunStats[0]=this.damage;
		this.gunStats[1]=this.damageMin;
		this.gunStats[2]=this.damageDropStart;
		this.gunStats[3]=this.damageDropEnd;
		this.gunStats[4]=this.ticksToLive;
		this.gunStats[5]=this.accuracy;
		this.gunStats[6]=this.penetration;
		this.gunStats[7]=this.radius;
		this.gunStats[8]=this.gravity;
	}
	
	@Override
	public void applyGunStats(double[] stats){
		this.gunStats=stats;	
		
		this.damage=(float) this.gunStats[0];
		this.damageMin=(float) this.gunStats[1];
		this.damageDropStart=(int) this.gunStats[2];
		this.damageDropEnd=(int) this.gunStats[3];
		this.ticksToLive=(int) this.gunStats[4];
		this.accuracy=(float) this.gunStats[5];
		this.penetration=(float) this.gunStats[6];
		this.radius=(float) this.gunStats[7];
		this.gravity=(float) this.gunStats[8];
	}

	@Override
	public String getStatComment() {
		return super.getStatComment()+"\n[Gravity - float, downward velocity of the projectile]";
	}
}
