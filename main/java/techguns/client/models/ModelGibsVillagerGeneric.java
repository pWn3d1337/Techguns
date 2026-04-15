package techguns.client.models;

import net.minecraft.client.model.ModelVillager;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelGibsVillagerGeneric extends ModelGibs
{
	public ModelVillager model;

    public ModelGibsVillagerGeneric(ModelVillager model)
    {
    	this.model = model;
    	this.model.villagerHead.setRotationPoint(0.0f,0.0f,0.0f);
    	this.model.villagerBody.setRotationPoint(0.0f,0.0f,0.0f);
    	this.model.villagerArms.setRotationPoint(0.0f,0.0f,0.0f);
    	this.model.rightVillagerLeg.setRotationPoint(0.0f,0.0f,0.0f);
    	this.model.leftVillagerLeg.setRotationPoint(0.0f,0.0f,0.0f);
    }


    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_, int part)
    {
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);

        switch(part) {
	        case 0:
	        	this.model.villagerHead.render(p_78088_7_);
	        	break;
	        case 1:
	        	 this.model.villagerBody.render(p_78088_7_);
	        	break;
	        case 2:
	        	this.model.rightVillagerLeg.render(p_78088_7_);
	        	break;
	        case 3:
	        	this.model.leftVillagerLeg.render(p_78088_7_);
	        	break;
	        case 4:
	        	this.model.villagerArms.render(p_78088_7_);
	        	break;
//	        case 5:
//	        	this.bipedLeftLeg.render(p_78088_7_);
//	        	break;
        }
    }

	@Override
	public int getNumGibs() {
		return 5;
	}
}