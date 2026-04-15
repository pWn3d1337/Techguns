package techguns.client.renderer.additionalslots;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.TGItems;
import techguns.TGuns;
import techguns.client.models.ModelAntiGravPack;
import techguns.client.models.ModelFaceMask;
import techguns.client.models.ModelGasMask;
import techguns.client.models.ModelGlider;
import techguns.client.models.ModelGoldenRevolver;
import techguns.client.models.ModelJetPack;
import techguns.client.models.ModelMac10;
import techguns.client.models.ModelMibGun;
import techguns.client.models.ModelNightVisionGoggles;
import techguns.client.models.ModelNightVisionGogglesOff;
import techguns.client.models.ModelOxygenFlasks;
import techguns.client.models.ModelPistol3;
import techguns.client.models.ModelRevolver;

public abstract class RenderAdditionalSlotItem {
	private static ResourceLocation[] gasmaskTextures = {new ResourceLocation("techguns:textures/armor/gasMask.png")};
	private static ResourceLocation[] oxygenmaskTextures = {new ResourceLocation("techguns:textures/armor/oxygenMask.png")};
	private static ResourceLocation[] tacticalmaskTextures = {new ResourceLocation("techguns:textures/armor/tacticalMask.png"), new ResourceLocation("techguns:textures/armor/tacticalMask_green.png"), new ResourceLocation("techguns:textures/armor/tacticalMask_white.png"), new ResourceLocation("techguns:textures/armor/tacticalMask_lightgreen.png")};
	private static ResourceLocation[] jetpackTextures = {new ResourceLocation("techguns:textures/armor/jetPack.png"), new ResourceLocation("techguns:textures/armor/jetPack_bronze.png"), new ResourceLocation("techguns:textures/armor/jetPack_black.png"), new ResourceLocation("techguns:textures/armor/jetPack_darkgreen.png")};
	private static ResourceLocation[] antiGravPackTextures = {new ResourceLocation("techguns:textures/armor/AntiGravPack.png"), new ResourceLocation("techguns:textures/armor/AntiGravPack_bronze.png"), new ResourceLocation("techguns:textures/armor/AntiGravPack_black.png"), new ResourceLocation("techguns:textures/armor/AntiGravPack_darkGreen.png"), new ResourceLocation("techguns:textures/armor/AntiGravPack_silver.png")};
	
	
	private static RenderAdditionalArmorModel renderGasMask = new RenderAdditionalArmorModel(gasmaskTextures, new ModelGasMask());
	//private static RenderGasMask renderGasMask2 = new RenderGasMask(new ResourceLocation("techguns:textures/armor/gasMask.png"), new ModelGasMask2());
	private static RenderGlider renderGlider = new RenderGlider(new ResourceLocation[]{new ResourceLocation("techguns:textures/armor/glider.png")}, new ModelGlider());
	private static RenderNightVisionGoggles renderNightVisionGoggles = new RenderNightVisionGoggles(new ResourceLocation("techguns:textures/armor/nightVisionGoggles.png"), new ModelNightVisionGoggles(),new ModelNightVisionGogglesOff());
	private static RenderJetpack renderJetpack = new RenderJetpack(jetpackTextures,new ModelJetPack(0));
	private static RenderJetpack renderJumppack = new RenderJetpack(jetpackTextures,new ModelJetPack(1));
	private static RenderOxygenFlasks renderOxygenFlasks = new RenderOxygenFlasks(new ResourceLocation[]{new ResourceLocation("techguns:textures/armor/oxygenFlasks.png")}, new ModelOxygenFlasks());
	private static RenderOxygenFlasks renderAntiGravPack = new RenderOxygenFlasks(antiGravPackTextures, new ModelAntiGravPack());
	
	private static RenderAdditionalArmorModel renderOxygenMask = new RenderAdditionalArmorModel(oxygenmaskTextures, new ModelFaceMask(true));
	private static RenderAdditionalArmorModel renderTacticalMask = new RenderAdditionalArmorModel(tacticalmaskTextures, new ModelFaceMask(true));
	
	private static RenderAkimboGun renderAkimboPistols = new RenderAkimboGun(new ModelPistol3(), new ResourceLocation("techguns","textures/guns/pistol3.png"),2);
	private static RenderAkimboGun renderAkimboRevolver = new RenderAkimboRevolver(new ModelRevolver(), new ResourceLocation("techguns","textures/guns/revolver.png"),1);
	
	private static RenderAkimboGun renderAkimboGoldenRevolver = new RenderAkimboRevolver(new ModelGoldenRevolver(), new ResourceLocation("techguns","textures/guns/goldenrevolver.png"),1);
	private static RenderAkimboGun renderAkimboMac10 = new RenderAkimboMac10(new ModelMac10(), new ResourceLocation("techguns","textures/guns/mac10texture.png"), 1);
	
	private static RenderAkimboGun renderAkimboMibGun = new RenderAkimboMibGun(new ModelMibGun(), new ResourceLocation("techguns","textures/guns/mibgun.png"), 1);
	
	
	public abstract void render(ItemStack item, EntityPlayer entityPlayer,RenderPlayer renderplayer, float partialTickTime, boolean hideSlot);
	/**
	  * Copied from RenderLivingBase
	  * 
    * Returns a rotation angle that is inbetween two other rotation angles. par1 and par2 are the angles between which
    * to interpolate, par3 is probably a float between 0.0 and 1.0 that tells us where "between" the two angles we are.
    * Example: par1 = 30, par2 = 50, par3 = 0.5, then return = 40
    */
   public static float interpolateRotation(float par1, float par2, float partialTickTime)
   {
       float f3;

       for (f3 = par2 - par1; f3 < -180.0F; f3 += 360.0F)
       {
           ;
       }

       while (f3 >= 180.0F)
       {
           f3 -= 360.0F;
       }

       return par1 + partialTickTime * f3;
   }
   
   public static double interp(double posX, double prevPosX, double partialTickTime)
   {
       double f3 = prevPosX - posX;
       return posX + partialTickTime * f3;
   }
   
   public static RenderAdditionalSlotItem getRenderForItem(ItemStack item){
	   if (item!=null){
		   
		   if(item.getItem() == TGItems.gasMask){
			   if(item.getItemDamage()<item.getMaxDamage()){
				   return renderGasMask;
			   }   
		   } else if (item.getItem() == TGItems.sharedItem){
			   if(item.getItemDamage()==TGItems.glider.getItemDamage()){
				   //return renderJetpack;
				   return renderGlider;
			   } else if (item.getItemDamage() == TGItems.oxygenMask.getItemDamage()){
				   return renderOxygenMask;
			   }
		   } else if (item.getItem()==TGItems.nightVisionGoggles){
			   return renderNightVisionGoggles;
		   } else if (item.getItem()==TGItems.jetpack){
			   return renderJetpack;
		   } else if (item.getItem()==TGItems.jumppack){
			   return renderJumppack;
		   } else if (item.getItem() == TGItems.scubaTanks){
			   return renderOxygenFlasks;
		   } else if (item.getItem() == TGItems.tacticalMask){
			   return renderTacticalMask;
		   } else if (item.getItem() == TGItems.antiGravPack ){
			   return renderAntiGravPack;
		   } else if (item.getItem() == TGuns.akimbo_pistol){
			   return renderAkimboPistols;
		   } else if (item.getItem() == TGuns.akimbo_revoler){
			   return renderAkimboRevolver;
		   } else if (item.getItem() == TGuns.akimbo_goldenrevolver){
			   return renderAkimboGoldenRevolver;
		   } else if (item.getItem() == TGuns.akimbo_mac10){
			   return renderAkimboMac10;
		   } else if (item.getItem() == TGuns.akimbo_mibGun){
			   return renderAkimboMibGun;
		   }
	   }
	   return null;
   }
   
  /* protected void applyTranslationAndRotation(EntityPlayer self, EntityPlayer entityPlayer,float partialTickTime, boolean flipX){
	    float offset=0.0f;
		if(entityPlayer!=self){
			offset=1.6225f;
		}
		GL11.glTranslated(-interp(self.prevPosX,self.posX, partialTickTime)+interp(entityPlayer.prevPosX, entityPlayer.posX,partialTickTime),
				-interp(self.prevPosY,self.posY, partialTickTime)+interp(entityPlayer.prevPosY, entityPlayer.posY,partialTickTime)+offset,
				-interp(self.prevPosZ,self.posZ, partialTickTime)+interp(entityPlayer.prevPosZ, entityPlayer.posZ,partialTickTime));
		if(flipX){
			GL11.glRotatef(180.0f, 1, 0, 0);
		}
   }*/
}
