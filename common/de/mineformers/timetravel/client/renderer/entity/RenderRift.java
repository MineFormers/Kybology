package de.mineformers.timetravel.client.renderer.entity;

import de.mineformers.timetravel.core.util.ResourceHelper;
import de.mineformers.timetravel.entity.EntityRift;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * TimeTravel
 * 
 * RenderRift
 * 
 * @author PaleoCrafter, Weneg
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class RenderRift extends Render {

    private static final ResourceLocation[] textures = new ResourceLocation[] {
    	ResourceHelper.getResourceLocation("textures/models/rift.png"),
    };
	
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float f,
	        float f1) {
		this.bindEntityTexture(entity);

		Tessellator tess = Tessellator.instance;

		// Top
		tess.startDrawingQuads();
		tess.addVertexWithUV(x + 2, y + 2, z + 2, 1, 1);
		tess.addVertexWithUV(x + 2, y + 2, z, 1, 0);
		tess.addVertexWithUV(x, y + 2, z, 0, 0);
		tess.addVertexWithUV(x, y + 2, z + 2, 0, 1);
		tess.draw();

		// Bottom
		tess.startDrawingQuads();
		tess.addVertexWithUV(x, y, z, 0, 0);
		tess.addVertexWithUV(x + 2, y, z, 1, 0);
		tess.addVertexWithUV(x + 2, y, z + 2, 1, 1);
		tess.addVertexWithUV(x, y, z + 2, 0, 1);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertexWithUV(x, y, z, 0, 0);
		tess.addVertexWithUV(x, y + 2, z, 0, 1);
		tess.addVertexWithUV(x + 2, y + 2, z, 1, 1);
		tess.addVertexWithUV(x + 2, y, z, 1, 1);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertexWithUV(x + 2, y + 2, z + 2, 1, 1);
		tess.addVertexWithUV(x, y + 2, z + 2, 0, 1);
		tess.addVertexWithUV(x, y, z + 2, 0, 0);
		tess.addVertexWithUV(x + 2, y, z + 2, 1, 0);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertexWithUV(x + 2, y, z, 0, 0);
		tess.addVertexWithUV(x + 2, y + 2, z, 1, 0);
		tess.addVertexWithUV(x + 2, y + 2, z + 2, 1, 1);
		tess.addVertexWithUV(x + 2, y, z + 2, 0, 1);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertexWithUV(x, y + 2, z + 2, 1, 1);
		tess.addVertexWithUV(x, y + 2, z, 1, 0);
		tess.addVertexWithUV(x, y, z, 0, 0);
		tess.addVertexWithUV(x, y, z + 2, 0, 1);
		tess.draw();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		if(entity instanceof EntityRift)
		return textures[(((EntityRift) entity).getType())];
		return textures[0];
	}

}
