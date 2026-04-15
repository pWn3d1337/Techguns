package techguns.client.renderer.item;

import org.lwjgl.opengl.GL11;

import techguns.debug.Keybinds;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;


public class RenderBlockModelItemChest extends RenderMachineBlockItemBase {

	public RenderBlockModelItemChest(ResourceLocation texture, ModelBase model) {
		super(texture, model);
	}

	@Override
	protected void GL_Transform_FirstPerson(){
		GL11.glTranslatef(-0.1f, -1.0f, -0.9f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
		GL11.glScalef(0.9f, 0.9f, 0.9f);
		//GL11.glRotatef(-90, 0, 1, 0);
	};
	@Override
	protected void GL_Transform_Equipped(){
		//GL11.glTranslatef(0.6f, -0.5f, 0.6f);
		GL11.glTranslatef(0.5f, -1.6f, -0.5f);
		GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
	};
	@Override
	protected void GL_Transform_Ground(){
		GL11.glTranslatef(0, -1.0f, 0);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
	};
	@Override
	protected void GL_Transform_Icon(){
		GL11.glTranslatef(-0.5f, 0.3f, 0.5f);
		//GL11.glTranslated(Keybinds.X, Keybinds.Y, Keybinds.Z);
		GL11.glScalef(1.1f, 1.1f, 1.1f);
		GL11.glRotatef(-90, 0, 1, 0);
	};
	@Override
	protected void GL_Transform_Translate(){
		GL11.glRotatef(180.0f, 1, 0, 0);
	};

}
