package de.mineformers.kybology.timetravel.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.GameRegistry;
import de.mineformers.kybology.core.client.gui.GuiContainerKybology;
import de.mineformers.kybology.core.client.gui.GuiScreenKybology;
import de.mineformers.kybology.core.inventory.ContainerExtractor;
import de.mineformers.kybology.core.util.LangHelper;
import de.mineformers.kybology.timetravel.client.gui.WidgetCanvasConflict;
import de.mineformers.kybology.timetravel.client.gui.WidgetCanvasExtractor;
import de.mineformers.kybology.timetravel.client.gui.WidgetCanvasTimeTravel;
import de.mineformers.kybology.timetravel.lib.GuiIds;
import de.mineformers.kybology.timetravel.lib.Strings;
import de.mineformers.kybology.timetravel.network.packet.PacketOpenGui;
import de.mineformers.kybology.timetravel.tileentity.TileCrystalOre;
import de.mineformers.kybology.timetravel.tileentity.TileEnergyExtractor;
import de.mineformers.kybology.timetravel.tileentity.TileTimeMachine;
import de.mineformers.kybology.timetravel.travelling.timemachine.TMPartPanel;

/**
 * Kybology
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
                PacketDispatcher.sendPacketToPlayer(new PacketOpenGui(ID, x, y,
                        z).makePacket(), (Player) player);
                return null;
            case GuiIds.TM_CONFLICT_NO_MB:
                PacketDispatcher.sendPacketToPlayer(new PacketOpenGui(ID, x, y,
                        z).makePacket(), (Player) player);
                return null;
            case GuiIds.TM_CONFLICT_MODULES:
                PacketDispatcher.sendPacketToPlayer(new PacketOpenGui(ID, x, y,
                        z).makePacket(), (Player) player);
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
                TileTimeMachine tile = (TileTimeMachine) world
                        .getBlockTileEntity(x, y, z);
                return new GuiScreenKybology(256, 210,
                        new WidgetCanvasTimeTravel(x, y, z, ((TMPartPanel) tile
                                .getPart()).isCountingDown()));
            case GuiIds.TM_CONFLICT_NO_MB:
                return new GuiScreenKybology(176, 75, new WidgetCanvasConflict(
                        LangHelper.translate("message",
                                "timeMachine.conflict.noMb")));
            case GuiIds.TM_CONFLICT_MODULES:
                return new GuiScreenKybology(176, 75, new WidgetCanvasConflict(
                        LangHelper.translate("message",
                                "timeMachine.conflict.modules")));
            case GuiIds.EXTRACTOR:
                TileEnergyExtractor tileExtractor = (TileEnergyExtractor) world
                        .getBlockTileEntity(x, y, z);
                return new GuiContainerKybology(176, 187,
                        new WidgetCanvasExtractor(0, 0, tileExtractor,
                                player.inventory));
        }

        return null;
    }

    public void registerEventHandlers() {
    }
}
