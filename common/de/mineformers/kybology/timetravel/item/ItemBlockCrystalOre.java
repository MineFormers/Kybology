package de.mineformers.kybology.timetravel.item;

import de.mineformers.kybology.core.item.ItemBlockSub;
import de.mineformers.kybology.timetravel.lib.Strings;

/**
 * Kybology
 * 
 * ItemBlockCrystalOre
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ItemBlockCrystalOre extends ItemBlockSub {

	public ItemBlockCrystalOre(int id) {
		super(id, Strings.CRYSTAL_ORE_NAME, new String[] { "blue", "red",
				"gold" });
	}
}
