package de.mineformers.timetravel.client.renderer.entity;

import de.mineformers.timetravel.core.util.ResourceHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * TimeTravel
 * 
 * RenderRift
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class RenderRift extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f,
	        float f1) {
		Tessellator tess = Tessellator.instance;

		tess.startDrawingQuads();
		tess.addVertex(x + 2, y + 2, z + 2);
		tess.addVertex(x + 2, y + 2, z);
		tess.addVertex(x, y + 2, z);
		tess.addVertex(x, y + 2, z + 2);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertex(x, y, z);
		tess.addVertex(x, y, z + 2);
		tess.addVertex(x + 2, y, z + 2);
		tess.addVertex(x + 2, y, z);
		tess.draw();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ResourceHelper.getResourceLocation("textures/models/rift.png");
	}

}
