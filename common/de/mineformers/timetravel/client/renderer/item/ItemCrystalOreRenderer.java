package de.mineformers.timetravel.client.renderer.item;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import de.mineformers.timetravel.client.model.ModelCrystals;
import de.mineformers.timetravel.lib.Textures;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

/**
 * TimeTravel
 * 
 * ItemCrystalOreRenderer
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ItemCrystalOreRenderer implements IItemRenderer {

	private ModelCrystals model;

	public ItemCrystalOreRenderer() {
		model = new ModelCrystals();
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
			renderModel(0F, 0F, 0F, 1F);
			return;
		}
		case EQUIPPED: {
			renderModel(0F, 0F, 0F, 1F);
			return;
		}
		case EQUIPPED_FIRST_PERSON: {
			renderModel(0F, 0.25F, 0, 1F);
			return;
		}
		case INVENTORY: {
			renderModel(0.0F, 0F, 0.0F, 1F);
			return;
		}
		default:
			return;
		}
	}

	private void renderModel(float x, float y, float z, float scale) {

		GL11.glPushMatrix();

		// Scale, Translate, Rotate
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(x, y, z);

		FMLClientHandler.instance().getClient().renderEngine
				.bindTexture(Textures.MODEL_CRYSTALS);
		GL11.glColor4f(0.498F, 0.737F, 1F, 0.95F);
		GL11.glTranslatef((float) x, (float) y, (float) z + 1);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		model.renderOre();

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

}
