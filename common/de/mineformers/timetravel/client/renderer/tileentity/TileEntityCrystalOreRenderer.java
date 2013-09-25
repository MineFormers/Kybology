package de.mineformers.timetravel.client.renderer.tileentity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import de.mineformers.timetravel.client.model.ModelCrystalOre;
import de.mineformers.timetravel.lib.Textures;

/**
 * TimeTravel
 * 
 * TileEntityCrystalOreRenderer
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class TileEntityCrystalOreRenderer extends TileEntitySpecialRenderer {

	ModelCrystalOre model;

	public TileEntityCrystalOreRenderer() {
		model = new ModelCrystalOre();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y,
			double z, float tick) {
		GL11.glPushMatrix();
		FMLClientHandler.instance().getClient().getTextureManager()
				.bindTexture(Textures.MODEL_CRYSTAL_ORE);
		GL11.glColor4f(0.498F, 0.737F, 1F, 0.95F);
		GL11.glTranslatef((float) x, (float) y, (float) z + 1);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		model.render();

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

}
