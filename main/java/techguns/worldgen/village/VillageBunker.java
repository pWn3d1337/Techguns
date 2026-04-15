package techguns.worldgen.village;

import java.util.List;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.House1;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import techguns.util.BlockUtils.BiomeColorType;
import techguns.worldgen.structures.Bunker;
import techguns.worldgen.structures.Structure;

public class VillageBunker extends House1 {

	
    public VillageBunker() {
		super();
	}

	public VillageBunker(Start p_i2094_1_, int p_i2094_2_, Random p_i2094_3_, StructureBoundingBox p_i2094_4_, int p_i2094_5_) {
		super(p_i2094_1_, p_i2094_2_, p_i2094_3_, p_i2094_4_, p_i2094_5_);
	}

	public static VillageBunker build(StructureVillagePieces.Start p_74898_0_, List p_74898_1_, Random p_74898_2_, int p_74898_3_, int p_74898_4_, int p_74898_5_, int p_74898_6_, int p_74898_7_)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_74898_3_, p_74898_4_, p_74898_5_, 0, 0, 0, 9, 9, 6, p_74898_6_);
        return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_74898_1_, structureboundingbox) == null ? new VillageBunker(p_74898_0_, p_74898_7_, p_74898_2_, structureboundingbox, p_74898_6_) : null;
    }

	@Override
	public boolean addComponentParts(World w, Random rnd, StructureBoundingBox bb) {
		
		//return super.addComponentParts(w, rnd, bb);
		
		if (this.field_143015_k < 0)
        {
            this.field_143015_k = this.getAverageGroundLevel(w, bb);

            if (this.field_143015_k < 0)
            {
                return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 9 - 1, 0);
        }
		
		Structure bunker = new Bunker(0, 0, 0, 0, 0, 0, rnd.nextInt(4));
		//System.out.println("Building Bunker at:"+bb.minX + ","+ bb.minY+2 +","+ bb.minZ + "  Dimensions:"+ (bb.getXSize()-1)+","+ (bb.getYSize()-1)+","+(bb.getZSize()-1));
		bunker.setBlocks(w, bb.minX, bb.minY+2, bb.minZ, bb.getXSize()-1, bb.getYSize()-1, bb.getZSize()-1, rnd.nextInt(4), BiomeColorType.WOODLAND, rnd);
		
		return true;
	}

	
}
