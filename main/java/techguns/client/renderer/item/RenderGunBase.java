package techguns.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import techguns.client.ClientProxy;
import techguns.client.models.ModelMultipart;
import techguns.debug.Keybinds;
import techguns.entities.npc.GenericNPC;
import techguns.entities.npc.NPCTurret;
import techguns.items.armors.ICamoChangeable;
import techguns.items.guns.GenericGun;
import techguns.items.guns.GenericGunCharge;

public abstract class RenderGunBase implements IItemRenderer {

	ResourceLocation texture;
	ModelMultipart model;
	float scale; //uniform scale
	float x; //offset
	float y; //offset
	float z; //offset
	boolean handleDamageValue=false;
	boolean handleProgress=false;
	boolean hasZoom=false;
	int parts;
	
	public RenderGunBase(ModelMultipart model, ResourceLocation texture,
			float scale, float x, float y, float z, boolean handledamage, boolean handleprogress, int parts,boolean hasZoom) {
		this.parts = parts;
		this.model = model;
		this.texture = texture;
		this.scale = scale;
		this.x = x;
		this.y = y;
		this.z = z;
		handleDamageValue=handledamage;
		handleProgress=handleprogress;
		this.hasZoom=hasZoom;
	}

	private boolean isZooming(){
		return this.hasZoom && ClientProxy.get().player_zoom!=1.0f;
	}
	
	public abstract void renderScope();
	
	/**
	 * @param part - part to be rendered, only interesting for multipart models
	 * @param prog - progress, [0.0f-1.0f]
	 * @param attackType
	 */
	protected abstract void GL_Transform_FirstPerson(int part,float prog, byte attackType);
	/**
	 * @param part - part to be rendered, only interesting for multipart models
	 * @param prog - progress, [0.0f-1.0f]
	 * @param attackType
	 */
	protected abstract void GL_Transform_Equipped(int part,float prog, byte attackType);
	/**
	 * @param part - part to be rendered, only interesting for multipart models
	 */
	protected abstract void GL_Transform_Ground(int part);
	/**
	 * @param part - part to be rendered, only interesting for multipart models
	 */
	protected abstract void GL_Transform_Icon(int part);
	/**
	 * @param part - part to be rendered, only interesting for multipart models
	 * @param prog - progress, [0.0f-1.0f]
	 * @param attackType
	 */
	protected abstract void GL_Transform_reloading(int part,float prog, byte attackType);
	/**
	 * @param part - part to be rendered, only interesting for multipart models
	 */
	//protected abstract void GL_Transform_reload_done(int part);
	/**
	 * @param part - part to be rendered, only interesting for multipart models
	 * @param attackType
	 */
	protected abstract void GL_Transform_firing(int part, float prog, byte attackType);
	/**
	 * @param part - part to be rendered, only interesting for multipart models
	 */
	protected abstract void GL_Transform_Translate(int part);
	/**
	 * @param prog - progress, [0.0f-1.0f]
	 * @param attackType TODO
	 */
	protected abstract void drawMuzzleFX(float prog, byte attackType);
	/**
	 * @param part - part to be rendered, only interesting for multipart models
	 * @param prog - progress, [0.0f-1.0f]
	 * @param attackType TODO
	 */
	protected void GL_TransformRecoil_Equipped(int part, float prog, byte attackType){
		if(prog<0.3f){
			GL11.glRotatef(prog/0.3f*-30, 0, 0, 1);
		}
	}
	
	/**
	 * @param part - part to be rendered, only interesting for multipart models
	 * @param prog - progress, [0.0f-1.0f]
	 * @param attackType TODO
	 */
	protected abstract void GL_TransformReload_Equipped(int part, float prog, byte attackType);
	/**
	 * @param part TODO
	 * @param part - part to be rendered, only interesting for multipart models
	 */
	//protected abstract void GL_Transform_reload_done_Equipped(int part);
	
	
	protected void transformEquippedAimedRotation(int part){
		//GL11.glRotated(90.0f-21.0f, 1, 0, 0);
				//GL11.glRotated(90.0f-21.0f, 1, 0, 0);
		GL11.glRotated(-45, 1, 0, 0);	
		GL11.glRotated(-18, 0, 1, 0);	
		GL11.glRotated(45, 0, 0, 1);
		//GL11.glTranslated(0,0,-0.45f);
		GL11.glRotatef(45, 0, 1, 0);
		this.transformEquippedAimed(part);
		GL11.glRotatef(-45, 0, 1, 0);
				//GL11.glRotated(Keybinds.Z, 0, 0, 1);		
	}
	
	protected void transformEquippedAimed(int part){
		GL11.glTranslatef(0f, -1.3f, -0.4f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
	}
	/*protected void transformRotateAimed(int part){
		GL11.glRotated(90.0f-21.0f, 1, 0, 0);	
	}*/
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		//muzzleflash
		boolean showMuzzleFX = false;
		float muzzleFXprogress = 0;
		boolean renderScope=false;
		boolean reloading=false;
		
		ClientProxy cp = ClientProxy.get();
		
		float prog =0.0f; //fire progress
		
		byte attackType=0;
		
		for (int i=0; i<this.parts; i++){
			prog=0.0f;
			attackType=0;
			
			GL11.glPushMatrix();
			
			
			float reloadProgress =10.0f; // A VALUE > 1.0f and outside of [0-1]
			if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
				attackType = cp.getplayerAttackType((EntityLivingBase) data[1]);
				//System.out.println("AttackType:"+attackType);
				
				if (this.handleProgress && Minecraft.getMinecraft().thePlayer.getItemInUse()!=null){
					int dur = Minecraft.getMinecraft().thePlayer.getItemInUseDuration();
					//System.out.println("dur:"+dur+" maxDur:"+maxdur);
					//System.out.println("X:"+(maxdur-dur));
					if(item.getItem() instanceof GenericGunCharge){
						prog = dur / ((GenericGunCharge)item.getItem()).fullChargeTime;
					} else {
						prog = dur / (20*1.0f);
					}
					
					if (prog < 0.0f) {
						prog = 0.0f;
					} else if (prog > 1.0f) {
						prog = 1.0f;
					}
//				} else if(!((GenericGun)item.getItem()).isShootWithLeftClick()){
//					long swingdiff = cp.getplayerRecoiltime(ClientProxy.get().getPlayerClient()) - System.currentTimeMillis();
//					if(swingdiff<0){
//						prog =0.0f;
//					} else if(cp.getplayerRecoiltimeTotal()!=0) {
//						prog=(swingdiff*1.0f)/(cp.getplayerRecoiltimeTotal()*1.0f);
//						if(prog>1.0f){
//							prog=1.0f;
//						}
//					}
//					//System.out.println("prog:"+prog);
				}
				this.GL_Transform_FirstPerson(i,prog, attackType);
				
				//Reloading
				if (cp.getplayerReloadtime(ClientProxy.get().getPlayerClient()) >0) {
				
					long diff = cp.getplayerReloadtime(ClientProxy.get().getPlayerClient()) - System.currentTimeMillis();
	
					if (diff <= 0) {
						//ClientProxy.setplayerReloadtime(0);
						//ClientProxy.setplayerReloadtimeTotal(0);
						cp.setplayerReloadtime(ClientProxy.get().getPlayerClient(),0, 0, (byte)0);
						
						//this.GL_Transform_reload_done(i);
						this.GL_Transform_Translate(i);
	
					}else {
	
						long reloadtimeTotal = cp.getplayerReloadtimeTotal();
						reloadProgress = 1.0f-((float)diff / (float)reloadtimeTotal);
						
						this.GL_Transform_reloading(i,reloadProgress, attackType);
	
					}
				}else if (cp.getplayerRecoiltime(ClientProxy.get().getPlayerClient()) > 0) {
					
					if (!isZooming()){
					
						long diff = cp.getplayerRecoiltime(ClientProxy.get().getPlayerClient()) - System.currentTimeMillis();
						//System.out.println("firing: "+diff);
						if (diff <= 0 || diff > cp.getplayerRecoiltimeTotal()) {
							//System.out.println("firing done");
							//ClientProxy.setplayerRecoiltime(0);
							//ClientProxy.setplayerRecoiltimeTotal(0);
							cp.setplayerRecoiltime(ClientProxy.get().getPlayerClient(), 0, 0, (byte)0);
							this.GL_Transform_Translate(i);
						}else{
							
							float fireProgress = 1.0f-((float)diff / (float)cp.getplayerRecoiltimeTotal());		
							this.GL_Transform_firing(i,fireProgress, attackType);
							prog=fireProgress;
						}			
					} else {
						renderScope=true;
					}
				}else {
					if(!isZooming()){
						this.GL_Transform_Translate(i);
						showMuzzleFX = false;
					} else {
						renderScope=true;
					}
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
				EntityLivingBase shooter = (EntityLivingBase) data[1];
				attackType = cp.getplayerAttackType(shooter);
					//float progress=0.0f;
					prog=0.0f;
					if (cp.getplayerReloadtime(shooter) > 0){
						
						long diff = cp.getplayerReloadtime(shooter) - System.currentTimeMillis();
						
						if (diff <= 0) {
							//ClientProxy.setplayerReloadtime(shooter,0);
							//ClientProxy.setplayerReloadtimeTotal(shooter,0);
							cp.setplayerReloadtime(shooter, 0, 0,(byte)0);
							
							//this.GL_Transform_Translate(i);
		
						}else {
		
							long reloadtimeTotal = cp.getplayerReloadtimeTotal(shooter);
							reloadProgress = 1.0f-((float)diff / (float)reloadtimeTotal);
							reloading=true;
							//this.GL_TransformReload_Equipped(i,reloadProgress);
		
						}
						
					} else if( cp.getplayerRecoiltime(shooter) > 0) {
						long diff = cp.getplayerRecoiltime(shooter) - System.currentTimeMillis();
						//System.out.println("firing: "+diff);
						if (diff <= 0 || diff > cp.getplayerRecoiltimeTotal(shooter)) {
							//System.out.println("firing done");
							//ClientProxy.setplayerRecoiltime(shooter, 0);
							//ClientProxy.setplayerRecoiltimeTotal(shooter, 0);
							cp.setplayerRecoiltime(shooter, 0, 0, (byte)0);
							
							//this.GL_Transform_Translate(i);
						}else{

							prog = 1.0f-((float)diff / (float)cp.getplayerRecoiltimeTotal(shooter));
							
							
							//System.out.println("FireProgress"+prog);
							//this.GL_Transform_firing(i,fireProgress);
						}
					}
					boolean aimed = shooter instanceof EntityPlayer || (shooter instanceof GenericNPC  && ((GenericNPC)shooter).getHasAimedBowAnim() ) ;
					if(aimed){
						this.transformEquippedAimedRotation(i);
					}
					
					this.GL_Transform_Equipped(i,prog, attackType);
					
					/*if(aimed){
						this.transformRotateAimed(i);
					}*/
					
					//no recoil for turrets
					if ((shooter instanceof NPCTurret)){
						prog=0.0f;
					}
					if(reloading){
						this.GL_TransformReload_Equipped(i,reloadProgress, attackType);
					} else {
						this.GL_TransformRecoil_Equipped(i,prog, attackType);
					}
					
					
								
			} else if (type == ItemRenderType.ENTITY){
				
				this.GL_Transform_Ground(i);
				
			} else if (type == ItemRenderType.INVENTORY){
				
				this.GL_Transform_Icon(i);
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
					model.render(null, 0, 0, 0, 0, 0, 0.0625F,((GenericGun)item.getItem()).getCurrentAmmo(item),reloadProgress, type,i, prog);
				} else {
					model.render(null, 0, 0, 0, 0, 0, 0.0625F );
				}
				
				GL11.glPopMatrix();
				
				if(i==this.parts-1){
					if (showMuzzleFX) {
						GL11.glPushMatrix();
						GL11.glRotatef(45, 0, 1.0f, 0);
						GL11.glRotatef(90, 0, 0, 1.0f);
						//GL11.glTranslatef(1.7f, 0f, -0.7f);
						GL11.glTranslatef(1.5f, -1.0f, -0.5f);
						drawMuzzleFX(muzzleFXprogress, attackType);
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
				
			} else {
				GL11.glPopMatrix();
				
				//SCOPE ONLY RENDERED ONCE
				if (i==0){
					GL11.glPushMatrix();
					GL11.glRotatef(45, 0, 1.0f, 0);
					GL11.glRotatef(90, 0, 0, 1.0f);
					//GL11.glTranslatef(1.7f, 0f, -0.7f);
					//up/down, ? , ?
					
					
					if (cp.getplayerRecoiltime(ClientProxy.get().getPlayerClient()) > 0) {
						
					
						long diff = cp.getplayerRecoiltime(ClientProxy.get().getPlayerClient()) - System.currentTimeMillis();
						//System.out.println("firing: "+diff);
						if (diff <= 0 || diff > cp.getplayerRecoiltimeTotal()) {
							//System.out.println("firing done");
							//ClientProxy.setplayerRecoiltime(0);
							//ClientProxy.setplayerRecoiltimeTotal(0);
							//cp.setplayerRecoiltime(ClientProxy.get().getPlayerClient(), 0, 0, (byte)0);
							//this.GL_Transform_Translate(i);
						}else{
							
							float fireProgress = 1.0f-((float)diff / (float)cp.getplayerRecoiltimeTotal());
							
							//this.GL_Transform_firing(i,fireProgress);
							float recoil;
							if (fireProgress<0.2f){
								recoil = fireProgress*5f;
							} else {
								recoil = 1-(fireProgress-0.2f)*1.25f;
							}
							//X -> up/down, Y-> back/front, z = right/left
							//System.out.println("Recoiling:"+recoil);
							this.translateScopeRecoil(recoil);
						}			
					
					}

					//GL11.glTranslatef(1.80f, 0f, -0.72f);
					//ScreenEffect.muzzleFlash.doRender(0.0f, 0, 0, 0, 1.0f);
					//ScreenEffect.sniperScope.doRender(0.0f, 0,0,0, 2.25f);
					renderScope();
					
					GL11.glPopMatrix();
				}
			}
			/*Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

			model.render(null, 0, 0, 0, 0, 0, 0.0625F, item.getMaxDamage()-item.getItemDamage(),reloadProgress, type,i );
			*/		
			//GL11.glPopMatrix();
		}

	}

	
	public void translateScopeRecoil(float recoil){
		GL11.glTranslatef(0.2f*recoil, 0.25f*recoil, 0.10f*recoil);
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

	protected void drawExtraEffects() {
		//null
	}
}
