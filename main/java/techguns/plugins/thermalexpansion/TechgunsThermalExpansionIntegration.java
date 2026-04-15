package techguns.plugins.thermalexpansion;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLInterModComms;

@Optional.Interface(iface = "techguns.plugins.tconstruct.ITechgunsThermalExpansionIntegration", modid = "ThermalExpansion")
public class TechgunsThermalExpansionIntegration implements ITechgunsThermalExpansionIntegration {

	@Override
	public void init() {
		
		/*ItemStack steel = OreDictionary.getOres("ingotSteel").get(0).copy();
		steel.stackSize=1;
		
		ItemStack pulverizedCoal = OreDictionary.getOres("dustCoal").get(0).copy();
		pulverizedCoal.stackSize=1;
		
		addSmelterRecipe(1000, new ItemStack(Items.iron_ingot,1), pulverizedCoal, steel, null, 0);*/

	}

	
	/**
	 * Copied from Thermal expansion examples
	 * https://github.com/CoFH/CoFHLib/blob/master/src/main/java/cofh/api/modhelpers/ThermalExpansionHelper.java
	 * 
	 * @param energy
	 * @param primaryInput
	 * @param secondaryInput
	 * @param primaryOutput
	 * @param secondaryOutput
	 * @param secondaryChance
	 */
	public static void addSmelterRecipe(int energy, ItemStack primaryInput, ItemStack secondaryInput, ItemStack primaryOutput, ItemStack secondaryOutput,
			int secondaryChance) {

		if (primaryInput == null || secondaryInput == null || primaryOutput == null) {
			return;
		}
		NBTTagCompound toSend = new NBTTagCompound();

		toSend.setInteger("energy", energy);
		toSend.setTag("primaryInput", new NBTTagCompound());
		toSend.setTag("secondaryInput", new NBTTagCompound());
		toSend.setTag("primaryOutput", new NBTTagCompound());

		if (secondaryOutput != null) {
			toSend.setTag("secondaryOutput", new NBTTagCompound());
		}

		primaryInput.writeToNBT(toSend.getCompoundTag("primaryInput"));
		secondaryInput.writeToNBT(toSend.getCompoundTag("secondaryInput"));
		primaryOutput.writeToNBT(toSend.getCompoundTag("primaryOutput"));

		if (secondaryOutput != null) {
			secondaryOutput.writeToNBT(toSend.getCompoundTag("secondaryOutput"));
			toSend.setInteger("secondaryChance", secondaryChance);
		}
		FMLInterModComms.sendMessage("ThermalExpansion", "SmelterRecipe", toSend);
	}
	
}
