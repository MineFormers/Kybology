package de.mineformers.timetravel.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.mineformers.timetravel.core.util.LangHelper;
import de.mineformers.timetravel.lib.CrystalProperties;
import de.mineformers.timetravel.lib.Reference;
import de.mineformers.timetravel.lib.Strings;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Time Travel
 * 
 * ItemCrystall
 * 
 * @author Weneg
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ItemCrystal extends ItemTT {

    /**
     * @param id
     * @param name
     */
    public ItemCrystal(int id) {
        super(id, Strings.CRYSTAL_NAME);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public String getItemDisplayName(ItemStack stack) {
        String format = LangHelper.translate("item", "crystal.format");
        if (stack.getTagCompound() == null) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setString(Strings.NBT_CRYSTAL_QUALITY,
                    Strings.CRYSTAL_QUALITIES[0]);
            compound.setString(Strings.NBT_CRYSTAL_COLOR,
                    Strings.CRYSTAL_COLORS[0]);
            CrystalProperties properties = CrystalProperties.NOTHING;
            compound.setInteger(properties.getValueName(),
                    properties.getValue(0));
        }
        String qualityKey = stack.getTagCompound().getString(
                Strings.NBT_CRYSTAL_QUALITY);
        String quality = LangHelper.translate("item", "crystal.quality."
                + qualityKey);

        String name = format.replace("%q%", quality);
        name = name.replace("%h%", "Horallium");
        name = name.replace("%n%", super.getItemDisplayName(stack));

        return name;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list,
            boolean advanced) {
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
        String typeKey = stack.getTagCompound().getString(
                Strings.NBT_CRYSTAL_TYPE);
        String type = LangHelper.translate("item", "crystal.type." + typeKey);
        String colorKey = stack.getTagCompound().getString(
                Strings.NBT_CRYSTAL_COLOR);
        String color = LangHelper
                .translate("item", "crystal.color." + colorKey);

        list.add(type);
        if (colorKey.equals("blue"))
            list.add("\u00A7b" + color + "\u00A7r");
        else if (colorKey.equals("red"))
            list.add("\u00A7c" + color + "\u00A7r");
        else if (colorKey.equals("gold"))
            list.add("\u00A76" + color + "\u00A7r");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(Reference.MOD_ID
                .toLowerCase() + ":crystal");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(int id, CreativeTabs tab, List list) {
        CrystalProperties properties = null;
        for (int i = 0; i < 3; i++) {
            properties = CrystalProperties.values()[i];
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 3; k++) {
                    ItemStack stack = new ItemStack(this, 1, i);
                    if (stack.stackTagCompound == null)
                        stack.setTagCompound(new NBTTagCompound());
                    stack.getTagCompound().setString(
                            Strings.NBT_CRYSTAL_QUALITY,
                            Strings.CRYSTAL_QUALITIES[j]);
                    stack.getTagCompound().setString(Strings.NBT_CRYSTAL_COLOR,
                            Strings.CRYSTAL_COLORS[k]);
                    if (properties != CrystalProperties.NOTHING) {
                        stack.getTagCompound().setInteger(
                                properties.getValueName(),
                                properties.getValue(k));
                    }

                    stack.getTagCompound().setString(Strings.NBT_CRYSTAL_TYPE,
                            properties.getDisplayKey());

                    list.add(stack);
                }
            }
        }
    }
}
