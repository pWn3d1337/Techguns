package techguns.client.particle;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public abstract class FXType {
	public String name;
	boolean isList = false;
	
	public abstract List<EntityParticleSystem> createParticleSystems(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ);
	public abstract List<EntityParticleSystem> createParticleSystemsOnEntity(Entity ent);
}
