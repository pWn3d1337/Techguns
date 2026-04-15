package techguns.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import techguns.client.models.ModelMultipart;
import techguns.debug.Keybinds;
import techguns.entities.npc.NPCTurret;
import techguns.items.guns.GenericGun;

public class RenderSimpleTurret extends Render {
	private ModelMultipart model;
	private float gunOffsetX=0.025f;
	private float gunOffsetY=-1.0f;
	private float gunOffsetZ=0.3f;
	
	public RenderSimpleTurret(ModelMultipart model) {
		super();
		this.model=model;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		NPCTurret turret = (NPCTurret) entity;
		return turret.getTexture();
	}

	@Override
	public void doRender(Entity entity, double par2, double par4, double par6, float par8, float par9) {

        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);

        
        NPCTurret turret = (NPCTurret) entity;
        Minecraft.getMinecraft().getTextureManager().bindTexture(turret.getTexture());
        
        GL11.glTranslatef(0, 1.5f, 0);
        GL11.glRotatef(180.0f, 1.0f, 0, 0);
        
        
        GL11.glRotatef(turret.rotationYawHead, 0.0F, 1.0F, 0.0F);
        model.render(entity, 0, 0, 0, 0, 0, 0.0625F, 0, 0.0f, ItemRenderType.ENTITY, 0, 0.0f);
        
        float rotoffset=0.9f;
        GL11.glTranslatef(0, rotoffset, 0);
        GL11.glRotatef(turret.rotationPitch, 1.0F, 0.0F, 0.0F);
        GL11.glTranslatef(0, -rotoffset, 0);
        model.render(entity, 0, 0, 0, 0, 0, 0.0625F, 0, 0.0f, ItemRenderType.ENTITY, 1, 0.0f);
        

        GL11.glRotatef(170.0f, 0.0F, 1.0F, 0.0F);
        this.renderEquippedItems(turret, 0);
        
        
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
		
	}

	
    protected void renderEquippedItems(EntityLiving p_77029_1_, float p_77029_2_)
    {
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        //super.renderEquippedItems(p_77029_1_, p_77029_2_);
        ItemStack itemstack = p_77029_1_.getHeldItem();
        ItemStack itemstack1 = p_77029_1_.func_130225_q(3);
        Item item;
        float f1;

        if (itemstack1 != null)
        {
            GL11.glPushMatrix();
            item = itemstack1.getItem();

            net.minecraftforge.client.IItemRenderer customRenderer = net.minecraftforge.client.MinecraftForgeClient.getItemRenderer(itemstack1, net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED);
            boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED, itemstack1, net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D));

            GL11.glPopMatrix();
        }

        if (itemstack != null && itemstack.getItem() != null)
        {
            item = itemstack.getItem();
            GL11.glPushMatrix();

           /* if (this.mainModel.isChild)
            {
                f1 = 0.5F;
                GL11.glTranslatef(0.0F, 0.625F, 0.0F);
                GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
                GL11.glScalef(f1, f1, f1);
            }

            this.modelBipedMain.bipedRightArm.postRender(0.0625F);*/
            //GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);

            net.minecraftforge.client.IItemRenderer customRenderer = net.minecraftforge.client.MinecraftForgeClient.getItemRenderer(itemstack, net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED);
            boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED, itemstack, net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D));

            if (item instanceof techguns.items.guns.GenericGun)
            {
            	GenericGun gun = (GenericGun) item;
            	f1 = 0.375F;
                GL11.glRotatef(180.0f, 1, 0, 0);
               // GL11.glTranslatef(gunOffsetX+gun.turretPosOffsetX+(float)Keybinds.X, gunOffsetY+gun.turretPosOffsetY+(float)Keybinds.Y, gunOffsetZ+gun.turretPosOffsetZ+(float)Keybinds.Z);
                GL11.glTranslatef(gunOffsetX+gun.turretPosOffsetX, gunOffsetY+gun.turretPosOffsetY, gunOffsetZ+gun.turretPosOffsetZ); 
                GL11.glTranslatef(0.0F, 0.125F, -0.3125F);                         
                //GL11.glRotatef(-100.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(f1, f1, f1);
                
                //GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
               GL11.glRotatef(-55.0F, 0.0F, 1.0F, 0.0F);
            }
           

            float f2;
            int i;
            float f5;

            if (itemstack.getItem().requiresMultipleRenderPasses())
            {
                for (i = 0; i < itemstack.getItem().getRenderPasses(itemstack.getItemDamage()); ++i)
                {
                    int j = itemstack.getItem().getColorFromItemStack(itemstack, i);
                    f5 = (float)(j >> 16 & 255) / 255.0F;
                    f2 = (float)(j >> 8 & 255) / 255.0F;
                    float f3 = (float)(j & 255) / 255.0F;
                    GL11.glColor4f(f5, f2, f3, 1.0F);
                    this.renderManager.itemRenderer.renderItem(p_77029_1_, itemstack, i);
                }
            }
            else
            {
                i = itemstack.getItem().getColorFromItemStack(itemstack, 0);
                float f4 = (float)(i >> 16 & 255) / 255.0F;
                f5 = (float)(i >> 8 & 255) / 255.0F;
                f2 = (float)(i & 255) / 255.0F;
                GL11.glColor4f(f4, f5, f2, 1.0F);
                this.renderManager.itemRenderer.renderItem(p_77029_1_, itemstack, 0);
            }

            GL11.glPopMatrix();
        }
    }
	
}
