package techguns.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import techguns.TGArmorBonus;
import techguns.TGItems;
import techguns.Techguns;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.gui.playerinventory.TGPlayerInventory;
import techguns.items.armors.ITGSpecialSlot;
import techguns.util.MathUtil;
import techguns.util.TextUtil;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class GenericItemShared extends Item implements ITGSpecialSlot{
		
	public static String[] names = {"stonebullets","bullets9mm","pistolMag9mm","pistolMag9mmEmpty","shotgunShell","smgMag","smgMagEmpty","rifleBullets",
		"flamethrowerTank","flamethrowerTankEmpty","bioTank","bioTankEmpty","m4Mag","m4MagEmpty","rocketAmmo","energyCell","energyCellEmpty","as50Mag",
		"as50MagEmpty","highCalibreBullets","lmgMag","lmgMagEmpty","advBullets","pdwMag","pdwMagEmpty","stoneBarrel",
		"woodStock",
		"ironBarrel",
		"compressedAirTank",
		"compressedAirTankEmpty",
		"mechanicalParts",
		"plasticGunstock",
		"pumpMechanism",
		"obsidianSteelBarrel",
		"mechanicalPartsT2",
		"laserBarrel",
		"laserFocus",
		"mechanicalPartsT3",
		"bioMass",
		"heavyCloth",
		"ingotObsidianSteel",
		"plateTin",
		"plateCopper",
		"plateIron",
		"plateBronze",
		"plateSteel",
		"minigunDrum",
		"minigunDrum_empty",
		"turretArmorIron",
		"turretArmorSteel",
		"turretArmorObsidianSteel",
		"glider",
		"machineStackUpgrade",
		"ironReceiver",
		"steelReceiver",
		"obsidianSteelReceiver",
		"carbonReceiver",
		"wireCopper",
		"gliderWing",
		"gliderBackback",
		"tgx",
		"carbonFibers",
		"carbonPlate",
		"nethercharge",
		"electricEngine",
		"plateObsidianSteel",
		"turretArmorCarbon",
		"steamArmorPlate",
		"circuitBoard",
		"coil",
		"rawRubber",
		"rawPlastic",
		"plasticSheet",
		"rubberBar",
		"gasMaskFilter",
		"oreDrillSmallSteel",
		"oreDrillSmallObsidianSteel",
		"oreDrillSmallCarbon",
		"oreDrillMediumSteel",
		"oreDrillMediumObsidianSteel",
		"oreDrillMediumCarbon",
		"oreDrillLargeSteel",
		"oreDrillLargeObsidianSteel",
		"oreDrillLargeCarbon",
		"carbonGunstock",
		"carbonBarrel",
		"goldWire",
		"circuitBoardElite",
		"rifleBulletsStack",
		"powerPlating",
		"oxygenMask",
		"cyberneticParts",
		"uvemitter",
		"40mmGrenade",
		"oreTitanium",
		"ingotTitanium",
		"plateTitanium",
		"sonicEmitter",
		"rcHeatRay",
		"quartz_rod",
		"antiGravityCore",
		"yellowcake",
		"enrichedUranium",
		"tacticalNuclearWarhead",
		"plateLead",
		"rocketAmmoNuclear",
		"nuclearPowerCell",
		"nuclearPowerCellDepleted",
		"radEmitter",
		"treatedLeather"};
	
	public TGSlotType[] slotType = new TGSlotType[names.length];
	
	public IIcon[] icons = new IIcon[names.length];
	
	public short[] maxStackSize = new short[names.length];
	
	protected ArrayList<Integer> magazineTooltip = new ArrayList<Integer>();
	
	public GenericItemShared() {
		// Constructor Configuration
		setMaxStackSize(64);
		setCreativeTab(Techguns.tabTechgun);
		//setUnlocalizedName("techgunsAmmo");
		setTextureName("techguns:testMag");
		setHasSubtypes(true);
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		for (int i=0;i<names.length;i++){
			this.icons[i]=iconRegister.registerIcon("techguns:"+names[i]);
			//System.out.println("ICON: techguns:"+names[i]);
		}
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab,
			List list) {
		for(int i=0;i<names.length;i++){
			list.add(new ItemStack(item,1,i));
		}
	}

	
	public void addMagazineTooltip(int damageValue){
		this.magazineTooltip.add(damageValue);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		if (stack!=null){
			if (stack.getItemDamage()>=this.maxStackSize.length){
				System.out.println("Techguns GenericItem has bad metadata:"+stack.getItemDamage()+" Max is:"+(this.maxStackSize.length-1));
				return 64;
			}
			return this.maxStackSize[stack.getItemDamage()];
		}
		return super.getItemStackLimit(stack);
	}

	@Override
	public IIcon getIconFromDamage(int damage) {
		if(damage<this.icons.length){
			return this.icons[damage];
		}
		return this.icons[0];
	}

	@Override
	public String getUnlocalizedName(ItemStack item) {
		return "techguns.item."+names[item.getItemDamage()];
	}

	@Override
	public TGSlotType getSlot(ItemStack item) {
		if (item!=null && item.getItem()==TGItems.sharedItem){
			return slotType[item.getItemDamage()];
		}
		return TGSlotType.NORMAL;
	}

	@Override
	public void onPlayerTick(ItemStack item, PlayerTickEvent event, TechgunsExtendedPlayerProperties extendedPlayer) {
		if (item.getItemDamage()==TGItems.glider.getItemDamage()){
			onPlayerTickGlider(item, event, extendedPlayer);
		}/* else if(item.getItemDamage()==TGItems.nightVisionGoggles.getItemDamage()){
			this.onPlayerTickNightVisionGoggles(item, event, extendedPlayer);
		}*/
	}
	
	public static void onPlayerTickGlider(ItemStack item, PlayerTickEvent event, TechgunsExtendedPlayerProperties extendedPlayer){
		//System.out.println("OnGround:"+event.player.onGround);
		if (!event.player.onGround && event.player.isSneaking() ){
			if(event.player.motionY < -0.1f){
				
				extendedPlayer.isGliding=true;
				
				/**
				 * totally stolen from modular powersuits glider code
				 */
				Vec3 lookVec = event.player.getLookVec();
				MathUtil.Vec2 playerMoveVec =new MathUtil.Vec2(lookVec.xCoord, lookVec.zCoord);
				playerMoveVec.normalize();
				
				if (event.player.motionY < -0.1f) {
	                double motion = Math.min(0.08, -0.1 - event.player.motionY);
	                event.player.motionY += motion;
	                event.player.motionX += playerMoveVec.x * motion;
	                event.player.motionZ += playerMoveVec.y * motion;
	
	               event.player.jumpMovementFactor += 0.03f;
	            }
			}
            event.player.fallDistance=0;
		
		}	
			
	}

	/*private void onPlayerTickNightVisionGoggles(ItemStack item, PlayerTickEvent event, TechgunsExtendedPlayerProperties extendedPlayer){
		event.player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 220, -1));
	}*/
	
	
	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean p_77624_4_) {
		super.addInformation(item, player, list, p_77624_4_);
		int meta = item.getItemDamage();
		
		if (meta == TGItems.oreDrillSmallSteel.getItemDamage()){
			list.add(TextUtil.trans("techguns.oreDrillTooltip.size")+" 1x1x1");
			list.add(TextUtil.trans("techguns.oreDrillTooltip.mininglevel")+": 1");
		}
		else if (meta == TGItems.oreDrillSmallObsidianSteel.getItemDamage()){
			list.add(TextUtil.trans("techguns.oreDrillTooltip.size")+" 1x1x1");
			list.add(TextUtil.trans("techguns.oreDrillTooltip.mininglevel")+": 2");
		}
		else if (meta == TGItems.oreDrillSmallCarbon.getItemDamage()){
			list.add(TextUtil.trans("techguns.oreDrillTooltip.size")+" 1x1x1");
			list.add(TextUtil.trans("techguns.oreDrillTooltip.mininglevel")+": 3");
		}
		else if (meta == TGItems.oreDrillMediumSteel.getItemDamage()){
			list.add(TextUtil.trans("techguns.oreDrillTooltip.size")+" 1x1xN or 3x3xN");
			list.add(TextUtil.trans("techguns.oreDrillTooltip.mininglevel")+": 1");
		}
		else if (meta == TGItems.oreDrillMediumObsidianSteel.getItemDamage()){
			list.add(TextUtil.trans("techguns.oreDrillTooltip.size")+" 1x1xN or 3x3xN");
			list.add(TextUtil.trans("techguns.oreDrillTooltip.mininglevel")+": 2");
		}
		else if (meta == TGItems.oreDrillMediumCarbon.getItemDamage()){
			list.add(TextUtil.trans("techguns.oreDrillTooltip.size")+" 1x1xN or 3x3xN");
			list.add(TextUtil.trans("techguns.oreDrillTooltip.mininglevel")+": 3");
		}
		else if (meta == TGItems.oreDrillLargeSteel.getItemDamage()){
			list.add(TextUtil.trans("techguns.oreDrillTooltip.size")+" 5x5xN or 7x7xN");
			list.add(TextUtil.trans("techguns.oreDrillTooltip.mininglevel")+": 1");
		}
		else if (meta == TGItems.oreDrillLargeObsidianSteel.getItemDamage()){
			list.add(TextUtil.trans("techguns.oreDrillTooltip.size")+" 5x5xN or 7x7xN");
			list.add(TextUtil.trans("techguns.oreDrillTooltip.mininglevel")+": 2");
		}
		else if (meta == TGItems.oreDrillLargeCarbon.getItemDamage()){
			list.add(TextUtil.trans("techguns.oreDrillTooltip.size")+" 5x5xN or 7x7xN");
			list.add(TextUtil.trans("techguns.oreDrillTooltip.mininglevel")+": 3");
		}
		else if (this.magazineTooltip.contains(meta)){
			list.add(TextUtil.trans("techguns.item.ammotooltip.ammobag"));
			list.add(TextUtil.trans("techguns.item.ammotooltip.empty"));
		}
	}

	@Override
	public float getBonus(TGArmorBonus type, ItemStack stack, boolean consume, EntityPlayer player) {
		if(type == TGArmorBonus.OXYGEN_GEAR){
			if(stack.getItemDamage() == TGItems.oxygenMask.getItemDamage()){
				return 1.0f;
			}
		}
		return 0.0f;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World w, EntityPlayer player) {
		
		if (player.isSneaking()){
			if(stack.getItemDamage()==TGItems.pistolMag9mm.getItemDamage()){
				if (stack.stackSize>=1){
					Techguns.addAmmoToPlayerInventory(player, TGItems.newStack(TGItems.pistolMag9mmEmtpy,1));
					Techguns.addAmmoToPlayerInventory(player, TGItems.newStack(TGItems.bullets9mm,3));
					stack.stackSize--;
					return stack;
				}
			} else if (stack.getItemDamage()==TGItems.m4Mag.getItemDamage()) {
				if (stack.stackSize>=1){
					Techguns.addAmmoToPlayerInventory(player, TGItems.newStack(TGItems.m4MagEmpty,1));
					Techguns.addAmmoToPlayerInventory(player, TGItems.newStack(TGItems.rifleBullets,3));
					stack.stackSize--;
					return stack;
				}
			} else if (stack.getItemDamage()==TGItems.pdwMag.getItemDamage()) {
				if (stack.stackSize>=1){
					Techguns.addAmmoToPlayerInventory(player, TGItems.newStack(TGItems.pdwMagEmpty,1));
					Techguns.addAmmoToPlayerInventory(player, TGItems.newStack(TGItems.advBullets,3));
					stack.stackSize--;
					return stack;
				}
			} else if (stack.getItemDamage()==TGItems.smgMag.getItemDamage()) {
				if (stack.stackSize>=1){
					Techguns.addAmmoToPlayerInventory(player, TGItems.newStack(TGItems.smgMagEmpty,1));
					Techguns.addAmmoToPlayerInventory(player, TGItems.newStack(TGItems.bullets9mm,2));
					stack.stackSize--;
					return stack;
				}
			}else if (stack.getItemDamage()==TGItems.as50Mag.getItemDamage()) {
				if (stack.stackSize>=1){
					Techguns.addAmmoToPlayerInventory(player, TGItems.newStack(TGItems.as50MagEmpty,1));
					Techguns.addAmmoToPlayerInventory(player, TGItems.newStack(TGItems.highCalibreBullets,2));
					stack.stackSize--;
					return stack;
				}
			} else if (stack.getItemDamage()==TGItems.minigunMag.getItemDamage()) {
				if (stack.stackSize>=1){
					Techguns.addAmmoToPlayerInventory(player, TGItems.newStack(TGItems.minigunMagEmpty,1));
					Techguns.addAmmoToPlayerInventory(player, TGItems.newStack(TGItems.rifleBulletsStack,4));
					stack.stackSize--;
					return stack;
				}
			}else if (stack.getItemDamage()==TGItems.lmgMag.getItemDamage()) {
				if (stack.stackSize>=1){
					Techguns.addAmmoToPlayerInventory(player, TGItems.newStack(TGItems.lmgMagEmpty,1));
					Techguns.addAmmoToPlayerInventory(player, TGItems.newStack(TGItems.rifleBulletsStack,2));
					stack.stackSize--;
					return stack;
				}
			}
		} else if(slotType[stack.getItemDamage()]==TGSlotType.AMMOSLOT) {
			
			
			//int stacksize_old = stack.stackSize;
			
			TechgunsExtendedPlayerProperties props  = TechgunsExtendedPlayerProperties.get(player);
			ItemStack newStack = stack.copy();
			int amount = Techguns.addItemToInventory(props.TG_inventory.inventory, newStack, TGPlayerInventory.SLOTS_AMMO_START,  TGPlayerInventory.SLOTS_AMMO_END+1);
			
			stack.stackSize = amount;
			
			
		} else if(slotType[stack.getItemDamage()]==TGSlotType.BACKSLOT) {
			TechgunsExtendedPlayerProperties props  = TechgunsExtendedPlayerProperties.get(player);
			if (props.TG_inventory.inventory[TGPlayerInventory.SLOT_BACK]==null){
				props.TG_inventory.inventory[TGPlayerInventory.SLOT_BACK] = stack.copy();
				stack.stackSize=0;
			}
		}
		
		return super.onItemRightClick(stack, w, player);
	}

	
}