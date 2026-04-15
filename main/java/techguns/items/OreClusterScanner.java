package techguns.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import techguns.TGBlocks;
import techguns.Techguns;
import techguns.util.TextUtil;
import cofh.api.energy.IEnergyContainerItem;

public class OreClusterScanner extends GenericItem implements IEnergyContainerItem{

	protected int capacity;
	protected int maxReceive;
	protected int maxExtract;
	
	private static final int RF_PER_USETICK=300;
	
	private static final int MAXRF=30000;
	
	private static final int SCANDURATION=40;
	
	public OreClusterScanner() {
		super("oreClusterScanner");
		this.setMaxStackSize(1);
		
		//RF values
		this.capacity=MAXRF;
		this.maxReceive=3000;
		this.maxExtract=3000;
	}

	
	
	
	
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		ItemStack stack = new ItemStack(item);
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		stack.stackTagCompound.setInteger("Energy", MAXRF);
		list.add(stack);
	}

	



	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b) {
		super.addInformation(stack, player, list, b);
		list.add(this.getEnergyStored(stack)+"/"+this.capacity+" RF");
	}





	@Override
	public void onPlayerStoppedUsing(ItemStack item, World world, EntityPlayer player, int count) {
		
		int dur = this.getMaxItemUseDuration(item) - count;
		//System.out.println("Stopped Using with dur:"+dur);
		
		int extracted = this.extractEnergy(item, RF_PER_USETICK, false);
		
		if(world.isRemote && extracted==RF_PER_USETICK){
			
			//int dur = this.getMaxItemUseDuration(item) - count;
			
			if (dur>=40){
				
				int x,y,z;
				x = (int) player.posX;
				y = (int) player.posY;
				z = (int) player.posZ;
				
				//System.out.println("ChunkCoordX:"+player.chunkCoordX);
				
				
				int startY = y+16;
				if (startY>world.getHeight()){
					startY = world.getHeight();
				}
				
				//System.out.println("Scanning:"+x+" "+startY+" "+z);
				
				Chunk c = world.getChunkFromBlockCoords(x, z);
				
				int x0 = c.xPosition*16;
				int z0 = c.zPosition*16;
				
				ArrayList<String> foundTypes = new ArrayList<String>();
				
				//System.out.println("Chunk:"+x0 + " "+z0);
				boolean found = false;
				for (int iy=startY; iy>0; iy--){
					for (int ix =0;ix<16;ix++){
						for (int iz =0; iz<16;iz++){
							Block b = c.getBlock(ix, iy, iz);
							//System.out.println("iX:"+ix+" iZ"+iz);
							if (TGBlocks.isOrecluster(b)){
								int clusterMeta = c.getBlockMetadata(ix, iy, iz);
								String name = TextUtil.trans(b.getUnlocalizedName()+"_"+clusterMeta+".name");
								if (!foundTypes.contains(name)){
									foundTypes.add(name);
								}
								Techguns.proxy.createFX("OreClusterPing", world, x0+ix+0.5d, iy+0.5d, z0+iz+0.5d, 0, 0, 0);
								
								found=true;
								//if (world.isRemote) player.addChatMessage(new ChatComponentText("Found Ore Cluster at ("+(x0+ix)+"/"+iy+"/"+(z0+iz)+")."));
							}
						}
					}
				}
				if (found){
					player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.OreClusterScanner.msg.found")));
					for (int i=0; i<foundTypes.size(); i++){
						player.addChatMessage(new ChatComponentText(foundTypes.get(i)));
					}
					
				} else {
					player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.OreClusterScanner.msg.notFound")));
				}
			}
		}
	}



	
	


	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}





	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		int dur = (this.getMaxItemUseDuration(stack) - count);
		/*if (player.worldObj.isRemote && (dur %10 == 0)){
			
			player.addChatMessage(new ChatComponentText("Scanning: "+( (int)(Math.round((dur*1.0f)/(SCANDURATION*1.0f))*100.0f) )+"%"));
		}*/
		if (dur >=SCANDURATION){
			player.stopUsingItem();
		} /* else {
			
			int extracted = this.extractEnergy(stack, RF_PER_USETICK, false);
			System.out.println("Extracted:"+extracted);
			if (extracted < RF_PER_USETICK){
				System.out.println("Too less power, STOP USING");
				player.stopUsingItem();
			}
			
		}*/
	}





	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return Integer.MAX_VALUE;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1.0d-(this.getEnergyStored(stack)*1.0d)/(this.capacity*1.0d);
	}





	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		//this.receiveEnergy(stack, MAXRF, false);
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		stack.stackTagCompound.setInteger("Energy", MAXRF);
	}





	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		
		player.setItemInUse(item, this.getMaxItemUseDuration(item));
	
		return item;
	}




	/* IEnergyContainerItem */
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {

		if (container.stackTagCompound == null) {
			container.stackTagCompound = new NBTTagCompound();
		}
		int energy = container.stackTagCompound.getInteger("Energy");
		int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

		if (!simulate) {
			energy += energyReceived;
			container.stackTagCompound.setInteger("Energy", energy);
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {

		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Energy")) {
			return 0;
		}
		int energy = container.stackTagCompound.getInteger("Energy");
		int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
			container.stackTagCompound.setInteger("Energy", energy);
		}
		return energyExtracted;
	}

	@Override
	public int getEnergyStored(ItemStack container) {

		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Energy")) {
			return 0;
		}
		return container.stackTagCompound.getInteger("Energy");
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {

		return capacity;
}
	
	
}
