package techguns.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import techguns.client.ClientProxy;
import techguns.client.models.ModelMultipart;

public abstract class RenderGunMultipart implements IItemRenderer {

	ResourceLocation texture;
	ModelMultipart model;
	float scale; //uniform scale
	float x; //offset
	float y; //offset
	float z; //offset
	boolean handleDamageValue=false;
	boolean handleProgress=false;
	int parts;
	
	public RenderGunMultipart(ModelMultipart model, ResourceLocation texture,
			float scale, float x, float y, float z, boolean handledamage, boolean handleprogress, int parts) {
		this.parts = parts;
		this.model = model;
		this.texture = texture;
		this.scale = scale;
		this.x = x;
		this.y = y;
		this.z = z;
		handleDamageValue=handledamage;
		handleProgress=handleprogress;
	}

	protected abstract void GL_Transform_FirstPerson(int part,float prog);
	protected abstract void GL_Transform_Equipped(int part,float prog);
	protected abstract void GL_Transform_Ground(int part);
	protected abstract void GL_Transform_Icon(int part);
	protected abstract void GL_Transform_reloading(int part,float prog);
	protected abstract void GL_Transform_reload_done(int part);
	protected abstract void GL_Transform_firing(int part, float prog);
	protected abstract void GL_Transform_Translate(int part);
	protected abstract void drawMuzzleFX(float prog);
	protected void GL_TransformRecoil_Equipped(int part, float prog){
		if(prog<0.3f){
			GL11.glRotatef(prog/0.3f*-30, 0, 0, 1);
		}
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		//muzzleflash
		boolean showMuzzleFX = false;
		float muzzleFXprogress = 0;
		ClientProxy cp = ClientProxy.get();
		
		for (int i=0; i<this.parts; i++){
			
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
				this.GL_Transform_FirstPerson(i,prog);
				
				//Reloading
				if (cp.getplayerReloadtime(ClientProxy.get().getPlayerClient()) >0) {
				
					long diff = cp.getplayerReloadtime(ClientProxy.get().getPlayerClient()) - System.currentTimeMillis();
	
					if (diff <= 0) {
						//ClientProxy.setplayerReloadtime(0);
						//ClientProxy.setplayerReloadtimeTotal(0);
						cp.setplayerReloadtime(ClientProxy.get().getPlayerClient(),0, 0, (byte)0);
						
						this.GL_Transform_reload_done(i);
	
					}else {
	
						long reloadtimeTotal = cp.getplayerReloadtimeTotal();
						reloadProgress = 1.0f-((float)diff / (float)reloadtimeTotal);
						
						this.GL_Transform_reloading(i,reloadProgress);
	
					}
				}else if (cp.getplayerRecoiltime(ClientProxy.get().getPlayerClient()) > 0) {
					long diff = cp.getplayerRecoiltime(ClientProxy.get().getPlayerClient()) - System.currentTimeMillis();
					//System.out.println("firing: "+diff);
					if (diff <= 0 || diff > cp.getplayerRecoiltimeTotal()) {
						//System.out.println("firing done");
						//ClientProxy.setplayerRecoiltime(0);
						//ClientProxy.setplayerRecoiltimeTotal(0);
						cp.setplayerRecoiltime(ClientProxy.get().getPlayerClient(),0, 0, (byte)0);
						this.GL_Transform_Translate(i);
					}else{
						
						float fireProgress = 1.0f-((float)diff / (float)cp.getplayerRecoiltimeTotal());		
						this.GL_Transform_firing(i,fireProgress);
					}			
					
				}else {
					this.GL_Transform_Translate(i);
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
				EntityLivingBase shooter = (EntityLivingBase) data[1];
				
					float progress=0.0f;
					if( cp.getplayerRecoiltime(shooter) > 0) {
						long diff = cp.getplayerRecoiltime(shooter) - System.currentTimeMillis();
						//System.out.println("firing: "+diff);
						if (diff <= 0 || diff > cp.getplayerRecoiltimeTotal(shooter)) {
							//System.out.println("firing done");
							//ClientProxy.setplayerRecoiltime(shooter, 0);
							//ClientProxy.setplayerRecoiltimeTotal(shooter, 0);
							cp.setplayerRecoiltime(shooter, 0, 0, (byte)0);
							
							//this.GL_Transform_Translate(i);
						}else{
							
							progress = 1.0f-((float)diff / (float)cp.getplayerRecoiltimeTotal(shooter));	
							
							//System.out.println("FireProgress"+fireProgress);
							//this.GL_Transform_firing(i,fireProgress);
						}
					} 
					this.GL_Transform_Equipped(i,progress);
					this.GL_TransformRecoil_Equipped(i,progress);
				/*try {
					EntityLivingBase owner = (EntityLivingBase) data[1];
					
					if (owner instanceof EntityPlayer){
						EntityPlayer ply = (EntityPlayer) owner;
						if (GenericGun.firetimes_client.containsKey(ply.getGameProfile().getName())){
							long firetime = GenericGun.firetimes_client.get(ply.getGameProfile().getName());
							long diff = System.currentTimeMillis() - firetime;
							
							int ticks = (int) (diff*0.02f);
							
							int maxdur = 20; //item.getMaxItemUseDuration();
							float prog = ticks / (maxdur*1.0f);
							//System.out.println("Prog:"+prog);
							if (prog < 0.0f) {
								prog = 0.0f;
							} else if (prog > 1.0f) {
								prog = 1.0f;
							}
							
							System.out.println("Rotate:"+prog);
						}
					}
					
				} catch(ArrayIndexOutOfBoundsException e){
					e.printStackTrace();
				} catch (ClassCastException e){
					e.printStackTrace();
				}*/
				
				
				
			} else if (type == ItemRenderType.ENTITY){
				
				this.GL_Transform_Ground(i);
				
			} else if (type == ItemRenderType.INVENTORY){
				
				this.GL_Transform_Icon(i);
			}
			
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

			model.render(null, 0, 0, 0, 0, 0, 0.0625F, item.getMaxDamage()-item.getItemDamage(),reloadProgress, type,i, 0.0f );
					
			GL11.glPopMatrix();
		}
		if (showMuzzleFX) {
			GL11.glPushMatrix();
			GL11.glRotatef(45, 0, 1.0f, 0);
			GL11.glRotatef(90, 0, 0, 1.0f);
			//GL11.glTranslatef(1.7f, 0f, -0.7f);
			GL11.glTranslatef(1.5f, -1.0f, -0.5f);
			drawMuzzleFX(muzzleFXprogress);
			GL11.glPopMatrix();
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
}
