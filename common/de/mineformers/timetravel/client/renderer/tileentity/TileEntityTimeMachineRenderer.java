package de.mineformers.timetravel.client.renderer.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.FMLClientHandler;
import de.mineformers.timetravel.client.model.ModelTimeMachine;
import de.mineformers.timetravel.lib.Textures;
import de.mineformers.timetravel.tileentity.TileTT;
import de.mineformers.timetravel.tileentity.TileTimeMachine;
import de.mineformers.timetravel.travelling.timemachine.TMPartModule;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

/**
 * TimeTravel
 * 
 * TileEntityTimeMachineRenderer
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class TileEntityTimeMachineRenderer extends TileEntitySpecialRenderer {

	private ModelTimeMachine model = new ModelTimeMachine();

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y,
	        double z, float tick) {
		if (tileEntity instanceof TileTimeMachine) {
			FMLClientHandler.instance().getClient().renderEngine
			        .bindTexture(Textures.MODEL_TIMEMACHINE);

			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float) x, (float) y + 2.0F, (float) z + 1.0F);
			GL11.glScalef(1.0F, -1.0F, -1.0F);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);

			switch (((TileTT) tileEntity).getOrientation()) {
				case SOUTH:
					GL11.glRotatef(90, 0, 1, 0);
					break;
				case NORTH:
					GL11.glRotatef(-90, 0, 1, 0);
					break;
				case WEST:
					GL11.glRotatef(180, 0, 1, 0);
					break;
				default:
					break;
			}

			switch (tileEntity.getBlockMetadata()) {
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
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA,
					        GL11.GL_ONE_MINUS_SRC_ALPHA);
					GL11.glTranslatef(-1F, 0F, 1F);
					model.renderCorner();
					GL11.glDisable(GL11.GL_BLEND);
					break;
				case 4:
					TMPartModule module = (TMPartModule) ((TileTimeMachine) tileEntity)
					        .getPart();
					FMLClientHandler.instance().getClient().renderEngine
					        .bindTexture(module.getTexture());
					GL11.glScalef(0.5F, 0.5F, 0.5F);
					GL11.glTranslatef(-1F, 1F, 1F);
					model.renderModule();
					break;
			}

			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
