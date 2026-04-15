package techguns.items.guns;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import techguns.Techguns;
import techguns.damagesystem.DamageType;
import techguns.entities.projectiles.GrenadeProjectile;
import techguns.util.TextUtil;

/**
 * Base class for Grenades, based on default minecraft bow
 */
public class GenericGrenade extends Item {
	private int maxUseDur;
	
	public float damage=20.0f;
	public float radius=5.0f;
	public int maxBounces=3;
	
	public GenericGrenade(String name,int stacksize,int maxUseDur) {
		setMaxStackSize(stacksize);
		setNoRepair();
		setCreativeTab(Techguns.tabTechgun);
		setUnlocalizedName(name);
		this.maxUseDur=maxUseDur;
	}
	
	
	
    @Override
	public String getUnlocalizedName(ItemStack s) {
		return "techguns."+super.getUnlocalizedName(s);
	}

	/**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
       /* ArrowNockEvent event = new ArrowNockEvent(player, item);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return event.result;
        }*/

       // if (player.capabilities.isCreativeMode || player.inventory.hasItem(Items.arrow))
       // {
            player.setItemInUse(item, this.getMaxItemUseDuration(item));
            //System.out.println("START_USE_GRENADE");
       // }

        return item;
    }
	
    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack item, World world, EntityPlayer player, int itemInUseCount)
    {
        int j = this.getMaxItemUseDuration(item) - itemInUseCount;

        //System.out.println("itemInUseCount:"+itemInUseCount);
        
       /* ArrowLooseEvent event = new ArrowLooseEvent(player, item, j);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return;
        }
        j = event.charge;*/

       /* boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0;

        if (flag || player.inventory.hasItem(Items.arrow))
        {*/
            float f = (float)j / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;

           /* if ((double)f < 0.1D)
            {
                return;
            }*/

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            //EntityArrow entityarrow = new EntityArrow(world, player, f * 2.0F);

            
            //item.damageItem(1, player);
            
            /*if (flag)
            {
                entityarrow.canBePickedUp = 2;
            }
            else
            {
                player.inventory.consumeInventoryItem(Items.arrow);
            }*/
            
            if (!player.capabilities.isCreativeMode){
            	player.inventory.consumeInventoryItem(item.getItem());
            }
            
            if (!world.isRemote)
            {
            	//System.out.println("THROW NADE: "+f);
                //world.spawnEntityInWorld(entityarrow);
            	spawnGrenade(world,player,f);
            }
        	player.swingItem();
        //}
    }
    
    
    
    
    public void spawnGrenade(World world, EntityPlayer player,float charge){
    	float grav = (0.015f/charge);
    	//world.spawnEntityInWorld(new SimpleProjectile(world, player, 10, 1.0f, 30, "techguns:textures/entity/handgunBullet.png", 1.0f, grav, 0.05f, 20,30, 5, 0.0f));
    	//world.spawnEntityInWorld(new RocketProjectile(world, player, 20, 10, 1.0f, 80, 0.05f, grav, false, 0.0f));
    	world.spawnEntityInWorld(new GrenadeProjectile(world, player, this.damage,this.radius,0.5f+0.5f*charge,200,0.05f,grav,false,0.0f,this.maxBounces));
    	world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);
    }
    
    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack item)
    {
        return 72000;
    }
    
    protected DamageType getDamageType(){
    	return DamageType.EXPLOSION;
    }
    
    @Override
	public void addInformation(ItemStack item, EntityPlayer player,
			List list, boolean b) {
		super.addInformation(item, player, list, b);
		//if (player.) {
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)){
			list.add(TextUtil.trans("techguns.gun.tooltip.damageType")+": " +this.getDamageType().toString());
			list.add(TextUtil.trans("techguns.gun.tooltip.damage")+": "+this.damage);
			list.add(TextUtil.trans("techguns.gun.tooltip.radius")+": "+this.radius);
			list.add(TextUtil.trans("techguns.gun.tooltip.bounces")+": "+this.maxBounces);
		} else {
			list.add(TextUtil.trans("techguns.gun.tooltip.damage")+": "+this.damage);
			list.add(TextUtil.trans("techguns.gun.tooltip.shift1")+" "+EnumChatFormatting.GREEN+TextUtil.trans("techguns.gun.tooltip.shift2")+" "+EnumChatFormatting.GRAY+TextUtil.trans("techguns.gun.tooltip.shift3"));
		}
		//} else {
		//	list.add("Sneak to view stats");
		//}
	}
}
