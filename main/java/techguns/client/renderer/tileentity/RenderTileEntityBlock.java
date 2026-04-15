package techguns.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import techguns.client.renderer.block.TesselatorCube;
import techguns.tileentities.BasicInventoryTileEnt;

public class RenderTileEntityBlock extends TileEntitySpecialRenderer {

	//private ResourceLocation texture;
	TesselatorCube cube;
	
	
	public RenderTileEntityBlock(TesselatorCube cube) {
		super();
		this.cube=cube;
	}



	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		 //The PushMatrix tells the renderer to "start" doing something.
        GL11.glPushMatrix();
//This is setting the initial location.
        GL11.glTranslated(x,y,z);
//This is the texture of your block. It's pathed to be the same place as your other blocks here.
        //Outdated bindTextureByName("/mods/roads/textures/blocks/TrafficLightPoleRed.png");
//Use in 1.6.2  this
        //ResourceLocation textures = (new ResourceLocation("techguns:textures/blocks/AmmoPress.png"));
//the ':' is very important
//binding the textures
        
//This rotation part is very important! Without it, your model will render upside-down! And for some reason you DO need PushMatrix again!                      
        GL11.glPushMatrix();
        //GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
//A reference to your Model file. Again, very important.
        byte rotation = ((BasicInventoryTileEnt) te).rotation;
        GL11.glTranslatef(0.5f, 0, 0.5f);
        GL11.glRotatef(90.0f*rotation, 0, 1, 0);
        GL11.glTranslatef(-0.5f, 0, -0.5f);
        
        this.adjustLightFixture(te.getWorldObj(), te.xCoord,te.yCoord,te.zCoord, te.blockType);
        cube.drawCube();

        
//Tell it to stop rendering for both the PushMatrix's
        GL11.glPopMatrix();
        GL11.glPopMatrix();
	}

    //Set the lighting stuff, so it changes it's brightness properly.      
    private void adjustLightFixture(World world, int i, int j, int k, Block block) {
            Tessellator tess = Tessellator.instance;
            //float brightness = block.getBlockBrightness(world, i, j, k);
            //As of MC 1.7+ block.getBlockBrightness() has become block.getLightValue():
            float brightness = block.getLightValue(world, i, j, k);
            int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
            int modulousModifier = skyLight % 65536;
            int divModifier = skyLight / 65536;
            tess.setColorOpaque_F(brightness, brightness, brightness);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,  (float) modulousModifier,  divModifier);
    }
}
