package techguns.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemMedkit extends GenericItem {

	String name;
	int maxMeta;
	IIcon[] icons;
	int[] dur;
	float[] healAmount;
	int[] regDur;
	int[] regLvl;
	
	public ItemMedkit(String name, int amount) {
		super(name);
		setMaxStackSize(16);
		icons = new IIcon[amount];
		maxMeta = amount-1;
		this.name=name;
		dur = new int[]{60,50};
		healAmount = new float[]{2f,6f};
		regDur = new int[]{300,400};
		regLvl = new int[]{0,1};
	}

	@Override
	public String getUnlocalizedName(ItemStack item) {
		return super.getUnlocalizedName()+"_"+item.getItemDamage();
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		for (int i=0;i<=maxMeta;i++){
			this.icons[i]=iconRegister.registerIcon("techguns:"+name+"_"+i);
		}
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab,
			List list) {
		for(int i=0;i<=maxMeta;i++){
			list.add(new ItemStack(item,1,i));
		}
	}

	@Override
	public IIcon getIconFromDamage(int damage) {
		if(damage<this.icons.length){
			return this.icons[damage];
		}
		return this.icons[0];
	}

	
	/**
     * How long it takes to use or consume an item
     */
	@Override
    public int getMaxItemUseDuration(ItemStack item)
    {
        return this.dur[item.getItemDamage()];
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
	@Override
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.none;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
	@Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
				
        if (player.getHealth() < player.getMaxHealth())
        {
            player.setItemInUse(item, this.getMaxItemUseDuration(item));
        }
        
        return item;
    }
	
	

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		super.onUsingTick(stack, player, count);
		
		if (count % 7 == 0){
			player.playSound("random.eat", 0.5F + 0.5F, 0.2F + 1.0F);
			player.swingItem();
		}
	}

	public ItemStack onEaten(ItemStack item, World world, EntityPlayer player)
    {
        --item.stackSize;
        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);

        this.onConsumed(item, world, player);
        
        return item;
    }
	
    public void onConsumed(ItemStack item, World world, EntityPlayer player){
    	int meta = item.getItemDamage();
    	player.heal(this.healAmount[meta]);
    	 if (!world.isRemote)
         {
             player.addPotionEffect(new PotionEffect(Potion.regeneration.id, regDur[meta], regLvl[meta],true));
         }
    }
    
    
}
