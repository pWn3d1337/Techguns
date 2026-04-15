package techguns.items.armors;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import techguns.Techguns;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.gui.playerinventory.TGPlayerInventory;
import techguns.items.GenericItem;
import techguns.items.TGSlotType;
import techguns.util.TextUtil;

public abstract class ItemTGSpecialSlot extends GenericItem implements ITGSpecialSlot, ICamoChangeable {
	protected String textureName;
	protected byte camoCount;
	
	public ItemTGSpecialSlot(String unlocalizedName,String textureName, int camoCount,int dur) {
		super(unlocalizedName);
		this.textureName = textureName; //Armor Texture
//		setCreativeTab(Techguns.tabTechgun);
//	    this.setUnlocalizedName(unlocalizedName);
	    this.setTextureName(Techguns.MODID + ":" + unlocalizedName); //Item Texture
	    this.maxStackSize=1;
	    this.setMaxDamage(dur);
	    this.camoCount = (byte) camoCount;
	    
	}
	
	@Override
	public int getCamoCount() {
		return this.camoCount;
	}

	@Override
	public String getCurrentCamoName(ItemStack item) {
		NBTTagCompound tags = item.getTagCompound();
		byte camoID=0;
		if (tags!=null && tags.hasKey("camo")){
			camoID=tags.getByte("camo");
		}
		if(camoID>0){
			return TextUtil.trans(this.getUnlocalizedName()+".camoname."+camoID);
		} else {
			return TextUtil.trans("techguns.item.defaultcamo");
		}
	}

	@Override
	public int getCurrentCamoIndex(ItemStack item) {
		NBTTagCompound tags = item.getTagCompound();
		byte camoID=0;
		if (tags!=null && tags.hasKey("camo")){
			camoID=tags.getByte("camo");
		}
		return camoID;
	}

	@Override
	public int switchCamo(ItemStack item) {
		return switchCamo(item,false);
	}

	@Override
	public int switchCamo(ItemStack item, boolean back) {
		byte camoID=0;
		NBTTagCompound tags = item.getTagCompound();
		if (tags!=null && tags.hasKey("camo")){
			camoID=tags.getByte("camo");
		}
		
		if(tags==null){
			tags=new NBTTagCompound();
			tags.setByte("camo", (byte) 0);
			item.setTagCompound(tags);
		}
		
		if(this.camoCount>1){
			
			if(!back){
				camoID++;
			} else {
				camoID--;
			}
			
			if(camoID>=camoCount){
				camoID=0;
			} else if (camoID<0){
				camoID=(byte) (camoCount-1);
			}
			tags.setByte("camo", camoID);
			return camoID;
			
		} else {
			return 0;
		}

	}
	
	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean b) {
		super.addInformation(item, player, list, b);
		list.add(EnumChatFormatting.GRAY+"Slot: "+this.getSlot(item));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World w, EntityPlayer player) {
		if(this.getSlot(stack)==TGSlotType.BACKSLOT) {
			TechgunsExtendedPlayerProperties props  = TechgunsExtendedPlayerProperties.get(player);
			if (props.TG_inventory.inventory[TGPlayerInventory.SLOT_BACK]==null){
				props.TG_inventory.inventory[TGPlayerInventory.SLOT_BACK] = stack.copy();
				stack.stackSize=0;
				return stack;
			}
		} else if (this.getSlot(stack)==TGSlotType.FACESLOT){
			TechgunsExtendedPlayerProperties props  = TechgunsExtendedPlayerProperties.get(player);
			if (props.TG_inventory.inventory[TGPlayerInventory.SLOT_FACE]==null){
				props.TG_inventory.inventory[TGPlayerInventory.SLOT_FACE] = stack.copy();
				stack.stackSize=0;
				return stack;
			}
		}
		return super.onItemRightClick(stack, w, player);
	}

	
}
