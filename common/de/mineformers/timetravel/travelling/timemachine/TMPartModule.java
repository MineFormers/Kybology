package de.mineformers.timetravel.travelling.timemachine;

import de.mineformers.timetravel.network.packet.PacketTimeMachineUpdate;
import net.minecraft.tileentity.TileEntity;

/**
 * TimeTravel
 * 
 * TMPartModule
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class TMPartModule extends TimeMachinePart {

	public TMPartModule(TileEntity parent) {
		super(TimeMachinePart.TYPE_MODULE, parent);
	}

	@Override
	public PacketTimeMachineUpdate getPacket() {
		return null;
	}

}
