package techguns.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import techguns.items.TGSlotType;
import techguns.items.armors.ITGSpecialSlot;
import techguns.items.guns.GenericGun;
import techguns.tileentities.TurretBaseEnt;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TurretBaseContainer extends Container {
	protected TurretBaseEnt tile;
	
	protected int lastRepairTime=0;
	protected boolean lastTurretDeath=false;
	//protected int lastPowerStored=0;
	
	public TurretBaseContainer(InventoryPlayer player, TurretBaseEnt ent) {
		this.tile = ent;	
		
		for (int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				this.addSlotToContainer(new Slot(ent, (i*3)+j, 18+18*i, 17+18*j));
			}
		}
		
		for (int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				this.addSlotToContainer(new Slot(ent, 9+(i*3)+j, 116+18*i, 17+18*j));
			}
		}
		
		this.addSlotToContainer(new SlotTechgun(ent, 18, 85, 19));
		
		this.addSlotToContainer(new SlotTurretArmor(ent, 19, 85, 42));
		
		/*this.addSlotToContainer(new Slot(ent, 0, 110, 17));
		this.addSlotToContainer(new Slot(ent, 1, 130, 17));
		
		this.addSlotToContainer(new Slot(ent, 2, 120, 60));*/
		// this.addSlotToContainer(new SlotFurnace(player.player, ent, 3, 116,
		// 35));
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
	public void addCraftingToCrafters(ICrafting craft) {
		super.addCraftingToCrafters(craft);
		craft.sendProgressBarUpdate(this, 0, this.tile.getRepairTime());
		craft.sendProgressBarUpdate(this, 1, this.tile.isTurretDeath()?1:0);
		craft.sendProgressBarUpdate(this, 2, this.tile.getPowerStored());
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		if (id == 0) {
			this.tile.repairTime = value;
		}

		if (id == 1) {
			this.tile.turretDeath = value!=0;
		}

		if (id == 2) {
			this.tile.setPowerStored(value);
		}

		/*
		 * if (id == 2) { this.tileFurnace.currentItemBurnTime = value; }
		 */
	}
	
	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if (this.lastRepairTime != this.tile.repairTime) {
				icrafting.sendProgressBarUpdate(this, 0, this.tile.repairTime);
			}

			if (this.lastTurretDeath != this.tile.turretDeath) {
				icrafting.sendProgressBarUpdate(this, 1, this.tile.turretDeath?1:0);
			}
			icrafting.sendProgressBarUpdate(this, 2, this.tile.getPowerStored());
			
			
			/*
			 * if (this.lastItemBurnTime !=
			 * this.tileFurnace.currentItemBurnTime) {
			 * icrafting.sendProgressBarUpdate(this, 2,
			 * this.tileFurnace.currentItemBurnTime); }
			 */
		}

		/*
		 * this.lastCookTime = this.tileFurnace.furnaceCookTime;
		 * this.lastBurnTime = this.tileFurnace.furnaceBurnTime;
		 * this.lastItemBurnTime = this.tileFurnace.currentItemBurnTime;
		 */
		this.lastRepairTime = this.tile.repairTime;
		this.lastTurretDeath = this.tile.turretDeath;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer ply) {
		return this.tile.isUseableByPlayer(ply);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotid) {
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotid);
		
		if(slot.getHasStack()){
			ItemStack stack1 = slot.getStack();
			stack=stack1.copy();
			if (stack != null){
		
				//machineslot pressed
				if(slotid<20){

					//put item back in player inventory
					if (!this.mergeItemStack(stack1, 20, 20+36, true)) {
						return null;
					}
					slot.onSlotChange(stack1, stack);
					
				} else if(slotid>=20) {
					//pressed in player gui
					
					if(stack.getItem() instanceof GenericGun){
						
						//put weapon in weaponslot
						if (!this.mergeItemStack(stack1, 18, 19, true)) {
							return null;
						}
						slot.onSlotChange(stack1, stack);
					} else if(stack.getItem() instanceof ITGSpecialSlot && (((ITGSpecialSlot)stack.getItem()).getSlot(stack)==TGSlotType.TURRETARMOR)){	
						//put in armorslot
						if (!this.mergeItemStack(stack1, 19, 20, true)) {
							return null;
						}
						slot.onSlotChange(stack1, stack);
					} else {
						//put item in input inventory
						if (!this.mergeItemStack(stack1, 0, 9, true)) {
							return null;
						}
						slot.onSlotChange(stack1, stack);
					}
				}
				
				if (stack1.stackSize == 0) {
					slot.putStack((ItemStack) null);
				} else {
					slot.onSlotChanged();
				}

				if (stack1.stackSize == stack.stackSize) {
					return null;
				}

				slot.onPickupFromSlot(player, stack1);
			}
		}
		return stack;
	}

	
	
}
