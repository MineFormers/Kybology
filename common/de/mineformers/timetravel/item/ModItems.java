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
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ModItems {
	
	public static Item stopWatch;
	public static Item crystal;

	public static void init() {
		stopWatch = new ItemPocketWatch(ItemIds.WATCH);
		crystal = new ItemCrystal(ItemIds.CRYSTAL, Strings.CRYSTAL_NAME);
		
		GameRegistry.registerItem(stopWatch, Strings.WATCH_NAME);
		GameRegistry.registerItem(crystal, Strings.CRYSTAL_NAME);
	}
	
}
