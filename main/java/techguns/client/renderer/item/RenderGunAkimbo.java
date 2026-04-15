package techguns.client.renderer.item;

import net.minecraft.util.ResourceLocation;
import techguns.client.models.ModelMultipart;

public abstract class RenderGunAkimbo extends RenderGunBase {

	protected int partsPerGun;
	
	public RenderGunAkimbo(ModelMultipart model, ResourceLocation texture, float scale, float x, float y, float z, boolean handledamage,
			boolean handleprogress, int parts, boolean hasZoom) {
		super(model, texture, scale, x, y, z, handledamage, handleprogress, parts*2, hasZoom);
		this.partsPerGun=parts;
	}

	
	/**
	 * Return if the passed part index is belonging to the second gun, for example part >0 for guns with only 1 part, or part >2 for guns with 2 parts.
	 * @param part
	 */
	protected abstract boolean is2ndGun(int part);

	
	private int getPartFor2ndGun(int part){
		if (this.is2ndGun(part)){
			return part - this.partsPerGun;
		}
		return part;
	}

	@Override
	protected void GL_Transform_FirstPerson(int part, float prog, byte attackType) {
		this.GL_Transform_FirstPerson(getPartFor2ndGun(part), prog, attackType, this.is2ndGun(part));
	}
	protected abstract void GL_Transform_FirstPerson(int part, float prog, byte attackType, boolean leftGun);

	@Override
	protected void GL_Transform_Equipped(int part, float prog, byte attackType) {
		this.GL_Transform_Equipped(getPartFor2ndGun(part), prog, attackType, this.is2ndGun(part));
	}
	protected abstract void GL_Transform_Equipped(int part, float prog, byte attackType, boolean leftGun);
	

	@Override
	protected void GL_Transform_Ground(int part) {
		this.GL_Transform_Ground(getPartFor2ndGun(part), this.is2ndGun(part));
	}
	protected abstract void GL_Transform_Ground(int part, boolean leftGun); 

	
	@Override
	protected void GL_Transform_Icon(int part) {
		this.GL_Transform_Icon(getPartFor2ndGun(part), this.is2ndGun(part));
	}
	protected abstract void GL_Transform_Icon(int part, boolean leftGun);


	@Override
	protected void GL_Transform_reloading(int part, float prog, byte attackType) {
		this.GL_Transform_reloading(getPartFor2ndGun(part), prog, attackType, this.is2ndGun(part));
	}
	protected abstract void GL_Transform_reloading(int part, float prog, byte attackType, boolean leftGun);
	
	
	@Override
	protected void GL_Transform_firing(int part, float prog, byte attackType) {
		this.GL_Transform_firing(getPartFor2ndGun(part), prog, attackType, this.is2ndGun(part));
	}
	protected abstract void GL_Transform_firing(int part, float prog, byte attackType, boolean leftGun);

	
	@Override
	protected void GL_Transform_Translate(int part) {
		this.GL_Transform_Translate(getPartFor2ndGun(part), this.is2ndGun(part));
	}
	protected abstract void GL_Transform_Translate(int part, boolean leftGun);


	@Override
	protected void GL_TransformReload_Equipped(int part, float prog, byte attackType) {
		this.GL_TransformReload_Equipped(getPartFor2ndGun(part), prog, attackType, this.is2ndGun(part));		
	}
	protected abstract void GL_TransformReload_Equipped(int part, float prog, byte attackType, boolean leftGun);
	
	
	
	
}
