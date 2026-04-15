package techguns.plugins.tconstruct;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import techguns.TGItems;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLInterModComms;

@Optional.Interface(iface = "techguns.plugins.tconstruct.ITechgunsTConstructIntegration", modid = "TConstruct")
public class TechgunsTConstructIntegration implements ITechgunsTConstructIntegration {

	@Override
	public void init() {
    	this.addPartsToTConstruct();
	}

	
	public void addPartsToTConstruct(){

		//TODO: id to config file
    	int id=50;
    	int idcarbon=51;
    	int idtitanium=52;
    	int idobsidianSteel=53;
    	int idRubber=54;
    	
    	/**
    	 * ADD PLASTIC
    	 */
    	NBTTagCompound tag = new NBTTagCompound();
    	tag.setInteger("Id", id); // Unique material ID. Reseved IDs: 0-40 Tinker, 41-45 Iguana Tinker Tweaks, 100-200 ExtraTiC
    	tag.setString("Name", "Plastic"); // Unique material name
    	tag.setInteger("HarvestLevel", 1); // diamond level
    	tag.setInteger("Durability", 1000);
    	tag.setInteger("MiningSpeed", 350);
    	tag.setInteger("Attack", 1); // optional
    	tag.setFloat("HandleModifier", 1.5f);
    	tag.setString("Style", EnumChatFormatting.WHITE.toString()); // optional, color of the material text
    	tag.setInteger("Color", 255 << 24 | 200 << 16 | 200 << 8 | 200); // argb

    	/* SINCE 1.8.2 - bow and arrow stats
    	 * for bow and arrow stats, best compare to other materials to find good values
    	 */
    	// additional stats for bows
    	tag.setInteger("Bow_DrawSpeed", 150); // the higher the longer it takes to draw the bow
    	tag.setFloat("Bow_ProjectileSpeed", 1.2f); // the higher the faster the projectile goes

    	// additional stats for arrows
    	tag.setFloat("Projectile_Mass", 1.0f);
    	tag.setFloat("Projectile_Fragility", 0.95f); // This is a multiplier to the shafts break-chance
    	
    	
    	FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);
    	
    	/* SINCE 1.8.3 - material mappings
    	 * This maps an item to a material so it can be used for repairing etc. */
    	tag = new NBTTagCompound();
    	tag.setInteger("MaterialId", id);
    	tag.setInteger("Value", 2); // 1 material ever 2 value. See PartMapping IMC
    	NBTTagCompound item = new NBTTagCompound();
    	(TGItems.newStack(TGItems.plasticSheet, 1)).writeToNBT(item); // seared brick item
    	tag.setTag("Item", item);
    	FMLInterModComms.sendMessage("TConstruct", "addMaterialItem", tag);

    	/* This part adds mappigs, so that you can convert items to toolparts in the Part Builder.
    	   Stone Parts are used as the baseline for what exists */
    	tag = new NBTTagCompound();
    	tag.setInteger("MaterialId", id); // output material id
    	item = new NBTTagCompound();
    	(TGItems.newStack(TGItems.plasticSheet, 1)).writeToNBT(item); // seared brick block
    	tag.setTag("Item", item);

    	/*item = new NBTTagCompound();
    	(TGItems.newStack(TGItems.plasticSheet, 1)).writeToNBT(item); // seared brick item
    	tag.setTag("Shard", item);*/

    	// 1 value = 1 shard. So 1 blocks like stone usually have value 2.
    	// Seared Brick is the shard, the block consists of 4 bricks, therefore value 4
    	tag.setInteger("Value", 2);
    	FMLInterModComms.sendMessage("TConstruct", "addPartBuilderMaterial", tag);
    	
    	/**
    	 * ADD carbon
    	 */
    	tag = new NBTTagCompound();
    	tag.setInteger("Id", idcarbon); // Unique material ID. Reseved IDs: 0-40 Tinker, 41-45 Iguana Tinker Tweaks, 100-200 ExtraTiC
    	tag.setString("Name", "Carbon"); // Unique material name
    	tag.setInteger("HarvestLevel", 5); // diamond level
    	tag.setInteger("Durability", 2500);
    	tag.setInteger("MiningSpeed", 250);
    	tag.setInteger("Attack", 4); // optional
    	tag.setFloat("HandleModifier", 2.0f);
    	tag.setInteger("Reinforced", 3);
    	tag.setString("Style", EnumChatFormatting.BLACK.toString()); // optional, color of the material text
    	tag.setInteger("Color", 255 << 24 | 35 << 16 | 35 << 8 | 35); // argb

    	/* SINCE 1.8.2 - bow and arrow stats
    	 * for bow and arrow stats, best compare to other materials to find good values
    	 */
    	// additional stats for bows
    	tag.setInteger("Bow_DrawSpeed", 180); // the higher the longer it takes to draw the bow
    	tag.setFloat("Bow_ProjectileSpeed", 1.3f); // the higher the faster the projectile goes

    	// additional stats for arrows
    	tag.setFloat("Projectile_Mass", 1.1f);
    	tag.setFloat("Projectile_Fragility", 0.975f); // This is a multiplier to the shafts break-chance
    	
    	
    	FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);
    	
    	/**
    	 * ADD obsidianSteel
    	 */
    	tag = new NBTTagCompound();
    	tag.setInteger("Id", idobsidianSteel); // Unique material ID. Reseved IDs: 0-40 Tinker, 41-45 Iguana Tinker Tweaks, 100-200 ExtraTiC
    	tag.setString("Name", "ObsidianSteel"); // Unique material name
    	tag.setInteger("HarvestLevel", 4); // diamond level
    	tag.setInteger("Durability", 2000);
    	tag.setInteger("MiningSpeed", 250);
    	tag.setInteger("Attack", 3); // optional
    	tag.setFloat("HandleModifier", 2.0f);
    	tag.setInteger("Reinforced", 2);
    	tag.setString("Style", EnumChatFormatting.DARK_PURPLE.toString()); // optional, color of the material text
    	tag.setInteger("Color", 255 << 24 | 50 << 16 | 25 << 8 | 65); // argb

    	/* SINCE 1.8.2 - bow and arrow stats
    	 * for bow and arrow stats, best compare to other materials to find good values
    	 */
    	// additional stats for bows
    	tag.setInteger("Bow_DrawSpeed", 180); // the higher the longer it takes to draw the bow
    	tag.setFloat("Bow_ProjectileSpeed", 1.2f); // the higher the faster the projectile goes

    	// additional stats for arrows
    	tag.setFloat("Projectile_Mass", 1.2f);
    	tag.setFloat("Projectile_Fragility", 0.975f); // This is a multiplier to the shafts break-chance
    	
    	
    	FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);
    	
    	
    	/**
    	 * ADD Titanium
    	 */
    	tag = new NBTTagCompound();
    	tag.setInteger("Id", idtitanium); // Unique material ID. Reseved IDs: 0-40 Tinker, 41-45 Iguana Tinker Tweaks, 100-200 ExtraTiC
    	tag.setString("Name", "Titanium"); // Unique material name
    	tag.setInteger("HarvestLevel", 4); // diamond level
    	tag.setInteger("Durability", 2250);
    	tag.setInteger("MiningSpeed", 500);
    	tag.setInteger("Attack", 3); // optional
    	tag.setFloat("HandleModifier", 2.5f);
    	//tag.setInteger("Reinforced", 3);
    	tag.setString("Style", EnumChatFormatting.GRAY.toString()); // optional, color of the material text
    	tag.setInteger("Color", 255 << 24 | 220 << 16 | 220 << 8 | 220); // argb

    	/* SINCE 1.8.2 - bow and arrow stats
    	 * for bow and arrow stats, best compare to other materials to find good values
    	 */
    	// additional stats for bows
    	tag.setInteger("Bow_DrawSpeed", 180); // the higher the longer it takes to draw the bow
    	tag.setFloat("Bow_ProjectileSpeed", 1.5f); // the higher the faster the projectile goes

    	// additional stats for arrows
    	tag.setFloat("Projectile_Mass", 1.0f);
    	tag.setFloat("Projectile_Fragility", 0.975f); // This is a multiplier to the shafts break-chance
    	
    	FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);
    	
    	
    	/**
    	 * ADD Rubber
    	 */
    	tag = new NBTTagCompound();
    	tag.setInteger("Id", idRubber); // Unique material ID. Reseved IDs: 0-40 Tinker, 41-45 Iguana Tinker Tweaks, 100-200 ExtraTiC
    	tag.setString("Name", "Rubber"); // Unique material name
    	tag.setInteger("HarvestLevel", 0); // diamond level
    	tag.setInteger("Durability", 500);
    	tag.setInteger("MiningSpeed", 2500);
    	tag.setInteger("Attack", 0); // optional
    	tag.setFloat("HandleModifier", 1.0f);
    	//tag.setInteger("Reinforced", 3);
    	tag.setString("Style", EnumChatFormatting.DARK_GRAY.toString()); // optional, color of the material text
    	tag.setInteger("Color", 255 << 24 | 65 << 16 | 65 << 8 | 0); // argb

    	/* SINCE 1.8.2 - bow and arrow stats
    	 * for bow and arrow stats, best compare to other materials to find good values
    	 */
    	// additional stats for bows
    	tag.setInteger("Bow_DrawSpeed", 20); // the higher the longer it takes to draw the bow
    	tag.setFloat("Bow_ProjectileSpeed", 1.0f); // the higher the faster the projectile goes

    	// additional stats for arrows
    	tag.setFloat("Projectile_Mass", 0.25f);
    	tag.setFloat("Projectile_Fragility", 1.0f); // This is a multiplier to the shafts break-chance
    	
    	FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);
    	
    	
    	
    	/* SINCE 1.8.3 - material mappings
    	 * This maps an item to a material so it can be used for repairing etc. */
    	tag = new NBTTagCompound();
    	tag.setInteger("MaterialId", id);
    	tag.setInteger("Value", 2); // 1 material ever 2 value. See PartMapping IMC
    	item = new NBTTagCompound();
    	(TGItems.newStack(TGItems.plasticSheet, 1)).writeToNBT(item); // seared brick item
    	tag.setTag("Item", item);
    	FMLInterModComms.sendMessage("TConstruct", "addMaterialItem", tag);

    	/* This part adds mappigs, so that you can convert items to toolparts in the Part Builder.
    	   Stone Parts are used as the baseline for what exists */
    	tag = new NBTTagCompound();
    	tag.setInteger("MaterialId", idcarbon); // output material id
    	item = new NBTTagCompound();
    	(TGItems.newStack(TGItems.carbonPlate, 1)).writeToNBT(item); // seared brick block
    	tag.setTag("Item", item);

    	/*item = new NBTTagCompound();
    	(TGItems.newStack(TGItems.plasticSheet, 1)).writeToNBT(item); // seared brick item
    	tag.setTag("Shard", item);*/

    	// 1 value = 1 shard. So 1 blocks like stone usually have value 2.
    	// Seared Brick is the shard, the block consists of 4 bricks, therefore value 4
    	tag.setInteger("Value", 1);
    	FMLInterModComms.sendMessage("TConstruct", "addPartBuilderMaterial", tag);
    	
    	
    	tag = new NBTTagCompound();
    	tag.setInteger("MaterialId", idtitanium); // output material id
    	item = new NBTTagCompound();
    	(TGItems.newStack(TGItems.ingotTitanium, 1)).writeToNBT(item); // seared brick block
    	tag.setTag("Item", item);
    	tag.setInteger("Value", 1);
    	FMLInterModComms.sendMessage("TConstruct", "addPartBuilderMaterial", tag);
    	
    	
    	tag = new NBTTagCompound();
    	tag.setInteger("MaterialId", idobsidianSteel); // output material id
    	item = new NBTTagCompound();
    	(TGItems.newStack(TGItems.ingotObsidianSteel, 1)).writeToNBT(item); // seared brick block
    	tag.setTag("Item", item);
    	tag.setInteger("Value", 1);
    	FMLInterModComms.sendMessage("TConstruct", "addPartBuilderMaterial", tag);
    	
    	tag = new NBTTagCompound();
    	tag.setInteger("MaterialId", idRubber); // output material id
    	item = new NBTTagCompound();
    	(TGItems.newStack(TGItems.rubberBar, 1)).writeToNBT(item); // seared brick block
    	tag.setTag("Item", item);
    	tag.setInteger("Value", 1);
    	FMLInterModComms.sendMessage("TConstruct", "addPartBuilderMaterial", tag);
    	
	}

}
