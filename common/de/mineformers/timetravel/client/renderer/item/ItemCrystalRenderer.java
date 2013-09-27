package de.mineformers.timetravel.client.renderer.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import de.mineformers.timetravel.client.model.ModelCrystals;
import de.mineformers.timetravel.lib.CrystalProperties;
import de.mineformers.timetravel.lib.Strings;
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
        if (stack.getTagCompound() == null) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setString(Strings.NBT_CRYSTAL_QUALITY,
                    Strings.CRYSTAL_QUALITIES[0]);
            compound.setString(Strings.NBT_CRYSTAL_COLOR,
                    Strings.CRYSTAL_COLORS[0]);
            CrystalProperties properties = CrystalProperties.NOTHING;
            stack.getTagCompound().setString(Strings.NBT_CRYSTAL_TYPE,
                    properties.getDisplayKey());
        }
        String colorStr = stack.getTagCompound().getString(
                Strings.NBT_CRYSTAL_COLOR);
        int color = 0;
        for (int i = 0; i < Strings.CRYSTAL_COLORS.length; i++)
            if (Strings.CRYSTAL_COLORS[i].equals(colorStr))
                color = i;
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
                GL11.glTranslatef(-0.25F, 1, -0.25F);
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
