package de.mineformers.timetravel.client.audio;

import de.mineformers.timetravel.core.util.LogHelper;
import de.mineformers.timetravel.lib.Sounds;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

/**
 * TimeTravel
 * 
 * SoundHandler
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class SoundHandler {

	@ForgeSubscribe
	public void onSoundLoad(SoundLoadEvent event) {
		// For each custom sound file we have defined in Sounds
		for (String soundFile : Sounds.soundFiles) {
			// Try to add the custom sound file to the pool of sounds
			try {
				event.manager.addSound(soundFile);
			}
			// If we cannot add the custom sound file to the pool, log the
			// exception
			catch (Exception e) {
				LogHelper.warning("Failed loading sound file: " + soundFile);
			}
		}
	}

}
