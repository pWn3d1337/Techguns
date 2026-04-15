/**
 * 
 */
package techguns.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import techguns.util.EntityDeathUtils.DeathType;


public class BulletDamageSource extends EntityDamageSource {
	
	public DeathType deathtype = DeathType.DEFAULT;

	protected Entity shootingEntity;
	
	public BulletDamageSource(String p_i1567_1_, Entity p_i1567_2_, Entity Attacker, boolean ArmorPiercing) {
		super(p_i1567_1_, p_i1567_2_);
		this.shootingEntity = Attacker;
		this.setProjectile();
		if (ArmorPiercing){
			this.setDamageBypassesArmor();
		}
	}
	
	public BulletDamageSource(String p_i1567_1_, Entity p_i1567_2_, Entity Attacker, boolean ArmorPiercing, DeathType deathtype) {
		this(p_i1567_1_, p_i1567_2_, Attacker, ArmorPiercing);
		this.deathtype = deathtype;
	}

	@Override
    public Entity getSourceOfDamage()
    {
        return this.damageSourceEntity;
    }

    @Override
    public Entity getEntity()
    {
        return this.shootingEntity;
    }
	
    @Override
    public IChatComponent func_151519_b(EntityLivingBase p_151519_1_)
    {
        IChatComponent ichatcomponent = this.shootingEntity == null ? this.damageSourceEntity.func_145748_c_() : this.shootingEntity.func_145748_c_();
        ItemStack itemstack = this.shootingEntity instanceof EntityLivingBase ? ((EntityLivingBase)this.shootingEntity).getHeldItem() : null;
        String s = "death.attack." + this.damageType;
        String s1 = s + ".item";
        
        return itemstack != null && itemstack.hasDisplayName() && StatCollector.canTranslate(s1) ? new ChatComponentTranslation(s1, new Object[] {p_151519_1_.func_145748_c_(), ichatcomponent, itemstack.func_151000_E()}): new ChatComponentTranslation(s, new Object[] {p_151519_1_.func_145748_c_(), ichatcomponent});
    }
    
    public static BulletDamageSource causeBulletDamage(Entity Bullet, Entity Shooter, boolean ArmorPiercing){
    	return (new BulletDamageSource("bullet", Bullet, Shooter, ArmorPiercing));
    }
    
	public static BulletDamageSource causeBulletDamage(Entity Bullet, Entity Shooter, boolean ArmorPiercing, DeathType deathType) {
		return (new BulletDamageSource("bullet", Bullet, Shooter, ArmorPiercing, deathType));
	}
    
   /* public static float getDamageWithArmorPen(Entity ent, float damage, float penetration){
    	if (ent instanceof EntityLivingBase) {
    		EntityLivingBase entity = (EntityLivingBase) ent;
    		int armor = entity.getTotalArmorValue();
    		
    		float damageBlocked = damage * (1-penetration);
    		
           float f1 = damageBlocked * (float)(25 - armor);
           damageBlocked = f1 / 25.0F;
    		
    		
    		return (damage*penetration) +damageBlocked;
    	} else {
    		return 0.0f;
    	}
    }*/



    
}
