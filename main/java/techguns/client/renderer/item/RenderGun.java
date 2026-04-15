package techguns.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import techguns.client.ClientProxy;
import techguns.client.models.ModelGunAmmo;
import techguns.items.armors.ICamoChangeable;
import techguns.items.guns.GenericGun;

public abstract class RenderGun implements IItemRenderer{
	
	ResourceLocation texture;
	ModelBase model;
	float scale; //uniform scale
	float x; //offset
	float y; //offset
	float z; //offset
	protected boolean handleDamageValue;
	protected boolean handleProgress;
	
	public RenderGun(ModelBase model, ResourceLocation texture, float scale, float x, float y, float z) {
		this.model = model;
		this.texture = texture;
		this.scale = scale;
		this.x = x;
		this.y = y;
		this.z = z;
		handleDamageValue=false;
		handleProgress=false;
	}
	
	public RenderGun(ModelBase model, ResourceLocation texture, float scale, float x, float y, float z, boolean handleDamageValue, boolean handleProgress) {
		this.model = model;
		this.texture = texture;
		this.scale = scale;
		this.x = x;
		this.y = y;
		this.z = z;
		this.handleDamageValue = handleDamageValue;
		this.handleProgress = handleProgress;
	}

	protected abstract void GL_Transform_FirstPerson(float prog);
	protected abstract void GL_Transform_Equipped();
	protected abstract void GL_Transform_Ground();
	protected abstract void GL_Transform_Icon();
	protected abstract void GL_Transform_reloading(float prog);
	protected abstract void GL_Transform_reload_done();
	protected abstract void GL_Transform_firing(float prog);
	protected abstract void GL_Transform_Translate();
	protected abstract void drawMuzzleFX(float prog);
	protected void GL_TransformRecoil_Equipped(float prog){
		if(prog<0.3f){
			GL11.glRotatef(prog/0.3f*-30, 0, 0, 1);
		}
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
//		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
//			
//		}
		//muzzleflash
		boolean showMuzzleFX = false;
		float muzzleFXprogress = 0;
		
//		if (GenericGun.player_recoiltime > 0) {
//			GL11.glPushMatrix();
//			GL11.glRotatef(45, 0, 1.0f, 0);
//			GL11.glRotatef(90, 0, 0, 1.0f);
//			GL11.glTranslatef(0, -1.0f, 0);
//			drawMuzzleFX(0.5f);
//			GL11.glPopMatrix();
//		}
		
		//System.out.println(type.toString());
		ClientProxy cp = ClientProxy.get();
		
		
		GL11.glPushMatrix();
		GL11.glScalef(scale, scale, scale);
		
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
				
			}else {
				this.GL_Transform_Translate();
				showMuzzleFX = false;
				//GL11.glTranslatef(0.1f, 0, -0.5f);
			}
			
			if (cp.player_muzzleFlashtime > 0) {
				long diff = cp.player_muzzleFlashtime - System.currentTimeMillis();
				if (diff <= 0 || diff > cp.player_muzzleFlashtime_total) {
					cp.player_muzzleFlashtime = 0;
					cp.player_muzzleFlashtime_total = 0;
				}else{			
					showMuzzleFX = true;
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
					//this.GL_Transform_firing(fireProgress);
					this.GL_TransformRecoil_Equipped(fireProgress);
				}	
			}
			
		} else if (type == ItemRenderType.ENTITY){
			
			this.GL_Transform_Ground();
			
		} else if (type == ItemRenderType.INVENTORY){
			
			this.GL_Transform_Icon();
		}
		
		
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
		
		if (showMuzzleFX) {
			GL11.glPushMatrix();
			GL11.glRotatef(45, 0, 1.0f, 0);
			GL11.glRotatef(90, 0, 0, 1.0f);
			//GL11.glTranslatef(1.7f, 0f, -0.7f);
			GL11.glTranslatef(1.5f, -1.0f, -0.5f);
			drawMuzzleFX(muzzleFXprogress);
			GL11.glPopMatrix();
		}
		
		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			//Show Additional effects (first person):
			GL11.glPushMatrix();
			GL11.glRotatef(45, 0, 1.0f, 0);
			GL11.glRotatef(90, 0, 0, 1.0f);
			GL11.glTranslatef(1.5f, -1.0f, -0.5f);
			drawExtraEffects();
			GL11.glPopMatrix();
		}
		
	}
	
	protected void drawExtraEffects() {
		//null
	}

}
