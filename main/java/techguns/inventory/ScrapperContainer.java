package techguns.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import techguns.gui.SlotOutput;
import techguns.tileentities.ScrapperTileEnt;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ScrapperContainer extends Container {
	protected ScrapperTileEnt tile;
	private int lastProgress = 0;
	private int lastTotaltime = 0;
	private int lastPowerStored = 0;


	public ScrapperContainer(InventoryPlayer player, ScrapperTileEnt ent) {
		this.tile = ent;
		
		this.addSlotToContainer(new Slot(ent, 0, 35, 17));
		
		for (int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				this.addSlotToContainer(new SlotOutput(ent, 1+(i*3)+j, 116+18*i, 17+18*j));
			}
		}
		

		int i;

		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9,
						8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
		}
		
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
	}

	@Override
	public void addCraftingToCrafters(ICrafting craft) {
		super.addCraftingToCrafters(craft);
		craft.sendProgressBarUpdate(this, 0, this.tile.progress);
		craft.sendProgressBarUpdate(this, 1, this.tile.totalTime);
		craft.sendProgressBarUpdate(this, 2, this.tile.getPowerStored());
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if (this.lastProgress != this.tile.progress) {
				icrafting.sendProgressBarUpdate(this, 0, this.tile.progress);
			}

			if (this.lastTotaltime != this.tile.totalTime) {
				icrafting.sendProgressBarUpdate(this, 1, this.tile.totalTime);
			}

			if ((this.lastPowerStored != this.tile.getPowerStored())||this.tile.isWorking()){
				icrafting.sendProgressBarUpdate(this, 2, this.tile.getPowerStored());
			}

		}

		this.lastProgress = this.tile.progress;
		this.lastTotaltime = this.tile.totalTime;
		this.lastPowerStored = this.tile.getPowerStored();
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		if (id == 0) {
			this.tile.progress = value;
		}

		if (id == 1) {
			this.tile.totalTime = value;
		}
		if(id==2){
			this.tile.setPowerStored(value);
		}

	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer ply, int id) {
		// TODO Auto-generated method stub
		return super.transferStackInSlot(ply,id);
	}
	
	
}
