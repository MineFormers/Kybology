package de.mineformers.timetravel.core.util;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * TimeTravel
 * 
 * NetworkHelper
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class NetworkHelper {

	public static void sendTilePacket(World world, int x, int z, int y) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			sendTilePacket(tile);
		}
	}

	public static void sendTilePacket(TileEntity tile) {
		Packet packet = tile.getDescriptionPacket();
		PacketDispatcher.sendPacketToAllAround(tile.xCoord, tile.yCoord,
		        tile.zCoord, 32, tile.worldObj.provider.dimensionId, packet);
	}

}
