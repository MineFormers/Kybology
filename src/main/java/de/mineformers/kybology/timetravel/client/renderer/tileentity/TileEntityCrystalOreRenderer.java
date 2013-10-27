package de.mineformers.kybology.timetravel.client.renderer.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import de.mineformers.kybology.core.client.util.UtilsFX;
import de.mineformers.kybology.timetravel.client.model.ModelCrystals;
import de.mineformers.kybology.timetravel.lib.Textures;

/**
 * Kybology
 * 
 * TileEntityCrystalOreRenderer
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class TileEntityCrystalOreRenderer extends TileEntitySpecialRenderer {

    private ModelCrystals model;

    public TileEntityCrystalOreRenderer() {
        model = new ModelCrystals();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y,
            double z, float tick) {
        GL11.glPushMatrix();
        UtilsFX.bindTexture(Textures.MODEL_CRYSTALS);
        switch (tileEntity.getBlockMetadata()) {
            case 0:
                GL11.glColor4f(0.498F, 0.737F, 1F, 0.95F);
                break;
            case 1:
                GL11.glColor4f(1F, 0F, 0F, 0.95F);
                break;
            case 2:
                GL11.glColor4f(1F, 0.66F, 0F, 0.95F);
                break;
            default:
                GL11.glColor4f(0.498F, 0.737F, 1F, 0.95F);
                break;
        }
        GL11.glTranslatef((float) x, (float) y, (float) z + 1);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        model.renderOre();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

}
