package techguns.client.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;

public abstract class ModelBipedTGBackSlot extends ModelBiped {
	
	public ModelBipedTGBackSlot(float p_i1149_1_, float p_i1149_2_, int p_i1149_3_, int p_i1149_4_) {
		super(p_i1149_1_, p_i1149_2_, p_i1149_3_, p_i1149_4_);
	}

	public abstract void renderOnPlayer(EntityPlayer ply, float x, float y, float z, float r, float s, float scale, RenderPlayer renderplayer, boolean showSlot);

	
	protected void applyBipedBodyTransformations(RenderPlayer renderPlayer){
		ModelRenderer target = renderPlayer.modelBipedMain.bipedBody;
		this.bipedBody.rotateAngleX = target.rotateAngleX;
		this.bipedBody.rotateAngleY = target.rotateAngleY;
		this.bipedBody.rotateAngleZ = target.rotateAngleZ;
	}
	
}
