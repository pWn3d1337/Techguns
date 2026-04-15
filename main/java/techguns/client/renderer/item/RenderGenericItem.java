package techguns.client.renderer.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import techguns.TGItems;
import techguns.client.models.ModelAS50Mag;
import techguns.client.models.ModelLmgMag;
import techguns.client.models.ModelLmgMagE;
import techguns.client.models.ModelM4Mag;
import techguns.client.models.ModelM4MagE;
import techguns.client.models.ModelRocket;

public class RenderGenericItem implements IItemRenderer {
	private IItemRenderer renderM4Mag = new RenderM4Mag(new ModelM4Mag(), new ResourceLocation("techguns","textures/guns/m4Texture.png"),1.0f, 0.f, 0.f, 0.f);
	private IItemRenderer renderM4MagEmpty = new RenderM4Mag(new ModelM4MagE(), new ResourceLocation("techguns","textures/guns/m4Texture.png"), 1.0f, 0.f, 0.f, 0.f);
	private IItemRenderer renderRocketAmmo = new RenderRocketAmmo(new ModelRocket(), new ResourceLocation("techguns","textures/guns/rocketlauncher.png"), 1.0f, 0.f, 0.f, 0.f);
    private IItemRenderer renderAS50Mag = new RenderAS50Mag(new ModelAS50Mag(false), new ResourceLocation("techguns","textures/guns/as50Texture.png"), 1.0f, 0.f, 0.f, 0.f);
    private IItemRenderer renderAS50MagEmpty =new RenderAS50Mag(new ModelAS50Mag(true), new ResourceLocation("techguns","textures/guns/as50Texture.png"), 1.0f, 0.f, 0.f, 0.f);
    private IItemRenderer renderLMGMag = new RenderLMGMag(new ModelLmgMag(), new ResourceLocation("techguns","textures/guns/mg2_texture.png"), 1.0f, 0.f, 0.f, 0.f);
    private IItemRenderer renderLMGMagEmpty = new RenderLMGMag(new ModelLmgMagE(), new ResourceLocation("techguns","textures/guns/mg2_texture.png"), 1.0f, 0.f, 0.f, 0.f);
    private IItemRenderer renderRocketAmmoNuclear = new RenderRocketAmmo(new ModelRocket(), new ResourceLocation("techguns","textures/guns/nukerocketlauncher.png"), 1.0f, 0.f, 0.f, 0.f);
            
    
	public RenderGenericItem(){
		
	}
	

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return has3DModel(item);
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return has3DModel(item);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		int meta = item.getItemDamage();
		if (meta==TGItems.m4Mag.getItemDamage()){
			renderM4Mag.renderItem(type, item, data);
			
		} else if (meta==TGItems.m4MagEmpty.getItemDamage()){
			renderM4MagEmpty.renderItem(type, item, data);
			
		} else if (meta==TGItems.rocketAmmo.getItemDamage()){
			renderRocketAmmo.renderItem(type, item, data);
			
		} else if (meta==TGItems.as50Mag.getItemDamage()){
			renderAS50Mag.renderItem(type, item, data);
			
		} else if (meta==TGItems.as50MagEmpty.getItemDamage()){
			renderAS50MagEmpty.renderItem(type, item, data);
			
		} else if (meta==TGItems.lmgMag.getItemDamage()){
			renderLMGMag.renderItem(type, item, data);
			
		} else if (meta==TGItems.lmgMagEmpty.getItemDamage()){
			renderLMGMagEmpty.renderItem(type, item, data);
			
		} else if (meta==TGItems.rocketAmmoNuclear.getItemDamage()){
			renderRocketAmmoNuclear.renderItem(type, item, data);
		}

	}

	private boolean has3DModel(ItemStack item){
		int dmg = item.getItemDamage();
		if (dmg==TGItems.m4Mag.getItemDamage()||dmg==TGItems.m4MagEmpty.getItemDamage()||dmg==TGItems.as50Mag.getItemDamage()||dmg==TGItems.as50MagEmpty.getItemDamage()
				||dmg==TGItems.lmgMag.getItemDamage()||dmg==TGItems.lmgMagEmpty.getItemDamage()||dmg==TGItems.rocketAmmo.getItemDamage()||dmg==TGItems.rocketAmmoNuclear.getItemDamage()){
			return true;
		}
		return false;
	}
}
