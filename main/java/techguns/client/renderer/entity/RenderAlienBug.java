package techguns.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import techguns.client.models.ModelAlienBug;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAlienBug extends RenderLiving
{
    private static final ResourceLocation texture = new ResourceLocation("techguns","textures/entity/alienbug.png");

    public RenderAlienBug()
    {
        super(new ModelAlienBug(), 1.0F);
    }

    protected float getDeathMaxRotation(EntityLiving p_77037_1_)
    {
        return 180.0F;
    }
    
//    /**
//     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
//     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
//     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
//     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
//     */
//    public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
//    {
//    	float f = 0.4375f;
//    	//GL11.glPushMatrix();
//    	//GL11.glTranslatef(0, -f, 0);
//        super.doRender((EntityLivingBase)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
//        //GL11.glTranslatef(0, f, 0);
//        //GL11.glPopMatrix();
//        this.func_110827_b(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
//    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
/*    protected ResourceLocation getEntityTexture(EntitySpider p_110775_1_)
    {
        return texture;
    }*/

    /*protected float getDeathMaxRotation(EntityLivingBase p_77037_1_)
    {
        return this.getDeathMaxRotation((EntitySpider)p_77037_1_);
    }*/

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
       // return this.getEntityTexture((EntitySpider)p_110775_1_);
    	return texture;
    }
}
