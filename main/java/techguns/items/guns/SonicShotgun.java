package techguns.items.guns;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.entities.projectiles.SonicShotgunProjectile;
import techguns.packets.PacketSpawnParticleOnEntity;
import techguns.util.MathUtil;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class SonicShotgun extends GenericGun {

	public SonicShotgun(String name, ItemStack ammo, boolean semiAuto,
			int minFiretime, int clipsize, int reloadtime, float damage,
			String firesound, String reloadsound, int TTL, float accuracy) {
		super(name, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage,
				firesound, reloadsound, TTL, accuracy);
	}

	@Override
	protected void spawnProjectile(World world, EntityLivingBase player,
			ItemStack itemstack, float spread, float offset, float damagebonus, boolean leftGun) {
		
		int count = 5; //Base projectile count per ring
		int rings = 2; //number of rings
		
		spread = 0.075f;
		
		//Center
		ArrayList<Entity> entitiesHit = new ArrayList<Entity>();
		SonicShotgunProjectile proj = new SonicShotgunProjectile(world, player, damage*damagebonus, speed, this.ticksToLive, 0f, this.damageDropStart, this.damageDropEnd, this.damageMin*damagebonus, this.penetration, getDoBlockDamage(player));    	
		proj.entitiesHit = entitiesHit;
		proj.mainProjectile = true;
		if (offset>0.0f){
    		proj.shiftForward(offset);
    	}
    	world.spawnEntityInWorld(proj);
    	TGPackets.network.sendToAllAround(new PacketSpawnParticleOnEntity("SonicShotgunTrail", proj), new TargetPoint(world.provider.dimensionId, proj.posX, proj.posY, proj.posZ, 200.0f));
    	
		
		//Vec3 motion = Vec3.createVectorHelper(proj.motionX, proj.motionY, proj.motionZ).normalize(); //Shoot direction
		//Vec3 pos = Vec3.createVectorHelper(proj.posX, proj.posY, proj.posZ);
    	
    	//Rings
		for (int j = 1; j <= rings; j++) {
			
			float angle =(float) ((Math.PI*2.0)/(double)(count*j));
			for (int i = 0; i < (count*j); i++) {
			
				proj = new SonicShotgunProjectile(world, player, damage*damagebonus, speed, this.ticksToLive, 0f, this.damageDropStart, this.damageDropEnd, this.damageMin*damagebonus, this.penetration, getDoBlockDamage(player));    	
				proj.entitiesHit = entitiesHit;
				Vec3 dir = Vec3.createVectorHelper(1, 0, 0);
				dir.rotateAroundY(spread*(float)j);
				dir.rotateAroundX(angle*(float)i);
				
				dir.rotateAroundZ((float) (player.rotationPitch*MathUtil.D2R));
				dir.rotateAroundY((float) ((-player.rotationYawHead-90.0)*MathUtil.D2R));
				
				//System.out.println("Yaw:"+player.rotationYawHead);
				//System.out.println("Pitch:"+player.rotationPitch);
				
				proj.motionX = dir.xCoord*speed;
				proj.motionY = dir.yCoord*speed;
				proj.motionZ = dir.zCoord*speed;
				
		    	if (offset>0.0f){
		    		proj.shiftForward(offset);
		    	}
		
		    	world.spawnEntityInWorld(proj);
			}
		}
	}


	
}
