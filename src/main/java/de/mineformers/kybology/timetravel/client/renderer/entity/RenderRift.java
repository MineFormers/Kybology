package de.mineformers.kybology.timetravel.client.renderer.entity;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import de.mineformers.kybology.core.client.util.UtilsFX;
import de.mineformers.kybology.core.entity.EntityRift;
import de.mineformers.kybology.core.util.ResourceHelper;

/**
 * Kybology
 * 
 * RenderRift
 * 
 * @author PaleoCrafter, Weneg
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class RenderRift extends Render {

    private static final ResourceLocation[] textures = new ResourceLocation[] {
            ResourceHelper.getCoreResource("textures/models/rift.png"),
            ResourceHelper
                    .getCoreResource("textures/models/rift_vortex.png") };

    @ForgeSubscribe(priority = EventPriority.HIGHEST)
    public void renderWorldLast(RenderWorldLastEvent event) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        int renderRadius = (int) Math.sqrt(UtilsFX.getRenderRadius());

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
            double distanceX = playerX - rift.posX;
            double distanceY = playerY - rift.posY;
            double distanceZ = playerZ - rift.posZ;

            double maxDistance = 16;

            double distanceSquared = distanceX * distanceX + distanceY
                    * distanceY + distanceZ * distanceZ;

            GL11.glPushMatrix();
            
            double renderAlpha = 0;

            if (distanceSquared > maxDistance * maxDistance) {
                renderAlpha = 1d / Math.sqrt(distanceSquared) / 2d;
            }
            if (distanceSquared > renderRadius * renderRadius)
                continue;
            if (UtilsFX.visibleToPlayer(AxisAlignedBB.getBoundingBox(
                    rift.posX - 1, rift.posY - 1, rift.posZ - 1, rift.posX + 1,
                    rift.posY + 1, rift.posZ + 1)))
                doRender(rift, event.partialTicks, renderAlpha);

            GL11.glPopMatrix();
        }
    }

    private void doRender(EntityRift entity, float partialTick, double renderAlpha) {
        GL11.glPushMatrix();
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        float alpha = (float) (0.001F * entity.getStoredEnergy() - (1- renderAlpha));
        GL11.glColor4f(1, 1, 1, alpha);
        float scale = 0.001F * (entity.getStoredEnergy() + 20);
        if (scale > 1)
            scale = 1;
        UtilsFX.bindTexture(textures[0]);
        renderRift(entity.posX, entity.posY, entity.posZ, partialTick, scale);
        UtilsFX.bindTexture(textures[1]);
        renderVortex(entity.posX, entity.posY, entity.posZ, partialTick, scale);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
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
        double rad = Math.PI * 2;
        float rotationAngle = (float) ((float) (time % 200L) / -200.0F * rad);
        UtilsFX.renderFacingQuad(x, y + 0.5F, z, rotationAngle, scale * 0.75F,
                partialTicks);

        GL11.glPopMatrix();
    }

    private void renderRift(double x, double y, double z, float partialTicks,
            float scale) {
        GL11.glPushMatrix();

        UtilsFX.renderFacingQuad(x, y + 0.5F, z, 0, scale, partialTicks);

        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        if (entity instanceof EntityRift)
            return textures[(((EntityRift) entity).getType())];
        return textures[0];
    }

}
