package de.mineformers.kybology.timetravel.network;

import de.mineformers.kybology.core.network.packet.BasePacket;
import de.mineformers.kybology.timetravel.network.packet.PacketOpenGui;
import de.mineformers.kybology.timetravel.network.packet.PacketSpawnParticle;
import de.mineformers.kybology.timetravel.network.packet.PacketStartTravel;
import de.mineformers.kybology.timetravel.network.packet.PacketTMModuleUpdate;
import de.mineformers.kybology.timetravel.network.packet.PacketTMPanelUpdate;
import de.mineformers.kybology.timetravel.network.packet.PacketTileUpdate;
import de.mineformers.kybology.timetravel.network.packet.PacketTimeMachineUpdate;
import de.mineformers.kybology.timetravel.network.packet.PacketUpdateWatch;

/**
 * Kybology
 * 
 * ModPackets
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ModPackets {

    public static void init() {
        BasePacket.registerPacket(PacketOpenGui.class);
        BasePacket.registerPacket(PacketTileUpdate.class);
        BasePacket.registerPacket(PacketTimeMachineUpdate.class);
        BasePacket.registerPacket(PacketTMPanelUpdate.class);
        BasePacket.registerPacket(PacketTMModuleUpdate.class);
        BasePacket.registerPacket(PacketStartTravel.class);
        BasePacket.registerPacket(PacketUpdateWatch.class);
        BasePacket.registerPacket(PacketSpawnParticle.class);
    }

}
