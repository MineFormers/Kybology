package de.mineformers.kybology.core.client.particle.updater;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import de.mineformers.kybology.core.client.particle.ParticleFX;
import de.mineformers.kybology.core.client.util.UtilsFX;

/**
 * Kybology
 * 
 * IParticleUpdater
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public abstract class IParticleUpdater {

    public abstract void onUpdate(ParticleFX fx);

    public void render(Tessellator tessellator, float partialTicks, float arX,
            float arXZ, float arZ, float arYZ, float arXY, ParticleFX fx) {
        GL11.glPushMatrix();

        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, 1);

        UtilsFX.bindTexture("textures/particles/" + fx.getTexture());

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);

        float minU = 0;
        float maxU = 1;
        float minV = 0.0F;
        float maxV = 1.0F;

        float x = (float) (fx.prevPosX + (fx.posX - fx.prevPosX) * partialTicks - EntityFX.interpPosX);
        float y = (float) (fx.prevPosY + (fx.posY - fx.prevPosY) * partialTicks - EntityFX.interpPosY);
        float z = (float) (fx.prevPosZ + (fx.posZ - fx.prevPosZ) * partialTicks - EntityFX.interpPosZ);

        tessellator.startDrawingQuads();
        tessellator.setBrightness(240);

        tessellator
                .setColorRGBA(fx.getR(), fx.getG(), fx.getB(), fx.getAlpha());
        tessellator.addVertexWithUV(x - arX * fx.scale - arYZ * fx.scale, y
                - arXZ * fx.scale, z - arZ * fx.scale - arXY * fx.scale, maxU,
                maxV);
        tessellator.addVertexWithUV(x - arX * fx.scale + arYZ * fx.scale, y
                + arXZ * fx.scale, z - arZ * fx.scale + arXY * fx.scale, maxU,
                minV);
        tessellator.addVertexWithUV(x + arX * fx.scale + arYZ * fx.scale, y
                + arXZ * fx.scale, z + arZ * fx.scale + arXY * fx.scale, minU,
                minV);
        tessellator.addVertexWithUV(x + arX * fx.scale - arYZ * fx.scale, y
                - arXZ * fx.scale, z + arZ * fx.scale - arXY * fx.scale, minU,
                maxV);

        tessellator.draw();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);

        GL11.glPopMatrix();
    }

}
