package techguns;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import techguns.client.audio.TGSoundCategory;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.items.guns.GenericGun;


public class CommonProxy {

        // Client stuff
        public void registerRenderers() {
                // Nothing here as the server doesn't render graphics or entities!
        }
        
        @Deprecated
        public void spawnParticle(String name, World world, double posX, double posY, double posZ, double MotionX, double MotionY, double MotionZ) {
        	
        }
        
        public void setGunTextures(GenericGun gun, String path, int variations){
        	
        }
        
        public void loadFXList(){};
        
        public void createFX(String name, World world, double posX, double posY, double posZ, double MotionX, double MotionY, double MotionZ){}
        
        public void createFXOnEntity(String name, Entity ent){}
        public int[] createFXOnEntity_ID(String name, Entity ent){return null;}
        
//        public ArrayList<Entity> getFXOnEntity(String name, Entity ent){
//			return null;}
//        
        
        public void initChisel(){	
        }
        
        /**
         * Gets the local player on client, else null
         * @return
         */
        public EntityPlayer getPlayerClient(){
        	return null;
        }
        
        public void RegisterClientHandlers(){
        	
        }
        
        public void setHasStepassist(boolean value){};
        
        public void setHasNightvision(boolean value){};
        
        public void setFlySpeed(float value){};
        
        public boolean getHasStepassist(){
        	return false;
        };
        
        public boolean getHasNightvision(){
        	return false;
        };
   
        public void handleSoundEvent(EntityPlayer ply, int entityId, String soundname, float volume, float pitch, boolean repeat, boolean moving, boolean gunPosition, boolean playOnOwnPlayer, TGSoundCategory soundCategory){
        	
        }
        public void playSoundOnEntity(Entity ent, String soundname, float volume, float pitch, boolean repeat, boolean moving, boolean gunPosition, TGSoundCategory category){
        	
        }
        
        public void playSoundOnPosition(String soundname, int posx, int posy, int posz, float volume, float pitch, boolean repeat, TGSoundCategory soundCategory) {
		}
        
        public void handlePlayerGliding(TechgunsExtendedPlayerProperties props, EntityPlayer player){
        	
        }
        
        public TechgunsExtendedPlayerProperties registerExtendedProperties(EntityPlayer ply){
        	return TechgunsExtendedPlayerProperties.register(ply);
        }
        
        public boolean isClientPlayerAndIn1stPerson(EntityLivingBase ent){
        	return false;
        }
        
		public boolean clientInRangeSquared(double posX, double posZ, double distSq) {
			return false;
		}
		
		public void setFluidIcons(Fluid f){
			
		};
		
		public void postInit(){
			
		};
}