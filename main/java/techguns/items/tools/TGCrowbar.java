package techguns.items.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Keyboard;

import scala.collection.parallel.HavingForkJoinPool;
import techguns.util.ItemUtil;
import techguns.util.TextUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class TGCrowbar extends TGPickaxe {

	protected HashMap<String, Integer> harvestLevels = new HashMap<String,Integer>();
	
	public TGCrowbar(ToolMaterial mat, String name) {
		super(mat, name);
		harvestLevels.put("default", mat.getHarvestLevel());
		harvestLevels.put("pickaxe", mat.getHarvestLevel());
		harvestLevels.put("wrench", mat.getHarvestLevel());
	}

	@Override
	public Set<String> getToolClasses(ItemStack stack) {
		return harvestLevels.keySet();
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean b) {
		super.addInformation(item, player, list, b);
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)){
			ItemUtil.addToolClassesTooltip(harvestLevels, list);
			list.add(TextUtil.trans("techguns.tooltip.crowbar.destroycluster"));
		} else {
			ItemUtil.addShiftExpandedTooltip(list);
		}
	}

	@Override
	public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
		return true;
	}

	
}
