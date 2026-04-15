package techguns.client.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelGibsSkeleton extends ModelGibsBiped
{
//	public ModelRenderer bipedHead;
//    public ModelRenderer bipedBody;
//    public ModelRenderer bipedRightArm;
//    public ModelRenderer bipedLeftArm;
//    public ModelRenderer bipedRightLeg;
//    public ModelRenderer bipedLeftLeg;
    
	public ModelGibsSkeleton(float p_i1148_1_)
    {
        this(p_i1148_1_, 0.0F, 64, 64);
    }

    public ModelGibsSkeleton(float p_i1149_1_, float p_i1149_2_, int textureWidth, int textureHeight)
    {
    	super(p_i1149_1_, 0.0F, 64, 32);
        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, p_i1149_1_);
        //this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, p_i1149_1_);
        //this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, p_i1149_1_);
        //this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, p_i1149_1_);
        //this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
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
    
}