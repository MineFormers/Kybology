package de.mineformers.timetravel.core.util;

import net.minecraft.util.StatCollector;
import de.mineformers.timetravel.lib.Reference;

/**
 * TimeTravel
 * 
 * LangHelper
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class LangHelper {
	
	public static String translate(String key) {
		return StatCollector.translateToLocal(key);
	}
	
	public static String translate(String type, String key) {
		return translate(getString(type, key));
	}

	public static String getString(String type, String key) {
		return type + "." + Reference.MOD_ID.toLowerCase() + ":" + key;
	}

}