package techguns.tileentities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import techguns.TGItems;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.entities.npc.NPCTurret;
import techguns.items.guns.GenericGun;
import techguns.packets.RequestTileEntitySync;
import techguns.util.ItemUtil;

public class TurretBaseEnt extends BasicPoweredTileEnt {
	
	public NPCTurret mountedTurret =null;
	
	protected ItemStack lastWeapon=null;
	
	private long lastRequest=0;
	
	
	public boolean turretDeath=false;
	public int repairTime=0;
	public int turretHealTime = 0;
		
	public boolean attackAnimals=false;
	
	public TurretBaseEnt() {
		super(20,1000);
		//9 slots input, 9 slots output, 1 weaponslot, 1 armorslot
	}

	public void spawnTurret(World w,int x,int y,int z){
		
		if (!w.isRemote){
			this.mountedTurret = new NPCTurret(w, this);
			this.mountedTurret.setPosition(x+0.5d, y+1, z+0.5d);
			w.spawnEntityInWorld(this.mountedTurret);
			//this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			//this.markDirty();
		}
	}
	
	public void spawnTurret(World w,int x,int y,int z,float health){
		
		if (!w.isRemote){
			this.mountedTurret = new NPCTurret(w, this);
			this.mountedTurret.setPosition(x+0.5d, y+1, z+0.5d);
			this.mountedTurret.setHealth(health);
			w.spawnEntityInWorld(this.mountedTurret);
			//this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			//this.markDirty();
		}
	}

	public ResourceLocation getTurretBaseTexture(){
		return NPCTurret.getTexture(this.getTurretArmorValue());
	}
	
	public int getTurretArmorValue(){
		ItemStack armor = this.content[19];
		if (armor !=null){
			if(ItemUtil.isItemEqual(armor,TGItems.turretArmorIron)) {
				return 5;
			} else if (ItemUtil.isItemEqual(armor,TGItems.turretArmorSteel)) {
				return 10;
			} else if (ItemUtil.isItemEqual(armor,TGItems.turretArmorObsidianSteel)) {
				return 15;
			} else if (ItemUtil.isItemEqual(armor,TGItems.turretArmorCarbon)) {
				return 20;
			}
		}
		return 0;
	}
	
	@Override
	public void readEntityDataFromNBT(NBTTagCompound tags) {
		super.readEntityDataFromNBT(tags);
		if(tags.hasKey("mountedTurret")){
			//System.out.println("read Turret:"+tags.getInteger("mountedTurret"));
			if (this.worldObj!=null){
				Entity ent = this.worldObj.getEntityByID(tags.getInteger("mountedTurret"));
				if (ent !=null){
					this.mountedTurret=(NPCTurret) ent;
					this.mountedTurret.setTileEnt(this);
					//System.out.println("Set tileent for mounted turret");
				} else {
//					System.out.println("Link failed, entity still null");
				}
			} else {
//				System.out.println("Link failed, world is null");
			}
			//System.out.println("Turret:"+this.mountedTurret);
		}else{
			this.mountedTurret=null;
		}
		
		this.turretDeath=tags.getBoolean("turretDeath");
		this.repairTime=tags.getInteger("repairTime");
		this.turretHealTime=tags.getInteger("turretHealTime");
		this.attackAnimals=tags.getBoolean("attackAnimals");
	}

	@Override
	public void writeEntityDataToNBT(NBTTagCompound tags) {
		super.writeEntityDataToNBT(tags);
		if(this.mountedTurret!=null){
			tags.setInteger("mountedTurret", this.mountedTurret.getEntityId());
			//System.out.println("wrote Turret:"+this.mountedTurret.getEntityId());
		}
		tags.setBoolean("turretDeath", this.turretDeath);
		tags.setInteger("repairTime", this.repairTime);
		tags.setInteger("turretHealTime",this.turretHealTime);
		tags.setBoolean("attackAnimals", this.attackAnimals);
	}

	public boolean hasMountedTurret(){
		return this.mountedTurret!=null;
	}
	
	/**
	 * Returns an ItemStack of a GenericGun or null
	 * @return
	 */
	public ItemStack getWeapon(){
		if ((content[18]!=null) && (content[18].getItem() instanceof GenericGun)){
			return content[18];
		}
		return null;
	}
	
	public boolean consumeAmmo(){
		
		ItemStack gunstack = this.getWeapon();
		if (gunstack!=null){
			GenericGun gun = (GenericGun) gunstack.getItem();
			
			if (gun.getCurrentAmmo(gunstack)<=0){
				this.doReload(gunstack);
			}
			
			
			if (gun.getCurrentAmmo(gunstack)>=1){
				gun.useAmmo(gunstack, 1);
				return true;
			}
			

			
			/*
			if(gunstack.getItemDamage()>=gun.getMaxDamage()){
				
				//try a reload
				//System.out.println("need reload");
				this.doReload(gunstack);
			}
			
			if(gunstack.getItemDamage()<=gun.getMaxDamage()){
				gunstack.setItemDamage(gunstack.getItemDamage()+1);
				return true;
			} else {
				return false;
			}
			*/
		}
		
		
		
		
		return false;
	}
	
	protected void doReload(ItemStack gunstack){
		GenericGun gun = (GenericGun) gunstack.getItem();
		ItemStack ammo = gun.getAmmo();
		ItemStack emptyMag = gun.getEmptyMag();
		
		//ItemStack[] ammoInv=new ItemStack[9];
		//ItemStack[] emptyAmmoInv = new ItemStack[9];
		/*for(int i=0;i<9;i++){
			ammoInv[i]=content[i];
			emptyAmmoInv[i]=content[i+9];
		}*/
		
		if (Techguns.consumeAmmo(this.content, ammo,0,9)){
			if (emptyMag!=null){
				int tooMuch=Techguns.addItemToInventory(this.content,TGItems.newStack(emptyMag,1),9,18);
				if (!this.worldObj.isRemote){
					this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord+1, this.zCoord, TGItems.newStack(emptyMag, tooMuch)));
				}
			}
	    			
			if (gun.getAmmoCount() >1) {
				int i =1;
				while (i<gun.getAmmoCount() && Techguns.consumeAmmo(this.content,ammo,0,9)){
					i++;
				}
				
				gun.reloadAmmo(gunstack,i);
				//gunstack.setItemDamage(gun.getAmmoCount()-i);	    				
			} else {
				gun.reloadAmmo(gunstack);
				//gunstack.setItemDamage(0);
			}
			
			if (!this.worldObj.isRemote){
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
				this.markDirty();
			}
		}
		
	}
	
	public boolean isTurretDeath() {
		return turretDeath;
	}

	public int getRepairTime() {
		return repairTime;
	}

	@Override
	public void updateEntity() {
		//super.updateEntity();
		//System.out.println("I AM THE TABLE");
		if (!this.worldObj.isRemote){
			if (this.mountedTurret!=null){
				if (this.mountedTurret.getHeldItem()!= this.lastWeapon){
					this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
					this.markDirty();
					this.lastWeapon=this.mountedTurret.getHeldItem();
					this.mountedTurret.setCombatTask();
					//System.out.println("weapon changed!");
				}
			}else {
				//System.out.println("I AM THE TABLE");
			}
		} else {
			//System.out.println("mounted turret:"+this.mountedTurret);
			if (this.mountedTurret==null && !this.turretDeath){
				if((Minecraft.getSystemTime()-this.lastRequest)>1000){
					TGPackets.network.sendToServer(new RequestTileEntitySync(this.xCoord, (short) this.yCoord, this.zCoord));
					this.lastRequest=Minecraft.getSystemTime();
	//				System.out.println("Sent request for sync");
				}
			} else if (this.mountedTurret!=null && this.mountedTurret.mountedTileEnt==null){
				//System.out.println("mounted tilent:"+this.mountedTurret.mountedTileEnt);
				this.mountedTurret.setTileEnt(this);
			}
		}
		
		
		//Normal turret logic
		if (this.turretDeath){
			if (this.isRedstoneEnabled()){
				if (this.consumePower(50)){
					this.repairTime--;
				}
				
				if(repairTime <=0){
					repairTime=0;
					this.turretDeath=false;
					
					if (!this.worldObj.isRemote){
						spawnTurret(this.worldObj,this.xCoord, this.yCoord, this.zCoord,10.0f);
						this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
						this.markDirty();
					}
				}
			}
			
		} else if (this.mountedTurret!=null){
			
			if(this.isRedstoneEnabled()){
			
				if(!this.consumePower(5)){
					//power off turret
					if (this.mountedTurret.active){
						this.mountedTurret.disable();
						this.mountedTurret.active=false;
					}
					
				} else if (!this.mountedTurret.active){
					//activate turret
					this.mountedTurret.active=true;
					this.mountedTurret.setAITasks();
					this.mountedTurret.setCombatTask();
				}
				
				if(this.turretHealTime<=0){
					if(this.mountedTurret.getHealth()<this.mountedTurret.getMaxHealth()){
						if (this.consumePower(500)){
							this.mountedTurret.heal(1.0f);
							this.turretHealTime=20*3;
						}
					}
				} else {
					this.turretHealTime--;
				}
			} else {
				
				if (this.mountedTurret.active){
					this.mountedTurret.disable();
					this.mountedTurret.active=false;
				}
				
			}
		}
	}
	
	



	/**
	 * @return the mountedTurret
	 */
	public NPCTurret getMountedTurret() {
		return mountedTurret;
	}

	/**
	 * @param mountedTurret the mountedTurret to set
	 */
	public void setMountedTurret(NPCTurret mountedTurret) {
		this.mountedTurret = mountedTurret;
	}	
	
	/**
	 * Called from the turret npc when he dies
	 */
	public void onTurretDeath(){
		if (!this.worldObj.isRemote){
			this.mountedTurret=null;
			this.turretDeath=true;
			this.repairTime=10*20;
			//System.out.println("Turret Death");
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			this.markDirty();
		}
	}
	
	/**
	 * called when base block is broken
	 */
	public void breakTurret(){
		if (this.mountedTurret!= null){
			this.mountedTurret.setHealth(0);
			this.mountedTurret.setDead();
		}
	}


	@Override
	public boolean canConnectEnergy(ForgeDirection dir) {
		if(dir != ForgeDirection.UP) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if (side==0){
			return new int[]{};
		}
		return new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item,
			int side) {
		return side!=0 && (slot<9) ;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item,
			int side) {
		return (side!=0) && (slot>=9 && slot <18);
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.name : "techguns.container.turretbase";
	}

	public int getRepairTimeScaled(int max){
		//TODO: not un-hardcode repairtime
		int totaltime = (10*20);
		return max-(this.repairTime*max/totaltime);
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		if (slot ==18){
			return item.getItem() instanceof GenericGun;
		} else {
			return true;
		}
	}

	@Override
	public void buttonClicked(int id, EntityPlayer ply) {
		super.buttonClicked(id, ply);
		if ((id==3) && (this.isUseableByPlayer(ply))){
			this.attackAnimals=!this.attackAnimals;
			if(!this.worldObj.isRemote){
				this.needUpdate();
			}
		} 
	}
	
}