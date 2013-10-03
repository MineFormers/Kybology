package de.mineformers.timetravel.client.renderer.tileentity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import de.mineformers.timetravel.api.util.CrystalHelper;
import de.mineformers.timetravel.client.model.ModelExtractor;
import de.mineformers.timetravel.lib.ItemIds;
import de.mineformers.timetravel.lib.Textures;
import de.mineformers.timetravel.tileentity.TileEnergyExtractor;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * TimeTravel
 * 
 * TileEntityExtractorRenderer
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class TileEntityExtractorRenderer extends TileEntitySpecialRenderer {

    private ModelExtractor model;
    private final RenderItem customRenderItem;
    private ItemStack stack;

    public TileEntityExtractorRenderer() {
        model = new ModelExtractor();
        customRenderItem = new RenderItem() {

            @Override
            public boolean shouldBob() {
                return false;
            };
        };

        customRenderItem.setRenderManager(RenderManager.instance);
        stack = new ItemStack(ItemIds.CRYSTAL, 1, 0);
        stack.setTagCompound(new NBTTagCompound());
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y,
            double z, float partialTick) {
        TileEnergyExtractor tileExtractor = (TileEnergyExtractor) tile;
        if (tileExtractor.getCrystalColor() != -1) {
            stack.getTagCompound().setInteger(CrystalHelper.NBT_CRYSTAL_COLOR,
                    tileExtractor.getCrystalColor());
            GL11.glPushMatrix();
            float scaleFactor = 1;
            float rotationAngle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);

            EntityItem ghostEntityItem = new EntityItem(tile.worldObj);
            ghostEntityItem.hoverStart = 0.0F;
            ghostEntityItem.setEntityItemStack(stack);

            GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F,
                    (float) z + 0.5F);
            GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);
            GL11.glRotatef(rotationAngle, 0.0F, 1.0F, 0.0F);

            customRenderItem.doRenderItem(ghostEntityItem, 0, 0, 0, 0, 0);
            GL11.glPopMatrix();
        }
        FMLClientHandler.instance().getClient().getTextureManager()
                .bindTexture(Textures.MODEL_EXTRACTOR);
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z + 1);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        model.renderExtractor();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();

        renderSpheres(x, y, z);
    }

    private void renderSpheres(double x, double y, double z) {
        float rotationAngle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
        renderSphere(x + 0.82F, y, z + 0.82F, rotationAngle - 20);
        renderSphere(x + 0.18F, y, z + 0.18F, rotationAngle - 45);
        renderSphere(x + 0.18F, y, z + 0.82F, rotationAngle - 140);
        renderSphere(x + 0.82F, y, z + 0.18F, rotationAngle - 70);
    }

    private void renderSphere(double x, double y, double z, float rotationAngle) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y + 0.9F, (float) z);
        GL11.glRotatef(rotationAngle, 0, 1, 0);
        GL11.glRotatef(rotationAngle + 30, 1, 0, 0);
        GL11.glRotatef(rotationAngle - 20, 0, 0, 1);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        model.renderSphere();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
}
