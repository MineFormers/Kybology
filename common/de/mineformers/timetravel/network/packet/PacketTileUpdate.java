package de.mineformers.timetravel.network.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;
import de.mineformers.timetravel.tileentity.TileTT;

/**
 * TimeTravel
 * 
 * PacketTileUpdate
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class PacketTileUpdate extends BasePacket {

	public int x, y, z;
	public byte orientation;
	public byte state;
	public String customName;

	public PacketTileUpdate() {

	}

	public PacketTileUpdate(int x, int y, int z, ForgeDirection orientation,
	        byte state, String customName) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.orientation = (byte) orientation.ordinal();
		this.state = state;
		this.customName = customName;
	}

	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
		out.writeByte(orientation);
		out.writeByte(state);
		out.writeUTF(customName);
	}

	@Override
	public void read(ByteArrayDataInput in) {
		x = in.readInt();
		y = in.readInt();
		z = in.readInt();
		orientation = in.readByte();
		state = in.readByte();
		customName = in.readUTF();
	}

	@Override
	public void execute(EntityPlayer player, Side side) {
		if (side.isClient()) {
			TileEntity te = player.worldObj.getBlockTileEntity(x, y, z);
			if (te instanceof TileTT) {
				((TileTT) te).setOrientation(orientation);
				((TileTT) te).setState(state);
				((TileTT) te).setCustomName(customName);
			}
		}
	}

}
