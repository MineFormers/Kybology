package de.mineformers.timetravel.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.GameRegistry;
import de.mineformers.timetravel.client.gui.GuiTimeMachine;
import de.mineformers.timetravel.lib.GuiIds;
import de.mineformers.timetravel.lib.Strings;
import de.mineformers.timetravel.network.packet.PacketOpenGui;
import de.mineformers.timetravel.tileentity.TileTimeMachine;
import de.mineformers.timetravel.travelling.timemachine.TMPartPanel;

/**
 * TimeTravel
 * 
 * CommonProxy
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class CommonProxy implements IGuiHandler {

	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileTimeMachine.class,
		        Strings.TE_TIMEMACHINE_NAME);
	}

	public void initRenderingAndTextures() {

	}

	public void registerSoundHandler() {

	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
	        int x, int y, int z) {

		if (ID == GuiIds.TIMEMACHINE) {
			PacketDispatcher.sendPacketToPlayer(
			        new PacketOpenGui(ID, x, y, z).makePacket(),
			        (Player) player);
			return null;
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
	        int x, int y, int z) {
		if (ID == GuiIds.TIMEMACHINE) {
			TileTimeMachine tile = (TileTimeMachine) world.getBlockTileEntity(
			        x, y, z);
			return new GuiTimeMachine(x, y, z,
			        ((TMPartPanel) tile.getPart()).isCountingDown());
		}

		return null;
	}

}
