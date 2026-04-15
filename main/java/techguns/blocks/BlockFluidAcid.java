package techguns.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import techguns.TGFluids;
import techguns.Techguns;
import techguns.damagesystem.TGDamageSource;
import techguns.util.EntityDeathUtils.DeathType;

public class BlockFluidAcid extends BlockFluidClassic {

    protected IIcon stillIcon;
    protected IIcon flowingIcon;
	
	
	public BlockFluidAcid(Fluid fluid) {
		super(fluid, Material.water);
		this.setBlockName("creeperAcid");
		this.setBlockTextureName("techguns:acid_still");
		this.setBlockUnbreakable();
		this.setCreativeTab(Techguns.tabTechgun);
	}
	
	
	
	@Override
	public String getUnlocalizedName() {
		return "techguns."+super.getUnlocalizedName();
	}



	@Override
	public void registerBlockIcons(IIconRegister r) {
		stillIcon = r.registerIcon("techguns:acid_still");
		flowingIcon = r.registerIcon("techguns:acid_flow");

		//It works ....
		 TGFluids.ACID.setIcons(stillIcon, flowingIcon);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (side==0||side==1){
			return this.stillIcon;
		}
		return this.flowingIcon;
	}	
	
    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
        if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
        	return false;
        } else {
            return super.canDisplace(world, x, y, z);
        }
    }
   
    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z) {
        if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
        	return false;
        } else {
        	return super.displaceIfPossible(world, x, y, z);
        }
    }

	@Override
	public void onEntityCollidedWithBlock(World w, int x, int y, int z, Entity ent) {
		if (ent instanceof EntityLivingBase){
			TGDamageSource acidDamage = TGDamageSource.causePoisonDamage(null, null, DeathType.BIO);
			acidDamage.goreChance=1.0f;
			acidDamage.ignoreHurtresistTime=false;
			acidDamage.knockbackMultiplier=0.0f;
			ent.attackEntityFrom(acidDamage, 2.0f);
		}
	}
    
    
}
