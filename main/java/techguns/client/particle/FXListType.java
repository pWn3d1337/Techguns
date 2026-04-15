package techguns.client.particle;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class FXListType extends FXType {
	ArrayList<ParticleSystemEntry> particleSystems = new ArrayList<ParticleSystemEntry>();

	
	
	public FXListType() {
		isList = true;
	}

	public void addParticleSystem(String particleSystem) {
		particleSystems.add(new ParticleSystemEntry(particleSystem));
	}
	
	class ParticleSystemEntry {
		//ParticleSystemType type; //Actually is just a string
		String particleSystem;
		//TODO: Delay, Offset, Scale, etc.

		public ParticleSystemEntry(String particleSystem) {
			super();
			this.particleSystem = particleSystem;
		}
	}

	@Override
	public List<EntityParticleSystem> createParticleSystems(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
		ArrayList<EntityParticleSystem> list = new ArrayList<EntityParticleSystem>();
		for (ParticleSystemEntry system : particleSystems) {		
			if (FXList.FXList.containsKey(system.particleSystem)) {
				FXType fxtype = FXList.FXList.get(system.particleSystem);
				list.addAll(fxtype.createParticleSystems(world, posX, posY, posZ, motionX, motionY, motionZ));
			}
		}
		return list;
	}

	@Override
	public List<EntityParticleSystem> createParticleSystemsOnEntity(Entity ent) {
		ArrayList<EntityParticleSystem> list = new ArrayList<EntityParticleSystem>();
		for (ParticleSystemEntry system : particleSystems) {		
			if (FXList.FXList.containsKey(system.particleSystem)) {
				FXType fxtype = FXList.FXList.get(system.particleSystem);
				list.addAll(fxtype.createParticleSystemsOnEntity(ent));
			}
		}
		return list;
	}
}
