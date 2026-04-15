package techguns.blocks.ctm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.cricketcraft.chisel.api.carving.ICarvingGroup;
import com.cricketcraft.chisel.api.carving.ICarvingRegistry;
import com.cricketcraft.chisel.api.carving.ICarvingVariation;

public class CarvingRegistry implements ICarvingRegistry{

	ArrayList<ICarvingGroup> carvingGroups;

	
	public CarvingRegistry() {
		this.carvingGroups = new ArrayList<ICarvingGroup>();
	}
	
	@Override
	public ICarvingGroup getGroup(Block block, int meta) {
		for (int i = 0; i< carvingGroups.size(); i++) {
			ICarvingGroup group = carvingGroups.get(i);
			List<ICarvingVariation> variations = group.getVariations();
			for (int j = 0; j < variations.size(); j++) {
				ICarvingVariation var = variations.get(j);
				if (var.getBlock() == block && var.getBlockMeta() == meta) {
					return group;
				}
			}
		}
		return null;
	}

	@Override
	public ICarvingGroup getGroup(String name) {
		for (int i = 0; i< carvingGroups.size(); i++) {
			ICarvingGroup group = carvingGroups.get(i);
			if (group.getName().equals(name)) {
				return group;
			}
		}
		return null;
	}

	@Override
	public ICarvingVariation getVariation(Block block, int meta) {
		for (int i = 0; i< carvingGroups.size(); i++) {
			ICarvingGroup group = carvingGroups.get(i);
			List<ICarvingVariation> variations = group.getVariations();
			for (int j = 0; j < variations.size(); j++) {
				ICarvingVariation var = variations.get(j);
				if (var.getBlock() == block && var.getBlockMeta() == meta) {
					return var;
				}
			}
		}
		return null;
	}

	@Override
	public List<ICarvingVariation> getGroupVariations(Block block, int meta) {
		for (int i = 0; i< carvingGroups.size(); i++) {
			ICarvingGroup group = carvingGroups.get(i);
			List<ICarvingVariation> variations = group.getVariations();
			for (int j = 0; j < variations.size(); j++) {
				ICarvingVariation var = variations.get(j);
				if (var.getBlock() == block && var.getBlockMeta() == meta) {
					return variations;
				}
			}
		}
		return null;
	}

	@Override
	public String getOreName(Block block, int meta) {
		for (int i = 0; i< carvingGroups.size(); i++) {
			ICarvingGroup group = carvingGroups.get(i);
			List<ICarvingVariation> variations = group.getVariations();
			for (int j = 0; j < variations.size(); j++) {
				ICarvingVariation var = variations.get(j);
				if (var.getBlock() == block && var.getBlockMeta() == meta) {
					return group.getOreName();
				}
			}
		}
		return null;
	}

	@Override
	public List<ItemStack> getItemsForChiseling(ItemStack chiseled) {
		ICarvingGroup group = this.getGroup(Block.getBlockFromItem(chiseled.getItem()), chiseled.getItemDamage());
		List<ItemStack> items = new ArrayList<ItemStack>();
		for (int i = 0; i < group.getVariations().size(); i++) {
			ICarvingVariation var = group.getVariations().get(i);
			items.add(new ItemStack(var.getBlock(), chiseled.stackSize, var.getItemMeta()));
		}
		return items;
	}

	@Override
	public String getVariationSound(Block block, int meta) {
		for (int i = 0; i< carvingGroups.size(); i++) {
			ICarvingGroup group = carvingGroups.get(i);
			List<ICarvingVariation> variations = group.getVariations();
			for (int j = 0; j < variations.size(); j++) {
				ICarvingVariation var = variations.get(j);
				if (var.getBlock() == block && var.getBlockMeta() == meta) {
					return group.getSound();
				}
			}
		}
		return null;
	}

	@Override
	public String getVariationSound(Item item, int meta) {
		return this.getVariationSound(Block.getBlockFromItem(item), meta);
	}

	@Override
	public List<String> getSortedGroupNames() {
		List<String> names = new ArrayList<String>();
		for (int i = 0; i < carvingGroups.size(); i++) {
			ICarvingGroup group = carvingGroups.get(i);
			names.add(group.getName());
		}
		Collections.sort(names);
		return names;
	}

	@Override
	public void addVariation(String groupName, Block block, int metadata,
			int order) {
		ICarvingGroup group = this.getGroup(groupName);
		group.addVariation(new CarvingVariation(block, metadata, metadata, order));
		
	}

	@Override
	public void addVariation(String groupName, ICarvingVariation variation) {
		ICarvingGroup group = this.getGroup(groupName);
		group.addVariation(variation);
	}

	@Override
	public void addGroup(ICarvingGroup group) {
		carvingGroups.add(group);
	}

	@Override
	public ICarvingGroup removeGroup(String groupName) {
		ICarvingGroup group = this.getGroup(groupName);
		carvingGroups.remove(group);
		return group;
	}

	@Override
	public ICarvingVariation removeVariation(Block block, int metadata) {
		ICarvingVariation lastRemoved = null;
		for (int i = 0; i< carvingGroups.size(); i++) {
			ICarvingGroup group = carvingGroups.get(i);
			List<ICarvingVariation> variations = group.getVariations();
			for (int j = 0; j < variations.size(); j++) {
				ICarvingVariation var = variations.get(j);
				if (var.getBlock() == block && var.getBlockMeta() == metadata) {
					group.removeVariation(var);
					lastRemoved = var;
				}
			}
		}
		return lastRemoved;
	}

	@Override
	public ICarvingVariation removeVariation(Block block, int metadata,
			String groupName) {
		ICarvingVariation lastRemoved = null;
		for (int i = 0; i< carvingGroups.size(); i++) {
			ICarvingGroup group = carvingGroups.get(i);
			if (group.getName().equals(groupName)) {
				List<ICarvingVariation> variations = group.getVariations();
				for (int j = 0; j < variations.size(); j++) {
					ICarvingVariation var = variations.get(j);
					if (var.getBlock() == block && var.getBlockMeta() == metadata) {
						group.removeVariation(var);
						lastRemoved = var;
					}
				}
			}
		}
		return lastRemoved;
	}

	@Override
	public void registerOre(String groupName, String oreName) {
		ICarvingGroup group = getGroup(groupName);
		group.setOreName(oreName);
	}

	@Override
	public void setVariationSound(String groupName, String sound) {
		ICarvingGroup group = getGroup(groupName);
		group.setSound(sound);
	}

}
