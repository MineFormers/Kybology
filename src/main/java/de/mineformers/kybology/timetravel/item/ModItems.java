package de.mineformers.kybology.timetravel.item;

import cpw.mods.fml.common.registry.GameRegistry;
import de.mineformers.kybology.timetravel.lib.ItemIds;
import de.mineformers.kybology.timetravel.lib.Strings;
import net.minecraft.item.Item;

/**
 * Kybology
 * 
 * ModItems
 * 
 * @author PaleoCrafter, Weneg
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ModItems {
	
	public static Item stopWatch;
	public static Item crystal;

	public static void init() {
		stopWatch = new ItemPocketWatch(ItemIds.WATCH);
		crystal = new ItemCrystal(ItemIds.CRYSTAL);
		
		GameRegistry.registerItem(stopWatch, Strings.WATCH_NAME);
	}
	
}
