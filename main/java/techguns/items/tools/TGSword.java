package techguns.items.tools;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import techguns.Techguns;
import techguns.damagesystem.DamageType;
import techguns.damagesystem.IToolTGDamageSystem;
import techguns.damagesystem.TGDamageSource;
import techguns.util.EntityDeathUtils.DeathType;

public class TGSword extends ItemSword implements IToolTGDamageSystem{

	protected DamageType dmgType=DamageType.PHYSICAL;
	protected float penetration=0.0f;
	
	public TGSword(ToolMaterial mat, String name) {
		super(mat);
		setCreativeTab(Techguns.tabTechgun);
		setUnlocalizedName(name);
		setTextureName(Techguns.MODID+":"+name);
	}
	
	public TGSword(ToolMaterial mat, String name, DamageType dmgType,float penetration) {
		this(mat,name);
		this.dmgType=dmgType;
		this.penetration=penetration;
	}

	
	
	@Override
	public String getUnlocalizedName(ItemStack s) {
		return "techguns."+super.getUnlocalizedName(s);
	}

	@Override
	public TGDamageSource getDamageSource(DamageSource original) {
		TGDamageSource src = new TGDamageSource(original.damageType,original.getEntity(),original.getSourceOfDamage(),this.dmgType, DeathType.GORE);
		src.ignoreHurtresistTime=false;
		src.goreChance=0.25f;
		return src;
	}


	@Override
	public void addInformation(ItemStack item, EntityPlayer player,
			List list, boolean b) {
		super.addInformation(item, player, list, b);
			list.add("DamageType: "+this.dmgType.toString());
			if (this.penetration>0.0f){
				list.add("ArmorPen: "+this.penetration*100+"%");
			}

	}
}
