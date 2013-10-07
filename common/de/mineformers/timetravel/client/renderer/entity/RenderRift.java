package de.mineformers.timetravel.client.renderer.entity;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.ForgeSubscribe;

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

    @ForgeSubscribe
    public void renderWorldLast(RenderWorldLastEvent event) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        int renderDistance = Minecraft.getMinecraft().gameSettings.renderDistance;
        int renderRadius = 0;
        switch (renderDistance) {
            case 0:
                renderRadius = 256;
                break;
            case 1:
                renderRadius = 128;
                break;
            case 2:
                renderRadius = 64;
                break;
            case 3:
                renderRadius = 32;
                break;
        }

        Minecraft mc = FMLClientHandler.instance().getClient();

        double playerX = mc.renderViewEntity.posX;
        double playerY = mc.renderViewEntity.posY;
        double playerZ = mc.renderViewEntity.posZ;
        @SuppressWarnings("rawtypes")
        List rifts = player.worldObj.getEntitiesWithinAABB(
                EntityRift.class,
                AxisAlignedBB.getBoundingBox(playerX - renderRadius, playerY
                        - renderRadius, playerZ - renderRadius, playerX
                        + renderRadius, playerY + renderRadius, playerZ
                        + renderRadius));
        for (Object o : rifts) {
            EntityRift rift = (EntityRift) o;
            double distanceX = mc.renderViewEntity.posX - rift.posX;
            double distanceY = mc.renderViewEntity.posY - rift.posY;
            double distanceZ = mc.renderViewEntity.posZ - rift.posZ;

            double maxDistance = 16;

            double distanceSquared = distanceX * distanceX + distanceY
                    * distanceY + distanceZ * distanceZ;

            GL11.glPushMatrix();

            if (distanceSquared > maxDistance * maxDistance) {
                GL11.glColor4d(1, 1, 1, 1 / Math.sqrt(distanceSquared) / 2);
            }
            if (distanceSquared > renderRadius * renderRadius)
                continue;
            doRender(rift, event.partialTicks);

            GL11.glPopMatrix();
        }
    }

    private void doRender(EntityRift entity, float partialTick) {
        GL11.glPushMatrix();
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        float alpha = 0.001F * entity.getStoredEnergy();
        GL11.glColor4f(1, 1, 1, alpha);
        float scale = 0.001F * (entity.getStoredEnergy() + 20);
        if (scale > 1)
            scale = 1;
        FMLClientHandler.instance().getClient().getTextureManager()
                .bindTexture(textures[0]);
        renderRift(entity.posX, entity.posY, entity.posZ, partialTick, scale);
        FMLClientHandler.instance().getClient().getTextureManager()
                .bindTexture(textures[1]);
        renderVortex(entity.posX, entity.posY, entity.posZ, partialTick, scale);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(true);
        GL11.glColor4f(1, 1, 1, 1F);
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float f,
            float partialTick) {
    }

    private void renderVortex(double x, double y, double z, float partialTicks,
            float scale) {
        GL11.glPushMatrix();
        long time = Minecraft.getMinecraft().theWorld.getWorldTime();
        float rad = 6.283186F;
        float rotationAngle = (float) (time % 200L) / -200.0F * rad;
        renderFacingQuad(x, y + 0.5F, z, rotationAngle, scale * 0.75F,
                partialTicks);

        GL11.glPopMatrix();
    }

    private void renderRift(double x, double y, double z, float partialTicks,
            float scale) {
        GL11.glPushMatrix();

        renderFacingQuad(x, y + 0.5F, z, 0, scale, partialTicks);

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
