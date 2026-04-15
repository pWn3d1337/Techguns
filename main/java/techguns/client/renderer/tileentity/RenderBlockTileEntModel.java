package techguns.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import techguns.client.renderer.TGRenderHelper;
import techguns.tileentities.TurretBaseEnt;

public class RenderBlockTileEntModel extends TileEntitySpecialRenderer{

	  //The model of your block
    private ModelBase model;
    private ResourceLocation texture;
   
    public RenderBlockTileEntModel(ModelBase model, ResourceLocation texture) {
            this.model =model;
            this.texture = texture;
    }
   
    private void adjustRotatePivotViaMeta(World world, int x, int y, int z) {
            int meta = world.getBlockMetadata(x, y, z);
            GL11.glPushMatrix();
            GL11.glRotatef(meta * (-90), 0.0F, 0.0F, 1.0F);
            GL11.glPopMatrix();
    }
   
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
    //The PushMatrix tells the renderer to "start" doing something.
            GL11.glPushMatrix();
    //This is setting the initial location.
            GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
    //This is the texture of your block. It's pathed to be the same place as your other blocks here.
            //Outdated bindTextureByName("/mods/roads/textures/blocks/TrafficLightPoleRed.png");
   //Use in 1.6.2  this
            //ResourceLocation textures = (new ResourceLocation("techguns:textures/blocks/AmmoPress.png"));
    //the ':' is very important
    //binding the textures
            TGRenderHelper.adjustLightFixture(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, te.getBlockType());
            if (te instanceof TurretBaseEnt){
            	TurretBaseEnt turret = (TurretBaseEnt) te;
            	Minecraft.getMinecraft().renderEngine.bindTexture(turret.getTurretBaseTexture());
            } else {
            	Minecraft.getMinecraft().renderEngine.bindTexture(texture);
            }

    //This rotation part is very important! Without it, your model will render upside-down! And for some reason you DO need PushMatrix again!                      
            GL11.glPushMatrix();
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
    //A reference to your Model file. Again, very important.
            
            this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
    //Tell it to stop rendering for both the PushMatrix's
            GL11.glPopMatrix();
            GL11.glPopMatrix();
    }

    
}
