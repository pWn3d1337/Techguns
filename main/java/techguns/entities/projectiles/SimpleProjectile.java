package techguns.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SimpleProjectile extends GenericProjectile{
	
	private ResourceLocation projectileTexture = new ResourceLocation("techguns:textures/items/testItem.png");
    private float scale;
    private float gravity;

	public SimpleProjectile(World par1World) {
		super(par1World);
		this.gravity = 0.015f;
	}

	/*public SimpleProjectile(World par1World, double par2, double par4,
			double par6) {
		super(par1World, par2, par4, par6);
	}

	public SimpleProjectile(World par1World,
			EntityLivingBase par2EntityLivingBase) {
		super(par1World, par2EntityLivingBase);
	}*/

	public SimpleProjectile(World par2World, EntityLivingBase par3Player,
			float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage) {
		super(par2World, par3Player, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage);
	}

	public SimpleProjectile(World par2World, EntityLivingBase par3Player,
			float damage, float speed, int TTL, String texture, float scale, float gravity, float spread, int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage) {
		super(par2World, par3Player, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage);
		this.projectileTexture = new ResourceLocation(texture);
		this.scale = scale;
		this.gravity = gravity;
		
	}
	
	  @Override
    protected float getGravityVelocity() 
    {
    	return gravity;
    }

	public float getScale() {
		
		return scale;
	}
	
	public ResourceLocation getProjectileTexture() {
		return projectileTexture;
	}
	

	
	 /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void writeEntityToNBT(NBTTagCompound nbtTagCompound)
    {
    	System.out.println("writeEntityToNBT");
    	super.writeEntityToNBT(nbtTagCompound);
    	
    	nbtTagCompound.setString("projectileTexture", projectileTexture.getResourcePath());
    	
    	nbtTagCompound.setFloat("scale", scale);
    	nbtTagCompound.setFloat("gravity", gravity);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
	@Override
    public void readEntityFromNBT(NBTTagCompound nbtTagCompound)
    {
    	System.out.println("ReadEntityFromNBT");
    	super.readEntityFromNBT(nbtTagCompound);
       this.projectileTexture = new ResourceLocation("techguns",nbtTagCompound.getString("projectileTexture"));	
       this.scale = nbtTagCompound.getFloat("scale");
       this.gravity = nbtTagCompound.getFloat("gravity");
    }
}
