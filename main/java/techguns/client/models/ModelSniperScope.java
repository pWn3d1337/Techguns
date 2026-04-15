package techguns.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelSniperScope extends ModelBase {

	ResourceLocation texture;
	
	ModelRenderer Shape1;
	
	public ModelSniperScope(ResourceLocation texture){
		super();
		this.texture=texture;
		
		Shape1 = new ModelRenderer(this, 0, 0);
		Shape1.addBox(0F, 0F, 0F, 1080, 1080, 0);
		Shape1.setRotationPoint(0, 0, 0);
		Shape1.setTextureSize(1080, 1080);
		Shape1.mirror = true;
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.client.model.ModelBase#render(net.minecraft.entity.Entity, float, float, float, float, float, float)
	 */
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	  {
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	    
	    Shape1.render(f5);
		/*float U1 = 0.0f;
		float V1 = 0.0f;
		float U2 = 1.0f;
		float V2 = 1.0f;
		
		RenderHelper.disableStandardItemLighting();
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		GL11.glPushMatrix();
		//GL11.glTranslatef(0.f, 0.f, 0.f);
		//GL11.glRotatef(rot_angle, rot_x, rot_y, rot_z);
		//GL11.glScalef(scale, scale, scale);
		//GL11.glDisable(GL11.GL_DIFFUSE);
		//GL11.glDisable(GL11.GL_LIGHTING);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		//float f = scale/2.0f;
        /*tessellator.addVertexWithUV(offsetX-f, offsetY, offsetZ+f, (double)U1, (double)V1);
        tessellator.addVertexWithUV(offsetX+f, offsetY, offsetZ+f, (double)U2, (double)V1);
        tessellator.addVertexWithUV(offsetX+f, offsetY, offsetZ-f, (double)U2, (double)V2);
        tessellator.addVertexWithUV(offsetX-f, offsetY, offsetZ-f, (double)U1, (double)V2);*/
      /*  tessellator.addVertexWithUV(0, 0, 0, (double)U1, (double)V1);
        tessellator.addVertexWithUV(1,0, 0, (double)U2, (double)V1);
        tessellator.addVertexWithUV(1, 0, 1, (double)U2, (double)V2);
        tessellator.addVertexWithUV(0, 0, 1, (double)U1, (double)V2);
                     
        //tessellator.setBrightness(15);
        //GL11.glColor3f(1, 1, 1);
        //tessellator.setColorOpaque(255, 255, 255);
        
        tessellator.draw();
        //GL11.glEnable(GL11.GL_DIFFUSE);
        //GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
        
        RenderHelper.enableStandardItemLighting();
	    */
	  }
}
