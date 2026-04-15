package techguns.worldgen.village;

import java.util.List;
import java.util.Random;

import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageCreationHandler;

public class VillageHandlerTGBunker implements IVillageCreationHandler {

	@Override
	public PieceWeight getVillagePieceWeight(Random random, int i) {
		return new PieceWeight(VillageBunker.class, 10, 1);
	}

	@Override
	public Class<?> getComponentClass() {
		return VillageBunker.class;
	}

	@Override
	public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5) {
		return VillageBunker.build(startPiece, pieces, random, p1, p2, p3, p4, p5);
	}

}
