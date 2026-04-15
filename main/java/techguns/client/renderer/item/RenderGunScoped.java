package techguns.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import techguns.client.ClientProxy;
import techguns.client.models.ModelGunAmmo;
import techguns.items.armors.ICamoChangeable;
import techguns.items.guns.GenericGun;

public abstract class RenderGunScoped extends RenderGun {
	
	
	public RenderGunScoped(ModelBase model, ResourceLocation texture,
			float scale, float x, float y, float z, boolean handleDamageValue, boolean handleProgress) {
		super(model, texture, scale, x, y, z, handleDamageValue, handleProgress);
	}

	public RenderGunScoped(ModelBase model, ResourceLocation texture,
			float scale, float x, float y, float z) {
		super(model, texture, scale, x, y, z);
	}

	private boolean isZooming(){
		return ClientProxy.get().player_zoom!=1.0f;
	}
	
	public abstract void renderScope();
	
	/* (non-Javadoc)
	 * @see techguns.client.renderer.item.RenderGun#renderItem(net.minecraftforge.client.IItemRenderer.ItemRenderType, net.minecraft.item.ItemStack, java.lang.Object[])
	 */
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
//		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
//		
//	}
	//muzzleflash
	boolean showMuzzleFX = false;
	float muzzleFXprogress = 0;
	
	boolean renderScope=false;

//	if (GenericGun.player_recoiltime > 0) {
//		GL11.glPushMatrix();
//		GL11.glRotatef(45, 0, 1.0f, 0);
//		GL11.glRotatef(90, 0, 0, 1.0f);
//		GL11.glTranslatef(0, -1.0f, 0);
//		drawMuzzleFX(0.5f);
//		GL11.glPopMatrix();
//	}
	ClientProxy cp = ClientProxy.get();
	GL11.glPushMatrix();
	
	float reloadProgress =10.0f; // A VALUE > 1.0f and outside of [0-1]
	if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
		
		float prog =0.0f;
		if (this.handleProgress){
			int dur = Minecraft.getMinecraft().thePlayer.getItemInUseDuration();
			//TODO correct maxdur
			int maxdur = 20; //item.getMaxItemUseDuration();
			//System.out.println("dur:"+dur+" maxDur:"+maxdur);
			//System.out.println("X:"+(maxdur-dur));
			prog = dur / (maxdur*1.0f);
			//System.out.println("Prog:"+prog);
			if (prog < 0.0f) {
				prog = 0.0f;
			} else if (prog > 1.0f) {
				prog = 1.0f;
			}
		}
		this.GL_Transform_FirstPerson(prog);
		
		//Reloading
		if (cp.getplayerReloadtime(ClientProxy.get().getPlayerClient()) >0) {
		
			long diff = cp.getplayerReloadtime(ClientProxy.get().getPlayerClient()) - System.currentTimeMillis();

			if (diff <= 0) {
				//ClientProxy.setplayerReloadtime(0);
				//ClientProxy.setplayerReloadtimeTotal(0);
				cp.setplayerReloadtime(ClientProxy.get().getPlayerClient(),0, 0, (byte)0);
				
				this.GL_Transform_reload_done();

			}else {

				long reloadtimeTotal = cp.getplayerReloadtimeTotal();
				reloadProgress = 1.0f-((float)diff / (float)reloadtimeTotal);
				
				this.GL_Transform_reloading(reloadProgress);

			}
		}else if (cp.getplayerRecoiltime(ClientProxy.get().getPlayerClient()) > 0) {
			
			if (!isZooming()){
				long diff = cp.getplayerRecoiltime(ClientProxy.get().getPlayerClient()) - System.currentTimeMillis();
				//System.out.println("firing: "+diff);
				if (diff <= 0 || diff > cp.getplayerRecoiltimeTotal()) {
					//System.out.println("firing done");
					//ClientProxy.setplayerRecoiltime(0);
					//ClientProxy.setplayerRecoiltimeTotal(0);
					cp.setplayerRecoiltime(ClientProxy.get().getPlayerClient(),0, 0, (byte)0);
					this.GL_Transform_Translate();
				}else{
					
					float fireProgress = 1.0f-((float)diff / (float)cp.getplayerRecoiltimeTotal());		
					this.GL_Transform_firing(fireProgress);
				}			
			} else {
				renderScope=true;
			}
		}else {
			if(!isZooming()){
			this.GL_Transform_Translate();
			showMuzzleFX = false;
			//GL11.glTranslatef(0.1f, 0, -0.5f);
			} else {
				renderScope=true;
			}
		}
		
		if (cp.player_muzzleFlashtime > 0) {
			long diff = cp.player_muzzleFlashtime - System.currentTimeMillis();
			if (diff <= 0 || diff > cp.player_muzzleFlashtime_total) {
				cp.player_muzzleFlashtime = 0;
				cp.player_muzzleFlashtime_total = 0;
			}else{			
				showMuzzleFX = !isZooming();//true;
				muzzleFXprogress = 1.0f-((float)diff / (float)cp.player_muzzleFlashtime_total);
			}			
		}
		
	} else if (type == ItemRenderType.EQUIPPED){
		
		this.GL_Transform_Equipped();
		
		EntityLivingBase shooter = (EntityLivingBase) data[1];
		if (cp.getplayerRecoiltime(shooter) > 0) {
			long diff = cp.getplayerRecoiltime(shooter) - System.currentTimeMillis();
			//System.out.println("firing: "+diff);
			if (diff <= 0 || diff > cp.getplayerRecoiltimeTotal(shooter)) {
				//System.out.println("firing done");
				//ClientProxy.setplayerRecoiltime(shooter,0);
				//ClientProxy.setplayerRecoiltimeTotal(shooter,0);
				cp.setplayerRecoiltime(shooter, 0, 0, (byte)0);
				//this.GL_Transform_Translate();
			}else{
				
				float fireProgress = 1.0f-((float)diff / (float)cp.getplayerRecoiltimeTotal(shooter));
				this.GL_TransformRecoil_Equipped(fireProgress);
				//this.GL_Transform_firing(fireProgress);
			}	
		}
		
	} else if (type == ItemRenderType.ENTITY){
		
		this.GL_Transform_Ground();
		
	} else if (type == ItemRenderType.INVENTORY){
		
		this.GL_Transform_Icon();
	}
	
	if (!renderScope){
		byte camo=0;
		if(item.getItem() instanceof ICamoChangeable){
			ICamoChangeable camoitem = (ICamoChangeable) item.getItem();
			camo=(byte) camoitem.getCurrentCamoIndex(item);
		}
		ResourceLocation tex= ((GenericGun) item.getItem()).textures.get(camo);
		
		//System.out.println("texture->:"+tex);
		Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
		if (this.handleDamageValue){
			((ModelGunAmmo) model).render(null, 0, 0, 0, 0, 0, 0.0625F,item.getMaxDamage()-item.getItemDamage(),reloadProgress, type);
		} else {
			model.render(null, 0, 0, 0, 0, 0, 0.0625F );
		}
		GL11.glPopMatrix();
	} else {
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glRotatef(45, 0, 1.0f, 0);
		GL11.glRotatef(90, 0, 0, 1.0f);
		//GL11.glTranslatef(1.7f, 0f, -0.7f);
		//up/down, ? , ?
		
		//GL11.glTranslatef(1.80f, 0f, -0.72f);
		//ScreenEffect.muzzleFlash.doRender(0.0f, 0, 0, 0, 1.0f);
		//ScreenEffect.sniperScope.doRender(0.0f, 0,0,0, 2.25f);
		renderScope();
		
		GL11.glPopMatrix();
	}
			
	//GL11.glPopMatrix();
	
	if (showMuzzleFX) {
		GL11.glPushMatrix();
		GL11.glRotatef(45, 0, 1.0f, 0);
		GL11.glRotatef(90, 0, 0, 1.0f);
		GL11.glTranslatef(1.5f, -1.0f, -0.5f);
		drawMuzzleFX(muzzleFXprogress);
		GL11.glPopMatrix();
	}
	}

	

}
