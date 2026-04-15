package techguns.entities.projectiles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class FlashBangProjectile extends GrenadeProjectile {

	public FlashBangProjectile(World par1World) {
		super(par1World);
	}

	public FlashBangProjectile(World par2World, EntityLivingBase par3Player,
			float damage, float radius, float speed, int TTL, float spread,
			float gravity, boolean blockdamage, float penetration,
			int maxBounces, double x, double y, double z) {
		super(par2World, par3Player, damage, radius, speed, TTL, spread, gravity,
				blockdamage, penetration, maxBounces, x, y, z);
	}

	public FlashBangProjectile(World par2World, EntityLivingBase par3Player,
			float damage, float radius, float speed, int TTL, float spread,
			float gravity, boolean blockdamage, float penetration,
			int maxBounces) {
		super(par2World, par3Player, damage, radius, speed, TTL, spread, gravity,
				blockdamage, penetration, maxBounces);
	}

	@Override
	protected void explode(Entity enthit) {

		List list = new ArrayList<PotionEffect>();//Items.potionitem.getEffects(this.potionDamage);
		PotionEffect flash= new PotionEffect(Potion.blindness.id,200,1,false);
		PotionEffect confusion = new PotionEffect(Potion.confusion.id,200,1,false);
		flash.setSplashPotion(true);
		confusion.setSplashPotion(true);
		list.add(flash);
		list.add(confusion);
		
		
        if (list != null && !list.isEmpty())
        {
            AxisAlignedBB axisalignedbb = this.boundingBox.expand(4.0D, 2.0D, 4.0D);
            List list1 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);

            if (list1 != null && !list1.isEmpty())
            {
                Iterator iterator = list1.iterator();

                while (iterator.hasNext())
                {
                    EntityLivingBase entitylivingbase = (EntityLivingBase)iterator.next();
                    double d0 = this.getDistanceSqToEntity(entitylivingbase);
                    //System.out.println("ENT:"+entitylivingbase+" Distance:"+d0);

                    double radius=32.0D;
                    
                    if (d0 < radius)
                    {
                        double d1 = 1.0D - Math.sqrt(d0) /  Math.sqrt(radius);

                        /*if (entitylivingbase == p_70184_1_.entityHit)
                        {
                            d1 = 1.0D;
                        }*/

                        Iterator iterator1 = list.iterator();

                        while (iterator1.hasNext())
                        {
                            PotionEffect potioneffect = (PotionEffect)iterator1.next();
                            int i = potioneffect.getPotionID();

                            //System.out.println("Apply potion:"+i);
                            if (Potion.potionTypes[i].isInstant())
                            {
                                Potion.potionTypes[i].affectEntity(this.shooter, entitylivingbase, potioneffect.getAmplifier(), d1);
                            }
                            else
                            {
                                int j = (int)(d1 * (double)potioneffect.getDuration() + 0.5D);
                                //System.out.println("J:"+j);
                                if (j > 20)
                                {
                                	//System.out.println("Apply potion:"+i+"for dur:"+j);
                                    entitylivingbase.addPotionEffect(new PotionEffect(i, j, potioneffect.getAmplifier()));
                                }
                            }
                        }
                    }
                }
            }
        }
	}
	
	


}
