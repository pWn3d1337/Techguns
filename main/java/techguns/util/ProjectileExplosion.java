package techguns.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ExplosionEvent;
import techguns.Techguns;
import techguns.damagesystem.DamageSystem;
import techguns.damagesystem.TGDamageSource;
import techguns.util.EntityDeathUtils.DeathType;

public class ProjectileExplosion{
	
	public Explosion explosion; //dummy object for various shitty coded methods
    public int entityDamage;
    public float dmgRadius1;
    public float dmgRadius2;
     
    public boolean isFlaming; //Create Fire;
    
    public TGDamageSource damageSource=null;
    public String explosionSound="random.explode";
    
    private int field_77289_h = 16;
    private Random explosionRNG = new Random();
    private World worldObj;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public EntityLivingBase exploder;
    public float explosionSize;
    /** A list of ChunkPositions of blocks affected by this explosion */
    public List affectedBlockPositions = new ArrayList();
    private Map hashmap = new HashMap();
    //private static final String __OBFID = "CL_00000134";

    
    public ProjectileExplosion(World worldObj, double explosionX,
			double explosionY, double explosionZ, EntityLivingBase exploder,
			float explosionSize, int entityDamage, float dmgRadius1,
			float dmgRadius2) {
		super();
		
		this.explosion = new Explosion(worldObj, exploder, explosionX, explosionY, explosionZ, explosionSize);
		this.exploder= exploder;
		this.entityDamage = entityDamage;
		this.dmgRadius1 = dmgRadius1;
		this.dmgRadius2 = dmgRadius2;
		
		this.worldObj = worldObj;
		this.explosionX = explosionX;
		this.explosionY = explosionY;
		this.explosionZ = explosionZ;
		this.explosionSize = explosionSize;
	}

    /**
     * Does the full explosion
     * @param blockDamage (destroy blocks?)
     */
    public void doExplosion(boolean blockDamage, EntityLivingBase attacker) {
    
    	
    	if (!worldObj.isRemote){
	    	final ExplosionEvent.Start ExplosionStart = new ExplosionEvent.Start(worldObj, explosion);
	    	MinecraftForge.EVENT_BUS.post(ExplosionStart);    	
	    	
	    	if (!ExplosionStart.isCanceled()){
		    	this.doExplosionA(blockDamage, attacker);
		    	this.doExplosionB(blockDamage);
	    	}
    	} else {
    		this.doExplosionA(blockDamage, attacker);
	    	this.doExplosionB(blockDamage);
    	}
    }
    
    
    public ProjectileExplosion setDamageSource(TGDamageSource dmgSrc){
    	this.damageSource=dmgSrc;
    	return this;
    }
    
    /**
     * Defaults to "random.explode" if no sound is set.
     * @param soundName
     * @return
     */
    public ProjectileExplosion setExplosionSound(String soundName){
    	this.explosionSound=soundName;
    	return this;
    }
    
    /**
     * Does the first part of the explosion (destroy blocks)
     */
    public void doExplosionA(boolean blockDamage, EntityLivingBase attacker)
    {
        float f = this.explosionSize;
        HashSet hashset = new HashSet();
        int i;
        int j;
        int k;
        double d5;
        double d6;
        double d7;

        for (i = 0; i < this.field_77289_h; ++i)
        {
            for (j = 0; j < this.field_77289_h; ++j)
            {
                for (k = 0; k < this.field_77289_h; ++k)
                {
                    if (i == 0 || i == this.field_77289_h - 1 || j == 0 || j == this.field_77289_h - 1 || k == 0 || k == this.field_77289_h - 1)
                    {
                        double d0 = (double)((float)i / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double d1 = (double)((float)j / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double d2 = (double)((float)k / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 /= d3;
                        d1 /= d3;
                        d2 /= d3;
                        float f1 = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
                        d5 = this.explosionX;
                        d6 = this.explosionY;
                        d7 = this.explosionZ;

                        for (float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.75F)
                        {
                            int j1 = MathHelper.floor_double(d5);
                            int k1 = MathHelper.floor_double(d6);
                            int l1 = MathHelper.floor_double(d7);
                            Block block = this.worldObj.getBlock(j1, k1, l1);

                            if (block.getMaterial() != Material.air)
                            {
                                float f3 = this.exploder != null ? this.exploder.func_145772_a(this.explosion, this.worldObj, j1, k1, l1, block) : block.getExplosionResistance(this.exploder, worldObj, j1, k1, l1, explosionX, explosionY, explosionZ);
                                f1 -= (f3 + 0.3F) * f2;
                            }

                            if (f1 > 0.0F && (this.exploder == null || this.exploder.func_145774_a(this.explosion, this.worldObj, j1, k1, l1, block, f1)))
                            {
                                hashset.add(new ChunkPosition(j1, k1, l1));
                            }

                            d5 += d0 * (double)f2;
                            d6 += d1 * (double)f2;
                            d7 += d2 * (double)f2;
                        }
                    }
                }
            }
        }

        this.affectedBlockPositions.addAll(hashset);
        this.explosionSize *= 2.0F;
        i = MathHelper.floor_double(this.explosionX - (double)this.dmgRadius2 - 1.0D);
        j = MathHelper.floor_double(this.explosionX + (double)this.dmgRadius2 + 1.0D);
        k = MathHelper.floor_double(this.explosionY - (double)this.dmgRadius2 - 1.0D);
        int i2 = MathHelper.floor_double(this.explosionY + (double)this.dmgRadius2 + 1.0D);
        int l = MathHelper.floor_double(this.explosionZ - (double)this.dmgRadius2 - 1.0D);
        int j2 = MathHelper.floor_double(this.explosionZ + (double)this.dmgRadius2 + 1.0D);
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, AxisAlignedBB.getBoundingBox((double)i, (double)k, (double)l, (double)j, (double)i2, (double)j2));
       
        if (!this.worldObj.isRemote){
        	net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.worldObj, this.explosion, list, this.explosionSize);
        }
        
        Vec3 vec3 = Vec3.createVectorHelper(this.explosionX, this.explosionY, this.explosionZ);

        for (int i1 = 0; i1 < list.size(); ++i1)
        {
            Entity entity = (Entity)list.get(i1);
            //double d4 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double)this.explosionSize;
            double distance = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ);
            double d4 = distance / (double)this.explosionSize;
            
            if (distance <= this.dmgRadius2)
            {
                d5 = entity.posX - this.explosionX;
                d6 = entity.posY + (double)entity.getEyeHeight() - this.explosionY;
                d7 = entity.posZ - this.explosionZ;
                double d9 = (double)MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);

                if (d9 != 0.0D)
                {
                    d5 /= d9;
                    d6 /= d9;
                    d7 /= d9;
                    
                    float density = this.worldObj.getBlockDensity(vec3, entity.boundingBox);
                    
                    double dmg = entityDamage * Math.min(1.0, 1- (distance - this.dmgRadius1) / (this.dmgRadius2 - this.dmgRadius1) );
                    double d11 = (1.0D - d4) * (double)density;   //explosionForce;           
                    
                    float damage = ((float)dmg ); //* (0.5f+(density/2.0f)));
                    
                    
                    if (attacker !=null && attacker instanceof EntityLivingBase && entity instanceof EntityLivingBase){
                    	damage *= DamageSystem.getDamageFactor(attacker, (EntityLivingBase) entity);                    	
                    }
                    
                    
                    if(this.damageSource==null){
                    
                    TGDamageSource dmgsrc = TGDamageSource.causeExplosionDamage(this.exploder,null, DeathType.GORE);
	                    //dmgsrc.setExplosionSource(this.explosion);
	                    dmgsrc.goreChance = 0.35f;
	                    
	                    //entity.attackEntityFrom(DamageSource.setExplosionSource(this.explosion), damage);
	                    entity.attackEntityFrom(dmgsrc, damage);
                    } else {
                    	//damageSource.setExplosionSource(this.explosion);
                    	 entity.attackEntityFrom(damageSource, damage);
                    	
                    }
                    
                    //System.out.printf("Explosiondamage to [%s]: %5.2f (dmg: %5.2f, distance: %5.2f)\n", entity.getClass().getName(),damage,dmg,distance);
                    double d8 = EnchantmentProtection.func_92092_a(entity, d11);
                    entity.motionX += d5 * d8;
                    entity.motionY += d6 * d8;
                    entity.motionZ += d7 * d8;

                    if (entity instanceof EntityPlayer)
                    {
                        this.hashmap.put((EntityPlayer)entity, Vec3.createVectorHelper(d5 * d11, d6 * d11, d7 * d11));
                    }
                }
            }
        }

        this.explosionSize = f;
    }

    /**
     * Does the second part of the explosion (sound, particles, drop spawn)
     */
    public void doExplosionB(boolean blockDamage)
    {
        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, explosionSound, 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

//        if (this.explosionSize >= 2.0F)
//        {
//            this.worldObj.spawnParticle("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
//        }
//        else
//        {
//            this.worldObj.spawnParticle("largeexplode", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
//        }

        //createExplosionEffect();

        Iterator iterator;
        ChunkPosition chunkposition;
        int i;
        int j;
        int k;
        Block block;

        if (true)
        {
            iterator = this.affectedBlockPositions.iterator();

            while (iterator.hasNext())
            {
                chunkposition = (ChunkPosition)iterator.next();
                i = chunkposition.chunkPosX;
                j = chunkposition.chunkPosY;
                k = chunkposition.chunkPosZ;
                block = this.worldObj.getBlock(i, j, k);

                if (true)
                {
                    double d0 = (double)((float)i + this.worldObj.rand.nextFloat());
                    double d1 = (double)((float)j + this.worldObj.rand.nextFloat());
                    double d2 = (double)((float)k + this.worldObj.rand.nextFloat());
                    double d3 = d0 - this.explosionX;
                    double d4 = d1 - this.explosionY;
                    double d5 = d2 - this.explosionZ;
                    double d6 = (double)MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
                    d3 /= d6;
                    d4 /= d6;
                    d5 /= d6;
                    double d7 = 0.5D / (d6 / (double)this.explosionSize + 0.1D);
                    d7 *= (double)(this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
                    d3 *= d7;
                    d4 *= d7;
                    d5 *= d7;
              //      this.worldObj.spawnParticle("explode", (d0 + this.explosionX * 1.0D) / 2.0D, (d1 + this.explosionY * 1.0D) / 2.0D, (d2 + this.explosionZ * 1.0D) / 2.0D, d3, d4, d5);
              //      this.worldObj.spawnParticle("smoke", d0, d1, d2, d3, d4, d5);
                }

                if (blockDamage) {
	                if (block.getMaterial() != Material.air)
	                {
	                    if (block.canDropFromExplosion(this.explosion))
	                    {
	                        block.dropBlockAsItemWithChance(this.worldObj, i, j, k, this.worldObj.getBlockMetadata(i, j, k), 1.0F / this.explosionSize, 0);
	                    }
	
	                    block.onBlockExploded(this.worldObj, i, j, k, this.explosion);
	                }
                }
            }
        }

        if (this.isFlaming)
        {
            iterator = this.affectedBlockPositions.iterator();

            while (iterator.hasNext())
            {
                chunkposition = (ChunkPosition)iterator.next();
                i = chunkposition.chunkPosX;
                j = chunkposition.chunkPosY;
                k = chunkposition.chunkPosZ;
                block = this.worldObj.getBlock(i, j, k);
                Block block1 = this.worldObj.getBlock(i, j - 1, k);

                if (block.getMaterial() == Material.air && block1.func_149730_j() && this.explosionRNG.nextInt(3) == 0)
                {
                    this.worldObj.setBlock(i, j, k, Blocks.fire);
                }
            }
        }
    }

    private void createExplosionEffect() {
    	//Small Explosion
        if (this.dmgRadius2 < 1.5) {
        	Techguns.proxy.spawnParticle("Explosion", this.worldObj, this.explosionX, this.explosionY, this.explosionZ, 0, 0.025f, 0);
        	Techguns.proxy.spawnParticle("LargeSmoke", this.worldObj, this.explosionX, this.explosionY, this.explosionZ, 0, 0.045f, 0);
        } else {
        	//Large Explosion
        	int count = Math.round(dmgRadius2 * 3f);
        	for (int i = 0; i < count; i++) {
        		Techguns.proxy.spawnParticle("Explosion", this.worldObj,
        				this.explosionX+(1f-(explosionRNG.nextFloat()*2f))*dmgRadius2*0.33f,
        				this.explosionY+(1f-(explosionRNG.nextFloat()*2f))*dmgRadius2*0.33f,
        				this.explosionZ+(1f-(explosionRNG.nextFloat()*2f))*dmgRadius2*0.33f,
        				0.050f*(1f-(explosionRNG.nextFloat()*2f)),
        				0.050f*explosionRNG.nextFloat(),
        				0.050f*(1f-(explosionRNG.nextFloat()*2f)));
        	}
        	//Explosion Ring
        	count = Math.round(dmgRadius2 * 8f);
        	float r = dmgRadius2 * 0.15f;
        	float angle = ((float)Math.PI)*2f/(float)count;
        	float v = 0.21f;
        	for (int i = 0; i < count; i++) {
        		float x = (float) Math.cos(angle*(float)i);
        		float z = (float) Math.sin(angle*(float)i);
        		float rand = 0.5f+(this.explosionRNG.nextFloat()*0.5f);
        		Techguns.proxy.spawnParticle("Fireblast", this.worldObj, this.explosionX+x, this.explosionY, this.explosionZ+z, x*v*rand, 0f, z*v*rand);
        	}
        	
        	//Smoke
        	count = Math.round(dmgRadius2 * 2f);
        	for (int i = 0; i < count; i++) {
        		Techguns.proxy.spawnParticle("LargeSmoke", this.worldObj,
        				this.explosionX+(1f-(explosionRNG.nextFloat()*2f))*dmgRadius2*0.22f,
        				this.explosionY+(explosionRNG.nextFloat())*dmgRadius2*0.16f,
        				this.explosionZ+(1f-(explosionRNG.nextFloat()*2f))*dmgRadius2*0.22f,
        				0.025f*(1f-(explosionRNG.nextFloat()*2f)),
        				0.010f*explosionRNG.nextFloat(),
        				0.025f*(1f-(explosionRNG.nextFloat()*2f)));
        	}
        }
        
        
        
        
        
        //Smoke
	}

	public Map func_77277_b()
    {
        return this.hashmap;
    }

//    /**
//     * Returns either the entity that placed the explosive block, the entity that caused the explosion or null.
//     */
//    public EntityLivingBase getExplosivePlacedBy()
//    {
//        return this.exploder == null ? null : (this.exploder instanceof EntityTNTPrimed ? ((EntityTNTPrimed)this.exploder).getTntPlacedBy() : (this.exploder instanceof EntityLivingBase ? (EntityLivingBase)this.exploder : null));
//    }
}
