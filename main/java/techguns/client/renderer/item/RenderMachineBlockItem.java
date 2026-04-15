package techguns.client.renderer.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class RenderMachineBlockItem implements IItemRenderer {
	
	protected RenderMachineBlockItemBase[] renderers; 

	public RenderMachineBlockItem(RenderMachineBlockItemBase[] renderers) {
		super();
		this.renderers = renderers;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		int meta = item.getItemDamage();
		return renderers[meta].handleRenderType(item, type);
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		int meta = item.getItemDamage();
		return renderers[meta].shouldUseRenderHelper(type, item, helper);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		int meta = item.getItemDamage();
		renderers[meta].renderItem(type, item, data);
	}

}
