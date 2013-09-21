package de.mineformers.timetravel.client.renderer.item;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.mineformers.timetravel.client.model.ModelPocketWatch;
import de.mineformers.timetravel.lib.Textures;

/**
 * TimeTravel
 * 
 * ItemPocketWatchRenderer
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
@SideOnly(Side.CLIENT)
public class ItemPocketWatchRenderer implements IItemRenderer {

	private ModelPocketWatch model;
	private int lastTime;

	public ItemPocketWatchRenderer() {
		model = new ModelPocketWatch();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
	        ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
			case ENTITY: {
				GL11.glPushMatrix();
				renderWatch(-0.5F, 0F, 0.5F, 1.0F);
				GL11.glPushMatrix();
				renderPane(-0.5F, 0F, 0.5F, 1.0F);
				return;
			}
			case EQUIPPED: {
				GL11.glPushMatrix();
				GL11.glRotatef(30, 0, 1, 0);
				GL11.glRotatef(60, 1, 0, 0);
				renderWatch(-0.5F, 1.1F, 0.2F, 1.0F);
				GL11.glPushMatrix();
				GL11.glRotatef(30, 0, 1, 0);
				GL11.glRotatef(60, 1, 0, 0);
				renderPane(-0.5F, 1.1F, 0.2F, 1.0F);
				return;
			}
			case EQUIPPED_FIRST_PERSON: {
				renderWatchFirstPerson(-0.6F, -0.35F, 0.0F, 2.0F);
				return;
			}
			case INVENTORY: {
				GL11.glPushMatrix();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glRotatef(45, 0, 1, 0);
				GL11.glRotatef(90, 1, 0, 0);
				renderWatch(-0.5F, 0.8F, 0.0F, 1.25F);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glPushMatrix();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glRotatef(45, 0, 1, 0);
				GL11.glRotatef(90, 1, 0, 0);
				renderPane(-0.5F, 0.8F, 0, 1.25F);
				GL11.glEnable(GL11.GL_LIGHTING);
				return;
			}
			default:
				return;
		}
	}

	private void renderWatch(float x, float y, float z, float scale) {
		// Scale, Translate, Rotate
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(x, y, z);

		// Bind texture
		FMLClientHandler.instance().getClient().renderEngine
		        .bindTexture(Textures.MODEL_WATCH);

		// Render
		model.renderBut("Pane");

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	private void renderPane(float x, float y, float z, float scale) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(x, y, z);

		model.renderPane();

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	private void renderWatchFirstPerson(float x, float y, float z, float scale) {
		int timeOfDay = (int) (FMLClientHandler.instance().getClient().theWorld
		        .getWorldTime() % 24000);

		if (timeOfDay % 1000 == 0 && timeOfDay > lastTime) {
			// hour += 1;
		}

		lastTime = timeOfDay;
		renderArm();
		GL11.glPushMatrix();
		GL11.glRotatef(-65, 0, 1, 0);
		GL11.glRotatef(90, 1, 0, 0);
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(x, y, z);

		// Bind texture
		FMLClientHandler.instance().getClient().renderEngine
		        .bindTexture(Textures.MODEL_WATCH);

		// Render
		model.renderBut("Pane");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glRotatef(-65, 0, 1, 0);
		GL11.glRotatef(90, 1, 0, 0);
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(x, y, z);

		model.renderPane();

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();

		/*
		 * GL11.glPushMatrix(); GL11.glTranslatef(x, y + 2, z); //
		 * GL11.glRotatef(-65, 0, 1, 0); // GL11.glRotatef(90, 1, 0, 0);
		 * GL11.glScalef(scale, scale, scale); GL11.glRotatef(hour * -15, 0, 1,
		 * 0); FMLClientHandler.instance().getClient().renderEngine
		 * .bindTexture(Textures.MODEL_WATCH);
		 * 
		 * model.renderHours();
		 * 
		 * GL11.glPopMatrix();
		 */
	}

	private void renderArm() {
		GL11.glPushMatrix();
		GL11.glRotatef(-25, 1, 0, 0);
		GL11.glRotatef(-30, 0, 0, 1);
		GL11.glTranslatef(2.5F, -0.25F, 0F);
		EntityClientPlayerMP player = FMLClientHandler.instance().getClient().thePlayer;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		FMLClientHandler.instance().getClient().getTextureManager()
		        .bindTexture(player.getLocationSkin());
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		Render render = RenderManager.instance
		        .getEntityRenderObject(FMLClientHandler.instance().getClient().thePlayer);
		RenderPlayer renderplayer = (RenderPlayer) render;
		float f11 = 3.0F;
		GL11.glScalef(f11, f11, f11);
		renderplayer.renderFirstPersonArm(FMLClientHandler.instance()
		        .getClient().thePlayer);
		GL11.glPopMatrix();
	}

}
