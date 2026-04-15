package techguns.extendedproperties;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import techguns.client.audio.TGSound;
import techguns.client.audio.TGSoundCategory;
import techguns.util.SoundUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TechgunsExtendedPlayerPropertiesClient extends TechgunsExtendedPlayerProperties {

	public TGSound gliderLoop=null;
	
	public TGSound jetPackLoop=null;
	
	public TechgunsExtendedPlayerPropertiesClient(EntityPlayer player) {
		super(player);
	}

	public static TechgunsExtendedPlayerProperties register(EntityPlayer ply)
	{
		TechgunsExtendedPlayerProperties props = new TechgunsExtendedPlayerPropertiesClient(ply);
		ply.registerExtendedProperties(TechgunsExtendedPlayerProperties.EXT_PROP_NAME, props);
		return props;
	}
	
	@Override
	public void setJumpkeyPressed(boolean isJumpkeyPressed) {
		if(this.player.worldObj.isRemote){
			if(isJumpkeyPressed==true && jetPackLoop==null){
				jetPackLoop = new TGSound("techguns:jetpack.loop", this.player, 1.0f, 1.0f, true, true, false, TGSoundCategory.PLAYER_EFFECT);
				SoundUtil.playSoundAtEntityPos(this.player.worldObj, this.player, "techguns:jetpack.start", 1.0f, 1.0f, false, TGSoundCategory.PLAYER_EFFECT);
			}
			
			if(isJumpkeyPressed && !this.isJumpkeyPressed){
				Minecraft.getMinecraft().getSoundHandler().playSound(jetPackLoop);
			} else if ( !isJumpkeyPressed && this.isJumpkeyPressed){
				if(jetPackLoop!=null){
					jetPackLoop.setDonePlaying();
					jetPackLoop=null;
					SoundUtil.playSoundAtEntityPos(this.player.worldObj, this.player, "techguns:jetpack.stop", 1.0f, 1.0f, false, TGSoundCategory.PLAYER_EFFECT);
				}
			}
			
			
		}
		super.setJumpkeyPressed(isJumpkeyPressed);
	}

	
}
