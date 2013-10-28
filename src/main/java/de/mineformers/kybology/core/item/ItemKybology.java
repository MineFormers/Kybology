package de.mineformers.kybology.core.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.mineformers.kybology.KybologyCore;
import de.mineformers.kybology.core.lib.Reference;
import de.mineformers.kybology.timetravel.lib.Strings;

/**
 * Kybology
 * 
 * ItemKybology
 * 
 * @author PaleoCrafter, Weneg
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ItemKybology extends Item {

    public ItemKybology(int id, String name) {
        super(id - Reference.SHIFTED_ID_RANGE_CORRECTION);
        this.setUnlocalizedName(Strings.RESOURCE_PREFIX + name);
        this.setCreativeTab(KybologyCore.tabKybology);
    }

    public ItemKybology(int id) {
        super(id - Reference.SHIFTED_ID_RANGE_CORRECTION);
        this.setCreativeTab(KybologyCore.tabKybology);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(KybologyCore.MOD_ID.toLowerCase()
                + ":"
                + super.getUnlocalizedName().substring(
                        super.getUnlocalizedName().indexOf(":") + 1));
    }
}
