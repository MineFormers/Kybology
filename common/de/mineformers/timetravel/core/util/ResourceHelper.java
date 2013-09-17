package de.mineformers.timetravel.core.util;

import net.minecraft.util.ResourceLocation;
import de.mineformers.timetravel.lib.Reference;

/**
 * TimeTravel
 * 
 * ResourceHelper
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ResourceHelper {

	public static ResourceLocation getResourceLocation(String modId, String path) {

		return new ResourceLocation(modId, path);
	}

	public static ResourceLocation getResourceLocation(String path) {

		return getResourceLocation(Reference.MOD_ID.toLowerCase(), path);
	}

}
