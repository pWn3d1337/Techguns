package techguns.entities.projectiles;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.world.BlockEvent;
import techguns.TGBlocks;
import techguns.damagesystem.TGDamageSource;
import techguns.tileentities.BioblobTileEnt;
import techguns.util.EntityDeathUtils.DeathType;
import techguns.util.ProjectileExplosion;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class BiogunProjectile extends GenericProjectile implements IEntityAdditionalSpawnData{
	float gravity = 0.01f;
	float speed = 0.75f;
	public int level;
	
	  public BiogunProjectile(World par2World, EntityLivingBase player,
			float damage, float speed, int TTL, float spread, int dmgDropStart,
			int dmgDropEnd, float dmgMin, float penetration, int level, boolean blockdamage) {
		super(par2World, player, damage, speed, TTL, spread, dmgDropStart,
				dmgDropEnd, dmgMin, penetration, blockdamage);
		
		this.motionX*=speed;
        this.motionY*=speed;
        this.motionZ*=speed;
        this.level = level;
	}

	public BiogunProjectile(World par1World) {
		super(par1World);
		
		this.motionX*=speed;
        this.motionY*=speed;
        this.motionZ*=speed;
	}

	@Override
    protected float getGravityVelocity() 
    {
    	return gravity;
    }

	
	
	
	 @Override
	protected void onHitEffect(EntityLivingBase ent) {
 		ent.addPotionEffect(new PotionEffect(Potion.poison.id,100,3,false));
	}

	 
	 
	 
	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causePoisonDamage(this, this.shooter, DeathType.BIO);
    	src.goreChance = 1.0f;
    	src.armorPenetration = this.penetration;
    	src.setNoKnockback();
    	return src;
	}

	@Override
	protected void hitBlock(MovingObjectPosition mop) {
		 Block blockHit = this.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
		 
		 if(blockHit == TGBlocks.bioBlobSmall){
			 //System.out.println("Hit Bioblob, increase size");       	
			 
			 TileEntity tile = this.worldObj.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
			 if(tile!=null && tile instanceof BioblobTileEnt){
				 
				 ((BioblobTileEnt)tile).hitBlob(level, this.shooter);
			 }
			 
			 
		 } else {
			 if (!this.worldObj.isRemote){
    			 int side = mop.sideHit;
    			 int[] offset = {0,0,0};
    			 
    			 if (side==0){
    				 offset[1]=-1;
    			 } else if(side==1){
    				 offset[1]=1;
    			 } else if(side==2){
    				 offset[2]=-1;
    			 } else if(side==3){
    				 offset[2]=1;
    			 } else if(side==4){
    				 offset[0]=-1;
    			 } else if(side==5){
    				 offset[0]=1;
    			 }
    			 
    			 int[] blobPos = {mop.blockX+offset[0], mop.blockY+offset[1], mop.blockZ+offset[2]};
    			 
    			 if (this.worldObj.isAirBlock(blobPos[0],blobPos[1],blobPos[2])){
    				 
    				 boolean canPlace = true;
    				 
    				 if ( this.shooter instanceof EntityPlayer){
	    				 final BlockEvent.PlaceEvent placeEvent = new BlockEvent.PlaceEvent(BlockSnapshot.getBlockSnapshot(worldObj, blobPos[0],blobPos[1],blobPos[2]), blockHit, (EntityPlayer) this.shooter);
	    				 MinecraftForge.EVENT_BUS.post(placeEvent);
	    				 canPlace = !placeEvent.isCanceled();
    				 }
    				 
    				 if (canPlace){
	    				 this.worldObj.setBlock(blobPos[0],blobPos[1],blobPos[2], TGBlocks.bioBlobSmall);
	    				 if (this.level>1){
	    					 TileEntity tile = this.worldObj.getTileEntity(blobPos[0],blobPos[1],blobPos[2]);
	    					 if(tile!=null && tile instanceof BioblobTileEnt){
	    						 ((BioblobTileEnt)tile).hitBlob(level-1, this.shooter);
	    					 }
	    				 }
	    				 this.worldObj.setBlockMetadataWithNotify(blobPos[0],blobPos[1],blobPos[2], side, 3);
    				 }
    			 } else {
    				 
    				 TileEntity tile = this.worldObj.getTileEntity(blobPos[0],blobPos[1],blobPos[2]);
        			 if(tile!=null && tile instanceof BioblobTileEnt){
        				 
        				 ((BioblobTileEnt)tile).hitBlob(level, this.shooter);
        			 }
    				 
    			 }
    			 
    			 
    		 }
		 }
	
	
	    this.worldObj.spawnParticle("slime", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
	}

	/*@Override
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {

        if (par1MovingObjectPosition.entityHit != null)
        { 	
        	TGDamageSource src = TGDamageSource.causeBulletDamage(this, this.shooter, DeathType.BIO);
        	src.goreChance = 1.0f;
        	src.armorPenetration = this.penetration;
        	src.setNoKnockback();
        	
        	TGDamageSource knockback = TGDamageSource.getKnockbackDummyDmgSrc(this, this.shooter);

        	if (par1MovingObjectPosition.entityHit instanceof EntityLiving){
        		EntityLiving ent = (EntityLiving) par1MovingObjectPosition.entityHit;
        		
        		ent.attackEntityFrom(knockback, 0.01f);
        		ent.attackEntityFrom(src, damage);
        		


        	} else {
        		par1MovingObjectPosition.entityHit.attackEntityFrom(src,damage);     	
        	}
           
            this.setDead();
        }else if (par1MovingObjectPosition.typeOfHit == MovingObjectType.BLOCK) { */
    		//hit a block
    		
        	//System.out.println("Side Hit:"+par1MovingObjectPosition.sideHit);
        	
        	/*
        	int hitBlockLevel=0;
    		if (!this.worldObj.isRemote) {
            	if (this.worldObj.getBlock(par1MovingObjectPosition.blockX, par1MovingObjectPosition.blockY, par1MovingObjectPosition.blockZ) == TGBlocks.bioBlobSmall) {
            		//this.worldObj.setBlock(par1MovingObjectPosition.blockX, par1MovingObjectPosition.blockY, par1MovingObjectPosition.blockZ, Techguns.bioBlobMedium);
            		hitBlockLevel=1;
            	}else if (this.worldObj.getBlock(par1MovingObjectPosition.blockX, par1MovingObjectPosition.blockY, par1MovingObjectPosition.blockZ) == TGBlocks.bioBlobMedium) {
            		//this.worldObj.setBlock(par1MovingObjectPosition.blockX, par1MovingObjectPosition.blockY, par1MovingObjectPosition.blockZ, Techguns.bioBlobLarge);
            		hitBlockLevel=2;
            	}else if (this.worldObj.getBlock(par1MovingObjectPosition.blockX, par1MovingObjectPosition.blockY, par1MovingObjectPosition.blockZ) == TGBlocks.bioBlobLarge) {
            		hitBlockLevel=3;
            		//if (Math.random() >= 0.5) {
            		//	new ProjectileExplosion(this.worldObj, par1MovingObjectPosition.blockX, par1MovingObjectPosition.blockY, par1MovingObjectPosition.blockZ, this, 1.5f, 30, 1.0f, 2.0f).doExplosion(false);
            		//	this.worldObj.setBlock(par1MovingObjectPosition.blockX, par1MovingObjectPosition.blockY, par1MovingObjectPosition.blockZ, Blocks.fire);
            		//}
            	}else {
		    		//if (this.worldObj.isAirBlock(par1MovingObjectPosition.blockX, par1MovingObjectPosition.blockY+1, par1MovingObjectPosition.blockZ))
		            //{
		    		//	this.worldObj.setBlock(par1MovingObjectPosition.blockX, par1MovingObjectPosition.blockY+1, par1MovingObjectPosition.blockZ, Techguns.bioBlobSmall);	
		            //}
            	}
            	setBlockForLevel(par1MovingObjectPosition.blockX, par1MovingObjectPosition.blockY, par1MovingObjectPosition.blockZ, hitBlockLevel);
    		}
        	 */
        	// if(!this.worldObj.isRemote){
        		 
        /*		 Block blockHit = this.worldObj.getBlock(par1MovingObjectPosition.blockX, par1MovingObjectPosition.blockY, par1MovingObjectPosition.blockZ);
        		 
        		 if(blockHit == TGBlocks.bioBlobSmall){
        			 //System.out.println("Hit Bioblob, increase size");       	
        			 
        			 TileEntity tile = this.worldObj.getTileEntity(par1MovingObjectPosition.blockX, par1MovingObjectPosition.blockY, par1MovingObjectPosition.blockZ);
        			 if(tile!=null && tile instanceof BioblobTileEnt){
        				 
        				 ((BioblobTileEnt)tile).hitBlob(level);
        			 }
        			 
        			 
        		 } else {
        			 if (!this.worldObj.isRemote){
	        			 int side = par1MovingObjectPosition.sideHit;
	        			 int[] offset = {0,0,0};
	        			 
	        			 if (side==0){
	        				 offset[1]=-1;
	        			 } else if(side==1){
	        				 offset[1]=1;
	        			 } else if(side==2){
	        				 offset[2]=-1;
	        			 } else if(side==3){
	        				 offset[2]=1;
	        			 } else if(side==4){
	        				 offset[0]=-1;
	        			 } else if(side==5){
	        				 offset[0]=1;
	        			 }
	        			 
	        			 int[] blobPos = {par1MovingObjectPosition.blockX+offset[0], par1MovingObjectPosition.blockY+offset[1], par1MovingObjectPosition.blockZ+offset[2]};
	        			 
	        			 if (this.worldObj.isAirBlock(blobPos[0],blobPos[1],blobPos[2])){
	        				 this.worldObj.setBlock(blobPos[0],blobPos[1],blobPos[2], TGBlocks.bioBlobSmall);
	        				 if (this.level>1){
	        					 TileEntity tile = this.worldObj.getTileEntity(blobPos[0],blobPos[1],blobPos[2]);
	        					 if(tile!=null && tile instanceof BioblobTileEnt){
	        						 ((BioblobTileEnt)tile).hitBlob(level-1);
	        					 }
	        				 }
	        				 this.worldObj.setBlockMetadataWithNotify(blobPos[0],blobPos[1],blobPos[2], side, 3);
	        			 } else {
	        				 
	        				 TileEntity tile = this.worldObj.getTileEntity(blobPos[0],blobPos[1],blobPos[2]);
	            			 if(tile!=null && tile instanceof BioblobTileEnt){
	            				 
	            				 ((BioblobTileEnt)tile).hitBlob(level);
	            			 }
	        				 
	        			 }
	        			 
	        			 
	        		 }
        		 }
        	 //}
    		
    		
            this.setDead();

            //EntityFX fx = new EntityBreakingFX(this.worldObj, this.posX, this.posY, this.posZ, Techguns.bioMass);
            //Minecraft.getMinecraft().effectRenderer.addEffect(fx);
            this.worldObj.spawnParticle("slime", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            //Techguns.proxy.spawnParticle("BioTrail", this.worldObj, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            //this.worldObj.playSoundAtEntity(this, "techguns:effects.biogunImpact", 1.0F, 1.0F );
        	
    	}
   	
    }*/
	
	 
	private void updateBlockLevel(int x, int y, int z, int level){
		switch (level){
		case 1:
			this.worldObj.setBlock(x, y, z, TGBlocks.bioBlobSmall);
			break;
		/*case 2:
			this.worldObj.setBlock(x, y, z, TGBlocks.bioBlobMedium);
			break;
		case 3:
			this.worldObj.setBlock(x, y, z, TGBlocks.bioBlobLarge);
			break;*/
		}
	}
	 
	private void setBlockForLevel(int x, int y, int z, int level){
		if (level==0){
			if (this.worldObj.isAirBlock(x, y+1, z)){
				updateBlockLevel(x,y+1,z,this.level);
			}
		} else {
			int newlvl = level+this.level;
			if (newlvl >3){
    			new ProjectileExplosion(this.worldObj, x, y, z, this.shooter, 1.5f, 30, 1.0f, 2.0f).doExplosion(false, this.shooter);
    			this.worldObj.setBlock(x, y, z, Blocks.fire);
			} else {
				updateBlockLevel(x,y,z,newlvl);
			}
		}
		
	};
	
	
	/* (non-Javadoc)
	 * @see techguns.entities.projectiles.GenericProjectile#readEntityFromNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	protected void readEntityFromNBT(NBTTagCompound tags) {
		super.readEntityFromNBT(tags);
	}

	/* (non-Javadoc)
	 * @see techguns.entities.projectiles.GenericProjectile#writeEntityToNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	protected void writeEntityToNBT(NBTTagCompound tags) {
		super.writeEntityToNBT(tags);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.isInWater())
        {
            for (int l = 0; l < 4; ++l)
            {
                float f4 = 0.25F;
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ);
            }
            this.setDead();
        } else {
        	//float f5 = 0.1f;
        	//this.worldObj.spawnParticle("slime", this.posX, this.posY, this.posZ, this.motionX*f5, (this.motionY*f5)-this.getGravityVelocity(), this.motionZ*f5);
        	//Techguns.proxy.spawnParticle("BioTrail", this.worldObj, this.posX, this.posY, this.posZ, this.motionX*f5, (this.motionY*f5)-this.getGravityVelocity(), this.motionZ*f5);

        }
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeByte((byte)this.level);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.level=additionalData.readByte();
	}
}
