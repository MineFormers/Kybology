package de.mineformers.timetravel.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.GameRegistry;
import de.mineformers.timetravel.client.gui.GuiContainerTT;
import de.mineformers.timetravel.client.gui.GuiScreenTT;
import de.mineformers.timetravel.client.gui.WidgetCanvasConflict;
import de.mineformers.timetravel.client.gui.WidgetCanvasExtractor;
import de.mineformers.timetravel.client.gui.WidgetCanvasTimeTravel;
import de.mineformers.timetravel.core.util.LangHelper;
import de.mineformers.timetravel.inventory.ContainerExtractor;
import de.mineformers.timetravel.lib.GuiIds;
import de.mineformers.timetravel.lib.Strings;
import de.mineformers.timetravel.network.packet.PacketOpenGui;
import de.mineformers.timetravel.tileentity.TileCrystalOre;
import de.mineformers.timetravel.tileentity.TileEnergyExtractor;
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
		GameRegistry.registerTileEntity(TileEnergyExtractor.class,
				Strings.TE_ENERGY_EXTRACTOR_NAME);
		GameRegistry.registerTileEntity(TileCrystalOre.class,
				Strings.TE_CRYSTAL_ORE_NAME);
	}

	public void initRenderingAndTextures() {

	}

	public void registerSoundHandler() {

	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {

		switch (ID) {
		case GuiIds.TIMEMACHINE:
			PacketDispatcher.sendPacketToPlayer(
					new PacketOpenGui(ID, x, y, z).makePacket(),
					(Player) player);
			return null;
		case GuiIds.TM_CONFLICT_NO_MB:
			PacketDispatcher.sendPacketToPlayer(
					new PacketOpenGui(ID, x, y, z).makePacket(),
					(Player) player);
			return null;
		case GuiIds.TM_CONFLICT_MODULES:
			PacketDispatcher.sendPacketToPlayer(
					new PacketOpenGui(ID, x, y, z).makePacket(),
					(Player) player);
			return null;
		case GuiIds.EXTRACTOR:
			TileEnergyExtractor tile = (TileEnergyExtractor) world
					.getBlockTileEntity(x, y, z);
			return new ContainerExtractor(player.inventory, tile);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		switch (ID) {
		case GuiIds.TIMEMACHINE:
			TileTimeMachine tile = (TileTimeMachine) world.getBlockTileEntity(
					x, y, z);
			return new GuiScreenTT(256, 210, new WidgetCanvasTimeTravel(x, y,
					z, ((TMPartPanel) tile.getPart()).isCountingDown()));
		case GuiIds.TM_CONFLICT_NO_MB:
			return new GuiScreenTT(176, 75, new WidgetCanvasConflict(
					LangHelper
							.translate("message", "timeMachine.conflict.noMb")));
		case GuiIds.TM_CONFLICT_MODULES:
			return new GuiScreenTT(176, 75, new WidgetCanvasConflict(
					LangHelper.translate("message",
							"timeMachine.conflict.modules")));
		case GuiIds.EXTRACTOR:
			TileEnergyExtractor tileExtractor = (TileEnergyExtractor) world
					.getBlockTileEntity(x, y, z);
			return new GuiContainerTT(176, 187, new WidgetCanvasExtractor(0, 0,
					tileExtractor, player.inventory));
		}

		return null;
	}
}
