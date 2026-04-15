package techguns.client.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelGibsBipedGeneric extends ModelGibs
{
	public ModelBiped model;
    
    public ModelGibsBipedGeneric(ModelBiped model)
    {
    	this.model = model;
    	this.model.bipedHead.setRotationPoint(0.0f,0.0f,0.0f);
    	this.model.bipedBody.setRotationPoint(0.0f,0.0f,0.0f);
    	this.model.bipedRightArm.setRotationPoint(0.0f,0.0f,0.0f);
    	this.model.bipedLeftArm.setRotationPoint(0.0f,0.0f,0.0f);
    	this.model.bipedRightLeg.setRotationPoint(0.0f,0.0f,0.0f);
    	this.model.bipedLeftLeg.setRotationPoint(0.0f,0.0f,0.0f);
    	
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_, int part)
    {
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);

        switch(part) {
	        case 0:
	        	this.model.bipedHead.render(p_78088_7_);
	        	break;
	        case 1:
	        	this.model.bipedBody.render(p_78088_7_);
	        	break;
	        case 2:
	        	this.model.bipedRightArm.render(p_78088_7_);
	        	break;
	        case 3:
	        	this.model.bipedLeftArm.render(p_78088_7_);
	        	break;
	        case 4:
	        	this.model.bipedRightLeg.render(p_78088_7_);
	        	break;
	        case 5:
	        	this.model.bipedLeftLeg.render(p_78088_7_);
	        	break;
        }
    }

	@Override
	public int getNumGibs() {
		return 6;
	}
}