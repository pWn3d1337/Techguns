package techguns.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.Techguns;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.util.TextUtil;

public class ItemCamoNetPlacer extends GenericItem {

	private static final int CAMOCOUNT=3;
	
	private static final int MAXBLOCKSPEROP=64;
	
	private static IIcon[] icons = new IIcon[CAMOCOUNT];
	
	public ItemCamoNetPlacer(String name) {
		super(name);
		setMaxStackSize(1);
		setTextureName("techguns:"+name+"0");
	}

	
	
	@Override
	public void registerIcons(IIconRegister r) {
		super.registerIcons(r);
		for (int i=0;i<icons.length;i++){
			icons[i]=r.registerIcon("techguns:camoNetPlacer"+i);
		}
	}

	@Override
	public IIcon getIconFromDamage(int dmg) {
		if (dmg<icons.length){
			return icons[dmg];
		} else {
			return super.getIconFromDamage(dmg);
		}
	}



	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {

		if (!item.hasTagCompound()) {
			item.setTagCompound(new NBTTagCompound());
		}
		if (player.isSneaking()) {

			//System.out.println("Side:"+side+" HitPos:"+hitX+","+hitY+","+hitZ);
			
			//byte mode = (byte) item.getItemDamage();
			/*if (item.getTagCompound().hasKey("mode")) {
				mode = (byte) ((item.getTagCompound().getByte("mode")+1) % CAMOCOUNT);
			}*/
			//item.getTagCompound().setByte("mode", mode);
			//item.setItemDamage((mode+1)%CAMOCOUNT);
			int mode = (item.getItemDamage()+1)%CAMOCOUNT;
			item.setItemDamage(mode);
			
			if (world.isRemote) player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.item.camoNetPlacer.info.msg")+" "+TextUtil.trans("techguns.item.camoNetPlacer.info.camo."+mode)));
		} else {
			//clear position
			
			item.getTagCompound().removeTag("x1");
			item.getTagCompound().removeTag("y1");
			item.getTagCompound().removeTag("z1");
			if (world.isRemote) player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.item.camoNetPlacer.info.clear")));
			
		}
		
		return super.onItemRightClick(item, world, player);
	}


	@Override
	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		if (!item.hasTagCompound()) {
			item.setTagCompound(new NBTTagCompound());
		}
		if (player.isSneaking()) {

			//System.out.println("Side:"+side+" HitPos:"+hitX+","+hitY+","+hitZ);
			
			/*byte mode = 1;
			if (item.getTagCompound().hasKey("mode")) {
				mode = (byte) ((item.getTagCompound().getByte("mode")+1) % CAMOCOUNT);
			}
			item.getTagCompound().setByte("mode", mode);*/
			
			int mode = (item.getItemDamage()+1)%CAMOCOUNT;
			item.setItemDamage(mode);
			
			if (world.isRemote) player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.item.camoNetPlacer.info.msg")+" "+TextUtil.trans("techguns.item.camoNetPlacer.info.camo."+mode)));
		}else {
			if (item.getTagCompound().hasKey("x1") && item.getTagCompound().hasKey("y1") && item.getTagCompound().hasKey("z1")) {
				
				//if (world.isRemote) player.addChatMessage(new ChatComponentText("Position2 set ("+x+"/"+y+"/"+z+")."));
				int x1 = item.getTagCompound().getInteger("x1");
				int y1 = item.getTagCompound().getInteger("y1");
				int z1 = item.getTagCompound().getInteger("z1");
			//	int sizeX = Math.abs(x1-x)+1;
			//	int sizeY = Math.abs(y1-y)+1;
			//	int sizeZ = Math.abs(z1-z)+1;
				
				//if (world.isRemote) player.addChatMessage(new ChatComponentText("Size: ("+sizeX+"/"+sizeY+"/"+sizeZ+")."));
				byte planeMode=0;
				
				if (x1==x){
					//plane yz;
					planeMode=0;
					
				} else if (z1==z){
					
					//plane yx;
					planeMode=2;
					
				} else if (y1==y){
					
					//planeXZ;
					planeMode=1;
					
				} else {
					
					
					if (world.isRemote) player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.item.camoNetPlacer.info.noplane")));
					item.getTagCompound().removeTag("x1");
					item.getTagCompound().removeTag("y1");
					item.getTagCompound().removeTag("z1");
					return true;
				}
				
				int camoMode = item.getItemDamage();
				/*if (item.getTagCompound().hasKey("mode")) {
					camoMode = item.getTagCompound().getInteger("mode");
				}*/

				
				int blockAmount = calcBlockAmount(x, y, z, x1, y1, z1);
				//System.out.println("BlocksNeeded: "+blockAmount);
				if (blockAmount > MAXBLOCKSPEROP){
					if (world.isRemote) player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.item.camoNetPlacer.info.maxBlock.1")+" "+MAXBLOCKSPEROP+" "+TextUtil.trans("techguns.item.camoNetPlacer.info.maxBlock.2")));
					item.getTagCompound().removeTag("x1");
					item.getTagCompound().removeTag("y1");
					item.getTagCompound().removeTag("z1");
					return true;
				}
				
				ItemStack itemToConsume = getItemBlockRequired(blockAmount,planeMode==1,(byte) camoMode);
				
				int tooFew= Techguns.canConsumeItem(player.inventory.mainInventory, itemToConsume, 0, player.inventory.mainInventory.length);
				
				
				if (tooFew > 0){
					//System.out.println("Required: "+blockAmount+" Missing:"+tooFew);
					if (world.isRemote) player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.item.camoNetPlacer.info.notEnough")));
					item.getTagCompound().removeTag("x1");
					item.getTagCompound().removeTag("y1");
					item.getTagCompound().removeTag("z1");
					return true;
				}
				
				
			//	if (!world.isRemote){
					int minX = Math.min(x, x1);
					int maxX = Math.max(x, x1);
					
					int minZ = Math.min(z, z1);
					int maxZ = Math.max(z, z1);
					
					int minY = Math.min(y, y1);
					int maxY = Math.max(y, y1);
					
					if(planeMode==1 && side==1){
						minY++;
						maxY++;
					}
					
					
					int reallyConsumed = placePlane(world, minX,minY,minZ,maxX,maxY,maxZ,getMBlock(planeMode==1,(byte) camoMode));
				
					//System.out.println("Blocks Placed:"+ reallyConsumed);
					
					Techguns.consumeItem(player.inventory.mainInventory, getItemBlockRequired(reallyConsumed,planeMode==1,(byte) camoMode), 0, player.inventory.mainInventory.length);
			//	}
				
				if (world.isRemote) player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.item.camoNetPlacer.info.placed")));

				
				item.getTagCompound().removeTag("x1");
				item.getTagCompound().removeTag("y1");
				item.getTagCompound().removeTag("z1");
				
			}else {
				item.getTagCompound().setInteger("x1",x);
				item.getTagCompound().setInteger("y1",y);
				item.getTagCompound().setInteger("z1",z);
				if (world.isRemote) player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.item.camoNetPlacer.info.pos1set")+" ("+x+"/"+y+"/"+z+")."));
			}
		}
		return true;
    }
	
	private ItemStack getItemBlockRequired(int amount,boolean top, byte camo){
		
		if (top){

			return new ItemStack(TGBlocks.camoNetRoof,amount,camo);
			
		} else {
			
			return new ItemStack(TGBlocks.camoNetPane,amount,camo);
			
		}
		
		
		
	}
	
	private MBlock getMBlock(boolean top, byte camo){
		
		if (top){

			return new MBlock(TGBlocks.camoNetRoof,camo);
			
		} else {
			
			return new MBlock(TGBlocks.camoNetPane,camo);
			
		}
		
		
		
	}
	
	private int calcBlockAmount(int x, int y, int z, int x1, int y1, int z1){
		int xlen = Math.abs(x-x1)+1;
		int ylen = Math.abs(y-y1)+1;
		int zlen = Math.abs(z-z1)+1;
		
		return xlen*ylen*zlen;		
	}
	
	private int placePlane(World w, int x1, int y1, int z1, int x2, int y2, int z2, MBlock block){
		int consumed=0;
		
		for (int x=x1;x<=x2;x++){
			for (int y=y1;y<=y2;y++){
				for (int z=z1;z<=z2;z++){
	
					
					if (w.getBlock(x, y, z).isAir(w, x, y, z)){
						if(!w.isRemote){
							block.setBlock(w, x, y, z, 0);
						}
						consumed++;
					}
				}
			}
		}
		return consumed;
	}
}
