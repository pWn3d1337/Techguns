package techguns.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.Techguns;
import techguns.tileentities.TGSpawnerTileEnt;

public class BlockTGMonsterSpawner extends BlockContainer {
	protected String[] blocktextures;
	protected String[] blocktextures_hole;
	protected IIcon[] icons;
	protected IIcon[] icons_hole;
	protected byte amountUsed;
	
	public BlockTGMonsterSpawner(Material mat, int amountUsed) {
		super(mat);
		setCreativeTab(Techguns.tabTechgun);
		setHardness(8.0f);
		setBlockName("TGMonsterSpawner");
		setStepSound(soundTypeStone);
		setHarvestLevel("pickaxe", 1);
		this.amountUsed = (byte) amountUsed;
		this.blocktextures = new String[amountUsed];
		this.blocktextures_hole = new String[amountUsed];
		this.icons = new IIcon[amountUsed];
		this.icons_hole = new IIcon[amountUsed];
	}

	
	
	@Override
	public String getUnlocalizedName() {
		return "techguns."+super.getUnlocalizedName();
	}



	@Override
	public IIcon getIcon(int side, int meta) {
		if(meta<amountUsed){
			if (side == 1){
				return icons_hole[meta];
			} else {
				return icons[meta];
			}
		}
		return super.getIcon(side, meta);
	}

	@Override
	public void registerBlockIcons(IIconRegister r) {
		super.registerBlockIcons(r);
		//icons_ = r.registerIcon("techguns:bugnestblock_hole");
		for (int i=0;i<amountUsed;i++){
			icons_hole[i] = r.registerIcon(blocktextures_hole[i]);
			icons[i] = r.registerIcon(blocktextures[i]);
		}
	}

	public BlockTGMonsterSpawner setBlockTypeForMeta(int meta, String tex, String hole){
		this.blocktextures[meta]=tex;
		this.blocktextures_hole[meta]=hole;
		return this;
	}
	
    @Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i=0;i<amountUsed;++i){
				list.add(new ItemStack(item,1,i));
		}
	}

    
    
    
	@Override
	public Item getItemDropped(int meta, Random rnd, int fortune) {
		if (meta==0){
			return Item.getItemFromBlock(TGBlocks.bugnest);
		} else if (meta==1){
			return Item.getItemFromBlock(TGBlocks.concrete);
		}
		return super.getItemDropped(meta, rnd, fortune);
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}
	
	
	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		TGSpawnerTileEnt spawner = new TGSpawnerTileEnt();
		
		switch(meta){
			case 0:
				spawner.setValues("Techguns.AlienBug", 20, 100, 300, 3, 5, 24, 3);
				break;
			case 1:
				spawner.setValues("Techguns.ZombieSoldier", 20, 100, 300, 3, 5, 24, 3);
				break;
		}
		
		
		return spawner;
	}

}
