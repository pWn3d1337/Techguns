package techguns.extendedproperties;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import techguns.TGConfig;
import techguns.gui.playerinventory.TGPlayerInventory;

/**
 * 	Extended Player properties for Techguns
 * 
 *	From tutorial:
 *	https://github.com/coolAlias/Forge_Tutorials/blob/master/IExtendedEntityPropertiesTutorial.java
 *
 */
public class TechgunsExtendedPlayerProperties implements IExtendedEntityProperties {
	public final static String EXT_PROP_NAME = "TechgunsExtendedPlayer";
	private static final int NUMSLOTS=12;
	protected final EntityPlayer player;
	
	public static int DATA_WATCHER_ID_FACESLOT=TGConfig.dataWatcherID_FaceSlot;
	public static int DATA_WATCHER_ID_BACKSLOT=TGConfig.dataWatcherID_BackSlot;
	
	
	/*public static final int SLOT_FACE=0;
	public static final int SLOT_BACK=1;
	public static final int SLOTS_AUTOFOOD_START=2;
	public static final int SLOTS_AUTOFOOD_END=4;
	public static final int SLOT_AUTOHEAL=5;
	public static final int SLOTS_AMMO_START=6;
	public static final int SLOTS_AMMO_END=11;
	
	public ItemStack[] extendedInventory;*/
	public int radlevel=0;
	public final TGPlayerInventory TG_inventory;
	public int foodleft=0;
	public float lastSaturation=1.0f;
	public boolean enableNightVision=false;
	public boolean enableSafemode=true;
	
	public boolean gotCreativeFlightLastTick=false;
	/**
	 * was the player flying last tick?
	 */
	public boolean wasFlying=false;
	/**
	 * for jetpack
	 */
	public boolean enableHovermode=false;
	
	/**
	 * used by client only
	 */
	public boolean enableStepAssist=true;
	/**
	 * used by client only
	 */
	public boolean showTGHudElements=true;
	
	
	public boolean enableJetpack=true;
	
	/**
	 * Used by server only, needed for jetpack, not saved to nbt
	 */
	protected  boolean isJumpkeyPressed=false;
	
	/**
	 * Used by server only, needed for jetpack, not saved to nbt
	 */
	public boolean isForwardKeyPressed=false;

	/**
	 * Amount of Ticks this player can't shoot, used by client(only own player) and server, but not synced
	 */
	public int fireDelay=0;
	
	/**
	 * amount of ticks to wait for replaying loopsound
	 */
	public int loopSoundDelay=0;
	
	/**
	 * only used by client, not synced
	 */
	public boolean isGliding=false;
	
	/**
	 * Amount of ticks no EntitySwing() sound is played, used by SERVER!, not synced
	 */
	public byte swingSoundDelay=0;
	
	
	public TechgunsExtendedPlayerProperties(EntityPlayer player) {
		this.player = player;
		TG_inventory=new TGPlayerInventory(player);
		//extendedInventory=new ItemStack[NUMSLOTS];
		//this.player.getDataWatcher().addObject(DATA_WATCHER_ID_FACESLOT, (ItemStack)TG_inventory.inventory[TGPlayerInventory.SLOT_FACE]);
		//this.player.getDataWatcher().addObject(DATA_WATCHER_ID_BACKSLOT, (ItemStack)TG_inventory.inventory[TGPlayerInventory.SLOT_BACK]);
		this.player.getDataWatcher().addObjectByDataType(DATA_WATCHER_ID_FACESLOT, 5);
		this.player.getDataWatcher().addObjectByDataType(DATA_WATCHER_ID_BACKSLOT, 5);
	}

	/**
	 * Used to register these extended properties for the player during EntityConstructing event
	 * This method is for convenience only; it will make your code look nicer
	 */
	public static TechgunsExtendedPlayerProperties register(EntityPlayer ply)
	{
		TechgunsExtendedPlayerProperties props = new TechgunsExtendedPlayerProperties(ply);
		ply.registerExtendedProperties(TechgunsExtendedPlayerProperties.EXT_PROP_NAME, props);
		return props;
	}
	
	/**
	 * Returns ExtendedPlayer properties for player
	 * This method is for convenience only; it will make your code look nicer
	 */
	public static final TechgunsExtendedPlayerProperties get(EntityPlayer ply)
	{
		return (TechgunsExtendedPlayerProperties) ply.getExtendedProperties(EXT_PROP_NAME);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound tags) {
		/*NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.extendedInventory.length; ++i)
        {
            if (this.extendedInventory[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.extendedInventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        tags.setTag(EXT_PROP_NAME+"Items", nbttaglist);*/
		this.TG_inventory.saveNBTData(tags);
        tags.setInteger("radlevel", radlevel);
        tags.setShort("foodLeft", (short) foodleft);
        tags.setFloat("lastSaturation", lastSaturation);
        tags.setBoolean("enableNightvision", this.enableNightVision);
        tags.setBoolean("enableSafemode", this.enableSafemode);
        tags.setBoolean("enableHovermode", this.enableHovermode);
        tags.setBoolean("enableJetpack", this.enableJetpack);
        
        tags.setBoolean("showHud",showTGHudElements);
        tags.setBoolean("stepAssist",enableStepAssist);
	}

	@Override
	public void loadNBTData(NBTTagCompound tags) {
		/*NBTTagList nbttaglist = tags.getTagList(EXT_PROP_NAME+"Items", 10);
        this.extendedInventory = new ItemStack[NUMSLOTS];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < NUMSLOTS)
            {
                this.extendedInventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }*/
		this.TG_inventory.loadNBTData(tags);
        radlevel=tags.getInteger("radlevel");
        foodleft = tags.getInteger("foodLeft");
        lastSaturation=tags.getFloat("lastSaturation");
        enableNightVision=tags.getBoolean("enableNightvision");
        enableSafemode=tags.getBoolean("enableSafemode");
        enableHovermode=tags.getBoolean("enableHovermode");
        enableJetpack=tags.getBoolean("enableJetpack");
        
        if (!enableSafemode && !TGConfig.allowUnsafemode(player)){
        	enableSafemode = true;
        }
        showTGHudElements = tags.getBoolean("showHud");
        enableStepAssist = tags.getBoolean("stepAssist");
	}

	@Override
	public void init(Entity entity, World world) {

	}

	
	/**
	 * Drops to world (player death)
	 */
	public void dropInventory(){
		if(!this.player.worldObj.isRemote){
			if (!this.player.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory"))
	        {
	            //this.inventory.dropAllItems();
	            int i;
	        
	            this.player.captureDrops=true;
	            for (i = 0; i < this.TG_inventory.inventory.length; ++i)
	            {
	                if (this.TG_inventory.inventory[i] != null)
	                {
	                	//System.out.println("Dropping "+TG_inventory.inventory[i].getDisplayName()+" x"+TG_inventory.inventory[i].stackSize);
	                    this.player.func_146097_a(this.TG_inventory.inventory[i], true, false);
	                    this.TG_inventory.inventory[i] = null;
	                }
	            }
	            this.player.captureDrops=false;
	         
	        }
		}
	}

	public boolean isJumpkeyPressed() {
		return isJumpkeyPressed;
	}

	public void setJumpkeyPressed(boolean isJumpkeyPressed) {
		/*if(this.player.worldObj.isRemote){
			if(isJumpkeyPressed==true && jetPackLoop==null){
				jetPackLoop = new TGSound("techguns:jetpack.loop", this.player, 1.0f, 1.0f, true, true, false);
				SoundUtil.playSoundAtEntityPos(this.player.worldObj, this.player, "techguns:jetpack.start", 1.0f, 1.0f, false);
			}
			
			if(isJumpkeyPressed && !this.isJumpkeyPressed){
				Minecraft.getMinecraft().getSoundHandler().playSound(jetPackLoop);
			} else if ( !isJumpkeyPressed && this.isJumpkeyPressed){
				if(jetPackLoop!=null){
					jetPackLoop.setDonePlaying();
					jetPackLoop=null;
					SoundUtil.playSoundAtEntityPos(this.player.worldObj, this.player, "techguns:jetpack.stop", 1.0f, 1.0f, false);
				}
			}
			
			
		}*/
		this.isJumpkeyPressed = isJumpkeyPressed;
	}
	
	
}
