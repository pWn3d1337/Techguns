/**
 * 
 */
package techguns.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.EntityDamageSource;
import techguns.util.EntityDeathUtils.DeathType;


public class TGExplosionDamageSource extends EntityDamageSource {
	
	public DeathType deathtype = DeathType.DEFAULT;

	protected Entity attacker;
	
	
	public TGExplosionDamageSource(String p_i1567_1_, Entity p_i1567_2_, Entity Attacker,  DeathType deathtype) {
		super(p_i1567_1_, p_i1567_2_);
		this.deathtype = deathtype;
		this.attacker = Attacker;
	}

	@Override
    public Entity getSourceOfDamage()
    {
        return this.damageSourceEntity;
    }

    @Override
    public Entity getEntity()
    {
        return this.attacker;
    }
	
//    @Override
//    public IChatComponent func_151519_b(EntityLivingBase p_151519_1_)
//    {
//        IChatComponent ichatcomponent = this.attacker == null ? this.damageSourceEntity.func_145748_c_() : this.attacker.func_145748_c_();
//        ItemStack itemstack = this.shootingEntity instanceof EntityLivingBase ? ((EntityLivingBase)this.shootingEntity).getHeldItem() : null;
//        String s = "death.attack." + this.damageType;
//        String s1 = s + ".item";
//        
//        return itemstack != null && itemstack.hasDisplayName() && StatCollector.canTranslate(s1) ? new ChatComponentTranslation(s1, new Object[] {p_151519_1_.func_145748_c_(), ichatcomponent, itemstack.func_151000_E()}): new ChatComponentTranslation(s, new Object[] {p_151519_1_.func_145748_c_(), ichatcomponent});
//    }
    
//    public static TGExplosionDamageSource causeExplosionDamage(Entity projectile, Entity attacker, DeathType deathtype){
//    	return (new TGExplosionDamageSource("explosion", projectile, attacker, deathtype));
//    }
    



    
}
