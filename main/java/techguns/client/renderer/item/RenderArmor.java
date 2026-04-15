//package techguns.client.renderer.item;
//
//import techguns.client.models.ModelGunAmmo;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.model.ModelBiped;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.client.IItemRenderer;
//
//public class RenderArmor implements IItemRenderer{
//	
//	ResourceLocation texture;
//	ModelBiped model;
//
//	public RenderArmor(ModelBiped model, ResourceLocation texture) {
//		this.model = model;
//		this.texture = texture;
//	}
//	
//
//	@Override
//	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
//		return true;
//	}
//	
//	@Override
//	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
//			ItemRendererHelper helper) {
//		return true;
//	}
//	
//	@Override
//	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
//		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
//			if (item.getItem().get)
//			model.render(null, 0, 0, 0, 0, 0, 1); //0.0625F);
//		
//	}
//}
