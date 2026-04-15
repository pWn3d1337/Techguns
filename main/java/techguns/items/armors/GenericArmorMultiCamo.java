package techguns.items.armors;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import techguns.Techguns;
import techguns.damagesystem.TGArmorMaterial;
import techguns.util.TextUtil;

public class GenericArmorMultiCamo extends GenericArmor implements ICamoChangeable{
	protected static Random rnd=new Random();
	protected String[] textureNames;
	protected String camoNameSuffix="";
	
	public GenericArmorMultiCamo(String unlocalizedName, TGArmorMaterial material, String[] textureNames, int type) {
	    super(unlocalizedName,material, textureNames[0],type);
	   this.textureNames=textureNames;
	}

	public GenericArmorMultiCamo setCamoNameSuffix(String s){
		this.camoNameSuffix=s+".";
		return this;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot,
			String type) {
		NBTTagCompound tags = stack.getTagCompound();
		byte camoID=0;
		if (tags!=null){
			if (tags.hasKey("camo")){
				camoID=tags.getByte("camo");
			}
		}
		
		if (this.hasDoubleTexture()) {
			return Techguns.MODID + ":textures/armor/" + this.textureNames[camoID] + "_" + (this.armorType == 2 ? "2" : "1") + ".png";
		} else { //USE SINGLE TEXTURE
			return Techguns.MODID + ":textures/armor/" + this.textureNames[camoID] + ".png";
		}
	}

	public int switchCamo(ItemStack item){
		return this.switchCamo(item, false);
	}
	
	public int switchCamo(ItemStack item,boolean back){
		NBTTagCompound tags = item.getTagCompound();
		if (tags== null){
			tags = new NBTTagCompound();
			item.setTagCompound(tags);
		}
		byte camoID=0;
		if (tags.hasKey("camo")){
			camoID=tags.getByte("camo");
		}
		
		ICamoChangeable it = (ICamoChangeable) item.getItem();
		
		if(back){
			camoID--;
		} else {
			camoID++;
		}
		
		if (camoID>=it.getCamoCount()){
			camoID=0;
		} else if(camoID<0){
			camoID=(byte) (it.getCamoCount()-1);
		}
		
		tags.setByte("camo", camoID);
		return camoID;
	}
	
/*	@Override
	public ItemStack onItemRightClick(ItemStack item, World world,
			EntityPlayer player) {
		
		if (player.isSneaking()){
			
			//int camoID=switchCamo(item);
			//this.setTextureName(this.textureNames[camoID]);
			//System.out.println("Switched Camo to:"+camoID);
			if (world.isRemote){
				player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.message.camoswitch")+" "+getCurrentCamoName(item)));
			}
	
			return item;
		} else {
			return super.onItemRightClick(item, world, player);
		}
	}*/

	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list,
			boolean b) {
		/*NBTTagCompound tags = item.getTagCompound();
		byte camoID=0;
		if (tags!=null && tags.hasKey("camo")){
			camoID=tags.getByte("camo");
		}*/
		super.addInformation(item, player, list, b);
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)){	
			list.add(TextUtil.trans("techguns.tooltip.currentcamo")+": "+getCurrentCamoName(item));
		}
	}

	@Override
	public int getCamoCount() {
		if (textureNames!=null){
			return textureNames.length;
		}
		return 0;
	}

	@Override
	public String getCurrentCamoName(ItemStack item) {
		NBTTagCompound tags = item.getTagCompound();
		byte camoID=-1;
		if (tags!=null && tags.hasKey("camo")){
			camoID=tags.getByte("camo");
		}
		if(camoID>=0){
			return TextUtil.trans("techguns.item."+textureNames[0]+"."+camoNameSuffix+"camoname."+camoID);
		} else {
			return TextUtil.trans("techguns.item.invalidcamo");
		}
	}

	@Override
	public int getCurrentCamoIndex(ItemStack item) {
		NBTTagCompound tags = item.getTagCompound();
		byte camoID=-1;
		if (tags!=null && tags.hasKey("camo")){
			camoID=tags.getByte("camo");
		}
		return camoID;
	}
	
	public static int getRandomCamoIndexFor(GenericArmorMultiCamo type){
		int count = type.getCamoCount();
		if( count <=0){
			return 0;
		}
		return rnd.nextInt(count);
	}
	
	public static ItemStack getNewWithCamo(Item item, int camo){
		ItemStack armor = new ItemStack(item);
		NBTTagCompound tags = armor.getTagCompound();
		if(tags==null){
			tags=new NBTTagCompound();
			armor.setTagCompound(tags);
		}
		tags.setByte("camo", (byte)camo);		
		return armor;
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		super.onCreated(stack, world, player);
		NBTTagCompound tags = stack.getTagCompound();
		if(tags==null){
			tags=new NBTTagCompound();
			stack.setTagCompound(tags);
		}
		tags.setByte("camo", (byte)0);
	}
	
}
