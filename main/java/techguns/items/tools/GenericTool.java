package techguns.items.tools;

import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import techguns.Techguns;
import techguns.damagesystem.DamageType;
import techguns.damagesystem.TGDamageSource;
import techguns.items.armors.ICamoChangeable;
import techguns.util.EntityDeathUtils.DeathType;

public class GenericTool extends ItemTool implements ICamoChangeable {

	public GenericTool(float p_i45333_1_, ToolMaterial p_i45333_2_, Set p_i45333_3_,String name) {
		super(p_i45333_1_, p_i45333_2_, p_i45333_3_);
		setMaxStackSize(1);
		setNoRepair();
		//setMaxDamage(clipsize);
		setCreativeTab(Techguns.tabTechgun);
		setUnlocalizedName(name);
		setTextureName(Techguns.MODID+":"+name);
	}

	/**
	 * Override in Subclass to define damagesource used for melee attacks
	 * @return
	 */
	protected TGDamageSource getMeleeDamageSource(EntityPlayer player, ItemStack stack){
		TGDamageSource src = new TGDamageSource("player", player, player, DamageType.PHYSICAL, DeathType.GORE);
		src.goreChance=0.5f;
		return src;
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		//super.onCreated(stack, world, player);
		NBTTagCompound tags = stack.getTagCompound();
		if(tags==null){
			tags=new NBTTagCompound();
			stack.setTagCompound(tags);
			tags.setByte("camo", (byte) 0);
		}
	}
	
	
	@Override
	public int getCamoCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int switchCamo(ItemStack item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int switchCamo(ItemStack item, boolean back) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentCamoIndex(ItemStack item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCurrentCamoName(ItemStack item) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
