package de.mineformers.kybology.core.util;

import net.minecraft.util.ResourceLocation;
import de.mineformers.kybology.KybologyCore;
import de.mineformers.kybology.core.lib.Reference;

/**
 * Kybology
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

    public static ResourceLocation getCoreResource(String path) {
        return getResourceLocation(Reference.MOD_GROUP.toLowerCase(),
                KybologyCore.RESOURCE_PATH + path);
    }

}
