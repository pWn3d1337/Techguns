package techguns.blocks;

import java.util.List;
import java.util.Random;

import techguns.TGItems;
import techguns.Techguns;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class OreCluster extends GenericBlock {
	private static int numclusters=0;
	private int id;
	
	public IIcon bedrockTex;
	public IIcon[] texture = new IIcon[3];
	public IIcon[] overlay_texture = new IIcon[4];
	
	public int numOres=0;
	public ItemStack[] ores = new ItemStack[16];
	public int[] mininglevels= new int[16];
	public float[][]colors = new float[16][3];
	public int[] textureType = new int[16];
	public int[] rarity = new int[16];
	public int[] maxy = new int[16];
	public FluidStack[] fluids = new FluidStack[16];
	
	public boolean[] isBedrock = new boolean[16];
	
	public double[] multipliersAmount;
	public double[] multipliersPower;
	
	public OreCluster(String blockname, double[] multipliersAmount, double[] multipliersPower) {
		super(Material.rock);
		this.setBlockName(blockname);
		this.setStepSound(soundTypeStone);
		this.setHardness(-1.0f);
		this.setResistance(6000000);
		this.multipliersAmount = multipliersAmount;
		this.multipliersPower = multipliersPower;
		this.isBedrock = new boolean[]{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
	}
	
	
	public OreCluster(String blockname) {
		super(Material.rock);
		this.setBlockName(blockname);
		this.setStepSound(soundTypeStone);
		this.setHardness(-1.0f);
		this.setResistance(6000000);
		this.multipliersAmount = new double[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		this.multipliersPower = new double[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		this.isBedrock = new boolean[]{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
	}

	@Override
	public int damageDropped (int metadata) {
		return metadata;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		/*texture[0] = reg.registerIcon("techguns:oreCluster");
		overlay_texture[0] = reg.registerIcon("techguns:oreCluster1");
		texture[1] = reg.registerIcon("techguns:oreCluster_1");
		overlay_texture[1] = reg.registerIcon("techguns:oreCluster_11");
		texture[2] = reg.registerIcon("techguns:oreCluster_2");
		overlay_texture[2] = reg.registerIcon("techguns:oreCluster_21");
		texture[3] = reg.registerIcon("techguns:oreCluster_3");
		overlay_texture[3] = reg.registerIcon("techguns:oreCluster_31");
		texture[4] = reg.registerIcon("techguns:oreCluster_4");
		overlay_texture[4] = reg.registerIcon("techguns:oreCluster_41");
		texture[5] = reg.registerIcon("techguns:oreCluster_5");
		overlay_texture[5] = reg.registerIcon("techguns:oreCluster_21");*/
		bedrockTex = reg.registerIcon("minecraft:bedrock");
		
		texture[0] = reg.registerIcon("minecraft:stone");
		texture[1] = reg.registerIcon("minecraft:netherrack");
		texture[2] = reg.registerIcon("minecraft:end_stone");
		
		overlay_texture[0] = reg.registerIcon("techguns:oreClusterNew1");
		overlay_texture[1] = reg.registerIcon("techguns:oreClusterNew2");
		overlay_texture[2] = reg.registerIcon("techguns:oreClusterNew3");
		overlay_texture[3] = reg.registerIcon("techguns:oreClusterNew4");
	}
	
	public void addOre(ItemStack ore, int level, float r, float g, float b, int tex,int rarity, int maxY, boolean bedrock){
		this.isBedrock[numOres]=bedrock;
		this.addOre(ore, level, r, g, b, tex, rarity, maxY);
	}
	
	public void addOre(ItemStack ore, int level, float r, float g, float b, int tex,int rarity, int maxY){
		this.ores[numOres]=ore;
		this.mininglevels[numOres]=level;
		this.colors[numOres][0]=r;
		this.colors[numOres][1]=g;
		this.colors[numOres][2]=b;
		this.textureType[numOres]=tex;
		this.rarity[numOres]=rarity;
		this.maxy[numOres]=maxY;
		this.fluids[numOres]=null;
		numOres++;
	}
	
	public void addLiquid(FluidStack liquid, int level, float r, float g, float b, int tex,int rarity, int maxY, boolean bedrock){
		this.isBedrock[numOres]=bedrock;
		this.addLiquid(liquid, level, r, g, b, tex, rarity, maxY);
	}
	
	public void addLiquid(FluidStack liquid, int level, float r, float g, float b, int tex,int rarity, int maxY){
		this.ores[numOres]=new ItemStack(this,1);
		this.mininglevels[numOres]=level;
		this.colors[numOres][0]=r;
		this.colors[numOres][1]=g;
		this.colors[numOres][2]=b;
		this.textureType[numOres]=tex;
		this.rarity[numOres]=rarity;
		this.maxy[numOres]=maxY;
		this.fluids[numOres]=liquid;
		numOres++;
	}
	
	
    @Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i=0;i<numOres;++i){
			if(mininglevels[i]>0){
				list.add(new ItemStack(item,1,i));
			}
		}
	}

	public boolean isOpaqueCube()
    {
        return true;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int getRenderType()
    {
    	return this.renderType; //8
    }


    public float[] getColors(int meta){
    	return this.colors[meta];
    }
    
    @SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {    	
    	if (this.isBedrock[meta]){
    		return this.bedrockTex;
    	}

    	EntityPlayer ply = Techguns.proxy.getPlayerClient(); 
    	if (ply!=null){
			int dimension = ply.worldObj.provider.dimensionId;
			if (dimension == -1){
				return this.texture[1];
			} else if (dimension == 1){
				return this.texture[2];
			}
    	}
		return this.texture[0];
	}

	public IIcon getOverlayIcon(int i, int meta) {
		return this.overlay_texture[this.textureType[meta]];
	}

	public String getTypeName(int meta){
		return this.ores[meta].getUnlocalizedName()+".name";
	}

	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer p, World w, int x, int y, int z) {
		
		ItemStack it = p.getHeldItem();
		if (it!=null && it.getItem() ==TGItems.crowbar){
			
			int meta = w.getBlockMetadata(x, y, z);
			if (!this.isBedrock[meta]){
				return 0.005f;
			}
		}

		return super.getPlayerRelativeBlockHardness(p, w, x, y, z);
	}


	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return null;
	}
	
	
	
	
	/*public boolean hasOverlay() {
		return true;
	}

	@Override
	public boolean canRenderInPass(int pass)
    {
		ClientProxy.renderPass = pass;
        return true;
    }
	
	@Override
    public int getRenderBlockPass()
	{
	    return 1;
	}*/
	
}
