package de.mineformers.kybology.core.item;

import de.mineformers.kybology.KybologyCore;
import de.mineformers.kybology.timetravel.lib.Strings;
import net.minecraft.item.ItemBlock;

/**
 * Kybology
 * 
 * ItemBlockKybology
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ItemBlockKybology extends ItemBlock {

    public ItemBlockKybology(int id, String name) {
        super(id);
        this.setUnlocalizedName(Strings.RESOURCE_PREFIX + name);
        this.setCreativeTab(KybologyCore.tabKybology);
    }

}
