package techguns.blocks;

import net.minecraft.init.Blocks;
import team.chisel.init.ChiselBlocks;
import techguns.util.MBlock;

public class TGChiselBlocks {
	public static MBlock concrete = new MBlock(ChiselBlocks.concrete, 0);
	public static MBlock concrete_dark = new MBlock(ChiselBlocks.concrete, 10);
	
    public static MBlock woodenCrate = new MBlock (ChiselBlocks.planks[0], 10); //Wooden Box
	
    //public static MBlock hexplating = new MBlock (ChiselBlocks.technical2, 5); //Massive hex plating
    public static MBlock glowblock = new MBlock (ChiselBlocks.glowstone, 4); //Neon glowstone
      
    public static MBlock factory_box = new MBlock (ChiselBlocks.factoryblock, 9);
    public static MBlock factory_wireframe = new MBlock (ChiselBlocks.factoryblock, 5);
    public static MBlock factory_hazard = new MBlock (ChiselBlocks.factoryblock, 6);
    public static MBlock factory_wall = new MBlock (ChiselBlocks.factoryblock, 1);
    
    public static MBlock technical_scaffold = new MBlock (ChiselBlocks.technical,0);
    
    //Barracks wood parts
    
    public static MBlock wood_scaffold = new MBlock (ChiselBlocks.planks[0], 12);
    public static MBlock wood_pillar = new MBlock (ChiselBlocks.planks[1], 12);
    public static MBlock wood_wall = new MBlock (ChiselBlocks.planks[0], 1);
    public static MBlock wood_floor = new MBlock (ChiselBlocks.planks[0], 5);
    public static MBlock wood_roof = new MBlock (Blocks.planks, 1);
    public static MBlock wood_roofSlab = new MBlock (Blocks.wooden_slab, 1);
    
    //Stone Blocks
    public static MBlock stonebrick_panel = new MBlock (ChiselBlocks.stonebricksmooth, 12); //Panel-Hard
    public static MBlock stonebrick_small = new MBlock (ChiselBlocks.stonebricksmooth, 5); //smallbricks
    public static MBlock stonebrick_sunken = new MBlock (ChiselBlocks.stonebricksmooth, 13); //sunken
    public static MBlock cobblestone_largetile = new MBlock(ChiselBlocks.cobblestone, 4); //terrain-cobblargetiledark
    
    //Glass
    public static MBlock glass_bubble = new MBlock (ChiselBlocks.glass, 1); //Panel-Hard
}
