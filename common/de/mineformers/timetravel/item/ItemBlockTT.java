package de.mineformers.timetravel.item;

import de.mineformers.timetravel.lib.Strings;
import net.minecraft.item.ItemBlock;

/**
 * TimeTravel
 * 
 * ItemBlockTT
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ItemBlockTT extends ItemBlock {

    public ItemBlockTT(int id, String name) {
	    super(id);
	    this.setUnlocalizedName(Strings.RESOURCE_PREFIX + name);
    }

}
