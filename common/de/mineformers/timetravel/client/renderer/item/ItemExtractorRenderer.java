package de.mineformers.timetravel.client.renderer.item;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import de.mineformers.timetravel.client.model.ModelExtractor;
import de.mineformers.timetravel.lib.Textures;

/**
 * TimeTravel
 * 
 * ItemExtractorRenderer
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ItemExtractorRenderer implements IItemRenderer {

    private ModelExtractor model;

    public ItemExtractorRenderer() {
        model = new ModelExtractor();
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
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case ENTITY: {
                if (RenderItem.renderInFrame)
                    renderModel(item.getItemDamage(), -0.25F, -0.2F, -0.25F,
                            1.5F);
                else
                    renderModel(item.getItemDamage(), -0.25F, -0.15F, -0.25F,
                            2F);
                return;
            }
            case EQUIPPED: {
                renderModel(item.getItemDamage(), 0F, 0F, 0F, 1F);
                return;
            }
            case EQUIPPED_FIRST_PERSON: {
                renderModel(item.getItemDamage(), 0F, 0.25F, 0, 1F);
                return;
            }
            case INVENTORY: {
                renderModel(item.getItemDamage(), 0.0F, 0F, 0.0F, 1F);
                return;
            }
            default:
                return;
        }
    }

    private void renderModel(int color, float x, float y, float z, float scale) {

        GL11.glPushMatrix();

        // Scale, Translate, Rotate
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(x, y, z);

        FMLClientHandler.instance().getClient().renderEngine
                .bindTexture(Textures.MODEL_EXTRACTOR);
        GL11.glTranslatef((float) x, (float) y, (float) z + 1);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        model.render();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
}
