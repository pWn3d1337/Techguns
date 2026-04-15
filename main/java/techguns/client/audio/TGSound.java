package techguns.client.audio;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import techguns.TGConfig;
import techguns.util.MathUtil;
import techguns.util.MathUtil.Vec2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TGSound extends MovingSound{
	
	Entity entity;

	boolean gunPosition;
	boolean moving;
	
	public TGSound(String soundname, Entity entity, float volume, float pitch, boolean repeat, boolean moving, boolean gunPosition, TGSoundCategory category) {
		this(soundname, entity, volume, pitch, repeat, moving, category);
		this.gunPosition = gunPosition;
		if (gunPosition) {
			if (entity instanceof EntityLivingBase) {
    			Vec2 pos = MathUtil.polarOffsetXZ(xPosF, zPosF, 1.0, ((EntityLivingBase)entity).rotationYawHead*Math.PI/180.0);
    			xPosF = (float) pos.x;
    			zPosF = (float) pos.y;
    			yPosF = yPosF + (entity.height*0.5f);		
    		}
		}
	}
	
	public TGSound(String soundname, Entity entity, float volume, float pitch, boolean repeat, boolean moving, TGSoundCategory category) {
		this(soundname, entity, volume, pitch, repeat, category);
		this.moving = moving;
		if (entity != null) {
			xPosF = (float) entity.posX;
	        yPosF = (float) entity.posY;
	        zPosF = (float) entity.posZ;
		}
	}
	
	public TGSound(String soundname, Entity entity, float volume, float pitch, boolean repeat, TGSoundCategory category) {
		super(new ResourceLocation(/*Techguns.MODID.toLowerCase(),*/soundname));
		this.volume = TGConfig.getVolumeMultiplier(volume, category);
		this.field_147663_c = pitch;
		this.repeat = repeat;
		this.entity = entity;
		//System.out.println("I'm a sound, lol - " + soundname);
	}
	
	public TGSound(String soundname, int posx, int posy, int posz, float volume, float pitch, boolean repeat, TGSoundCategory category){
		this(soundname,null,volume,pitch,repeat, category);
		this.xPosF= posx;
		this.yPosF = posy;
		this.zPosF = posz;
	}
	
//	public TGSound(String soundname, float volume, float pitch, boolean repeat) {
//		this(soundname, null, volume, pitch, repeat);
//		this.field_147666_i = ISound.AttenuationType.NONE;
//	}
	
//	public TGSound(String soundName, float x, float y, float z, float volume, float pitch, boolean repeat) {
//		this(soundName, null, volume, pitch, repeat);
//		this.xPosF = x;
//		this.yPosF = y;
//		this.zPosF = z;
//
//	}

	public void setDonePlaying()
    {
        this.repeat = false;
        this.donePlaying = true;
        this.field_147665_h = 0;
    }

    @Override
    public boolean isDonePlaying()
    {
        return this.donePlaying;
    }

    @Override
    public void update()
    {
       //TODO: Auto-remove?
        if (moving && entity != null) {
	        xPosF = (float) entity.posX;
	        yPosF = (float) entity.posY;
	        zPosF = (float) entity.posZ;
        	if (gunPosition) {
        		if (entity instanceof EntityLivingBase) {
        			Vec2 pos = MathUtil.polarOffsetXZ(xPosF, zPosF, 1.0, ((EntityLivingBase)entity).rotationYawHead*Math.PI/180.0);
        			xPosF = (float) pos.x;
        			zPosF = (float) pos.y;
        			yPosF = yPosF + (entity.height*0.5f);
        			
        		}
        		
        	}
        }
    }

	
	public void setPitch(float pitch) {
		this.field_147663_c = pitch;
	}

	public void setRepeatDelay(int delay)
    {
        this.field_147665_h = delay;
    }

}

//this.mc.getSoundHandler().playSound(new MovingSoundMinecart((EntityMinecart)p_72838_1_));