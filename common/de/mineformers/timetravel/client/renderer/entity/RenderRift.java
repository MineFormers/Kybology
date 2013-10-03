package de.mineformers.timetravel.client.renderer.entity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import de.mineformers.timetravel.core.util.ResourceHelper;
import de.mineformers.timetravel.entity.EntityRift;

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
            ResourceHelper
                    .getResourceLocation("textures/models/rift_vortex.png") };

    @Override
    public void doRender(Entity entity, double x, double y, double z, float f,
            float partialTick) {
        FMLClientHandler.instance().getClient().getTextureManager()
                .bindTexture(textures[0]);
        renderRift(x, y, z + 0.5F);
        FMLClientHandler.instance().getClient().getTextureManager()
                .bindTexture(textures[1]);
        renderVortex(x, y, z + 0.5F);
    }

    private void renderVortex(double x, double y, double z) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glTranslated(x + 0.5F, y + 0.5F, z);
        float rotationAngle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL) * 2;
        GL11.glRotatef(rotationAngle, 0, 0, 1);
        GL11.glColor4f(1, 0.5F, 1, 0.5F);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        Tessellator tess = Tessellator.instance;

        tess.startDrawingQuads();
        tess.addVertexWithUV(-1F, -1F, -0.01F, 0, 1);
        tess.addVertexWithUV(-1F, 1F, -0.01F, 0, 0);
        tess.addVertexWithUV(1F, 1F, -0.01F, 1F, 0);
        tess.addVertexWithUV(1F, -1F, -0.01F, 1F, 1);
        tess.draw();

        tess.startDrawingQuads();
        tess.addVertexWithUV(1F, 1F, 0.01F, 1F, 0);
        tess.addVertexWithUV(-1F, 1F, 0.01F, 0, 0);
        tess.addVertexWithUV(-1F, -1F, 0.01F, 0, 1);
        tess.addVertexWithUV(1F, -1F, 0.01F, 1F, 1);
        tess.draw();
        GL11.glColor4f(1, 1, 1, 1F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    private void renderRift(double x, double y, double z) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glTranslated(x + 0.5F, y + 0.5F, z);
        GL11.glColor4f(0.5F, 0.5F, 0.5F, 1F);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        Tessellator tess = Tessellator.instance;

        tess.startDrawingQuads();
        tess.addVertexWithUV(-1.5F, -1.5F, 0, 0, 1);
        tess.addVertexWithUV(-1.5F, 1.5F, 0, 0, 0);
        tess.addVertexWithUV(1.5F, 1.5F, 0, 1F, 0);
        tess.addVertexWithUV(1.5F, -1.5F, 0, 1F, 1);
        tess.draw();

        tess.startDrawingQuads();
        tess.addVertexWithUV(1.5F, 1.5F, 0, 1F, 0);
        tess.addVertexWithUV(-1.5F, 1.5F, 0, 0, 0);
        tess.addVertexWithUV(-1.5F, -1.5F, 0, 0, 1);
        tess.addVertexWithUV(1.5F, -1.5F, 0, 1F, 1);
        tess.draw();
        GL11.glColor4f(1, 1, 1, 1F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        if (entity instanceof EntityRift)
            return textures[(((EntityRift) entity).getType())];
        return textures[0];
    }

}
