package de.mineformers.timetravel.item;

import de.mineformers.timetravel.lib.Strings;

/**
 * TimeTravel
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
