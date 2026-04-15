package techguns.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import techguns.blocks.BlockTGChest;
import techguns.tileentities.TGChestTileEnt;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTGChest extends TileEntitySpecialRenderer
{
   //private static final ResourceLocation field_147507_b = new ResourceLocation("textures/entity/chest/trapped_double.png");
  //private static final ResourceLocation field_147508_c = new ResourceLocation("textures/entity/chest/christmas_double.png");
    private static final ResourceLocation texture_double[] = {new ResourceLocation("techguns:textures/blocks/reinforced_chest_double.png"),new ResourceLocation("techguns:textures/blocks/weapon_chest_double.png")}; //new ResourceLocation("textures/entity/chest/normal_double.png");
    //private static final ResourceLocation field_147506_e = new ResourceLocation("textures/entity/chest/trapped.png");
    //private static final ResourceLocation field_147503_f = new ResourceLocation("textures/entity/chest/christmas.png");
   private static final ResourceLocation texture_single[] = {new ResourceLocation("techguns:textures/blocks/reinforced_chest.png"),new ResourceLocation("techguns:textures/blocks/weapon_chest.png")}; //new ResourceLocation("textures/entity/chest/normal.png");
    private ModelChest singleChest = new ModelChest();
    private ModelChest doubleChest = new ModelLargeChest();
    private boolean field_147509_j;

    public RenderTGChest()
    {
    }

    public void renderTileEntityAt(TGChestTileEnt chestTileEnt, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_)
    {
        int i;

        int chestType = ((BlockTGChest)chestTileEnt.getBlockType()).type;
        
        if (!chestTileEnt.hasWorldObj())
        {
            i = 0;
        }
        else
        {
            Block block = chestTileEnt.getBlockType();
            i = chestTileEnt.getBlockMetadata();

            if (block instanceof BlockTGChest && i == 0)
            {
                try
                {
                ((BlockTGChest)block).func_149954_e(chestTileEnt.getWorldObj(), chestTileEnt.xCoord, chestTileEnt.yCoord, chestTileEnt.zCoord);
                }
                catch (ClassCastException e)
                {
                    FMLLog.severe("Attempted to render a chest at %d,  %d, %d that was not a chest", chestTileEnt.xCoord, chestTileEnt.yCoord, chestTileEnt.zCoord);
                }
                i = chestTileEnt.getBlockMetadata();
            }

            chestTileEnt.checkForAdjacentChests();
        }

        if (chestTileEnt.adjacentChestZNeg == null && chestTileEnt.adjacentChestXNeg == null)
        {
            ModelChest modelchest;

            if (chestTileEnt.adjacentChestXPos == null && chestTileEnt.adjacentChestZPos == null)
            {
                modelchest = this.singleChest;

                /*if (p_147500_1_.func_145980_j() == 1)
                {
                    this.bindTexture(field_147506_e);
                }
                else if (this.field_147509_j)
                {
                    this.bindTexture(field_147503_f);
                }
                else
                {*/
                    this.bindTexture(texture_single[chestType]);
                //}
            }
            else
            {
                modelchest = this.doubleChest;

               /* if (p_147500_1_.func_145980_j() == 1)
                {
                    this.bindTexture(field_147507_b);
                }
                else if (this.field_147509_j)
                {
                    this.bindTexture(field_147508_c);
                }
                else
                {*/
                    this.bindTexture(texture_double[chestType]);
                //}
            }

            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float)p_147500_2_, (float)p_147500_4_ + 1.0F, (float)p_147500_6_ + 1.0F);
            GL11.glScalef(1.0F, -1.0F, -1.0F);
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            short short1 = 0;

            if (i == 2)
            {
                short1 = 180;
            }

            if (i == 3)
            {
                short1 = 0;
            }

            if (i == 4)
            {
                short1 = 90;
            }

            if (i == 5)
            {
                short1 = -90;
            }

            if (i == 2 && chestTileEnt.adjacentChestXPos != null)
            {
                GL11.glTranslatef(1.0F, 0.0F, 0.0F);
            }

            if (i == 5 && chestTileEnt.adjacentChestZPos != null)
            {
                GL11.glTranslatef(0.0F, 0.0F, -1.0F);
            }

            GL11.glRotatef((float)short1, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            float f1 = chestTileEnt.prevLidAngle + (chestTileEnt.lidAngle - chestTileEnt.prevLidAngle) * p_147500_8_;
            float f2;

            if (chestTileEnt.adjacentChestZNeg != null)
            {
                f2 = chestTileEnt.adjacentChestZNeg.prevLidAngle + (chestTileEnt.adjacentChestZNeg.lidAngle - chestTileEnt.adjacentChestZNeg.prevLidAngle) * p_147500_8_;

                if (f2 > f1)
                {
                    f1 = f2;
                }
            }

            if (chestTileEnt.adjacentChestXNeg != null)
            {
                f2 = chestTileEnt.adjacentChestXNeg.prevLidAngle + (chestTileEnt.adjacentChestXNeg.lidAngle - chestTileEnt.adjacentChestXNeg.prevLidAngle) * p_147500_8_;

                if (f2 > f1)
                {
                    f1 = f2;
                }
            }

            f1 = 1.0F - f1;
            f1 = 1.0F - f1 * f1 * f1;
            modelchest.chestLid.rotateAngleX = -(f1 * (float)Math.PI / 2.0F);
            modelchest.renderAll();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_)
    {
        this.renderTileEntityAt((TGChestTileEnt)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
}