package techguns.client.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelGibsBiped extends ModelGibs
{
	public ModelRenderer bipedHead;
    public ModelRenderer bipedBody;
    public ModelRenderer bipedRightArm;
    public ModelRenderer bipedLeftArm;
    public ModelRenderer bipedRightLeg;
    public ModelRenderer bipedLeftLeg;
    
	public ModelGibsBiped(float p_i1148_1_)
    {
        this(p_i1148_1_, 0.0F, 64, 64);
    }

    public ModelGibsBiped(float p_i1149_1_, float p_i1149_2_, int textureWidth, int textureHeight)
    {
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, p_i1149_1_);
        //this.bipedHead.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, p_i1149_1_);
        //this.bipedBody.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, p_i1149_1_);
        //this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + p_i1149_2_, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i1149_1_);
        //this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + p_i1149_2_, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1149_1_);
        //this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + p_i1149_2_, 0.0F);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1149_1_);
        //this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + p_i1149_2_, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_, int part)
    {
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);

        switch(part) {
	        case 0:
	        	this.bipedHead.render(p_78088_7_);
	        	break;
	        case 1:
	        	this.bipedBody.render(p_78088_7_);
	        	break;
	        case 2:
	        	this.bipedRightArm.render(p_78088_7_);
	        	break;
	        case 3:
	        	this.bipedLeftArm.render(p_78088_7_);
	        	break;
	        case 4:
	        	this.bipedRightLeg.render(p_78088_7_);
	        	break;
	        case 5:
	        	this.bipedLeftLeg.render(p_78088_7_);
	        	break;
        }
    }

	@Override
	public int getNumGibs() {
		return 6;
	}
}