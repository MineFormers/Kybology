package de.mineformers.timetravel.client.renderer.item;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import de.mineformers.timetravel.api.energy.CrystalType;
import de.mineformers.timetravel.api.util.CrystalHelper;
import de.mineformers.timetravel.client.model.ModelCrystals;
import de.mineformers.timetravel.lib.Textures;

/**
 * TimeTravel
 * 
 * ItemCrystalRenderer
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ItemCrystalRenderer implements IItemRenderer {

    private ModelCrystals model;

    public ItemCrystalRenderer() {
        model = new ModelCrystals();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
            ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        CrystalType crystalType = CrystalHelper.getType(stack);
        int color = crystalType.getColor().ordinal();
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
        switch (stack.getItemDamage()) {
            case 0:
                renderStorage(type);
                break;
            case 1:
                renderTransfer(type);
                break;
            case 2:
                renderUndefined(type);
                break;
            default:
                break;
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private void renderStorage(ItemRenderType type) {
        GL11.glTranslatef(0.5F, 0, 0.5F);
        switch (type) {
            case ENTITY:
                GL11.glRotatef(90, 0, 1, 0);
                GL11.glRotatef(90, 1, 0, 0);
                GL11.glTranslatef(1, -0.5F, 0.5F);
                break;
            case EQUIPPED:
                GL11.glTranslatef(0.55F, 0.5F, -0F);
                GL11.glRotatef(45, 0, 1, 0);
                GL11.glRotatef(60, 1, 0, 0);
                break;
            case INVENTORY:
                GL11.glRotatef(45, 0, 1, 0);
                GL11.glRotatef(90, 1, 0, 0);
                GL11.glTranslatef(0.5F, 0, 0);
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glTranslatef(0, 0.5F, -0.25F);
                GL11.glRotatef(45, 0, 1, 0);
                GL11.glRotatef(90, 1, 0, 0);
                break;
            default:
                break;
        }
        model.renderStorageCrystal();
    }

    private void renderTransfer(ItemRenderType type) {
        switch (type) {
            case ENTITY:
                if (RenderItem.renderInFrame) {
                    GL11.glTranslatef(0.5F, 0.4F, 0.25F);
                    GL11.glTranslatef(-0.75F, 0, 0F);
                    GL11.glScalef(0.75F, 0.75F, 0.75F);
                } else {
                    GL11.glTranslatef(-0.25F, 1F, 0.4F);
                }
                break;
            case INVENTORY:
                GL11.glTranslatef(-0.75F, 0.3F, 0);
                break;
            case EQUIPPED:
                GL11.glTranslatef(0.3F, 1, 1F);
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glTranslatef(0.3F, 1.35F, 1F);
                break;
            default:
                break;
        }
        GL11.glRotatef(45, 0, 1, 0);
        GL11.glRotatef(90, 1, 0, 0);

        model.renderTransferCrystal();
    }

    private void renderUndefined(ItemRenderType type) {
        switch (type) {
            case ENTITY:
                GL11.glTranslatef(0.5F, -0.3F, -0.5F);
                break;
            case INVENTORY:
                GL11.glTranslatef(1F, -0.05F, 0);
                break;
            case EQUIPPED:
                GL11.glTranslatef(0.725F, 0.4F, 0F);
                GL11.glRotatef(45, 0, 1, 1);
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glTranslatef(0.5F, 0.75F, 0);
                break;
            default:
                break;
        }
        model.renderUndefinedCrystal();
    }

}
