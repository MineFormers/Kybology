package de.mineformers.kybology.core.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.mineformers.kybology.core.util.ResourceHelper;
import de.mineformers.kybology.core.util.Vector3;

/**
 * Kybology
 * 
 * UtilsFX
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class UtilsFX {

    public static double getDistanceToPlayer(Vector3 vec) {
        EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;

        return vec.distanceSquared(new Vector3(player.posX, player.posY,
                player.posZ));
    }

    public static double getRenderRadius() {
        int renderDistance = FMLClientHandler.instance().getClient().gameSettings.renderDistance;
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

        return Math.pow(renderRadius, 2);
    }

    @SideOnly(Side.CLIENT)
    public static boolean visibleToPlayer(AxisAlignedBB box) {
        Entity player = FMLClientHandler.instance().getClient().renderViewEntity;

        boolean valid = false;
        for (double x = box.minX; x <= box.maxX; x++) {
            for (double y = box.minY; y <= box.maxY; y++) {
                for (double z = box.minZ; z <= box.maxZ; z++) {
                    valid = player.worldObj
                            .clip(player.worldObj.getWorldVec3Pool()
                                    .getVecFromPool(
                                            player.posX,
                                            player.posY
                                                    + (double) player
                                                            .getEyeHeight(),
                                            player.posZ), player.worldObj
                                    .getWorldVec3Pool().getVecFromPool(x, y, z)) == null;
                }
            }
        }

        return valid;
    }

    public static void renderFacingQuad(double px, double py, double pz,
            float angle, float scale, float partialTicks) {
        GL11.glPushMatrix();
        if ((Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer)) {
            Tessellator tessellator = Tessellator.instance;
            float arX = ActiveRenderInfo.rotationX;
            float arZ = ActiveRenderInfo.rotationZ;
            float arYZ = ActiveRenderInfo.rotationYZ;
            float arXY = ActiveRenderInfo.rotationXY;
            float arXZ = ActiveRenderInfo.rotationXZ;

            Entity player = Minecraft.getMinecraft().renderViewEntity;
            double iPX = player.prevPosX + (player.posX - player.prevPosX)
                    * partialTicks;
            double iPY = player.prevPosY + (player.posY - player.prevPosY)
                    * partialTicks;
            double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ)
                    * partialTicks;

            GL11.glTranslated(-iPX, -iPY, -iPZ);

            tessellator.startDrawingQuads();

            Vector3 v1 = new Vector3(-arX * scale - arYZ * scale,
                    -arXZ * scale, -arZ * scale - arXY * scale);
            Vector3 v2 = new Vector3(-arX * scale + arYZ * scale, arXZ * scale,
                    -arZ * scale + arXY * scale);
            Vector3 v3 = new Vector3(arX * scale + arYZ * scale, arXZ * scale,
                    arZ * scale + arXY * scale);
            Vector3 v4 = new Vector3(arX * scale - arYZ * scale, -arXZ * scale,
                    arZ * scale - arXY * scale);

            if (angle != 0.0F) {
                Vector3 pvec = new Vector3(iPX, iPY, iPZ);
                Vector3 tvec = new Vector3(px, py, pz);
                Vector3 qvec = pvec.subtract(tvec).normalize();
                QuadHelper.setAxis(qvec, angle).rotate(v1);
                QuadHelper.setAxis(qvec, angle).rotate(v2);
                QuadHelper.setAxis(qvec, angle).rotate(v3);
                QuadHelper.setAxis(qvec, angle).rotate(v4);
            }

            tessellator.addVertexWithUV(px + v1.x, py + v1.y, pz + v1.z, 0.0D,
                    1.0D);
            tessellator.addVertexWithUV(px + v2.x, py + v2.y, pz + v2.z, 1.0D,
                    1.0D);
            tessellator.addVertexWithUV(px + v3.x, py + v3.y, pz + v3.z, 1.0D,
                    0.0D);
            tessellator.addVertexWithUV(px + v4.x, py + v4.y, pz + v4.z, 0.0D,
                    0.0D);

            tessellator.draw();
        }
        GL11.glPopMatrix();
    }

    public static void bindTexture(ResourceLocation path) {
        FMLClientHandler.instance().getClient().getTextureManager()
                .bindTexture(path);
    }

    public static void bindTexture(String path) {
        bindTexture(ResourceHelper.getCoreResource(path));
    }

}
