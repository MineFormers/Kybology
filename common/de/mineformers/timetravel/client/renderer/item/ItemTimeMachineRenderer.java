package de.mineformers.timetravel.client.renderer.item;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import de.mineformers.timetravel.client.model.ModelTimeMachine;
import de.mineformers.timetravel.lib.Textures;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

/**
 * TimeTravel
 * 
 * ItemTimeMachineRenderer
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ItemTimeMachineRenderer implements IItemRenderer {

	private ModelTimeMachine model;

	public ItemTimeMachineRenderer() {
		model = new ModelTimeMachine();
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
		int dmg = item.getItemDamage();

		if (dmg != 4) {
			switch (type) {
				case ENTITY: {
					renderModel(dmg, -0.5F, -1.2F, 0.5F, 1F);
					return;
				}
				case EQUIPPED: {
					renderModel(dmg, 0.5F, -2F, -0.6F, 1F);
					return;
				}
				case EQUIPPED_FIRST_PERSON: {
					renderModel(dmg, 0.5F, -2F, -0.6F, 1F);
					return;
				}
				case INVENTORY: {
					renderModel(dmg, 0.0F, -1.2F, 0.0F, 1F);
					return;
				}
				default:
					return;
			}
		} else {
			switch (type) {
				case ENTITY: {
					renderModule(-0.5F, -1F, 0.5F, 0.5F);
					return;
				}
				case EQUIPPED: {
					renderModule(-0.6F, -2F, 0.75F, 0.5F);
					return;
				}
				case EQUIPPED_FIRST_PERSON: {
					renderModule(-1.05F, -2.75F, 0.5F, 0.5F);
					return;
				}
				case INVENTORY: {
					renderModel(dmg, 0.0F, -2.2F, 0.0F, 0.5F);
					return;
				}
				default:
					return;
			}
		}
	}

	private void renderModel(int dmg, float x, float y, float z, float scale) {

		GL11.glPushMatrix();

		// Scale, Translate, Rotate
		GL11.glRotatef(180, 0, 0, 1);
		GL11.glRotatef(180, 0, 1, 0);
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(x, y, z);

		// Bind texture
		FMLClientHandler.instance().getClient().renderEngine
		        .bindTexture(Textures.MODEL_TIMEMACHINE);

		// Render
		switch (dmg) {
			case 0:
				model.renderBase();
				break;
			case 1:
				GL11.glTranslatef(-1F, 0F, 1F);
				model.renderPanelStand();
				GL11.glTranslatef(1.9125F, -0.6F, -0.3125F);
				GL11.glRotatef(45, 0, 1, 0);
				GL11.glRotatef(57, 0, 0, 1);
				model.renderPanelDisplay();
				break;
			case 2:
				GL11.glTranslatef(-1F, 0F, 0F);
				model.renderStairs();
				break;
			case 3:
				GL11.glScalef(0.6F, 0.6F, 0.6F);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glTranslatef(-1F, 1.5F, 1F);
				model.renderCorner();
				GL11.glDisable(GL11.GL_BLEND);
				break;
			case 4:
				FMLClientHandler.instance().getClient().renderEngine
				        .bindTexture(Textures.MODEL_TM_MODULE_DEFAULT);
				model.renderModule();
				break;
		}

		GL11.glPopMatrix();
	}

	private void renderModule(float x, float y, float z, float scale) {
		GL11.glPushMatrix();

		// Scale, Translate, Rotate
		GL11.glRotatef(180, 0, 0, 1);
		GL11.glRotatef(180, 0, 1, 0);
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(x, y, z);

		// Bind texture
		FMLClientHandler.instance().getClient().renderEngine
		        .bindTexture(Textures.MODEL_TM_MODULE_DEFAULT);
		model.renderModule();
		GL11.glPopMatrix();
	}

}
