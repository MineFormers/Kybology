package de.mineformers.kybology.timetravel.handler;

import cpw.mods.fml.common.event.FMLServerStartingEvent;
import de.mineformers.kybology.core.command.CommandTT;

/**
 * Kybology
 *
 * CommandHandler
 *
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
public class CommandHandler {

    public static void initCommands(FMLServerStartingEvent event) {

        event.registerServerCommand(new CommandTT());
    }
}
