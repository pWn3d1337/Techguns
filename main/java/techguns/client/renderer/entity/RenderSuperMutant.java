package techguns.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import techguns.entities.npc.SuperMutantBasic;

public class RenderSuperMutant extends RenderGenericNPC {

	protected int type;
	
	private static ResourceLocation[] textures = {
		new ResourceLocation("techguns:textures/entity/supermutant_texture_1.png"),
		new ResourceLocation("techguns:textures/entity/supermutant_texture_2.png"),
		new ResourceLocation("techguns:textures/entity/supermutant_texture_3.png")
	};
	

	public RenderSuperMutant(ModelBiped par1ModelBiped, String texturePath, float parShadowSize, float gunOffsetX, float gunOffsetY, float gunOffsetZ, int type) {
		super(par1ModelBiped, texturePath+type+".png", parShadowSize, gunOffsetX, gunOffsetY, gunOffsetZ);
		this.type=type;
	}

	
	@Override
	public void doRender(EntityLiving ent, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		super.doRender(ent, p_76986_2_, p_76986_4_+((SuperMutantBasic)ent).getModelHeightOffset(), p_76986_6_, p_76986_8_, p_76986_9_);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity ent) {
		return textures[type];
	}

	
	
}
