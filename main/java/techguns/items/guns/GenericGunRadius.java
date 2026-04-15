package techguns.items.guns;

import net.minecraft.item.ItemStack;

public abstract class GenericGunRadius extends GenericGun {
	public float radius=1.0f;
	
	public GenericGunRadius() {
		super();
	}

	public GenericGunRadius(ItemStack ammo) {
		super(ammo);

	}

	public GenericGunRadius(String name, ItemStack ammo, boolean semiAuto, int minFiretime, int clipsize, int reloadtime, float damage, String firesound,
			String reloadsound, int TTL, float accuracy) {
		super(name, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL, accuracy);
	}

	@Override
	public void initGunStats(){
		this.gunStats = new double[8];
		
		this.gunStats[0]=this.damage;
		this.gunStats[1]=this.damageMin;
		this.gunStats[2]=this.damageDropStart;
		this.gunStats[3]=this.damageDropEnd;
		this.gunStats[4]=this.ticksToLive;
		this.gunStats[5]=this.accuracy;
		this.gunStats[6]=this.penetration;
		this.gunStats[7]=this.radius;
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
	}

	@Override
	public String getStatComment() {
		return super.getStatComment()+"\n[Radius - float, area of effect radius in block distance]";
	}
	
}
