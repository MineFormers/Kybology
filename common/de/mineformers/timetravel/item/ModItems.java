package de.mineformers.timetravel.item;

import cpw.mods.fml.common.registry.GameRegistry;
import de.mineformers.timetravel.lib.ItemIds;
import de.mineformers.timetravel.lib.Strings;
import net.minecraft.item.Item;

/**
 * TimeTravel
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
