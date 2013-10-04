package de.mineformers.timetravel.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import de.mineformers.timetravel.client.util.QuadHelper;
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
        GL11.glPushMatrix();
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        FMLClientHandler.instance().getClient().getTextureManager()
                .bindTexture(textures[0]);
        renderRift(entity.posX, entity.posY, entity.posZ + 0.5F, partialTick);
        FMLClientHandler.instance().getClient().getTextureManager()
                .bindTexture(textures[1]);
        renderVortex(entity.posX, entity.posY, entity.posZ + 0.5F, partialTick);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }

    private void renderVortex(double x, double y, double z, float partialTicks) {
        GL11.glPushMatrix();
        long time = Minecraft.getMinecraft().theWorld.getWorldTime();
        float rad = 6.283186F;
        float rotationAngle = (float) (time % 200L) / -200.0F * rad;
        GL11.glColor4f(1, 1F, 1, 0.75F);
        renderFacingQuad(x + 0.5F, y + 0.5F, z, rotationAngle, 0.75F,
                partialTicks);

        GL11.glColor4f(1, 1, 1, 1F);
        GL11.glPopMatrix();
    }

    private void renderRift(double x, double y, double z, float partialTicks) {
        GL11.glPushMatrix();
        GL11.glColor4f(1F, 1F, 1F, 1F);

        renderFacingQuad(x + 0.5F, y + 0.5F, z, 0, 1, partialTicks);

        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        if (entity instanceof EntityRift)
            return textures[(((EntityRift) entity).getType())];
        return textures[0];
    }

    private void renderFacingQuad(double px, double py, double pz, float angle,
            float scale, float partialTicks) {
        
        if ((Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer)) {
            Tessellator tessellator = Tessellator.instance;
            float arX = ActiveRenderInfo.rotationX;
            float arZ = ActiveRenderInfo.rotationZ;
            float arYZ = ActiveRenderInfo.rotationYZ;
            float arXY = ActiveRenderInfo.rotationXY;
            float arXZ = ActiveRenderInfo.rotationXZ;

            EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().renderViewEntity;
            double iPX = player.prevPosX + (player.posX - player.prevPosX)
                    * partialTicks;
            double iPY = player.prevPosY + (player.posY - player.prevPosY)
                    * partialTicks;
            double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ)
                    * partialTicks;

            GL11.glTranslated(-iPX, -iPY, -iPZ);

            tessellator.startDrawingQuads();

            Vec3 v1 = Vec3.createVectorHelper(-arX * scale - arYZ * scale,
                    -arXZ * scale, -arZ * scale - arXY * scale);
            Vec3 v2 = Vec3.createVectorHelper(-arX * scale + arYZ * scale, arXZ
                    * scale, -arZ * scale + arXY * scale);
            Vec3 v3 = Vec3.createVectorHelper(arX * scale + arYZ * scale, arXZ
                    * scale, arZ * scale + arXY * scale);
            Vec3 v4 = Vec3.createVectorHelper(arX * scale - arYZ * scale, -arXZ
                    * scale, arZ * scale - arXY * scale);

            if (angle != 0.0F) {
                Vec3 pvec = Vec3.createVectorHelper(iPX, iPY, iPZ);
                Vec3 tvec = Vec3.createVectorHelper(px, py, pz);
                Vec3 qvec = pvec.subtract(tvec).normalize();
                QuadHelper.setAxis(qvec, angle).rotate(v1);
                QuadHelper.setAxis(qvec, angle).rotate(v2);
                QuadHelper.setAxis(qvec, angle).rotate(v3);
                QuadHelper.setAxis(qvec, angle).rotate(v4);
            }

            tessellator.addVertexWithUV(px + v1.xCoord, py + v1.yCoord, pz
                    + v1.zCoord, 0.0D, 1.0D);
            tessellator.addVertexWithUV(px + v2.xCoord, py + v2.yCoord, pz
                    + v2.zCoord, 1.0D, 1.0D);
            tessellator.addVertexWithUV(px + v3.xCoord, py + v3.yCoord, pz
                    + v3.zCoord, 1.0D, 0.0D);
            tessellator.addVertexWithUV(px + v4.xCoord, py + v4.yCoord, pz
                    + v4.zCoord, 0.0D, 0.0D);

            tessellator.draw();
        }
    }
}
