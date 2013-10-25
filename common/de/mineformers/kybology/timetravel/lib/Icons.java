package de.mineformers.kybology.timetravel.lib;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;

/**
 * Kybology
 * 
 * Icons
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class Icons {

    @ForgeSubscribe
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        if (event.map.textureType == 0) {
            // Register block icons
        }
    }
}
