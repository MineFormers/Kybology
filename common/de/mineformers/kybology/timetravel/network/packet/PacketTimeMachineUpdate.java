package de.mineformers.kybology.timetravel.network.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;
import de.mineformers.kybology.core.network.packet.BasePacket;
import de.mineformers.kybology.core.tileentity.TileKybology;
import de.mineformers.kybology.timetravel.tileentity.TileTimeMachine;

/**
 * Kybology
 * 
 * PacketTimeMachineUpdate
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class PacketTimeMachineUpdate extends BasePacket {

	public int x, y, z;
	public byte orientation;
	public byte state;
	public String customName;
	public boolean valid;
	public int type;

	public PacketTimeMachineUpdate() {

	}

	public PacketTimeMachineUpdate(int x, int y, int z,
	        ForgeDirection orientation, byte state, String customName,
	        boolean valid, int type) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.orientation = (byte) orientation.ordinal();
		this.state = state;
		this.customName = customName;
		this.valid = valid;
		this.type = type;
	}

	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
		out.writeByte(orientation);
		out.writeByte(state);
		out.writeUTF(customName);
		out.writeBoolean(valid);
		out.writeInt(type);
	}

	@Override
	public void read(ByteArrayDataInput in) {
		x = in.readInt();
		y = in.readInt();
		z = in.readInt();
		orientation = in.readByte();
		state = in.readByte();
		customName = in.readUTF();
		valid = in.readBoolean();
		type = in.readInt();
	}

	@Override
	public void execute(EntityPlayer player, Side side) {
		if (side.isClient()) {
			TileEntity te = player.worldObj.getBlockTileEntity(x, y, z);
			if (te instanceof TileKybology) {
				((TileKybology) te).setOrientation(orientation);
				((TileKybology) te).setState(state);
				((TileKybology) te).setCustomName(customName);
				((TileTimeMachine) te).getPart().setValidMultiblock(valid);
				((TileTimeMachine) te).setTypeMeta(type);
			}
		}
	}

}
