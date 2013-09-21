package de.mineformers.timetravel.network.packet;

import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;
import de.mineformers.timetravel.client.gui.overlay.GuiOverlayWatch;

/**
 * TimeTravel
 * 
 * PacketUpdateWatch
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class PacketUpdateWatch extends BasePacket {

	public int secsLeft;

	public PacketUpdateWatch() {
	}

	public PacketUpdateWatch(int secsLeft) {
		this.secsLeft = secsLeft;
	}

	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(secsLeft);
	}

	@Override
	public void read(ByteArrayDataInput in) {
		secsLeft = in.readInt();
	}

	@Override
	public void execute(EntityPlayer player, Side side) {
		if (side.isClient()) {
			GuiOverlayWatch.secsLeft = secsLeft;
		}
	}

}
