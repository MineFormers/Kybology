package de.mineformers.kybology.core.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import cpw.mods.fml.common.FMLCommonHandler;
import de.mineformers.kybology.core.lib.Commands;

/**
 * Kybology
 * 
 * CommandTT
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class CommandTT extends CommandBase {

    @Override
    public String getCommandName() {
        return Commands.COMMAND_TT;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length > 0) {
            String commandName = args[0];
            System.arraycopy(args, 1, args, 0, args.length - 1);

            if (commandName.equals(Commands.COMMAND_FIND))
                CommandFind.processCommand(sender, args);

            FMLCommonHandler.instance().getSidedDelegate().getServer()
                    .isPVPEnabled();
        } else
            throw new WrongUsageException(Commands.COMMAND_TT_USAGE,
                    new Object[0]);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

}
