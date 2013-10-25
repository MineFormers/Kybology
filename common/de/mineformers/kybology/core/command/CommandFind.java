package de.mineformers.kybology.core.command;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatMessageComponent;
import de.mineformers.kybology.core.entity.EntityRift;

/**
 * Kybology
 * 
 * CommandFind
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class CommandFind {

    @SuppressWarnings("unchecked")
    public static void processCommand(ICommandSender sender, String[] args) {
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;

            List<EntityRift> rifts = player.worldObj.getEntitiesWithinAABB(
                    EntityRift.class, AxisAlignedBB.getBoundingBox(
                            player.posX - 500, player.posY - 32,
                            player.posZ - 500, player.posX + 500,
                            player.posY + 32, player.posZ + 500));

            for (EntityRift rift : rifts) {
                sender.sendChatToPlayer(ChatMessageComponent
                        .createFromText(rift.getPosition().toString()));
            }
        }
    }
}
