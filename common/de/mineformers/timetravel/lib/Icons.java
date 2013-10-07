package de.mineformers.timetravel.lib;

import net.minecraft.util.Icon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;

/**
 * TimeTravel
 * 
 * Icons
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class Icons {

    public static Icon particleEnergyTrail;

    @ForgeSubscribe
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        if (event.map.textureType == 0) {
            particleEnergyTrail = event.map.registerIcon(Reference.MOD_ID
                    .toLowerCase() + ":particles/" + "energyTrail");
        }
    }
}
