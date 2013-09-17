package de.mineformers.timetravel.network.packet;

import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import de.mineformers.timetravel.tileentity.TileTimeMachine;
import de.mineformers.timetravel.travelling.timemachine.TMPartPanel;

/**
 * TimeTravel
 * 
 * PacketStartTravel
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class PacketStartTravel extends BasePacket {

	public int x, y, z;

	public PacketStartTravel() {

	}

	public PacketStartTravel(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
	}

	@Override
	public void read(ByteArrayDataInput in) {
		this.x = in.readInt();
		this.y = in.readInt();
		this.z = in.readInt();
	}

	@Override
	public void execute(EntityPlayer player, Side side) {
		if (side.isServer()) {
			TileTimeMachine tile = (TileTimeMachine) player.worldObj
			        .getBlockTileEntity(x, y, z);
			((TMPartPanel) tile.getPart()).startCountdown();
			PacketDispatcher.sendPacketToAllAround(x, y, z, 64,
			        player.worldObj.provider.dimensionId,
			        tile.getDescriptionPacket());
		}
	}

}
