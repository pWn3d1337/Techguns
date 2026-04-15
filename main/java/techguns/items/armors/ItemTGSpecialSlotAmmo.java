package techguns.items.armors;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import techguns.TGItems;
import techguns.Techguns;
import techguns.util.TextUtil;

public abstract class ItemTGSpecialSlotAmmo extends ItemTGSpecialSlot {

	ItemStack ammo;
	ItemStack ammoEmpty;
	
	public ItemTGSpecialSlotAmmo(String unlocalizedName, String textureName, int camoCount, int dur, ItemStack ammo, ItemStack ammoEmpty) {
		super(unlocalizedName, textureName, camoCount, dur);
		this.ammo=ammo;
		this.ammoEmpty=ammoEmpty;
	}

	
	
	public ItemStack getAmmo() {
		return ammo;
	}



	public ItemStack getAmmoEmpty() {
		return ammoEmpty;
	}



	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean b) {
		super.addInformation(item, player, list, b);
		list.add(EnumChatFormatting.AQUA+"Reloads with: "+TextUtil.trans(this.ammo.getUnlocalizedName()+".name"));
	}

	protected void tryReloadAndRepair(ItemStack item, EntityPlayer ply){
		if(Techguns.consumeAmmoPlayer(ply, ammo)){
			item.setItemDamage(0);
			if(Techguns.addAmmoToPlayerInventory(ply, ammoEmpty)>0){
				if(!ply.worldObj.isRemote){
					ply.worldObj.spawnEntityInWorld(new EntityItem(ply.worldObj, ply.posX, ply.posY, ply.posZ, TGItems.newStack(ammoEmpty, 1)));
				}
			}
		}
	}
}
