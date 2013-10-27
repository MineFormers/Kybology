package de.mineformers.kybology.timetravel.client.renderer.tileentity;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import de.mineformers.kybology.api.energy.CrystalType;
import de.mineformers.kybology.api.util.CrystalHelper;
import de.mineformers.kybology.core.client.util.UtilsFX;
import de.mineformers.kybology.core.entity.EntityRift;
import de.mineformers.kybology.timetravel.client.model.ModelCrystals;
import de.mineformers.kybology.timetravel.client.model.ModelExtractor;
import de.mineformers.kybology.timetravel.lib.Textures;
import de.mineformers.kybology.timetravel.tileentity.TileEnergyExtractor;

/**
 * Kybology
 * 
 * TileEntityExtractorRenderer
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class TileEntityExtractorRenderer extends TileEntitySpecialRenderer {

    private ModelCrystals modelCrystal;
    private ModelExtractor model;
    private final RenderItem customRenderItem;

    public TileEntityExtractorRenderer() {
        modelCrystal = new ModelCrystals();
        model = new ModelExtractor();
        customRenderItem = new RenderItem() {

            @Override
            public boolean shouldBob() {
                return false;
            };
        };

        customRenderItem.setRenderManager(RenderManager.instance);
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y,
            double z, float partialTick) {

        TileEnergyExtractor tileExtractor = (TileEnergyExtractor) tile;

        UtilsFX.bindTexture(Textures.MODEL_EXTRACTOR);
        GL11.glPushMatrix();
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glTranslatef((float) x, (float) y, (float) z + 1);
        model.renderExtractor();
        GL11.glPopMatrix();

        if (tileExtractor.getStackInSlot(TileEnergyExtractor.SLOT_STORAGE) != null) {
            GL11.glPushMatrix();
            float scaleFactor = 1;
            float rotationAngle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);

            EntityItem ghostEntityItem = new EntityItem(tile.worldObj);
            ghostEntityItem.hoverStart = 0.0F;
            ghostEntityItem.setEntityItemStack(tileExtractor
                    .getStackInSlot(TileEnergyExtractor.SLOT_STORAGE));

            GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F,
                    (float) z + 0.5F);
            GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);
            GL11.glRotatef(rotationAngle, 0.0F, 1.0F, 0.0F);

            customRenderItem.doRenderItem(ghostEntityItem, 0, 0, 0, 0, 0);
            GL11.glPopMatrix();
        }

        if (tileExtractor.getStackInSlot(TileEnergyExtractor.SLOT_INPUT) != null) {
            CrystalType crystalType = CrystalHelper.getType(tileExtractor
                    .getStackInSlot(TileEnergyExtractor.SLOT_INPUT));
            int color = crystalType.getColor().ordinal();
            float bob = MathHelper
                    .sin(Minecraft.getMinecraft().renderViewEntity.ticksExisted / 14.0F) * 0.03F + 0.03F;
            float weave = MathHelper
                    .sin(Minecraft.getMinecraft().renderViewEntity.ticksExisted / 10.0F) * 0.5F + 0.5F;
            float angle = 0;
            @SuppressWarnings("rawtypes")
            List entities = Minecraft.getMinecraft().theWorld
                    .getEntitiesWithinAABB(EntityRift.class, AxisAlignedBB
                            .getBoundingBox(tile.xCoord - 25, tile.yCoord - 25,
                                    tile.zCoord - 25, tile.xCoord + 25,
                                    tile.yCoord + 25, tile.zCoord + 25));

            for (Object o : entities) {
                EntityRift rift = (EntityRift) o;
                angle = getAngle(tile.xCoord + 0.5F, tile.zCoord + 0.5F,
                        rift.posX, rift.posZ);
                break;
            }

            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            FMLClientHandler.instance().getClient().renderEngine
                    .bindTexture(Textures.MODEL_CRYSTALS);
            switch (color) {
                case 0:
                    GL11.glColor4f(0.498F, 0.737F, 1F, 0.80F);
                    break;
                case 1:
                    GL11.glColor4f(1F, 0F, 0F, 0.80F);
                    break;
                case 2:
                    GL11.glColor4f(1F, 0.66F, 0F, 0.80F);
                    break;
                default:
                    GL11.glColor4f(0.498F, 0.737F, 1F, 0.80F);
                    break;
            }
            GL11.glTranslatef((float) x + 0.5F, (float) y + 1.1F + bob,
                    (float) z + 0.5F);
            GL11.glRotatef(angle, 0, 1, 0);
            GL11.glTranslatef(-0.25F, 0, 0.3F);
            GL11.glRotatef(90, 1, 0, 0);
            GL11.glRotatef(85.0F + weave * 10.0F, 1.0F, 0, 0.0F);
            GL11.glScalef(0.5F, 0.5F, 0.5F);

            modelCrystal.renderTransferCrystal();
            GL11.glPopMatrix();
        }
    }

    public float getAngle(double x, double y, double targetX, double targetY) {
        float angle = (float) Math.toDegrees(Math.atan2(targetX - x, targetY
                - y));

        return angle;
    }

}
