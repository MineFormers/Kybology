package de.mineformers.kybology.timetravel.lib;

import de.mineformers.kybology.KybologyCore;

/**
 * Kybology
 * 
 * Sounds
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class Sounds {

    private static final String SOUND_RESOURCE_LOCATION = KybologyCore.MOD_ID
            .toLowerCase() + ":";
    private static final String SOUND_PREFIX = KybologyCore.MOD_ID
            .toLowerCase() + ":";

    public static final String[] soundFiles = {
            SOUND_RESOURCE_LOCATION + "countdown/one.ogg",
            SOUND_RESOURCE_LOCATION + "countdown/two.ogg",
            SOUND_RESOURCE_LOCATION + "countdown/three.ogg",
            SOUND_RESOURCE_LOCATION + "countdown/four.ogg",
            SOUND_RESOURCE_LOCATION + "countdown/five.ogg",
            SOUND_RESOURCE_LOCATION + "countdown/six.ogg",
            SOUND_RESOURCE_LOCATION + "countdown/seven.ogg",
            SOUND_RESOURCE_LOCATION + "countdown/eight.ogg",
            SOUND_RESOURCE_LOCATION + "countdown/nine.ogg",
            SOUND_RESOURCE_LOCATION + "countdown/ten.ogg" };

    public static final String[] countdown = { SOUND_PREFIX + "countdown.one",
            SOUND_PREFIX + "countdown.two", SOUND_PREFIX + "countdown.three",
            SOUND_PREFIX + "countdown.four", SOUND_PREFIX + "countdown.five",
            SOUND_PREFIX + "countdown.six", SOUND_PREFIX + "countdown.seven",
            SOUND_PREFIX + "countdown.eight", SOUND_PREFIX + "countdown.nine",
            SOUND_PREFIX + "countdown.ten" };

}
