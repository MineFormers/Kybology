package de.mineformers.kybology.timetravel.handler;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import de.mineformers.kybology.KybologyCore;
import de.mineformers.kybology.api.TravellingRegistry;
import de.mineformers.kybology.timetravel.entity.PlayerPropertiesTT;
import de.mineformers.kybology.timetravel.network.packet.PacketUpdateWatch;
import de.mineformers.kybology.timetravel.world.TeleporterTime;

/**
 * Kybology
 * 
 * TickHandlerCountdown
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class TickHandlerCountdown implements IScheduledTickHandler {

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {

	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		EntityPlayer player = (EntityPlayer) tickData[0];
		World world = player.getEntityWorld();

		if (TravellingRegistry
		        .isValidTimeDestination(world.provider.dimensionId)) {
			PlayerPropertiesTT props = PlayerPropertiesTT.getByEntity(player);
			if (props.getSecondsAvail() > 0) {
				int secsLeft = props.getSecondsLeft();
				secsLeft -= 1;

				PacketDispatcher.sendPacketToPlayer(new PacketUpdateWatch(
				        secsLeft).makePacket(), (Player) player);

				if (secsLeft <= 0) {
					secsLeft = 0;
					props.setSecondsLeft(0);
					props.setSecondsAvail(0);
					props.setTimeStarted(0);

					if (player instanceof EntityPlayerMP) {
						EntityPlayerMP thePlayer = (EntityPlayerMP) player;
						if (thePlayer.timeUntilPortal > 0) {
							thePlayer.timeUntilPortal = 20;
						} else {
							thePlayer.timeUntilPortal = 20;
							thePlayer.mcServer
							        .getConfigurationManager()
							        .transferPlayerToDimension(
							                thePlayer,
							                props.getTmDimension(),
							                new TeleporterTime(
							                        thePlayer.mcServer
							                                .worldServerForDimension(props
							                                        .getTmDimension())));
						}
					}

					return;
				}

				props.setSecondsLeft(secsLeft);
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		return KybologyCore.MOD_NAME + ": " + this.getClass().getSimpleName();
	}

	@Override
	public int nextTickSpacing() {
		return 20;
	}

}
